// Offerings.java
package Catalog;

import java.util.ArrayList;
import java.util.List;
import Booking.*;
import DAO.OfferingDAO;

public class Offerings {

    private static OfferingDAO offeringDAO = new OfferingDAO();

    // Add an offering
    public static void addOffering(Offering offering) {
        offeringDAO.saveOffering(offering);
    }

    // Retrieve all offerings
    public static List<Offering> getAllOfferings() {
        return offeringDAO.getAllOfferings();
    }

    // Retrieve offerings with a specific status
    public static List<Offering> getOfferingsByStatus(OfferingStatus status) {
        return offeringDAO.getAllOfferings().stream()
                .filter(offering -> offering.getOfferingStatus() == status)
                .toList();
    }

    // Display all offerings
    public static void displayOfferings() {
        List<Offering> offerings = getAllOfferings();
        if (offerings.isEmpty()) {
            System.out.println("No offerings available.");
            return;
        }

        for (Offering offering : offerings) {
            offering.getLesson().displayLessonDetails();
            System.out.println("Instructor: " + offering.getInstructor().getName());
            System.out.println("Status: " + offering.getOfferingStatus());
            System.out.println("--------------------------------");
        }
    }

    // Display offerings by status
    public static void displayOfferingsByStatus(OfferingStatus status) {
        List<Offering> filteredOfferings = getOfferingsByStatus(status);
        if (filteredOfferings.isEmpty()) {
            System.out.println("No offerings with status: " + status);
            return;
        }
        for (int i = 0; i < filteredOfferings.size(); i++) {
            Offering offering = filteredOfferings.get(i);
            System.out.println((i + 1) + ". Offering Details:");
            offering.getLesson().displayLessonDetails();
            System.out.println("Instructor: " + offering.getInstructor().getName());
            System.out.println("Status: " + offering.getOfferingStatus());
            System.out.println("--------------------------------");
        }
    }

    // Display public offerings
    public static void displayPublicOfferings() {
        List<Offering> publicOfferings = getOfferingsByStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        if (publicOfferings.isEmpty()) {
            System.out.println("No offerings available to the public.");
            return;
        }
        for (int i = 0; i < publicOfferings.size(); i++) {
            Offering offering = publicOfferings.get(i);
            System.out.println((i + 1) + ". Offering Details:");
            offering.getLesson().displayLessonDetails();
            System.out.println("Instructor: " + offering.getInstructor().getName());
            System.out.println("Status: " + offering.getOfferingStatus());
            System.out.println("--------------------------------");
        }
    }

    public static void bookOffering(int index, String clientName) {
        List<Offering> publicOfferings = getOfferingsByStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        if (index < 1 || index > publicOfferings.size()) {
            System.out.println("Invalid selection. Booking canceled.");
            return;
        }

        Offering offering = publicOfferings.get(index - 1);
        offering.setOfferingStatus(OfferingStatus.FULLY_BOOKED); // Update status
        offeringDAO.saveOffering(offering); // Persist changes to the database

        System.out.println("Booking successful for client: " + clientName);
        System.out.println("Offering Status updated to FULLY_BOOKED.");
    }


    // Get cancellable offerings (not booked yet)
    public static List<Offering> getCancellableOfferings() {
        return offeringDAO.getAllOfferings().stream()
                .filter(offering -> offering.getOfferingStatus() == OfferingStatus.AVAILABLE_TO_PUBLIC)
                .toList();
    }

    // Display cancellable offerings
    public static void displayCancellableOfferings() {
        List<Offering> cancellableOfferings = getCancellableOfferings();
        if (cancellableOfferings.isEmpty()) {
            System.out.println("No offerings available for cancellation.");
            return;
        }

        for (int i = 0; i < cancellableOfferings.size(); i++) {
            Offering offering = cancellableOfferings.get(i);
            System.out.println("Offering " + (i + 1) + ":");
            offering.getLesson().displayLessonDetails();
            System.out.println("Instructor: " + offering.getInstructor().getName());
            System.out.println("Status: " + offering.getOfferingStatus());
            System.out.println("--------------------------------");
        }
    }

    public static void cancelOffering(int index) {
        List<Offering> cancellableOfferings = getCancellableOfferings();
        if (index < 1 || index > cancellableOfferings.size()) {
            System.out.println("Invalid selection. Operation canceled.");
            return;
        }

        Offering offeringToCancel = cancellableOfferings.get(index - 1);
        offeringToCancel.setOfferingStatus(OfferingStatus.UNAVAILABLE); // Update status
        offeringDAO.saveOffering(offeringToCancel); // Persist changes to the database

        System.out.println("Offering canceled successfully.");
    }


}
