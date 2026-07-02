/*
 * Problem Statement:
 * You are given an m x n binary matrix grid and an integer health.
 * You start on the upper-left corner (0, 0) and would like to get to the lower-right corner (m - 1, n - 1).
 * Cells (i, j) with grid[i][j] = 1 reduce your health by 1.
 * Return true if you can reach the final cell with a health value of 1 or more, and false otherwise.
 */

/*
 * Approach: Dijkstra's Algorithm / Max-Priority Queue Simulation
 * We can treat this problem as finding a path on a grid that maximizes the remaining health. 
 * Since moving to an unsafe cell cost 1 health point and moving to a safe cell costs 0, 
 * this can be framed as finding a path with minimum total cost (health damage).
 * * 1. Maintain a 2D array `maxHealth` to store the maximum possible health remaining at each cell (r, c). 
 * Initialize all cells with -1.
 * 2. Deduct the cost of the starting cell (0, 0) from the initial health pool. If it drops to 0 or below, 
 * return false immediately.
 * 3. Use a Max-Priority Queue to always expand the path node that currently has the HIGHEST remaining health.
 * 4. For each cell, explore its 4 neighbors, calculate the next health state, and update `maxHealth[nr][nc]` 
 * if the new path yields strictly more health than previously recorded.
 * 5. If we successfully pop the target cell (m - 1, n - 1), we check if its health is >= 1.
 */
import java.util.List;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class SafeWalkGrid3286 {
    public boolean findSafeWalk(List<List<Integer>> grid, int health) {
        int m = grid.size();
        int n = grid.get(0).size();
        
        // Track the maximum health we can maintain upon reaching each grid tile
        int[][] maxHealth = new int[m][n];
        for (int[] row : maxHealth) {
            Arrays.fill(row, -1);
        }
        
        // Max-heap configuration based on remaining health at index 2
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(b[2], a[2]));
        
        // Initial state evaluation for the starting corner cell
        int startHealth = health - grid.get(0).get(0);
        if (startHealth <= 0) {
            return false;
        }
        
        maxHealth[0][0] = startHealth;
        pq.offer(new int[]{0, 0, startHealth});
        
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int r = curr[0];
            int c = curr[1];
            int h = curr[2];
            
            // Short-circuit optimization: First time reaching destination from max-heap yields optimal max health
            if (r == m - 1 && c == n - 1) {
                return h >= 1;
            }
            
            // Stale element check: If we found a path with better health already, skip this node
            if (h < maxHealth[r][c]) {
                continue;
            }
            
            // Traverse adjacent direction coordinates
            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];
                
                if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                    int nextHealth = h - grid.get(nr).get(nc);
                    // Push onto heap only if it offers a healthier path sequence to (nr, nc)
                    if (nextHealth > maxHealth[nr][nc]) {
                        maxHealth[nr][nc] = nextHealth;
                        pq.offer(new int[]{nr, nc, nextHealth});
                    }
                }
            }
        }
        
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter matrix row count M and column count N:");
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        
        System.out.println("Enter initial health pool value:");
        int health = scanner.nextInt();
        
        List<List<Integer>> grid = new ArrayList<>();
        System.out.println("Enter matrix values (0 or 1) row by row:");
        for (int i = 0; i < m; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(scanner.nextInt());
            }
            grid.add(row);
        }
        
        SafeWalkGrid3286 solver = new SafeWalkGrid3286();
        boolean result = solver.findSafeWalk(grid, health);
        System.out.println("Can complete safe walk path? " + result);
        
        scanner.close();
    }
}
