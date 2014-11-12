import java.util.ArrayList;
import java.util.List;

public class StringSearching implements StringSearchingInterface {

    @Override
    public List<Integer> boyerMoore(CharSequence pattern,
                                    CharSequence text) {
        if (pattern == null || pattern.length() < 1
                || text == null || pattern.length() > text.length()) {
            throw new IllegalArgumentException();
        }

        List<Integer> result = new ArrayList<Integer>();

        if (text.length() > 0) {
            boyerMoore(pattern, text, result);
        }

        return result;
    }

    @Override
    public int[] buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException();
        }
        int[] result = new int[Character.MAX_VALUE + 1];

        // padding with -1
        for (int i = 0; i < result.length; i++) {
            result[i] = -1;
        }

        // compute last char
        for (int i = 0; i < pattern.length(); i++) {
            result[pattern.charAt(i)] = i;
        }

        return result;
    }

    @Override
    public int generateHash(CharSequence current,
                            int length) {
        if (current == null || length > current.length() || length < 0) {
            throw new IllegalArgumentException();
        }

        int hash = 0;
        for (int i = 0; i < length; i++) {
            hash += ((int) current.charAt(i))
                    * pow(length - 1 - i);
        }

        return hash;
    }

    @Override
    public int updateHash(int oldHash, int length,
                          char oldChar, char newChar) {
        // TODO edge case check
        if (length < 0) {
            throw new IllegalArgumentException();
        }

        if (length == 1) {
            return oldHash;
        }

        oldHash -= (int) oldChar * pow(length - 1);
        oldHash *= StringSearchingInterface.BASE;
        oldHash += (int) newChar;
        return oldHash;
    }

    @Override
    public List<Integer> rabinKarp(CharSequence pattern,
                                   CharSequence text) {
        if (pattern == null || pattern.length() < 1
                || text == null || pattern.length() > text.length()) {
            throw new IllegalArgumentException();
        }

        List<Integer> result = new ArrayList<Integer>();

        if (text.length() > 0) {
            rabinKarp(pattern, text, result);
        }

        return result;
    }


    /**
     * Rabin Karp helper that actually conducts the search
     *
     * @param pattern to search for
     * @param text to search within
     * @param list list to add results to
     */
    private void rabinKarp(CharSequence pattern,
                           CharSequence text,
                           List<Integer> list) {
        final int patternHash = generateHash(pattern, pattern.length());
        int textHash          = generateHash(text, pattern.length());

        for (int begin = 0, end = pattern.length() - 1;
             end < text.length();
             begin++) {

            if (patternHash == textHash) {      // check characters
                boolean mismatch = false;
                for (int i = 0; !mismatch
                        && i < pattern.length(); i++) {
                    if (text.charAt(i + begin) != pattern.charAt(i)) {
                        mismatch = true;
                    }
                }
                if (!mismatch) {
                    list.add(begin);
                }
            }

            if (++end < text.length()) {        // shift by one
                textHash = updateHash(textHash,
                        pattern.length(),
                        text.charAt(begin),
                        text.charAt(end));
            }
        }


    }

    /**
     * Integer power calculator
     * @param n is exponent
     * @return result of power exponentiation
     */
    private int pow(int n) {
        int power = 1;

        for (int j = 0; j < n; j++) {
            power *= StringSearchingInterface.BASE;
        }

        return power;
    }

    /**
     * Boyer Moore helper that actually does the
     * string search
     * @param pattern to search for
     * @param text to look within
     * @param list for storing results
     */
    private void boyerMoore(CharSequence pattern,
                            CharSequence text,
                            List<Integer> list) {
        final int[] lastTable = buildLastTable(pattern);
        int i = pattern.length() - 1;
        int j = pattern.length() - 1;

        while (i < text.length()) {
            int currentChar = text.charAt(i);

            if (currentChar == pattern.charAt(j)) {
                if (j == 0) {
                    list.add(i); // match at i

                    int last = lastTable[currentChar] + 1;
                    i += pattern.length() - Math.min(j, last);
                    j = pattern.length() - 1;
                } else {
                    i--;
                    j--;
                }
            } else { // jump character
                int last = lastTable[currentChar] + 1;
                i += pattern.length() - Math.min(j, last);
                j = pattern.length() - 1;
            }
        }
    }
}
