/*
Problem: 3418. Maximum Amount of Money Robot Can Earn

You are given an m x n grid where:
- Positive values = coins gained
- Negative values = coins lost (robbers)

Robot starts at (0,0) and can move only RIGHT or DOWN.

Special power:
- Robot can neutralize robbers in at most 2 cells (ignore negative value)

Goal:
Return the maximum coins robot can collect.

-----------------------------------------------------

LOGIC EXPLANATION (IMPORTANT):

This is a DP problem with an extra constraint (at most 2 neutralizations).

We use a 3D DP array:
dp[i][j][k] → max coins at cell (i,j) using k neutralizations

k = 0 → no neutralization used
k = 1 → used once
k = 2 → used twice

Why 3D DP?
Because reaching same cell with different neutralization counts gives different results.

-----------------------------------------------------

TRANSITIONS:

1. NORMAL MOVE (no neutralization):
   From top or left:
   dp[i][j][k] = max(prev) + coins[i][j]

2. USING NEUTRALIZATION (only if coins[i][j] < 0):
   Instead of adding negative value, we skip it:
   dp[i][j][k] = max(prev with k-1 neutralizations)

-----------------------------------------------------

KEY IDEA:

At each cell:
- Either take the value (gain or lose coins)
- Or neutralize robber (only if negative and k > 0)

-----------------------------------------------------

TIME COMPLEXITY: O(m * n * 3)
SPACE COMPLEXITY: O(m * n * 3)
*/

import java.util.*;

class MaxBot {  // short & unique class name

    public int maximumAmount(int[][] coins) {

        int m = coins.length;
        int n = coins[0].length;

        int[][][] dp = new int[m][n][3];

        // Using a very small value to represent impossible states
        int INF = (int) -1e9;

        // Initialize all states as impossible
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }

        // Starting point
        dp[0][0][0] = coins[0][0];

        // If first cell is negative, we can neutralize it
        if (coins[0][0] < 0) {
            dp[0][0][1] = 0;
        }

        // Fill DP table
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) continue;

                for (int k = 0; k < 3; k++) {

                    int maxPrev = INF;

                    // Coming from top
                    if (i > 0) {
                        maxPrev = Math.max(maxPrev, dp[i - 1][j][k]);
                    }

                    // Coming from left
                    if (j > 0) {
                        maxPrev = Math.max(maxPrev, dp[i][j - 1][k]);
                    }

                    // Case 1: Take the cell value normally
                    if (maxPrev != INF) {
                        dp[i][j][k] = Math.max(dp[i][j][k], maxPrev + coins[i][j]);
                    }

                    // Case 2: Neutralize robber (only if negative and we have k > 0)
                    if (k > 0 && coins[i][j] < 0) {

                        int maxPrevUse = INF;

                        if (i > 0) {
                            maxPrevUse = Math.max(maxPrevUse, dp[i - 1][j][k - 1]);
                        }

                        if (j > 0) {
                            maxPrevUse = Math.max(maxPrevUse, dp[i][j - 1][k - 1]);
                        }

                        // If we neutralize, we DO NOT add coins[i][j]
                        if (maxPrevUse != INF) {
                            dp[i][j][k] = Math.max(dp[i][j][k], maxPrevUse);
                        }
                    }
                }
            }
        }

        // Final answer = best among all 3 neutralization states
        return Math.max(dp[m - 1][n - 1][0],
                Math.max(dp[m - 1][n - 1][1], dp[m - 1][n - 1][2]));
    }


    // ----------- USER INPUT DRIVER CODE -----------
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Input rows and columns
        int m = sc.nextInt();
        int n = sc.nextInt();

        int[][] coins = new int[m][n];

        // Input grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                coins[i][j] = sc.nextInt();
            }
        }

        MaxBot obj = new MaxBot();
        int result = obj.maximumAmount(coins);

        System.out.println(result);

        sc.close(); // good practice
    }
}