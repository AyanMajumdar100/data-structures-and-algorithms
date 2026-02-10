/*
## Problem: Longest Balanced Subarray with Distinct Parity

Description
You are given an integer array nums.

A subarray nums[i..j] is considered balanced if the number of distinct even
integers in the subarray is equal to the number of distinct odd integers.

Return the length of the longest balanced subarray.

Example 1:
Input: nums = [1, 2, 3, 4]
Output: 4

Example 2:
Input: nums = [1, 1, 2, 2, 3]
Output: 4

Example 3:
Input: nums = [2, 4, 6]
Output: 0

Constraints:
1 <= nums.length <= 1500
1 <= nums[i] <= 10^5
*/

import java.util.*;

class LongestBalancedSubarray {

    // Function to find longest balanced subarray
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int maxLen = 0;     // Stores best answer
        int maxVal = 0;     // To know max value in array

        // Find maximum value in nums
        // This helps create a tracking array for distinct elements
        for (int num : nums) {
            if (num > maxVal) maxVal = num;
        }

        // seen[val] stores last starting index where val appeared
        // Used to count DISTINCT numbers only once per subarray
        int[] seen = new int[maxVal + 1];
        Arrays.fill(seen, -1);

        // Fix starting index of subarray
        for (int i = 0; i < n; i++) {

            // Optimization:
            // If remaining elements can't beat maxLen → stop early
            if (n - i <= maxLen) break;

            int evenCount = 0; // count of distinct even numbers
            int oddCount = 0;  // count of distinct odd numbers

            // Expand subarray from i → j
            for (int j = i; j < n; j++) {
                int val = nums[j];

                // If this value hasn't appeared in this subarray
                if (seen[val] != i) {
                    seen[val] = i;

                    // Check parity using bit operation
                    if ((val & 1) == 1) oddCount++;
                    else evenCount++;
                }

                // If counts match → balanced subarray found
                if (evenCount == oddCount) {
                    int currentLen = j - i + 1;

                    // Update maximum length
                    if (currentLen > maxLen) maxLen = currentLen;
                }
            }
        }

        return maxLen;
    }


    // ----------- USER INPUT + DRIVER CODE -----------

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter array elements separated by space:");
        String input = sc.nextLine();

        // Convert input string to integer array
        String[] parts = input.trim().split(" ");
        int[] nums = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        // Create object and call function
        LongestBalancedSubarray obj = new LongestBalancedSubarray();
        int result = obj.longestBalanced(nums);

        System.out.println("Longest balanced subarray length: " + result);

        sc.close();
    }
}
