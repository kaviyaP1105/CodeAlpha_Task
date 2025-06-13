/*✅ TASK 4: Hotel Reservation System
● Design a system to search, book, and manage hotel rooms.
● Add room categorization (e.g., Standard, Deluxe, Suite)
● Allow users to make and cancel reservations.
● Implement payment simulation and booking details view.
● Use OOP + database/File I/O for storing bookings and availability. */
import java.io.*;
import java.util.*;

class Room {
    String category;
    int roomNumber;
    boolean isBooked;

    Room(String category, int roomNumber) {
        this.category = category;
        this.roomNumber = roomNumber;
        this.isBooked = false;
    }

    public String toString() {
        return "Room " + roomNumber + " (" + category + ") - " + (isBooked ? "Booked" : "Available");
    }
}

class Booking {
    String customerName;
    int roomNumber;
    String category;

    Booking(String customerName, int roomNumber, String category) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
    }

    public String toString() {
        return "Customer: " + customerName + ", Room: " + roomNumber + " (" + category + ")";
    }
}

public class HotelReservationSystem {
    static List<Room> rooms = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static final String FILE_NAME = "bookings.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadData();

        initializeRooms();

        while (true) {
            System.out.println("\n--- Hotel Reservation Menu ---");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View My Booking");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1 -> viewAvailableRooms();
                case 2 -> bookRoom(sc);
                case 3 -> cancelBooking(sc);
                case 4 -> viewMyBooking(sc);
                case 5 -> {
                    saveData();
                    System.out.println("Thank you for using the Hotel Reservation System!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void initializeRooms() {
        if (!rooms.isEmpty()) return;

        for (int i = 101; i <= 105; i++) rooms.add(new Room("Standard", i));
        for (int i = 201; i <= 203; i++) rooms.add(new Room("Deluxe", i));
        for (int i = 301; i <= 302; i++) rooms.add(new Room("Suite", i));

        for (Booking b : bookings) {
            for (Room r : rooms) {
                if (r.roomNumber == b.roomNumber) r.isBooked = true;
            }
        }
    }

    static void viewAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room r : rooms) {
            if (!r.isBooked) System.out.println(r);
        }
    }

    static void bookRoom(Scanner sc) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Choose category (Standard/Deluxe/Suite): ");
        String cat = sc.nextLine();

        for (Room r : rooms) {
            if (r.category.equalsIgnoreCase(cat) && !r.isBooked) {
                r.isBooked = true;
                bookings.add(new Booking(name, r.roomNumber, r.category));
                System.out.println("Booking successful! Your room number is: " + r.roomNumber);
                simulatePayment(sc);
                return;
            }
        }
        System.out.println("Sorry, no available rooms in that category.");
    }

    static void simulatePayment(Scanner sc) {
        System.out.print("Enter payment amount to confirm (simulated): ");
        double amt = sc.nextDouble();
        System.out.println("Payment of ₹" + amt + " received. Booking confirmed.");
    }

    static void cancelBooking(Scanner sc) {
        System.out.print("Enter your name to cancel booking: ");
        String name = sc.nextLine();
        Iterator<Booking> it = bookings.iterator();
        boolean found = false;

        while (it.hasNext()) {
            Booking b = it.next();
            if (b.customerName.equalsIgnoreCase(name)) {
                for (Room r : rooms) {
                    if (r.roomNumber == b.roomNumber) r.isBooked = false;
                }
                it.remove();
                System.out.println("Booking cancelled for " + name);
                found = true;
            }
        }

        if (!found) System.out.println("No booking found with that name.");
    }

    static void viewMyBooking(Scanner sc) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        boolean found = false;
        for (Booking b : bookings) {
            if (b.customerName.equalsIgnoreCase(name)) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) System.out.println("No booking found.");
    }

    static void saveData() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Booking b : bookings) {
                pw.println(b.customerName + "," + b.roomNumber + "," + b.category);
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    static void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                bookings.add(new Booking(parts[0], Integer.parseInt(parts[1]), parts[2]));
            }
        } catch (IOException e) {
            // No previous data found — that’s okay
        }
    }
}
