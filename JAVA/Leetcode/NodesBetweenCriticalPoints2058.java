/*
 * Problem Statement: LeetCode 2058 - Find the Minimum and Maximum Number of Nodes Between Critical Points
 * A critical point is either a local maxima (strictly greater than both prev and next) or 
 * a local minima (strictly smaller than both prev and next).
 * Return [minDistance, maxDistance] between any two distinct critical points.
 * If fewer than two critical points exist, return [-1, -1].
 */

/*
 * Approach: Single-Pass Pointer Traversal (O(N) Time, O(1) Space)
 * 1. Track three pointers: `prev`, `curr`, and `curr.next`.
 * 2. Keep track of:
 *    - `firstCritical`: 1-based index of the first critical point observed.
 *    - `prevCritical`: 1-based index of the most recently observed critical point.
 *    - `minDistance`: Running minimum difference between adjacent critical points.
 * 3. Whenever a node meets the critical condition:
 *    - If it's the first critical point, record `firstCritical = index`.
 *    - Otherwise, update `minDistance = min(minDistance, index - prevCritical)`.
 *    - Update `prevCritical = index`.
 * 4. After traversal:
 *    - If fewer than 2 critical points were found (`minDistance == Integer.MAX_VALUE`), return [-1, -1].
 *    - Otherwise, max distance is the distance between the very first and very last critical point:
 *      `maxDistance = prevCritical - firstCritical`.
 */

import java.util.Arrays;
import java.util.Scanner;

public class NodesBetweenCriticalPoints2058 {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public int[] nodesBetweenCriticalPoints(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return new int[]{-1, -1};
        }

        ListNode prev = head;
        ListNode curr = head.next;

        int index = 1;
        int firstCritical = -1;
        int prevCritical = -1;
        int minDistance = Integer.MAX_VALUE;

        // Traverse through the linked list evaluating nodes that have both prev and next
        while (curr.next != null) {
            ListNode next = curr.next;

            // Check if curr is a local maxima or local minima
            if ((curr.val > prev.val && curr.val > next.val) || 
                (curr.val < prev.val && curr.val < next.val)) {

                if (firstCritical == -1) {
                    firstCritical = index;
                } else {
                    minDistance = Math.min(minDistance, index - prevCritical);
                }
                prevCritical = index;
            }

            prev = curr;
            curr = next;
            index++;
        }

        // Return [-1, -1] if fewer than two critical points were found
        if (minDistance == Integer.MAX_VALUE) {
            return new int[]{-1, -1};
        }

        return new int[]{minDistance, prevCritical - firstCritical};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter linked list values separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Result: [-1, -1]");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;

        for (String token : tokens) {
            tail.next = new ListNode(Integer.parseInt(token));
            tail = tail.next;
        }

        NodesBetweenCriticalPoints2058 solver = new NodesBetweenCriticalPoints2058();
        int[] result = solver.nodesBetweenCriticalPoints(dummyHead.next);

        System.out.println("Distance [minDistance, maxDistance]: " + Arrays.toString(result));
        scanner.close();
    }
}
