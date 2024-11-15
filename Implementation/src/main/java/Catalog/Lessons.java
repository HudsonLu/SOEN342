// Lessons.java
package Catalog;

import DAO.LessonDAO;
import Lesson.*;
import Booking.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Lessons {
    private List<Lesson> lessons;

    public Lessons() {
        // Fetch lessons from database
        LessonDAO lessonDAO = new LessonDAO();
        this.lessons = lessonDAO.getAllLessons();
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    // Display all lessons (both associated and non-associated)
    public void displayAllLessons() {
        if (lessons.isEmpty()) {
            System.out.println("No lessons available.");
            return;
        }

        for (Lesson lesson : lessons) {
            lesson.displayLessonDetails();
            System.out.println("--------------------------------");
        }
    }

    public void displayLessons() {
        if (lessons.isEmpty()) {
            System.out.println("No lessons available.");
            return;
        }

        for (int i = 0; i < lessons.size(); i++) {
            System.out.println("Lesson " + (i + 1) + ":");
            lessons.get(i).displayLessonDetails();
            System.out.println("--------------------------------");
        }
    }

    // Display only cancellable lessons (not associated with offerings)
    public void displayCancellableLessons() {
        List<Lesson> cancellableLessons = getCancellableLessons();
        if (cancellableLessons.isEmpty()) {
            System.out.println("No lessons available for cancellation.");
            return;
        }

        for (int i = 0; i < cancellableLessons.size(); i++) {
            System.out.println("Lesson " + (i + 1) + ":");
            cancellableLessons.get(i).displayLessonDetails();
            System.out.println("--------------------------------");
        }
    }

    // Get lessons that are not associated with any offerings
    public List<Lesson> getCancellableLessons() {
        List<Lesson> cancellableLessons = new ArrayList<>();
        for (Lesson lesson : lessons) {
            // Check if the lesson is associated with any offering
            boolean isAssociatedWithOffering = Offerings.getAllOfferings().stream()
                    .anyMatch(offering -> offering.getLesson().equals(lesson));

            // If not associated, add to the cancellable list
            if (!isAssociatedWithOffering) {
                cancellableLessons.add(lesson);
            }
        }
        return cancellableLessons;
    }

    // Remove a lesson from the cancellable list
    public void removeLesson(int index) {
        List<Lesson> cancellableLessons = getCancellableLessons();
        if (index < 1 || index > cancellableLessons.size()) {
            System.out.println("Invalid selection. Operation canceled.");
            return;
        }

        // Find and remove the selected lesson
        Lesson lessonToRemove = cancellableLessons.get(index - 1);
        lessons.remove(lessonToRemove);

        System.out.println("Lesson removed successfully.");
    }
}
