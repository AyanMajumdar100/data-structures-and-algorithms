'''
Problem Statement: LeetCode 115 - Distinct Subsequences
Given two strings s and t, return the number of distinct subsequences of s which equals t.
The answer is guaranteed to fit in a 32-bit signed integer.
'''

'''
Approach: Dynamic Programming with 1D Space Optimization (O(M * N) Time, O(N) Space)
1. Let dp[j] be the number of distinct subsequences of the prefix of s processed so far that equal t[0...j-1].
2. Base Case:
   - dp[0] = 1, because an empty string t can always be formed in exactly 1 way.
3. Transitions:
   - For each character s[i - 1] in s:
     * Traverse j backwards from n down to 1:
       - If s[i - 1] == t[j - 1]:
         dp[j] = dp[j] + dp[j - 1]
       - If s[i - 1] != t[j - 1]:
         dp[j] remains unchanged.
   - Traversing j backwards prevents using the updated value of dp[j - 1] in the current step.
4. Result:
   - dp[n] contains the number of distinct subsequences of s that equal t.
'''

class DistinctSubsequences115:
    def numDistinct(self, s: str, t: str) -> int:
        m, n = len(s), len(t)
        if m < n:
            return 0

        # dp[j] represents ways to form t[0...j-1]
        dp = [0] * (n + 1)
        dp[0] = 1

        # Process each character of s
        for sc in s:
            for j in range(n, 0, -1):
                if sc == t[j - 1]:
                    dp[j] += dp[j - 1]

        return dp[n]

if __name__ == '__main__':
    try:
        s_param = input("Enter string s: ").strip()
        t_param = input("Enter string t: ").strip()

        python_solver = DistinctSubsequences115()
        result = python_solver.numDistinct(s_param, t_param)

        print("Number of distinct subsequences:", result)
    except ValueError:
        print("Invalid input format.")