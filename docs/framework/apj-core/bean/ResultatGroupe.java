/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;

public class ResultatGroupe
extends ClassMAPTable {
    private String nomColonne;
    private double somme;
    private double nombre;

    public ResultatGroupe() {
    }

    public ResultatGroupe(String nomCol, double som, double nb) {
        this.setNomColonne(nomCol);
        this.setSomme(som);
        this.setNombre(nb);
    }

    @Override
    public String getAttributIDName() {
        throw new UnsupportedOperationException("Method getAttributIDName() not yet implemented.");
    }

    @Override
    public String getTuppleID() {
        throw new UnsupportedOperationException("Method getTuppleID() not yet implemented.");
    }

    public void setNomColonne(String nomColonne) {
        this.nomColonne = nomColonne;
    }

    public String getNomColonne() {
        return this.nomColonne;
    }

    public void setSomme(double somme) {
        this.somme = somme;
    }

    public double getSomme() {
        return this.somme;
    }

    public void setNombre(double nombre) {
        this.nombre = nombre;
    }

    public double getNombre() {
        return this.nombre;
    }
}

