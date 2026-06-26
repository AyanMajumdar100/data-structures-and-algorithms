/*
 * Problem Statement:
 * You are given an integer array nums and an integer target.
 * Return the number of subarrays of nums in which target is the majority element.
 * The majority element of a subarray is the element that appears strictly more than half of the times in that subarray.
 * * Constraints:
 * 1 <= nums.length <= 10^5  <-- Notice that N is up to 10^5, so O(N^2) will Time Out.
 */

/*
 * Approach: Fenwick Tree (Binary Indexed Tree) + Prefix Sum Transformation
 * 1. Map the array to a sequence of contributions:
 * - If nums[k] == target, value = +1
 * - If nums[k] != target, value = -1
 * 2. Let P[i] be the prefix sum of these values up to index i.
 * 3. The balance of a subarray nums[i..j] is calculated as: P[j] - P[i-1].
 * 4. For target to be the majority element, we need: P[j] - P[i-1] > 0  =>  P[i-1] < P[j].
 * 5. As we iterate through the array up to right bound `j`, we need to count how many previous 
 * prefix sums `P[i-1]` are strictly less than the current prefix sum `P[j]`.
 * 6. Since prefix sums can range from -N to +N, we apply an `offset = n + 1` to shift indices to 
 * positive numbers, allowing us to query and update frequencies using a Fenwick Tree (BIT) in O(log N) per element.
 * * Total Time Complexity: O(N log N)
 * Total Space Complexity: O(N)
 */
import java.util.Scanner;

public class MajoritySubarraysII3739 {
    
    // Standard BIT update operation to add frequency counters to data layers
    private void update(int[] bit, int idx, int val) {
        for (; idx < bit.length; idx += idx & -idx) {
            bit[idx] += val;
        }
    }

    // Standard BIT prefix query operation to count values occurring before index
    private int query(int[] bit, int idx) {
        int sum = 0;
        for (; idx > 0; idx -= idx & -idx) {
            sum += bit[idx];
        }
        return sum;
    }

    public long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;
        // The prefix sum ranges from -n to +n. 
        // Array size needs to accommodate indices up to 2 * n + 1 after offsetting.
        int[] bit = new int[2 * n + 2];
        int offset = n + 1; // Coordinate shift mapping to prevent negative indices
        
        long totalSubarrays = 0;
        int currentPrefixSum = 0;
        
        // Add the baseline empty prefix state P[0] = 0 into our tracking tree
        update(bit, currentPrefixSum + offset, 1);
        
        for (int num : nums) {
            // Step 1: Advance our prefix sum balance pointer
            currentPrefixSum += (num == target) ? 1 : -1;
            
            // Step 2: Query the BIT for all historical prefix sums strictly smaller than currentPrefixSum
            // (i.e., historical_prefix_sum <= currentPrefixSum - 1)
            totalSubarrays += query(bit, currentPrefixSum + offset - 1);
            
            // Step 3: Insert the current prefix sum state into the tree
            update(bit, currentPrefixSum + offset, 1);
        }
        
        return totalSubarrays;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the target element:");
        int target = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter array elements separated by space:");
        String inputLine = scanner.nextLine().trim();
        if (inputLine.isEmpty()) {
            System.out.println("Total valid subarrays: 0");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        int[] nums = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            nums[i] = Integer.parseInt(parts[i]);
        }
        
        MajoritySubarraysII3739 solver = new MajoritySubarraysII3739();
        long result = solver.countMajoritySubarrays(nums, target);
        System.out.println("Total valid subarrays: " + result);
        
        scanner.close();
    }
}
