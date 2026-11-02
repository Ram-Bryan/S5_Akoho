/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;
import bean.ValeurEtiquette;
import java.util.ArrayList;

public class EtiquetteLigneCol {
    ArrayList<ValeurEtiquette> ligne = new ArrayList();

    public ArrayList<ValeurEtiquette> getLigne() {
        return this.ligne;
    }

    public void setLigne(ArrayList<ValeurEtiquette> ligne) {
        this.ligne = ligne;
    }

    public EtiquetteLigneCol() {
    }

    public EtiquetteLigneCol(ValeurEtiquette v) throws Exception {
        this.getLigne().add(v);
    }

    public void ajouter(ClassMAPTable c) throws Exception {
    }

    public boolean comparer(EtiquetteLigneCol autre) throws Exception {
        int i = 0;
        for (ValeurEtiquette valLigne : this.ligne) {
            ValeurEtiquette valAutre = autre.getLigne().get(i);
            if (valAutre.getValeur().compareToIgnoreCase(valLigne.getValeur()) != 0) {
                return false;
            }
            ++i;
        }
        return true;
    }
}

