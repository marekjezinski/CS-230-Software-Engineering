
package CS230.items;


import javafx.scene.image.Image;
/**
 * Class for the clock object in the game
 * @author Tom Stevens
 * @version 1.0
 */
public class Clock extends Item {
    private int clockTime;

    /**
     * Method to read positions of the clocks and how much extra time they
     * give to the player
     * @param img
     * @param x
     * @param y
     * @param clockTime
     */
    public Clock(Image img, int x, int y, int clockTime) {
        super(img, x, y);
        this.clockTime = clockTime;
    }

    /**
     * Returns what time has been set for the clocks
     * @return clockTime
     */
    public int getClockTime() {
        return clockTime;
    }
}
