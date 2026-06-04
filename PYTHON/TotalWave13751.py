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
Approach: Brute Force Iteration
Since the upper bound constraint for num2 is relatively small (10^5), we can simulate 
the process for each number sequentially in the given range.
1. For each number from num1 to num2, we calculate its waviness.
2. To compute waviness, we convert the number into a string/list of digits.
3. We then iterate from the second digit to the second-to-last digit, checking 
   whether each digit behaves as a peak or a valley compared to its neighbors.
4. Sum up the counts of peaks and valleys across all numbers.
'''
class TotalWave13751:
    def totalWaviness(self, num1: int, num2: int) -> int:
        total_sum = 0
        # Loop through all numbers in the inclusive range
        for i in range(num1, num2 + 1):
            total_sum += self.getWaviness(i)
        return total_sum

    def getWaviness(self, num: int) -> int:
        # Numbers with less than 3 digits cannot contain any peak or valley
        if num < 100:
            return 0
            
        # Convert number to string to access characters as digits
        digits = str(num)
        length = len(digits)
        
        waviness = 0
        # Check middle digits (index 1 to length - 2)
        for i in range(1, length - 1):
            # Peak condition
            if digits[i] > digits[i - 1] and digits[i] > digits[i + 1]:
                waviness += 1
            # Valley condition
            elif digits[i] < digits[i - 1] and digits[i] < digits[i + 1]:
                waviness += 1
                
        return waviness

if __name__ == '__main__':
    try:
        # Handle user input
        num1 = int(input("Enter num1: "))
        num2 = int(input("Enter num2: "))
        
        solver = TotalWave13751()
        result = solver.totalWaviness(num1, num2)
        print("Total waviness:", result)
    except ValueError:
        print("Invalid input format. Please enter integers only.")