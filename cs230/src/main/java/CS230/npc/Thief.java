package CS230.npc;

import CS230.map.Map;
import CS230.map.Tile;
import CS230.items.Bomb;
import CS230.items.Gate;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Class that creates a Thief object, inherits from the superclass Character
 * @author Wiktoria Bruzgo
 * @version 1.0
 */
public class Thief extends NPC {

    //private currentPath --  a current path for the npc to move on
    //constructor for smartThief

    char colour;
    char direction;
    boolean isActive = true;
    /**
     * constructors that creates a Thief object
     * @param x - Thief's x coordinate
     * @param y - Thief's y coordinate
     * @param ThiefPic - image that represents a Thief
     */
    public Thief (int x, int y, Image ThiefPic, char colour, char direction) {
        super(ThiefPic, x, y);
        this.colour = colour;
        this.direction = direction;
    }

    /**
     * Movement for the thief
     * @param m the map of the current level
     */
    public void movement(Map m) {
        if(isActive) {
            Tile[][] tilesArray = m.getTilesArray();
            int MAP_MAX_X = m.getMAP_MAX_X();
            int MAP_MAX_Y = m.getMAP_MAX_Y();
            if(direction == 'e') {
                if(canMoveUp(m)) {
                    moveUp();
                }
                else if(canMoveRight(m)) {
                    moveRight();
                }
                else if(canMoveDown(m)) {
                    moveDown();
                }
                else if(canMoveLeft(m)) {
                    moveLeft();
                }
            }
            else if(direction == 's') {
                if(canMoveRight(m)) {
                    moveRight();
                }
                else if(canMoveDown(m)) {
                    moveDown();
                }
                else if(canMoveLeft(m)) {
                    moveLeft();
                }
                else if(canMoveUp(m)) {
                    moveUp();
                }
            }
            else if(direction == 'w') {
                if(canMoveDown(m)) {
                    moveDown();
                }
                else if(canMoveLeft(m)) {
                    moveLeft();
                }
                else if(canMoveUp(m)) {
                    moveUp();
                }
                else if(canMoveRight(m)) {
                    moveRight();
                }
            }
            else if(direction == 'n') {
                if(canMoveLeft(m)) {
                    moveLeft();
                }
                else if(canMoveUp(m)) {
                    moveUp();
                }
                else if(canMoveRight(m)) {
                    moveRight();
                }
                else if(canMoveDown(m)) {
                    moveDown();
                }
            }
            trigger(m);
        }
    }

    /**
     * Method to see if any items can be removed
     * @param m map of the level
     */

    private void trigger(Map m) {
        m.checkLoots(x, y);
        m.checkRLever(x, y);
        m.checkClocks(x, y);
        m.checkWLever(x, y);
    }

    /**
     * Method to see if there is a gate or bomb
     * @param m - Map elvel
     * @param x - x coordinate
     * @param y - y coordinate
     * @return true if it an item, false otherwise
     */
    private boolean isItem(Map m, int x, int y) {
        Gate r = m.getRGate();
        Gate w = m.getWGate();
        ArrayList<Bomb> bombs = m.getBombs();
        if(x == r.getX() && y == r.getY()) {
            return true;
        }
        if(x == w.getX() && y == w.getY()) {
            return true;
        }
        for(int i = 0; i < bombs.size(); i++) {
            if(bombs.get(i).getX() == x && bombs.get(i).getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for legal movement right
     * @param m - Map level
     * @return true if the thief can move right, false otherwise
     */

    private boolean canMoveRight(Map m) {
        if(x + 1 < m.getMAP_MAX_X()) {
            if(m.getTilesArray()[x+1][y].getTileColours().indexOf(colour) != -1) {
                if(!isItem(m, x+1, y)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Method for legal movement down
     * @param m - Map level
     * @return - true if the thief can move down, false otherwise
     */
    private boolean canMoveDown(Map m) {
        if(y + 1 < m.getMAP_MAX_Y()) {
            if(m.getTilesArray()[x][y+1].getTileColours().indexOf(colour) != -1) {
                if(!isItem(m, x, y+1)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Method for legal movement left
     * @param m - Map level
     * @return true if thief can move left, false otherwise
     */
    private boolean canMoveLeft(Map m) {
        if(x - 1 >= 0) {
            if(m.getTilesArray()[x-1][y].getTileColours().indexOf(colour) != -1) {
                if(!isItem(m, x-1, y)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Method for legal movement up
     * @param m - Map level
     * @return true if thief can move up, false otherwise
     */
    private boolean canMoveUp(Map m) {
        if(y - 1 >= 0) {
            if(m.getTilesArray()[x][y-1].getTileColours().indexOf(colour) != -1) {
                if(!isItem(m, x, y-1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * method for moving right
     */
    private void moveRight() {
        x += 1;
        direction = 'e';
    }
    /**
     * method for moving down
     */
    private void moveDown() {
        y += 1;
        direction = 's';
    }
    /**
     * method for moving left
     */
    private void moveLeft() {
        x -= 1;
        direction = 'w';
    }
    /**
     * method for moving up
     */
    private void moveUp() {
        y -= 1;
        direction = 'n';
    }

    /**
     * Method for changing the active variable
     * @param active to check if it is active.
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    public char getColour() {
        return colour;
    }

    public char getDirection() {
        return direction;
    }
}
