// Users.java
package Authentication;

import User.*;
import java.util.ArrayList;
import java.util.List;

public class Users {
    private static final List<User> users = new ArrayList<>();

    static {
        // Add hardcoded users for testing
        users.add(new Administrator("Admin Alice", "111-222-3333"));
        users.add(new Instructor("John Doe", "123-456-7890", "Yoga", List.of("Montreal", "Toronto")));
        users.add(new Client("Bob", "987-654-3210"));
    }

    // Retrieve user by name and phone number
    public static User getUserByNameAndPhone(String name, String phoneNumber) {
        for (User user : users) {
            if (user.validateCredentials(name, phoneNumber)) {
                return user;
            }
        }
        return null; // Return null if user is not found
    }

    // Add a new user to the repository
    public static void addUser(User user) {
        if (getUserByNameAndPhone(user.getName(), user.getPhoneNumber()) == null) {
            users.add(user);
            System.out.println("User registered successfully: " + user.getName());
        } else {
            System.out.println("User already exists with the provided name and phone number.");
        }
    }

    // Get all users (for debugging or admin purposes)
    public static List<User> getAllUsers() {
        return users;
    }
}