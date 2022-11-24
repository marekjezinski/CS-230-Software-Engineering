package CS230;

public class Tile {
    private Cell[] cells = new Cell[4];

    //colourCodes is array of 4 chars representing (1 tile is 4 cells): R - red,
    //G - green, B - blue, Y - yellow, C - cyan, M - magenta
    public Tile(String tileText) {
        char[] colourCodes = tileText.toUpperCase().toCharArray();
        for(int i = 0; i < colourCodes.length; i++) {
            cells[i] = new Cell(colourCodes[i]);
        }
    }

    public boolean isLegalMovement(char colourCode) {
        for(Cell var : cells) {
            if(var.getColourCode() == colourCode) {
                return true;
            }
        }
        return false;
    }
    public String getTileColours() {
        String s = "";
        for(Cell var : cells) {
            s += var.getColourCode();
        }
        return s;
    }

    public Cell getTopLeftCell() {
        return cells[0];
    }

    public Cell getTopRightCell() {
        return cells[1];
    }

    public Cell getBottomLeftCell() {
        return cells[2];
    }

    public Cell getBottomRightCell() {
        return cells[3];
    }

}
