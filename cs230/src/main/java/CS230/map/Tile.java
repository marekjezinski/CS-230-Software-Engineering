package CS230.map;

/**
 * Tile class for the game
 * @author Marek Jezinski
 * @version 1.0
 */
public class Tile {
    private Cell[] cells = new Cell[4];

    //colourCodes is array of 4 chars representing (1 tile is 4 cells): R - red,
    //G - green, B - blue, Y - yellow, C - cyan, M - magenta

    /**
     * Constructor creates tile consisting of four cells
     * @param tileText colour code from map file
     */
    public Tile(String tileText) {
        char[] colourCodes = tileText.toUpperCase().toCharArray();
        for(int i = 0; i < colourCodes.length; i++) {
            cells[i] = new Cell(colourCodes[i]);
        }
    }

    /**
     * Compares one tile with another and determines if jump is legal
     * @param t another tile
     * @return true if legal jump false otherwise
     */
    public boolean isLegalJump(Tile t) {
        String colourCode1 = t.getTileColours();
        String colourCode2 = getTileColours();
        char[] charColourArray1 = colourCode1.toCharArray();
        char[] charColourArray2 = colourCode2.toCharArray();
        for(char var : charColourArray1) {
            for(char var2 : charColourArray2) {
                if (var == var2) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns tile colours in file format
     * @return string of tile colours
     */
    public String getTileColours() {
        String s = "";
        for(Cell var : cells) {
            s += var.getColourCode();
        }
        return s;
    }

    /**
     * Returns top left cell
     * @return top left cell
     */
    public Cell getTopLeftCell() {
        return cells[0];
    }

    /**
     * Returns top right cell
     * @return top right cell
     */
    public Cell getTopRightCell() {
        return cells[1];
    }

    /**
     * Returns bottom left cell
     * @return bottom left cell
     */
    public Cell getBottomLeftCell() {
        return cells[2];
    }

    /**
     * Returns bottom right cell
     * @return bottom right cell
     */
    public Cell getBottomRightCell() {
        return cells[3];
    }

}
