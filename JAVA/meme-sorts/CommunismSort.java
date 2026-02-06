/*Philosophy : "Why should some elements be bigger than others?"
Make everyone equal. Problem solved.
So instead of sorting…We calculate the average
Then replace every element with that value
The array becomes perfectly equal → therefore perfectly sorted.*/

// Time Complexity: O(n)
import java.util.*;

public class CommunismSort {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();

        int[] arr = new int[n];
        System.out.println("Enter array elements:");

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        communismSort(arr);

        System.out.println("Sorted array using Communism Sort:");
        System.out.println(Arrays.toString(arr));

        sc.close();
    }

    public static void communismSort(int[] arr) {
        if (arr.length == 0) return;

        int sum = 0;
        for (int num : arr) {
            sum += num;
        }

        int average = sum / arr.length;

        for (int i = 0; i < arr.length; i++) {
            arr[i] = average;
        }
    }
}
