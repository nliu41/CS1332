import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BST<T extends Comparable<T>> implements BSTInterface<T> {
    private Node<T> root;
    private int size;

    @Override
    public void add(T data) {
        if (root == null) { // empty tree
            root = new Node(data);
            changeSize(1);
        } else { // subtree exists
            addSubtree(root, data);
        }
    }

    /**
     * This function adds a node to a subtree, recursively helping the add function.
     *
     * @param base is the node of the subtree root
     * @param data is the data to add to the tree
     */
    private void addSubtree(Node base, T data) {
        int compare = base.getData().compareTo(data);
        Node ref;

        if (compare == 0) {
            // equals! duplicate...
            // TA's: how do we handle duplicates?
            throw new RuntimeException("Duplicate item");
        } else if (compare > 0) { // A < B, left tree
            if (base.getLeft() == null) {
                base.setLeft(new Node(data));
                changeSize(1);
            } else {
                addSubtree(base.getLeft(), data);
            }
        } else if (compare < 0) { // A > B, right tree
            if (base.getRight() == null) {
                base.setRight(new Node(data));
                changeSize(1);
            } else {
                addSubtree(base.getRight(), data);
            }
        }
    }


    @Override
    public T remove(T data) {
        // case with two children (10)
        //   Node pred = findPredecessor(current) - swap node to delete with predecessor
        //   swap data of node to remove with predecessor
        //   remove old predecessor (either 1 left child or no children)

        // first check if root
        // get parent node of item to remove

        if (size == 0 || (size == 1 && root.getData() != data)) {
            return null; // no data found in tree (empty) or single node that does not match the data to remove
        } else if (size == 1) {
            T temp = root.getData();
            clear(); // must match the root
            return temp;
        } else if (root.getData() == data) {
            return remove(data, root, root, 1);
        } else { // more than one node in the tree... send off to recursive helper
            return remove(data, null, root);
        }
    }

    /**
     * This function is a helper to the remove method that functions
     * by searching for the node as necessary and determining the direction
     * of the child.
     *
     * This is an overloaded method.
     *
     * @param data is the data to remove
     * @param parent is the parent of the current element
     * @param current is the current element to process
     *
     * @return the removed node
     */
    private T remove(T data, Node<T> parent, Node<T> current) {
        int compare = current.getData().compareTo(data);
        if (compare > 0) { // data < current.getdata
            if (current.getLeft() != null) {
                return remove(data, current, current.getLeft());
            }
            return null;
        } else if (compare < 0) {
            if (current.getRight() != null) {
                return remove(data, current, current.getRight());
            }
            return null;
        } else {
            if (parent.getLeft() == current) {
                return remove(data, parent, current, -1);
            } else if (parent.getRight() == current) {
                return remove(data, parent, current, 1);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    /**
     * This function is a helper to the remove method that functions
     * by removing a particular node given it's parent node.
     *
     *
     * @param data is the data to remove
     * @param parent is the parent of the current element
     * @param current is the current element to process
     * @param direction indicates the direction to process current (-1 being left, 1 being right)
     *
     * @return the removed node
     */
    private T remove(T data, Node<T> parent, Node<T> current, int direction) {
        T temp = current.getData();
        if (current.getLeft() != null && current.getRight() != null) { // two nodes...

            Node<T> currentPredecessor = current.getLeft();
            while (currentPredecessor.getRight() != null) {
                currentPredecessor = currentPredecessor.getRight();
            }

            current.setData(currentPredecessor.getData());
            remove(currentPredecessor.getData(), current, current.getRight());

        } else if (direction < 0) { // one node, promote left child up, also handles leaf
            if (current.getLeft() == null) {
                parent.setLeft(current.getRight());
            } else {
                parent.setLeft(current.getLeft());
            }
        } else if (direction > 0) { // one node, promote right node up, also handles leaf
            if (current.getRight() == null) {
                parent.setRight(current.getLeft());
            } else {
                parent.setRight(current.getRight());
            }
        }

        return temp;
    }

    @Override
    public T get(T data) {
        return get(root, data);
    }

    /**
     * This function is a helper to the get method that gets the data
     * for the relevant node as passed to the function
     *
     *
     * @param parent is the parent node to search below
     * @param data is the data to find
     *
     * @return the found node
     */
    private T get(Node<T> parent, T data) {
        if (parent == null) return null;

        int compare = parent.getData().compareTo(data);
        if (compare > 0) {
            return get(parent.getLeft(), data);
        } else if (compare < 0) {
            return get(parent.getRight(), data);
        } else {
            return parent.getData();
        }
    }

    @Override
    public boolean contains(T data) {
        return contains(root, data);
    }

    /**
     * This function is a helper to the contains method that
     * searches for data below a particular node
     *
     *
     * @param parent is the parent node to search below
     * @param data is the data to find
     *
     * @return boolean if found
     */
    private boolean contains(Node<T> parent, T data) {
        if (parent == null) return false;

        int compare = parent.getData().compareTo(data);
        if (compare > 0) {
            return contains(parent.getLeft(), data);
        } else if (compare < 0) {
            return contains(parent.getRight(), data);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * This function changes the size of the BST that ensures that the
     * tree is cleared when empty
     *
     *
     * @param delta delta is the amount to change size by
     *
     * @return the new size
     */
    private int changeSize(int delta) {
        size += delta;
        if (size == 0) {
            clear();
        }
        return size;
    }

    @Override
    public List<T> preorder() {
        ArrayList<T> list = new ArrayList<T>(size);
        return preorder(list, root);
    }

    /**
     * This function is a recursive helper for preorder
     * to traverse a child node
     *
     *
     * @param list is the list to create and add to
     * @param parent is the parent node to search under
     *
     * @return a preorder traversal of the tree
     */
    private List<T> preorder(List<T> list, Node<T> parent) {
        if (parent == null) return list;
        list.add(parent.getData());
        list = preorder(list, parent.getLeft());
        list = preorder(list, parent.getRight());
        return list;
    }

    @Override
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<T>(size);
        return postorder(list, root);
    }

    /**
     * This function is a recursive helper for postorder
     * to traverse a child node
     *
     *
     * @param list is the list to create and add to
     * @param parent is the parent node to search under
     *
     * @return a postorder traversal of the tree
     */
    private List<T> postorder(List<T> list, Node<T> parent) {
        if (parent == null) return list;
        list = postorder(list, parent.getLeft());
        list = postorder(list, parent.getRight());
        list.add(parent.getData());
        return list;
    }

    @Override
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<T>(size);
        return inorder(list, root);
    }

    /**
     * This function is a recursive helper for inorder
     * to traverse a child node
     *
     *
     * @param list is the list to create and add to
     * @param parent is the parent node to search under
     *
     * @return an inorder traversal of the tree
     */
    private List<T> inorder(List<T> list, Node<T> parent) {
        if (parent == null) return list;
        list = inorder(list, parent.getLeft());
        list.add(parent.getData());
        list = inorder(list, parent.getRight());
        return list;
    }

    @Override
    public List<T> levelorder() {
        ArrayList<T> list = new ArrayList<T>(size);
        Queue<Node<T>> queue = new LinkedList<Node<T>>();
        Node<T> temp;
        queue.add(root);
        while (!queue.isEmpty()) {
            temp = queue.remove();
            list.add(temp.getData());

            if (temp.getLeft() != null)
                queue.add(temp.getLeft());
            if (temp.getRight() != null)
                queue.add(temp.getRight());
        }

        return list;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        if (root == null) return -1;
        return Math.max(height(root.getLeft()), height(root.getRight()));
    }

    /**
     * This function is a recursive helper for height
     * to traverse a child node and calculate the max
     * between the right and left nodes
     *
     * @param parent is the parent node to search under
     *
     * @return the height of a subtree
     */
    private int height(Node<T> parent) {
        if (parent == null) return 0;
        return 1 + Math.max(height(parent.getLeft()), height(parent.getRight()));
    }

    @Override
    public Node<T> getRoot() {
        return root;
    }
}