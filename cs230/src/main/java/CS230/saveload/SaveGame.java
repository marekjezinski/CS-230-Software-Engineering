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

public class SaveGame {
    public void update(Map m, int currentTimer, String username) {
        File f = new File(String.format("textfiles/saves/%s.txt", username));
        if (f.exists()) {
            f.delete();
        }
        FileWriter wr = null;
        try {
            f.createNewFile();
            wr = new FileWriter(f);
            Tile[][] tilesArray = m.getTilesArray();
            wr.write(String.valueOf(m.getLevelID()));
            wr.write(System.lineSeparator());
            wr.write(String.valueOf(m.getMAP_MAX_X()) + " " + String.valueOf(m.getMAP_MAX_Y()));
            wr.write(System.lineSeparator());
            for(int y = 0; y < tilesArray[0].length; y++) {
                for(int x = 0; x < tilesArray.length; x++) {
                    wr.write(tilesArray[x][y].getTileColours() + " ");
                }
                wr.write(System.lineSeparator());
            }
            wr.write("player " + m.getPlayerX() + " " + m.getPlayerY());
            wr.write(System.lineSeparator());
            wr.write("timer " + String.valueOf(currentTimer));
            for (Clock clock : m.getClocks()) {
                wr.write(System.lineSeparator());
                wr.write("clock " + clock.getX() + " " + clock.getY());
            }
            for (Loot loot : m.getLoots()) {
                wr.write(System.lineSeparator());
                wr.write(loot.getLootName() + " " + loot.getX() + " " + loot.getY());
            }
            for (Bomb bomb : m.getBombs()) {
                if(bomb.getX() != -1 || bomb.getY() != -1) {
                    wr.write(System.lineSeparator());
                    wr.write("bomb " + bomb.getX() + " " + bomb.getY());
                }
            }
            for (Thief thief : m.getThieves()) {
                if(thief.getX() != -1 || thief.getY() != -1) {
                    wr.write(System.lineSeparator());
                    wr.write("thief " + thief.getX() + " " + thief.getY() + " " +
                            String.valueOf(thief.getColour()) + " " + String.valueOf(thief.getDirection()));
                }
            }
            for (FlyingAssassin flyingAssassin : m.getFlyingAssassins()) {
                wr.write(System.lineSeparator());
                wr.write("flyingAssassin " + flyingAssassin.getX() + " " + flyingAssassin.getY() + " " +
                        String.valueOf(flyingAssassin.getDirection()));
            }
            wr.write(System.lineSeparator());
            wr.write("rlever " + m.getRLever().getX() + " " + m.getRLever().getY());
            wr.write(System.lineSeparator());
            wr.write("wlever " + m.getWLever().getX() + " " + m.getWLever().getY());
            wr.write(System.lineSeparator());
            wr.write("rgate " + m.getRGate().getX() + " " + m.getRGate().getY());
            wr.write(System.lineSeparator());
            wr.write("wgate " + m.getWGate().getX() + " " + m.getWGate().getY());
            wr.write(System.lineSeparator());
            wr.write("door " + m.getDoor().getX() + " " + m.getDoor().getY());
            wr.write(System.lineSeparator());
            wr.write("score " + String.valueOf(m.getScore()));
            wr.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean doesUserSaveExists(String username) {
        File f = new File(String.format("textfiles/saves/%s.txt", username));
        if (f.exists()) {
            return true;
        }
        return false;
    }
}
