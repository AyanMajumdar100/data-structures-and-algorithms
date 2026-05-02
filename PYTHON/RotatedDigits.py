'''
Problem Statement:
An integer x is good if after rotating each digit individually by 180 degrees, 
we get a valid number that is different from x. Each digit must be rotated.

A number is valid if each digit remains a digit after rotation:
- 0, 1, and 8 rotate to themselves.
- 2 and 5 rotate to each other.
- 6 and 9 rotate to each other.
- 3, 4, and 7 do not rotate to a valid digit.

Given an integer n, return the number of good integers in the range [1, n].

Constraints:
1 <= n <= 10^4
'''

def rotated_digits(n: int) -> int:
    # dp array stores the "state" of each number:
    # 0 = invalid
    # 1 = valid but rotates to itself
    # 2 = valid and rotates to a different number (Good Number)
    dp = [0] * (n + 1)
    count = 0
    
    for i in range(n + 1):
        if i < 10:
            if i in (0, 1, 8):
                dp[i] = 1
            elif i in (2, 5, 6, 9):
                dp[i] = 2
                count += 1
        else:
            # Check the state of the prefix (i // 10) and the last digit (i % 10)
            a = dp[i // 10]
            b = dp[i % 10]
            
            if a == 1 and b == 1:
                dp[i] = 1
            elif a >= 1 and b >= 1:
                dp[i] = 2
                count += 1
                
    return count

if __name__ == "__main__":
    try:
        n = int(input("Enter the integer n:\n").strip())
        
        if n < 1:
            print("Please enter a positive integer.")
        else:
            result = rotated_digits(n)
            print(f"Number of good integers: {result}")
            
    except ValueError:
        print("Invalid input detected. Please ensure you enter a valid integer.")