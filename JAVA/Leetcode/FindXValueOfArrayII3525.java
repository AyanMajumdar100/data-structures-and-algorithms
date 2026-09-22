/*
 * Problem Statement: LeetCode 3525 - Find X Value of Array II
 * Given an array `nums`, an integer `k` (1 <= k <= 5), and a list of `queries`.
 * Each query updates an element in `nums` and queries the x-value for a given range `[start, n - 1]` and target remainder `x`.
 * The x-value here represents the number of prefixes of the suffix `nums[start...n-1]` whose product leaves a remainder of `x` modulo `k`.
 */

/*
 * Approach: Segment Tree with Modular Product Merging (O((N + Q) * K) Time, O(N * K) Space)
 * 1. Segment Tree Structure:
 *    - Each tree node maintains:
 *      * `prod`: The total product of elements in the segment modulo `k`.
 *      * `remain[i]`: The frequency count of prefixes of the segment that yield remainder `i` when multiplied 
 *        by incoming prefix products from the left.
 * 2. Merge Operation (`merge(left, right)`):
 *    - Combining two adjacent segment nodes:
 *      * `res.prod = (left.prod * right.prod) % k`
 *      * Left subsegment remainders are fully inherited.
 *      * Right subsegment remainders are scaled by `left.prod` before being added.
 * 3. Point Updates & Range Queries:
 *    - Process single-point updates efficiently in $O(\log N)$ time.
 *    - Query ranges `[start, n - 1]` to retrieve the aggregated node state and read `remain[x]`.
 */

import java.util.Arrays;
import java.util.Scanner;

public class FindXValueOfArrayII3525 {
    private static class Node {
        int[] remain;
        int prod;

        Node(int k) {
            remain = new int[k];
            prod = 1;
        }
    }

    private int n, k;
    private Node[] tree;
    private int[] numsMod;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;
        this.numsMod = new int[n];
        for (int i = 0; i < n; i++) {
            numsMod[i] = nums[i] % k;
        }

        tree = new Node[4 * n];
        for (int i = 0; i < 4 * n; i++) {
            tree[i] = new Node(k);
        }

        build(0, 0, n - 1);

        int[] ans = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int index = queries[q][0];
            int value = queries[q][1] % k;
            int start = queries[q][2];
            int x = queries[q][3];

            numsMod[index] = value;
            update(0, 0, n - 1, index, value);

            Node res = query(0, 0, n - 1, start, n - 1);
            ans[q] = res.remain[x];
        }

        return ans;
    }

    private void build(int node, int left, int right) {
        if (left == right) {
            tree[node].remain[numsMod[left]] = 1;
            tree[node].prod = numsMod[left];
            return;
        }
        int mid = (left + right) / 2;
        build(2 * node + 1, left, mid);
        build(2 * node + 2, mid + 1, right);
        tree[node] = merge(tree[2 * node + 1], tree[2 * node + 2]);
    }

    private void update(int node, int lo, int hi, int idx, int val) {
        if (lo == hi) {
            Arrays.fill(tree[node].remain, 0);
            tree[node].remain[val] = 1;
            tree[node].prod = val;
            return;
        }
        int mid = (lo + hi) / 2;
        if (idx <= mid) {
            update(2 * node + 1, lo, mid, idx, val);
        } else {
            update(2 * node + 2, mid + 1, hi, idx, val);
        }
        tree[node] = merge(tree[2 * node + 1], tree[2 * node + 2]);
    }

    private Node query(int node, int lo, int hi, int i, int j) {
        if (i <= lo && hi <= j) {
            return tree[node];
        }
        int mid = (lo + hi) / 2;
        if (j <= mid) {
            return query(2 * node + 1, lo, mid, i, j);
        } else if (i > mid) {
            return query(2 * node + 2, mid + 1, hi, i, j);
        } else {
            Node leftNode = query(2 * node + 1, lo, mid, i, j);
            Node rightNode = query(2 * node + 2, mid + 1, hi, i, j);
            return merge(leftNode, rightNode);
        }
    }

    private Node merge(Node left, Node right) {
        Node res = new Node(k);
        res.prod = (left.prod * right.prod) % k;
        for (int i = 0; i < k; i++) {
            res.remain[i] = left.remain[i];
        }
        for (int i = 0; i < k; i++) {
            res.remain[(i * left.prod) % k] += right.remain[i];
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Result: []");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNums = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNums[i] = Integer.parseInt(tokens[i]);
        }

        System.out.println("Enter integer k:");
        int kParam = scanner.nextInt();

        System.out.println("Enter number of queries:");
        int numQueries = scanner.nextInt();

        int[][] queriesParam = new int[numQueries][4];
        System.out.println("Enter queries as [index, value, start, x] line by line:");
        for (int i = 0; i < numQueries; i++) {
            queriesParam[i][0] = scanner.nextInt();
            queriesParam[i][1] = scanner.nextInt();
            queriesParam[i][2] = scanner.nextInt();
            queriesParam[i][3] = scanner.nextInt();
        }

        FindXValueOfArrayII3525 solver = new FindXValueOfArrayII3525();
        int[] result = solver.resultArray(userNums, kParam, queriesParam);

        System.out.println("Query results array: " + Arrays.toString(result));
        scanner.close();
    }
}
