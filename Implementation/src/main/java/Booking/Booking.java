// Booking.java
package Booking;
import User.Client;

import java.time.LocalDateTime;

public class Booking {
    private Client client;
    private Offering offering;
    private LocalDateTime bookingDateTime;

    public Booking(Client client, Offering offering) {
        this.client = client;
        this.offering = offering;
        this.bookingDateTime = LocalDateTime.now(); // Timestamp when booking is created
    }

    public void cancel() {
        offering.setAvailable(true); // Mark the offering as available again
        System.out.println("Booking canceled for client: " + client.getName());
    }

    public Client getClient() {
        return client;
    }

    public Offering getOffering() {
        return offering;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }
}
