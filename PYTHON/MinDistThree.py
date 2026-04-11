'''
Problem Statement:
You are given an integer array nums.
A tuple (i, j, k) of 3 distinct indices is good if nums[i] == nums[j] == nums[k].
The distance of a good tuple is abs(i - j) + abs(j - k) + abs(k - i).
Return an integer denoting the minimum possible distance of a good tuple. 
If no good tuples exist, return -1.

Constraints:
1 <= n == nums.length <= 10^5
1 <= nums[i] <= n
'''

import math

def minimum_distance(nums: list[int]) -> int:
    n = len(nums)
    
    # We use fixed-size arrays instead of dictionaries because the constraints 
    # guarantee 1 <= nums[i] <= n. Array lookups are slightly faster.
    first = [-1] * (n + 1)
    second = [-1] * (n + 1)
    
    min_dist = math.inf
    
    for i, val in enumerate(nums):
        # If we have seen 'val' at least twice before, calculate the distance.
        # Math trick: |i - j| + |j - k| + |k - i| for i < j < k simplifies to 2 * (k - i).
        if first[val] != -1:
            dist = 2 * (i - first[val])
            if dist < min_dist:
                min_dist = dist
                
        # Shift the index history: 
        # The 'second' most recent becomes the 'first' (oldest tracked),
        # and the current index 'i' becomes the new 'second' most recent.
        first[val] = second[val]
        second[val] = i
        
    return min_dist if min_dist != math.inf else -1

if __name__ == "__main__":
    try:
        user_input = input("Enter the array elements separated by spaces:\n").strip()
        
        if not user_input:
            print("Array cannot be empty.")
        else:
            nums = [int(x) for x in user_input.split()]
            result = minimum_distance(nums)
            print(f"Minimum Distance: {result}")
            
    except ValueError:
        print("Invalid input. Please enter valid integers only.")