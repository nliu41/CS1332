import java.util.Arrays;
import static org.junit.Assert.assertArrayEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
/**
 *@author Nate
 */
public class AdditionalSortingTests {
    private static final int LARGE_TEST_SIZE = 5000;
    private static final int RANDOM_MAX = 10000;
    private static final long seed = System.currentTimeMillis();
    private Random rand;
    private SortingInterface sorter;
    private Integer[] smallIntegerArrayTest, smallIntegerArrayExpected;
    private Pair[] largeStableTest, largeStableExpected;
    private Integer[] largeUnstableTest, largeUnstableExpected;


    @Before
    public void setup() {
        sorter = new Sorting();
        rand = new Random(seed);
        //setup small test variables
        smallIntegerArrayTest = new Integer[6];
        smallIntegerArrayExpected = new Integer[6];

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

        largeStableTest = new Pair[LARGE_TEST_SIZE];
        largeStableExpected = new Pair[LARGE_TEST_SIZE];
        largeUnstableTest = new Integer[LARGE_TEST_SIZE];
        largeUnstableExpected = new Integer[LARGE_TEST_SIZE];

        for(int i = 0; i<LARGE_TEST_SIZE; i++){
            Pair randomPair = new Pair(rand.nextInt(RANDOM_MAX*2)-RANDOM_MAX, rand.nextInt(RANDOM_MAX*2)-RANDOM_MAX);
            int randomInt = rand.nextInt(RANDOM_MAX*2)-RANDOM_MAX;//Makes the range -RANDOM_MAX -> +RANDOM_MAX-1
            largeStableTest[i] = randomPair;
            largeStableExpected[i] = randomPair;
            largeUnstableTest[i] = randomInt;
            largeUnstableExpected[i] = randomInt;
        }
        Arrays.sort(largeStableExpected);
        Arrays.sort(largeUnstableExpected);
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
        sorter.quicksort(actual, new Random());
        assertArrayEquals(expected, actual);
    }

    private <T extends Comparable<T>> void testMerge(T[] expected, T[] actual) {
        sorter.mergesort(actual);
        assertArrayEquals(expected, actual);
    }
    private void testRadix(int[] expected, int[] actual) {
        sorter.radixsort(actual);
        assertArrayEquals(expected, actual);
    }

    //Test Small Sort
    //Bubble
    @Test
    public void testSmallBubble0() {
        testBubble(smallIntegerArrayExpected, smallIntegerArrayTest);
    }

    //Insertion
    @Test
    public void testSmallInsertion0() {
        testInsertion(smallIntegerArrayExpected, smallIntegerArrayTest);
    }

    //Selection
    @Test
    public void testSmallSelection0() {
        testSelection(smallIntegerArrayExpected, smallIntegerArrayTest);
    }

    //Quick
    @Test
    public void testSmallQuick0() {
        testQuick(smallIntegerArrayExpected, smallIntegerArrayTest);
    }

    //Merge
    @Test
    public void testSmallMerge0() {
        testMerge(smallIntegerArrayExpected, smallIntegerArrayTest);
    }

    //Test Large Sort
    //Bubble
    @Test
    public void testLargeBubble0() {
        testBubble(largeStableExpected, largeStableTest);
    }

    //Insertion
    @Test
    public void testLargeInsertion0() {
        testInsertion(largeStableExpected, largeStableTest);
    }

    //Selection
    @Test
    public void testLargeSelection0() {
        testSelection(largeUnstableExpected, largeUnstableExpected);
    }

    //Quick
    @Test
    public void testLargeQuick0() {
        testQuick(largeUnstableExpected, largeUnstableExpected);
    }

    //Merge
    @Test
    public void testLargeMerge0() {
        testMerge(largeStableExpected, largeStableTest);
    }


    //A Large Radix Sort on random numbers
    @Test
    public void testLargeRandomRadix(){
        int[] actual = new int[LARGE_TEST_SIZE];
        int[] expected = new int[LARGE_TEST_SIZE];
        for(int i = 0; i<LARGE_TEST_SIZE; i++){
            int randomInt = rand.nextInt();
            actual[i] = randomInt;
            expected[i] = randomInt;
        }
        Arrays.sort(expected);
        testRadix(expected, actual);
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

    private class Pair implements Comparable<Pair>{
        private int x;
        private int y;

        public Pair(int a, int b){
            x = a;
            y = b;
        }
        @Override
        public int compareTo(Pair p) {
            return ((Integer)x).compareTo((Integer)p.getx());
        }
        @Override
        public boolean equals(Object o){
            if(o == null){
                return false;
            } if (!(o instanceof Pair)){
                return false;
            }
            return x == ((Pair)o).getx() && y == ((Pair)o).gety();
        }
        public int getx(){
            return x;
        }
        public int gety(){
            return y;
        }
        public String toString(){
            return "Pair{x = " + x + ", y = "+ y;
        }
    }

}
