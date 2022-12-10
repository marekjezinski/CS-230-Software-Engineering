package CS230;
import java.util.ArrayList;

/**
 * Class that reads the current Player's status from saved files
 * and handle player input to move or make changes
 * @author Caleb Ocansey
 * @author Alex-Carmen Macoveanu
 * @version 1.0
 */
public class PlayerProfile {
    private String username;
    private int score;
    private int maxLevel;

    public PlayerProfile(String username){
            this.username = username;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getScore(){
            return this.score;
        }

    public int getMaxLevel(){
        return this.maxLevel + 1; // so levels can start from 1 instead of 0
    }

    public String getUsername() {
            return username;
        }

    public PlayerProfile createProfile(ArrayList<PlayerProfile> playersProfiles, PlayerProfile player) {
        // checking if the profile is already in the arraylist for players, if not: create one
        if (playersProfiles.contains(player)) {
            return player;
        } else {
            PlayerProfile newPlayer = new PlayerProfile(this.username);
            playersProfiles.add(newPlayer);
            return newPlayer;
        }
    }

    public ArrayList<PlayerProfile> deleteProfile(ArrayList<PlayerProfile> playersProfiles, PlayerProfile player){
        if (playersProfiles.contains(player)) {
            playersProfiles.remove(player);
            return playersProfiles;
        } else {
            return playersProfiles;
        }
    }


}
