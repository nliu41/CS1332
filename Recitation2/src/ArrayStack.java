/**
 * ArrayStack
 *
 * Implementation of a stack using an array
 * as the backing store.
 *
 * @author Paras Jain
 * @version 1.0
 */

public class ArrayStack<T> implements StackInterface<T> {

    private static final int STACK_SIZE = 200;

    private T[] data;
    private int size;

    public ArrayStack() {
        data = (T[]) new Object[STACK_SIZE];
    }

    @Override
    public void push(T t) {
        if (size > STACK_SIZE) {
            throw new RuntimeException("Stack is full");
        } else if (t == null) {
            throw new IllegalArgumentException("Null argument");
        }
        data[size] = t;
        size++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        size--;
        return data[size];
    }

    @Override
    public T[] toArray() {
        return data;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }
}