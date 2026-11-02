/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.ClassMAPTable;

public class ParamCrypt
extends ClassMAPTable {
    public String id;
    public int niveau;
    public int croissante;
    public String idUtilisateur;

    public ParamCrypt() {
        this.setNomTable("ParamCrypt");
    }

    public ParamCrypt(String id, int niveau, int croissante, String idUtilisateur) {
        this.id = id;
        this.niveau = niveau;
        this.croissante = croissante;
        this.idUtilisateur = idUtilisateur;
        this.setNomTable("ParamCrypt");
    }

    public ParamCrypt(int niveau, int croissante, String idUtilisateur) {
        this.niveau = niveau;
        this.croissante = croissante;
        this.idUtilisateur = idUtilisateur;
        this.setNomTable("ParamCrypt");
        this.setIndicePk("CRY");
        this.setNomProcedureSequence("getseqParamCrypt");
        this.setId(this.makePK());
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public String getTuppleID() {
        return String.valueOf(this.id);
    }

    public int getCroissante() {
        return this.croissante;
    }

    public void setCroissante(int croissante) {
        this.croissante = croissante;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getNiveau() {
        return this.niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }
}

