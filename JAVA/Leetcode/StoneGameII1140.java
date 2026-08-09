/*
 * Problem Statement:
 * Alice and Bob play a game with piles of stones.
 * A player can take all stones in the first X remaining piles where 1 <= X <= 2M.
 * Then M is updated to max(M, X). Initially, M = 1 and Alice goes first.
 * Return the maximum number of stones Alice can get assuming optimal play.
 */

/*
 * Approach: Dynamic Programming with Suffix Sums (O(N^3) Time, O(N^2) Space)
 * 1. Suffix Sum Precomputation:
 *    `suffixSum[i]` stores the total sum of stones from index `i` to `n - 1`.
 * 2. DP State:
 *    Let `dp[i][m]` represent the maximum number of stones a player can collect starting at index `i` with parameter `m`.
 * 3. Base Case:
 *    If `i + 2 * m >= n`, the active player can take ALL remaining piles, yielding `suffixSum[i]`.
 * 4. Recurrence:
 *    Otherwise, for every choice of `X` where `1 <= X <= 2 * m`:
 *    - The opponent is left starting at index `i + X` with `nextM = max(m, X)`.
 *    - The current player gets `suffixSum[i] - dp[i + X][nextM]`.
 *    - `dp[i][m] = max(suffixSum[i] - dp[i + X][nextM])` for all valid `X`.
 */

import java.util.Scanner;

public class StoneGameII1140 {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        if (n == 0) return 0;

        // Step 1: Precompute suffix sums
        int[] suffixSum = new int[n];
        suffixSum[n - 1] = piles[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }

        // Step 2: Initialize DP table
        int[][] dp = new int[n][n + 1];

        // Step 3: Fill DP table bottom-up from right to left
        for (int i = n - 1; i >= 0; i--) {
            for (int m = 1; m <= n; m++) {
                if (i + 2 * m >= n) {
                    dp[i][m] = suffixSum[i];
                } else {
                    int maxStones = 0;
                    for (int x = 1; x <= 2 * m; x++) {
                        int nextM = Math.min(n, Math.max(m, x));
                        int currentStones = suffixSum[i] - dp[i + x][nextM];
                        maxStones = Math.max(maxStones, currentStones);
                    }
                    dp[i][m] = maxStones;
                }
            }
        }

        // Step 4: Alice starts at index 0 with M = 1
        return dp[0][1];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter stone piles separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Maximum stones Alice can get: 0");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userPilesArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userPilesArray[i] = Integer.parseInt(tokens[i]);
        }

        StoneGameII1140 gameSolver = new StoneGameII1140();
        int aliceMaxStones = gameSolver.stoneGameII(userPilesArray);

        System.out.println("Maximum stones Alice can get: " + aliceMaxStones);
        scanner.close();
    }
}