package CS230.saveload;

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
    private ArrayList<Integer> maxScores = new ArrayList<>();
    private int maxLevel;

    public PlayerProfile(String username, int levelsNumber){
        this.username = username;
        for(int i = 0; i < levelsNumber; i++) {
            maxScores.add(0);
        }
    }

    /**
     * method that sets the Player's max level
     * @param maxLevel the highest level player had passed
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void addScore(int score) {
        maxScores.add(score);
    }
    public void setUsername(String username){
        this.username = username;
    }

    public int getScore(int levelID) {
        return(maxScores.get(levelID));
    }

    public void setScore(int levelID, int score) {
        maxScores.set(levelID, score);
    }

    /**
     * method that gets the max level
     * @return maxLevel the max level avaliable
     */
    public int getMaxLevel(){
        return this.maxLevel;
    }

    /**
     * method that gets the Player's username
     * @return username the player's username
     */
    public String getUsername() {
            return username;
        }

}
