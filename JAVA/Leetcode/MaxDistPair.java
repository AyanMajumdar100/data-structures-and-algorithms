/*
Problem Statement:
You are given two non-increasing 0-indexed integer arrays nums1 and nums2.
A pair of indices (i, j), where 0 <= i < nums1.length and 0 <= j < nums2.length, 
is valid if both i <= j and nums1[i] <= nums2[j]. The distance of the pair is j - i.
Return the maximum distance of any valid pair (i, j). If there are no valid pairs, return 0.

An array arr is non-increasing if arr[i-1] >= arr[i] for every 1 <= i < arr.length.

Constraints:
1 <= nums1.length, nums2.length <= 10^5
1 <= nums1[i], nums2[j] <= 10^5
Both nums1 and nums2 are non-increasing.
*/

import java.util.Scanner;

public class MaxDistPair {

    public static int maxDistance(int[] nums1, int[] nums2) {
        int i = 0; // Pointer for nums1
        int j = 0; // Pointer for nums2
        int maxDist = 0;
        
        // Traverse both arrays simultaneously
        while (i < nums1.length && j < nums2.length) {
            // If the condition is met, we have a valid pair
            if (nums1[i] <= nums2[j]) {
                // Calculate distance and update the maximum distance found so far
                if (j - i > maxDist) {
                    maxDist = j - i;
                }
                // Since we want the MAXIMUM distance, we push 'j' further to the right
                j++;
            } else {
                // The condition failed (nums1[i] is too big). 
                // Since arrays are descending, we move 'i' to the right to find a smaller number.
                i++;
            }
        }
        
        return maxDist;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Enter elements for nums1 (non-increasing) separated by space:");
            String[] input1 = scanner.nextLine().trim().split("\\s+");
            int[] nums1 = new int[input1.length];
            for (int i = 0; i < input1.length; i++) {
                nums1[i] = Integer.parseInt(input1[i]);
            }
            
            System.out.println("Enter elements for nums2 (non-increasing) separated by space:");
            String[] input2 = scanner.nextLine().trim().split("\\s+");
            int[] nums2 = new int[input2.length];
            for (int i = 0; i < input2.length; i++) {
                nums2[i] = Integer.parseInt(input2[i]);
            }
            
            int result = maxDistance(nums1, nums2);
            System.out.println("Maximum Distance: " + result);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter valid integers only.");
        } finally {
            scanner.close();
        }
    }
}