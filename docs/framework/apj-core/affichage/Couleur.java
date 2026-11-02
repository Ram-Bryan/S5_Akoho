/*
 * Decompiled with CFR 0.152.
 */
package affichage;

public class Couleur {
    private static final int couleurMax = 255;
    private static final int opaciteMax = 1;
    int r;
    int v;
    int b;
    double a = 1.0;

    public Couleur(int r, int v, int b) throws Exception {
        this.setR(r);
        this.setV(v);
        this.setB(b);
    }

    public Couleur(int r, int v, int b, double a) throws Exception {
        this.setR(r);
        this.setV(v);
        this.setB(b);
        this.setA(a);
    }

    public Couleur[] genererCouleur(int pas, int taille) throws Exception {
        Couleur[] couleurs = new Couleur[taille];
        int[] rvb = new int[3];
        if (taille > 0) {
            couleurs[0] = this;
        }
        int max = 2;
        if (this.getR() > this.getV() && this.getR() > this.getB()) {
            max = 0;
        } else if (this.getV() > this.getB() && this.getV() > this.getB()) {
            max = 1;
        } else if (this.getB() > this.getV() && this.getB() > this.getR()) {
            max = 2;
        }
        rvb[0] = this.getR();
        rvb[1] = this.getV();
        rvb[2] = this.getB();
        for (int i = 1; i < taille; ++i) {
            int temp = pas;
            int indice = max;
            while (true) {
                int n = --indice;
                rvb[n] = rvb[n] + temp;
                if (rvb[n] <= 255) break;
                rvb[indice] = rvb[indice] - 255 - 1;
                temp = 1;
                if (indice != 0) continue;
                indice = 2;
            }
            couleurs[i] = new Couleur(rvb[0], rvb[1], rvb[2]);
        }
        return couleurs;
    }

    public Couleur() {
    }

    public int getR() {
        return this.r;
    }

    public void setR(int r) throws Exception {
        if (r < 0 || r > 255) {
            throw new Exception("Erreur : r invalide");
        }
        this.r = r;
    }

    public int getV() {
        return this.v;
    }

    public void setV(int v) throws Exception {
        if (v < 0 || v > 255) {
            throw new Exception("Erreur : v invalide");
        }
        this.v = v;
    }

    public int getB() {
        return this.b;
    }

    public void setB(int b) throws Exception {
        if (b < 0 || b > 255) {
            throw new Exception("Erreur : b invalide");
        }
        this.b = b;
    }

    public double getA() {
        return this.a;
    }

    public void addR(int pas) {
        int temp = this.r + pas;
        if (temp > 255) {
            this.r = 255;
            this.addV(temp -= 255);
        } else {
            this.r = temp == 510 ? 0 : temp;
        }
    }

    public void addV(int pas) {
        int temp = this.v + pas;
        if (temp > 255) {
            this.v = 255;
            this.addB(temp -= 255);
        } else {
            this.v = temp == 510 ? 0 : temp;
        }
    }

    public void addB(int pas) {
        int temp = this.b + pas;
        if (temp > 255 && temp < 510) {
            this.b = 255;
            this.addR(temp -= 255);
        } else {
            this.b = temp == 510 ? 0 : temp;
        }
    }

    public void setA(double a) throws Exception {
        if (a < 0.0 || a > 1.0) {
            throw new Exception("Erreur : a invalide");
        }
        this.a = a;
    }

    public String getCouleur() {
        return String.format("rgba(%s,%s,%s,%s)", this.r, this.v, this.b, this.a);
    }

    public void addRVB(int pas) throws Exception {
        int tr = this.r;
        int tv = this.v;
        int tb = this.b;
        if ((tr += pas) > 255) {
            tr = 255;
            if ((tv += pas) > 255) {
                tv = 255;
                if ((tb += pas) > 255) {
                    tb = 255;
                }
            }
        }
        this.setR(tr);
        this.setV(tv);
        this.setB(tb);
    }

    public void addAll(int pas) throws Exception {
        int temp = this.r;
        if ((temp += pas) > 255) {
            temp = 255;
        }
        this.setR(temp);
        temp = this.v;
        if ((temp += pas) > 255) {
            temp = 255;
        }
        this.setV(temp);
        temp = this.b;
        if ((temp += pas) > 255) {
            temp = 255;
        }
        this.setB(temp);
    }

    public Couleur[] getCouleurs(int pas, int taille) throws Exception {
        Couleur[] couleurs = new Couleur[taille];
        couleurs[0] = this;
        boolean monter = true;
        int indice = 1;
        for (int i = 1; i < taille; ++i) {
            couleurs[i] = new Couleur(couleurs[i - 1].getR(), couleurs[i - 1].getV(), couleurs[i - 1].getB());
            couleurs[i].addR(pas);
            if (!couleurs[i].equals(couleurs[i - 1])) continue;
            if (++indice % 3 == 0) {
                couleurs[i].setR(0);
            }
            if (indice % 3 == 1) {
                couleurs[i].setV(0);
            }
            if (indice % 3 != 2) continue;
            couleurs[i].setB(0);
        }
        return couleurs;
    }

    public boolean equals(Couleur couleur) {
        boolean verifier = false;
        if (this.r == couleur.r && this.v == couleur.v && this.b == couleur.b && this.a == couleur.a) {
            verifier = true;
        }
        return verifier;
    }
}

