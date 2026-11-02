/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package utilisateur;

import bean.ClassMAPTable;

public class RestrictionLibelle
extends ClassMAPTable {
    private String idrole;
    private String tablename;
    private String ajout;
    private String modif;
    private String suppr;
    private String read;
    private String idaction;
    private String iddirection;

    public RestrictionLibelle(String idrole, String tablename, String ajout, String modif, String suppr, String read, String idaction, String iddirection) {
        this.setIdrole(idrole);
        this.setTablename(tablename);
        this.setAjout(ajout);
        this.setModif(modif);
        this.setSuppr(suppr);
        this.setRead(read);
        this.setIdaction(idaction);
        this.setIddirection(iddirection);
        super.setNomTable("RESTRICTION_LIBELLE");
    }

    public String getIddirection() {
        return this.iddirection;
    }

    public void setIddirection(String iddirection) {
        this.iddirection = iddirection;
    }

    public RestrictionLibelle() {
        super.setNomTable("RESTRICTION_LIBELLE");
    }

    public String getIdrole() {
        return this.idrole;
    }

    public void setIdrole(String idrole) {
        this.idrole = idrole;
    }

    public String getTablename() {
        return this.tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getAjout() {
        return this.ajout;
    }

    public void setAjout(String ajout) {
        this.ajout = ajout;
    }

    public String getModif() {
        return this.modif;
    }

    public void setModif(String modif) {
        this.modif = modif;
    }

    public String getSuppr() {
        return this.suppr;
    }

    public void setSuppr(String suppr) {
        this.suppr = suppr;
    }

    public String getRead() {
        return this.read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getTuppleID() {
        return this.getIdrole();
    }

    public String getAttributIDName() {
        return "idrole";
    }

    public String getIdaction() {
        return this.idaction;
    }

    public void setIdaction(String idaction) {
        this.idaction = idaction;
    }
}

