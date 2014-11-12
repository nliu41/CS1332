import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Huffman {

    // Do NOT add any variables (instance or static)

    /**
     * Builds a frequency map of characters for the given string.
     *
     * This should just be the count of each character.
     * No character not in the input string should be in the frequency map.
     *
     * @param s the string to map
     * @return the character -> Integer frequency map
     */
    public static Map<Character, Integer> buildFrequencyMap(String s) {
        if (s == null || s.length() < 1) {
            throw new IllegalArgumentException();
        }

        // TreeMap is naturally sorted, // Character, Integer
        HashMap<Character, Integer> map = new HashMap<>(26 * 2);
        char[] arr = s.toCharArray();
        // for each, increment each
        for (char c : arr) {
            int oldValue = (map.containsKey(c)) ? map.get(c) : 0;
            map.put(c, oldValue + 1);
        }

        return map;
    }

    /**
     * Build the Huffman tree using the frequencies given.
     *
     * Add the nodes to the tree based on their natural ordering (the order
     * given by the compareTo method).
     * The frequency map will not necessarily come from the
     * {@code buildFrequencyMap()} method. Every entry in the map should be in
     * the tree.
     *
     * @param freq the frequency map to represent
     * @return the root of the Huffman Tree
     */
    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> freq) {
        if (freq == null || freq.size() < 1) {
            throw new IllegalArgumentException();
        }
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(freq.size());

        // first insert all characters into the huffman tree
        int i = 0;
        for (char c : freq.keySet()) {
            i = freq.get(c);
            pq.add(new HuffmanNode(c, i));
        }

        // while the priority queue contains more than one item
        while (pq.size() > 1) {
            HuffmanNode left, right;
            left = pq.remove();
            right = pq.remove();
            pq.add(new HuffmanNode(left, right));
        }

        return pq.remove();
    }

    /**
     * Traverse the tree and extract the encoding for each character in the
     * tree.
     *
     * The tree provided will be a valid huffman tree but may not come from the
     * {@code buildHuffmanTree()} method.
     *
     * @param tree the root of the Huffman Tree
     * @return the map of each character to the encoding string it represents
     */
    public static Map<Character, EncodedString> buildEncodingMap(
            HuffmanNode tree) {
        if (tree == null) {
            throw new IllegalArgumentException();
        }

        Map<Character, EncodedString> map = new HashMap<>(26 * 2);
        // Single character case
        if (tree.getLeft() == null && tree.getRight() == null) {
            EncodedString es = new EncodedString();
            es.zero();
            map.put(tree.getCharacter(), es);
        } else {
            map = buildEncodingMap(tree, map, new EncodedString());
        }
        return map;
    }

    /**
     * Encode each character in the string using the map provided.
     *
     * If a character in the string doesn't exist in the map ignore it.
     *
     * The encoding map may not necessarily come from the
     * {@code buildEncodingMap()} method, but will be correct for the tree given
     * to {@code decode()} when decoding this method's output.
     *
     * @param encodingMap the map of each character to the encoding string it
     * represents
     * @param s the string to encode
     * @return the encoded string
     */
    public static EncodedString encode(Map<Character, EncodedString>
                                               encodingMap, String s) {
        if (encodingMap == null || s == null || s.length() < 1) {
            throw new IllegalArgumentException();
        }
        EncodedString es = new EncodedString();
        for (char c : s.toCharArray()) {
            es.concat(encodingMap.get(c));
        }
        return es;
    }

    /**
     * Decode the encoded string using the tree provided.
     *
     * The encoded string may not necessarily come from {@code encode()}, but
     * will be a valid string for the given tree.
     *
     * (tip: use StringBuilder to make this method faster -- concatenating
     * strings is SLOW.)
     *
     * @param tree the tree to use to decode the string
     * @param es the encoded string
     * @return the decoded string
     */
    public static String decode(HuffmanNode tree, EncodedString es) {
        if (tree == null || es == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder s = new StringBuilder();
        HuffmanNode current = tree;
        boolean singleItem = (tree.getLeft() == null
                && tree.getRight() == null);

        for (byte b : es) {
            if (current == null) {
                // not sure when this would happen,
                // probably when the string does not match
                // throw new IllegalStateException(
                //        "Couldn't find EncodedString in Huffman Tree");
                current = tree;
            } else if (b == 0 && !singleItem) { // go left
                current = current.getLeft();
            } else if (b == 1 && !singleItem) { // go right
                current = current.getRight();
            }

            // leaf, add and reset
            if (current.getLeft() == null && current.getRight() == null) {
                s.append(current.getCharacter());
                current = tree;
            }
        }
        return s.toString();
    }

    /**
     * Debugs a string by printing to console
     * @param s is a string to debug
     */
    private static void debug(String s) {
        if (false) { // flip a bit to turn this on or off
            System.out.print(s);
        }
    }

    /**
     * Copies an EncodedString to a new EncodedString object
     * @param s is the encoded string to copy
     * @return the copied encoded string
     */
    private static EncodedString copyEncodedString(EncodedString s) {
        EncodedString s1 = new EncodedString();
        s1.concat(s);
        return s1;
    }

    /**
     * Recursive function to build encoding map
     * @param tree is the root of the tree
     * @param map is the map to store the
     *            grouping of encoded strings to
     * @param s is the encoded string
     * @return the updated map
     */
    private static Map<Character, EncodedString> buildEncodingMap(
            HuffmanNode tree, Map<Character, EncodedString> map,
            EncodedString s) {
        if (tree != null) {
            if (tree.getLeft() == null && tree.getRight() == null) {
                // leaf, process me :P
                map.put(tree.getCharacter(), s);

            } else {
                EncodedString s1 = copyEncodedString(s);
                s1.zero();
                map = buildEncodingMap(tree.getLeft(), map, s1);

                EncodedString s2 = copyEncodedString(s);
                s2.one();
                map = buildEncodingMap(tree.getRight(), map, s2);
            }
        }
        return map;
    }
}
