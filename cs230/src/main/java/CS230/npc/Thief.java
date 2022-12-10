package CS230.npc;

import CS230.Map;
import CS230.Tile;
import CS230.items.Bomb;
import CS230.items.Gate;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Class that creates a Thief object, inherits from the superclass Character
 * @author
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
                    System.out.println(canMoveLeft(m));
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

    private void trigger(Map m) {
        m.checkLoots(x, y);
        m.checkRLever(x, y);
        m.checkClocks(x, y);
        m.checkWLever(x, y);
    }

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
    private boolean canMoveLeft(Map m) {
        if(x - 1 > 0) {
            if(m.getTilesArray()[x-1][y].getTileColours().indexOf(colour) != -1) {
                if(!isItem(m, x-1, y)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean canMoveUp(Map m) {
        if(y - 1 > 0) {
            if(m.getTilesArray()[x][y-1].getTileColours().indexOf(colour) != -1) {
                if(!isItem(m, x, y-1)) {
                    return true;
                }
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

    public void setActive(boolean active) {
        isActive = active;
    }

    //Thief to be implemented
}
