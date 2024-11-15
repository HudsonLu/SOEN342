// Offering.java
package Booking;

import User.Instructor;
import Lesson.Lesson;
import jakarta.persistence.*;

@Entity
@Table(name = "Offerings")
public class Offering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferingStatus offeringStatus;

    public Offering() {
        // Default constructor for JPA
    }

    public Offering(boolean isAvailable, Lesson lesson, Instructor instructor) {
        if (lesson == null || instructor == null) {
            throw new IllegalArgumentException("Lesson and Instructor must not be null");
        }
        this.isAvailable = isAvailable;
        this.lesson = lesson;
        this.instructor = instructor;
        this.offeringStatus = OfferingStatus.UNAVAILABLE; // Default status
    }

    public Long getId() {
        return id;
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

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public OfferingStatus getOfferingStatus() {
        return offeringStatus;
    }

    public void setOfferingStatus(OfferingStatus offeringStatus) {
        this.offeringStatus = offeringStatus;
    }
}
