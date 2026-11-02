/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package utilisateur;

import bean.ClassMAPTable;

public class UtilisateurRole
extends ClassMAPTable {
    private int rang;
    private int refuser;
    private String loginuser;
    private String pwduser;
    private String direction;
    private String service;
    private String codeEtablissement;
    private String idrole;
    private String nomuser;
    private String teluser;
    private String adruser;

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCodeEtablissement() {
        return this.codeEtablissement;
    }

    public void setCodeEtablissement(String codeEtablissement) {
        this.codeEtablissement = codeEtablissement;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
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

    public UtilisateurRole() {
        super.setNomTable("utilisateurrole");
    }

    public int getRang() {
        return this.rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public String getTuppleID() {
        return this.refuser + "";
    }

    public String getAttributIDName() {
        return "refuser";
    }
}

