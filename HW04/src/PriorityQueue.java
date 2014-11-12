public class PriorityQueue<T extends Comparable<? super T>> implements
        PriorityQueueInterface<T>, Gradable<T> {

    private Heap<T> heap;

    public PriorityQueue() {
        heap = new Heap<T>();
    }

    @Override
    public void insert(T item) {
        heap.add(item);
    }

    @Override
    public T findMin() {
        return heap.peek();
    }

    @Override
    public T deleteMin() {
        return heap.remove();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public void makeEmpty() {
        heap = new Heap<T>();
    }

    @Override
    public T[] toArray() {
        return heap.toArray();
    }


}