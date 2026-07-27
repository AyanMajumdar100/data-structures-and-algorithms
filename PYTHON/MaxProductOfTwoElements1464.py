'''
Problem Statement:
Given the array of integers nums, choose two different indices i and j.
Return the maximum value of (nums[i] - 1) * (nums[j] - 1).
'''

'''
Approach: Single-Pass Top Two Maxima Tracking (O(N) Time, O(1) Space)
1. Since all elements nums[i] >= 1, maximizing (nums[i] - 1) * (nums[j] - 1) requires finding 
   the two largest numbers in the array.
2. Traverse `nums` linearly while keeping track of `max1` (largest) and `max2` (second largest).
   - If `num > max1`: Shift `max1` to `max2`, then update `max1 = num`.
   - Else if `num > max2`: Update `max2 = num`.
3. Return `(max1 - 1) * (max2 - 1)`.
'''

class MaxProductOfTwoElements1464:
    def maxProduct(self, nums: list[int]) -> int:
        max1 = 0
        max2 = 0

        # Traverse array linearly to extract top two largest values
        for num in nums:
            if num > max1:
                max2 = max1
                max1 = num
            elif num > max2:
                max2 = num

        return (max1 - 1) * (max2 - 1)

if __name__ == '__main__':
    try:
        raw_input_string = input("Enter array elements separated by space: ").strip()
        if not raw_input_string:
            print("Maximum product: 0")
        else:
            user_nums_array = list(map(int, raw_input_string.split()))

            python_solver = MaxProductOfTwoElements1464()
            maximum_product = python_solver.maxProduct(user_nums_array)

            print("Maximum value of (nums[i]-1)*(nums[j]-1):", maximum_product)
    except ValueError:
        print("Invalid array input format.")