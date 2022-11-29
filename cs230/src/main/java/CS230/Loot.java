
package CS230;
import java.util.ArrayList;
import javafx.scene.image.Image;
public class Loot {
    private String fileName;

    public Loot(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Integer> lootSetter(){
        ArrayList<Integer> params = new ArrayList<Integer>();
        MapReader l = new MapReader(fileName);
        params = l.getLoot();
        return params;
    }
}
