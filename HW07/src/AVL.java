import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * My AVL implementation.
 *
 * @author Paras Jain
 */
public class AVL<T extends Comparable<T>> implements AVLInterface<T>,
       Gradable<T> {

    // Do not add additional instance variables
    private Node<T> root;
    private int size;

    /**
     * Add the data as a leaf in the BST.  Should traverse the tree to find the
     * appropriate location.  You may assume null is never passed in.
     * Should have a worst case running time of O(logn)
     * If the data is already in the tree, do nothing.
     * @param data the data to be added
     * @throws IllegalArgumentException if {@code data} is null
     */
    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }

        if (size < 1) {
            Node<T> node = new Node<T>(data);
            root = node;
            size++;
        } else {
            // BST insert
            root = add(data, root);
            size++;
        }
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }

        Node<T> toReturn = new Node<T>(null);

        if (size == 1 && root.getData().equals(data)) {
            toReturn.setData(root.getData());
            clear();
        } else if (size < 1) {

        } else if (size > 1) {
            root = remove(data, root, null, toReturn);
        }

        return toReturn.getData();
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }

        Node<T> node = get(root, data);
        return (node == null) ? null : node.getData();
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        return get(root, data) != null;
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
    public List<T> preorder() {
        return preorder(new ArrayList<T>(size), root);
    }

    @Override
    public List<T> postorder() {
        return postorder(new ArrayList<T>(size), root);
    }

    @Override
    public List<T> inorder() {
        return inorder(new ArrayList<T>(size), root);
    }

    @Override
    public List<T> levelorder() {
        Queue<Node<T>> queue = new LinkedList<Node<T>>();
        List<T> list = new ArrayList<T>(size);

        queue.add(root);
        Node<T> current = null;
        while (!queue.isEmpty()) {
            current = queue.remove();
            if (current != null) {
                list.add(current.getData());
                if (current.getLeft() != null) {
                    queue.add(current.getLeft());
                }
                if (current.getRight() != null) {
                    queue.add(current.getRight());
                }
            }
        }

        return list;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public int height() {
        return height(root);
    }

    @Override
    public Node<T> getRoot() {
        return root;
    }

    /**
     * Returns a node that contains the data
     * @param parent to search from
     * @param data to search fore
     * @return node found
     */
    private Node<T> get(Node<T> parent, T data) {
        Node<T> result = null;
        if (parent != null) {
            int compare = data.compareTo(parent.getData());
            if (compare == 0) {
                result = parent;
            } else if (compare < 0) {
                result = get(parent.getLeft(), data);
            } else {
                result = get(parent.getRight(), data);
            }
        }
        return result;
    }

    /**
     * Recursively adds in a new node where it belongs
     * @param data is the data to add
     * @param parent is the parent to start from
     * @return the added node, balanced, to replace old pointer with
     */
    private Node<T> add(T data, Node<T> parent) {
        if (parent == null) {
            parent = new Node<>(data);
        } else if (data.compareTo(parent.getData()) < 0) {
            parent.setLeft(add(data, parent.getLeft()));
            recomputeBalance(parent);
            parent = runBalance(parent);
        } else if (data.compareTo(parent.getData()) > 0) {
            parent.setRight(add(data, parent.getRight()));
            recomputeBalance(parent);
            parent = runBalance(parent);
        }

        parent.setHeight(calcHeight(parent));
        recomputeBalance(parent);
        return parent;
    }


    /*
            O grandparent                                  O parent.getLeft
             \                                            / \
              O   parent          =>         grandparent O   O parent
             /
            O     parent.getLeft
    */

    /*
                O grandparent                               O parent
               /                                           / \
              O   parent          =>       parent.getLeft O   O grandparent
             /
            O     parent.getLeft
     */

    /**
     * Runs a balance on the node in question
     * @param a is the node to balance
     * @return the balanced node
     */
    private Node<T> runBalance(Node<T> a) {
        if (a.getBalanceFactor() > 1) { // if A is left heavy
            if (a.getLeft() != null && a.getLeft().getBalanceFactor() >= 0) {
            // if a is double left heavy
                a = rotateR(a);
            } else {
                a = rotateLR(a);
            }
        } else if (a.getBalanceFactor() < -1) {
            if (a.getRight() != null && a.getRight().getBalanceFactor() <= 0) {
                a = rotateL(a);
            } else {
                a = rotateRL(a);
            }
        }
        return a;
    }

    /**
     * Right rotation
     * @param parent is the node to rotate
     * @return the rotated parent
     */
    private Node<T> rotateR(Node<T> parent) {
        Node<T> left = parent.getLeft();
        parent.setLeft(left.getRight());
        left.setRight(parent);
        parent.setHeight(calcHeight(parent));
        left.setHeight(calcHeight(left));
        return left;
    }

    /**
     * Rotate left
     * @param parent is the node to rotate
     * @return the rotated parent
     */
    private Node<T> rotateL(Node<T> parent) {
        Node<T> right = parent.getRight();
        parent.setRight(right.getLeft());
        right.setLeft(parent);
        parent.setHeight(calcHeight(parent));
        right.setHeight(calcHeight(right));
        return right;
    }

    /**
     * Rotate left then right
     * @param parent is the node to rotate
     * @return the rotated parent
     */
    private Node<T> rotateLR(Node<T> parent) {
        parent.setLeft(rotateL(parent.getLeft()));
        return rotateR(parent);
    }

    /**
     * Rotate right then left
     * @param parent is the node to rotate
     * @return the rotated parent
     */
    private Node<T> rotateRL(Node<T> parent) {
        parent.setRight(rotateR(parent.getRight()));
        return rotateL(parent);
    }

    /**
     * Recalculates the height of a node
     * @param node
     * @return
     */
    private int calcHeight(Node<T> node) {
        return Math.max(height(node.getLeft()),
                height(node.getRight())) + 1;
    }

    /**
     * Returns the height of the node
     * @param root is the node to measure height
     * @return the updated height of the node
     */
    private int height(Node<T> root) {
        return (root == null) ? -1 : root.getHeight();
    }


    /**
     * Recursive helper method for remove
     * @param data is the data to remove
     * @param parent is the parent node to start removal from
     * @param grandparent is the grandparent of node
     * @param toReturn is a data object to return back
     * @return the current node in the recusive call stack
     */
    private Node<T> remove(T data, Node<T> parent,
                           Node<T> grandparent, Node<T> toReturn) {
        if (parent == null) {
            return null;
        }

        if (data.compareTo(parent.getData()) < 0) { // left
            parent.setLeft(remove(data, parent.getLeft(), parent, toReturn));
            parent.setHeight(calcHeight(parent));
            recomputeBalance(parent);

            parent = runBalance(parent);
        } else if (data.compareTo(parent.getData()) > 0) { // right
            parent.setRight(remove(data, parent.getRight(), parent, toReturn));
            parent.setHeight(calcHeight(parent));
            recomputeBalance(parent);

            parent = runBalance(parent);
        } else {
            size--;
            toReturn.setData(parent.getData());

            if (parent.getLeft() != null && parent.getRight() != null) {
                parent.setData(twoChildren(parent));
            } else if (parent.getLeft() == null) {
                parent = parent.getRight();
            } else {
                parent = parent.getLeft();
            }
        }

        recomputeBalance(parent);
        return parent;
    }

    /**
     * Removal with case of two children
     * @param curr is the current node
     * @return Removed data
     * @author 1332 TAs
     */
    private T twoChildren(Node<T> curr) {
        Node<T> pred = curr.getLeft();
        Node<T> predParent = null;
        while (pred.getRight() != null) {
            predParent = pred;
            pred = pred.getRight();
        }
        T ret = pred.getData();
        if (predParent == null) {
            curr.setLeft(pred.getLeft());
        } else {
            predParent.setRight(pred.getLeft());
        }

        return ret;
    }

    /**
     * Preorder traversal helper
     * @param list list to add to
     * @param root is the node to start searching from
     * @return is the list
     */
    private List<T> preorder(List<T> list, Node<T> root) {
        if (root != null) {
            list.add(root.getData());
            list = preorder(list, root.getLeft());
            list = preorder(list, root.getRight());
        }
        return list;
    }


    /**
     * Postorder traversal helper
     * @param list list to add to
     * @param root is the node to start searching from
     * @return is the list
     */
    private List<T> postorder(List<T> list, Node<T> root) {
        if (root != null) {
            list = postorder(list, root.getLeft());
            list = postorder(list, root.getRight());
            list.add(root.getData());
        }
        return list;
    }

    /**
     * Inorder traversal helper
     * @param list list to add to
     * @param root is the node to start searching from
     * @return is the list
     */
    private List<T> inorder(List<T> list, Node<T> root) {
        if (root != null) {
            list = inorder(list, root.getLeft());
            list.add(root.getData());
            list = inorder(list, root.getRight());
        }
        return list;
    }


    /**
     * Recomputes the balance factor of a node
     * @param node the node to recompute tha balance factor for
     */
    private void recomputeBalance(Node<T> node) {
        if (node != null) {
            node.setBalanceFactor(height(node.getLeft()) - height(node.getRight()));
        }
    }
}