/**
 * Your queue implementation. Don't add any new public methods.
 *
 * @author Paras Jain
 * @version 1.0
 */
public class Queue<T> implements QueueInterface<T> {

    private LinkedListInterface<T> queue;

    public Queue() {
        queue = new DoublyLinkedList<T>();
    }

    @Override
    public void enqueue(T t) {
        queue.addToFront(t);
    }

    @Override
    public T dequeue() {
        return queue.removeFromBack();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

}
