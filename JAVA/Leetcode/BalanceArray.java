import java.util.Arrays;

class BalanceArray {
    public int minRemoval(int[] nums, int k) {
        int n = nums.length;

        // Sort the array so we can use a sliding window approach
        Arrays.sort(nums);

        int maxWindow = 0; // Stores the maximum size of a valid subarray
        int j = 0;         // Right pointer of the sliding window

        // i is the left pointer of the sliding window
        for (int i = 0; i < n; i++) {

            // Expand the window while the condition is satisfied:
            // nums[j] should be <= nums[i] * k
            // Long casting avoids integer overflow
            while (j < n && (long) nums[j] <= (long) nums[i] * k) {
                j++;
            }

            // Update the maximum valid window size
            maxWindow = Math.max(maxWindow, j - i);
        }

        // Minimum removals = total elements - largest valid subarray
        return n - maxWindow;
    }
}
