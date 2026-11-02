/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.AdminGen;
import bean.CGenUtil;
import bean.ClassMAPTable;

public class TypeObjet
extends ClassMAPTable {
    public String val;
    public String desce;
    public String id;

    public static TypeObjet[] filtrer(String id, String val, String desce, TypeObjet[] parametre) throws Exception {
        String[] col = new String[]{"id", "val", "desce"};
        String[] valeur = new String[]{id, val, desce};
        TypeObjet[] paramCompta = (TypeObjet[])AdminGen.find(parametre, col, valeur);
        return paramCompta;
    }

    public static TypeObjet getById(String id, TypeObjet[] parametre) throws Exception {
        String[] col = new String[]{"id"};
        String[] valeur = new String[]{id};
        TypeObjet[] retour = (TypeObjet[])AdminGen.find(parametre, col, valeur);
        if (retour != null) {
            return retour[0];
        }
        return null;
    }

    public TypeObjet(String nomTable, String nomProcedure, String suff, String vale, String desc) {
        this.setNomTable(nomTable);
        this.setNomProcedureSequence(nomProcedure);
        this.setIndicePk(suff);
        this.id = this.makePK();
        this.val = vale;
        this.setDesce(desc);
    }

    public TypeObjet(String nomTable, String ide, String vale, String desc) {
        this.setNomTable(nomTable);
        this.id = ide;
        this.val = vale;
        this.setDesce(desc);
    }

    public TypeObjet(String ide, String vale, String desc) {
        this.id = ide;
        this.val = vale;
        this.setDesce(desc);
    }

    public TypeObjet(String nt) {
        this.setNomTable(nt);
    }

    public TypeObjet() {
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
        this.desce = desc == null ? "-" : desc;
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

    @Override
    public String getValColLibelle() {
        return this.val;
    }

    public static TypeObjet[] get(String nom_Table) {
        try {
            TypeObjet to = new TypeObjet();
            to.setNomTable(nom_Table);
            TypeObjet[] liste = (TypeObjet[])CGenUtil.rechercher((ClassMAPTable)to, null, null, "");
            return liste;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String[] getMotCles() {
        return new String[]{"id", "val", "desce"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"id", "val", "desce"};
    }
}

