import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is a class that will allow you to iterate through the first n
 * Fibonacci elements
 * @author kushagramansingh
 * @author Paras Jain
 *
 */
public class FibonacciIterator implements Iterator<Integer> {
    private Integer n;
    private Integer current = 0; // index
    private Integer runningValue = 1; // the current value
    private Integer previousValue = 0; // the previous one

    public FibonacciIterator(Integer n) {
        this.n = n;
    }

    @Override
    public boolean hasNext() {
        return current < n; // if current index is less than n
    }

    @Override
    public Integer next() {
        if (!hasNext()) { // throw exception if it's at end of it's list
            throw new NoSuchElementException("iterator is now empty");
        }
        if (current == 0) { // base case
            current++;
            return 1;
        } else { // else iterate
            Integer value = previousValue + runningValue;
            previousValue = runningValue;
            runningValue = value;
            current++;

            return value;
        }
    }
}