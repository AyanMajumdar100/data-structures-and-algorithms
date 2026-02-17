/*
PROBLEM: 110. Balanced Binary Tree

Given a binary tree, determine if it is height-balanced.

A height-balanced binary tree is defined as:
A binary tree in which the depth of the two subtrees of every node
never differs by more than 1.

Example:
Input:  [3,9,20,null,null,15,7]
Output: true

Input:  [1,2,2,3,3,null,null,4,4]
Output: false

Input:  []
Output: true
*/

import java.util.*;

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

class BalancedBTree {

    // Main function to check if tree is balanced
    public boolean isBalanced(TreeNode root) {
        // If checkHeight returns -1 → tree is NOT balanced
        // Otherwise → tree is balanced
        return checkHeight(root) != -1;
    }

    // Returns height of subtree OR -1 if unbalanced
    private int checkHeight(TreeNode node) {

        // Base case: empty tree has height 0
        if (node == null) {
            return 0;
        }

        // Recursively get height of left subtree
        int leftHeight = checkHeight(node.left);

        // If left subtree is already unbalanced → stop early
        if (leftHeight == -1)
            return -1;

        // Recursively get height of right subtree
        int rightHeight = checkHeight(node.right);

        // If right subtree is already unbalanced → stop early
        if (rightHeight == -1)
            return -1;

        // If difference in heights is more than 1 → unbalanced
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        // Otherwise return height of current node
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // ---------- USER INPUT + DRIVER CODE ----------

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter tree in level-order (use 'null' for empty nodes):");
        System.out.println("Example: 3 9 20 null null 15 7");

        String input = sc.nextLine();

        TreeNode root = buildTree(input);

        final BalancedBTree sol = new BalancedBTree();
        boolean result = sol.isBalanced(root);

        System.out.println("Is the tree balanced? " + result);

        sc.close();
    }

    // Helper method to build tree from level-order input
    private static TreeNode buildTree(String data) {
        if (data == null || data.trim().isEmpty())
            return null;

        String[] values = data.split(" ");
        if (values[0].equals("null"))
            return null;

        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;

        while (!queue.isEmpty() && i < values.length) {
            TreeNode current = queue.poll();

            // Left child
            if (!values[i].equals("null")) {
                current.left = new TreeNode(Integer.parseInt(values[i]));
                queue.add(current.left);
            }
            i++;

            if (i >= values.length)
                break;

            // Right child
            if (!values[i].equals("null")) {
                current.right = new TreeNode(Integer.parseInt(values[i]));
                queue.add(current.right);
            }
            i++;
        }

        return root;
    }
}
