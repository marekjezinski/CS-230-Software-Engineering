package CS230.items;
import javafx.scene.image.Image;

/**
 * Class that creates a Gate object and inherits from the Item class
 * @author Alex-Carmen Macoveanu
 * @version 1.0
 */
public class Gate extends Item {
    /**
     * class that creates a Gate, with the x and y coordinates and the graphic used to represent
     * a gate
     * @param img - graphic for gate
     * @param x - x coordinate of gate
     * @param y - y coordinate of gate
     */
    public Gate(Image img, int x, int y) {
        super(img, x, y);
    }
}
