/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package utilisateur;

import bean.ClassMAPTable;
import java.sql.Connection;

public class Role
extends ClassMAPTable {
    private String idrole;
    private String descrole;
    private int rang;

    public Role() {
        super.setNomTable("roles");
    }

    public String getIdrole() {
        return this.idrole;
    }

    public void setIdrole(String idrole) {
        this.idrole = idrole;
    }

    public String getDescrole() {
        return this.descrole;
    }

    public void setDescrole(String descrole) {
        this.descrole = descrole;
    }

    public int getRang() {
        return this.rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public void construirePK(Connection c) throws Exception {
        super.setNomTable("roles");
        this.preparePk("ROL", "getSeqRoles");
        this.setIdrole(this.makePK(c));
    }

    public String getTuppleID() {
        return this.getIdrole();
    }

    public String getAttributIDName() {
        return "idrole";
    }
}

