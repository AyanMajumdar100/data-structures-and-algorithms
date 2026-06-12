'''
Problem Statement:
There is an undirected tree with n nodes labeled from 1 to n, rooted at node 1.
You must assign each edge a weight of either 1 or 2. You are given a 2D integer array queries.
For each queries[i] = [ui, vi], determine the number of ways to assign weights to edges 
in the path such that the cost of the path between ui and vi is odd.
Return an array answer, where answer[i] is the number of valid assignments for queries[i] modulo 10^9 + 7.
Note: For each query, disregard all edges not in the path between node ui and vi.
'''

'''
Approach: Binary Lifting (LCA) + Combinatorics
1. For each path between u and v, let `len` be the number of edges on the unique path between them.
2. If u == v, len = 0, and the sum is always 0 (even), so the number of ways to make it odd is 0.
3. If len > 0, we can assign weights of 1 or 2 to each edge. For the sum to be odd, we must pick 
   an odd number of edges to have weight 1. The number of ways to do this is exactly 2^(len - 1).
4. To find `len` efficiently for multiple queries, we find the Lowest Common Ancestor (LCA) using Binary Lifting.
   The length of the path is: depth[u] + depth[v] - 2 * depth[lca].
5. Precompute powers of 2 modulo 10^9 + 7 to answer each query in O(log N) time.
'''
from collections import deque
import sys

# Increase recursion depth if needed for deep deep call stacks
sys.setrecursionlimit(200000)

class NumWaysAssignII3559:
    def assignEdgeWeights(self, edges: list[list[int]], queries: list[list[int]]) -> list[int]:
        n = len(edges) + 1
        LOG = 0
        while (1 << LOG) <= n:
            LOG += 1
            
        adj = [[] for _ in range(n + 1)]
        for u, v in edges:
            adj[u].append(v)
            adj[v].append(u)
            
        depth = [0] * (n + 1)
        up = [[1] * LOG for _ in range(n + 1)]
        
        queue = deque([1])
        visited = [False] * (n + 1)
        visited[1] = True
        depth[1] = 0
        
        # BFS to build the depth array and initialize binary lifting parent tables
        while queue:
            u = queue.popleft()
            for v in adj[u]:
                if not visited[v]:
                    visited[v] = True
                    depth[v] = depth[u] + 1
                    up[v][0] = u # Immediate parent tracking
                    for j in range(1, LOG):
                        up[v][j] = up[up[v][j - 1]][j - 1] # Compute 2^j ancestors
                    queue.append(v)
                    
        MOD = 1_000_000_007
        pow2 = [1] * (n + 1)
        for i in range(1, n + 1):
            pow2[i] = (pow2[i - 1] * 2) % MOD
            
        def getLCA(u: int, v: int) -> int:
            # Bring node u to a deeper level than or equal to node v
            if depth[u] < depth[v]:
                u, v = v, u
                
            # Lift u to the same depth level as v
            for j in range(LOG - 1, -1, -1):
                if depth[u] - (1 << j) >= depth[v]:
                    u = up[u][j]
                    
            if u == v:
                return u
                
            # Lift both nodes together right below their common ancestor
            for j in range(LOG - 1, -1, -1):
                if up[u][j] != up[v][j]:
                    u = up[u][j]
                    v = up[v][j]
                    
            return up[u][0] # Return the direct parent which is the LCA
            
        ans = []
        for u, v in queries:
            if u == v:
                ans.append(0) # Length 0 path sum is 0, which is always even
            else:
                lca = getLCA(u, v)
                length = depth[u] + depth[v] - 2 * depth[lca] # Calculate path length
                ans.append(pow2[length - 1]) # Number of combinations is 2^(length - 1)
                
        return ans

if __name__ == '__main__':
    try:
        n = int(input("Enter number of nodes N: "))
        edges = []
        print("Enter edges (u v) line by line:")
        for _ in range(n - 1):
            edges.append(list(map(int, input().split())))
            
        q = int(input("Enter number of queries Q: "))
        queries = []
        print("Enter queries (u v) line by line:")
        for _ in range(q):
            queries.append(list(map(int, input().split())))
            
        solver = NumWaysAssignII3559()
        result = solver.assignEdgeWeights(edges, queries)
        print("Query Results:", result)
    except ValueError:
        print("Invalid input format.")