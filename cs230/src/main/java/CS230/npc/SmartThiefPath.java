package CS230.npc;


import CS230.map.Map;
import CS230.items.Loot;
import CS230.map.Tile;

import java.util.ArrayList;

//unsure about what this class does

/**
 * Class that finds the current goal (item) for the smart thief to follow
 * @author Caleb Ocansey
 * @version 1.0
 */
public class SmartThiefPath {

    private ArrayList<Loot> currentLoot;
    private Tile[][] currentTileArray;
    private int pathGoalX,pathGoalY;
    private Loot closest;

    /**
     *the path of smartthief
     * @param currentLevel the map of current level
     */
    public SmartThiefPath(Map currentLevel){
        this.currentLoot = currentLevel.getLoots();
        currentTileArray = currentLevel.getTilesArray();
    }

    /**
     *
     * @param currentLevel the current level of the game
     * @param s the SmartThief
     * @return closest loot or nothing if no Loot is found
     */
    public Loot findClosestLoot(Map currentLevel, SmartThief s){
        int xDist,yDist;
        int minDist = 999;
        Loot closest = null;
        for (Loot l: currentLevel.getLoots()) {
            xDist = Math.abs(s.getX() - l.getX()); // calculates distance from x
            yDist = Math.abs(s.getY() - l.getY()); // calculates distance from y
            int dist = xDist + yDist;
            if (dist < minDist){
                closest = l;
                minDist = dist;
            }

        }

        if (closest != null){ //set the goal and also return the closest item
            setPathGoal(closest.getX(), closest.getY());
            return closest;
        }
        return null;
    }

    /**
     * method that gets the x coordinate of the pathGoal
     * @return pathGoalX the x coordinate of goal
     */
    public int getPathGoalX() {
        return pathGoalX;
    }

    /**
     * method that gets the y coordinate of the pathGoal
     * @return pathGoalY the y coordinate of goal
     */
    public int getPathGoalY() {
        return pathGoalY;
    }

    /**
     * method that sets the path goal which will
     * be used by SmartThiefSearch to get the shortest path
     * @param x the x of the path goal
     * @param y the y of the path goal
     */
    public void setPathGoal(int x, int y){
        this.pathGoalX = x;
        this.pathGoalY = y;
    }

    /**
     * method that checks if there's a valid move
     * between 2 tiles before adding to queue
     * @param x1 the x coordinate of the current tile
     * @param y1 the y coordinate of the current tile
     * @param x2 the x coordinate of the desired next tile
     * @param y2 the y coordinate of the desired next tile
     * @return boolean, whether the move can be made or not
     */
    public boolean isValid(int x1,int y1,int x2, int y2){
        Tile current = currentTileArray[x1][y1];
        Tile nextTile =currentTileArray[x2][y2];

        //returns whether smart thief can move to tile or not
        return  current.isLegalJump(nextTile);

    }

}
