/*
1622. Fancy Sequence

Problem Statement:
Design a data structure that supports the following operations on a sequence:

Operations:
1. append(val)  -> Append integer val to the sequence
2. addAll(inc)  -> Add inc to every element in the sequence
3. multAll(m)   -> Multiply every element in the sequence by m
4. getIndex(idx)-> Return element at idx (0-indexed) modulo 1e9+7,
                   or -1 if idx is out of bounds

Constraints:
At most 10^5 operations.

Key Idea:
If we apply operations directly on the entire array each time,
it would be O(n) per operation → too slow.

Instead maintain a global linear transformation:

value = original * a + b

Where:
a → cumulative multiplier
b → cumulative addition

When we append a value, we reverse this transformation so that
future operations still work correctly.

Mathematically:
stored_value = (val - b) * modular_inverse(a)

Then when retrieving:
actual_value = stored_value * a + b
*/

import java.util.*;

class Fancy {

    private List<Long> seq;
    private long a;
    private long b;

    private static final int MOD = 1_000_000_007;

    public Fancy() {

        seq = new ArrayList<>();

        a = 1; // multiplier
        b = 0; // addition
    }

    // Append value
    public void append(int val) {

        long v = ((val - b) % MOD + MOD) % MOD;

        v = (v * modInverse(a)) % MOD;

        seq.add(v);
    }

    // Add inc to all
    public void addAll(int inc) {

        b = (b + inc) % MOD;
    }

    // Multiply all by m
    public void multAll(int m) {

        a = (a * m) % MOD;

        b = (b * m) % MOD;
    }

    // Get value at index
    public int getIndex(int idx) {

        if (idx >= seq.size()) return -1;

        long val = (seq.get(idx) * a % MOD + b) % MOD;

        return (int) val;
    }

    // Fast exponentiation
    private long power(long base, long exp) {

        long res = 1;

        base %= MOD;

        while (exp > 0) {

            if ((exp & 1) == 1)
                res = (res * base) % MOD;

            base = (base * base) % MOD;

            exp >>= 1;
        }

        return res;
    }

    // Modular inverse using Fermat's theorem
    private long modInverse(long x) {

        return power(x, MOD - 2);
    }
}

/*
Example Usage:

Fancy fancy = new Fancy();

fancy.append(2);
fancy.addAll(3);
fancy.append(7);
fancy.multAll(2);

System.out.println(fancy.getIndex(0)); // 10

fancy.addAll(3);
fancy.append(10);
fancy.multAll(2);

System.out.println(fancy.getIndex(0)); // 26
System.out.println(fancy.getIndex(1)); // 34
System.out.println(fancy.getIndex(2)); // 20
*/
