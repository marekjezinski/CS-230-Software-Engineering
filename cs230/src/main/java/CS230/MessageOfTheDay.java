package CS230;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

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
        }

        this.breakdown = this.puzzle.split("");

        for(int i = 0; i < this.breakdown.length; i++) {
            for (int j = 0; j < this.alphabet.length; j++){
                if (this.breakdown[i].equals(this.alphabet[j])){
                    if ((i + 1) % 2 != 0){
                        j = j - i - 1;
                    }
                    else{
                        j = j + i + 1;
                    }

                    if (j <0){
                        j = 25;
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