
package CS230.items;
import javafx.scene.image.Image;

/**
 * Abstract class that constructs a general Loot object, used as a general template
 * for more specific loot objects
 * @author Marek Jezinski
 * @version 1.0
 */
public abstract class Loot extends Item {
    private final int lootValue;

    /**
     * constructs a general loot object
     * @param img image of the loot object
     * @param x the X coordinate of the loot object
     * @param y the Y coordinate of the loot object
     * @param lootValue the Value of the loot item.
     */
    public Loot(Image img, int x, int y, int lootValue) {
        super(img, x, y);
        this.lootValue = lootValue;
    }

    /**
     * method that gets the value of the loot item
     * @return lootValue the value of the loot item
     */
    public int getLootValue() {
        return lootValue;
    }
}
