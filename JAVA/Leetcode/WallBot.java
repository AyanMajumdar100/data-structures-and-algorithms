/*
Problem: 3661. Maximum Walls Destroyed by Robots

We are given:
- robots[i] → position of robot
- distance[i] → how far it can shoot
- walls[j] → positions of walls

Each robot can shoot LEFT or RIGHT (only once).
Bullet stops if it hits another robot.

Goal:
Maximize number of UNIQUE walls destroyed.

-----------------------------------------------------

🔥 CORE INTUITION (VERY IMPORTANT):

1. Robots act like "blockers"
   → A robot cannot shoot past another robot.

2. So each robot's shooting range is LIMITED by its neighbors.

3. We sort robots → process from left to right.

4. Between every pair of robots, we decide:
   - Previous robot shot LEFT or RIGHT
   - Current robot shot LEFT or RIGHT

   → This creates a DP decision problem.

-----------------------------------------------------

🔥 DP IDEA:

dp0 → max walls destroyed till i-th robot if current robot shoots LEFT
dp1 → max walls destroyed till i-th robot if current robot shoots RIGHT

We update dp0 & dp1 based on previous robot choices.

-----------------------------------------------------

🔥 RANGE COUNTING:

We use binary search:
countWalls(low, high) → how many walls lie in this interval

-----------------------------------------------------

🔥 SPECIAL CASE:

Walls exactly at robot positions → always destroyed
Handled separately using two-pointer technique.

-----------------------------------------------------

TIME: O(n log n)
SPACE: O(1) (excluding input)
*/

import java.util.*;

class WallBot {  // short unique name

    public int maxWalls(int[] robots, int[] distance, int[] walls) {

        int n = robots.length;

        // Combine robot position + distance for sorting
        long[] r = new long[n];
        for (int i = 0; i < n; i++) {
            r[i] = ((long) robots[i] << 32) | (distance[i] & 0xFFFFFFFFL);
        }

        Arrays.sort(r);

        // Extract sorted positions and distances
        int[] pos = new int[n];
        int[] D = new int[n];

        for (int i = 0; i < n; i++) {
            pos[i] = (int) (r[i] >> 32);
            D[i] = (int) r[i];
        }

        // Sort walls
        Arrays.sort(walls);

        // Count walls exactly at robot positions
        int wallsAtRobots = 0;
        int p = 0, w = 0;

        while (p < n && w < walls.length) {
            if (pos[p] == walls[w]) {
                wallsAtRobots++;
                p++; w++;
            } else if (pos[p] < walls[w]) {
                p++;
            } else {
                w++;
            }
        }

        // First robot
        int dp0 = countWalls(walls, pos[0] - D[0], pos[0] - 1); // shoot LEFT
        int dp1 = 0; // shoot RIGHT initially gives nothing between robots

        // Process robots
        for (int i = 1; i < n; i++) {

            int A = pos[i - 1]; // previous robot
            int B = pos[i];     // current robot

            // Right limit of previous robot
            int R_prev = Math.min(B, pos[i - 1] + D[i - 1]);

            // Left limit of current robot
            int L_curr = Math.max(A, pos[i] - D[i]);

            // Case LL (prev LEFT, curr LEFT)
            int costLL = countWalls(walls, Math.max(A + 1, L_curr), B - 1);

            // Case LR (prev LEFT, curr RIGHT)
            int costLR = 0;

            // Case RL (prev RIGHT, curr LEFT)
            int i1_start = A + 1;
            int i1_end = Math.min(B - 1, R_prev);

            int i2_start = Math.max(A + 1, L_curr);
            int i2_end = B - 1;

            int costRL = countWalls(walls, i1_start, i1_end)
                    + countWalls(walls, i2_start, i2_end)
                    - countWalls(walls,
                        Math.max(i1_start, i2_start),
                        Math.min(i1_end, i2_end)); // remove overlap

            // Case RR (prev RIGHT, curr RIGHT)
            int costRR = countWalls(walls, A + 1, Math.min(B - 1, R_prev));

            // DP transitions
            int next_dp0 = Math.max(dp0 + costLL, dp1 + costRL);
            int next_dp1 = Math.max(dp0 + costLR, dp1 + costRR);

            dp0 = next_dp0;
            dp1 = next_dp1;
        }

        // Last robot shooting RIGHT
        int R_last = pos[n - 1] + D[n - 1];
        int cost_n_R = countWalls(walls, pos[n - 1] + 1, R_last);

        return Math.max(dp0, dp1 + cost_n_R) + wallsAtRobots;
    }

    // Count walls in range [low, high]
    private int countWalls(int[] walls, int low, int high) {
        if (low > high) return 0;

        int s = lowerBound(walls, low);
        int e = upperBound(walls, high) - 1;

        return Math.max(0, e - s + 1);
    }

    // First index >= target
    private int lowerBound(int[] arr, int target) {
        int left = 0, right = arr.length;

        while (left < right) {
            int mid = (left + right) >>> 1;

            if (arr[mid] >= target) right = mid;
            else left = mid + 1;
        }
        return left;
    }

    // First index > target
    private int upperBound(int[] arr, int target) {
        int left = 0, right = arr.length;

        while (left < right) {
            int mid = (left + right) >>> 1;

            if (arr[mid] > target) right = mid;
            else left = mid + 1;
        }
        return left;
    }

    // -------- USER INPUT DRIVER --------
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] robots = new int[n];
        int[] distance = new int[n];

        for (int i = 0; i < n; i++) robots[i] = sc.nextInt();
        for (int i = 0; i < n; i++) distance[i] = sc.nextInt();

        int m = sc.nextInt();
        int[] walls = new int[m];

        for (int i = 0; i < m; i++) walls[i] = sc.nextInt();

        WallBot obj = new WallBot();
        System.out.println(obj.maxWalls(robots, distance, walls));

        sc.close();
    }
}