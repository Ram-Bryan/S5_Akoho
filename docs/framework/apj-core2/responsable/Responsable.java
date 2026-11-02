/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package responsable;

import bean.ClassMAPTable;
import java.sql.Connection;

public class Responsable
extends ClassMAPTable {
    private String id;
    private String nom;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Responsable() {
        super.setNomTable("RESPONSABLE");
    }

    public void construirePK(Connection c) throws Exception {
        super.setNomTable("RESPONSABLE");
        this.preparePk("RESP", "getseqresponsable");
        this.setId(this.makePK(c));
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }
}

