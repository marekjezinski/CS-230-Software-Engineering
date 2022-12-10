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
public class FileHandler {
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
    public void levelSave (String username,int level, int score,
                           int playerX, int playerY, int timerLeft,
                           int rgate, int wgate, int rlever, int wlever,
                           String bombInMap) {
        try{
            FileWriter fI = new FileWriter("savestate.txt",true);
            fI.write(System.lineSeparator() + username +" "+ level
                    + " " + score + " " + playerX + " " + playerY + " "
                    + timerLeft + " " + rgate + " " + wgate
                    + " " + rlever + " " + wlever); //  + " " + bombInMap + "end");
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
        int rgate = 0;
        int wgate = 0;
        int rlever = 0;
        int wlever = 0;
        //ArrayList<Integer> bombCoords = new ArrayList<Integer>();

        File f = new File("savestate.txt");
        try  {
            Scanner in = new Scanner(f);
            while(in.hasNext()) {
                if (in.next().equals(username)){
                    //bombCoords.clear();
                    level = in.nextInt();
                    score = in.nextInt();
                    playerX = in.nextInt();
                    playerY = in.nextInt();
                    timer = in.nextInt();
                    rgate = in.nextInt();
                    wgate = in.nextInt();
                    rlever = in.nextInt();
                    wlever = in.nextInt();
                    /*boolean bombTest = false;
                    while (bombTest == false){
                        if (in.next() == "end"){
                            bombTest = true;
                        }
                        else {
                            int bombCoordIn = in.nextInt();
                            System.out.println(bombCoordIn);
                            bombCoords.add(bombCoordIn);
                        }
                    }*/
                }
            }
            startState.add(level);
            startState.add(score);
            startState.add(playerX);
            startState.add(playerY);
            startState.add(timer);
            startState.add(rgate);
            startState.add(wgate);
            startState.add(rlever);
            startState.add(wlever);
            /*for (int i = 0; i < bombCoords.size(); i++){
                startState.add(bombCoords.get(i));
            }*/


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
        ArrayList<String> usernameCheck = new ArrayList<String>();
        File f = new File("savestate.txt");
        try  {
            Scanner in = new Scanner(f);
            while(in.hasNext()) {
                usernameCheck.add(in.next());
                in.next();
                in.next();
                in.next();
                in.next();
                in.next();
                in.next();
                in.next();
                in.next();
                in.next();

            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
       for (int i = 0; i < usernameCheck.size(); i++) {
           if (username.equals(usernameCheck.get(i))){
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
                in.next();
                in.next();
                in.next();
                in.next();
                in.next();
                in.next();
                in.next();
                in.next();


            }
            for (int i = 0; i < this.usernames.size(); i++){
                if (username.equals(this.usernames.get(i))){
                    this.userLevels.add(this.levels.get(i));
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

