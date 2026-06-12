/*
 * Problem Statement:
 * There is an undirected tree with n nodes labeled from 1 to n, rooted at node 1.
 * You must assign each edge a weight of either 1 or 2. You are given a 2D integer array queries.
 * For each queries[i] = [ui, vi], determine the number of ways to assign weights to edges 
 * in the path such that the cost of the path between ui and vi is odd.
 * Return an array answer, where answer[i] is the number of valid assignments for queries[i] modulo 10^9 + 7.
 * Note: For each query, disregard all edges not in the path between node ui and vi.
 */

/*
 * Approach: Binary Lifting (LCA) + Combinatorics
 * 1. For each path between u and v, let `len` be the number of edges on the unique path between them.
 * 2. If u == v, len = 0, and the sum is always 0 (even), so the number of ways to make it odd is 0.
 * 3. If len > 0, we can assign weights of 1 or 2 to each edge. For the sum to be odd, we must pick 
 * an odd number of edges to have weight 1. The number of ways to do this is exactly 2^(len - 1).
 * 4. To find `len` efficiently for multiple queries, we find the Lowest Common Ancestor (LCA) using Binary Lifting.
 * The length of the path is: depth[u] + depth[v] - 2 * depth[lca].
 * 5. Precompute powers of 2 modulo 10^9 + 7 to answer each query in O(log N) time.
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Arrays;

public class NumWaysAssignII3559 {
    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n = edges.length + 1;
        int LOG = 0;
        while ((1 << LOG) <= n) {
            LOG++;
        }
        
        List<Integer>[] adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        
        int[] depth = new int[n + 1];
        int[][] up = new int[n + 1][LOG];
        
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        
        // Root the tree at Node 1
        queue.offer(1);
        visited[1] = true;
        depth[1] = 0;
        for (int j = 0; j < LOG; j++) {
            up[1][j] = 1;
        }
        
        // BFS to build the depth array and initialize binary lifting parent tables
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    depth[v] = depth[u] + 1;
                    up[v][0] = u; // Immediate parent tracking
                    for (int j = 1; j < LOG; j++) {
                        up[v][j] = up[up[v][j - 1]][j - 1]; // Compute 2^j ancestors
                    }
                    queue.offer(v);
                }
            }
        }
        
        int MOD = 1_000_000_007;
        int[] pow2 = new int[n + 1];
        pow2[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow2[i] = (pow2[i - 1] * 2) % MOD;
        }
        
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            if (u == v) {
                ans[i] = 0; // Length 0 path sum is 0, which is always even
            } else {
                int lca = getLCA(u, v, depth, up, LOG);
                int len = depth[u] + depth[v] - 2 * depth[lca]; // Calculate path length
                ans[i] = pow2[len - 1]; // Number of combinations is 2^(len - 1)
            }
        }
        
        return ans;
    }
    
    private int getLCA(int u, int v, int[] depth, int[][] up, int LOG) {
        // Bring node u to a deeper level than or equal to node v
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        
        // Lift u to the same depth level as v
        for (int j = LOG - 1; j >= 0; j--) {
            if (depth[u] - (1 << j) >= depth[v]) {
                u = up[u][j];
            }
        }
        
        if (u == v) return u;
        
        // Lift both nodes together right below their common ancestor
        for (int j = LOG - 1; j >= 0; j--) {
            if (up[u][j] != up[v][j]) {
                u = up[u][j];
                v = up[v][j];
            }
        }
        
        return up[u][0]; // Return the direct parent which is the LCA
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of nodes N:");
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
        
        int[][] edges = new int[n - 1][2];
        System.out.println("Enter edges (u v) line by line:");
        for (int i = 0; i < n - 1; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
        }
        
        System.out.println("Enter number of queries Q:");
        int q = scanner.nextInt();
        int[][] queries = new int[q][2];
        System.out.println("Enter queries (u v) line by line:");
        for (int i = 0; i < q; i++) {
            queries[i][0] = scanner.nextInt();
            queries[i][1] = scanner.nextInt();
        }
        
        NumWaysAssignII3559 solver = new NumWaysAssignII3559();
        int[] result = solver.assignEdgeWeights(edges, queries);
        System.out.println("Query Results: " + Arrays.toString(result));
        
        scanner.close();
    }
}