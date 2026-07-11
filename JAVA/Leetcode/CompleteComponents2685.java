/*
 * Problem Statement:
 * Given an undirected graph with n vertices, return the number of complete connected components.
 * A component is complete if there exists an edge between every pair of its vertices.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompleteComponents2685 {
    public int countCompleteComponents(int n, int[][] edges) {
        // Step 1: Initialize adjacency list and degree array tracker
        List<Integer>[] adj = new ArrayList[n];
        int[] degree = new int[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adj[u].add(v);
            adj[v].add(u);
            degree[u]++;
            degree[v]++;
        }
        
        boolean[] visited = new boolean[n];
        int completeComponentsCount = 0;
        
        // Step 2: Traverse every unvisited component block
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfs(i, adj, visited, component);
                
                // Step 3: Validate if the component meets completeness requirements
                boolean isComplete = true;
                int targetDegree = component.size() - 1;
                for (int node : component) {
                    if (degree[node] != targetDegree) {
                        isComplete = false;
                        break;
                    }
                }
                
                if (isComplete) {
                    completeComponentsCount++;
                }
            }
        }
        
        return completeComponentsCount;
    }
    
    private void dfs(int node, List<Integer>[] adj, boolean[] visited, List<Integer> component) {
        visited[node] = true;
        component.add(node);
        for (int neighbor : adj[node]) {
            if (!visited[neighbor]) {
                dfs(neighbor, adj, visited, component);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of vertices n:");
        int n = scanner.nextInt();
        
        System.out.println("Enter number of edges m:");
        int m = scanner.nextInt();
        
        int[][] edges = new int[m][2];
        System.out.println("Enter edges (u v) line by line:");
        for (int i = 0; i < m; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
        }
        
        CompleteComponents2685 solver = new CompleteComponents2685();
        int result = solver.countCompleteComponents(n, edges);
        System.out.println("Number of complete components: " + result);
        
        scanner.close();
    }
}
