package CS230.items;
import javafx.scene.image.Image;

/**
 * Class that constructs a general item object, used as a general
 * template for more specifying item objects.
 * @author
 * @version
 */
public abstract class Item {
    private Image img;
    protected int x;
    protected int y;

    /**
     * constructs an Item object
     * @param img
     * @param x
     * @param y
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
     * @param img
     */
    public void setImg(Image img) {
        this.img = img;
    }

    /**
     * method that gets the item's x coordinate
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * method that get's the item's y coordinate
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * method that sets the x coordinate of an item
     * @param newX
     */
    public void setX(int newX) {
        this.x = newX;
    }

    /**
     * method that set the y coordinate of an item
     * @param newY
     */
    public void setY(int newY) {
        this.y = newY;
    }
}
