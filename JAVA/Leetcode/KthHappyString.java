/*
1415. The k-th Lexicographical String of All Happy Strings of Length n

Problem Statement:
A happy string is a string that:

1. Contains only characters from the set ['a', 'b', 'c'].
2. No two adjacent characters are the same (s[i] != s[i+1]).

Given integers n and k, return the k-th happy string of length n
in lexicographical order.

If fewer than k happy strings exist, return "".

Examples:

Example 1
Input:
n = 1
k = 3
Output:
"c"

Explanation:
All happy strings of length 1 = ["a","b","c"]
The 3rd string is "c".

Example 2
Input:
n = 1
k = 4
Output:
""

Explanation:
Only 3 happy strings exist.

Example 3
Input:
n = 3
k = 9
Output:
"cab"

All happy strings of length 3:
["aba","abc","aca","acb","bab","bac","bca","bcb","cab","cac","cba","cbc"]

The 9th string is "cab".

Constraints:
1 <= n <= 10
1 <= k <= 100

Approach:
Total happy strings = 3 * 2^(n-1)

Reason:
First position → 3 choices (a,b,c)
Every next position → 2 choices (anything except previous character)

Instead of generating all strings, we directly compute the k-th string
using block sizes.

Time Complexity: O(n)
Space Complexity: O(n)
*/

import java.util.Scanner;

public class KthHappyString {

    public static String getHappyString(int n, int k) {

        int total = 3 * (1 << (n - 1));

        // If k exceeds total possible happy strings
        if (k > total) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        // Convert to 0-based index
        k--;

        int blockSize = 1 << (n - 1);

        int firstCharIdx = k / blockSize;

        char prev = (char) ('a' + firstCharIdx);

        sb.append(prev);

        k %= blockSize;

        // Fill remaining characters
        for (int i = 1; i < n; i++) {

            blockSize = 1 << (n - 1 - i);

            int choiceIdx = k / blockSize;

            char choice1 = prev == 'a' ? 'b' : 'a';
            char choice2 = prev == 'c' ? 'b' : 'c';

            char nextChar = choiceIdx == 0 ? choice1 : choice2;

            sb.append(nextChar);

            prev = nextChar;

            k %= blockSize;
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter n (length of string): ");
        int n = sc.nextInt();

        System.out.print("Enter k: ");
        int k = sc.nextInt();

        String result = getHappyString(n, k);

        System.out.println("K-th Happy String: " + result);

        sc.close();
    }
}
