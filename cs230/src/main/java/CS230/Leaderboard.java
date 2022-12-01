package CS230;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Leaderboard extends SaveLoad {
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<Integer> scores = new ArrayList<Integer>();

    public void addScore (String username, int score) {
        try{
            FileWriter fI = new FileWriter("scores.txt", true);
            fI.write(System.lineSeparator() + username +" "+ score);
            fI.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }


    private void addUser(){
        String username = null;
        //collect inputs in gui
        //upload to txt file
    }
    public String getAllScores(){
        String scores = "";
        File f = new File("scores.txt");
        try  {
            Scanner in = new Scanner(f);
            while(in.hasNextLine()){
                scores = scores + in.nextLine();
            }
        }
        catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        return scores;
    }

    public void getTopScores(){
        int topScore = 0;
        File f = new File("scores.txt");
        try  {
            Scanner in = new Scanner(f);
            while(in.hasNext()) {
                this.names.add(in.next());

                this.scores.add(in.nextInt());
            }
           for (int i = 0; i < 10; i++){
               System.out.println(this.names.get(i) + " " + this.scores.get(i));
           }
                }


        catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private Object showTop10Scores(){
        return null;
    }

    /*
    what else would a leaderboard need to do?

    figure out the best way to change top scores

    maybe store top 10 in some array or file
    then add the new score, sort and then remove the last score.
    ^ first compare new score with the lowest score on the list
     */
}
