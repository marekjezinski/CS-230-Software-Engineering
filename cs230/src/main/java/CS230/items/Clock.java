
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
     * @param img image of the clock
     * @param x the x coordinate of the clock item
     * @param y   the y coordinate of the clock item
     * @param clockTime  the time of the clock item
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
