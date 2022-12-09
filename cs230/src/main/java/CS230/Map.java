package CS230;

import CS230.items.*;

import java.util.ArrayList;

public class Map {
    private MapReader mapRead = null;
    private int playerStartX;
    private int playerStartY;
    private int timerLeft;
    private final int MAP_MAX_X;
    private final int MAP_MAX_Y;
    private Tile[][] tilesArray; //contains every tile (that is tile with 4 cells)
    private ArrayList<Clock> clocks = new ArrayList<>();
    private Door door;
    private ArrayList<Loot> loots = new ArrayList<>();
    private Gate rgate;
    private Lever rlever;
    private Gate wgate;
    private Lever wlever;

    private int lootcount;

    private ArrayList<Bomb> bombs = new ArrayList<>();

    public Map(String fileName) {
        this.mapRead = new MapReader(fileName);
        this.MAP_MAX_X = mapRead.getMaxTileX();
        this.MAP_MAX_Y = mapRead.getMaxTileY();
        this.tilesArray = mapRead.getTiles();
        this.timerLeft = mapRead.getTimer();
        this.clocks = mapRead.getClocks();
        this.door = mapRead.getDoor();
        this.loots = mapRead.getLoot();
        this.rgate = mapRead.getRGate();
        this.rlever = mapRead.getRLever();
        this.wgate = mapRead.getWGate();
        this.wlever = mapRead.getWLever();
        this.bombs = mapRead.getBomb();
        this.playerStartX = mapRead.getPlayerStartX();
        this.playerStartY = mapRead.getPlayerStartY();
    }

    public int moveRight(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for (int x = playerX + 1; x < MAP_MAX_X; x++) {
            if (currentTile.isLegalJump(tilesArray[x][playerY])) {
                if (isLegalMovement(x, playerY)) {
                    return x * 2;
                }
            }
        }
        return playerX * 2;
    }

    public int moveLeft(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for (int x = playerX - 1; x >= 0; x--) {
            if (currentTile.isLegalJump(tilesArray[x][playerY])) {
                if (isLegalMovement(x, playerY)) {
                    return x * 2;
                }
            }
        }
        return playerX * 2;
    }

    public int moveDown(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for (int y = playerY + 1; y < MAP_MAX_Y; y++) {
            if (currentTile.isLegalJump(tilesArray[playerX][y])) {
                if (isLegalMovement(playerX, y)) {
                    return y * 2;
                }
            }
        }
        return playerY * 2;
    }

    public int moveUp(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for (int y = playerY - 1; y >= 0; y--) {
            if (currentTile.isLegalJump(tilesArray[playerX][y])) {
                if (isLegalMovement(playerX, y)) {
                    return y * 2;
                }
            }
        }
        return playerY * 2;
    }

    private boolean isLegalMovement(int cordX, int cordY) {
        if (rgate.getX() == cordX && rgate.getY() == cordY) {
            return false;
        }
        else if (wgate.getX() == cordX && wgate.getY() == cordY) {
            return false;
        }
        for(int i = 0; i < bombs.size(); i++) {
            if(bombs.get(i).getX() == cordX && bombs.get(i).getY() == cordY) {
                return false;
            }
        }
        return true;
    }

    public boolean isBombTriggered(int playerX, int playerY) {
        for(int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).isNextToBomb(playerX, playerY)) {
                int secToExplode = bombs.get(i).getSecondsToExplode();
                if (secToExplode == -2) {
                    bombs.get(i).setSecondsToExplode(3);
                    return true;
                }
            }
        }
        return false;
    }

    public void explodeBomb(int index) {
        ArrayList<Bomb> bombs = getBombs();
        int coordX = bombs.get(index).getX();
        int coordY = bombs.get(index).getY();
        for(int x = 0; x < MAP_MAX_X; x++) {
            for(int i = 0; i < loots.size(); i++) {
                if(loots.get(i).getX() == x && loots.get(i).getY() == coordY) {
                    loots.get(i).setX(-1);
                    loots.get(i).setY(-1);
                }
            }
            for(int i = 0; i < clocks.size(); i++) {
                if(clocks.get(i).getX() == x && clocks.get(i).getY() == coordY) {
                    clocks.get(i).setX(-1);
                    clocks.get(i).setY(-1);
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
                wlever.setX(-1);
                wlever.setY(-1);
            }
            if(rlever.getX() == x && rlever.getY() == coordY) {
                rlever.setX(-1);
                rlever.setY(-1);
            }
        }
        for(int y = 0; y < MAP_MAX_Y; y++) {
            for(int i = 0; i < loots.size(); i++) {
                if(loots.get(i).getX() == coordX && loots.get(i).getY() == y) {
                    loots.get(i).setX(-1);
                    loots.get(i).setY(-1);
                }
            }
            for(int i = 0; i < clocks.size(); i++) {
                if(clocks.get(i).getX() == coordX && clocks.get(i).getY() == y) {
                    clocks.get(i).setX(-1);
                    clocks.get(i).setY(-1);
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
                wlever.setX(-1);
                wlever.setY(-1);
            }
            if(rlever.getX() == coordY && rlever.getY() == y) {
                rlever.setX(-1);
                rlever.setY(-1);
            }
        }
    }

    public Cell[][] getCellsArray() {
        return convertTilesToCellsArray(tilesArray);
    }

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

    public int getTimerLeft() {
        return this.timerLeft;
    }

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

    public int checkDoor(int playerX, int playerY) {

        if (door.getX() == playerX && door.getY() == playerY) {
            //TODO: add conditions to make door collectable

            return (1);
        }


        return (0);
    }

    public int checkLoots(int playerX, int playerY) {
        for (int i = 0; i < loots.size(); i++) {
            Loot currentLoot = loots.get(i);
            if (currentLoot.getX() == playerX && currentLoot.getY() == playerY) {
                loots.remove(i);
                return (currentLoot.getLootValue());
            }
        }
        return (0);
    }


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


    public int checkRGate(int playerX, int playerY) {

        Gate current = this.getRGate();
        int gateX = current.getX();
        int gateY = current.getY();
        if (gateX == playerX && gateY == playerY) {
            return 1;
        } else {
            return 0;
        }
    }

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

    public int checkWGate(int playerX, int playerY) {

        Gate current = this.getWGate();
        int gateX = current.getX();
        int gateY = current.getY();
        if (gateX == playerX && gateY == playerY) {
            return 1;
        } else {
            return 0;
        }
    }

    /*public int checkBomb(int playerX, int playerY) {
        for(int i = 0; i < bombs.size(); i++) {
            
        }
    }*/


    public ArrayList<Clock> getClocks() {
        return clocks;
    }

    public Tile[][] getTilesArray() {
        return tilesArray;
    }

    public Door getDoor() {
        return door;
    }

    public Gate getRGate() {
        return rgate;
    }
    public ArrayList<Bomb> getBombs() {
        return bombs;
    }
    public Lever getRLever() {
        return rlever;
    }
    public Gate getWGate() {
        return wgate;
    }
    public Lever getWLever() {
        return wlever;
    }
    public ArrayList<Loot> getLoots() {
        return loots;
    }

    public int lootleft = MapReader.totalloot;




    //public int lootleft = loots.size();



    public int getPlayerStartX() {
        return playerStartX;
    }

    public int getPlayerStartY() {
        return playerStartY;
    }

    public void setBombs(ArrayList<Bomb> bombs) {
        this.bombs = bombs;
    }
}
