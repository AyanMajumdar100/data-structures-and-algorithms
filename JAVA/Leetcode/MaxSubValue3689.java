/*
 * Problem Statement:
 * You are given an integer array nums of length n and an integer k.
 * You need to choose exactly k non-empty subarrays nums[l..r] of nums. Subarrays may overlap, 
 * and the exact same subarray (same l and r) can be chosen more than once.
 * The value of a subarray nums[l..r] is defined as: max(nums[l..r]) - min(nums[l..r]).
 * The total value is the sum of the values of all chosen subarrays.
 * Return the maximum possible total value you can achieve.
 */

/*
 * Approach: Greedy Optimization
 * The value of any subarray is bounded by the global maximum and global minimum of the entire array.
 * Since we are permitted to select overlapping subarrays, and the exact same subarray can be chosen 
 * multiple times up to k times, we can greedily find the absolute maximum and minimum values present 
 * across the entire array.
 * The optimal strategy is to select the entire array (or any subarray containing both the global maximum 
 * and global minimum elements) exactly k times. This maximizes the value of each of the k chosen subarrays 
 * to (global_max - global_min).
 * Thus, the maximum total value is simply: k * (global_max - global_min).
 */
import java.util.Scanner;

public class MaxSubVal3689 {
    public long maxTotalValue(int[] nums, int k) {
        // Handle edge case where array is empty
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // Initialize global maximum and minimum tracking with the first element
        int max = nums[0];
        int min = nums[0];
        
        // Find the global maximum and minimum values across the entire array in a single pass
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i]; // Update global maximum
            }
            if (nums[i] < min) {
                min = nums[i]; // Update global minimum
            }
        }
        
        // Return the total value by multiplying the maximum possible subarray variance by k
        // Cast to long to prevent integer overflow during the multiplication
        return (long) k * (max - min);
    }

    public static void main(String[] args) {
        // Setup scanner to read user input
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the value of k:");
        int k = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter array elements separated by space:");
        String inputLine = scanner.nextLine().trim();
        if (inputLine.isEmpty()) {
            System.out.println("Maximum total value: 0");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        int[] nums = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            nums[i] = Integer.parseInt(parts[i]);
        }
        
        // Execute the algorithm and print the result
        MaxSubVal3689 solver = new MaxSubVal3689();
        long result = solver.maxTotalValue(nums, k);
        System.out.println("Maximum total value: " + result);
        
        scanner.close();
    }
}
