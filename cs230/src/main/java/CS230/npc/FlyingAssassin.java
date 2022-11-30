package CS230.npc;

import javafx.scene.image.Image;
import javafx.scene.transform.Translate;

import java.io.FileInputStream;

public class FlyingAssassin {
    private String direction;
    private Image sprite;
    private Boolean hasCollided;

    public FlyingAssassin () {

    }

    public void spawn (Image sprite) {

    }

    public void movement(){
        Translate translate = new Translate();

    }

    public Boolean playerCollision(Boolean hasCollided){
        return false;
    }

    public Boolean NCPCollision(Boolean hasCollided) {
        return false;
    }

}
