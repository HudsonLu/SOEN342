// Instructor.java
package User;
import java.util.List;
public class Instructor extends User {

    private String specialization;
    private List<String> availableCities;

    public Instructor(String name, String phoneNumber, String specialization, List<String> availableCities) {
        super(name, phoneNumber);
        this.specialization = specialization;
        this.availableCities = availableCities;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<String> getAvailableCities() {
        return availableCities;
    }

    public void setAvailableCities(List<String> availableCities) {
        this.availableCities = availableCities;
    }
}
