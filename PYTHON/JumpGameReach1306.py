'''
Problem Statement:
Given an array of non-negative integers arr, you are initially positioned at start index of the array.
When you are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach any index with value 0.
Notice that you can not jump outside of the array at any time.
'''

'''
Approach: Depth-First Search (DFS)
We use a recursive DFS approach to explore all possible valid jumps (left and right) from the current index.
To prevent infinite loops (cycles), we mark visited indices by changing their value to -1.
If we go out of bounds or step on a visited index (value < 0), we return false.
If we land on an index with the value 0, we've found our target and return true.
'''
import sys

class JumpGameReach1306:
    def canReach(self, arr: list[int], start: int) -> bool:
        # Base case 1: Check if current index is out of bounds or already visited (marked negative)
        if start < 0 or start >= len(arr) or arr[start] < 0:
            return False
        
        # Base case 2: If we reach an index containing 0, target is found
        if arr[start] == 0:
            return True
            
        # Store the jump length for the current index
        jump = arr[start]
        
        # Mark the current index as visited by setting it to a negative value to prevent infinite loops
        arr[start] = -1
        
        # Recursively explore jumping to the right OR jumping to the left
        return self.canReach(arr, start + jump) or self.canReach(arr, start - jump)

if __name__ == '__main__':
    # Prompt user for an array input and start index
    user_input = input("Enter array elements separated by space: ")
    
    if user_input.strip():
        # Parse the string into a list of integers
        arr = list(map(int, user_input.split()))
        start = int(input("Enter start index: "))
        
        # Instantiate the solution class and execute the algorithm
        solution = JumpGameReach1306()
        print("Can reach zero?", solution.canReach(arr, start))
    else:
        print("Invalid input.")