/*
 * Problem Statement:
 * Given an array of integers arr and an integer d. In one step you can jump from index i to index:
 * - i + x where: i + x < arr.length and  0 < x <= d.
 * - i - x where: i - x >= 0 and  0 < x <= d.
 * In addition, you can only jump from index i to index j if arr[i] > arr[j] and arr[i] > arr[k] 
 * for all indices k between i and j.
 * You can choose any index of the array and start jumping. Return the maximum number of indices you can visit.
 * Notice that you can not jump outside of the array at any time.
 */

/*
 * Approach: Depth-First Search (DFS) with Memoization (Dynamic Programming)
 * We want to find the longest path in a directed acyclic graph (DAG) where edges represent valid jumps.
 * We use an array `dp` to memoize the maximum jumps starting from each index to avoid redundant calculations.
 * For each index `i`, we explore jumping to the right (up to `d` steps) and to the left (up to `d` steps).
 * We stop exploring a direction as soon as we encounter a building (element) that is taller or equal 
 * to the current building `arr[i]`, as it blocks further jumps in that direction.
 * The overall answer is the maximum value computed across all possible starting indices.
 */
import java.util.Scanner;

public class JumpGame5MaxV1340 {

    public int maxJumps(int[] arr, int d) {
        int n = arr.length;
        // dp array to store the maximum jumps possible starting from each index
        int[] dp = new int[n];
        int res = 0;
        
        // Try starting the jump sequence from every possible index
        for (int i = 0; i < n; i++) {
            int jumps = dfs(arr, i, d, n, dp);
            // Update the global maximum jumps found so far
            if (jumps > res) {
                res = jumps;
            }
        }
        
        return res;
    }
    
    private int dfs(int[] arr, int i, int d, int n, int[] dp) {
        // If the result for this index is already computed, return it directly (memoization)
        if (dp[i] != 0) {
            return dp[i];
        }
        
        // Base case: we can always visit the starting index itself (1 index visited)
        int res = 1;
        
        // Explore jumps to the right
        // We can jump up to 'd' steps, but must stay within array bounds
        for (int j = i + 1; j <= i + d && j < n; j++) {
            // A jump is blocked if the target building is greater than or equal to the current
            if (arr[j] >= arr[i]) break;
            
            // Recursively calculate jumps from the valid target index
            int jumps = 1 + dfs(arr, j, d, n, dp);
            if (jumps > res) {
                res = jumps;
            }
        }
        
        // Explore jumps to the left
        // We can jump up to 'd' steps backwards, but must not go below index 0
        for (int j = i - 1; j >= i - d && j >= 0; j--) {
            // A jump is blocked if the target building is greater than or equal to the current
            if (arr[j] >= arr[i]) break;
            
            // Recursively calculate jumps from the valid target index
            int jumps = 1 + dfs(arr, j, d, n, dp);
            if (jumps > res) {
                res = jumps;
            }
        }
        
        // Save the computed maximum jumps for index i into the dp array
        dp[i] = res;
        return res;
    }

    public static void main(String[] args) {
        // Setup scanner to read user input
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the array elements separated by space:");
        String inputLine = scanner.nextLine().trim();
        
        // Parse array input
        String[] parts = inputLine.split("\\s+");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }
        
        System.out.println("Enter max jump distance d:");
        int d = scanner.nextInt();
        
        // Execute the algorithm
        JumpGame5MaxV1340 solver = new JumpGame5MaxV1340();
        int result = solver.maxJumps(arr, d);
        
        System.out.println("Maximum jumps: " + result);
        
        scanner.close();
    }
}