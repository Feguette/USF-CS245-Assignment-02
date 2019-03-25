/**
 * Sorts an array in-place using the insertion sort algorithm.
 *
 * @author Jason Liang
 * @vesion (27 February 2019)
 */
public class InsertionSort implements SortingAlgorithm {
    /*
     *
     */
    public void sort(double[] a) {
        double temp;
        int j;
        for (int i = 1; i < a.length; i++) {
            temp = a[i];
            j = i-1;
            while (j >= 0 && temp < a[j])
                a[j+1] = a[j--];
            a[j+1] = temp;
        }
    }
}
