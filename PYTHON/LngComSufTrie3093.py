'''
Problem Statement:
You are given two arrays of strings wordsContainer and wordsQuery.
For each wordsQuery[i], you need to find a string from wordsContainer that has the 
longest common suffix with wordsQuery[i]. If there are two or more strings in 
wordsContainer that share the longest common suffix, find the string that is the 
smallest in length. If there are two or more such strings that have the same 
smallest length, find the one that occurred earlier in wordsContainer.
Return an array of integers ans, where ans[i] is the index of the string in 
wordsContainer that has the longest common suffix with wordsQuery[i].
'''

'''
Approach: Trie (Suffix Tree)
To efficiently find common suffixes, we can insert all strings from wordsContainer 
into a Trie in reverse order (backwards). 
Each TrieNode will maintain a `best_idx`, which stores the index of the optimal string 
(shortest length, breaking ties with the earliest index) that passes through this node.
For each query, we search the Trie backwards character by character. We stop when we 
can't match a character anymore, and return the `best_idx` of the deepest matched node.
'''
class TrieNode:
    def __init__(self, best_idx: int):
        # Dictionary to map characters to child TrieNodes
        self.children = {}
        # Best index seen passing through this node
        self.best_idx = best_idx

class LngComSufTrie3093:
    def stringIndices(self, wordsContainer: list[str], wordsQuery: list[str]) -> list[int]:
        # Track the index of the globally best string (shortest length, earliest index)
        global_best = 0
        for i in range(1, len(wordsContainer)):
            if len(wordsContainer[i]) < len(wordsContainer[global_best]):
                global_best = i
                
        # Initialize the root with the global best index
        root = TrieNode(global_best)
        
        # Build the Trie with reversed words from wordsContainer
        for i, word in enumerate(wordsContainer):
            curr = root
            # Traverse the string backwards
            for char in reversed(word):
                if char not in curr.children:
                    # If child doesn't exist, create it and mark current index as best
                    curr.children[char] = TrieNode(i)
                else:
                    # If child exists, compare lengths to update best_idx if needed
                    curr_best = curr.children[char].best_idx
                    if len(word) < len(wordsContainer[curr_best]):
                        curr.children[char].best_idx = i
                
                # Move down the Trie
                curr = curr.children[char]
                
        ans = []
        # Process each query
        for q in wordsQuery:
            curr = root
            # Traverse the Trie using the reversed query string
            for char in reversed(q):
                # Stop traversing if there is no further matching suffix character
                if char not in curr.children:
                    break
                curr = curr.children[char]
            
            # The best_idx at the deepest reached node is our answer for this query
            ans.append(curr.best_idx)
            
        return ans

if __name__ == '__main__':
    try:
        user_container = input("Enter wordsContainer separated by space: ").strip()
        wordsContainer = user_container.split()
        
        user_query = input("Enter wordsQuery separated by space: ").strip()
        wordsQuery = user_query.split()
        
        solver = LngComSufTrie3093()
        result = solver.stringIndices(wordsContainer, wordsQuery)
        
        print("Result:", result)
    except ValueError:
        print("Invalid input format.")