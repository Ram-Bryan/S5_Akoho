/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package utilisateur;

import bean.ClassMAPTable;
import java.sql.Connection;

public class UserMenu
extends ClassMAPTable {
    private String id;
    private String idrole;
    private String idmenu;
    private String codeservice;
    private String codedir;
    private int interdit;
    private String refuser;

    public UserMenu(String idrole, String idmenu, String codeservice, String codedir, int interdit, String refuser) {
        this.idrole = idrole;
        this.idmenu = idmenu;
        this.codeservice = codeservice;
        this.codedir = codedir;
        this.interdit = interdit;
        this.refuser = refuser;
    }

    public void construirePK(Connection c) throws Exception {
        super.setNomTable("usermenu");
        this.preparePk("MEN", "getSeqUserMenu");
        this.setId(this.makePK(c));
    }

    public UserMenu() {
        super.setNomTable("usermenu");
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

    public String getIdmenu() {
        return this.idmenu;
    }

    public void setIdmenu(String idmenu) {
        this.idmenu = idmenu;
    }

    public String getCodeservice() {
        return this.codeservice;
    }

    public void setCodeservice(String codeservice) {
        this.codeservice = codeservice;
    }

    public String getCodedir() {
        return this.codedir;
    }

    public void setCodedir(String codedir) {
        this.codedir = codedir;
    }

    public String getRefuser() {
        return this.refuser;
    }

    public void setRefuser(String refuser) {
        this.refuser = refuser;
    }

    public int getInterdit() {
        return this.interdit;
    }

    public void setInterdit(int interdit) {
        this.interdit = interdit;
    }

    public String getTuppleID() {
        return this.getId();
    }

    public String getAttributIDName() {
        return "id";
    }
}

