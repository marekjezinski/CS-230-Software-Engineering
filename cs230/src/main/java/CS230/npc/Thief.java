package CS230.npc;

import CS230.Character;
import javafx.scene.image.Image;

public class Thief extends Character {

    //private currentPath --  a current path for the npc to move on
    //constructor for smartThief
    public Thief (int x, int y, Image ThiefPic) {
        super(x,y,ThiefPic);
    }

    public Thief (int x, int y) {
        super(x,y);
    }

    //Thief to be implemented
}
