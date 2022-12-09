package CS230.items;

import javafx.scene.image.Image;

/**
 * Class that creates a Diamond object, inhertis from the superclass Loot
 * @author
 * @version 1.0
 */
public class Diamond extends Loot {
    /**
     * Constructs a Diamond object
     * @param img - graphic for Diamond
     * @param x - x coordinate
     * @param y - y coordinate
     * @param lootValue - holds the value of a Diamond object
     */
    public Diamond(Image img, int x, int y, int lootValue) {
        super(img, x, y, lootValue);
    }
}
