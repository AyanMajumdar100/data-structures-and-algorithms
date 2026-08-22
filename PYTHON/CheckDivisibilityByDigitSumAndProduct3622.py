'''
Problem Statement:
Given a positive integer n, determine whether n is divisible by the sum of its digit sum and digit product:
total = (digit sum of n) + (digit product of n)
Return True if n % total == 0, otherwise return False.
'''

'''
Approach: Single-Pass Digit Extraction (O(log10(N)) Time, O(1) Space)
1. Initialize `digit_sum = 0` and `digit_product = 1`.
2. Extract digits of `n` one by one using `% 10` and `// 10`:
   - Add each digit to `digit_sum`.
   - Multiply each digit into `digit_product`.
3. Compute `total_divisor = digit_sum + digit_product`.
4. Return `n % total_divisor == 0`.
'''

class CheckDivisibilityByDigitSumAndProduct3622:
    def checkDivisibility(self, n: int) -> bool:
        temp = n
        digit_sum = 0
        digit_product = 1

        # Extract each digit and accumulate sum and product
        while temp > 0:
            digit = temp % 10
            digit_sum += digit
            digit_product *= digit
            temp //= 10

        total_divisor = digit_sum + digit_product
        return n % total_divisor == 0

if __name__ == '__main__':
    try:
        n_param = int(input("Enter positive integer n: ").strip())

        python_solver = CheckDivisibilityByDigitSumAndProduct3622()
        is_divisible = python_solver.checkDivisibility(n_param)

        print(f"Is {n_param} divisible by (digit sum + digit product):", is_divisible)
    except ValueError:
        print("Invalid integer input provided.")