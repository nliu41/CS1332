import java.util.Arrays;

import static org.junit.Assert.*;

public class CircularLinkedListTest {

    @org.junit.Test
    public void testIterator() throws Exception {
        CircularLinkedList<Integer> list = new CircularLinkedList<Integer>();
        list.addToFront(1);
        list.addToFront(2);
        list.addToFront(3);
        list.addToFront(4);
        list.addToFront(5);

        System.out.println(Arrays.toString(list.toList()));

        for (Integer i : list) {
            System.out.println(i);
        }
    }
}