package CS230.npc;


import CS230.Map;
import CS230.items.Loot;
import CS230.Tile;

import java.util.ArrayList;
import java.util.LinkedList;

public class SmartThiefPath {

    private ArrayList<Loot> currentLoot;
    private Map currentLevel;
    private Tile[][] currentTileArray;
    private int pathGoalX,pathGoalY;
    //queue

    public SmartThiefPath(Map mapForLoots){
        currentLoot = mapForLoots.getLoots();
        currentLevel = mapForLoots;
        currentTileArray = mapForLoots.getTilesArray();
    }

    //find the closest loot by calculating x & y distance from smartThief and sets it as the goal
    public void findClosestLoot(SmartThief s){
        int xDist,yDist;
        int minDist = 999;
        Loot closest = null;
        for (Loot l: currentLoot) {
            xDist = s.getXPos() - l.getX();
            yDist = s.getYPos() - l.getY();
            int dist = xDist + yDist;
            if (dist < minDist){
                closest = l;
                minDist = dist;
            }

        }

        if (closest != null){ //set the goal and also print its coords - for testing
            setPathGoal(closest.getX(), closest.getY());
            System.out.println("X: "+closest.getX()+" Y:"+closest.getY());
        }

    }

    public int getPathGoalX() {
        return pathGoalX;
    }

    public int getPathGoalY() {
        return pathGoalY;
    }

    public void setPathGoal(int x, int y){
        this.pathGoalX = x;
        this.pathGoalY = y;
    }
     //-- checks if theres a valid move between the 2 tiles before adding to queue
    public boolean isValid(int x1,int y1,int x2, int y2){
        Tile current = currentTileArray[x1][y1];
        Tile nextTile =currentTileArray[x2][y2];

        //returns whether smart thief can move to tile or not
        return  current.isLegalMovement(nextTile);

    }




    //public void BFSsearch -- search whole map + add path to queue while item exists in arraylist

    /*public static void main(String[] args){ for testing
        Map level1 = new Map("cs230/15x10.txt");
        SmartThief s = new SmartThief(0,0);
        SmartThiefPath smartThiefPath = new SmartThiefPath(level1);
        smartThiefPath.findClosestLoot(s);
    }*/
    //public void valid(){}
}
