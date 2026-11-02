/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;

public class RgMultiple
extends ClassMAPTable {
    private String[] colonneGroupe;
    private double[] sommeGroupe;
    private int nombre;

    public RgMultiple() {
    }

    public RgMultiple(String[] col, double[] s, int nb) {
        this.setColonneGroupe(col);
        this.setSommeGroupe(s);
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

    public void setColonneGroupe(String[] colonneGroupe) {
        this.colonneGroupe = colonneGroupe;
    }

    public String[] getColonneGroupe() {
        return this.colonneGroupe;
    }

    public void setSommeGroupe(double[] sommeGroupe) {
        this.sommeGroupe = sommeGroupe;
    }

    public double[] getSommeGroupe() {
        return this.sommeGroupe;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public int getNombre() {
        return this.nombre;
    }
}

