
package CS230.items;
import java.util.ArrayList;

import CS230.MapReader;
import javafx.scene.image.Image;
public abstract class Loot extends Item {
    private int lootValue;

    public Loot(Image img, int x, int y, int lootValue) {
        super(img, x, y);
        this.lootValue = lootValue;
    }
    public int getLootValue() {
        return lootValue;
    }
}
