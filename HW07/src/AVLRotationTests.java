import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This test suite ensures that the most basic parts of your AVL tree work.
 * However, much of it's ability to completely test your implementation
 * depends on you having used the fact that subtrees are themselves an
 * AVL tree, to your advantage. In other words, because every left and
 * right node of an AVL node is itself an AVL tree, this test suite doesn't
 * have to test insane numbers of cases to completely test your implementation.
 *
 * Disclaimer: Use these at your own risk. I'm not responsible for what you do
 * with this file, whether it be getting a bad grade because you trusted me and
 * I turned out to be wrong, starting a thermonuclear war, bricking your phone,
 * or any other side effect, intended or unintended. Though if you believe that
 * there's a problem with these test cases, please tell me! Enjoy!
 *
 * @author Siddharth Duddikunta
 */
public class AVLRotationTests {
    private AVL<String> tree;

    @Before
    public void setup() {
        tree = new AVL<>();
    }

    // BEGIN BASIC FUNCTIONALITY TESTS

    @Test
    public void testNewTree() {
        assertEquals(0, tree.size());
        assertTrue(tree.isEmpty());
        assertNull(tree.getRoot());
    }

    @Test
    public void testAddingAValue() {
        tree.add("somedata");
        assertEquals(1, tree.size());
        assertFalse(tree.isEmpty());
        assertNotNull(tree.getRoot());
    }

