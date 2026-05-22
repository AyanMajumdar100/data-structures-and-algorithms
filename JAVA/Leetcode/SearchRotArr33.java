/*
 * Problem Statement:
 * There is an integer array nums sorted in ascending order (with distinct values).
 * Prior to being passed to your function, nums is possibly left rotated at an unknown index k.
 * Given the array nums after the possible rotation and an integer target, return the index 
 * of target if it is in nums, or -1 if it is not in nums.
 * You must write an algorithm with O(log n) runtime complexity.
 */

/*
 * Approach: Binary Search
 * Even though the array is rotated, at least one half of the array (left or right of mid) 
 * will always be sorted. We can use this property to perform a binary search.
 * 1. Find the mid index.
 * 2. Check if the mid element is the target.
 * 3. Determine which half (left or right) is sorted.
 * 4. Check if the target falls within the bounds of the sorted half.
 * 5. If it does, eliminate the other half. If not, eliminate the sorted half.
 * 6. Repeat until target is found or search space is exhausted.
 */
import java.util.Scanner;

public class SearchRotArr33 {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        // Loop until search space is exhausted
        while (left <= right) {
            // Calculate mid index safely to avoid overflow
            int mid = left + (right - left) / 2;
            
            // Target found
            if (nums[mid] == target) {
                return mid;
            }
            
            // Check if the left half is sorted
            if (nums[left] <= nums[mid]) {
                // Check if target lies within the sorted left half
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1; // Narrow search to left half
                } else {
                    left = mid + 1; // Narrow search to right half
                }
            } 
            // Otherwise, the right half must be sorted
            else {
                // Check if target lies within the sorted right half
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1; // Narrow search to right half
                } else {
                    right = mid - 1; // Narrow search to left half
                }
            }
        }
        
        // Target not found in the array
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Take user input for the target value
        System.out.println("Enter the target integer:");
        int target = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        // Take user input for the array elements
        System.out.println("Enter the rotated sorted array elements separated by space:");
        String[] input = scanner.nextLine().trim().split("\\s+");
        
        if (input[0].isEmpty()) {
            System.out.println("Target index: -1");
            return;
        }
        
        int[] nums = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            nums[i] = Integer.parseInt(input[i]);
        }
        
        // Execute the algorithm and print the result
        SearchRotArr33 solver = new SearchRotArr33();
        int result = solver.search(nums, target);
        System.out.println("Target index: " + result);
        
        scanner.close();
    }
}
