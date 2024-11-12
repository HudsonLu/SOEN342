//public class LoginService {
//    public static boolean authenticate(String username, String password) {
//        String storedPassword = UserRepository.getPassword(username);
//        return storedPassword != null && storedPassword.equals(password);
//    }
//
//    public static User getUserByRole(String username) {
//        String role = UserRepository.getRole(username);
//
//        switch (role) {
//            case "Administrator":
//                return new Administrator("Admin Name", "Admin Phone");
//            case "Instructor":
//                return new Instructor("Instructor Name", "Instructor Phone", "Specialization");
//            case "Client":
//                return new Client("Client Name", "Client Phone");
//            default:
//                return null;
//        }
//    }
//}
