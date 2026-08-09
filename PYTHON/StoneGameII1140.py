'''
Problem Statement:
Alice and Bob play a game with piles of stones.
A player can take all stones in the first X remaining piles where 1 <= X <= 2M.
Then M is updated to max(M, X). Initially, M = 1 and Alice goes first.
Return the maximum number of stones Alice can get assuming optimal play.
'''

'''
Approach: Dynamic Programming with Suffix Sums (O(N^3) Time, O(N^2) Space)
1. Suffix Sum Precomputation:
   `suffix_sum[i]` stores the total sum of stones from index `i` to `n - 1`.
2. DP State:
   Let `dp[i][m]` represent the maximum number of stones a player can collect starting at index `i` with parameter `m`.
3. Base Case:
   If `i + 2 * m >= n`, the active player can take ALL remaining piles, yielding `suffix_sum[i]`.
4. Recurrence:
   Otherwise, for every choice of `X` where `1 <= X <= 2 * m`:
   - The opponent is left starting at index `i + X` with `next_m = max(m, X)`.
   - The current player gets `suffix_sum[i] - dp[i + X][next_m]`.
   - `dp[i][m] = max(suffix_sum[i] - dp[i + X][next_m])` for all valid `X`.
'''

class StoneGameII1140:
    def stoneGameII(self, piles: list[int]) -> int:
        n = len(piles)
        if n == 0:
            return 0

        # Step 1: Precompute suffix sums
        suffix_sum = [0] * n
        suffix_sum[-1] = piles[-1]
        for i in range(n - 2, -1, -1):
            suffix_sum[i] = suffix_sum[i + 1] + piles[i]

        # Step 2: Initialize DP table
        dp = [[0] * (n + 1) for _ in range(n)]

        # Step 3: Fill DP table bottom-up from right to left
        for i in range(n - 1, -1, -1):
            for m in range(1, n + 1):
                if i + 2 * m >= n:
                    dp[i][m] = suffix_sum[i]
                else:
                    max_stones = 0
                    for x in range(1, 2 * m + 1):
                        next_m = min(n, max(m, x))
                        current_stones = suffix_sum[i] - dp[i + x][next_m]
                        max_stones = max(max_stones, current_stones)
                    dp[i][m] = max_stones

        # Step 4: Alice starts at index 0 with M = 1
        return dp[0][1]

if __name__ == '__main__':
    try:
        raw_input_string = input("Enter stone piles separated by space: ").strip()
        if not raw_input_string:
            print("Maximum stones Alice can get: 0")
        else:
            user_piles_array = list(map(int, raw_input_string.split()))

            python_solver = StoneGameII1140()
            alice_max_stones = python_solver.stoneGameII(user_piles_array)

            print("Maximum stones Alice can get:", alice_max_stones)
    except ValueError:
        print("Invalid array input format.")