/*
 * Problem Statement: LeetCode 1621 - Number of Sets of K Non-Overlapping Line Segments
 * Given n points on a 1-D plane (from x = 0 to n - 1), find the number of ways to draw 
 * exactly k non-overlapping line segments whose endpoints have integral coordinates.
 * Segments can share endpoints. Return the answer modulo 10^9 + 7.
 */

/*
 * Approach: Combinatorics / Stars and Bars Transformation (O(N * K) Time, O(N * K) Space)
 * 1. Mathematical Modeling:
 *    - Let the n points be divided into:
 *      * k line segments of length >= 1 (each segment covers at least 2 points).
 *      * gaps between segments, and spaces before/after the segments.
 *    - By transforming the coordinates, choosing k non-overlapping line segments from n points 
 *      is equivalent to choosing 2k points out of (n + k - 1) available "slots" or positions.
 *    - Specifically, the formula simplifies to combinations: C(n + k - 1, 2k).
 * 2. Pascal's Identity / Dynamic Programming for Combinations:
 *    - Compute C(total, r) modulo 10^9 + 7 where `total = n + k - 1` and `r = 2 * k`.
 */

import java.util.Scanner;

public class NumberOfSetsOfKNonOverlappingLineSegments1621 {
    public int numberOfSets(int n, int k) {
        int MOD = 1_000_000_007;
        int total = n + k - 1;
        int r = 2 * k;

        // Pascal's triangle for combination C(total, r)
        long[][] C = new long[total + 1][r + 1];
        for (int i = 0; i <= total; i++) {
            C[i][0] = 1;
            for (int j = 1; j <= Math.min(i, r); j++) {
                C[i][j] = (C[i - 1][j - 1] + C[i - 1][j]) % MOD;
            }
        }

        return (int) C[total][r];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter n (number of points):");
        int nParam = scanner.nextInt();

        System.out.println("Enter k (number of segments):");
        int kParam = scanner.nextInt();

        NumberOfSetsOfKNonOverlappingLineSegments1621 solver = new NumberOfSetsOfKNonOverlappingLineSegments1621();
        int result = solver.numberOfSets(nParam, kParam);

        System.out.println("Number of ways to draw " + kParam + " segments: " + result);
        scanner.close();
    }
}
