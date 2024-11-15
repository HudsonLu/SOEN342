// Client.java
package User;
import Booking.Booking;
import Booking.*;
import Catalog.Offerings;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "Client")
public class Client extends User {
    @Transient
    private List<Offering> bookings; // List of bookings made by the client
    @Id
    private Long id;

    public Client(String name, String phoneNumber) {
        super(name, phoneNumber, "Client");
        this.bookings = new ArrayList<>();
    }

    public Client() {

    }

    public List<Offering> getBookings() {
        return bookings;
    }

    public void viewPersonalBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No personal bookings available.");
            return;
        }

        System.out.println("Your Personal Bookings:");
        for (int i = 0; i < bookings.size(); i++) {
            Offering offering = bookings.get(i);
            System.out.println("Booking " + (i + 1) + ":");
            offering.getLesson().displayLessonDetails();
            System.out.println("Instructor: " + offering.getInstructor().getName());
            System.out.println("--------------------------------");
        }
    }

    public void addBooking(Offering offering) {
        bookings.add(offering);
    }

    public void cancelBooking() {
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings to cancel.");
            return;
        }

        // Display client's bookings
        System.out.println("Your Bookings:");
        for (int i = 0; i < bookings.size(); i++) {
            Offering offering = bookings.get(i);
            System.out.println("Booking " + (i + 1) + ":");
            offering.getLesson().displayLessonDetails();
            System.out.println("Instructor: " + offering.getInstructor().getName());
            System.out.println("--------------------------------");
        }

        // Prompt client to select a booking to cancel
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the index of the booking you want to cancel:");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (index < 1 || index > bookings.size()) {
            System.out.println("Invalid selection. Operation canceled.");
            return;
        }

        // Remove the booking
        Offering offeringToCancel = bookings.get(index - 1);
        bookings.remove(offeringToCancel);

        // Update the offering's status to AVAILABLE_TO_PUBLIC
        offeringToCancel.setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);

        System.out.println("Booking canceled successfully. The offering is now available to the public.");
    }

    // Method to make a booking
    public Booking makeBooking(Offering offering) {
        if (offering.isAvailable()) {
            offering.setAvailable(false); // Mark the offering as booked
            Booking booking = new Booking(this, offering);
            System.out.println("Booking successful for offering: " + offering.getLesson().getLessonName());
            return booking;
        } else {
            System.out.println("Offering is not available.");
            return null;
        }
    }

    @Override
    public void performRoleSpecificActions() {
        System.out.println("Administrator Dashboard: Manage all users, offerings, and system settings.");
    }

    // Method to view and book public offerings
    public void viewAndBookOfferings() {
        System.out.println("Offerings Available to the Public:");
        Offerings.displayPublicOfferings();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an offering to book (Enter the index):");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Offerings.bookOffering(index, this.getName());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
