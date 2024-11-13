package org.example;
import Authentication.*;
import User.*;
import Lesson.*;
import Catalog.*;
import Booking.*;
import java.util.List;
import java.util.Scanner;

public class MainCancel {
    public static void main(String[] args) {
        // Initialize spaces
        Spaces spaces = new Spaces();

        // Initialize lessons
        Lessons lessons = new Lessons(spaces);

        // Create offerings associated with lessons
        Instructor instructor1 = new Instructor("John Doe", "123-456-7890", "Yoga", List.of("Montreal"));
        Offerings.addOffering(new Offering(false, lessons.getLessons().get(0), instructor1)); // Judo offering

        // Display all lessons
        System.out.println("All Lessons:");
        lessons.displayAllLessons();
        System.out.println();

        // Display cancellable lessons (not associated with offerings)
        System.out.println("Available Lessons for Cancellation:");
        lessons.displayCancellableLessons();
        System.out.println();

        // Prompt user to cancel a lesson
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the index of the lesson you want to cancel (or 0 to cancel):");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (index == 0) {
            System.out.println("No lesson was canceled.");
        } else {
            lessons.removeLesson(index);
        }
        System.out.println();

        // Display updated lessons
        System.out.println("Updated Lessons:");
        lessons.displayAllLessons();
        System.out.println();

        // Display updated cancellable lessons
        System.out.println("Updated Lessons for Cancellation:");
        lessons.displayCancellableLessons();
    }
}
