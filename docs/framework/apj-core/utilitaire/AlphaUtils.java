/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import utilitaire.AlphaIterator;

public class AlphaUtils {
    public static String indexToColumnItr(int index, char[] alphabet) {
        if (index <= 0) {
            throw new IndexOutOfBoundsException("index must be a positive number");
        }
        if (index <= alphabet.length) {
            return Character.toString(alphabet[index - 1]);
        }
        StringBuffer sb = new StringBuffer();
        while (index > 0) {
            sb.insert(0, alphabet[--index % alphabet.length]);
            index /= alphabet.length;
        }
        return sb.toString();
    }

    public static String indexToColumnRec(int index, char[] alphabet) {
        if (index <= 0) {
            throw new IndexOutOfBoundsException("index must be a positive number");
        }
        if (index <= alphabet.length) {
            return Character.toString(alphabet[index - 1]);
        }
        return AlphaUtils.indexToColumnRec(--index / alphabet.length, alphabet) + alphabet[index % alphabet.length];
    }

    public static String[] generateSequence(int size) {
        String[] sequence = new String[size];
        int i = 0;
        AlphaIterator it = new AlphaIterator(size);
        while (it.hasNext()) {
            sequence[i] = it.next();
            ++i;
        }
        return sequence;
    }
}

