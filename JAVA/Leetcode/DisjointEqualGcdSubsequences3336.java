/*
 * Problem Statement:
 * You are given an integer array nums. Find the number of pairs of non-empty disjoint 
 * subsequences (seq1, seq2) of nums such that the GCD of elements in seq1 equals the GCD of elements in seq2.
 * Return the total number of such pairs modulo 10^9 + 7.
 */

/*
 * Approach: Dynamic Programming (Three-Choice Decision State Matrix)
 * 1. Let dp[g1][g2] represent the number of ways to form two disjoint subsequences with 
 *    GCD values g1 and g2 respectively. We use 0 as a placeholder for an empty subsequence.
 * 2. For each number x in nums, we have three choices that expand our state space:
 *    - Choice 1: Skip x (x belongs to neither subsequence).
 *    - Choice 2: Add x to the first subsequence -> new GCD is gcd(g1, x) (or x if g1 == 0).
 *    - Choice 3: Add x to the second subsequence -> new GCD is gcd(g2, x) (or x if g2 == 0).
 * 3. Sum up all configurations where g1 == g2 and g1 > 0 at the end to ensure both are non-empty and equal.
 */

import java.util.Scanner;

public class DisjointEqualGcdSubsequences3336 {
    public int subsequencePairCount(int[] nums) {
        int MOD = 1_000_000_007;
        int maxVal = 0;
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
        }

        // dp[g1][g2] stores the count of subsequence combinations matching specific GCD thresholds
        int[][] dp = new int[maxVal + 1][maxVal + 1];
        dp[0][0] = 1; // Base initialization matching two empty collections

        for (int x : nums) {
            int[][] nextDp = new int[maxVal + 1][maxVal + 1];
            for (int g1 = 0; g1 <= maxVal; g1++) {
                for (int g2 = 0; g2 <= maxVal; g2++) {
                    if (dp[g1][g2] == 0) continue;

                    long currentCombinationsCount = dp[g1][g2];

                    // Choice 1: Don't include x in either subsequence sequence
                    nextDp[g1][g2] = (int) ((nextDp[g1][g2] + currentCombinationsCount) % MOD);

                    // Choice 2: Include x in the first subsequence stream
                    int ng1 = (g1 == 0) ? x : gcd(g1, x);
                    nextDp[ng1][g2] = (int) ((nextDp[ng1][g2] + currentCombinationsCount) % MOD);

                    // Choice 3: Include x in the second subsequence stream
                    int ng2 = (g2 == 0) ? x : gcd(g2, x);
                    nextDp[g1][ng2] = (int) ((nextDp[g1][ng2] + currentCombinationsCount) % MOD);
                }
            }
            dp = nextDp;
        }

        long totalMatchingPairs = 0;
        // Step 3: Accumulate valid pairs where both subsets are non-empty and possess identical GCD values
        for (int g = 1; g <= maxVal; g++) {
            totalMatchingPairs = (totalMatchingPairs + dp[g][g]) % MOD;
        }

        return (int) totalMatchingPairs;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        // Step 4: Handle standard console inputs using custom labeled parameters
        Scanner gcdConsoleScanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String inputLine = gcdConsoleScanner.nextLine().trim();
        
        if (inputLine.isEmpty()) {
            System.out.println("Total matching subsequence pairs: 0");
            return;
        }
        
        String[] lineParts = inputLine.split("\\s+");
        int[] userNumsParam = new int[lineParts.length];
        for (int i = 0; i < lineParts.length; i++) {
            userNumsParam[i] = Integer.parseInt(lineParts[i]);
        }
        
        // Step 5: Process evaluation and return total calculations
        DisjointEqualGcdSubsequences3336 visualSolver = new DisjointEqualGcdSubsequences3336();
        int finalOutcome = visualSolver.subsequencePairCount(userNumsParam);
        System.out.println("Total matching subsequence pairs: " + finalOutcome);
        
        gcdConsoleScanner.close();
    }
}
