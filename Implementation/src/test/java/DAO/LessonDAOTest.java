package DAO;

import Lesson.Lesson;
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

public class LessonDAOTest {

    private LessonDAO lessonDAO;
    private SpacesDAO spacesDAO;

    @Before
    public void setup() {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.SEVERE);
        lessonDAO = new LessonDAO();
        spacesDAO = new SpacesDAO();
        lessonDAO.truncateLessonsTable();
        spacesDAO.truncateTables();
    }

    @Test
    public void testSaveAndGetLessons() {
        // Create and save a space
        Space space = new Space(true, LocalTime.of(9, 0), LocalTime.of(17, 0), "Montreal", "Room 101", Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
        spacesDAO.saveSpace(space);

        // Create and save lessons
        Lesson lesson1 = new Lesson(LocalTime.of(10, 0), LocalTime.of(12, 0), true, "Yoga", true, space, DayOfWeek.MONDAY, "Sep 1 - Nov 30, 2024");
        Lesson lesson2 = new Lesson(LocalTime.of(14, 0), LocalTime.of(16, 0), true, "Judo", false, space, DayOfWeek.FRIDAY, "Oct 1 - Dec 31, 2024");

        lessonDAO.saveLesson(lesson1);
        lessonDAO.saveLesson(lesson2);

        // Fetch lessons from database
        List<Lesson> lessons = lessonDAO.getAllLessons();

        // Verify results
        assertNotNull(lessons);
        assertEquals(2, lessons.size());
        System.out.println("Saved Lessons:");
        for (Lesson lesson : lessons) {
            lesson.displayLessonDetails();
        }
    }

    @Test
    public void testDeleteAllLessons() {
        // Save some lessons
        Space space1 = new Space(true, LocalTime.of(9, 0), LocalTime.of(12, 0), "Montreal", "Room A", Arrays.asList(DayOfWeek.MONDAY));
        Space space2 = new Space(true, LocalTime.of(13, 0), LocalTime.of(15, 0), "Toronto", "Room B", Arrays.asList(DayOfWeek.TUESDAY));
        spacesDAO.saveSpace(space1);
        spacesDAO.saveSpace(space2);

        Lesson lesson1 = new Lesson(LocalTime.of(9, 0), LocalTime.of(10, 0), true, "Math", true, space1, DayOfWeek.MONDAY, "Jan 1 - Mar 31, 2024");
        Lesson lesson2 = new Lesson(LocalTime.of(10, 0), LocalTime.of(12, 0), true, "Physics", false, space2, DayOfWeek.TUESDAY, "Apr 1 - Jun 30, 2024");
        lessonDAO.saveLesson(lesson1);
        lessonDAO.saveLesson(lesson2);

        // Verify lessons exist
        List<Lesson> lessons = lessonDAO.getAllLessons();
        assertNotNull(lessons);
        assertEquals(2, lessons.size());

        // Delete all lessons
        lessonDAO.truncateLessonsTable();

        // Verify lessons are deleted
        lessons = lessonDAO.getAllLessons();
        assertNotNull(lessons);
        assertEquals(0, lessons.size());
    }

    @Test
    public void testDeleteSpecificLesson() {
        // Save a lesson
        Space space = new Space(true, LocalTime.of(9, 0), LocalTime.of(11, 0), "Vancouver", "Room C", Arrays.asList(DayOfWeek.WEDNESDAY));
        spacesDAO.saveSpace(space);

        Lesson lesson = new Lesson(LocalTime.of(9, 0), LocalTime.of(10, 0), true, "History", true, space, DayOfWeek.WEDNESDAY, "Jan 1 - Mar 31, 2024");
        lessonDAO.saveLesson(lesson);

        // Verify the lesson exists
        List<Lesson> lessons = lessonDAO.getAllLessons();
        assertNotNull(lessons);
        assertEquals(1, lessons.size());

        // Delete the lesson
        lessonDAO.deleteLesson(lessons.get(0).getId());

        // Verify the lesson is deleted
        lessons = lessonDAO.getAllLessons();
        assertNotNull(lessons);
        assertEquals(0, lessons.size());
    }

    @Test
    public void testGetAllLessons() {
        // Save multiple lessons
        Space space1 = new Space(true, LocalTime.of(8, 0), LocalTime.of(10, 0), "Montreal", "Room D", Arrays.asList(DayOfWeek.THURSDAY));
        Space space2 = new Space(true, LocalTime.of(10, 0), LocalTime.of(12, 0), "Toronto", "Room E", Arrays.asList(DayOfWeek.FRIDAY));
        spacesDAO.saveSpace(space1);
        spacesDAO.saveSpace(space2);

        Lesson lesson1 = new Lesson(LocalTime.of(8, 0), LocalTime.of(9, 0), true, "Biology", true, space1, DayOfWeek.THURSDAY, "Jul 1 - Sep 30, 2024");
        Lesson lesson2 = new Lesson(LocalTime.of(10, 0), LocalTime.of(11, 0), true, "Chemistry", false, space2, DayOfWeek.FRIDAY, "Oct 1 - Dec 31, 2024");
        lessonDAO.saveLesson(lesson1);
        lessonDAO.saveLesson(lesson2);

        // Retrieve and validate lessons
        List<Lesson> lessons = lessonDAO.getAllLessons();
        assertNotNull(lessons);
        assertEquals(2, lessons.size());

        for (Lesson lesson : lessons) {
            System.out.println("Lesson Name: " + lesson.getLessonName());
            System.out.println("Day: " + lesson.getDayOfWeek());
            System.out.println("Start Time: " + lesson.getStartTime());
            System.out.println("End Time: " + lesson.getEndTime());
            System.out.println("--------------------------------");
        }
    }


}
