import java.sql.Date;
import java.util.Objects;
import java.util.Scanner;


//TODO: I have to do something with this gigantic menu. Maybe dividing each menu into a different class/function?
public class MainMenu {
    static boolean restart = false;
    static boolean admin = false;

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
            } else if (!admin) {
                //TODO: Figure out if this is the best placement for the balance
                System.out.println("Current balance: " + BalanceDAO.getBalance());
                System.out.println("1. Profile");
                System.out.println("2. Add Transaction");
                System.out.println("3. Transfer to an another user");
                System.out.println("4. View Transactions");
                System.out.println("8. Update user information");
                System.out.println("9. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showProfile();
                    case 2 -> addTransaction(scanner);
                    case 3 -> transferTo(scanner);
                    case 4 -> viewTransactions(scanner);
                    case 8 -> updateInformation(scanner);
                    case 9 -> {
                        System.out.println("Exiting...");
                        restart = false;
                        Session.clearSession();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } else {
                System.out.println("Admin Panel");
                System.out.println("1. View All Users");
                System.out.println("2. View User details");
                System.out.println("3. View All User Transactions"); //TODO: Should I implement being able to see transaction of a certain date range?
                System.out.println("4. Delete User");
                System.out.println("9. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                int userID;

                switch (choice) {
                    case 1:
                        AdminDAO.viewAllUsers();
                        break;
                    case 2:
                        System.out.print("Enter user ID: ");
                        userID = scanner.nextInt();
                        AdminDAO.viewUser(userID);
                        break;
                    case 3:
                        System.out.print("Enter user ID: ");
                        userID = scanner.nextInt();
                        AdminDAO.viewUserTransactions(userID);
                        break;
                    case 4:
                        System.out.print("Enter user ID to delete: ");
                        userID = scanner.nextInt();
                        AdminDAO.deleteUser(userID);
                        break;
                    case 9:
                        System.out.println("Exiting admin panel...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
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

        if (username.equals("__admin__") && password.equals("87654321")) { //Admin Panel enter logic


            admin = true;
            restart = true;
        } else if (UserDAO.login(username, password)) { //User log in
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
        System.out.println("Enter transaction amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        //TODO: Add more types. Maybe add so that depending on whether the amount is negative or positive
        //TODO: it would give different types of tags.
        System.out.println("Choose a tag:");
        String[] tags = {"Food", "Rent", "Salary", "Entertainment", "Other"};
        for (int i = 0; i < tags.length; i++) {
            System.out.println((i + 1) + ". " + tags[i]);
        }
        System.out.println("0. Enter a custom tag");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String tag;
        if (choice == 0) {
            System.out.println("Enter custom tag:");
            tag = scanner.nextLine().trim();
        } else if (choice > 0 && choice <= tags.length) {
            tag = tags[choice - 1];
        } else {
            System.out.println("Invalid choice. Using 'Other'.");
            tag = "Other";
        }

        boolean success = TransactionDAO.addTransaction(Session.getCurrentID(), amount, tag);
        if (success) {
            System.out.println("Transaction added successfully!");
        } else {
            System.out.println("Failed to add transaction.");
        }
    }


    private static void viewTransactions(Scanner scanner) {
        System.out.print("Select start date (YYYY-MM-DD or * to get all dates): ");
        String startDateInput = scanner.nextLine().trim();

        if (startDateInput.equals("*")) {
            TransactionDAO.listTransactions(Session.getCurrentID());

            double pos = BalanceDAO.positiveSum(Session.getCurrentID());
            double neg = BalanceDAO.negativeSum(Session.getCurrentID());
            System.out.println("Total Income: " + pos);
            System.out.println("Total Expense: " + neg);

            return;
        }

        System.out.print("Select end date (YYYY-MM-DD): ");
        String endDateInput = scanner.nextLine().trim();

        try {
            Date startDate = Date.valueOf(startDateInput); // Converts string to SQL Date
            Date endDate = Date.valueOf(endDateInput);

            if (startDate.after(endDate)) {
                System.out.println("Start date cannot be after end date. Please try again.");
                return;
            }

            TransactionDAO.listTransactions(Session.getCurrentID(), startDate, endDate);

            double pos = BalanceDAO.positiveSum(Session.getCurrentID(), startDate, endDate);
            double neg = BalanceDAO.negativeSum(Session.getCurrentID(), startDate, endDate);
            System.out.println("Total Income: " + pos);
            System.out.println("Total Expense: " + neg);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }


    }

    private static void transferTo(Scanner scanner) {
        System.out.print("Enter username of the receiver: ");
        String username = scanner.nextLine().trim();
        int idReceiver = UserDAO.findIDByUsername(username);

        System.out.print("Enter the amount to transfer: ");
        String amountInput = scanner.nextLine();
        double amount = Double.parseDouble(amountInput);

        if(TransactionDAO.TransferTo(idReceiver, amount))
            System.out.println("Transfer done successfully");
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
