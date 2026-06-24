/*
 * Problem Statement:
 * You are given three integers n, l, and r.
 * A ZigZag array of length n is defined as follows:
 * - Each element lies in the range [l, r].
 * - No two adjacent elements are equal.
 * - No three consecutive elements form a strictly increasing or strictly decreasing sequence.
 * Return the total number of valid ZigZag arrays modulo 10^9 + 7.
 * * Constraints:
 * 3 <= n <= 10^9  <-- Notice that N is massive now!
 * 1 <= l < r <= 75 <-- M is very small (M <= 75)
 */

/*
 * Approach: DP Matrix Exponentiation
 * Since N can be as large as 10^9, a linear O(N) or O(N * M) layer-by-layer dynamic programming 
 * solution will Time Out. However, since M is small (M = r - l + 1 <= 75), we can transform 
 * the linear recurrences into a fixed-size Matrix Multiplication problem.
 * * 1. State Mapping: 
 * We have 2 * M states tracking the last chosen value `v` and the last transition type (UP or DOWN):
 * - State [0 to M-1]: We reached value `v` (index v-1) via an UP step.
 * - State [M to 2M-1]: We reached value `v` (index M + v - 1) via a DOWN step.
 * * 2. Transitions & Transition Matrix T:
 * - An UP state `v` can transition to a DOWN state `u` only if u > v.
 * Hence, T[M + u - 1][v - 1] = 1 for all u > v.
 * - A DOWN state `v` can transition to an UP state `u` only if u < v.
 * Hence, T[u - 1][M + v - 1] = 1 for all u < v.
 * * 3. Matrix Power:
 * Finding the count at length N starting from baseline configurations at length 2 requires multiplying 
 * the transition matrix by itself (N - 2) times. We use Binary Exponentiation on the matrix to compute 
 * T^(n-2) in O( (2M)^3 * log(N) ) time.
 */
import java.util.Scanner;

public class ZigZagArraysII3700 {
    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;
        int size = 2 * m;
        long mod = 1_000_000_007;
        
        // Construct the State Transition Matrix T
        long[][] T = new long[size][size];
        for (int v = 1; v <= m; v++) {
            // From an UP state 'v' (indexed v - 1), we can transition to a DOWN state 'u' if u < v
            for (int u = 1; u < v; u++) {
                T[v - 1][m + u - 1] = 1;
            }
            // From a DOWN state 'v' (indexed m + v - 1), we can transition to an UP state 'u' if u > v
            for (int u = v + 1; u <= m; u++) {
                T[m + v - 1][u - 1] = 1;
            }
        }
        
        // Raise the transition matrix to the power of (n - 2)
        long[][] T_pow = power(T, n - 2, mod);
        
        // Base configurations array representing valid counts at length n = 2
        long[] V2 = new long[size];
        for (int v = 1; v <= m; v++) {
            V2[v - 1] = v - 1;       // Options smaller than v that can result in an UP transition
            V2[m + v - 1] = m - v;   // Options larger than v that can result in a DOWN transition
        }
        
        // Multiply T_pow with vector V2 and accumulate all matching combinations
        long total = 0;
        for (int i = 0; i < size; i++) {
            long sum = 0;
            for (int j = 0; j < size; j++) {
                sum = (sum + T_pow[i][j] * V2[j]) % mod;
            }
            total = (total + sum) % mod;
        }
        
        return (int) total;
    }
    
    // Matrix binary exponentiation: O(size^3 * log(p))
    private long[][] power(long[][] A, long p, long mod) {
        int n = A.length;
        long[][] res = new long[n][n];
        for (int i = 0; i < n; i++) {
            res[i][i] = 1; // Initialize identity matrix
        }
        while (p > 0) {
            if ((p & 1) == 1) {
                res = multiply(res, A, mod);
            }
            A = multiply(A, A, mod);
            p >>= 1;
        }
        return res;
    }
    
    // Standard matrix multiplication with O(1) skipping optimization
    private long[][] multiply(long[][] A, long[][] B, long mod) {
        int n = A.length;
        long[][] C = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                if (A[i][k] == 0) continue;
                for (int j = 0; j < n; j++) {
                    C[i][j] = (C[i][j] + A[i][k] * B[k][j]) % mod;
                }
            }
        }
        return C;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter length n:");
        int n = scanner.nextInt();
        System.out.println("Enter lower bound l:");
        int l = scanner.nextInt();
        System.out.println("Enter upper bound r:");
        int r = scanner.nextInt();
        
        ZigZagArraysII3700 solver = new ZigZagArraysII3700();
        int result = solver.zigZagArrays(n, l, r);
        System.out.println("Total valid ZigZag arrays: " + result);
        
        scanner.close();
    }
}
