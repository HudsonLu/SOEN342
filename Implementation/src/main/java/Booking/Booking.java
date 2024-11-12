// Booking.java
package Booking;
import User.Client;

public class Booking {
    private Client client;
    private Offering offering;

    public Booking(Client client, Offering offering) {
        this.client = client;
        this.offering = offering;
    }

    public Client getClient() {
        return client;
    }

    public Offering getOffering() {
        return offering;
    }
}
