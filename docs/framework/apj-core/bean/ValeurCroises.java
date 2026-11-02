/*
 * Decompiled with CFR 0.152.
 */
package bean;

public class ValeurCroises {
    double valeur;
    String lien;
    double[] listeVal;

    public double[] getListeVal() {
        return this.listeVal;
    }

    public void setListeVal(double[] listeVal) {
        this.listeVal = listeVal;
    }

    public double getValeur() {
        return this.valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public String getLien() {
        return this.lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public ValeurCroises(double val, String lie) {
        this.setValeur(val);
        this.setLien(lie);
    }

    public ValeurCroises(double[] val, String lie) {
        this.setListeVal(val);
        if (val.length >= 1) {
            this.setValeur(val[0]);
        }
        this.setLien(lie);
    }
}

