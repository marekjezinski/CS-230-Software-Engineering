package CS230.items;
import javafx.scene.image.Image;

public abstract class Item {
    private Image img;
    private int x;
    private int y;

    public Item(Image img, int x, int y) {
        this.img = img;
        this.x = x;
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
