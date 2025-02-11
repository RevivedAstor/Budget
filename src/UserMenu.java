import java.sql.Date;
import java.util.Objects;
import java.util.Scanner;

public class UserMenu extends MainMenu {
    public static void showMenu(Scanner scanner) {
        System.out.println("Current balance: " + BalanceDAO.getBalance());
        System.out.println("1. Profile");
        System.out.println("2. Add Transaction");
        System.out.println("3. Transfer to Another User");
        System.out.println("4. View Transactions");
        System.out.println("8. Update User Information");
        System.out.println("9. Logout");

        int choice = getValidChoice(scanner, 1, 9);

        switch (choice) {
            case 1 -> UserDAO.description();
            case 2 -> addTransaction(scanner);
            case 3 -> transferTo(scanner);
            case 4 -> viewTransactions(scanner);
            case 8 -> updateInformation(scanner);
            case 9 -> {
                Session.clearSession();
                UnregisteredMenu.showMenu(scanner);
            }
            default -> System.out.println("Invalid choice. Try again.");
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

                placeholder = validateNewPassword(scanner);


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
        int idReceiver = UserUtils.findIDByUsername(username);

        System.out.print("Enter the amount to transfer: ");
        String amountInput = scanner.nextLine();
        double amount = Double.parseDouble(amountInput);

        if(TransactionDAO.TransferTo(idReceiver, amount))
            System.out.println("Transfer done successfully");
    }


    private static String validateNewPassword(Scanner scanner) {
        String password;
        while (true) {
            System.out.println("New password should have minimum 8 characters, 1 uppercase, 1 number, and 1 special character");
            System.out.print("Enter new password: ");
            password = scanner.nextLine().trim();

            if (ValidatorUtil.isValidPassword(password)) {
                System.out.print("Validate new password: ");
                String confirmPassword = scanner.nextLine().trim();

                if (password.equals(confirmPassword)) {
                    return password;
                } else {
                    System.out.println("Passwords do not match. Please try again.");
                }
            } else {
                System.out.println("Password does not meet the requirements. Please try again.");
            }
        }
    }
}

