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

    public PlayerProfile(String username){
            this.username = username;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getMaxScore(){
            return this.score;
        }

    public int getMaxLevel(){
        return this.maxLevel;
    }

    public String getUsername() {
            return username;
        }


}
