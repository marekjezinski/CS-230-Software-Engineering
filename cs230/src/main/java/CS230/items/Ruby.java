package CS230.items;

import javafx.scene.image.Image;

/**
 * Class that creates a Ruby object, inherits from the Loot class
 * @author Marek Jezinski
 * @version 1.0
 */
public class Ruby extends Loot {
    /**
     * constructs a Ruby object
     * @param img - graphic that represents a Ruby
     * @param x - x coordinate
     * @param y - y coordinate
     * @param lootValue - holds the value of a Ruby object
     */
    public Ruby(Image img, int x, int y, int lootValue) {
        super(img, x, y, lootValue, "ruby");
    }
}
