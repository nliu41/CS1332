import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DisjointSets<T> implements DisjointSetsInterface<T> {
    //Should be a map of data to its parent; root data maps to itself.
    private Map<T, Node> set;

    /**
     * @param setItems the initial items (sameSet and merge will never be called
     * with items not in this set, this set will never contain null elements).
     */
    public DisjointSets(Set<T> setItems) {
        set = new HashMap<>(2 * setItems.size());
        setItems.forEach((T item) -> set.put(item, new Node(item)));
    }

    /**
     * If either parameter is null, throw an {@code IllegalArgumentException}.
     *
     * @param u
     * @param v
     * @return true if the items given are in the same set, false otherwise
     */
    @Override
    public boolean sameSet(T u, T v) {
        if (u == null || v == null) {
            throw new IllegalArgumentException();
        }

        return find(u).equals(find(v));
    }

    /**
     * Finds which set an item is in
     * @param start is the node to start searching from
     * @return the root of the set
     */
    private T find(T start) {
        Node parent = set.get(start);
        if (parent.getData().equals(start)) {
            return start;
        }
        return find(parent.getData());
    }

    @Override
    public void merge(T u, T v) {
        if (u == null || v == null) {
            throw new IllegalArgumentException();
        } else if (!u.equals(v)) { // u != v
            Node small, large;

            // set up smaller, larger ranks
            if (set.get(u).getRank() < set.get(v).getRank()) {
                small = set.get(u);
                large = set.get(v);
            } else {
                large = set.get(u);
                small = set.get(v);
            }

            // update larger rank
            large.setRank(large.getRank() + small.getRank());
            set.put(small.getData(), large);


            Node setRoot = set.get(find(small.getData()));
            compress(small.getData(), setRoot); // now compress subtree
        }
    }

    /**
     * Recursively compresses a tree
     * @param root is the item to compress above
     * @param setRoot is the root of the set
     */
    private void compress(T root, Node setRoot) {
        Node parent = set.get(root);
        set.put(root, setRoot);
        if (!parent.equals(setRoot)) {
            compress(parent.data, setRoot);
        }
    }

    private class Node {
        private T data;
        private int rank;

        public Node(T data) {
            this.data = data;
            rank = 0;
        }

        public T getData() {
            return data;
        }

        public void setData(T newdata) {
            data = newdata;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int newrank) {
            rank = newrank;
        }

        @Override
        public boolean equals(Object o) {
            if (o != null) { //  && o instanceof Node
                return data == ((Node) o).data;
            } else {
                return false;
            }
        }
    }
}
