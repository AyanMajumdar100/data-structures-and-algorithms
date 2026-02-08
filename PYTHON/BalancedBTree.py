"""
PROBLEM: 110. Balanced Binary Tree

Given a binary tree, determine if it is height-balanced.

A height-balanced binary tree is defined as:
A binary tree in which the depth of the two subtrees of every node
never differs by more than 1.

Example:
Input:  [3,9,20,null,null,15,7]
Output: True

Input:  [1,2,2,3,3,null,null,4,4]
Output: False

Input:  []
Output: True
"""

from collections import deque


# Definition for a binary tree node
class TreeNode:
    def __init__(self, val):
        self.val = val
        self.left = None
        self.right = None


class BalancedBTree:

    # Returns True if tree is balanced
    def is_balanced(self, root):
        # If check_height returns -1 → tree is NOT balanced
        # Otherwise → tree is balanced
        return self.check_height(root) != -1

    # Returns height of subtree OR -1 if unbalanced
    def check_height(self, node):

        # Base case: empty node has height 0
        if node is None:
            return 0

        # Recursively get height of left subtree
        left_height = self.check_height(node.left)

        # If left subtree is unbalanced → stop early
        if left_height == -1:
            return -1

        # Recursively get height of right subtree
        right_height = self.check_height(node.right)

        # If right subtree is unbalanced → stop early
        if right_height == -1:
            return -1

        # If height difference is greater than 1 → unbalanced
        if abs(left_height - right_height) > 1:
            return -1

        # Otherwise return height of current node
        return max(left_height, right_height) + 1


# ---------- USER INPUT DRIVER ----------

def build_tree(data):
    # Builds binary tree from level-order input
    if not data.strip():
        return None

    values = data.split()
    if values[0] == "null":
        return None

    root = TreeNode(int(values[0]))
    queue = deque([root])

    i = 1
    while queue and i < len(values):
        current = queue.popleft()

        # Left child
        if values[i] != "null":
            current.left = TreeNode(int(values[i]))
            queue.append(current.left)
        i += 1

        if i >= len(values):
            break

        # Right child
        if values[i] != "null":
            current.right = TreeNode(int(values[i]))
            queue.append(current.right)
        i += 1

    return root


if __name__ == "__main__":
    print("Enter tree in level-order (use 'null' for empty nodes):")
    print("Example: 3 9 20 null null 15 7")

    user_input = input()

    root = build_tree(user_input)

    tree = BalancedBTree()
    result = tree.is_balanced(root)

    print("Is the tree balanced?", result)
