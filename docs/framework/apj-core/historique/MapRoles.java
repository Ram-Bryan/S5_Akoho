/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.ClassMAPTable;
import java.io.Serializable;

public class MapRoles
extends ClassMAPTable
implements Serializable {
    public String idrole;
    public String descrole;
    public int rang;

    public MapRoles() {
        super.setNomTable("roles");
    }

    public MapRoles(String descRole, String idRole) {
        this.descrole = descRole;
        this.idrole = idRole;
        super.setNomTable("roles");
    }

    public MapRoles(String descRole, String idRole, int rang) {
        this.descrole = descRole;
        this.idrole = idRole;
        this.setRang(rang);
        super.setNomTable("roles");
    }

    @Override
    public String getAttributIDName() {
        return "idrole";
    }

    @Override
    public String getTuppleID() {
        return this.idrole;
    }

    public int getRang() {
        return this.rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public String getDescrole() {
        return this.descrole;
    }

    public void setDescrole(String descrole) {
        this.descrole = descrole;
    }

    public void setIdrole(String idrole) {
        this.idrole = idrole;
    }

    public String getIdrole() {
        return this.idrole;
    }
}

