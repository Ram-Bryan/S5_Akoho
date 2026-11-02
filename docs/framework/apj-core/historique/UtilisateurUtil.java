/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.CGenUtil;
import bean.GenUtil;
import historique.MapUtilisateur;
import historique.MapUtilisateurServiceDirection;
import historique.ParamCrypt;
import historique.ParamCryptUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class UtilisateurUtil
extends GenUtil
implements Serializable,
Cloneable {
    public UtilisateurUtil() {
        super("Utilisateur");
    }

    public UtilisateurUtil(Connection c) {
        super("Utilisateur", c);
        System.out.println("test");
    }

    public MapUtilisateurServiceDirection testeValide(String user, String passe, Connection c) throws Exception {
        try {
            this.setNomTable("UtilisateurValide");
            MapUtilisateurServiceDirection[] temp = null;
            int[] a = new int[]{1};
            String[] valeur = new String[a.length];
            if (user.compareToIgnoreCase("") == 0 || passe.compareToIgnoreCase("") == 0 || passe.compareToIgnoreCase("%") == 0) {
                throw new Exception("Identifiants de connexion invalides ");
            }
            valeur[0] = user;
            MapUtilisateurServiceDirection rech = new MapUtilisateurServiceDirection();
            rech.setNomTable("UtilisateurValide");
            temp = (MapUtilisateurServiceDirection[])CGenUtil.rechercher(rech, null, null, c, String.format(" and loginuser like '%s'", user));
            if (temp.length > 0) {
                ParamCrypt[] pc = (ParamCrypt[])new ParamCryptUtil(c).rechercher(4, (Object)temp[0].getTuppleID(), c);
                if (pc.length == 0) {
                    throw new Exception("Pas de cryptage associe");
                }
                String passCrypt = Utilitaire.cryptWord(passe, pc[0].getNiveau(), pc[0].getCroissante());
                int u = temp[0].getPwduser().compareTo(passCrypt);
                if (u == 0) {
                    return temp[0];
                }
                throw new Exception("Identifiant ou mot de passe invalide ");
            }
            throw new Exception("Identifiant ou mot de passe invalide ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public MapUtilisateurServiceDirection testeValide(String user, String passe) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            MapUtilisateurServiceDirection mapUtilisateurServiceDirection = this.testeValide(user, passe, c);
            return mapUtilisateurServiceDirection;
        }
    }

    public MapUtilisateur testeValide(String nomTable, String user, String passe, Connection c) throws Exception {
        try {
            this.setNomTable(nomTable);
            MapUtilisateur[] temp = null;
            int[] a = new int[]{2};
            String[] valeur = new String[a.length];
            if (user.compareToIgnoreCase("") == 0 || passe.compareToIgnoreCase("") == 0 || passe.compareToIgnoreCase("%") == 0) {
                throw new Exception("Identifiants de connexion invalides ");
            }
            valeur[0] = user;
            MapUtilisateur crt = new MapUtilisateur();
            crt.setNomTable(nomTable);
            temp = (MapUtilisateur[])CGenUtil.rechercher(crt, null, null, c, " and upper(loginuser) =upper('" + user + "')");
            if (temp.length > 0) {
                ParamCrypt[] pc = (ParamCrypt[])new ParamCryptUtil(c).rechercher(4, (Object)temp[0].getTuppleID(), c);
                if (pc.length == 0) {
                    throw new Exception("Pas de cryptage associe");
                }
                String passCrypt = Utilitaire.cryptWord(passe, pc[0].getNiveau(), pc[0].getCroissante());
                System.out.println(temp[0].getLoginuser() + " : " + passCrypt);
                if (temp[0].getPwduser().compareTo(passCrypt) == 0) {
                    return temp[0];
                }
                throw new Exception("Identifiant ou mot de passe invalide ");
            }
            throw new Exception("Identifiant ou mot de passe invalide ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public MapUtilisateur testeValide(String nomTable, String user, String passe) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            MapUtilisateur mapUtilisateur = this.testeValide(nomTable, user, passe, c);
            return mapUtilisateur;
        }
    }

    public MapUtilisateur rechercheByRef(String refUser) {
        MapUtilisateur retour = ((MapUtilisateur[])this.rechercher(1, refUser))[0];
        return retour;
    }

    public MapUtilisateur[] recherche() {
        MapUtilisateur[] retour = (MapUtilisateur[])this.rechercher(1, "%");
        return retour;
    }

    public MapUtilisateur[] rechercheByRole(int role) {
        try {
            MapUtilisateur[] retour = null;
            int[] a = new int[]{7};
            Object[] valeur = new Object[a.length];
            if (role == 0) {
                retour = (MapUtilisateur[])this.rechercher(1, "%");
            } else {
                valeur[0] = String.valueOf(role);
                retour = (MapUtilisateur[])this.rechercher(a, valeur);
            }
            return retour;
        }
        catch (Exception ex) {
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object[] resultatGen(ResultSet rs) {
        try {
            int i = 0;
            int k = 0;
            MapUtilisateur temp = null;
            Vector<MapUtilisateur> vect = new Vector<MapUtilisateur>();
            while (rs.next()) {
                temp = new MapUtilisateur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                vect.add(i, temp);
                ++i;
            }
            Object[] retour = new MapUtilisateur[i];
            while (k < i) {
                retour[k] = (MapUtilisateur)vect.elementAt(k);
                ++k;
            }
            Object[] objectArray = retour;
            return objectArray;
        }
        catch (Exception s) {
            System.out.println("Resultat " + s.getMessage());
            Object[] objectArray = null;
            return objectArray;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL HistoriqueOrdUtil " + e.getMessage());
            }
        }
    }

    @Override
    public Object[] resultatLimit(int numBloc, ResultSet rs) {
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public MapUtilisateur testeValideCemedi(String nomTable, String user, String passe) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            this.setNomTable(nomTable);
            MapUtilisateur[] temp = null;
            int[] a = new int[]{2};
            Object[] valeur = new String[a.length];
            if (user.compareToIgnoreCase("") == 0 || passe.compareToIgnoreCase("") == 0 || passe.compareToIgnoreCase("%") == 0) {
                throw new Exception("Erreur de Login ");
            }
            valeur[0] = user;
            temp = (MapUtilisateur[])this.rechercher(a, valeur);
            if (temp.length <= 0) throw new Exception("Erreur de Login ");
            ParamCrypt crt = new ParamCrypt();
            crt.setIdUtilisateur(String.valueOf(temp[0].getRefuser()));
            ParamCrypt[] pc = (ParamCrypt[])CGenUtil.rechercher(crt, null, null, c, "");
            if (pc.length == 0) {
                throw new Exception("Pas de cryptage associe");
            }
            String passCrypt = Utilitaire.cryptWord(passe, pc[0].getNiveau(), pc[0].getCroissante());
            System.out.println("user == " + user + " == passCrypt == " + passCrypt);
            if (temp[0].getPwduser().compareTo(passCrypt) == 0) {
                MapUtilisateur mapUtilisateur = temp[0];
                return mapUtilisateur;
            }
            try {
                throw new Exception("Erreur de Login ");
            }
            catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }
    }
}

