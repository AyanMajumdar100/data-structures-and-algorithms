'''
Problem Statement: LeetCode 3876 - Construct Uniform Parity Array II
You are given an array nums1 of n distinct integers.
Construct an array nums2 of length n such that all elements in nums2 have uniform parity (all odd or all even).
For each index i, you may choose:
  1. nums2[i] = nums1[i]
  2. nums2[i] = nums1[i] - nums1[j] (for some j != i where nums1[i] - nums1[j] >= 1)
Return true if it is possible to construct such an array, otherwise return false.
'''

'''
Approach: Parity Subtraction Rules & Extremum Analysis (O(N) Time, O(1) Space)
1. Goal A: Make all elements Even
   - Even numbers can stay as nums1[i].
   - To turn an odd number even, we must subtract another odd number: odd - odd = even.
   - But the smallest odd number in nums1 cannot subtract any smaller odd number from itself,
     so it can NEVER be turned even. Thus, making all elements even is possible IF AND ONLY IF
     there are NO odd numbers in nums1.

2. Goal B: Make all elements Odd
   - Odd numbers can simply stay as nums1[i].
   - To turn an even number even into odd, we must subtract an odd number: even - odd = odd.
   - For an even number E to subtract an odd number O such that E - O >= 1, we must have E > O.
   - Specifically, this must hold for ALL even numbers in nums1, which is possible if and only if
     every even number is strictly greater than the minimum odd number:
     min_even > min_odd (or min_odd < min_even).

3. Synthesis:
   - If all numbers are already odd or all even, return True.
   - If mixed, we can only make everything odd, which is valid if and only if min_odd < min_even.
'''

class ConstructUniformParityArrayII3876:
    def uniformArray(self, nums1: list[int]) -> bool:
        min_odd = float('inf')
        min_even = float('inf')

        # Step 1: Find the minimum odd and minimum even values
        for num in nums1:
            if num % 2 != 0:
                if num < min_odd:
                    min_odd = num
            else:
                if num < min_even:
                    min_even = num

        # If nums1 contains only even numbers or only odd numbers, parity is already uniform
        if min_odd == float('inf') or min_even == float('inf'):
            return True

        # If mixed, all elements can be made odd iff min_odd < min_even
        return min_odd < min_even

if __name__ == '__main__':
    try:
        raw_input_string = input("Enter array elements separated by space: ").strip()
        if not raw_input_string:
            print("Result: True")
        else:
            user_nums_array = list(map(int, raw_input_string.split()))

            python_solver = ConstructUniformParityArrayII3876()
            can_construct = python_solver.uniformArray(user_nums_array)

            print("Can construct uniform parity array:", can_construct)
    except ValueError:
        print("Invalid array input format.")