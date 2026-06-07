/*
 * Problem Statement:
 * You are given a 2D integer array descriptions where descriptions[i] = [parenti, childi, isLefti] 
 * indicates that parenti is the parent of childi in a binary tree of unique values.
 * If isLefti == 1, then childi is the left child of parenti.
 * If isLefti == 0, then childi is the right child of parenti.
 * Construct the binary tree described by descriptions and return its root.
 */

/*
 * Approach: Hash Map for Nodes + Hash Set for Children
 * 1. Use a HashMap to map integer values to their corresponding TreeNode objects. This ensures 
 * we reuse existing nodes or create them dynamically when we first encounter them.
 * 2. Use a HashSet to record all node values that act as a child to another node.
 * 3. Iterate through descriptions, link parent nodes to child nodes based on the `isLeft` flag, 
 * and populate the children set.
 * 4. Finally, iterate through the parent values. The unique root node of the binary tree is the 
 * one that never appears in the children HashSet.
 */
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;

// Given Definition for a binary tree node.
class TreeNode {
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

public class BuildTree2196 {
    public TreeNode createBinaryTree(int[][] descriptions) {
        // Map to keep track of created tree nodes by value
        Map<Integer, TreeNode> map = new HashMap<>();
        // Set to collect all values that are children of some node
        Set<Integer> children = new HashSet<>();
        
        // Build the structure of the binary tree
        for (int[] d : descriptions) {
            int parentVal = d[0];
            int childVal = d[1];
            int isLeft = d[2];
            
            // Create nodes if they do not exist in our registry yet
            map.putIfAbsent(parentVal, new TreeNode(parentVal));
            map.putIfAbsent(childVal, new TreeNode(childVal));
            
            // Assign pointers based on directional description flags
            if (isLeft == 1) {
                map.get(parentVal).left = map.get(childVal);
            } else {
                map.get(parentVal).right = map.get(childVal);
            }
            
            // Log that this value is a child node
            children.add(childVal);
        }
        
        // Find the root node by scanning for a parent value that is not a child of any node
        for (int[] d : descriptions) {
            if (!children.contains(d[0])) {
                return map.get(d[0]);
            }
        }
        
        return null;
    }

    public static void main(String[] args) {
        // Simple input simulator
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of descriptions:");
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
        
        int[][] descriptions = new int[n][3];
        System.out.println("Enter descriptions (parent, child, isLeft) line by line:");
        for (int i = 0; i < n; i++) {
            descriptions[i][0] = scanner.nextInt();
            descriptions[i][1] = scanner.nextInt();
            descriptions[i][2] = scanner.nextInt();
        }
        
        BuildTree2196 solver = new BuildTree2196();
        TreeNode root = solver.createBinaryTree(descriptions);
        if (root != null) {
            System.out.println("Tree successfully built! Root value is: " + root.val);
        } else {
            System.out.println("Failed to build tree.");
        }
        scanner.close();
    }
}
