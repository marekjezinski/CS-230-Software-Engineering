package CS230.npc;

import CS230.Character;
import javafx.scene.image.Image;

public class SmartThief extends Character {

    //private currentPath --  a current path for the npc to move on
    //constructor for smartThief
    public SmartThief (int x, int y, Image smThiefPic) {
        super(x,y,smThiefPic);
    }

    public SmartThief (int x, int y) {
        super(x,y);
    }

    //to be implemented after smartthief map BFS
    //public void moveSmartThief(){}
}
