package CS230.items;
/**
 * Bomb item class for the game, is a subclass of item
 * @author Tom Stevens
 * @version 1.0
 */

import javafx.scene.image.Image;

public class Bomb extends Item {
    private boolean isTriggered;
    private int secondsToExplode = -1;
    public Bomb(Image img, int x, int y) {
        super(img, x, y);
    }
    public void trigger() {
        isTriggered = true;
        secondsToExplode = 3;
    }

    public void setSecondsToExplode(int secondsToExplode) {
        this.secondsToExplode = secondsToExplode;
    }
}
