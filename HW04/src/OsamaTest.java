import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class OsamaTest {

    private Heap<Integer> heap;
    private PriorityQueue<Integer> pQueue;

    @Before
    public void setUp() throws Exception {
        heap = new Heap<Integer>();
        pQueue = new PriorityQueue<Integer>();
    }

    @Test(timeout = 200)
    public void orderedAdd() throws Exception {
        basicAdd(20, 30, 40, 31, 37, 44, 42, 80, 43);
        checkBackingArray(20, 30, 40, 31, 37, 44, 42, 80, 43);
    }

    @Test(timeout = 200)
    public void unorderedAdd() throws Exception {
        basicAdd(20, 37, 22, 38, 46, 4);
        checkBackingArray(4, 37, 20, 38, 46, 22);
        assertEquals(6, heap.size());
        basicAdd(27);
        checkBackingArray(4, 37, 20, 38, 46, 22, 27);
        assertEquals(7, heap.size());
    }

    @Test(timeout = 200)
    public void removal() throws Exception {
        basicAdd(20, 37, 22, 38, 46, 4, 27);
        assertEquals(7, heap.size());
        assertEquals(new Integer(4), heap.remove());
        assertEquals(6, heap.size());
        checkBackingArray(20, 37, 22, 38, 46, 27);
    }

    @Test(timeout = 200)
    public void unorderedAddPQ() throws Exception {
        basicAddPQ(20, 37, 22, 38, 46, 4);
        checkBackingPQ(4, 37, 20, 38, 46, 22);
        basicAddPQ(27);
        checkBackingPQ(4, 37, 20, 38, 46, 22, 27);
    }

    @Test(timeout = 200)
    public void orderedAddPQ() throws Exception {
        basicAddPQ(20, 30, 40, 31, 37, 44, 42, 80, 43);
        checkBackingPQ(20, 30, 40, 31, 37, 44, 42, 80, 43);
    }

    @Test(timeout = 200)
    public void removalPQ() throws Exception {
        basicAddPQ(20, 37, 22, 38, 46, 4, 27);
        assertEquals(new Integer(4), pQueue.deleteMin());
        checkBackingPQ(20, 37, 22, 38, 46, 27);
    }

    private void basicAdd(Integer... arr) {
        int i = 0;
        while (i < arr.length && arr[i] != null) {
            heap.add(arr[i]);
            i++;
        }
    }

    private void basicAddPQ(Integer... arr) {
        int i = 0;
        while (i < arr.length && arr[i] != null) {
            pQueue.insert(arr[i]);
            i++;
        }
    }

    private void checkBackingArray(Integer... numbers) {
        @SuppressWarnings("rawtypes")
        Comparable[] backingArray = heap.toArray();
        assertNull(backingArray[0]);

        for (int i = 1; i < backingArray.length; i++) {
            if (i > numbers.length) {
                assertNull(backingArray[i]);
            } else {
                assertEquals(numbers[i - 1], backingArray[i]);
            }
        }
    }

    private void checkBackingPQ(Integer... numbers) {
        @SuppressWarnings("rawtypes")
        Comparable[] backingArray = pQueue.toArray();
        assertNull(backingArray[0]);

        for (int i = 1; i < backingArray.length; i++) {
            if (i > numbers.length) {
                assertNull(backingArray[i]);
            } else {
                assertEquals(numbers[i - 1], backingArray[i]);
            }
        }
    }
}