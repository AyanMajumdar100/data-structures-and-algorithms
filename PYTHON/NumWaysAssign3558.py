'''
Problem Statement:
There is an undirected tree with n nodes labeled from 1 to n, rooted at node 1.
You are given a 2D integer array edges of length n - 1. Initially, all edges have 
a weight of 0. You must assign each edge a weight of either 1 or 2.
Select any one node x at the maximum depth. Return the number of ways to assign 
edge weights in the path from node 1 to x such that its total cost is odd.
Since the answer may be large, return it modulo 10^9 + 7.
Note: Ignore all edges not in the path from node 1 to x.
'''

'''
Approach: BFS Depth Tracking + Combinatorics (Binary Exponentiation)
1. Build an adjacency list representation of the tree and run a Breadth-First Search (BFS) 
   starting from the root (Node 1) to find the maximum depth of any node.
2. Let `L` be the maximum depth (which also equals the number of edges on the path from 1 to x).
3. Each edge can be assigned a weight of 1 (odd) or 2 (even). For the total path sum to be odd, 
   we must choose an odd number of edges to have weight 1.
4. Mathematically, the sum of combinations choosing an odd number of elements out of L elements 
   is always exactly 2^(L - 1).
5. Compute 2^(maxDepth - 1) % (10^9 + 7) efficiently using built-in modular pow.
'''
from collections import deque

class NumWaysAssign3558:
    def assignEdgeWeights(self, edges: list[list[int]]) -> int:
        n = len(edges) + 1
        adj = [[] for _ in range(n + 1)]
        
        # Build the tree adjacency structure
        for u, v in edges:
            adj[u].append(v)
            adj[v].append(u)
            
        max_depth = 0
        # Initialize queue for BFS tracking (node, depth)
        queue = deque([(1, 0)])
        visited = [False] * (n + 1)
        visited[1] = True
        
        # Standard BFS loop to identify maximum path length from node 1
        while queue:
            u, d = queue.popleft()
            if d > max_depth:
                max_depth = d # Track the absolute maximum distance
                
            for v in adj[u]:
                if not visited[v]:
                    visited[v] = True
                    queue.append((v, d + 1))
                    
        # Target calculation: 2^(max_depth - 1) % mod
        mod = 1_000_000_007
        
        # Guard case where max_depth is 0 (though constraints imply max_depth >= 1)
        if max_depth == 0:
            return 0
            
        return pow(2, max_depth - 1, mod)

if __name__ == '__main__':
    try:
        num_edges = int(input("Enter the number of edges: "))
        edges = []
        print("Enter edges as space-separated node pairs (u v) line by line:")
        for _ in range(num_edges):
            u, v = map(int, input().split())
            edges.append([u, v])
            
        solver = NumWaysAssign3558()
        result = solver.assignEdgeWeights(edges)
        print("Number of valid edge assignment ways:", result)
    except ValueError:
        print("Invalid input format.")