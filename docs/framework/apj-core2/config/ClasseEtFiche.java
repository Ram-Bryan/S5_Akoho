/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package config;

import bean.ClassMAPTable;

public class ClasseEtFiche
extends ClassMAPTable {
    String id;
    String classe;
    String fiche;
    String libelle;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClasse() {
        return this.classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getFiche() {
        return this.fiche;
    }

    public void setFiche(String fiche) {
        this.fiche = fiche;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }
}

