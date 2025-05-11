package khethelo;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Scanner scanner = new Scanner(System.in);
    private static Player player = new Player();
    public static void main( String[] args )
    {
        App app = new App();
        app.login();
        app.run();
    }

    public void login() {
        String usrNm;
        String pswrd;
        display("Welcome to The Leader Board.");
        display("Please enter your username and password to login.");

        while (true) {
            display("Username:");
            usrNm = scanner.nextLine();

            display("Password:");
            pswrd = scanner.nextLine();

            // Login Logic
            if (player.playerExists(usrNm)) {
                if (player.validatePassword(usrNm, pswrd)) {
                    display("Login successful! Welcome, " + usrNm + "!");
                    break; // Exit the login loop
                } else {
                    display("Incorrect password. Please try again.");
                }
            } else {
                display("User does not exist. Would you like to create a new account? (yes/no)");
                String response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes") || response.equals("y")) {
                    player.savePlayerToDatabase(usrNm, pswrd); // Initialize with a score of 0
                    display("User created successfully! You can now log in.");
                } else {
                    display("Returning to login prompt...");
                }
            }
        }
    }

    public void run(){
        while (true) {
            display("\nChoose an option:\n1. View Leaderboard\n2. Submit Score\n3. Exit");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    player.displayLeaderboard();
                    break;
                case "2":
                    display("Submitting score...");
                    // Logic to submit score
                    break;
                case "3":
                    display("Exiting...");
                    return;
                default:
                    display("Invalid choice. Please try again.");
            }
        }
        // scanner.close();
    }

    private void display(String message) {
        System.out.println(message);
    }
}
