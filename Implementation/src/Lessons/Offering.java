package Lessons;

import java.time.LocalTime;
import Users.*;

enum Days {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

enum OfferingStatus {
 UNAVAILABLE,              // Not available for instructors yet
 AVAILABLE_TO_INSTRUCTORS, // Available for instructors to select
 AVAILABLE_TO_PUBLIC,      // Available for the public to book
 FULLY_BOOKED              // Maxed out for public bookings
}

public class Offering {
    private int offerId;
    private boolean isPrivateLesson;
    private String lesson;
    private LocalTime startTime;
    private LocalTime endTime;
    private Days day;
    private Instructor instructor;
    private OfferingStatus status;
    private int maxCapacity;       
    private int currentBookings;  

    private static final int PRIVATE_LESSON_CAPACITY = 1;
    private static final int GROUP_LESSON_CAPACITY = 5;
  
    public Offering(int offerId, boolean isPrivateLesson, String lesson, LocalTime startTime, LocalTime endTime, Days day) {
        this.offerId = offerId;
        this.isPrivateLesson = isPrivateLesson;
        this.lesson = lesson;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.status = OfferingStatus.UNAVAILABLE;
        this.maxCapacity = isPrivateLesson ? PRIVATE_LESSON_CAPACITY : GROUP_LESSON_CAPACITY;
        this.currentBookings = 0;
    }
    public void makeAvailableToInstructors() {
        this.status = OfferingStatus.AVAILABLE_TO_INSTRUCTORS;
    }

    public void selectByInstructor(Instructor instructor) {
        this.instructor = instructor;
        this.status = OfferingStatus.AVAILABLE_TO_PUBLIC;
    }
    public void bookOffering() {
        if (status == OfferingStatus.AVAILABLE_TO_PUBLIC) {
            if (currentBookings < maxCapacity) {
                currentBookings++;
                if (currentBookings >= maxCapacity) {
                    status = OfferingStatus.FULLY_BOOKED;
                }
            } else {
                throw new IllegalStateException("Offering is fully booked.");
            }
        } else {
            throw new IllegalStateException("Offering is not available for public booking.");
        }
    }
    public boolean isAvailableForPublicView() {
        return status == OfferingStatus.AVAILABLE_TO_PUBLIC || status == OfferingStatus.FULLY_BOOKED;
    }

    public int getOfferId() {
        return offerId;
    }

    public OfferingStatus getStatus() {
        return status;
    }

    public boolean isPrivateLesson() {
        return isPrivateLesson;
    }

    
}