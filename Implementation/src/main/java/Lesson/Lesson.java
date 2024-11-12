// Lesson.java
package Lesson;

import java.util.Date;

public class Lesson {
    private Date startTime;
    private Date endTime;
    private boolean isAvailable;
    private String lessonName;
    private boolean isPrivate;
    private Space space; // Assuming Space is defined elsewhere in your project

    // Constructor
    public Lesson(Date startTime, Date endTime, boolean isAvailable, String lessonName, boolean isPrivate, Space space) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
        this.lessonName = lessonName;
        this.isPrivate = isPrivate;
        this.space = space;
    }

    // Getters and Setters
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    // Method to display lesson details
    public void displayLessonDetails() {
        System.out.println("Lesson Name: " + lessonName);
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("Availability: " + (isAvailable ? "Available" : "Not Available"));
        System.out.println("Type: " + (isPrivate ? "Private" : "Group"));
        System.out.println("Location: " + (space != null ? space.getDetails() : "Not Assigned"));
    }
}
