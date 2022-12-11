package CS230;

import CS230.saveload.PlayerProfile;
import CS230.saveload.SaveLoad;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for adding scores to the leaderboard as well
 * as obtaining the top 10 scores
 * @author Tom Stevens
 * @author Caleb Ocansey
 * @author Alex
 * @author Marek Jezinski
 * @version 1.0
 */
public class Leaderboard extends SaveLoad {
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<Integer> scores = new ArrayList<Integer>();

    /**
     * Method for adding scores to the text document
     * @param username the username of the player
     * @param score the score player had been gotten
     */
    public void addScore (String username, int score) {
        File f = new File("scores.txt");
        boolean top10score = false;
        boolean duplicate = false;
        int i= 0;
        while (!top10score && i < scores.size() ){
            int storedScore = scores.get(i);
            if( score > storedScore){
                top10score = true;
            }
            i++;
        }
        try  {
            Scanner in = new Scanner(f);
            while(in.hasNext() && !duplicate) {
                String name = in.next();
                int storedScore = in.nextInt();
                if (name.equals(username) && score <= storedScore){
                    duplicate = true;
                    i = names.indexOf(name);
                }
            }
        }catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
        }

        if(!duplicate) {
            names.add(username);
            scores.add(score);
            try {
                FileWriter fI = new FileWriter("scores.txt", true);
                fI.write(System.lineSeparator() + username + " " + score);
                fI.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

    }

    /**
     * method for printing out the top 10 scores
     */
    public void getTopScores(){
        File f = new File("scores.txt");
        try  {
            Scanner in = new Scanner(f);
            while(in.hasNext()) {
                this.names.add(in.next());
                this.scores.add(in.nextInt());
            }
            for(int i = 0; i < this.names.size(); i++){
                int score = 0;
                for(int j = 1; j < this.names.size(); j++){
                    if (this.names.get(i).equals(this.names.get(j))){
                        if (this.scores.get(i) >= score){
                            this.scores.remove(j);
                            this.names.remove(j);
                        }
                        /*else {
                            this.scores.remove(i);
                            this.names.remove(i);
                        }*/
                    }
                }
            }
           for (int i = 0; i < 10; i++) {
               if (this.scores.size() > 0) {
                   int topScore = 0;
                   String name = "";
                   int position = 0;
                   for (int j = 0; j < this.scores.size(); j++) {
                       if (this.scores.get(j) >= topScore) {
                           topScore = this.scores.get(j);
                           name = this.names.get(j);
                           position = j;
                       }
                   }
                   System.out.println(name + " " + topScore);
                   this.names.remove(position);
                   this.scores.remove(position);

               }
           }
                }


        catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

}
