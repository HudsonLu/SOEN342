package org.example;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
        public static void main(String[] args) {

                // Suppress Hibernate logs
                Logger hibernateLogger = Logger.getLogger("org.hibernate");
                hibernateLogger.setLevel(Level.SEVERE);

                Client client = new Client("John Doe", "123-456-7890");
                ClientDAO clientDAO = new ClientDAO();
                clientDAO.saveClient(client);

                // Fetch and print all clients
                List<Client> clients = clientDAO.getAllClients();
                System.out.println("All Clients in the Database:");
                for (Client x : clients) {
                        System.out.println("ID: " + x.getId() + ", Name: " + x.getName() + ", Phone: " + x.getTelephoneNumber());
                }

                // Delete a client
                System.out.println("Deleting client with ID: " + client.getId());
                clientDAO.deleteClient(client.getId());
                clientDAO.deleteClient(2L);

                // Fetch and print all clients again
                clients = clientDAO.getAllClients();
                System.out.println("All Clients in the Database after deletion:");
                for (Client x : clients) {
                        System.out.println("ID: " + x.getId() + ", Name: " + x.getName() + ", Phone: " + x.getTelephoneNumber());
                }


                // Update the client with ID 10
                Long clientIdToUpdate = 10L; // ID of the client you want to update
                String newName = "Updated Name"; // New name to assign to the client

                System.out.println("Updating client with ID: " + clientIdToUpdate + " to name: " + newName);
                clientDAO.updateClientName(clientIdToUpdate, newName);

                // Fetch and print all clients to verify the update
                List<Client> clients1 = clientDAO.getAllClients();
                System.out.println("All Clients in the Database after update:");
                for (Client y : clients1) {
                        System.out.println("ID: " + y.getId() + ", Name: " + y.getName() + ", Phone: " + y.getTelephoneNumber());
                }
        }
}
