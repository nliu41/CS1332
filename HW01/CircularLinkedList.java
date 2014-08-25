/**
 * CircularLinkedList implementation
 * @author Paras Jain
 * @version 1.0
 */

public class CircularLinkedList<T> implements LinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;

    @Override
    public void addAtIndex(int index, T data) {
        // edge cases: add to front, add to end
        
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T removeAtIndex(int index) {
        // edge cases: remove front, remove end
        return null;
        
    }

    @Override
    public void addToFront(T t) {
        head = new Node<T>(t, head);
    }

    @Override
    public void addToBack(T t) {

    }

    @Override
    public T removeFromFront() {
        return null;
    }

    @Override
    public T removeFromBack() {
        return null;
    }

    @Override
    public T[] toList() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        
    }

    /**
     * Reference to the head node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
     *
     * @return Node representing the head of the linked list
     */
    public Node<T> getHead() {
        return head;
    }

    /**
     * Reference to the tail node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
     *
     * @return Node representing the tail of the linked list
     */
    public Node<T> getTail() {
        return tail;
    }

    /**
     * This method is for your testing purposes.
     * You may choose to implement it if you wish.
     */
    @Override
    public String toString() {
        return "";
    }
}

