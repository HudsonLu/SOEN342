package org.example;

import Authentication.LoginService;
import User.User;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the System");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                // Login
                System.out.print("Enter your name: ");
                String name = scanner.nextLine();

                System.out.print("Enter your phone number: ");
                String phoneNumber = scanner.nextLine();

                User user = LoginService.authenticate(name, phoneNumber);
                if (user != null) {
                    user.performRoleSpecificActions();
                }
            } else if (choice == 2) {
                // Register
                System.out.print("Enter your name: ");
                String name = scanner.nextLine();

                System.out.print("Enter your phone number: ");
                String phoneNumber = scanner.nextLine();

                System.out.print("Enter your role (Instructor/Client): ");
                String role = scanner.nextLine();

                if (role.equalsIgnoreCase("Instructor")) {
                    System.out.print("Enter your specialization: ");
                    String specialization = scanner.nextLine();

                    System.out.print("Enter available cities (comma-separated): ");
                    String cities = scanner.nextLine();
                    List<String> availableCities = Arrays.asList(cities.split(",\\s*"));

                    LoginService.register(name, phoneNumber, role, specialization, availableCities);
                } else if (role.equalsIgnoreCase("Client")) {
                    LoginService.register(name, phoneNumber, role, null, null);
                } else {
                    System.out.println("Invalid role. Registration failed.");
                }
            } else if (choice == 3) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}

