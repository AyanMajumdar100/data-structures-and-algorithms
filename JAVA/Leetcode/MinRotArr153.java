/*
 * Problem Statement:
 * Suppose an array of length n sorted in ascending order is rotated between 1 and n times.
 * Given the sorted rotated array nums of unique elements, return the minimum element of this array.
 * You must write an algorithm that runs in O(log n) time.
 */

/*
 * Approach: Binary Search
 * Since the array is initially sorted and then rotated, we can use binary search to locate the minimum.
 * We initialize 'left' and 'right' pointers. At each step, we calculate 'mid'.
 * If nums[mid] > nums[right], it indicates the array wraps around to the right of 'mid', so the minimum is in the right half (left = mid + 1).
 * Otherwise, the minimum is either at 'mid' or in the left half, so we narrow our search to the left (right = mid).
 * The loop ends when left == right, converging precisely on the minimum element.
 */
import java.util.Scanner;

public class MinRotArr153 {
    public int findMin(int[] nums) {
        // Initialize the start and end pointers for binary search
        int left = 0;
        int right = nums.length - 1;
        
        // Continue searching while the search space has more than one element
        while (left < right) {
            // Calculate the middle index safely to prevent integer overflow
            int mid = left + (right - left) / 2;
            
            // Compare middle element with the rightmost element
            // If mid is greater, the smallest value must be to its right
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } 
            // If mid is less than or equal to the rightmost, the smallest value is at mid or to its left
            else {
                right = mid;
            }
        }
        
        // Once left equals right, we have found our minimum element
        return nums[left];
    }

    public static void main(String[] args) {
        // Setup scanner to read user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        
        // Read the line and parse it into an integer array
        String[] input = scanner.nextLine().split(" ");
        int[] nums = new int[input.length];
        
        for (int i = 0; i < input.length; i++) {
            nums[i] = Integer.parseInt(input[i]);
        }
        
        // Instantiate the solution class and execute the algorithm
        MinRotArr153 solution = new MinRotArr153();
        System.out.println("Minimum element: " + solution.findMin(nums));
        
        // Clean up resources
        scanner.close();
    }
}
