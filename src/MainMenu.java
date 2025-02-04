import java.util.Scanner;

public class MainMenu {
    static boolean restart = false;

    public static void start() {
        Scanner scanner = new Scanner(System.in);


        while (true) {
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
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }  else {
                System.out.println("1. Profile");
                System.out.println("9. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {showProfile();}
                    case 9 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    private static void showProfile() {
        if (Session.getCurrentUsername() != null) {
            System.out.println("Profile Information:");
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

        restart = true;
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (UserDAO.login(username, password)) {
            System.out.println("Login successful! Welcome, " + Session.getCurrentUsername());
        } else {
            System.out.println("Invalid credentials. Try again.");
        }

        restart = true;
    }
}
