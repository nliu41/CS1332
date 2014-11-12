/**
 * StacksQueue
 *
 * Implementation of a queue using
 * two stacks as the backing store.
 *
 * @author Paras Jain
 * @version 1.0
 */


public class StacksQueue<T> implements QueueInterface<T> {
    private ArrayStack<T> in, out;

    public StacksQueue() {
        in = new ArrayStack<T>();
        out = new ArrayStack<T>();
    }

    @Override
    public void enqueue(T o) {
        in.push(o);
    }

    @Override
    public T dequeue() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }

        return out.pop();
    }

    @Override
    public boolean isEmpty() {
        return in.isEmpty() && out.isEmpty();
    }
}