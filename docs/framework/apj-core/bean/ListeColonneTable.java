/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.Champ;
import bean.ClassMAPTable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import utilitaire.UtilDB;

public class ListeColonneTable {
    static Hashtable listeColPTable = new Hashtable();
    static Hashtable listeFieldHeritage = new Hashtable();
    static Hashtable listeFieldHeritageClassMAPTable = new Hashtable();
    static Hashtable listeMethodHeritage = new Hashtable();
    static Hashtable classeEtPackage = new Hashtable();

    public static Hashtable getClasseEtPackage() {
        return classeEtPackage;
    }

    public static void setClasseEtPackage(Hashtable classeEtPackage) {
        ListeColonneTable.classeEtPackage = classeEtPackage;
    }

    public static Method[] getMethodListeHeritage(ClassMAPTable e) throws Exception {
        boolean testDedans = listeMethodHeritage.containsKey(e.getNomTable() + e.getNomTableSelect() + e.getClassName());
        if (testDedans) {
            return (Method[])listeMethodHeritage.get(e.getNomTable() + e.getNomTableSelect() + e.getClassName());
        }
        Method[] prim = e.getClass().getDeclaredMethods();
        if (e.getClass().getSuperclass().getName().endsWith("ClassMAPTable")) {
            return prim;
        }
        Vector<Method> listeF = new Vector<Method>();
        List<Method> temp = Arrays.asList(prim);
        listeF.addAll(temp);
        Class<?> p = e.getClass().getSuperclass();
        while (true) {
            if (p.getName().endsWith("ClassMAPTable")) {
                if (e.getMode().compareToIgnoreCase("groupe") != 0) break;
                break;
            }
            Method[] second = p.getDeclaredMethods();
            listeF.addAll(Arrays.asList(second));
            p = p.getSuperclass();
        }
        Object[] retour = new Method[listeF.size()];
        listeF.copyInto(retour);
        listeMethodHeritage.put(e.getNomTable() + e.getNomTableSelect() + e.getClassName(), retour);
        return retour;
    }

    public static Method[] getMethodListeHeritageWithClassMAPTable(ClassMAPTable e) throws Exception {
        Method[] second;
        Method[] prim = e.getClass().getDeclaredMethods();
        if (e.getClass().getSuperclass().getName().endsWith("ClassMAPTable")) {
            return prim;
        }
        Vector<Method> listeF = new Vector<Method>();
        List<Method> temp = Arrays.asList(prim);
        listeF.addAll(temp);
        Class<?> p = e.getClass().getSuperclass();
        while (true) {
            if (p.getName().endsWith("ClassMAPTable")) {
                if (e.getMode().compareToIgnoreCase("groupe") == 0) {
                    // empty if block
                }
                break;
            }
            second = p.getDeclaredMethods();
            listeF.addAll(Arrays.asList(second));
            p = p.getSuperclass();
        }
        second = p.getDeclaredMethods();
        listeF.addAll(Arrays.asList(second));
        Object[] retour = new Method[listeF.size()];
        listeF.copyInto(retour);
        return retour;
    }

    public static Field[] getFieldListeHeritage(ClassMAPTable e) throws Exception {
        boolean testDedans = listeFieldHeritage.containsKey(e.getNomTable() + e.getNomTableSelect() + e.getClassName());
        if (testDedans) {
            return (Field[])listeFieldHeritage.get(e.getNomTable() + e.getNomTableSelect() + e.getClassName());
        }
        Field[] prim = e.getClass().getDeclaredFields();
        if (e.getClass().getSuperclass().getName().endsWith("ClassMAPTable")) {
            // empty if block
        }
        Vector<Field> listeF = new Vector<Field>();
        List<Field> temp = Arrays.asList(prim);
        listeF.addAll(temp);
        Class<?> p = e.getClass().getSuperclass();
        while (true) {
            if (p.getName().endsWith("ClassMAPTable")) {
                if (e.getMode().compareToIgnoreCase("groupe") != 0) break;
                break;
            }
            Field[] second = p.getDeclaredFields();
            listeF.addAll(Arrays.asList(second));
            p = p.getSuperclass();
        }
        Object[] retour = new Field[listeF.size()];
        listeF.copyInto(retour);
        if (e.getClass().getPackage() != null) {
            classeEtPackage.put(e.getClassName(), e.getClass().getPackage().getName());
        }
        listeFieldHeritage.put(e.getNomTable() + e.getNomTableSelect() + e.getClassName(), retour);
        return retour;
    }

    public static Field[] getFieldListeHeritageWithClassMAPTAble(ClassMAPTable e) throws Exception {
        Field[] second;
        boolean testDedans = listeFieldHeritageClassMAPTable.containsKey(e.getNomTable() + e.getNomTableSelect() + e.getClassName());
        if (testDedans) {
            return (Field[])listeFieldHeritageClassMAPTable.get(e.getNomTable() + e.getNomTableSelect() + e.getClassName());
        }
        Field[] prim = e.getClass().getDeclaredFields();
        if (e.getClass().getSuperclass().getName().endsWith("ClassMAPTable")) {
            // empty if block
        }
        Vector<Field> listeF = new Vector<Field>();
        List<Field> temp = Arrays.asList(prim);
        listeF.addAll(temp);
        Class<?> p = e.getClass().getSuperclass();
        while (true) {
            if (p.getName().endsWith("ClassMAPTable")) {
                if (e.getMode().compareToIgnoreCase("groupe") == 0) {
                    // empty if block
                }
                break;
            }
            second = p.getDeclaredFields();
            listeF.addAll(Arrays.asList(second));
            p = p.getSuperclass();
        }
        second = p.getDeclaredFields();
        listeF.addAll(Arrays.asList(second));
        Object[] retour = new Field[listeF.size()];
        listeF.copyInto(retour);
        listeFieldHeritageClassMAPTable.put(e.getNomTable() + e.getNomTableSelect() + e.getClassName(), retour);
        return retour;
    }

