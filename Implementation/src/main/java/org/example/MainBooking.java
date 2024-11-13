package org.example;
import Authentication.*;
import User.*;
import Lesson.*;
import Catalog.*;
import Booking.*;
import java.util.List;

public class MainBooking {
    public static void main(String[] args) {
        // Initialize Spaces and Lessons
        Spaces spaces = new Spaces();
        Lessons lessons = new Lessons(spaces);

        // Add offerings for demonstration
        Instructor instructor1 = new Instructor("John Doe", "123-456-7890", "Yoga", List.of("Montreal"));
        Instructor instructor2 = new Instructor("Jane Smith", "789-456-1230", "Karate", List.of("Toronto"));

        Offerings.addOffering(new Offering(false, lessons.getLessons().get(0), instructor1)); // Judo
        Offerings.addOffering(new Offering(false, lessons.getLessons().get(1), instructor2)); // Yoga

        // Update offering statuses to AVAILABLE_TO_PUBLIC for testing
        Offerings.getAllOfferings().get(0).setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        Offerings.getAllOfferings().get(1).setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);

        // Retrieve client from repository
        User user = UserRepository.getUserByNameAndPhone("Bob", "987-654-3210");
        if (user instanceof Client) {
            Client client = (Client) user;

            // Display client actions
            client.performRoleSpecificActions();

            // View and book offerings
            client.viewAndBookOfferings();

            // Display all offerings after booking
            System.out.println("\nCurrent Offerings:");
            Offerings.displayPublicOfferings();
        } else {
            System.out.println("No client found with the given credentials.");
        }
    }
}
