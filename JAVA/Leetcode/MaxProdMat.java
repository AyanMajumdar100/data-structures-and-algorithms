/*
Problem: 1594. Maximum Non Negative Product in a Matrix

You start at (0,0) and can only move:
→ right OR down

Goal:
Find a path to (m-1, n-1) such that:
- Product is maximum
- Product is NON-NEGATIVE

If no such path exists → return -1

--------------------------------------------------

DETAILED LOGIC:

⚠️ Key Challenge:
Multiplication with NEGATIVE numbers flips sign.

Example:
- A large negative value can become a large positive value later
  if multiplied by another negative.

So tracking only MAX is NOT enough.

--------------------------------------------------

💡 Core Idea:
At every cell, we track TWO values:
1. maxDp[i][j] → maximum product till this cell
2. minDp[i][j] → minimum product till this cell

Why min?
Because:
(min negative) × (negative) = large positive → future max

--------------------------------------------------

TRANSITION:

From top (i-1, j) and left (i, j-1):

We compute:
p1 = max(top) * current
p2 = min(top) * current
p3 = max(left) * current
p4 = min(left) * current

Then:
maxDp[i][j] = max(p1, p2, p3, p4)
minDp[i][j] = min(p1, p2, p3, p4)

--------------------------------------------------

BASE CASE:
At (0,0):
maxDp = minDp = grid[0][0]

--------------------------------------------------

FIRST ROW & COLUMN:
Only one path possible → just keep multiplying

--------------------------------------------------

FINAL ANSWER:
- If maxDp[m-1][n-1] < 0 → return -1
- Else return maxDp % (1e9 + 7)

--------------------------------------------------

Time Complexity: O(m * n)
Space Complexity: O(m * n)
*/

import java.util.*;

class MaxProdMat {
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

        // First column (only downward movement possible)
        for (int i = 1; i < m; i++) {
            maxDp[i][0] = maxDp[i - 1][0] * grid[i][0];
            minDp[i][0] = minDp[i - 1][0] * grid[i][0];
        }

        // First row (only right movement possible)
        for (int j = 1; j < n; j++) {
            maxDp[0][j] = maxDp[0][j - 1] * grid[0][j];
            minDp[0][j] = minDp[0][j - 1] * grid[0][j];
        }

        // Fill DP tables
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {

                long p1 = maxDp[i - 1][j] * grid[i][j];
                long p2 = minDp[i - 1][j] * grid[i][j];
                long p3 = maxDp[i][j - 1] * grid[i][j];
                long p4 = minDp[i][j - 1] * grid[i][j];

                // Choose best and worst among all possibilities
                maxDp[i][j] = Math.max(Math.max(p1, p2), Math.max(p3, p4));
                minDp[i][j] = Math.min(Math.min(p1, p2), Math.min(p3, p4));
            }
        }

        // Final result
        if (maxDp[m - 1][n - 1] < 0) {
            return -1;
        }

        return (int) (maxDp[m - 1][n - 1] % 1_000_000_007);
    }
}