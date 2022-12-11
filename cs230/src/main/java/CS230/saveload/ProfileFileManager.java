package CS230.saveload;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
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

public class ProfileFileManager {
    private ArrayList<PlayerProfile> playerProfiles = new ArrayList<>();

    /**
     * method for managing the profiles saved on a txt file
     */
    public ProfileFileManager() {
        try{
            Scanner sc = new Scanner(new File("profiles.txt"));
            while(sc.hasNext()) {
                String username = sc.next();
                int maxLevel = sc.nextInt();
                int maxScore = sc.nextInt();
                PlayerProfile pl = new PlayerProfile(username);
                pl.setMaxLevel(maxLevel);
                pl.setScore(maxScore);
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
    /**
     * method for updating the profile file
     */

    private void updateFile() {
        File f = new File("profiles.txt");
        if (f.exists()) {
            f.delete();
        }
        FileWriter wr = null;
        try {
            f.createNewFile();
            wr = new FileWriter(f);
            for (PlayerProfile playerProfile : playerProfiles) {
                wr.write(playerProfile.getUsername() + " " + playerProfile.getMaxLevel() + " " +
                        playerProfile.getMaxScore());
                wr.write(System.lineSeparator());
            }
            wr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    /**
     * method for updating the max level player had reached
     * @param username the username of the player
     * @param maxLvl the max level of the player
     */
    public void updateMaxLvl(String username, int maxLvl) {
        for(int i = 0; i < playerProfiles.size(); i++) {
            if(playerProfiles.get(i).getUsername().equals(username)) {
                if(playerProfiles.get(i).getMaxLevel() < maxLvl) {
                    playerProfiles.get(i).setMaxLevel(maxLvl);
                }
            }
        }
        updateFile();
    }
    /**
     * method for updating the max score player had gained
     * @param username the username of the player
     * @param maxScore the maximum score of the username.
     */
    public void updateMaxScore(String username, int maxScore) {
        for(int i = 0; i < playerProfiles.size(); i++) {
            if(playerProfiles.get(i).getUsername().equals(username)) {
                if(playerProfiles.get(i).getMaxScore() < maxScore) {
                    playerProfiles.get(i).setScore(maxScore);
                }
            }
        }
        updateFile();
    }
    /**
     * method for checking the username is vaild
     * @param username the username inputted
     */
    public boolean isValidName(String username) {
        for (PlayerProfile playerProfile : playerProfiles) {
            if(playerProfile.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    //public
    /**
     * method for adding a new profile to the txt
     * @param username the username of the player
     */
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
        updateFile();
    }
    /**
     * method for getting usernames
     */
    public ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("Profiles:");
        for (PlayerProfile playerProfile : playerProfiles) {
            usernames.add(playerProfile.getUsername() + ": max lvl " + playerProfile.getMaxLevel());
        }
        return(usernames);
    }
    /**
     * method for removing the profile
     * @param username the username of the player
     */
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
        updateFile();
    }
    /**
     * method for getting the score of player
     * @param username the username of the player
     */
    public int getScore(String username) {
        for (PlayerProfile playerProfile : playerProfiles) {
            if(playerProfile.getUsername().equals(username)) {
                return(playerProfile.getMaxScore());
            }
        }
        return(-1);
    }
    /**
     * method for getting the Max level of the player
     * @param username the username of the player
     */
    public int getMaxLvl(String username) {
        for (PlayerProfile playerProfile : playerProfiles) {
            if(playerProfile.getUsername().equals(username)) {
                return(playerProfile.getMaxLevel());
            }
        }
        return(-1);
    }
}

