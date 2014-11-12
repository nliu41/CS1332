import java.util.Random;

/**
  * Sorting implementation
  * CS 1332 : Fall 2014
  * @author Paras Jain
  * @version 1.0
  */
public class Sorting implements SortingInterface {

    // Do not add any instance variables.


    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @param arr the array that must be sorted after the method runs
     */

    @Override
    public <T extends Comparable<? super T>> void bubblesort(T[] arr) {
        for (int o = 0; o < arr.length; o++) {
            for (int i = 0; i < arr.length - o - 1; i++) {
                if (arr[i].compareTo(arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                }

//                System.out.printf("%d   ", i);
//                debug(arr);
            }
        }
    }

    @Override
    public <T extends Comparable<? super T>> void insertionsort(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0 && arr[j-1].compareTo(arr[j]) > 0; j--) {
                swap(arr, j, j-1);
            }
        }

    }

    @Override
    public <T extends Comparable<? super T>> void selectionsort(T[] arr) {
        int maxIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            maxIndex = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] > arr[maxIndex]) {
                    maxIndex = j;
                }
            }
            swap(arr, i, maxIndex);
        }
    }

    @Override
    public <T extends Comparable<? super T>> void quicksort(T[] arr, Random r) {
        // TODO Auto-generated method stub

    }

    @Override
    public <T extends Comparable<? super T>> void mergesort(T[] arr) {
        // TODO Auto-generated method stub
    }

    @Override
    public int[] radixsort(int[] arr) {
        // TODO Auto-generated method stub
        return null;
    }

    private <T extends Comparable<? super T>> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private <T extends Comparable<? super T>> void debug(T[] arr) {
        System.out.print("[ ");
        for (T item : arr) {
            System.out.print(item + "   ");
        }
        System.out.print("]\n");
    }

}
