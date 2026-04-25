/*
Problem Statement:
You are given an integer side, representing the edge length of a square with corners at 
(0, 0), (0, side), (side, 0), and (side, side) on a Cartesian plane.
You are also given a positive integer k and a 2D integer array points, where points[i] = [xi, yi] 
represents the coordinate of a point lying on the boundary of the square.

You need to select k elements among points such that the minimum Manhattan distance between any 
two points is maximized.
Return the maximum possible minimum Manhattan distance between the selected k points.

Constraints:
1 <= side <= 10^9
4 <= points.length <= min(4 * side, 15 * 10^3)
points[i] lies on the boundary of the square.
All points[i] are unique.
4 <= k <= min(25, points.length)
*/

import java.util.Arrays;
import java.util.Scanner;

public class SquarePointsMaxDist {

    public static int maxDistance(int side, int[][] points, int k) {
        int n = points.length;
        long[] pos = new long[n];
        
        // Step 1: Flatten 2D boundary coordinates into a 1D perimeter distance from (0,0)
        for (int i = 0; i < n; i++) {
            pos[i] = getPos(points[i][0], points[i][1], side);
        }
        
        // Sort the points in clockwise order
        Arrays.sort(pos);
        
        // Step 2: Duplicate the array to handle the circular wrap-around easily
        long[] arr = new long[2 * n];
        long perimeter = 4L * side;
        for (int i = 0; i < n; i++) {
            arr[i] = pos[i];
            arr[i + n] = pos[i] + perimeter;
        }
        
        // Step 3: Binary Search on the minimum maximum distance
        // The answer can't exceed 'side' because k >= 4. 
        int low = 1;
        int high = side;
        int ans = 0;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (check(mid, arr, n, k, perimeter)) {
                ans = mid; // This distance is possible, try for a larger one
                low = mid + 1;
            } else {
                high = mid - 1; // This distance is too large, scale back
            }
        }
        
        return ans;
    }
    
    // Helper to map a 2D coordinate on the boundary to a 1D clockwise distance from (0,0)
    private static long getPos(long x, long y, long side) {
        if (y == 0) return x;                   // Bottom edge
        if (x == side) return side + y;         // Right edge
        if (y == side) return 3L * side - x;    // Top edge
        return 4L * side - y;                   // Left edge
    }
    
    // Helper to verify if we can pick 'k' points with at least 'd' distance between them
    private static boolean check(int d, long[] arr, int n, int k, long perimeter) {
        int[] next = new int[2 * n];
        int j = 0;
        
        // Precompute the next valid point we can jump to for EVERY point using two pointers. O(N)
        for (int i = 0; i < 2 * n; i++) {
            while (j < 2 * n && arr[j] - arr[i] < d) {
                j++;
            }
            next[i] = j;
        }
        
        // Try starting the sequence from each of the original 'n' points
        for (int i = 0; i < n; i++) {
            int curr = i;
            int count = 1;
            
            // Greedily pick the next valid point until we have 'k' points
            while (count < k && curr < 2 * n) {
                curr = next[curr];
                count++;
            }
            
            // If we found 'k' points, ensure the gap between the LAST point and the FIRST point 
            // across the wrap-around is also >= d. 
            // Remaining perimeter gap is: perimeter - (arr[curr] - arr[i]).
            // (perimeter - (arr[curr] - arr[i]) >= d) algebraically shifts to the below:
            if (count == k && curr < 2 * n && arr[curr] - arr[i] <= perimeter - d) {
                return true;
            }
        }
        
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter square side length: ");
            int side = scanner.nextInt();
            
            System.out.print("Enter k (number of points to select): ");
            int k = scanner.nextInt();
            
            System.out.print("Enter total number of points: ");
            int n = scanner.nextInt();
            
            int[][] points = new int[n][2];
            System.out.println("Enter the points (x y) one by one:");
            for (int i = 0; i < n; i++) {
                points[i][0] = scanner.nextInt();
                points[i][1] = scanner.nextInt();
            }
            
            int result = maxDistance(side, points, k);
            System.out.println("Maximum Possible Minimum Manhattan Distance: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected. Please try again.");
        } finally {
            scanner.close();
        }
    }
}