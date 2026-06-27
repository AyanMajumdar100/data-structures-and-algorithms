/*
 * Problem Statement:
 * You are given an array of positive integers nums.
 * Select a subset of nums which satisfies the following mountain-like squaring pattern:
 * [x, x^2, x^4, ..., x^(k/2), x^k, x^(k/2), ..., x^4, x^2, x]
 * Return the maximum number of elements in a subset that satisfies these conditions.
 */

/*
 * Approach: Frequency Map + Perfect Square Sequencing Simulation
 * The target sequence requires exactly 2 copies of each base element (x, x^2, x^4, ...) 
 * except for the peak element (x^k), which needs exactly 1 copy.
 * * 1. Corner Case (x = 1): 1 squared is always 1. So if we have multiple 1s, we can chain them. 
 * Since the total length of the pattern must always be odd, if we have an even count of 1s, 
 * we must drop 1 element to make the length odd: (count % 2 == 0 ? count - 1 : count).
 * * 2. General Case (x > 1):
 * - For each unique key `x`, we attempt to make it the starting base element.
 * - We chain upwards by squaring: x -> x^2 -> x^4 -> ...
 * - To continue the chain at any layer, the frequency of that element must be >= 2.
 * - The moment we hit an element with count == 1, that element can serve as the peak (x^k), and our chain terminates.
 * - If we run out of elements entirely (count == 0), the previous layer becomes the peak, meaning we overshot and must adjust our step count downward.
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MaxElementsSubset3020 {
    public int maximumLength(int[] nums) {
        // Step 1: Create a frequency map of all elements
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int maxLen = 1;

        // Step 2: Handle the special case for number 1
        if (map.containsKey(1)) {
            int count1 = map.get(1);
            // Must be an odd length
            maxLen = Math.max(maxLen, count1 % 2 == 0 ? count1 - 1 : count1);
        }

        // Step 3: Handle general bases x > 1
        for (int x : map.keySet()) {
            if (x == 1) {
                continue;
            }

            int steps = 0;
            long temp = x;

            // Keep climbing as long as we have at least 2 elements to form the left and right slopes
            while (temp <= 1000000000 && map.containsKey((int) temp) && map.get((int) temp) >= 2) {
                steps++;
                temp *= temp; // Square it for the next tier
            }

            // If the loop broke because we hit a valid element with count == 1, it becomes the peak
            if (temp <= 1000000000 && map.containsKey((int) temp)) {
                maxLen = Math.max(maxLen, 2 * steps + 1);
            } 
            // If the loop broke because the element is entirely missing, the last element with count >= 2 
            // must sacrifice one of its copies to act as the peak instead.
            else {
                maxLen = Math.max(maxLen, 2 * steps - 1);
            }
        }

        return maxLen;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String inputLine = scanner.nextLine().trim();
        
        if (inputLine.isEmpty()) {
            System.out.println("Maximum subset length: 0");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        int[] nums = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            nums[i] = Integer.parseInt(parts[i]);
        }
        
        MaxElementsSubset3020 solver = new MaxElementsSubset3020();
        int result = solver.maximumLength(nums);
        System.out.println("Maximum subset length: " + result);
        
        scanner.close();
    }
}
