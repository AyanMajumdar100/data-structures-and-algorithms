/*
Problem Statement:
There are some robots and factories on the X-axis. 
You are given an integer array robot where robot[i] is the position of the ith robot. 
You are also given a 2D integer array factory where factory[j] = [position, limit] indicates 
that the jth factory is at 'position' and can repair at most 'limit' robots.

All robots are initially broken and move in one direction. You can set the initial direction 
to minimize the total distance traveled by all robots to be repaired.
Return the minimum total distance traveled by all the robots.

Constraints:
1 <= robot.length, factory.length <= 100
factory[j].length == 2
-10^9 <= robot[i], positionj <= 10^9
0 <= limitj <= robot.length
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MinTotalDist {

    public static long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        // Step 1: Sort robots and factories by their positions from left to right on the X-axis
        Collections.sort(robot);
        Arrays.sort(factory, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Step 2: Flatten the factories into a 1D list of individual available slots.
        // If a factory has a limit of 3, we add its position to this list 3 times.
        List<Integer> factoryPositions = new ArrayList<>();
        int n = robot.size();
        for (int[] f : factory) {
            // We never need more slots from a single factory than the total number of robots
            int limit = Math.min(f[1], n);
            for (int i = 0; i < limit; i++) {
                factoryPositions.add(f[0]);
            }
        }
        
        // Step 3: Dynamic Programming array setup
        // dp[i] represents the minimum distance to repair the first 'i' robots
        long[] dp = new long[n + 1];
        long INF = (long) 1e18; // A very large number to represent infinity
        Arrays.fill(dp, INF);
        dp[0] = 0; // 0 distance to repair 0 robots
        
        // Step 4: Process each factory slot one by one
        for (int p : factoryPositions) {
            // Traverse backwards to use 1D DP space effectively (avoids overwriting data we still need)
            for (int i = n; i >= 1; i--) {
                // If it is possible to repair 'i-1' robots, see if we can repair the 'i-th' robot here
                if (dp[i - 1] != INF) {
                    // Calculate the distance for the current robot to reach this specific factory slot
                    long currentDistance = Math.abs((long) robot.get(i - 1) - p);
                    // Update the minimum distance to repair 'i' robots
                    dp[i] = Math.min(dp[i], dp[i - 1] + currentDistance);
                }
            }
        }
        
        return dp[n];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter robot positions separated by space:");
            String[] robotInput = scanner.nextLine().trim().split("\\s+");
            List<Integer> robot = new ArrayList<>();
            for (String s : robotInput) {
                robot.add(Integer.parseInt(s));
            }
            
            System.out.println("Enter number of factories:");
            int numFactories = Integer.parseInt(scanner.nextLine().trim());
            int[][] factory = new int[numFactories][2];
            
            System.out.println("Enter factory data (position limit) on separate lines:");
            for (int i = 0; i < numFactories; i++) {
                String[] factInput = scanner.nextLine().trim().split("\\s+");
                factory[i][0] = Integer.parseInt(factInput[0]);
                factory[i][1] = Integer.parseInt(factInput[1]);
            }
            
            long result = minimumTotalDistance(robot, factory);
            System.out.println("Minimum Total Distance: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected. Please try again.");
        } finally {
            scanner.close();
        }
    }
}