package CS230.npc;

import CS230.Map;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Class that creates a FlyingAssassin, inherits from the superclass NPC
 * @author Marek Jezinski
 * @version 1.0
 */
//heavy wip
public class FlyingAssassin extends NPC {
    private char direction;

    /**
     * Constructor that creates a FlyingAssassin
     * @param img - graphic that represents a FlyingAssassin
     * @param x - X coordinate of this NPC
     * @param y - Y coordinate of this NPC
     * @param direction - direction of where this NPC is going. Can be north, south, west or east(n, s, w, e)
     */
    public FlyingAssassin(Image img, int x, int y, char direction) {
        super(img, x, y);
        this.direction = direction;
    }

    /**
     * method that checks the FlyingAssassin's movement. Checks whether it goes north,
     * south, west or east and then it sets its x and y coordinates
     * @param m the current map
     */
    public void movement(Map m) {
        int MAP_MAX_X = m.getMAP_MAX_X();
        int MAP_MAX_Y = m.getMAP_MAX_Y();
        if(direction == 'n') {
            if(y == 0) {
                direction = 's';
                y += 1;
            }
            else {
                y -= 1;
            }
        }
        else if(direction == 's') {
            if(y == MAP_MAX_Y - 1) {
                direction = 'n';
                y -= 1;
            }
            else {
                y += 1;
            }
        }
        else if(direction == 'w') {
            if(x == 0) {
                direction = 'e';
                x += 1;
            }
            else {
                x -= 1;
            }
        }
        else if(direction == 'e') {
            if(x == MAP_MAX_X - 1) {
                direction = 'w';
                x -= 1;
            }
            else {
                x += 1;
            }
        }
    }

    /**
     * Method to see if the flying assassin has collided with
     * the player or not
     * @param playerX
     * @param playerY
     * @return
     */
    public boolean isCollidedWithPlayer(int playerX, int playerY) {
        if(x == playerX && y == playerY) {
            return true;
        }
        return false;
    }

    public char getDirection() {
        return direction;
    }
}
