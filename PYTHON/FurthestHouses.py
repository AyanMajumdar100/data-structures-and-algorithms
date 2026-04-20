'''
Problem Statement:
There are n houses evenly lined up on the street, and each house is beautifully painted. 
You are given a 0-indexed integer array colors of length n, where colors[i] represents the color of the ith house.

Return the maximum distance between two houses with different colors.
The distance between the ith and jth houses is abs(i - j).

Constraints:
n == colors.length
2 <= n <= 100
0 <= colors[i] <= 100
Test data are generated such that at least two houses have different colors.
'''

def max_distance(colors: list[int]) -> int:
    n = len(colors)
    res = 0
    
    # Scenario 1: Fix the first house, find the furthest different color from the right
    # Step backwards from the last element to the first
    for i in range(n - 1, -1, -1):
        if colors[i] != colors[0]:
            res = max(res, i)
            break # Break early since it's the maximum possible distance involving index 0
            
    # Scenario 2: Fix the last house, find the furthest different color from the left
    # Step forwards from the first element
    for i in range(n):
        if colors[i] != colors[-1]:
            res = max(res, n - 1 - i)
            break # Break early since it's the maximum possible distance involving index n-1
            
    return res

if __name__ == "__main__":
    try:
        user_input = input("Enter the colors of the houses separated by spaces:\n").strip()
        
        if not user_input:
            print("Array cannot be empty.")
        else:
            colors = [int(x) for x in user_input.split()]
            if len(colors) < 2:
                print("At least two houses are required.")
            else:
                result = max_distance(colors)
                print(f"Maximum Distance: {result}")
                
    except ValueError:
        print("Invalid input. Please enter valid integers only.")