/*
 * Problem Statement:
 * You are given an integer array nums.
 * You replace each element in nums with the sum of its digits.
 * Return the minimum element in nums after all replacements.
 */

/*
 * Approach:
 * Iterate through the array. For each number, calculate the sum of its digits
 * using a while loop (repeatedly take modulo 10 and divide by 10).
 * Keep track of the minimum sum encountered so far and return it at the end.
 */
import java.util.Scanner;

public class MinElemDigitSum3300 {
    public int minElement(int[] nums) {
        // Initialize the minimum value to the maximum possible integer
        int min = Integer.MAX_VALUE;
        
        // Iterate over each number in the input array
        for (int num : nums) {
            // Variable to store the sum of digits for the current number
            int sum = 0;
            
            // Extract digits until the number becomes 0
            while (num > 0) {
                // Add the last digit to the sum
                sum += num % 10;
                // Remove the last digit from the number
                num /= 10;
            }
            
            // Update the minimum if the current sum is smaller
            if (sum < min) {
                min = sum;
            }
        }
        
        // Return the overall minimum digit sum
        return min;
    }

    public static void main(String[] args) {
        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the array elements separated by space:");
        
        // Read the entire line and split it by spaces
        String inputLine = scanner.nextLine().trim();
        if (inputLine.isEmpty()) {
            System.out.println("No input provided.");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        int[] nums = new int[parts.length];
        
        // Parse string inputs to integers
        for (int i = 0; i < parts.length; i++) {
            nums[i] = Integer.parseInt(parts[i]);
        }
        
        // Instantiate the solution class and execute the algorithm
        MinElemDigitSum3300 solver = new MinElemDigitSum3300();
        int result = solver.minElement(nums);
        
        // Output the result
        System.out.println("Minimum element after replacement: " + result);
        
        // Close the scanner
        scanner.close();
    }
}
