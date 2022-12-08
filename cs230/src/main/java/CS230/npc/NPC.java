package CS230.npc;

import javafx.scene.image.Image;

public abstract class NPC {
    private Image img;
    protected int x;
    protected int y;

    public NPC(Image img, int x, int y) {
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
