package CS230.saveload;

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

    /**
     * Constructor that creates a Player Profile
     * @param username - the user's unique name
     */
    public PlayerProfile(String username){
            this.username = username;
    }

    /**
     * method that sets the Player's max level
     * @param maxLevel
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * method that sets the Player's score
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * method that sets the Player's unique username
     * @param username
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * method that gets the max score
     * @return score
     */
    public int getMaxScore(){
            return this.score;
        }

    /**
     * method that gets the max level
      * @return maxLevel
     */
    public int getMaxLevel(){
        return this.maxLevel;
    }

    /**
     * method that gets the Player's username
     * @return username
     */
    public String getUsername() {
            return username;
        }


}
