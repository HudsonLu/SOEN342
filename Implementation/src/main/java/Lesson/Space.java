// Space.java
package Lesson;

public class Space {
    private String roomName;
    private String location; // City or detailed address
    private int capacity; // Maximum number of people the space can hold

    // Constructor
    public Space(String roomName, String location, int capacity) {
        this.roomName = roomName;
        this.location = location;
        this.capacity = capacity;
    }

    // Getters and Setters
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Method to display space details
    public String getDetails() {
        return "Room: " + roomName + ", Location: " + location + ", Capacity: " + capacity;
    }
}
