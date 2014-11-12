import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciIteratorTest {

    @Test
    public void testNext() throws Exception {
        FibonacciIterator fb = new FibonacciIterator(10);
        while (fb.hasNext()) {
            System.out.println(fb.next());
        }
    }
}