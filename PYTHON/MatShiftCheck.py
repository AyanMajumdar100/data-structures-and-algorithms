"""
Problem: Matrix Similarity After Cyclic Shifts

--------------------------------------------------

CORE IDEA:

We simulate shifting WITHOUT actually modifying matrix.

Instead, we check if every element matches
its expected shifted position.

--------------------------------------------------

KEY OPTIMIZATION:

k = k % n

Because after n shifts → same matrix

--------------------------------------------------

LOGIC:

For each element (i, j):

Even row:
→ left shift → compare with (j + k) % n

Odd row:
→ right shift → compare with (j - k + n) % n

--------------------------------------------------

If any mismatch → False
Else → True
"""

def are_similar(mat, k):
    m, n = len(mat), len(mat[0])

    k %= n  # reduce shifts

    for i in range(m):
        for j in range(n):

            if i % 2 == 0:
                # Even row → left shift
                if mat[i][j] != mat[i][(j + k) % n]:
                    return False
            else:
                # Odd row → right shift
                if mat[i][j] != mat[i][(j - k + n) % n]:
                    return False

    return True


# ----------- User Input -----------

m, n = map(int, input().split())
mat = [list(map(int, input().split())) for _ in range(m)]

k = int(input())

print(are_similar(mat, k))