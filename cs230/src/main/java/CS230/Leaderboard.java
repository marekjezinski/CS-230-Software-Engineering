package CS230;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Leaderboard extends SaveLoad {

    public void addScore (String username, int score) {
        try{
            FileWriter f = new FileWriter("scores.txt", true);
            f.write(username + " " + score + System.lineSeparator());
            f.close();
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

    public String getTopScores(){
        String scores = "";
        File f = new File("scores.txt");
        try  {
            Scanner in = new Scanner(f);
            while(in.hasNextLine()){
                System.out.println(in.nextLine());
            }
        }
        catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        return scores;
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
