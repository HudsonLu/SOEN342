package org.example;


import Authentication.LoginService;
import User.User;
import Authentication.*;
import User.*;
import Lesson.*;
import Catalog.*;
import Booking.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class MainCancelBooking {
    public static void main(String[] args) {
        // Initialize spaces and lessons
        Spaces spaces = new Spaces();
        Lessons lessons = new Lessons(spaces);

        // Add offerings
        Instructor instructor1 = new Instructor("John Doe", "123-456-7890", "Yoga", List.of("Montreal"));
        Offering offering1 = new Offering(false, lessons.getLessons().get(0), instructor1); // Judo
        Offering offering2 = new Offering(false, lessons.getLessons().get(1), instructor1); // Yoga
        Offerings.addOffering(offering1);
        Offerings.addOffering(offering2);

        // Set offerings as available to public
        offering1.setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        offering2.setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);

        // Retrieve client from repository
        User user = UserRepository.getUserByNameAndPhone("Bob", "987-654-3210");
        if (user instanceof Client) {
            Client client = (Client) user;

            // Client books an offering
            client.addBooking(offering1);
            offering1.setOfferingStatus(OfferingStatus.FULLY_BOOKED);

            // Display client's bookings
            System.out.println("Client Dashboard:");
            client.cancelBooking();

            // Display updated offerings
            System.out.println("\nUpdated Offerings:");
            Offerings.displayCancellableOfferings();
        } else {
            System.out.println("No client found with the given credentials.");
        }
    }
}
