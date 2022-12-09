package CS230.items;

import javafx.scene.image.Image;

/**
 * class that creates a Cent object, which inherits from the Loot class
 * @author
 * @version 1.0
 */

public class Cent extends Loot {
    /**
     * constructs a Cent object
     * @param img - graphic for cent
     * @param x - x coordinate
     * @param y - y coordinate
     * @param lootValue - holds the value of a Cent object
     */
    public Cent(Image img, int x, int y, int lootValue) {
        super(img, x, y, lootValue);
    }
}
