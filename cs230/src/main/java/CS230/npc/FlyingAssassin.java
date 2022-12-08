package CS230.npc;

import CS230.Map;
import javafx.scene.image.Image;

//heavy wip
public class FlyingAssassin extends NPC {
    private char direction;
    public FlyingAssassin(Image img, int x, int y, char direction) {
        super(img, x, y);
        this.direction = direction;
    }

    public void movement(int mapX, int mapY) {
        if(direction == 'n') {
            if(y == 0) {
                this.direction = 's';
                y += 1;
            }
            else {
                y -= 1;
            }
        }
        else if(direction == 's') {
            if(y == mapY) {
                direction = 'n';
                y -= 1;
            }
            else {
                y += 1;
            }
        }
        else if(direction == 'w') {
            if(x == mapX) {
                direction = 'e';
                x -= 1;
            }
            else {
                x += 1;
            }
        }
        else if(direction == 'e') {
            if(x == 0) {
                direction = 'w';
                x += 1;
            }
            x -= 1;
        }
    }
    /*public Map interact(Map m) {

    }*/
}
