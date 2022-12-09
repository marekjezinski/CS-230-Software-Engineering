package CS230.items;
/**
 * Bomb item class for the game, is a subclass of item
 * @author Tom Stevens
 * @version 1.0
 */

import javafx.scene.image.Image;

public class Bomb extends Item {
    private boolean isTriggered;
    private int secondsToExplode = -2;
    public Bomb(Image img, int x, int y) {
        super(img, x, y);
    }
    public void trigger() {
        isTriggered = true;
        secondsToExplode = 3;
    }

    public int getSecondsToExplode() {
        return secondsToExplode;
    }

    public void setSecondsToExplode(int secondsToExplode) {
        this.secondsToExplode = secondsToExplode;
    }
    public boolean isNextToBomb(int playerX, int playerY) {
        if (x == playerX - 1 && y == playerY) {
            return true;
        }
        else if (x == playerX + 1 && y == playerY) {
            return true;
        }
        else if (x == playerX && y == playerY + 1) {
            return true;
        }
        else if (x == playerX && y == playerY - 1) {
            return true;
        }
        return false;
    }
}
