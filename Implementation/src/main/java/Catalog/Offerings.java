package Catalog;

import Booking.Offering;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Offerings {

    // List of offerings
    private List<Offering> offers;

    // Private constructor for singleton pattern
    private Offerings() {
        offers = new ArrayList<>();
    }


    // Method to retrieve all offerings
    public List<Offering> getOffers() {
        return offers;
    }

    // Method to get an offering by ID
    public Offering getOffer(int offerId) {
        for (Offering offer : offers) {
            if (offer.getOfferId() == offerId) {
                return offer;
            }
        }
        return null; // Return null if not found
    }

    // Method to create a new offering and add it to the list
    public void createOffer(boolean isPrivateLesson, String lesson, String startTime, String endTime) {

        int newOfferId = offers.size() + 1; // Generate a new ID for the offer
        Offering newOffering = new Offering(
                newOfferId,
                isPrivateLesson,
                lesson,
                LocalTime.parse(startTime),
                LocalTime.parse(endTime),

        );
        offers.add(newOffering);
    }
}
