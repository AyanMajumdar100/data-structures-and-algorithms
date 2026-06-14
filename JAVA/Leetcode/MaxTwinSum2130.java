/*
 * Problem Statement:
 * In a linked list of size n, where n is even, the ith node (0-indexed) is the twin of the (n-1-i)th node.
 * The twin sum is defined as the sum of a node and its twin.
 * Given the head of a linked list with even length, return the maximum twin sum of the linked list.
 */

/*
 * Approach: Fast & Slow Pointers + Reverse Second Half
 * To calculate the twin sums efficiently in O(1) auxiliary space and O(N) time:
 * 1. Find the middle of the linked list using the Fast and Slow pointer approach. 
 * When `fast` reaches the end, `slow` will point to the start of the second half.
 * 2. Reverse the second half of the linked list in place.
 * 3. Maintain two pointers: `first` starting at the head of the list, and `second` 
 * starting at the head of the reversed second half.
 * 4. Traverse both halves simultaneously, compute the sum of their values at each step, 
 * and track the maximum twin sum encountered.
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

public class MaxTwinSum2130 {
    public int pairSum(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        
        // Step 1: Locate the starting split boundary of the second half
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Step 2: Reverse the nodes of the second half in place
        ListNode prev = null;
        ListNode curr = slow;
        while (curr != null) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        
        int maxTwinSum = 0;
        ListNode first = head;
        ListNode second = prev; // Head of the reversed second half
        
        // Step 3: Pair up the twins sequentially from the two independent lists
        while (second != null) {
            maxTwinSum = Math.max(maxTwinSum, first.val + second.val);
            first = first.next;
            second = second.next;
        }
        
        return maxTwinSum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the elements of the even-length linked list separated by space:");
        
        String inputLine = scanner.nextLine().trim();
        if (inputLine.isEmpty()) {
            System.out.println("Maximum twin sum: 0");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Build the linked list from the scanned items
        for (String part : parts) {
            current.next = new ListNode(Integer.parseInt(part));
            current = current.next;
        }
        
        // Execute and print the twin sum results
        MaxTwinSum2130 solver = new MaxTwinSum2130();
        int result = solver.pairSum(dummy.next);
        System.out.println("Maximum twin sum: " + result);
        
        scanner.close();
    }
}
