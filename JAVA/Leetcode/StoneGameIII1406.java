/*
 * Problem Statement:
 * Alice and Bob take turns taking 1, 2, or 3 stones from the front of the remaining row of stones.
 * Alice goes first. Both play optimally to maximize their score.
 * Return "Alice" if Alice wins, "Bob" if Bob wins, or "Tie" if they draw.
 */

/*
 * Approach: Dynamic Programming / Minimax Game Theory (O(N) Time, O(N) Space)
 * 1. Score Difference Concept:
 *    Let `dp[i]` represent the maximum score difference (Current Player's Score - Opponent's Score) 
 *    the active player can achieve starting from index `i` to the end of the array `n`.
 * 2. Recurrence Relation:
 *    At index `i`, the active player can take `k` stones where `k` in {1, 2, 3}:
 *    - The player gains `currentSum` = sum of values of stones taken from `i` to `i + k - 1`.
 *    - The opponent is left with the game starting from index `i + k`, where they achieve a score difference `dp[i + k]`.
 *    - Net score difference for the current player taking `k` stones is `currentSum - dp[i + k]`.
 *    - `dp[i] = max(currentSum - dp[i + k])` over all valid `k` (1 <= k <= 3 and i + k <= n).
 * 3. Game Outcome:
 *    - If `dp[0] > 0`: Alice achieves a positive score advantage and wins -> "Alice".
 *    - If `dp[0] < 0`: Bob achieves a positive score advantage and wins -> "Bob".
 *    - If `dp[0] == 0`: Both players achieve the same score -> "Tie".
 */

import java.util.Scanner;

public class StoneGameIII1406 {
    public String stoneGameIII(int[] stoneValue) {
        int arrayLength = stoneValue.length;
        // dp[i] stores the max net advantage obtainable starting from index i
        int[] dp = new int[arrayLength + 1];

        // Process backward from the end of the stone row
        for (int i = arrayLength - 1; i >= 0; i--) {
            int maxScoreDiff = Integer.MIN_VALUE;
            int currentTakeSum = 0;

            // Try taking 1, 2, or 3 stones
            for (int k = 1; k <= 3 && i + k <= arrayLength; k++) {
                currentTakeSum += stoneValue[i + k - 1];
                maxScoreDiff = Math.max(maxScoreDiff, currentTakeSum - dp[i + k]);
            }

            dp[i] = maxScoreDiff;
        }

        // Determine winner based on Alice's net advantage at index 0
        if (dp[0] > 0) {
            return "Alice";
        } else if (dp[0] < 0) {
            return "Bob";
        } else {
            return "Tie";
        }
    }

    public static void main(String[] args) {
        Scanner stoneScanner = new Scanner(System.in);
        System.out.println("Enter stone values separated by space:");
        String rawInputString = stoneScanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Game Result: Tie");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userStoneValues = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userStoneValues[i] = Integer.parseInt(tokens[i]);
        }

        StoneGameIII1406 gameSolver = new StoneGameIII1406();
        String winnerResult = gameSolver.stoneGameIII(userStoneValues);

        System.out.println("Game Result: " + winnerResult);
        stoneScanner.close();
    }
}