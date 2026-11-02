/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package utilisateur;

import bean.ClassMAPTable;
import java.sql.Connection;

public class HomePageURL
extends ClassMAPTable {
    private String id;
    private String codeservice;
    private String urlpage;
    private String idrole;
    private String codedir;

    public HomePageURL() {
        this.setNomTable("USERHOMEPAGE");
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeservice() {
        return this.codeservice;
    }

    public void setCodeservice(String codeservice) {
        this.codeservice = codeservice;
    }

    public String getUrlpage() {
        return this.urlpage;
    }

    public void setUrlpage(String urlpage) {
        this.urlpage = urlpage;
    }

    public String getIdrole() {
        return this.idrole;
    }

    public void setIdrole(String idrole) {
        this.idrole = idrole;
    }

    public String getCodedir() {
        return this.codedir;
    }

    public void setCodedir(String codedir) {
        this.codedir = codedir;
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public void construirePK(Connection c) throws Exception {
        this.preparePk("UHP", "getSeqHomePageURL");
        this.setId(this.makePK(c));
    }
}

