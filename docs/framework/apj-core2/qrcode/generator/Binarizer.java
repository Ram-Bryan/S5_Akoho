/*
 * Decompiled with CFR 0.152.
 */
package qrcode.generator;

import java.util.ArrayList;
import java.util.BitSet;

public class Binarizer {
    public int E;

    public Binarizer(int e) {
        this.E = e;
    }

    public String toBin() {
        int j;
        int q;
        boolean b;
        String Resultat = "";
        int i = 0;
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        if (this.E == 1) {
            b = true;
            res.add(b);
        } else if (this.E == 0) {
            b = false;
            res.add(b);
        }
        while ((q = this.E / 2) != 0) {
            boolean b2;
            int r;
            if (q != 1) {
                r = this.E % 2;
                if (r == 1) {
                    b2 = true;
                    res.add(b2);
                } else {
                    b2 = false;
                    res.add(b2);
                }
                this.E = q;
                ++i;
                continue;
            }
            if (q != 1) continue;
            r = this.E % 2;
            if (r == 1) {
                b2 = true;
                res.add(b2);
            } else {
                b2 = false;
                res.add(b2);
            }
            boolean bol = true;
            res.add(bol);
            break;
        }
        BitSet bt = new BitSet(res.size());
        int k = 0;
        for (j = res.size() - 1; j >= 0; --j) {
            bt.set(j, (Boolean)res.get(k));
            ++k;
        }
        for (j = 0; j < bt.length(); ++j) {
            Resultat = bt.get(j) ? Resultat + "1" : Resultat + "0";
        }
        return Resultat;
    }
}

