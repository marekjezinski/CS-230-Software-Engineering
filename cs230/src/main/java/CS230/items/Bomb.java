package CS230.items;
/**
 * Bomb item class for the game, is a subclass of item
 * @author Tom Stevens
 * @version 1.0
 */

import javafx.scene.image.Image;
/**
 * The class Bomb extends item
 */
public class Bomb extends Item {
    private boolean isTriggered;
    private int secondsToExplode = -2;
    /**
     *
     * Bomb
     *
     * @param img  the image of the bomb
     * @param x  the x coordinate
     * @param y  the y coordinate
     */
    public Bomb(Image img, int x, int y) {
        super(img, x, y);
    }

    /**
     * method that triggers a bomb and sets its seconds before it explodes
     */


    /**
     * method that gets the seconds that a bomb will last before exploding
     * @return secondsToExplode
     */
    public int getSecondsToExplode() {
        return secondsToExplode;
    }

    /**
     * method that sets a bomb's seconds to explode
     * @param secondsToExplode reading the bomb explode time
     */
    public void setSecondsToExplode(int secondsToExplode) {
        this.secondsToExplode = secondsToExplode;
    }

    /**
     * method that returns true or false if whether the player's coordinates is the same as a bomb.
     * checks whether a player is next to a bomb or not.
     * @param playerX  the player X coordinate
     * @param playerY  the player Y coordinate
     * @return true if player is next to a bomb, false otherwise.
     */
    public boolean isNextToBomb(int playerX, int playerY) {
        if ((x == playerX - 1 || x == playerX + 1 )&& y == playerY) {
            return true;
        }
        else return (x == playerX && (y == playerY + 1 || y == playerY - 1));
    }
}
