"""
PROBLEM STATEMENT:
You are given an integer array nums and an integer k.
Your task is to remove the minimum number of elements from nums
so that for every element in the remaining array, 
the ratio of the largest to smallest element is at most k.

Return the minimum number of removals required.

Approach:
- Sort the array and use a sliding window to find the largest valid subarray.
- Minimum removals = total elements - size of the largest valid subarray.
"""

def min_removal(nums, k):
    n = len(nums)
    
    # Sort the array to use sliding window approach
    nums.sort()
    
    max_window = 0  # Stores the maximum size of a valid subarray
    j = 0           # Right pointer of the sliding window
    
    # i is the left pointer of the sliding window
    for i in range(n):
        # Expand the window while the condition is satisfied:
        # nums[j] <= nums[i] * k
        while j < n and nums[j] <= nums[i] * k:
            j += 1
        
        # Update the maximum valid window size
        max_window = max(max_window, j - i)
    
    # Minimum removals = total elements - largest valid subarray
    return n - max_window


if __name__ == "__main__":
    # Take user input for the array
    nums = list(map(int, input("Enter the array elements separated by space: ").split()))
    
    # Take input for k
    k = int(input("Enter the value of k: "))
    
    # Call the function
    result = min_removal(nums, k)
    
    # Print the result
    print("Minimum removals needed:", result)
