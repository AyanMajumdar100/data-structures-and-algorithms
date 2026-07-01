/*
 * Problem Statement:
 * You are given a 0-indexed 2D matrix grid of size n x n, where (r, c) represents:
 * - A cell containing a thief if grid[r][c] = 1
 * - An empty cell if grid[r][c] = 0
 * You start at (0, 0) and want to reach (n - 1, n - 1). The safeness factor of a path is the 
 * minimum Manhattan distance from any cell in the path to any thief in the grid.
 * Return the maximum safeness factor of all paths leading to cell (n - 1, n - 1).
 */

/*
 * Approach: Multi-source BFS + Dijkstra's Algorithm (Max Priority Queue)
 * 1. Multi-source BFS: Find the minimum Manhattan distance from every cell to any thief.
 * We initialize a queue with all thief locations (grid[r][c] == 1) set to distance 0, 
 * and propagate outward level-by-level to calculate minimum distance maps for all nodes.
 * 2. Dijkstra-style Path Exploration: Run a Max-Priority Queue search from (0,0) to (n-1, n-1).
 * Instead of minimizing the path length, we prioritize paths that maintain the MAXIMUM possible 
 * bottleneck safeness factor (`Math.min(sf, dist[nr][nc])`). 
 * 3. Termination: The moment we pop the target cell (n - 1, n - 1) from the priority queue, 
 * we are guaranteed to have found the maximum possible safeness factor.
 */
import java.util.*;

public class SafestPathGrid2812 {
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();
        
        // If start or end points have a thief, safeness factor is instantly 0
        if (grid.get(0).get(0) == 1 || grid.get(n - 1).get(n - 1) == 1) {
            return 0;
        }

        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        Queue<int[]> queue = new LinkedList<>();
        // Multi-source BFS setup: initialize all thief coordinates as search seeds
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid.get(r).get(c) == 1) {
                    queue.offer(new int[]{r, c});
                    dist[r][c] = 0;
                }
            }
        }

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // Step 1: Compute minimum distance from each cell to its nearest thief
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        // Step 2: Use Max-Priority Queue to find the path maximizing the bottleneck safeness factor
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));
        boolean[][] visited = new boolean[n][n];

        // Format: {current bottleneck safeness factor, row, col}
        pq.offer(new int[]{dist[0][0], 0, 0});
        visited[0][0] = true;

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int sf = curr[0];
            int r = curr[1];
            int c = curr[2];

            // Reached destination, return the safe factor bottleneck directly
            if (r == n - 1 && c == n - 1) {
                return sf;
            }

            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    // The new safeness factor is bounded by the minimum distance along the entire path
                    pq.offer(new int[]{Math.min(sf, dist[nr][nc]), nr, nc});
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter grid size n:");
        int n = scanner.nextInt();
        
        List<List<Integer>> grid = new ArrayList<>();
        System.out.println("Enter grid rows (0 for empty, 1 for thief) line by line:");
        for (int i = 0; i < n; i++) {
            List<List<Integer>> rowList = new ArrayList<>();
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(scanner.nextInt());
            }
            grid.add(row);
        }
        
        SafestPathGrid2812 solver = new SafestPathGrid2812();
        int result = solver.maximumSafenessFactor(grid);
        System.out.println("Maximum safeness factor: " + result);
        
        scanner.close();
    }
}
