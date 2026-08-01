/*
 * Problem Statement:
 * You are given an integer array nums. Two players take turns picking numbers from either end of the array.
 * Player 1 goes first. Both start with a score of 0.
 * Return true if Player 1 can achieve a total score greater than or equal to Player 2, assuming both play optimally.
 */

/*
 * Approach: Dynamic Programming / Minimax Game Theory (O(N^2) Time, O(N) Space)
 * 1. Score Difference Concept:
 *    Let `dp[i][j]` represent the maximum score difference (Current Player's Score - Opponent's Score) 
 *    that the active player can achieve from subarray `nums[i...j]`.
 * 2. Recurrence Relation:
 *    When picking from subarray `nums[i...j]`, the current player has two optimal moves:
 *    - Pick `nums[i]`: Gain `nums[i]` and leave subarray `nums[i+1...j]` to the opponent, 
 *      resulting in net score difference `nums[i] - dp[i+1][j]`.
 *    - Pick `nums[j]`: Gain `nums[j]` and leave subarray `nums[i...j-1]` to the opponent, 
 *      resulting in net score difference `nums[j] - dp[i][j-1]`.
 *    The player chooses `Math.max(nums[i] - dp[i+1][j], nums[j] - dp[i][j-1])`.
 * 3. 1D Space Optimization:
 *    Since `dp[i][j]` only depends on `dp[i+1][j]` and `dp[i][j-1]`, we can compress the 2D matrix into a 
 *    1D array `dp[i]` updated bottom-up for increasing window lengths `len`.
 * 4. Winner Determination:
 *    If `dp[0] >= 0` for the full array range `[0...n-1]`, Player 1 wins or ties.
 */

import java.util.Scanner;

public class PredictTheWinner486 {
    public boolean predictTheWinner(int[] nums) {
        int arrayLength = nums.length;
        int[] dp = new int[arrayLength];

        // Base case: Subarray of length 1 -> the active player takes the single element
        for (int i = 0; i < arrayLength; i++) {
            dp[i] = nums[i];
        }

        // Expand subarray length from 2 up to n
        for (int length = 2; length <= arrayLength; length++) {
            for (int left = 0; left <= arrayLength - length; left++) {
                int right = left + length - 1;
                dp[left] = Math.max(nums[left] - dp[left + 1], nums[right] - dp[left]);
            }
        }

        // Player 1 wins if final net score difference >= 0
        return dp[0] >= 0;
    }

    public static void main(String[] args) {
        Scanner gameScanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = gameScanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Player 1 can win: true");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNumsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNumsArray[i] = Integer.parseInt(tokens[i]);
        }

        PredictTheWinner486 gameSolver = new PredictTheWinner486();
        boolean canPlayer1Win = gameSolver.predictTheWinner(userNumsArray);

        System.out.println("Player 1 can win: " + canPlayer1Win);
        gameScanner.close();
    }
}
