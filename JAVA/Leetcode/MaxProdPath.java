/*
Problem: 1594. Maximum Non Negative Product in a Matrix

You start at (0,0) and can move only:
→ right OR down

Goal:
Find the path to (m-1, n-1) such that:
- Product is MAXIMUM
- Product is NON-NEGATIVE

If no such path → return -1

--------------------------------------------------

DETAILED LOGIC:

Why normal DP fails?
- Because of NEGATIVE numbers

Example:
(-2) * (-3) = +6 (good)
But picking min earlier might give max later!

So we must track BOTH:
1. Maximum product till this cell
2. Minimum product till this cell

--------------------------------------------------

DP Definition:

maxDp[i][j] → maximum product reaching (i,j)
minDp[i][j] → minimum product reaching (i,j)

Why min?
Because:
min * negative → can become max

--------------------------------------------------

Transition:

From top (i-1, j) and left (i, j-1):

We consider 4 possibilities:
- max * current
- min * current

Then:
maxDp[i][j] = max(all possibilities)
minDp[i][j] = min(all possibilities)

--------------------------------------------------

Base Case:
dp[0][0] = grid[0][0]

--------------------------------------------------

Final Answer:
- If maxDp[m-1][n-1] < 0 → return -1
- Else → return maxDp % (1e9 + 7)

--------------------------------------------------

Time Complexity: O(m * n)
Space Complexity: O(m * n)
*/

import java.util.*;

class MaxProdPath {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m = sc.nextInt();
        int n = sc.nextInt();

        int[][] grid = new int[m][n];

        // Input grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        System.out.println(maxProductPath(grid));

        sc.close();
    }

    public static int maxProductPath(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        long[][] maxDp = new long[m][n];
        long[][] minDp = new long[m][n];

        // Base case
        maxDp[0][0] = grid[0][0];
        minDp[0][0] = grid[0][0];

        // First column
        for (int i = 1; i < m; i++) {
            maxDp[i][0] = maxDp[i - 1][0] * grid[i][0];
            minDp[i][0] = maxDp[i][0];
        }

        // First row
        for (int j = 1; j < n; j++) {
            maxDp[0][j] = maxDp[0][j - 1] * grid[0][j];
            minDp[0][j] = maxDp[0][j];
        }

        // Fill DP tables
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {

                long a = maxDp[i - 1][j] * grid[i][j];
                long b = minDp[i - 1][j] * grid[i][j];
                long c = maxDp[i][j - 1] * grid[i][j];
                long d = minDp[i][j - 1] * grid[i][j];

                maxDp[i][j] = Math.max(Math.max(a, b), Math.max(c, d));
                minDp[i][j] = Math.min(Math.min(a, b), Math.min(c, d));
            }
        }

        long res = maxDp[m - 1][n - 1];

        if (res < 0) return -1;

        return (int)(res % 1_000_000_007);
    }
}