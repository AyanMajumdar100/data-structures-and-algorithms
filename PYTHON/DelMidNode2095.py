'''
Problem Statement:
You are given the head of a linked list. Delete the middle node, and return the head of the modified linked list.
The middle node of a linked list of size n is the ⌊n / 2⌋th node from the start using 0-based indexing.
'''

'''
Approach: Fast & Slow Pointers (Two-Pointer Technique)
To delete a node from a singly linked list, we need to stop at the node immediately 
*before* the target node.

1. Base Case: If the list contains 0 or 1 node, deleting the middle node results in an empty list. 
   Thus, we return None.
2. Optimization: By initializing `slow` at `head` and `fast` at `head.next.next`, we offset the 
   `fast` pointer by 2 steps. 
3. Execution: When `fast` (moving 2 steps at a time) reaches the end of the list, `slow` (moving 1 step 
   at a time) will land precisely on the node *just before* the actual middle node.
4. Deletion: We bypass the middle node by pointing `slow.next` directly to `slow.next.next`.
'''
# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class DelMidNode2095:
    def deleteMiddle(self, head: ListNode) -> ListNode:
        # Base Case: If there's only one node (or none), the result is an empty list
        if not head or not head.next:
            return None
            
        slow = head
        # Start fast two nodes ahead so slow stops right before the middle node
        fast = head.next.next
        
        # Advance fast by 2 steps and slow by 1 step
        while fast and fast.next:
            slow = slow.next
            fast = fast.next.next
            
        # slow is now right before the middle node. Bypass the middle node.
        slow.next = slow.next.next
        
        return head

# Helper function to print the linked list
def print_list(head: ListNode):
    curr = head
    elements = []
    while curr:
        elements.append(str(curr.val))
        curr = curr.next
    print(" -> ".join(elements) if elements else "Empty List")

if __name__ == '__main__':
    try:
        user_input = input("Enter the elements of the linked list separated by space: ").strip()
        if not user_input:
            print("Result: Empty List")
        else:
            elements = list(map(int, user_input.split()))
            
            # Construct the linked list
            dummy = ListNode(0)
            current = dummy
            for val in elements:
                current.next = ListNode(val)
                current = current.next
                
            # Execute algorithm and show output
            solver = DelMidNode2095()
            new_head = solver.deleteMiddle(dummy.next)
            
            print("Modified List: ", end="")
            print_list(new_head)
    except ValueError:
        print("Invalid input format. Please enter integers only.")