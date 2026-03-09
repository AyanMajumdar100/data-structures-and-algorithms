/*
3129. Find All Possible Stable Binary Arrays I

Problem Statement:
You are given three integers:
- zero  → number of 0s that must appear in the array
- one   → number of 1s that must appear in the array
- limit → maximum number of consecutive identical elements allowed

A binary array is called STABLE if:
1. It contains exactly 'zero' number of 0s.
2. It contains exactly 'one' number of 1s.
3. No subarray longer than 'limit' contains the same number only.
   In other words:
   - You cannot have more than 'limit' consecutive 0s
   - You cannot have more than 'limit' consecutive 1s

Return the number of such stable binary arrays.
Since the answer can be very large, return it modulo (10^9 + 7).

Example 1:
Input: zero = 1, one = 1, limit = 2
Output: 2
Possible arrays:
[0,1], [1,0]

Example 2:
Input: zero = 1, one = 2, limit = 1
Output: 1
Only valid array:
[1,0,1]

Example 3:
Input: zero = 3, one = 3, limit = 2
Output: 14

-------------------------------------------------------------

Core Idea (Dynamic Programming):

Let:
dp[i][j][0] = number of stable arrays using
              i zeros and j ones
              where the last element is 0

dp[i][j][1] = number of stable arrays using
              i zeros and j ones
              where the last element is 1

Transitions:

To end with 0:
append 0 to arrays that previously ended with 0 or 1
BUT ensure consecutive 0s do not exceed limit

To end with 1:
append 1 to arrays that previously ended with 0 or 1
BUT ensure consecutive 1s do not exceed limit

We subtract invalid states when consecutive count exceeds limit.

Time Complexity:  O(zero × one)
Space Complexity: O(zero × one)

-------------------------------------------------------------
*/

import java.util.Scanner;

public class StableBinaryArrays {

    public static int numberOfStableArrays(int zero, int one, int limit) {

        long MOD = 1_000_000_007;

        /*
        dp[i][j][0] → arrays with i zeros, j ones, ending with 0
        dp[i][j][1] → arrays with i zeros, j ones, ending with 1
        */
        long[][][] dp = new long[zero + 1][one + 1][2];

        /*
        Base cases:
        If we only place zeros initially (no ones yet)
        we can place at most 'limit' consecutive zeros
        */
        for (int i = 1; i <= Math.min(zero, limit); i++) {
            dp[i][0][0] = 1;
        }

        /*
        Similarly for only ones
        */
        for (int j = 1; j <= Math.min(one, limit); j++) {
            dp[0][j][1] = 1;
        }

        /*
        Fill DP table
        */
        for (int i = 1; i <= zero; i++) {
            for (int j = 1; j <= one; j++) {

                /*
                Case 1: End with 0
                We append 0 to any array that previously used (i-1 zeros, j ones)
                */
                dp[i][j][0] =
                        (dp[i - 1][j][0] + dp[i - 1][j][1]) % MOD;

                /*
                If we exceed 'limit' consecutive zeros,
                subtract the invalid configurations
                */
                if (i > limit) {
                    dp[i][j][0] =
                            (dp[i][j][0] - dp[i - limit - 1][j][1] + MOD) % MOD;
                }

                /*
                Case 2: End with 1
                We append 1 to arrays with (i zeros, j-1 ones)
                */
                dp[i][j][1] =
                        (dp[i][j - 1][0] + dp[i][j - 1][1]) % MOD;

                /*
                Remove invalid cases if consecutive ones exceed limit
                */
                if (j > limit) {
                    dp[i][j][1] =
                            (dp[i][j][1] - dp[i][j - limit - 1][0] + MOD) % MOD;
                }
            }
        }

        /*
        Final answer:
        Arrays ending with 0 OR ending with 1
        */
        return (int) ((dp[zero][one][0] + dp[zero][one][1]) % MOD);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of zeros: ");
        int zero = sc.nextInt();

        System.out.print("Enter number of ones: ");
        int one = sc.nextInt();

        System.out.print("Enter limit: ");
        int limit = sc.nextInt();

        int result = numberOfStableArrays(zero, one, limit);

        System.out.println("Number of Stable Binary Arrays: " + result);

        sc.close();
    }
}