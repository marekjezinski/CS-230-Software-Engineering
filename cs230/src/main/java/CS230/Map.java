package CS230;

import CS230.items.*;

import java.util.ArrayList;

public class Map {
    private MapReader mapRead = null;
    private int timerLeft;
    private final int MAP_MAX_X;
    private final int MAP_MAX_Y;
    private Tile[][] tilesArray; //contains every tile (that is tile with 4 cells)
    private ArrayList<Clock> clocks = new ArrayList<>();
    private Door door;
    private ArrayList<Loot> loots = new ArrayList<>();
    private Gate rgate;

    public Map(String fileName) {
        this.mapRead = new MapReader(fileName);
        this.MAP_MAX_X = mapRead.getMaxTileX();
        this.MAP_MAX_Y = mapRead.getMaxTileY();
        this.tilesArray = mapRead.getTiles();
        this.timerLeft = mapRead.getTimer();
        this.clocks = mapRead.getClocks();
        this.door = mapRead.getDoor();
        this.loots = mapRead.getLoot();
        this.rgate = mapRead.getRgate();
    }

    public int moveRight(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for(int x = playerX + 1; x < MAP_MAX_X; x++) {
            if(currentTile.isLegalMovement(tilesArray[x][playerY])) {
                return x * 2;
            }
        }
        return playerX * 2;
    }

    public int moveLeft(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for(int x = playerX - 1; x >= 0; x--) {
            if(currentTile.isLegalMovement(tilesArray[x][playerY])) {
                return x * 2;
            }
        }
        return playerX * 2;
    }

    public int moveDown(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for(int y = playerY + 1; y < MAP_MAX_Y; y++) {
            if(currentTile.isLegalMovement(tilesArray[playerX][y])) {
                return y * 2;
            }
        }
        return playerY * 2;
    }

    public int moveUp(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for(int y = playerY - 1; y >= 0; y--) {
            if(currentTile.isLegalMovement(tilesArray[playerX][y])) {
                return y * 2;
            }
        }
        return playerY * 2;
    }

    public Cell[][] getCellsArray() {
        return convertTilesToCellsArray(tilesArray);
    }

    private Cell[][] convertTilesToCellsArray(Tile[][] tilesArray) {
        Cell[][] cellsArray = new Cell[MAP_MAX_X * 2][MAP_MAX_Y * 2];
        for(int y = 0; y < MAP_MAX_Y; y++) {
            for(int x = 0; x < MAP_MAX_X; x++) {
                cellsArray[x * 2][y * 2] = tilesArray[x][y].getTopLeftCell();
                cellsArray[x * 2 + 1][y * 2] = tilesArray[x][y].getTopRightCell();
                cellsArray[x * 2][y * 2 + 1] = tilesArray[x][y].getBottomLeftCell();
                cellsArray[x * 2 + 1][y * 2 + 1] = tilesArray[x][y].getBottomRightCell();
            }
        }
        return cellsArray;
    }

    public int getTimerLeft() {
        return this.timerLeft;
    }

    public int checkClocks(int playerX, int playerY) {
        for(int i = 0; i < clocks.size(); i++) {
            Clock currentClock = clocks.get(i);
            if(currentClock.getX() == playerX && currentClock.getY() == playerY) {
                clocks.remove(i);
                return(currentClock.getClockTime());
            }
        }
        return(0);
    }

    public int checkDoor(int playerX, int playerY) {

            if (door.getX() == playerX && door.getY() == playerY) {
                //TODO: add conditions to make door collectable

                return (1);
            }


        return (0);
    }

    public int checkLoots(int playerX, int playerY) {
        for(int i = 0; i < loots.size(); i++) {
            Loot currentLoot = loots.get(i);
            if(currentLoot.getX() == playerX && currentLoot.getY() == playerY) {
                loots.remove(i);
                return(currentLoot.getLootValue());
            }
        }
        return(0);
    }

    public ArrayList<Clock> getClocks() {
        return clocks;
    }

    public Door getDoor() {
        return door;
    }

    public Gate getGate() {
        return rgate;
    }
    public ArrayList<Loot> getLoots() {
        return loots;
    }
}
