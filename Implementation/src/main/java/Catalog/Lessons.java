// Lessons.java
package Catalog;

import Lesson.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Lessons {
    private List<Lesson> lessons;

    public Lessons(Spaces spaces) {
        this.lessons = new ArrayList<>();
        initializeLessons(spaces);
    }

    private void initializeLessons(Spaces spaces) {
        this.lessons.add(new Lesson(LocalTime.of(12, 0), LocalTime.of(15, 0), true, "Judo", false, spaces.getSpaces().get(0), DayOfWeek.SUNDAY, "Sep 1 - Nov 30, 2024"));
        this.lessons.add(new Lesson(LocalTime.of(10, 0), LocalTime.of(12, 0), true, "Yoga", true, spaces.getSpaces().get(1), DayOfWeek.SATURDAY, "Sep 1 - Nov 30, 2024"));
    }

    public List<Lesson> getLessons() {
        return lessons;
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
}
