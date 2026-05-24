'''
Problem Statement:
Given an array of integers arr and an integer d. In one step you can jump from index i to index:
- i + x where: i + x < arr.length and  0 < x <= d.
- i - x where: i - x >= 0 and  0 < x <= d.
In addition, you can only jump from index i to index j if arr[i] > arr[j] and arr[i] > arr[k] 
for all indices k between i and j.
You can choose any index of the array and start jumping. Return the maximum number of indices you can visit.
Notice that you can not jump outside of the array at any time.
'''

'''
Approach: Depth-First Search (DFS) with Memoization (Dynamic Programming)
We want to find the longest path in a directed acyclic graph (DAG) where edges represent valid jumps.
We use an array `dp` to memoize the maximum jumps starting from each index to avoid redundant calculations.
For each index `i`, we explore jumping to the right (up to `d` steps) and to the left (up to `d` steps).
We stop exploring a direction as soon as we encounter a building (element) that is taller or equal 
to the current building `arr[i]`, as it blocks further jumps in that direction.
The overall answer is the maximum value computed across all possible starting indices.
'''
import sys

# Increase recursion depth just in case of large arrays and maximum linear jumps
sys.setrecursionlimit(20000)

class JumpGame5MaxV1340:
    def maxJumps(self, arr: list[int], d: int) -> int:
        n = len(arr)
        # dp array to store the maximum jumps possible starting from each index
        dp = [0] * n
        max_total_jumps = 0
        
        # Try starting the jump sequence from every possible index
        for i in range(n):
            jumps = self.dfs(arr, i, d, n, dp)
            # Update the global maximum jumps found so far
            if jumps > max_total_jumps:
                max_total_jumps = jumps
                
        return max_total_jumps
        
    def dfs(self, arr: list[int], i: int, d: int, n: int, dp: list[int]) -> int:
        # If the result for this index is already computed, return it directly (memoization)
        if dp[i] != 0:
            return dp[i]
            
        # Base case: we can always visit the starting index itself (1 index visited)
        res = 1
        
        # Explore jumps to the right
        # We can jump up to 'd' steps, but must stay within array bounds
        for j in range(i + 1, min(i + d + 1, n)):
            # A jump is blocked if the target building is greater than or equal to the current
            if arr[j] >= arr[i]:
                break
                
            # Recursively calculate jumps from the valid target index
            jumps = 1 + self.dfs(arr, j, d, n, dp)
            if jumps > res:
                res = jumps
                
        # Explore jumps to the left
        # We can jump up to 'd' steps backwards, but must not go below index 0
        for j in range(i - 1, max(i - d - 1, -1), -1):
            # A jump is blocked if the target building is greater than or equal to the current
            if arr[j] >= arr[i]:
                break
                
            # Recursively calculate jumps from the valid target index
            jumps = 1 + self.dfs(arr, j, d, n, dp)
            if jumps > res:
                res = jumps
                
        # Save the computed maximum jumps for index i into the dp array
        dp[i] = res
        return res

if __name__ == '__main__':
    try:
        # Take user input for the array elements
        user_input = input("Enter the array elements separated by space: ").strip()
        arr = list(map(int, user_input.split()))
        
        # Take user input for max jump distance d
        d = int(input("Enter max jump distance d: "))
        
        # Execute the algorithm and print result
        solver = JumpGame5MaxV1340()
        result = solver.maxJumps(arr, d)
        print("Maximum jumps:", result)
        
    except ValueError:
        print("Invalid input. Please enter valid integers.")