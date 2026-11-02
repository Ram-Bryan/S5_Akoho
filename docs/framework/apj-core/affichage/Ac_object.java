/*
 * Decompiled with CFR 0.152.
 */
package affichage;

public class Ac_object {
    private Object valeur;
    private Object affiche;

    public Object getValeur() {
        return this.valeur;
    }

    public void setValeur(Object valeur) {
        this.valeur = valeur;
    }

    public Object getAffiche() {
        return this.affiche;
    }

    public void setAffiche(Object affiche) {
        this.affiche = affiche;
    }

    public Ac_object(Object valeur, Object affiche) {
        this.setValeur(valeur);
        this.setAffiche(affiche);
    }

    public Ac_object() {
    }
}

