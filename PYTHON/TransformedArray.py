# PROBLEM STATEMENT:You are given an integer array nums that represents a circular array. Your task is to create a new array result of the same size, following these rules:

# For each index i (where 0 <= i < nums.length), perform the following independent actions:
# If nums[i] > 0: Start at index i and move nums[i] steps to the right in the circular array. Set result[i] to the value of the index where you land.
# If nums[i] < 0: Start at index i and move abs(nums[i]) steps to the left in the circular array. Set result[i] to the value of the index where you land.
# If nums[i] == 0: Set result[i] to nums[i].
# Return the new array result.

# Note: Since nums is circular, moving past the last element wraps around to the beginning, and moving before the first element wraps back to the end.

class TransformedArray:
    def construct_transformed_array(self, nums):
        n = len(nums)                  # Length of the input array
        result = [0] * n               # Array to store the transformed values

        for i in range(n):
            if nums[i] == 0:
                # If the current value is 0, keep it as 0
                result[i] = 0
            else:
                # Move forward (or backward if negative) by nums[i] steps
                # Use modulo to wrap around the array (circular behavior)
                target_index = (i + nums[i]) % n

                # If modulo gives a negative index, adjust it
                if target_index < 0:
                    target_index += n

                # Set the result at i to the value at the computed index
                result[i] = nums[target_index]

        return result


# Example usage with user input
if __name__ == "__main__":
    nums = list(map(int, input("Enter the array elements separated by space: ").split()))
    transformer = TransformedArray()
    result = transformer.construct_transformed_array(nums)
    print("Transformed array:", result)
