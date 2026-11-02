/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import java.util.Iterator;
import utilitaire.AlphaUtils;

public class AlphaIterator
implements Iterator<String> {
    private int maxIndex;
    private int index;
    private char[] alphabet;

    public AlphaIterator() {
        this(Integer.MAX_VALUE);
    }

    public AlphaIterator(int maxIndex) {
        this(maxIndex, "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray());
    }

    public AlphaIterator(char[] alphabet) {
        this(Integer.MAX_VALUE, alphabet);
    }

    public AlphaIterator(int maxIndex, char[] alphabet) {
        this.maxIndex = maxIndex;
        this.alphabet = alphabet;
        this.index = 1;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.maxIndex;
    }

    @Override
    public String next() {
        return AlphaUtils.indexToColumnItr(this.index++, this.alphabet);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

