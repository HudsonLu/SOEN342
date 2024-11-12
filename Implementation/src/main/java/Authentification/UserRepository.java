////UserRepository
//
//package Authentification;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class UserRepository {
//    private static final Map<String, String> users = new HashMap<>(); // username, password
//    private static final Map<String, String> roles = new HashMap<>(); // username, role
//
//    static {
//        // Pre-populated users for testing
//        users.put("admin", "admin123"); // Username: admin, Password: admin123
//        users.put("instructor1", "password1");
//        users.put("client1", "password2");
//
//        // Assign roles
//        roles.put("admin", "Administrator");
//        roles.put("instructor1", "Instructor");
//        roles.put("client1", "Client");
//    }
//
//    public static String getPassword(String username) {
//        return users.get(username);
//    }
//
//    public static String getRole(String username) {
//        return roles.get(username);
//    }
//}
