/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassFille
 */
package reporting.mapping;

import bean.ClassFille;
import java.sql.Connection;

public class CRContentFille
extends ClassFille {
    private String id;
    private String idCrContent;
    private String libEntete;
    private String libEnteteAfficher;
    private int colSomme;
    private int colDate;

    public CRContentFille() throws Exception {
        this.setNomTable("CRCONTENTFILLE");
        this.setNomClasseMere("reporting.mapping.CRContent");
        this.setLiaisonMere("idCrContent");
    }

    public void construirePK(Connection c) throws Exception {
        this.preparePk("CRCF", "getseqCRContentFille");
        this.setId(this.makePK(c));
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCrContent() {
        return this.idCrContent;
    }

    public void setIdCrContent(String idCrContent) {
        this.idCrContent = idCrContent;
    }

    public String getLibEntete() {
        return this.libEntete;
    }

    public void setLibEntete(String libEntete) {
        this.libEntete = libEntete;
    }

    public String getLibEnteteAfficher() {
        return this.libEnteteAfficher;
    }

    public void setLibEnteteAfficher(String libEnteteAfficher) {
        this.libEnteteAfficher = libEnteteAfficher;
    }

    public int getColSomme() {
        return this.colSomme;
    }

    public void setColSomme(int colSomme) {
        this.colSomme = colSomme;
    }

    public int getColDate() {
        return this.colDate;
    }

    public void setColDate(int colDate) {
        this.colDate = colDate;
    }
}

