package com.example.cs230;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class MapReader {
    private Tile[][] tiles = null;
    private int maxTileX;
    private int maxTileY;
    String fileName;
    //private ArrayList<Integer> clockParam = new ArrayList<Integer>();
    public MapReader(String fileName) {
        this.fileName = fileName;
        Scanner in = null;
        File f = new File(this.fileName);
        try {
            in = new Scanner(f);
        }
        catch(FileNotFoundException e) {
            System.err.println(String.format("Map file '%s' not found!", this.fileName));
            System.exit(0);
        }

        try {
            maxTileX = in.nextInt();
            maxTileY = in.nextInt();
            tiles = new Tile[maxTileX][maxTileY];
            for(int y = 0; y < maxTileY; y++) {
                for(int x = 0; x < maxTileX; x++) {
                    String word = in.next();
                    if(word.length() == 4) {
                        tiles[x][y] = new Tile(word);
                    } else {
                        System.err.println("Level file tile shorter/longer than 4 characters detected");
                        throw new Exception();
                    }
                }
            }
        }
        catch(Exception e) {
            System.err.println("Please check level file!");
            System.exit(1);
        }
        in.close();
    }
    public int getMaxTileX() {
        return maxTileX;
    }
    public int getMaxTileY() {
        return maxTileY;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

//    public ArrayList<Integer> clockSetter(){
//        Scanner in = null;
//        File f = new File("l1.txt");
//        try {
//            in = new Scanner(f);
//        }
//        catch(FileNotFoundException e){
//            System.out.println("Error!");
//            System.exit(0);
//        }
//        //97
//        for (int i = 0; i <= 95; i++){
//            in.next();
//        }
//        while (in.hasNext()) {
//             x = in.nextInt();
//             y = in.nextInt();
//             this.clockParam.add(x);
//             this.clockParam.add(y);
//        }
//        return this.clockParam;
//    }
    }

