// Administrator.java
package User;

import Authentication.UserRepository;
import Booking.Offering;
import Booking.OfferingStatus;
import Lesson.*;
import Catalog.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Administrator extends User {

    private Lessons lessons;
    private Spaces spaces;

    // Full constructor with all parameters
    public Administrator(String name, String phoneNumber, Lessons lessons, Spaces spaces) {
        super(name, phoneNumber, "Administrator");
        this.lessons = lessons;
        this.spaces = spaces;
    }

    // View all offerings
    public void viewAllOfferings() {
        System.out.println("All Offerings:");
        Offerings.displayOfferings();
    }

    // View all user accounts
    public void viewAccounts() {
        System.out.println("All Registered Accounts:");
        UserRepository.getAllUsers().forEach(user ->
                System.out.println(user.getRole() + ": " + user.getName() + " (" + user.getPhoneNumber() + ")")
        );
    }

    // Delete a user account
    public void deleteAccount(String name, String phoneNumber) {
        User user = UserRepository.getUserByNameAndPhone(name, phoneNumber);
        if (user == null) {
            System.out.println("No user found with the given details.");
            return;
        }
        UserRepository.getAllUsers().remove(user);
        System.out.println("Account deleted successfully: " + name);
    }

    // Overloaded constructor for simpler creation
    public Administrator(String name, String phoneNumber) {
        super(name, phoneNumber, "Administrator");
        this.lessons = new Lessons(new Spaces()); // Default Lessons instance
        this.spaces = new Spaces();               // Default Spaces instance
    }

    @Override
    public void performRoleSpecificActions() {
        System.out.println("Administrator Dashboard: Manage all users, offerings, and system settings.");
    }

    public void createLesson() {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Display available spaces
        System.out.println("Available Spaces:");
        spaces.displaySpaces();

        // Step 2: Allow admin to select a space
        System.out.println("Enter the index of the space you want to assign to this lesson:");
        int spaceIndex = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (spaceIndex < 1 || spaceIndex > spaces.getSpaces().size()) {
            System.out.println("Error: Invalid space index selected.");
            return;
        }

        Space selectedSpace = spaces.getSpaces().get(spaceIndex - 1);
        // Step 3: Check if the space has availability
        System.out.println("Selected Space: " + selectedSpace.getDetails());
        System.out.println("Enter Lesson Day (e.g., MONDAY, TUESDAY):");
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(scanner.nextLine().toUpperCase());

        if (!selectedSpace.getAvailableDays().contains(dayOfWeek)) {
            System.out.println("Error: The selected space is not available on the specified day.");
            return;
        }
        // Step 4: Gather remaining lesson details
        System.out.println("Enter Lesson Name:");
        String lessonName = scanner.nextLine();

        System.out.println("Enter Start Time (HH:MM):");
        LocalTime startTime = LocalTime.parse(scanner.nextLine());

        System.out.println("Enter End Time (HH:MM):");
        LocalTime endTime = LocalTime.parse(scanner.nextLine());

        System.out.println("Is the lesson private? (yes/no):");
        boolean isPrivate = scanner.nextLine().equalsIgnoreCase("yes");

        System.out.println("Enter the Date Range (e.g., Sep 1 - Nov 30, 2024):");
        String dateRange = scanner.nextLine();

        // Step 5: Create and add the new lesson
        Lesson newLesson = new Lesson(startTime, endTime, true, lessonName, isPrivate, selectedSpace, dayOfWeek, dateRange);
        lessons.getLessons().add(newLesson);

        System.out.println("Lesson created successfully!");
    }

    // View all available spaces
    public void viewSpaces(Spaces spaces) {
        System.out.println("Available Spaces:");
        spaces.displaySpaces();
    }

    public void updateOfferingsToAvailable() {
        System.out.println("Offerings Currently Unavailable to the Public:");
        Offerings.displayOfferingsByStatus(OfferingStatus.UNAVAILABLE);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an offering to make available to the public (Enter the index):");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<Offering> unavailableOfferings = Offerings.getOfferingsByStatus(OfferingStatus.UNAVAILABLE);
        if (index < 1 || index > unavailableOfferings.size()) {
            System.out.println("Invalid selection. Operation canceled.");
            return;
        }

        Offering selectedOffering = unavailableOfferings.get(index - 1);
        selectedOffering.setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);

        System.out.println("Offering updated successfully! New status: AVAILABLE_TO_PUBLIC");
    }


    // Method to create an offering
    public void createOffering(String offeringName) {
        System.out.println("Creating offering: " + offeringName);
    }

    // Method to delete a user account
    public void deleteAccount(User user) {
        System.out.println("Deleting user account: " + user.getName());
    }

    public void cancelLesson() {
        System.out.println("Available Lessons for Cancellation:");
        lessons.displayCancellableLessons();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a lesson to cancel (Enter the index):");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        lessons.removeLesson(index);
    }

    public void cancelOffering() {
        System.out.println("Available Offerings for Cancellation:");
        Offerings.displayCancellableOfferings();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an offering to cancel (Enter the index):");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Offerings.cancelOffering(index);
    }

}

