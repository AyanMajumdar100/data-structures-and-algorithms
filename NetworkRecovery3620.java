/*
 * Problem Statement:
 * You are given a directed acyclic graph (DAG) of n nodes numbered 0 to n-1.
 * edges[i] = [u, v, cost] indicates a one-way edge with a recovery cost.
 * online[i] indicates whether node i is online. Nodes 0 and n-1 are always online.
 * A path from 0 to n-1 is valid if all intermediate nodes are online and its total cost <= k.
 * The score of a path is the minimum edge-cost along it.
 * Return the maximum path score among all valid paths, or -1 if none exist.
 */

/*
 * Approach: Binary Search on Answer + DP over Topological Sort
 * 1. Filter out edges connected to offline nodes.
 * 2. Generate a Topological Sort of the online nodes using Kahn's algorithm (since it's a DAG).
 * 3. The target answer is one of the edge costs. Sort all unique valid edge costs to binary search.
 * 4. For a candidate 'minCost', filter out any edges whose cost is strictly less than 'minCost'.
 * 5. Run a Shortest Path DP using the topological order to find the minimum path cost from 0 to n-1 
 * using only the allowed edges.
 * 6. If the minimum path cost <= k, then 'minCost' is feasible, and we can look for a larger value.
 */
import java.util.Arrays;
import java.util.Scanner;

public class NetworkRecovery3620 {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        int m = edges.length;
        
        // Fast I/O / Graph representation using adjacency arrays (Forward Star representation)
        int[] head = new int[n];
        Arrays.fill(head, -1);
        int[] to = new int[m];
        int[] next = new int[m];
        int[] weight = new int[m];
        int edgeCount = 0;
        
        int[] inDegree = new int[n];
        for (int i = 0; i < m; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int w = edges[i][2];
            // Only add edges where both endpoints are online
            if (online[u] && online[v]) {
                to[edgeCount] = v;
                weight[edgeCount] = w;
                next[edgeCount] = head[u];
                head[u] = edgeCount++;
                inDegree[v]++;
            }
        }
        
        // Step 1: Compute Topological Sort on the online sub-graph
        int[] topo = new int[n];
        int topoCount = 0;
        int[] queue = new int[n];
        int headQ = 0, tailQ = 0;
        
        for (int i = 0; i < n; i++) {
            if (online[i] && inDegree[i] == 0) {
                queue[tailQ++] = i;
            }
        }
        
        while (headQ < tailQ) {
            int u = queue[headQ++];
            topo[topoCount++] = u;
            for (int e = head[u]; e != -1; e = next[e]) {
                int v = to[e];
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue[tailQ++] = v;
                }
            }
        }
        
        // Step 2: Extract and sort all unique available edge costs
        int[] validCosts = new int[edgeCount];
        for (int i = 0; i < edgeCount; i++) {
            validCosts[i] = weight[i];
        }
        Arrays.sort(validCosts);
        int uniqueCount = 0;
        for (int i = 0; i < edgeCount; i++) {
            if (i == 0 || validCosts[i] != validCosts[i - 1]) {
                validCosts[uniqueCount++] = validCosts[i];
            }
        }
        
        // Step 3: Binary Search on the maximum possible bottleneck edge score
        int low = 0;
        int high = uniqueCount - 1;
        int ans = -1;
        long[] dist = new long[n];
        
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int minCost = validCosts[mid];
            
            if (check(minCost, topo, topoCount, head, next, to, weight, dist, k, n)) {
                ans = minCost;      // Found a valid path, try to find a larger bottleneck score
                low = mid + 1;
            } else {
                high = mid - 1;     // Path invalid or cost too high, reduce bottleneck score requirement
            }
        }
        
        return ans;
    }
    
    // Check feasibility using Shortest Path DAG DP based on topological order
    private boolean check(int minCost, int[] topo, int topoCount, int[] head, int[] next, int[] to, int[] weight, long[] dist, long k, int n) {
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        
        for (int i = 0; i < topoCount; i++) {
            int u = topo[i];
            if (dist[u] == Long.MAX_VALUE) {
                continue;
            }
            
            // Relax outgoing edges from u that satisfy our candidate bottleneck floor criteria
            for (int e = head[u]; e != -1; e = next[e]) {
                if (weight[e] >= minCost) {
                    int v = to[e];
                    if (dist[u] + weight[e] < dist[v]) {
                        dist[v] = dist[u] + weight[e];
                    }
                }
            }
        }
        
        return dist[n - 1] <= k;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of nodes n:");
        int n = scanner.nextInt();
        
        System.out.println("Enter number of edges m:");
        int m = scanner.nextInt();
        
        int[][] edges = new int[m][3];
        System.out.println("Enter edges (u v cost) line by line:");
        for (int i = 0; i < m; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
            edges[i][2] = scanner.nextInt();
        }
        
        boolean[] online = new boolean[n];
        System.out.println("Enter online status (true/false) for all nodes 0 to n-1:");
        for (int i = 0; i < n; i++) {
            online[i] = scanner.nextBoolean();
        }
        
        System.out.println("Enter max recovery cost allowance limit k:");
        long k = scanner.nextLong();
        
        NetworkRecovery3620 solver = new NetworkRecovery3620();
        int result = solver.findMaxPathScore(edges, online, k);
        System.out.println("Maximum path score: " + result);
        
        scanner.close();
    }
}
