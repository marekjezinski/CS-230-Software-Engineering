
package CS230.items;
import java.util.ArrayList;

import CS230.MapReader;
import javafx.scene.image.Image;

/**
 * Abstract class that constructs a general Loot object, used as a general template
 * for more specific loot objects
 * @author
 * @version 1.0
 */
public abstract class Loot extends Item {
    private int lootValue;

    /**
     * constructs a general loot object
     * @param img
     * @param x
     * @param y
     * @param lootValue
     */
    public Loot(Image img, int x, int y, int lootValue) {
        super(img, x, y);
        this.lootValue = lootValue;
    }

    /**
     * method that gets a loot value
     * @return lootValue
     */
    public int getLootValue() {
        return lootValue;
    }
}
