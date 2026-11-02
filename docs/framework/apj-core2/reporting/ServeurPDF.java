/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassEtat
 */
package reporting;

import bean.ClassEtat;
import java.sql.Connection;

public class ServeurPDF
extends ClassEtat {
    private String id;
    private String nom_objet;
    private String idobjet;
    private String remarque;

    public ServeurPDF() {
        super.setNomTable("SERVEUR_PDF_CNAPS");
    }

    public ServeurPDF(String id, String nom_objet, String idobjet) {
        this.setId(id);
        this.setNom_objet(nom_objet);
        this.setIdobjet(idobjet);
        super.setNomTable("SERVEUR_PDF_CNAPS");
    }

    public ServeurPDF(String nom_objet, String idobjet) {
        this.setNom_objet(nom_objet);
        this.setIdobjet(idobjet);
        super.setNomTable("SERVEUR_PDF_CNAPS");
    }

    public ServeurPDF(String id, String nom_objet, String idobjet, String remarque) {
        this.setId(id);
        this.setNom_objet(nom_objet);
        this.setIdobjet(idobjet);
        this.setRemarque(remarque);
        super.setNomTable("SERVEUR_PDF_CNAPS");
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom_objet() {
        return this.nom_objet;
    }

    public void setNom_objet(String nom_objet) {
        this.nom_objet = nom_objet;
    }

    public String getIdobjet() {
        return this.idobjet;
    }

    public void setIdobjet(String idobjet) {
        this.idobjet = idobjet;
    }

    public String getRemarque() {
        return this.remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public void construirePK(Connection c) throws Exception {
        this.preparePk("PEX", "getSeqServeurPdfCnaps");
        this.setId(this.makePK(c));
    }
}

