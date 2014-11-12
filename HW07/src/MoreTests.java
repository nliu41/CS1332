import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MoreTests {
    private AVL<MagicNumber> avlMagicNumber;

    @Before
    public void setup() {
        avlMagicNumber = new AVL<MagicNumber>();
    }

    @Test(timeout = 250)
    public void testEverything() {
        assertTrue(avlMagicNumber.isEmpty());
        assertEquals(0, avlMagicNumber.size());

        avlMagicNumber.add(new MagicNumber("c", 3));
        avlMagicNumber.add(new MagicNumber("f", 10));
        avlMagicNumber.add(new MagicNumber("k", 20));
        avlMagicNumber.add(new MagicNumber("g", 15));
        avlMagicNumber.add(new MagicNumber("i", 16));
        Node<MagicNumber> root = avlMagicNumber.getRoot();

        assertEquals(5, avlMagicNumber.size());
        assertTrue(avlMagicNumber.contains(new MagicNumber("c", 3)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("f", 10)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("k", 20)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("g", 15)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("i", 16)));
        assertFalse(avlMagicNumber.isEmpty());


        assertEquals(new MagicNumber("f", 10), root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        assertEquals(new MagicNumber("c", 3), root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals(new MagicNumber("i", 16), root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals(new MagicNumber("g", 15), root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals(new MagicNumber("k", 20), root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());


        assertEquals(new MagicNumber("i", 16), avlMagicNumber.remove(new MagicNumber("i", 16)));

        assertTrue(avlMagicNumber.contains(new MagicNumber("c", 3)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("f", 10)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("k", 20)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("g", 15)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("i", 16)));
        assertFalse(avlMagicNumber.isEmpty());
        assertEquals(4, avlMagicNumber.size());

        assertEquals(new MagicNumber("f", 10), root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        assertEquals(new MagicNumber("c", 3), root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals(new MagicNumber("g", 15), root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(-1, root.getRight().getBalanceFactor());

        assertEquals(new MagicNumber("k", 20), root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());


        assertEquals(new MagicNumber("f", 10), avlMagicNumber.remove(new MagicNumber("f", 10)));

        assertTrue(avlMagicNumber.contains(new MagicNumber("c", 3)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("f", 10)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("k", 20)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("g", 15)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("i", 16)));
        assertFalse(avlMagicNumber.isEmpty());
        assertEquals(3, avlMagicNumber.size());

        root = avlMagicNumber.getRoot();

        assertEquals(new MagicNumber("g", 15), root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals(new MagicNumber("k", 20), root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals(new MagicNumber("c", 3), root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        avlMagicNumber.add(new MagicNumber("h", 16));
        avlMagicNumber.add(new MagicNumber("l", 21));
        avlMagicNumber.add(new MagicNumber("a", 1));
        avlMagicNumber.add(new MagicNumber("d", 4));

        assertTrue(avlMagicNumber.contains(new MagicNumber("c", 3)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("f", 10)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("k", 20)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("g", 15)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("i", 16)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("h", 16)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("l", 21)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("a", 1)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("d", 4)));
        assertFalse(avlMagicNumber.isEmpty());
        assertEquals(7, avlMagicNumber.size());

        assertEquals(new MagicNumber("g", 15), root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals(new MagicNumber("k", 20), root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals(new MagicNumber("c", 3), root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals(new MagicNumber("h", 16), root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals(new MagicNumber("l", 21), root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals(new MagicNumber("a", 1), root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals(new MagicNumber("d", 4), root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());


        assertEquals(new MagicNumber("g", 15), avlMagicNumber.remove(new MagicNumber("g", 15)));

        assertTrue(avlMagicNumber.contains(new MagicNumber("c", 3)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("f", 10)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("k", 20)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("g", 15)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("i", 16)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("h", 16)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("l", 21)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("a", 1)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("d", 4)));
        assertFalse(avlMagicNumber.isEmpty());
        assertEquals(6, avlMagicNumber.size());

        assertEquals(new MagicNumber("d", 4), root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals(new MagicNumber("c", 3), root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());

        assertEquals(new MagicNumber("a", 1), root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());


        assertEquals(new MagicNumber("l", 21), avlMagicNumber.remove(new MagicNumber("l", 21)));

        assertTrue(avlMagicNumber.contains(new MagicNumber("c", 3)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("f", 10)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("k", 20)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("g", 15)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("i", 16)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("h", 16)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("l", 21)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("a", 1)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("d", 4)));
        assertFalse(avlMagicNumber.isEmpty());
        assertEquals(5, avlMagicNumber.size());

        assertEquals(new MagicNumber("d", 4), root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals(new MagicNumber("k", 20), root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(1, root.getRight().getBalanceFactor());


        assertEquals(new MagicNumber("a", 1), avlMagicNumber.remove(new MagicNumber("a", 1)));

        assertTrue(avlMagicNumber.contains(new MagicNumber("c", 3)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("f", 10)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("k", 20)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("g", 15)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("i", 16)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("h", 16)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("l", 21)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("a", 1)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("d", 4)));
        assertFalse(avlMagicNumber.isEmpty());
        assertEquals(4, avlMagicNumber.size());

        assertEquals(new MagicNumber("d", 4), root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        assertEquals(new MagicNumber("c", 3), root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());


        assertEquals(new MagicNumber("c", 3), avlMagicNumber.remove(new MagicNumber("c", 3)));

        assertFalse(avlMagicNumber.contains(new MagicNumber("c", 3)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("f", 10)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("k", 20)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("g", 15)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("i", 16)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("h", 16)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("l", 21)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("a", 1)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("d", 4)));
        assertFalse(avlMagicNumber.isEmpty());
        assertEquals(3, avlMagicNumber.size());

        root = avlMagicNumber.getRoot();

        assertEquals(new MagicNumber("h", 16), root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals(new MagicNumber("d", 4), root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals(new MagicNumber("k", 20), root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());


        assertEquals(new MagicNumber("d", 4), avlMagicNumber.remove(new MagicNumber("d", 4)));

        assertFalse(avlMagicNumber.contains(new MagicNumber("c", 3)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("f", 10)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("k", 20)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("g", 15)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("i", 16)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("h", 16)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("l", 21)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("a", 1)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("d", 4)));
        assertFalse(avlMagicNumber.isEmpty());
        assertEquals(2, avlMagicNumber.size());

        assertEquals(new MagicNumber("h", 16), root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        assertEquals(new MagicNumber("k", 20), root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals(new MagicNumber("h", 16), avlMagicNumber.remove(new MagicNumber("h", 16)));

        assertFalse(avlMagicNumber.contains(new MagicNumber("c", 3)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("f", 10)));
        assertTrue(avlMagicNumber.contains(new MagicNumber("k", 20)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("g", 15)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("i", 16)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("h", 16)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("l", 21)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("a", 1)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("d", 4)));
        assertFalse(avlMagicNumber.isEmpty());
        assertEquals(1, avlMagicNumber.size());

        assertEquals(new MagicNumber("k", 20), avlMagicNumber.getRoot().getData());
        assertEquals(0, avlMagicNumber.getRoot().getHeight());
        assertEquals(0, avlMagicNumber.getRoot().getBalanceFactor());

        assertEquals(new MagicNumber("k", 20), avlMagicNumber.remove(new MagicNumber("k", 20)));

        assertFalse(avlMagicNumber.contains(new MagicNumber("c", 3)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("f", 10)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("k", 20)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("g", 15)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("i", 16)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("h", 16)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("l", 21)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("a", 1)));
        assertFalse(avlMagicNumber.contains(new MagicNumber("d", 4)));
        assertTrue(avlMagicNumber.isEmpty());
        assertEquals(0, avlMagicNumber.size());

        assertEquals(null, avlMagicNumber.getRoot());

        avlMagicNumber.add(new MagicNumber("h", 16));
        avlMagicNumber.clear();
        assertTrue(avlMagicNumber.isEmpty());

    }

    private class MagicNumber implements Comparable<MagicNumber> {
        private final String string;
        private final int magicNumber;

        public MagicNumber(String string) {
            this(string, 0);
        }

        public MagicNumber(String string, int magicNumber) {
            this.string = string;
            this.magicNumber = magicNumber;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (!(other instanceof MagicNumber)) {
                return false;
            }
            MagicNumber that = (MagicNumber) other;
            return that.string.equals(string);
        }

        @Override
        public int compareTo(MagicNumber other) {
            return string.compareTo(other.string);
        }

        @Override
        public String toString() {
            return "MagicNumber: " + string + ", " + magicNumber;
        }
    }
}