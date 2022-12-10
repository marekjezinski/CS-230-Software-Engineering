package CS230.npc;


import CS230.Map;
import CS230.items.Loot;
import CS230.Tile;

import java.util.ArrayList;

//unsure about what this class does

/**
 * Class that creates a path for the smart thief to follow
 * @author Caleb Ocansey
 * @version 1.0
 */
public class SmartThiefPath {

    private ArrayList<Loot> currentLoot;
    private Tile[][] currentTileArray;
    private int pathGoalX,pathGoalY;
    //queue

    /**
     * constructor
     * @param currentLoot gets current loot
     */
    public SmartThiefPath(Map currentLvl){
        this.currentLoot = currentLvl.getLoots();
        currentTileArray = currentLvl.getTilesArray();
    }

    /**
     * method that finds the closest loot by calculating x and y distance from
     * the Smart Thief NPC and sets it as the goal
     * @param s - a smartAssassin
     */
    public void findClosestLoot(SmartThief s){
        int xDist,yDist;
        int minDist = 999;
        Loot closest = null;
        for (Loot l: this.currentLoot) {
            System.out.println("L val: "+l.getLootValue());
            xDist = Math.abs(s.getX() - l.getX());
            yDist = Math.abs(s.getY() - l.getY());
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

    /**
     * method that gets the x coordinate of the pathGoal
     * @return pathGoalX
     */
    public int getPathGoalX() {
        return pathGoalX;
    }

    /**
     * method that gets the y coordinate of the pathGoal
     * @return pathGoalY
     */
    public int getPathGoalY() {
        return pathGoalY;
    }

    /**
     * method that sets the PathGoal
     * @param x
     * @param y
     */
    public void setPathGoal(int x, int y){
        this.pathGoalX = x;
        this.pathGoalY = y;
    }

    /**
     * method that checks if there's a valid move between 2 tiles before adding to queue
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return boolean, whether the move can be made or not
     */
    public boolean isValid(int x1,int y1,int x2, int y2){
        Tile current = currentTileArray[x1][y1];
        Tile nextTile =currentTileArray[x2][y2];

        //returns whether smart thief can move to tile or not
        return  current.isLegalJump(nextTile);

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
