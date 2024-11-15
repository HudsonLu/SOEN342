package DAO;

import DAO.UserDAO;
import User.Administrator;
import User.Client;
import User.Instructor;
import org.junit.Before;
import org.junit.Test;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    }
}
