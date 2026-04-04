/*
Problem: 2075. Decode the Slanted Ciphertext

You are given:
- encodedText → a string formed by reading a matrix row-wise
- rows → number of rows in the matrix

Original text was written diagonally (top-left → bottom-right),
then matrix was read row-wise to form encodedText.

Your task:
Reconstruct the original string.

-----------------------------------------------------

🧠 LOGIC EXPLANATION:

STEP 1: Find number of columns
- Total length = rows * cols
→ cols = len / rows

STEP 2: Understand mapping
- Matrix is stored row-wise in encodedText
- Index in matrix = (row * cols + col)

STEP 3: Decoding trick
- Original text was written diagonally:
  (0,0) → (1,1) → (2,2) ...
- So we simulate diagonal traversal:
  start from each column in first row

STEP 4: Traversal
- Fix starting column i
- Move diagonally:
    row = j
    col = i + j
- Stop when col goes out of bounds

STEP 5: Remove trailing spaces
- Because matrix had padded spaces

-----------------------------------------------------

TIME: O(n)
SPACE: O(n)
*/

import java.util.*;

class CipherBot { 

    public String decodeCiphertext(String encodedText, int rows) {

        int len = encodedText.length();

        // Number of columns in matrix
        int cols = len / rows;

        StringBuilder sb = new StringBuilder();

        // Traverse diagonals starting from each column
        for (int i = 0; i < cols; i++) {

            for (int j = 0; j < rows; j++) {

                // If column goes out of bounds, stop this diagonal
                if (i + j >= cols) {
                    break;
                }

                // Convert (row, col) → index in encodedText
                sb.append(encodedText.charAt(j * cols + i + j));
            }
        }

        // Remove trailing spaces
        int right = sb.length() - 1;

        while (right >= 0 && sb.charAt(right) == ' ') {
            right--;
        }

        sb.setLength(right + 1);

        return sb.toString();
    }

    // -------- USER INPUT DRIVER --------
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Input encoded string
        String encodedText = sc.nextLine();

        // Input rows
        int rows = sc.nextInt();

        CipherBot obj = new CipherBot();
        String result = obj.decodeCiphertext(encodedText, rows);

        System.out.println(result);

        sc.close();
    }
}
