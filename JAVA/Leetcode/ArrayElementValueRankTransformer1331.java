/*
 * Problem Statement:
 * Given an array of integers arr, replace each element with its rank.
 * Rank rules: Starts from 1, larger elements get larger ranks, equal elements share the same rank.
 */

/*
 * Approach: Sorting + Hash Map Ranking Sequence
 * 1. Clone the input array and sort the clone to discover the relative ordering of elements.
 * 2. Walk through the sorted array to assign sequential ranks using a Hash Map. 
 *    Skip values already present to guarantee duplicate values capture the same initial rank.
 * 3. Rewrite the original array elements with their mapped rank numbers.
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArrayElementValueRankTransformer1331 {
    public int[] arrayRankTransform(int[] arr) {
        // Step 1: Clone and sort the original array to find global value positions
        int[] sortedCopy = arr.clone();
        Arrays.sort(sortedCopy);
        
        // Step 2: Sequentially map each unique value to an incremental rank counter
        Map<Integer, Integer> valueToRankMap = new HashMap<>();
        int currentRankTracker = 1;
        for (int distinctNum : sortedCopy) {
            if (!valueToRankMap.containsKey(distinctNum)) {
                valueToRankMap.put(distinctNum, currentRankTracker++);
            }
        }
        
        // Step 3: Replace original array elements with their associated rank mappings
        for (int i = 0; i < arr.length; i++) {
            arr[i] = valueToRankMap.get(arr[i]);
        }
        
        return arr;
    }
}