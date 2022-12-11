
package CS230.items;
import javafx.scene.image.Image;

/**
 * Class that creates a Door object, inherits from the superclass Item
 * @author Marek Jezinski
 * @version 1.0
 */
public class Door extends Item {
    /**
     * class that creates a door, with the x and y coordinates and the graphic used to represent
     * a door
     * @param img - graphic for door
     * @param x - x coordinate of door
     * @param y - y coordinate of door
     */
    public Door(Image img, int x, int y) {
        super(img, x, y);
    }
}
