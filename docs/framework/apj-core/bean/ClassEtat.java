/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  user.UserEJB
 *  user.UserEJBBean
 *  utilisateur.Utilisateur
 */
package bean;

import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.ClassUser;
import bean.RoleValidation;
import historique.MapHistorique;
import historique.MapUtilisateur;
import java.sql.Connection;
import java.sql.Statement;
import user.UserEJB;
import user.UserEJBBean;
import utilisateur.Utilisateur;
import utilitaire.ConstanteEtat;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public abstract class ClassEtat
extends ClassUser {
    int etat = 1;
    String[] roleValidation = null;
    private boolean isHierarchique = false;

    public boolean getIsHierarchique() {
        return this.isHierarchique;
    }

    public void setIsHierarchique(boolean hierarchique) {
        this.isHierarchique = hierarchique;
    }

    public String[] getRoleValidation() {
        return this.roleValidation;
    }

    public void setRoleValidation(String[] roleValidation) {
        this.roleValidation = roleValidation;
    }

    public int getEtat() {
        return this.etat;
    }

    @Override
    public int updateObject(String u, Connection c) throws Exception {
        if (this.getEtat() >= ConstanteEtat.getEtatValider()) {
            throw new Exception("Impossible de modifier un objet deja valide");
        }
        return this.updateToTableWithHisto(u, c);
    }

    public int updateEtat(String u, Connection c, int eta) throws Exception {
        this.setEtat(eta);
        return this.updateToTableWithHisto(u, c);
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getEtatText(int value) {
        return this.chaineEtat(value);
    }

    public void annulerVisa(String u, Connection c) throws Exception {
        boolean ouvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                c.setAutoCommit(false);
                ouvert = true;
            }
            this.setMode("modif");
            this.controlerAnnulationVisa(c);
            this.setEtat(1);
            this.updateToTableWithHisto(u, c);
            if (c != null && ouvert) {
                c.commit();
            }
        }
        catch (Exception e) {
            if (c != null && ouvert) {
                c.rollback();
            }
            e.printStackTrace();
            throw e;
        }
        finally {
            if (c != null && ouvert) {
                c.close();
            }
        }
    }

    public int updateEtat(int valeurEtat, String id, Connection c) throws Exception {
        try (Statement cmd = null;){
            String req = "update " + this.getNomTable() + " set etat=" + valeurEtat + " where " + this.getAttributIDName() + " = '" + id + "'";
            cmd = c.createStatement();
            int n = cmd.executeUpdate(req);
            return n;
        }
    }

    public String chaineEtat(int value) {
        if (value == constante.ConstanteEtat.getEtatCreer()) {
            return "<b style='color:lightskyblue' multi-lang>CR&Eacute;&Eacute;(E)</b>";
        }
        if (value == constante.ConstanteEtat.getEtatValider()) {
            return "<b style='color:green' multi-lang>TRAIT&Eacute;(E)</b>";
        }
        if (value == constante.ConstanteEtat.getEtatAnnuler()) {
            return "<b style='color:orange' multi-lang>ANNUL&Eacute;(E)</b>";
        }
        if (value == constante.ConstanteEtat.getEtatCloture()) {
            return "<b style='color:orange' multi-lang>CLOTUR&Eacute;(E)</b>";
        }
        if (value == constante.ConstanteEtat.getEtatPlanifie()) {
            return "<b style='color:lightskyblue' multi-lang>PLANIFI&Eacute;(E)</b>";
        }
        if (value == constante.ConstanteEtat.getEtatInterviewe()) {
            return "<b style='color:lightskyblue' multi-lang>INTERVIEW&Eacute;(E)</b>";
        }
        return "<b style='color:lightskyblue' multi-lang>AUTRE</b>";
    }

    public String chaineEtat() {
        if (this.getEtat() == constante.ConstanteEtat.getEtatCreer()) {
            return "<b style='color:lightskyblue' multi-lang>CR&Eacute;&Eacute;(E)</b>";
        }
        if (this.getEtat() == constante.ConstanteEtat.getEtatValider()) {
            return "<b style='color:green' multi-lang>TRAIT&Eacute;(E)</b>";
        }
        if (this.getEtat() == constante.ConstanteEtat.getEtatAnnuler()) {
            return "<b style='color:orange' multi-lang>ANNUL&Eacute;(E)</b>";
        }
        if (this.getEtat() == constante.ConstanteEtat.getEtatCloture()) {
            return "<b style='color:orange' multi-lang>CLOTUR&Eacute;(E)</b>";
        }
        if (this.getEtat() == constante.ConstanteEtat.getEtatPlanifie()) {
            return "<b style='color:lightskyblue' multi-lang>PLANIFI&Eacute;(E)</b>";
        }
        if (this.getEtat() == constante.ConstanteEtat.getEtatInterviewe()) {
            return "<b style='color:lightskyblue' multi-lang>INTERVIEW&Eacute;(E)</b>";
        }
        return "<b style='color:lightskyblue' multi-lang>AUTRE</b>";
    }

    public Object validerObject(String u, Connection c) throws Exception {
        boolean estHierarchique;
        this.setMode("modif");
        this.controlerUpdate(c);
        if (this.getMessageNotif() != null && !this.getMessageNotif().isEmpty()) {
            this.creerNotification(u, c);
        }
        boolean bl = estHierarchique = this.getIsHierarchique() || this.getRoleValidation() != null;
        if (this.getEtat() > 0 && this.getEtat() < constante.ConstanteEtat.getEtatValider() && !estHierarchique) {
            this.setEtat(constante.ConstanteEtat.getEtatValider());
        }
        this.setActionValidation("Validation");
        this.updateToTableWithHisto(u, c);
        return this;
    }

    public Object validerObject(MapUtilisateur u, Connection c) throws Exception {
        return this.validerObject(u.getTuppleID(), c);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void viser(UserEJB user, Connection c) throws Exception {
        RoleValidation rl = new RoleValidation();
        rl.setMapping(this.getClass().getName());
        RoleValidation[] rltab = null;
        try {
            rltab = (RoleValidation[])CGenUtil.rechercher(rl, null, null, null, "");
        }
        catch (Exception e) {
            c.rollback();
            System.err.println("(ClassEtat.java:186) : " + e.getMessage());
        }
        if (rltab != null && rltab.length > 0) {
            this.setIsHierarchique(true);
            int indexe = 0;
            int mitovy = 0;
            for (int i = 0; i < rltab.length; ++i) {
                if (!rltab[i].getRole().equalsIgnoreCase(user.getUser().getIdrole())) continue;
                ++mitovy;
                indexe = i;
            }
            if (mitovy == 0) {
                throw new Exception("Cet utilisateur ne peut pas valider cet objet.");
            }
            ClassMAPTable temp = (ClassMAPTable)Class.forName(this.getClass().getName()).newInstance();
            temp.setNomTable(this.getNomTable());
            temp.setTuppleId(this.getTuppleID());
            ClassEtat obj = (ClassEtat)temp.getById(temp.getTuppleID(), temp.getNomTable(), c);
            if (obj.getEtat() >= rltab[indexe].getValeurEtat()) {
                throw new Exception("Objet deja vise.");
            }
            if (rltab[indexe].getPersonne().equalsIgnoreCase("*")) {
                this.setEtat(rltab[indexe].getValeurEtat());
                this.validerObject(user.getUser(), c);
                return;
            }
            if (rltab[indexe].getPersonne().contains(user.getUser().getLoginuser())) {
                this.setEtat(rltab[indexe].getValeurEtat());
                this.validerObject(user.getUser(), c);
                return;
            }
            CharSequence[] parts = rltab[indexe].getPersonne().split(";");
            String result = String.join((CharSequence)" ou ", parts);
            throw new Exception("Seul l'utilisateur " + result + " peut valider cet objet.");
        }
        if (this.getRoleValidation() != null && this.getRoleValidation().length > 0) {
            String previousRole = Utilitaire.getPreviousRole(this.getRoleValidation(), user.getUser().getIdrole());
            if ((previousRole == null || previousRole.equalsIgnoreCase("") || previousRole.equalsIgnoreCase("null")) && user.getUser().getIdrole().equalsIgnoreCase(this.getRoleValidation()[0])) {
                this.validerObject(user.getUser(), c);
                return;
            }
            Utilisateur u1 = new Utilisateur();
            u1.setIdrole(previousRole);
            Utilisateur[] allPreviousUser = (Utilisateur[])CGenUtil.rechercher((ClassMAPTable)u1, null, null, c, "");
            MapHistorique h1 = new MapHistorique();
            h1.setRefObjet(this.getTuppleID());
            MapHistorique[] histo = (MapHistorique[])CGenUtil.rechercher(h1, null, null, c, " and ACTION like '%Validation%' order by DATEHISTORIQUE desc, HEURE desc");
            if (histo.length <= 0) throw new Exception("L'utilisateur avec le r\u00f4le " + previousRole + " doit encore valider cet objet");
            String erreur = "";
            for (int i = 0; i < allPreviousUser.length; ++i) {
                if (allPreviousUser[i].getRefuser().equalsIgnoreCase(histo[0].getIdUtilisateur())) {
                    this.validerObject(user.getUser(), c);
                    return;
                }
                erreur = previousRole;
            }
            if (erreur.equalsIgnoreCase("")) return;
            throw new Exception("L'utilisateur avec le r\u00f4le " + erreur + " doit encore valider cet objet");
        }
        this.validerObject(user.getUser(), c);
    }

    public void viser(UserEJBBean user, Connection c) throws Exception {
        this.validerObject(user.getUser(), c);
    }

    public void controleAnnuler(Connection c) throws Exception {
        if (this.getEtat() >= constante.ConstanteEtat.getEtatValider()) {
            throw new Exception("Impossible \u00c3\u00a0 annuler, deja valide");
        }
    }

    public void controleAnnulerVisa(Connection c) throws Exception {
        if (this.getEtat() > constante.ConstanteEtat.getEtatValider()) {
            throw new Exception("Impossible d annuler le Visa, superieur valide");
        }
    }

    public void annulerVisaBatch(String user, Statement st) throws Exception {
        if (this.getEtat() > constante.ConstanteEtat.getEtatValider()) {
            throw new Exception("Impossible \u00ef\u00bf\u00bd annuler, superieur valide");
        }
        this.setMode("modif");
        this.setEtat(constante.ConstanteEtat.getEtatCreer());
        this.updateToTableWithHistoBatch(user, st);
    }

    public Object validerObjectBatch(String u, Statement st) throws Exception {
        this.setMode("modif");
        this.controlerBatch(st);
        this.setEtat(constante.ConstanteEtat.getEtatValider());
        this.updateToTableWithHistoBatch(u, st);
        return this;
    }

    public Object cloturerObjectBatch(String u, Statement st) throws Exception {
        this.setMode("modif");
        this.controlerClotureBatch(st);
        this.setEtat(constante.ConstanteEtat.getEtatCloture());
        this.updateToTableWithHistoBatch(u, st);
        return this;
    }

    public int annuler(String user, Connection c) throws Exception {
        this.controleAnnuler(c);
        this.setMode("modif");
        this.setEtat(constante.ConstanteEtat.getEtatAnnuler());
        return this.updateToTableWithHisto(user, c);
    }

    public int annuler(Connection c) throws Exception {
        if (this.getEtat() >= constante.ConstanteEtat.getEtatValider()) {
            throw new Exception("Impossible \u00ef\u00bf\u00bd annuler, deja valide");
        }
        this.setMode("modif");
        return this.updateEtat(constante.ConstanteEtat.getEtatAnnuler(), CGenUtil.getValeurInsert(this, "id"), c);
    }

    public Object annulerValidation(String u, Connection c) throws Exception {
        this.annulerVisa(u, c);
        return this;
    }

    public Object cloturerObject(String u, Connection c) throws Exception {
        this.setMode("modif");
        this.setEtat(constante.ConstanteEtat.getEtatCloture());
        this.controler(c);
        this.updateToTableWithHisto(u, c);
        return this;
    }

    public Object payerObject(String u, Connection c) throws Exception {
        this.setMode("modif");
        this.setEtat(constante.ConstanteEtat.getEtatPayer());
        this.controler(c);
        this.updateToTableWithHisto(u, c);
        return this;
    }

    public Object payerObjectCarte(String u, Connection c) throws Exception {
        this.setMode("modif");
        this.setEtat(21);
        this.controler(c);
        this.updateToTableWithHisto(u, c);
        return this;
    }

    @Override
    public void controleVisa(Connection c) throws Exception {
        if (this.getEtat() > constante.ConstanteEtat.getEtatValider()) {
            throw new Exception("Impossible d annuler le Visa, superieur valide");
        }
    }

    public void controlerAnnulationVisa(Connection c) throws Exception {
    }

    public void changeState(String acte, String u, Connection con) throws Exception {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object cloturer(UserEJBBean user, Connection c, int etat) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
            }
            int etatClot = 9;
            if (etat >= 0) {
                etatClot = etat;
            }
            if (this.getEtat() > etat) {
                throw new Exception("Objet non cloturable, deja vis\u00c3\u00a9");
            }
            this.setEtat(etat);
            this.updateToTableWithHisto(user.getUser().getTuppleID(), c);
            if (estOuvert) {
                c.commit();
            }
            ClassEtat classEtat = this;
            return classEtat;
        }
        catch (Exception e) {
            c.rollback();
            e.printStackTrace();
        }
        finally {
            if (estOuvert && c != null) {
                c.close();
            }
        }
        return this;
    }

    public Object annulerObject(String u, Connection c) throws Exception {
        this.setMode("modif");
        this.controleAnnule(c);
        this.setEtat(constante.ConstanteEtat.getEtatAnnuler());
        this.updateToTableWithHisto(u, c);
        return this;
    }
}

