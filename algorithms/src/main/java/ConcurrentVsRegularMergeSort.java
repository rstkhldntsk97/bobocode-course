import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

public class ConcurrentVsRegularMergeSort {
    public static void main(String[] args) {
        System.out.println("Regular merge sort");
        for (int i = 0; i < 10; i++) {
            int[] arr = new int[20_000_000];
            for (int j = 0; j < arr.length; j++) {
                arr[j] = ThreadLocalRandom.current().nextInt();
            }
            long start = System.currentTimeMillis();
            MergeSort.sort(arr);
            long finish = System.currentTimeMillis();
            System.out.println(finish - start);
        }
        System.out.println("Concurrent merge sort");
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        for (int i = 0; i < 10; i++) {
            int[] arr = new int[20_000_000];
            for (int j = 0; j < arr.length; j++) {
                arr[j] = ThreadLocalRandom.current().nextInt();
            }
            long start = System.currentTimeMillis();
            forkJoinPool.invoke(new ConcurrentMergeSort(arr));
            long finish = System.currentTimeMillis();
            System.out.println(finish - start);
        }
    }
}
