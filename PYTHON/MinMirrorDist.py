'''
Problem Statement:
You are given an integer array nums.
A mirror pair is a pair of indices (i, j) such that:
1. 0 <= i < j < nums.length
2. reverse(nums[i]) == nums[j], where reverse(x) denotes the integer formed by reversing the digits of x. 
   Leading zeros are omitted after reversing, for example reverse(120) = 21.

Return the minimum absolute distance between the indices of any mirror pair. 
If no mirror pair exists, return -1.

Constraints:
1 <= nums.length <= 10^5
1 <= nums[i] <= 10^9
'''

import math

def min_mirror_pair_distance(nums: list[int]) -> int:
    # Dictionary to map the reversed number to its latest observed index
    rev_map = {}
    min_distance = math.inf
    
    for i, num in enumerate(nums):
        # If current number acts as nums[j] to a previously processed reversed nums[i]
        if num in rev_map:
            min_distance = min(min_distance, i - rev_map[num])
            
        # Pythonic trick: Reverse the integer using string slicing. 
        # C-level string operations in Python are highly optimized and often faster 
        # than purely mathematical loops in the Python interpreter.
        rev = int(str(num)[::-1])
        
        # Store the reversed number. Re-writing the key natively keeps the most 
        # recent index, ensuring minimum distance for future lookups.
        rev_map[rev] = i
        
    return min_distance if min_distance != math.inf else -1

if __name__ == "__main__":
    try:
        user_input = input("Enter the array elements separated by spaces:\n").strip()
        
        if not user_input:
            print("Array cannot be empty.")
        else:
            nums = [int(x) for x in user_input.split()]
            result = min_mirror_pair_distance(nums)
            print(f"Minimum Absolute Distance: {result}")
            
    except ValueError:
        print("Invalid input. Please enter valid integers only.")