"""
Problem: 1886. Determine Whether Matrix Can Be Obtained By Rotation

--------------------------------------------------

DETAILED LOGIC:

We are NOT rotating the matrix physically.

Instead, we use index mapping to simulate rotations.

--------------------------------------------------

Rotation Mapping:

For position (i, j) in original matrix:

0°   → (i, j)
90°  → (j, n-1-i)
180° → (n-1-i, n-1-j)
270° → (n-1-j, i)

--------------------------------------------------

Strategy:

- Assume all 4 rotations are valid
- Traverse entire matrix
- Compare values using mapping
- If mismatch found → mark that rotation invalid

--------------------------------------------------

Final Answer:
- If ANY rotation is still valid → return True
- Else → False

--------------------------------------------------

Time Complexity: O(n^2)
Space Complexity: O(1)
"""

def find_rotation(mat, target):
    n = len(mat)

    # Assume all rotations are valid initially
    rot0 = True
    rot90 = True
    rot180 = True
    rot270 = True

    for i in range(n):
        for j in range(n):

            # 0° rotation
            if mat[i][j] != target[i][j]:
                rot0 = False

            # 90° rotation
            if mat[i][j] != target[j][n - 1 - i]:
                rot90 = False

            # 180° rotation
            if mat[i][j] != target[n - 1 - i][n - 1 - j]:
                rot180 = False

            # 270° rotation
            if mat[i][j] != target[n - 1 - j][i]:
                rot270 = False

    return rot0 or rot90 or rot180 or rot270


# ----------- User Input Handling -----------

# Input size
n = int(input())

# Input mat
mat = [list(map(int, input().split())) for _ in range(n)]

# Input target
target = [list(map(int, input().split())) for _ in range(n)]

# Output result
print(find_rotation(mat, target))