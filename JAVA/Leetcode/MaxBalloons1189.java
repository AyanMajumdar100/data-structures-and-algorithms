/*
 * Problem Statement:
 * Given a string text, you want to use the characters of text to form as many instances 
 * of the word "balloon" as possible. You can use each character in text at most once. 
 * Return the maximum number of instances that can be formed.
 */

/*
 * Approach: Frequency Map / Bottleneck Strategy
 * 1. The word "balloon" consists of the following character counts:
 * 'b' -> 1, 'a' -> 1, 'l' -> 2, 'o' -> 2, 'n' -> 1
 * 2. We use a frequency array of size 26 to count the occurrences of all characters in the given text.
 * 3. To find how many full words we can create, we divide the counts of 'l' and 'o' by 2 
 * since each instance of "balloon" requires two of them.
 * 4. The maximum number of "balloon" words we can build is bounded by the rarest 
 * component (the bottleneck). Thus, we return the minimum frequency among these needed counts.
 */
import java.util.Scanner;

public class MaxBalloons1189 {
    public int maxNumberOfBalloons(String text) {
        // Frequency tracker array for all 26 lowercase English alphabet characters
        int[] counts = new int[26];
        for (int i = 0; i < text.length(); i++) {
            counts[text.charAt(i) - 'a']++;
        }
        
        // Single characters required per "balloon" instance
        int b = counts['b' - 'a'];
        int a = counts['a' - 'a'];
        int n = counts['n' - 'a'];
        
        // Double characters required per "balloon" instance (perform integer division by 2)
        int l = counts['l' - 'a'] / 2;
        int o = counts['o' - 'a'] / 2;
        
        // The limiting component determines the maximum instances we can assemble
        return Math.min(b, Math.min(a, Math.min(l, Math.min(o, n))));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the text string:");
        String text = scanner.nextLine().trim();
        
        MaxBalloons1189 solver = new MaxBalloons1189();
        int result = solver.maxNumberOfBalloons(text);
        
        System.out.println("Maximum number of 'balloon' instances: " + result);
        scanner.close();
    }
}
