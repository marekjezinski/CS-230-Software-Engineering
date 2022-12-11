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
        updateFile();
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
        updateFile();
    }

    public int getScore(String username) {
        for (PlayerProfile playerProfile : playerProfiles) {
            if(playerProfile.getUsername().equals(username)) {
                return(playerProfile.getMaxScore());
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

