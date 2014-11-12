import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

/**
 * Created by Daniel on 11/6/2014.
 */
public class HuffmanEdgeCases {

    @Test(expected=IllegalArgumentException.class)
    public void testNullString() {
        Map<Character, Integer> map = Huffman.buildFrequencyMap(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullHuffTree() {
        Huffman.buildHuffmanTree(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullEncodingMap() {
        Huffman.buildEncodingMap(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullEncode() {
        Huffman.encode(null, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullDecode() {
        Huffman.decode(null, null);
    }

    @Test
    public void testStringWithOneChar() {
        Map<Character, Integer> map = Huffman.buildFrequencyMap("aaaaaaaaaaaaaa");
        assertEquals("There should be only one encoded value",1, map.size());
        int freq = map.get('a');
        assertEquals("Did not get the correct frequency", 14, freq); //did this to shut intellij up

        HuffmanNode huffNode = Huffman.buildHuffmanTree(map);
        assertTrue("The HuffmanNode should not have children", (huffNode.getLeft() == null && huffNode.getRight() == null));

        Map<Character, EncodedString> enMap = Huffman.buildEncodingMap(huffNode);
        assertEquals("Should only have 1 key-value pair..", 1, enMap.size());
        assertEquals("The encoded value should only be one byte long", 1, enMap.get('a').length());
    }

    @Test
    public void testNormalStr() {
        Map<Character, Integer> map = Huffman.buildFrequencyMap("abracadabra");
        assertEquals("Testing freq map size", 5, map.size());

        int freq; //intellij being annoying again
        for (Map.Entry<Character, Integer> curEntry : map.entrySet()) {
            if (curEntry.getKey() == 'a') {
                freq = curEntry.getValue();
                assertEquals("Wrong freq for a", 5, freq);
            } else if (curEntry.getKey() == 'b' || curEntry.getKey() == 'r') {
                freq = curEntry.getValue();
                assertEquals("Wrong freq for b or r", 2, freq);
            } else if (curEntry.getKey() == 'c' || curEntry.getKey() == 'd') {
                freq = curEntry.getValue();
                assertEquals("Wrong freq for c or d", 1, freq);
            }
        }

        HuffmanNode tree = Huffman.buildHuffmanTree(map);
        Map<Character, EncodedString> enMap = Huffman.buildEncodingMap(tree);

        EncodedString esAbraCadabra = Huffman.encode(enMap, "abracadabra");
        assertEquals("Can't decode or encode properly", "abracadabra", Huffman.decode(tree, esAbraCadabra));

        EncodedString esOther = Huffman.encode(enMap, "abbbbrrrrcccaaaaddddrrrrr");
        assertEquals("Can't decode or encode properly with a different string", "abbbbrrrrcccaaaaddddrrrrr", Huffman.decode(tree, esOther));
    }
}
