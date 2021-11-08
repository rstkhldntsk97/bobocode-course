public class MergeSort {

    public static int[] sort(int[] arr) {
        if (arr == null) {
            return null;
        }
        if (arr.length < 2) {
            return arr;
        }
        int[] a1 = new int[arr.length / 2];
        System.arraycopy(arr, 0, a1, 0, arr.length / 2);

        int[] a2 = new int[arr.length - arr.length / 2];
        System.arraycopy(arr, arr.length / 2, a2, 0, arr.length - arr.length / 2);

        a1 = sort(a1);
        a2 = sort(a2);

        return merge(a1, a2);
    }

    private static int[] merge(int[] a1, int[] a2) {
        int[] arr = new int[a1.length + a2.length];
        int i = 0;
        int j = 0;
        for (int k = 0; k < arr.length; k++) {
            if (i > a1.length - 1) {
                arr[k] = a2[j];
                j++;
            } else if (j > a2.length - 1) {
                arr[k] = a1[i];
                i++;
            } else if (a1[i] < a2[j]) {
                arr[k] = a1[i];
                i++;
            } else {
                arr[k] = a2[j];
                j++;
            }
        }
        return arr;
    }

}
