/*
110. Balanced Binary Tree — Problem Statement

Given the root of a binary tree, determine whether the tree is height-balanced.

A binary tree is considered height-balanced if for every node in the tree,
the difference between the heights of its left subtree and right subtree is at most 1.

Examples

Example 1
Input: root = [3,9,20,null,null,15,7]
Output: true

Example 2
Input: root = [1,2,2,3,3,null,null,4,4]
Output: false

Example 3
Input: root = []
Output: true

Constraints
- Number of nodes is in the range [0, 5000]
- -10^4 <= Node.val <= 10^4
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

class BalanceBST {

    // Main function to balance a BST
    public TreeNode balanceBST(TreeNode root) {

        // Step 1: Store inorder traversal of BST (sorted values)
        List<Integer> values = new ArrayList<>();
        inorder(root, values);

        // Step 2: Build balanced BST from sorted values
        return buildBalancedTree(values, 0, values.size() - 1);
    }

    // Performs inorder traversal → gives sorted order in BST
    private void inorder(TreeNode node, List<Integer> values) {
        if (node == null)
            return;

        inorder(node.left, values); // Visit left subtree
        values.add(node.val); // Store current node value
        inorder(node.right, values); // Visit right subtree
    }

    // Builds balanced BST using sorted array (divide and conquer)
    private TreeNode buildBalancedTree(List<Integer> values, int start, int end) {

        // Base case: no elements left
        if (start > end)
            return null;

        // Choose middle element → keeps tree balanced
        int mid = start + (end - start) / 2;

        // Create node with middle value
        TreeNode node = new TreeNode(values.get(mid));

        // Recursively build left and right subtrees
        node.left = buildBalancedTree(values, start, mid - 1);
        node.right = buildBalancedTree(values, mid + 1, end);

        return node;
    }

    // ---------- USER INPUT + DRIVER CODE ----------

    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter BST in level-order (use 'null' for empty nodes):");
        System.out.println("Example: 2 1 3");

        String input = sc.nextLine();

        TreeNode root = buildTree(input);

        final BalanceBST sol = new BalanceBST();
        TreeNode balancedRoot = sol.balanceBST(root);

        System.out.println("Inorder of Balanced BST:");
        printInorder(balancedRoot);

        sc.close();
    }

    // Builds binary tree from level-order input
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

    // Prints inorder traversal of tree
    private static void printInorder(TreeNode root) {
        if (root == null)
            return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }
}