    public static void ajouterAlaListe(ClassMAPTable e, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            String nomTable = e.getNomTableSelect();
            boolean testDedans = listeColPTable.containsKey(e.getNomTable() + e.getNomTableSelect() + e.getClassName());
            if (!testDedans) {
                if (c == null) {
                    c = new UtilDB().GetConn();
                    estOuvert = true;
                }
                Vector r = ListeColonneTable.getChampFromTable(nomTable, c);
                Field[] fieldlist = ListeColonneTable.getFieldListeHeritageWithClassMAPTAble(e);
                Champ[] retour = new Champ[r.size()];
                Vector<Champ> intersect = new Vector<Champ>();
                block5: for (int i = 0; i < r.size(); ++i) {
                    retour[i] = (Champ)r.get(i);
                    for (int k = 0; k < fieldlist.length; ++k) {
                        if (retour[i].getNomColonne().compareToIgnoreCase(fieldlist[k].getName()) != 0 || retour[i].getNomColonne().compareToIgnoreCase("NOMBREPARGROUPE") == 0) continue;
                        intersect.add(new Champ(fieldlist[k], retour[i].getPrecision()));
                        continue block5;
                    }
                }
                Object[] valiny = new Champ[intersect.size()];
                intersect.copyInto(valiny);
                if (valiny.length > 0) {
                    listeColPTable.put(e.getNomTable() + e.getNomTableSelect() + e.getClassName(), valiny);
                }
            }
        }
        catch (Exception ex) {
            if (c != null) {
                c.rollback();
            }
            throw ex;
        }
        finally {
            if (estOuvert && c != null) {
                c.close();
            }
        }
    }

    public static Field[] getChampBase(ClassMAPTable e, Connection c) throws Exception {
        String nomTable = e.getNomTable();
        boolean testDedans = listeColPTable.containsKey(e.getNomTable() + e.getNomTableSelect() + e.getClassName());
        Object[] retour = null;
        if (!testDedans) {
            Vector r = ListeColonneTable.getChampFromTable(nomTable, c);
            retour = new Champ[r.size()];
            r.copyInto(retour);
        } else {
            retour = (Champ[])listeColPTable.get(e.getNomTable() + e.getNomTableSelect() + e.getClassName());
        }
        Field[] fieldlist = ListeColonneTable.getFieldListeHeritage(e);
        Vector<Field> intersect = new Vector<Field>();
        block0: for (int i = 0; i < retour.length; ++i) {
            for (int k = 0; k < fieldlist.length; ++k) {
                if (((Champ)retour[i]).getNomColonne().compareToIgnoreCase(fieldlist[k].getName()) != 0) continue;
                intersect.add(fieldlist[k]);
                continue block0;
            }
        }
        Object[] valiny = new Field[intersect.size()];
        intersect.copyInto(valiny);
        return valiny;
    }

    public static Champ[] getFromListe(ClassMAPTable e, Connection c) throws Exception {
        ListeColonneTable.ajouterAlaListe(e, c);
        return (Champ[])listeColPTable.get(e.getNomTable() + e.getNomTableSelect() + e.getClassName());
    }

    public static Champ getChamp(ClassMAPTable e, String col, Connection cnx) throws Exception {
        try {
            Champ[] liste = null;
            liste = !listeColPTable.containsKey(e.getNomTable() + e.getNomTableSelect() + e.getClassName()) ? ListeColonneTable.getFromListe(e, cnx) : (Champ[])listeColPTable.get(e.getNomTable() + e.getNomTableSelect() + e.getClassName());
            if (liste == null) {
                return null;
            }
            for (int i = 0; i < liste.length; ++i) {
                if (liste[i].getNomColonne().compareToIgnoreCase(col) != 0) continue;
                return liste[i];
            }
            return null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Champ getChamp(ClassMAPTable e, String col) throws Exception {
        try (Connection cnx = null;){
            Champ[] liste = null;
            if (!listeColPTable.containsKey(e.getNomTable() + e.getNomTableSelect() + e.getClassName())) {
                cnx = new UtilDB().GetConn();
                liste = ListeColonneTable.getFromListe(e, cnx);
            } else {
                liste = (Champ[])listeColPTable.get(e.getNomTable() + e.getNomTableSelect() + e.getClassName());
            }
            for (int i = 0; i < liste.length; ++i) {
                if (liste[i].getNomColonne().compareToIgnoreCase(col) != 0) continue;
                Champ champ = liste[i];
                return champ;
            }
            Champ champ = null;
            return champ;
        }
    }

    public static Vector getChampFromTable(String nomT, Connection c) throws Exception {
        Statement st = null;
        ResultSet dr = null;
        try {
            Champ temp;
            String comande = "SELECT * FROM listeColonneTable where table_Name = upper('" + nomT + "') order by column_id asc";
            st = c.createStatement();
            dr = st.executeQuery(comande);
            Vector<Champ> retour = new Vector<Champ>();
            int i = 0;
            while (dr.next()) {
                temp = new Champ(dr.getString(3), dr.getString(4), dr.getInt(8), dr.getInt(9));
                retour.add(temp);
                ++i;
            }
            temp = new Champ("nombrepargroupe", "NUMBER", 10, 0);
            Vector<Champ> vector = retour;
            return vector;
        }
        catch (Exception ex) {
            throw ex;
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
}

