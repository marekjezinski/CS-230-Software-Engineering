package CS230.npc;

import CS230.map.Map;
import javafx.scene.image.Image;

import java.util.Random;

/**
 * Class that creates a SmartThief NPC, inherits from the NPC abstract superclass
 * @author Caleb Ocansey
 * @version 1.0
 */
public class SmartThief extends NPC {

    /**
     * constructor for the NPC: SmartThief
     * @param x - x coordinate
     * @param y - y coordinate
     * @param smThiefPic - graphic that represents SmartThief
     */
    public SmartThief (int x, int y, Image smThiefPic) {
        super(smThiefPic,x,y);
    }

    /**
     *  randomly moves if no valid path is found
     * @param currentLevel the map of current level
     */
    public void randomMovement(Map currentLevel){
        Random rand = new Random();
        int directionChoice = rand.nextInt(3);

        //Switch based on what the random number generator outputs

        switch (directionChoice){
            case 0:
                    y-=1;
                currentLevel.checkLoots(this.x,this.y);
                break;
            case 1:

                    y+=1;
                currentLevel.checkLoots(this.x,this.y);
                break;
            case 2:
                    x+=1;
                currentLevel.checkLoots(this.x,this.y);
                break;
            case 3:
                    x-=1;
                currentLevel.checkLoots(this.x,this.y);
                break;
            default:
                break;
        }

    }

}
