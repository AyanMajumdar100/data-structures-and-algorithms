'''
Problem Statement:
You are given a 0-indexed integer array nums and an integer pivot. Rearrange nums such that:
1. Every element less than pivot appears before every element greater than pivot.
2. Every element equal to pivot appears in between the elements less than and greater than pivot.
3. The relative order of the elements less than pivot and the elements greater than pivot is maintained.
Return nums after the rearrangement.
'''

'''
Approach: Counting and Three-Pointer Assignment
1. First, we perform a pass over the array to count how many elements are strictly less than the pivot 
   and how many are exactly equal to it.
2. With these counts, we can precalculate the starting indices for each category in our new result array:
   - Elements less than pivot start at index 0.
   - Elements equal to pivot start at index `less_count`.
   - Elements greater than pivot start at index `less_count + equal_count`.
3. We perform a second pass over the original array. For each element, we check its category, 
   insert it into its respective starting position in the result array, and increment that category's pointer.
   This guarantees stable placement and operates in linear O(N) time and auxiliary space.
'''
class PartArrPivot2161:
    def pivotArray(self, nums: list[int], pivot: int) -> list[int]:
        n = len(nums)
        result = [0] * n
        
        less_count = 0
        equal_count = 0
        
        # Pass 1: Count categories to establish clear partition segments
        for num in nums:
            if num < pivot:
                less_count += 1
            elif num == pivot:
                equal_count += 1
                
        # Establish independent starting pointer heads for each destination group
        less_index = 0
        equal_index = less_count
        greater_index = less_count + equal_count
        
        # Pass 2: Distribute elements into the result structure preserving order stability
        for num in nums:
            if num < pivot:
                result[less_index] = num
                less_index += 1
            elif num == pivot:
                result[equal_index] = num
                equal_index += 1
            else:
                result[greater_index] = num
                greater_index += 1
                
        return result

if __name__ == '__main__':
    try:
        pivot_val = int(input("Enter the pivot value: "))
        user_input = input("Enter array elements separated by space: ").strip()
        
        if not user_input:
            print("Result: []")
        else:
            nums_arr = list(map(int, user_input.split()))
            solver = PartArrPivot2161()
            res = solver.pivotArray(nums_arr, pivot_val)
            print("Rearranged array:", res)
    except ValueError:
        print("Invalid input format. Please enter integers only.")