/*
 * Problem Statement: LeetCode 1477 - Find Two Non-overlapping Sub-arrays Each With Target Sum
 * Given an array of integers arr and an integer target, find two non-overlapping sub-arrays
 * each with a sum equal to target such that the sum of their lengths is minimized.
 * Return the minimum sum of lengths, or return -1 if no such pair exists.
 */

/*
 * Approach: Sliding Window + Prefix Minimum Length Array (O(N) Time, O(N) Space)
 * 1. Sliding Window for Target Subarrays:
 *    - Maintain a sliding window `[left, right]` whose sum is dynamically adjusted to be `<= target`.
 *    - When `sum == target`, we found a valid sub-array of length `len = right - left + 1`.
 * 2. Prefix Minimum Length Tracker (`minLen`):
 *    - To find a valid non-overlapping sub-array to the left, we track the minimum length of any valid
 *      sub-array ending at or before index `left - 1`.
 *    - If `minLen[left - 1]` is valid (not `Integer.MAX_VALUE`), we can combine it with the current sub-array
 *      to form a candidate total length: `len + minLen[left - 1]`.
 * 3. State Update:
 *    - Keep track of the global minimum combined length (`ans`).
 *    - Update `minLen[right]` to store the minimum sub-length found from index 0 up to `right`.
 */

import java.util.Arrays;
import java.util.Scanner;

public class FindTwoNonOverlappingSubArrays1477 {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int[] minLen = new int[n];
        Arrays.fill(minLen, Integer.MAX_VALUE);

        int sum = 0;
        int left = 0;
        int ans = Integer.MAX_VALUE;
        int currentMin = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {
            sum += arr[right];
            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            if (sum == target) {
                int len = right - left + 1;
                // If there is a valid non-overlapping sub-array to the left
                if (left > 0 && minLen[left - 1] != Integer.MAX_VALUE) {
                    ans = Math.min(ans, len + minLen[left - 1]);
                }
                currentMin = Math.min(currentMin, len);
            }
            minLen[right] = currentMin;
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Minimum sum of lengths: -1");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userArr = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userArr[i] = Integer.parseInt(tokens[i]);
        }

        System.out.println("Enter target sum:");
        int targetParam = scanner.nextInt();

        FindTwoNonOverlappingSubArrays1477 solver = new FindTwoNonOverlappingSubArrays1477();
        int result = solver.minSumOfLengths(userArr, targetParam);

        System.out.println("Minimum sum of lengths of two non-overlapping sub-arrays: " + result);
        scanner.close();
    }
}
