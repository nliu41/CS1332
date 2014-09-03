/**
 * Created by parasjain on 9/2/14.
 */

import org.junit.*;

import java.util.Arrays;

import static org.junit.Assert.*;

public class EdgeCaseTest {
    private LinkedListInterface<Integer> list;

    @Before
    public void setup() {
        list = new CircularLinkedList<Integer>();
    }

    @Test(timeout=200)
    public void testaddAtIndex() {

        // Standart addAtIndex
        list.addToFront(3);
        list.addToFront(2);
        list.addToFront(1);
        list.addToFront(0);
        list.addAtIndex(0, -1);
        list.addAtIndex(1, -2);
        list.addAtIndex(list.size(), -3);

        System.out.println("List, " + list.toString());

        Integer[] testList = {-1, -2, 0, 1, 2, 3, -3};
        assertEquals(testList, list.toList());
    }

    @Test(timeout=200, expected=java.lang.IndexOutOfBoundsException.class)
    public void testaddAtIndexOutofBoundsLow() {
        list.addToFront(3);
        list.addToFront(2);
        list.addToFront(1);
        list.addToFront(0);
        list.addAtIndex(0, -1);
        list.addAtIndex(1, -2);
        list.addAtIndex(list.size(), -3);
        list.addAtIndex(-1, -3);
    }

    @Test(timeout=200, expected=java.lang.IndexOutOfBoundsException.class)
    public void testaddAtIndexOutofBoundsHigh() {
        list.addToFront(3);
        list.addToFront(2);
        list.addToFront(1);
        list.addToFront(0);
        list.addAtIndex(0, -1);
        list.addAtIndex(1, -2);
        list.addAtIndex(list.size(), -3);
        list.addAtIndex(list.size()+1, -3);
    }


    @Test(timeout=200, expected=java.lang.IndexOutOfBoundsException.class)
    public void testgetAtIndexOutofBoundsLow() {
        list.addToFront(3);
        list.addToFront(2);
        list.addToFront(1);
        list.addToFront(0);
        list.addAtIndex(0, -1);
        list.addAtIndex(1, -2);
        list.addAtIndex(list.size(), -3);
        list.get(-1);
    }

    @Test(timeout=200, expected=java.lang.IndexOutOfBoundsException.class)
    public void testgetAtIndexOutofBoundsHigh() {
        list.addToFront(3);
        list.addToFront(2);
        list.addToFront(1);
        list.addToFront(0);
        list.addAtIndex(0, -1);
        list.addAtIndex(1, -2);
        list.addAtIndex(list.size(), -3);
        list.get(list.size());
    }

    @Test(timeout=200)
    public void testGet() {
        list.addToFront(3);
        list.addToFront(2);
        list.addToFront(1);
        list.addToFront(0);
        list.addAtIndex(0, -1);
        list.addAtIndex(1, -2);
        list.addAtIndex(list.size(), -3);
        assertEquals(new Integer(-1), list.get(0));
    }



    @Test(timeout=200)
    public void testRemoveAtIndex() {
        list.addToFront(3);
        list.addToFront(2);
        list.addToFront(1);
        list.addToFront(0);
        list.addAtIndex(0, -1);
        list.addAtIndex(1, -2);
        list.addAtIndex(list.size(), -3);


        assertEquals(new Integer(0), list.removeAtIndex(2));


        Integer[] testList = {-1, -2, 1, 2, 3, -3};
        assertEquals(testList, list.toList());
    }



    @Test(timeout=200, expected=java.lang.IndexOutOfBoundsException.class)
    public void testRemoveAtIndexHigh() {
        list.addToFront(3);
        list.addToFront(2);
        list.addToFront(1);
        list.addToFront(0);
        list.addAtIndex(0, -1);
        list.addAtIndex(1, -2);
        list.addAtIndex(list.size(), -3);


        assertEquals(new Integer(0), list.removeAtIndex(-1));
    }


    @Test(timeout=200, expected=java.lang.IndexOutOfBoundsException.class)
    public void testRemoveAtIndexLow() {
        list.addToFront(3);
        list.addToFront(2);
        list.addToFront(1);
        list.addToFront(0);
        list.addAtIndex(0, -1);
        list.addAtIndex(1, -2);
        list.addAtIndex(list.size(), -3);


        assertEquals(new Integer(0), list.removeAtIndex(list.size()));
    }



    @After
    public void afterTest()
    {
//        System.out.println(list);
        Node<Integer> head = ((CircularLinkedList)list).getHead();

        if(head == null) { // If the list is empty, we can't test circularity.
            return;
        }

        Node<Integer> curNode = head.getNext();

        while (curNode != head) {
            if (curNode == head) { //testing for reference equality!
                assertTrue(curNode == head);
            }
            curNode = curNode.getNext();
            assertNotEquals(curNode, null);
        }
    }


}
