/**
 * Created by parasjain on 9/14/14.
 */
public class DoublyLinkedList {
    protected Node head;

    public void addToFront(String data) {
        head = new Node(data, head, null);
    }

    public void removeFirstOccurrence(String data) {
        Node Current = head;
    }
}
