/*
 * Problem Statement:
 * You are given two integers num1 and num2 representing an inclusive range [num1, num2].
 * The waviness of a number is defined as the total count of its peaks and valleys:
 * - A digit is a peak if it is strictly greater than both of its immediate neighbors.
 * - A digit is a valley if it is strictly less than both of its immediate neighbors.
 * - The first and last digits of a number cannot be peaks or valleys.
 * - Any number with fewer than 3 digits has a waviness of 0.
 * Return the total sum of waviness for all numbers in the range [num1, num2].
 */

/*
 * Approach: Brute Force Iteration
 * Since the upper bound constraint for num2 is relatively small (10^5), we can simulate 
 * the process for each number sequentially in the given range.
 * 1. For each number from num1 to num2, we calculate its waviness.
 * 2. To compute waviness, we extract the digits of the number into an array.
 * 3. We then iterate from the second digit to the second-to-last digit, checking 
 * whether each digit behaves as a peak or a valley compared to its neighbors.
 * 4. Sum up the counts of peaks and valleys across all numbers.
 */
import java.util.Scanner;

public class TotalWave13751 {
    public int totalWaviness(int num1, int num2) {
        int totalSum = 0;
        // Loop through all numbers in the inclusive range
        for (int i = num1; i <= num2; i++) {
            totalSum += getWaviness(i);
        }
        return totalSum;
    }

    private int getWaviness(int num) {
        // Numbers with less than 3 digits cannot contain any peak or valley
        if (num < 100) {
            return 0;
        }

        // Extract digits into an array. (10^5 fits within 6 digits)
        int[] digits = new int[10];
        int len = 0;
        while (num > 0) {
            digits[len++] = num % 10;
            num /= 10;
        }

        int waviness = 0;
        // Check middle digits (index 1 to len - 2)
        // Since we extract digits backward, neighbors remain the same relatively (left/right)
        for (int i = 1; i < len - 1; i++) {
            // Peak condition
            if (digits[i] > digits[i - 1] && digits[i] > digits[i + 1]) {
                waviness++;
            } 
            // Valley condition
            else if (digits[i] < digits[i - 1] && digits[i] < digits[i + 1]) {
                waviness++;
            }
        }
        return waviness;
    }

    public static void main(String[] args) {
        // Handle user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter num1:");
        int num1 = scanner.nextInt();
        System.out.println("Enter num2:");
        int num2 = scanner.nextInt();

        TotalWave13751 solver = new TotalWave13751();
        int result = solver.totalWaviness(num1, num2);
        System.out.println("Total waviness: " + result);

        scanner.close();
    }
}