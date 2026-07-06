/*
 * Problem Statement:
 * Given an array of intervals where intervals[i] = [li, ri], remove all intervals 
 * that are covered by another interval in the list.
 * An interval [a, b) is covered by [c, d) if and only if c <= a and b <= d.
 * Return the number of remaining intervals.
 */

/*
 * Approach: Custom Sorting + Greedy End-Bound Tracking
 * 1. Sorting: Sort intervals primarily by their start times in ascending order (`a[0] - b[0]`).
 * - Critical Tie-breaker: If two intervals have the exact same start time, sort them by 
 * their end times in descending order (`b[1] - a[1]`). This ensures that the larger 
 * enclosing interval is processed first, correctly covering shorter intervals that follow.
 * 2. Iterative Sweep: Keep track of the maximum end bound (`maxEnd`) seen among valid intervals.
 * - Since the list is sorted by start times, any subsequent interval `curr` will naturally 
 * have `curr[0] >= prev[0]`.
 * - If `curr[1] <= maxEnd`, then this interval is completely swallowed/covered by a 
 * previously processed interval. We ignore it.
 * - If `curr[1] > maxEnd`, it cannot be covered. It counts as a unique remaining interval, 
 * and we update `maxEnd = curr[1]`.
 */
import java.util.Arrays;
import java.util.Scanner;

public class RemoveIntervals1288 {
    public int removeCoveredIntervals(int[][] intervals) {
        // Step 1: Sort by start ascending, then by end descending for ties
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return Integer.compare(b[1], a[1]);
            }
            return Integer.compare(a[0], b[0]);
        });

        int remaining = 0;
        int maxEnd = 0;

        // Step 2: Linear sweep checking end boundaries
        for (int[] interval : intervals) {
            // If the current interval's right endpoint stretches past maxEnd, it survives
            if (interval[1] > maxEnd) {
                remaining++;
                maxEnd = interval[1];
            }
        }

        return remaining;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of intervals:");
        int n = scanner.nextInt();
        
        int[][] intervals = new int[n][2];
        System.out.println("Enter intervals (start end) line by line:");
        for (int i = 0; i < n; i++) {
            intervals[i][0] = scanner.nextInt();
            intervals[i][1] = scanner.nextInt();
        }
        
        RemoveIntervals1288 solver = new RemoveIntervals1288();
        int result = solver.removeCoveredIntervals(intervals);
        System.out.println("Number of remaining non-covered intervals: " + result);
        
        scanner.close();
    }
}
