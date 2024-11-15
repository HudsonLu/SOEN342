package DAO;

import Booking.Offering;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OfferingDAO {

    // Save an Offering
    public void saveOffering(Offering offering) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(offering);
            transaction.commit();
            System.out.println("Offering saved with ID: " + offering.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Fetch all Offerings
    public List<Offering> getAllOfferings() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Offering", Offering.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Fetch an Offering by ID
    public Offering getOfferingById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Offering.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Delete an Offering by ID
    public void deleteOffering(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Offering offering = session.get(Offering.class, id);
            if (offering != null) {
                session.remove(offering);
                System.out.println("Offering deleted with ID: " + id);
            } else {
                System.out.println("Offering with ID " + id + " not found.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Truncate the Offerings table
    public void truncateOfferings() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE Offerings CASCADE").executeUpdate();
            transaction.commit();
            System.out.println("Offerings table truncated.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
