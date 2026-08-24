/*
 * Problem Statement:
 * Alice and Bob take turns removing the leftmost x stones (x > 1), adding their sum to their score, 
 * and placing a new stone with that sum back at the beginning of the row.
 * The game stops when only one stone remains.
 * Return the optimal score difference (Alice's score - Bob's score).
 */

/*
 * Approach: Prefix Sums + Backward Dynamic Programming (O(N) Time, O(1) Auxiliary Space)
 * 1. Prefix Sum Invariance:
 *    When a player chooses x stones, the sum of those stones is simply prefixSum[x - 1].
 *    Replacing those x stones with their sum preserves the prefix sums for all subsequent indices >= x.
 * 2. Minimax DP State:
 *    Let `dp[i]` be the maximum score difference the active player can achieve given that they 
 *    can pick any index j >= i as their stopping point.
 * 3. Recurrence:
 *    At index i (where i corresponds to taking the first i + 1 stones, 1 <= i < n):
 *    - Option A: Stop at index i, scoring `prefixSum[i]`, leaving the opponent to play from index i + 1 onward.
 *      Net difference = `prefixSum[i] - dp[i + 1]`.
 *    - Option B: Choose an index greater than i -> `dp[i + 1]`.
 *    Therefore: `dp[i] = max(dp[i + 1], prefixSum[i] - dp[i + 1])`.
 * 4. Base Case:
 *    At the last index `n - 1`, the only choice is to take all stones, so `dp[n - 1] = prefixSum[n - 1]`.
 * 5. Player 1 constraint:
 *    Alice must choose x >= 2 (i >= 1), so the answer is `dp[1]`.
 */

import java.util.Scanner;

public class StoneGameVIII1872 {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;

        // Step 1: Compute prefix sums in-place
        for (int i = 1; i < n; i++) {
            stones[i] += stones[i - 1];
        }

        // Step 2: Base case at index n - 1 (taking all stones)
        int dp = stones[n - 1];

        // Step 3: Backward DP transition down to index 1 (x >= 2)
        for (int i = n - 2; i >= 1; i--) {
            dp = Math.max(dp, stones[i] - dp);
        }

        return dp;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter stone values separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Max score difference: 0");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userStonesArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userStonesArray[i] = Integer.parseInt(tokens[i]);
        }

        StoneGameVIII1872 solverInstance = new StoneGameVIII1872();
        int maxScoreDifference = solverInstance.stoneGameVIII(userStonesArray);

        System.out.println("Optimal score difference (Alice - Bob): " + maxScoreDifference);
        scanner.close();
    }
}
