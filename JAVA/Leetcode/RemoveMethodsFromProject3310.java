/*
 * Problem Statement:
 * Given n methods (0 to n - 1), a target method k, and directed edges invocations [u, v] (u calls v).
 * Method k and all methods reachable from k (directly or indirectly) are suspicious.
 * Suspicious methods can ONLY be removed if NO non-suspicious method invokes any suspicious method.
 * Return the list of remaining methods. If suspicious methods cannot be removed, return all n methods.
 */

/*
 * Approach: Graph Reachability DFS + Cross-Boundary Edge Validation (O(V + E) Time, O(V + E) Space)
 * 1. Build Adjacency List: Construct a directed graph representing method invocation edges.
 * 2. Identify Suspicious Set (DFS/BFS):
 *    Start a traversal from method `k` to discover all reachable methods. Store these in a boolean array or set `suspicious`.
 * 3. Validate Removal Condition:
 *    Iterate over all invocation edges `[u, v]`. If `u` is NOT suspicious but `v` IS suspicious, then an external dependency 
 *    invokes a suspicious method. Thus, removal is invalid (`canRemove = false`).
 * 4. Assemble Output:
 *    - If `canRemove` is true, return only non-suspicious methods.
 *    - If `canRemove` is false, return all methods from `0` to `n - 1`.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RemoveMethodsFromProject3310 {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<List<Integer>> adjacencyGraph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjacencyGraph.add(new ArrayList<>());
        }
        for (int[] inv : invocations) {
            adjacencyGraph.get(inv[0]).add(inv[1]);
        }

        // Step 1: Identify all suspicious methods starting from target method k
        boolean[] isSuspicious = new boolean[n];
        traverseSuspiciousMethods(k, adjacencyGraph, isSuspicious);

        // Step 2: Check if any non-suspicious method invokes a suspicious method
        boolean canRemoveSuspicious = true;
        for (int[] inv : invocations) {
            int callerMethod = inv[0];
            int calleeMethod = inv[1];
            if (!isSuspicious[callerMethod] && isSuspicious[calleeMethod]) {
                canRemoveSuspicious = false;
                break;
            }
        }

        // Step 3: Return remaining methods based on validation result
        List<Integer> remainingMethodsList = new ArrayList<>();
        if (!canRemoveSuspicious) {
            for (int i = 0; i < n; i++) {
                remainingMethodsList.add(i);
            }
        } else {
            for (int i = 0; i < n; i++) {
                if (!isSuspicious[i]) {
                    remainingMethodsList.add(i);
                }
            }
        }

        return remainingMethodsList;
    }

    private void traverseSuspiciousMethods(int currentMethod, List<List<Integer>> graph, boolean[] isSuspicious) {
        isSuspicious[currentMethod] = true;
        for (int nextMethod : graph.get(currentMethod)) {
            if (!isSuspicious[nextMethod]) {
                traverseSuspiciousMethods(nextMethod, graph, isSuspicious);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter total number of methods (n):");
        int nParam = scanner.nextInt();
        
        System.out.println("Enter target bug method (k):");
        int kParam = scanner.nextInt();
        
        System.out.println("Enter number of invocations:");
        int invocationCount = scanner.nextInt();
        int[][] invocationsParam = new int[invocationCount][2];
        
        System.out.println("Enter invocations (u v) line by line:");
        for (int i = 0; i < invocationCount; i++) {
            invocationsParam[i][0] = scanner.nextInt();
            invocationsParam[i][1] = scanner.nextInt();
        }

        RemoveMethodsFromProject3310 solverInstance = new RemoveMethodsFromProject3310();
        List<Integer> finalRemainingMethods = solverInstance.remainingMethods(nParam, kParam, invocationsParam);

        System.out.println("Remaining methods: " + finalRemainingMethods);
        scanner.close();
    }
}
