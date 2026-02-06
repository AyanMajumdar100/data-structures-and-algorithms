/*The most hope-based algorithm ever invented.
Core belief

"If the array is not sorted… wait.
One day, a miracle will happen."

No swaps.
No removals.
No equality.
Just faith.*/

// Time Complexity: O(∞)
import java.util.*;

public class MiracleSort {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();

        int[] arr = new int[n];
        System.out.println("Enter array elements:");

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        miracleSort(arr);

        System.out.println("Array is sorted! A miracle occurred ✨");
        System.out.println(Arrays.toString(arr));

        sc.close();
    }

    public static void miracleSort(int[] arr) {
        while (!isSorted(arr)) {
            // Do absolutely nothing
            // Waiting for a miracle...
        }
    }

    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }
}
