/*
 * Problem Statement:
 * You are given an integer n representing the number of nodes in a graph, labeled from 0 to n - 1.
 * You are also given an integer array nums of length n sorted in non-decreasing order, and an integer maxDiff.
 * An undirected edge exists between nodes i and j if |nums[i] - nums[j]| <= maxDiff.
 * For each query [ui, vi], determine whether there exists a path between nodes ui and vi.
 */

/*
 * Approach: Connected Components using Array Sorted Property (O(N + Q))
 * Key Insight: 
 * Because the `nums` array is already sorted in non-decreasing order, an element `nums[i]` can connect 
 * to elements further down the array if and only if it can reach them through a continuous chain of 
 * adjacent elements where each step satisfies: nums[k] - nums[k - 1] <= maxDiff.
 * * If a gap between two adjacent elements is strictly greater than maxDiff, it acts as a permanent wall. 
 * No element from the left of the gap can ever reach any element to the right of the gap.
 * * 1. Maintain a component ID array `comp`. Initialize `comp[0] = 0`.
 * 2. Sweep from left to right. If `nums[i] - nums[i - 1] <= maxDiff`, node `i` belongs to the same 
 * connected component as node `i - 1`. Otherwise, increment the component ID.
 * 3. For each query [ui, vi], simply check if `comp[ui] == comp[vi]` in constant O(1) time.
 */
import java.util.Arrays;
import java.util.Scanner;

public class PathQueries3532 {
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[] comp = new int[n];
        
        // Single linear pass to mark component IDs based on valid adjacent transitions
        for (int i = 1; i < n; i++) {
            if (nums[i] - nums[i - 1] <= maxDiff) {
                comp[i] = comp[i - 1]; // Chain is intact
            } else {
                comp[i] = comp[i - 1] + 1; // Disconnected chunk boundary encountered
            }
        }
        
        boolean[] ans = new boolean[queries.length];
        // Answer each component reachability check in O(1)
        for (int i = 0; i < queries.length; i++) {
            ans[i] = comp[queries[i][0]] == comp[queries[i][1]];
        }
        
        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter size of nums array n:");
        int n = scanner.nextInt();
        
        int[] nums = new int[n];
        System.out.println("Enter sorted elements of nums array:");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        
        System.out.println("Enter maxDiff:");
        int maxDiff = scanner.nextInt();
        
        System.out.println("Enter number of queries:");
        int numQueries = scanner.nextInt();
        int[][] queries = new int[numQueries][2];
        System.out.println("Enter queries (u v) line by line:");
        for (int i = 0; i < numQueries; i++) {
            queries[i][0] = scanner.nextInt();
            queries[i][1] = scanner.nextInt();
        }
        
        PathQueries3532 solver = new PathQueries3532();
        boolean[] result = solver.pathExistenceQueries(n, nums, maxDiff, queries);
        System.out.println("Query Answers: " + Arrays.toString(result));
        
        scanner.close();
    }
}
