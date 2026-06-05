'''
Problem Statement:
You are given two integers num1 and num2 representing an inclusive range [num1, num2].
The waviness of a number is defined as the total count of its peaks and valleys:
- A digit is a peak if it is strictly greater than both of its immediate neighbors.
- A digit is a valley if it is strictly less than both of its immediate neighbors.
- The first and last digits of a number cannot be peaks or valleys.
- Any number with fewer than 3 digits has a waviness of 0.
Return the total sum of waviness for all numbers in the range [num1, num2].
'''

'''
Approach: Digit Dynamic Programming (Digit DP) with Memoization
Since range elements can be large, we avoid linear scanning by looking at the problem 
through digit combinations using Prefix Math: solve(num2) - solve(num1 - 1).
We track state over multi-dimensional caches:
- idx: Current digit index we are filling.
- d1: The grandfather digit (index i - 2). Value 10 represents unassigned state.
- d2: The father digit (index i - 1). Value 10 represents unassigned state.
- isLess: Binary flag indicating if the built prefix is strictly smaller than bounds.
- isStarted: Binary flag indicating if we have started forming non-zero numbers.
At each recursive junction, we look ahead to see if the past digit d2 forms a peak or valley 
with its predecessor d1 and successors d. If validated, it contributes to waviness.
'''
class TotalWaveII3751:
    def totalWaviness(self, num1: int, num2: int) -> int:
        # Use prefix math properties to extract total sum for range [num1, num2]
        return self.solve(num2) - self.solve(num1 - 1)

    def solve(self, num: int) -> int:
        # Constraints state any number below 100 has a baseline waviness of 0
        if num < 100:
            return 0
            
        self.limit_chars = str(num)
        n = len(self.limit_chars)
        
        # Initialize memoization tables for storing (count, waviness) tracking states
        self.memo_count = [[[[[-1] * 2 for _ in range(2)] for _ in range(11)] for _ in range(11)] for _ in range(n)]
        self.memo_sum = [[[[[-1] * 2 for _ in range(2)] for _ in range(11)] for _ in range(11)] for _ in range(n)]
        
        # Compute transitions tracking total count of matching numbers and their waviness sum
        count, total_wave = self.dp(0, 10, 10, 0, 0)
        return total_wave

    def dp(self, idx: int, d1: int, d2: int, is_less: int, is_started: int) -> tuple[int, int]:
        # Base case: successfully processed all digits
        if idx == len(self.limit_chars):
            return 1, 0  # Returns (count = 1, waviness = 0)
            
        # Return already calculated results if cached
        if self.memo_count[idx][d1][d2][is_less][is_started] != -1:
            return (self.memo_count[idx][d1][d2][is_less][is_started], 
                    self.memo_sum[idx][d1][d2][is_less][is_started])
                    
        # Determine current ceiling value for the processed position
        limit = 9 if is_less == 1 else int(self.limit_chars[idx])
        total_count = 0
        total_waviness = 0
        
        # Try placing each candidate digit
        for d in range(limit + 1):
            next_is_less = 1 if (is_less == 1 or d < limit) else 0
            next_is_started = 1 if (is_started == 1 or d > 0) else 0
            
            next_d1, next_d2 = 10, 10
            if next_is_started == 1:
                if is_started == 0:
                    # First digit placed sets up the future father element
                    next_d2 = d
                else:
                    # Subsequent digits shift the window (father becomes grandfather, current is father)
                    next_d1 = d2
                    next_d2 = d
                    
            # Recurse to process the subsequent placements
            sub_count, sub_sum = self.dp(idx + 1, next_d1, next_d2, next_is_less, next_is_started)
            
            wave_contribution = 0
            # Check if the previous digit (d2) acts as a peak or a valley
            if is_started == 1 and d1 != 10 and d2 != 10:
                if (d1 < d2 and d2 > d) or (d1 > d2 and d2 < d):
                    wave_contribution = 1
                    
            # Accumulate counts and running totals combining deeper recursion trees
            total_count += sub_count
            total_waviness += sub_sum + wave_contribution * sub_count
            
        # Cache state calculations
        self.memo_count[idx][d1][d2][is_less][is_started] = total_count
        self.memo_sum[idx][d1][d2][is_less][is_started] = total_waviness
        
        return total_count, total_waviness

if __name__ == '__main__':
    try:
        # Handle user input
        num1 = int(input("Enter num1: "))
        num2 = int(input("Enter num2: "))
        
        solver = TotalWaveII3751()
        result = solver.totalWaviness(num1, num2)
        print("Total waviness:", result)
    except ValueError:
        print("Invalid input format. Please enter integers only.")