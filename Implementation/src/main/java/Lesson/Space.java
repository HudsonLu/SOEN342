package Lesson;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Spaces")
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isAvailable;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String room;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Space_Available_Days", joinColumns = @JoinColumn(name = "space_id"))
    @Column(name = "day_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> availableDays;

    // Constructors
    public Space() {}

    public Space(boolean isAvailable, LocalTime startTime, LocalTime endTime, String city, String room, List<DayOfWeek> availableDays) {
        this.isAvailable = isAvailable;
        this.startTime = startTime;
        this.endTime = endTime;
        this.city = city;
        this.room = room;
        this.availableDays = availableDays;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

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

    public String getDetails() {
        return "Room: " + room + ", City: " + city +
                ", Available: " + (isAvailable ? "Yes" : "No") +
                ", Start Time: " + startTime +
                ", End Time: " + endTime +
                ", Available Days: " + availableDays;
    }
}
