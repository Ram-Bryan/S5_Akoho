/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;
import java.sql.Connection;

public class UploadPj
extends ClassMAPTable {
    private String id;
    private String libelle;
    private String chemin;
    private String mere;

    public UploadPj(String nomTable, String nomProcedure, String suff, String libelle, String chemin, String mere) {
        super.setNomTable(nomTable);
        super.setNomProcedureSequence(nomProcedure);
        super.setIndicePk(suff);
        this.setLibelle(libelle);
        this.setChemin(chemin);
        this.setMere(mere);
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk(this.getINDICE_PK(), this.getNomProcedureSequence());
        this.setId(this.makePK(c));
    }

    public UploadPj(String nt) {
        super.setNomTable(nt);
    }

    public UploadPj() {
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public String getTuppleID() {
        return this.id;
    }

    public String getChemin() {
        return this.chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public String getMere() {
        return this.mere;
    }

    public void setMere(String mere) {
        this.mere = mere;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle == null ? "-" : libelle;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}

