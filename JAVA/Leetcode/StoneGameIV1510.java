/*
 * Problem Statement:
 * Alice and Bob take turns removing any non-zero square number of stones from a pile of n stones.
 * Alice goes first. The player who cannot make a move loses.
 * Return true if Alice wins, assuming optimal play.
 */

/*
 * Approach: Dynamic Programming / Game Theory (O(N * sqrt(N)) Time, O(N) Space)
 * 1. Winning and Losing States:
 *    - A state `i` is winning (`dp[i] = true`) if there is AT LEAST ONE move `k * k` such that 
 *      the remaining state `i - k * k` is a losing state (`dp[i - k * k] = false`).
 *    - A state `i` is losing (`dp[i] = false`) if ALL valid moves `k * k` lead to winning states for the opponent.
 * 2. Recurrence:
 *    - For each `i` from `1` to `n`, test all positive square numbers `k * k <= i`.
 *    - If `!dp[i - k * k]`, mark `dp[i] = true` and break early.
 * 3. Result:
 *    - Return `dp[n]`.
 */

import java.util.Scanner;

public class StoneGameIV1510 {
    public boolean winnerSquareGame(int n) {
        boolean[] dp = new boolean[n + 1];

        // Fill DP table for each stone pile size from 1 to n
        for (int i = 1; i <= n; i++) {
            for (int k = 1; k * k <= i; k++) {
                if (!dp[i - k * k]) {
                    dp[i] = true;
                    break; // Found a winning move, no need to check further squares
                }
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of stones (n):");
        int nParam = scanner.nextInt();

        StoneGameIV1510 gameSolver = new StoneGameIV1510();
        boolean aliceWins = gameSolver.winnerSquareGame(nParam);

        System.out.println("Alice wins: " + aliceWins);
        scanner.close();
    }
}
