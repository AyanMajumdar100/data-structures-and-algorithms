"""
Problem: Find the String with LCP

--------------------------------------------------

🧠 CORE IDEA:

We solve in 2 steps:

1. Construct string using grouping logic
2. Validate by recomputing LCP

--------------------------------------------------

PHASE 1: BUILD STRING

If lcp[i][j] > 0:
→ s[i] == s[j]

So:
Group them using same character

We assign smallest characters first
to ensure lexicographically smallest result

--------------------------------------------------

PHASE 2: VALIDATE

We rebuild LCP matrix from string:

If s[i] == s[j]:
    lcp[i][j] = 1 + lcp[i+1][j+1]

Then compare with given matrix

--------------------------------------------------

If mismatch → return ""

--------------------------------------------------
"""

def find_the_string(lcp):
    n = len(lcp)

    s = [''] * n  # result string
    c = 'a'

    # ----------- PHASE 1: BUILD STRING -----------

    for i in range(n):

        # If not assigned yet
        if s[i] == '':

            # If we run out of characters
            if c > 'z':
                return ""

            s[i] = c

            # Assign same char to all j where LCP > 0
            for j in range(i + 1, n):
                if lcp[i][j] > 0:
                    s[j] = c

            # Move to next character
            c = chr(ord(c) + 1)

    # ----------- PHASE 2: VALIDATION -----------

    # Build actual LCP matrix
    actual = [[0] * (n + 1) for _ in range(n + 1)]

    for i in range(n - 1, -1, -1):
        for j in range(n - 1, -1, -1):

            if s[i] == s[j]:
                actual[i][j] = actual[i + 1][j + 1] + 1

            # If mismatch → invalid
            if actual[i][j] != lcp[i][j]:
                return ""

    return "".join(s)


# ----------- User Input -----------

n = int(input())
lcp = [list(map(int, input().split())) for _ in range(n)]

print(find_the_string(lcp))