'''
Problem Statement:
Given the head of a linked list, rotate the list to the right by k places.

Constraints:
The number of nodes in the list is in the range [0, 500].
-100 <= Node.val <= 100
0 <= k <= 2 * 10^9
'''

from typing import Optional

# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

def rotate_right(head: Optional[ListNode], k: int) -> Optional[ListNode]:
    if not head or not head.next or k == 0:
        return head
        
    # Find the length and the tail of the list
    length = 1
    tail = head
    while tail.next:
        tail = tail.next
        length += 1
        
    # Form a circular linked list
    tail.next = head
    
    # Calculate the effective rotation
    k = k % length
    steps_to_new_tail = length - k
    
    # Traverse to the new tail
    new_tail = tail
    for _ in range(steps_to_new_tail):
        new_tail = new_tail.next
        
    # Break the circle and set the new head
    new_head = new_tail.next
    new_tail.next = None
    
    return new_head

# Helper to build a linked list from a list of values
def create_linked_list(arr: list[int]) -> Optional[ListNode]:
    if not arr:
        return None
    head = ListNode(arr[0])
    curr = head
    for val in arr[1:]:
        curr.next = ListNode(val)
        curr = curr.next
    return head

# Helper to print the linked list
def print_linked_list(head: Optional[ListNode]) -> None:
    res = []
    curr = head
    while curr:
        res.append(str(curr.val))
        curr = curr.next
    print("[" + ", ".join(res) + "]")

if __name__ == "__main__":
    try:
        user_input = input("Enter the elements of the linked list separated by spaces (leave empty for empty list):\n").strip()
        
        arr = []
        if user_input:
            arr = [int(x) for x in user_input.split()]
            
        head = create_linked_list(arr)
        
        k = int(input("Enter the number of places to rotate (k):\n").strip())
        
        result = rotate_right(head, k)
        print("Output:", end=" ")
        print_linked_list(result)
        
    except ValueError:
        print("Invalid input detected. Please ensure you enter valid integers.")
