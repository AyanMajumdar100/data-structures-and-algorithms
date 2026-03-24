"""
Problem: 1594. Maximum Non Negative Product in a Matrix

--------------------------------------------------

CORE IDEA:

We cannot track only maximum product.

Because:
negative × negative = positive

So:
minimum value today can become maximum tomorrow.

--------------------------------------------------

We track:
- maxDp[i][j] → maximum product till cell
- minDp[i][j] → minimum product till cell

--------------------------------------------------

TRANSITION:

From top and left:

p1 = max(top) * current
p2 = min(top) * current
p3 = max(left) * current
p4 = min(left) * current

maxDp = max(p1, p2, p3, p4)
minDp = min(p1, p2, p3, p4)

--------------------------------------------------

FINAL:
If result < 0 → return -1
Else → return result % (1e9 + 7)
"""

def max_product_path(grid):
    m, n = len(grid), len(grid[0])

    maxDp = [[0]*n for _ in range(m)]
    minDp = [[0]*n for _ in range(m)]

    # Base case
    maxDp[0][0] = grid[0][0]
    minDp[0][0] = grid[0][0]

    # First column
    for i in range(1, m):
        maxDp[i][0] = maxDp[i-1][0] * grid[i][0]
        minDp[i][0] = minDp[i-1][0] * grid[i][0]

    # First row
    for j in range(1, n):
        maxDp[0][j] = maxDp[0][j-1] * grid[0][j]
        minDp[0][j] = minDp[0][j-1] * grid[0][j]

    # Fill DP
    for i in range(1, m):
        for j in range(1, n):

            p1 = maxDp[i-1][j] * grid[i][j]
            p2 = minDp[i-1][j] * grid[i][j]
            p3 = maxDp[i][j-1] * grid[i][j]
            p4 = minDp[i][j-1] * grid[i][j]

            maxDp[i][j] = max(p1, p2, p3, p4)
            minDp[i][j] = min(p1, p2, p3, p4)

    res = maxDp[m-1][n-1]

    if res < 0:
        return -1

    return res % (10**9 + 7)


# ----------- User Input Handling -----------

m, n = map(int, input().split())

grid = [list(map(int, input().split())) for _ in range(m)]

print(max_product_path(grid))