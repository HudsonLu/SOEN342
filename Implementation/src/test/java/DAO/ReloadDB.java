package DAO;
import Booking.Offering;
import Booking.OfferingStatus;
import Lesson.Lesson;
import Lesson.Space;
import User.Administrator;
import User.Client;
import User.Instructor;
import User.User;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
    private SpacesDAO spacesDAO;
    private LessonDAO lessonDAO;

    @Before
    public void setup() {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.SEVERE);
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
    public void CreateSpaces() {
        SpacesDAO SpacesDAO = new SpacesDAO();

        Space x1 = new Space(true, LocalTime.of(12, 0), LocalTime.of(15, 0), "Montreal", "EV-Building Gym Room 7", Arrays.asList(DayOfWeek.SUNDAY));
        Space x2 = new Space(true, LocalTime.of(10, 0), LocalTime.of(14, 0), "Toronto", "Room A1", Arrays.asList(DayOfWeek.SATURDAY));
        Space x3 = new Space(true, LocalTime.of(9, 0), LocalTime.of(12, 0), "Vancouver", "Conference Hall 3", Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY));

        SpacesDAO.saveSpace(x1);
        SpacesDAO.saveSpace(x2);
        SpacesDAO.saveSpace(x3);
    }

    @Test
    public void CreateLessons() {
        LessonDAO lessonDAO = new LessonDAO();
        SpacesDAO spacesDAO = new SpacesDAO();
        List<Space> spaces = spacesDAO.getAllSpaces();

        Lesson x1 = new Lesson(LocalTime.of(12, 0), LocalTime.of(15, 0), true, "Judo", false, spaces.get(0), DayOfWeek.SUNDAY, "Sep 1 - Nov 30, 2024");
        Lesson x2 = new Lesson(LocalTime.of(10, 0), LocalTime.of(12, 0), true, "Yoga", true, spaces.get(1), DayOfWeek.SATURDAY, "Sep 1 - Nov 30, 2024");
        Lesson x3 = new Lesson(LocalTime.of(12, 0), LocalTime.of(13, 0), true, "Aikido", true, spaces.get(1), DayOfWeek.SATURDAY, "Sep 1 - Nov 30, 2024");

        lessonDAO.saveLesson(x1);
        lessonDAO.saveLesson(x2);
        lessonDAO.saveLesson(x3);

        List<Lesson> lessons = lessonDAO.getAllLessons();

    }

    @Test
    public void CreateOfferings() {
        LessonDAO lessonDAO = new LessonDAO();
        OfferingDAO offeringDAO = new OfferingDAO();
        List<Lesson> lessons = lessonDAO.getAllLessons();

        // Ensure lessons are available
        assertNotNull("Lessons should not be null", lessons);
        assertFalse("Lessons should not be empty", lessons.isEmpty());

        // Save or fetch instructor
        Instructor instructor1 = saveOrGetInstructor(new Instructor("John Doe", "123-456-7890", "Yoga", Arrays.asList("Montreal", "Toronto")));
        Instructor instructor2 = saveOrGetInstructor(new Instructor("Jane Smith", "987-654-3210", "Aikido", Arrays.asList("Vancouver")));

        // Create offerings
        Offering offering1 = new Offering(true, lessons.get(0), instructor1);
        Offering offering2 = new Offering(true, lessons.get(1), instructor1);
        Offering offering3 = new Offering(true, lessons.get(2), instructor2);

        // Save offerings
        offeringDAO.saveOffering(offering1);
        offeringDAO.saveOffering(offering2);
        offeringDAO.saveOffering(offering3);

        // Fetch and verify all offerings
        List<Offering> offerings = offeringDAO.getAllOfferings();
        assertNotNull("Offerings should not be null", offerings);
        assertEquals("There should be 3 offerings", 3, offerings.size());

        // Verify offering details
        for (Offering offering : offerings) {
            assertNotNull("Offering lesson should not be null", offering.getLesson());
            assertNotNull("Offering instructor should not be null", offering.getInstructor());
            assertEquals("Offering status should be UNAVAILABLE", OfferingStatus.UNAVAILABLE, offering.getOfferingStatus());
        }
    }

    // Helper method to save or fetch an instructor
    private Instructor saveOrGetInstructor(Instructor instructor) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Check if instructor exists
            Instructor existingInstructor = session.createQuery("FROM Instructor WHERE phoneNumber = :phoneNumber", Instructor.class)
                    .setParameter("phoneNumber", instructor.getPhoneNumber())
                    .uniqueResult();

            if (existingInstructor != null) {
                return existingInstructor;
            }

            // Save new instructor
            Transaction transaction = session.beginTransaction();
            session.persist(instructor);
            transaction.commit();
            return instructor;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to save or fetch instructor: " + e.getMessage(), e);
        }
    }


}

