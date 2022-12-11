package CS230.items;
import javafx.scene.image.Image;

/**
 * Class that constructs a general item object, used as a general
 * template for more specifying item objects.
 * @author Marek Jezinski
 * @version 0
 */
public abstract class Item {
    private Image img;
    protected int x;
    protected int y;

    /**
     * constructs an Item object
     * @param img item image
     * @param x item x coordinate
     * @param y item y coordinate
     */
    public Item(Image img, int x, int y) {
        this.img = img;
        this.x = x;
        this.y = y;
    }

    /**
     * method that gets the item's image/graphic
     * @return img
     */
    public Image getImg() {
        return img;
    }

    /**
     * method that sets an item's image
     * @param img image of the item
     */
    public void setImg(Image img) {
        this.img = img;
    }

    /**
     * method that gets the item's x coordinate
     * @return the item's x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * method that get the item's y coordinate
     * @return y the item's y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * method that sets the x coordinate of an item
     * @param newX the new X coordinate of the item
     */
    public void setX(int newX) {
        this.x = newX;
    }

    /**
     * method that set the y coordinate of an item
     * @param newY the new Y coordinate of the item
     */
    public void setY(int newY) {
        this.y = newY;
    }
}
