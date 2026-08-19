/*
 * Problem Statement:
 * A cinema has n rows, each with 10 seats (1 to 10).
 * Four-person groups can sit in [2,3,4,5], [6,7,8,9], or [4,5,6,7].
 * Given 2D array reservedSeats, return the maximum number of four-person groups that can be seated.
 */

/*
 * Approach: Bitmask Hash Map (O(R) Time, O(R) Space where R = reservedSeats.length)
 * 1. Filter out seats 1 and 10 since they never belong to any 4-person block.
 * 2. Group seat reservations by row using an 8-bit integer mask for seats 2 through 9.
 * 3. Base count: Unreserved rows hold 2 families each -> (n - map.size()) * 2.
 * 4. Check each reserved row:
 *    - Left & Right both free -> +2
 *    - Left OR Right OR Middle free -> +1
 *    - None free -> +0
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CinemaSeatAllocation1386 {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> rowBitmasks = new HashMap<>();

        // Step 1: Record reserved seats in bits 0..7 for columns 2..9
        for (int[] reservation : reservedSeats) {
            int row = reservation[0];
            int col = reservation[1];
            if (col >= 2 && col <= 9) {
                rowBitmasks.put(row, rowBitmasks.getOrDefault(row, 0) | (1 << (col - 2)));
            }
        }

        // Step 2: Unaffected rows can seat 2 families each
        int totalFamilies = (n - rowBitmasks.size()) * 2;

        // Step 3: Evaluate rows with reservations
        for (int mask : rowBitmasks.values()) {
            boolean leftFree = (mask & 15) == 0;    // Bits 0..3 (Seats 2..5)
            boolean rightFree = (mask & 240) == 0;  // Bits 4..7 (Seats 6..9)
            boolean middleFree = (mask & 60) == 0;  // Bits 2..5 (Seats 4..7)

            if (leftFree && rightFree) {
                totalFamilies += 2;
            } else if (leftFree || rightFree || middleFree) {
                totalFamilies += 1;
            }
        }

        return totalFamilies;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of rows (n):");
        int nParam = scanner.nextInt();

        System.out.println("Enter number of reserved seats:");
        int numReservations = scanner.nextInt();
        int[][] reservedSeatsParam = new int[numReservations][2];

        System.out.println("Enter reserved seats (row col) line by line:");
        for (int i = 0; i < numReservations; i++) {
            reservedSeatsParam[i][0] = scanner.nextInt();
            reservedSeatsParam[i][1] = scanner.nextInt();
        }

        CinemaSeatAllocation1386 solver = new CinemaSeatAllocation1386();
        int result = solver.maxNumberOfFamilies(nParam, reservedSeatsParam);

        System.out.println("Maximum number of four-person groups: " + result);
        scanner.close();
    }
}
