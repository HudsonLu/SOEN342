package DAO;

import Lesson.Space;
import User.Administrator;
import User.Client;
import User.Instructor;
import User.User;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
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

    @Test
    public void CreateSpaces() {
        SpacesDAO SpacesDAO = new SpacesDAO();


        Space x1 = new Space(true, LocalTime.of(12, 0), LocalTime.of(15, 0), "Montreal", "EV-Building Gym Room 7", Arrays.asList(DayOfWeek.SUNDAY));
        Space x2 = new Space(true, LocalTime.of(10, 0), LocalTime.of(14, 0), "Toronto", "Room A1", Arrays.asList(DayOfWeek.SATURDAY));
        Space x3 = new Space(true, LocalTime.of(9, 0), LocalTime.of(12, 0), "Vancouver", "Conference Hall 3", Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY));

        SpacesDAO.saveSpace(x1);
        SpacesDAO.saveSpace(x2);
        SpacesDAO.saveSpace(x3);
    }

}

