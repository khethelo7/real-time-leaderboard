package khethelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        if (database.has(username)) {
            JSONObject playerData = database.getJSONObject(username);
            return playerData.getString("password").equals(password);
        }
        return false;
    }


    // private boolean isValidPassword(String password, String username) {
    //     String savedPass =  playerExists(username) ? players.get(username) : null;
    //     return false;
    // }

    public void savePlayerToDatabase(String playerName, String playerPassword) {
        JSONObject database = loadDatabase();
        JSONObject playerData = new JSONObject();
        playerData.put("password", playerPassword);
        playerData.put("score", playerScore);
        database.put(playerName, playerData);
        try {
            Files.write(Paths.get(DATABASE_FILE), database.toString(4).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error saving to database: " + e.getMessage());
        }
    }

    public void updatePlayerScore(String playerName, int newScore) {
        JSONObject database = loadDatabase();
        if (database.has(playerName)) {
            JSONObject playerData = database.getJSONObject(playerName);
            playerData.put("score", newScore);
            database.put(playerName, playerData);
            try {
                Files.write(Paths.get(DATABASE_FILE), database.toString(4).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                System.err.println("Error updating score in database: " + e.getMessage());
            }
        } else {
            System.err.println("Player does not exist in the database.");
        }
    }

    public Map<String, Integer> fetchScores() {
        JSONObject database = loadDatabase();
        return database.keySet().stream()
                .collect(Collectors.toMap(
                        playerName -> playerName,
                        playerName -> database.getJSONObject(playerName).getInt("score")
                ));
    }

    public void displayLeaderboard() {
        Map<String, Integer> scores = fetchScores();
        System.out.println("Leaderboard:");
        scores.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " points"));
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
        return new JSONObject();
    }
}
