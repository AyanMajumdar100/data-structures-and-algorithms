'''
Problem Statement:
You are given a 0-indexed binary string s and two integers minJump and maxJump. 
In the beginning, you are standing at index 0, which is equal to '0'. 
You can move from index i to index j if:
- i + minJump <= j <= min(i + maxJump, s.length - 1)
- s[j] == '0'
Return true if you can reach index s.length - 1 in s, or false otherwise.
'''

'''
Approach: Sliding Window + Dynamic Programming
We use a boolean array dp where dp[i] is True if index i is reachable.
Instead of checking all possible previous indices for each i (which causes TLE), 
we maintain a sliding window of reachable indices. The variable 'active' keeps 
track of how many reachable indices exist in the window [i - maxJump, i - minJump].
- When moving to index i, the index (i - minJump) enters the window. If it's reachable, active += 1.
- The index (i - maxJump - 1) exits the window. If it was reachable, active -= 1.
- If active > 0 and s[i] == '0', the current index i is reachable.
'''
class JumpGame7Reach1871:
    def canReach(self, s: str, minJump: int, maxJump: int) -> bool:
        n = len(s)
        # Early exit: if the last character is '1', we can never land on it
        if s[-1] == '1':
            return False
            
        # dp array to track reachable positions
        dp = [False] * n
        # We start at index 0, so it's reachable
        dp[0] = True
        
        # Tracks the number of reachable indices in the valid jump window
        active = 0
        
        for i in range(1, n):
            # A new index enters the valid jump window [i - maxJump, i - minJump]
            if i >= minJump and dp[i - minJump]:
                active += 1
                
            # An old index exits the valid jump window
            if i > maxJump and dp[i - maxJump - 1]:
                active -= 1
                
            # If there's at least one valid jump origin in the window and current spot is '0'
            if active > 0 and s[i] == '0':
                dp[i] = True
                
        # Return whether the last index is reachable
        return dp[-1]

if __name__ == '__main__':
    try:
        s = input("Enter the binary string s: ").strip()
        minJump = int(input("Enter minJump: "))
        maxJump = int(input("Enter maxJump: "))
        
        solver = JumpGame7Reach1871()
        result = solver.canReach(s, minJump, maxJump)
        
        print(f"Can reach end: {result}")
    except ValueError:
        print("Invalid input. Please enter valid integers for jumps.")
