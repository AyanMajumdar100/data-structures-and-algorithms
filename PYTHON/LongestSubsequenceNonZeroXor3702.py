'''
Problem Statement:
You are given an integer array nums.
Return the length of the longest subsequence in nums whose bitwise XOR is non-zero.
If no such subsequence exists, return 0.
'''

'''
Approach: Bitwise XOR Global Parity Analysis (O(N) Time, O(1) Space)
1. Compute the total XOR sum of all elements in the entire array `nums`.
2. Case 1: Total XOR sum != 0
   - The entire array is already a valid subsequence with non-zero XOR.
   - The maximum length is `len(nums)`.
3. Case 2: Total XOR sum == 0
   - If all elements in `nums` are 0, every possible subsequence has XOR = 0. Return `0`.
   - If there is at least one non-zero element `x`, removing `x` from the full array leaves 
     a subsequence of length `len(nums) - 1` whose XOR is `0 ^ x = x != 0`.
   - Thus, the maximum length is `len(nums) - 1`.
'''

class LongestSubsequenceNonZeroXor3702:
    def longestSubsequence(self, nums: list[int]) -> int:
        total_xor_sum = 0
        contains_non_zero_element = False

        # Compute total XOR sum and check for any non-zero element
        for num in nums:
            total_xor_sum ^= num
            if num != 0:
                contains_non_zero_element = True

        # Evaluate max length based on XOR parity
        if total_xor_sum != 0:
            return len(nums)
        elif contains_non_zero_element:
            return len(nums) - 1
        else:
            return 0

if __name__ == '__main__':
    try:
        raw_input_string = input("Enter array elements separated by space: ").strip()
        if not raw_input_string:
            print("Longest subsequence length: 0")
        else:
            user_nums_array = list(map(int, raw_input_string.split()))

            python_solver = LongestSubsequenceNonZeroXor3702()
            max_subsequence_length = python_solver.longestSubsequence(user_nums_array)

            print("Length of longest subsequence with non-zero bitwise XOR:", max_subsequence_length)
    except ValueError:
        print("Invalid array input format.")