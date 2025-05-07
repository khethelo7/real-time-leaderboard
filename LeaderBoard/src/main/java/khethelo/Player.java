package khethelo;

import java.util.HashMap;

public class Player {

    private String playerName;
    private String playerPassword;
    private int playerScore;

    public static HashMap<String, String> players = new HashMap<>();

    Player() {
    }

    Player(String playerName, String playerPassword) {
        this.playerName = playerName;
        this.playerPassword = playerPassword;
        this.playerScore = 0;

        players.put(playerName, playerPassword);
    }

    public String getName() {
        return playerName;
    }

    public int getScore() {
        return playerScore;
    }

    public boolean playerExists(String playerName) {
        return players.containsKey(playerName);
    }

    public boolean validatePassword(String username, String password) {
        return players.containsKey(username) && players.get(username).equals(password);
    }


    // private boolean isValidPassword(String password, String username) {
    //     String savedPass =  playerExists(username) ? players.get(username) : null;
    //     return false;
    // }
}
