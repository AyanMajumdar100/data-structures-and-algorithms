"""
Problem: Lexicographically Smallest Generated String

--------------------------------------------------

🧠 OVERVIEW:

We construct string in 3 steps:
1. Force 'T' matches
2. Track 'F' constraints
3. Fill '?' greedily

--------------------------------------------------
"""

def generate_string(str1, str2):
    n, m = len(str1), len(str2)
    length = n + m - 1

    # ----------- PHASE 1: Initialize -----------
    ans = ['?'] * length

    # ----------- PHASE 2: Handle 'T' -----------
    for i in range(n):
        if str1[i] == 'T':
            for j in range(m):
                if ans[i + j] != '?' and ans[i + j] != str2[j]:
                    return ""
                ans[i + j] = str2[j]

    # ----------- PHASE 3: Track 'F' constraints -----------
    ends_at = [[] for _ in range(length)]

    for i in range(n):
        if str1[i] == 'F':

            last_q = -1

            for j in range(m - 1, -1, -1):
                if ans[i + j] == '?':
                    last_q = i + j
                    break

            if last_q == -1:
                match = True
                for j in range(m):
                    if ans[i + j] != str2[j]:
                        match = False
                        break
                if match:
                    return ""
            else:
                ends_at[last_q].append(i)

    # ----------- PHASE 4: Fill '?' greedily -----------

    for k in range(length):

        if ans[k] != '?':
            continue

        forbidden = [False] * 26

        for i in ends_at[k]:

            match = True

            for j in range(m):
                if i + j == k:
                    continue
                if ans[i + j] != str2[j]:
                    match = False
                    break

            if match:
                forbidden[ord(str2[k - i]) - ord('a')] = True

        # Pick smallest valid char
        picked = None

        for c in range(26):
            if not forbidden[c]:
                picked = chr(c + ord('a'))
                break

        if picked is None:
            return ""

        ans[k] = picked

    return "".join(ans)


# ----------- User Input -----------

str1 = input().strip()
str2 = input().strip()

print(generate_string(str1, str2))