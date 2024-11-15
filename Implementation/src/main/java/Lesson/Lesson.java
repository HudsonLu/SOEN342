// Lesson.java
package Lesson;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.DayOfWeek;
import java.time.LocalTime;


public class Lesson {
    private LocalTime startTime;     // Time when the lesson starts
    private LocalTime endTime;       // Time when the lesson ends
    private boolean isAvailable;     // Indicates if the lesson is available
    private String lessonName;       // Name of the lesson
    private boolean isPrivate;       // True if private, false if group
    private Space space;             // Space where the lesson is held
    private DayOfWeek dayOfWeek;     // Day of the week the lesson occurs
    private String dateRange;        // Overall date range as a string (e.g., "Sep 1 - Nov 30, 2024")


    // Constructor
    public Lesson(LocalTime startTime, LocalTime endTime, boolean isAvailable, String lessonName, boolean isPrivate, Space space, DayOfWeek dayOfWeek, String dateRange) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
        this.lessonName = lessonName;
        this.isPrivate = isPrivate;
        this.space = space;
        this.dayOfWeek = dayOfWeek;
        this.dateRange = dateRange;
    }

    public Lesson() {

    }

    // Getters and Setters
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    // Method to display lesson details
    public void displayLessonDetails() {
        System.out.println("Lesson Name: " + lessonName);
        System.out.println("Day: " + dayOfWeek);
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("Availability: " + (isAvailable ? "Available" : "Not Available"));
        System.out.println("Type: " + (isPrivate ? "Private" : "Group"));
        System.out.println("Date Range: " + dateRange);
        System.out.println("Location: " + (space != null ? space.getDetails() : "Not Assigned"));
    }

}

