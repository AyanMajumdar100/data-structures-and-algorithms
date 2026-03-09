"""
3129. Find All Possible Stable Binary Arrays I

We must build a binary array using:
- exactly 'zero' number of 0s
- exactly 'one' number of 1s

Constraint:
No more than 'limit' identical elements can appear consecutively.

Approach: Dynamic Programming

dp[i][j][0] → arrays using i zeros and j ones ending with 0
dp[i][j][1] → arrays using i zeros and j ones ending with 1
"""

def numberOfStableArrays(zero, one, limit):

    MOD = 10**9 + 7

    dp = [[[0]*2 for _ in range(one+1)] for _ in range(zero+1)]

    # Base cases for only zeros
    for i in range(1, min(zero, limit) + 1):
        dp[i][0][0] = 1

    # Base cases for only ones
    for j in range(1, min(one, limit) + 1):
        dp[0][j][1] = 1

    for i in range(1, zero+1):
        for j in range(1, one+1):

            # End with 0
            dp[i][j][0] = (dp[i-1][j][0] + dp[i-1][j][1]) % MOD

            if i > limit:
                dp[i][j][0] = (dp[i][j][0] - dp[i-limit-1][j][1]) % MOD

            # End with 1
            dp[i][j][1] = (dp[i][j-1][0] + dp[i][j-1][1]) % MOD

            if j > limit:
                dp[i][j][1] = (dp[i][j][1] - dp[i][j-limit-1][0]) % MOD

    return (dp[zero][one][0] + dp[zero][one][1]) % MOD


# User Input
zero = int(input("Enter number of zeros: "))
one = int(input("Enter number of ones: "))
limit = int(input("Enter limit: "))

result = numberOfStableArrays(zero, one, limit)

print("Number of Stable Binary Arrays:", result)