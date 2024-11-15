// Offering.java
package Booking;

import User.Instructor;
import Lesson.Lesson;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


public class Offering {
    private boolean isAvailable;
    private Lesson lesson;
    private Instructor instructor;
    private OfferingStatus offeringStatus;
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Offering(boolean isAvailable, Lesson lesson, Instructor instructor) {
        if (lesson == null || instructor == null) {
            throw new IllegalArgumentException("Lesson and Instructor must not be null");
        }
        this.isAvailable = isAvailable;
        this.lesson = lesson;
        this.instructor = instructor;
        this.offeringStatus = OfferingStatus.UNAVAILABLE; // Set initial status to UNAVAILABLE
    }

    public Offering() {

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
