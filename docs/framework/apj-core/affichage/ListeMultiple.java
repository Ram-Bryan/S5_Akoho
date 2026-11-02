/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import affichage.Liste;
import bean.CGenUtil;
import bean.ClassMAPTable;

public class ListeMultiple
extends Liste {
    private String javascript;

    public ListeMultiple(String nom, ClassMAPTable type, String colAff, String colVal) {
        this.setNom(nom);
        this.setSingleton(type);
        this.setColAffiche(colAff);
        this.setColValeur(colVal);
    }

    public ListeMultiple(String nom, String ajax, ClassMAPTable type, String colAff, String colVal) {
        this.setNom(nom);
        this.setSingleton(type);
        this.setColAffiche(colAff);
        this.setColValeur(colVal);
        this.setJavascript(ajax);
    }

    public String getJavascript() {
        return this.javascript;
    }

    public void setJavascript(String javascript) {
        this.javascript = javascript;
    }

    @Override
    public void makeHtml() throws Exception {
        String temp = "";
        temp = "<select name=" + this.getNom() + " class=" + this.getCss() + " id=" + this.getNom() + " " + this.getJavascript() + " " + this.getAutre() + "multiple=\"multiple\">";
        if (this.getBase() == null || this.getBase().length == 0) {
            if (this.getValeurBrute() != null && this.getValeurBrute().length > 0) {
                temp = this.makeHtmlValeur(temp);
            }
        } else {
            for (int i = 0; i < this.getBase().length; ++i) {
                String champValeur = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColValeur()));
                String champAffiche = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColAffiche()));
                String selected = "";
                if ((this.getValeur() == null || this.getValeur().compareToIgnoreCase("") == 0) && this.getDefaultSelected() != null) {
                    if (champValeur.compareToIgnoreCase(this.getDefaultSelected()) == 0) {
                        selected = "selected";
                    }
                } else if (champValeur.compareToIgnoreCase(this.getValeur()) == 0) {
                    selected = "selected";
                }
                temp = temp + "<option value='" + champValeur + "' " + selected + ">" + champAffiche + "</option>";
            }
        }
        temp = temp + "</select>";
        this.setHtml(temp);
    }
}

