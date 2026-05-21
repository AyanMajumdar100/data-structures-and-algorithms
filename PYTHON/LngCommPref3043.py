'''
Problem Statement:
You are given two arrays with positive integers arr1 and arr2.
You need to find the length of the longest common prefix between all pairs of 
integers (x, y) such that x belongs to arr1 and y belongs to arr2.
Return the length of the longest common prefix among all pairs. If no common 
prefix exists among them, return 0.
'''

'''
Approach: HashSet for Prefix Storage
We extract every possible prefix from each number in arr1 by continuously dividing 
the number by 10, and store these prefixes in a set.
Then, for each number in arr2, we also continuously divide by 10 to check if its 
prefix exists in our set. If a match is found, we calculate the number of digits 
of this common prefix and update the maximum length found so far.
'''
import math

class LngCommPref3043:
    def longestCommonPrefix(self, arr1: list[int], arr2: list[int]) -> int:
        # Set to store all prefixes from arr1
        prefixes = set()
        
        # Generate all prefixes for each number in arr1
        for num in arr1:
            while num > 0:
                prefixes.add(num)
                num //= 10 # Remove the last digit to get the next prefix
                
        max_length = 0
        
        # Check prefixes of each number in arr2 against the set
        for num in arr2:
            # Keep reducing num until it's a prefix present in the set or becomes 0
            while num > 0 and num not in prefixes:
                num //= 10
                
            # If a common prefix is found, calculate its length
            if num > 0:
                length = int(math.log10(num)) + 1
                if length > max_length:
                    max_length = length
                    
        return max_length

if __name__ == '__main__':
    try:
        # Handle user input for arr1
        user_input1 = input("Enter elements of arr1 separated by space: ")
        arr1 = list(map(int, user_input1.split()))
        
        # Handle user input for arr2
        user_input2 = input("Enter elements of arr2 separated by space: ")
        arr2 = list(map(int, user_input2.split()))
        
        solver = LngCommPref3043()
        result = solver.longestCommonPrefix(arr1, arr2)
        print("Length of longest common prefix:", result)
        
    except ValueError:
        print("Invalid input. Please enter integers only.")
