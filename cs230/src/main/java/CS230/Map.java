package CS230;

import java.util.ArrayList;

public class Map {
    private MapReader mapRead = null;

    private ArrayList<Integer> clockParams = new ArrayList<Integer>();
    private ArrayList<Integer> lootParams = new ArrayList<Integer>();
    private ArrayList<Integer> doorParams = new ArrayList<Integer>();

    private int timerLeft;
    private final int MAP_MAX_X;
    private final int MAP_MAX_Y;
    private Tile[][] tilesArray; //contains every tile (that is tile with 4 cells)


    public Map(String fileName) {
        Clock c = new Clock(fileName);
        Loot l = new Loot(fileName);
        Timer t = new Timer(fileName);
        Door d = new Door(fileName);
        mapRead = new MapReader(fileName);
        MAP_MAX_X = mapRead.getMaxTileX();
        MAP_MAX_Y = mapRead.getMaxTileY();
        tilesArray = mapRead.getTiles();
        this.clockParams = c.clockSetter();
        this.lootParams = l.lootSetter();
        this.timerLeft = t.timerSetter();
        this.doorParams = d.doorSetter();
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

    public ArrayList<Integer> getClockParams() {
        return clockParams;
    }

    public ArrayList<Integer> getLootParams() {
        return lootParams;
    }

    public ArrayList<Integer> getDoorParams() {
        return doorParams;
    }

    public int getTimerLeft() {
        return timerLeft;
    }
}
