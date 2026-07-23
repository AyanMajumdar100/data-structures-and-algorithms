'''
Problem Statement:
You are given an integer array nums of length n, which is a permutation of integers in the range [1, n].
A XOR triplet is defined as nums[i] XOR nums[j] XOR nums[k] where i <= j <= k.
Return the number of unique XOR triplet values possible.
'''

'''
Approach: Bitwise Power-of-Two Bound (O(1) / O(log N))
1. Base Cases:
   - For n = 1, the only element is 1. The only XOR triplet is 1 ^ 1 ^ 1 = 1. Output = 1.
   - For n = 2, possible values are 1 and 2. Triplets yield {1, 2}. Output = 2.
2. General Case (n >= 3):
   - Because nums contains all numbers from 1 to n, we can generate any bit combination 
     up to the largest power of 2 required to represent n.
   - Specifically, if `x` is the position of the Most Significant Bit (MSB) of n, 
     we can form every integer from 0 up to `(2^(x + 1) - 1)` using XOR operations of 3 elements.
   - Thus, the total count of unique XOR values is equal to the smallest power of 2 
     strictly greater than n, which is `1 << (floor(log2(n)) + 1)`.
'''

import math

class UniqueXorTriplets3513:
    def uniqueXorTriplets(self, nums: list[int]) -> int:
        array_length = len(nums)
        
        # Base cases for small array sizes where full bit combinations cannot be formed
        if array_length < 3:
            return array_length
            
        # Compute the smallest power of 2 strictly greater than n
        msb_power = int(math.log2(array_length))
        return 1 << (msb_power + 1)

if __name__ == '__main__':
    try:
        user_input_line = input("Enter permutation elements separated by space: ").strip()
        user_permutation_array = list(map(int, user_input_line.split()))
        
        python_solver_instance = UniqueXorTriplets3513()
        unique_triplets_count = python_solver_instance.uniqueXorTriplets(user_permutation_array)
        
        print("Number of unique XOR triplet values:", unique_triplets_count)
    except ValueError:
        print("Invalid array input format.")