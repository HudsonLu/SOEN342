package User;

import Booking.Booking;
import Booking.Offering;
import Booking.OfferingStatus;
import Catalog.Offerings;
import DAO.BookingDAO;
import Utils.HibernateUtil;
import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "Client")
public class Client extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Booking> bookings = new ArrayList<>();

    public Client(String name, String phoneNumber) {
        super(name, phoneNumber, "Client");
    }

    public Client() {
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void viewPersonalBookings() {
        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookings = bookingDAO.getClientBookings(this.getId());

        if (bookings == null || bookings.isEmpty()) {
            System.out.println("No personal bookings available.");
            return;
        }

        System.out.println("Your Personal Bookings:");
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            System.out.println("Booking " + (i + 1) + ":");
            System.out.println("Lesson: " + booking.getOffering().getLesson().getLessonName());
            System.out.println("Instructor: " + booking.getOffering().getInstructor().getName());
            System.out.println("Date: " + booking.getBookingDateTime());
            System.out.println("--------------------------------");
        }
    }


    // Add a booking
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    // Cancel a booking
    public void cancelBooking() {
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings to cancel.");
            return;
        }

        // Display bookings
        System.out.println("Your Bookings:");
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            System.out.println("Booking " + (i + 1) + ":");
            booking.getOffering().getLesson().displayLessonDetails();
            System.out.println("Instructor: " + booking.getOffering().getInstructor().getName());
            System.out.println("--------------------------------");
        }

        // Prompt user to select booking
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the index of the booking you want to cancel:");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (index < 1 || index > bookings.size()) {
            System.out.println("Invalid selection. Operation canceled.");
            return;
        }

        // Remove booking
        Booking bookingToCancel = bookings.get(index - 1);
        bookings.remove(bookingToCancel);

        // Update the offering status
        bookingToCancel.getOffering().setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);

        System.out.println("Booking canceled successfully. The offering is now available to the public.");
    }

    // Make a booking
    public Booking makeBooking(Offering offering) {
        if (offering.isAvailable()) {
            offering.setAvailable(false); // Mark offering as booked
            Booking booking = new Booking(this, offering);
            bookings.add(booking); // Add booking to the client's list
            System.out.println("Booking successful for offering: " + offering.getLesson().getLessonName());
            return booking;
        } else {
            System.out.println("Offering is not available.");
            return null;
        }
    }

    @Override
    public void performRoleSpecificActions() {
        System.out.println("Client Dashboard: View and manage your bookings.");
    }

    // View and book public offerings
    public void viewAndBookOfferings() {
        System.out.println("Offerings Available to the Public:");
        Offerings.displayPublicOfferings();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an offering to book (Enter the index):");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<Offering> publicOfferings = Offerings.getOfferingsByStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        if (index < 1 || index > publicOfferings.size()) {
            System.out.println("Invalid selection. Booking canceled.");
            return;
        }

        Offering selectedOffering = publicOfferings.get(index - 1);
        makeBooking(selectedOffering);
    }
}
