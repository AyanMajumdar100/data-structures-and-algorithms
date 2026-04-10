/*
Problem Statement:
You are given an integer array nums.
A tuple (i, j, k) of 3 distinct indices is good if nums[i] == nums[j] == nums[k].
The distance of a good tuple is abs(i - j) + abs(j - k) + abs(k - i).
Return an integer denoting the minimum possible distance of a good tuple. 
If no good tuples exist, return -1.

Constraints:
1 <= n == nums.length <= 100
1 <= nums[i] <= n
*/

import java.util.Scanner;

public class MinTripletDist {

    public static int minimumDistance(int[] nums) {
        int n = nums.length;
        
        // Since values are guaranteed to be between 1 and n, 
        // we can use an array of size n + 1 to store the last 3 indices for each number.
        int[][] lastThree = new int[n + 1][3];
        
        // Initialize the array with -1 indicating 'empty' slots
        for (int i = 0; i <= n; i++) {
            lastThree[i][0] = -1;
            lastThree[i][1] = -1;
            lastThree[i][2] = -1;
        }
        
        int minDistance = Integer.MAX_VALUE;
        
        for (int i = 0; i < n; i++) {
            int val = nums[i];
            
            // Shift older indices to the left and append the new current index (i)
            lastThree[val][0] = lastThree[val][1];
            lastThree[val][1] = lastThree[val][2];
            lastThree[val][2] = i;
            
            // If the oldest index (index 0) is not -1, we have found at least 3 occurrences
            if (lastThree[val][0] != -1) {
                // Mathematical optimization:
                // Assuming indices a < b < c, the distance formula is:
                // abs(a - b) + abs(b - c) + abs(c - a)
                // This simplifies to: (b - a) + (c - b) + (c - a) = 2 * (c - a)
                // Notice how 'b' cancels out entirely! We only care about the first and last index.
                int dist = 2 * (lastThree[val][2] - lastThree[val][0]);
                minDistance = Math.min(minDistance, dist);
            }
        }
        
        // Return the minimum distance found, or -1 if no triplet was ever formed
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Enter the array elements separated by spaces:");
            String[] input = scanner.nextLine().trim().split("\\s+");
            
            if (input.length == 0 || input[0].isEmpty()) {
                System.out.println("Array cannot be empty.");
                return;
            }

            int[] nums = new int[input.length];
            for (int i = 0; i < input.length; i++) {
                nums[i] = Integer.parseInt(input[i]);
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