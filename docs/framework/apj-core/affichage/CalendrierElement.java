/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package affichage;

import affichage.PageRecherche;
import bean.ClassMAPTable;
import javax.servlet.http.HttpServletRequest;

public class CalendrierElement
extends PageRecherche {
    private String colDate;
    private String colTitre;
    private String colHeure;
    private String lienClick;
    private String nomTable;
    private String nomClasse;
    private String couleur;
    private String colHeureFin;
    private String colStatut;
    private String[] valStatut;
    private String[] affStatut;
    private String[] couleurStatut;

    public String[] getCouleurStatut() {
        return this.couleurStatut;
    }

    public void setCouleurStatut(String[] couleurStatut) {
        this.couleurStatut = couleurStatut;
    }

    public String getColStatut() {
        return this.colStatut;
    }

    public void setColStatut(String colStatut) {
        this.colStatut = colStatut;
    }

    public String[] getValStatut() {
        return this.valStatut;
    }

    public void setValStatut(String[] valStatut) {
        this.valStatut = valStatut;
    }

    public String[] getAffStatut() {
        return this.affStatut;
    }

    public void setAffStatut(String[] affStatut) {
        this.affStatut = affStatut;
    }

    public void setStatut(String colStatut, String[] valStatut, String[] affStatut, String[] couleurStatut) {
        this.colStatut = colStatut;
        this.valStatut = valStatut;
        this.affStatut = affStatut;
        this.couleurStatut = couleurStatut;
    }

    public String getColHeureFin() {
        return this.colHeureFin;
    }

    public void setColHeureFin(String colHeureFin) {
        this.colHeureFin = colHeureFin;
    }

    public String getCouleur() {
        return this.couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getLienClick() {
        return this.lienClick;
    }

    public void setLienClick(String lienClick) {
        this.lienClick = lienClick;
    }

    public String getNomClasse() {
        return this.nomClasse;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    public String getNomTable() {
        return this.nomTable;
    }

    public void setNomTable(String nomTable) {
        this.nomTable = nomTable;
    }

    public String getColHeure() {
        return this.colHeure;
    }

    public void setColHeure(String colHeure) {
        this.colHeure = colHeure;
    }

    public String getColDate() {
        return this.colDate;
    }

    public void setColDate(String colDate) {
        this.colDate = colDate;
    }

    public String getColTitre() {
        return this.colTitre;
    }

    public void setColTitre(String colTitre) {
        this.colTitre = colTitre;
    }

    public CalendrierElement(ClassMAPTable o, HttpServletRequest req, String[] vrt, String[] listInterval) throws Exception {
        super(o, req, vrt, listInterval, 4, new String[0], 0);
    }

    public CalendrierElement(ClassMAPTable o, HttpServletRequest req) throws Exception {
        super(o, req, new String[0], new String[0], 4, new String[0], 0);
    }

    public CalendrierElement(ClassMAPTable o, HttpServletRequest req, String colDate, String nomTable, String nomClasse, String colTitre) throws Exception {
        super(o, req, new String[0], new String[0], 4, new String[0], 0);
        this.colDate = colDate;
        this.nomClasse = nomClasse;
        this.colTitre = colTitre;
        this.nomTable = nomTable;
    }
}

