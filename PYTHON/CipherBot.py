"""
Problem: 2075. Decode the Slanted Ciphertext

Same logic as Java.

-----------------------------------------------------

🧠 HUMAN THINKING:

Encoded text is basically a matrix flattened row-wise.

But original text was written diagonally.

So to decode:
👉 Reconstruct diagonal reading from matrix

-----------------------------------------------------

KEY STEPS:

1. Find columns:
   cols = len(encodedText) // rows

2. Traverse diagonally:
   For each starting column i:
      move like (row=j, col=i+j)

3. Convert 2D → 1D index:
   index = j * cols + (i + j)

4. Remove trailing spaces

-----------------------------------------------------
"""

class CipherBot:

    def decodeCiphertext(self, encodedText: str, rows: int) -> str:

        length = len(encodedText)

        # Number of columns
        cols = length // rows

        result = []

        # Traverse diagonals
        for i in range(cols):

            for j in range(rows):

                # Stop if out of bounds
                if i + j >= cols:
                    break

                index = j * cols + (i + j)
                result.append(encodedText[index])

        # Convert to string and strip trailing spaces
        return "".join(result).rstrip()


# -------- USER INPUT DRIVER --------

if __name__ == "__main__":

    encodedText = input()
    rows = int(input())

    obj = CipherBot()
    print(obj.decodeCiphertext(encodedText, rows))
