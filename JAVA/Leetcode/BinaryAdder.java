/*
67. Add Binary
Given two binary strings a and b, return their sum as a binary string.
A binary string contains only '0' and '1'.

Examples:
Input:  a = "11", b = "1"
Output: "100"

Input:  a = "1010", b = "1011"
Output: "10101"

Constraints:
1 <= a.length, b.length <= 10^4
a and b contain only '0' or '1'
No leading zeros except the number zero itself
*/

#include <iostream>
#include <algorithm>
using namespace std;

class BinaryAdder {
public:
    string addBinary(string a, string b) {

        // I will build the answer from right to left
        // just like normal addition.
        string result = "";

        // Pointers start from the last index of both strings
        int i = a.length() - 1;
        int j = b.length() - 1;

        // Carry stores overflow when sum >= 2
        int carry = 0;

        // I continue while digits remain OR carry exists
        while (i >= 0 || j >= 0 || carry) {

            // If digit exists in a, I add it to carry
            if (i >= 0) {
                carry += a[i] - '0';  // convert char to int
                i--;
            }

            // If digit exists in b, I add it to carry
            if (j >= 0) {
                carry += b[j] - '0';
                j--;
            }

            // Current binary digit is carry % 2
            // I convert it back to char and append
            result += (carry % 2 + '0');

            // Update carry (either 0 or 1)
            carry /= 2;
        }

        // I reverse because digits were added backwards
        reverse(result.begin(), result.end());

        return result;
    }
};


// ---------------- USER INPUT DRIVER ----------------
int main() {
    string a, b;

    cout << "Enter first binary string: ";
    cin >> a;

    cout << "Enter second binary string: ";
    cin >> b;

    BinaryAdder solver;
    string sum = solver.addBinary(a, b);

    cout << "Binary Sum: " << sum << endl;

    return 0;
}
