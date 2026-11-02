/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.Champ;
import bean.ClassMAPTable;
import bean.ResultatEtSomme;
import bean.Table;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import utilitaire.Parametre;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public abstract class GenUtil
implements Serializable {
    String nomTable = null;
    String nomCle = null;
    int nbTuple = 0;
    Vector vect = null;
    String msgErreur = null;
    int nbChampaMapper = 0;
    Champ[] champ = null;
    private String nomClasse = "";

    public void utiliserChampBase() {
        Table t = new Table(this.getNomTable());
        this.champ = t.getChamp();
    }

    public void utiliserChampBase(Connection c) {
        Table t = new Table(this.getNomTable(), c);
        this.champ = t.getChamp();
    }

    public Object findByPk(ClassMAPTable c) {
        return null;
    }

    public Object[] findByPk(String[] pk, ClassMAPTable c) {
        return null;
    }

    public void modifNomTable(String n) {
        this.nomTable = n;
    }

    public void utiliserChampClasse() {
        this.getChampFromClass();
    }

    public GenUtil(String nomtable, String nomClasse) {
        this.setNomClasse(nomClasse);
        this.setNomTable(nomtable);
        this.utiliserChampBase();
    }

    public GenUtil(String nomtable, String nomClasse, Connection c) {
        this.setNomClasse(nomClasse);
        this.setNomTable(nomtable);
        this.utiliserChampBase(c);
    }

    public GenUtil(String nomtable, String nomClasse, int nbCM) {
        this.setNomClasse(nomClasse);
        this.setNomTable(nomtable);
        this.setNbChampaMapper(nbCM);
        this.utiliserChampBase();
    }

    public GenUtil(String table) {
        Table t = new Table(table);
        this.nomTable = t.getNomTable();
        this.champ = t.getChamp();
    }

    public GenUtil(String table, Connection c) {
        Table t = new Table(table, c);
        this.nomTable = t.getNomTable();
        this.champ = t.getChamp(c);
    }

    public void getChampFromClass() {
        try {
            Class<?> f = Class.forName(this.getNomClasse());
            ClassMAPTable cmp = (ClassMAPTable)f.newInstance();
            this.champ = new Champ[cmp.getNombreChamp()];
            int nb = 0;
            nb = this.getNbChampaMapper() == 0 ? cmp.getNombreChamp() : this.getNbChampaMapper();
            for (int j = 0; j < nb; ++j) {
                this.champ[j] = new Champ(cmp.getFieldList()[j]);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setChamp(Champ[] cham) {
        this.champ = cham;
    }

    public Champ[] getChamp() {
        return this.champ;
    }

    public GenUtil() {
    }

    public void setNomTable(String obj) {
        Table t = new Table(obj);
        this.modifNomTable(t.getNomTable());
        if (this.champ == null) {
            if (this.getNomClasse().compareToIgnoreCase("") == 0 || this.getNomClasse() == null) {
                this.champ = t.getChamp();
            } else {
                this.getChampFromClass();
            }
        }
    }

    public void setNomTable(String obj, Connection c) {
        Table t = new Table(obj, c);
        this.nomTable = t.getNomTable();
        if (this.champ == null) {
            if (this.getNomClasse().compareToIgnoreCase("") == 0 || this.getNomClasse() == null) {
                this.champ = t.getChamp();
            } else {
                this.getChampFromClass();
            }
        }
    }

    public void setNomCle(String cle) {
        this.nomCle = cle;
    }

    public String getNomTable() {
        return this.nomTable;
    }

    public String getNomCle() {
        return this.nomCle;
    }

    public Object[] rechercher(String ref) {
        return this.rechercher(1, ref);
    }

    public Object[] rechercherAll() {
        return this.rechercher(1, "%");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object[] rechercher(String nomColonne, String type, Object valeur) {
        UtilDB util = null;
        Statement st = null;
        ResultSet rs = null;
        Connection c = null;
        try {
            util = new UtilDB();
            c = util.GetConn();
            String param = "SELECT * FROM " + this.getNomTable() + " WHERE " + nomColonne + " like ?";
            st = c.prepareStatement(param, 1004, 1008);
            if (type.compareToIgnoreCase("Varchar2") == 0 || type.compareToIgnoreCase("char") == 0) {
                String temp = (String)valeur;
                if (temp.compareTo("") == 0) {
                    valeur = "%";
                }
                st.setString(1, (String)valeur);
            }
            if (type.compareToIgnoreCase("Number") == 0 || type.compareToIgnoreCase("Counter") == 0) {
                st.setInt(1, Utilitaire.stringToInt((String)valeur));
            }
            if (type.compareToIgnoreCase("Date") == 0) {
                st.setDate(1, (Date)valeur);
            }
            rs = st.executeQuery();
            Object[] objectArray = this.resultatGen(rs);
            return objectArray;
        }
        catch (SQLException s) {
            System.out.println("RechercheExecution11 par Reference " + s.getMessage());
            Object[] objectArray = null;
            return objectArray;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (c != null) {
                    c.close();
                }
                util.close_connection();
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution " + e.getMessage());
            }
        }
    }

    public Object[] rechercher(int numChamp, Object valeur, Connection c) throws Exception {
        Object util = null;
        Statement st = null;
        ResultSet rs = null;
        String nomColonne = this.champ[numChamp - 1].getNomColonne();
        String type = this.champ[numChamp - 1].getTypeColonne();
        try {
            String temp;
            String param = "SELECT * FROM " + this.getNomTable() + " WHERE " + nomColonne + " like ?";
            st = c.prepareStatement(param, 1004, 1008);
            if (type.compareToIgnoreCase("Varchar2") == 0 || type.compareToIgnoreCase("char") == 0) {
                temp = (String)valeur;
                if (temp.compareTo("") == 0) {
                    valeur = "%";
                }
                st.setString(1, (String)valeur);
            }
            if (type.compareToIgnoreCase("character varying") == 0 || type.compareToIgnoreCase("char") == 0) {
                temp = (String)valeur;
                if (temp.compareTo("") == 0) {
                    valeur = "%";
                }
                st.setString(1, (String)valeur);
            }
            if (type.compareToIgnoreCase("Number") == 0 || type.compareToIgnoreCase("Counter") == 0) {
                if (Utilitaire.stringToInt((String)valeur) != 0) {
                    st.setInt(1, Utilitaire.stringToInt((String)valeur));
                } else {
                    st.setString(1, "%");
                }
            }
            if (type.compareToIgnoreCase("integer") == 0 || type.compareToIgnoreCase("Counter") == 0) {
                if (Utilitaire.stringToInt((String)valeur) != 0) {
                    st.setInt(1, Utilitaire.stringToInt((String)valeur));
                } else {
                    st.setString(1, "%");
                }
            }
            if (type.compareToIgnoreCase("double") == 0 || type.compareToIgnoreCase("Counter") == 0) {
                if (Utilitaire.stringToInt((String)valeur) != 0) {
                    st.setInt(1, Utilitaire.stringToInt((String)valeur));
                } else {
                    st.setString(1, "%");
                }
            }
            if (type.compareToIgnoreCase("Date") == 0) {
                Date date1 = Utilitaire.string_date("dd/MM/yyyy", (String)valeur);
                st.setDate(1, date1);
            }
            rs = st.executeQuery();
            Object[] objectArray = this.resultatGen(rs);
            return objectArray;
        }
        catch (SQLException s) {
            s.printStackTrace();
            System.out.println("RechercheExecution2222 par Reference " + s.getMessage());
            throw new Exception(s.getMessage());
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object[] rechercher(int numChamp, Object valeur) {
        Object util = null;
        Connection c = null;
        try {
            c = new UtilDB().GetConn();
            Object[] objectArray = this.rechercher(numChamp, valeur, c);
            return objectArray;
        }
        catch (Exception s) {
            s.printStackTrace();
            System.out.println("RechercheExecution2222 par Reference " + s.getMessage());
            Object[] objectArray = null;
            return objectArray;
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution " + e.getMessage());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object[] rechercher(int numChamp, Object valeur, Object valeurSup) {
        UtilDB util = null;
        Statement st = null;
        ResultSet rs = null;
        Connection c = null;
        String nomColonne = this.champ[numChamp - 1].getNomColonne();
        String type = this.champ[numChamp - 1].getTypeColonne();
        try {
            util = new UtilDB();
            c = util.GetConn();
            String param = "SELECT * FROM " + this.getNomTable() + " WHERE " + nomColonne + " <= ? and " + nomColonne + " >=?";
            st = c.prepareStatement(param, 1004, 1008);
            if (type.compareToIgnoreCase("Number") == 0 || type.compareToIgnoreCase("Counter") == 0) {
                if (Utilitaire.stringToInt((String)valeur) != 0) {
                    st.setInt(1, Utilitaire.stringToInt((String)valeur));
                    st.setInt(2, Utilitaire.stringToInt((String)valeurSup));
                } else {
                    st.setString(1, "%");
                }
            }
            if (type.compareToIgnoreCase("Date") == 0) {
                Date date1 = Utilitaire.string_date("dd/MM/yyyy", (String)valeur);
                Date date2 = Utilitaire.string_date("dd/MM/yyyy", (String)valeurSup);
                st.setDate(1, date1);
                st.setDate(2, date2);
            }
            rs = st.executeQuery();
            Object[] objectArray = this.resultatGen(rs);
            return objectArray;
        }
        catch (SQLException s) {
            System.out.println("RechercheExecution333 par Reference " + s.getMessage());
            Object[] objectArray = null;
            return objectArray;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (c != null) {
                    c.close();
                }
                util.close_connection();
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution " + e.getMessage());
            }
        }
    }

    public Object[] rechercher(int[] numChamp, Object[] valeur, String apresWhere, int numPage) {
        return this.rechercher(numChamp, valeur, apresWhere, null, null, numPage);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object[] rechercher(int[] numChamp, Object[] valeur, String apresWhere, Connection c, int[] numChampDaty, String[] daty, int numPage) {
        Object util = null;
        Statement st = null;
        ResultSet rs = null;
        int indiceInf = 0;
        int indiceSup = 0;
        int nbParPage = Parametre.getNbParPage();
        try {
            int[] num = new int[]{1};
            String[] val = new String[]{"1"};
            indiceInf = nbParPage * (numPage - 1) + 1;
            indiceSup = nbParPage * numPage;
            String param = "select * from (select " + this.getListeChampPage() + ",rowNum as r from (select * from " + this.getNomTable() + " where " + this.makeWhere(numChamp, valeur) + " " + this.makeWhereIntervalle(numChampDaty, daty) + " " + apresWhere + ")) where r between " + indiceInf + " and " + indiceSup;
            st = this.preparerStatement(param, numChamp, valeur, c);
            rs = st.executeQuery();
            Object[] objectArray = this.resultatGen(rs);
            return objectArray;
        }
        catch (Exception s) {
            System.out.println("Erreur dans Recherche multicritere rechercher(int[] numChamp,Object[] valeur,String apresWhere,Connection c,int numChampDaty[],String daty[],int numPage " + s.getMessage());
            Object[] objectArray = null;
            return objectArray;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution " + e.getMessage());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object[] rechercher(int[] numChamp, Object[] valeur, String apresWhere, int[] numChampDaty, String[] daty, int numPage) {
        UtilDB util = null;
        Connection c = null;
        boolean indiceInf = false;
        boolean indiceSup = false;
        try {
            util = new UtilDB();
            c = util.GetConn();
            Object[] objectArray = this.rechercher(numChamp, valeur, apresWhere, c, numChampDaty, daty, numPage);
            return objectArray;
        }
        catch (Exception s) {
            s.printStackTrace();
            Object[] objectArray = null;
            return objectArray;
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution " + e.getMessage());
            }
        }
    }

    public Object[] rechercher(int[] numChamp, Object[] valeur, int numPage) {
        return this.rechercher(numChamp, valeur, "", numPage);
    }

    public Object[] rechercher(int[] numChamp, Object[] valeur) throws Exception {
        try {
            return this.rechercher(numChamp, valeur, "");
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public Object[] rechercher(int[] numChamp, Object[] valeur, String apresWhere) throws Exception {
        UtilDB util = null;
        Connection c = null;
        try {
            util = new UtilDB();
            c = util.GetConn();
            Object[] objectArray = this.rechercher(numChamp, valeur, apresWhere, c);
            return objectArray;
        }
        catch (Exception s) {
            s.printStackTrace();
            throw s;
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution " + e.getMessage());
            }
        }
    }

    public Object[] rechercherVect(int[] numChamp, Object[] valeur, String apresWhere) throws Exception {
        UtilDB util = null;
        Connection c = null;
        try {
            util = new UtilDB();
            c = util.GetConn();
            Object[] objectArray = this.rechercherVect(numChamp, valeur, apresWhere, c);
            return objectArray;
        }
        catch (Exception s) {
            System.out.println("Erreur dans Recherche multicritere 2" + s.getMessage());
            throw new Exception(s.getMessage());
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution " + e.getMessage());
            }
        }
    }

    public Object[] rechercherVect(int[] numChamp, Object[] valeur, String apresWhere, Connection c) throws Exception {
        Vector v = null;
        try (ResultSet rs = null;){
            rs = this.rechercherResult(numChamp, valeur, apresWhere, c);
            v = this.resultToVect(rs);
        }
        return this.resultatGen(v);
    }

    public Object[] rechercher(int[] numChamp, Object[] valeur, String apresWhere, Connection c) throws Exception {
        Statement st = null;
        ResultSet rs = null;
        try {
            String param = "SELECT * FROM " + this.getNomTable() + " WHERE " + this.makeWhere(numChamp, valeur) + " " + apresWhere;
            System.out.println("param = " + param);
            st = this.preparerStatement(param, numChamp, valeur, c);
            rs = st.executeQuery();
            Object[] objectArray = this.resultatGen(rs);
            return objectArray;
        }
        catch (SQLException s) {
            System.out.println("Erreur dans Recherche multicritere 1" + s.getMessage());
            throw new Exception(s.getMessage());
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    public ResultSet rechercherResult(int[] numChamp, Object[] valeur, String apresWhere, Connection c) throws Exception {
        Statement st = null;
        ResultSet rs = null;
        try {
            String param = "SELECT * FROM " + this.getNomTable() + " WHERE " + this.makeWhere(numChamp) + " " + apresWhere;
            st = c.prepareStatement(param);
            for (int j = 0; j < valeur.length; ++j) {
                String type = this.champ[numChamp[j] - 1].getTypeColonne();
                if (type.compareToIgnoreCase("Varchar2") == 0 || type.compareToIgnoreCase("char") == 0) {
                    String temp = (String)valeur[j];
                    if (temp.compareTo("") == 0 || temp == null) {
                        valeur[j] = "%";
                    }
                    st.setString(j + 1, (String)valeur[j]);
                }
                if (type.compareToIgnoreCase("Number") == 0 || type.compareToIgnoreCase("Counter") == 0) {
                    if (((String)valeur[j]).compareTo("%") == 0 || ((String)valeur[j]).compareTo("") == 0 || valeur[j] == null) {
                        st.setString(j + 1, "%");
                    } else {
                        st.setInt(j + 1, Utilitaire.stringToInt((String)valeur[j]));
                    }
                }
                if (type.compareToIgnoreCase("Date") != 0) continue;
                Date date1 = Utilitaire.string_date("dd/MM/yyyy", (String)valeur[j]);
                st.setDate(j + 1, date1);
            }
            ResultSet resultSet = rs = st.executeQuery();
            return resultSet;
        }
        catch (SQLException s) {
            System.out.println("Erreur dans Recherche multicritere 1" + s.getMessage());
            throw new Exception(s.getMessage());
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution " + e.getMessage());
            }
        }
    }

    public int calculNombreLigne(int[] numChamp, Object[] valeur, String apresWhere) throws Exception {
        Connection c = null;
        try {
            UtilDB util = new UtilDB();
            c = util.GetConn();
            c.setAutoCommit(true);
            int n = this.calculNombreLigne(numChamp, valeur, apresWhere, c);
            return n;
        }
        catch (Exception s) {
            throw new Exception(s.getMessage());
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL Calcul Nombre" + e.getMessage());
            }
        }
    }

    public int calculNombreLigne(int[] numChamp, Object[] valeur, String apresWhere, Connection c) throws Exception {
        Statement st = null;
        ResultSet rs = null;
        try {
            String param = "SELECT count(*) FROM " + this.getNomTable() + " WHERE " + this.makeWhere(numChamp) + " " + apresWhere;
            st = this.preparerStatement(param, numChamp, valeur, c);
            rs = st.executeQuery();
            rs.next();
            int n = rs.getInt(1);
            return n;
        }
        catch (SQLException s) {
            System.out.println("Erreur dans Recherche multicritere " + s.getMessage());
            throw new Exception(s.getMessage());
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution " + e.getMessage());
            }
        }
    }

    public String getListeChampPage() {
        String retour = null;
        retour = this.getChamp()[0].getNomColonne();
        for (int i = 1; i < this.getChamp().length; ++i) {
            retour = retour + "," + this.getChamp()[i].getNomColonne();
        }
        return retour;
    }

    public String makeWhere(int[] numChamp, Object[] val) {
        String retour = null;
        retour = "UPPER(" + this.champ[numChamp[0] - 1].getNomColonne() + ") like UPPER(?)";
        for (int i = 1; i < numChamp.length; ++i) {
            String mot = (String)val[i];
            if (val[i] == null || mot.compareTo("%") == 0 || mot.compareTo("") == 0) continue;
            retour = retour + " AND UPPER(" + this.champ[numChamp[i] - 1].getNomColonne() + ") like UPPER(?)";
        }
        if (retour == null) {
            return "1<2";
        }
        return retour;
    }

    public String makeWhereIntervalle(int[] numChamp, String[] val) {
        String retour = "";
        if (numChamp == null || val == null) {
            return "";
        }
        val = Utilitaire.remplacerNullParBlanc(val, "");
        for (int i = 0; i < numChamp.length; ++i) {
            if (val[2 * i].compareTo("") > 0 & val[2 * i + 1].compareTo("") == 0) {
                retour = retour + " AND " + this.champ[numChamp[i] - 1].getNomColonne() + " >= '" + val[2 * i] + "'";
            }
            if (val[2 * i].compareTo("") == 0 & val[2 * i + 1].compareTo("") > 0) {
                retour = retour + " AND " + this.champ[numChamp[i] - 1].getNomColonne() + " <= '" + val[2 * i + 1] + "'";
            }
            if (!(val[2 * i].compareTo("") > 0 & val[2 * i + 1].compareTo("") > 0)) continue;
            retour = retour + " AND " + this.champ[numChamp[i] - 1].getNomColonne() + " >= '" + val[2 * i] + "' AND " + this.champ[numChamp[i] - 1].getNomColonne() + " <= '" + val[2 * i + 1] + "'";
        }
        return retour;
    }

    public String makeWhere(int[] numChamp) {
        String retour = null;
        retour = "UPPER(" + this.champ[numChamp[0] - 1].getNomColonne() + ") like UPPER(?)";
        for (int i = 1; i < numChamp.length; ++i) {
            retour = retour + " AND UPPER(" + this.champ[numChamp[i] - 1].getNomColonne() + ") like UPPER(?)";
        }
        return retour;
    }

    public Vector resultToVect(ResultSet rs) throws Exception {
        try {
            Vector<String> obj = new Vector<String>();
            Vector<Vector<String>> result = new Vector<Vector<String>>();
            ResultSetMetaData md = rs.getMetaData();
            int nbColonne = md.getColumnCount();
            while (rs.next()) {
                for (int i = 0; i < nbColonne; ++i) {
                    obj.add(rs.getString(i));
                }
                result.add(obj);
            }
            return result;
        }
        catch (SQLException ex) {
            throw new Exception();
        }
    }

    public Object[] resultatGen(Vector v) {
        return null;
    }

    public abstract Object[] resultatLimit(int var1, ResultSet var2);

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    public String getNomClasse() {
        return this.nomClasse;
    }

    public Object[] rechercher(int[] numChamp, Object[] valeur, int[] numChampDaty, String[] daty) throws Exception {
        try {
            return this.rechercher(numChamp, valeur, "", numChampDaty, daty);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public String getColonneSomme(String[] listeCol) {
        if (listeCol == null || listeCol.length == 0) {
            return "0";
        }
        String retour = "sum(" + listeCol[0] + ")";
        for (int i = 1; i < listeCol.length; ++i) {
            retour = retour + ",sum(" + listeCol[i] + ")";
        }
        return retour;
    }

    public double[] calculSommeNombre(int[] numChamp, Object[] valeur, String apresWhere, int[] numChampDaty, String[] daty, String[] nomColSomme) throws Exception {
        Object st = null;
        Object rs = null;
        Connection c = null;
        try {
            c = new UtilDB().GetConn();
            double[] dArray = this.calculSommeNombre(numChamp, valeur, apresWhere, c, numChampDaty, daty, nomColSomme);
            return dArray;
        }
        catch (SQLException s) {
            System.out.println("Erreur dans calculSommeNombre multicritere 1" + s.getMessage());
            throw new Exception(s.getMessage());
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL calculSommeNombre " + e.getMessage());
            }
        }
    }

    public double[] calculSommeNombre(int[] numChamp, Object[] valeur, String apresWhere, Connection c, int[] numChampDaty, String[] daty, String[] nomColSomme) throws Exception {
        Statement st = null;
        ResultSet rs = null;
        try {
            String param = "SELECT " + this.getColonneSomme(nomColSomme) + ",COUNT(*) FROM " + this.getNomTable() + " WHERE " + this.makeWhere(numChamp, valeur) + " " + this.makeWhereIntervalle(numChampDaty, daty) + " " + apresWhere;
            st = this.preparerStatement(param, numChamp, valeur, c);
            rs = st.executeQuery();
            double[] retour = null;
            retour = nomColSomme == null ? new double[2] : new double[nomColSomme.length + 1];
            rs.next();
            int i = 0;
            for (i = 0; i < retour.length - 1; ++i) {
                retour[i] = rs.getDouble(i + 1);
            }
            retour[i] = rs.getDouble(i + 1);
            double[] dArray = retour;
            return dArray;
        }
        catch (SQLException s) {
            s.printStackTrace();
            System.out.println("Erreur dans Recherche multicritere 1" + s.getMessage());
            throw new Exception(s.getMessage());
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution " + e.getMessage());
            }
        }
    }

    public PreparedStatement preparerStatement(String param, int[] numChamp, Object[] valeur, Connection c) throws Exception {
        PreparedStatement st = c.prepareStatement(param, 1004, 1008);
        int k = 0;
        for (int j = 0; j < valeur.length; ++j) {
            String type = this.champ[numChamp[j] - 1].getTypeColonne();
            if (type.compareToIgnoreCase("Varchar2") == 0 || type.compareToIgnoreCase("char") == 0 || type.compareToIgnoreCase("character varying") == 0 || type.compareToIgnoreCase("varchar") == 0 || type.compareToIgnoreCase("text") == 0) {
                if (valeur[j] == null || ((String)valeur[j]).compareTo("%") == 0 || ((String)valeur[j]).compareTo("") == 0) {
                    if (j == 0) {
                        st.setString(k + 1, "%");
                        ++k;
                    }
                } else {
                    st.setString(k + 1, (String)valeur[j]);
                    ++k;
                }
            }
            if (type.compareToIgnoreCase("Number") == 0 || type.compareToIgnoreCase("Counter") == 0 || type.compareToIgnoreCase("integer") == 0 || type.compareToIgnoreCase("smallint") == 0 || type.compareToIgnoreCase("bigint") == 0 || type.compareToIgnoreCase("bigint") == 0) {
                if (((String)valeur[j]).compareTo("%") == 0 || ((String)valeur[j]).compareTo("") == 0 || valeur[j] == null) {
                    if (j == 0) {
                        st.setString(k + 1, "%");
                        ++k;
                    }
                } else {
                    st.setInt(k + 1, Utilitaire.stringToInt((String)valeur[j]));
                    ++k;
                }
            }
            if (type.compareToIgnoreCase("Float") == 0 || type.compareToIgnoreCase("real") == 0 || type.compareToIgnoreCase("double precision") == 0 || type.compareToIgnoreCase("decimal") == 0 || type.compareToIgnoreCase("numeric") == 0) {
                if (((String)valeur[j]).compareTo("%") == 0 || ((String)valeur[j]).compareTo("") == 0 || valeur[j] == null) {
                    if (j == 0) {
                        st.setString(k + 1, "%");
                        ++k;
                    }
                } else {
                    st.setDouble(k + 1, Utilitaire.stringToDouble((String)valeur[j]));
                    ++k;
                }
            }
            if (type.compareToIgnoreCase("Date") != 0 && type.compareToIgnoreCase("date") != 0) continue;
            if (((String)valeur[j]).compareTo("%") == 0 || ((String)valeur[j]).compareTo("") == 0 || valeur[j] == null) {
                if (j != 0) continue;
                st.setString(k + 1, "%");
                ++k;
                continue;
            }
            Date date1 = Utilitaire.string_date("dd/MM/yyyy", (String)valeur[j]);
            st.setDate(k + 1, date1);
            ++k;
        }
        return st;
    }

    public Object[] rechercher(int[] numChamp, Object[] valeur, String apresWhere, Connection c, int[] numChampDaty, String[] daty) throws Exception {
        Statement st = null;
        ResultSet rs = null;
        try {
            String param = "SELECT * FROM " + this.getNomTable() + " WHERE " + this.makeWhere(numChamp, valeur) + " " + this.makeWhereIntervalle(numChampDaty, daty) + " " + apresWhere;
            st = this.preparerStatement(param, numChamp, valeur, c);
            rs = st.executeQuery();
            Object[] objectArray = this.resultatGen(rs);
            return objectArray;
        }
        catch (SQLException s) {
            System.out.println("Erreur dans Recherche multicritere 1" + s.getMessage());
            throw new Exception(s.getMessage());
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution " + e.getMessage());
            }
        }
    }

    public Object[] rechercher(int[] numChamp, Object[] valeur, String apresWhere, int[] numChampDaty, String[] daty) throws Exception {
        UtilDB util = null;
        Connection c = null;
        try {
            util = new UtilDB();
            c = util.GetConn();
            Object[] objectArray = this.rechercher(numChamp, valeur, apresWhere, c, numChampDaty, daty);
            return objectArray;
        }
        catch (Exception s) {
            System.out.println("Erreur dans Recherche multicritere 2" + s.getMessage());
            s.printStackTrace();
            throw new Exception(s.getMessage());
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution " + e.getMessage());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object[] resultatGen(ResultSet rs) {
        ArrayList list = new ArrayList();
        Vector<ClassMAPTable> v = new Vector<ClassMAPTable>();
        Class<?> cls = null;
        try {
            cls = Class.forName(this.getNomClasse());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object[] aret = null;
        try {
            ClassMAPTable temp = (ClassMAPTable)cls.newInstance();
            Field[] fields = temp.getFieldList();
            while (rs.next()) {
                ClassMAPTable obj = (ClassMAPTable)cls.newInstance();
                obj.setNomTable(this.getNomTable());
                block20: for (int i = 0; i < fields.length; ++i) {
                    Field fld = fields[i];
                    switch (obj.getTypeMAPField(fld)) {
                        case 0: {
                            this.setValField(obj, fld, new Boolean(rs.getBoolean(fld.getName())));
                            continue block20;
                        }
                        case 1: {
                            this.setValField(obj, fld, new Byte(rs.getByte(fld.getName())));
                            continue block20;
                        }
                        case 2: {
                            this.setValField(obj, fld, new Short(rs.getShort(fld.getName())));
                            continue block20;
                        }
                        case 3: {
                            this.setValField(obj, fld, new Integer(rs.getInt(fld.getName())));
                            continue block20;
                        }
                        case 4: {
                            this.setValField(obj, fld, new Long(rs.getLong(fld.getName())));
                            continue block20;
                        }
                        case 5: {
                            this.setValField(obj, fld, new Float(rs.getFloat(fld.getName())));
                            continue block20;
                        }
                        case 6: {
                            this.setValField(obj, fld, new Double(rs.getDouble(fld.getName())));
                            continue block20;
                        }
                        case 10: {
                            this.setValField(obj, fld, rs.getString(fld.getName()));
                            continue block20;
                        }
                        case 21: {
                            this.setValField(obj, fld, rs.getDate(fld.getName()));
                            continue block20;
                        }
                        case 33: {
                            this.setValField(obj, fld, new Integer(rs.getInt(fld.getName())));
                        }
                    }
                }
                v.add(obj);
            }
            aret = (Object[])Array.newInstance(cls.newInstance().getClass(), v.size());
            v.copyInto(aret);
            Object[] objectArray = aret;
            return objectArray;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            return aret;
        }
    }

    public Object setValField(Object obj, Field f, Object value) {
        try {
            String nomMethode = "set" + Utilitaire.convertDebutMajuscule(f.getName());
            Class[] paramT = new Class[]{f.getType()};
            Object[] args = new Object[]{value};
            Object o = obj.getClass().getMethod(nomMethode, paramT).invoke(obj, args);
            return o;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int getNbChampaMapper() {
        return this.nbChampaMapper;
    }

    public void setNbChampaMapper(int nbChampaMapper) {
        this.nbChampaMapper = nbChampaMapper;
    }

    public ResultatEtSomme rechercherPage(int[] numChamp, Object[] valeur, String apresWhere, Connection c, int[] numChampDaty, String[] daty, int numPage, String[] nomColSom) throws Exception {
        Object[] ret = this.rechercher(numChamp, valeur, apresWhere, c, numChampDaty, daty, numPage);
        double[] sommeNombre = this.calculSommeNombre(numChamp, valeur, apresWhere, c, numChampDaty, daty, nomColSom);
        return new ResultatEtSomme(ret, sommeNombre);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ResultatEtSomme rechercherPage(int[] numChamp, Object[] valeur, String apresWhere, int[] numChampDaty, String[] daty, int numPage, String[] nomColSom) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            ResultatEtSomme resultatEtSomme = this.rechercherPage(numChamp, valeur, apresWhere, c, numChampDaty, daty, numPage, nomColSom);
            return resultatEtSomme;
        }
    }

    public Object[] rechercher(int[] numChamp, Object[] valeur, Connection c) throws Exception {
        try {
            return this.rechercher(numChamp, valeur, "", c);
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public Object[] rechercher(int[] numChamp, Object[] valeur, String apresWhere, int[] numChampDaty, String[] daty, Connection c) throws Exception {
        UtilDB util = null;
        try {
            util = new UtilDB();
            return this.rechercher(numChamp, valeur, apresWhere, c, numChampDaty, daty);
        }
        catch (Exception s) {
            s.printStackTrace();
            throw new Exception(s.getMessage());
        }
    }
}

