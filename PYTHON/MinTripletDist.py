'''
Problem Statement:
You are given an integer array nums.
A tuple (i, j, k) of 3 distinct indices is good if nums[i] == nums[j] == nums[k].
The distance of a good tuple is abs(i - j) + abs(j - k) + abs(k - i).
Return an integer denoting the minimum possible distance of a good tuple. 
If no good tuples exist, return -1.

Constraints:
1 <= n == nums.length <= 100
1 <= nums[i] <= n
'''

from collections import deque
import math

def minimum_distance(nums: list[int]) -> int:
    # Dictionary to keep track of the last up to 3 indices of each number.
    # Using a deque with a max length of 3 automatically pops the oldest 
    # index when a 4th index is added, simulating the shifting logic perfectly.
    last_three = {}
    min_distance = math.inf
    
    for i, val in enumerate(nums):
        if val not in last_three:
            last_three[val] = deque(maxlen=3)
            
        # Append the current index
        last_three[val].append(i)
        
        # If we have collected exactly 3 indices for this value
        if len(last_three[val]) == 3:
            # Mathematical optimization:
            # For indices a < b < c, distance is:
            # abs(a - b) + abs(b - c) + abs(c - a) -> (b - a) + (c - b) + (c - a) = 2 * (c - a)
            # We only need to subtract the oldest index (index 0) from the newest (index 2)
            dist = 2 * (last_three[val][2] - last_three[val][0])
            min_distance = min(min_distance, dist)
            
    return min_distance if min_distance != math.inf else -1

if __name__ == "__main__":
    try:
        # Robust user input handling
        user_input = input("Enter the array elements separated by spaces:\n").strip()
        
        if not user_input:
            print("Array cannot be empty.")
        else:
            nums = [int(x) for x in user_input.split()]
            result = minimum_distance(nums)
            print(f"Minimum Distance: {result}")
            
    except ValueError:
        print("Invalid input. Please enter valid integers only.")