
package CS230.items;

import CS230.items.Item;
import javafx.scene.image.Image;
public class Clock extends Item {
    private int clockTime;
    public Clock(Image img, int x, int y, int clockTime) {
        super(img, x, y);
        this.clockTime = clockTime;
    }

    public int getClockTime() {
        return clockTime;
    }
}
