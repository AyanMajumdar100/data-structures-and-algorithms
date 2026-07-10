/*
 * Problem Statement:
 * You are given an integer n representing the number of nodes in a graph, labeled from 0 to n - 1.
 * You are also given an integer array nums of length n and an integer maxDiff.
 * An undirected edge exists between nodes i and j if |nums[i] - nums[j]| <= maxDiff.
 * For each query [ui, vi], find the minimum distance between nodes ui and vi. 
 * If no path exists between the two nodes, return -1.
 * 
 * Approach: Unique Value Sorting + Greedy Jump Pointers + Binary Lifting (O((N + Q) log N))
 * 
 * 1. Values over Indices: The initial array isn't sorted and contains duplicate values. 
 *    Since nodes with the same value are functionally identical and share the same edges, 
 *    we extract unique values and sort them into a distinct array `U`.
 * 2. Greedy Jumping: For any sorted unique value index `i`, we want to make the longest step to the 
 *    right while maintaining a value difference <= maxDiff. Using a sliding two-pointer window, we compute 
 *    `next[i]`, which represents the furthest index reached in one hop.
 * 3. Binary Lifting: To avoid linear hopping (which causes an O(N) TLE query penalty), we build 
 *    an ancestral doubling table `up[j][i]` mapping where an item lands after making 2^j maximum hops.
 * 4. Logarithmic Queries: For a query between two sorted indexes `iu` and `iv`, we use the binary lifting 
 *    table from the largest power down to 0 to hop as close to `iv` as possible without hitting or passing it. 
 *    If one final step reaches or exceeds `iv`, our total hops is the answer; otherwise, they are disconnected.
 */
import java.util.Arrays;
import java.util.Scanner;

public class PathQueriesII3534 {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int maxVal = 0;
        for (int num : nums) {
            if (num > maxVal) {
                maxVal = num;
            }
        }
        
        // Step 1: Extract unique values and arrange them in sorted order
        boolean[] present = new boolean[maxVal + 1];
        int uniqueCount = 0;
        for (int num : nums) {
            if (!present[num]) {
                present[num] = true;
                uniqueCount++;
            }
        }
        
        int[] U = new int[uniqueCount];
        int idx = 0;
        for (int i = 0; i <= maxVal; i++) {
            if (present[i]) {
                U[idx++] = i;
            }
        }
        
        // Reverse lookup mapping from numerical value to its index inside array U
        int[] valToIdx = new int[maxVal + 1];
        for (int i = 0; i < uniqueCount; i++) {
            valToIdx[U[i]] = i;
        }
        
        // Step 2: Compute greedy forward jump index targets via sliding window
        int[] next = new int[uniqueCount];
        int r = 0;
        for (int i = 0; i < uniqueCount; i++) {
            while (r < uniqueCount && U[r] - U[i] <= maxDiff) {
                r++;
            }
            next[i] = r - 1; // Furthest index reachable within maxDiff boundary
        }
        
        // Step 3: Populate the binary lifting table up[LOG][N]
        int[][] up = new int[17][uniqueCount];
        for (int i = 0; i < uniqueCount; i++) {
            up[0][i] = next[i];
        }
        for (int j = 1; j < 17; j++) {
            for (int i = 0; i < uniqueCount; i++) {
                up[j][i] = up[j - 1][up[j - 1][i]];
            }
        }
        
        // Step 4: Process all distance calculation queries using the binary lifting structure
        int[] ans = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int u = queries[q][0];
            int v = queries[q][1];
            
            if (u == v) {
                ans[q] = 0;
                continue;
            }
            
            int numU = nums[u];
            int numV = nums[v];
            if (numU == numV) {
                ans[q] = 1; // Nodes share the same value -> 1 edge hop via their shared value cluster
                continue;
            }
            
            int iu = valToIdx[numU];
            int iv = valToIdx[numV];
            // Ensure we are always jumping forward from a smaller value index to a larger value index
            if (iu > iv) {
                int tmp = iu;
                iu = iv;
                iv = tmp;
            }
            
            int curr = iu;
            int steps = 0;
            // Jump as far as possible without reaching or overshooting iv
            for (int j = 16; j >= 0; j--) {
                if (up[j][curr] < iv) {
                    curr = up[j][curr];
                    steps += (1 << j);
                }
            }
            
            // Check if one final single hop from the current landing position can bridge the gap to iv
            if (next[curr] >= iv) {
                ans[q] = steps + 1;
            } else {
                ans[q] = -1; // Path is fundamentally blocked
            }
        }
        
        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter size of nums array n:");
        int n = scanner.nextInt();
        
        int[] nums = new int[n];
        System.out.println("Enter array elements:");
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
        
        PathQueriesII3534 solver = new PathQueriesII3534();
        int[] result = solver.pathExistenceQueries(n, nums, maxDiff, queries);
        System.out.println("Shortest Path Distances: " + Arrays.toString(result));
        
        scanner.close();
    }
}
