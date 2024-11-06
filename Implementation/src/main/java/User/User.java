package User;

public abstract class User {

    private static long lastUserId = 0; // Static variable to keep track of the last assigned ID
    private long user_id;
    private String name;
    private String phoneNumber;

    public User(String name, String phoneNumber) {
        this.user_id = ++lastUserId; // Increment and assign to user_id
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getUser_id() {
        return user_id;
    }

    // Remove or restrict setUser_id to avoid changing the ID after assignment
    public void setUser_id(long user_id) {
        throw new UnsupportedOperationException("User ID is automatically generated and cannot be set manually.");
    }
}
