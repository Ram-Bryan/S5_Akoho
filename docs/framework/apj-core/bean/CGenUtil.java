/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  web.servlet.DateAutoComplete
 */
package bean;

import bean.Champ;
import bean.ClassFille;
import bean.ClassMAPTable;
import bean.ClassMAPTableException;
import bean.ClassMere;
import bean.ListeColonneTable;
import bean.ResultatEtSomme;
import historique.MapHistorique;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import utilitaire.Parametre;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;
import utilitaire.UtilitaireString;
import web.servlet.DateAutoComplete;

public class CGenUtil {
    public static String getListeChampPage(ClassMAPTable e, Connection c) throws Exception {
        String retour = "";
        Champ[] liste = ListeColonneTable.getFromListe(e, c);
        if (liste.length == 0) {
            throw new Exception("Table non existante");
        }
        retour = liste[0].getNomColonne();
        for (int i = 1; i < liste.length; ++i) {
            retour = retour + "," + liste[i].getNomColonne();
        }
        return retour;
    }

    public static Object[] getValFromEntete(ClassMAPTable e, String[] entete) throws Exception {
        Object[] results = new Object[entete.length];
        for (int i = 0; i < entete.length; ++i) {
            System.out.println("Test entete" + entete[i]);
            results[i] = CGenUtil.getValeurFieldByMethod(e, entete[i]);
        }
        return results;
    }

    public static String getListeChampGroupeSomme(String[] groupe, String[] somme) throws Exception {
        if (groupe == null || groupe.length == 0) {
            return "*";
        }
        String retour = groupe[0];
        for (int i = 1; i < groupe.length; ++i) {
            if (groupe[i] == null || groupe[i].compareToIgnoreCase("") == 0 || groupe[i].compareToIgnoreCase("-") == 0) continue;
            retour = retour + "," + groupe[i];
        }
        if (somme == null) {
            return retour;
        }
        for (int j = 0; j < somme.length; ++j) {
            if (somme[j] == null || somme[j].compareToIgnoreCase("") == 0 || somme[j].compareToIgnoreCase("-") == 0) continue;
            retour = retour + ", sum(" + somme[j] + ") as " + somme[j];
        }
        return retour;
    }

    public static String getListeChampGroupeSimple(String[] groupe, String[] somme) throws Exception {
        if (groupe == null) {
            return "*";
        }
        String retour = groupe[0];
        for (int i = 1; i < groupe.length; ++i) {
            if (groupe[i] == null || groupe[i].compareToIgnoreCase("") == 0 || groupe[i].compareToIgnoreCase("-") == 0) continue;
            retour = retour + "," + groupe[i];
        }
        if (somme == null) {
            return retour;
        }
        for (int j = 0; j < somme.length; ++j) {
            if (somme[j] == null || somme[j].compareToIgnoreCase("") == 0 || somme[j].compareToIgnoreCase("-") == 0) continue;
            retour = retour + ", " + somme[j];
        }
        return retour;
    }

    public static double[] calculSommeNombreGroupe(String selectInterne, String[] nomColSomme, Connection c) throws Exception {
        try (ResultSet dr = null;){
            String param = null;
            param = nomColSomme == null || nomColSomme.length == 0 ? "SELECT COUNT(*) FROM (" + selectInterne + ")" : "SELECT " + CGenUtil.getColonneSomme(nomColSomme) + ",COUNT(*) FROM (" + selectInterne + ")";
            Statement st = c.createStatement(1004, 1007);
            dr = st.executeQuery(param);
            double[] retour = null;
            retour = nomColSomme == null ? new double[1] : new double[nomColSomme.length + 1];
            dr.next();
            int i = 0;
            for (i = 0; i < retour.length - 1; ++i) {
                retour[i] = dr.getDouble(i + 1);
            }
            retour[i] = dr.getDouble(i + 1);
            double[] dArray = retour;
            return dArray;
        }
    }

