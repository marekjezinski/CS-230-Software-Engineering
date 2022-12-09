package CS230.npc;

import javafx.scene.image.Image;

/**
 * Abstract class that creates a general NPC object, it's used
 * as a template for more specific NPCs
 * @author
 * @version 0
 */
public abstract class NPC {
    private final Image img;
    protected int x;
    protected int y;

    /**
     * constructs an NPC object
     * @param img image of NPC
     * @param x X coordinate for NPC
     * @param y Y coordinate of NPC
     */
    public NPC(Image img, int x, int y) {
        this.img = img;
        this.x = x;
        this.y = y;
    }

    /**
     * method that gets an NPC image/graphic
     * @return NPC's image
     */
    public Image getImg() {
        return img;
    }

    /**
     * method that gets the NPC's x coordinate
     * @return x  the X coordinate of the NPC
     */
    public int getX() {
        return x;
    }

    /**
     * method that gets the NPC's y coordinate
     * @return y  the Y coordinate of the NPC
     */
    public int getY() {
        return y;
    }

    /**
     * method that sets an NPC's x coordinate
     * @param x input of NPC's X coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * method that sets an NPC's y coordinate
     * @param y input of NPC's y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }
}
