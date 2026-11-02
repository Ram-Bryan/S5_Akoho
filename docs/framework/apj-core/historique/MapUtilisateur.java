/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  utilisateur.ActionRole
 */
package historique;

import bean.CGenUtil;
import bean.ClassMAPTable;
import historique.AnnulationUtilisateurUtil;
import historique.MapRoles;
import java.io.Serializable;
import java.sql.Connection;
import utilisateur.ActionRole;
import utilitaire.Utilitaire;

public class MapUtilisateur
extends ClassMAPTable
implements Serializable {
    private String loginuser;
    private String pwduser;
    private String idrole;
    private String nomuser;
    private String adruser;
    private String teluser;
    private int refuser;
    private int rang;
    MapRoles role;

    public void setRole(MapRoles role) {
        this.role = role;
    }

    public int getRang() {
        return this.rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public MapUtilisateur() {
        this.setNomTable("utilisateur");
    }

    public MapUtilisateur(int refus, String loginus, String pwdus, String nomus, String adrus, String telus, String idrol) throws Exception {
        this.setRefuser(refus);
        this.setLoginuser(loginus);
        this.setPwduser(pwdus);
        this.setNomuser(nomus);
        this.setAdruser(adrus);
        this.setTeluser(telus);
        this.setIdrole(idrol);
        super.setNomTable("utilisateur");
    }

    public MapUtilisateur(String refus, String loginus, String pwdus, String nomus, String adrus, String telus, String idrol) throws Exception {
        this.setRefuser(Integer.valueOf(refus));
        this.setLoginuser(loginus);
        this.setPwduser(pwdus);
        this.setNomuser(nomus);
        this.setAdruser(adrus);
        this.setTeluser(telus);
        this.setIdrole(idrol);
        super.setNomTable("utilisateur");
    }

    public MapUtilisateur(String loginus, String pwdus, String nomus, String adrus, String telus, String idrol) throws Exception {
        super.setNomTable("utilisateur");
        this.setRefuser(Utilitaire.getMaxSeq("getsequtilisateur"));
        this.setLoginuser(loginus);
        this.setPwduser(pwdus);
        this.setNomuser(nomus);
        this.setAdruser(adrus);
        this.setTeluser(telus);
        this.setIdrole(idrol);
        super.setNomTable("utilisateur");
    }

    @Override
    public String getAttributIDName() {
        return "refuser";
    }

    @Override
    public String getTuppleID() {
        return String.valueOf(this.refuser);
    }

    public MapUtilisateur(String loginuser, String pwduser) {
        this.loginuser = loginuser;
        this.pwduser = pwduser;
        this.setNomTable("utilisateur");
        super.setNombreChamp(7);
    }

    public MapRoles getRole() {
        return this.role;
    }

    public ActionRole[] getActionRoles(Connection c) throws Exception {
        String aWhere = " and utilisateur = '" + this.getTuppleID() + "' or (direction='" + this.getAdruser() + "' and utilisateur is null )";
        aWhere = aWhere + " or (roleminimum<='" + this.getRang() + "' and utilisateur is null and direction is null)";
        return (ActionRole[])CGenUtil.rechercher((ClassMAPTable)new ActionRole(), null, null, c, aWhere);
    }

    public String getEtat() {
        AnnulationUtilisateurUtil au = new AnnulationUtilisateurUtil();
        if (au.rechercher(2, this.getTuppleID()).length > 0) {
            return "activer";
        }
        return "desactiver";
    }

    public boolean isSuperUser() {
        return this.getIdrole().compareToIgnoreCase("dg") == 0 || this.getIdrole().compareToIgnoreCase("controle") == 0;
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

    public void setPwduser(String pwduser) throws Exception {
        if (this.getMode().equals("modif") && (pwduser == null || pwduser.compareToIgnoreCase("") == 0)) {
            throw new Exception("Mots de passe invalide");
        }
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

    public String getAdruser() {
        return this.adruser;
    }

    public void setAdruser(String adruser) {
        this.adruser = adruser;
    }

    public String getTeluser() {
        return this.teluser;
    }

    public void setTeluser(String teluser) {
        this.teluser = teluser;
    }

    public int getRefuser() {
        return this.refuser;
    }

    public void setRefuser(int refuser) {
        this.refuser = refuser;
    }

    public void setRefuser(String refuser) {
        this.refuser = Integer.valueOf(refuser);
    }

    @Override
    public String[] getMotCles() {
        String[] motCles = new String[]{"refuser", "loginuser"};
        return motCles;
    }
}

