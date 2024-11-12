// Administrator.java
package User;

public class Administrator extends User {

    public Administrator(String name, String phoneNumber) {
        super(name, phoneNumber, "Administrator");
    }

    @Override
    public void performRoleSpecificActions() {
        System.out.println("Administrator Dashboard: Manage all users, offerings, and system settings.");
    }

    // Additional administrator-specific methods
    public void createOffering(String offeringName) {
        System.out.println("Creating offering: " + offeringName);
    }

    public void deleteAccount(User user) {
        System.out.println("Deleting user account: " + user.getName());
    }
}
