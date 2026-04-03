"""
Problem: 3661. Maximum Walls Destroyed by Robots

Same logic as Java.

-----------------------------------------------------

🧠 HUMAN THINKING:

Imagine robots standing on a line.

Each robot:
- Can shoot LEFT or RIGHT
- Bullet stops at next robot

So robots divide the line into segments.

Between every 2 robots:
👉 We decide direction of both robots
👉 That decides which walls are destroyed

-----------------------------------------------------

DP IDEA:

dp0 → current robot shoots LEFT
dp1 → current robot shoots RIGHT

We update based on previous robot decisions.

-----------------------------------------------------

KEY TRICK:
We count walls using binary search (fast range query).
"""

class WallBot:

    def maxWalls(self, robots, distance, walls):

        n = len(robots)

        # Combine and sort robots
        r = sorted([(robots[i], distance[i]) for i in range(n)])

        pos = [x[0] for x in r]
        D = [x[1] for x in r]

        walls.sort()

        # Count walls at robot positions
        wallsAtRobots = 0
        p = w = 0

        while p < n and w < len(walls):
            if pos[p] == walls[w]:
                wallsAtRobots += 1
                p += 1
                w += 1
            elif pos[p] < walls[w]:
                p += 1
            else:
                w += 1

        # First robot
        dp0 = self.countWalls(walls, pos[0] - D[0], pos[0] - 1)
        dp1 = 0

        for i in range(1, n):

            A = pos[i - 1]
            B = pos[i]

            R_prev = min(B, pos[i - 1] + D[i - 1])
            L_curr = max(A, pos[i] - D[i])

            costLL = self.countWalls(walls, max(A + 1, L_curr), B - 1)
            costLR = 0

            i1_start = A + 1
            i1_end = min(B - 1, R_prev)

            i2_start = max(A + 1, L_curr)
            i2_end = B - 1

            costRL = (
                self.countWalls(walls, i1_start, i1_end)
                + self.countWalls(walls, i2_start, i2_end)
                - self.countWalls(walls,
                    max(i1_start, i2_start),
                    min(i1_end, i2_end))
            )

            costRR = self.countWalls(walls, A + 1, min(B - 1, R_prev))

            next_dp0 = max(dp0 + costLL, dp1 + costRL)
            next_dp1 = max(dp0 + costLR, dp1 + costRR)

            dp0, dp1 = next_dp0, next_dp1

        R_last = pos[-1] + D[-1]
        cost_n_R = self.countWalls(walls, pos[-1] + 1, R_last)

        return max(dp0, dp1 + cost_n_R) + wallsAtRobots

    # Count walls in range
    def countWalls(self, walls, low, high):
        if low > high:
            return 0

        s = self.lowerBound(walls, low)
        e = self.upperBound(walls, high) - 1

        return max(0, e - s + 1)

    def lowerBound(self, arr, target):
        left, right = 0, len(arr)

        while left < right:
            mid = (left + right) // 2
            if arr[mid] >= target:
                right = mid
            else:
                left = mid + 1

        return left

    def upperBound(self, arr, target):
        left, right = 0, len(arr)

        while left < right:
            mid = (left + right) // 2
            if arr[mid] > target:
                right = mid
            else:
                left = mid + 1

        return left


# -------- USER INPUT DRIVER --------

if __name__ == "__main__":

    n = int(input())

    robots = list(map(int, input().split()))
    distance = list(map(int, input().split()))

    m = int(input())
    walls = list(map(int, input().split()))

    obj = WallBot()
    print(obj.maxWalls(robots, distance, walls))