package CS230;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 * not sure if i'm correct, not finished
 * Class that displays on screen a message of the day
 * @author
 * @version 1.0
 */
public class MessageOfTheDay {
    private String puzzle;
    private String message;
    private String[] breakdown;
    private String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","L","K","M","N","O","P","Q","R",
            "S","T","U","V","W","X","Y","Z"};


    public MessageOfTheDay() {
    }

    public String getMessage() throws IOException {
        String together = "";
        URL url = new URL("http://cswebcat.swansea.ac.uk/puzzle");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");

        for(Scanner in = new Scanner(con.getInputStream());
            in.hasNextLine(); this.puzzle = in.nextLine()) {
            getMessage();
        }

        this.breakdown = this.puzzle.split("");

        for(int i = 0; i < this.breakdown.length; i++) {
            for (int j = 0; j < this.alphabet.length; j++){
                int a = i;
                if (this.breakdown[i].equals(this.alphabet[j]) && a>1){
                    if ((i + 1) % 2 != 0){
                        j = j - 1;
                        while (a > 0){
                            j = j - 1;
                            a = a - 1;
                            if (j == -1) {
                                j = 25;
                            }
                        }

                    }
                    else{
                        j = j + 1;
                        while (a > 0){
                            j = j + 1;
                            a = a - 1;
                            if (j == 27) {
                                j = 0;
                            }
                        };
                    }

                    this.breakdown[i] = this.alphabet[j];
                }

            }
        }
        for(int i = 0; i < this.breakdown.length;i++){
            together = together + this.breakdown[i];
        }

        this.message = this.urlMessage(together);
        return this.message;
    }

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