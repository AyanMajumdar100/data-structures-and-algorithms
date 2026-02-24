"""
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
"""

from collections import deque
import sys


class BinaryRootToLeafSum:

    class TreeNode:
        def __init__(self, val):
            self.val = val
            self.left = None
            self.right = None

    """
    We perform an iterative DFS traversal.

    Beginner-friendly idea:
    - Start from root.
    - While moving down the tree, keep building the binary number.
    - To add a new bit:
        shift left (multiply by 2) and add node value.
    - When we reach a leaf → we add that number to total sum.
    """
    @staticmethod
    def sum_root_to_leaf(root):
        if root is None:
            return 0

        stack = [None] * 1000
        vals = [0] * 1000
        top = 0

        # Start from root
        stack[top] = root
        vals[top] = root.val
        top += 1

        total = 0

        # Process nodes until stack becomes empty
        while top > 0:
            top -= 1
            node = stack[top]
            current_val = vals[top]

            # If leaf → complete binary number formed
            if node.left is None and node.right is None:
                total += current_val
            else:

                # Visit right child
                if node.right is not None:
                    stack[top] = node.right
                    vals[top] = (current_val << 1) | node.right.val
                    top += 1

                # Visit left child
                if node.left is not None:
                    stack[top] = node.left
                    vals[top] = (current_val << 1) | node.left.val
                    top += 1

        return total

    # Build binary tree from level-order input
    @staticmethod
    def build_tree(values):
        if not values or values[0] == "null":
            return None

        root = BinaryRootToLeafSum.TreeNode(int(values[0]))
        queue = deque([root])
        i = 1

        while queue and i < len(values):
            current = queue.popleft()

            if i < len(values) and values[i] != "null":
                current.left = BinaryRootToLeafSum.TreeNode(int(values[i]))
                queue.append(current.left)
            i += 1

            if i < len(values) and values[i] != "null":
                current.right = BinaryRootToLeafSum.TreeNode(int(values[i]))
                queue.append(current.right)
            i += 1

        return root


# User input: level-order values separated by space
line = sys.stdin.readline().strip()
values = line.split()

root = BinaryRootToLeafSum.build_tree(values)
result = BinaryRootToLeafSum.sum_root_to_leaf(root)

print(result)