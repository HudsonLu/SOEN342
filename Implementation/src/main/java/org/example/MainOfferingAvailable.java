package org.example;
import Authentication.*;
import User.*;
import Lesson.*;
import Catalog.*;
import Booking.*;
import java.util.List;

public class MainOfferingAvailable {

    public static void main(String[] args) {
        // Initialize Spaces and Lessons
        Spaces spaces = new Spaces();
        Lessons lessons = new Lessons(spaces);

        // Add offerings for demonstration
        Instructor instructor1 = new Instructor("John Doe", "123-456-7890", "Yoga", List.of("Montreal"));
        Instructor instructor2 = new Instructor("Jane Smith", "789-456-1230", "Karate", List.of("Toronto"));

        Offerings.addOffering(new Offering(false, lessons.getLessons().get(0), instructor1)); // Judo
        Offerings.addOffering(new Offering(false, lessons.getLessons().get(1), instructor2)); // Yoga

        // Retrieve admin from repository
        User user = UserRepository.getUserByNameAndPhone("Admin Alice", "111-222-3333");
        if (user instanceof Administrator) {
            Administrator admin = (Administrator) user;

            // Display admin actions
            admin.performRoleSpecificActions();

            // Update offerings to AVAILABLE_TO_PUBLIC
            admin.updateOfferingsToAvailable();

            // Display all offerings after update
            System.out.println("\nCurrent Offerings:");
            Offerings.displayOfferings();
        } else {
            System.out.println("No administrator found with the given credentials.");
        }
    }
}
