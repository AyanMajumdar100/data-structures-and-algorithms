'''
Problem Statement:
Given two integer arrays nums1 and nums2, sorted in non-decreasing order, 
return the minimum integer common to both arrays. If there is no common integer 
amongst nums1 and nums2, return -1.

Note that an integer is said to be common to nums1 and nums2 if both arrays 
have at least one occurrence of that integer.
'''

'''
Approach: Two Pointers
Since both arrays are already sorted in non-decreasing order, we can use two 
pointers starting at the beginning of each array. We compare the elements at 
both pointers. If they are equal, we found the minimum common value. If the 
element in nums1 is smaller, we increment the pointer for nums1. Otherwise, 
we increment the pointer for nums2. We repeat this until we find a match or 
exhaust either array.
'''
class MinCommonVal2540:
    def getCommon(self, nums1: list[int], nums2: list[int]) -> int:
        # Initialize pointers for both sorted arrays
        i, j = 0, 0
        n, m = len(nums1), len(nums2)
        
        # Traverse through both arrays until one is fully processed
        while i < n and j < m:
            if nums1[i] == nums2[j]:
                # Both pointers point to the same value, returning the minimum common value
                return nums1[i]
            elif nums1[i] < nums2[j]:
                # nums1 has a smaller value, increment its pointer to catch up to nums2
                i += 1
            else:
                # nums2 has a smaller value, increment its pointer to catch up to nums1
                j += 1
                
        # If traversal finishes without returning, no common integers exist
        return -1

if __name__ == '__main__':
    try:
        # Handle user input for the first array
        user_input1 = input("Enter first array elements separated by space: ")
        nums1 = list(map(int, user_input1.split()))
        
        # Handle user input for the second array
        user_input2 = input("Enter second array elements separated by space: ")
        nums2 = list(map(int, user_input2.split()))
        
        solution = MinCommonVal2540()
        result = solution.getCommon(nums1, nums2)
        print("Minimum common value:", result)
        
    except ValueError:
        print("Invalid input. Please enter integers only.")