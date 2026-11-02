/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package utilisateur;

import bean.ClassMAPTable;
import java.sql.Connection;

public class Utilisateur
extends ClassMAPTable {
    private String refuser;
    private String loginuser;
    private String pwduser;
    private String idrole;
    private String nomuser;
    private String teluser;
    private String adruser;

    public Utilisateur() {
        super.setNomTable("utilisateur");
    }

    public Utilisateur(String loginuser, String pwduser, String idrole, String nomuser, String teluser) {
        this.setLoginuser(loginuser);
        this.setPwduser(pwduser);
        this.setIdrole(idrole);
        this.setNomuser(nomuser);
        this.setTeluser(teluser);
    }

    public Utilisateur(String loginuser, String pwduser, String idrole, String nomuser, String teluser, String adruser) {
        this.setLoginuser(loginuser);
        this.setPwduser(pwduser);
        this.setIdrole(idrole);
        this.setNomuser(nomuser);
        this.setTeluser(teluser);
        this.setAdruser(adruser);
    }

    public String getAdruser() {
        return this.adruser;
    }

    public void setAdruser(String adruser) {
        this.adruser = adruser;
    }

    public String getRefuser() {
        return this.refuser;
    }

    public void setRefuser(String refuser) {
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

    public String getTuppleID() {
        return this.refuser;
    }

    public String getAttributIDName() {
        return "refuser";
    }

    public void construirePK(Connection c) throws Exception {
        super.setNomTable("user_bo");
        this.preparePk("USER", "getSeqUtilisateur");
        this.setRefuser(this.makePK(c));
    }
}

