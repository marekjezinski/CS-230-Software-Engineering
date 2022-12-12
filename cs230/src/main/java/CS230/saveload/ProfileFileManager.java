package CS230.saveload;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class for saving the current state of the program and seeing if
 * the player is new or not and what levels they have unlocked
 * @author Marek Jezinski
 * @author Tom Stevens
 * @version 1.0
 */

public class ProfileFileManager {
    private ArrayList<PlayerProfile> playerProfiles = new ArrayList<>();
    private int levelsNumber;

    /**
     * Accesses profiles file and sets array list of player profiles accordingly
     * @param levelsNumber maximal number of levels
     */
    public ProfileFileManager(int levelsNumber) {
        this.levelsNumber = levelsNumber;
        try{
            Scanner sc = new Scanner(new File("textfiles/profiles.txt"));
            while(sc.hasNext()) {
                String username = sc.next();
                int maxLevel = sc.nextInt();
                PlayerProfile pl = new PlayerProfile(username, levelsNumber);
                for(int i = 0; i < this.levelsNumber; i++) {
                    int x = sc.nextInt();
                    pl.setScore(i, x);
                }
                pl.setMaxLevel(maxLevel);
                playerProfiles.add(pl);
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            try {
                File f = new File("textfiles/profiles.txt");
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
     * method for updating the profile file by accessing player profile arraylist
     */
    private void updateFile() {
        File f = new File("textfiles/profiles.txt");
        if (f.exists()) {
            f.delete();
        }
        FileWriter wr = null;
        try {
            f.createNewFile();
            wr = new FileWriter(f);
            for (PlayerProfile playerProfile : playerProfiles) {
                String toWrite = playerProfile.getUsername();
                toWrite += " ";
                toWrite += playerProfile.getMaxLevel();
                for(int i = 0; i < this.levelsNumber; i++) {
                    toWrite += " ";
                    toWrite += playerProfile.getScore(i);
                }
                wr.write(toWrite);
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
     * Updates max score reached on given level
     * @param username username of the player
     * @param scoreToUpdate score to be updated
     * @param levelID id of the level
     */
    public void updateMaxScore(String username, int scoreToUpdate, int levelID) {
        for(int i = 0; i < playerProfiles.size(); i++) {
            if(playerProfiles.get(i).getUsername().equals(username)) {
                if(playerProfiles.get(i).getScore(levelID) < scoreToUpdate) {
                    playerProfiles.get(i).setScore(levelID, scoreToUpdate);
                }
            }
        }
        updateFile();
    }
    /**
     * method for checking if the username is vaild
     * @param username the username inputted
     * @return true if valid false otherwise
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
            playerProfiles.add(new PlayerProfile(username, levelsNumber));
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
            usernames.add(playerProfile.getUsername() + ": max level " + (playerProfile.getMaxLevel() + 1));
        }
        return(usernames);
    }
    /**
     * method for removing the profile
     * @param username the username of the player
     */
    public void removeProfile(String username) {
        System.out.println(username);
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
     * method for getting the Max level reached of the player
     * @param username the username of the player
     * @return max level reached
     */
    public int getMaxLvl(String username) {
        for (PlayerProfile playerProfile : playerProfiles) {
            if(playerProfile.getUsername().equals(username)) {
                return(playerProfile.getMaxLevel());
            }
        }
        return(-1);
    }

    /**
     * Returns string that consists of top 10 results on given level
     * @param levelID id of the level
     * @return string in readable format
     */
    public String getLeaderboardForLevelID(int levelID) {
        //creating copy of profiles to be sorted later
        ArrayList<PlayerProfile> profilesSortedByScore = new ArrayList<>();
        String leaderboard;
        for (PlayerProfile playerProfile : playerProfiles) {
            profilesSortedByScore.add(playerProfile);
        }

        //accessing private method explained below
        profilesSortedByScore = sortProfilesByScore(profilesSortedByScore, levelID);

        //limiting leaderboard to 10
        int numOfScores = profilesSortedByScore.size();
        if(numOfScores > 10) {
            numOfScores = 10;
        }

        //generating string
        leaderboard = "Leaderboard for level " + (levelID + 1) + System.lineSeparator();
        for(int i = 0; i < numOfScores; i++) {
            leaderboard += String.valueOf((i + 1) + ": " + profilesSortedByScore.get(i).getUsername() + " "
                    + profilesSortedByScore.get(i).getScore(levelID) + System.lineSeparator());
        }
        return leaderboard;
    }

    /**
     * Sorting player profile arraylist by scores descending, bubble sort
     * @param p arraylist of player profiles
     * @param levelID id of the level for which player profiles are going to be sorted
     * @return sorted arraylist
     */
    private ArrayList<PlayerProfile> sortProfilesByScore(ArrayList<PlayerProfile> p, int levelID) {
        for(int i = 0; i < p.size(); i++) {
            for(int j = 0; j < p.size() - 1; j++) {
                if(p.get(j).getScore(levelID) < p.get(j + 1).getScore(levelID)) {
                    PlayerProfile temp = p.get(j);
                    p.set(j, p.get(j+1));
                    p.set(j+1, temp);
                }
            }
        }
        return p;
    }
}

