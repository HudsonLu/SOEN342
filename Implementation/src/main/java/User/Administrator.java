// Administrator.java
package User;

import Authentication.Users;
import Booking.Offering;
import Booking.OfferingStatus;
import Lesson.Lesson;
import Lesson.Space;
import Catalog.Offerings;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "Administrator")
public class Administrator extends User {

    // Constructor
    public Administrator(String name, String phoneNumber) {
        super(name, phoneNumber, "Administrator");
    }

    public Administrator() {
    }

    @Override
    public void performRoleSpecificActions() {
        System.out.println("Administrator Dashboard: Manage all users, offerings, and system settings.");
    }

    public void createLesson(List<Space> spaces, List<Lesson> lessons) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Display available spaces
        System.out.println("Available Spaces:");
        for (int i = 0; i < spaces.size(); i++) {
            System.out.println((i + 1) + ". " + spaces.get(i).getDetails());
        }

        // Step 2: Allow admin to select a space
        System.out.println("Enter the index of the space you want to assign to this lesson:");
        int spaceIndex = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (spaceIndex < 1 || spaceIndex > spaces.size()) {
            System.out.println("Error: Invalid space index selected.");
            return;
        }

        Space selectedSpace = spaces.get(spaceIndex - 1);

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
        lessons.add(newLesson);

        System.out.println("Lesson created successfully!");
    }

    public void viewSpaces(List<Space> spaces) {
        System.out.println("Available Spaces:");
        for (Space space : spaces) {
            System.out.println(space.getDetails());
        }
    }

    public void cancelLesson(List<Lesson> lessons) {
        System.out.println("Available Lessons for Cancellation:");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println((i + 1) + ". " + lessons.get(i).getLessonName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a lesson to cancel (Enter the index):");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (index < 1 || index > lessons.size()) {
            System.out.println("Invalid selection. Operation canceled.");
            return;
        }

        lessons.remove(index - 1);
        System.out.println("Lesson removed successfully.");
    }

    public void cancelOffering(List<Offering> offerings) {
        System.out.println("Available Offerings for Cancellation:");
        for (int i = 0; i < offerings.size(); i++) {
            System.out.println((i + 1) + ". " + offerings.get(i).getLesson().getLessonName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an offering to cancel (Enter the index):");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (index < 1 || index > offerings.size()) {
            System.out.println("Invalid selection. Operation canceled.");
            return;
        }

        offerings.remove(index - 1);
        System.out.println("Offering removed successfully.");
    }

    // View all user accounts
    public void viewAccounts() {
        System.out.println("All Registered Accounts:");
        List<User> users = Users.getAllUsers();
        for (User user : users) {
            System.out.println(user.getRole() + ": " + user.getName() + " (" + user.getPhoneNumber() + ")");
        }
    }

    // Delete a user account
    public void deleteAccount(String name, String phoneNumber) {
        User user = Users.getUserByNameAndPhone(name, phoneNumber);
        if (user == null) {
            System.out.println("No user found with the given details.");
            return;
        }
        Users.getAllUsers().remove(user);
        System.out.println("Account deleted successfully: " + name);
    }

    // View all offerings
    public void viewAllOfferings() {
        System.out.println("All Offerings:");
        Offerings.displayOfferings();
    }
}


