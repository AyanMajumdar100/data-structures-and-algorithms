/*
 * Problem Statement:
 * You are given an integer array nums consisting of unique integers that originally contained
 * every integer within a range [min, max]. Find and return a sorted list of all missing integers.
 */

/*
 * Approach: Sorting + Gap Filling Iteration (O(N log N) Time, O(1) Auxiliary Space)
 * 1. Sort the `nums` array in ascending order.
 * 2. Identify the smallest element `nums[0]` and largest element `nums[nums.length - 1]`.
 * 3. Iterate through the sorted array index by index. For any adjacent pair `nums[i - 1]` and `nums[i]`,
 *    if `nums[i] - nums[i - 1] > 1`, collect all integers between them into the result list.
 * 4. Return the list of missing elements.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FindMissingElements3731 {
    public List<Integer> findMissingElements(int[] nums) {
        Arrays.sort(nums);
        List<Integer> missingElements = new ArrayList<>();
        
        int currentVal = nums[0];
        for (int i = 1; i < nums.length; i++) {
            while (currentVal + 1 < nums[i]) {
                currentVal++;
                missingElements.add(currentVal);
            }
            currentVal = nums[i];
        }
        
        return missingElements;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Missing elements: []");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNumsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNumsArray[i] = Integer.parseInt(tokens[i]);
        }

        FindMissingElements3731 solverInstance = new FindMissingElements3731();
        List<Integer> missingResult = solverInstance.findMissingElements(userNumsArray);

        System.out.println("Missing elements: " + missingResult);
        scanner.close();
    }
}
