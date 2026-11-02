/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package utilisateur;

import bean.ClassMAPTable;
import java.sql.Connection;

public class UtilisateurPg
extends ClassMAPTable {
    private int refuser;
    private String loginuser;
    private String pwduser;
    private String idrole;
    private String nomuser;
    private String teluser;
    private String adruser;

    public UtilisateurPg() {
        super.setNomTable("utilisateur");
    }

    public UtilisateurPg(String loginuser, String pwduser, String idrole, String nomuser, String teluser) {
        this.setLoginuser(loginuser);
        this.setPwduser(pwduser);
        this.setIdrole(idrole);
        this.setNomuser(nomuser);
        this.setTeluser(teluser);
    }

    public int getRefuser() {
        return this.refuser;
    }

    public void setRefuser(int refuser) {
        this.refuser = refuser;
    }

    public String getLoginuser() {
        return this.loginuser;
    }

    public void setLoginuser(String loginuser) {
        this.loginuser = loginuser;
    }

    public String getPwduser() {
        return this.pwduser;
    }

    public void setPwduser(String pwduser) {
        this.pwduser = pwduser;
    }

    public String getIdrole() {
        return this.idrole;
    }

    public String getIdRole() {
        return this.idrole;
    }

    public void setIdrole(String idrole) {
        this.idrole = idrole;
    }

    public String getNomuser() {
        return this.nomuser;
    }

    public void setNomuser(String nomuser) {
        this.nomuser = nomuser;
    }

    public String getTeluser() {
        return this.teluser;
    }

    public void setTeluser(String teluser) {
        this.teluser = teluser;
    }

    public String getAdruser() {
        return this.adruser;
    }

    public void setAdruser(String adruser) {
        this.adruser = adruser;
    }

    public String getTuppleID() {
        return String.valueOf(this.refuser);
    }

    public String getAttributIDName() {
        return "refuser";
    }

    public String getAttribuLoguser() {
        return "loginuser";
    }

    public String getAttribumdp() {
        return "pwduser";
    }

    public void construirePK(Connection c) throws Exception {
        super.setNomTable("utilisateur");
        this.preparePk("", "getSeqUtilisateur");
        this.setRefuser(Integer.valueOf(this.makePK(c)));
    }
}

