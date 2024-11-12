// Instructor.java
package User;

import Catalog.Offerings;
import Booking.Offering;
import Lesson.Lesson;

import java.util.List;
import java.util.Scanner;

public class Instructor extends User {
    private String specialization;
    private List<String> cities;

    public Instructor(String name, String phoneNumber, String specialization, List<String> cities) {
        super(name, phoneNumber, "Instructor");
        this.specialization = specialization;
        this.cities = cities;
    }

    // Overloaded constructor for simplicity
    public Instructor(String name, String phoneNumber) {
        this(name, phoneNumber, "Unknown", List.of());
    }

    public String getSpecialization() {
        return specialization;
    }

    public List<String> getCities() {
        return cities;
    }

    @Override
    public void performRoleSpecificActions() {
        System.out.println("Instructor Dashboard: Manage your schedule, classes, and offerings.");
    }

    public void createOffering(List<Lesson> lessons) {
        if (lessons.isEmpty()) {
            System.out.println("No lessons available to create an offering.");
            return;
        }

        // Display lessons with full details
        System.out.println("Available Lessons:");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println("Lesson " + (i + 1) + ":");
            lessons.get(i).displayLessonDetails();
            System.out.println("--------------------------------");
        }

        // Prompt instructor to select a lesson
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a lesson to create an offering:");
        int lessonIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (lessonIndex < 1 || lessonIndex > lessons.size()) {
            System.out.println("Invalid selection. Please try again.");
            return;
        }
        Lesson selectedLesson = lessons.get(lessonIndex - 1);
        // Create and add the offering
        Offering offering = new Offering(false, selectedLesson, this);
        Offerings.addOffering(offering);

        // Display confirmation message with full offering details
        System.out.println("Offering created successfully with the following details:");
        System.out.println("Offering Details:");
        selectedLesson.displayLessonDetails(); // Include lesson details
        System.out.println("Instructor: " + this.getName());
        System.out.println("Status: " + offering.getOfferingStatus());
    }

}
