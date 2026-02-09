"""
110. Balanced Binary Tree — Problem Statement

Given the root of a binary tree, determine whether the tree is height-balanced.

A binary tree is considered height-balanced if for every node in the tree,
the difference between the heights of its left subtree and right subtree
is at most 1.

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
"""


# Definition for a binary tree node
class TreeNode:
    def __init__(self, val):
        self.val = val
        self.left = None
        self.right = None


class BalanceBST:

    # Main function to balance a BST
    def balanceBST(self, root):
        values = []  # This will store inorder traversal (sorted values)

        # Step 1: Perform inorder traversal to collect values
        self.inorder(root, values)

        # Step 2: Build balanced BST from sorted values
        return self.buildBalancedTree(values, 0, len(values) - 1)

    # Inorder traversal → Left → Root → Right
    # For a BST, this gives values in sorted order
    def inorder(self, node, values):
        if node is None:
            return

        self.inorder(node.left, values)   # Visit left subtree
        values.append(node.val)           # Store current node value
        self.inorder(node.right, values)  # Visit right subtree

    # Builds balanced BST using sorted values (divide and conquer)
    def buildBalancedTree(self, values, start, end):

        # Base case: no elements left
        if start > end:
            return None

        # Choose middle element → ensures height balance
        mid = start + (end - start) // 2

        # Create node using middle value
        node = TreeNode(values[mid])

        # Recursively build left and right subtrees
        node.left = self.buildBalancedTree(values, start, mid - 1)
        node.right = self.buildBalancedTree(values, mid + 1, end)

        return node
