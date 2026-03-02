"""
Problem: Minimum Swaps to Arrange a Binary Grid

You are given an n x n binary grid.
In one step, you may swap two adjacent rows.

A grid is valid if every cell above the main diagonal is 0.
Return the minimum number of swaps required, or -1 if impossible.

Example:
Input: [[0,0,1],[1,1,0],[1,0,0]]
Output: 3

Input: [[0,1,1,0],[0,1,1,0],[0,1,1,0],[0,1,1,0]]
Output: -1

Input: [[1,0,0],[1,1,0],[1,1,1]]
Output: 0
"""

import sys


class BinaryGridMinimumSwaps:
    """
    Beginner-friendly idea:

    Row i must have at least (n - 1 - i) trailing zeros.

    We:
    1. Count trailing zeros per row.
    2. For each position, find nearest valid row.
    3. Move it upward using adjacent swaps.
    """

    @staticmethod
    def min_swaps(grid):
        n = len(grid)
        zeros = [0] * n

        # Count trailing zeros for each row
        for i in range(n):
            count = 0
            for j in range(n - 1, -1, -1):
                if grid[i][j] == 0:
                    count += 1
                else:
                    break
            zeros[i] = count

        swaps = 0

        # Arrange rows greedily
        for i in range(n):
            required_zeros = n - 1 - i
            j = i

            while j < n and zeros[j] < required_zeros:
                j += 1

            if j == n:
                return -1

            while j > i:
                zeros[j], zeros[j - 1] = zeros[j - 1], zeros[j]
                swaps += 1
                j -= 1

        return swaps


# User input
data = sys.stdin.read().strip().split()
n = int(data[0])

grid = []
idx = 1
for _ in range(n):
    row = list(map(int, data[idx:idx+n]))
    grid.append(row)
    idx += n

result = BinaryGridMinimumSwaps.min_swaps(grid)
print(result)