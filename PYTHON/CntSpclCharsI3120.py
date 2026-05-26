'''
Problem Statement:
You are given a string word. A letter is called special if it appears both in 
lowercase and uppercase in word.
Return the number of special letters in word.
'''

'''
Approach: Bit Manipulation
We use two integer bitmasks (`lower` and `upper`) to keep track of the letters 
we have seen. Since there are only 26 English letters, integers are sufficient.
As we iterate through the characters of the string:
- If it's a lowercase letter, we set the corresponding bit (0-25) in `lower`.
- If it's an uppercase letter, we set the corresponding bit (0-25) in `upper`.
Finally, a bitwise AND (`lower & upper`) will leave bits set only for letters 
that appeared in both cases. We return the count of set bits.
'''
class CntSpclCharsI3120:
    def numberOfSpecialChars(self, word: str) -> int:
        # Bitmask for tracking lowercase characters
        lower = 0
        # Bitmask for tracking uppercase characters
        upper = 0
        
        # Iterate over each character in the given word
        for c in word:
            # If the character is lowercase
            if 'a' <= c <= 'z':
                # Set the nth bit corresponding to the letter's alphabetical index
                lower |= 1 << (ord(c) - ord('a'))
            # Otherwise, it is an uppercase letter
            else:
                # Set the nth bit in upper bitmask
                upper |= 1 << (ord(c) - ord('A'))
                
        # Perform bitwise AND to find letters that exist in both bitmasks
        # Count and return the number of set bits (special characters)
        return (lower & upper).bit_count()

if __name__ == '__main__':
    # Prompt user for input string
    word = input("Enter the word: ").strip()
    
    # Instantiate solution class and compute result
    solver = CntSpclCharsI3120()
    result = solver.numberOfSpecialChars(word)
    
    # Display result
    print("Number of special characters:", result)