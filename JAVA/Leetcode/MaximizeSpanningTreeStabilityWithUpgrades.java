/*
3600. Maximize Spanning Tree Stability with Upgrades

Problem Statement:
You are given n nodes numbered from 0 to n-1 and a list of edges.

Each edge is represented as:
[ui, vi, si, musti]

ui, vi  -> endpoints of the undirected edge
si      -> strength of the edge
musti   -> if 1, this edge MUST be included in the spanning tree
           if 0, this edge is optional and can be upgraded

You are also given an integer k representing the maximum number of upgrades allowed.

Upgrade Rule:
Each upgrade doubles the strength of an edge.
Only optional edges (musti == 0) can be upgraded.
Each optional edge can be upgraded at most once.

Definition:
The stability of a spanning tree = minimum edge strength among all edges in the tree.

Goal:
Return the maximum possible stability of a valid spanning tree.
If it is impossible to connect all nodes without forming cycles, return -1.

Examples:

Example 1:
Input:
n = 3
edges = [[0,1,2,1],[1,2,3,0]]
k = 1

Output:
2

Explanation:
Mandatory edge [0,1] must be included.
Optional edge [1,2] can be upgraded from 3 -> 6.
Minimum strength in tree = min(2,6) = 2.

Example 2:
Input:
n = 3
edges = [[0,1,4,0],[1,2,3,0],[0,2,1,0]]
k = 2

Output:
6

Explanation:
Upgrade edges [0,1] -> 8 and [1,2] -> 6.
Minimum edge in spanning tree = 6.

Example 3:
Input:
n = 3
edges = [[0,1,1,1],[1,2,1,1],[2,0,1,1]]
k = 0

Output:
-1

Explanation:
Mandatory edges form a cycle which is invalid for a spanning tree.

Constraints:
2 <= n <= 10^5
1 <= edges.length <= 10^5
1 <= si <= 10^5
0 <= k <= n
*/

import java.util.*;

public class MaximizeSpanningTreeStabilityWithUpgrades {

    // DSU (Disjoint Set Union) structure for efficiently managing connected components
    static class DSU {

        int[] parent;

        public DSU(int n) {
            parent = new int[n];

            // Initially each node is its own parent
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        // Path compression find
        public int find(int i) {
            if (parent[i] == i) return i;
            return parent[i] = find(parent[i]);
        }

        // Union two components
        public boolean union(int i, int j) {

            int rootI = find(i);
            int rootJ = find(j);

            // If already in same component, union fails (cycle)
            if (rootI == rootJ) return false;

            parent[rootI] = rootJ;
            return true;
        }
    }

    public static int maxStability(int n, int[][] edges, int k) {

        List<int[]> mandatory = new ArrayList<>();
        List<int[]> optional = new ArrayList<>();

        int minMandatory = Integer.MAX_VALUE;

        // Separate mandatory and optional edges
        for (int[] e : edges) {

            if (e[3] == 1) {
                mandatory.add(e);
                minMandatory = Math.min(minMandatory, e[2]);
            } else {
                optional.add(e);
            }
        }

        // Pre-check 1: Ensure mandatory edges don't form a cycle
        DSU preDsu = new DSU(n);
        int comps = n;

        for (int[] e : mandatory) {

            if (!preDsu.union(e[0], e[1])) {
                return -1; // cycle detected
            }

            comps--;
        }

        // Pre-check 2: Check if graph can even be connected
        for (int[] e : optional) {

            if (preDsu.union(e[0], e[1])) {
                comps--;
            }
        }

        if (comps > 1) {
            return -1; // graph disconnected
        }

        // Binary search range for stability
        int low = 1;
        int high = 200000; // max strength after upgrade

        if (minMandatory != Integer.MAX_VALUE) {
            high = Math.min(high, minMandatory);
        }

        int ans = -1;

        // Binary search for best possible stability
        while (low <= high) {

            int mid = low + (high - low) / 2;

            if (check(mid, n, mandatory, optional, k)) {
                ans = mid;
                low = mid + 1; // try larger stability
            } else {
                high = mid - 1;
            }
        }

        return ans;
    }

    // Check if a spanning tree with minimum edge >= M is possible
    private static boolean check(int M, int n, List<int[]> mandatory, List<int[]> optional, int k) {

        DSU dsu = new DSU(n);
        int comps = n;

        // Step 1: Add mandatory edges
        for (int[] e : mandatory) {

            if (e[2] < M) return false;

            dsu.union(e[0], e[1]);
            comps--;
        }

        // Step 2: Add optional edges without upgrade
        for (int[] e : optional) {

            if (e[2] >= M) {

                if (dsu.union(e[0], e[1])) {
                    comps--;
                }
            }
        }

        if (comps == 1) return true;

        // Step 3: Use upgrades if needed
        int upgradesUsed = 0;

        for (int[] e : optional) {

            if (e[2] < M && e[2] * 2 >= M) {

                if (upgradesUsed < k) {

                    if (dsu.union(e[0], e[1])) {

                        comps--;
                        upgradesUsed++;

                        if (comps == 1) return true;
                    }
                }
            }
        }

        return comps == 1;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int m = sc.nextInt();

        int[][] edges = new int[m][4];

        System.out.println("Enter edges as: u v strength must(0/1)");

        for (int i = 0; i < m; i++) {

            edges[i][0] = sc.nextInt();
            edges[i][1] = sc.nextInt();
            edges[i][2] = sc.nextInt();
            edges[i][3] = sc.nextInt();
        }

        System.out.print("Enter maximum upgrades k: ");
        int k = sc.nextInt();

        int result = maxStability(n, edges, k);

        System.out.println("Maximum Stability of Spanning Tree: " + result);

        sc.close();
    }
}