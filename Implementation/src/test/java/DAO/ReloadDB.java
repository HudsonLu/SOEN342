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

    @Test
    public void CreateData() {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.SEVERE);

        // Initialize DAOs
        UserDAO userDAO = new UserDAO();
        SpacesDAO spacesDAO = new SpacesDAO();
        LessonDAO lessonDAO = new LessonDAO();
        OfferingDAO offeringDAO = new OfferingDAO();
        BookingDAO bookingDAO = new BookingDAO();

        // Truncate all tables to ensure a clean state
        userDAO.truncateTables();
        spacesDAO.truncateTables();
        lessonDAO.truncateLessonsTable();
        offeringDAO.truncateOfferings();
        //bookingDAO.truncateBookingsTable();

        // Create 10 users (4 Clients, 5 Instructors, 1 Administrator)
        Client client1 = new Client("Alice Johnson", "123-456-7890");
        Client client2 = new Client("Bob Smith", "234-567-8901");
        Client client3 = new Client("Carol Lee", "345-678-9012");
        Client client4 = new Client("David Brown", "456-789-0123");

        Instructor instructor1 = new Instructor("Emma Davis", "567-890-1234", "Yoga", List.of("New York"));
        Instructor instructor2 = new Instructor("Frank Harris", "678-901-2345", "Judo", List.of("Toronto"));
        Instructor instructor3 = new Instructor("Grace Wilson", "789-012-3456", "Swimming", List.of("Vancouver"));
        Instructor instructor4 = new Instructor("Hank Martinez", "890-123-4567", "Boxing", List.of("Montreal"));
        Instructor instructor5 = new Instructor("Ivy Clark", "901-234-5678", "Pilates", List.of("Calgary"));

        Administrator admin = new Administrator("Jane Admin", "012-345-6789");

        // Save users
        userDAO.saveUser(client1);
        userDAO.saveUser(client2);
        userDAO.saveUser(client3);
        userDAO.saveUser(client4);
        userDAO.saveUser(instructor1);
        userDAO.saveUser(instructor2);
        userDAO.saveUser(instructor3);
        userDAO.saveUser(instructor4);
        userDAO.saveUser(instructor5);
        userDAO.saveUser(admin);

        // Create 10 spaces
        Space space1 = new Space(true, LocalTime.of(9, 0), LocalTime.of(17, 0), "New York", "Room A", List.of(DayOfWeek.MONDAY));
        Space space2 = new Space(true, LocalTime.of(10, 0), LocalTime.of(18, 0), "Toronto", "Room B", List.of(DayOfWeek.TUESDAY));
        Space space3 = new Space(true, LocalTime.of(8, 0), LocalTime.of(16, 0), "Vancouver", "Room C", List.of(DayOfWeek.WEDNESDAY));
        Space space4 = new Space(true, LocalTime.of(9, 0), LocalTime.of(15, 0), "Montreal", "Room D", List.of(DayOfWeek.THURSDAY));
        Space space5 = new Space(true, LocalTime.of(7, 0), LocalTime.of(19, 0), "Calgary", "Room E", List.of(DayOfWeek.FRIDAY));
        Space space6 = new Space(true, LocalTime.of(10, 0), LocalTime.of(14, 0), "New York", "Room F", List.of(DayOfWeek.SATURDAY));
        Space space7 = new Space(true, LocalTime.of(12, 0), LocalTime.of(18, 0), "Toronto", "Room G", List.of(DayOfWeek.SUNDAY));
        Space space8 = new Space(true, LocalTime.of(11, 0), LocalTime.of(20, 0), "Vancouver", "Room H", List.of(DayOfWeek.MONDAY));

        // Save spaces
        spacesDAO.saveSpace(space1);
        spacesDAO.saveSpace(space2);
        spacesDAO.saveSpace(space3);
        spacesDAO.saveSpace(space4);
        spacesDAO.saveSpace(space5);
        spacesDAO.saveSpace(space6);
        spacesDAO.saveSpace(space7);
        spacesDAO.saveSpace(space8);

        // Create 8 lessons
        Lesson lesson1 = new Lesson(LocalTime.of(9, 0), LocalTime.of(11, 0), true, "Yoga Basics", true, space1, DayOfWeek.MONDAY, "Sep 1 - Nov 30, 2024");
        Lesson lesson2 = new Lesson(LocalTime.of(10, 0), LocalTime.of(12, 0), true, "Advanced Judo", false, space2, DayOfWeek.TUESDAY, "Oct 1 - Dec 31, 2024");
        Lesson lesson3 = new Lesson(LocalTime.of(14, 0), LocalTime.of(16, 0), true, "Swimming 101", true, space3, DayOfWeek.WEDNESDAY, "Jul 1 - Sep 30, 2024");
        Lesson lesson4 = new Lesson(LocalTime.of(13, 0), LocalTime.of(15, 0), true, "Boxing Techniques", false, space4, DayOfWeek.THURSDAY, "May 1 - Jul 31, 2024");
        Lesson lesson5 = new Lesson(LocalTime.of(8, 0), LocalTime.of(10, 0), true, "Pilates for Beginners", true, space5, DayOfWeek.FRIDAY, "Jan 1 - Mar 31, 2024");
        Lesson lesson6 = new Lesson(LocalTime.of(9, 0), LocalTime.of(11, 0), true, "Meditation", true, space6, DayOfWeek.SATURDAY, "Aug 1 - Oct 31, 2024");
        Lesson lesson7 = new Lesson(LocalTime.of(11, 0), LocalTime.of(13, 0), true, "Kickboxing", false, space7, DayOfWeek.SUNDAY, "Feb 1 - Apr 30, 2024");
        Lesson lesson8 = new Lesson(LocalTime.of(12, 0), LocalTime.of(14, 0), true, "Cardio Blast", true, space8, DayOfWeek.MONDAY, "Mar 1 - May 31, 2024");

        // Save lessons
        lessonDAO.saveLesson(lesson1);
        lessonDAO.saveLesson(lesson2);
        lessonDAO.saveLesson(lesson3);
        lessonDAO.saveLesson(lesson4);
        lessonDAO.saveLesson(lesson5);
        lessonDAO.saveLesson(lesson6);
        lessonDAO.saveLesson(lesson7);
        lessonDAO.saveLesson(lesson8);

        // Create 5 offerings (3 public, 2 unavailable)
        Offering offering1 = new Offering(true, lesson1, instructor1); // Public
        Offering offering2 = new Offering(false, lesson2, instructor2); // Unavailable
        Offering offering3 = new Offering(true, lesson3, instructor3); // Public
        Offering offering4 = new Offering(true, lesson4, instructor4); // Public
        Offering offering5 = new Offering(false, lesson5, instructor5); // Unavailable

        offering1.setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        offering2.setOfferingStatus(OfferingStatus.UNAVAILABLE);
        offering3.setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        offering4.setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        offering5.setOfferingStatus(OfferingStatus.UNAVAILABLE);

        // Save offerings
        offeringDAO.saveOffering(offering1);
        offeringDAO.saveOffering(offering2);
        offeringDAO.saveOffering(offering3);
        offeringDAO.saveOffering(offering4);
        offeringDAO.saveOffering(offering5);

//        // Create 2 bookings
//        Booking booking1 = new Booking(client1, offering1);
//        Booking booking2 = new Booking(client2, offering3);
//
//        // Save bookings
//        bookingDAO.saveBooking(booking1);
//        bookingDAO.saveBooking(booking2);
    }



}

