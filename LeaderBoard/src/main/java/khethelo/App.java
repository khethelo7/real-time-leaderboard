package khethelo;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Player player = new Player();

        Scanner scanner = new Scanner(System.in);
        String usrNm;
        String pswrd;

        System.out.println( "Welcome to The Leader Board.");
        System.out.println( "Please enter your username and password to login." );
        
        System.out.println("Username:");
        usrNm = scanner.nextLine();

        System.out.println("Password:");
        pswrd = scanner.nextLine();

        // Login Logic (if user exists in the database then login, else ask to create user)
        if (player.playerExists(usrNm)) {
            if (player.validatePassword(usrNm, pswrd)) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Incorrect password. Please try again.");
            }
        } else {
            System.out.println("User does not exist. Creating a new user...");
            Player newPlayer = new Player(usrNm, pswrd);
            System.out.println("User created successfully!");
        }

        scanner.close();
    }
}
