import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class HW2Tests {

    private LinkedListInterface<Integer> list;
    private StackInterface<Integer> stack;
    private QueueInterface<Integer> queue;

    @Before
    public void setUp() {
        list = new DoublyLinkedList<Integer>();
        stack = new Stack<Integer>();
        queue = new Queue<Integer>();
    }

    /**
     * Some general tests on all three
     * Asserts that all structures can handle emptieness alright
     */
    @Test (timeout = 200)
    public void testEmpties() {
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertNull(stack.pop());
        assertNull(queue.dequeue());
    }

    //additional tests on the linked list

    /**
     * This is a fun one
     * Asserts that the linkedlist can handle null values
     * kind of checks toArray
     */
    @Test (timeout = 200)
    public void testNulls() {
        list.addToFront(null);
        list.addToFront(null);
        list.addToFront(null);
        assertArrayEquals(new Object[]{null, null, null}, list.toArray());
    }

    /**
     * Asserts that removeAtIndex from an empty list raises an exception
     */
    @Test (timeout = 200, expected = java.lang.IndexOutOfBoundsException.class)
    public void testRemoveIndexZeroEmpty() {
        list.removeAtIndex(0);
    }

    /**
     * Asserts that removing beyond size-1 causes issues
     */
    @Test (timeout = 200, expected = java.lang.IndexOutOfBoundsException.class)
    public void testRemoveLargeIndexLarge() {
        list.addToFront(1);
        list.addToFront(0);
        list.removeAtIndex(5);
    }

    /**
     * Asserts that removing at negative indicies throws exceptions
     */
    @Test (timeout = 200, expected = java.lang.IndexOutOfBoundsException.class)
    public void testNegativeIndex() {
        list.addToFront(1);
        list.removeAtIndex(-1);
    }

    /**
     * Asserts that addToFront works
     * Asserts that addToBack works
     * Asserts that addAtIndex works
     * Asserts that toArray works
     * Asserts that removeFromFront works
     * Asserts that removeFromBack works
     * Asserts that removeAtIndex works
     * Asserts that toArray works
     * Asserts that removing down to an empty array sets head and tail to null
     */
    @Test (timeout = 200)
    public void testRemovalNotCircular() {
        list.addToFront(0);
        list.addToBack(5);
        list.addAtIndex(1, 4);
        list.addAtIndex(1,3);
        list.addAtIndex(1,2);
        list.addAtIndex(1,1);
        assertArrayEquals(new Integer[]{0,1,2,3,4,5}, list.toArray());
        list.removeFromBack();
        assertArrayEquals(new Integer[]{0,1,2,3,4}, list.toArray());
        list.removeFromFront();
        assertArrayEquals(new Integer[]{1,2,3,4}, list.toArray());
        list.removeAtIndex(1);
        list.removeAtIndex(2);
        list.removeAtIndex(1);
        list.removeAtIndex(0);
        assertArrayEquals(new Integer[]{}, list.toArray());
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

    }

    /**
     * Asserts that head==tail!=null at length 1 both before and after more has been added and then removed from
     * the list
     */
    @Test (timeout = 200)
    public void testHeadTailEquality() {
        list.addToFront(0);
        assertEquals(list.getHead(), list.getTail());
        assertNotNull(list.getHead());

        list.addToFront(1);
        assertEquals(new Integer(0), list.removeFromBack());
        assertEquals(list.getHead(), list.getTail());
    }

    /**
     * Asserts that removing from back is O(1)
     * this is the only big oh test this time
     */
    @Test (timeout = 50)
    public void testRemoveAtIndexNBigOh() {
        for (int i = 0; i < 100000; i++) {
            list.addToFront(0);
        }
        while (!list.isEmpty()){
            list.removeFromBack();
        }
    }
}