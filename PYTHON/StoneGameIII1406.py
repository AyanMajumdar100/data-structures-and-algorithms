'''
Problem Statement:
Alice and Bob take turns taking 1, 2, or 3 stones from the front of the remaining row of stones.
Alice goes first. Both play optimally to maximize their score.
Return "Alice" if Alice wins, "Bob" if Bob wins, or "Tie" if they draw.
'''

'''
Approach: Dynamic Programming / Minimax Game Theory (O(N) Time, O(N) Space)
1. Score Difference Concept:
   Let `dp[i]` represent the maximum score difference (Current Player's Score - Opponent's Score) 
   the active player can achieve starting from index `i` to the end of the array `n`.
2. Recurrence Relation:
   At index `i`, the active player can take `k` stones where `k` in {1, 2, 3}:
   - The player gains `current_sum` = sum of values of stones taken from `i` to `i + k - 1`.
   - The opponent is left with the game starting from index `i + k`, where they achieve a score difference `dp[i + k]`.
   - Net score difference for the current player taking `k` stones is `current_sum - dp[i + k]`.
   - `dp[i] = max(current_sum - dp[i + k])` over all valid `k` (1 <= k <= 3 and i + k <= n).
3. Game Outcome:
   - If `dp[0] > 0`: Alice achieves a positive score advantage and wins -> "Alice".
   - If `dp[0] < 0`: Bob achieves a positive score advantage and wins -> "Bob".
   - If `dp[0] == 0`: Both players achieve the same score -> "Tie".
'''

class StoneGameIII1406:
    def stoneGameIII(self, stoneValue: list[int]) -> str:
        array_length = len(stoneValue)
        # dp[i] stores the max net advantage obtainable starting from index i
        dp = [0] * (array_length + 1)

        # Process backward from the end of the stone row
        for i in range(array_length - 1, -1, -1):
            max_score_diff = float('-inf')
            current_take_sum = 0

            # Try taking 1, 2, or 3 stones
            for k in range(1, 4):
                if i + k <= array_length:
                    current_take_sum += stoneValue[i + k - 1]
                    max_score_diff = max(max_score_diff, current_take_sum - dp[i + k])

            dp[i] = max_score_diff

        # Determine winner based on Alice's net advantage at index 0
        if dp[0] > 0:
            return "Alice"
        elif dp[0] < 0:
            return "Bob"
        else:
            return "Tie"

if __name__ == '__main__':
    try:
        raw_input_string = input("Enter stone values separated by space: ").strip()
        if not raw_input_string:
            print("Game Result: Tie")
        else:
            user_stone_values = list(map(int, raw_input_string.split()))

            python_solver = StoneGameIII1406()
            winner_result = python_solver.stoneGameIII(user_stone_values)

            print("Game Result:", winner_result)
    except ValueError:
        print("Invalid array input format.")