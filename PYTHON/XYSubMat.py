"""
Problem: 3212. Count Submatrices With Equal Frequency of X and Y

Approach:
- Only consider submatrices from (0,0) → (i,j)
- Track counts of X and Y using prefix idea

Optimization idea:
Instead of full 2D prefix matrix, we use:
- prevX[j] → total X till (i,j)
- prevY[j] → total Y till (i,j)

Row-wise accumulation:
- currRowX, currRowY track counts in current row

Condition:
- Equal X and Y
- At least one X (important edge case)
"""

def number_of_submatrices(grid):
    m = len(grid)
    n = len(grid[0])

    count = 0

    # Column-wise prefix storage
    prevX = [0] * n
    prevY = [0] * n

    for i in range(m):

        currRowX = 0
        currRowY = 0

        for j in range(n):

            # Update current row counts
            if grid[i][j] == 'X':
                currRowX += 1
            elif grid[i][j] == 'Y':
                currRowY += 1

            # Build prefix sum vertically
            prevX[j] += currRowX
            prevY[j] += currRowY

            # Check conditions
            if prevX[j] > 0 and prevX[j] == prevY[j]:
                count += 1

    return count


# ----------- User Input Handling -----------

# Input dimensions
m, n = map(int, input().split())

# Input grid
grid = []
for _ in range(m):
    row = input().split()
    grid.append(row)

# Output result
print(number_of_submatrices(grid))