/*
 * Problem Statement: LeetCode 2265 - Count Nodes Equal to Average of Subtree
 * Given the root of a binary tree, return the number of nodes where the value of the node
 * is equal to the average of the values in its subtree (rounded down to the nearest integer).
 */

/*
 * Approach: Post-Order DFS Traversal (O(N) Time, O(H) Space where H is tree height)
 * 1. A subtree's average requires:
 *    - The sum of all node values in that subtree.
 *    - The total number of nodes in that subtree.
 * 2. Recursively traverse the tree in post-order (left, right, root):
 *    - Base case: `node == null` returns `{0, 0}` (sum = 0, count = 0).
 *    - Recursive step:
 *      * Query the left subtree: `[leftSum, leftCount]`.
 *      * Query the right subtree: `[rightSum, rightCount]`.
 *      * Compute current subtree properties:
 *        `currentSum = leftSum + rightSum + node.val`
 *        `currentCount = leftCount + rightCount + 1`
 *    - Condition check:
 *      * If `currentSum / currentCount == node.val`, increment the match counter.
 *    - Return `new int[]{currentSum, currentCount}` to the parent caller.
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CountNodesEqualToAverageOfSubtree2265 {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private int matchingNodesCount = 0;

    public int averageOfSubtree(TreeNode root) {
        matchingNodesCount = 0;
        postOrderDfs(root);
        return matchingNodesCount;
    }

    // Returns an array: [sum of subtree, count of nodes in subtree]
    private int[] postOrderDfs(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }

        int[] leftSubtree = postOrderDfs(node.left);
        int[] rightSubtree = postOrderDfs(node.right);

        int currentSum = leftSubtree[0] + rightSubtree[0] + node.val;
        int currentCount = leftSubtree[1] + rightSubtree[1] + 1;

        if (currentSum / currentCount == node.val) {
            matchingNodesCount++;
        }

        return new int[]{currentSum, currentCount};
    }

    // Helper to build a binary tree from level-order tokens
    public static TreeNode buildTree(String[] tokens) {
        if (tokens.length == 0 || tokens[0].equals("null") || tokens[0].isEmpty()) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < tokens.length) {
            TreeNode curr = queue.poll();

            if (i < tokens.length && !tokens[i].equals("null")) {
                curr.left = new TreeNode(Integer.parseInt(tokens[i]));
                queue.offer(curr.left);
            }
            i++;

            if (i < tokens.length && !tokens[i].equals("null")) {
                curr.right = new TreeNode(Integer.parseInt(tokens[i]));
                queue.offer(curr.right);
            }
            i++;
        }

        return root;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter level-order binary tree nodes separated by space (use 'null' for empty nodes):");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Matching nodes: 0");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        TreeNode root = buildTree(tokens);

        CountNodesEqualToAverageOfSubtree2265 solver = new CountNodesEqualToAverageOfSubtree2265();
        int result = solver.averageOfSubtree(root);

        System.out.println("Number of nodes equal to average of their subtree: " + result);
        scanner.close();
    }
}
