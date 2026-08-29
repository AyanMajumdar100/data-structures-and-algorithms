/*
 * Problem Statement:
 * Given an integer array nums and an integer limit, you can swap nums[i] and nums[j]
 * if |nums[i] - nums[j]| <= limit. Return the lexicographically smallest array reachable
 * after performing the swap operation any number of times.
 */

/*
 * Approach: Connected Components via Value Sorting (O(N log N) Time, O(N) Space)
 * 1. Transitivity of Swaps:
 *    If element A can swap with B, and B can swap with C, then any permutation of {A, B, C}
 *    is achievable across their combined positions.
 * 2. Grouping by Proximity:
 *    Pair each element with its original index `(nums[i], i)` and sort primarily by value.
 *    Any contiguous block of elements in the sorted array where consecutive difference 
 *    `pairs[k + 1].val - pairs[k].val <= limit` belongs to the same connected component.
 * 3. Optimal Placement:
 *    For each connected component, collect all their original indices and sort them.
 *    Assign the smallest values to the smallest indices to ensure lexicographical minimality.
 */

import java.util.Arrays;
import java.util.Scanner;

public class MakeLexicographicallySmArr {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;
        int[][] pairs = new int[n][2];
        for (int i = 0; i < n; i++) {
            pairs[i][0] = nums[i];
            pairs[i][1] = i;
        }

        // Sort pairs primarily by value
        Arrays.sort(pairs, (a, b) -> Integer.compare(a[0], b[0]));

        int[] result = new int[n];
        int i = 0;

        // Process connected components
        while (i < n) {
            int j = i;
            while (j + 1 < n && pairs[j + 1][0] - pairs[j][0] <= limit) {
                j++;
            }

            // Extract original indices for this component
            int componentSize = j - i + 1;
            int[] indices = new int[componentSize];
            for (int k = i; k <= j; k++) {
                indices[k - i] = pairs[k][1];
            }

            // Sort original indices in ascending order
            Arrays.sort(indices);

            // Assign sorted values to sorted indices
            for (int k = i; k <= j; k++) {
                result[indices[k - i]] = pairs[k][0];
            }

            i = j + 1;
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Result: []");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNumsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNumsArray[i] = Integer.parseInt(tokens[i]);
        }

        System.out.println("Enter integer limit:");
        int userLimitParam = scanner.nextInt();

        MakeLexicographicallySmallestArray2948 solverInstance = new MakeLexicographicallySmallestArray2948();
        int[] sortedResult = solverInstance.lexicographicallySmallestArray(userNumsArray, userLimitParam);

        System.out.println("Lexicographically smallest array: " + Arrays.toString(sortedResult));
        scanner.close();
    }
}
