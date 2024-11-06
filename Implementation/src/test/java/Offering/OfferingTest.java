package Offering;

import User.Instructor;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.Assert.*;

public class OfferingTest {

    private Offering privateOffering;
    private Offering groupOffering;
    private Instructor instructor;

    @Before
    public void setUp() {
        // Initialize an instructor
        instructor = new Instructor("John Doe", "123-456-7890", "Yoga", Arrays.asList("New York", "Los Angeles"));

        // Initialize offerings
        privateOffering = new Offering(1, true, "Private Yoga Class", LocalTime.of(10, 0), LocalTime.of(11, 0), Days.MONDAY);
        groupOffering = new Offering(2, false, "Group Yoga Class", LocalTime.of(14, 0), LocalTime.of(15, 0), Days.TUESDAY);
    }

    @Test
    public void makeAvailableToInstructors() {
        privateOffering.makeAvailableToInstructors();
        groupOffering.makeAvailableToInstructors();

        assertEquals(OfferingStatus.AVAILABLE_TO_INSTRUCTORS, privateOffering.getStatus());
        assertEquals(OfferingStatus.AVAILABLE_TO_INSTRUCTORS, groupOffering.getStatus());
    }

    @Test
    public void selectByInstructor() {
        // Make the offering available to instructors first
        groupOffering.makeAvailableToInstructors();

        // Select the offering by an instructor
        groupOffering.selectByInstructor(instructor);

        // Check that the status has changed to AVAILABLE_TO_PUBLIC
        assertEquals(OfferingStatus.AVAILABLE_TO_PUBLIC, groupOffering.getStatus());

        // Check that the instructor was correctly assigned
        assertSame(instructor, groupOffering.getInstructor());
    }


    @Test
    public void bookOffering_PrivateLesson() {
        privateOffering.makeAvailableToInstructors();
        privateOffering.selectByInstructor(instructor);

        // Book the private offering and check if it’s fully booked
        privateOffering.bookOffering();
        assertEquals(OfferingStatus.FULLY_BOOKED, privateOffering.getStatus());

        // Attempting to book again should throw an exception
        assertThrows(IllegalStateException.class, privateOffering::bookOffering);
    }

    @Test
    public void bookOffering_GroupLesson() {
        groupOffering.makeAvailableToInstructors();
        groupOffering.selectByInstructor(instructor);

        // Book the group offering up to capacity
        for (int i = 0; i < 5; i++) {
            groupOffering.bookOffering();
        }

        // Verify the group offering is fully booked after reaching max capacity
        assertEquals(OfferingStatus.FULLY_BOOKED, groupOffering.getStatus());

        // Attempting to book again should throw an exception
        assertThrows(IllegalStateException.class, groupOffering::bookOffering);
    }

    @Test
    public void isAvailableForPublicView() {
        // Initially, offerings should not be available for public view
        assertFalse(privateOffering.isAvailableForPublicView());
        assertFalse(groupOffering.isAvailableForPublicView());

        // Make the group offering available to instructors and then select by an instructor
        groupOffering.makeAvailableToInstructors();
        groupOffering.selectByInstructor(instructor);

        // Verify the offering is now available for public view
        assertTrue(groupOffering.isAvailableForPublicView());

        // Fully book the offering
        for (int i = 0; i < 5; i++) {
            groupOffering.bookOffering();
        }

        // Verify the offering is still available for public view even when fully booked
        assertTrue(groupOffering.isAvailableForPublicView());
    }

    @Test
    public void getOfferId() {
        assertEquals(1, privateOffering.getOfferId());
        assertEquals(2, groupOffering.getOfferId());
    }

    @Test
    public void getStatus() {
        // Initially, both offerings should be unavailable
        assertEquals(OfferingStatus.UNAVAILABLE, privateOffering.getStatus());
        assertEquals(OfferingStatus.UNAVAILABLE, groupOffering.getStatus());

        // Change the status and check again
        privateOffering.makeAvailableToInstructors();
        assertEquals(OfferingStatus.AVAILABLE_TO_INSTRUCTORS, privateOffering.getStatus());
    }

    @Test
    public void isPrivateLesson() {
        assertTrue(privateOffering.isPrivateLesson());
        assertFalse(groupOffering.isPrivateLesson());
    }

}
