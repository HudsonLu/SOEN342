package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClientDAO {

    public void saveClient(Client client) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Save the Client object
            session.save(client);

            transaction.commit();
            System.out.println("Saved Client with ID: " + client.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Fetch all Clients from the database
    public List<Client> getAllClients() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Client", Client.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Delete a Client by ID
    public void deleteClient(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Fetch the Client object to delete
            Client client = session.get(Client.class, id);
            if (client != null) {
                session.delete(client);
                System.out.println("Deleted Client with ID: " + id);
            } else {
                System.out.println("Client with ID: " + id + " not found.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }
        // Update the name of a Client by ID
        public void updateClientName(Long id, String newName) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();

                // Fetch the Client object to update
                Client client = session.get(Client.class, id);
                if (client != null) {
                    client.setName(newName); // Update the name
                    session.update(client);  // Save the updated object
                    System.out.println("Updated Client with ID: " + id + " to name: " + newName);
                } else {
                    System.out.println("Client with ID: " + id + " not found.");
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
