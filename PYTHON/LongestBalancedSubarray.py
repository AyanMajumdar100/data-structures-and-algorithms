"""
## Problem: Longest Balanced Subarray with Distinct Parity

Description
You are given an integer array nums.

A subarray nums[i..j] is considered balanced if the number of distinct even
integers in the subarray is equal to the number of distinct odd integers.

Return the length of the longest balanced subarray.

Example 1:
Input: nums = [1, 2, 3, 4]
Output: 4

Example 2:
Input: nums = [1, 1, 2, 2, 3]
Output: 4

Example 3:
Input: nums = [2, 4, 6]
Output: 0

Constraints:
1 <= nums.length <= 1500
1 <= nums[i] <= 10^5
"""

import sys

class LongestBalancedSubarray:

    # Function to find longest balanced subarray
    def longest_balanced(self, nums):
        n = len(nums)
        max_len = 0     # Stores best answer
        max_val = 0     # To know max value in array

        # Find maximum value in nums
        # This helps create a tracking array for distinct elements
        for num in nums:
            if num > max_val:
                max_val = num

        # seen[val] stores last starting index where val appeared
        # Used to count DISTINCT numbers only once per subarray
        seen = [-1] * (max_val + 1)

        # Fix starting index of subarray
        for i in range(n):

            # Optimization:
            # If remaining elements can't beat max_len -> stop early
            if n - i <= max_len:
                break

            even_count = 0 # count of distinct even numbers
            odd_count = 0  # count of distinct odd numbers

            # Expand subarray from i -> j
            for j in range(i, n):
                val = nums[j]

                # If this value hasn't appeared in this subarray
                if seen[val] != i:
                    seen[val] = i

                    # Check parity using bit operation
                    if (val & 1) == 1:
                        odd_count += 1
                    else:
                        even_count += 1

                # If counts match -> balanced subarray found
                if even_count == odd_count:
                    current_len = j - i + 1

                    # Update maximum length
                    if current_len > max_len:
                        max_len = current_len

        return max_len


# ----------- USER INPUT + DRIVER CODE -----------

if __name__ == "__main__":
    print("Enter array elements separated by space:")
    try:
        input_str = sys.stdin.readline()
        
        # Convert input string to integer list
        if input_str.strip():
            nums = list(map(int, input_str.split()))
        else:
            nums = []

        # Create object and call function
        obj = LongestBalancedSubarray()
        result = obj.longest_balanced(nums)

        print("Longest balanced subarray length:", result)
        
    except ValueError:
        print("Invalid input. Please enter integers only.")
