"""
Problem: 1594. Maximum Non Negative Product in a Matrix

--------------------------------------------------

CORE IDEA:

We cannot track only max product.

Why?
Because negative numbers flip signs.

Example:
min (-10) * (-2) = +20 (becomes max!)

So we track:
- maxDp[i][j]
- minDp[i][j]

--------------------------------------------------

Transition:

From top and left:
Take all possibilities:
    max * current
    min * current

Pick:
    max → for maxDp
    min → for minDp

--------------------------------------------------

Final:
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
        minDp[i][0] = maxDp[i][0]

    # First row
    for j in range(1, n):
        maxDp[0][j] = maxDp[0][j-1] * grid[0][j]
        minDp[0][j] = maxDp[0][j]

    # Fill DP
    for i in range(1, m):
        for j in range(1, n):

            a = maxDp[i-1][j] * grid[i][j]
            b = minDp[i-1][j] * grid[i][j]
            c = maxDp[i][j-1] * grid[i][j]
            d = minDp[i][j-1] * grid[i][j]

            maxDp[i][j] = max(a, b, c, d)
            minDp[i][j] = min(a, b, c, d)

    res = maxDp[m-1][n-1]

    if res < 0:
        return -1

    return res % (10**9 + 7)


# ----------- User Input Handling -----------

m, n = map(int, input().split())

grid = [list(map(int, input().split())) for _ in range(m)]

print(max_product_path(grid))