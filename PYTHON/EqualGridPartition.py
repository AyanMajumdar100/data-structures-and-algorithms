"""
Problem: Equal Sum Grid Partition I

--------------------------------------------------

CORE IDEA:

We need to split grid into TWO parts such that:
sum(part1) == sum(part2)

So:
totalSum must be EVEN

--------------------------------------------------

STEP 1: Compute:
- row sums
- column sums
- total sum

--------------------------------------------------

STEP 2:
target = totalSum // 2

--------------------------------------------------

STEP 3: Horizontal cuts

Keep adding row sums:
if prefix == target → valid split

--------------------------------------------------

STEP 4: Vertical cuts

Same logic using column sums

--------------------------------------------------

If no valid split → return False

--------------------------------------------------

Time: O(m*n)
Space: O(m+n)
"""

def can_partition_grid(grid):
    m = len(grid)
    n = len(grid[0])

    rowSums = [0]*m
    colSums = [0]*n
    totalSum = 0

    # Compute sums
    for i in range(m):
        for j in range(n):
            rowSums[i] += grid[i][j]
            colSums[j] += grid[i][j]
            totalSum += grid[i][j]

    # If total is odd → impossible
    if totalSum % 2 != 0:
        return False

    target = totalSum // 2

    currentSum = 0

    # Horizontal cuts
    for i in range(m-1):
        currentSum += rowSums[i]
        if currentSum == target:
            return True

    currentSum = 0

    # Vertical cuts
    for j in range(n-1):
        currentSum += colSums[j]
        if currentSum == target:
            return True

    return False


# ----------- User Input -----------

m, n = map(int, input().split())
grid = [list(map(int, input().split())) for _ in range(m)]

print(can_partition_grid(grid))