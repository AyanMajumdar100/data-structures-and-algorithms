'''
Problem Statement:
You are given a 0-indexed array nums of n integers and an integer target.
You are initially positioned at index 0. In one step, you can jump from index i to any index j such that:
0 <= i < j < n
-target <= nums[j] - nums[i] <= target
Return the maximum number of jumps you can make to reach index n - 1.
If there is no way to reach index n - 1, return -1.

Constraints:
2 <= nums.length == n <= 1000
-10^9 <= nums[i] <= 10^9
0 <= target <= 2 * 10^9
'''

def maximum_jumps(nums: list[int], target: int) -> int:
    n = len(nums)
    
    # Initialize DP array with -1 to represent unreachable states
    dp = [-1] * n
    dp[0] = 0
    
    for i in range(n):
        # Skip evaluating jumps from an index we can't even reach
        if dp[i] == -1:
            continue
            
        # Check all possible forward jumps
        for j in range(i + 1, n):
            # Python automatically handles arbitrarily large integers, so overflow isn't an issue here
            if abs(nums[j] - nums[i]) <= target:
                dp[j] = max(dp[j], dp[i] + 1)
                
    return dp[n - 1]

if __name__ == "__main__":
    try:
        user_input = input("Enter the array elements separated by spaces:\n").strip()
        if not user_input:
            print("Array cannot be empty.")
        else:
            nums = [int(x) for x in user_input.split()]
            target = int(input("Enter the target value:\n").strip())
            
            result = maximum_jumps(nums, target)
            print(f"Maximum Jumps: {result}")
            
    except ValueError:
        print("Invalid input detected. Please ensure you enter valid integers.")