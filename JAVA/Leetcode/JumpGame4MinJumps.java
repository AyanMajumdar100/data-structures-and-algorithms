/*
 * Problem Statement:
 * Given an array of integers arr, you are initially positioned at the first index of the array.
 * In one step you can jump from index i to index:
 *   - i + 1 where: i + 1 < arr.length.
 *   - i - 1 where: i - 1 >= 0.
 *   - j where: arr[i] == arr[j] and i != j.
 * Return the minimum number of steps to reach the last index of the array.
 * Notice that you can not jump outside of the array at any time.
 */

/*
 * Approach: Breadth-First Search (BFS)
 * We treat the array as a graph where edges exist between adjacent elements and elements with the same value.
 * We use BFS to find the shortest path (minimum jumps) from index 0 to the last index.
 * A HashMap stores the indices of all elements with the same value to allow O(1) lookups for value-based jumps.
 * To optimize and avoid Time Limit Exceeded (TLE), we remove the value from the map once its indices are processed.
 * We use a custom array-based queue for maximum performance and a boolean array to track visited indices.
 */
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class JumpGame4MinJumps {

    public int minJumps(int[] arr) {
        int n = arr.length;
        // Base case: if array has 1 or fewer elements, no jumps are needed
        if (n <= 1) return 0;
        
        // Map to store lists of indices that share the same value
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }
        
        // Custom array-based queue for BFS performance
        int[] q = new int[n];
        int head = 0, tail = 0;
        
        // Boolean array to track visited indices and prevent cycles
        boolean[] vis = new boolean[n];
        
        // Start BFS from the first index (index 0)
        q[tail++] = 0;
        vis[0] = true;
        
        int steps = 0;
        
        // Standard BFS traversal level by level
        while (head < tail) {
            int size = tail - head;
            
            // Process all nodes at the current BFS level
            for (int k = 0; k < size; k++) {
                int curr = q[head++];
                
                // 1. Explore jump to the right (i + 1)
                if (curr + 1 < n && !vis[curr + 1]) {
                    if (curr + 1 == n - 1) return steps + 1;
                    vis[curr + 1] = true;
                    q[tail++] = curr + 1;
                }
                
                // 2. Explore jump to the left (i - 1)
                if (curr - 1 >= 0 && !vis[curr - 1]) {
                    vis[curr - 1] = true;
                    q[tail++] = curr - 1;
                }
                
                // 3. Explore jumps to indices with the same value (arr[i] == arr[j])
                List<Integer> list = map.get(arr[curr]);
                if (list != null) {
                    // Iterate backwards for slight optimization in queueing later elements first
                    for (int i = list.size() - 1; i >= 0; i--) {
                        int next = list.get(i);
                        if (!vis[next]) {
                            if (next == n - 1) return steps + 1;
                            vis[next] = true;
                            q[tail++] = next;
                        }
                    }
                    // CRITICAL: Remove the list from the map to prevent redundant processing later
                    map.remove(arr[curr]);
                }
            }
            // Increment jump count after fully processing the current level
            steps++;
        }
        
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        
        String inputLine = scanner.nextLine().trim();
        if (inputLine.isEmpty()) {
            System.out.println("Minimum jumps: 0");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }
        
        JumpGame4MinJumps1345 solver = new JumpGame4MinJumps1345();
        int result = solver.minJumps(arr);
        System.out.println("Minimum jumps: " + result);
        
        scanner.close();
    }
}
