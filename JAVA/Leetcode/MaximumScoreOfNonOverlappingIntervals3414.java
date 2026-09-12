/*
 * Problem Statement: LeetCode 3414 - Maximum Score of Non-overlapping Intervals
 * Given an array of intervals [li, ri, weighti], choose at most 4 pairwise non-overlapping intervals
 * (intervals cannot share any points, meaning l_j > r_i) such that total weight is maximized.
 * If there are ties, return the lexicographically smallest list of indices (each set of indices
 * sorted in ascending order, and compared index by index).
 */

/*
 * Approach: Dynamic Programming + Binary Search + Lexicographical Index Tracking
 * 1. Store intervals with original indices `(li, ri, weighti, originalIdx)` and sort by `li`.
 * 2. Precompute next compatible interval:
 *    - For interval `i`, use binary search to find the smallest index `nextJ > i` such that `arr[nextJ][0] > arr[i][1]`.
 * 3. DP State:
 *    - `dpWeight[i][k]` = maximum total weight picking up to `k` intervals from `arr[i...n-1]`.
 *    - `dpIndices[i][k]` = lexicographically smallest sorted array of indices corresponding to `dpWeight[i][k]`.
 * 4. Transitions (for i from n - 1 down to 0, and k from 1 to 4):
 *    - Skip interval i:
 *      Weight: `w1 = dpWeight[i + 1][k]`, Indices: `id1 = dpIndices[i + 1][k]`
 *    - Take interval i:
 *      Weight: `w2 = arr[i][2] + dpWeight[nextJ][k - 1]`, Indices: `id2 = merge(arr[i][3], dpIndices[nextJ][k - 1])`
 *    - Comparison:
 *      * If `w1 > w2` -> keep option 1.
 *      * If `w2 > w1` -> take option 2.
 *      * If `w1 == w2` -> break ties by choosing the lexicographically smaller index array.
 * 5. Answer is `dpIndices[0][4]`.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MaximumScoreOfNonOverlappingIntervals3414 {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        int[][] arr = new int[n][4];
        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i; // original index
        }

        // Sort intervals by start time li
        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        long[][] dpWeight = new long[n + 1][5];
        int[][][] dpIndices = new int[n + 1][5][];

        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                dpIndices[i][k] = new int[0];
            }
        }

        // Backward DP
        for (int i = n - 1; i >= 0; i--) {
            // Binary search for the first interval starting strictly after arr[i][1]
            int nextJ = n;
            int left = i + 1, right = n - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (arr[mid][0] > arr[i][1]) {
                    nextJ = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            for (int k = 1; k <= 4; k++) {
                long w1 = dpWeight[i + 1][k];
                int[] id1 = dpIndices[i + 1][k];

                long w2 = arr[i][2] + dpWeight[nextJ][k - 1];

                if (w1 > w2) {
                    dpWeight[i][k] = w1;
                    dpIndices[i][k] = id1;
                } else if (w2 > w1) {
                    dpWeight[i][k] = w2;
                    dpIndices[i][k] = merge(arr[i][3], dpIndices[nextJ][k - 1]);
                } else {
                    int[] id2 = merge(arr[i][3], dpIndices[nextJ][k - 1]);
                    if (compare(id1, id2) <= 0) {
                        dpWeight[i][k] = w1;
                        dpIndices[i][k] = id1;
                    } else {
                        dpWeight[i][k] = w2;
                        dpIndices[i][k] = id2;
                    }
                }
            }
        }

        return dpIndices[0][4];
    }

    // Inserts id into already-sorted array arr preserving ascending order
    private int[] merge(int id, int[] arr) {
        int[] res = new int[arr.length + 1];
        int i = 0, j = 0;
        boolean inserted = false;
        while (i < arr.length) {
            if (!inserted && id < arr[i]) {
                res[j++] = id;
                inserted = true;
            } else {
                res[j++] = arr[i++];
            }
        }
        if (!inserted) {
            res[j] = id;
        }
        return res;
    }

    // Lexicographical comparison of index arrays
    private int compare(int[] a, int[] b) {
        int len = Math.min(a.length, b.length);
        for (int i = 0; i < len; i++) {
            if (a[i] != b[i]) {
                return Integer.compare(a[i], b[i]);
            }
        }
        return Integer.compare(a.length, b.length);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of intervals:");
        int numIntervals = scanner.nextInt();

        List<List<Integer>> intervalsList = new ArrayList<>();
        System.out.println("Enter intervals as (start end weight) line by line:");
        for (int i = 0; i < numIntervals; i++) {
            List<Integer> interval = new ArrayList<>();
            interval.add(scanner.nextInt());
            interval.add(scanner.nextInt());
            interval.add(scanner.nextInt());
            intervalsList.add(interval);
        }

        MaximumScoreOfNonOverlappingIntervals3414 solver = new MaximumScoreOfNonOverlappingIntervals3414();
        int[] result = solver.maximumWeight(intervalsList);

        System.out.println("Lexicographically smallest optimal index set: " + Arrays.toString(result));
        scanner.close();
    }
}
