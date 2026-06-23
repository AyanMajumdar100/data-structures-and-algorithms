/*
 * Problem Statement:
 * You are given three integers n, l, and r.
 * A ZigZag array of length n is defined as follows:
 * - Each element lies in the range [l, r].
 * - No two adjacent elements are equal.
 * - No three consecutive elements form a strictly increasing or strictly decreasing sequence.
 * Return the total number of valid ZigZag arrays modulo 10^9 + 7.
 */

/*
 * Approach: Dynamic Programming with Prefix/Suffix Sums
 * Let M = r - l + 1 be the number of choices for each element. We can normalize the values to [1, M].
 * * To ensure no three consecutive elements increase or decrease monotonically, the direction of trends 
 * must strictly alternate at each index step.
 * * Define state representations for building the array element by element:
 * - dpUp[v]: Number of valid prefixes of length `i` ending with value `v`, where the last step was an INCREASING transition (v > previous).
 * - dpDown[v]: Number of valid prefixes of length `i` ending with value `v`, where the last step was a DECREASING transition (v < previous).
 * * Transitions:
 * - An UP step must follow a DOWN step from the prior transition:
 * nextUp[v] = sum(dpDown[u]) for all u < v
 * - A DOWN step must follow an UP step from the prior transition:
 * nextDown[v] = sum(dpUp[u]) for all u > v
 * * Using prefix sums (`prefDown`) and suffix sums (`suffUp`), we optimize these state sums 
 * down to O(1) per value, bringing total complexity to O(N * M).
 */
import java.util.Scanner;

public class ZigZagArrays3699 {
    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;
        long mod = 1_000_000_007;
        
        long[] dpUp = new long[m + 1];
        long[] dpDown = new long[m + 1];
        
        // Base Case: Initialize for length n = 2
        // For a fixed ending value v (1-indexed relative offset):
        // There are (v - 1) valid values strictly smaller than v (giving an UP transition)
        // There are (m - v) valid values strictly greater than v (giving a DOWN transition)
        for (int v = 1; v <= m; v++) {
            dpUp[v] = v - 1;
            dpDown[v] = m - v;
        }
        
        // Propagate state iteratively up to length n
        for (int i = 3; i <= n; i++) {
            long[] nextUp = new long[m + 1];
            long[] nextDown = new long[m + 1];
            
            // nextUp[v] requires the sum of dpDown[u] for all u < v
            long prefDown = 0;
            for (int v = 1; v <= m; v++) {
                nextUp[v] = prefDown;
                prefDown = (prefDown + dpDown[v]) % mod;
            }
            
            // nextDown[v] requires the sum of dpUp[u] for all u > v
            long suffUp = 0;
            for (int v = m; v >= 1; v--) {
                nextDown[v] = suffUp;
                suffUp = (suffUp + dpUp[v]) % mod;
            }
            
            // Move pointers to the newly calculated length layer
            dpUp = nextUp;
            dpDown = nextDown;
        }
        
        // Aggregate all permutations ending at any valid value v
        long total = 0;
        for (int v = 1; v <= m; v++) {
            total = (total + dpUp[v] + dpDown[v]) % mod;
        }
        
        return (int) total;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter length n:");
        int n = scanner.nextInt();
        System.out.println("Enter lower bound l:");
        int l = scanner.nextInt();
        System.out.println("Enter upper bound r:");
        int r = scanner.nextInt();
        
        ZigZagArrays3699 solver = new ZigZagArrays3699();
        int result = solver.zigZagArrays(n, l, r);
        System.out.println("Total valid ZigZag arrays: " + result);
        
        scanner.close();
    }
}
