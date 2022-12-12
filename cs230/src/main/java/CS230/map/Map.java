package CS230.map;

import CS230.items.*;
import CS230.npc.FlyingAssassin;
import CS230.npc.Thief;
import java.util.ArrayList;

/**
 * Class that holds the Map where the player will play the game
 * @author Marek Jezinski
 * @author Tom Stevens
 * @author Wiktoria
 * @author Kam Leung
 * @version 1.0
 */

public class Map {
    private int playerX;
    private int playerY;
    private int timerLeft;
    private final int LEVEL_ID;
    private final int MAP_MAX_X;
    private final int MAP_MAX_Y;
    private final Tile[][] TILES_ARRAY; //contains every tile (that is tile with 4 cells)
    private ArrayList<Clock> clocks;
    private Door door;
    private ArrayList<Loot> loots;
    private ArrayList<FlyingAssassin> flyingAssassins;
    private ArrayList<Thief> thieves;
    private Gate rgate;
    private Lever rlever;
    private Gate wgate;
    private Lever wlever;
    private int starttime;
    public int lootleft;
    private int score;
    private ArrayList<Bomb> bombs;
    /**
     * creates a Map object from a text file
     * @param fileName - text file that specify how the Map will be laid out
     */
    public Map(String fileName) {
        //declaring null mapread to import attributes
        MapReader mapRead = new MapReader(fileName);
        this.MAP_MAX_X = mapRead.getMaxTileX();
        this.MAP_MAX_Y = mapRead.getMaxTileY();
        this.TILES_ARRAY = mapRead.getTiles();
        this.timerLeft = mapRead.getTimer();
        this.clocks = mapRead.getClocks();
        this.door = mapRead.getDoor();
        this.loots = mapRead.getLoot();
        this.rgate = mapRead.getRGate();
        this.rlever = mapRead.getRLever();
        this.wgate = mapRead.getWGate();
        this.wlever = mapRead.getWLever();
        this.bombs = mapRead.getBomb();
        this.playerX = mapRead.getPlayerStartX();
        this.playerY = mapRead.getPlayerStartY();
        this.starttime = mapRead.getStarttimer();
        this.flyingAssassins = mapRead.getFlyingAssassins();
        this.thieves = mapRead.getThieves();
        this.LEVEL_ID = mapRead.getLevelID();
        this.score = mapRead.getScore();
    }
    /**
     * method that moves the player to the right
     * @param playerX - player's x coordinate
     * @param playerY - player's y coordinate
     * @return player's x coordinate * 2
     */
    public int moveRight(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = TILES_ARRAY[playerX][playerY];
        for (int x = playerX + 1; x < MAP_MAX_X; x++) {
            if (currentTile.isLegalJump(TILES_ARRAY[x][playerY])) {
                if (isLegalMovement(x, playerY)) {
                    this.playerX = x;
                    return this.playerX * 2;
                }
            }
        }
        this.playerX = playerX;
        return playerX * 2;
    }
    /**
     * method that moves the player to the left
     * @param playerX - player's x coordinate
     * @param playerY - player's y coordinate
     * @return player's x coordinate * 2
     */
    public int moveLeft(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = TILES_ARRAY[playerX][playerY];
        for (int x = playerX - 1; x >= 0; x--) {
            if (currentTile.isLegalJump(TILES_ARRAY[x][playerY])) {
                if (isLegalMovement(x, playerY)) {
                    this.playerX = x;
                    return this.playerX * 2;
                }
            }
        }
        this.playerX = playerX;
        return playerX * 2;
    }
    /**
     * method that moves the player down in the Map
     * @param playerX - player's x coordinate
     * @param playerY - player's y coordinate
     * @return player's y coordinate * 2
     */
    public int moveDown(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = TILES_ARRAY[playerX][playerY];
        for (int y = playerY + 1; y < MAP_MAX_Y; y++) {
            if (currentTile.isLegalJump(TILES_ARRAY[playerX][y])) {
                if (isLegalMovement(playerX, y)) {
                    this.playerY = y;
                    return this.playerY * 2;
                }
            }
        }
        this.playerY = playerY;
        return playerY * 2;
    }
    /**
     * method that moves the player up in the Map
     * @param playerX - player's x coordinate
     * @param playerY - player's y coordinate
     * @return player's y coordinate * 2
     */
    public int moveUp(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = TILES_ARRAY[playerX][playerY];
        for (int y = playerY - 1; y >= 0; y--) {
            if (currentTile.isLegalJump(TILES_ARRAY[playerX][y])) {
                if (isLegalMovement(playerX, y)) {
                    this.playerY = y;
                    return this.playerY * 2;
                }
            }
        }
        this.playerY = playerY;
        return playerY * 2;
    }
    /**
     *
     * method that checks whether the Player's is allowed to do this movement
     * @param cordX coordinate x of player
     * @param cordY coordinate y of player
     * @return true if the movement can happen, false otherwise
     */
    public boolean isLegalMovement(int cordX, int cordY) {
        if (rgate.getX() == cordX && rgate.getY() == cordY) {
            return false;
        }
        else if (wgate.getX() == cordX && wgate.getY() == cordY) {
            return false;
        }
        for (Bomb bomb : bombs) {
            if (bomb.getX() == cordX && bomb.getY() == cordY) {
                return false;
            }
        }
        return true;
    }
    /**
     * method that checks whether a bomb is triggered or not
     * @param playerX coordinate x of player
     * @param playerY coordinate y of player
     * @return true if a bomb is triggered, false otherwise
     */
    public boolean isBombTriggered(int playerX, int playerY) {
        for (Bomb bomb : bombs) {
            if (bomb.isNextToBomb(playerX, playerY)) {
                int secToExplode = bomb.getSecondsToExplode();
                if (secToExplode == -2) {
                    bomb.setSecondsToExplode(3);
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * method that makes a bomb explode
     * @param index index of bomb being exploded
     */
    public void explodeBomb(int index) {
        ArrayList<Bomb> bombs = getBombs();
        int coordX = bombs.get(index).getX();
        int coordY = bombs.get(index).getY();
        for(int x = 0; x < MAP_MAX_X; x++) {
            for(int i = 0; i < loots.size(); i++) {
                if(loots.get(i).getX() == x && loots.get(i).getY() == coordY) {
                    loots.remove(i);
                }
            }
            for(int i = 0; i < clocks.size(); i++) {
                if(clocks.get(i).getX() == x && clocks.get(i).getY() == coordY) {
                    loots.remove(i);
                }
            }
            for(int i = 0; i < bombs.size(); i++) {
                if(bombs.get(i).getX() == x && bombs.get(i).getY() == coordY) {
                    if(index != i) {
                        bombs.get(i).setSecondsToExplode(1);
                    }
                }
            }
            if(wlever.getX() == x && wlever.getY() == coordY) {
                wlever.setX(-2);
                wlever.setY(-2);
            }
            if(rlever.getX() == x && rlever.getY() == coordY) {
                rlever.setX(-2);
                rlever.setY(-2);
            }
        }
        for(int y = 0; y < MAP_MAX_Y; y++) {
            for(int i = 0; i < loots.size(); i++) {
                if(loots.get(i).getX() == coordX && loots.get(i).getY() == y) {
                    loots.remove(i);
                }
            }
            for(int i = 0; i < clocks.size(); i++) {
                if(clocks.get(i).getX() == coordX && clocks.get(i).getY() == y) {
                    clocks.remove(i);
                }
            }
            for(int i = 0; i < bombs.size(); i++) {
                if(bombs.get(i).getX() == coordX && bombs.get(i).getY() == y) {
                    if(index != i) {
                        bombs.get(i).setSecondsToExplode(1);
                    }
                }
            }
            if(wlever.getX() == coordX && wlever.getY() == y) {
                wlever.setX(-2);
                wlever.setY(-2);
            }
            if(rlever.getX() == coordY && rlever.getY() == y) {
                rlever.setX(-2);
                rlever.setY(-2);
            }
        }
    }
    /**
     * method that gets an array of Cells
     * @return returns cells
     */
    public Cell[][] getCellsArray() {
        return convertTilesToCellsArray(TILES_ARRAY);
    }
    /**
     * method that converts a tile array to cells, needed for easier implementation
     * @param tilesArray array of tiles
     * @return cells array
     */
    private Cell[][] convertTilesToCellsArray(Tile[][] tilesArray) {
        Cell[][] cellsArray = new Cell[MAP_MAX_X * 2][MAP_MAX_Y * 2];
        for (int y = 0; y < MAP_MAX_Y; y++) {
            for (int x = 0; x < MAP_MAX_X; x++) {
                cellsArray[x * 2][y * 2] = tilesArray[x][y].getTopLeftCell();
                cellsArray[x * 2 + 1][y * 2] = tilesArray[x][y].getTopRightCell();
                cellsArray[x * 2][y * 2 + 1] = tilesArray[x][y].getBottomLeftCell();
                cellsArray[x * 2 + 1][y * 2 + 1] = tilesArray[x][y].getBottomRightCell();
            }
        }
        return cellsArray;
    }
    /**
     * method that shows how much time is left
     * @return time left
     */
    public int getTimerLeft() {
        return this.timerLeft;
    }
    /**
     * method that checks how much time is left on the clock
     * @param playerX player X coordinate
     * @param playerY player Y coordinate
     * @return clockTime if it is larger than 0
     */
    public int checkClocks(int playerX, int playerY) {
        for (int i = 0; i < clocks.size(); i++) {
            Clock currentClock = clocks.get(i);
            if (currentClock.getX() == playerX && currentClock.getY() == playerY) {
                clocks.remove(i);
                return (currentClock.getClockTime());
            }
        }
        return (0);
    }
    /**
     * method that checks if a player reached the door or not
     * @param playerX player X coordinate
     * @param playerY player Y coordinate
     * @return 1 if true, 0 if false
     */
    public int checkDoor(int playerX, int playerY) {
        if (door.getX() == playerX && door.getY() == playerY) {
            return (1);
        }
        return (0);
    }

    /**
     * method that checks if player is on the same tile as a loot and removes the loot from the tile
     * if it is.
     * @param playerX player X coordinate
     * @param playerY player Y coordinate
     * @return loot value if player is on the same tile as loot, 0 otherwise
     */
    public int checkLoots(int playerX, int playerY) {
        for (int i = 0; i < loots.size(); i++) {
            Loot currentLoot = loots.get(i);
            if (currentLoot.getX() == playerX && currentLoot.getY() == playerY) {
                loots.remove(i);
                lootleft--;
                return (currentLoot.getLootValue());
            }
        }
        return (0);
    }

    /**
     * method that checks if rusty lever is on the same tile as plauer
     * @param playerX player X coordinate
     * @param playerY player Y coordinate
     */
    public void checkRLever(int playerX, int playerY) {

        Lever current = this.getRLever();
        int leverX = current.getX();
        int leverY = current.getY();
        if (leverX == playerX && leverY == playerY) {
            this.rgate.setX(-1);
            this.rgate.setY(-1);
            this.rlever.setX(-1);
            this.rlever.setY(-1);
        }
    }
    /**
     * method that checks if player is on the same tile as wooden lever
     * @param playerX player X coordinate
     * @param playerY player Y coordinate
     */
    public void checkWLever(int playerX, int playerY) {

        Lever current = this.getWLever();
        int leverX = current.getX();
        int leverY = current.getY();
        if (leverX == playerX && leverY == playerY) {
            this.wgate.setX(-1);
            this.wgate.setY(-1);
            this.wlever.setX(-1);
            this.wlever.setY(-1);
        }
    }

    public void setRgate(Gate rgate) {
        this.rgate = rgate;
    }

    public void setWgate(Gate wgate) {
        this.wgate = wgate;
    }


    /**
     * method that gets clocks
     * @return clocks
     */
    public ArrayList<Clock> getClocks() {
        return clocks;
    }
    /**
     * method that gets the Tiles array
     * @return tiles array
     */
    public Tile[][] getTILES_ARRAY() {
        return TILES_ARRAY;
    }
    /**
     * method that gets door
     * @return door
     */
    public Door getDoor() {
        return door;
    }


    /**
     * method that gets bombs
     * @return bombs array
     */
    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    /**
     * method that gets rusty gate
     * @return rgate
     */
    public Gate getRGate() {
        return rgate;
    }

    /**
     * method that gets rusty lever
     * @return rlever
     */
    public Lever getRLever() {
        return rlever;
    }

    /**
     * method that gets wooden gate
     * @return wgate
     */
    public Gate getWGate() {
        return wgate;
    }
    /**
     * method that gets wooden lever
     * @return wlever
     */
    public Lever getWLever() {
        return wlever;
    }
    /**
     * method that gets loots
     * @return loots array
     */
    public ArrayList<Loot> getLoots() {
        return loots;
    }

    public int getMAP_MAX_X() {
        return MAP_MAX_X;
    }

    public int getMAP_MAX_Y() {
        return MAP_MAX_Y;
    }


    /**
     * method that gets the player's starting x coordinate
     * @return player's x coordinate
     */

    public int getPlayerX() {
        return playerX;
    }
    /**
     * method that gets the player's starting y coordinate
     * @return player's y coordinate
     */
    public int getPlayerY() {
        return playerY;
    }
    /**
     * method that sets the bombs array
     * @param bombs array list of bombs
     */
    public void setBombs(ArrayList<Bomb> bombs) {
        this.bombs = bombs;
    }
    /**
     * method that return the starting time array
     * @return starttime start time
     */
    public int getStartTimer(){
        return starttime;
    }
    /**
     * method that return the thieves.
     * @return thieves array of thieves
     */
    public ArrayList<Thief> getThieves() {
        return thieves;
    }
    /**
     * method that sets the thieves
     * @param thieves array of thieves
     */
    public void setThieves(ArrayList<Thief> thieves) {
        this.thieves = thieves;
    }
    /**
     * method that get the flyingAssassins
     * @return flyingAssassins array of flying assassing
     */
    public ArrayList<FlyingAssassin> getFlyingAssassins() {
        return flyingAssassins;
    }
    /**
     * method that sets the flyingAssassins
     * @param flyingAssassins array of flying assassins
     */
    public void setFlyingAssassins(ArrayList<FlyingAssassin> flyingAssassins) {
        this.flyingAssassins = flyingAssassins;
    }
    /**
     * method that check if all the lever was sat off already
     * @return  boolean , true if all lever is toggled or exploded.else, false
     */
    public boolean checklever(){
        if(wlever.getX() == -1 && wlever.getY() == -1) {
            return (rlever.getX() == -1 && rlever.getY() == -1);
        }
        return false;
    }

    /**
     * checks if flying assassin is collided with player
     * @return true if collided false otherwise
     */
    public boolean isFlAsCollidedWPlayer() {
        for(int i = 0; i < flyingAssassins.size(); i++) {
            if(flyingAssassins.get(i).isCollidedWithPlayer(playerX, playerY)) {
                return true;
            }
        }
        return false;
    }
    /**
     * method that check if flyingAssassin is at the sane location with the thief
     * if yes the program will send the thief out of the map(remove)
     */
    public void isFlCollidedWithNPC() {
        for(int i = 0; i < flyingAssassins.size(); i++) {
            for(int j = 0; j < thieves.size(); j++) {
                if(flyingAssassins.get(i).getX() == thieves.get(j).getX() &&
                        flyingAssassins.get(i).getY() == thieves.get(j).getY()) {
                    thieves.get(j).setActive(false);
                    thieves.get(j).setX(-1);
                }
            }
        }
    }

    /**
     * Get id of level
     * @return level id
     */
    public int getLEVEL_ID() {
        return LEVEL_ID;
    }

    /**
     * Get current score
     * @return player score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets player score
     * @param score player score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Sets time left
     * @param timerLeft time left
     */
    public void setTimerLeft(int timerLeft) {
        this.timerLeft = timerLeft;
    }
}
