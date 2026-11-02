/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import affichage.Champ;
import utilitaire.Utilitaire;

public class ChampDate
extends Champ {
    private final String MODEUPDATE = "UPDATE";
    private String mode = "";

    public ChampDate() {
    }

    public ChampDate(String nom) {
        super(nom);
    }

    public ChampDate(String nom, String mode) {
        super(nom);
        this.mode = mode;
    }

    @Override
    public void makeHtml() throws Exception {
        String temp = "";
        String val = this.getValeur();
        if (this.mode.compareToIgnoreCase("UPDATE") == 0) {
            val = Utilitaire.convertDatyFormtoRealDatyFormat(this.getValeur());
        } else if (this.getValeur() == null || this.getValeur().compareToIgnoreCase("") == 0) {
            val = Utilitaire.dateDuJour();
        }
        temp = !this.getVisible() ? "<input data-type='date' name=" + this.getNom() + " type='hidden' class=" + this.getCss() + "  id=" + this.getNom() + " value='" + val + "' onmouseover=\"datepicker('" + this.getNom() + "')\" " + this.getAutre() + ">" : (this.getIsDateHeure() ? "<input data-type='date' name=" + this.getNom() + " type='datetime-local' class='" + this.getCss() + "'  id=" + this.getNom() + " value='" + val + "'  " + this.getAutre() + ">" : "<input data-type='date' name=" + this.getNom() + " type=" + this.getType() + " class='" + this.getCss() + " datepicker'  id=" + this.getNom() + " value='" + val + "'  " + this.getAutre() + ">");
        this.setHtml(temp);
    }
}

