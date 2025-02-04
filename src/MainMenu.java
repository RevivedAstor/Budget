import java.util.Scanner;

public class MainMenu {
    static boolean restart = false;

    public static void start() {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            //The menu the user gets before logging in
            if (!restart) {
                System.out.println("Welcome to Personal Finance Tracker");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("9. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> register(scanner);
                    case 2 -> login(scanner);
                    case 9 -> {
                        System.out.println("Exiting...");
                        Session.clearSession();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            //The menu user gets after logging in
            } else {
                System.out.println("1. Profile");
                System.out.println("2. Add Transaction");
                System.out.println("9. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showProfile();
                    case 2 -> addTransaction(scanner);
                    case 9 -> {
                        System.out.println("Exiting...");
                        Session.clearSession();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

//PROFILE
    private static void showProfile() {
        if (Session.getCurrentUsername() != null) {
            System.out.println("Profile Information:");
            System.out.println("ID: " + Session.getCurrentID());
            System.out.println("Username: " + Session.getCurrentUsername());
            System.out.println("Email: " + Session.getCurrentEmail());
        } else {
            System.out.println("You need to be logged in to view your profile.");
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean success = UserDAO.register(username, email, password);
        System.out.println(success ? "Registration successful!" : "Registration failed.");
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (UserDAO.login(username, password)) {
            System.out.println("Login successful! Welcome, " + Session.getCurrentUsername());
            //We set restart to true here so that the user would get access to profile related options
            restart = true;
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

//TRANSACTIONS
private static void addTransaction(Scanner scanner) {
    System.out.print("Enter amount (prefix with '+' for income, '-' for expense): ");
    String amountInput = scanner.nextLine().trim();

    if (amountInput.isEmpty() || (!amountInput.startsWith("+") && !amountInput.startsWith("-"))) {
        System.out.println("Invalid input. Please start with '+' for income or '-' for expense.");
        return;
    }

    boolean type = amountInput.charAt(0) == '-'; // True for '-', False for '+'
    double amount;

    try {
        amount = Double.parseDouble(amountInput.substring(1)); // Remove '+' or '-' and parse the number
    } catch (NumberFormatException e) {
        System.out.println("Invalid amount format.");
        return;
    }

    System.out.print("Enter description: ");
    String description = scanner.nextLine().trim();

    TransactionDAO.addTransaction(Session.getCurrentUsername(), type, amount, description);
    System.out.println("Transaction added successfully.");
}
}
