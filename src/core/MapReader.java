package com.example.cs230;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
public class MapReader {
    private ArrayList<String> tiles = new ArrayList<String>();

    public ArrayList<String> tileReader(){
        Scanner in = null;
        File f = new File("l1.txt");
        try {
            in = new Scanner(f);
        }
        catch(FileNotFoundException e){
            System.out.println("Error!");
            System.exit(0);
        }
        while (in.hasNext()){
            tiles.add(in.next());
        }
        return this.tiles;
    }
}