    @Test
    public void testAddingAndGetting() {
        tree.add("somedata");
        assertEquals(1, tree.size());
        assertFalse(tree.isEmpty());
        assertNotNull(tree.getRoot());
        assertTrue(tree.contains("somedata"));
        assertEquals("somedata", tree.get("somedata"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNull() {
        tree.add(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGettingNull() {
        tree.get(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemovingNull() {
        tree.remove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testContainsNull() {
        tree.contains(null);
    }

    @Test()
    public void testRemovingNonexistant() {
        assertNull(tree.remove(""));
    }

    @Test()
    public void testGettingNonexistant() {
        assertNull(tree.get(""));
        assertFalse(tree.contains(""));
    }

    @Test
    public void testAddingASmallerValue() {
        testAddingAValue();
        tree.add("Something");
        assertEquals(2, tree.size());
        assertNotNull(tree.getRoot().getLeft());
    }

    @Test
    public void testAddingABiggerValue() {
        testAddingAValue();
        tree.add("z");
        assertEquals(2, tree.size());
        assertNotNull(tree.getRoot().getRight());
    }

    @Test
    public void testAddingBothBiggerAndSmaller() {
        testAddingASmallerValue();
        tree.add("z");
        assertEquals(3, tree.size());
        assertNotNull(tree.getRoot().getRight());
    }

    // END BASIC FUNCTIONALITY TESTS
 
    /* BEGIN ROTATION TESTS
        Note that failing any of these tests indicates a serious rebalancing
        problem, which will likely cause you to fail many other tests. These
        tests use simplest case rotations with both zero and some children.
     */

    @Test
    public void testInsertWithLeftRotation() {
        testInsert("abc", "bac");
    }

    @Test
    public void testInsertWithRightRotation() {
        testInsert("cba", "bac");
    }

    @Test
    public void testInsertWithRightLeftRotation() {
        testInsert("acb", "bac");
    }

    @Test
    public void testInsertWithLeftRightRotation() {
        testInsert("cab", "bac");
    }

    @Test
    public void testDeletionWithLeftRotate() {
        testDelete("bcad", "a", "cbd");
    }

    @Test
    public void testDeletionWithRightRotate() {
        testDelete("cbda", "d", "bac");
    }

    @Test
    public void testDeletionWithRightLeftRotation() {
        testDelete("bdac", "a", "cbd");
    }

    @Test
    public void testDeletionWithLeftRightRotation() {
        testDelete("cadb", "d", "bac");
    }

    @Test
    public void testComplexRotationCases() {
        testInsert("cbedfg", "ecfbdg");
        tree.clear();
        testDelete("cbedfag", "a", "ecfbdg");
        tree.clear();
        testInsert("ecfbda", "cbeadf");
        tree.clear();
        testDelete("ecfbdga", "g", "cbeadf");
        tree.clear();
        testInsert("ecjadhkgilf", "hejcgikadfl");
        tree.clear();
        testDelete("ecjadhkgilbf", "b", "hejcgikadfl");
        tree.clear();
        testInsert("hckbeiladfg", "echbdfkagil");
        tree.clear();
        testDelete("hckbeiladfjg", "j", "echbdfkagil");
    }

    // END ROTATION TESTS
 
    /* BEGIN "STRESS" TESTS
       These tests tie together multiple rotations, deletion types, etc.
       If you don't pass these, but pass the above, check your implementation
       for possible edge cases.
    */

    @Test
    public void testInserts() {
        testInsert("knYoVNlXDIgUhfKtTSjEMHeLQFcZWdiwyqsbrRGvBz",
                "VNkITctELRUYgqwDGKMQSXZeinsvyBFHWbdfhjlorz");
        tree.clear();
        testInsert("fleHpSsvdKqNZUPDjBrLEOTCGtMWxgVaYmFIbizJuc",
                "SKfDNZpBGLPUdjsCEIMOTWbeglqvFHJVYacimrtxuz");
        tree.clear();
        testInsert("OYAEUNrHWdRGoSnyPXlxzLVfwkJachTbsFMtDeuCvi",
                "YOoHUfxELRWcluyCGJNPSVXadinswzADFMTbehkrtv");
        tree.clear();
        testInsert("XgDCEinozUQkSyMIeGNRwKdvVcFJftpZaAjHsYOBxh",
                "XInEQevCGMScipyADFHKNRUZdgkotwzBJOVYafhjsx");
        tree.clear();
        testInsert("zdvagueYXyPlwLZjWASHmQJDIRcbKhOEMfTntCFVBk",
                "YPgHScnDLRWaelvBEJOQTXZbdfjmuyACFIKMVhktwz");
    }

    @Test
    public void testRemovals() {
        testDelete("KUTExFtpjyNiZLIAwGahsuXvogVkSlembnzrcCfDdP",
                "a", "iTtFboxCKVekrvyAEINUZcgjmpsuwzDGLSXdfhlnP");
        tree.clear();
//        testDelete("txzvHrIKMGsaPioQOwcpRLblDCfUuNqAkyXhgnmJSZ",
//                "n", "iMtIapxGKQclrvzCHJLOUbgkoqsuwyADNPRXfhmSZ");
        tree.clear();
        testDelete("hnDVgYzNtuJmaCklOoqRwxKATjHBfSQIMFPdrEyGcW",
                "c", "gOnHVkuCJSahmqxAEIMQTYdjlotwzBDFKNPRWfryG");
        tree.clear();
        testDelete("qaQIpioGeMJBvlCxjAdPcSEKzZugXHstyNDfLWbUwF",
                "m", "aIpCQivBGMXdlsyAEHKPUZcfjoquxzDFJLNSWbegtw");
        tree.clear();
        testDelete("QvnZswNzaBYGiXjfSAyKbTOxrHCcotkhgpPELIVeMF",
                "q", "ZNnGQfwBKOXbjsyAEHLPTYachkpvxzCFIMSVegiort");
    }

    // END "STRESS" TESTS

    // BEGIN UTILITY METHODS

    private void testInsert(String insertOrder, String levelOrder) {
        for (int i = 0; i < insertOrder.length(); i++) {
            tree.add(insertOrder.substring(i, i + 1));
        }
        String test = "";
        for (String s : tree.levelorder()) {
            test += s;
        }
        assertEquals(levelOrder, test);
    }

    private void testDelete(String insertOrder, String toDelete, String levelOrder) {
        for (int i = 0; i < insertOrder.length(); i++) {
            tree.add(insertOrder.substring(i, i + 1));
        }
        tree.remove(toDelete);
        String test = "";
        for (String s : tree.levelorder()) {
            test += s;
        }
        assertEquals(levelOrder, test);
    }

    // END UTILITY METHODS
}