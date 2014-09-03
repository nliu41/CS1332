/**
 * Doubly linked list implementation
 * @author Paras Jain
 * @version 1.0
 */

public class DoublyLinkedList<T> implements LinkedListInterface<T> {

    private Node head, tail;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) { // should also include empty array edge case
            throw new IndexOutOfBoundsException("Index out of bounds of list");
        } else if (index == 0) {  // shortcut for beginning or end
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            Node<T> item, before, after;
            item = getIndex(index);
            before = item.getPrevious();
            after = item.getNext();

            Node<T> newNode = new Node<T>(data);

            // Link it to the previous node
            before.setNext(newNode);
            newNode.setPrevious(before);

            item.setPrevious(newNode);
            newNode.setNext(item);

            changeSize(1);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) { // should also include empty array edge case
            throw new IndexOutOfBoundsException("Index out of bounds of list");
        }
        return getIndex(index).getData();
    }


    /* TODO comment */
    private Node<T> getIndex(int index) {
        if (index < 0 || index >= size) { // should also include empty array edge case
            throw new IndexOutOfBoundsException("Index out of bounds of list");
        } else if (index == 0) {  // shortcut for beginning or end
            return head;
        } else if (index == size - 1) {
            return tail;
        }

        Node<T> current = head;
        int i = 0;
        while (current != null && i != index) {
            current = current.getNext();
            i++;

            if (i == index) {
                return current;
            }
        }

        return null;
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) { // should also include empty array edge case
            throw new IndexOutOfBoundsException("Index out of bounds of list");
        } else if (index == 0) {  // shortcut for beginning or end
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        }

        Node<T> item, before, after;
        item = getIndex(index);
        before = item.getPrevious();
        after = item.getNext();

        item.setNext(null);
        item.setPrevious(null);

        before.setNext(after);
        after.setPrevious(before);

        changeSize(-1);

        return item.getData();
    }

    @Override
    public void addToFront(T t) {
        if (isEmpty()) { // if empty, create a new list
            addEmptyList(t);
        } else {
            Node<T> oldHead = head;
            head = new Node<T>(t);
            head.setNext(oldHead);
            oldHead.setPrevious(head);
            changeSize(1);
        }
    }

    @Override
    public void addToBack(T t) {
        if (isEmpty()) { // if empty, create a new list
            addEmptyList(t);
        } else {
            Node<T> oldTail = tail;
            tail = new Node<T>(t);
            oldTail.setNext(tail);
            tail.setPrevious(oldTail);
            changeSize(1);
        }
    }

    /**
     * Adds an item to an empty list, ensuring that head and tail are correctly set
     *
     * Must be O(1)
     */
    private void addEmptyList(T t) { // node that has a next that points to itself
        Node<T> temp = new Node<T>(t);
        temp.setNext(null);
        temp.setPrevious(null);
        head = temp;
        tail = temp;
        changeSize(1);
    }

    @Override
    public T removeFromFront() {
        Node<T> removednode = head;
        if (isEmpty()) {
            return null;
        } else if (size() == 1) { // edge case - single item in list
            clear();
        } else { // normal list
            Node<T> oldHead = head;
            head = head.getNext();

            oldHead.setNext(null); // isolate this node
            oldHead.setPrevious(null);

            head.setPrevious(null); // update previous reference

            changeSize(-1);
        }
        return removednode != null ? removednode.getData() : null;
    }

    @Override
    public T removeFromBack() {
        Node<T> removednode = tail;
        if (isEmpty()) {
            return null;
        } else if (size() == 1) { // edge case - single item in list
            clear();
        } else { // normal list
            Node<T> oldTail = tail;
            tail = tail.getPrevious();

            oldTail.setNext(null); // orhpan this node
            oldTail.setPrevious(null);

            tail.setNext(null); // update previous reference

            changeSize(-1);
        }
        return removednode != null ? removednode.getData() : null;
    }

    @Override
    public T[] toArray() {
        T[] list = (T[]) new Object[size()];

        Node<T> current = head;

        if (isEmpty()) {
            return list;
        }

        int i = 0;
        do {
            list[i] = (T) current.getData();
            i++;
            current = current.getNext();
        } while (current != null);

        return list;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Updates the size of the linked list. Changes to size are in a separate method to allow easier debugging and also
     * to allow checking to see if the list should be cleared to clean up head and tail references.
     *
     * Must be O(1)
     *
     * @return int size representing the length of the linked list
     */
    private int changeSize(int delta) {
        size += delta;
        if (size < 1) {
            clear();
        }
        return size;
    }


    /**
     * Reference to the head node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
     *
     * You will get a 0 if you do not implement this method.
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
     * You will get a 0 if you do not implement this method.
     *
     * @return Node representing the tail of the linked list
     */
    public Node<T> getTail() {
        return tail;
    }
}
