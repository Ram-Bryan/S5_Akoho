/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.CGenUtil;
import bean.ClassMAPTable;
import java.lang.reflect.Field;

public class ResultatEtSomme {
    Object[] resultat;
    private double[] sommeEtNombre;

    public ResultatEtSomme() {
    }

    public ResultatEtSomme(Object[] r, double[] rn) {
        this.setResultat(r);
        this.setSommeEtNombre(rn);
    }

    public ResultatEtSomme(Object[] r) {
        this.setResultat(r);
    }

    public Object[] getResultat() {
        return this.resultat;
    }

    public double[] getSommeEtNombre() {
        return this.sommeEtNombre;
    }

    public void setResultat(Object[] resultat) {
        this.resultat = resultat;
    }

    public void setSommeEtNombre(double[] sommeEtNombre) {
        this.sommeEtNombre = sommeEtNombre;
    }

    public void initialise(String[] colSomme) {
        this.resultat = new ClassMAPTable[0];
        if (colSomme == null) {
            this.sommeEtNombre = new double[1];
            this.sommeEtNombre[0] = 0.0;
        } else {
            this.sommeEtNombre = new double[colSomme.length + 1];
            for (int i = 0; i < colSomme.length; ++i) {
                this.sommeEtNombre[i] = 0.0;
            }
            this.sommeEtNombre[colSomme.length] = 0.0;
        }
    }

    public void cacher(String nomChamp) throws Exception {
        if (this.resultat.length == 0) {
            return;
        }
        ClassMAPTable[] eq = (ClassMAPTable[])this.resultat;
        Field attr = eq[0].getFieldByName(nomChamp);
        if (attr == null) {
            return;
        }
        for (int i = 0; i < eq.length; ++i) {
            CGenUtil.setValChamp(eq[i], attr, 0);
        }
        for (int j = 0; j < this.sommeEtNombre.length; ++j) {
            this.sommeEtNombre[j] = 0.0;
        }
    }

    public void cacherMontant() throws Exception {
        this.cacher("montant");
    }
}

