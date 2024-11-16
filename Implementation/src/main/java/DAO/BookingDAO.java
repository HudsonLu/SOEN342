// BookingDAO.java
package DAO;

import Booking.Booking;
import User.Client;
import Utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BookingDAO {

    public void saveBooking(Booking booking) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(booking);
            transaction.commit();
            System.out.println("Booking saved with ID: " + booking.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Booking> getAllBookings() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Booking", Booking.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteBooking(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Booking booking = session.get(Booking.class, id);
            if (booking != null) {
                session.delete(booking);
                System.out.println("Deleted Booking with ID: " + id);
            } else {
                System.out.println("Booking with ID: " + id + " not found.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Booking> getClientBookings(Long clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Client client = session.get(Client.class, clientId);
            Hibernate.initialize(client.getBookings()); // Initialize lazy collection
            return client.getBookings();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void truncateBookingsTable() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // Using native SQL to truncate the Booking table
            session.createNativeQuery("TRUNCATE TABLE Booking CASCADE").executeUpdate();
            transaction.commit();
            System.out.println("All bookings have been deleted from the Booking table.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    
}
