package Authentication;

import DAO.UserDAO;
import User.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class LoginServiceTest {

    private UserDAO userDAO;

    @Before
    public void setup() {
        // Setup the UserDAO and clean up the database before each test
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.SEVERE);
        userDAO = new UserDAO();
        // userDAO.truncateTables(); // Clears the database
    }

    @Test
    public void testRegisterAndAuthenticateClient() {
        // Register a new Client
        String clientName = "Test Client";
        String clientPhone = "111-111-1111";
        LoginService.register(clientName, clientPhone, "Client", null, null);

        // Authenticate the Client
        User authenticatedUser = LoginService.authenticate(clientName, clientPhone);
        assertNotNull(authenticatedUser);
        assertEquals(clientName, authenticatedUser.getName());
        assertEquals(clientPhone, authenticatedUser.getPhoneNumber());
        assertEquals("Client", authenticatedUser.getRole());
    }

    @Test
    public void testRegisterAndAuthenticateInstructor() {
        // Register a new Instructor
        String instructorName = "Test Instructor";
        String instructorPhone = "222-222-2222";
        String specialization = "Math";
        List<String> cities = List.of("City1", "City2");
        LoginService.register(instructorName, instructorPhone, "Instructor", specialization, cities);

        // Authenticate the Instructor
        User authenticatedUser = LoginService.authenticate(instructorName, instructorPhone);
        assertNotNull(authenticatedUser);
        assertEquals(instructorName, authenticatedUser.getName());
        assertEquals(instructorPhone, authenticatedUser.getPhoneNumber());
        assertEquals("Instructor", authenticatedUser.getRole());

        // Verify specialization and cities
        assertTrue(authenticatedUser instanceof Instructor);
        Instructor instructor = (Instructor) authenticatedUser;
        assertEquals(specialization, instructor.getSpecialization());
        assertEquals(cities, instructor.getCities());
    }

    @Test
    public void testAuthenticateValidClient() {
        // Authenticate an existing client
        User authenticatedUser = LoginService.authenticate("Bob", "987-654-3210");
        assertNotNull("User should be authenticated successfully", authenticatedUser);
        assertEquals("Bob", authenticatedUser.getName());
        assertEquals("987-654-3210", authenticatedUser.getPhoneNumber());
        assertEquals("Client", authenticatedUser.getRole());
    }



    @Test
    public void testRegisterDuplicateUser() {
        // Register a new Client
        String clientName = "Duplicate Client";
        String clientPhone = "333-333-3333";
        LoginService.register(clientName, clientPhone, "Client", null, null);

        // Try registering the same user again
        LoginService.register(clientName, clientPhone, "Client", null, null);

        // Verify only one user exists with the given phone number
        List<User> users = Users.getAllUsers();
        assertEquals(1, users.stream()
                .filter(user -> user.getPhoneNumber().equals(clientPhone)).count());
    }

    @Test
    public void testInvalidRoleRegistration() {
        // Attempt to register with an invalid role
        LoginService.register("Invalid Role User", "444-444-4444", "InvalidRole", null, null);

        // Verify the user was not registered
        User user = Users.getUserByNameAndPhone("Invalid Role User", "444-444-4444");
        assertNull(user);
    }

    @Test
    public void testAdministratorSelfRegister() {
        // Attempt to register an Administrator
        LoginService.register("Admin Self Register", "555-555-5555", "Administrator", null, null);

        // Verify the user was not registered
        User user = Users.getUserByNameAndPhone("Admin Self Register", "555-555-5555");
        assertNull(user);
    }
}
