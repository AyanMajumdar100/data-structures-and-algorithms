// You go from index 0 to n-1 and remove any element that breaks the sorted order.
// No swapping. No fixing. Just elimination!

// Time Complexity: O(n)
import java.util.*;

public class StalinSort {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();

        int[] arr = new int[n];
        System.out.println("Enter array elements:");

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        List<Integer> sorted = stalinSort(arr);

        System.out.println("Sorted array using Meme (Stalin) Sort:");
        System.out.println(sorted);

        sc.close();
    }

    public static List<Integer> stalinSort(int[] arr) {
        List<Integer> result = new ArrayList<>();

        if (arr.length == 0) return result;

        int lastAccepted = arr[0];
        result.add(lastAccepted);

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] >= lastAccepted) {
                lastAccepted = arr[i];
                result.add(arr[i]);
            }
        }

        return result;
    }
}
