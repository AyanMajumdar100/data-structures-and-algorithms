/*
 * Problem Statement: LeetCode 115 - Distinct Subsequences
 * Given two strings s and t, return the number of distinct subsequences of s which equals t.
 * The answer is guaranteed to fit in a 32-bit signed integer.
 */

/*
 * Approach: Dynamic Programming with 1D Space Optimization (O(M * N) Time, O(N) Space)
 * 1. Let dp[j] be the number of distinct subsequences of the prefix of s processed so far that equal t[0...j-1].
 * 2. Base Case:
 *    - dp[0] = 1, because an empty string t can always be formed in exactly 1 way (by deleting all characters).
 * 3. Transitions:
 *    - For each character s[i - 1] in s:
 *      * Traverse j backwards from n down to 1:
 *        - If s[i - 1] == t[j - 1]:
 *          We can either match s[i - 1] with t[j - 1] (adding dp[j - 1] ways from the previous step)
 *          or ignore s[i - 1] (keeping the existing dp[j] ways).
 *          dp[j] = dp[j] + dp[j - 1]
 *        - If s[i - 1] != t[j - 1]:
 *          dp[j] remains unchanged (ignore s[i - 1]).
 *    - Traversing j backwards allows us to reuse the 1D array in-place without overwriting states needed from the previous step.
 * 4. Result:
 *    - dp[n] contains the number of distinct subsequences of s that equal t.
 */

import java.util.Scanner;

public class DistinctSubsequences115 {
    public int numDistinct(String s, String t) {
        int m = s.length();
        int n = t.length();

        if (m < n) {
            return 0;
        }

        // dp[j] represents ways to form t[0...j-1]
        int[] dp = new int[n + 1];
        dp[0] = 1;

        // Process each character of s
        for (int i = 1; i <= m; i++) {
            char sc = s.charAt(i - 1);
            for (int j = n; j >= 1; j--) {
                if (sc == t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string s:");
        String sParam = scanner.nextLine().trim();

        System.out.println("Enter string t:");
        String tParam = scanner.nextLine().trim();

        DistinctSubsequences115 solver = new DistinctSubsequences115();
        int result = solver.numDistinct(sParam, tParam);

        System.out.println("Number of distinct subsequences: " + result);
        scanner.close();
    }
}