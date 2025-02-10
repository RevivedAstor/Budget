import java.util.Objects;
import java.util.Scanner;


//TODO: Maybe dividing this class into several different ones would not be bad
public class MainMenu {
    static boolean restart = false;

    public static void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Personal Finance Tracker");
        while (true) {
            //The menu the user gets before logging in
            if (!restart) {
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
                //TODO: Figure out if this is the best placement for the balance
                System.out.println("Current balance: " + BalanceDAO.getBalance());
                System.out.println("1. Profile");
                System.out.println("2. Add Transaction");
                System.out.println("3. View Transactions");
                System.out.println("4. Transfer to an another user");
                System.out.println("8. Update user information");
                System.out.println("9. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showProfile();
                    case 2 -> addTransaction(scanner);
                    case 3 -> viewTransactions();
                    case 4 -> transferTo(scanner);
                    case 8 -> updateInformation(scanner);
                    case 9 -> {
                        System.out.println("Exiting...");
                        restart = false;
                        Session.clearSession();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Registration and Login
    private static void register(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        String password = validatePassword(scanner);

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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //PROFILE
    private static void showProfile() {
        UserDAO.description();
    }

    private static void updateInformation(Scanner scanner) {
        System.out.println("What would you like to update?");
        System.out.println("1. Username");
        System.out.println("2. Email");
        System.out.println("3. Password");

        int choice = scanner.nextInt();
        scanner.nextLine();

        boolean success;
        String placeholder;
        switch (choice) {
            case 1 -> {
                System.out.print("Write your new username: ");
                placeholder = scanner.nextLine().trim();

                success = UserDAO.updateUsername(placeholder);
                System.out.println(success ? "Username changed successfully" : "Update failed");
            }
            case 2 -> {
                System.out.print("Write your new email: ");
                placeholder = scanner.nextLine().trim();

                success = UserDAO.updateEmail(placeholder);
                System.out.println(success ? "Email changed successfully" : "Update failed");
            }
            case 3 -> {
                System.out.print("Write your current password: ");
                placeholder = scanner.nextLine().trim();


                while (!Objects.equals(placeholder, Session.getCurrentPassword())) {
                    System.out.print("Old password is uncorrected. Try again: ");
                    placeholder = scanner.nextLine().trim();
                }

                placeholder = validatePassword(scanner);


                success = UserDAO.updatePassword(placeholder);
                System.out.println(success ? "Password changed successfully" : "Update failed");
            }
            default -> System.out.println("Invalid choice. Try again.");
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TRANSACTIONS
    private static void addTransaction(Scanner scanner) {
        System.out.print("Enter amount (prefix with '+' for income, '-' for expense): ");
        String amountInput = scanner.nextLine().trim();

        double amount = Double.parseDouble(amountInput);

        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();

        if (TransactionDAO.addTransaction(amount, description))
            System.out.println("Transaction added successfully.");
    }

    private static void viewTransactions() {
        TransactionDAO.listTransactions(Session.getCurrentID());

        double pos = BalanceDAO.positiveSum(Session.getCurrentID());
        double neg = BalanceDAO.negativeSum(Session.getCurrentID());
        System.out.println(pos + " | " + neg);
    }

    private static void transferTo(Scanner scanner) {
        System.out.print("Enter username of the receiver: ");
        String username = scanner.nextLine().trim();
        int idReceiver = UserDAO.findIDByUsername(username);

        System.out.print("Enter the amount to transfer: ");
        String amountInput = scanner.nextLine();
        double amount = Double.parseDouble(amountInput);

        TransactionDAO.TransferTo(idReceiver, amount);
    }

    //SMALL FUNCTIONS
    //TODO: make so that when updating the password it would show "new password" instead of "password"
    private static String validatePassword(Scanner scanner) {
        System.out.print("Enter password: ");
        String password1 = scanner.nextLine();
        System.out.print("Validate password: ");
        String password2 = scanner.nextLine();

        while (!Objects.equals(password1.trim(), password2.trim())) {
            System.out.println("Passwords are not the same. Please try again.");
            System.out.print("Enter password: ");
            password1 = scanner.nextLine();
            System.out.print("Validate password: ");
            password2 = scanner.nextLine();
        }

        return password1.trim();
    }
}
