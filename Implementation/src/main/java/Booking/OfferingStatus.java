package Booking;

public enum OfferingStatus {
    UNAVAILABLE,              // Not available for instructors yet
    AVAILABLE_TO_INSTRUCTORS, // Available for instructors to select
    AVAILABLE_TO_PUBLIC,      // Available for the public to book
    FULLY_BOOKED              // Maxed out for public bookings
}
