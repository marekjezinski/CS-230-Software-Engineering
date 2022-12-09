package CS230.npc;

import CS230.Tile;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SmartThiefSearch {
    public static List<int[]> path = new LinkedList<>();
    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; //used to iterate through all the directions of grid

    public static boolean bfs(Tile[][] tiles, int startRow, int startCol, int goalRow, int goalCol) {
        int rows = tiles.length;
        int cols = tiles[0].length;

        boolean[][] visited = new boolean[rows][cols];

        // create a queue for BFS
        Queue<int[]> queue = new LinkedList<>();


        // mark the start cell as visited and enqueue it
        visited[startRow][startCol] = true;
        queue.add(new int[]{startRow, startCol, -1, -1});

        while (!queue.isEmpty()) {
            // dequeue the current cell
            int[] curr = queue.poll();
            int currRow = curr[0];
            int currCol = curr[1];

            // add the current cell to the path
            path.add(curr);

            // check if we have reached the goal cell
            if (currRow == goalRow && currCol == goalCol) {
                return true;
            }

            // iterate through the four possible directions
            for (int[] dir : DIRS) {
                int nextRow = currRow + dir[0];
                int nextCol = currCol + dir[1];

                // check if the next cell is valid, not visited, and has at least one common color with the current cell
                if (nextRow >= 0 && nextRow < rows && nextCol >= 0 && nextCol < cols && !visited[nextRow][nextCol] &&
                        tiles[nextRow][nextCol].isLegalJump(tiles[currRow][currCol])) {
                    // mark the cell as visited and enqueue it
                    visited[nextRow][nextCol] = true;
                    queue.add(new int[]{nextRow, nextCol, currRow, currCol});
                }
            }
        }

        // if reached here, no path to the goal
        return false;
    }
}
