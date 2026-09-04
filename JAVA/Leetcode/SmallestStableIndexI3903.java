/*
 * Problem Statement: LeetCode 3903 - Smallest Stable Index I
 * Given an integer array nums of length n and an integer k.
 * For each index i, instability score = max(nums[0..i]) - min(nums[i..n-1]).
 * An index i is stable if instability score <= k.
 * Return the smallest stable index, or -1 if no such index exists.
 */

/*
 * Approach: Prefix Maximum & Suffix Minimum Arrays (O(N) Time, O(N) Space)
 * 1. Compute `prefixMax[i]`: The maximum value from `nums[0]` to `nums[i]`.
 * 2. Compute `suffixMin[i]`: The minimum value from `nums[i]` to `nums[n - 1]`.
 * 3. Iterate through `i` from 0 to n - 1:
 *    - Instability score at `i` is `prefixMax[i] - suffixMin[i]`.
 *    - The first index where this value is <= k is the smallest stable index.
 * 4. Return -1 if no index satisfies the condition.
 */

import java.util.Scanner;

public class SmallestStableIndexI3903 {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;
        int[] prefixMax = new int[n];
        int[] suffixMin = new int[n];

        // Step 1: Precompute prefix maximums
        prefixMax[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prefixMax[i] = Math.max(prefixMax[i - 1], nums[i]);
        }

        // Step 2: Precompute suffix minimums
        suffixMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(suffixMin[i + 1], nums[i]);
        }

        // Step 3: Find the first index satisfying the stability condition
        for (int i = 0; i < n; i++) {
            if (prefixMax[i] - suffixMin[i] <= k) {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Result: -1");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNumsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNumsArray[i] = Integer.parseInt(tokens[i]);
        }

        System.out.println("Enter integer k:");
        int kParam = scanner.nextInt();

        SmallestStableIndexI3903 solver = new SmallestStableIndexI3903();
        int result = solver.firstStableIndex(userNumsArray, kParam);

        System.out.println("Smallest stable index: " + result);
        scanner.close();
    }
}
