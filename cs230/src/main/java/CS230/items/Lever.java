package CS230.items;
import javafx.scene.image.Image;

/**
 * Class for creating a lever which inherits from the item class
 * @author Alex-Carmen Macoveanu
 * @version 1.0
 */
public class Lever extends Item {
    /**
     * method that constructs a Lever object
     * @param img - graphic for lever
     * @param x - x coordinate of lever
     * @param y - y coordinate of lever
     */
    public Lever(Image img, int x, int y) {
        super(img, x, y);
    }
}