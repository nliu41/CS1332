import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;


public class SkipListTestStudentExtended {
    private SkipListInterface<Integer> list;
    private SkipListInterface<String> listString;
    CoinFlipper randomness;
    @Before
    public void setup() {
        randomness = new CoinFlipper(new Random(10));
        list = new SkipList<Integer>(randomness);
        listString = new SkipList<String>(randomness);
    }


    // This is an example of how your SkipList is tested
    // for efficiency.
    @Test (timeout = 200)
    public void testPutSingle() {
        list.put(1);
        assertEquals(1, list.size());
        assertEquals(new Integer(1), list.first());
        assertEquals(new Integer(1), list.last());
        assertEquals(2, randomness.getNumFlips());
    }

    @Test (timeout = 200)
    public void testPut() {
        addBasic();
        assertEquals("First element is incorrect", new Integer(0),
                list.first());
        assertEquals("Last element is incorrect", new Integer(10), list.last());
        assertEquals("Size of the list is incorrect", 11, list.size());
    }

    @Test (timeout = 200)
    public void testContains() {
        addBasic();
        for (int i = 0; i < 11; i++) {
            assertTrue("List should contain " + i, list.contains(i));
        }

        for (int i = 11; i < 20; i++) {
            assertFalse("List should not contain " + i, list.contains(i));
        }
    }

    @Test (timeout = 200)
    public void testRemove() {
        addBasic();
        for (int i = 0; i < 5; i++) {
            list.remove(i);
        }
        assertEquals("Size isn't being decremented correctly when removing from"
                + " the list", 6, list.size());

        for (int i = 5; i < 10; i++) {
            list.remove(i);
        }
        assertEquals("Size isn't being decremented correctly when removing from"
                + " the list", 1, list.size());

        list.remove(10);
        assertEquals("Size should be 0 on an empty list", 0, list.size());

    }

    @Test (timeout = 200)
    public void testClear() {
        addBasic();
        assertEquals("Size is incorrect after adding", 11, list.size());

        list.clear();
        assertEquals("Size isn't 0 after clearing the list", 0, list.size());

        addJagged();
        assertEquals("Size is incorrect after adding unordered numbers", 11,
                list.size());

        list.clear();
        assertEquals("Size isn't 0 after clearing the list", 0, list.size());
    }

    @Test (timeout = 200)
    public void testGet() {
        addJagged();
        for (int i = 0; i < 11; i++) {
            assertEquals("Incorrect order of elements in the list",
                    new Integer(i), list.get(i));
        }
    }

    @Test (timeout = 200)
    public void testDataSet() {
        Set<Integer> expected = new TreeSet<Integer>();
        for (int i = 0; i < 11; i++) {
            expected.add(i);
        }
        addJagged();
        Set<Integer> dataSet = list.dataSet();
        assertEquals("Incorrect elements in the data set", expected, dataSet);
    }

    @Test (timeout = 200)
    public void testEmptyBasic() {
        assertNull(list.first());
        assertNull(list.last());
        assertNull(list.get(1));
        assertNull(list.remove(1));
        assertEquals("Empty set not being returned for an empty list",
                list.dataSet(), new HashSet<Integer>());
    }

