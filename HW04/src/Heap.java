public class Heap<T extends Comparable<? super T>> implements HeapInterface<T>,
        Gradable<T> {

    private T[] data;
    private int size;

    public Heap() {
        data = (T[]) new Comparable[10];
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("null item");
        } else {
            boostSize();
            data[++size] = item;
            if (size > 1) {
                floatUp(size);
            }
        }
    }


    /**
     * floatUp swaps up elements after an add, recursively
     * restoring the heap property
     * by allowing the smallest elements to float up to the top
     *
     * @param n is the number of the index to compare
     */
    private void floatUp(int n) {
        if (n > 1) {
            int parent = n / 2;
            if (data[n].compareTo(data[parent]) < 0) { // parent > child
                swap(n, parent);
                floatUp(parent);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    @Override
    public T peek() {
        T value = null;
        if (!isEmpty()) {
            value = data[1];
        }
        return value;
    }

    @Override
    public T remove() {
        T temp = null;
        if (!isEmpty()) {
            temp = data[1];
            if (size > 1) {
                data[1] = data[size];
                sink(1);
            }
            data[size--] = null;
        }
        return temp;
    }

    /**
     * sink is a recursive method to sink the largest elements
     * to the bottom of the heap
     *
     * @param n is the number of the index to compare
     */
    private void sink(int n) {
        T min = minValue(minValue(get(2 * n), get(2 * n + 1)), get(n));
        if (get(n) != null && min != data[n]) {
            if (min == get(2 * n)) {
                swap(n, 2 * n);
                sink(2 * n);
            } else {
                swap(n, 2 * n + 1);
                sink(2 * n + 1);
            }
        }
    }

    /**
     * swap is a private helper method to swap two items,
     * checking if the two elements are null
     *
     * @param n1 is element 1
     * @param n2 is element 2
     */
    private void swap(int n1, int n2) {
        if (get(n1) != null && get(n2) != null) {
            T temp = data[n1];
            data[n1] = data[n2];
            data[n2] = temp;
        }
    }

    /**
     * minValue checkS which of two objects are the minimum, also checking nulls
     *
     * @param a is the first element
     * @param b is the second element
     * @return is the minimum value
     */
    private T minValue(T a, T b) {
        if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        }
        return (a.compareTo(b) <= 0) ? a : b;
    }

    /**
     * boostSize grows the array as necessary
     */
    private void boostSize() {
        if (size >= data.length - 1) {
            T[] temp = (T[]) new Comparable[(size + 1) * 2];
            for (int i = 1; i < data.length; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
    }

    /**
     * get returns a get call ensuring bounds are checked
     *
     * @param i is the index of the item to get
     * @return is the item (or null if the index is out of bounds)
     */
    private T get(int i) {
        T temp = null;
        if (i <= size) {
            temp = data[i];
        }
        return temp;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T[] toArray() {
        return data;
    }
}
