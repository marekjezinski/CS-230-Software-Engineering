package CS230;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MessageOfTheDay {
    private String puzzle;
    private String message;
    private String[] breakdown;
    private ArrayList<String> breakdown2 = new ArrayList<String>();
    private ArrayList<String> alphabet = new ArrayList<>();


    public MessageOfTheDay() {
    }

    public String getMessage() throws IOException {
        boolean found;
        int b = 0;
        this.alphabet.addAll(Arrays.asList("A","B","C","D","E","F","G","H","I","J","L","K","M","N","O","P","Q","R",
                "S","T","U","V","W","X","Y","Z"));
        String together = "";
        URL url = new URL("http://cswebcat.swansea.ac.uk/puzzle");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        for(Scanner in = new Scanner(con.getInputStream());
            in.hasNextLine(); this.puzzle = in.nextLine()) {
        }
        this.breakdown = this.puzzle.split("");
        for (int i = 0; i < this.breakdown.length-1; i++){
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
                        System.out.print(this.alphabet.get(b));
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
                        System.out.println(","+this.alphabet.get(b));
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