/*
 * Decompiled with CFR 0.152.
 */
package qrcode.generator;

import qrcode.generator.Binarizer;

public class Cryptage {
    public int M;
    public int E;
    public int N;

    public Cryptage(int m, int e, int n) {
        this.M = m;
        this.E = e;
        this.N = n;
    }

    public int calculate() {
        int c = 1;
        String b = new Binarizer(this.E).toBin();
        int k = b.length();
        for (int i = 0; i < k; ++i) {
            c = b.charAt(i) == '0' ? this.puissance(c, 2) % this.N : this.puissance(c, 2) * this.M % this.N;
        }
        return c;
    }

    private int puissance(int x, int n) {
        int resPuis = 1;
        if (x == 0) {
            return 0;
        }
        if (n == 0) {
            return 1;
        }
        for (int i = 0; i < n; ++i) {
            resPuis *= x;
        }
        return resPuis;
    }
}

