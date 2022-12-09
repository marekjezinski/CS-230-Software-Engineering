package CS230.npc;

import CS230.Character;
import javafx.scene.image.Image;

/**
 * Class that creates a Thief object, inherits from the superclass Character
 * @author
 * @version 1.0
 */
public class Thief extends Character {

    //private currentPath --  a current path for the npc to move on
    //constructor for smartThief

    /**
     * constructors that creates a Thief object
     * @param x - Thief's x coordinate
     * @param y - Thief's y coordinate
     * @param ThiefPic - image that represents a Thief
     */
    public Thief (int x, int y, Image ThiefPic) {
        super(x,y,ThiefPic);
    }

    public Thief (int x, int y) {
        super(x,y);
    }

    //Thief to be implemented
}
