'''
Problem Statement:
You are given an integer array nums of length n and an integer k.
You need to choose exactly k non-empty subarrays nums[l..r] of nums. Subarrays may overlap, 
and the exact same subarray (same l and r) can be chosen more than once.
The value of a subarray nums[l..r] is defined as: max(nums[l..r]) - min(nums[l..r]).
The total value is the sum of the values of all chosen subarrays.
Return the maximum possible total value you can achieve.
'''

'''
Approach: Greedy Optimization
The value of any subarray is bounded by the global maximum and global minimum of the entire array.
Since we are permitted to select overlapping subarrays, and the exact same subarray can be chosen 
multiple times up to k times, we can greedily find the absolute maximum and minimum values present 
across the entire array.
The optimal strategy is to select the entire array (or any subarray containing both the global maximum 
and global minimum elements) exactly k times. This maximizes the value of each of the k chosen subarrays 
to (global_max - global_min).
Thus, the maximum total value is simply: k * (global_max - global_min).
'''
class MaxSubVal3689:
    def maxTotalValue(self, nums: list[int], k: int) -> int:
        # Handle edge case where array is empty
        if not nums:
            return 0
            
        # Extract the global maximum and global minimum elements from the array
        max_val = max(nums)
        min_val = min(nums)
        
        # Multiply the maximum single subarray value by k to get the total cumulative value
        return k * (max_val - min_val)

if __name__ == '__main__':
    try:
        # Handle user input for k value
        k_val = int(input("Enter the value of k: "))
        
        # Handle user input for array elements
        user_input = input("Enter array elements separated by space: ").strip()
        
        if not user_input:
            print("Maximum total value: 0")
        else:
            nums_arr = list(map(int, user_input.split()))
            
            # Execute the algorithm and print the result
            solver = MaxSubVal3689()
            res = solver.maxTotalValue(nums_arr, k_val)
            print("Maximum total value:", res)
            
    except ValueError:
        print("Invalid input format. Please enter integers only.")