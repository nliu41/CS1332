import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


import java.util.Map;

/**
 * Created by joshua on 10/27/14.
 * The students in these tests in no way resemble their "real" counterparts
 */
public class AdditionalTests {

    /**
     * So people seem to continue to have problems with the idea of lookup and search and the whole .equals vs == thing
     * As a result, these tests are not only going to test your code, but also teach you something about **why**
     * someone might want to do something silly like call tree.get(new Student("bob", null); and get Student("bob", 86)
     * back out.
     *
     * C++ has std::map and java has TreeMap.  These classes are implemented as Key-Value stores, TreeMap specifically
     * TreeMap<K,V> much like the generic HashMap is HashMap<K,V>.  These trees store things internally in Node classes
     * similar to the ones we use.  When you want to store a mapping of {String name -> String address} you can create a
     * TreeMap<String,String> which is conceptually extremely similar to the AVL tree we've created (its technically a
     * red-black tree).
     *
     * When you do that, you aren't actually storing a Node with separate key and value data, you actually store
     * these, more or less: http://docs.oracle.com/javase/8/docs/api/java/util/Map.Entry.html
     * Map.Entry is an interface that stores a Key.
     *
     * So, these tests will use "Student", a class that implements Map.Entry.
     */

    /**
     * equals, getKey, getValue, setValue are required by the Map.Entry<K,V> interface
     * also, the hacking required to get this working frightens me, java generics are quite possibly one of the dumbest
     * things in the universe
     *
     * This class also has a nice counter for keeping track of the number of compareTo calls used for BigO tracking
     * that is better than just adding 10000 things to a tree.
     */
    static private class Student<String extends Comparable<String>, Integer> implements Map.Entry<String, Integer>, Comparable<Student<String, Integer>> {
        static private int counter = 0;
        String key;
        Integer value;

        /**
         * A constructor for the student class, not strictly required, but it will simplify things
         * @param name - the name of the student
         * @param grade - the grade the student has in the course
         */
        public Student(String name, Integer grade) {
            this.key = name;
            this.value = grade;
        }

        /**
         * sets the counter back to zero
         */
        public static void resetCounter() {
            Student.counter = 0;
        }

        public static int getCounter() {
            return Student.counter;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            } else if (! (o instanceof Student)) {
                return false;
            } else if (((Student) o).getKey() == this.key) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public String getKey() {
            return this.key;
        }

        @Override
        public Integer getValue() {
            return this.value;
        }

        @Override
        public Integer setValue(Integer value) {
            Integer old = this.value;
            this.value = value;
            return old;
        }

        @Override
        public int compareTo(Student<String, Integer> o) {
            Student.counter++;
            return this.key.compareTo(o.getKey());
        }
    }

    /**
     * So now we have a Student class.  This class is the kind of thing that java.util.TreeMap might return.
     * The most obvious difference between the interface that TreeMap follows and the interface our AVL tree uses
     * is that the TreeMap hides the instantiation of the MapEntry inside the put method.  Our AVL tree requires that
     * you create the item before adding it.
     */

    private AVL<Student<String, Integer>> cs1332;

    @Before
    public void setup() {
        cs1332 = new AVL<Student<String, Integer>>();
    }

    /**
     * Now, lets say that Monica is wanting to use our new tool to keep track of her class.  She decided to put all of
     * the students into it and then use it to look up their grades when she forgets.
     */

    @Test(timeout = 250)
    public void ExampleTest() {
        cs1332.add(new Student<String, Integer>("Josh", 100));
        cs1332.add(new Student<String, Integer>("Emeke", 90));
        cs1332.add(new Student<String, Integer>("Alana", 25));
        cs1332.add(new Student<String, Integer>("Mitchell", 50));
        //obviously, Tech has been downsizing its classes
        //now that Monica needs to check grades again at the end of the semester, she forgot mine, so she uses the
        //get method that the class provides.  She forgot my grade though and wants to be able to find it without
        //having to know it to begin with, so she does this:
        cs1332.get(new Student<String, Integer>("Josh", null));

        //and now lets be sure that it works:
        assertEquals(new Integer(100), cs1332.get(new Student<String, Integer>("Josh", null)).getValue());
        //looks like I'm getting a 100 in 1332
        //or at least a guy can hope
    }

    /**
     * Now for some actual JUnits that do actual things and don't suck.
     */


