package DAO;

import Booking.*;
import Catalog.Offerings;
import Lesson.*;
import User.*;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

public class OfferingDAOTest {

    private OfferingDAO offeringDAO;
    private LessonDAO lessonDAO;
    private UserDAO userDAO;

    @Before
    public void setup() {
        // Initialize DAOs
        offeringDAO = new OfferingDAO();
        lessonDAO = new LessonDAO();
        userDAO = new UserDAO();

        // Truncate tables for a clean state
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE Offering CASCADE").executeUpdate();
            session.createNativeQuery("TRUNCATE TABLE Lesson CASCADE").executeUpdate();
            session.createNativeQuery("TRUNCATE TABLE Instructor CASCADE").executeUpdate();
            transaction.commit();
        }
    }

    private Instructor createInstructor() {
        Instructor instructor = new Instructor("John Doe", "123-456-7890", "Yoga", List.of("Montreal"));
        userDAO.saveUser(instructor);
        return instructor;
    }

    private Lesson createLesson() {
        Lesson lesson = new Lesson(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                true,
                "Yoga",
                true,
                null, // Assume space is null for this test
                DayOfWeek.MONDAY,
                "Sep 1 - Nov 30, 2024"
        );
        lessonDAO.saveLesson(lesson);
        return lesson;
    }

    @Test
    public void testAddOffering() {
        Instructor instructor = createInstructor();
        Lesson lesson = createLesson();

        Offering offering = new Offering(true, lesson, instructor);
        offeringDAO.saveOffering(offering);

        List<Offering> offerings = offeringDAO.getAllOfferings();
        assertNotNull(offerings);
        assertEquals(1, offerings.size());
        assertEquals("Yoga", offerings.get(0).getLesson().getLessonName());
        assertEquals("John Doe", offerings.get(0).getInstructor().getName());
    }

    @Test
    public void testGetAllOfferings() {
        Instructor instructor = createInstructor();
        Lesson lesson = createLesson();

        Offering offering1 = new Offering(true, lesson, instructor);
        Offering offering2 = new Offering(true, lesson, instructor);

        offeringDAO.saveOffering(offering1);
        offeringDAO.saveOffering(offering2);

        List<Offering> offerings = offeringDAO.getAllOfferings();
        assertNotNull(offerings);
        assertEquals(2, offerings.size());
    }

    @Test
    public void testBookOffering() {
        Instructor instructor = createInstructor();
        Lesson lesson = createLesson();

        Offering offering = new Offering(true, lesson, instructor);
        offering.setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        offeringDAO.saveOffering(offering);

        // Retrieve the offering and update its status
        List<Offering> publicOfferings = Offerings.getOfferingsByStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        assertEquals(1, publicOfferings.size());

        Offerings.bookOffering(1, "ClientName");

        Offering bookedOffering = offeringDAO.getAllOfferings().get(0);
        assertEquals(OfferingStatus.FULLY_BOOKED, bookedOffering.getOfferingStatus());
    }

    @Test
    public void testCancelOffering() {
        Instructor instructor = createInstructor();
        Lesson lesson = createLesson();

        Offering offering = new Offering(true, lesson, instructor);
        offering.setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        offeringDAO.saveOffering(offering);

        List<Offering> cancellableOfferings = Offerings.getCancellableOfferings();
        assertEquals(1, cancellableOfferings.size());

        Offerings.cancelOffering(1);

        List<Offering> updatedOfferings = offeringDAO.getAllOfferings();
        assertTrue(updatedOfferings.isEmpty()); // Ensure offering is removed
    }

    @Test
    public void testGetOfferingsByStatus() {
        Instructor instructor = createInstructor();
        Lesson lesson = createLesson();

        Offering offering1 = new Offering(true, lesson, instructor);
        offering1.setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        offeringDAO.saveOffering(offering1);

        Offering offering2 = new Offering(true, lesson, instructor);
        offering2.setOfferingStatus(OfferingStatus.UNAVAILABLE);
        offeringDAO.saveOffering(offering2);

        List<Offering> publicOfferings = Offerings.getOfferingsByStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);
        assertEquals(1, publicOfferings.size());

        List<Offering> unavailableOfferings = Offerings.getOfferingsByStatus(OfferingStatus.UNAVAILABLE);
        assertEquals(1, unavailableOfferings.size());
    }
}
