package Lessons;

public class Offering {
    private String lessonName;
    private String instructorName;
    private String schedule;
    private String location;

    public Offering(String lessonName, String instructorName, String schedule, String location) {
        this.lessonName = lessonName;
        this.instructorName = instructorName;
        this.schedule = schedule;
        this.location = location;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Offering{" +
                "lessonName='" + lessonName + '\'' +
                ", instructorName='" + instructorName + '\'' +
                ", schedule='" + schedule + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
