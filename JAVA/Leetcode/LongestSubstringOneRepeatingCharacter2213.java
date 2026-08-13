/*
 * Problem Statement:
 * You are given a 0-indexed string s, and k point-update queries specified by queryCharacters and queryIndices.
 * After each query updating s[queryIndices[i]] to queryCharacters[i], return the length of the longest 
 * contiguous substring consisting of a single repeating character.
 */

/*
 * Approach: Segment Tree for Dynamic Range Maximum Subsegment (O(N + K log N) Time, O(N) Space)
 * 1. Segment Tree Structure:
 *    Each tree node representing a range storing:
 *    - `prefChar`, `suffChar`: First and last characters of the segment.
 *    - `prefLen`: Length of the longest prefix consisting of `prefChar`.
 *    - `suffLen`: Length of the longest suffix consisting of `suffChar`.
 *    - `maxLen`: Maximum repeating character substring length fully contained within the range.
 * 2. Node Merging:
 *    When combining left child `L` and right child `R`:
 *    - `maxLen[node] = max(maxLen[L], maxLen[R])`
 *    - If `suffChar[L] == prefChar[R]`, cross-boundary candidate `suffLen[L] + prefLen[R]` is considered.
 *    - `prefLen` and `suffLen` extend into adjacent children if a child is entirely composed of a single character.
 * 3. Point Updates:
 *    Update single character in O(log N) time and query `maxLen[0]` (root node) in O(1) time after each update.
 */

import java.util.Arrays;
import java.util.Scanner;

public class LongestSubstringOneRepeatingCharacter2213 {
    private int[] prefLen, suffLen, maxLen;
    private char[] prefChar, suffChar;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        prefLen = new int[4 * n];
        suffLen = new int[4 * n];
        maxLen = new int[4 * n];
        prefChar = new char[4 * n];
        suffChar = new char[4 * n];

        build(0, 0, n - 1, s);

        int k = queryIndices.length;
        int[] answer = new int[k];
        for (int i = 0; i < k; i++) {
            update(0, 0, n - 1, queryIndices[i], queryCharacters.charAt(i));
            answer[i] = maxLen[0];
        }
        return answer;
    }

    private void build(int node, int start, int end, String s) {
        if (start == end) {
            prefLen[node] = suffLen[node] = maxLen[node] = 1;
            prefChar[node] = suffChar[node] = s.charAt(start);
            return;
        }
        int mid = start + (end - start) / 2;
        int left = 2 * node + 1;
        int right = 2 * node + 2;
        build(left, start, mid, s);
        build(right, mid + 1, end, s);
        merge(node, left, right, mid - start + 1, end - mid);
    }

    private void update(int node, int start, int end, int idx, char c) {
        if (start == end) {
            prefChar[node] = suffChar[node] = c;
            return;
        }
        int mid = start + (end - start) / 2;
        int left = 2 * node + 1;
        int right = 2 * node + 2;
        if (idx <= mid) {
            update(left, start, mid, idx, c);
        } else {
            update(right, mid + 1, end, idx, c);
        }
        merge(node, left, right, mid - start + 1, end - mid);
    }

    private void merge(int node, int left, int right, int lenLeft, int lenRight) {
        prefChar[node] = prefChar[left];
        suffChar[node] = suffChar[right];

        prefLen[node] = prefLen[left];
        if (prefLen[left] == lenLeft && prefChar[left] == prefChar[right]) {
            prefLen[node] += prefLen[right];
        }

        suffLen[node] = suffLen[right];
        if (suffLen[right] == lenRight && suffChar[right] == suffChar[left]) {
            suffLen[node] += suffLen[left];
        }

        maxLen[node] = Math.max(maxLen[left], maxLen[right]);
        if (suffChar[left] == prefChar[right]) {
            maxLen[node] = Math.max(maxLen[node], suffLen[left] + prefLen[right]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string s:");
        String sParam = scanner.nextLine().trim();

        System.out.println("Enter query characters string:");
        String queryCharactersParam = scanner.nextLine().trim();

        System.out.println("Enter query indices separated by space:");
        String rawIndices = scanner.nextLine().trim();
        String[] tokens = rawIndices.split("\\s+");
        int[] queryIndicesParam = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            queryIndicesParam[i] = Integer.parseInt(tokens[i]);
        }

        LongestSubstringOneRepeatingCharacter2213 solver = new LongestSubstringOneRepeatingCharacter2213();
        int[] result = solver.longestRepeating(sParam, queryCharactersParam, queryIndicesParam);

        System.out.println("Longest repeating substring lengths after queries: " + Arrays.toString(result));
        scanner.close();
    }
}
