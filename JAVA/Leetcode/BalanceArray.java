/*
PROBLEM STATEMENT:
You are given an integer array nums and an integer k.
Your task is to remove the minimum number of elements from nums
so that for every element in the remaining array, 
the ratio of the largest to smallest element is at most k.

Return the minimum number of removals required.

Approach:
- Sort the array and use a sliding window to find the largest valid subarray.
- Minimum removals = total elements - size of the largest valid subarray.
*/

import java.util.Arrays;
import java.util.Scanner;

class BalanceArray {
    public int minRemoval(int[] nums, int k) {
        int n = nums.length;

        // Sort the array so we can use a sliding window approach
        Arrays.sort(nums);

        int maxWindow = 0; // Stores the maximum size of a valid subarray
        int j = 0;         // Right pointer of the sliding window

        // i is the left pointer of the sliding window
        for (int i = 0; i < n; i++) {

            // Expand the window while the condition is satisfied:
            // nums[j] should be <= nums[i] * k
            // Long casting avoids integer overflow
            while (j < n && (long) nums[j] <= (long) nums[i] * k) {
                j++;
            }

            // Update the maximum valid window size
            maxWindow = Math.max(maxWindow, j - i);
        }

        // Minimum removals = total elements - largest valid subarray
        return n - maxWindow;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Take input for the size of the array
        System.out.print("Enter the number of elements: ");
        int n = sc.nextInt();

        int[] nums = new int[n];

        // Take input for array elements
        System.out.println("Enter the array elements:");
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        // Take input for k
        System.out.print("Enter the value of k: ");
        int k = sc.nextInt();

        // Create an instance and call the method
        BalanceArray balance = new BalanceArray();
        int result = balance.minRemoval(nums, k);

        // Print the result
        System.out.println("Minimum removals needed: " + result);

        sc.close();
    }
}
