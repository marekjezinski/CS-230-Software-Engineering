package CS230;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for saving the current state of the program and seeing if
 * the player is new or not and what levels they have unlocked
 * @author Tom Stevens
 * @version 1.0
 */
public class CurrentState {
    private ArrayList<String> usernames = new ArrayList<String>();
    private ArrayList<Integer> levels = new ArrayList<Integer>();
    private ArrayList<Integer> userLevels = new ArrayList<Integer>();
    private ArrayList<Integer> scores = new ArrayList<Integer>();
    private ArrayList<Integer> userScores = new ArrayList<Integer>();

    private ArrayList<Integer> startState =  new ArrayList<Integer>();

    /**
     * method that saves current level the user is on and all the
     * relevant details with it
     * @param username
     * @param score
     */
    public void levelSave (String username,int level, int score, int playerX, int playerY, int timerLeft) {
        try{
            FileWriter fI = new FileWriter("savestate.txt",true);
            fI.write(System.lineSeparator() + username +" "+ level
                    + " " + score + " " + playerX + " " + playerY + " " + timerLeft);
            fI.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Method that loads most recent level user was on
     * @param username
     * @return startState
     */
    public ArrayList<Integer> levelLoad (String username) {
        int level = 0;
        int timer = 0;
        int score = 0;
        int playerX = 0;
        int playerY = 0;

        File f = new File("savestate.txt");
        try  {
            Scanner in = new Scanner(f);
            while(in.hasNext()) {
                if (in.next().equals(username)){
                    level = in.nextInt();
                    score = in.nextInt();
                    playerX = in.nextInt();
                    playerY = in.nextInt();
                    timer = in.nextInt();
                }
            }
            startState.add(level);
            startState.add(score);
            startState.add(playerX);
            startState.add(playerY);
            startState.add(timer);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return startState;
    }

    /**
     *
     * method that creates a new Player
     * @param username
     * @return boolean
     */
    public boolean newPlayer(String username){
        int level = 0;
        int indexPosition = 0;
        File f = new File("savestate.txt");
        try  {
            Scanner in = new Scanner(f);
            while(in.hasNext()) {
                this.usernames.add(in.next());
                this.levels.add(in.nextInt());
                this.scores.add(in.nextInt());
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
       for (int i = 0; i < usernames.size(); i++) {
           if (username.equals(this.usernames.get(i))){
               return true;
           }
       }
        return false;
    }

    /**
     * method that checks what levels
     * are unlocked to the user
     * @param username
     * @return level
     */
    public int checkLevel (String username) {
        int level = 0;
        File f = new File("savestate.txt");
        try  {
            Scanner in = new Scanner(f);
            while(in.hasNext()) {
                this.usernames.add(in.next());
                this.levels.add(in.nextInt());
                this.scores.add(in.nextInt());
            }
            for (int i = 0; i < usernames.size(); i++){
                if (username.equals(this.usernames.get(i))){
                    this.userLevels.add(this.levels.get(i));
                    this.userScores.add(this.scores.get(i));
                }
            }
            for (int i = 0; i < this.userLevels.size(); i++){
                if (this.userLevels.get(i) > level){
                    level = this.userLevels.get(i);

                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return level;
    }

    }

