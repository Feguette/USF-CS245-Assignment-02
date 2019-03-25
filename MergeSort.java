/**
 * Sorts an array in-place using the merge sort algorithm.
 *
 * @author Jason Liang
 * @vesion (27 February 2019)
 */
public class MergeSort implements SortingAlgorithm {
    /*
     * Splits the array. Naming scheme similar to cell division
     * @param parent int[] used to be split into two, then remerged at mergeSplice()
     */
    public void sort(double[] array) {
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
}
