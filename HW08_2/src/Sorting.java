import java.util.ArrayList;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;

/**
  * Sorting implementation
  * CS 1332 : Fall 2014
  * @author Paras Jain
  * @version 1.0
  */
public class Sorting implements SortingInterface {

    // Do not add any instance variables.

    @Override
    public <T extends Comparable<? super T>> void bubblesort(T[] arr) {
        checkArgs(arr);
        if (arr.length > 1) {
            int swaps = -1;
            for (int o = 0; swaps != 0 && o < arr.length - 1; o++) {
                swaps = 0;
                for (int i = 0; i < arr.length - o - 1; i++) {
                    if (arr[i].compareTo(arr[i + 1]) > 0) {
                        swap(arr, i, i + 1);
                        swaps++;
                    }
                }
            }
        }
    }

    @Override
    public <T extends Comparable<? super T>> void insertionsort(T[] arr) {
        checkArgs(arr);
        if (arr.length > 1) {
            for (int i = 1; i < arr.length; i++) {
                for (int j = i;
                     j > 0 && arr[j - 1].compareTo(arr[j]) > 0;
                     j--) {
                    swap(arr, j, j - 1);
                }
            }
        }
    }

    @Override
    public <T extends Comparable<? super T>> void selectionsort(T[] arr) {
        checkArgs(arr);
        if (arr.length > 1) {
            int minIndex = 0;
            for (int i = 0; i < arr.length - 1; i++) {
                minIndex = i;
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[j].compareTo(arr[minIndex]) < 0) {
                        minIndex = j;
                    }
                }
                if (minIndex != i) {
                    swap(arr, i, minIndex);
                }
            }
        }
    }

    @Override
    public <T extends Comparable<? super T>> void quicksort(T[] arr,
                                                            Random r) {
        checkArgs(arr);
        if (arr.length > 1) {
            quicksort(arr, r, 0, arr.length - 1);
        }
    }

    @Override
    public <T extends Comparable<? super T>> void mergesort(T[] arr) {
        checkArgs(arr);

        if (arr.length > 1) {
            // List partitioning
            int middle = arr.length / 2;
            T[] list1 = (T[]) new Comparable[arr.length / 2];
            int tempIndexCount = ((arr.length % 2 == 0)
                    ? arr.length / 2
                    : arr.length / 2 + 1); // odd vs even shift
            T[] list2 = (T[]) new Comparable[tempIndexCount];

            for (int i = 0; i < middle; i++) {
                list1[i] = arr[i];
            }
            for (int i = 0; middle + i < arr.length; i++) {
                list2[i] = arr[middle + i];
            }

            // Sub-problems
            mergesort(list1);
            mergesort(list2);

            // Merge sub-problems
            merge(arr, list1, list2);
        }
    }

    @Override
    public int[] radixsort(int[] arr) {
        if (arr.length > 1) {
            ArrayList<Integer> negative =
                    new ArrayList<Integer>(arr.length);
            ArrayList<Integer> positive =
                    new ArrayList<Integer>(arr.length);

            for (int i : arr) {
                ((i < 0) ? negative : positive).add(i);
            }

            radixsortHelper(negative);
            radixsortHelper(positive);

            int[] negarr = toPrimitiveArray(negative);
            int[] posarr = toPrimitiveArray(positive);

            arr = merge(arr, reverse(negarr), posarr);
        }

        return arr;
    }

    /**
     * Converts a List<Integer> into an int[]
     * @param list to be processed
     * @return an int[] array representing the data in list
     */
    private int[] toPrimitiveArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    /**
     * Radix sort helper that sorts a List<Integer>
     * @param arr to be sorted
     */
    private void radixsortHelper(List<Integer> arr) {
        if (arr == null) {
            throw new IllegalArgumentException();
        } else if (arr.size() >= 2) {
            // radix sort :P
            LinkedList<Integer>[] digitList =
                    (LinkedList<Integer>[]) new LinkedList[10];

            int maxDigits = getMaxDigits(arr);
            for (int currentDigit = 0;
                 currentDigit <= maxDigits; currentDigit++) {
                // fill buckets
                for (int i = 0; i < arr.size(); i++) {
                    int currentItem = arr.get(i);
                    int tempDigit =
                            Math.abs(getDigit(currentItem, currentDigit));

                    if (digitList[tempDigit] == null) {
                        digitList[tempDigit] = new LinkedList<Integer>();
                    }
                    LinkedList<Integer> currentlist = digitList[tempDigit];

                    currentlist.add(currentItem);
                }

                // copy the value over
                int i = 0;
                for (LinkedList ll : digitList) {
                    if (ll != null) {
                        for (Object n : ll) {
                            arr.set(i++, (Integer) n);
                        }
                    }
                }

                digitList = (LinkedList<Integer>[]) new LinkedList[10];
            }
        }
    }

    /**
     * Returns the digit at a position i
     * @param n the number to find the digit of
     * @param i the index to get
     * @return the value of the digit
     */
    private int getDigit(int n, int i) {
        int divFactor = 1;
        for (int j = 0; j < i; j++) {
            divFactor *= 10;
        }
        return (int) ((n / divFactor) % 10);
    }

    /**
     * Counts number of digits, finding the
     * maximum number of digits in a List<Integer>
     * @param arr to search
     * @return the max number of digits
     */
    private int getMaxDigits(List<Integer> arr) {
        int max = Integer.MIN_VALUE;
        for (int n : arr) {
            int ndigits = 0;
            do {
                n /= 10;
                ndigits++;
            } while (n != 0);

            max = Math.max(max, ndigits);
        }
        return max;
    }

    /**
     * Swaps two elements at each given index
     * @param arr is the array to sort
     * @param i is the first index
     * @param j is the second index to swap arr[i] with (arr[j])
     * @param <T> is a Type argument
     */
    private <T extends Comparable<? super T>> void swap(T[] arr,
                                                        int i,
                                                        int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Checks args for valid constraints, throwing exceptions if needed
     * @param arr is the array to sort
     * @param <T> Type argument
     */
    private <T extends Comparable<? super T>> void checkArgs(T[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Merges two lists (should be stable) into one original list
     * @param arr is the array to merge back into
     * @param list1 is the first list to merge with
     * @param list2 is the second list to merge with
     * @param <T> is a Type argument
     */
    private <T extends Comparable<? super T>> void merge(T[] arr,
                                                         T[] list1,
                                                         T[] list2) {
        int l1index = 0, l2index = 0;

        // compare each
        while (l1index < list1.length && l2index < list2.length) {
            if (list2[l2index].compareTo(list1[l1index]) < 0) { // l2 > l1
                arr[l1index + l2index] = list2[l2index++];
            } else {
                arr[l1index + l2index] = list1[l1index++];
            }
        }

        // copy over remaining values
        while (l1index < list1.length) {
            arr[l1index + l2index] = list1[l1index++];
        }
        while (l2index < list2.length) {
            arr[l1index + l2index] = list2[l2index++];
        }
    }

    /**
     * Merges two lists (should be stable) into one original list
     * @param arr is the array to merge back into
     * @param list1 is the first list to merge with
     * @param list2 is the second list to merge with
     * @param <T> is a Type argument
     * @return the merged list
     */
    private <T extends Comparable<? super T>> int[] merge(int[] arr,
                                                          int[] list1,
                                                          int[] list2) {
        int l1index = 0, l2index = 0;

        // compare each
        while (l1index < list1.length && l2index < list2.length) {
            if (list2[l2index] < (list1[l1index])) { // l2 > l1
                arr[l1index + l2index] = list2[l2index++];
            } else {
                arr[l1index + l2index] = list1[l1index++];
            }
        }

        // copy over remaining values
        while (l1index < list1.length) {
            arr[l1index + l2index] = list1[l1index++];
        }
        while (l2index < list2.length) {
            arr[l1index + l2index] = list2[l2index++];
        }

        return arr;
    }

    /**
     * Performs QuickSort partition, im-place
     * @param arr the array to sort
     * @param r the source of randomness
     * @param l the low index
     * @param h the high index
     * @param <T> the type argument
     * @return the array
     */
    private <T extends Comparable<? super T>> int quicksortPartition(T[] arr,
                                                                     Random r,
                                                                     int l,
                                                                     int h) {
        int pivotIndex = r.nextInt(h - l) + l;
        swap(arr, pivotIndex, h);
        int m = l;

        for (int i = l; i < h; i++) {
            if (arr[i].compareTo(arr[h]) < 0) {
                swap(arr, i, m++);
            }
        }

        swap(arr, m, h);
        return m;
    }

    /**
     * Reverses an array
     * @param arr int[] array to reverse
     * @return int[] reversed array
     */
    private int[] reverse(int[] arr) {
        int temp = 0;
        for (int i = 0; i < arr.length / 2; i++) {
            temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
        return arr;
    }


    /**
     * Quicksort helper method that actually conducts the sort
     * @param arr array to be sorted
     * @param r source of randomness
     * @param l low index
     * @param h high index
     * @param <T> Type argument
     */
    private <T extends Comparable<? super T>> void quicksort(T[] arr,
                                                             Random r,
                                                             int l,
                                                             int h) {
        if (arr.length > 1 && l < h) {
            // Partition
            int m = quicksortPartition(arr, r, l, h);

            // sort
            quicksort(arr, r, l, m - 1);
            quicksort(arr, r, m + 1, h);
        }
    }
}
