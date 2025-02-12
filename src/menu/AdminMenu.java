package menu;

import dao.AdminDAO;
import utils.Session;
import utils.UserUtils;

import java.util.Scanner;

public class AdminMenu extends MainMenu {
    public static void showMenu(Scanner scanner) {
        System.out.println("Admin Panel");
        System.out.println("1. View All Users");
        System.out.println("2. View User Details");
        System.out.println("3. View User Transactions");//TODO: Should I implement being able to see transaction of a certain date range?
        System.out.println("4. Delete User");
        System.out.println("9. Logout");

        int choice = getValidChoice(scanner, 1, 9);

        String username;
        int userID;

        switch (choice) {
            case 1 -> AdminDAO.viewAllUsers();
            case 2 -> {
                System.out.print("Enter username: ");
                username = scanner.nextLine();
                userID = UserUtils.findIDByUsername(username);
                AdminDAO.viewUser(userID);
            }
            case 3 -> {
                System.out.print("Enter username: ");
                username = scanner.nextLine();
                userID = UserUtils.findIDByUsername(username);
                AdminDAO.viewUserTransactions(userID);
            }
            case 4 -> {
                System.out.print("Enter username to delete: ");
                username = scanner.nextLine();
                userID = UserUtils.findIDByUsername(username);
                AdminDAO.deleteUser(userID);
            }
            case 9 -> {
                Session.clearSession();
                UnregisteredMenu.showMenu(scanner);
            }
            default -> System.out.println("Invalid choice. Try again.");
        }
    }
}

