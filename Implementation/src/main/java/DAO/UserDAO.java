package DAO;

import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Authentication.*;
import User.*;
import Catalog.*;

import java.util.List;

public class UserDAO {

    // Save a User (can be any subclass of User)
    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Save the User object
            session.save(user);

            transaction.commit();
            System.out.println("Saved User with ID: " + user.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Fetch all Users from the database
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Delete a User by ID
    public void deleteUser(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Fetch the User object to delete
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.println("Deleted User with ID: " + id);
            } else {
                System.out.println("User with ID: " + id + " not found.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Update the name of a User by ID
    public void updateUserName(Long id, String newName) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Fetch the User object to update
            User user = session.get(User.class, id);
            if (user != null) {
                user.setName(newName); // Update the name
                session.update(user);  // Save the updated object
                System.out.println("Updated User with ID: " + id + " to name: " + newName);
            } else {
                System.out.println("User with ID: " + id + " not found.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
