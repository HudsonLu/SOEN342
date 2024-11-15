package DAO;

import User.Administrator;
import User.Client;
import User.Instructor;
import User.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class ReloadDB {

    private UserDAO userDAO;

    @Before
    public void setup() {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.SEVERE);
    }

    @Test
    public void CreateUsers() {
        userDAO = new UserDAO();
        userDAO.truncateTables(); // Clean database before tests

        Administrator admin = new Administrator("Admin Alice", "111-222-3333");
        Instructor instructor = new Instructor("John Doe", "123-456-7890", "Yoga", List.of("Montreal", "Toronto"));
        Client client = new Client("Bob", "987-654-3210");

        userDAO.saveUser(client);
        userDAO.saveUser(admin);
        userDAO.saveUser(instructor);
    }

    @Test
    public void getAllUsers() {
        userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                System.out.println("User ID: " + user.getId());
                System.out.println("Name: " + user.getName());
                System.out.println("Phone Number: " + user.getPhoneNumber());
                System.out.println("Role: " + user.getRole());
                System.out.println("-----------------------------------");
            }
        } else {
            System.out.println("No users found.");
        }
    }

    @Test
    public void getAllUsersInfo() {
        userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                System.out.println("User ID: " + user.getId());
                System.out.println("Name: " + user.getName());
                System.out.println("Phone Number: " + user.getPhoneNumber());
                System.out.println("Role: " + user.getRole());

                // Handle subclasses
                if (user instanceof Instructor) {
                    Instructor instructor = (Instructor) user;
                    System.out.println("Specialization: " + instructor.getSpecialization());
                    System.out.println("Cities: " + instructor.getCities());
                } else if (user instanceof Client) {
                    System.out.println("Additional Info: This user is a Client.");
                } else if (user instanceof Administrator) {
                    System.out.println("Additional Info: This user is an Administrator.");
                }

                System.out.println("-----------------------------------");
            }
        } else {
            System.out.println("No users found.");
        }
    }

}

