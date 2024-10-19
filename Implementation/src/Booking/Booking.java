package Booking;
import Users.Client;
import Lessons.Lesson;

public class Booking {
    private Client client;
    private Lesson lesson;


    public Booking(Client client, Lesson lesson) {
        this.client = client;
        this.lesson = lesson;
    }

    public void makeBooking() { 
       System.out.println("Booking made for " + client.getName());    
    }

    public void cancelBooking() {
        System.out.println("Booking cancelled for " + client.getName());
    }
}
