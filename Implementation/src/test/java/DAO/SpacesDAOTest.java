package DAO;

import Lesson.Space;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class SpacesDAOTest {

    private SpacesDAO spacesDAO;

    @Before
    public void setup() {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.SEVERE);
        spacesDAO = new SpacesDAO();
    }

    @Test
    public void testSaveAndRetrieveSpaces() {
        // Create a new space
        Space space = new Space(true, LocalTime.of(9, 0), LocalTime.of(17, 0), "Montreal", "Room 101", Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
        spacesDAO.saveSpace(space);

        // Retrieve spaces
        List<Space> spaces = spacesDAO.getAllSpaces();
        assertNotNull(spaces);
        assertTrue(spaces.size() > 0);

        // Print spaces
        for (Space s : spaces) {
            System.out.println(s.getDetails());
        }
    }

    @Test
    public void testDeleteSpace() {
        // Create a new space
        Space space = new Space(true, LocalTime.of(10, 0), LocalTime.of(14, 0), "Toronto", "Room A1", Arrays.asList(DayOfWeek.TUESDAY));
        spacesDAO.saveSpace(space);

        // Retrieve spaces
        List<Space> spaces = spacesDAO.getAllSpaces();
        assertNotNull(spaces);

        // Delete the space
        spacesDAO.deleteSpace(space.getId());

        // Verify deletion
        List<Space> updatedSpaces = spacesDAO.getAllSpaces();
        assertFalse(updatedSpaces.contains(space));
    }

    @Test
    public void testTruncateTables() {
        // Truncate tables
        spacesDAO.truncateTables();
        // Verify that the tables are empty
        assertTrue(spacesDAO.getAllSpaces().isEmpty());
    }

    @Test
    public void testGetAllSpaces() {
        // Add sample spaces
        Space space1 = new Space(true, LocalTime.of(9, 0), LocalTime.of(17, 0), "Montreal", "Room 101", Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
        Space space2 = new Space(true, LocalTime.of(10, 0), LocalTime.of(16, 0), "Toronto", "Room 202", Arrays.asList(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY));
        spacesDAO.saveSpace(space1);
        spacesDAO.saveSpace(space2);

        // Fetch all spaces
        List<Space> spaces = spacesDAO.getAllSpaces();
        // Print details of each space (optional)
        for (Space space : spaces) {
            System.out.println("Space Details:");
            System.out.println("City: " + space.getCity());
            System.out.println("Room: " + space.getRoom());
            System.out.println("Available: " + space.isAvailable());
            System.out.println("Start Time: " + space.getStartTime());
            System.out.println("End Time: " + space.getEndTime());
            System.out.println("Available Days: " + space.getAvailableDays());
            System.out.println("-----------------------------------");
        }
    }

}
