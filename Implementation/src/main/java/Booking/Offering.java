// Offering.java
package Booking;

import User.Instructor;
import Lesson.Lesson;

public class Offering {
    private boolean isAvailable;
    private Lesson lesson;
    private Instructor instructor;
    private OfferingStatus offeringStatus;

    public Offering(boolean isAvailable, Lesson lesson, Instructor instructor) {
        if (lesson == null || instructor == null) {
            throw new IllegalArgumentException("Lesson and Instructor must not be null");
        }
        this.isAvailable = isAvailable;
        this.lesson = lesson;
        this.instructor = instructor;
        this.offeringStatus = OfferingStatus.AVAILABLE_TO_INSTRUCTORS;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public OfferingStatus getOfferingStatus() {
        return offeringStatus;
    }

    public void setOfferingStatus(OfferingStatus status) {
        this.offeringStatus = status;
    }
}
