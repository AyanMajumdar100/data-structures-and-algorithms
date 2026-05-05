/*
Problem Statement:
Given the head of a linked list, rotate the list to the right by k places.

Constraints:
The number of nodes in the list is in the range [0, 500].
-100 <= Node.val <= 100
0 <= k <= 2 * 10^9
*/

import java.util.Scanner;

// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class RotateList {

    public static ListNode rotateRight(ListNode head, int k) {
        // Base cases: empty list, single node, or no rotation needed
        if (head == null || head.next == null || k == 0) {
            return head;
        }
        
        // Step 1: Find the length of the list and the original tail node
        int len = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            len++;
        }
        
        // Step 2: Connect the tail to the head to form a circular linked list
        tail.next = head;
        
        // Step 3: Calculate effective rotations. 
        // If k >= len, we only care about the remainder.
        k = k % len;
        
        // Step 4: Find the new tail, which is (len - k) steps from the beginning
        int stepsToNewTail = len - k;
        ListNode newTail = tail; // We start at tail because tail.next is head (0 steps)
        
        while (stepsToNewTail > 0) {
            newTail = newTail.next;
            stepsToNewTail--;
        }
        
        // Step 5: Break the circle and define the new head
        ListNode newHead = newTail.next;
        newTail.next = null;
        
        return newHead;
    }

    // Helper to print the linked list
    public static void printList(ListNode head) {
        System.out.print("[");
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + (curr.next != null ? ", " : ""));
            curr = curr.next;
        }
        System.out.println("]");
    }

    // Helper to create a linked list from an array of integers
    public static ListNode createList(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode curr = head;
        for (int i = 1; i < arr.length; i++) {
            curr.next = new ListNode(arr[i]);
            curr = curr.next;
        }
        return head;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the elements of the linked list separated by spaces (leave empty for empty list):");
            String input = scanner.nextLine().trim();
            
            ListNode head = null;
            if (!input.isEmpty()) {
                String[] strVals = input.split("\\s+");
                int[] arr = new int[strVals.length];
                for (int i = 0; i < strVals.length; i++) {
                    arr[i] = Integer.parseInt(strVals[i]);
                }
                head = createList(arr);
            }
            
            System.out.println("Enter the number of places to rotate (k):");
            int k = Integer.parseInt(scanner.nextLine().trim());
            
            ListNode result = rotateRight(head, k);
            System.out.print("Output: ");
            printList(result);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input detected. Please enter valid integers.");
        } finally {
            scanner.close();
        }
    }
}
