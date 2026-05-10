/*
Problem Statement:
You are given a 0-indexed array nums of n integers and an integer target.
You are initially positioned at index 0. In one step, you can jump from index i to any index j such that:
0 <= i < j < n
-target <= nums[j] - nums[i] <= target
Return the maximum number of jumps you can make to reach index n - 1.
If there is no way to reach index n - 1, return -1.

Constraints:
2 <= nums.length == n <= 1000
-10^9 <= nums[i] <= 10^9
0 <= target <= 2 * 10^9
*/

import java.util.Scanner;

public class MaximumJumps {

    public static int maximumJumps(int[] nums, int target) {
        int n = nums.length;
        int[] dp = new int[n];
        
        // Initialize the dp array with -1 to indicate unreachable indices
        for (int i = 1; i < n; i++) {
            dp[i] = -1;
        }
        dp[0] = 0; 
        
        for (int i = 0; i < n; i++) {
            // If the current index is unreachable, skip it
            if (dp[i] == -1) continue;
            
            for (int j = i + 1; j < n; j++) {
                // Cast to long to prevent overflow since nums[i] can be up to 10^9
                // and target up to 2 * 10^9
                long diff = (long) nums[j] - nums[i];
                
                if (Math.abs(diff) <= target) {
                    // Update the destination with the maximum jumps possible
                    dp[j] = Math.max(dp[j], dp[i] + 1);
                }
            }
        }
        
        return dp[n - 1];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the array elements separated by spaces:");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Array cannot be empty.");
                return;
            }
            
            String[] strArr = input.split("\\s+");
            int[] nums = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                nums[i] = Integer.parseInt(strArr[i]);
            }
            
            System.out.println("Enter the target value:");
            int target = Integer.parseInt(scanner.nextLine().trim());
            
            int result = maximumJumps(nums, target);
            System.out.println("Maximum Jumps: " + result);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input detected. Please enter valid integers.");
        } finally {
            scanner.close();
        }
    }
}