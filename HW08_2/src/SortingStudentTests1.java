
import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.runners.MethodSorters;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

public class SortingStudentTests1 {
    private static final int NLOGNLARGEMAX = 100000;
    private static final int N2LARGEMAX = 2000000;
    private SortingInterface sorter;
    private Integer[] smallIntegerArrayTest, smallIntegerArrayExpected;
    private Integer[] oddTest, oddTestExpected;
    private TestElement[] stabilityTest, stabilityTestExpected;

    @Before
    public void setup() {
        sorter = new Sorting();

        //setup small test variables
        smallIntegerArrayTest = new Integer[6];
        smallIntegerArrayExpected = new Integer[6];
        oddTest = new Integer[7];
        oddTestExpected = new Integer[7];
        stabilityTest = new TestElement[8];
        stabilityTestExpected = new TestElement[8];

        smallIntegerArrayExpected[0] = 0;
        smallIntegerArrayExpected[1] = 2;
        smallIntegerArrayExpected[2] = 3;
        smallIntegerArrayExpected[3] = 5;
        smallIntegerArrayExpected[4] = 7;
        smallIntegerArrayExpected[5] = 9;

        smallIntegerArrayTest[0] = 7;
        smallIntegerArrayTest[1] = 2;
        smallIntegerArrayTest[2] = 9;
        smallIntegerArrayTest[3] = 5;
        smallIntegerArrayTest[4] = 0;
        smallIntegerArrayTest[5] = 3;

        for (int i = 0; i < 6; i++) {
            oddTestExpected[i] = smallIntegerArrayExpected[i];
            oddTest[i+1] = smallIntegerArrayTest[i];
        }
        oddTestExpected[6] = 10;
        oddTest[0] = 10;

        stabilityTestExpected[0] = new TestElement(0, "a");
        stabilityTestExpected[1] = new TestElement(0, "b");
        stabilityTestExpected[2] = new TestElement(2, "c");
        stabilityTestExpected[3] = new TestElement(3, "d");
        stabilityTestExpected[4] = new TestElement(5, "e");
        stabilityTestExpected[5] = new TestElement(7, "f");
        stabilityTestExpected[6] = new TestElement(7, "g");
        stabilityTestExpected[7] = new TestElement(9, "h");

        stabilityTest[0] = new TestElement(0, "a");
        stabilityTest[1] = new TestElement(9, "h");
        stabilityTest[2] = new TestElement(2, "c");
        stabilityTest[3] = new TestElement(3, "d");
        stabilityTest[4] = new TestElement(7, "f");
        stabilityTest[5] = new TestElement(5, "e");
        stabilityTest[6] = new TestElement(7, "g");
        stabilityTest[7] = new TestElement(0, "b");
    }

    //Test helpers
    private <T extends Comparable<T>> void testBubble(T[] expected,
                                                      T[] actual) {
        sorter.bubblesort(actual);
        assertArrayEquals(expected, actual);
    }

    private <T extends Comparable<T>> void testInsertion(T[] expected,
                                                         T[] actual) {
        sorter.insertionsort(actual);
        assertArrayEquals(expected, actual);
    }
    private <T extends Comparable<T>> void testSelection(T[] expected,
                                                         T[] actual) {
        sorter.selectionsort(actual);
        assertArrayEquals(expected, actual);
    }
    private <T extends Comparable<T>> void testQuick(T[] expected, T[] actual) {
        long seed = 1414933072742L;
        Random r = new Random(seed);
    	/*System.out.print("Random numbers: ");
    	for (int i = 0; i < 15; i++) {
    		System.out.print(r.nextInt());
    	}
    	System.out.println();*/
        System.out.print("Before: ");
        for (T element : actual) {
            System.out.print(element + ", ");
        }
        System.out.println();

        r.setSeed(seed);
        sorter.quicksort(actual, new Random());
        System.out.print("After: ");

        for (T element : actual) {
            System.out.print(element + ", ");
        }
        assertArrayEquals("Fail with seed = " + seed, expected, actual);

    }

    private <T extends Comparable<T>> void testMerge(T[] expected, T[] actual) {
        sorter.mergesort(actual);
        assertArrayEquals(expected, actual);
    }
    private void testRadix(int[] expected, int[] actual) {
        actual = sorter.radixsort(actual);
        assertArrayEquals(expected, actual);
    }

    //Test Small Sort
    //Bubble
    @Test
    public void testSmallBubble0() {
        testBubble(smallIntegerArrayExpected, smallIntegerArrayTest);
        testBubble(oddTestExpected, oddTest);
        testBubble(stabilityTestExpected, stabilityTest);
    }

    //Insertion
    @Test
    public void testSmallInsertion0() {
        testInsertion(smallIntegerArrayExpected, smallIntegerArrayTest);
        testInsertion(oddTestExpected, oddTest);
        testInsertion(stabilityTestExpected, stabilityTest);
    }

    //Selection
    @Test
    public void testSmallSelection0() {
        testSelection(smallIntegerArrayExpected, smallIntegerArrayTest);
        testSelection(oddTestExpected, oddTest);
    }

    //Quick
    @Test
    public void testSmallQuick0() {
        testQuick(smallIntegerArrayExpected, smallIntegerArrayTest);
        testQuick(oddTestExpected, oddTest);
    }

    //Merge
    @Test
    public void testSmallMerge0() {
        testMerge(smallIntegerArrayExpected, smallIntegerArrayTest);
        testMerge(oddTestExpected, oddTest);
        testMerge(stabilityTestExpected, stabilityTest);
    }

    //Radix
    @Test
    public void testSmallRadix0() {
        int [] expected = {0, 3, 10, 25, 33, 99};
        int [] test = {99, 0, 10, 3, 25, 33};
        testRadix(expected, test);
    }

    //Radix Specific small negative integer test
    @Test
    public void testSmallNegativeRadix() {
        int[] test = {5, 7, -1, 6, 10};
        int[] answer = {-1, 5, 6, 7, 10};
        testRadix(answer, test);
    }

    private class TestElement implements Comparable<TestElement> {
        private int i;
        private String s;

        public TestElement(int i, String s) {
            this.i = i;
            this.s = s;
        }

        public int compareTo(TestElement other) {
            if (i < other.i) {
                return -1;
            } else if (i > other.i){
                return 1;
            } else {
                return 0;
            }
        }

        public boolean equals(Object other) {
            if (other == null) return false;
            else if (other == this) return true;
            else if (!(other instanceof TestElement)) return false;

            TestElement otherElement = (TestElement) other;
            return i == otherElement.i && s == otherElement.s;
        }

        public String toString() {
            return i + " " + s;
        }
    }
}
