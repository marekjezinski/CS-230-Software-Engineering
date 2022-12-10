package CS230.npc;

import javafx.scene.image.Image;

/**
 * Class that creates a SmartThief NPC, inherits from the Character superclass
 * @author
 * @version 1.0
 */
public class SmartThief extends NPC {

    //private currentPath --  a current path for the npc to move on
    /**
     * constructor for the NPC: SmartThief
     * @param x - x coordinate
     * @param y - y coordinate
     * @param smThiefPic - graphic that represents SmartThief
     */
    public SmartThief (int x, int y, Image smThiefPic) {
        super(smThiefPic,x,y);
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
