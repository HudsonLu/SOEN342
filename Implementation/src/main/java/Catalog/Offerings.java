package Catalog;

import java.util.ArrayList;
import java.util.List;
import Booking.*;

public class Offerings {
    private static final List<Offering> offerings = new ArrayList<>();

    // Add an offering
    public static void addOffering(Offering offering) {
        offerings.add(offering);
    }

    // Retrieve all offerings
    public static List<Offering> getAllOfferings() {
        return offerings;
    }

    // Display all offerings with full details
    public static void displayOfferings() {
        if (offerings.isEmpty()) {
            System.out.println("No offerings available.");
            return;
        }
        for (Offering offering : offerings) {
            System.out.println("Offering Details:");
            offering.getLesson().displayLessonDetails(); // Include lesson details
            System.out.println("Instructor: " + offering.getInstructor().getName());
            System.out.println("Status: " + offering.getOfferingStatus());
            System.out.println("--------------------------------");
        }
    }
}
