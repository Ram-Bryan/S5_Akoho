/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import affichage.Champ;
import constante.ConstanteAffichage;

public class ChampAutocomplete
extends Champ {
    private String idsuggestion;
    private String idliste;
    private String colonne;
    private String nomclasse;
    private String javascript;

    public ChampAutocomplete(String nom, String colonne, String nomclasse) {
        this.setNom(nom);
        this.setIdSuggestion("sugg" + nom);
        this.setIdListe("liste" + nom);
        this.setColonne(colonne);
        this.setNomClasse(nomclasse);
        String javascript = "onkeyup=\"showSuggestion(";
        javascript = javascript + "'" + nom + "', ";
        javascript = javascript + "'" + colonne + "',";
        javascript = javascript + "'" + nomclasse + "'";
        javascript = javascript + ")\"";
        javascript = javascript + " onkeypress=\"moveToList(event, 'liste" + nom + "')\"";
        this.setJavascript(javascript);
    }

    public ChampAutocomplete(String nom, String javascript, String idsuggestion, String idliste, String colonne, String nomclasse) {
        this.setNom(nom);
        this.setJavascript(javascript);
        this.setIdSuggestion(idsuggestion);
        this.setIdListe(idliste);
        this.setColonne(colonne);
        this.setNomClasse(nomclasse);
    }

    public String getIdSuggestion() {
        return this.idsuggestion;
    }

    public String getIdListe() {
        return this.idliste;
    }

    public String getColonne() {
        return this.colonne;
    }

    public String getNomClasse() {
        return this.nomclasse;
    }

    public String getJavascript() {
        return this.javascript;
    }

    public void setIdSuggestion(String idsuggestion) {
        this.idsuggestion = idsuggestion;
    }

    public void setIdListe(String idliste) {
        this.idliste = idliste;
    }

    public void setColonne(String colonne) {
        this.colonne = colonne;
    }

    public void setNomClasse(String nomclasse) {
        this.nomclasse = nomclasse;
    }

    public void setJavascript(String javascript) {
        this.javascript = javascript;
    }

    @Override
    public void makeHtml() throws Exception {
        String temp = "";
        temp = this.getType().compareTo(ConstanteAffichage.textarea) == 0 ? "<textarea data-type='String name=" + this.getNom() + " class=" + this.getCss() + "id=" + this.getNom() + "maxlength=" + this.getTaille() + " " + this.getJavascript() + " " + this.getAutre() + ">" + this.getValeur() + "</textarea>" : "<input data-type='String name=" + this.getNom() + " type=" + this.getType() + " class=" + this.getCss() + "  id=" + this.getNom() + " value=\"" + this.getValeur() + "\" " + this.getJavascript() + " " + this.getAutre() + ">";
        temp = temp + "<div id=\"" + this.getIdSuggestion() + "\"></div>";
        this.setHtml(temp);
    }

    public static String makeSelectionEvent(String idchamp) {
        String javascript = "onclick=\"selection(";
        javascript = javascript + "'" + idchamp + "'";
        javascript = javascript + ")\"";
        javascript = javascript + "onkeydown=\"selectionEnter(event, '" + idchamp + "')\"";
        return javascript;
    }
}

