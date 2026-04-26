/*
Problem Statement:
Given a 2D array of characters grid of size m x n, you need to find if there exists any cycle consisting of the same value in grid.
A cycle is a path of length 4 or more in the grid that starts and ends at the same cell. 
From a given cell, you can move to one of the cells adjacent to it - in one of the four directions 
(up, down, left, or right), if it has the same value of the current cell.
You cannot move to the cell that you visited in your last move.

Return true if any cycle of the same value exists in grid, otherwise, return false.

Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 500
grid consists only of lowercase English letters.
*/

import java.util.Scanner;

public class GridCycleDetector {

    public static boolean containsCycle(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        
        // Custom queue implementation using flat arrays for maximum speed
        int[] q = new int[m * n];
        int[] parent = new int[m * n]; 
        
        // Direction array trick: (-1,0), (0,1), (1,0), (0,-1) represents Up, Right, Down, Left
        int[] d = {-1, 0, 1, 0, -1};
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // Only start a BFS from unvisited nodes
                if (!visited[i][j]) {
                    int head = 0;
                    int tail = 0;
                    
                    // Add starting node to queue (encode 2D coords to 1D)
                    q[tail] = i * n + j;
                    parent[tail++] = -1; // -1 means no parent
                    visited[i][j] = true;
                    
                    while (head < tail) {
                        int curr = q[head];
                        int p = parent[head++]; 
                        
                        int r = curr / n; // Decode row
                        int c = curr % n; // Decode column
                        
                        // Check all 4 adjacent neighbors
                        for (int k = 0; k < 4; k++) {
                            int nr = r + d[k];
                            int nc = c + d[k + 1];
                            
                            // If neighbor is within bounds and has the same character
                            if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == grid[r][c]) {
                                int next = nr * n + nc;
                                
                                // Ensure we are not stepping back to the immediate parent we just came from
                                if (next != p) {
                                    // If we visit an already visited cell (that isn't the parent), a cycle exists!
                                    if (visited[nr][nc]) {
                                        return true;
                                    }
                                    
                                    // Otherwise, mark visited and add to queue
                                    visited[nr][nc] = true;
                                    q[tail] = next;
                                    parent[tail++] = curr; // The parent of the next cell is the current cell
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter number of rows (m) and columns (n):");
            String[] dims = scanner.nextLine().trim().split("\\s+");
            int m = Integer.parseInt(dims[0]);
            int n = Integer.parseInt(dims[1]);
            
            char[][] grid = new char[m][n];
            System.out.println("Enter the grid row by row (characters continuously without spaces, e.g. 'aaaa'):");
            for (int i = 0; i < m; i++) {
                String rowStr = scanner.nextLine().trim();
                if (rowStr.length() != n) {
                    System.out.println("Row length does not match specified columns.");
                    return;
                }
                grid[i] = rowStr.toCharArray();
            }
            
            boolean result = containsCycle(grid);
            System.out.println("Contains Cycle: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected.");
        } finally {
            scanner.close();
        }
    }
}
