/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.ejb.CreateException
 *  javax.ejb.Remote
 *  javax.ejb.SessionBean
 *  javax.ejb.SessionContext
 *  javax.ejb.Stateless
 *  mg.cnaps.utilisateur.CNAPSUser
 */
package historique;

import bean.ClassMAPTable;
import bean.ErreurDAO;
import historique.AnnulationUtilisateur;
import historique.AnnulationUtilisateurUtil;
import historique.Historique;
import historique.HistoriqueEtat;
import historique.HistoriqueLocal;
import historique.HistoriqueRemote;
import historique.HistoriqueUtil;
import historique.MapHistorique;
import historique.MapRoles;
import historique.MapUtilisateur;
import historique.Objet;
import historique.ObjetUtil;
import historique.ParamCrypt;
import historique.ParamCryptUtil;
import historique.RoleUtil;
import historique.UtilisateurUtil;
import java.sql.Connection;
import javax.ejb.CreateException;
import javax.ejb.Remote;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import mg.cnaps.utilisateur.CNAPSUser;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

@Stateless(name="HistoriqueBean")
@Remote(value={Historique.class})
public class HistoriqueBean
implements HistoriqueRemote,
HistoriqueLocal,
SessionBean {
    SessionContext sessionContext;

    public void ejbCreate() throws CreateException {
    }

    @Override
    public void ejbRemove() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    @Override
    public MapHistorique[] findHistorique(String refuser, String refObjet, String objet, String action, String daty1, String daty2) throws Exception {
        try {
            HistoriqueUtil hu = new HistoriqueUtil();
            int[] a = new int[]{4, 5, 6, 7};
            Object[] val = new String[a.length];
            val[3] = new String(refObjet);
            val[0] = new String(objet);
            val[1] = new String(action);
            val[2] = new String(refuser);
            if (daty1.compareTo("") == 0 & daty2.compareTo("") == 0) {
                return (MapHistorique[])hu.rechercher(a, val, " order by idHistorique desc");
            }
            if (daty1.compareTo("") > 0 & daty2.compareTo("") == 0) {
                return (MapHistorique[])hu.rechercher(a, val, " and datehistorique>='" + daty1 + "' order by idHistorique desc");
            }
            if (daty1.compareTo("") == 0 & daty2.compareTo("") > 0) {
                return (MapHistorique[])hu.rechercher(a, val, " and datehistorique<='" + daty2 + "' order by idHistorique desc");
            }
            if (daty1.compareTo("") != 0 & daty2.compareTo("") != 0) {
                return (MapHistorique[])hu.rechercher(a, val, " and datehistorique>='" + daty1 + "' and datehistorique<='" + daty2 + "'  order by idHistorique desc");
            }
            return null;
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public MapHistorique[] findHistoriquePage(String refuser, String refObjet, String objet, String action, String daty1, String daty2, int numPage) throws Exception {
        try {
            HistoriqueUtil hu = new HistoriqueUtil();
            int[] a = new int[]{4, 5, 6, 7};
            Object[] val = new String[a.length];
            val[3] = new String(refObjet);
            val[0] = new String(objet);
            val[1] = new String(action);
            val[2] = new String(refuser);
            if (daty1.compareTo("") == 0 & daty2.compareTo("") == 0) {
                return (MapHistorique[])hu.rechercher(a, val, " order by idHistorique desc", numPage);
            }
            if (daty1.compareTo("") > 0 & daty2.compareTo("") == 0) {
                return (MapHistorique[])hu.rechercher(a, val, " and datehistorique>='" + daty1 + "' order by idHistorique desc", numPage);
            }
            if (daty1.compareTo("") == 0 & daty2.compareTo("") > 0) {
                return (MapHistorique[])hu.rechercher(a, val, " and datehistorique<='" + daty2 + "' order by idHistorique desc", numPage);
            }
            if (daty1.compareTo("") != 0 & daty2.compareTo("") != 0) {
                return (MapHistorique[])hu.rechercher(a, val, " and datehistorique>='" + daty1 + "' and datehistorique<='" + daty2 + "'  order by idHistorique desc", numPage);
            }
            return null;
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public int calculHistoriquePage(String refuser, String refObjet, String objet, String action, String daty1, String daty2) throws Exception {
        try {
            HistoriqueUtil hu = new HistoriqueUtil();
            int[] a = new int[]{4, 5, 6, 7};
            Object[] val = new String[a.length];
            val[3] = new String(refObjet);
            val[0] = new String(objet);
            val[1] = new String(action);
            val[2] = new String(refuser);
            if (daty1.compareTo("") == 0 & daty2.compareTo("") == 0) {
                return hu.calculNombreLigne(a, val, " order by idHistorique desc");
            }
            if (daty1.compareTo("") > 0 & daty2.compareTo("") == 0) {
                return hu.calculNombreLigne(a, val, " and datehistorique>='" + daty1 + "' order by idHistorique desc");
            }
            if (daty1.compareTo("") == 0 & daty2.compareTo("") > 0) {
                return hu.calculNombreLigne(a, val, " and datehistorique<='" + daty2 + "' order by idHistorique desc");
            }
            if (daty1.compareTo("") != 0 & daty2.compareTo("") != 0) {
                return hu.calculNombreLigne(a, val, " and datehistorique>='" + daty1 + "' and datehistorique<='" + daty2 + "'  order by idHistorique desc");
            }
            return 0;
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Objet[] findObjet(String obj, String des) throws Exception {
        try {
            ObjetUtil ou = new ObjetUtil();
            int[] a = new int[]{1, 2};
            Object[] val = new String[a.length];
            val[0] = obj;
            val[1] = des;
            return (Objet[])ou.rechercher(a, val);
        }
        catch (CreateException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public MapRoles[] findRole(String rol) {
        return (MapRoles[])new RoleUtil().rechercher(1, rol);
    }

    @Override
    public String createUtilisateurs(String loginuser, String pwduser, String nomuser, String adruser, String teluser, String idrole, String refUser) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            MapUtilisateur[] uT = this.findUtilisateurs("%", loginuser, "%", "%", "%", "%", "%");
            if (uT.length > 0) {
                throw new Exception("Login deja\u00ef\u00bf\u00bd utilise!");
            }
            String passCrypt = null;
            int niveau = (int)Math.round(Math.random() * 10.0);
            int sens = (int)Math.round(Math.random());
            if (niveau == 0) {
                niveau = -5;
            }
            passCrypt = sens == 0 ? Utilitaire.cryptWord(pwduser.toLowerCase(), niveau, true) : Utilitaire.cryptWord(pwduser.toLowerCase(), niveau, false);
            MapUtilisateur u = new MapUtilisateur(loginuser, passCrypt, nomuser, adruser, teluser, idrole);
            u.insertToTableWithHisto(refUser, c);
            CNAPSUser cnapsUser = new CNAPSUser(idrole, "1", "1", "1", loginuser, "1", u.getTuppleID());
            cnapsUser.insertToTableWithHisto(refUser, c);
            ParamCrypt pc = new ParamCrypt(niveau, sens, u.getTuppleID());
            pc.insertToTable(c);
            c.commit();
            String string = u.getTuppleID();
            return string;
        }
    }

    @Override
    public String updateUtilisateurs(String iduser, String loginuser, String pwduser, String nomuser, String adruser, String teluser, String idrole, String refUser) throws Exception {
        try {
            ParamCrypt[] pc = (ParamCrypt[])new ParamCryptUtil().rechercher(4, iduser);
            if (pc.length == 0) {
                throw new Exception("Pas de cryptage associe");
            }
            String passCrypt = Utilitaire.cryptWord(pwduser, pc[0].getNiveau(), pc[0].getCroissante());
            MapUtilisateur u = new MapUtilisateur(iduser, loginuser, passCrypt, nomuser, adruser, teluser, idrole);
            MapHistorique h = new MapHistorique("Utilisateurs", "update", refUser, u.getTuppleID());
            u.updateToTableWithHisto(h);
            return u.getTuppleID();
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public int deleteUtilisateurs(String iduser, String refUser) {
        return 0;
    }

    @Override
    public MapUtilisateur[] findUtilisateurs(String refuser, String loginuser, String pwduser, String nomuser, String adruser, String teluser, String idrole) throws Exception {
        try {
            int[] a = new int[]{1, 2, 3, 4, 5, 6, 7};
            Object[] val = new String[a.length];
            val[0] = refuser;
            val[1] = loginuser;
            val[2] = pwduser;
            val[3] = nomuser;
            val[4] = adruser;
            val[5] = teluser;
            val[6] = idrole;
            UtilisateurUtil cu = new UtilisateurUtil();
            return (MapUtilisateur[])cu.rechercher(a, val);
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public String createHistorique(String dateHistorique, String heure, String objet, String action, String refuser, String refObjet) {
        return null;
    }

    @Override
    public String updateHistorique(String idHistorique, String dateHistorique, String heure, String objet, String action, String refuser, String refObjet) {
        return null;
    }

    @Override
    public int deleteHistorique(String idHistorique, String refUser) {
        return 0;
    }

    @Override
    public String createHistoriqueEtat(String idMere, String idUser, String idEtat, String dateModification) {
        return null;
    }

    @Override
    public String updateHistoriqueEtat(String idHistoriqueEtat, String idMere, String idUser, String idEtat, String dateModification) {
        return null;
    }

    @Override
    public int deleteHistoriqueEtat(String idHistoriqueEtat, String refUser) {
        return 0;
    }

    @Override
    public HistoriqueEtat[] findHistoriqueEtat(String idHistoriqueEtat, String idMere, String idUser, String idEtat, String dateModification) {
        return null;
    }

    @Override
    public int desactiveUtilisateur(String ref, String refUser) throws Exception {
        try {
            AnnulationUtilisateur au = new AnnulationUtilisateur(ref);
            MapHistorique h = new MapHistorique("Utilisateurs", "annule", refUser, ref);
            au.insertToTable(h);
            return 1;
        }
        catch (ErreurDAO ex) {
            throw new ErreurDAO(ex.getMessage());
        }
    }

    @Override
    public int activeUtilisateur(String ref, String refUser) throws ErreurDAO {
        try {
            AnnulationUtilisateur[] au = (AnnulationUtilisateur[])new AnnulationUtilisateurUtil().rechercher(2, ref);
            MapHistorique h = new MapHistorique("Utilisateurs", "active", refUser, ref);
            for (int i = 0; i < au.length; ++i) {
                au[i].deleteToTable(h);
            }
            return 1;
        }
        catch (ErreurDAO ex) {
            throw new ErreurDAO(ex.getMessage());
        }
    }

    public MapUtilisateur testeValide(String user, String pass) throws Exception {
        try {
            UtilisateurUtil uI = new UtilisateurUtil();
            return uI.testeValide(user, pass);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public int annulerSortie(String idSortie) {
        return 0;
    }

    @Override
    public int testException() throws ErreurDAO {
        HistoriqueUtil hl = new HistoriqueUtil();
        try {
            hl.testException();
            return 0;
        }
        catch (Exception ex) {
            throw new ErreurDAO(ex.getMessage());
        }
    }

    @Override
    public int create(ClassMAPTable value1, MapUtilisateur value2) throws Exception {
        return 0;
    }

    @Override
    public int update(ClassMAPTable value1, MapUtilisateur value2) throws Exception {
        return 0;
    }

    @Override
    public int delete(ClassMAPTable value1, MapUtilisateur value2) throws Exception {
        return 0;
    }

    @Override
    public MapUtilisateur testeValide(String user, String pass, String interim, String service) throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'testeValide'");
    }
}

