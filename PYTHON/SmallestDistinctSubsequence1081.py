'''
Problem Statement:
Given a string s, return the lexicographically smallest subsequence of s 
that contains all the distinct characters of s exactly once.
'''

'''
Approach: Monotonic Greedy Stack Optimization (O(N) Time, O(1) Space)
1. Find the final index position of each character in the string `s` using a dictionary mapping.
2. Maintain a dynamic stack via a Python list to collect the chosen character result sequence.
3. Use a set tracker `seen` to quickly prevent duplicate characters from entering our collection.
4. Scan the string linearly:
   - If the current character `ch` is already in our stack, skip it.
   - While the top element of our stack is lexicographically LARGER than `ch`, AND that top character 
     appears again later in the string (`last_occurrence[top] > i`), pop the top character off the stack 
     and remove it from `seen`. This guarantees the smallest dictionary order.
   - Append the current character to the stack and mark it as present.
'''

class SmallestDistinctSubsequence1081:
    def smallestSubsequence(self, s: str) -> str:
        # Step 1: Precompute the last index where each character appears in the string s
        last_occurrence = {char: idx for idx, char in enumerate(s)}
        
        result_stack = []
        seen_characters_set = set()
        
        # Step 2: Traverse the string character by character
        for idx, ch in enumerate(s):
            # Skip if the character is already picked in the stack
            if ch in seen_characters_set:
                continue
                
            # Step 3: Pop elements if they are larger than the current char and appear later
            while result_stack and result_stack[-1] > ch and last_occurrence[result_stack[-1]] > idx:
                popped_char = result_stack.pop()
                seen_characters_set.remove(popped_char)
                
            # Step 4: Push the current character into our monotonic stack frame
            result_stack.append(ch)
            seen_characters_set.add(ch)
            
        return "".join(result_stack)

if __name__ == '__main__':
    try:
        # Step 5: Handle user inputs cleanly using clear variables
        user_string_param = input("Enter string s consisting of lowercase letters: ").strip()
        
        # Step 6: Execute string optimization sequence
        python_solver_instance = SmallestDistinctSubsequence1081()
        unique_sequence_output = python_solver_instance.smallestSubsequence(user_string_param)
        print("Lexicographically smallest subsequence:", unique_sequence_output)
    except ValueError:
        print("Invalid character string format entered.")