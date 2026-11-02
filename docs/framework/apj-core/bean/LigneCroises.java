/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ValeurCroises;
import java.util.ArrayList;

public class LigneCroises {
    ArrayList<ValeurCroises> ligne = new ArrayList();

    public ArrayList<ValeurCroises> getLigne() {
        return this.ligne;
    }

    public void setLigne(ArrayList<ValeurCroises> ligne) {
        this.ligne = ligne;
    }

    public void ajouter(ValeurCroises v) throws Exception {
        this.getLigne().add(v);
    }

    public LigneCroises(double c, String lien) throws Exception {
        this.ligne.add(new ValeurCroises(c, lien));
    }
}

