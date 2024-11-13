// Spaces.java
package Catalog;
import Lesson.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class Spaces {
    private List<Space> spaces;

    public Spaces() {
        this.spaces = Arrays.asList(
                new Space(true, LocalTime.of(12, 0), LocalTime.of(15, 0), "Montreal", "EV-Building Gym Room 7", Arrays.asList(DayOfWeek.SUNDAY)),
                new Space(true, LocalTime.of(10, 0), LocalTime.of(14, 0), "Toronto", "Room A1", Arrays.asList(DayOfWeek.SATURDAY)),
                new Space(true, LocalTime.of(9, 0), LocalTime.of(12, 0), "Vancouver", "Conference Hall 3", Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY))
        );
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    public void displaySpaces() {
        for (int i = 0; i < spaces.size(); i++) {
            System.out.println("Space " + (i + 1) + ": " + spaces.get(i).getDetails());
        }
    }
}
