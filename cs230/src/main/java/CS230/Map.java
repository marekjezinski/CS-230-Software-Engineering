package CS230;

public class Map {
    private MapReader mapRead = null;

    private final int MAP_MAX_X;
    private final int MAP_MAX_Y;
    private Tile[][] tilesArray; //contains every tile (that is tile with 4 cells)

    public Map(String fileName) {
        mapRead = new MapReader(fileName);
        MAP_MAX_X = mapRead.getMaxTileX();
        MAP_MAX_Y = mapRead.getMaxTileY();
        tilesArray = mapRead.getTiles();
    }

    public int moveRight(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for(int x = playerX + 1; x < MAP_MAX_X; x++) {
            if(currentTile.isLegalMovement(tilesArray[x][playerY])) {
                return x * 2;
            }
        }
        return playerX * 2;
    }

    public int moveLeft(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for(int x = playerX - 1; x >= 0; x--) {
            if(currentTile.isLegalMovement(tilesArray[x][playerY])) {
                return x * 2;
            }
        }
        return playerX * 2;
    }

    public int moveDown(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for(int y = playerY + 1; y < MAP_MAX_Y; y++) {
            if(currentTile.isLegalMovement(tilesArray[playerX][y])) {
                return y * 2;
            }
        }
        return playerY * 2;
    }

    public int moveUp(int playerX, int playerY) {
        playerX = playerX / 2;
        playerY = playerY / 2;
        Tile currentTile = tilesArray[playerX][playerY];
        for(int y = playerY - 1; y >= 0; y--) {
            if(currentTile.isLegalMovement(tilesArray[playerX][y])) {
                return y * 2;
            }
        }
        return playerY * 2;
    }

    public Cell[][] getCellsArray() {
        return convertTilesToCellsArray(tilesArray);
    }

    private Cell[][] convertTilesToCellsArray(Tile[][] tilesArray) {
        Cell[][] cellsArray = new Cell[MAP_MAX_X * 2][MAP_MAX_Y * 2];
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


    //private ArrayList<Integer> clockParams = new ArrayList<Integer>();
}
