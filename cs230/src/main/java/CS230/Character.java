package CS230;

import javafx.scene.image.Image;

public class Character {
    private int xPos;
    private int yPos;
    private boolean alive;
    private Image charImage;

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

    public int getYPos() {
        return yPos;
    }

    public int getXPos() {
        return xPos;
    }

    public boolean isAlive() {
        return alive;
    }

    
    public Image getCharImage() {
        return charImage;
    }

    public void setXPos(int newX){
        this.xPos = newX;
    }

    public void setYPos(int newY){
        this.yPos = newY;
    }

    public void killPlayer(){
        this.alive = false;
    }
    // public void moveChar() to be implemented...

}
