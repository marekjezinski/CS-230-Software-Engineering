package CS230.saveload;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class for saving the current state of the program and seeing if
 * the player is new or not and what levels they have unlocked
 * @author Marek Jezinski
 * @author Tom Stevens
 * @version 1.0
 */
public class FileHandler {
    private ArrayList<PlayerProfile> playerProfiles = new ArrayList<>();

    public FileHandler() {
        try{
            Scanner sc = new Scanner(new File("profiles.txt"));
            while(sc.hasNext()) {
                String username = sc.next();
                int maxLevel = sc.nextInt();
                int score = sc.nextInt();
                PlayerProfile pl = new PlayerProfile(username);
                pl.setMaxLevel(maxLevel);
                pl.setScore(score);
                playerProfiles.add(pl);
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            try {
                File f = new File("profiles.txt");
                f.createNewFile();
            } catch (IOException err) {
                err.printStackTrace();
                System.exit(1);
            }
        }
        catch (InputMismatchException err) {
            System.err.println("Check profiles.txt!");
            err.printStackTrace();
            System.exit(1);
        }
    }

    public void updateSave(String username, int maxLvl, int score) {
        ArrayList<String> line = new ArrayList<>();
        try{
            Scanner sc = new Scanner(new File("profiles.txt"));
            while(sc.hasNext()) {
                line.add(sc.nextLine());
            }
            sc.close();
        }
        catch (FileNotFoundException e) {

        }
        try {
            File f = new File("profiles.txt");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            FileWriter wr = new FileWriter(f);
            line = modifyProfileFile(line, username, maxLvl, score);
            for (String s : line) {
                wr.write(s);
                wr.write(System.lineSeparator());
            }
            wr.close();

        } catch (IOException err) {

        }

    }

    private ArrayList<String> modifyProfileFile(ArrayList<String> lines, String username2, int maxLvl2, int score2) {
        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Scanner sc = new Scanner(line);
            String username = sc.next();
            int maxLvl = sc.nextInt();
            int score = sc.nextInt();
            sc.close();
            if(username.equals(username2)) {
                maxLvl = maxLvl2;
                score = score2;
            }
            line = username + " " + maxLvl + " " + score;
            lines.set(i, line);
        }
        return(lines);
    }

    public boolean isValidName(String username) {
        for (PlayerProfile playerProfile : playerProfiles) {
            if(playerProfile.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    //public

    public void addProfile(String username) {
        boolean exists = false;
        for (PlayerProfile playerProfile : playerProfiles) {
            if(playerProfile.getUsername().equals(username)) {
                exists = true;
            }
        }
        if(!exists) {
            playerProfiles.add(new PlayerProfile(username));
        }
    }

    public ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("Profiles:");
        for (PlayerProfile playerProfile : playerProfiles) {
            usernames.add(playerProfile.getUsername() + ": max lvl " + playerProfile.getMaxLevel());
        }
        return(usernames);
    }

    public void removeProfile(String username) {
        int index = -1;
        for(int i = 0; i < playerProfiles.size(); i++) {
            if(playerProfiles.get(i).getUsername().equals(username)) {
                index = i;
            }
        }
        if(index != -1) {
            playerProfiles.remove(index);
        }
    }

    public int getScore(String username) {
        for (PlayerProfile playerProfile : playerProfiles) {
            if(playerProfile.getUsername().equals(username)) {
                return(playerProfile.getScore());
            }
        }
        return(-1);
    }

    public int getMaxLvl(String username) {
        for (PlayerProfile playerProfile : playerProfiles) {
            if(playerProfile.getUsername().equals(username)) {
                return(playerProfile.getMaxLevel());
            }
        }
        return(-1);
    }
}

