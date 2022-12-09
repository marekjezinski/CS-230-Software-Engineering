package CS230.npc;

import CS230.Character;
import javafx.scene.image.Image;

/**
 * Class that creates a SmartThief NPC, inherits from the Character superclass
 * @author
 * @version 1.0
 */
public class SmartThief extends Character {

    //private currentPath --  a current path for the npc to move on
    /**
     * constructor for the NPC: SmartThief
     * @param x - x coordinate
     * @param y - y coordinate
     * @param smThiefPic - graphic that represents SmartThief
     */
    public SmartThief (int x, int y, Image smThiefPic) {
        super(x,y,smThiefPic);
    }

    public SmartThief (int x, int y) {
        super(x,y);
    }

    //to be implemented after smartthief map BFS
    /**
     *
     */
    //public void moveSmartThief(){}
}
