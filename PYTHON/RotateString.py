'''
Problem Statement:
Given two strings s and goal, return true if and only if s can become goal after some number of shifts on s.
A shift on s consists of moving the leftmost character of s to the rightmost position.

Constraints:
1 <= s.length, goal.length <= 100
s and goal consist of lowercase English letters.
'''

def rotate_string(s: str, goal: str) -> bool:
    # First, ensure lengths match.
    # Then, check if goal is a substring of s concatenated with itself.
    # Python's 'in' operator is highly optimized in C and very fast.
    return len(s) == len(goal) and goal in (s + s)

if __name__ == "__main__":
    try:
        s = input("Enter the original string (s):\n").strip()
        goal = input("Enter the target string (goal):\n").strip()
        
        if not s or not goal:
            print("Strings cannot be empty.")
        else:
            result = rotate_string(s, goal)
            print(f"Can Rotate: {result}")
            
    except Exception as e:
        print("An error occurred processing your input.")