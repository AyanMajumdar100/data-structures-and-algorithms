/*
Problem Statement:
You are given a 0-indexed integer array nums. There exists an array arr of length nums.length, 
where arr[i] is the sum of |i - j| over all j such that nums[j] == nums[i] and j != i. 
If there is no such j, set arr[i] to be 0.
Return the array arr.

Constraints:
1 <= nums.length <= 10^5
0 <= nums[i] <= 10^9
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SumDistances {

    public static long[] distance(int[] nums) {
        int n = nums.length;
        long[] ans = new long[n];
        
        // Map to group all the indices where a specific number appears.
        Map<Integer, List<Integer>> map = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            // computeIfAbsent is a cleaner way to initialize the list if it doesn't exist
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }
        
        for (List<Integer> list : map.values()) {
            long totalSum = 0;
            for (int idx : list) {
                totalSum += idx;
            }
            
            long leftSum = 0;
            long leftCount = 0;
            long rightSum = totalSum;
            long rightCount = list.size();
            
            // Calculate the distance for each index using prefix/suffix sums
            for (int idx : list) {
                // Remove the current element from the right-side calculations
                rightSum -= idx;
                rightCount--;
                
                // Math Trick: 
                // Left elements are smaller than 'idx', so |idx - left_elem| becomes (idx - left_elem).
                // Right elements are larger than 'idx', so |idx - right_elem| becomes (right_elem - idx).
                long leftDistance = (idx * leftCount) - leftSum;
                long rightDistance = rightSum - (idx * rightCount);
                
                ans[idx] = leftDistance + rightDistance;
                
                // Add the current element to the left-side calculations for the next iteration
                leftSum += idx;
                leftCount++;
            }
        }
        
        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the elements of nums separated by spaces:");
            String[] inputStr = scanner.nextLine().trim().split("\\s+");
            
            if (inputStr.length == 0 || inputStr[0].isEmpty()) {
                System.out.println("Array cannot be empty.");
                return;
            }

            int[] nums = new int[inputStr.length];
            for (int i = 0; i < inputStr.length; i++) {
                nums[i] = Integer.parseInt(inputStr[i]);
            }
            
            long[] result = distance(nums);
            System.out.print("Output: [");
            for (int i = 0; i < result.length; i++) {
                System.out.print(result[i] + (i == result.length - 1 ? "" : ", "));
            }
            System.out.println("]");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid integers only.");
        } finally {
            scanner.close();
        }
    }
}
