package Booking;
import User.Client;
import Offering.Offering;

public class Booking {
    private Client client;
    private Offering offer;

    public Booking(Client client, Offering offer) {
        this.client = client;
        this.offer = offer;
    }

    public void makeBooking() {
        System.out.println("Booking made for " + client.getName());
    }

    public void cancelBooking() {
        System.out.println("Booking cancelled for " + client.getName());
    }
}
