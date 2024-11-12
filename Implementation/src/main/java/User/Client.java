// Client.java
package User;
import Booking.Booking;
import Booking.Offering;

public class Client extends User {

    public Client(String name, String phoneNumber) {
        super(name, phoneNumber, "Client");
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
}
