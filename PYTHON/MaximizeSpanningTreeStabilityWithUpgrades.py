"""
3600. Maximize Spanning Tree Stability with Upgrades

Problem:
You are given n nodes and a list of edges.

Each edge contains:
[u, v, strength, must]

must = 1 -> edge must be included in the spanning tree
must = 0 -> edge optional and can be upgraded

Upgrade Rule:
An upgrade doubles the strength of the edge.
At most k upgrades are allowed.
Each optional edge can be upgraded at most once.

Stability of a spanning tree:
minimum strength among all edges in the tree.

Goal:
Return the maximum possible stability.

If it is impossible to connect all nodes, return -1.
"""

class DSU:

    def __init__(self, n):

        # Initially each node is its own parent
        self.parent = list(range(n))

    def find(self, x):

        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])

        return self.parent[x]

    def union(self, a, b):

        ra = self.find(a)
        rb = self.find(b)

        if ra == rb:
            return False

        self.parent[ra] = rb
        return True


def check(M, n, mandatory, optional, k):

    dsu = DSU(n)
    comps = n

    # Add mandatory edges
    for u, v, s, _ in mandatory:

        if s < M:
            return False

        dsu.union(u, v)
        comps -= 1

    # Add optional edges without upgrades
    for u, v, s, _ in optional:

        if s >= M:

            if dsu.union(u, v):
                comps -= 1

    if comps == 1:
        return True

    upgrades = 0

    # Try using upgrades
    for u, v, s, _ in optional:

        if s < M and s * 2 >= M:

            if upgrades < k:

                if dsu.union(u, v):

                    comps -= 1
                    upgrades += 1

                    if comps == 1:
                        return True

    return comps == 1


def maxStability(n, edges, k):

    mandatory = []
    optional = []

    minMandatory = float('inf')

    # Separate mandatory and optional edges
    for e in edges:

        if e[3] == 1:
            mandatory.append(e)
            minMandatory = min(minMandatory, e[2])
        else:
            optional.append(e)

    # Pre-check for cycles
    pre = DSU(n)
    comps = n

    for u, v, s, _ in mandatory:

        if not pre.union(u, v):
            return -1

        comps -= 1

    # Check if graph can be connected
    for u, v, s, _ in optional:

        if pre.union(u, v):
            comps -= 1

    if comps > 1:
        return -1

    low = 1
    high = 200000

    if minMandatory != float('inf'):
        high = min(high, minMandatory)

    ans = -1

    # Binary search on stability
    while low <= high:

        mid = (low + high) // 2

        if check(mid, n, mandatory, optional, k):
            ans = mid
            low = mid + 1
        else:
            high = mid - 1

    return ans


# User Input
n = int(input("Enter number of nodes: "))
m = int(input("Enter number of edges: "))

edges = []

print("Enter edges as: u v strength must(0/1)")

for _ in range(m):
    u, v, s, must = map(int, input().split())
    edges.append([u, v, s, must])

k = int(input("Enter maximum upgrades k: "))

result = maxStability(n, edges, k)

print("Maximum Stability of Spanning Tree:", result)