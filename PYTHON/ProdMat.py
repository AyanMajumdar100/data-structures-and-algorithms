"""
Problem: 2906. Construct Product Matrix

--------------------------------------------------

CORE IDEA:

We need product of all elements except self.

We CANNOT use division.

So we use:
- Prefix product
- Suffix product

--------------------------------------------------

THINK OF MATRIX AS FLATTENED ARRAY:

Traversal order:
row → row → row

--------------------------------------------------

STEP 1: PREFIX

For each cell:
- Store product of all previous elements
- Update prefix

--------------------------------------------------

STEP 2: SUFFIX

Traverse backward:
- Multiply suffix (product of elements after)
- Update suffix

--------------------------------------------------

FINAL:
p[i][j] = prefix * suffix

--------------------------------------------------

Time: O(n*m)
Space: O(1) extra
"""

def construct_product_matrix(grid):
    n = len(grid)
    m = len(grid[0])

    p = [[0]*m for _ in range(n)]

    MOD = 12345

    pref = 1

    # Prefix pass
    for i in range(n):
        for j in range(m):
            p[i][j] = pref
            pref = (pref * (grid[i][j] % MOD)) % MOD

    suff = 1

    # Suffix pass
    for i in range(n-1, -1, -1):
        for j in range(m-1, -1, -1):
            p[i][j] = (p[i][j] * suff) % MOD
            suff = (suff * (grid[i][j] % MOD)) % MOD

    return p


# ----------- User Input Handling -----------

n, m = map(int, input().split())

grid = [list(map(int, input().split())) for _ in range(n)]

result = construct_product_matrix(grid)

for row in result:
    print(*row)