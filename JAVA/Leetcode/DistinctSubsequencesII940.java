/*
 * Problem Statement: LeetCode 940 - Distinct Subsequences II
 * Given a string s, return the number of distinct non-empty subsequences of s.
 * Since the answer may be very large, return it modulo 10^9 + 7.
 */

/*
 * Approach: Dynamic Programming Grouped by Ending Character (O(N) Time, O(26) Space)
 * 1. Let `dp[c]` be the number of distinct non-empty subsequences ending with character `c`.
 * 2. Let `total` be the total number of distinct non-empty subsequences formed so far:
 *    `total = sum(dp[c]) for all c in 'a'..'z'`.
 * 3. When processing a new character `c`:
 *    - Any existing non-empty subsequence can have `c` appended to form a new subsequence ending in `c`.
 *    - The single-character subsequence `c` can also be formed on its own.
 *    - Hence, the total number of distinct subsequences ending in `c` becomes `curr = total + 1`.
 * 4. Updating `total`:
 *    - To avoid duplicates, replace the previous contribution of `dp[c]` with the new count `curr`:
 *      `new_total = total - dp[c] + curr`.
 *    - Update `dp[c] = curr`.
 * 5. Handle modular arithmetic carefully when subtracting `(total - dp[idx])`.
 */

import java.util.Scanner;

public class DistinctSubsequencesII940 {
    public int distinctSubseqII(String s) {
        int MOD = 1_000_000_007;
        int[] dp = new int[26];
        int total = 0;

        // Process each character and update the counts ending with character c
        for (char c : s.toCharArray()) {
            int idx = c - 'a';
            int curr = (total + 1) % MOD;
            total = (total - dp[idx] + curr) % MOD;
            if (total < 0) {
                total += MOD;
            }
            dp[idx] = curr;
        }

        return total;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string s:");
        String sParam = scanner.nextLine().trim();

        DistinctSubsequencesII940 solver = new DistinctSubsequencesII940();
        int result = solver.distinctSubseqII(sParam);

        System.out.println("Number of distinct non-empty subsequences: " + result);
        scanner.close();
    }
}
