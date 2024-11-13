package org.example;

import Authentication.*;
import User.*;
import Lesson.*;
import Catalog.*;

public class MainTest {
    public static void main(String[] args) {
        // Initialize Spaces and Lessons
        Spaces spaces = new Spaces();
        Lessons lessons = new Lessons(spaces);

        // Initialize Administrator
        Administrator admin = new Administrator("Admin Alice", "111-222-3333", lessons, spaces);

        // Display Administrator actions
        System.out.println("Administrator Role Actions:");
        admin.performRoleSpecificActions();

        // Create a lesson
        System.out.println("\nAdmin: Create a new lesson.");
        //admin.createLesson();

        // Display updated lessons
        System.out.println("\nUpdated Lessons:");
        lessons.displayAllLessons();
    }
}
