import java.util.Scanner;

/**
 * 3130. Find All Possible Stable Binary Arrays II
 * * Problem Statement:
 * You are given 3 positive integers zero, one, and limit.
 * A binary array arr is called stable if:
 * - The number of occurrences of 0 in arr is exactly zero.
 * - The number of occurrences of 1 in arr is exactly one.
 * - Each subarray of arr with a size greater than limit must contain both 0 and 1.
 * * Return the total number of stable binary arrays.
 * Since the answer may be very large, return it modulo 10^9 + 7.
 */
public class StableBinaryArrays2 {

    public static int numberOfStableArrays(int zero, int one, int limit) {
        int MOD = 1_000_000_007;
        
        // Let's set up our DP table.
        // dp[i][j][0] -> Number of valid arrays using exactly 'i' zeros, 'j' ones, and ending with a '0'
        // dp[i][j][1] -> Number of valid arrays using exactly 'i' zeros, 'j' ones, and ending with a '1'
        long[][][] dp = new long[zero + 1][one + 1][2];

        // Base cases: If we only have 0s (and 0 ones), there's exactly 1 way to arrange them,
        // provided we don't exceed our consecutive limit. Same logic applies to arrays of only 1s.
        for (int i = 1; i <= Math.min(zero, limit); i++) {
            dp[i][0][0] = 1;
        }
        for (int j = 1; j <= Math.min(one, limit); j++) {
            dp[0][j][1] = 1;
        }

        // Fill the DP table bottom-up
        for (int i = 1; i <= zero; i++) {
            for (int j = 1; j <= one; j++) {
                
                // --- Calculate arrays ending in '0' ---
                // We can append a '0' to any valid array of length (i-1, j) regardless of whether it ended in 0 or 1.
                dp[i][j][0] = (dp[i - 1][j][0] + dp[i - 1][j][1]) % MOD;
                
                // The tricky part: If we just added a '0' and our total consecutive '0's exceed the limit,
                // we've accidentally counted invalid sequences. Specifically, we need to subtract the sequences
                // that ended with a '1' followed by exactly 'limit' zeros.
                if (i > limit) {
                    dp[i][j][0] = (dp[i][j][0] - dp[i - limit - 1][j][1] + MOD) % MOD;
                }

                // --- Calculate arrays ending in '1' ---
                // Similar logic here: append a '1' to any valid array of length (i, j-1).
                dp[i][j][1] = (dp[i][j - 1][0] + dp[i][j - 1][1]) % MOD;
                
                // Remove invalid sequences where appending '1' created a block of (limit + 1) ones.
                if (j > limit) {
                    dp[i][j][1] = (dp[i][j][1] - dp[i][j - limit - 1][0] + MOD) % MOD;
                }
            }
        }

        // The total stable arrays will be the sum of those ending in 0 and those ending in 1 for the exact counts.
        return (int) ((dp[zero][one][0] + dp[zero][one][1]) % MOD);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Find All Possible Stable Binary Arrays II ---");
        
        System.out.print("Enter the number of zeros: ");
        int zero = scanner.nextInt();
        
        System.out.print("Enter the number of ones: ");
        int one = scanner.nextInt();
        
        System.out.print("Enter the maximum consecutive limit: ");
        int limit = scanner.nextInt();

        int result = numberOfStableArrays(zero, one, limit);
        
        System.out.println("\nTotal number of stable binary arrays: " + result);
        
        scanner.close();
    }
}
