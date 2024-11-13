
package org.Tests;
import Authentication.*;
import User.*;
import Catalog.*;
import Booking.*;
import java.util.List;

public class CancelOffering {
    public static void main(String[] args) {
        // Initialize spaces and lessons
        Spaces spaces = new Spaces();
        Lessons lessons = new Lessons(spaces);

        // Add offerings for demonstration
        Instructor instructor1 = new Instructor("John Doe", "123-456-7890", "Yoga", List.of("Montreal"));
        Instructor instructor2 = new Instructor("Jane Smith", "789-456-1230", "Karate", List.of("Toronto"));

        Offerings.addOffering(new Offering(false, lessons.getLessons().get(0), instructor1)); // Judo offering
        Offerings.addOffering(new Offering(false, lessons.getLessons().get(1), instructor2)); // Yoga offering

        // Set offerings as available to public
        Offerings.getAllOfferings().get(0).setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        Offerings.getAllOfferings().get(1).setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);

        // Retrieve admin from repository
        User user = Users.getUserByNameAndPhone("Admin Alice", "111-222-3333");
        if (user instanceof Administrator) {
            Administrator admin = (Administrator) user;

            // Display admin actions
            admin.performRoleSpecificActions();

            // Cancel an offering
            admin.cancelOffering();

            // Display updated offerings
            System.out.println("\nUpdated Offerings:");
            Offerings.displayCancellableOfferings();
        } else {
            System.out.println("No administrator found with the given credentials.");
        }
    }
}
