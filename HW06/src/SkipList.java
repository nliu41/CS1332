import java.util.Set;
import java.util.TreeSet;

public class SkipList<T extends Comparable<? super T>>
        implements SkipListInterface<T> {

    private CoinFlipper coinFlipper;
    private int size, height;
    private Node<T> head;

    /**
     * Constructs a SkipList object that stores data in ascending order
     * when an item is inserted, the flipper is called until it returns a tails
     * if for an item the flipper returns n heads, the corresponding node has
     * n + 1 levels
     *
     * @param coinFlipper the source of randomness
     */
    public SkipList(CoinFlipper coinFlipper) {
        this.coinFlipper = coinFlipper;
        clear();
    }

    @Override
    public T first() {
        Node<T> firstNode = null;
        if (size > 0) {
            firstNode = firstNode();
        }
        return (firstNode == null) ? null : firstNode.getData();
    }

    @Override
    public T last() {
        Node<T> lastNode = null;
        if (size > 0) {
            Node<T> current = head;

            while (current.getNext() != null
                    && current.getNext().getData() != null) {
                current = current.getNext();
            }

            while (current.getDown() != null) {
                current = current.getDown();
                while (current.getNext() != null
                        && current.getNext().getData() != null) {
                    current = current.getNext();
                }
            }

            lastNode = current;
        }
        return (lastNode == null) ? null : lastNode.getData();
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        return (size >= 1) && get(data) != null;
    }

    @Override
    public void put(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }

        int heightToPut = 0;
        while (coinFlipper.flipCoin() == CoinFlipper.Coin.HEADS) {
            heightToPut++;
        }

        while (height < heightToPut) { // add levels to match
            Node<T> headPrime = head, tailPrime = head, beg, end;
            end = new Node<T>(null, ++height);
            beg = new Node<T>(null, height);
            while (tailPrime.getNext() != null) {
                tailPrime = tailPrime.getNext();
            }
            beg.setNext(end);
            headPrime.setUp(beg);
            beg.setDown(headPrime);
            tailPrime.setUp(end);
            end.setDown(tailPrime);
            head = beg;
        }

        put(head, data, heightToPut);
        size++;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }

        T returnValue = null;

        if (size == 1) {
            returnValue = first();
            clear();
        } else if (size > 1) {
            Node<T> predFirst = get(head, data);
            if (predFirst != null) {
                returnValue = predFirst.getNext().getData();
                remove(predFirst, data);
                size--;
                if (size == 0) {
                    clear();
                }
                // pruneLevels(); // Not necessary
            }
        }

        return returnValue;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }

        Node<T> pred = null;
        if (size > 0) {
            pred = get(head, data);
        }

        return (pred == null
                || pred.getNext() == null
                || pred.getNext().getData() == null)
                ? null : pred.getNext().getData();

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Node<T> end = new Node<T>(null, 0);
        Node<T> beg = new Node<T>(null, 0);

        beg.setNext(end);
        head = beg;

        size = 0;
        height = 0;
    }

    @Override
    public Set<T> dataSet() {
        Set<T> data = new TreeSet<>();
        if (size > 0) {
            Node<T> current = firstNode();
            while (current != null && current.getData() != null) {
                data.add(current.getData());
                current = current.getNext();
            }
        }
        return data;
    }

    /**
     * Recursive method for remove that works to remove
     * an item below an item (given node)
     * @param first is the node to search from
     * @param data is the data to remove
     */
    private void remove(Node<T> first, T data) {
        if (first != null && first.getNext() != null
                && first.getNext().getData() != null) {
            Node<T> middle = first.getNext();
            Node<T> last = middle.getNext();
            first.setNext(last);

            middle.setNext(null);
            middle.setUp(null);
            middle.setDown(null);

            remove(get(first.getDown(), data), data);
        }
    }

    /**
     * first helper that returns first element
     * @return first node
     */
    private Node<T> firstNode() {
        if (size < 1) {
            return null;
        }

        Node<T> current = head;
        while (current.getDown() != null) {
            current = current.getDown();
        }

        return current.getNext();
    }


    /**
     * Put method helper
     * @param parent parent node to traverse below
     * @param data data to place in
     */
    private void put(Node<T> parent, T data, int heightToPut) {
        Node<T> rowHeader, predecessor,
                insertedElement, previousInsertedElement;
        insertedElement = null;

        // 1) iterate down until current item level = height to put

        Node<T> current = null;
        if (!(heightToPut > height || heightToPut < 0)) {
            current = head;
            while (current != null && current.getLevel() > heightToPut) {
                while (current != null
                        && current.getNext() != null
                        && current.getNext().getData() != null
                        && data.compareTo(current.getNext().getData()) > 0) {
                    current = current.getNext();
                }
                current = current.getDown();
            }
        }

        rowHeader = current;

        while (rowHeader != null) {
            // 2) insert into row
            predecessor = getPredecessorGivenSentinel(data, rowHeader);
            previousInsertedElement = insertedElement;
            // insertedElement = putAfter(predecessor, data);
            Node<T> after = predecessor.getNext();
            predecessor.setNext(new Node<T>(data, predecessor.getLevel()));
            predecessor.getNext().setNext(after);
            insertedElement = predecessor.getNext();

            // 3) link elements together (up and down)
            insertedElement.setUp(previousInsertedElement);
            if (previousInsertedElement != null) {
                previousInsertedElement.setDown(insertedElement);
            }

            // 4) repeat with predecessor.getDown()
            rowHeader = predecessor.getDown();
        }
    }

    /**
     * returns the predecessor for the data given a sentinel node
     * @param data the data to search for
     * @param sentinel the first node in the row
     * @return predecessor
     */
    private Node<T> getPredecessorGivenSentinel(T data, Node<T> sentinel) {
        // while data > next element
        if (size > 0) {
            while (sentinel != null
                    && sentinel.getNext() != null
                    && sentinel.getNext().getData() != null
                    && data.compareTo(sentinel.getNext().getData()) > 0) {
                sentinel = sentinel.getNext();
            }
        }
        return sentinel;
    }

    /**
     * Returns the predecessor on the row of the
     * first occurrence of the data item
     * @param parent parent to search from
     * @param data data item to search for
     * @return predecessor
     */
    private Node<T> get(Node<T> parent, T data) {
        Node<T> pred = getPredecessorGivenSentinel(data, parent);
        // check if it hit the end
        if (pred != null && pred.getNext() != null
                && pred.getNext().getData() != null
                && pred.getNext().getData().equals(data)) {
            // check if next is equal else go down a level
            return pred;
        } else if (pred != null && pred.getDown() != null) {
            // if we can go down a level
            return get(pred.getDown(), data);
        }
        return null;
    }
}