/*
Problem: Sum of Root To Leaf Binary Numbers

You are given the root of a binary tree where each node has a value 0 or 1.
Each root-to-leaf path represents a binary number starting from the root
(the root is the most significant bit).

For every path from root → leaf:
Keep forming a binary number and convert it to decimal.
Return the total sum of all such values.

Example:
Input: root = [1,0,1,0,1,0,1]
Output: 22
Explanation:
Paths → 100 (4), 101 (5), 110 (6), 111 (7)
Total = 22

Input: root = [0]
Output: 0
*/

import java.util.*;

class BinaryRootToLeafSum {

    // Basic structure of a tree node
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    /*
    We perform an iterative DFS traversal.

    Idea in simple words:
    - As we move from root to child, we keep forming the binary number.
    - To append a new bit:
        shift left (multiply by 2) and add current node value.
    - When we reach a leaf node → we add that number to total.
    */
    public static int sumRootToLeaf(TreeNode root) {
        if (root == null) return 0;

        // Manual stacks to simulate recursion
        TreeNode[] stack = new TreeNode[1000];
        int[] vals = new int[1000];
        int top = 0;

        // Start traversal from root
        stack[top] = root;
        vals[top] = root.val;
        top++;

        int total = 0;

        // Continue until no nodes left to process
        while (top > 0) {

            // Pop last inserted node (DFS behavior)
            top--;
            TreeNode node = stack[top];
            int currentVal = vals[top];

            // If this node is a leaf, we have a complete binary number
            if (node.left == null && node.right == null) {
                total += currentVal;
            } else {

                // Visit right child
                if (node.right != null) {
                    stack[top] = node.right;

                    // Shift left and add new bit
                    vals[top] = (currentVal << 1) | node.right.val;
                    top++;
                }

                // Visit left child
                if (node.left != null) {
                    stack[top] = node.left;

                    // Shift left and add new bit
                    vals[top] = (currentVal << 1) | node.left.val;
                    top++;
                }
            }
        }
        return total;
    }

    // Build binary tree from level-order input
    static TreeNode buildTree(String[] values) {
        if (values.length == 0 || values[0].equals("null")) return null;

        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;

        while (!queue.isEmpty() && i < values.length) {
            TreeNode current = queue.poll();

            if (i < values.length && !values[i].equals("null")) {
                current.left = new TreeNode(Integer.parseInt(values[i]));
                queue.offer(current.left);
            }
            i++;

            if (i < values.length && !values[i].equals("null")) {
                current.right = new TreeNode(Integer.parseInt(values[i]));
                queue.offer(current.right);
            }
            i++;
        }
        return root;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User enters tree in level order (space separated)
        String line = scanner.nextLine().trim();
        String[] input = line.split("\\s+");

        TreeNode root = buildTree(input);
        int result = sumRootToLeaf(root);

        System.out.println(result);
        scanner.close();
    }
}