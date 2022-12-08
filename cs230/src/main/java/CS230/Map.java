package CS230;

import CS230.items.*;

import java.net.URISyntaxException;
import java.util.ArrayList;

import javafx.animation.Timeline;
import javafx.scene.image.Image;

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
    private Lever rlever;
    private Gate wgate;
    private Lever wlever;

    private Bomb bomb;
    private int explodeX;
    private int explodeY;

    private ArrayList<Bomb> bombs = new ArrayList<>();
    private int countdownForBomb;

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

    }

    public int moveRight(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for (int x = playerX + 1; x < MAP_MAX_X; x++) {
            if (currentTile.isLegalMovement(tilesArray[x][playerY])) {
                return x * 2;
            }
        }
        return playerX * 2;
    }

    public int moveLeft(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for (int x = playerX - 1; x >= 0; x--) {
            if (currentTile.isLegalMovement(tilesArray[x][playerY])) {
                return x * 2;
            }
        }
        return playerX * 2;
    }

    public int moveDown(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for (int y = playerY + 1; y < MAP_MAX_Y; y++) {
            if (currentTile.isLegalMovement(tilesArray[playerX][y])) {
                return y * 2;
            }
        }
        return playerY * 2;
    }

    public int moveUp(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for (int y = playerY - 1; y >= 0; y--) {
            if (currentTile.isLegalMovement(tilesArray[playerX][y])) {
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

    public int moveBomb(int playerX, int playerY) {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb currentBomb = bombs.get(i);
            int bombX = currentBomb.getX();
            int bombY = currentBomb.getY();
            if (bombX == playerX && bombY == playerY) {
                return 1;

            }
        }
        return 0;
    }


    public int bombCheck(int playerX, int playerY) {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb currentBomb = bombs.get(i);
            int bombX = currentBomb.getX();
            int bombY = currentBomb.getY();
            if ((bombX == playerX && bombY == playerY + 1)
                    || (bombX == playerX && bombY == playerY - 1)
                    || (bombX == playerX - 1 && bombY == playerY)
                    || (bombX == playerX + 1 && bombY == playerY)
                    || (bombX == playerX - 1 && bombY == playerY - 1)
                    || (bombX == playerX + 1 && bombY == playerY + 1)
                    || (bombX == playerX - 1 && bombY == playerY + 1)
                    || (bombX == playerX + 1 && bombY == playerY - 1)) {
                this.explodeX = bombX;
                this.explodeY = bombY;
                this.countdownForBomb = 3;
                return 1;
            }
        }
        return 0;
    }


    public void bombActivate() throws URISyntaxException {
        boolean neighbour = false;
        if (this.countdownForBomb <= 0) {
            for (int i = 0; i < this.loots.size(); i++) {
                Loot loot = this.loots.get(i);
                int lootX = loot.getX();
                int lootY = loot.getY();
                if (this.explodeX == lootX || this.explodeY == lootY) {
                    this.loots.remove(i);
                }
            }
            for (int i = 0; i < this.clocks.size(); i++) {
                Clock clock = this.clocks.get(i);
                int clockX = clock.getX();
                int clockY = clock.getY();
                if (this.explodeX == clockX || this.explodeY == clockY) {
                    this.clocks.remove(i);
                }
            }
            int leverX = this.wlever.getX();
            int leverY = this.wlever.getY();
            if (this.explodeY == leverX || this.explodeY == leverY) {
                this.wlever.setX(-1);
                this.wlever.setY(-1);
            }
            leverX = this.rlever.getX();
            leverY = this.rlever.getY();
            if (this.explodeX == leverX || this.explodeY == leverY) {
                this.rlever.setX(-1);
                this.rlever.setY(-1);
            }

            for (int i = 0; i < this.bombs.size(); i++) {
                Bomb bomb = this.bombs.get(i);
                int bombX = bomb.getX();
                int bombY = bomb.getY();
                if (this.explodeX == bombX || this.explodeY == bombY) {
                    for (int j = 0; j < bombs.size(); j++) {
                        Bomb moveBomb = bombs.get(j);
                        if (moveBomb.getX() == explodeX
                                && moveBomb.getY() == explodeY) {
                            System.out.println(this.explodeX + " " + this.explodeY);
                            neighbour = true;
                            bombs.get(j).setX(-1);
                            bombs.get(j).setY(-1);
                            this.explodeX = bombX;
                            this.explodeY = bombY;
                            this.countdownForBomb = 3;
                            bombActivate();

                        }
                    }
                }

            }
        }



        String bombImg = "bomb" + this.countdownForBomb + ".png";
        bomb.setImg(new Image(getClass().getResource(bombImg).toURI().toString()));
        this.countdownForBomb = this.countdownForBomb - 1;
    }

    public ArrayList<Clock> getClocks() {
        return clocks;
    }

    public Door getDoor() {
        return door;
    }

    public Gate getRGate() {
        return rgate;
    }
    public ArrayList<Bomb> getBomb() {
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

    public int getCountdownForBomb() {
        return countdownForBomb;
    }
}
