'''
Problem Statement:
Given a 0-indexed integer array nums, compute the sum of the longest sequential prefix 
(where nums[j] = nums[j-1] + 1). Return the smallest integer x >= prefixSum that is missing from nums.
'''

'''
Approach: Sequential Prefix Sum + Hash Set Lookup (O(N) Time, O(N) Space)
1. Sequential Prefix Identification:
   - Start accumulating the sum from `nums[0]`.
   - Iterate from `index = 1` onwards as long as `nums[i] == nums[i - 1] + 1` and add `nums[i]` to `prefix_sum`.
   - Stop at the first break in the sequence.
2. Hash Set Construction:
   - Store all elements of `nums` in a set for O(1) lookup time.
3. Search for Smallest Missing Integer:
   - Starting at `target_value = prefix_sum`, check if `target_value` exists in the set.
   - Increment `target_value` by 1 until finding a value not present in the set, then return it.
'''

class SmallestMissingSequentialPrefix2996:
    def missingInteger(self, nums: list[int]) -> int:
        prefix_sum = nums[0]
        
        # Step 1: Compute sum of the longest sequential prefix
        for i in range(1, len(nums)):
            if nums[i] == nums[i - 1] + 1:
                prefix_sum += nums[i]
            else:
                break
                
        # Step 2: Store elements in a hash set
        num_set = set(nums)
        
        # Step 3: Find smallest missing integer >= prefix_sum
        target_value = prefix_sum
        while target_value in num_set:
            target_value += 1
            
        return target_value

if __name__ == '__main__':
    try:
        raw_input_string = input("Enter array elements separated by space: ").strip()
        if not raw_input_string:
            print("Smallest missing integer: 0")
        else:
            user_nums_array = list(map(int, raw_input_string.split()))

            python_solver = SmallestMissingSequentialPrefix2996()
            result = python_solver.missingInteger(user_nums_array)

            print("Smallest missing integer:", result)
    except ValueError:
        print("Invalid array input format.")