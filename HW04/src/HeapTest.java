import org.junit.Before;
import org.junit.Test;

//import static org.junit.Assert.*;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HeapTest {
    private Heap<Integer> heap;
    private PriorityQueue<Integer> pQueue;

    @Before
    public void setUp() {
        heap = new Heap<Integer>();
        pQueue = new PriorityQueue<Integer>();
    }

    @Test
    public void testAddTons() throws Exception {
        Integer[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};

        for (Integer i : arr) {
            heap.add(i);
        }

        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(heap.toArray()));

//        assertArrayEquals(arr, heap.toArray());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIsEmpty1() throws Exception {
        pQueue.insert(null);
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertEquals(true, heap.isEmpty());
    }

    @Test (expected = IllegalArgumentException.class)
    public void addNull() throws Exception {
        heap.add(null);
    }

    @Test
    public void isEmpty_empty() throws Exception {
        assertEquals(true, heap.isEmpty());
    }

    @Test
    public void peek_empty() throws Exception {
        assertEquals(null, heap.peek());
    }

    @Test
    public void remove_empty() throws Exception {
        assertEquals(null, heap.remove());
    }

    @Test
    public void emptypq() throws Exception {
        pQueue.insert(0);
        pQueue.insert(1);
        pQueue.insert(-1);

        pQueue.makeEmpty();

        assertEquals(true, pQueue.isEmpty());
        assertEquals(null, pQueue.deleteMin());
    }

    @Test
    public void empty_negative_size() {
        heap.remove();
        heap.remove();
        heap.remove();
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(0, heap.size());
    }
}