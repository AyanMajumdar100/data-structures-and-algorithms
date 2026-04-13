/*
Problem Statement:
Given an integer array nums (0-indexed) and two integers target and start, 
find an index i such that nums[i] == target and abs(i - start) is minimized.

Return abs(i - start).
It is guaranteed that target exists in nums.

Constraints:
1 <= nums.length <= 1000
1 <= nums[i] <= 10^4
0 <= start < nums.length
target is in nums.
*/

import java.util.Scanner;

public class MinTargetDist {

    public static int getMinDistance(int[] nums, int target, int start) {
        int n = nums.length;
        
        // Optimize: Expand outwards from the 'start' index.
        // i represents the current distance we are checking.
        // The first time we hit the target, 'i' is mathematically guaranteed to be the minimum.
        for (int i = 0; ; i++) {
            // Check the right side at distance i
            if (start + i < n && nums[start + i] == target) {
                return i;
            }
            // Check the left side at distance i
            if (start - i >= 0 && nums[start - i] == target) {
                return i;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the array elements separated by spaces:");
            String[] inputStr = scanner.nextLine().trim().split("\\s+");
            
            if (inputStr.length == 0 || inputStr[0].isEmpty()) {
                System.out.println("Array cannot be empty.");
                return;
            }

            int[] nums = new int[inputStr.length];
            for (int i = 0; i < inputStr.length; i++) {
                nums[i] = Integer.parseInt(inputStr[i]);
            }
            
            System.out.println("Enter the target element:");
            int target = scanner.nextInt();
            
            System.out.println("Enter the start index:");
            int start = scanner.nextInt();
            
            if (start < 0 || start >= nums.length) {
                System.out.println("Invalid start index. Must be within array bounds.");
                return;
            }

            int result = getMinDistance(nums, target, start);
            System.out.println("Minimum Distance: " + result);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter integers only.");
        } finally {
            scanner.close();
        }
    }
}