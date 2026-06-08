/*
 * Problem Statement:
 * You are given a 0-indexed integer array nums and an integer pivot. Rearrange nums such that:
 * 1. Every element less than pivot appears before every element greater than pivot.
 * 2. Every element equal to pivot appears in between the elements less than and greater than pivot.
 * 3. The relative order of the elements less than pivot and the elements greater than pivot is maintained.
 * Return nums after the rearrangement.
 */

/*
 * Approach: Counting and Three-Pointer Assignment
 * 1. First, we perform a pass over the array to count how many elements are strictly less than the pivot 
 * and how many are exactly equal to it.
 * 2. With these counts, we can precalculate the starting indices for each category in our new result array:
 * - Elements less than pivot start at index 0.
 * - Elements equal to pivot start at index `lessCount`.
 * - Elements greater than pivot start at index `lessCount + equalCount`.
 * 3. We perform a second pass over the original array. For each element, we check its category, 
 * insert it into its respective starting position in the result array, and increment that category's pointer.
 * This guarantees stable placement and operates in linear O(N) time and auxiliary space.
 */
import java.util.Scanner;
import java.util.Arrays;

public class PartArrPivot2161 {
    public int[] pivotArray(int[] nums, int pivot) {
        int n = nums.length;
        int[] result = new int[n];
        
        int lessCount = 0;
        int equalCount = 0;
        
        // Pass 1: Count categories to establish clear partition segments
        for (int num : nums) {
            if (num < pivot) {
                lessCount++;
            } else if (num == pivot) {
                equalCount++;
            }
        }
        
        // Establish independent starting pointer heads for each destination group
        int lessIndex = 0;
        int equalIndex = lessCount;
        int greaterIndex = lessCount + equalCount;
        
        // Pass 2: Distribute elements into the result structure preserving order stability
        for (int num : nums) {
            if (num < pivot) {
                result[lessIndex++] = num;
            } else if (num == pivot) {
                result[equalIndex++] = num;
            } else {
                result[greaterIndex++] = num;
            }
        }
        
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the pivot value:");
        int pivot = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter array elements separated by space:");
        String inputLine = scanner.nextLine().trim();
        if (inputLine.isEmpty()) {
            System.out.println("Result: []");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        int[] nums = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            nums[i] = Integer.parseInt(parts[i]);
        }
        
        PartArrPivot2161 solver = new PartArrPivot2161();
        int[] result = solver.pivotArray(nums, pivot);
        
        System.out.println("Rearranged array: " + Arrays.toString(result));
        scanner.close();
    }
}