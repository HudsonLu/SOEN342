// Bookings.java
package Catalog;

import Booking.Booking;
import DAO.BookingDAO;

import java.util.List;

public class Bookings {
    private static BookingDAO bookingDAO = new BookingDAO();

    // Add a booking
    public static void addBooking(Booking booking) {
        bookingDAO.saveBooking(booking);
    }

    // Retrieve all bookings
    public static List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }

    // Cancel a booking
    public static void cancelBooking(Long bookingId) {
        bookingDAO.deleteBooking(bookingId);
        System.out.println("Booking canceled successfully.");
    }
}
