/*
799. Champagne Tower

We stack glasses in a pyramid, where the first row has 1 glass,
the second row has 2 glasses, and so on until the 100th row.
Each glass holds exactly 1 cup of champagne.

We pour some champagne into the top glass.
If a glass becomes full, the extra champagne spills equally
to the glass directly below-left and below-right.

If glasses in the last row overflow, the extra falls to the floor.

We need to return how full the glass at (query_row, query_glass)
is after pouring the champagne.
Rows and glasses are 0-indexed.

Examples:
Input: poured = 1, query_row = 1, query_glass = 1
Output: 0.00000

Input: poured = 2, query_row = 1, query_glass = 1
Output: 0.50000

Input: poured = 100000009, query_row = 33, query_glass = 17
Output: 1.00000

Constraints:
0 <= poured <= 10^9
0 <= query_glass <= query_row < 100
*/

import java.util.*;

class ChampagneTowerSolver {

    public double champagneTower(int poured, int query_row, int query_glass) {

        // I create a DP array to store how much champagne
        // reaches each glass in the current row.
        // Size is query_row + 2 so I never go out of bounds.
        double[] dp = new double[query_row + 2];

        // I pour everything into the top glass initially.
        dp[0] = poured;

        // I simulate row by row until I reach query_row.
        for (int r = 0; r < query_row; r++) {

            // I iterate backwards so I don't overwrite values
            // that are still needed in this row.
            for (int c = r; c >= 0; c--) {

                // I calculate overflow from current glass.
                // A glass holds 1 cup, so anything above 1 spills.
                double excess = (dp[c] - 1.0) / 2.0;

                if (excess > 0) {
                    // I send half overflow to right glass
                    dp[c + 1] += excess;

                    // I keep half overflow in current index
                    // which represents left child in next row
                    dp[c] = excess;
                } else {
                    // If no overflow, this position becomes empty
                    dp[c] = 0.0;
                }
            }
        }

        // A glass can be at most full (1.0)
        return Math.min(1.0, dp[query_glass]);
    }

    // ---------------- USER INPUT DRIVER ----------------

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter poured amount:");
        int poured = sc.nextInt();

        System.out.println("Enter query row:");
        int queryRow = sc.nextInt();

        System.out.println("Enter query glass:");
        int queryGlass = sc.nextInt();

        ChampagneTowerSolver solver = new ChampagneTowerSolver();
        double result = solver.champagneTower(poured, queryRow, queryGlass);

        System.out.println("Glass fullness: " + result);

        sc.close();
    }
}
