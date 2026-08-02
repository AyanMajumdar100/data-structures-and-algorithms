'''
Problem Statement:
Alice and Bob play a game with an EVEN number of stone piles arranged in a row.
The total number of stones is ODD, so there are no ties.
Alice goes first and takes a pile from either end. Bob follows under the same rules.
Return true if Alice wins, assuming optimal play.
'''

'''
Approach: Parity Strategy / Game Theory Mathematical Proof
1. Mathematical Guarantee (O(1) Time & Space):
   - The total number of piles N is EVEN.
   - Piles can be partitioned into two distinct parity groups based on index:
     Group 1 (Odd-indexed piles):  piles[0], piles[2], piles[4], ...
     Group 2 (Even-indexed piles): piles[1], piles[3], piles[5], ...
   - Since the total sum of all stones across both groups is ODD, one group must strictly 
     contain more stones than the other (Sum(Odd) != Sum(Even)).
2. Strategy Control:
   - Alice moves first and can choose whether to force taking ALL odd-indexed piles or ALL 
     even-indexed piles:
     * If she wants all even-indexed piles (0, 2, ...), she takes `piles[0]`. Bob is left 
       choosing between `piles[1]` and `piles[N-1]` (both odd indices).
     * If she wants all odd-indexed piles (1, 3, ...), she takes `piles[N-1]`. Bob is left 
       choosing between `piles[0]` and `piles[N-2]` (both even indices).
   - Thus, Alice calculates `Sum(Even)` and `Sum(Odd)` before her first move and chooses 
     the strictly larger group. Bob is forced to take whatever remains.
3. Conclusion:
   Alice can ALWAYS force a win. Simply returning `True` is mathematically optimal.
'''

class StoneGame877:
    def stoneGame(self, piles: list[int]) -> bool:
        # Alice always wins due to the parity advantage on even-sized arrays
        return True

if __name__ == '__main__':
    try:
        raw_input_string = input("Enter stone piles separated by space: ").strip()
        if not raw_input_string:
            print("Alice wins: True")
        else:
            user_piles_array = list(map(int, raw_input_string.split()))

            python_solver = StoneGame877()
            result = python_solver.stoneGame(user_piles_array)

            print("Alice wins:", result)
    except ValueError:
        print("Invalid array input format.")