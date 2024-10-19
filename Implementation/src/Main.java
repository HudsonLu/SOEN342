import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Menu:");
            System.out.println("1. View Locations");
            System.out.println("2. View Lessons");
            System.out.println("3. View Offerings");
            System.out.println("4. Login");
            System.out.println("5. Sign up");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Viewing locations.");
                    break;
                case 2:
                    System.out.println("Viewing lessons.");
                    break;
                case 3:
                    System.out.println("Viewing offerings.");
                    break;
                case 4:
                    System.out.println("Logging in.");
                    break;
                case 5:
                    System.out.println("Signing up.");
                    break;
                case 6:
                    System.out.println("Exiting.");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }

        scanner.close();
        System.out.println("Program terminated.");
    }
}
