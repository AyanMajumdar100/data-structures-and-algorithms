/*
Problem Statement:
You are given an integer array nums.
Jump rules:
- Forward (j > i): allowed if nums[j] < nums[i]
- Backward (j < i): allowed if nums[j] > nums[i]
Find the maximum reachable value starting from each index i.

Constraints:
1 <= nums.length <= 10^5
1 <= nums[i] <= 10^9
*/

import java.util.Scanner;

public class JumpGameIX {

    public static int[] maxValue(int[] nums) {
        int n = nums.length;
        int[] suffMin = new int[n];
        
        // Precompute the suffix minimums
        suffMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffMin[i] = Math.min(suffMin[i + 1], nums[i]);
        }
        
        int[] ans = new int[n];
        int start = 0;
        int currentPrefMax = nums[0];
        
        // Traverse to find the partition cuts
        for (int i = 0; i < n; i++) {
            if (nums[i] > currentPrefMax) {
                currentPrefMax = nums[i];
            }
            
            // A cut happens if all elements to the left are <= all elements to the right.
            // Or if we reach the very end of the array.
            if (i == n - 1 || currentPrefMax <= suffMin[i + 1]) {
                // All elements in this partition share the same maximum reachable value
                for (int j = start; j <= i; j++) {
                    ans[j] = currentPrefMax;
                }
                
                // Reset for the next partition
                start = i + 1;
                if (i + 1 < n) {
                    currentPrefMax = nums[i + 1];
                }
            }
        }
        
        return ans;
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
            
            int[] result = maxValue(nums);
            
            System.out.print("Output: [");
            for (int i = 0; i < result.length; i++) {
                System.out.print(result[i] + (i == result.length - 1 ? "" : ", "));
            }
            System.out.println("]");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input detected. Please enter valid integers.");
        } finally {
            scanner.close();
        }
    }
}
