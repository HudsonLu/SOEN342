// Main.java
package Console;

import Authentication.Users;
import User.*;
import Catalog.*;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.SEVERE);
        Scanner scanner = new Scanner(System.in);

        // Initialize Spaces, Lessons
        Spaces spaces = new Spaces();
        Lessons lessons = new Lessons();

        // Main Menu Loop
        while (true) {
            System.out.println("Welcome to the Booking System!");
            System.out.println("1. View Public Offerings");
            System.out.println("2. Login");
            System.out.println("3. Sign Up");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> Offerings.displayPublicOfferings();
                case 2 -> login(scanner, lessons, spaces);
                case 3 -> signUp(scanner);
                case 4 -> {
                    System.out.println("Thank you for using the Booking System. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login(Scanner scanner, Lessons lessons, Spaces spaces) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        User user = Users.getUserByNameAndPhone(name, phoneNumber);
        if (user == null) {
            System.out.println("Invalid credentials. Please try again or sign up.");
            return;
        }

        if (user instanceof Administrator admin) {
            adminDashboard(scanner, admin, lessons, spaces);
        } else if (user instanceof Instructor instructor) {
            instructorDashboard(scanner, instructor, lessons);
        } else if (user instanceof Client client) {
            clientDashboard(scanner, client);
        } else {
            System.out.println("Unknown user type. Contact system administrator.");
        }
    }

    private static void signUp(Scanner scanner) {
        System.out.println("Sign Up:");
        System.out.println("1. Client");
        System.out.println("2. Instructor");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        switch (choice) {
            case 1 -> Users.addUser(new Client(name, phoneNumber));
            case 2 -> {
                System.out.print("Enter your specialization: ");
                String specialization = scanner.nextLine();

                System.out.print("Enter cities you are available in (comma-separated): ");
                List<String> cities = List.of(scanner.nextLine().split(","));
                Users.addUser(new Instructor(name, phoneNumber, specialization, cities));
            }
            default -> System.out.println("Invalid choice. Returning to Main Menu.");
        }

        System.out.println("User registered successfully!");
    }

    private static void adminDashboard(Scanner scanner, Administrator admin, Lessons lessons, Spaces spaces) {
        while (true) {
            System.out.println("Administrator Dashboard");
            System.out.println("1. View All Lessons");
            System.out.println("2. Cancel a Lesson");
            System.out.println("2. Cancel an Offering");
            System.out.println("4. Manage Accounts");
            System.out.println("5. View All Offerings");
            System.out.println("6. Create a Lesson");
            System.out.println("7. View Available Spaces");
            System.out.println("8. Log Out");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> lessons.displayAllLessons();
                case 2 -> admin.cancelLesson(lessons.getLessons());
                //case 3 -> admin.cancelOffering();
                case 4 -> {
                    System.out.println("1. View Accounts");
                    System.out.println("2. Delete an Account");
                    System.out.print("Enter your choice: ");
                    int accountChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (accountChoice == 1) {
                        admin.viewAccounts();
                    } else if (accountChoice == 2) {
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Phone Number: ");
                        String phone = scanner.nextLine();
                        admin.deleteAccount(name, phone);
                    } else {
                        System.out.println("Invalid choice.");
                    }
                }
                case 5 -> admin.viewAllOfferings();
                case 6 -> admin.createLesson(spaces.getSpaces(), lessons.getLessons());
                case 7 -> admin.viewSpaces(spaces.getSpaces());
                case 8 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void clientDashboard(Scanner scanner, Client client) {
        while (true) {
            System.out.println("Client Dashboard");
            System.out.println("1. View Public Offerings");
            System.out.println("2. Book an Offering");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. View Personal Bookings");
            System.out.println("5. Log Out");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> Offerings.displayPublicOfferings();
                case 2 -> {
                    System.out.println("Select an offering to book (Enter the index):");
                    int index = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Offerings.bookOffering(index, client.getName());
                }
                case 3 -> client.cancelBooking();
                case 4 -> client.viewPersonalBookings();
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void instructorDashboard(Scanner scanner, Instructor instructor, Lessons lessons) {
        while (true) {
            System.out.println("Instructor Dashboard");
            System.out.println("1. Create an Offering");
            System.out.println("2. View All Offerings");
            System.out.println("3. View Lessons Not Associated with Offerings");
            System.out.println("4. Log Out");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> instructor.createOffering(lessons.getLessons());
                case 2 -> instructor.viewAllOfferings();
                case 3 -> instructor.viewUnassociatedLessons(lessons);
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
