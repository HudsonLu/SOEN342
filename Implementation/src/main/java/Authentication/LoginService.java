// LoginService.java
package Authentication;

import User.Client;
import User.Instructor;
import User.User;

import java.util.List;

public class LoginService {

    // Authenticate an existing user
    public static User authenticate(String name, String phoneNumber) {
        User user = UserRepository.getUserByNameAndPhone(name, phoneNumber);

        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getName() + ".");
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
        return user;
    }

    // Register a new user
    public static void register(String name, String phoneNumber, String role, String specialization, List<String> availableCities) {
        User newUser;
        switch (role) {
            case "Instructor":
                newUser = new Instructor(name, phoneNumber, specialization, availableCities);
                break;
            case "Client":
                newUser = new Client(name, phoneNumber);
                break;
            case "Administrator":
                System.out.println("Administrators cannot self-register.");
                return;
            default:
                System.out.println("Invalid role specified. Registration failed.");
                return;
        }

        UserRepository.addUser(newUser);
    }
}


