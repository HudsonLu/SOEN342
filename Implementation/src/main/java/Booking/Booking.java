// Booking.java
package Booking;

import User.Client;
import Lesson.Lesson;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "offering_id", nullable = false)
    private Offering offering;

    @Column(name = "booking_date_time", nullable = false)
    private LocalDateTime bookingDateTime;

    public Booking(Client client, Offering offering) {
        this.client = client;
        this.offering = offering;
        this.bookingDateTime = LocalDateTime.now(); // Timestamp when booking is created
    }

    public Booking() {
    }

    public void cancel() {
        offering.setAvailable(true); // Mark the offering as available again
        System.out.println("Booking canceled for client: " + client.getName());
    }

    public Long getId() {
        return id;
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
