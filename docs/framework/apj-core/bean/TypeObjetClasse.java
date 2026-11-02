/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;

public class TypeObjetClasse
extends ClassMAPTable {
    public String val;
    public String desce;
    public String id;
    public String idParent;

    public TypeObjetClasse(String nomTable, String nomProcedure, String suff, String vale, String desc, String idParent) {
        this.setNomTable(nomTable);
        this.setNomProcedureSequence(nomProcedure);
        this.setIdParent(idParent);
        this.setIndicePk(suff);
        this.id = this.makePK();
        this.val = vale;
        this.setDesce(desc);
    }

    public TypeObjetClasse(String nomTable, String ide, String vale, String desc, String idParent) {
        this.setNomTable(nomTable);
        this.setId(ide);
        this.setVal(vale);
        this.setDesce(desc);
        this.setIdParent(idParent);
    }

    public TypeObjetClasse(String ide, String vale, String desc, String idParent) {
        this.id = ide;
        this.val = vale;
        this.setDesce(desc);
        this.setIdParent(idParent);
    }

    public TypeObjetClasse() {
    }

    public String getIdParent() {
        return this.idParent;
    }

    public void setIdParent(String idParent) {
        this.idParent = idParent;
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public String getTuppleID() {
        return this.id;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getVal() {
        return this.val;
    }

    public void setDesce(String desc) {
        this.desce = desc == null || desc.compareTo("") == 0 ? "-" : desc;
    }

    public String getDesce() {
        return this.desce;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}

