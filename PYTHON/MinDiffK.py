"""
Problem: 3567. Minimum Absolute Difference in Sliding Submatrix

--------------------------------------------------

DETAILED LOGIC (Human-friendly understanding):

We need to evaluate EVERY k x k block in the grid.

For each block:
1. Collect all elements
2. Find minimum difference between any two DISTINCT values

--------------------------------------------------

Core Insight:

Instead of comparing every pair (which is slow),
we SORT the values first.

Why sorting helps?
- After sorting, the closest numbers are next to each other
- So we only compare adjacent elements

Example:
[5, 1, 9, 3] → sorted → [1, 3, 5, 9]
Now check:
(3-1), (5-3), (9-5)

--------------------------------------------------

Edge Case:
If all values are same:
→ no distinct pair exists
→ answer = 0

--------------------------------------------------

Time Complexity:
O((m-k+1)*(n-k+1) * k^2 log(k^2))

Works because constraints are small (<= 30)
"""

def min_abs_diff(grid, k):
    m = len(grid)
    n = len(grid[0])

    ans = [[0] * (n - k + 1) for _ in range(m - k + 1)]

    # Slide over all possible k x k submatrices
    for i in range(m - k + 1):
        for j in range(n - k + 1):

            vals = []

            # Step 1: Extract submatrix elements
            for r in range(i, i + k):
                for c in range(j, j + k):
                    vals.append(grid[r][c])

            # Step 2: Sort values
            vals.sort()

            minDiff = float('inf')
            allSame = True

            # Step 3: Check adjacent differences
            for x in range(1, len(vals)):

                if vals[x] != vals[x - 1]:
                    allSame = False

                    # Since sorted → smallest differences appear between neighbors
                    minDiff = min(minDiff, vals[x] - vals[x - 1])

            # Step 4: Assign result
            if allSame:
                ans[i][j] = 0
            else:
                ans[i][j] = minDiff

    return ans


# ----------- User Input Handling -----------

# Input matrix size
m, n = map(int, input().split())

# Input grid
grid = []
for _ in range(m):
    grid.append(list(map(int, input().split())))

# Input k
k = int(input())

# Compute and print result
result = min_abs_diff(grid, k)

for row in result:
    print(*row)