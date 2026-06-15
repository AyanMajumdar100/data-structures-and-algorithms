/*
 * Problem Statement:
 * You are given the head of a linked list. Delete the middle node, and return the head of the modified linked list.
 * The middle node of a linked list of size n is the ⌊n / 2⌋th node from the start using 0-based indexing.
 */

/*
 * Approach: Fast & Slow Pointers (Two-Pointer Technique)
 * To delete a node from a singly linked list, we need to stop at the node immediately 
 * *before* the target node.
 * * 1. Base Case: If the list contains 0 or 1 node, deleting the middle node results in an empty list. 
 * Thus, we return null.
 * 2. Optimization: By initializing `slow` at `head` and `fast` at `head.next.next`, we offset the 
 * `fast` pointer by 2 steps. 
 * 3. Execution: When `fast` (moving 2 steps at a time) reaches the end of the list, `slow` (moving 1 step 
 * at a time) will land precisely on the node *just before* the actual middle node.
 * 4. Deletion: We bypass the middle node by pointing `slow.next` directly to `slow.next.next`.
 */
import java.util.Scanner;

// Given Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class DelMidNode2095 {
    public ListNode deleteMiddle(ListNode head) {
        // Base Case: If there's only one node (or none), the result is an empty list
        if (head == null || head.next == null) {
            return null;
        }
        
        ListNode slow = head;
        // Start fast two nodes ahead so slow stops right before the middle node
        ListNode fast = head.next.next;
        
        // Advance fast by 2 steps and slow by 1 step
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // slow is now right before the middle node. Bypass the middle node.
        slow.next = slow.next.next;
        
        return head;
    }

    // Helper method to print the linked list
    public static void printList(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + (curr.next != null ? " -> " : ""));
            curr = curr.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the elements of the linked list separated by space:");
        
        String inputLine = scanner.nextLine().trim();
        if (inputLine.isEmpty()) {
            System.out.println("Result: Empty List");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Build the linked list
        for (String part : parts) {
            current.next = new ListNode(Integer.parseInt(part));
            current = current.next;
        }
        
        DelMidNode2095 solver = new DelMidNode2095();
        ListNode newHead = solver.deleteMiddle(dummy.next);
        
        System.out.print("Modified List: ");
        printList(newHead);
        
        scanner.close();
    }
}