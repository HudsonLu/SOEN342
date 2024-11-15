package DAO;

import Booking.Booking;
import Booking.Offering;
import Booking.OfferingStatus;
import Lesson.Lesson;
import User.Client;
import User.Instructor;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class BookingDAOTest {


    private Client testClient;
    private Instructor testInstructor;
    private Lesson testLesson;
    private Offering testOffering;

    @Before
    public void setup() {
        // Clean up database before each test
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.SEVERE);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE Booking CASCADE").executeUpdate();
            session.createNativeQuery("TRUNCATE TABLE Offering CASCADE").executeUpdate();
            session.createNativeQuery("TRUNCATE TABLE Lesson CASCADE").executeUpdate();
            session.createNativeQuery("TRUNCATE TABLE Client CASCADE").executeUpdate();
            session.createNativeQuery("TRUNCATE TABLE Instructor CASCADE").executeUpdate();
            transaction.commit();
        }

        // Set up test data
        testClient = new Client("Test Client", "123-456-7890");
        testInstructor = new Instructor("Test Instructor", "098-765-4321", "Yoga", List.of("New York"));
        testLesson = new Lesson(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                true,
                "Yoga Basics",
                true,
                null,
                DayOfWeek.MONDAY,
                "Sep 1 - Nov 30, 2024"
        );

        testOffering = new Offering(true, testLesson, testInstructor);
        testOffering.setOfferingStatus(OfferingStatus.AVAILABLE_TO_PUBLIC);

        // Persist test data
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(testClient);
            session.persist(testInstructor);
            session.persist(testLesson);
            session.persist(testOffering);
            transaction.commit();
        }
    }

    @Test
    public void testCreateBooking() {
        Booking booking = new Booking(testClient, testOffering);

        // Persist booking
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(booking);
            transaction.commit();
        }

        // Verify booking persistence
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Booking fetchedBooking = session.get(Booking.class, booking.getId());
            assertNotNull(fetchedBooking);
            assertEquals(testClient.getId(), fetchedBooking.getClient().getId());
            assertEquals(testOffering.getId(), fetchedBooking.getOffering().getId());
        }
    }

    @Test
    public void testViewBookingsByClient() {
        // Create a booking
        Booking booking = new Booking(testClient, testOffering);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(booking);
            transaction.commit();
        }

        // Verify the client's bookings
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Client fetchedClient = session.get(Client.class, testClient.getId());
            assertNotNull(fetchedClient);
            assertEquals(1, fetchedClient.getBookings().size());
            assertEquals(testOffering, fetchedClient.getBookings().get(0).getOffering());
        }
    }

    @Test
    public void testCancelBooking() {
        // Create a booking
        Booking booking = new Booking(testClient, testOffering);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(booking);
            transaction.commit();
        }

        // Cancel the booking
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Booking fetchedBooking = session.get(Booking.class, booking.getId());
            assertNotNull(fetchedBooking);

            Offering offeringToCancel = fetchedBooking.getOffering();
            fetchedBooking.cancel();

            session.delete(fetchedBooking); // Remove booking
            session.merge(offeringToCancel); // Update offering status
            transaction.commit();

            // Verify offering is now available
            Offering updatedOffering = session.get(Offering.class, offeringToCancel.getId());
            assertTrue(updatedOffering.isAvailable());
        }

        // Verify booking is removed
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Booking deletedBooking = session.get(Booking.class, booking.getId());
            assertNull(deletedBooking);
        }
    }

    @Test
    public void testFetchAllBookings() {
        // Create multiple bookings
        Booking booking1 = new Booking(testClient, testOffering);
        Booking booking2 = new Booking(testClient, testOffering);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(booking1);
            session.persist(booking2);
            transaction.commit();
        }

        // Fetch all bookings
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Booking> allBookings = session.createQuery("FROM Booking", Booking.class).list();
            assertEquals(2, allBookings.size());
        }
    }
}
