package CS230.saveload;

import CS230.map.Map;
import CS230.map.Tile;
import CS230.items.Bomb;
import CS230.items.Clock;
import CS230.items.Loot;
import CS230.npc.FlyingAssassin;
import CS230.npc.Thief;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class saves current player progress and position of all npcs and items
 * @author Marek Jezinski
 */
public class SaveGame {

    /**
     * Updates save file in format readable by MapReader (that is, specified in A2 specification)
     * @param m current map that consists of all details regarding the game
     * @param username name of the user
     */
    public void update(Map m, String username) {
        //specifying directory of user save
        File f = new File(String.format("textfiles/saves/%s.txt", username));
        //if file exists, delete as no longer relevant
        if (f.exists()) {
            f.delete();
        }
        FileWriter wr;
        try {
            //creating new file
            f.createNewFile();
            wr = new FileWriter(f);
            //importing tiles array from map
            Tile[][] tilesArray = m.getTILES_ARRAY();
            wr.write(String.valueOf(m.getLEVEL_ID()));
            wr.write(System.lineSeparator());
            //writing max X and max Y coordinates
            wr.write(String.valueOf(m.getMAP_MAX_X()) + " " + String.valueOf(m.getMAP_MAX_Y()));
            wr.write(System.lineSeparator());
            //printing tiles array the same way as in specification, in order to avoid the need of reimplementing
            //map import
            for(int y = 0; y < tilesArray[0].length; y++) {
                for(int x = 0; x < tilesArray.length; x++) {
                    wr.write(tilesArray[x][y].getTileColours() + " ");
                }
                wr.write(System.lineSeparator());
            }
            //print current player coordinates
            wr.write("player " + m.getPlayerX() + " " + m.getPlayerY());
            wr.write(System.lineSeparator());
            //print time left to complete level
            wr.write("timer " + String.valueOf(m.getTimerLeft()));
            //print clocks
            for (Clock clock : m.getClocks()) {
                wr.write(System.lineSeparator());
                wr.write("clock " + clock.getX() + " " + clock.getY());
            }
            //print loots
            for (Loot loot : m.getLoots()) {
                wr.write(System.lineSeparator());
                wr.write(loot.getLootName() + " " + loot.getX() + " " + loot.getY());
            }
            //print bombs
            for (Bomb bomb : m.getBombs()) {
                //if bomb is placed outside map then no need to print
                if(bomb.getX() < 0 || bomb.getY() < 0) {
                    wr.write(System.lineSeparator());
                    wr.write("bomb " + bomb.getX() + " " + bomb.getY());
                }
            }
            for (Thief thief : m.getThieves()) {
                //if thief outside the map then no need to pring
                if(thief.getX() < 0 || thief.getY() < 0) {
                    wr.write(System.lineSeparator());
                    wr.write("thief " + thief.getX() + " " + thief.getY() + " " +
                            String.valueOf(thief.getColour()) + " " + String.valueOf(thief.getDirection()));
                }
            }
            //print flying assassins
            for (FlyingAssassin flyingAssassin : m.getFlyingAssassins()) {
                wr.write(System.lineSeparator());
                wr.write("flyingAssassin " + flyingAssassin.getX() + " " + flyingAssassin.getY() + " " +
                        String.valueOf(flyingAssassin.getDirection()));
            }
            //print levers and gates
            wr.write(System.lineSeparator());
            wr.write("rlever " + m.getRLever().getX() + " " + m.getRLever().getY());
            wr.write(System.lineSeparator());
            wr.write("wlever " + m.getWLever().getX() + " " + m.getWLever().getY());
            wr.write(System.lineSeparator());
            wr.write("rgate " + m.getRGate().getX() + " " + m.getRGate().getY());
            wr.write(System.lineSeparator());
            wr.write("wgate " + m.getWGate().getX() + " " + m.getWGate().getY());
            wr.write(System.lineSeparator());
            //print door
            wr.write("door " + m.getDoor().getX() + " " + m.getDoor().getY());
            wr.write(System.lineSeparator());
            //print current score
            wr.write("score " + String.valueOf(m.getScore()));
            //close
            wr.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Determines whether user save exists
     * @param username name of the user
     * @return true if file exists false otherwise
     */
    public boolean doesUserSaveExists(String username) {
        File f = new File(String.format("textfiles/saves/%s.txt", username));
        return f.exists();
    }
}
