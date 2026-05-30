/*
 * Problem Statement:
 * There exists an infinite number line, with its origin at 0 extending towards the positive x-axis.
 * You are given a 2D array queries containing two types of queries:
 * 1. [1, x]: Build an obstacle at distance x from the origin.
 * 2. [2, x, sz]: Check if it is possible to place a block of size sz entirely in the range [0, x].
 * Return a boolean array results, where results[i] is true if the block can be placed for the ith query of type 2.
 */

/*
 * Approach: Segment Tree + TreeSet
 * 1. Use a TreeSet to maintain the active positions of obstacles.
 * 2. Use a Segment Tree to maintain the maximum gap between consecutive obstacles in any given range.
 * 3. When inserting an obstacle at `x`, find the `prev` and `next` obstacles using the TreeSet.
 * - Update the Segment Tree at `x` to store the gap `x - prev`.
 * - If `next` exists, update the Segment Tree at `next` to store the gap `next - x`.
 * 4. For a query `[2, x, sz]`, find the `prev` obstacle before or at `x`.
 * - The maximum possible gap in `[0, x]` is the maximum of the gap between `prev` and `x` (`x - prev`),
 * or the maximum gap completely to the left of `prev` (queried from the Segment Tree).
 * - If this max gap is >= `sz`, it's possible to place the block.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.Scanner;

public class BlkPlcQueries3161 {
    // Segment tree array to store maximum gaps
    int[] tree;
    
    // Point update in segment tree: update max gap at a specific index
    public void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            tree[node] = val;
            return;
        }
        int mid = start + (end - start) / 2;
        if (idx <= mid) {
            update(2 * node, start, mid, idx, val);
        } else {
            update(2 * node + 1, mid + 1, end, idx, val);
        }
        // Current node's max gap is the maximum of its left and right children
        tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
    }
    
    // Range max query in segment tree: find max gap in range [L, R]
    public int query(int node, int start, int end, int L, int R) {
        // Completely outside the range
        if (R < start || end < L) {
            return 0;
        }
        // Completely inside the range
        if (L <= start && end <= R) {
            return tree[node];
        }
        int mid = start + (end - start) / 2;
        int p1 = query(2 * node, start, mid, L, R);
        int p2 = query(2 * node + 1, mid + 1, end, L, R);
        return Math.max(p1, p2);
    }
    
    public List<Boolean> getResults(int[][] queries) {
        int maxX = 0;
        // Determine the maximum x coordinate to size the segment tree
        for (int[] q : queries) {
            if (q[1] > maxX) {
                maxX = q[1];
            }
        }
        
        // Initialize segment tree (size 4 * N is safe)
        tree = new int[4 * maxX + 4];
        
        // TreeSet to store obstacle locations, starting with origin 0
        TreeSet<Integer> obstacles = new TreeSet<>();
        obstacles.add(0);
        
        List<Boolean> res = new ArrayList<>();
        
        for (int[] q : queries) {
            if (q[0] == 1) { // Type 1: Add obstacle
                int x = q[1];
                Integer prev = obstacles.floor(x);
                Integer next = obstacles.higher(x);
                
                // Update gap for the newly added obstacle
                update(1, 0, maxX, x, x - prev);
                
                // If there is an obstacle after x, update its gap as it now relies on x
                if (next != null) {
                    update(1, 0, maxX, next, next - x);
                }
                obstacles.add(x);
            } else { // Type 2: Query placement
                int x = q[1];
                int sz = q[2];
                
                // Find the closest obstacle before or at x
                Integer prev = obstacles.floor(x);
                int maxGapToEnd = x - prev;
                
                // Check if the gap at the end is sufficient
                if (maxGapToEnd >= sz) {
                    res.add(true);
                } else {
                    // Otherwise, check if any gap before the previous obstacle is sufficient
                    int treeMax = query(1, 0, maxX, 0, prev);
                    res.add(treeMax >= sz);
                }
            }
        }
        
        return res;
    }

    public static void main(String[] args) {
        // User input simulation
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of queries:");
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
        
        int[][] queries = new int[n][];
        System.out.println("Enter queries (e.g., '1 2' or '2 3 3'):");
        for (int i = 0; i < n; i++) {
            int type = scanner.nextInt();
            if (type == 1) {
                queries[i] = new int[]{type, scanner.nextInt()};
            } else {
                queries[i] = new int[]{type, scanner.nextInt(), scanner.nextInt()};
            }
        }
        
        BlkPlcQueries3161 solution = new BlkPlcQueries3161();
        List<Boolean> results = solution.getResults(queries);
        System.out.println("Results: " + results);
        scanner.close();
    }
}
