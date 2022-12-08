package CS230;

import javafx.scene.image.Image;

public class Character {
    private int xPos;
    private int yPos;
    private Image img;

    public Character(int x, int y,Image pic){
        this.xPos = x;
        this.yPos = y;
        this.img = pic;
    }

    public int getYPos() {
        return yPos;
    }

    public int getXPos() {
        return xPos;
    }

    
    public Image getImg() {
        return img;
    }

    public void setXPos(int newX){
        this.xPos = newX;
    }

    public void setYPos(int newY){
        this.yPos = newY;
    }

}
