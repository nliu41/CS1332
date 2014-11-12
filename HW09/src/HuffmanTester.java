import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class HuffmanTester {

    @Test
    public void testEncodeSingleChar() throws Exception {
        Map<Character, Integer> map = Huffman.buildFrequencyMap("aaaaaaaaaaaaaa");
        int freq = map.get('a');
        assertEquals("Did not get the correct frequency", 14, freq);

        HuffmanNode huffNode = Huffman.buildHuffmanTree(map);
        assertTrue("The HuffmanNode should not have children", (huffNode.getLeft() == null && huffNode.getRight() == null));

        Map<Character, EncodedString> enMap = Huffman.buildEncodingMap(huffNode);
        assertEquals("Should only have 1 key-value pair..", 1, enMap.size());
        assertEquals("The encoded value should only be one byte long", 1, enMap.get('a').length());

        EncodedString es = Huffman.encode(enMap, "aaaaaaaaaaaaaa");

        String s = Huffman.decode(huffNode, es);
        assertEquals("Encode should equal", "aaaaaaaaaaaaaa", s);
    }

    @Test
    public void testHuffman() throws Exception {
        testEncodeDecode("a");
        testEncodeDecode("aa");
        testEncodeDecode("aaa");
        testEncodeDecode("aaaa");
        testEncodeDecode("aaaaa");
        testEncodeDecode("ab");
        testEncodeDecode("1");
        testEncodeDecode("101");
        testEncodeDecode(" !\"#$%&\\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");
        testEncodeDecode(" !\"#$%&\\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" +
                         " !\"#$%&\\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");

        StringBuilder s1 = new StringBuilder(100000);
        for (int i = 0; i < 1000; i++) {
            s1.append((char) i);
        }
        testEncodeDecode(s1.toString());

        StringBuilder s = new StringBuilder(100000);
        for (int i = 0; i < 100000; i++) {
            s.append((char) (((i % 127) + 32) % 127));
        }
        testEncodeDecode(s.toString());
    }

    private void testEncodeDecode(String s) {
        Map<Character, Integer> map = Huffman.buildFrequencyMap(s);
        HuffmanNode huffNode = Huffman.buildHuffmanTree(map);
        Map<Character, EncodedString> enMap = Huffman.buildEncodingMap(huffNode);
        EncodedString es = Huffman.encode(enMap, s);
        String str = Huffman.decode(huffNode, es);
        assertEquals("Encode should equal", s, str);
    }
}