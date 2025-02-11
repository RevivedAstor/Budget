import java.util.Objects;
import java.util.Scanner;

public class UnregisteredMenu extends MainMenu{
    public static void showMenu(Scanner scanner) {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("9. Exit");
        System.out.print("Choose an option: ");

        int choice = getValidChoice(scanner, 1, 9);

        switch (choice) {
            case 1 -> register(scanner);
            case 2 -> login(scanner);
            case 9 -> {
                Session.clearSession();
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Try again.");
        }
    }

    private static void register(Scanner scanner) {
        System.out.println("Username should be 3-20 characters long");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        // Check if username is valid
        while (!ValidatorUtil.isValidUsername(username)) {
            System.out.print("Username does not match the requirements, try again: ");
            username = scanner.nextLine().trim();
        }

        // Check if username already exists (simple check for uniqueness)
        while (UserUtils.usernameExists(username)) {
            System.out.print("Username already exists. Please enter a different username: ");
            username = scanner.nextLine().trim();
        }

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        // Validate email format
        while (!ValidatorUtil.isValidEmail(email)) {
            System.out.print("Invalid email format. Please enter a valid email: ");
            email = scanner.nextLine().trim();
        }

        String password = validatePassword(scanner);

        boolean success = UserDAO.register(username, email, password);
        System.out.println(success ? "Registration successful!" : "Registration failed.");
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (username.equals("__admin__") && password.equals("87654321")) {
            Session.setAdmin();
            Session.setUser();
        } else if (UserDAO.login(username, password)) {
            System.out.println("Login successful! Welcome, " + Session.getCurrentUsername());
            Session.setUser();
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private static String validatePassword(Scanner scanner) {
        String password;
        while (true) {
            System.out.println("Password should have minimum 8 characters, 1 uppercase, 1 number, and 1 special character");
            System.out.print("Enter password: ");
            password = scanner.nextLine().trim();

            if (ValidatorUtil.isValidPassword(password)) {
                System.out.print("Validate password: ");
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
