"""
1727. Largest Submatrix With Rearrangements

Problem:
Given a binary matrix, you can rearrange columns in any order.
Find the largest submatrix consisting only of 1s.

------------------------------------------------------------

Core Idea:
1. Convert matrix into heights (like histogram)
2. For each row:
   - Sort heights (simulates column rearrangement)
   - Calculate max possible rectangle

------------------------------------------------------------
"""

def largestSubmatrix(matrix):

    m = len(matrix)
    n = len(matrix[0])

    max_area = 0

    for i in range(m):

        # Step 1: Build heights
        for j in range(n):
            if matrix[i][j] != 0 and i > 0:
                matrix[i][j] += matrix[i - 1][j]

        # Step 2: Copy current row
        curr_row = matrix[i][:]

        # Step 3: Sort heights (ascending)
        curr_row.sort()

        # Step 4: Calculate max area
        for j in range(n):

            height = curr_row[j]

            # Width = number of columns from j → end
            width = n - j

            area = height * width

            max_area = max(max_area, area)

    return max_area


# User Input
m = int(input("Enter rows: "))
n = int(input("Enter columns: "))

matrix = []

print("Enter matrix values (0 or 1):")

for _ in range(m):
    matrix.append(list(map(int, input().split())))

result = largestSubmatrix(matrix)

print("Largest Submatrix Area:", result)