/**
 * Helper class for External sort
 */
public class Sorting {
    private static final int MAX_LIMIT = 10;

    public static void hybridSort(double[] array) {
        hybridSort(array, 0, array.length-1);
    }
    private static void hybridSort(double[] array, int lower, int upper) {
        if (upper - lower > MAX_LIMIT)
            quickSort(array, lower, upper);
        else
            insertionSort(array, lower, upper);
    }

    public static void mergeSort(double[] array) {
        mergeSplit(array);
    }
    private static void mergeSplit(double[] parent) {
        int genome = parent.length;
        if (genome > 1) {
            double[] clone1 = new double[genome/2], clone2 = new double[genome - genome/2];
            //Copies both arrays
            for (int i = 0; i < genome / 2; i++)
                clone1[i] = parent[i];
            for (int i = 0; i < genome - genome/2; i++)
                clone2[i] = parent[i + genome/2];
            mergeSplit(clone1);
            mergeSplit(clone2);
            mergeSplice(parent, clone1, clone2);
        }
    }
    private static void mergeSplice(double[] parent, double[] clone1, double[] clone2) {
        int counter1 = 0, counter2 = 0;
        while (parent.length > counter1 + counter2) {
            if (counter2 > clone2.length - 1 || (counter1 <= clone1.length - 1 && clone1[counter1] <= clone2[counter2]))
                parent[counter1+counter2] = clone1[counter1++];
            else
                parent[counter1+counter2] = clone2[counter2++];
        }
    }

    public static void quickSort(double[] array) {
        quickSort(array, 0, array.length-1);
    }
    private static void quickSort(double[] array, int lower, int upper) {
        if (lower < upper) {
            int pivot = partition(array, lower, upper);
            quickSort(array, lower, pivot - 1);
            quickSort(array,pivot + 1, upper);
        }
    }
    private static int partition (double[] array, int lower, int upper) {
        if (lower >= upper)
            return lower;
        int mid = (lower + upper)/2;
        if (array[mid] < Math.max(array[lower], array[upper]) && array[mid] > Math.min(array[lower], array[upper]))
            swap(array, mid, lower);
        else if (array[upper] < Math.max(array[lower], array[mid]) && array[upper] > Math.min(array[lower], array[mid]))
            swap(array, lower, upper);
        int left = lower + 1;
        int right = upper;
        while (left < right) {
            while (left <= upper && array[left] <= array[lower])
                left++;
            while (right >= left && array[right] > array[lower])
                right--;
            if (left < right)
                swap(array, left, right);
        }
        swap(array, lower, right);

        if (array[lower] > array[upper])
            swap(array, lower, upper);
        return right;

    }
    private static void swap(double[] array, int index1, int index2) {
        double temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    public static void insertionSort(double[] array) {
        insertionSort(array, 0, array.length);
    }
    private static void insertionSort(double[] array, int lower, int upper) {
        double temp;
        int j;
        for (int i = lower + 1; i <= upper; i++) {
            temp = array[i];
            j = i - 1;
            while (j >= lower && temp < array[j])
                array[j + 1] = array[j--];
            array[j + 1] = temp;
        }
    }

    public static boolean inOrder(double[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i-1] > array[i])
                return false;
        }
        return true;
    }
}
