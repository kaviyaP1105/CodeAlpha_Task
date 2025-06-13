/*✅ TASK 2: Stock Trading Platform
● Simulate a basic stock trading environment.
● Add features for market data display and buy/sell operations.
● Allow users to track portfolio performance over time.
● Use Object-Oriented Programming (OOP) to manage stocks, users and transactions.
● Optionally, include file I/O or database to persist portfolio data. */
import java.util.*;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    double balance;

    Portfolio(double balance) {
        this.balance = balance;
    }

    void buyStock(String symbol, int quantity, double price) {
        double cost = quantity * price;
        if (cost <= balance) {
            balance -= cost;
            holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
            System.out.println("Bought " + quantity + " of " + symbol);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    void sellStock(String symbol, int quantity, double price) {
        if (holdings.getOrDefault(symbol, 0) >= quantity) {
            balance += quantity * price;
            holdings.put(symbol, holdings.get(symbol) - quantity);
            System.out.println("Sold " + quantity + " of " + symbol);
        } else {
            System.out.println("Not enough stocks to sell!");
        }
    }

    void displayPortfolio() {
        System.out.println("Current Balance: $" + balance);
        System.out.println("Holdings: " + holdings);
    }
}

public class StockTradingPlatform {
    static Map<String, Stock> market = new HashMap<>();
    static {
        market.put("AAPL", new Stock("AAPL", 150.0));
        market.put("GOOG", new Stock("GOOG", 2800.0));
        market.put("TSLA", new Stock("TSLA", 700.0));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Portfolio portfolio = new Portfolio(10000.0); // Starting with $10,000

        while (true) {
            System.out.println("\n--- Stock Trading Menu ---");
            System.out.println("1. Display Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int ch = sc.nextInt();

            if (ch == 1) {
                System.out.println("Available Stocks:");
                for (Stock s : market.values()) {
                    System.out.println(s.symbol + " - $" + s.price);
                }
            } else if (ch == 2) {
                System.out.print("Enter stock symbol to buy: ");
                String symbol = sc.next().toUpperCase();
                if (market.containsKey(symbol)) {
                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();
                    portfolio.buyStock(symbol, qty, market.get(symbol).price);
                } else {
                    System.out.println("Stock not found.");
                }
            } else if (ch == 3) {
                System.out.print("Enter stock symbol to sell: ");
                String symbol = sc.next().toUpperCase();
                if (market.containsKey(symbol)) {
                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();
                    portfolio.sellStock(symbol, qty, market.get(symbol).price);
                } else {
                    System.out.println("Stock not found.");
                }
            } else if (ch == 4) {
                portfolio.displayPortfolio();
            } else if (ch == 5) {
                System.out.println("Exiting... Thank you!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
