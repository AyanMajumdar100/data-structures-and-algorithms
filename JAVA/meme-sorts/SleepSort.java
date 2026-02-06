/* Idea (beautifully cursed)
For each number x in the array:
Start a thread Sleep for x milliseconds Print x when it wakes up Smaller numbers wake up first → sorted 
WORKS UNLESS TWO VALUES ARE EQUAL or THREADS DIE*/

//Time Complexity: O(n + MAX(n))
import java.util.*;

public class SleepSort {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();

        int[] arr = new int[n];
        System.out.println("Enter array elements (non-negative integers):");

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        sleepSort(arr);

        sc.close();
    }

    public static void sleepSort(int[] arr) {
        for (int num : arr) {
            new Thread(() -> {
                try {
                    Thread.sleep(num);
                    System.out.print(num + " ");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
