/**
 * CircularLinkedList implementation
 * @author Paras Jain
 * @version 1.0
 */

import java.util.Arrays;

public class CircularLinkedList<T> implements LinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds of list");
        }

        if (index == 0) { // O(1)
            addToFront(data);
        } else if (index > size - 1) { // O(1)
            addToBack(data);
        } else { // O(n)
            Node<T> temp = new Node<T>(data);
            Node<T> current = head; // element is in the middle of the list
            int i = 0;
            while (i < index - 1) {
                i++;
                current = current.getNext();
            }

            temp.setNext(current.getNext());
            current.setNext(temp);

            changeSize(1);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) { // should also include empty array edge case
            throw new ArrayIndexOutOfBoundsException("Index out of bounds of list");
        }

        if (index == 0) {  // shortcut for beginning or end
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        }

        Node<T> current = head; // element is in the middle of the list
        int i = 0;
        do {
            if (i == index) {
                return current.getData();
            }

            i++;
            current = current.getNext();
        } while (current != head);

        return null; // ? when would this get hit?
    }

    @Override
    public T removeAtIndex(int index) {
        // edge cases: remove front, remove end
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds of list");
        }

        if (index == 0) { // O(1)
            return removeFromFront();
        } else if (index == size - 1) { // O(1)
            return removeFromBack();
        } else { // O(n)
            Node<T> current = head; // element is in the middle of the list
            int i = 0;

            do {
                if (i == index - 1) {
                    T removeData = current.getNext().getData(); // data for n
                    Node<T> newNext = current.getNext().getNext(); // node n+1
                    current.getNext().setNext(null); // orphan the node to be removed, node n, should get gc'ed
                    current.setNext(newNext); // set n-1 next to n+1

                    changeSize(-1); // decrement size

                    return removeData;
                }

                i++;
                current = current.getNext();
            } while (current != head);

            return null;
        }
    }

    @Override
    public void addToFront(T t) {
        if (isEmpty()) { // if empty, create a new list
            addEmptyList(t);
        } else {
            head = new Node<T>(t, head);
            tail.setNext(head); // update head reference in tail
            changeSize(1);
        }
    }

    @Override
    public void addToBack(T t) {
        if (isEmpty()) { // if empty, create a new list
            addEmptyList(t);
        } else {
            Node<T> temp = new Node<T>(t, head);
            tail.setNext(temp);
            tail = temp;
            changeSize(1);
        }
    }

    @Override
    public T removeFromFront() {
        Node<T> removednode = head;
        if (isEmpty()) { // edge case - empty list
//            throw new IllegalStateException("List is empty, cannot remove item.");
            return null;
        } else if (size() == 1) { // edge case - single item in list
            clear();
        } else { // normal list
            head = head.getNext();
            tail.getNext().setNext(null); // orphan the removed node
            tail.setNext(head);
            changeSize(-1);
        }
        return removednode != null ? removednode.getData() : null;
    }

    @Override
    public T removeFromBack() {
        Node<T> removednode = tail; // preserve a pointer to the tail
        if (isEmpty()) { // edge case - empty list
//          throw new IllegalStateException("List is empty, cannot remove item.");
            return null;
        } else if (size() == 1) { // edge case - single item in list, preserve O(1)
            clear();
        } else { // normal list, O(n)

            // iterate to node size-2
            // set tail's next to null to orphan it
            // set size-2's next to head
            // decrement size

            Node<T> current = head; // element is in the middle of the list
            int i = 0;
            while (i < size - 2) {
                i++;
                current = current.getNext();
            }
            changeSize(-1);
            tail.setNext(null); // orphan the tail
            current.setNext(head);
            tail = current;
        }
        return removednode != null ? removednode.getData() : null;
    }

    @Override
    public T[] toList() {
        T[] list = (T[]) new Object[size()];

        Node<T> current = head;

        if (head == null) {
            return list;
        }

        int i = 0;
        do {
            list[i] = (T) current.getData();
            i++;
            current = current.getNext();
        } while (current != head);

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
     * Adds an item to an empty list, ensuring that head and tail are correctly set
     *
     * Must be O(1)
     */
    private void addEmptyList(T t) { // node that has a next that points to itself
        Node<T> temp = new Node<T>(t);
        temp.setNext(temp);
        head = temp;
        tail = temp;
        changeSize(1);
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
        return size + ":    " + Arrays.toString(toList());
    }
}