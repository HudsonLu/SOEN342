package User;

public abstract class User {
    private String name;
    private String phoneNumber;
    private String role;

    public User(String name, String phoneNumber, String role) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    // Credential validation (for simplicity in this example)
    public boolean validateCredentials(String inputName, String inputPhoneNumber) {
        return this.name.equals(inputName) && this.phoneNumber.equals(inputPhoneNumber);
    }

    // Abstract method for role-specific actions
    public abstract void performRoleSpecificActions();
}
