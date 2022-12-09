package CS230;

import javafx.scene.image.Image;

/**
 * Class that creates a character object, it's used as a superclass for more
 * specific characters
 * @author
 * @version 1.0
 */
public class Character {
    private int xPos;
    private int yPos;
    private boolean alive;
    private Image charImage;

    /**
     * constructor that creats a Character object
     * @param x - Character's x coordinate
     * @param y - Character's y coordinate
     * @param pic - Graphic that represents a Character
     */
    public Character(int x, int y,Image pic){
        this.xPos = x;
        this.yPos = y;
        this.alive = true;
        this.charImage = pic;
    }

    //character constructor without image for testing
    public Character(int x, int y){
        this.xPos = x;
        this.yPos = y;
        this.alive = true;
    }

    /**
     * method that gets the Character's y position
     * @return y coordinate
     */
    public int getYPos() {
        return yPos;
    }

    /**
     * method that gets the Character's x position
     * @return x coordinate
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * method that checks whether a Character is alive or not
     * @return true if the Character is alive, false otherwise
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * method that gets a Character's image/graphic
     * @return Character's image
     */
    public Image getCharImage() {
        return charImage;
    }

    /**
     * method that sets the Character's x position
     * @param Character's x position
     */
    public void setXPos(int newX){
        this.xPos = newX;
    }

    /**
     * method that sets the Character's y position
     * @param Character's y position
     */
    public void setYPos(int newY){
        this.yPos = newY;
    }

    /**
     * method that kills a Character
     */
    public void killPlayer(){
        this.alive = false;
    }
    /**
     *
     * method that moves a Character
     */
    // public void moveChar() to be implemented...

}
