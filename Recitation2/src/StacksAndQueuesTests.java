import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StacksAndQueuesTests {
    private StacksQueue<Integer> queue;

    @Before
    public void setup() {
        queue = new StacksQueue<Integer>();
    }


    @Test
    public void testEnqueue() throws Exception {
        queue.enqueue(new Integer(0));
        queue.enqueue(new Integer(1));
        queue.enqueue(new Integer(2));
        queue.enqueue(new Integer(3));

        assertEquals(new Integer(0), queue.dequeue());
        assertEquals(new Integer(1), queue.dequeue());
        assertEquals(new Integer(2), queue.dequeue());
        assertEquals(new Integer(3), queue.dequeue());
        assertEquals(null, queue.dequeue());
    }

    @Test
    public void testDequeue() throws Exception {
        assertEquals(null, queue.dequeue());

        queue.enqueue(new Integer(0));
        queue.enqueue(new Integer(1));
        queue.enqueue(new Integer(2));
        assertEquals(new Integer(0), queue.dequeue());
        queue.enqueue(new Integer(3));
        queue.enqueue(new Integer(4));
        assertEquals(new Integer(1), queue.dequeue());
        assertEquals(new Integer(2), queue.dequeue());
        assertEquals(new Integer(3), queue.dequeue());
        assertEquals(new Integer(4), queue.dequeue());


    }

    @Test
    public void testIsEmpty() throws Exception {
        assertEquals(true, queue.isEmpty());
        queue.enqueue(new Integer(3));
        assertEquals(new Integer(3), queue.dequeue());
        assertEquals(true, queue.isEmpty());
    }


    /*
    @Override
    public String toString() {
        return "IN: " + Arrays.toString(in.toArray()) + "\nOUT: " + Arrays.toString(out.toArray()) + "\n\n";
    }*/
}