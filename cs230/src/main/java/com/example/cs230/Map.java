package com.example.cs230;

public class Map {
    private MapReader mapRead = null;

    private final int MAP_MAX_X;
    private final int MAP_MAX_Y;
    private Tile[][] tilesArray;
    private Cell[][] cellsArray;

    public Cell[][] getCellsArray() {
        return cellsArray;
    }

    public int getMAP_MAX_X() {
        return MAP_MAX_X;
    }

    public int getMAP_MAX_Y() {
        return MAP_MAX_Y;
    }

    public Tile[][] getTilesArray() {
        return tilesArray;
    }

    private Cell[][] convertTilesToCellsArray(Tile[][] tilesArray) {
        cellsArray = new Cell[MAP_MAX_X * 2][MAP_MAX_Y * 2];
        for(int y = 0; y < MAP_MAX_Y; y++) {
            for(int x = 0; x < MAP_MAX_X; x++) {
                cellsArray[x * 2][y * 2] = tilesArray[x][y].getTopLeftCell();
                cellsArray[x * 2 + 1][y * 2] = tilesArray[x][y].getTopRightCell();
                cellsArray[x * 2][y * 2 + 1] = tilesArray[x][y].getBottomLeftCell();
                cellsArray[x * 2 + 1][y * 2 + 1] = tilesArray[x][y].getBottomRightCell();
            }
        }
        return cellsArray;
    }
    public Map(String fileName) {
        mapRead = new MapReader(fileName);
        MAP_MAX_X = mapRead.getMaxTileX();
        MAP_MAX_Y = mapRead.getMaxTileY();
        tilesArray = mapRead.getTiles();
        cellsArray = convertTilesToCellsArray(tilesArray);
    }

    //private ArrayList<Integer> clockParams = new ArrayList<Integer>();
}
