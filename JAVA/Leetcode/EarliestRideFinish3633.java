/*
 * Problem Statement:
 * You are given two categories of theme park attractions: land rides and water rides.
 * Land rides: landStartTime[i] (earliest board time), landDuration[i] (duration).
 * Water rides: waterStartTime[j] (earliest board time), waterDuration[j] (duration).
 * A tourist must experience exactly one ride from each category, in either order.
 * Return the earliest possible time at which the tourist can finish both rides.
 */

/*
 * Approach:
 * Since the constraints are small (n, m <= 100), we can iterate through all possible pairs 
 * of land rides (i) and water rides (j).
 * For each pair, we calculate the finish time for two scenarios:
 * 1. Land ride first, then water ride.
 * 2. Water ride first, then land ride.
 * To calculate the finish time of the second ride, we take the maximum of the first ride's 
 * finish time and the second ride's start time, then add the second ride's duration.
 * We keep track of the minimum finish time across all scenarios and pairs.
 */
import java.util.Scanner;

public class EarliestRideFinish3633 {

    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int minFinishTime = Integer.MAX_VALUE;
        int n = landStartTime.length;
        int m = waterStartTime.length;
        
        // Iterate through all possible pairs of land and water rides
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // Scenario 1: Do land ride i first, then water ride j
                int finishLandFirst = landStartTime[i] + landDuration[i];
                int finish1 = Math.max(finishLandFirst, waterStartTime[j]) + waterDuration[j];
                
                // Scenario 2: Do water ride j first, then land ride i
                int finishWaterFirst = waterStartTime[j] + waterDuration[j];
                int finish2 = Math.max(finishWaterFirst, landStartTime[i]) + landDuration[i];
                
                // Find the best order for this specific pair
                int bestForPair = Math.min(finish1, finish2);
                
                // Update the global minimum finish time if this pair is better
                if (bestForPair < minFinishTime) {
                    minFinishTime = bestForPair;
                }
            }
        }
        
        return minFinishTime;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Helper function to read integer arrays from a line of space-separated strings
        System.out.println("Enter landStartTime separated by space:");
        String[] lst = scanner.nextLine().trim().split("\\s+");
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

        EarliestRideFinish3633 solver = new EarliestRideFinish3633();
        int result = solver.earliestFinishTime(landStartTime, landDuration, waterStartTime, waterDuration);
        System.out.println("Earliest finish time: " + result);
        
        scanner.close();
    }
}
