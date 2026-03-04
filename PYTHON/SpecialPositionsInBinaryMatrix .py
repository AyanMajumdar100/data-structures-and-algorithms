"""
1582. Special Positions in a Binary Matrix

Problem Statement:
Given an m x n binary matrix mat, return the number of special positions.

A position (i, j) is special if:
1. mat[i][j] == 1
2. All other elements in row i are 0
3. All other elements in column j are 0

Constraints:
1 <= m, n <= 100
mat[i][j] is either 0 or 1
"""

def numSpecial(mat):
    m = len(mat)
    n = len(mat[0])

    # Count of 1s in each row
    rowCount = [0] * m

    # Count of 1s in each column
    colCount = [0] * n

    # First pass: count 1s in rows and columns
    for i in range(m):
        for j in range(n):
            if mat[i][j] == 1:
                rowCount[i] += 1
                colCount[j] += 1

    specialPositions = 0

    # Second pass: check special position condition
    for i in range(m):
        for j in range(n):
            if (mat[i][j] == 1 and
                rowCount[i] == 1 and
                colCount[j] == 1):
                specialPositions += 1

    return specialPositions


# User Input
m = int(input("Enter number of rows (m): "))
n = int(input("Enter number of columns (n): "))

mat = []

print("Enter matrix values (0 or 1):")

for _ in range(m):
    row = list(map(int, input().split()))
    mat.append(row)

result = numSpecial(mat)

print("Number of Special Positions:", result)