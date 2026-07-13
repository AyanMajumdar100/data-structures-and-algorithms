/*
 * Problem Statement:
 * An integer has sequential digits if and only if each digit in the number is one more than the previous digit.
 * Return a sorted list of all the integers in the range [low, high] inclusive that have sequential digits.
 */

/*
 * Approach: Sliding Window over Reference Digits String
 * 1. Since valid sequential numbers are constructed from substrings of the sequence "123456789", 
 *    the total pool of possible sequential numbers is extremely small (less than 40 numbers).
 * 2. We can systematically generate all numbers of all possible lengths (ranging from 2 digits to 9 digits).
 * 3. We use a sliding window mechanism across the reference string "123456789" for each length constraint.
 * 4. Parse each substring slice into an integer value. If the integer falls within the target 
 *    range [low, high], it gets stored inside our accumulation list.
 * 5. Since we generate patterns incrementally by window length and start position, the resulting list 
 *    is inherently sorted.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SequentialDigits1291 {
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> sequentialResultList = new ArrayList<>();
        String referenceDigits = "123456789";
        
        // Step 1: Loop through all possible lengths of sequential digits (from 2 to 9 digits long)
        for (int windowLength = 2; windowLength <= 9; windowLength++) {
            // Step 2: Slide the window across the "123456789" reference template
            for (int startPointer = 0; startPointer <= 9 - windowLength; startPointer++) {
                String dynamicSubstring = referenceDigits.substring(startPointer, startPointer + windowLength);
                int parsedSequentialNum = Integer.parseInt(dynamicSubstring);
                
                // Step 3: Verify if the parsed pattern falls within the bounded range requirements
                if (parsedSequentialNum >= low && parsedSequentialNum <= high) {
                    sequentialResultList.add(parsedSequentialNum);
                }
            }
        }
        
        return sequentialResultList;
    }

    public static void main(String[] args) {
        // Step 4: Handle standard scanner inputs using descriptive parameter variable tags
        Scanner sequenceConsoleScanner = new Scanner(System.in);
        System.out.println("Enter the lower bound limit (low):");
        int uniqueLowBound = sequenceConsoleScanner.nextInt();
        
        System.out.println("Enter the upper bound limit (high):");
        int uniqueHighBound = sequenceConsoleScanner.nextInt();
        
        // Step 5: Initialize the solver object and output matching collection records
        SequentialDigits1291 uniqueSolver = new SequentialDigits1291();
        List<Integer> computedSequences = uniqueSolver.sequentialDigits(uniqueLowBound, uniqueHighBound);
        
        System.out.println("Sequential numbers found: " + computedSequences);
        sequenceConsoleScanner.close();
    }
}
