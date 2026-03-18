"""
Problem: 3070. Count Submatrices with Top-Left Element and Sum Less Than k

Approach:
- Every valid submatrix must start from (0,0)
- So we only consider submatrices ending at (i,j)
- Use 2D prefix sum to calculate sum efficiently

Key idea:
prefix[i][j] = grid[i][j] 
               + top 
               + left 
               - overlap

If prefix sum <= k → count it

Optimization:
If sum exceeds k in a row, break early (further values will only increase)
"""

def count_submatrices(grid, k):
    m = len(grid)
    n = len(grid[0])

    count = 0

    for i in range(m):
        for j in range(n):

            # Start with current cell
            total = grid[i][j]

            # Add top
            if i > 0:
                total += grid[i - 1][j]

            # Add left
            if j > 0:
                total += grid[i][j - 1]

            # Remove overlap
            if i > 0 and j > 0:
                total -= grid[i - 1][j - 1]

            # Store prefix sum back
            grid[i][j] = total

            # Check condition
            if total <= k:
                count += 1
            else:
                # Once exceeded, no need to continue this row
                break

    return count


# ----------- User Input Handling -----------

# Input matrix size
m, n = map(int, input().split())

# Input grid
grid = []
for _ in range(m):
    row = list(map(int, input().split()))
    grid.append(row)

# Input k
k = int(input())

# Output result
print(count_submatrices(grid, k))