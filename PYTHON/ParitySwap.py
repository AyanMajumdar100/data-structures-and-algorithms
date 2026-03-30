"""
Problem: Check if Strings Can be Made Equal With Operations II

--------------------------------------------------

🧠 CORE IDEA:

Swaps allowed only when:
(j - i) is EVEN

👉 So indices must have same parity

--------------------------------------------------

💡 KEY OBSERVATION:

We can independently rearrange:
- Even indices
- Odd indices

So this becomes:
👉 Compare frequency of characters in both groups

--------------------------------------------------

🎯 APPROACH:

Use two frequency arrays:
- even_counts → for index 0,2,4,...
- odd_counts  → for index 1,3,5,...

For each index:
- Add s1 char
- Subtract s2 char

--------------------------------------------------

✅ If all values = 0 → possible
❌ Else → not possible

--------------------------------------------------
"""

def check_strings(s1, s2):

    even_counts = [0] * 26
    odd_counts = [0] * 26

    for i in range(len(s1)):

        if i % 2 == 0:
            even_counts[ord(s1[i]) - ord('a')] += 1
            even_counts[ord(s2[i]) - ord('a')] -= 1
        else:
            odd_counts[ord(s1[i]) - ord('a')] += 1
            odd_counts[ord(s2[i]) - ord('a')] -= 1

    # Validate
    for i in range(26):
        if even_counts[i] != 0 or odd_counts[i] != 0:
            return False

    return True


# ----------- User Input -----------

s1 = input().strip()
s2 = input().strip()

print(check_strings(s1, s2))