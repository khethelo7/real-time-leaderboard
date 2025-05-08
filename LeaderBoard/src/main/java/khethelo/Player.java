package khethelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

import org.json.JSONObject;

public class Player {

    private String playerName;
    private String playerPassword;
    private int playerScore;

    public static HashMap<String, String> players = new HashMap<>();

    private static final String DATABASE_FILE = "players.json";

    Player() {
    }

    Player(String playerName, String playerPassword) {
        this.playerName = playerName;
        this.playerPassword = playerPassword;
        this.playerScore = 0;

        savePlayerToDatabase(playerName, playerPassword);
    }

    public String getName() {
        return playerName;
    }

    public int getScore() {
        return playerScore;
    }

    public boolean playerExists(String playerName) {
        JSONObject database = loadDatabase();
        return database.has(playerName);
    }

    public boolean validatePassword(String username, String password) {
        JSONObject database = loadDatabase();
        return database.has(username) && database.getString(username).equals(password);
    }


    // private boolean isValidPassword(String password, String username) {
    //     String savedPass =  playerExists(username) ? players.get(username) : null;
    //     return false;
    // }

    public void savePlayerToDatabase(String playerName, String playerPassword) {
        JSONObject database = loadDatabase();
        database.put(playerName, playerPassword);
        try {
            Files.write(Paths.get(DATABASE_FILE), database.toString(4).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error saving to database: " + e.getMessage());
        }
    }

    private JSONObject loadDatabase() {
        try {
            if (Files.exists(Paths.get(DATABASE_FILE))) {
                String content = new String(Files.readAllBytes(Paths.get(DATABASE_FILE)));
                return new JSONObject(content);
            }
        } catch (IOException e) {
            System.err.println("Error loading database: " + e.getMessage());
        }
        return new JSONObject(); // Return an empty database if file doesn't exist or an error occurs
    }
}
