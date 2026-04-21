'''
Problem Statement:
You are given two integer arrays, source and target, both of length n. 
You are also given an array allowedSwaps where each allowedSwaps[i] = [ai, bi] indicates 
that you are allowed to swap the elements at index ai and index bi (0-indexed) of array source. 
You can swap elements at a specific pair of indices multiple times and in any order.

The Hamming distance of two arrays of the same length is the number of positions where the elements differ.
Return the minimum Hamming distance of source and target after performing any amount of swap operations.

Constraints:
n == source.length == target.length
1 <= n <= 10^5
1 <= source[i], target[i] <= 10^5
0 <= allowedSwaps.length <= 10^5
allowedSwaps[i].length == 2
'''

from collections import defaultdict, Counter

def minimum_hamming_distance(source: list[int], target: list[int], allowed_swaps: list[list[int]]) -> int:
    n = len(source)
    parent = list(range(n))
    
    # DSU Find with path compression
    def find(x: int) -> int:
        if parent[x] != x:
            parent[x] = find(parent[x])
        return parent[x]
        
    # DSU Union
    def union(x: int, y: int):
        root_x = find(x)
        root_y = find(y)
        if root_x != root_y:
            parent[root_x] = root_y
            
    # Group indices into connected components
    for u, v in allowed_swaps:
        union(u, v)
        
    # Build frequency maps for each component using Python's Counter
    # Key is the component root, Value is a Counter of source elements in that component
    component_counts = defaultdict(Counter)
    for i in range(n):
        root = find(i)
        component_counts[root][source[i]] += 1
        
    hamming_distance = 0
    
    # Verify if target elements exist in their respective components
    for i in range(n):
        root = find(i)
        
        # If the required target element is available in this component pool
        if component_counts[root][target[i]] > 0:
            component_counts[root][target[i]] -= 1  # Consume it
        else:
            # Missing element translates to +1 Hamming distance
            hamming_distance += 1
            
    return hamming_distance

if __name__ == "__main__":
    try:
        source = list(map(int, input("Enter 'source' elements separated by spaces:\n").strip().split()))
        target = list(map(int, input("Enter 'target' elements separated by spaces:\n").strip().split()))
        
        num_swaps = int(input("Enter number of allowed swaps:\n").strip())
        allowed_swaps = []
        
        if num_swaps > 0:
            print("Enter each swap pair (index1 index2) on a new line:")
            for _ in range(num_swaps):
                u, v = map(int, input().strip().split())
                allowed_swaps.append([u, v])
                
        result = minimum_hamming_distance(source, target, allowed_swaps)
        print(f"Minimum Hamming Distance: {result}")
        
    except ValueError:
        print("Invalid input detected. Please ensure you are entering valid integers.")