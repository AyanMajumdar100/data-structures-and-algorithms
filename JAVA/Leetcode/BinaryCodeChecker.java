/*
1461. Check If a String Contains All Binary Codes of Size K

Given a binary string s and an integer k,
return true if every binary code of length k exists as a substring of s.

Total possible binary codes of length k = 2^k.

Examples:
Input: s = "00110110", k = 2 → Output: true
Input: s = "0110", k = 1 → Output: true
Input: s = "0110", k = 2 → Output: false

Constraints:
1 <= s.length <= 5 * 10^5
s contains only '0' and '1'
1 <= k <= 20
*/

import java.util.*;

class BinaryCodeChecker {

    public boolean hasAllCodes(String s, int k) {

        int n = s.length();

        // Total number of binary codes of size k
        int totalCodes = 1 << k;

        // If string is too short, impossible to contain all codes
        if (n < totalCodes + k - 1) {
            return false;
        }

        boolean[] seen = new boolean[totalCodes];
        int count = 0;

        int hash = 0;
        int mask = totalCodes - 1;

        byte[] bytes = s.getBytes();

        for (int i = 0; i < n; i++) {

            // I shift left and add new bit → rolling hash of k bits
            hash = ((hash << 1) & mask) | (bytes[i] - '0');

            // Once window size reaches k, I check if code is new
            if (i >= k - 1 && !seen[hash]) {
                seen[hash] = true;
                count++;

                // If all possible codes are found → done
                if (count == totalCodes) {
                    return true;
                }
            }
        }

        return false;
    }

    // ---------- USER INPUT DRIVER ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter binary string: ");
        String s = sc.nextLine();

        System.out.print("Enter k: ");
        int k = sc.nextInt();

        BinaryCodeChecker solver = new BinaryCodeChecker();
        boolean result = solver.hasAllCodes(s, k);

        System.out.println("Contains all binary codes of size k: " + result);

        sc.close();
    }
}