// Users.java
package Authentication;

import DAO.UserDAO;
import User.*;
import java.util.ArrayList;
import java.util.List;

public class Users {
    private static final List<User> users = new ArrayList<>();
    private static final UserDAO userDAO = new UserDAO();

    // Retrieve user by name and phone number
    public static User getUserByNameAndPhone(String name, String phoneNumber) {
        // First, check the in-memory list
        for (User user : users) {
            if (user.validateCredentials(name, phoneNumber)) {
                return user;
            }
        }

        // If not found, fetch from the database
        User user = userDAO.findByPhoneNumber(phoneNumber);
        if (user != null && user.getName().equals(name)) {
            users.add(user); // Cache the user in memory
        }
        return user;
    }

    // Add a new user to the repository
    public static void addUser(User user) {
        if (getUserByNameAndPhone(user.getName(), user.getPhoneNumber()) == null) {
            users.add(user); // Add to in-memory list
            userDAO.saveUser(user); // Persist to the database
            System.out.println("User registered successfully: " + user.getName());
        } else {
            System.out.println("User already exists with the provided name and phone number.");
        }
    }

    // Get all users (for debugging or admin purposes)
    // Get all users (merge in-memory and database)
    public static List<User> getAllUsers() {
        // Fetch all users from the database and merge with in-memory list
        List<User> dbUsers = userDAO.getAllUsers();
        for (User dbUser : dbUsers) {
            if (users.stream().noneMatch(user -> user.getId().equals(dbUser.getId()))) {
                users.add(dbUser);
            }
        }
        return users;
    }

    // Remove a user
    public static void removeUser(User user) {
        users.remove(user); // Remove from in-memory list
        userDAO.deleteUser(user.getId()); // Delete from the database
    }

}