    /**
     * tests a subset of rotations when only adding nodes
     * This currently tests
     *      right rotation at root
     *      left-right rotation at root where some rotated nodes have additional children
     */
    @Test(timeout = 250)
    public void testAdditionsOne() {
        cs1332.add(new Student<String, Integer>("Aron", 100));
        assertEquals("You're tree is broken and adding things doesn't work","Aron", cs1332.getRoot().getData().getKey());
        cs1332.add(new Student<String, Integer>("Emeke", 80));
        cs1332.add(new Student<String, Integer>("Josh", 92));
        //this causes a root level left rotation
        assertEquals("You're tree doesn't rotate left", "Emeke", cs1332.getRoot().getData().getKey());
        assertEquals("You're tree doesn't rotate left", "Aron", cs1332.getRoot().getLeft().getData().getKey());
        assertEquals("You're tree doesn't rotate left", "Josh", cs1332.getRoot().getRight().getData().getKey());
        cs1332.add(new Student<String, Integer>("Alana", 86));
        cs1332.add(new Student<String, Integer>("Craig", 95));
        cs1332.add(new Student<String, Integer>("Carey", 78));
        //this causes a complex rotation
        /**
         *                  E                     Cr
         *                 / \                   /  \
         *                Ar  J     ->          Ar   E
         *               /  \                  /  \   \
         *              Al   Cr               Al  Ca   J
         *                  /
         *                 Ca
         */
        assertEquals("A root level Left-Right rotation doesn't work", "Craig", cs1332.getRoot().getData().getKey());
        assertEquals("A root level Left-Right rotation doesn't work", "Aron", cs1332.getRoot().getLeft().getData().getKey());
        assertEquals("A root level Left-Right rotation doesn't work", "Alana", cs1332.getRoot().getLeft().getLeft().getData().getKey());
        assertEquals("A root level Left-Right rotation doesn't work", "Carey", cs1332.getRoot().getLeft().getRight().getData().getKey());
        assertEquals("A root level Left-Right rotation doesn't work", "Emeke", cs1332.getRoot().getRight().getData().getKey());
        assertEquals("A root level Left-Right rotation doesn't work", "Josh", cs1332.getRoot().getRight().getRight().getData().getKey());
    }

    /**
     * tests a subset of rotations when only adding nodes
     * This currently tests
     *      left rotation at root
     *      right-left at root with additional children
     */
    @Test(timeout = 250)
    public void testAdditionsTwo() {
        cs1332.add(new Student<String, Integer>("Josh", 92));
        assertEquals("You're tree is broken and adding things doesn't work", "Josh", cs1332.getRoot().getData().getKey());
        cs1332.add(new Student<String, Integer>("Emeke", 80));
        cs1332.add(new Student<String, Integer>("Aron", 100));
        assertEquals("You're tree doesn't rotate left", "Emeke", cs1332.getRoot().getData().getKey());
        assertEquals("You're tree doesn't rotate left", "Aron", cs1332.getRoot().getLeft().getData().getKey());
        assertEquals("You're tree doesn't rotate left", "Josh", cs1332.getRoot().getRight().getData().getKey());
        cs1332.add(new Student<String, Integer>("Ian", 82));
        cs1332.add(new Student<String, Integer>("Kirsten", 86));
        cs1332.add(new Student<String, Integer>("Issac", 73));
        //this also causes a similarly complex rotation
        /**
         *                  E                     Ia
         *                 / \                   /  \
         *                A   J     ->          E    J
         *                   / \               /    / \
         *                  Ia  K             A    Is  K
         *                   \
         *                    Is
         */
        assertEquals("A root level Right-Left rotation doesn't work", "Ian", cs1332.getRoot().getData().getKey());
        assertEquals("A root level Left-Right rotation doesn't work", "Emeke", cs1332.getRoot().getLeft().getData().getKey());
        assertEquals("A root level Left-Right rotation doesn't work", "Aron", cs1332.getRoot().getLeft().getLeft().getData().getKey());
        assertEquals("A root level Left-Right rotation doesn't work", "Josh", cs1332.getRoot().getRight().getData().getKey());
        assertEquals("A root level Left-Right rotation doesn't work", "Kirsten", cs1332.getRoot().getRight().getRight().getData().getKey());
        assertEquals("A root level Left-Right rotation doesn't work", "Issac", cs1332.getRoot().getRight().getLeft().getData().getKey());
    }

}