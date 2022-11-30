package CS230;

import javafx.scene.image.Image;

public class Character {
    private int xPos;
    private int yPos;
    private String CharType;
    private boolean alive;
    private Image charImage;

    public int getYPos() {
        return yPos;
    }

    public int getXPos() {
        return xPos;
    }

    public boolean isAlive() {
        return alive;
    }

    public String getCharType() {
        return CharType;
    }

    public Image getCharImage() {
        return charImage;
    }

    // public void moveChar() to be implemented...

}
