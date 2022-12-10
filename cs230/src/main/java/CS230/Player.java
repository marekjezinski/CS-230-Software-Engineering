package CS230;

import javafx.scene.image.Image;

/**
 * Class that creates a Player object,
 * @author
 * @version 1.0
 */

public class Player {
    private int x;
    private int y;
    private boolean alive;
    private Image charImage;

    /**
     * constructor that creats a Player object
     * @param x - Player's x coordinate
     * @param y - Player's y coordinate
     * @param pic - Graphic that represents a Player
     */
    public Player(int x, int y,Image pic){
        this.x = x;
        this.y = y;
        this.alive = true;
        this.charImage = pic;
    }

    //Player constructor without image for testing
    public Player(int x, int y){
        this.x = x;
        this.y = y;
        this.alive = true;
    }

    /**
     * method that gets the Player's y position
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * method that gets the Player's x position
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * method that checks whether a Player is alive or not
     * @return true if the Player is alive, false otherwise
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * method that gets a Player's image/graphic
     * @return Player's image
     */
    public Image getCharImage() {
        return charImage;
    }

    /**
     * method that sets the Player's x position
     * @param newX Player's x position
     */
    public void setX(int newX){
        this.x = newX;
    }

    /**
     * method that sets the Player's y position
     * @param newY Player's y position
     */
    public void setY(int newY){
        this.y = newY;
    }

    /**
     * method that kills a Player
     */
    /*public void killPlayer(){
        this.alive = false;
    }*/


}

