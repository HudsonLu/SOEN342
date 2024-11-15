package DAO;

import Booking.Offering;
import Booking.OfferingStatus;
import DAO.LessonDAO;
import DAO.OfferingDAO;
import DAO.SpacesDAO;
import Lesson.Lesson;
import Lesson.Space;
import User.Instructor;
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

public class OfferingDAOTest {

    private OfferingDAO offeringDAO;
    private LessonDAO lessonDAO;
    private SpacesDAO spacesDAO;

    @Before
    public void setup() {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.SEVERE);

        offeringDAO = new OfferingDAO();
        lessonDAO = new LessonDAO();
        spacesDAO = new SpacesDAO();

        // Truncate tables to ensure clean state
        offeringDAO.truncateOfferings();
        lessonDAO.truncateLessonsTable();
        spacesDAO.truncateTables();

        populateSpacesAndLessons();
    }

    private void populateSpacesAndLessons() {
        // Save spaces
        Space space1 = new Space(true, LocalTime.of(12, 0), LocalTime.of(15, 0), "Montreal", "EV-Building Gym Room 7", Arrays.asList(DayOfWeek.SUNDAY));
        Space space2 = new Space(true, LocalTime.of(10, 0), LocalTime.of(14, 0), "Toronto", "Room A1", Arrays.asList(DayOfWeek.SATURDAY));
        spacesDAO.saveSpace(space1);
        spacesDAO.saveSpace(space2);

        // Fetch spaces from DB
        List<Space> spaces = spacesDAO.getAllSpaces();

        // Save lessons using the fetched spaces
        Lesson lesson1 = new Lesson(LocalTime.of(12, 0), LocalTime.of(15, 0), true, "Judo", false, spaces.get(0), DayOfWeek.SUNDAY, "Sep 1 - Nov 30, 2024");
        Lesson lesson2 = new Lesson(LocalTime.of(10, 0), LocalTime.of(12, 0), true, "Yoga", true, spaces.get(1), DayOfWeek.SATURDAY, "Sep 1 - Nov 30, 2024");

        lessonDAO.saveLesson(lesson1);
        lessonDAO.saveLesson(lesson2);
    }

    @Test
    public void testCreateOfferings() {
        // Fetch lessons from the DB
        List<Lesson> lessons = lessonDAO.getAllLessons();
        assertNotNull("Lessons should not be null", lessons);
        assertFalse("Lessons should not be empty", lessons.isEmpty());

        // Save instructor
        Instructor instructor = new Instructor("tre He", "456-456-7850", "Yoga", Arrays.asList("Montreal", "Toronto"));
        saveInstructor(instructor);

        // Create offerings
        Offering offering1 = new Offering(true, lessons.get(0), instructor);
        Offering offering2 = new Offering(true, lessons.get(1), instructor);

        offeringDAO.saveOffering(offering1);
        offeringDAO.saveOffering(offering2);

        // Fetch all offerings from DB
        List<Offering> offerings = offeringDAO.getAllOfferings();
        assertNotNull("Offerings should not be null", offerings);
        assertEquals("There should be 2 offerings", 2, offerings.size());

        // Verify offering details
        Offering savedOffering1 = offerings.get(0);
        assertEquals("Instructor should match", instructor.getName(), savedOffering1.getInstructor().getName());
        assertEquals("Lesson should match", lessons.get(0).getLessonName(), savedOffering1.getLesson().getLessonName());
        assertEquals("Status should be UNAVAILABLE", OfferingStatus.UNAVAILABLE, savedOffering1.getOfferingStatus());
    }

    @Test
    public void testDeleteOffering() {
        // Fetch lessons from DB
        List<Lesson> lessons = lessonDAO.getAllLessons();
        assertNotNull("Lessons should not be null", lessons);
        assertFalse("Lessons should not be empty", lessons.isEmpty());

        // Save instructor
        Instructor instructor = new Instructor("Jane Smith", "987-342-3210", "Aikido", Arrays.asList("Vancouver"));
        saveInstructor(instructor);

        // Create an offering
        Offering offering = new Offering(true, lessons.get(0), instructor);
        offeringDAO.saveOffering(offering);

        // Verify offering is saved
        List<Offering> offerings = offeringDAO.getAllOfferings();
        assertEquals("There should be 1 offering", 1, offerings.size());

        // Delete the offering
        offeringDAO.deleteOffering(offerings.get(0).getId());

        // Verify offering is deleted
        offerings = offeringDAO.getAllOfferings();
        assertTrue("Offerings list should be empty after deletion", offerings.isEmpty());
    }

    private void saveInstructor(Instructor instructor) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(instructor);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to save instructor: " + e.getMessage());
        }
    }
}
