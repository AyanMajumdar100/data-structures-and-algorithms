'''
Problem Statement:
You are given two string arrays, queries and dictionary. All words in each array comprise 
of lowercase English letters and have the same length.

In one edit you can take a word from queries, and change any letter in it to any other letter. 
Find all words from queries that, after a maximum of two edits, equal some word from dictionary.
Return a list of all words from queries, that match with some word from dictionary after a 
maximum of two edits. Return the words in the same order they appear in queries.

Constraints:
1 <= queries.length, dictionary.length <= 100
n == queries[i].length == dictionary[j].length
1 <= n <= 100
All queries[i] and dictionary[j] are composed of lowercase English letters.
'''

def two_edit_words(queries: list[str], dictionary: list[str]) -> list[str]:
    res = []
    
    for q in queries:
        for d in dictionary:
            diff = 0
            
            # Using zip allows us to iterate through both strings in parallel easily
            for c1, c2 in zip(q, d):
                if c1 != c2:
                    diff += 1
                
                # Optimization: Early exit if edits exceed 2
                if diff > 2:
                    break
                    
            # If the loop finishes (or breaks) and diff is valid, add to results and move to next query
            if diff <= 2:
                res.append(q)
                break
                
    return res

if __name__ == "__main__":
    try:
        queries_input = input("Enter the words for 'queries' separated by spaces:\n").strip()
        queries = queries_input.split()
        
        dict_input = input("Enter the words for 'dictionary' separated by spaces:\n").strip()
        dictionary = dict_input.split()
        
        if not queries or not dictionary:
            print("Input arrays cannot be empty.")
        else:
            result = two_edit_words(queries, dictionary)
            print(f"Output: {result}")
            
    except Exception as e:
        print("An error occurred while processing your input.")