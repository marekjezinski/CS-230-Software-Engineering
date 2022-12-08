package CS230.npc;

import javafx.scene.image.Image;

//heavy wip
public class FlyingAssassin extends NPC {
    private char direction;
    public FlyingAssassin(Image img, int x, int y, char direction) {
        super(img, x, y);
        this.direction = direction;
    }

    public void movement() {
        if(direction == 'n') {
            super.setY(super.getY() - 1);
        }
        else if(direction == 's') {
            super.setY(super.getY() + 1);
        }
        else if(direction == 'w') {
            super.setX(super.getX() + 1);
        }
        else if(direction == 'e') {
            super.setX(super.getX() - 1);
        }
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }
}
