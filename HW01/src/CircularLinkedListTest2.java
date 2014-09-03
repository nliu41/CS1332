import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;


import java.util.Random;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class CircularLinkedListTest2 {

    private CircularLinkedList<Integer> list;

    @Before
    public void setup() {
        list = new CircularLinkedList<Integer>();
    }

    @Test(timeout = 500)
    public void testAddToIndexAtSize() {
        for(int i =0; i < 100000; i++)
        {
            list.addAtIndex(list.size(), new Integer(i));
        }
    }

    @Test(timeout = 1000)
    public void testCircularity() {
        addManyElementsToBack();
        Node<Integer> head = list.getHead();
        Node<Integer> curNode = head.getNext();
        while (curNode != head) {
            if (curNode == head) { //testing for reference equality!
                assertTrue(curNode == head);
            }
            curNode = curNode.getNext();
        }
    }

    @Test(timeout = 1000)
    public void testTailPointsToHead() {
        addManyElementsToBack();
        Node<Integer> head = list.getHead();
//        list.addToBack(new Integer(1));
//        list.addToBack(new Integer(2));
//        list.addToBack(new Integer(3));
//        System.out.println(list);
//
//        assertSame(null, list.removeAtIndex(4));
//
//        list.removeAtIndex(20);
//        list.removeFromBack();

        assertTrue(list.getTail().getNext() == head);
    }

    @Test(timeout = 200)
    public void testSimpleAddAndGet() {
        list.addToBack(0);
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        System.out.println(list);
        list.addAtIndex(3, new Integer(100));
        System.out.println(list);

        assertEquals(new Integer(100), list.get(3));
    }

    @Test(timeout = 1000)
    public void testRandomAddAndGet() {
        addManyElementsToBack();

        Random rand = new Random();

        for (int i = 0; i <= 200; i++) {
            int index = rand.nextInt(list.size());
            Integer randNum = new Integer(rand.nextInt());
//            System.out.println(index);
//            System.out.println(list.get(index));

            list.addAtIndex(index, randNum);

//            System.out.println(list.get(index));
            assertEquals(randNum, list.get(index));
        }
    }

    private Integer[] addManyElementsToBack() {
        Integer[] nums = new Integer[1000];
        for (int i = 0; i < 1000; i++) {
            list.addToBack(new Integer(i));
            nums[i] = new Integer(i);
        }
        return nums;
    }

    @Test(timeout = 50)
    public void emptyListShouldReturnEmptyArray() {
        Integer[] emptyArray = new Integer[0];
        assertArrayEquals(list.toList(), emptyArray);
    }

    @After
    public void afterTest()
    {
        Node<Integer> head = list.getHead();

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