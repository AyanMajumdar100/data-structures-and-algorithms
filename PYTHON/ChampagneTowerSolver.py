"""
799. Champagne Tower

We stack glasses in a pyramid, where the first row has 1 glass,
the second row has 2 glasses, and so on until the 100th row.
Each glass holds exactly 1 cup of champagne.

We pour some champagne into the top glass.
If a glass becomes full, the extra champagne spills equally
to the glass directly below-left and below-right.

If glasses in the last row overflow, the extra falls to the floor.

We need to return how full the glass at (query_row, query_glass)
is after pouring the champagne.
Rows and glasses are 0-indexed.

Examples:
Input: poured = 1, query_row = 1, query_glass = 1
Output: 0.00000

Input: poured = 2, query_row = 1, query_glass = 1
Output: 0.50000

Input: poured = 100000009, query_row = 33, query_glass = 17
Output: 1.00000

Constraints:
0 <= poured <= 10^9
0 <= query_glass <= query_row < 100
"""


class ChampagneTowerSolver:

    def champagneTower(self, poured: int, query_row: int, query_glass: int) -> float:

        # I create a DP list where dp[c] stores how much champagne
        # reaches position c in the current row.
        # I keep size query_row + 2 so I never go out of bounds.
        dp = [0.0] * (query_row + 2)

        # I pour all champagne into the top glass initially.
        dp[0] = poured

        # I simulate the pouring process row by row.
        for r in range(query_row):

            # I iterate backwards so values needed later
            # in this row are not overwritten too early.
            for c in range(r, -1, -1):

                # Each glass holds 1 cup.
                # Anything above 1 spills equally to two glasses below.
                excess = (dp[c] - 1.0) / 2.0

                if excess > 0:
                    # I send half overflow to the right child
                    dp[c + 1] += excess

                    # I keep half overflow in current index
                    # which becomes the left child in next row
                    dp[c] = excess
                else:
                    # If no overflow, nothing flows down
                    dp[c] = 0.0

        # A glass cannot hold more than 1 cup
        return min(1.0, dp[query_glass])


# ---------------- USER INPUT DRIVER ----------------

if __name__ == "__main__":
    poured = int(input("Enter poured amount: "))
    query_row = int(input("Enter query row: "))
    query_glass = int(input("Enter query glass: "))

    solver = ChampagneTowerSolver()
    result = solver.champagneTower(poured, query_row, query_glass)

    print("Glass fullness:", result)
