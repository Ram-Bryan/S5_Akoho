/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package configuration;

import bean.ClassMAPTable;

public class Parametre
extends ClassMAPTable {
    private String id;
    private String idrole;
    private double valeurpourc;

    public String getId() {
        return this.id;
    }

    public Parametre(String id, String idrole, double valeurpourc) {
        this.setNomTable("parametre");
        this.setId(id);
        this.setIdrole(idrole);
        this.setValeurpourc(valeurpourc);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdrole() {
        return this.idrole;
    }

    public void setIdrole(String idrole) {
        this.idrole = idrole;
    }

    public double getValeurpourc() {
        return this.valeurpourc;
    }

    public void setValeurpourc(double valeurpourc) {
        this.valeurpourc = valeurpourc;
    }

    public Parametre() {
        this.setNomTable("parametre");
    }

    public String getAttributIDName() {
        return "id";
    }

    public String getTuppleID() {
        return this.id;
    }
}

