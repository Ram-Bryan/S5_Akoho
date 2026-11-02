/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;

public class CaractTable
extends ClassMAPTable {
    private String id;
    private String nomTable;
    private String nomSeq;
    private String nomProc;
    private String nomFille;

    public CaractTable() {
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public String getTuppleID() {
        return this.id;
    }

    public CaractTable(String i, String nomT, String nomS, String nomProc) {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public void setNomTable(String nomTable) {
        this.nomTable = nomTable;
    }

    @Override
    public String getNomTable() {
        return this.nomTable;
    }

    public void setNomSeq(String nomSeq) {
        this.nomSeq = nomSeq;
    }

    public String getNomSeq() {
        return this.nomSeq;
    }

    public void setNomProc(String nomProc) {
        this.nomProc = nomProc;
    }

    public String getNomProc() {
        return this.nomProc;
    }

    public void setNomFille(String nomFille) {
        this.nomFille = nomFille;
    }

    public String getNomFille() {
        return this.nomFille;
    }
}

