
package CS230;
import java.util.ArrayList;

public class Door {
    private String fileName;

    public Door(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Integer> doorSetter(){
        ArrayList<Integer> params = new ArrayList<Integer>();
        MapReader d = new MapReader(fileName);
        params = d.getDoor();
        return params;
    }
}
