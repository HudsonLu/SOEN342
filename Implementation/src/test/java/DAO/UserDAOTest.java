package DAO;

import User.Administrator;
import User.Client;
import User.Instructor;
import User.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class UserDAOTest {

    private UserDAO userDAO;

    @Before
    public void setup() {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.SEVERE);

        userDAO = new UserDAO();
        userDAO.truncateTables(); // Clean database before tests
    }

    @Test
    public void saveUser() {
        Client client = new Client("John client", "145-456-7890");
        Administrator admin = new Administrator("John admin", "245-456-7890");
        Instructor instructor = new Instructor("John instructor", "445-456-7890");

        userDAO.saveUser(client);
        userDAO.saveUser(admin);
        userDAO.saveUser(instructor);

        User fetchedClient = userDAO.findByPhoneNumber("145-456-7890");
        User fetchedAdmin = userDAO.findByPhoneNumber("245-456-7890");
        User fetchedInstructor = userDAO.findByPhoneNumber("445-456-7890");

        assertNotNull(fetchedClient);
        assertNotNull(fetchedAdmin);
        assertNotNull(fetchedInstructor);

        assertEquals("John client", fetchedClient.getName());
        assertEquals("John admin", fetchedAdmin.getName());
        assertEquals("John instructor", fetchedInstructor.getName());
    }

    @Test
    public void findByPhoneNumber() {
        Client client = new Client("Jane client", "555-456-7890");
        userDAO.saveUser(client);

        User fetchedUser = userDAO.findByPhoneNumber("555-456-7890");
        assertNotNull(fetchedUser);
        assertEquals("Jane client", fetchedUser.getName());
        assertEquals("555-456-7890", fetchedUser.getPhoneNumber());
    }

    @Test
    public void getAllUsers() {
        Client client = new Client("Client 1", "111-111-1111");
        Administrator admin = new Administrator("Admin 1", "222-222-2222");
        Instructor instructor = new Instructor("Instructor 1", "333-333-3333");

        userDAO.saveUser(client);
        userDAO.saveUser(admin);
        userDAO.saveUser(instructor);

        List<User> users = userDAO.getAllUsers();
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void deleteUser() {
        Client client = new Client("Delete Client", "999-999-9999");
        userDAO.saveUser(client);

        User fetchedUser = userDAO.findByPhoneNumber("999-999-9999");
        assertNotNull(fetchedUser);

        userDAO.deleteUser(fetchedUser.getId());

        User deletedUser = userDAO.findByPhoneNumber("999-999-9999");
        assertNull(deletedUser);
    }

    @Test
    public void updateUserName() {
        Client client = new Client("Old Name", "888-888-8888");
        userDAO.saveUser(client);

        User fetchedUser = userDAO.findByPhoneNumber("888-888-8888");
        assertNotNull(fetchedUser);

        userDAO.updateUserName(fetchedUser.getId(), "New Name");

        User updatedUser = userDAO.findByPhoneNumber("888-888-8888");
        assertNotNull(updatedUser);
        assertEquals("New Name", updatedUser.getName());
    }
}
