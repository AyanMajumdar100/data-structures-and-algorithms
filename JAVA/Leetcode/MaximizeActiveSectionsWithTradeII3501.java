/*
 * Problem Statement:
 * You are given a binary string s of length n and a 2D array queries where queries[i] = [li, ri].
 * For each query, consider the substring s[li...ri] augmented with '1' at both ends.
 * Determine the maximum possible number of active sections ('1's) in the entire string s after 
 * making the optimal trade on the substring s[li...ri].
 */

/*
 * Approach: Group Compression + Range Maximum Query via Sparse Table (O(N log N) precomputation, O(1) per Query)
 * 1. Problem Reduction:
 *    A trade on a substring converts a block of '1's surrounded by '0's to '0's, merging two adjacent 0-groups,
 *    and then converts the merged '0's to '1's. The net gain is the combined lengths of the two adjacent '0' groups.
 * 2. Precomputation:
 *    - Count the total number of '1's (`totalOnesCount`) in the entire string s.
 *    - Compress all contiguous blocks of '0's into `zeroGroups` containing their starting index and length.
 *    - Create an array `zeroGroupIndex` mapping each index in s to its corresponding 0-group index (-1 for '1's).
 *    - Construct `mergeLengths`, where `mergeLengths[i]` stores the sum of lengths of `zeroGroups[i]` and `zeroGroups[i + 1]`.
 *    - Build a Sparse Table over `mergeLengths` to perform O(1) range maximum queries.
 * 3. Answering Queries [l, r]:
 *    - Truncate partial 0-groups at the left boundary $l$ and right boundary $r$.
 *    - Query the Sparse Table for fully contained 0-group pairs strictly within the window.
 *    - Account for boundary combinations (partial left 0-group + full next 0-group, or partial right 0-group + full prev 0-group).
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaximizeActiveSectionsWithTradeII3501 {

    private static class Group {
        int start;
        int length;

        Group(int start, int length) {
            this.start = start;
            this.length = length;
        }
    }

    // Sparse Table for O(1) Range Maximum Query (RMQ)
    private static class SparseTable {
        private final int[][] st;

        public SparseTable(int[] nums) {
            int n = nums.length;
            if (n == 0) {
                st = new int[1][0];
                return;
            }
            int log = 32 - Integer.numberOfLeadingZeros(n);
            st = new int[log + 1][n];
            System.arraycopy(nums, 0, st[0], 0, n);
            for (int i = 1; i <= log; i++) {
                for (int j = 0; j + (1 << i) <= n; j++) {
                    st[i][j] = Math.max(st[i - 1][j], st[i - 1][j + (1 << (i - 1))]);
                }
            }
        }

        public int query(int l, int r) {
            if (l > r) return 0;
            int i = 32 - Integer.numberOfLeadingZeros(r - l + 1) - 1;
            return Math.max(st[i][l], st[i][r - (1 << i) + 1]);
        }
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int totalOnesCount = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                totalOnesCount++;
            }
        }

        List<Group> zeroGroups = new ArrayList<>();
        int[] zeroGroupIndex = new int[n];

        // Step 1: Compress contiguous '0's into zero groups
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                if (i > 0 && s.charAt(i - 1) == '0') {
                    zeroGroups.get(zeroGroups.size() - 1).length++;
                } else {
                    zeroGroups.add(new Group(i, 1));
                }
                zeroGroupIndex[i] = zeroGroups.size() - 1;
            } else {
                zeroGroupIndex[i] = -1;
            }
        }

        int m = zeroGroups.size();
        int[] mergeLengths = new int[Math.max(0, m - 1)];
        for (int i = 0; i < m - 1; i++) {
            mergeLengths[i] = zeroGroups.get(i).length + zeroGroups.get(i + 1).length;
        }

        // Step 2: Build Sparse Table over adjacent zero group length sums
        SparseTable st = new SparseTable(mergeLengths);
        List<Integer> queryAnswers = new ArrayList<>(queries.length);

        // Step 3: Process each range query [l, r]
        for (int[] q : queries) {
            int l = q[0];
            int r = q[1];

            int activeSections = totalOnesCount;

            int gL = (s.charAt(l) == '0') ? zeroGroupIndex[l] : getFirstGroupIndexAfter(zeroGroups, l);
            int gR = (s.charAt(r) == '0') ? zeroGroupIndex[r] : getLastGroupIndexBefore(zeroGroups, r);

            if (gL == -1 || gR == -1 || gL > gR) {
                queryAnswers.add(activeSections);
                continue;
            }

            int leftPartialLength = 0;
            if (s.charAt(l) == '0') {
                Group g = zeroGroups.get(zeroGroupIndex[l]);
                leftPartialLength = g.length - (l - g.start);
            }

            int rightPartialLength = 0;
            if (s.charAt(r) == '0') {
                Group g = zeroGroups.get(zeroGroupIndex[r]);
                rightPartialLength = r - g.start + 1;
            }

            if (gL == gR) {
                queryAnswers.add(activeSections);
                continue;
            }

            if (gL + 1 == gR) {
                int lenLeft = (s.charAt(l) == '0') ? leftPartialLength : zeroGroups.get(gL).length;
                int lenRight = (s.charAt(r) == '0') ? rightPartialLength : zeroGroups.get(gR).length;
                activeSections = Math.max(activeSections, totalOnesCount + lenLeft + lenRight);
            } else {
                int startAdjIndex = (s.charAt(l) == '0') ? gL + 1 : gL;
                int endAdjIndex = (s.charAt(r) == '0') ? gR - 2 : gR - 1;

                if (startAdjIndex <= endAdjIndex) {
                    activeSections = Math.max(activeSections, totalOnesCount + st.query(startAdjIndex, endAdjIndex));
                }

                if (s.charAt(l) == '0') {
                    int nextGroupLen = zeroGroups.get(gL + 1).length;
                    activeSections = Math.max(activeSections, totalOnesCount + leftPartialLength + nextGroupLen);
                }

                if (s.charAt(r) == '0') {
                    int prevGroupLen = zeroGroups.get(gR - 1).length;
                    activeSections = Math.max(activeSections, totalOnesCount + rightPartialLength + prevGroupLen);
                }
            }

            queryAnswers.add(activeSections);
        }

        return queryAnswers;
    }

    private int getFirstGroupIndexAfter(List<Group> zeroGroups, int idx) {
        int low = 0, high = zeroGroups.size() - 1;
        int res = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (zeroGroups.get(mid).start >= idx) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    private int getLastGroupIndexBefore(List<Group> zeroGroups, int idx) {
        int low = 0, high = zeroGroups.size() - 1;
        int res = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (zeroGroups.get(mid).start <= idx) {
                res = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner queryConsoleScanner = new Scanner(System.in);
        System.out.println("Enter binary string s:");
        String userBinaryStringParam = queryConsoleScanner.nextLine().trim();

        System.out.println("Enter number of queries:");
        int numQueries = queryConsoleScanner.nextInt();
        int[][] userQueriesParam = new int[numQueries][2];

        System.out.println("Enter query bounds (l r) line by line:");
        for (int i = 0; i < numQueries; i++) {
            userQueriesParam[i][0] = queryConsoleScanner.nextInt();
            userQueriesParam[i][1] = queryConsoleScanner.nextInt();
        }

        MaximizeActiveSectionsWithTradeII3501 querySolver = new MaximizeActiveSectionsWithTradeII3501();
        List<Integer> finalResults = querySolver.maxActiveSectionsAfterTrade(userBinaryStringParam, userQueriesParam);

        System.out.println("Query Results: " + finalResults);
        queryConsoleScanner.close();
    }
}
