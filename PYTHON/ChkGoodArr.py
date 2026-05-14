'''
Problem Statement:
You are given an integer array nums. We consider an array good if it is a permutation of an array base[n].
base[n] = [1, 2, ..., n - 1, n, n] (contains 1 to n - 1 exactly once, plus two occurrences of n).
Return true if the given array is good, otherwise return false.
'''

'''
Approach:
An array is "good" if its length is n + 1, and it contains numbers from 1 to n - 1 exactly once, 
and the number n exactly twice.
We can determine n as len(nums) - 1.
Using a frequency array (count), we count the occurrences of each number in nums.
If any number is greater than n, it is immediately invalid.
Finally, we verify that counts for 1 to n - 1 are exactly 1, and the count for n is exactly 2.
'''
class ChkGoodArr2784:
    def isGood(self, nums: list[int]) -> bool:
        length = len(nums)
        n = length - 1
        # Frequency array to track occurrences of each number up to n
        count = [0] * length
        
        # Iterate through the array to populate the frequency array
        for num in nums:
            # If any number is strictly greater than n, it violates the base[n] structure
            if num > n:
                return False
            count[num] += 1
            
        # Verify that numbers from 1 to n - 1 appear exactly one time
        for i in range(1, n):
            if count[i] != 1:
                return False
                
        # Verify that the maximum element n appears exactly two times
        return count[n] == 2

if __name__ == '__main__':
    # Handle user input
    user_input = input("Enter array elements separated by space: ")
    nums = list(map(int, user_input.split()))
    
    solution = ChkGoodArr2784()
    result = solution.isGood(nums)
    print(f"Is the array good? {result}")