/*
Problem Statement:
You are given an integer array nums.
A tuple (i, j, k) of 3 distinct indices is good if nums[i] == nums[j] == nums[k].
The distance of a good tuple is abs(i - j) + abs(j - k) + abs(k - i).
Return an integer denoting the minimum possible distance of a good tuple. 
If no good tuples exist, return -1.

Constraints:
1 <= n == nums.length <= 10^5
1 <= nums[i] <= n
*/

import java.util.Arrays;
import java.util.Scanner;

public class MinDistThree {

    public static int minimumDistance(int[] nums) {
        int n = nums.length;
        
        // Track the previous two indices for each number
        // Size is n + 1 because the problem guarantees 1 <= nums[i] <= n
        int[] first = new int[n + 1];
        int[] second = new int[n + 1];
        
        Arrays.fill(first, -1);
        Arrays.fill(second, -1);
        
        int minDistance = Integer.MAX_VALUE;
        
        for (int i = 0; i < n; i++) {
            int val = nums[i];
            
            // If we have seen this number at least twice before,
            // 'first[val]' holds the index of the element two steps ago.
            // The distance simplifies to 2 * (current_index - index_two_steps_ago)
            if (first[val] != -1) {
                int dist = 2 * (i - first[val]);
                if (dist < minDistance) {
                    minDistance = dist;
                }
            }
            
            // Shift the history of indices for the current value
            // The old 'most recent' becomes the 'second most recent'
            first[val] = second[val];
            second[val] = i; // Current index is now the most recent
        }
        
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
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
            
            int result = minimumDistance(nums);
            System.out.println("Minimum Distance: " + result);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter integers only.");
        } finally {
            scanner.close();
        }
    }
}