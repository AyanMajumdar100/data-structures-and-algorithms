// PROBLEM STATEMENT:You are given an integer array nums that represents a circular array. Your task is to create a new array result of the same size, following these rules:

// For each index i (where 0 <= i < nums.length), perform the following independent actions:
// If nums[i] > 0: Start at index i and move nums[i] steps to the right in the circular array. Set result[i] to the value of the index where you land.
// If nums[i] < 0: Start at index i and move abs(nums[i]) steps to the left in the circular array. Set result[i] to the value of the index where you land.
// If nums[i] == 0: Set result[i] to nums[i].
// Return the new array result.

// Note: Since nums is circular, moving past the last element wraps around to the beginning, and moving before the first element wraps back to the end.

class transformedArray {
    public int[] constructTransformedArray(int[] nums) {
        int n = nums.length;              // Length of the input array
        int[] result = new int[n];        // Array to store the final transformed values

        for (int i = 0; i < n; i++) {

            // If the current value is 0, the result at this position is also 0
            if (nums[i] == 0) {
                result[i] = 0;
            } else {

                // Move forward (or backward if negative) by nums[i] steps
                // Using modulo to wrap around the array
                int targetIndex = (i + nums[i]) % n;

                // If modulo gives a negative index, adjust it to stay within bounds
                if (targetIndex < 0) {
                    targetIndex += n;
                }

                // Pick the value from the computed index
                result[i] = nums[targetIndex];
            }
        }

        // Return the transformed array
        return result;
    }
}