    public static double[] calculSommeNombre(ClassMAPTable e, String apresWhere, String[] numChampDaty, String[] daty, String[] nomColSomme, Connection c) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        try {
            String param = null;
            param = nomColSomme == null ? "SELECT COUNT(*) FROM " + e.getNomTableSelect() + " WHERE " + CGenUtil.makeWhere(e, c) + " " + CGenUtil.makeWhereIntervalle(e, numChampDaty, daty) + " " + apresWhere : "SELECT " + CGenUtil.getColonneSomme(nomColSomme) + ",COUNT(*) FROM " + e.getNomTableSelect() + " WHERE " + CGenUtil.makeWhere(e, c) + " " + CGenUtil.makeWhereIntervalle(e, numChampDaty, daty) + " " + apresWhere;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(param);
            double[] retour = null;
            retour = nomColSomme == null ? new double[1] : new double[nomColSomme.length + 1];
            dr.next();
            int i = 0;
            for (i = 0; i < retour.length - 1; ++i) {
                retour[i] = dr.getDouble(i + 1);
            }
            retour[i] = dr.getDouble(i + 1);
            double[] dArray = retour;
            return dArray;
        }
        catch (Exception s) {
            s.printStackTrace();
            throw new Exception(s.getMessage());
        }
        finally {
            if (st != null) {
                st.close();
            }
            if (dr != null) {
                dr.close();
            }
        }
    }

    public static double[] calculSommeNombre(ClassMAPTable e, String req, String apresWhere, String[] numChampDaty, String[] daty, String[] nomColSomme, Connection c) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        try {
            String param = null;
            param = nomColSomme == null ? "SELECT COUNT(*) FROM (" + req + ") WHERE " + CGenUtil.makeWhere(e, c) + " " + CGenUtil.makeWhereIntervalle(numChampDaty, daty) + " " + apresWhere : "SELECT " + CGenUtil.getColonneSomme(nomColSomme) + ",COUNT(*) FROM (" + req + ") WHERE " + CGenUtil.makeWhere(e, c) + " " + CGenUtil.makeWhereIntervalle(numChampDaty, daty) + " " + apresWhere;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(param);
            double[] retour = null;
            retour = nomColSomme == null ? new double[1] : new double[nomColSomme.length + 1];
            dr.next();
            int i = 0;
            for (i = 0; i < retour.length - 1; ++i) {
                retour[i] = dr.getDouble(i + 1);
            }
            retour[i] = dr.getDouble(i + 1);
            double[] dArray = retour;
            return dArray;
        }
        catch (Exception s) {
            s.printStackTrace();
            throw new Exception(s.getMessage());
        }
        finally {
            if (st != null) {
                st.close();
            }
            if (dr != null) {
                dr.close();
            }
        }
    }

    public static String getColonneSomme(String[] listeCol) {
        if (listeCol == null || listeCol.length == 0) {
            return "0";
        }
        String retour = "sum(" + listeCol[0] + ")";
        for (int i = 1; i < listeCol.length; ++i) {
            retour = retour + ",sum(" + listeCol[i] + ")";
        }
        return retour;
    }

    public static String makePK(ClassMAPTable e, Connection c) {
        int maxSeq = Utilitaire.getMaxSeq(e.getNomProcedureSequence());
        String nombre = Utilitaire.completerInt(e.getLonguerClePrimaire(), maxSeq);
        return e.getINDICE_PK() + nombre;
    }

    public static ClassMAPTable saveWithPKSeq(ClassMAPTable e, Connection c) throws Exception {
        CGenUtil.makePK(e, c);
        return CGenUtil.save(e, c);
    }

    public static ClassMAPTable save(ClassMAPTable e) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            ClassMAPTable classMAPTable = CGenUtil.save(e, c);
            return classMAPTable;
        }
    }

    public static int insertBatch(ClassMAPTable[] liste, Connection c) throws Exception {
        try (Statement cmd = null;){
            cmd = c.createStatement();
            for (ClassMAPTable obj : liste) {
                obj.construirePK(c);
                String req = obj.getComandeInsert(c);
                cmd.addBatch(req);
            }
            int n = cmd.executeBatch().length;
            return n;
        }
    }

    public static int updateBatch(ClassMAPTable[] liste, Connection c) throws Exception {
        try (Statement cmd = null;){
            cmd = c.createStatement();
            for (ClassMAPTable obj : liste) {
                String req = obj.getComandeUpdate(c);
                cmd.addBatch(req);
            }
            int n = cmd.executeBatch().length;
            return n;
        }
    }

    public static ClassMAPTable save(ClassMAPTable e, Connection c) throws Exception {
        try {
            Statement cmd = null;
            Champ[] listeC = ListeColonneTable.getFromListe(e, c);
            String comande = "insert into " + e.getNomTable() + " values (";
            comande = comande + "'" + CGenUtil.getValeurFieldByMethod(e, listeC[0].getNomColonne()) + "'";
            for (int i = 1; i < listeC.length; ++i) {
                comande = comande + ",";
                comande = CGenUtil.getValeurFieldByMethod(e, listeC[i].getNomColonne()) == null ? comande + " null " : comande + "'" + CGenUtil.getValeurFieldByMethod(e, listeC[i].getNomColonne()) + "'";
            }
            comande = comande + ")";
            cmd = c.createStatement();
            cmd.executeUpdate(comande);
            return e;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public static Object[] rechercher(ClassMAPTable e, String[] colInt, String[] valInt, String apresWhere) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            Object[] objectArray = CGenUtil.rechercher(e, colInt, valInt, c, apresWhere);
            return objectArray;
        }
    }

    public static Object[] rechercher(ClassMAPTable e, String requete) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            Object[] objectArray = CGenUtil.rechercher(e, requete, c);
            return objectArray;
        }
    }

    public static ClassMere getMereAvecFille(ClassMere crt, Connection c, String nTableFille) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
            }
            ClassMere ret = (ClassMere)crt.getById(crt.getTuppleID(), null, c);
            ClassFille cfille = (ClassFille)Class.forName(ret.getNomClasseFille()).newInstance();
            String nt = cfille.getNomTable();
            if (nTableFille != null && nTableFille.compareToIgnoreCase("") != 0) {
                nt = nTableFille;
            }
            ClassFille[] cf = ret.getFille(nt, c, "");
            ret.setFille(cf);
            ClassMere classMere = ret;
            return classMere;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if (estOuvert && c != null) {
                c.close();
            }
        }
    }

    public static ClassFille getFilleAvecMere(ClassFille crt, Connection c, String nTableMere) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
            }
            ClassFille fi = (ClassFille)crt.getById(crt.getTuppleID(), null, c);
            ClassMere mama = fi.findMere(nTableMere, c);
            mama.ajouterFille(fi);
            ClassFille classFille = fi;
            return classFille;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if (estOuvert && c != null) {
                c.close();
            }
        }
    }

    public static Object[] rechercher(ClassMAPTable e, String requete, Connection c) throws Exception {
        ResultSet dr = null;
        Statement cmd = null;
        boolean ifconnnull = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                ifconnnull = true;
            }
            cmd = c.createStatement();
            String req = "select * from (" + requete + ") where " + CGenUtil.makeWhere(e, c);
            dr = cmd.executeQuery(req);
            Object[] objectArray = CGenUtil.transformeDataReader(dr, e, c);
            return objectArray;
        }
        catch (Exception x) {
            x.printStackTrace();
            throw new Exception("Erreur durant la recherche " + x.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (cmd != null) {
                cmd.close();
            }
            if (ifconnnull && c != null) {
                c.close();
            }
        }
    }

    public static Object[] rechercherReq(ClassMAPTable e, String requete, Connection c, String aWHere) throws Exception {
        ResultSet dr = null;
        Statement cmd = null;
        boolean ifconnnull = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                ifconnnull = true;
            }
            cmd = c.createStatement();
            String apres = "";
            if (aWHere != null) {
                apres = aWHere;
            }
            String req = "select * from (" + requete + ") where " + CGenUtil.makeWhere(e, c) + " " + apres;
            if (UtilDB.properties.getProperty("apj.debug") != null && UtilDB.properties.getProperty("apj.debug").equalsIgnoreCase("true")) {
                System.out.println("Requete select rechercherReq : " + req);
            }
            dr = cmd.executeQuery(req);
            Object[] objectArray = CGenUtil.transformeDataReader(dr, e, c);
            return objectArray;
        }
        catch (Exception x) {
            x.printStackTrace();
            throw new Exception("Erreur durant la recherche " + x.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (cmd != null) {
                cmd.close();
            }
            if (ifconnnull && c != null) {
                c.close();
            }
        }
    }

    public static ResultatEtSomme rechercher(ClassMAPTable e, String requete, String[] nomColSomme) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            ResultatEtSomme resultatEtSomme = CGenUtil.rechercher(e, requete, c, nomColSomme);
            return resultatEtSomme;
        }
    }

    public static ResultatEtSomme rechercher(ClassMAPTable e, String requete, Connection c, String[] nomColSomme) throws Exception {
        ResultSet dr = null;
        Statement cmd = null;
        boolean ifconnnull = false;
        try {
            ResultatEtSomme rs;
            if (c == null) {
                c = new UtilDB().GetConn();
                ifconnnull = true;
            }
            cmd = c.createStatement();
            dr = cmd.executeQuery(requete);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombreGroupe(requete, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception x) {
            x.printStackTrace();
            throw new Exception("Erreur durant la recherche " + x.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (cmd != null) {
                cmd.close();
            }
            if (ifconnnull && c != null) {
                c.close();
            }
        }
    }

    public static HashMap<String, Vector> rechercher2D(ClassMAPTable e, String[] colInt, String[] valInt, String attr2D, Connection c, String apresWhere) throws Exception {
        ResultSet dr = null;
        Statement cmd = null;
        boolean ifconnnull = false;
        try {
            HashMap<String, Vector> ret;
            if (c == null) {
                c = new UtilDB().GetConn();
                ifconnnull = true;
            }
            String comandeInterne = "select * from " + e.getNomTableSelect();
            if (apresWhere == null || apresWhere.compareToIgnoreCase("") == 0) {
                apresWhere = " order by " + attr2D + " asc";
            }
            comandeInterne = comandeInterne + " where " + CGenUtil.makeWhere(e, c) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere;
            cmd = c.createStatement();
            dr = cmd.executeQuery(comandeInterne);
            HashMap<String, Vector> hashMap = ret = CGenUtil.transformeDataReader2D(dr, e, attr2D, c);
            return hashMap;
        }
        catch (Exception x) {
            x.printStackTrace();
            throw new Exception("Erreur durant la recherche " + x.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (cmd != null) {
                cmd.close();
            }
            if (ifconnnull && c != null) {
                c.close();
            }
        }
    }

    public static Object[] rechercher(ClassMAPTable e, String[] colInt, String[] valInt, Connection c, String apresWhere) throws Exception {
        ResultSet dr = null;
        Statement cmd = null;
        boolean ifconnnull = false;
        try {
            Object[] ret;
            if (c == null) {
                c = new UtilDB().GetConn();
                ifconnnull = true;
            }
            String comandeInterne = "select * from " + e.getNomTableSelect();
            comandeInterne = comandeInterne + " where " + CGenUtil.makeWhere(e, c) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere;
            if (UtilDB.properties.getProperty("apj.debug") != null && UtilDB.properties.getProperty("apj.debug").equalsIgnoreCase("true")) {
                System.out.println("Requete select rechercher : " + comandeInterne);
            }
            cmd = c.createStatement();
            if (e.getNomTable().compareToIgnoreCase("CLIENT_actif") == 0) {
                System.out.println("AVANT execute query client " + Utilitaire.heureCouranteHMS() + " select " + comandeInterne);
            }
            dr = cmd.executeQuery(comandeInterne);
            if (e.getNomTable().compareToIgnoreCase("CLIENT_actif") == 0) {
                System.out.println("APRESSSS execute query client " + Utilitaire.heureCouranteHMS());
            }
            Object[] objectArray = ret = CGenUtil.transformeDataReader(dr, e, c);
            return objectArray;
        }
        catch (Exception x) {
            x.printStackTrace();
            throw new Exception("Erreur durant la recherche " + x.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (cmd != null) {
                cmd.close();
            }
            if (ifconnnull && c != null) {
                c.close();
            }
        }
    }

    public static Object rechercherSimple(String requete, Connection c) throws Exception {
        ResultSet dr = null;
        Statement cmd = null;
        boolean ifconnnull = false;
        Object val = null;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                ifconnnull = true;
            }
            if ((dr = (cmd = c.createStatement(1004, 1008)).executeQuery(requete)).next()) {
                val = dr.getObject(1);
            }
        }
        catch (Exception x) {
            x.printStackTrace();
            throw new Exception("Erreur durant la recherche " + x.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (cmd != null) {
                cmd.close();
            }
            if (ifconnnull && c != null) {
                c.close();
            }
        }
        return val;
    }

    public static Object[] rechercherPrecis(ClassMAPTable e, String[] colInt, String[] valInt, Connection c, String apresWhere) throws Exception {
        ResultSet dr = null;
        Statement cmd = null;
        boolean ifconnnull = false;
        try {
            Object[] ret;
            if (c == null) {
                c = new UtilDB().GetConn();
                ifconnnull = true;
            }
            String comandeInterne = "select * from " + e.getNomTableSelect();
            comandeInterne = comandeInterne + " where " + CGenUtil.makeWherePrecis(e, c) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere;
            cmd = c.createStatement();
            dr = cmd.executeQuery(comandeInterne);
            Object[] objectArray = ret = CGenUtil.transformeDataReader(dr, e, c);
            return objectArray;
        }
        catch (Exception x) {
            x.printStackTrace();
            throw new Exception("Erreur durant la recherche " + x.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (cmd != null) {
                cmd.close();
            }
            if (ifconnnull && c != null) {
                c.close();
            }
        }
    }

    public static Object[] rechercherANDOR(ClassMAPTable e, String[] colInt, String[] valInt, Connection c, String[] colOr, String[][] valOr, String apresWhere) throws Exception {
        ResultSet dr = null;
        Statement cmd = null;
        try {
            String comandeInterne = "select * from " + e.getNomTableSelect();
            comandeInterne = comandeInterne + " where " + CGenUtil.makeWhere(e, c) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " and (" + CGenUtil.makeOr(colOr, valOr) + ") " + apresWhere;
            cmd = c.createStatement();
            dr = cmd.executeQuery(comandeInterne);
            Object[] objectArray = CGenUtil.transformeDataReader(dr, e, c);
            return objectArray;
        }
        catch (Exception x) {
            x.printStackTrace();
            throw new Exception("Erreur durant la recherche " + x.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (cmd != null) {
                cmd.close();
            }
        }
    }

    public static ResultatEtSomme rechercherPage(ClassMAPTable e, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, String colOrdre, String ordre) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            if (colOrdre == null || colOrdre.compareToIgnoreCase("") == 0) {
                colOrdre = e.getAttributIDName();
                ordre = "DESC";
            }
            apresWhere = apresWhere + " order by " + colOrdre + " " + ordre;
            ResultatEtSomme resultatEtSomme = CGenUtil.rechercherPage(e, colInt, valInt, numPage, apresWhere, nomColSomme, c);
            return resultatEtSomme;
        }
    }

    public static ResultatEtSomme rechercherPage(ClassMAPTable e, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            ResultatEtSomme resultatEtSomme = CGenUtil.rechercherPage(e, colInt, valInt, numPage, apresWhere, nomColSomme, c);
            return resultatEtSomme;
        }
    }

    public static ResultatEtSomme rechercherPage(ClassMAPTable e, String requete, int numPage, String[] nomColSomme, int nbPage) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            ResultatEtSomme resultatEtSomme = CGenUtil.rechercherPage(e, requete, numPage, nomColSomme, c, nbPage);
            return resultatEtSomme;
        }
    }

    public static ResultatEtSomme rechercherParCritere(ClassMAPTable e, String apresWhere) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            ResultatEtSomme resultatEtSomme = CGenUtil.rechercherPCritere(e, apresWhere, c);
            return resultatEtSomme;
        }
    }

    public static Object[] copierPartiel(Object[] o, int iInf, int iSup) {
        Object[] retour = new Object[iSup - iInf + 1];
        for (int i = iInf; i < iSup || i < o.length; ++i) {
            retour[i] = o[i];
        }
        return retour;
    }

    public static String[] separerOrderBy(String apresWhere) {
        String[] retour = new String[]{"", apresWhere};
        char[] cv = apresWhere.toCharArray();
        if (apresWhere == null || apresWhere.compareTo("") == 0) {
            // empty if block
        }
        for (int i = 0; i < cv.length; ++i) {
            if (!UtilitaireString.testMotDansUnePhrase(apresWhere, i, "order")) continue;
            retour[0] = apresWhere.substring(0, i);
            retour[1] = apresWhere.substring(i, apresWhere.length());
            return retour;
        }
        return retour;
    }

    public static ResultatEtSomme rechercherPage(ClassMAPTable e, String requete, int numPage, String[] nomColSomme, String apresWhere, Connection c, int nbPage) throws Exception {
        boolean estOuvert = false;
        ResultSet dr = null;
        Statement st = null;
        try {
            ResultatEtSomme rs;
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
            }
            int npp = 0;
            npp = nbPage <= 1 ? Parametre.getNbParPage() : nbPage;
            if (numPage == 0) {
                numPage = 1;
            }
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String commande = "select * from (select dt.*,rowNum as r from (" + requete + ") dt where " + CGenUtil.makeWhere(e, c) + " " + apresWhere + ")  where r between " + indiceInf + " and " + indiceSup;
            st = c.createStatement(1004, 1007);
            if (UtilDB.properties.getProperty("apj.debug") != null && UtilDB.properties.getProperty("apj.debug").equalsIgnoreCase("true")) {
                System.out.println("Requete rechercherPage : " + commande);
            }
            dr = st.executeQuery(commande);
            e.setRequete(commande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombre(e, requete, apresWhere, null, null, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche pagine de la table " + e.getNomTableSelect() + ex.getMessage());
        }
        finally {
            if (c != null && estOuvert) {
                c.close();
            }
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static ResultatEtSomme rechercherPage(ClassMAPTable e, String requete, int numPage, String[] nomColSomme, Connection c, int nbPage) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        try {
            ResultatEtSomme rs;
            int npp = 0;
            npp = nbPage <= 0 ? Parametre.getNbParPage() : nbPage;
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String commande = "select * from (" + requete + ") where r between " + indiceInf + " and " + indiceSup + "";
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(commande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombre(e, "", null, null, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche pagine de la table " + e.getNomTableSelect() + ex.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static ResultatEtSomme rechercherPageMax(ClassMAPTable e, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, Connection c, int nbPage) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        try {
            ResultatEtSomme rs;
            int npp = 0;
            npp = nbPage <= 0 ? 50 : nbPage;
            if (apresWhere == null || apresWhere.compareToIgnoreCase("") == 0) {
                String colOrdre = e.getAttributIDName();
                String ordre = "DESC";
                apresWhere = " order by " + colOrdre + " " + ordre;
            }
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String comande = "select * from (select " + CGenUtil.getListeChampPage(e, c) + " from (select * from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhere(e, c) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere + ")) where rownum <=" + indiceSup;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombre(e, apresWhere, colInt, valInt, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche pagine de la table " + e.getNomTableSelect() + ex.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static ResultatEtSomme rechercherPageMaxSansRecap(ClassMAPTable e, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, Connection c, int nbPage) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        try {
            ResultatEtSomme rs;
            int npp = 0;
            npp = nbPage <= 0 ? 50 : nbPage;
            if (apresWhere == null || apresWhere.compareToIgnoreCase("") == 0) {
                String colOrdre = e.getAttributIDName();
                String ordre = "DESC";
                apresWhere = " order by " + colOrdre + " " + ordre;
            }
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String comande = "select * from (select " + CGenUtil.getListeChampPage(e, c) + " from (select * from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhere(e, c) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere + ")) where rownum <=" + indiceSup;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = null;
            somme = nomColSomme == null ? new double[1] : new double[nomColSomme.length + 1];
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche pagine de la table " + e.getNomTableSelect() + ex.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static ResultatEtSomme rechercherPage(ClassMAPTable e, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, Connection c, int nbPage) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        try {
            ResultatEtSomme rs;
            int npp = 0;
            if (nbPage <= 0) {
                if (nbPage == -2) {
                    double[] sommeNeg = new double[]{0.0};
                    Object[] valiny = CGenUtil.rechercher(e, colInt, valInt, c, apresWhere);
                    ResultatEtSomme resultatEtSomme = new ResultatEtSomme(valiny, sommeNeg);
                    return resultatEtSomme;
                }
                npp = Parametre.getNbParPage();
            } else {
                npp = nbPage;
            }
            if (apresWhere == null || apresWhere.compareToIgnoreCase("") == 0) {
                Champ[] liste = ListeColonneTable.getFromListe(e, c);
                String colOrdre = liste[0].getNomColonne();
                String ordre = "DESC";
                apresWhere = " order by " + colOrdre + " " + ordre;
            }
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String comande = "select * from (select " + CGenUtil.getListeChampPage(e, c) + ",rowNum as r from (select * from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhere(e, c) + CGenUtil.makeWhereIntervalle(e, colInt, valInt) + CGenUtil.makeWhereSelectMultiple(e) + " " + apresWhere + ")) where r between " + indiceInf + " and " + indiceSup;
            if (UtilDB.properties.getProperty("apj.debug") != null && UtilDB.properties.getProperty("apj.debug").equalsIgnoreCase("true")) {
                System.out.println("Requete select rechercher Page : " + comande);
            }
            st = c.createStatement(1003, 1007);
            st.setFetchSize(500);
            if (e.getNomTable().compareToIgnoreCase("CLIENT_actif") == 0 || e.getNomTable().startsWith("VENTE") || e.getNomTable().startsWith("FACTUREFOURN")) {
                System.out.println("AVANT EXCUTION REQUETE " + Utilitaire.heureCouranteHMS() + " req = " + comande);
            }
            dr = st.executeQuery(comande);
            if (e.getNomTable().compareToIgnoreCase("CLIENT_actif") == 0 || e.getNomTable().startsWith("VENTE") || e.getNomTable().startsWith("FACTUREFOURN")) {
                System.out.println("APRES EXCUTION REQUETE " + Utilitaire.heureCouranteHMS() + " req = " + comande);
            }
            e.setRequete(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            if (e.getNomTable().compareToIgnoreCase("CLIENT_actif") == 0 || e.getNomTable().startsWith("VENTE") || e.getNomTable().startsWith("FACTUREFOURN")) {
                System.out.println("AVANT GROUPE REQUETE " + Utilitaire.heureCouranteHMS() + " req = " + comande);
            }
            double[] somme = CGenUtil.calculSommeNombre(e, apresWhere, colInt, valInt, nomColSomme, c);
            if (e.getNomTable().compareToIgnoreCase("CLIENT_actif") == 0 || e.getNomTable().startsWith("VENTE") || e.getNomTable().startsWith("FACTUREFOURN")) {
                System.out.println("APRES GROUPE REQUETE " + Utilitaire.heureCouranteHMS() + " req = " + comande);
            }
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche pagine de la table " + e.getNomTableSelect() + ex.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static Object[] rechercherPage(ClassMAPTable e, String[] colInt, String[] valInt, int numPage, String apresWhere, Connection c, int nbPage) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        try {
            int npp = 0;
            if (nbPage <= 0) {
                if (nbPage == -2) {
                    double[] sommeNeg = new double[]{0.0};
                    Object[] objectArray = CGenUtil.rechercher(e, colInt, valInt, c, apresWhere);
                    return objectArray;
                }
                npp = Parametre.getNbParPage();
            } else {
                npp = nbPage;
            }
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String comande = "select * from (select " + CGenUtil.getListeChampPage(e, c) + ",rowNum as r from (select * from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhere(e, c) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere + ")) where r between " + indiceInf + " and " + indiceSup;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] objectArray = CGenUtil.transformeDataReader(dr, e, c);
            return objectArray;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche pagine de la table " + e.getNomTableSelect() + ex.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object[] rechercherPage(ClassMAPTable e, String[] colInt, String[] valInt, int numPage, String apresWhere, int nbPage) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            Object[] objectArray = CGenUtil.rechercherPage(e, colInt, valInt, numPage, apresWhere, c, nbPage);
            return objectArray;
        }
        return null;
    }

    public static ResultatEtSomme rechercherCritere(ClassMAPTable e, String apresWhere, Connection c) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        try {
            ResultatEtSomme rs;
            String comande = "select * from " + e.getNomTableSelect() + " where " + apresWhere;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche pagine de la table " + e.getNomTableSelect() + ex.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static ResultatEtSomme rechercherPage(ClassMAPTable e, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, Connection c) throws Exception {
        return CGenUtil.rechercherPage(e, colInt, valInt, numPage, apresWhere, nomColSomme, c, 0);
    }

    public static ResultatEtSomme rechercherPCritere(ClassMAPTable e, String apresWhere, Connection c) throws Exception {
        return CGenUtil.rechercherCritere(e, apresWhere, c);
    }

    public static ResultatEtSomme rechercherPageGroupe(ClassMAPTable e, String[] groupe, String[] sommeGroupe, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, String ordre, Connection c) throws Exception {
        return CGenUtil.rechercherPageGroupe(e, groupe, sommeGroupe, colInt, valInt, numPage, apresWhere, nomColSomme, ordre, c, 0);
    }

    public static ResultatEtSomme rechercherPageGroupe(ClassMAPTable e, String[] groupe, String[] sommeGroupe, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, String ordre, Connection c, int nppa) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        boolean ouvertInterne = false;
        try {
            ResultatEtSomme rs;
            e.setMode("groupe");
            if (c == null) {
                c = new UtilDB().GetConn();
                ouvertInterne = true;
            }
            int npp = nppa <= 0 ? Parametre.getNbParPage() : nppa;
            if (!(apresWhere != null && apresWhere.compareToIgnoreCase("") != 0 || ordre != null && ordre.compareToIgnoreCase("") != 0)) {
                boolean test = false;
                String groupeInit = "";
                for (String objet : groupe) {
                    if (objet.equalsIgnoreCase(e.getAttributIDName())) {
                        test = true;
                    }
                    groupeInit = objet;
                }
                String colOrdre = test ? e.getAttributIDName() : groupeInit;
                String ordre2 = "DESC";
                ordre = " order by " + colOrdre + " " + ordre2;
            }
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String selectInterne = "select " + CGenUtil.getListeChampGroupeSomme(groupe, sommeGroupe) + ", COUNT(*) as nombrepargroupe from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhere(e, c) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere + " group by " + CGenUtil.getListeChampGroupeSomme(groupe, null) + " " + ordre;
            String comande = "select * from (select " + CGenUtil.getListeChampGroupeSimple(groupe, sommeGroupe) + ",nombrepargroupe,rowNum as r from (" + selectInterne + ")) where r between " + indiceInf + " and " + indiceSup;
            if (UtilDB.properties.getProperty("apj.debug") != null && UtilDB.properties.getProperty("apj.debug").equalsIgnoreCase("true")) {
                System.out.println("Requete select rechercher Page Groupe : " + comande);
            }
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombreGroupe(selectInterne, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche groupee " + ex.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
            if (ouvertInterne && c != null) {
                c.close();
            }
        }
    }

    public static ResultatEtSomme rechercherPageGroupe(ClassMAPTable e, String req, String[] groupe, String[] sommeGroupe, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, String ordre, Connection c, int nppa) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        boolean ouvertInterne = false;
        try {
            ResultatEtSomme rs;
            e.setMode("groupe");
            if (c == null) {
                c = new UtilDB().GetConn();
                ouvertInterne = true;
            }
            int npp = nppa <= 0 ? Parametre.getNbParPage() : nppa;
            if (!(apresWhere != null && apresWhere.compareToIgnoreCase("") != 0 || ordre != null && ordre.compareToIgnoreCase("") != 0)) {
                boolean test = false;
                String groupeInit = "";
                for (String objet : groupe) {
                    if (objet.equalsIgnoreCase(e.getAttributIDName())) {
                        test = true;
                    }
                    groupeInit = objet;
                }
                String colOrdre = test ? e.getAttributIDName() : groupeInit;
                String ordre2 = "DESC";
                ordre = " order by " + colOrdre + " " + ordre2;
            }
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String selectInterne = "select " + CGenUtil.getListeChampGroupeSomme(groupe, sommeGroupe) + ", COUNT(*) as nombrepargroupe from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhere(e, c) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere + " group by " + CGenUtil.getListeChampGroupeSomme(groupe, null) + " " + ordre;
            String comande = "select * from (select " + CGenUtil.getListeChampGroupeSimple(groupe, sommeGroupe) + ",nombrepargroupe,rowNum as r from (" + selectInterne + ")) where r between " + indiceInf + " and " + indiceSup;
            if (req != null && !req.equalsIgnoreCase("")) {
                comande = req;
            }
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombreGroupe(selectInterne, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche groupee " + ex.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
            if (ouvertInterne && c != null) {
                c.close();
            }
        }
    }

    @Deprecated
    public static ResultatEtSomme rechercherPageGroupeMoyenneCompte(ClassMAPTable e, String[] groupe, String[] sommeGroupe, String[] moygroupe, String[] cptgroupe, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, String ordre, Connection c, int nppa) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        boolean ouvertInterne = false;
        try {
            ResultatEtSomme rs;
            if (c == null) {
                c = new UtilDB().GetConn();
                ouvertInterne = true;
            }
            int npp = nppa <= 0 ? Parametre.getNbParPage() : nppa;
            if (!(apresWhere != null && apresWhere.compareToIgnoreCase("") != 0 || ordre != null && ordre.compareToIgnoreCase("") != 0)) {
                String colOrdre = e.getAttributIDName();
                String ordre2 = "DESC";
                apresWhere = " order by " + colOrdre + " " + ordre2;
            }
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String selectInterne = "select " + CGenUtil.getListeChampGroupeSomme(groupe, sommeGroupe) + " from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhere(e, c) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere + " group by " + CGenUtil.getListeChampGroupeSomme(groupe, null) + " " + ordre;
            String comande = "select * from (select " + CGenUtil.getListeChampGroupeSimple(groupe, sommeGroupe) + ",rowNum as r from (" + selectInterne + ")) where r between " + indiceInf + " and " + indiceSup;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombreGroupe(selectInterne, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche groupee " + ex.getMessage());
        }
        finally {
            if (ouvertInterne && c != null) {
                c.close();
            }
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static void setValChamp(ClassMAPTable e, Field nomChamp, Object valeur) throws Exception {
        String nomMethode = "set" + Utilitaire.convertDebutMajuscule(nomChamp.getName());
        Class[] paramT = new Class[]{nomChamp.getType()};
        Object[] args = new Object[]{valeur};
        try {
            if (nomChamp.getType().getName().equalsIgnoreCase("int")) {
                args[0] = new Integer(new Double(Utilitaire.stringToDouble(valeur.toString())).intValue());
            }
            if (valeur != null) {
                Object object = e.getClass().getMethod(nomMethode, paramT).invoke(e, args);
            }
        }
        catch (NoSuchMethodException ex) {
            Method[] list = e.getClass().getMethods();
            for (int i = 0; i < list.length; ++i) {
                if (list[i].getName().compareToIgnoreCase(nomMethode) != 0) continue;
                list[i].invoke(e, args);
                return;
            }
        }
        catch (InvocationTargetException x) {
            throw new Exception(x.getTargetException().getMessage());
        }
    }

    public static String getValeurInsert(ClassMAPTable e, String nomChamp) throws Exception {
        Object o = CGenUtil.getMethod(e, nomChamp).invoke(e, new Object[0]);
        String retour = null;
        Object oret = null;
        if (o != null) {
            oret = CGenUtil.getMethod(e, nomChamp).invoke(e, new Object[0]);
        }
        if (oret != null) {
            retour = oret.toString();
        }
        if (retour != null && retour.isEmpty()) {
            retour = null;
        }
        return retour;
    }

    public static Method getMethod(ClassMAPTable e, String nomChamp) throws Exception {
        String nomMethode = "get" + Utilitaire.convertDebutMajuscule(nomChamp);
        Method ret = null;
        try {
            ret = e.getClass().getDeclaredMethod(nomMethode, null);
            return ret;
        }
        catch (NoSuchMethodException ex) {
            Method[] list = ListeColonneTable.getMethodListeHeritage(e);
            for (int i = 0; i < list.length; ++i) {
                if (list[i].getName().compareToIgnoreCase(nomMethode) != 0) continue;
                ret = list[i];
                return ret;
            }
            return null;
        }
    }

    public static Object getValeurFieldByMethod(ClassMAPTable e, String nomChamp) throws Exception {
        String nomMethode = "get" + Utilitaire.convertDebutMajuscule(nomChamp);
        Object ret = null;
        try {
            ret = e.getClass().getMethod(nomMethode, null).invoke(e, null);
            return ret;
        }
        catch (NoSuchMethodException ex) {
            Method[] list = e.getClass().getMethods();
            for (int i = 0; i < list.length; ++i) {
                if (list[i].getName().compareToIgnoreCase(nomMethode) != 0) continue;
                ret = list[i].invoke(e, null);
                return ret;
            }
            return ret;
        }
    }

    public static Object getMethodDynamiqueAvecConn(ClassMAPTable e, String user, String nomMethode, Connection c) throws Exception {
        Object ret = null;
        Class[] typ = new Class[]{String.class, Connection.class};
        Object[] args = new Object[]{user, c};
        Method m = null;
        try {
            m = e.getClass().getMethod(nomMethode, typ);
            ret = m.invoke(e, args);
            return ret;
        }
        catch (NoSuchMethodException ex) {
            Method[] list = e.getClass().getMethods();
            for (int i = 0; i < list.length; ++i) {
                if (list[i].getName().compareToIgnoreCase(nomMethode) != 0) continue;
                m = list[i];
                break;
            }
            if ((ret = m.invoke(e, args)) == null) {
                throw new Exception("Methode non trouve " + nomMethode);
            }
            return ret;
        }
    }

    public static Object getValeurFieldByMethod(ClassMAPTable e, Field g) throws Exception {
        return CGenUtil.getValeurFieldByMethod(e, g.getName());
    }

    public static String makeWhereIntervalle(String[] numChamp, String[] val) throws Exception {
        String retour = "";
        if (numChamp == null) {
            return "";
        }
        for (int i = 0; i < numChamp.length; ++i) {
            String champ = numChamp[i];
            String vMin = val != null && val.length > 2 * i ? val[2 * i] : null;
            String vMax = val != null && val.length > 2 * i + 1 ? val[2 * i + 1] : null;
            boolean hasMin = vMin != null && !vMin.trim().isEmpty();
            boolean hasMax = vMax != null && !vMax.trim().isEmpty();
            String champUpper = champ == null ? "" : champ.toUpperCase();
            boolean isChampDate = champUpper.contains("DATE") || champUpper.contains("DATY");
            boolean isChampHeure = champUpper.contains("HEURE") || champUpper.contains("TIME") || champUpper.contains("HORA") || hasMin && vMin.contains(":") || hasMax && vMax.contains(":");
            boolean isChampNombre = champUpper.contains("NOMBRE") || champUpper.contains("MONTANT") || champUpper.contains("CHIFFRE") || champUpper.contains("NOTE") || hasMin && CGenUtil.isStrictSqlNumber(CGenUtil.normalizeNombreSql(vMin)) || hasMax && CGenUtil.isStrictSqlNumber(CGenUtil.normalizeNombreSql(vMax));
            String vMinSql = vMin;
            String vMaxSql = vMax;
            boolean isValidMin = true;
            boolean isValidMax = true;
            if (hasMin) {
                if (isChampHeure) {
                    try {
                        vMinSql = Utilitaire.checkHeureFormat(vMin.trim());
                        isValidMin = true;
                    }
                    catch (Exception ex) {
                        isValidMin = false;
                    }
                } else if (isChampDate) {
                    isValidMin = Utilitaire.isValidDate(vMin.trim(), "dd/MM/yyyy");
                } else if (isChampNombre) {
                    vMinSql = CGenUtil.normalizeNombreSql(vMin);
                    isValidMin = CGenUtil.isStrictSqlNumber(vMinSql);
                } else {
                    isValidMin = Utilitaire.isStringNumeric(vMin.trim());
                }
            }
            if (hasMax) {
                if (isChampHeure) {
                    try {
                        vMaxSql = Utilitaire.checkHeureFormat(vMax.trim());
                        isValidMax = true;
                    }
                    catch (Exception ex) {
                        isValidMax = false;
                    }
                } else if (isChampDate) {
                    isValidMax = Utilitaire.isValidDate(vMax.trim(), "dd/MM/yyyy");
                } else if (isChampNombre) {
                    vMaxSql = CGenUtil.normalizeNombreSql(vMax);
                    isValidMax = CGenUtil.isStrictSqlNumber(vMaxSql);
                } else {
                    isValidMax = Utilitaire.isStringNumeric(vMax.trim());
                }
            }
            if (hasMin && !isValidMin || hasMax && !isValidMax) {
                return " AND 1>2";
            }
            String champSql = champ;
            if (isChampHeure && (isChampDate || champUpper.contains("TIMESTAMP"))) {
                champSql = "to_char(" + champ + ",'HH24:MI:SS')";
            }
            if (hasMin) {
                retour = isChampNombre ? retour + " AND " + champSql + " >= " + vMinSql : retour + " AND " + champSql + " >= '" + vMinSql + "'";
            }
            if (!hasMax) continue;
            retour = isChampNombre ? retour + " AND " + champSql + " <= " + vMaxSql : retour + " AND " + champSql + " <= '" + vMaxSql + "'";
        }
        return retour;
    }

    public static String makeWhereIntervalle(ClassMAPTable e, String[] numChamp, String[] val) throws Exception {
        String retour = "";
        if (numChamp == null) {
            return "";
        }
        for (int i = 0; i < numChamp.length; ++i) {
            String valSup;
            String valInf;
            boolean estSupValide;
            Champ c = ListeColonneTable.getChamp(e, numChamp[i]);
            boolean estInfValide = val[2 * i] != "";
            boolean bl = estSupValide = val[2 * i + 1] != "";
            if (c.getTypeColonne().compareToIgnoreCase("DATE") == 0 || c.getTypeColonne().compareToIgnoreCase("DATETIME") == 0) {
                valInf = "TO_DATE('" + val[2 * i] + "','DD-MM-YYYY')";
                valSup = "TO_DATE('" + val[2 * i + 1] + "','DD-MM-YYYY')";
            } else {
                valInf = "'" + val[2 * i] + "'";
                valSup = "'" + val[2 * i + 1] + "'";
            }
            if (estInfValide && !estSupValide) {
                retour = retour + " AND " + numChamp[i] + " >= " + valInf;
            }
            if (!estInfValide && estSupValide) {
                retour = retour + " AND " + numChamp[i] + " <= " + valSup;
            }
            if (!estInfValide || !estSupValide) continue;
            retour = retour + " AND " + numChamp[i] + " >= " + valInf + " AND " + numChamp[i] + " <= " + valSup;
        }
        return retour;
    }

    private static String normalizeNombreSql(String nombre) {
        if (nombre == null) {
            return null;
        }
        String t = nombre.trim();
        t = t.replace('\u00a0', ' ');
        if ((t = t.replace(" ", "")).indexOf(44) >= 0 && t.indexOf(46) >= 0) {
            t = t.replace(".", "");
            t = t.replace(',', '.');
        } else {
            t = t.replace(',', '.');
        }
        return t;
    }

    private static boolean isStrictSqlNumber(String s) {
        if (s == null) {
            return false;
        }
        String t = s.trim();
        if (t.isEmpty()) {
            return false;
        }
        return t.matches("[-+]?\\d+(\\.\\d+)?");
    }

    public static Field getField(ClassMAPTable ec, String nomChamp) throws ClassMAPTableException {
        try {
            Field[] fieldlist = ListeColonneTable.getFieldListeHeritage(ec);
            for (int i = 0; i < fieldlist.length; ++i) {
                if (fieldlist[i].getName().compareToIgnoreCase(nomChamp) != 0) continue;
                return fieldlist[i];
            }
            return null;
        }
        catch (Exception e) {
            throw new ClassMAPTableException(e.getMessage());
        }
    }

    public static Field[] getFieldList(ClassMAPTable ec) throws ClassMAPTableException {
        try {
            Class<?> cls = ec.getClass();
            Field[] retour = new Field[ec.getNombreChamp()];
            Field[] fieldlist = cls.getDeclaredFields();
            for (int i = 0; i < fieldlist.length; ++i) {
                retour[i] = fieldlist[i];
            }
            return retour;
        }
        catch (Exception e) {
            throw new ClassMAPTableException(e.getMessage());
        }
    }

    public static String makeOr(String[] ch, String[][] valOr) throws Exception {
        String retour = " ";
        if (ch == null || ch.length == 0 || valOr == null || valOr.length == 0) {
            return " 1<2";
        }
        String or = "";
        for (int k = 0; k < ch.length; ++k) {
            for (int j = 0; j < valOr[k].length; ++j) {
                if (j > 0) {
                    or = " or ";
                }
                retour = retour + or + " upper(" + ch[k] + ") like upper('" + Utilitaire.replaceChar(valOr[k][j]) + "')";
            }
        }
        return retour;
    }

    private static String makeConditionRechercheExacte(String nomAttribut, Object valeur) {
        String valeurSql = valeur.toString().replace("'", "''");
        return " and upper(" + nomAttribut + ") like upper('" + valeurSql + "')";
    }

    public static String makeWhere(ClassMAPTable e) throws Exception {
        String retour = "1<2";
        Champ[] ch = ListeColonneTable.getFromListe(e, null);
        for (int k = 0; k < ch.length; ++k) {
            boolean testNombre;
            Object valeurk = CGenUtil.getValeurFieldByMethod(e, ch[k].getNomClasse().getName());
            boolean testDaty = ch[k].getNomClasse().getType().toString() == "System.DateTime";
            boolean bl = testNombre = ch[k].getNomClasse().getType().getName().compareToIgnoreCase("double") == 0 || ch[k].getNomClasse().getType().getName().compareToIgnoreCase("int") == 0 || ch[k].getNomClasse().getType().getName().compareToIgnoreCase("short") == 0;
            if (testNombre || testDaty || valeurk == null || valeurk.toString().compareToIgnoreCase("") == 0 || valeurk.toString().compareToIgnoreCase("%") == 0) continue;
            retour = e.isRecherchePrecis(ch[k].getNomClasse().getName()) ? retour + CGenUtil.makeConditionRechercheExacte(ch[k].getNomClasse().getName(), valeurk) : (ch[k].getNomColonne().compareToIgnoreCase("id") == 0 ? retour + " and " + ch[k].getNomClasse().getName() + " like '%" + Utilitaire.replaceChar(valeurk.toString()) + "%'" : retour + " and upper(" + ch[k].getNomClasse().getName() + ") like upper('%" + Utilitaire.replaceChar(valeurk.toString()) + "%')");
        }
        return retour;
    }

    public static String makeWhereSelectMultiple(ClassMAPTable e) throws Exception {
        StringBuilder where = new StringBuilder(" and 1 < 2");
        Map<String, String[]> params = e.getParamsMultiple();
        if (params == null || params.isEmpty()) {
            return where.toString();
        }
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String colonne = entry.getKey();
            String[] valeurs = entry.getValue();
            if (colonne == null || colonne.trim().isEmpty() || valeurs == null || valeurs.length == 0) continue;
            StringBuilder inClause = new StringBuilder();
            for (String val : valeurs) {
                if (val == null || val.trim().isEmpty()) continue;
                inClause.append("UPPER('").append(val.replace("'", "''")).append("'), ");
            }
            if (inClause.length() <= 0) continue;
            inClause.setLength(inClause.length() - 2);
            where.append(" AND UPPER(").append(colonne).append(") IN (").append((CharSequence)inClause).append(")");
        }
        return where.toString();
    }

    public static String makeWhereUpper(ClassMAPTable e) throws Exception {
        String retour = "1<2";
        Champ[] ch = ListeColonneTable.getFromListe(e, null);
        for (int k = 0; k < ch.length; ++k) {
            boolean testNombre;
            Object valeurk = CGenUtil.getValeurFieldByMethod(e, ch[k].getNomClasse().getName());
            boolean testDaty = ch[k].getNomClasse().getType().toString() == "System.DateTime";
            boolean bl = testNombre = ch[k].getNomClasse().getType().getName().compareToIgnoreCase("double") == 0 || ch[k].getNomClasse().getType().getName().compareToIgnoreCase("int") == 0 || ch[k].getNomClasse().getType().getName().compareToIgnoreCase("short") == 0;
            if (testNombre || testDaty || valeurk == null || valeurk.toString().compareToIgnoreCase("") == 0 || valeurk.toString().compareToIgnoreCase("%") == 0) continue;
            retour = e.isRecherchePrecis(ch[k].getNomClasse().getName()) ? retour + CGenUtil.makeConditionRechercheExacte(ch[k].getNomClasse().getName(), valeurk) : (ch[k].getNomColonne().compareToIgnoreCase("id") == 0 ? retour + " and " + ch[k].getNomClasse().getName() + " like '%" + Utilitaire.replaceChar(valeurk.toString()) + "%'" : retour + " and " + ch[k].getNomClasse().getName() + " like '%" + Utilitaire.replaceChar(valeurk.toString()) + "%'");
        }
        return retour;
    }

    public static String makeWhere(ClassMAPTable e, Connection con) throws Exception {
        try {
            String retour = "1<2";
            Champ[] ch = ListeColonneTable.getFromListe(e, con);
            for (int k = 0; k < ch.length; ++k) {
                boolean testNombre;
                Object valeurk = CGenUtil.getValeurFieldByMethod(e, ch[k].getNomClasse().getName());
                boolean testDaty = ch[k].getNomClasse().getType().toString() == "System.DateTime";
                boolean bl = testNombre = ch[k].getNomClasse().getType().getName().compareToIgnoreCase("double") == 0 || ch[k].getNomClasse().getType().getName().compareToIgnoreCase("int") == 0 || ch[k].getNomClasse().getType().getName().compareToIgnoreCase("short") == 0;
                if (testNombre || testDaty || valeurk == null || valeurk.toString().compareToIgnoreCase("") == 0 || valeurk.toString().compareToIgnoreCase("%") == 0) continue;
                if (e.isRecherchePrecis(ch[k].getNomClasse().getName())) {
                    retour = retour + CGenUtil.makeConditionRechercheExacte(ch[k].getNomClasse().getName(), valeurk);
                    continue;
                }
                if (e.getAttributIDName() != null && e.getAttributIDName().compareToIgnoreCase("") != 0 && ch[k].getNomColonne().compareToIgnoreCase(e.getAttributIDName()) == 0) {
                    retour = retour + " and " + ch[k].getNomClasse().getName() + " like '" + Utilitaire.replaceChar(valeurk.toString()) + "'";
                    continue;
                }
                String val = Utilitaire.replaceChar(valeurk.toString()).replace("'", "''");
                retour = retour + " and upper(" + ch[k].getNomClasse().getName() + ") like upper('%" + val + "%')";
            }
            return retour;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur makewhere : " + ex.getMessage());
        }
    }

    public static String makeWherePrecis(ClassMAPTable e, Connection con) throws Exception {
        try {
            String retour = "1<2";
            Champ[] ch = ListeColonneTable.getFromListe(e, con);
            for (int k = 0; k < ch.length; ++k) {
                boolean testNombre;
                Object valeurk = CGenUtil.getValeurFieldByMethod(e, ch[k].getNomClasse().getName());
                boolean testDaty = ch[k].getNomClasse().getType().toString() == "System.DateTime";
                boolean bl = testNombre = ch[k].getNomClasse().getType().getName().compareToIgnoreCase("double") == 0 || ch[k].getNomClasse().getType().getName().compareToIgnoreCase("int") == 0 || ch[k].getNomClasse().getType().getName().compareToIgnoreCase("short") == 0;
                if (testNombre || testDaty || valeurk == null || valeurk.toString().compareToIgnoreCase("") == 0 || valeurk.toString().compareToIgnoreCase("%") == 0) continue;
                retour = ch[k].getNomColonne().compareToIgnoreCase("id") == 0 ? retour + " and " + ch[k].getNomClasse().getName() + " like '" + Utilitaire.replaceChar(valeurk.toString()) + "'" : retour + " and " + ch[k].getNomClasse().getName() + " like '" + Utilitaire.replaceChar(valeurk.toString()) + "'";
            }
            return retour;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur makewhere : " + ex.getMessage());
        }
    }

    public static Object[] transformeDataReader(ResultSet dr, ClassMAPTable e, Connection c, int iInf, int iSup) throws Exception {
        Vector<ClassMAPTable> retour = new Vector<ClassMAPTable>();
        String nomTable = e.getNomTableSelect();
        Champ[] listeChamp = ListeColonneTable.getFromListe(e, c);
        int ii = 0;
        while (dr.next()) {
            if (ii >= iInf && ii <= iSup) {
                Object o = Class.forName(e.getClassName()).newInstance();
                ClassMAPTable ex = (ClassMAPTable)o;
                ex.setMode("select");
                for (int i = 0; i < listeChamp.length; ++i) {
                    try {
                        int n = dr.findColumn(listeChamp[i].getNomColonne());
                    }
                    catch (SQLException sq) {
                        continue;
                    }
                    if (listeChamp[i].getTypeColonne().compareToIgnoreCase("NUMBER") == 0 && listeChamp[i].getPrecision() == 0) {
                        try {
                            CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), new Integer(dr.getInt(listeChamp[i].getNomColonne())));
                        }
                        catch (Exception sq) {
                            CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), new Double(dr.getDouble(listeChamp[i].getNomColonne())));
                        }
                    }
                    if (listeChamp[i].getTypeColonne().compareToIgnoreCase("NUMBER") == 0 && listeChamp[i].getPrecision() > 0 || listeChamp[i].getTypeColonne().compareToIgnoreCase("FLOAT") == 0) {
                        CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), new Double(dr.getDouble(listeChamp[i].getNomColonne())));
                    }
                    if (listeChamp[i].getTypeColonne().compareToIgnoreCase("VARCHAR2") == 0 || listeChamp[i].getTypeColonne().compareToIgnoreCase("CHAR") == 0) {
                        CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), dr.getString(listeChamp[i].getNomColonne()));
                    }
                    if (listeChamp[i].getTypeColonne().compareToIgnoreCase("DATE") != 0) continue;
                    CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), dr.getDate(listeChamp[i].getNomColonne()));
                }
                ex.setNomTable(e.getNomTableSelect());
                retour.add(ex);
            }
            ++ii;
        }
        Object[] temp = (Object[])Array.newInstance(((ClassMAPTable)e.getClass().newInstance()).getClass(), retour.size());
        retour.copyInto(temp);
        return temp;
    }

    public static HashMap<String, Vector> transformeDataReader2D(ResultSet dr, ClassMAPTable e, String attr2D, Connection c) throws Exception {
        Vector retour = new Vector();
        Vector retour2D = new Vector();
        String nomTable = e.getNomTableSelect();
        Champ[] listeChamp = ListeColonneTable.getFromListe(e, c);
        boolean compteur = false;
        HashMap<String, Vector> map = new HashMap<String, Vector>();
        while (dr.next()) {
            Object o = Class.forName(e.getClassName()).newInstance();
            ClassMAPTable ex = (ClassMAPTable)o;
            ex.setMode("select");
            for (int i = 0; i < listeChamp.length; ++i) {
                try {
                    int n = dr.findColumn(listeChamp[i].getNomColonne());
                }
                catch (SQLException sq) {
                    continue;
                }
                if (listeChamp[i].getTypeColonne().compareToIgnoreCase("NUMBER") == 0 && listeChamp[i].getPrecision() == 0 && listeChamp[i].getTypeJava().compareToIgnoreCase("java.lang.String") != 0) {
                    try {
                        CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), new Integer(dr.getInt(listeChamp[i].getNomColonne())));
                    }
                    catch (Exception sq) {
                        CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), new Double(dr.getDouble(listeChamp[i].getNomColonne())));
                    }
                }
                if ((listeChamp[i].getTypeColonne().compareToIgnoreCase("NUMBER") == 0 && listeChamp[i].getPrecision() > 0 || listeChamp[i].getTypeColonne().compareToIgnoreCase("FLOAT") == 0) && listeChamp[i].getTypeJava().compareToIgnoreCase("java.lang.String") != 0) {
                    CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), new Double(dr.getDouble(listeChamp[i].getNomColonne())));
                }
                if (listeChamp[i].getTypeColonne().compareToIgnoreCase("VARCHAR2") == 0 || listeChamp[i].getTypeColonne().compareToIgnoreCase("CHAR") == 0) {
                    CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), dr.getString(listeChamp[i].getNomColonne()));
                }
                if (listeChamp[i].getTypeColonne().compareToIgnoreCase("DATE") == 0) {
                    CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), dr.getDate(listeChamp[i].getNomColonne()));
                }
                if (!listeChamp[i].getTypeColonne().contains("Timestamp")) continue;
                CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), dr.getTimestamp(listeChamp[i].getNomColonne()));
            }
            ex.setNomTable(e.getNomTableSelect());
            String valeur = dr.getObject(attr2D).toString();
            if (e.getFieldByName(attr2D).getType().getName().contains("Date")) {
                valeur = Utilitaire.formatterDatySql(dr.getDate(attr2D));
            }
            if (map.containsKey(valeur)) {
                map.get(valeur).add(ex);
                continue;
            }
            Vector<ClassMAPTable> v = new Vector<ClassMAPTable>();
            v.add(ex);
            map.put(valeur, v);
        }
        return map;
    }

    public static Object[] transformeDataReader(ResultSet dr, ClassMAPTable e, Connection c) throws Exception {
        Vector<ClassMAPTable> retour = new Vector<ClassMAPTable>();
        String nomTable = e.getNomTableSelect();
        Champ[] listeChamp = ListeColonneTable.getFromListe(e, c);
        while (dr.next()) {
            Object o = Class.forName(e.getClassName()).newInstance();
            ClassMAPTable ex = (ClassMAPTable)o;
            ex.setMode("select");
            for (int i = 0; i < listeChamp.length; ++i) {
                try {
                    int n = dr.findColumn(listeChamp[i].getNomColonne());
                }
                catch (SQLException sq) {
                    continue;
                }
                if (listeChamp[i].getTypeColonne().compareToIgnoreCase("NUMBER") == 0 && listeChamp[i].getPrecision() == 0 && listeChamp[i].getTypeJava().compareToIgnoreCase("java.lang.String") != 0) {
                    try {
                        CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), new Integer(dr.getInt(listeChamp[i].getNomColonne())));
                    }
                    catch (Exception sq) {
                        CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), new Double(dr.getDouble(listeChamp[i].getNomColonne())));
                    }
                }
                if ((listeChamp[i].getTypeColonne().compareToIgnoreCase("NUMBER") == 0 && listeChamp[i].getPrecision() > 0 || listeChamp[i].getTypeColonne().compareToIgnoreCase("FLOAT") == 0 || listeChamp[i].getTypeColonne().compareToIgnoreCase("DECIMAL") == 0 || listeChamp[i].getTypeColonne().compareToIgnoreCase("NUMERIC") == 0) && listeChamp[i].getTypeJava().compareToIgnoreCase("java.lang.String") != 0) {
                    CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), new Double(dr.getDouble(listeChamp[i].getNomColonne())));
                }
                if (listeChamp[i].getTypeColonne().compareToIgnoreCase("VARCHAR2") == 0 || listeChamp[i].getTypeColonne().compareToIgnoreCase("CHAR") == 0) {
                    CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), dr.getString(listeChamp[i].getNomColonne()));
                }
                if (listeChamp[i].getTypeColonne().compareToIgnoreCase("DATE") == 0) {
                    CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), dr.getDate(listeChamp[i].getNomColonne()));
                }
                if (!listeChamp[i].getTypeColonne().contains("Timestamp")) continue;
                CGenUtil.setValChamp(ex, listeChamp[i].getNomClasse(), dr.getTimestamp(listeChamp[i].getNomColonne()));
            }
            if (e.nomTableSelect != null && e.nomTableSelect.compareToIgnoreCase("") != 0) {
                ex.setNomTableSelect(e.getNomTableSelect());
            } else {
                ex.setNomTable(e.getNomTable());
            }
            retour.add(ex);
        }
        Object[] temp = (Object[])Array.newInstance(((ClassMAPTable)e.getClass().newInstance()).getClass(), retour.size());
        retour.copyInto(temp);
        return temp;
    }

    public static ClassMAPTable[] saveWithoutPkRangHisto(ClassMAPTable e, ClassMAPTable histo, Connection c) throws Exception {
        Connection cnx = null;
        ClassMAPTable[] retour = new ClassMAPTable[2];
        boolean estOuvert = false;
        try {
            if (c == null) {
                cnx = new UtilDB().GetConn();
                cnx.setAutoCommit(false);
                c = cnx;
                estOuvert = true;
            }
            retour[0] = CGenUtil.saveWithoutPkRang(e, c);
            retour[1] = CGenUtil.saveWithoutPkRang(histo, c);
            if (cnx != null && estOuvert) {
                cnx.commit();
            }
            ClassMAPTable[] classMAPTableArray = retour;
            return classMAPTableArray;
        }
        catch (Exception ex) {
            if (cnx != null && estOuvert) {
                cnx.rollback();
            }
            throw new Exception("ERREUR INSERT_TABLEtttttttttttttttttt " + ex.getMessage());
        }
        finally {
            if (cnx != null && estOuvert) {
                cnx.close();
            }
        }
    }

    public static ClassMAPTable[] saveWithoutPkRangHisto(ClassMAPTable e, String refUser, Connection c) throws Exception {
        MapHistorique histo = new MapHistorique(e.getNomTable(), "insert", refUser, e.getTuppleID());
        return CGenUtil.saveWithoutPkRangHisto(e, histo, c);
    }

    public static ClassMAPTable saveWithoutPkRang(ClassMAPTable e, Connection c) throws Exception {
        Connection cnx = null;
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = cnx = new UtilDB().GetConn();
                estOuvert = true;
            }
            Champ[] listeC = ListeColonneTable.getFromListe(e, c);
            String comande = "insert into " + e.getNomTable() + " values (";
            comande = comande + "'" + CGenUtil.getValeurInsert(e, listeC[0].getNomClasse().getName()) + "'";
            for (int i = 1; i < listeC.length; ++i) {
                comande = comande + ",";
                comande = CGenUtil.getValeurInsert(e, listeC[i].getNomClasse().getName()) == null ? comande + " null " : comande + "'" + CGenUtil.getValeurInsert(e, listeC[i].getNomClasse().getName()) + "'";
            }
            comande = comande + ")";
            Statement st = c.createStatement();
            st.executeUpdate(comande);
            ClassMAPTable classMAPTable = e;
            return classMAPTable;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            if (cnx != null && estOuvert) {
                cnx.close();
            }
        }
    }

    public static ResultatEtSomme rechercherGroupe(ClassMAPTable e, String[] groupe, String[] sommeGroupe, String[] colInt, String[] valInt, String apresWhere, String[] nomColSomme, String ordre, Connection c) throws Exception {
        e.setMode("select");
        ResultSet dr = null;
        Statement st = null;
        boolean ouvertInterne = false;
        try {
            ResultatEtSomme rs;
            if (c == null) {
                c = new UtilDB().GetConn();
                ouvertInterne = true;
            }
            String selectInterne = "select " + CGenUtil.getListeChampGroupeSomme(groupe, sommeGroupe) + " from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhere(e, c) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere + " group by " + CGenUtil.getListeChampGroupeSomme(groupe, null) + " " + ordre;
            String comande = "select * from (select " + CGenUtil.getListeChampGroupeSimple(groupe, sommeGroupe) + ",rowNum as r from (" + selectInterne + "))";
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombreGroupe(selectInterne, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche groupee " + ex.getMessage());
        }
        finally {
            if (ouvertInterne && c != null) {
                c.close();
            }
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static ResultatEtSomme rechercherPageGroupeM(ClassMAPTable e, String[] groupe, String[] sommeGroupe, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, String ordre, Connection c, int nppa, String count) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        boolean ouvertInterne = false;
        try {
            ResultatEtSomme rs;
            if (c == null) {
                c = new UtilDB().GetConn();
                ouvertInterne = true;
            }
            int npp = nppa <= 0 ? Parametre.getNbParPage() : nppa;
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String apresWR = "";
            String orderby = "";
            char[] ch = new char[]{' '};
            String[] tabstr = Utilitaire.split(apresWhere, ch);
            boolean tmp = false;
            for (int i = 0; i < tabstr.length; ++i) {
                if (tabstr[i].trim().compareToIgnoreCase("order") != 0 && !tmp) {
                    apresWR = apresWR + " " + tabstr[i].trim();
                    continue;
                }
                tmp = true;
                orderby = orderby + " " + tabstr[i].trim();
            }
            String selectInterne = "select " + CGenUtil.getListeChampGroupeSomme(groupe, sommeGroupe) + ", " + CGenUtil.getSelectNombreGroupe(e, groupe, count) + " as nombrepargroupe from " + e.getNomTableSelect() + " p where " + CGenUtil.makeWhereOr(e) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere + " group by " + CGenUtil.getListeChampGroupeSomme(groupe, null) + " " + orderby;
            String comande = "select * from (select " + CGenUtil.getListeChampGroupeSimple(groupe, sommeGroupe) + ",nombrepargroupe,rowNum as r from (" + selectInterne + ")) where r between " + indiceInf + " and " + indiceSup;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombreGroupe(selectInterne, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche groupee " + ex.getMessage());
        }
        finally {
            if (ouvertInterne && c != null) {
                c.close();
            }
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static String getSelectNombreGroupe(ClassMAPTable e, String[] groupe, String groupby) {
        String ret = "(select count(count(*)) from " + e.getNomTableSelect() + " where 1< 2";
        for (String gr : groupe) {
            ret = ret + " and upper(" + gr + ") like (p." + gr + ")";
        }
        ret = ret + " group by " + groupby + ")";
        return ret;
    }

    public static ResultatEtSomme rechercherPageGroupeM(ClassMAPTable e, String[] groupe, String[] sommeGroupe, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, String ordre, Connection c) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        boolean ouvertInterne = false;
        try {
            ResultatEtSomme rs;
            if (c == null) {
                c = new UtilDB().GetConn();
                ouvertInterne = true;
            }
            int npp = 1;
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String pagination = "";
            if (numPage >= 0) {
                pagination = "where r between " + indiceInf + " and " + indiceSup;
            }
            String apresWR = "";
            String orderby = "";
            char[] ch = new char[]{' '};
            String[] tabstr = Utilitaire.split(apresWhere, ch);
            boolean tmp = false;
            for (int i = 0; i < tabstr.length; ++i) {
                if (tabstr[i].trim().compareToIgnoreCase("order") != 0 && !tmp) {
                    apresWR = apresWR + " " + tabstr[i].trim();
                    continue;
                }
                tmp = true;
                orderby = orderby + " " + tabstr[i].trim();
            }
            String selectInterne = "select " + CGenUtil.getListeChampGroupeSomme(groupe, sommeGroupe) + ", COUNT(*) as nombrepargroupe from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhereOr(e) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere + " group by " + CGenUtil.getListeChampGroupeSomme(groupe, null) + " " + ordre;
            String comande = "select * from (select " + CGenUtil.getListeChampGroupeSimple(groupe, sommeGroupe) + ",nombrepargroupe,rowNum as r from (" + selectInterne + ")) where r between " + indiceInf + " and " + indiceSup;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombreGroupe(selectInterne, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche groupee " + ex.getMessage());
        }
        finally {
            if (ouvertInterne && c != null) {
                c.close();
            }
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static ResultatEtSomme rechercherPageGroupeM(ClassMAPTable e, String[] groupe, String[] sommeGroupe, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, String ordre, Connection c, int nppa) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        boolean ouvertInterne = false;
        String compteLigne = "count(*)";
        if (e.getNomTable().contains("PAIE_EDITION")) {
            compteLigne = "count(distinct idPersonnel)";
        }
        try {
            ResultatEtSomme rs;
            if (c == null) {
                c = new UtilDB().GetConn();
                ouvertInterne = true;
            }
            int npp = nppa <= 0 ? Parametre.getNbParPage() : nppa;
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String apresWR = "";
            String orderby = "";
            char[] ch = new char[]{' '};
            String[] tabstr = Utilitaire.split(apresWhere, ch);
            boolean tmp = false;
            for (int i = 0; i < tabstr.length; ++i) {
                if (tabstr[i].trim().compareToIgnoreCase("order") != 0 && !tmp) {
                    apresWR = apresWR + " " + tabstr[i].trim();
                    continue;
                }
                tmp = true;
                orderby = orderby + " " + tabstr[i].trim();
            }
            String selectInterne = "select " + CGenUtil.getListeChampGroupeSomme(groupe, sommeGroupe) + ", " + compteLigne + "as nombrepargroupe from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhereOr(e) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere + " group by " + CGenUtil.getListeChampGroupeSomme(groupe, null) + " " + orderby;
            String comande = "select * from (select " + CGenUtil.getListeChampGroupeSimple(groupe, sommeGroupe) + ",nombrepargroupe,rowNum as r from (" + selectInterne + ")) where r between " + indiceInf + " and " + indiceSup;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombreGroupe(selectInterne, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche groupee " + ex.getMessage());
        }
        finally {
            if (ouvertInterne && c != null) {
                c.close();
            }
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static String makeWhereOr(ClassMAPTable e) throws Exception {
        String retour = "1<2";
        Champ[] ch = ListeColonneTable.getFromListe(e, null);
        for (int k = 0; k < ch.length; ++k) {
            boolean testNombre;
            Object valeurk = CGenUtil.getValeurFieldByMethod(e, ch[k].getNomClasse().getName());
            boolean testDaty = ch[k].getNomClasse().getType().toString() == "System.DateTime";
            boolean bl = testNombre = ch[k].getNomClasse().getType().getName().compareToIgnoreCase("double") == 0 || ch[k].getNomClasse().getType().getName().compareToIgnoreCase("int") == 0 || ch[k].getNomClasse().getType().getName().compareToIgnoreCase("short") == 0;
            if (testNombre || testDaty || valeurk == null || valeurk.toString().compareToIgnoreCase("") == 0 || valeurk.toString().compareToIgnoreCase("%") == 0) continue;
            String temp = valeurk.toString();
            if (e.isRecherchePrecis(ch[k].getNomClasse().getName())) {
                retour = retour + CGenUtil.makeConditionRechercheExacte(ch[k].getNomClasse().getName(), valeurk);
                continue;
            }
            String concat = "like ('";
            String after = "')";
            if (temp.lastIndexOf(",") != -1) {
                concat = "in ('";
                after = "')";
            }
            retour = retour + " and upper(" + ch[k].getNomClasse().getName() + ") " + concat + "" + valeurk.toString().toUpperCase() + "" + after;
        }
        return retour;
    }

    public static String[] filtrerMotsCles(ClassMAPTable e, String mc) throws Exception {
        String[] motCles = e.getMotCles();
        Champ[] listeChamp = ListeColonneTable.getFromListe(e, null);
        String[] listeChampString = new String[listeChamp.length];
        for (int i = 0; i < listeChamp.length; ++i) {
            listeChampString[i] = listeChamp[i].getNomColonne();
        }
        return Utilitaire.intersectionIgnoreCasse(motCles, listeChampString);
    }

    public static String makeWhereMotsCles(ClassMAPTable e, String motCleValeur) throws Exception {
        String[] motCles = CGenUtil.filtrerMotsCles(e, motCleValeur);
        String whereClause = "";
        DateAutoComplete dateAutoComplete = new DateAutoComplete().parse(motCleValeur);
        if (dateAutoComplete.isDate()) {
            for (int i = 0; i < motCles.length; ++i) {
                if (DateAutoComplete.isDateColumn((String)motCles[i])) {
                    whereClause = dateAutoComplete.isRange() ? whereClause + motCles[i] + " BETWEEN TO_DATE('" + dateAutoComplete.getStartDate() + "', 'YYYY-MM-DD') AND TO_DATE('" + dateAutoComplete.getEndDate() + "', 'YYYY-MM-DD')" : whereClause + motCles[i] + " = TO_DATE('" + dateAutoComplete.getStartDate() + "', 'YYYY-MM-DD')";
                } else {
                    String motCleModifie = motCleValeur.replace(" ", "%").toUpperCase();
                    whereClause = whereClause + "UPPER(" + motCles[i] + ") LIKE '%" + motCleModifie + "%'";
                }
                if (i >= motCles.length - 1) continue;
                whereClause = whereClause + " OR ";
            }
        } else {
            String motCleModifie = motCleValeur.replace(" ", "%").toUpperCase();
            for (int i = 0; i < motCles.length; ++i) {
                whereClause = whereClause + "UPPER(" + motCles[i] + ") LIKE '%" + motCleModifie + "%'";
                if (i >= motCles.length - 1) continue;
                whereClause = whereClause + " OR ";
            }
        }
        return whereClause;
    }

    public static String makeWhereMotsClesPropre(ClassMAPTable e, String motCleValeur) throws Exception {
        String[] motCles = CGenUtil.filtrerMotsCles(e, motCleValeur);
        String whereClause = "";
        DateAutoComplete dateAutoComplete = new DateAutoComplete().parse(motCleValeur);
        if (dateAutoComplete.isDate()) {
            for (int i = 0; i < motCles.length; ++i) {
                if (DateAutoComplete.isDateColumn((String)motCles[i])) {
                    whereClause = dateAutoComplete.isRange() ? whereClause + motCles[i] + " BETWEEN TO_DATE('" + dateAutoComplete.getStartDate() + "', 'YYYY-MM-DD') AND TO_DATE('" + dateAutoComplete.getEndDate() + "', 'YYYY-MM-DD')" : whereClause + motCles[i] + " = TO_DATE('" + dateAutoComplete.getStartDate() + "', 'YYYY-MM-DD')";
                } else {
                    String motCleModifie = motCleValeur.replace(" ", "%").toUpperCase();
                    whereClause = whereClause + "UPPER(" + motCles[i] + ") LIKE '" + motCleModifie + "%'";
                }
                if (i >= motCles.length - 1) continue;
                whereClause = whereClause + " OR ";
            }
        } else {
            String motCleModifie = motCleValeur.replace(" ", "%").toUpperCase();
            for (int i = 0; i < motCles.length; ++i) {
                whereClause = whereClause + "UPPER(" + motCles[i] + ") LIKE '" + motCleModifie + "%'";
                if (i >= motCles.length - 1) continue;
                whereClause = whereClause + " OR ";
            }
        }
        return whereClause;
    }

    public static String makeWhereMotsClesPrecis(ClassMAPTable e, String motCleValeur) throws Exception {
        String[] motCles = CGenUtil.filtrerMotsCles(e, motCleValeur);
        String whereClause = "";
        DateAutoComplete dateAutoComplete = new DateAutoComplete().parse(motCleValeur);
        if (dateAutoComplete.isDate()) {
            for (int i = 0; i < motCles.length; ++i) {
                if (DateAutoComplete.isDateColumn((String)motCles[i])) {
                    whereClause = dateAutoComplete.isRange() ? whereClause + motCles[i] + " BETWEEN TO_DATE('" + dateAutoComplete.getStartDate() + "', 'YYYY-MM-DD') AND TO_DATE('" + dateAutoComplete.getEndDate() + "', 'YYYY-MM-DD')" : whereClause + motCles[i] + " = TO_DATE('" + dateAutoComplete.getStartDate() + "', 'YYYY-MM-DD')";
                } else {
                    String motCleModifie = motCleValeur.replace(" ", "%").toUpperCase();
                    whereClause = whereClause + "UPPER(" + motCles[i] + ") LIKE '" + motCleModifie + "'";
                }
                if (i >= motCles.length - 1) continue;
                whereClause = whereClause + " OR ";
            }
        } else {
            String motCleModifie = motCleValeur.replace(" ", "%").toUpperCase();
            for (int i = 0; i < motCles.length; ++i) {
                whereClause = whereClause + "UPPER(" + motCles[i] + ") LIKE '" + motCleModifie + "'";
                if (i >= motCles.length - 1) continue;
                whereClause = whereClause + " OR ";
            }
        }
        return whereClause;
    }

    public static String getValeurFieldByMotCle(ClassMAPTable e) throws Exception {
        return CGenUtil.getValeurFieldByMotCle(e, e.getValMotCles(), " - ");
    }

    public static String getValeurFieldByMotCle(ClassMAPTable e, String[] mts, String separator) throws Exception {
        String valeurAffiche = "";
        if (mts.length > 0) {
            for (int j = 0; j < mts.length; ++j) {
                Object val = CGenUtil.getValeurFieldByMethod(e, mts[j]);
                valeurAffiche = val == null ? valeurAffiche + "N/A" : (val instanceof Number ? valeurAffiche + Utilitaire.formaterAr(val.toString()).replace("\n", " ").replace(" ", "").replace("\u00a0", "").replace("\u202f", "") : valeurAffiche + val.toString().replace("\n", " "));
                if (j >= mts.length - 1) continue;
                valeurAffiche = valeurAffiche + separator;
            }
        }
        return valeurAffiche;
    }

    public static double[] initialiserColSomme(String[] nomColSomme) throws Exception {
        double[] retour = null;
        if (nomColSomme == null) {
            retour = new double[]{0.0};
            return retour;
        }
        retour = new double[nomColSomme.length + 1];
        for (int i = 0; i < retour.length; ++i) {
            retour[i] = 0.0;
        }
        return retour;
    }

    public static ResultatEtSomme rechercherPage(ClassMAPTable e, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, Connection c, int nbPage, boolean avoirRecap) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        try {
            ResultatEtSomme rs;
            int npp = 0;
            if (nbPage <= 0) {
                if (nbPage == -2) {
                    double[] sommeNeg = new double[]{0.0};
                    Object[] valiny = CGenUtil.rechercher(e, colInt, valInt, c, apresWhere);
                    ResultatEtSomme resultatEtSomme = new ResultatEtSomme(valiny, sommeNeg);
                    return resultatEtSomme;
                }
                npp = Parametre.getNbParPage();
            } else {
                npp = nbPage;
            }
            if (apresWhere == null || apresWhere.compareToIgnoreCase("") == 0) {
                String colOrdre = e.getAttributIDName();
                String ordre = "DESC";
                apresWhere = " order by " + colOrdre + " " + ordre;
            }
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String comande = "select * from (select " + CGenUtil.getListeChampPage(e, c) + ",rowNum as r from (select * from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhere(e) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere + ")) where r between " + indiceInf + " and " + indiceSup;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.initialiserColSomme(nomColSomme);
            somme = CGenUtil.calculSommeNombre(e, apresWhere, colInt, valInt, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche pagine de la table " + e.getNomTableSelect() + ex.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static String miseajourDifferentTable(String nomTable, String valcritere, String cc, String colonneamodifier, String critere) throws Exception {
        Connection c = null;
        Statement pst = null;
        try {
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            String sql = "UPDATE " + nomTable + " SET " + colonneamodifier + "=? WHERE " + critere + "=?";
            pst = c.prepareStatement(sql);
            pst.setString(1, cc);
            pst.setString(2, valcritere);
            pst.executeUpdate();
            c.commit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            c.rollback();
            throw ex;
        }
        finally {
            pst.close();
            if (c != null) {
                c.close();
            }
        }
        return null;
    }

    public static String miseajourDifferentTable(Connection c, String nomTable, String valcritere, String cc, String colonneamodifier, String critere) throws Exception {
        try (Statement pst = null;){
            String sql = "UPDATE " + nomTable + " SET " + colonneamodifier + "=? WHERE " + critere + "=?";
            pst = c.prepareStatement(sql);
            pst.setString(1, cc);
            pst.setString(2, valcritere);
            pst.executeUpdate();
        }
        return null;
    }

    public static String modifClient(Connection c, String nomTable, String nom, String regime, String adr, String tel, String fax, String numstat, String nif, String rc, String tp, String quittance, String cc) throws Exception {
        PreparedStatement pst = null;
        try {
            String sql = "UPDATE " + nomTable + " SET nom=?, idregime=?, adresse=?, telephone=?,fax=?,numstat=?,nif=?,rc=?,tp=?,quittance=? WHERE idclient='" + cc + "'";
            pst = c.prepareStatement(sql);
            pst.setString(1, nom);
            pst.setString(2, regime);
            pst.setString(3, adr);
            pst.setString(4, tel);
            pst.setString(5, fax);
            pst.setString(6, numstat);
            pst.setString(7, nif);
            pst.setString(8, rc);
            pst.setString(9, tp);
            pst.setString(10, quittance);
            pst.executeUpdate();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return null;
    }

    public static String modifClient(String nomTable, String nom, String regime, String adr, String tel, String fax, String numstat, String nif, String rc, String tp, String quittance, String cc) throws Exception {
        Connection c = null;
        Statement pst = null;
        try {
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            String sql = "UPDATE " + nomTable + " SET nom=?, idregime=?, adresse=?, telephone=?,fax=?,numstat=?,nif=?,rc=?,tp=?,quittance=? WHERE idclient='" + cc + "'";
            pst = c.prepareStatement(sql);
            pst.setString(1, nom);
            pst.setString(2, regime);
            pst.setString(3, adr);
            pst.setString(4, tel);
            pst.setString(5, fax);
            pst.setString(6, numstat);
            pst.setString(7, nif);
            pst.setString(8, rc);
            pst.setString(9, tp);
            pst.setString(10, quittance);
            pst.executeUpdate();
            c.commit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            c.rollback();
            throw ex;
        }
        finally {
            pst.close();
            if (c != null) {
                c.close();
            }
        }
        return null;
    }

    public static String getListeChampGroupeSommeMoyenne(String[] groupe, String[] somme, String[] moyenne) throws Exception {
        int j;
        if (groupe == null || groupe.length == 0) {
            return "*";
        }
        String retour = groupe[0];
        for (int i = 1; i < groupe.length; ++i) {
            if (groupe[i] == null || groupe[i].compareToIgnoreCase("") == 0 || groupe[i].compareToIgnoreCase("-") == 0) continue;
            retour = retour + "," + groupe[i];
        }
        if (somme == null) {
            return retour;
        }
        for (j = 0; j < somme.length; ++j) {
            if (somme[j] == null || somme[j].compareToIgnoreCase("") == 0 || somme[j].compareToIgnoreCase("-") == 0) continue;
            retour = retour + ", sum(" + somme[j] + ") as " + somme[j];
        }
        if (moyenne == null) {
            return retour;
        }
        for (j = 0; j < moyenne.length; ++j) {
            if (moyenne[j] == null || moyenne[j].compareToIgnoreCase("") == 0 || moyenne[j].compareToIgnoreCase("-") == 0) continue;
            retour = retour + ", avg(" + moyenne[j] + ") as " + moyenne[j];
        }
        return retour;
    }

    public static String getListeChampGroupeSimple(String[] groupe, String[] somme, String[] moyenne) throws Exception {
        int j;
        if (groupe == null) {
            return "*";
        }
        String retour = groupe[0];
        for (int i = 1; i < groupe.length; ++i) {
            if (groupe[i] == null || groupe[i].compareToIgnoreCase("") == 0 || groupe[i].compareToIgnoreCase("-") == 0) continue;
            retour = retour + "," + groupe[i];
        }
        if (somme == null) {
            return retour;
        }
        for (j = 0; j < somme.length; ++j) {
            if (somme[j] == null || somme[j].compareToIgnoreCase("") == 0 || somme[j].compareToIgnoreCase("-") == 0) continue;
            retour = retour + ", " + somme[j];
        }
        if (moyenne == null) {
            return retour;
        }
        for (j = 0; j < moyenne.length; ++j) {
            if (moyenne[j] == null || moyenne[j].compareToIgnoreCase("") == 0 || moyenne[j].compareToIgnoreCase("-") == 0) continue;
            retour = retour + ", " + moyenne[j];
        }
        return retour;
    }

    public static ResultatEtSomme rechercherPageGroupe(ClassMAPTable e, String[] groupe, String[] sommeGroupe, String[] moyenneGroupe, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, String ordre, Connection c, int nppa) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        boolean ouvertInterne = false;
        try {
            ResultatEtSomme rs;
            if (c == null) {
                c = new UtilDB().GetConn();
                ouvertInterne = true;
            }
            int npp = nppa <= 0 ? Parametre.getNbParPage() : nppa;
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String selectInterne = "select " + CGenUtil.getListeChampGroupeSommeMoyenne(groupe, sommeGroupe, moyenneGroupe) + " from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhere(e, c) + CGenUtil.makeWhereIntervalle(colInt, valInt) + apresWhere + " group by " + CGenUtil.getListeChampGroupeSomme(groupe, null) + "  " + ordre;
            String comande = "select * from (select " + CGenUtil.getListeChampGroupeSimple(groupe, sommeGroupe, moyenneGroupe) + ",rowNum as r from (" + selectInterne + ")) where r between " + indiceInf + " and " + indiceSup;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombreGroupe(selectInterne, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche groupee " + ex.getMessage());
        }
        finally {
            if (ouvertInterne && c != null) {
                c.close();
            }
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static double[] calculSommeNombreMultiple(ClassMAPTable e, String apresWhere, String[] numChampDaty, String[] daty, String[] nomColSomme, Connection c) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        try {
            String param = null;
            param = nomColSomme == null ? "SELECT COUNT(*) FROM " + e.getNomTableSelect() + " WHERE " + CGenUtil.makeWhereOr(e) + " " + CGenUtil.makeWhereIntervalle(numChampDaty, daty) + " " + apresWhere : "SELECT " + CGenUtil.getColonneSomme(nomColSomme) + ",COUNT(*) FROM " + e.getNomTableSelect() + " WHERE " + CGenUtil.makeWhereOr(e) + " " + CGenUtil.makeWhereIntervalle(numChampDaty, daty) + " " + apresWhere;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(param);
            double[] retour = null;
            retour = nomColSomme == null ? new double[1] : new double[nomColSomme.length + 1];
            dr.next();
            int i = 0;
            for (i = 0; i < retour.length - 1; ++i) {
                retour[i] = dr.getDouble(i + 1);
            }
            retour[i] = dr.getDouble(i + 1);
            double[] dArray = retour;
            return dArray;
        }
        catch (Exception s) {
            s.printStackTrace();
            throw new Exception(s.getMessage());
        }
        finally {
            if (st != null) {
                st.close();
            }
            if (dr != null) {
                dr.close();
            }
        }
    }

    public static ResultatEtSomme rechercherPageMultiple(ClassMAPTable e, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, Connection c, int nbPage) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        try {
            ResultatEtSomme rs;
            int npp = 0;
            npp = nbPage <= 0 ? Parametre.getNbParPage() : nbPage;
            int indiceInf = npp * (numPage - 1) + 1;
            int indiceSup = npp * numPage;
            String comande = "select * from (select " + CGenUtil.getListeChampPage(e, c) + ",rowNum as r from (select * from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhereOr(e) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWhere + ")) where r between " + indiceInf + " and " + indiceSup;
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] resultat = CGenUtil.transformeDataReader(dr, e, c);
            double[] somme = CGenUtil.calculSommeNombreMultiple(e, apresWhere, colInt, valInt, nomColSomme, c);
            ResultatEtSomme resultatEtSomme = rs = new ResultatEtSomme(resultat, somme);
            return resultatEtSomme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche pagine de la table " + e.getNomTableSelect() + ex.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static Object[] getAllDataNoPagination(ClassMAPTable e, String[] groupe, String[] sommeGroupe, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, String ordre, Connection c) throws Exception {
        ResultSet dr = null;
        Statement st = null;
        boolean ouvertInterne = false;
        try {
            Object[] resultat;
            if (c == null) {
                c = new UtilDB().GetConn();
                ouvertInterne = true;
            }
            String apresWR = "";
            String orderby = "";
            char[] ch = new char[]{' '};
            String[] tabstr = Utilitaire.split(apresWhere, ch);
            boolean tmp = false;
            for (int i = 0; i < tabstr.length; ++i) {
                if (tabstr[i].trim().compareToIgnoreCase("order") != 0 && !tmp) {
                    apresWR = apresWR + " " + tabstr[i].trim();
                    continue;
                }
                tmp = true;
                orderby = orderby + " " + tabstr[i].trim();
            }
            String selectInterne = "select " + CGenUtil.getListeChampGroupeSomme(groupe, sommeGroupe) + " from " + e.getNomTableSelect() + " where " + CGenUtil.makeWhereOr(e) + CGenUtil.makeWhereIntervalle(colInt, valInt) + " " + apresWR + " group by " + CGenUtil.getListeChampGroupeSomme(groupe, null) + " " + orderby + " " + ordre;
            String comande = "select * from (select " + CGenUtil.getListeChampGroupeSimple(groupe, sommeGroupe) + ",rowNum as r from (" + selectInterne + "))";
            st = c.createStatement(1004, 1007);
            dr = st.executeQuery(comande);
            Object[] objectArray = resultat = CGenUtil.transformeDataReader(dr, e, c);
            return objectArray;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur durant la recherche groupee " + ex.getMessage());
        }
        finally {
            if (ouvertInterne && c != null) {
                c.close();
            }
            if (dr != null) {
                dr.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static String formatterDaty(String daty) {
        Object retour = null;
        if (daty == null || daty == "") {
            return "";
        }
        return daty.substring(8, 10) + "/" + daty.substring(5, 7) + "/" + daty.substring(0, 4);
    }

    public static Object[] findreq(ClassMAPTable e, String req, Connection c) throws Exception {
        ResultSet dr = null;
        Statement cmd = null;
        boolean ifconnnull = false;
        try {
            Object[] ret;
            if (c == null) {
                c = new UtilDB().GetConn();
                ifconnnull = true;
            }
            String comandeInterne = req;
            cmd = c.createStatement(1004, 1008);
            dr = cmd.executeQuery(comandeInterne);
            Object[] objectArray = ret = CGenUtil.transformeDataReader(dr, e, c);
            return objectArray;
        }
        catch (Exception x) {
            x.printStackTrace();
            throw new Exception("Erreur durant la findreq " + x.getMessage());
        }
        finally {
            if (dr != null) {
                dr.close();
            }
            if (cmd != null) {
                cmd.close();
            }
            if (ifconnnull && c != null) {
                c.close();
            }
        }
    }

    public static void executeReq(String req, Connection c) throws Exception {
        try {
            Statement st = c.createStatement();
            st.execute(req);
        }
        catch (Exception s) {
            s.printStackTrace();
            throw new Exception(s.getMessage());
        }
    }
}

