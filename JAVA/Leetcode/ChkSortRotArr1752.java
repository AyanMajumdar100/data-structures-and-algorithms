/*
 * Problem Statement:
 * Given an array nums, return true if the array was originally sorted in non-decreasing order, 
 * then rotated some number of positions (including zero). Otherwise, return false.
 * There may be duplicates in the original array.
 * Note: An array A rotated by x positions results in an array B of the same length 
 * such that B[i] == A[(i+x) % A.length] for every valid index i.
 */

/*
 * Approach: Count Drops
 * A valid sorted and rotated array will have at most one "drop" where an element is 
 * strictly greater than the next element. We must also consider the circular wrap-around 
 * (comparing the last element to the first element). 
 * We traverse the array, count the number of drops, and if the total drops are 
 * less than or equal to 1, the array is valid.
 */
import java.util.Scanner;

public class ChkSortRotArr1752 {
    
    public boolean check(int[] nums) {
        int n = nums.length;
        int drops = 0;
        
        // Traverse the array to find any drops in the expected non-decreasing order
        for (int i = 0; i < n - 1; i++) {
            // If current element is greater than the next, we found a drop
            if (nums[i] > nums[i + 1]) {
                drops++;
            }
        }
        
        // Check the wrap-around from the last element to the first element
        if (nums[n - 1] > nums[0]) {
            drops++;
        }
        
        // A valid sorted and rotated array can have at most 1 drop
        return drops <= 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the array elements separated by space:");
        String inputLine = scanner.nextLine().trim();
        
        if (inputLine.isEmpty()) {
            System.out.println("Result: true");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        int[] nums = new int[parts.length];
        
        // Parse input strings to integers
        for (int i = 0; i < parts.length; i++) {
            nums[i] = Integer.parseInt(parts[i]);
        }
        
        ChkSortRotArr1752 solver = new ChkSortRotArr1752();
        boolean result = solver.check(nums);
        
        System.out.println("Is sorted and rotated? " + result);
        
        scanner.close();
    }
}
