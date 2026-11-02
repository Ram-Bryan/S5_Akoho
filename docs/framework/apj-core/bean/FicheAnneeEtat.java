/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassUser;
import utilitaire.ConstanteEtat;

public abstract class FicheAnneeEtat
extends ClassUser {
    int etat = 1;
    int annee;

    public int getAnnee() {
        return this.annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getEtat() {
        return this.etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getEtatText(int value) {
        return this.chaineEtat(value);
    }

    public String chaineEtat(int value) {
        if (value == ConstanteEtat.getEtatCreer()) {
            return "<b style='color:lightskyblue'>CR&Eacute;&Eacute;(E)</b>";
        }
        if (value == ConstanteEtat.getEtatValider()) {
            return "<b style='color:green'>VIS&Eacute;(E)</b>";
        }
        if (value == ConstanteEtat.getEtatAnnuler()) {
            return "<b style='color:orange'>ANNUL&Eacute;(E)</b>";
        }
        if (value == ConstanteEtat.getEtatCloture()) {
            return "<b style='color:orange'>CLOTUR&Eacute;(E)</b>";
        }
        if (value == ConstanteEtat.getConstanteEtatFinaliser()) {
            return "<b style='color:orange'>CR&Eacute;&Eacute;(E)</b>";
        }
        return null;
    }
}

