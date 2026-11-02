/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;

public class SituationUnion
extends ClassMAPTable {
    private String id;
    private double montant;
    private double montantMap;
    private int nombreMap;

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public String getTuppleID() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public double getMontant() {
        return this.montant;
    }

    public void setMontantMap(double montantMap) {
        this.montantMap = montantMap;
    }

    public double getMontantMap() {
        return this.montantMap;
    }

    public void setNombreMap(int nombreMap) {
        this.nombreMap = nombreMap;
    }

    public int getNombreMap() {
        return this.nombreMap;
    }
}

