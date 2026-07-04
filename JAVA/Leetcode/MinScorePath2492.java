/*
 * Problem Statement:
 * You are given a positive integer n representing n cities numbered from 1 to n. 
 * You are also given a 2D array roads where roads[i] = [ai, bi, distancei] indicates 
 * a bidirectional road between cities ai and bi.
 * The score of a path between two cities is the minimum distance of a road along that path.
 * Return the minimum possible score of a path between cities 1 and n.
 * * Note: You can visit roads and cities multiple times.
 */

/*
 * Approach: Graph Traversal (BFS / Connected Components)
 * Key Insight: 
 * Because we can traverse any road multiple times, we don't need to find the shortest path 
 * from 1 to n in terms of hop count or total distance. Instead, any road belonging to the 
 * *same connected component* as city 1 and city n can be safely added to our path 
 * (e.g., traveling 1 -> 2 -> 1 -> 3 -> 4 allows us to include the weight of edge (1,2) even 
 * if it doesn't sit strictly between 1 and n).
 * * 1. Find all edges reachable from city 1 using Breadth-First Search (BFS).
 * 2. As we traverse the connected component, we look at every single edge connected 
 * to our visited nodes and keep track of the absolute minimum weight (`minScore`).
 * 3. Return that minimum weight.
 */
import java.util.Arrays;
import java.util.Scanner;

public class MinScorePath2492 {
    public int minScore(int n, int[][] roads) {
        int m = roads.length;
        
        // Fast graph representation using a Forward Star array format
        int[] head = new int[n + 1];
        Arrays.fill(head, -1);
        int[] to = new int[2 * m];
        int[] next = new int[2 * m];
        int[] weight = new int[2 * m];
        int edgeCount = 0;
        
        // Populate adjacency structures for bidirectional roads
        for (int i = 0; i < m; i++) {
            int u = roads[i][0];
            int v = roads[i][1];
            int w = roads[i][2];
            
            // Forward Edge: u -> v
            to[edgeCount] = v;
            weight[edgeCount] = w;
            next[edgeCount] = head[u];
            head[u] = edgeCount++;
            
            // Backward Edge: v -> u
            to[edgeCount] = u;
            weight[edgeCount] = w;
            next[edgeCount] = head[v];
            head[v] = edgeCount++;
        }
        
        int minScore = Integer.MAX_VALUE;
        boolean[] visited = new boolean[n + 1];
        int[] queue = new int[n + 1];
        int front = 0, rear = 0;
        
        // Start BFS from City 1
        queue[rear++] = 1;
        visited[1] = true;
        
        while (front < rear) {
            int u = queue[front++];
            
            // Inspect all roads branching from the current city node
            for (int e = head[u]; e != -1; e = next[e]) {
                int v = to[e];
                int w = weight[e];
                
                // Track the smallest edge weight seen anywhere inside this component
                if (w < minScore) {
                    minScore = w;
                }
                
                // If the neighboring city hasn't been explored yet, queue it up
                if (!visited[v]) {
                    visited[v] = true;
                    queue[rear++] = v;
                }
            }
        }
        
        return minScore;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of cities n:");
        int n = scanner.nextInt();
        
        System.out.println("Enter number of roads m:");
        int m = scanner.nextInt();
        
        int[][] roads = new int[m][3];
        System.out.println("Enter roads (u v weight) line by line:");
        for (int i = 0; i < m; i++) {
            roads[i][0] = scanner.nextInt();
            roads[i][1] = scanner.nextInt();
            roads[i][2] = scanner.nextInt();
        }
        
        MinScorePath2492 solver = new MinScorePath2492();
        int result = solver.minScore(n, roads);
        System.out.println("Minimum possible path score: " + result);
        
        scanner.close();
    }
}
