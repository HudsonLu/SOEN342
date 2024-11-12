// Space.java
package Lesson;

import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.List;

public class Space {
    private boolean isAvailable; // Indicates if the space is available
    private LocalTime startTime; // Time of availability start
    private LocalTime endTime;   // Time of availability end
    private String city;         // City where the space is located
    private String room;         // Room name or identifier
    private List<DayOfWeek> availableDays; // Days of the week the space is available

    // Constructor
    public Space(boolean isAvailable, LocalTime startTime, LocalTime endTime, String city, String room, List<DayOfWeek> availableDays) {
        this.isAvailable = isAvailable;
        this.startTime = startTime;
        this.endTime = endTime;
        this.city = city;
        this.room = room;
        this.availableDays = availableDays;
    }

    // Getters and Setters
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<DayOfWeek> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<DayOfWeek> availableDays) {
        this.availableDays = availableDays;
    }

    // Method to display space details
    public String getDetails() {
        return "Room: " + room + ", City: " + city +
                ", Available: " + (isAvailable ? "Yes" : "No") +
                ", Start Time: " + startTime +
                ", End Time: " + endTime +
                ", Available Days: " + availableDays;
    }
}
