/*
 * Problem Statement:
 * You are given two integers num1 and num2 representing an inclusive range [num1, num2].
 * The waviness of a number is defined as the total count of its peaks and valleys:
 * - A digit is a peak if it is strictly greater than both of its immediate neighbors.
 * - A digit is a valley if it is strictly less than both of its immediate neighbors.
 * - The first and last digits of a number cannot be peaks or valleys.
 * - Any number with fewer than 3 digits has a waviness of 0.
 * Return the total sum of waviness for all numbers in the range [num1, num2].
 */

/*
 * Approach: Digit Dynamic Programming (Digit DP) with Memoization
 * Since range elements can be large, we avoid linear scanning by looking at the problem 
 * through digit combinations using Prefix Math: solve(num2) - solve(num1 - 1).
 * We track state over multi-dimensional caches:
 * - idx: Current digit index we are filling.
 * - d1: The grandfather digit (index i - 2). Value 10 represents unassigned state.
 * - d2: The father digit (index i - 1). Value 10 represents unassigned state.
 * - isLess: Binary flag indicating if the built prefix is strictly smaller than bounds.
 * - isStarted: Binary flag indicating if we have started forming non-zero numbers.
 * At each recursive junction, we look ahead to see if the past digit d2 forms a peak or valley 
 * with its predecessor d1 and successors d. If validated, it contributes to waviness.
 */
import java.util.Scanner;

public class TotalWaveII {
    private long[][][][][] memoCount;
    private long[][][][][] memoSum;
    private char[] limitChars;

    public long totalWaviness(long num1, long num2) {
        // Use prefix math properties to extract total sum for range [num1, num2]
        return solve(num2) - solve(num1 - 1);
    }

    private long solve(long num) {
        // Constraints state any number below 100 has a baseline waviness of 0
        if (num < 100) return 0;
        
        limitChars = String.valueOf(num).toCharArray();
        int n = limitChars.length;
        
        // Initialize memoization matrices: dims for index, d1, d2, isLess, isStarted
        memoCount = new long[n][11][11][2][2];
        memoSum = new long[n][11][11][2][2];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 11; j++) {
                for (int k = 0; k < 11; k++) {
                    for (int l = 0; l < 2; l++) {
                        for (int m = 0; m < 2; m++) {
                            memoCount[i][j][k][l][m] = -1;
                        }
                    }
                }
            }
        }
        
        // Compute transitions tracking total count of matching numbers and their waviness sum
        long[] res = dp(0, 10, 10, 0, 0);
        return res[1];
    }

    private long[] dp(int idx, int d1, int d2, int isLess, int isStarted) {
        // Base case: successfully processed all digits
        if (idx == limitChars.length) {
            return new long[]{1, 0}; // Returns {count = 1, waviness = 0}
        }
        
        // Return already calculated results if cached
        if (memoCount[idx][d1][d2][isLess][isStarted] != -1) {
            return new long[]{memoCount[idx][d1][d2][isLess][isStarted], memoSum[idx][d1][d2][isLess][isStarted]};
        }

        // Determine current ceiling value for the processed position
        int limit = (isLess == 1) ? 9 : (limitChars[idx] - '0');
        long totalCount = 0;
        long totalWaviness = 0;

        // Try placing each candidate digit
        for (int d = 0; d <= limit; d++) {
            int nextIsLess = (isLess == 1 || d < limit) ? 1 : 0;
            int nextIsStarted = (isStarted == 1 || d > 0) ? 1 : 0;
            
            int nextD1 = 10, nextD2 = 10;
            if (nextIsStarted == 1) {
                if (isStarted == 0) {
                    // First digit placed sets up the future father element
                    nextD2 = d;
                } else {
                    // Subsequent digits shift the window (father becomes grandfather, current is father)
                    nextD1 = d2;
                    nextD2 = d;
                }
            }

            // Recurse to process the subsequent placements
            long[] subRes = dp(idx + 1, nextD1, nextD2, nextIsLess, nextIsStarted);
            long subCount = subRes[0];
            long subSum = subRes[1];

            long waveContribution = 0;
            // Check if the previous digit (d2) acts as a peak or a valley
            if (isStarted == 1 && d1 != 10 && d2 != 10) {
                if ((d1 < d2 && d2 > d) || (d1 > d2 && d2 < d)) {
                    waveContribution = 1;
                }
            }

            // Accumulate counts and running totals combining deeper recursion trees
            totalCount += subCount;
            totalWaviness += subSum + waveContribution * subCount;
        }

        // Cache state calculations
        memoCount[idx][d1][d2][isLess][isStarted] = totalCount;
        memoSum[idx][d1][d2][isLess][isStarted] = totalWaviness;

        return new long[]{totalCount, totalWaviness};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter num1:");
        long num1 = scanner.nextLong();
        System.out.println("Enter num2:");
        long num2 = scanner.nextLong();

        TotalWaveII3751 solver = new TotalWaveII3751();
        long result = solver.totalWaviness(num1, num2);
        System.out.println("Total waviness: " + result);
        scanner.close();
    }
}