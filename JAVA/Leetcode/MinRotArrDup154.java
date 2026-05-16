/*
 * Problem Statement:
 * Suppose an array of length n sorted in ascending order is rotated between 1 and n times.
 * Given the sorted rotated array nums that may contain duplicates, return the minimum 
 * element of this array.
 * You must decrease the overall operation steps as much as possible.
 */

/*
 * Approach: Binary Search with Duplicate Handling
 * We use binary search to locate the minimum element in the rotated array.
 * Initialize 'left' and 'right' pointers. Calculate 'mid' at each step.
 * If nums[mid] > nums[right], the minimum lies strictly to the right of mid (left = mid + 1).
 * If nums[mid] < nums[right], the minimum is at mid or to its left (right = mid).
 * If nums[mid] == nums[right], we cannot definitively know which half has the minimum.
 * However, since nums[right] is duplicated at mid, we can safely ignore the rightmost 
 * element by decrementing right (right--).
 * The loop terminates when left == right, leaving the minimum element at nums[left].
 */
import java.util.Scanner;

public class MinRotArrDup154 {
    
    public int findMin(int[] nums) {
        // Initialize start and end pointers
        int left = 0;
        int right = nums.length - 1;
        
        // Loop until search space is reduced to a single element
        while (left < right) {
            // Calculate mid safely to avoid integer overflow
            int mid = left + (right - left) / 2;
            
            // If mid element is greater than rightmost, the minimum is in the right half
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } 
            // If mid element is less than rightmost, the minimum is in the left half or at mid
            else if (nums[mid] < nums[right]) {
                right = mid;
            } 
            // If they are equal, we can't be sure, but we can safely discard the duplicate rightmost element
            else {
                right--;
            }
        }
        
        // Return the located minimum element
        return nums[left];
    }

    public static void main(String[] args) {
        // Setup scanner to read user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        
        // Read input string and split by spaces
        String[] input = scanner.nextLine().split(" ");
        int[] nums = new int[input.length];
        
        // Parse strings to integers
        for (int i = 0; i < input.length; i++) {
            nums[i] = Integer.parseInt(input[i]);
        }
        
        // Instantiate the solution class and execute algorithm
        MinRotArrDup154 solution = new MinRotArrDup154();
        System.out.println("Minimum element: " + solution.findMin(nums));
        
        // Close resources
        scanner.close();
    }
}