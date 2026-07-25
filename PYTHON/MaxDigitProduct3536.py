'''
Problem Statement:
You are given a positive integer n.
Return the maximum product of any two digits in n.
Note: You may use the same digit twice if it appears more than once in n.
'''

'''
Approach: Single-Pass Top Two Maxima Tracking (O(log10(N)) Time, O(1) Space)
1. Initialize `max1` and `max2` to track the largest and second largest digits encountered so far.
2. Extract digits one by one using modulo 10 (`n % 10`) while shifting right via integer division (`n //= 10`).
3. Update top digit candidates dynamically:
   - If `digit > max1`: Shift `max1` to `max2`, then store `digit` in `max1`.
   - Else if `digit > max2`: Update `max2` with `digit`.
4. Return `max1 * max2`.
'''

class MaxDigitProduct3536:
    def maxProduct(self, n: int) -> int:
        max1 = 0
        max2 = 0

        # Extract digits right-to-left and maintain top two maximum digits
        while n > 0:
            current_digit = n % 10
            if current_digit > max1:
                max2 = max1
                max1 = current_digit
            elif current_digit > max2:
                max2 = current_digit
            n //= 10

        return max1 * max2

if __name__ == '__main__':
    try:
        user_number_param = int(input("Enter integer n: ").strip())

        python_solver = MaxDigitProduct3536()
        maximum_product = python_solver.maxProduct(user_number_param)

        print("Maximum product of two digits:", maximum_product)
    except ValueError:
        print("Invalid integer input provided.")