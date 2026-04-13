'''
Problem Statement:
Given an integer array nums (0-indexed) and two integers target and start, 
find an index i such that nums[i] == target and abs(i - start) is minimized.

Return abs(i - start).
It is guaranteed that target exists in nums.

Constraints:
1 <= nums.length <= 1000
1 <= nums[i] <= 10^4
0 <= start < nums.length
target is in nums.
'''

def get_min_distance(nums: list[int], target: int, start: int) -> int:
    n = len(nums)
    i = 0
    
    # Optimize: Expand outwards from the 'start' index.
    # We check distance 'i' in both directions simultaneously.
    # The moment we find the target, we immediately return the distance 'i'.
    while True:
        # Check right bound
        if start + i < n and nums[start + i] == target:
            return i
            
        # Check left bound
        if start - i >= 0 and nums[start - i] == target:
            return i
            
        i += 1

if __name__ == "__main__":
    try:
        user_input = input("Enter the array elements separated by spaces:\n").strip()
        
        if not user_input:
            print("Array cannot be empty.")
        else:
            nums = [int(x) for x in user_input.split()]
            
            target = int(input("Enter the target element: ").strip())
            start = int(input("Enter the start index: ").strip())
            
            if start < 0 or start >= len(nums):
                print("Invalid start index. Must be within array bounds.")
            else:
                result = get_min_distance(nums, target, start)
                print(f"Minimum Distance: {result}")
                
    except ValueError:
        print("Invalid input. Please enter valid integers only.")