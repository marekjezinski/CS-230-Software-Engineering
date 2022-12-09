package CS230.npc;

import CS230.Tile;
import javafx.scene.image.Image;

/**
 * Class that creates a Thief object, inherits from the superclass Character
 * @author
 * @version 1.0
 */
public class Thief extends NPC {

    //private currentPath --  a current path for the npc to move on
    //constructor for smartThief

    char colour;
    char direction = 'e';
    /**
     * constructors that creates a Thief object
     * @param x - Thief's x coordinate
     * @param y - Thief's y coordinate
     * @param ThiefPic - image that represents a Thief
     */
    public Thief (int x, int y, Image ThiefPic, char colour) {
        super(ThiefPic, x, y);
        this.colour = colour;
    }

    public void movement(Tile[][] tilesArray, int MAP_MAX_X, int MAP_MAX_Y) {
        System.out.println(direction);
        if(direction == 'e') {
            if(canMoveUp(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveUp();
            }
            else if(canMoveRight(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveRight();
            }
            else if(canMoveDown(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveDown();
            }
            else if(canMoveLeft(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveLeft();
            }
        }
        else if(direction == 's') {
            if(canMoveRight(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveRight();
            }
            else if(canMoveDown(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveDown();
            }
            else if(canMoveLeft(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveLeft();
            }
            else if(canMoveUp(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveUp();
            }
        }
        else if(direction == 'w') {
            if(canMoveDown(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveDown();
            }
            else if(canMoveLeft(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveLeft();
            }
            else if(canMoveUp(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveUp();
            }
            else if(canMoveRight(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveRight();
            }
        }
        else if(direction == 'n') {
            if(canMoveLeft(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveLeft();
            }
            else if(canMoveUp(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveUp();
            }
            else if(canMoveRight(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveRight();
            }
            else if(canMoveDown(tilesArray, MAP_MAX_X, MAP_MAX_Y)) {
                moveDown();
            }
        }
    }
    private boolean canMoveRight(Tile[][] tilesArray, int MAP_MAX_X, int MAP_MAX_Y) {
        if(x + 1 < MAP_MAX_X) {
            if(tilesArray[x+1][y].getTileColours().indexOf(colour) != -1) {
                return true;
            }
        }
        return false;
    }
    private boolean canMoveDown(Tile[][] tilesArray, int MAP_MAX_X, int MAP_MAX_Y) {
        if(y + 1 < MAP_MAX_Y) {
            if(tilesArray[x][y+1].getTileColours().indexOf(colour) != -1) {
                return true;
            }
        }
        return false;
    }
    private boolean canMoveLeft(Tile[][] tilesArray, int MAP_MAX_X, int MAP_MAX_Y) {
        if(x - 1 > 0) {
            if(tilesArray[x-1][y].getTileColours().indexOf(colour) != -1) {
                return true;
            }
        }
        return false;
    }
    private boolean canMoveUp(Tile[][] tilesArray, int MAP_MAX_X, int MAP_MAX_Y) {
        if(y - 1 > 0) {
            if(tilesArray[x][y-1].getTileColours().indexOf(colour) != -1) {
                return true;
            }
        }
        return false;
    }
    private void moveRight() {
        x += 1;
        direction = 'e';
    }
    private void moveDown() {
        y += 1;
        direction = 's';
    }
    private void moveLeft() {
        x -= 1;
        direction = 'w';
    }
    private void moveUp() {
        y -= 1;
        direction = 'n';
    }

    //Thief to be implemented
}
