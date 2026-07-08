/*
 * Problem Statement:
 * You are given a string s of digits and a 2D integer array queries where queries[i] = [li, ri].
 * For each query, extract s[li..ri] and find the product of:
 * 1. x: The number formed by concatenating all non-zero digits in their original order.
 * 2. sum: The sum of digits in x.
 * Return an array containing the answers modulo 10^9 + 7.
 * * Constraints:
 * s.length <= 10^5, queries.length <= 10^5
 */

/*
 * Approach: Prefix Sum + Prefix Rolling Hash (O(N) Precomputation, O(1) per Query)
 * Since constraints are up to 10^5 for both string size and query count, an O(R - L) 
 * approach per query will result in Time Limit Exceeded (TLE). We need O(1) query time.
 * * We track three prefix properties:
 * 1. P[i]: The total count of NON-ZERO digits up to index i.
 * 2. A[i]: The rolling integer value formed by non-zero digits up to index i (modulo 10^9 + 7).
 * 3. B[i]: The prefix sum of non-zero digit values up to index i.
 * * Substring Extraction Logic:
 * For a substring query [l, r]:
 * - The number of non-zero digits in the range is k = P[r + 1] - P[l].
 * - The digit sum is sum = B[r + 1] - B[l].
 * - The specific value x is derived by peeling off the left-prefix contribution using a rolling hash strategy:
 * x = (A[r + 1] - (A[l] * 10^k)) % mod.
 */
import java.util.Arrays;
import java.util.Scanner;

public class NonZeroDigitQuery3756 {
    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();
        long mod = 1_000_000_007;
        
        // P[i] stores count of non-zero digits up to index i
        int[] P = new int[n + 1];
        // A[i] stores prefix rolling hash numerical value of non-zero digits
        long[] A = new long[n + 1];
        // B[i] stores prefix sum of non-zero digits
        long[] B = new long[n + 1];
        // Precomputed powers of 10 for quick rolling hash shifting
        long[] pow10 = new long[n + 1];
        
        pow10[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow10[i] = (pow10[i - 1] * 10) % mod;
        }
        
        // Build prefix arrays
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '0') {
                P[i + 1] = P[i];
                A[i + 1] = A[i];
                B[i + 1] = B[i];
            } else {
                int digit = c - '0';
                P[i + 1] = P[i] + 1;
                A[i + 1] = (A[i] * 10 + digit) % mod;
                B[i + 1] = B[i] + digit;
            }
        }
        
        int numQueries = queries.length;
        int[] ans = new int[numQueries];
        
        // Answer each query in O(1) time
        for (int i = 0; i < numQueries; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            
            int k = P[r + 1] - P[l]; // Number of non-zero digits inside this query range
            long x = (A[r + 1] - (A[l] * pow10[k]) % mod + mod) % mod; // Extract substring integer value
            long sum = B[r + 1] - B[l]; // Extract sum of digits
            
            ans[i] = (int) ((x * sum) % mod);
        }
        
        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the digit string s:");
        String s = scanner.nextLine().trim();
        
        System.out.println("Enter number of queries:");
        int numQueries = scanner.nextInt();
        int[][] queries = new int[numQueries][2];
        
        System.out.println("Enter queries (l r) line by line:");
        for (int i = 0; i < numQueries; i++) {
            queries[i][0] = scanner.nextInt();
            queries[i][1] = scanner.nextInt();
        }
        
        NonZeroDigitQuery3756 solver = new NonZeroDigitQuery3756();
        int[] result = solver.sumAndMultiply(s, queries);
        System.out.println("Query Results: " + Arrays.toString(result));
        
        scanner.close();
    }
}
