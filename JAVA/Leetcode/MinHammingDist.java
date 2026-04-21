/*
Problem Statement:
You are given two integer arrays, source and target, both of length n. 
You are also given an array allowedSwaps where each allowedSwaps[i] = [ai, bi] indicates 
that you are allowed to swap the elements at index ai and index bi (0-indexed) of array source. 
You can swap elements at a specific pair of indices multiple times and in any order.

The Hamming distance of two arrays of the same length is the number of positions where the elements differ.
Return the minimum Hamming distance of source and target after performing any amount of swap operations.

Constraints:
n == source.length == target.length
1 <= n <= 10^5
1 <= source[i], target[i] <= 10^5
0 <= allowedSwaps.length <= 10^5
allowedSwaps[i].length == 2
*/

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MinHammingDist {

    // Helper class for Disjoint Set Union (Union-Find)
    static class DSU {
        int[] parent;
        
        public DSU(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i; // Initially, every node is its own parent
            }
        }
        
        // Find with path compression
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Flatten the tree for O(1) future lookups
            }
            return parent[x];
        }
        
        // Union by connecting roots
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                parent[rootX] = rootY;
            }
        }
    }

    public static int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;
        DSU dsu = new DSU(n);
        
        // Group indices that can be swapped into connected components
        for (int[] swap : allowedSwaps) {
            dsu.union(swap[0], swap[1]);
        }

        // Map each component root to a frequency map of elements present in 'source'
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = dsu.find(i);
            map.putIfAbsent(root, new HashMap<>());
            Map<Integer, Integer> store = map.get(root);
            store.put(source[i], store.getOrDefault(source[i], 0) + 1);
        }

        int distance = 0;
        
        // Check if target elements can be formed from the available elements in their components
        for (int i = 0; i < n; i++) {
            int root = dsu.find(i);
            Map<Integer, Integer> store = map.get(root);
            
            // If the element we need for the target is available in this component's pool
            if (store.getOrDefault(target[i], 0) > 0) {
                // Consume one occurrence of it
                store.put(target[i], store.get(target[i]) - 1);
            } else {
                // If it's not available, it contributes to the Hamming distance
                distance++;
            }
        }

        return distance;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter 'source' elements separated by spaces:");
            String[] srcInput = scanner.nextLine().trim().split("\\s+");
            int n = srcInput.length;
            int[] source = new int[n];
            for (int i = 0; i < n; i++) source[i] = Integer.parseInt(srcInput[i]);
            
            System.out.println("Enter 'target' elements separated by spaces:");
            String[] tgtInput = scanner.nextLine().trim().split("\\s+");
            int[] target = new int[n];
            for (int i = 0; i < n; i++) target[i] = Integer.parseInt(tgtInput[i]);
            
            System.out.println("Enter number of allowed swaps:");
            int numSwaps = Integer.parseInt(scanner.nextLine().trim());
            int[][] allowedSwaps = new int[numSwaps][2];
            
            if (numSwaps > 0) {
                System.out.println("Enter each swap pair (index1 index2) on a new line:");
                for (int i = 0; i < numSwaps; i++) {
                    String[] pair = scanner.nextLine().trim().split("\\s+");
                    allowedSwaps[i][0] = Integer.parseInt(pair[0]);
                    allowedSwaps[i][1] = Integer.parseInt(pair[1]);
                }
            }
            
            int result = minimumHammingDistance(source, target, allowedSwaps);
            System.out.println("Minimum Hamming Distance: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected.");
        } finally {
            scanner.close();
        }
    }
}