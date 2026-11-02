/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package utilisateur;

import bean.ClassMAPTable;
import java.sql.Connection;

public class Restriction
extends ClassMAPTable {
    private String id;
    private String idrole;
    private String idaction;
    private String tablename;
    private String autorisation;
    private String description;
    private String iddirection;

    public Restriction(String idrole, String idaction, String tablename, String autorisation, String description, String iddirection) {
        this.setIdrole(idrole);
        this.setIdaction(idaction);
        this.setTablename(tablename);
        this.setAutorisation("false");
        this.setDescription(description);
        this.setIddirection(iddirection);
        super.setNomTable("restriction");
    }

    public String getIddirection() {
        return this.iddirection;
    }

    public void setIddirection(String iddirection) {
        this.iddirection = iddirection;
    }

    public Restriction(String idrole, String idpermission, String nt) {
        super.setNomTable("restriction");
        this.setIdrole(idrole);
        this.setIdaction(idpermission);
        this.setTablename(nt);
        this.setAutorisation("false");
        this.setDescription("");
    }

    public Restriction(String idrole, String action, String nt, String direction) {
        super.setNomTable("restriction");
        this.setIdrole(idrole);
        this.setIdaction(action);
        this.setTablename(nt);
        this.setAutorisation("non");
        this.setIddirection(direction);
    }

    public Restriction() {
        super.setNomTable("restriction");
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public void construirePK(Connection c) throws Exception {
        super.setNomTable("restriction");
        this.preparePk("RES", "getSeqRestriction");
        this.setId(this.makePK(c));
    }

    public String getId() {
        return this.id;
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

    public String getIdaction() {
        return this.idaction;
    }

    public void setIdaction(String idaction) {
        this.idaction = idaction;
    }

    public String getTablename() {
        return this.tablename;
    }

    public void setTablename(String idelement) {
        this.tablename = idelement;
    }

    public String getAutorisation() {
        return this.autorisation;
    }

    public void setAutorisation(String autorisation) {
        this.autorisation = autorisation;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

