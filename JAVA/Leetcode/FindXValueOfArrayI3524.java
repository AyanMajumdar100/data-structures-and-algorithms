/*
 * Problem Statement: LeetCode 3524 - Find X Value of Array I
 * Given an array of positive integers nums and a positive integer k.
 * You can remove any non-overlapping prefix and suffix from nums such that the remaining subarray is non-empty.
 * The x-value of nums for a given remainder x (0 <= x < k) is the number of ways to choose a non-empty 
 * subarray whose product leaves a remainder of x when divided by k.
 * Return an array result of size k where result[x] is the x-value of nums.
 */

/*
 * Approach: Dynamic Programming with Modular Product Counting (O(N * K) Time, O(K) Space)
 * 1. Subarray Selection Equivalent:
 *    - Removing a non-overlapping prefix and suffix is equivalent to choosing *any non-empty contiguous subarray* from `nums`.
 * 2. Dynamic Programming State:
 *    - For each element `num` in `nums`, we track the counts of products of all subarrays ending at the current element modulo `k`.
 *    - Let `dp[i]` be the number of subarrays ending at the previous element with product remainder `i`.
 * 3. Transition:
 *    - When processing a new element `num`, any existing subarray product ending at the previous step can be multiplied by `num % k`.
 *    - Additionally, `num` itself can start a new subarray of length 1.
 *    - We use a rolling `newDp` array to accumulate these remainder counts and add them to the global `ans` array.
 */

import java.util.Arrays;
import java.util.Scanner;

public class FindXValueOfArrayI3524 {
    public long[] resultArray(int[] nums, int k) {
        long[] ans = new long[k];
        long[] dp = new long[k];

        for (int num : nums) {
            long[] newDp = new long[k];
            int numMod = num % k;

            // A new subarray starting and ending at the current element
            newDp[numMod] = 1;

            // Extend all previous valid subarrays ending at the last element
            for (int i = 0; i < k; i++) {
                int newMod = (int) ((1L * i * numMod) % k);
                newDp[newMod] += dp[i];
            }

            // Accumulate counts into the global answer
            for (int i = 0; i < k; i++) {
                ans[i] += newDp[i];
            }

            dp = newDp;
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Result: []");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNums = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNums[i] = Integer.parseInt(tokens[i]);
        }

        System.out.println("Enter integer k:");
        int kParam = scanner.nextInt();

        FindXValueOfArrayI3524 solver = new FindXValueOfArrayI3524();
        long[] result = solver.resultArray(userNums, kParam);

        System.out.println("Result x-value array: " + Arrays.toString(result));
        scanner.close();
    }
}
