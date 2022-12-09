package CS230;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for saving the current state of the program
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
     * method that saves current level the user is on
     * @param username
     * @param score
     */
    public void levelSave (String username,int level, int score) {
        try{
            FileWriter fI = new FileWriter("savestate.txt",true);
            fI.write(System.lineSeparator() + username +" "+ level + " " + score);
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
        int indexPosition = 0;
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
                    indexPosition = i;
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (this.userLevels.size() > 0) {
            this.startState.add(this.userLevels.get(indexPosition));
            this.startState.add(this.userScores.get(indexPosition));
        }
        else {
            this.startState.add(0);
            this.startState.add(0);
        }
        return startState;
    }

    /**
     *
     * method that creates a new Player
     * @param username - the user's unique identifier (idk if it's unique)
     * @return boolean, whether the Player can be created or not (?)
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
     * method that checks level (more detail ?)
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

