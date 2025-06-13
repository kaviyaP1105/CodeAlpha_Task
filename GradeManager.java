/*✅ TASK 1: Student Grade Tracker
● Build a Java program to input and manage student grades.
● Calculate average, highest, and lowest scores.
● Use arrays or ArrayLists to store and manage data.
● Display a summary report of all students.
● Make the interface console-based or GUI-based as desired. */
import java.util.*;

class Student {
    String name;
    ArrayList<Integer> grades;

    Student(String name, ArrayList<Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    double getAverage() {
        int sum = 0;
        for (int g : grades) sum += g;
        return sum / (double) grades.size();
    }

    int getHighest() {
        return Collections.max(grades);
    }

    int getLowest() {
        return Collections.min(grades);
    }

    void displayReport() {
        System.out.println("Name: " + name);
        System.out.println("Grades: " + grades);
        System.out.println("Average: " + getAverage());
        System.out.println("Highest: " + getHighest());
        System.out.println("Lowest: " + getLowest());
        System.out.println("---------------------------");
    }
}

public class GradeManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int num = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < num; i++) {
            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            ArrayList<Integer> grades = new ArrayList<>();
            System.out.print("Enter number of subjects: ");
            int subjects = sc.nextInt();
            System.out.println("Enter grades:");
            for (int j = 0; j < subjects; j++) {
                grades.add(sc.nextInt());
            }
            sc.nextLine(); // consume newline
            students.add(new Student(name, grades));
        }

        System.out.println("\n------ STUDENT REPORT ------");
        for (Student s : students) {
            s.displayReport();
        }
    }
}
