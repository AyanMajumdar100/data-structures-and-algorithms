/*
 * Problem Statement:
 * Given a numeric string num and a target integer t, find the smallest zero-free positive integer 
 * (as a string) greater than or equal to num whose digit product is divisible by t.
 * Return "-1" if no such number exists.
 */

/*
 * Approach: Prime Factorization + DP Bounds + Greedy Prefix Search (O(N) Time, O(1) Space)
 * 1. Factorize t into powers of 2, 3, 5, 7. Return "-1" if any other prime factor exists.
 * 2. Precompute DP matrix `dp[c2][c3]` storing the minimum digit count to satisfy powers of 2 and 3.
 * 3. Search for the longest prefix of `num` where incrementing a digit allows the remaining suffix
 *    to fulfill prime factor requirements.
 * 4. Fill remaining positions greedily with the smallest valid digits (1 through 9).
 */

import java.util.Arrays;
import java.util.Scanner;

public class SmallestDivisibleDigitProductII3348 {
    public String smallestNumber(String num, long t) {
        long tempT = t;
        int count2 = 0, count3 = 0, count5 = 0, count7 = 0;

        while (tempT % 2 == 0) { count2++; tempT /= 2; }
        while (tempT % 3 == 0) { count3++; tempT /= 3; }
        while (tempT % 5 == 0) { count5++; tempT /= 5; }
        while (tempT % 7 == 0) { count7++; tempT /= 7; }

        // If t contains prime factors other than 2, 3, 5, or 7, it's impossible
        if (tempT > 1) {
            return "-1";
        }

        // DP table for min digits required for required powers of 2 and 3
        int[][] dp = new int[65][45];
        for (int[] row : dp) Arrays.fill(row, 1000);
        dp[0][0] = 0;

        for (int i = 0; i <= 60; i++) {
            for (int j = 0; j <= 40; j++) {
                if (dp[i][j] == 1000) continue;
                int[][] transitions = {{1, 0}, {0, 1}, {2, 0}, {1, 1}, {3, 0}, {0, 2}};
                for (int[] tr : transitions) {
                    int ni = Math.min(60, i + tr[0]);
                    int nj = Math.min(40, j + tr[1]);
                    dp[ni][nj] = Math.min(dp[ni][nj], dp[i][j] + 1);
                }
            }
        }

        for (int i = 60; i >= 0; i--) {
            for (int j = 40; j >= 0; j--) {
                if (i < 60) dp[i][j] = Math.min(dp[i][j], dp[i + 1][j]);
                if (j < 40) dp[i][j] = Math.min(dp[i][j], dp[i][j + 1]);
            }
        }

        int n = num.length();
        int firstZero = num.indexOf('0');
        int maxPrefixLen = (firstZero != -1) ? firstZero : n;

        int[] f2 = {0, 0, 1, 0, 2, 0, 1, 0, 3, 0};
        int[] f3 = {0, 0, 0, 1, 0, 0, 1, 0, 0, 2};
        int[] f5 = {0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
        int[] f7 = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0};

        int[] pref2 = new int[n + 1];
        int[] pref3 = new int[n + 1];
        int[] pref5 = new int[n + 1];
        int[] pref7 = new int[n + 1];

        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';
            pref2[i + 1] = pref2[i] + f2[d];
            pref3[i + 1] = pref3[i] + f3[d];
            pref5[i + 1] = pref5[i] + f5[d];
            pref7[i + 1] = pref7[i] + f7[d];
        }

        // Try prefixes of length `len` from `maxPrefixLen` down to 0
        for (int len = maxPrefixLen; len >= 0; len--) {
            int needed2 = Math.max(0, count2 - pref2[len]);
            int needed3 = Math.max(0, count3 - pref3[len]);
            int needed5 = Math.max(0, count5 - pref5[len]);
            int needed7 = Math.max(0, count7 - pref7[len]);

            if (len == n) {
                if (needed2 == 0 && needed3 == 0 && needed5 == 0 && needed7 == 0) {
                    return num;
                }
                continue;
            }

            int startDigit = num.charAt(len) - '0' + 1;

            for (int d = startDigit; d <= 9; d++) {
                int n2 = Math.max(0, needed2 - f2[d]);
                int n3 = Math.max(0, needed3 - f3[d]);
                int n5 = Math.max(0, needed5 - f5[d]);
                int n7 = Math.max(0, needed7 - f7[d]);

                if (n5 + n7 + dp[n2][n3] <= n - 1 - len) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, len);
                    sb.append(d);

                    int remLen = n - 1 - len;
                    int r2 = n2, r3 = n3, r5 = n5, r7 = n7;

                    for (int i = 0; i < remLen; i++) {
                        for (int nextD = 1; nextD <= 9; nextD++) {
                            int nn2 = Math.max(0, r2 - f2[nextD]);
                            int nn3 = Math.max(0, r3 - f3[nextD]);
                            int nn5 = Math.max(0, r5 - f5[nextD]);
                            int nn7 = Math.max(0, r7 - f7[nextD]);

                            if (nn5 + nn7 + dp[nn2][nn3] <= remLen - 1 - i) {
                                sb.append(nextD);
                                r2 = nn2; r3 = nn3; r5 = nn5; r7 = nn7;
                                break;
                            }
                        }
                    }
                    return sb.toString();
                }
            }
        }

        // If no prefix works, expand string length
        int minLenNeeded = count5 + count7 + dp[count2][count3];
        int totalLen = Math.max(n + 1, minLenNeeded);

        StringBuilder sb = new StringBuilder();
        int r2 = count2, r3 = count3, r5 = count5, r7 = count7;

        for (int i = 0; i < totalLen; i++) {
            for (int nextD = 1; nextD <= 9; nextD++) {
                int nn2 = Math.max(0, r2 - f2[nextD]);
                int nn3 = Math.max(0, r3 - f3[nextD]);
                int nn5 = Math.max(0, r5 - f5[nextD]);
                int nn7 = Math.max(0, r7 - f7[nextD]);

                if (nn5 + nn7 + dp[nn2][nn3] <= totalLen - 1 - i) {
                    sb.append(nextD);
                    r2 = nn2; r3 = nn3; r5 = nn5; r7 = nn7;
                    break;
                }
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string num:");
        String numParam = scanner.nextLine().trim();

        System.out.println("Enter long t:");
        long tParam = scanner.nextLong();

        SmallestDivisibleDigitProductII3348 solver = new SmallestDivisibleDigitProductII3348();
        String result = solver.smallestNumber(numParam, tParam);

        System.out.println("Smallest zero-free number: " + result);
        scanner.close();
    }
}
