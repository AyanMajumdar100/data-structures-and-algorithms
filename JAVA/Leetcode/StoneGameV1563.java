/*
 * Problem Statement:
 * There are several stones arranged in a row with values in stoneValue.
 * In each round, Alice divides the row into two non-empty rows: left and right.
 * Bob calculates the sum of each row and throws away the row with the strictly maximum sum.
 * Alice's score increases by the sum of the remaining row, and the game continues with it.
 * If both rows have equal sum, Alice decides which one Bob throws away.
 * The game ends when only one stone remains. Return the maximum score Alice can obtain.
 */

/*
 * Approach: Interval Dynamic Programming + Prefix Sums (O(N^3) Time, O(N^2) Space)
 * 1. Prefix Sums:
 *    Precompute `pre[i]` = sum of elements in `stoneValue[0...i-1]` so that the sum of any 
 *    subarray `stoneValue[l...r]` is calculated in O(1) time as `pre[r + 1] - pre[l]`.
 * 2. DP State Definition:
 *    Let `dp[i][j]` represent the maximum score Alice can achieve from the subarray `stoneValue[i...j]`.
 * 3. Base Case:
 *    When `i == j` (a single stone remains), `dp[i][i] = 0` (no moves possible).
 * 4. Recurrence Relation:
 *    For every subarray `stoneValue[i...j]` of length `len` from 2 to `n`, test every split point `k` (i <= k < j):
 *    - `leftSum = pre[k + 1] - pre[i]`
 *    - `rightSum = pre[j + 1] - pre[k + 1]`
 *    - If `leftSum < rightSum`: Bob keeps the left row -> score gain = `leftSum + dp[i][k]`.
 *    - If `leftSum > rightSum`: Bob keeps the right row -> score gain = `rightSum + dp[k + 1][j]`.
 *    - If `leftSum == rightSum`: Alice chooses the optimal side -> score gain = `leftSum + max(dp[i][k], dp[k + 1][j])`.
 *    `dp[i][j] = max(all possible score gains across valid split points k)`.
 * 5. Result:
 *    `dp[0][n - 1]` yields the maximum total score for the full row.
 */

import java.util.Scanner;

public class StoneGameV1563 {
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;

        // Step 1: Prefix sum array for O(1) range sum queries
        int[] pre = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] + stoneValue[i];
        }

        // Step 2: dp[i][j] stores maximum score Alice can achieve from stoneValue[i...j]
        int[][] dp = new int[n][n];

        // Step 3: Process intervals by length, from 2 up to n
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                int maxScore = 0;

                // Test every possible split point k
                for (int k = i; k < j; k++) {
                    int leftSum = pre[k + 1] - pre[i];
                    int rightSum = pre[j + 1] - pre[k + 1];

                    if (leftSum < rightSum) {
                        maxScore = Math.max(maxScore, leftSum + dp[i][k]);
                    } else if (leftSum > rightSum) {
                        maxScore = Math.max(maxScore, rightSum + dp[k + 1][j]);
                    } else {
                        maxScore = Math.max(maxScore, leftSum + Math.max(dp[i][k], dp[k + 1][j]));
                    }
                }
                dp[i][j] = maxScore;
            }
        }

        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter stone values separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Maximum score: 0");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userStoneValues = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userStoneValues[i] = Integer.parseInt(tokens[i]);
        }

        StoneGameV1563 solverInstance = new StoneGameV1563();
        int maxAliceScore = solverInstance.stoneGameV(userStoneValues);

        System.out.println("Maximum score Alice can obtain: " + maxAliceScore);
        scanner.close();
    }
}
