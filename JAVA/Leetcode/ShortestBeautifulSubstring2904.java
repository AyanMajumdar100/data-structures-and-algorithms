/*
 * Problem Statement:
 * Given a binary string s and an integer k, find the shortest beautiful substring containing 
 * exactly k ones. If there are multiple substrings of the shortest length, return the 
 * lexicographically smallest one. If no such substring exists, return an empty string "".
 */

/*
 * Approach: One-Indices Tracking / Sliding Window (O(N^2) Time, O(N) Space)
 * 1. Collect all indices of '1' in string `s` into a list `ones`.
 * 2. If `ones.size() < k`, it's impossible to form a beautiful substring, return `""`.
 * 3. Any minimal substring containing exactly k ones must start at `ones[i]` and end at `ones[i + k - 1]`.
 *    Adding surrounding zeros only increases length and makes the substring lexicographically larger.
 * 4. Iterate over all valid pairs `(ones[i], ones[i + k - 1])`:
 *    - Extract candidate substring `sub = s.substring(start, end + 1)`.
 *    - If `sub.length() < minLen`, update `minLen = sub.length()` and `ans = sub`.
 *    - If `sub.length() == minLen` and `sub.compareTo(ans) < 0`, update `ans = sub`.
 * 5. Return `ans`.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShortestBeautifulSubstring2904 {
    public String shortestBeautifulSubstring(String s, int k) {
        List<Integer> ones = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                ones.add(i);
            }
        }

        if (ones.size() < k) {
            return "";
        }

        String ans = "";
        int minLen = Integer.MAX_VALUE;

        for (int i = 0; i <= ones.size() - k; i++) {
            int start = ones.get(i);
            int end = ones.get(i + k - 1);
            String sub = s.substring(start, end + 1);

            if (sub.length() < minLen) {
                minLen = sub.length();
                ans = sub;
            } else if (sub.length() == minLen) {
                if (sub.compareTo(ans) < 0) {
                    ans = sub;
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter binary string s:");
        String sParam = scanner.nextLine().trim();

        System.out.println("Enter integer k:");
        int kParam = scanner.nextInt();

        ShortestBeautifulSubstring2904 solver = new ShortestBeautifulSubstring2904();
        String result = solver.shortestBeautifulSubstring(sParam, kParam);

        System.out.println("Shortest and lexicographically smallest beautiful string: \"" + result + "\"");
        scanner.close();
    }
}