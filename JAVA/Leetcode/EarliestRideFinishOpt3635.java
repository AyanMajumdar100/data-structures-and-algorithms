/*
 * Problem Statement:
 * You are given two categories of theme park attractions: land rides and water rides.
 * Land rides: landStartTime[i] (earliest board time), landDuration[i] (duration).
 * Water rides: waterStartTime[j] (earliest board time), waterDuration[j] (duration).
 * A tourist must experience exactly one ride from each category, in either order.
 * Return the earliest possible time at which the tourist can finish both rides.
 * * Note: Arrays can be up to length 50,000, so an O(N*M) solution will Time Out.
 */

/*
 * Approach: Sorting + Binary Search + Prefix/Suffix Arrays
 * To solve this efficiently (O(N log N + M log M)), we evaluate the two main orderings:
 * 1. Land ride first, then Water ride.
 * 2. Water ride first, then Land ride.
 *
 * For a chosen first ride ending at `target`, the second ride can either:
 * A) Have started BEFORE `target`. In this case, we have to wait at the entrance until `target`.
 * Finish time = `target` + duration of second ride.
 * To minimize this, we want the second ride with the MINIMUM duration among all those starting before `target`.
 * B) Start AT or AFTER `target`. In this case, we walk up and board immediately when it opens.
 * Finish time = start time of second ride + duration of second ride.
 * To minimize this, we want the second ride with the MINIMUM overall finish time among all those starting after `target`.
 *
 * By sorting the second rides by start time, we can use binary search to find the cutoff for `target`.
 * We precompute prefix minimums for duration (Case A) and suffix minimums for finish times (Case B) to answer queries in O(1).
 */
import java.util.Scanner;
import java.util.Arrays;

public class EarliestRideFinishOpt3635 {

    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int n = landStartTime.length;
        int m = waterStartTime.length;
        
        // Pack start time and duration into a single long for efficient sorting by start time
        // Higher 32 bits = start time, lower 32 bits = duration
        long[] landPacked = new long[n];
        for (int i = 0; i < n; i++) {
            landPacked[i] = ((long) landStartTime[i] << 32) | landDuration[i];
        }
        
        long[] waterPacked = new long[m];
        for (int i = 0; i < m; i++) {
            waterPacked[i] = ((long) waterStartTime[i] << 32) | waterDuration[i];
        }
        
        // Sort arrays. Since start time is in the higher bits, this sorts primarily by start time
        Arrays.sort(landPacked);
        Arrays.sort(waterPacked);
        
        // Check both permutations of ride order
        int ans1 = solve(landPacked, waterPacked);
        int ans2 = solve(waterPacked, landPacked);
        
        return Math.min(ans1, ans2);
    }
    
    private int solve(long[] first, long[] second) {
        int m = second.length;
        int[] prefMinDur = new int[m]; // Prefix min of durations
        int[] suffMinEnd = new int[m]; // Suffix min of (start + duration)
        
        int[] secStart = new int[m];
        int[] secDur = new int[m];
        
        // Unpack the sorted second array
        for (int i = 0; i < m; i++) {
            secStart[i] = (int) (second[i] >>> 32);
            secDur[i] = (int) second[i];
        }
        
        // Compute prefix minimums for duration
        // Helps answer: "What is the shortest ride that opened before I finished the first ride?"
        prefMinDur[0] = secDur[0];
        for (int i = 1; i < m; i++) {
            prefMinDur[i] = Math.min(prefMinDur[i - 1], secDur[i]);
        }
        
        // Compute suffix minimums for finish time
        // Helps answer: "What is the earliest finishing ride among those that open after I finish the first ride?"
        suffMinEnd[m - 1] = secStart[m - 1] + secDur[m - 1];
        for (int i = m - 2; i >= 0; i--) {
            suffMinEnd[i] = Math.min(suffMinEnd[i + 1], secStart[i] + secDur[i]);
        }
        
        int best = Integer.MAX_VALUE;
        
        // Iterate through all possible choices for the first ride
        for (long f : first) {
            int target = (int) (f >>> 32) + (int) f; // calculate when the first ride ends
            
            // Binary search to find the first ride in the 'second' category that starts AT or AFTER 'target'
            int left = 0, right = m;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (secStart[mid] >= target) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            
            // Case 1: Start the second ride at or after the first ride ends
            // The tourist boards immediately when the ride opens.
            if (left < m) {
                best = Math.min(best, suffMinEnd[left]);
            }
            
            // Case 2: Start the second ride before the first ride ends 
            // The tourist waits at the entrance until they finish their first ride (time = target).
            if (left > 0) {
                best = Math.min(best, target + prefMinDur[left - 1]);
            }
        }
        
        return best;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Helper function to read input arrays
        System.out.println("Enter landStartTime separated by space:");
        String[] lst = scanner.nextLine().trim().split("\\s+");
        if(lst[0].isEmpty()) return;
        int[] landStartTime = new int[lst.length];
        for (int i = 0; i < lst.length; i++) landStartTime[i] = Integer.parseInt(lst[i]);

        System.out.println("Enter landDuration separated by space:");
        String[] ld = scanner.nextLine().trim().split("\\s+");
        int[] landDuration = new int[ld.length];
        for (int i = 0; i < ld.length; i++) landDuration[i] = Integer.parseInt(ld[i]);

        System.out.println("Enter waterStartTime separated by space:");
        String[] wst = scanner.nextLine().trim().split("\\s+");
        int[] waterStartTime = new int[wst.length];
        for (int i = 0; i < wst.length; i++) waterStartTime[i] = Integer.parseInt(wst[i]);

        System.out.println("Enter waterDuration separated by space:");
        String[] wd = scanner.nextLine().trim().split("\\s+");
        int[] waterDuration = new int[wd.length];
        for (int i = 0; i < wd.length; i++) waterDuration[i] = Integer.parseInt(wd[i]);

        EarliestRideFinishOpt3635 solver = new EarliestRideFinishOpt3635();
        int result = solver.earliestFinishTime(landStartTime, landDuration, waterStartTime, waterDuration);
        System.out.println("Earliest finish time: " + result);
        
        scanner.close();
    }
}
