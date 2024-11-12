// Offering.java
package Booking;
import User.Instructor;
import Lesson.Lesson;

public class Offering {
    private boolean isAvailable;
    private Lesson lesson;
    private Instructor instructor;

    public Offering(boolean isAvailable, Lesson lesson, Instructor instructor) {
        this.isAvailable = isAvailable;
        this.lesson = lesson;
        this.instructor = instructor;
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
}
