package CS230.npc;

import javafx.scene.image.Image;

/**
 * Class that creates a FlyingAssassin, inherits from the superclass NPC
 * @author
 * @version 1.0
 */
//heavy wip
public class FlyingAssassin extends NPC {
    private char direction;

    /**
     * Constructor that creates a FlyingAssassin
     * @param img - graphic that represents a FlyingAssassin
     * @param x - x coordinate of this NPC
     * @param y - y coordinate of this NPC
     * @param direction - direction of where this NPC is going. Can be north, south, west or east
     */
    public FlyingAssassin(Image img, int x, int y, char direction) {
        super(img, x, y);
        this.direction = direction;
    }

    /**
     * method that checks the FlyingAssassin's movement. Checks whether it goes north,
     * south, west or east and then it sets its x and y coordinates
     */
    public void movement(int MAP_MAX_X, int MAP_MAX_Y) {
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
     * method that sets the FlyingAssassin's direction
     * @param direction the direction of FlyingAssassin
     */
    public void setDirection(char direction) {
        this.direction = direction;
    }
}
