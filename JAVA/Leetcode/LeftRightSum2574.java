/*
 * Problem Statement:
 * You are given a 0-indexed integer array nums of size n.
 * Define two arrays leftSum and rightSum where:
 * - leftSum[i] is the sum of elements to the left of the index i in the array nums. 
 * If there is no such element, leftSum[i] = 0.
 * - rightSum[i] is the sum of elements to the right of the index i in the array nums. 
 * If there is no such element, rightSum[i] = 0.
 * Return an integer array answer of size n where answer[i] = |leftSum[i] - rightSum[i]|.
 */

/*
 * Approach: Total Sum Track / Linear Scan
 * Instead of creating two full auxiliary arrays for leftSum and rightSum (which takes O(N) space),
 * we can compute the overall sum of the array first and store it in rightSum.
 * We then initialize leftSum to 0.
 * As we iterate through each element from left to right:
 * 1. Subtract the current element from rightSum (since it's no longer to the right of itself).
 * 2. Compute the absolute difference between leftSum and rightSum for the current index.
 * 3. Add the current element to leftSum so it becomes part of the prefix for the next elements.
 */
import java.util.Scanner;
import java.util.Arrays;

public class LeftRightSum2574 {
    public int[] leftRightDifference(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];
        
        // Calculate the total sum of all elements to represent initial rightSum
        int rightSum = 0;
        for (int num : nums) {
            rightSum += num;
        }
        
        int leftSum = 0;
        // Single pass to update leftSum and rightSum dynamically
        for (int i = 0; i < n; i++) {
            // The current element is no longer to the right of index i
            rightSum -= nums[i];
            
            // Calculate absolute difference between elements on the left and right sides
            answer[i] = Math.abs(leftSum - rightSum);
            
            // Add the current element to leftSum for subsequent indices
            leftSum += nums[i];
        }
        
        return answer;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
        
        LeftRightSum2574 solver = new LeftRightSum2574();
        int[] result = solver.leftRightDifference(nums);
        
        System.out.println("Result: " + Arrays.toString(result));
        scanner.close();
    }
}
