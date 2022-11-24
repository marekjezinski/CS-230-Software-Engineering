package com.example.cs230;

import java.net.URISyntaxException;

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

    public Cell[] getCellsArray() {
        return cells;
    }

    public Cell getTopLeft() {
        return cells[0];
    }

    public Cell getTopRight() {
        return cells[1];
    }

    public Cell getBottomLeft() {
        return cells[2];
    }

    public Cell getBottomRight() {
        return cells[3];
    }
}
