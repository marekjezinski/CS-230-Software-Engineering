package CS230;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Class for finding the message of the day
 * @author Tom Stevens
 * @author Kam Leung
 */
public class MessageOfTheDay {
    private String puzzle;
    private String message;
    private String[] breakdown;
    private ArrayList<String> breakdown2 = new ArrayList<String>();
    private ArrayList<String> alphabet = new ArrayList<>();


    public MessageOfTheDay() {

    }

    /**
     * First part of obtaining message via the api where it breaks it down
     * into an array
     * @return
     * @throws IOException
     */

    public String getMessage() throws IOException {
        boolean found;
        int b = 0;
        this.alphabet.addAll(Arrays.asList("A","B","C","D","E","F","G"
                ,"H","I","J","K","L","M","N","O","P","Q","R",
                "S","T","U","V","W","X","Y","Z"));
        String together = "";
        URL url = new URL("http://cswebcat.swansea.ac.uk/puzzle");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        for(Scanner in = new Scanner(con.getInputStream());
            in.hasNextLine(); this.puzzle = in.nextLine()) {
        }
        this.breakdown = this.puzzle.split("");
        for (int i = 0; i < this.breakdown.length; i++){
            this.breakdown2.add(this.breakdown[i]);
        }

        for(int i = 0; i < this.breakdown2.size(); i++) {
            found = false;
            for (int j = 0; j < this.alphabet.size(); j++){
                if (found == false){
                    if (this.breakdown2.get(i).equals(this.alphabet.get(j))) {
                        int a = 0;
                        boolean first = true;
                        b = j;
                        while (a <= i || first == true) {
                            first = false;
                            if ((i + 1) % 2 == 0) {
                                if (b + 1 > 25) {
                                    b = 0;
                                    a++;
                                } else {
                                    b++;
                                    a++;
                                }
                            } else {
                                if (b - 1 < 0) {
                                    b = 25;
                                    a++;
                                } else {
                                    b--;
                                    a++;
                                }
                            }
                        }
                        this.breakdown2.set(i,this.alphabet.get(b));
                        found = true;
                    }
                }

            }


        }


        for(int i = 0; i < this.breakdown2.size();i++){
            together = together + this.breakdown2.get(i);
        }

        this.message = this.urlMessage(together);
        return this.message;
    }

    /**
     * Makes the api call for the quote
     * @param together
     * @return returnMessage
     * @throws IOException
     */
    public String urlMessage(String together) throws IOException {
        String returnMessage = "";
        together = together + "CS-230";
        URL url = new URL("http://cswebcat.swansea.ac.uk/message?solution="
                + together.length() + together);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        Scanner in = new Scanner(con.getInputStream());
        while(in.hasNextLine()){
            returnMessage = returnMessage + in.nextLine();
        }
        return returnMessage;
    }
}