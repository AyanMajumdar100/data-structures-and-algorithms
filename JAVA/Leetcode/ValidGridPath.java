/*
Problem Statement:
You are given an m x n grid. Each cell of grid represents a street. The street of grid[i][j] can be:
1: a street connecting the left cell and the right cell.
2: a street connecting the upper cell and the lower cell.
3: a street connecting the left cell and the lower cell.
4: a street connecting the right cell and the lower cell.
5: a street connecting the left cell and the upper cell.
6: a street connecting the right cell and the upper cell.

You will initially start at the street of the upper-left cell (0, 0). A valid path in the grid is a path 
that starts from the upper left cell (0, 0) and ends at the bottom-right cell (m - 1, n - 1). 
The path should only follow the streets. Notice that you are not allowed to change any street.
Return true if there is a valid path in the grid or false otherwise.

Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 300
1 <= grid[i][j] <= 6
*/

import java.util.Scanner;

public class ValidGridPath {

    public static boolean hasValidPath(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        // Edge case: Grid is just a single cell, so we are already at the destination
        if (m == 1 && n == 1) {
            return true;
        }
        
        boolean[][] visited = new boolean[m][n];
        // Flat array used as a highly optimized queue for BFS
        int[] q = new int[m * n];
        int head = 0;
        int tail = 0;
        
        q[tail++] = 0;
        visited[0][0] = true;
        
        // Directions represent: 0: Up, 1: Right, 2: Down, 3: Left
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};
        
        // Bitmask representations of the 6 street types.
        // The binary bits (from right to left) represent Up(0), Right(1), Down(2), Left(3).
        // e.g., Street type 1 (Left & Right) uses bit 1 and bit 3 -> 1010 in binary -> 10 in decimal.
        // Masks index maps directly to the street piece number (1 to 6).
        int[] masks = {0, 10, 5, 12, 6, 9, 3};
        
        while (head < tail) {
            int curr = q[head++];
            int r = curr / n;
            int c = curr % n;
            
            int mask = masks[grid[r][c]];
            
            // Check all 4 possible directions to move out of the current cell
            for (int d = 0; d < 4; d++) {
                // If the current street piece has an opening in direction 'd'
                if ((mask & (1 << d)) != 0) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    
                    // Check bounds and whether we've visited the cell
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n && !visited[nr][nc]) {
                        int nMask = masks[grid[nr][nc]];
                        
                        // The opposite direction from 'd' (e.g., if we moved Right(1), opposite is Left(3))
                        int op = (d + 2) % 4;
                        
                        // If the NEXT street piece has an opening facing us (the opposite direction)
                        if ((nMask & (1 << op)) != 0) {
                            // If we reached the target cell, return true immediately
                            if (nr == m - 1 && nc == n - 1) {
                                return true;
                            }
                            visited[nr][nc] = true;
                            q[tail++] = nr * n + nc;
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
            System.out.println("Enter number of rows and columns (e.g. '2 3'):");
            String[] dims = scanner.nextLine().trim().split("\\s+");
            int m = Integer.parseInt(dims[0]);
            int n = Integer.parseInt(dims[1]);
            
            int[][] grid = new int[m][n];
            System.out.println("Enter the grid rows (numbers separated by spaces):");
            for (int i = 0; i < m; i++) {
                String[] rowStrs = scanner.nextLine().trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    grid[i][j] = Integer.parseInt(rowStrs[j]);
                }
            }
            
            boolean result = hasValidPath(grid);
            System.out.println("Has Valid Path: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected. Please try again.");
        } finally {
            scanner.close();
        }
    }
}