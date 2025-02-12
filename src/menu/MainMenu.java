package menu;

import utils.Session;

import java.util.Scanner;

abstract public class MainMenu {
    public static void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Personal Finance Tracker");
        while (true) {
            if (!Session.isUser()) {
                UnregisteredMenu.showMenu(scanner);
            } else if (!Session.isAdmin()) {
                UserMenu.showMenu(scanner);
            } else {
                AdminMenu.showMenu(scanner);
            }
        }
    }

    public static int getValidChoice(Scanner scanner, int min, int max) {
        int choice = -1;
        boolean valid = false;
        while (!valid) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    valid = true;
                } else {
                    System.out.println("Invalid choice. Please choose a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        scanner.nextLine(); // Consume newline character
        return choice;
    }
}
