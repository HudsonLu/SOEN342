package org.Tests;

import Authentication.*;
import User.*;
import Catalog.*;


public class MainMenu {

    public static void main(String[] args) {
        // Initialize Spaces and Lessons
        Spaces spaces = new Spaces();
        Lessons lessons = new Lessons();

        // Retrieve instructor from repository
        User user = Users.getUserByNameAndPhone("John Doe", "123-456-7890");
        if (user instanceof Instructor) {
            Instructor instructor = (Instructor) user;

            // Display instructor actions
            instructor.performRoleSpecificActions();

            // Create an offering
            instructor.createOffering(lessons.getLessons());

            // Display offerings
            System.out.println("\nCurrent Offerings:");
            Offerings.displayOfferings();
        } else {
            System.out.println("No instructor found with the given credentials.");
        }
    }

}
