/*
Problem Statement:
You are given an m x n grid where each cell contains one of the values 0, 1, or 2. 
You are also given an integer k.

You start from the top-left corner (0, 0) and want to reach the bottom-right corner 
(m - 1, n - 1) by moving only right or down.

Each cell contributes a specific score and incurs an associated cost:
0: adds 0 to your score and costs 0.
1: adds 1 to your score and costs 1.
2: adds 2 to your score and costs 1.

Return the maximum score achievable without exceeding a total cost of k, 
or -1 if no valid path exists.
Note: If you reach the last cell but the total cost exceeds k, the path is invalid.

Constraints:
1 <= m, n <= 200
0 <= k <= 1000
grid[0][0] == 0
0 <= grid[i][j] <= 2
*/

import java.util.Scanner;

public class MaxPathScoreGrid {

    public static int maxPathScore(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        
        // prev[j][c] stores the max score at column j with total cost c for the previous row
        int[][] prev = new int[n][k + 1];
        
        // Initialize all states to -1 (unreachable)
        for (int j = 0; j < n; j++) {
            for (int c = 0; c <= k; c++) {
                prev[j][c] = -1;
            }
        }
        
        // Base case: starting point has 0 cost and 0 score
        prev[0][0] = 0;
        
        for (int i = 0; i < m; i++) {
            // curr[j][c] stores the max score at column j with total cost c for the current row
            int[][] curr = new int[n][k + 1];
            for (int j = 0; j < n; j++) {
                for (int c = 0; c <= k; c++) {
                    curr[j][c] = -1;
                }
            }
            
            for (int j = 0; j < n; j++) {
                // Skip the starting cell as it is pre-configured
                if (i == 0 && j == 0) {
                    curr[0][0] = 0;
                    continue;
                }
                
                // Determine the cost and score for stepping into the current cell
                int cost = grid[i][j] == 0 ? 0 : 1;
                int score = grid[i][j];
                
                // We only check states where we can at least afford the current cell's cost
                for (int c = cost; c <= k; c++) {
                    int maxScore = -1;
                    
                    // Try coming from the cell above (if not in the first row)
                    if (i > 0 && prev[j][c - cost] != -1) {
                        maxScore = Math.max(maxScore, prev[j][c - cost] + score);
                    }
                    
                    // Try coming from the cell to the left (if not in the first column)
                    if (j > 0 && curr[j - 1][c - cost] != -1) {
                        maxScore = Math.max(maxScore, curr[j - 1][c - cost] + score);
                    }
                    
                    // Update the state with the best possible score
                    curr[j][c] = maxScore;
                }
            }
            // Move to the next row
            prev = curr;
        }
        
        // Find the maximum score at the bottom-right cell across all valid costs
        int ans = -1;
        for (int c = 0; c <= k; c++) {
            if (prev[n - 1][c] > ans) {
                ans = prev[n - 1][c];
            }
        }
        
        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter number of rows and columns (e.g., '2 2'):");
            String[] dims = scanner.nextLine().trim().split("\\s+");
            int m = Integer.parseInt(dims[0]);
            int n = Integer.parseInt(dims[1]);
            
            int[][] grid = new int[m][n];
            System.out.println("Enter the grid rows (values 0, 1, or 2 separated by spaces):");
            for (int i = 0; i < m; i++) {
                String[] rowStrs = scanner.nextLine().trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    grid[i][j] = Integer.parseInt(rowStrs[j]);
                }
            }
            
            System.out.println("Enter the maximum allowed cost (k):");
            int k = Integer.parseInt(scanner.nextLine().trim());
            
            int result = maxPathScore(grid, k);
            System.out.println("Maximum Achievable Score: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected. Please try again.");
        } finally {
            scanner.close();
        }
    }
}