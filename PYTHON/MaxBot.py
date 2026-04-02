"""
Problem: 3418. Maximum Amount of Money Robot Can Earn

Same logic as Java.

-----------------------------------------------------

LOGIC (Human Explanation):

Think of this like a game path where:
- You move only RIGHT or DOWN
- Some cells give coins, some steal coins

Twist:
You can "ignore" (neutralize) up to 2 bad cells.

So at every cell, you decide:
1. Take the value (even if negative)
2. Or neutralize it (if negative and you still have chances left)

-----------------------------------------------------

DP STATE:

dp[i][j][k]
= max coins at position (i,j)
= using k neutralizations

-----------------------------------------------------

TRANSITIONS:

1. Normal move:
   take value → add coins[i][j]

2. Neutralize (only if negative):
   skip adding negative value

-----------------------------------------------------
"""

class MaxBot:  # same short class name

    def maximumAmount(self, coins):

        m, n = len(coins), len(coins[0])

        INF = -10**9

        # 3D DP array
        dp = [[[INF]*3 for _ in range(n)] for _ in range(m)]

        # Starting point
        dp[0][0][0] = coins[0][0]

        # Neutralize if first cell is negative
        if coins[0][0] < 0:
            dp[0][0][1] = 0

        # Fill DP
        for i in range(m):
            for j in range(n):

                if i == 0 and j == 0:
                    continue

                for k in range(3):

                    maxPrev = INF

                    # From top
                    if i > 0:
                        maxPrev = max(maxPrev, dp[i-1][j][k])

                    # From left
                    if j > 0:
                        maxPrev = max(maxPrev, dp[i][j-1][k])

                    # Case 1: take value
                    if maxPrev != INF:
                        dp[i][j][k] = max(dp[i][j][k], maxPrev + coins[i][j])

                    # Case 2: neutralize robber
                    if k > 0 and coins[i][j] < 0:

                        maxPrevUse = INF

                        if i > 0:
                            maxPrevUse = max(maxPrevUse, dp[i-1][j][k-1])

                        if j > 0:
                            maxPrevUse = max(maxPrevUse, dp[i][j-1][k-1])

                        # skip adding negative value
                        if maxPrevUse != INF:
                            dp[i][j][k] = max(dp[i][j][k], maxPrevUse)

        # Final answer
        return max(dp[m-1][n-1])


# ----------- USER INPUT DRIVER CODE -----------

if __name__ == "__main__":

    # Input rows and columns
    m, n = map(int, input().split())

    coins = []

    # Input grid
    for _ in range(m):
        coins.append(list(map(int, input().split())))

    obj = MaxBot()
    result = obj.maximumAmount(coins)

    print(result)