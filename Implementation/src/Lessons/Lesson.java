package Lessons;
import Users.Instructor;

public class Lesson {
    private LessonType type;
    private boolean isPrivate;
    private String schedule;
    private Instructor instructor;

    public Lesson(LessonType type, boolean isPrivate, String schedule, Instructor instructor) {
        this.type = type;
        this.isPrivate = isPrivate;
        this.schedule = schedule;
        this.instructor = instructor;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

}
