"""
Problem: 1358. Number of Substrings Containing All Three Characters

Problem Statement:
Given a string s consisting only of 'a', 'b', and 'c',
return the number of substrings that contain at least one
occurrence of all three characters.

Example:
Input:  "aaacb"
Output: 3

Approach (Sliding Window):
- Use two pointers (left and right).
- Expand window using right pointer.
- Keep track of counts of 'a', 'b', and 'c'.
- When all three are present:
    → Add (n - right) to result.
    → Move left pointer to shrink window.

Time Complexity: O(n)
Space Complexity: O(1)

Commit Message:
feat: sliding window solution for counting substrings with a, b, c (java & python)
"""

class SubstringCounterUniquePython:

    @staticmethod
    def count_substrings(s):
        n = len(s)

        # count[0] -> 'a', count[1] -> 'b', count[2] -> 'c'
        count = [0, 0, 0]

        left = 0
        result = 0

        # Move right pointer
        for right in range(n):

            # Increase count of current character
            count[ord(s[right]) - ord('a')] += 1

            # Check if all 3 characters exist in window
            while count[0] > 0 and count[1] > 0 and count[2] > 0:

                # Add all valid substrings from this window
                result += (n - right)

                # Shrink window from left
                count[ord(s[left]) - ord('a')] -= 1
                left += 1

        return result


# Taking user input
s = input("Enter the string: ")

# Calling function and printing result
print("Number of valid substrings:", SubstringCounterUniquePython.count_substrings(s))
