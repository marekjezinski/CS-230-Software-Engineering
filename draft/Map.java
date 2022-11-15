public class Map {
    private final int SIZE_X;
    private final int SIZE_Y;
    private Tile[][] tileMap;

    public Map(int SIZE_X, int SIZE_Y) {
        this.SIZE_X = SIZE_X;
        this.SIZE_Y = SIZE_Y;
        for(int i = 0; i < SIZE_X; i++) {
            for(int j = 0; j < SIZE_Y; j++) {
                tileMap[i][j] = new Tile();
            }
        }
    }
   
    private void placeItem(Loot item) {
        // placing an item in a random tile
    }

    public void createLevel (FileReader fileName) {
        // create a level based on a text file using
    }
}
