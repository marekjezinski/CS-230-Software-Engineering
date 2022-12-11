package CS230.items;

import javafx.scene.image.Image;

/**
 * Class that creates a Dollar object, inherits from the Loot class
 * @author Marek Jezinski
 * @version 1.0
 */
public class Dollar extends Loot {
    /**
     * constructor a Dollar object
     * @param img - graphic for the Dollar object
     * @param x - x coordinate
     * @param y - y coordinate
     * @param lootValue - holds the value of a Dollar object
     */
    public Dollar(Image img, int x, int y, int lootValue) {
        super(img, x, y, lootValue, "dollar");
    }
}
