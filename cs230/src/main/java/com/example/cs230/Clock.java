package com.example.cs230;
import java.util.ArrayList;
public class Clock {
    private String fileName;

    public Clock(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Integer> clockSetter(){
        ArrayList<Integer> params = new ArrayList<Integer>();
        MapReader c = new MapReader(fileName);
        params = c.getClock();
        return params;
    }
}
