"""
Problem: 3643. Flip Square Submatrix Vertically

--------------------------------------------------

DETAILED LOGIC:

We are given:
- (x, y) → top-left of square
- k → size of square

We need to flip ONLY that square vertically.

--------------------------------------------------

What does vertical flip mean?

Before:
Row x
Row x+1
Row x+2

After:
Row x+2
Row x+1
Row x

So basically → reverse rows inside the square

--------------------------------------------------

How do we do this efficiently?

Use TWO POINTERS:
- top → starts from x
- bottom → starts from x + k - 1

Swap:
top row ↔ bottom row

Then move inward:
top += 1
bottom -= 1

--------------------------------------------------

Important:
We only swap columns inside the square:
from y → y + k - 1

--------------------------------------------------

Stop when:
top >= bottom

--------------------------------------------------

Time Complexity:
O(k * k)

Space Complexity:
O(1) (in-place)
"""

def reverse_submatrix(grid, x, y, k):

    top = x
    bottom = x + k - 1

    # Continue until rows meet
    while top < bottom:

        # Swap elements within the square columns
        for col in range(y, y + k):
            grid[top][col], grid[bottom][col] = grid[bottom][col], grid[top][col]

        # Move inward
        top += 1
        bottom -= 1

    return grid


# ----------- User Input Handling -----------

# Input matrix size
m, n = map(int, input().split())

# Input grid
grid = []
for _ in range(m):
    grid.append(list(map(int, input().split())))

# Input x, y, k
x, y, k = map(int, input().split())

# Perform operation
result = reverse_submatrix(grid, x, y, k)

# Print result
for row in result:
    print(*row)