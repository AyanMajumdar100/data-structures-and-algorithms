/*
 * Problem Statement:
 * You are given an array of positive integers arr. You can perform two operations:
 * 1. Rearrange elements in any order.
 * 2. Decrease the value of any element to a smaller positive integer.
 * * After operations, the array must satisfy:
 * - The first element must be 1.
 * - The absolute difference between any two adjacent elements must be <= 1.
 * Return the maximum possible value of an element in arr.
 */

/*
 * Approach: Sorting + Greedy Strategy
 * To maximize the largest element, we should sort the array first to make optimal use of 
 * larger values later.
 * 1. Sort the array in ascending order.
 * 2. Set the first element's virtual value or benchmark (`currentMax`) to 1, satisfying the first condition.
 * 3. Iterate through the rest of the array. For each element `arr[i]`:
 * - If `arr[i]` is strictly greater than `currentMax`, it can at least cover a value of `currentMax + 1`. 
 * So, we increment `currentMax`. (If it is much larger, we can always decrease it to exactly `currentMax + 1`).
 * - If `arr[i]` is less than or equal to `currentMax`, it cannot push our boundary higher, so `currentMax` stays the same.
 * 4. Return `currentMax` after the loop. This takes O(N log N) time and O(1) auxiliary space.
 */
import java.util.Arrays;
import java.util.Scanner;

public class MaxElementOpt1846 {
    public int maximumElementAfterDecrementingAndRearranging(int[] arr) {
        // Step 1: Sort the array to process from smallest to largest elements
        Arrays.sort(arr);
        
        // Step 2: The first element must be 1, so the initial max boundary is 1
        int currentMax = 1;
        
        // Step 3: Greedily build up the consecutive sequence
        for (int i = 1; i < arr.length; i++) {
            // If the element is larger than our current height, we can increase our max element by 1
            if (arr[i] > currentMax) {
                currentMax++;
            }
        }
        
        return currentMax;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String inputLine = scanner.nextLine().trim();
        
        if (inputLine.isEmpty()) {
            System.out.println("Maximum element: 0");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }
        
        MaxElementOpt1846 solver = new MaxElementOpt1846();
        int result = solver.maximumElementAfterDecrementingAndRearranging(arr);
        System.out.println("Maximum possible element: " + result);
        
        scanner.close();
    }
}
