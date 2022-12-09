package CS230.npc;

import CS230.Tile;
import javafx.scene.image.Image;

/**
 * Class that creates a Thief object, inherits from the superclass Character
 * @author
 * @version 1.0
 */
public class Thief extends NPC {

    //private currentPath --  a current path for the npc to move on
    //constructor for smartThief

    char colour;
    /**
     * constructors that creates a Thief object
     * @param x - Thief's x coordinate
     * @param y - Thief's y coordinate
     * @param ThiefPic - image that represents a Thief
     */
    public Thief (int x, int y, Image ThiefPic, char colour) {
        super(ThiefPic, x, y);
        this.colour = colour;
    }

    public void movement(Tile[][] tilesArray, int MAP_MAX_X, int MAP_MAX_Y) {
        if(x + 1 < MAP_MAX_X) {
            if(tilesArray[x+1][y].getTileColours().indexOf(colour) != -1) {
                x += 1;
            }
        }
        else if(y + 1 < MAP_MAX_Y) {
            if(tilesArray[x][y+1].getTileColours().indexOf(colour) != -1) {
                y += 1;
            }
        }
        else if(y - 1 > 0) {
            if(tilesArray[x-1][y].getTileColours().indexOf(colour) != -1) {
                x -= 1;
            }
        }



    }

    //Thief to be implemented
}
