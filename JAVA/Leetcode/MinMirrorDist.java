/*
Problem Statement:
You are given an integer array nums.
A mirror pair is a pair of indices (i, j) such that:
1. 0 <= i < j < nums.length
2. reverse(nums[i]) == nums[j], where reverse(x) denotes the integer formed by reversing the digits of x. 
   Leading zeros are omitted after reversing, for example reverse(120) = 21.

Return the minimum absolute distance between the indices of any mirror pair. 
If no mirror pair exists, return -1.

Constraints:
1 <= nums.length <= 10^5
1 <= nums[i] <= 10^9
*/

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MinMirrorDist {

    public static int minMirrorPairDistance(int[] nums) {
        // Map stores the reversed value of nums[i] as the key, and its index 'i' as the value.
        Map<Integer, Integer> map = new HashMap<>();
        int minDistance = Integer.MAX_VALUE;
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            
            // Check if the current number satisfies the condition for a previously seen reversed number.
            // Since we scan left to right, 'i' is always > the stored index (j > i condition met).
            if (map.containsKey(num)) {
                minDistance = Math.min(minDistance, i - map.get(num));
            }
            
            // Reverse the current number mathematically (O(log10(num)) time)
            int rev = 0;
            int temp = num;
            while (temp > 0) {
                rev = rev * 10 + temp % 10;
                temp /= 10;
            }
            
            // Store or update the index of the reversed number.
            // Updating naturally keeps the largest (most recent) index, 
            // which guarantees we find the minimum possible distance for future matches.
            map.put(rev, i);
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
            
            int result = minMirrorPairDistance(nums);
            System.out.println("Minimum Absolute Distance: " + result);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid integers only.");
        } finally {
            scanner.close();
        }
    }
}