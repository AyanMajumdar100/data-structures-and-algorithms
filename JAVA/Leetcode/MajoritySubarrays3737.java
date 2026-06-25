/*
 * Problem Statement:
 * You are given an integer array nums and an integer target.
 * Return the number of subarrays of nums in which target is the majority element.
 * The majority element of a subarray is the element that appears strictly more than half of the times in that subarray.
 */

/*
 * Approach: Brute Force O(N^2) Simulation
 * For a target to be the majority element in a subarray, its frequency must be strictly greater 
 * than the frequency of all other elements combined.
 * * 1. We can reframe the problem by transforming our array conceptually:
 * - If nums[k] == target, it contributes +1 to our target's dominance.
 * - If nums[k] != target, it contributes -1.
 * 2. A subarray nums[i..j] has target as a majority element if and only if the sum of these 
 * contributions (balance) is strictly greater than 0.
 * 3. Since constraints are small (N <= 1000), we can check all possible subarrays using two nested loops.
 * - The outer loop fixes the starting index `i`.
 * - The inner loop expands the ending index `j` from `i` to `n - 1`, maintaining a running `balance`.
 * - If `balance > 0`, we increment our total valid subarray count.
 */
import java.util.Scanner;

public class MajoritySubarrays3737 {
    public int countMajoritySubarrays(int[] nums, int target) {
        int count = 0;
        int n = nums.length;
        
        // Loop through every possible starting position of the subarray
        for (int i = 0; i < n; i++) {
            int balance = 0;
            
            // Expand the subarray to the right and update the target vs non-target balance
            for (int j = i; j < n; j++) {
                if (nums[j] == target) {
                    balance++; // Target element helps the majority status
                } else {
                    balance--; // Non-target element hurts the majority status
                }
                
                // If balance > 0, target appears strictly more than half of the times
                if (balance > 0) {
                    count++;
                }
            }
        }
        
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the target element:");
        int target = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter array elements separated by space:");
        String inputLine = scanner.nextLine().trim();
        if (inputLine.isEmpty()) {
            System.out.println("Total subarrays: 0");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        int[] nums = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            nums[i] = Integer.parseInt(parts[i]);
        }
        
        MajoritySubarrays3737 solver = new MajoritySubarrays3737();
        int result = solver.countMajoritySubarrays(nums, target);
        System.out.println("Total valid subarrays: " + result);
        
        scanner.close();
    }
}
