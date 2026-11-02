/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ValeurCroises;
import utilitaire.Utilitaire;

public class ValeurEtiquette {
    String valeur;
    String lien;
    String type = "valeur";
    String align = "right";

    public String getAlign() {
        return this.align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValeur() {
        return this.valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getLien() {
        return this.lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public ValeurEtiquette(String val, String lie) {
        this.setValeur(ValeurEtiquette.formatter(val));
        this.setLien(lie);
        this.setType("entete");
        this.setAlign("left");
    }

    public ValeurEtiquette(double val, String lie) {
        this.setValeur(Utilitaire.formaterAr(val));
        this.setLien(lie);
    }

    public ValeurEtiquette(ValeurCroises v) {
        if (v != null) {
            if (v.getListeVal() != null && v.getListeVal().length > 0) {
                String retour = "";
                for (int i = 0; i < v.getListeVal().length; ++i) {
                    retour = retour + Utilitaire.formaterAr(v.getListeVal()[i]);
                    if (i >= v.getListeVal().length - 1) continue;
                    retour = retour + "<BR>";
                }
                this.setValeur(retour);
            } else {
                this.setValeur(Utilitaire.formaterAr(v.getValeur()));
            }
            this.setLien(v.getLien());
        }
    }

    public static String formatter(String val) {
        try {
            boolean dat = Utilitaire.isValidDate(val, "yyyy-mm-dd");
            boolean dat2 = Utilitaire.isValidDate(val, "yy-mm-dd");
            if (dat || dat2) {
                return Utilitaire.formatterDaty(val);
            }
            return val;
        }
        catch (Exception e) {
            return val;
        }
    }
}

