'''
Problem Statement:
Given a binary string s and an integer k, find the shortest beautiful substring containing 
exactly k ones. If there are multiple substrings of the shortest length, return the 
lexicographically smallest one. If no such substring exists, return an empty string "".
'''

'''
Approach: One-Indices Tracking / Sliding Window (O(N^2) Time, O(N) Space)
1. Collect all indices of '1' in string `s` into a list `ones`.
2. If `len(ones) < k`, return `""`.
3. Any minimal substring containing exactly k ones must start at `ones[i]` and end at `ones[i + k - 1]`.
4. Iterate over all valid pairs `(ones[i], ones[i + k - 1])`:
   - Extract candidate substring `sub = s[start:end + 1]`.
   - If `len(sub) < min_len`, update `min_len = len(sub)` and `ans = sub`.
   - If `len(sub) == min_len` and `sub < ans`, update `ans = sub`.
5. Return `ans`.
'''

class ShortestBeautifulSubstring2904:
    def shortestBeautifulSubstring(self, s: str, k: int) -> str:
        ones = [i for i, ch in enumerate(s) if ch == '1']

        if len(ones) < k:
            return ""

        ans = ""
        min_len = float('inf')

        for i in range(len(ones) - k + 1):
            start = ones[i]
            end = ones[i + k - 1]
            sub = s[start:end + 1]

            if len(sub) < min_len:
                min_len = len(sub)
                ans = sub
            elif len(sub) == min_len:
                if sub < ans:
                    ans = sub

        return ans

if __name__ == '__main__':
    try:
        s_param = input("Enter binary string s: ").strip()
        k_param = int(input("Enter integer k: ").strip())

        python_solver = ShortestBeautifulSubstring2904()
        result = python_solver.shortestBeautifulSubstring(s_param, k_param)

        print(f"Shortest and lexicographically smallest beautiful string: \"{result}\"")
    except ValueError:
        print("Invalid input format.")