    @Test (timeout = 1000)
    public void stressTesting_Add() {
        Random r = new Random(System.currentTimeMillis());
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            Integer num = r.nextInt();
            set.add(num);
            if (!list.contains(num)) {
                list.put(num);
            }
        }
        assertEquals("Adding not working properly", set, list.dataSet());
        assertEquals("Size isn't being incremented correctly", set.size(), list.dataSet().size());
    }

    @Test (timeout = 1000)
    public void stressTesting_LevelPromoting() {
        int quantity = 10000;
        Random r = new Random(System.currentTimeMillis());
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < quantity; i++) {
            Integer num = r.nextInt();
            set.add(num);
            if (!list.contains(num)) {
                list.put(num);
            }
        }
        Random rand =new Random(10);
        int flips = 0;
        for (int i = 0; i < quantity; i++) {
            while (rand.nextBoolean()) {
                flips++;
            }
            flips++;
        }
        assertEquals("Adding is not working properly", set, list.dataSet());
        assertEquals("CoinFlipper is not being used properly", flips, randomness.getNumFlips());
    }

    @Test (timeout = 1000)
    public void stressTesting_AddAndRemove() {
        Random r = new Random(System.currentTimeMillis());
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            Integer num = r.nextInt();
            if (r.nextBoolean()) {
                set.add(num);
                if (!list.contains(num)) {
                    list.put(num);
                }
            } else {
                set.remove(num);
                list.remove(num);
            }
        }
        assertEquals("Check your remove method", set, list.dataSet());
        assertEquals("Size isn't being decremented correctly", set.size(), list.dataSet().size());
    }

    @Test (timeout = 10000)
    public void stressTesting_AddAndRemoveAndContains() {
        Random r = new Random(System.currentTimeMillis());
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            Integer num = r.nextInt();
            if (r.nextBoolean()) {
                set.add(num);
                if (!list.contains(num)) {
                    list.put(num);
                }
            } else {
                set.remove(num);
                list.remove(num);
            }
        }

        for (int i = 0; i < 1000; i++) {
            Integer num = r.nextInt();
            assertEquals("Check your contains method", set.contains(num), list.dataSet().contains(num));
        }
    }

    @Test (timeout = 10000)
    public void stressTesting_String() {
        Random r = new Random(98);
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 50; i++) {
            String str = Character.toString((char) (r.nextInt(90) + 33));
            if (r.nextBoolean()) {
                set.add(str);
                if (!listString.contains(str)) {
                    listString.put(str);
                }
            } else {
                set.remove(str);
                listString.remove(str);
            }
        }
        assertEquals("Check your remove method", set, listString.dataSet());
        assertEquals("Size isn't being decremented correctly", set.size(), listString.dataSet().size());
    }

    private void addBasic() {
        for (int i = 10; i > -1; i--) {
            list.put(i);
        }
    }

    private void addJagged() {
        list.put(3);
        list.put(7);
        list.put(1);
        list.put(4);
        list.put(10);
        list.put(5);
        list.put(2);
        list.put(6);
        list.put(0);
        list.put(8);
        list.put(9);
    }

    @Test (timeout = 1000)
    public void testNullFirst() {
        assertNull(list.first());
    }

    @Test (timeout = 1000)
    public void testNullLast() {
        assertNull(list.last());
    }

    @Test (timeout = 1000)
    public void testNullContains() {
        assertEquals(false, list.contains(1));
    }

    @Test (timeout = 1000, expected = IllegalArgumentException.class)
    public void testIllegalContains() {
        list.contains(null);
    }

    @Test (timeout = 1000, expected = IllegalArgumentException.class)
    public void testIllegalPut() {
        list.put(null);
    }

    @Test (timeout = 1000, expected = IllegalArgumentException.class)
    public void testIllegalRemove() {
        list.remove(null);
    }

    @Test (timeout = 1000)
    public void testRemoveNull() {
        assertEquals(null, list.remove(2));
    }

    @Test (timeout = 1000, expected = IllegalArgumentException.class)
    public void testIllegalGet() {
        list.get(null);
    }

    @Test (timeout = 1000)
    public void testGetNull() {
        assertEquals(null, list.get(2));
    }

    @Test (timeout = 1000)
    public void testGet2() {
        list.put(3);
        list.put(8);
        assertEquals(null, list.get(2));
        assertEquals(new Integer(8), list.get(8));
    }

    @Test (timeout = 1000)
    public void testSize() {
        assertEquals(0, list.size());
        list.put(3);
        list.put(8);
        assertEquals(2, list.size());
    }

    @Test (timeout = 1000)
    public void testClear2() {
        list.put(3);
        list.put(8);
        list.clear();
        assertEquals(0, list.size());
    }

    @Test (timeout = 1000)
    public void testEmptySet() {
        assertEquals(0, list.dataSet().size());
    }
}
