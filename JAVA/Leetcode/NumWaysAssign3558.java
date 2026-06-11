/*
 * Problem Statement:
 * There is an undirected tree with n nodes labeled from 1 to n, rooted at node 1.
 * You are given a 2D integer array edges of length n - 1. Initially, all edges have 
 * a weight of 0. You must assign each edge a weight of either 1 or 2.
 * Select any one node x at the maximum depth. Return the number of ways to assign 
 * edge weights in the path from node 1 to x such that its total cost is odd.
 * Since the answer may be large, return it modulo 10^9 + 7.
 * Note: Ignore all edges not in the path from node 1 to x.
 */

/*
 * Approach: BFS Depth Tracking + Combinatorics (Binary Exponentiation)
 * 1. Build an adjacency list representation of the tree and run a Breadth-First Search (BFS) 
 * starting from the root (Node 1) to find the maximum depth of any node.
 * 2. Let `L` be the maximum depth (which also equals the number of edges on the path from 1 to x).
 * 3. Each edge can be assigned a weight of 1 (odd) or 2 (even). For the total path sum to be odd, 
 * we must choose an odd number of edges to have weight 1.
 * 4. Mathematically, the sum of combinations choosing an odd number of elements out of L elements 
 * is always exactly 2^(L - 1).
 * 5. Compute 2^(maxDepth - 1) % (10^9 + 7) efficiently using binary exponentiation.
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class NumWaysAssign3558 {
    public int assignEdgeWeights(int[][] edges) {
        int n = edges.length + 1;
        List<Integer>[] adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }
        
        // Build the tree adjacency structure
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        
        int maxDepth = 0;
        Queue<int[]> queue = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        
        // Push root node 1 with initial depth 0 onto queue
        queue.offer(new int[]{1, 0});
        visited[1] = true;
        
        // Standard BFS loop to identify maximum path length from node 1
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int u = curr[0];
            int d = curr[1];
            
            if (d > maxDepth) {
                maxDepth = d; // Track the absolute maximum distance
            }
            
            for (int v : adj[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    queue.offer(new int[]{v, d + 1});
                }
            }
        }
        
        // Target calculation: 2^(maxDepth - 1) % mod
        long ans = 1;
        long base = 2;
        int exp = maxDepth - 1;
        long mod = 1_000_000_007;
        
        // Fast Modular Exponentiation O(log(exp))
        while (exp > 0) {
            if ((exp & 1) == 1) {
                ans = (ans * base) % mod;
            }
            base = (base * base) % mod;
            exp >>= 1;
        }
        
        return (int) ans;
    }

    public static void main(String[] args) {
        // User Input Handler
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of edges:");
        if (!scanner.hasNextInt()) return;
        int numEdges = scanner.nextInt();
        
        int[][] edges = new int[numEdges][2];
        System.out.println("Enter edges as space-separated node pairs (u v) line by line:");
        for (int i = 0; i < numEdges; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
        }
        
        NumWaysAssign3558 solver = new NumWaysAssign3558();
        int result = solver.assignEdgeWeights(edges);
        System.out.println("Number of valid edge assignment ways: " + result);
        
        scanner.close();
    }
}