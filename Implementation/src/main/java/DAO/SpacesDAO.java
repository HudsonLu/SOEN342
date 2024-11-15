//SpacesDAO.java
package DAO;

import Lesson.Space;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class SpacesDAO {

    public void saveSpace(Space space) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(space);
            transaction.commit();
            System.out.println("Saved Space with ID: " + space.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new IllegalStateException("Error saving space to the database", e);
        }
    }

    public List<Space> getAllSpaces() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Space", Space.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteSpace(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Space space = session.get(Space.class, id);
            if (space != null) {
                session.delete(space);
                System.out.println("Deleted Space with ID: " + id);
            } else {
                System.out.println("Space with ID: " + id + " not found.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void truncateTables() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // Truncate the Spaces table
            session.createNativeQuery("TRUNCATE TABLE Spaces CASCADE").executeUpdate();
            // Truncate related tables if necessary
            session.createNativeQuery("TRUNCATE TABLE Space_Available_Days CASCADE").executeUpdate();
            transaction.commit();
            System.out.println("Truncated Spaces and related tables.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Error truncating Spaces tables.", e);
        }
    }
}
