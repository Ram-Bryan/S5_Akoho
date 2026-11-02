/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.CGenUtil;
import bean.ClassEtat;
import bean.ClassFille;
import bean.ClassMAPTable;
import bean.ClassMere;
import bean.ResultatGroupe;
import bean.RgMultiple;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import utilitaire.UtilExcel;
import utilitaire.Utilitaire;
import utilitaire.XlsImport;

public class AdminGen {
    public static LinkedHashMap<String, ClassMAPTable[]> toHashLinked(ClassMAPTable[] lo, String attrCle) throws Exception {
        LinkedHashMap<String, ClassMAPTable[]> result = new LinkedHashMap<String, ClassMAPTable[]>();
        for (ClassMAPTable obj : lo) {
            String key = CGenUtil.getValeurInsert(obj, attrCle);
            result.putIfAbsent(key, new ClassMAPTable[0]);
            ClassMAPTable[] currentArray = result.get(key);
            ClassMAPTable[] newArray = new ClassMAPTable[currentArray.length + 1];
            System.arraycopy(currentArray, 0, newArray, 0, currentArray.length);
            newArray[currentArray.length] = obj;
            result.put(key, newArray);
        }
        return result;
    }

    public static Map<String, Double> getDataChart(ClassMAPTable[] data, String nomAttr, String nomColValeur) throws Exception {
        LinkedHashMap<String, Double> dataChart = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, ClassMAPTable[]> hashe = AdminGen.toHashLinked(data, nomAttr);
        for (String key : hashe.keySet()) {
            ClassMAPTable[] groupe = hashe.get(key);
            double somme = AdminGen.calculSommeDouble(groupe, nomColValeur);
            dataChart.put(key, somme);
        }
        return dataChart;
    }

    public static Map<String, Double> getDataChartAvecNombre(ClassMAPTable[] data, String nomAttr, String nomColValeur) throws Exception {
        LinkedHashMap<String, Double> dataChart = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, ClassMAPTable[]> hashe = AdminGen.toHashLinked(data, nomAttr);
        for (String key : hashe.keySet()) {
            ClassMAPTable[] groupe = hashe.get(key);
            double somme = 0.0;
            somme = nomColValeur != null && nomColValeur.compareTo("nombrepargroupe") == 0 ? (double)groupe.length : AdminGen.calculSommeDouble(groupe, nomColValeur);
            dataChart.put(key, somme);
        }
        return dataChart;
    }

    public static Map<String, Double[]> getDataChartAvecNombre(ClassMAPTable[] data, String nomAttr, String[] nomColValeurListe) throws Exception {
        if (nomColValeurListe == null) {
            return null;
        }
        LinkedHashMap<String, Double[]> dataChart = new LinkedHashMap<String, Double[]>();
        LinkedHashMap<String, ClassMAPTable[]> hashe = AdminGen.toHashLinked(data, nomAttr);
        for (String key : hashe.keySet()) {
            ClassMAPTable[] groupe = hashe.get(key);
            Double[] somme = new Double[nomColValeurListe.length];
            for (int i = 0; i < nomColValeurListe.length; ++i) {
                String nomColValeur = nomColValeurListe[i];
                somme[i] = nomColValeur != null && nomColValeur.compareTo("nombrepargroupe") == 0 ? Double.valueOf(groupe.length) : Double.valueOf(AdminGen.calculSommeDouble(groupe, nomColValeur));
            }
            dataChart.put(key, somme);
        }
        return dataChart;
    }

    public static Map<String, String[]> getDataChartAvecNombreToString(ClassMAPTable[] data, String nomAttr, String[] nomColValeurListe) throws Exception {
        Map<String, Double[]> mapDouble = AdminGen.getDataChartAvecNombre(data, nomAttr, nomColValeurListe);
        Map<String, String[]> mapString = mapDouble.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> (String[])Arrays.stream((Object[])entry.getValue()).map(String::valueOf).toArray(String[]::new)));
        return mapString;
    }

    public static String[] getDistinct(ClassMAPTable[] liste, String attr) throws Exception {
        ArrayList<String> ls = new ArrayList<String>();
        for (ClassMAPTable c : liste) {
            String valeur = (String)CGenUtil.getValeurFieldByMethod(c, attr);
            if (ls.contains(valeur)) continue;
            ls.add(valeur);
        }
        return ls.toArray(new String[ls.size()]);
    }

    public static ClassMAPTable[] copier(ClassMAPTable[] aCopier) throws Exception {
        ClassMAPTable[] retour = new ClassMAPTable[aCopier.length];
        for (int i = 0; i < aCopier.length; ++i) {
            retour[i].genererPKInterne();
        }
        return retour;
    }

    public static void copierToTable(ClassMAPTable[] aCopier) throws Exception {
        for (int i = 0; i < aCopier.length; ++i) {
            aCopier[i].genererPKInterne();
            aCopier[i].insertToTableWithHisto("6");
        }
    }

    public static void setRangExequo(ClassMAPTable[] liste, String attrVal, String attrRang) throws Exception {
        liste[0].setValChamp(attrRang, (Object)"1");
        int rangResultat = 1;
        int mitovy = 0;
        for (int i = 1; i < liste.length; ++i) {
            if (liste[i].getValInsert(attrVal).equals(liste[i - 1].getValInsert(attrVal))) {
                ++mitovy;
            } else {
                rangResultat = rangResultat + mitovy + 1;
                mitovy = 0;
            }
            liste[i].setValChamp(attrRang, (Object)String.valueOf(rangResultat));
        }
    }

    public static void insertGroupe(ClassMAPTable[] a) throws Exception {
        for (int i = 0; i < a.length; ++i) {
            a[i].insertToTableWithHisto("6");
        }
    }

    public static void updateGroupe(ClassMAPTable[] a) throws Exception {
        for (int i = 0; i < a.length; ++i) {
            a[i].insertToTableWithHisto("6");
        }
    }

    public static ClassMAPTable[] findInList(ClassMAPTable[] liste, String[] attributET, String[] valET) throws Exception {
        Object[] retour = null;
        if (liste.length == 0) {
            return null;
        }
        Vector<ClassMAPTable> temp = new Vector<ClassMAPTable>();
        for (int i = 0; i < liste.length; ++i) {
            Object[] valeurListe = new Object[attributET.length];
            int nbMitovy = 0;
            for (int iAtr = 0; iAtr < attributET.length; ++iAtr) {
                valeurListe[iAtr] = CGenUtil.getValeurFieldByMethod(liste[i], attributET[iAtr]);
                if (valeurListe[iAtr].toString().compareTo(valET[iAtr]) != 0) break;
                ++nbMitovy;
            }
            if (nbMitovy != attributET.length) continue;
            temp.add(liste[i]);
        }
        retour = (ClassMAPTable[])Array.newInstance(((ClassMAPTable)liste[0].getClass().newInstance()).getClass(), temp.size());
        temp.copyInto(retour);
        return retour;
    }

    public static ClassMAPTable[] findAvecOrder(ClassMAPTable[] liste, String[] attributET, String[] valET) throws Exception {
        Object[] retour = null;
        if (liste == null || liste.length == 0) {
            return null;
        }
        Vector<ClassMAPTable> temp = new Vector<ClassMAPTable>();
        for (int i = 0; i < liste.length; ++i) {
            Object[] valeurListe = new Object[attributET.length];
            int nbMitovy = 0;
            for (int iAtr = 0; iAtr < attributET.length; ++iAtr) {
                valeurListe[iAtr] = CGenUtil.getValeurFieldByMethod(liste[i], attributET[iAtr]);
                if (valeurListe[iAtr] == null || valET[iAtr] == null || valeurListe[iAtr].toString().compareTo(valET[iAtr]) != 0) break;
                ++nbMitovy;
            }
            if (nbMitovy == attributET.length) {
                temp.add(liste[i]);
                continue;
            }
            if (temp.size() > 0) break;
        }
        retour = (ClassMAPTable[])Array.newInstance(((ClassMAPTable)liste[0].getClass().newInstance()).getClass(), temp.size());
        temp.copyInto(retour);
        return retour;
    }

    public static ClassMAPTable findUnique(ClassMAPTable[] liste, String[] attributET, String[] valET) throws Exception {
        for (int i = 0; i < liste.length; ++i) {
            Object[] valeurListe = new Object[attributET.length];
            int nbMitovy = 0;
            for (int iAtr = 0; iAtr < attributET.length; ++iAtr) {
                valeurListe[iAtr] = CGenUtil.getValeurFieldByMethod(liste[i], attributET[iAtr]);
                if (valeurListe[iAtr] == null) continue;
                if (valeurListe[iAtr].toString().compareTo(valET[iAtr]) != 0) break;
                ++nbMitovy;
            }
            if (nbMitovy != attributET.length) continue;
            return liste[i];
        }
        return null;
    }

    public static ClassMAPTable findUnique(Vector liste, String[] attributET, String[] valET) throws Exception {
        for (int i = 0; i < liste.size(); ++i) {
            Object[] valeurListe = new Object[attributET.length];
            int nbMitovy = 0;
            for (int iAtr = 0; iAtr < attributET.length; ++iAtr) {
                valeurListe[iAtr] = CGenUtil.getValeurFieldByMethod((ClassMAPTable)liste.elementAt(i), attributET[iAtr]);
                if (valeurListe[iAtr] == null) continue;
                if (valeurListe[iAtr].toString().compareTo(valET[iAtr]) != 0) break;
                ++nbMitovy;
            }
            if (nbMitovy != attributET.length) continue;
            return (ClassMAPTable)liste.elementAt(i);
        }
        return null;
    }

    public static HashMap<String, ClassMAPTable[]> toHash(ClassMAPTable[] lo, String attrCle) throws Exception {
        HashMap<String, ClassMAPTable[]> result = new HashMap<String, ClassMAPTable[]>();
        for (ClassMAPTable obj : lo) {
            String key = CGenUtil.getValeurInsert(obj, attrCle);
            result.putIfAbsent(key, new ClassMAPTable[0]);
            ClassMAPTable[] currentArray = result.get(key);
            ClassMAPTable[] newArray = new ClassMAPTable[currentArray.length + 1];
            System.arraycopy(currentArray, 0, newArray, 0, currentArray.length);
            newArray[currentArray.length] = obj;
            result.put(key, newArray);
        }
        return result;
    }

    public static ClassMAPTable[] fromExcel(String pathUrl, ClassMAPTable o) throws Exception {
        return XlsImport.toObjectFlexible(pathUrl, o);
    }

    public static void toExcel(String pathUrl, ClassMAPTable[] o) throws Exception {
        UtilExcel.fromObjectToExcelFlexible(pathUrl, o);
    }

    public static double[] findAvecSommeOrder(ClassMAPTable[] liste, String[] attributET, String[] valET, String[] asommer) throws Exception {
        Object retour = null;
        if (liste == null || liste.length == 0) {
            return null;
        }
        Vector<ClassMAPTable> temp = new Vector<ClassMAPTable>();
        double[] valiny = new double[asommer.length];
        for (int i = 0; i < liste.length; ++i) {
            Object[] valeurListe = new Object[attributET.length];
            int nbMitovy = 0;
            for (int iAtr = 0; iAtr < attributET.length; ++iAtr) {
                valeurListe[iAtr] = CGenUtil.getValeurFieldByMethod(liste[i], attributET[iAtr]);
                if (valeurListe[iAtr] == null || valeurListe[iAtr].toString() == null || valeurListe[iAtr].toString().compareTo(valET[iAtr]) != 0) break;
                ++nbMitovy;
            }
            if (nbMitovy == attributET.length) {
                for (int isom = 0; isom < asommer.length; ++isom) {
                    int n = isom;
                    valiny[n] = valiny[n] + new Double(CGenUtil.getValeurFieldByMethod(liste[i], asommer[isom]).toString());
                }
                temp.add(liste[i]);
                continue;
            }
            if (temp.size() > 0) break;
        }
        return valiny;
    }

    public static ClassMAPTable[] find(Vector liste, String[] attributET, String[] valET) throws Exception {
        if (liste == null || liste.size() == 0) {
            return null;
        }
        Vector temp = new Vector();
        for (int i = 0; i < liste.size(); ++i) {
            Object[] valeurListe = new Object[attributET.length];
            int nbMitovy = 0;
            for (int iAtr = 0; iAtr < attributET.length; ++iAtr) {
                valeurListe[iAtr] = CGenUtil.getValeurFieldByMethod((ClassMAPTable)liste.elementAt(i), attributET[iAtr]);
                if (valeurListe[iAtr] == null) continue;
                if (valeurListe[iAtr].toString().compareToIgnoreCase(valET[iAtr]) != 0) break;
                ++nbMitovy;
            }
            if (nbMitovy != attributET.length) continue;
            temp.add(liste.elementAt(i));
        }
        Object[] retour = (ClassMAPTable[])Array.newInstance(liste.elementAt(0).getClass().newInstance().getClass(), temp.size());
        temp.copyInto(retour);
        return retour;
    }

    public static ClassMAPTable findById(ClassMAPTable[] liste, String id) throws Exception {
        String[] valEt;
        if (liste == null || liste.length == 0) {
            return null;
        }
        String[] colEt = new String[]{liste[0].getAttributIDName()};
        ClassMAPTable[] retour = AdminGen.find(liste, colEt, valEt = new String[]{id});
        if (retour == null) {
            return null;
        }
        return retour[0];
    }

    public static ClassMAPTable[] find(ClassMAPTable[] liste, String[] attributET, String[] valET) throws Exception {
        if (liste == null || liste.length == 0) {
            return null;
        }
        Vector<ClassMAPTable> temp = new Vector<ClassMAPTable>();
        for (int i = 0; i < liste.length; ++i) {
            Object[] valeurListe = new Object[attributET.length];
            int nbMitovy = 0;
            for (int iAtr = 0; iAtr < attributET.length; ++iAtr) {
                valeurListe[iAtr] = CGenUtil.getValeurFieldByMethod(liste[i], attributET[iAtr]);
                if (valeurListe[iAtr] == null) continue;
                if (valeurListe[iAtr] instanceof Number) {
                    double valDroite;
                    double valgauche = ((Number)valeurListe[iAtr]).doubleValue();
                    if (valgauche != (valDroite = Utilitaire.string_to_double(valET[iAtr]))) continue;
                    ++nbMitovy;
                    continue;
                }
                if (valeurListe[iAtr].toString().compareTo(valET[iAtr]) != 0) break;
                ++nbMitovy;
            }
            if (nbMitovy != attributET.length) continue;
            temp.add(liste[i]);
        }
        if (temp.size() == 0) {
            return null;
        }
        Object[] retour = (ClassMAPTable[])Array.newInstance(((ClassMAPTable)liste[0].getClass().newInstance()).getClass(), temp.size());
        temp.copyInto(retour);
        return retour;
    }

    public static ClassMAPTable[] find(ClassMAPTable[] liste, String[] attributET, String[] valET, String[] colInt, String[] valInt) throws Exception {
        if (liste == null || liste.length == 0) {
            return null;
        }
        Vector<ClassMAPTable> temp = new Vector<ClassMAPTable>();
        for (int i = 0; i < liste.length; ++i) {
            int nbMitovy = 0;
            if (attributET != null) {
                Object[] valeurListe = new Object[attributET.length];
                for (int iAtr = 0; iAtr < attributET.length; ++iAtr) {
                    valeurListe[iAtr] = CGenUtil.getValeurFieldByMethod(liste[i], attributET[iAtr]);
                    if (valeurListe[iAtr] == null) {
                        if (valET[iAtr] != null) continue;
                        ++nbMitovy;
                        continue;
                    }
                    if (valeurListe[iAtr].toString().compareTo(valET[iAtr]) != 0) break;
                    ++nbMitovy;
                }
            }
            int nbMitovyInterv = 0;
            if (valInt != null) {
                Object[] valeurInterv = new Object[colInt.length];
                for (int j = 0; j < colInt.length; ++j) {
                    valeurInterv[j] = CGenUtil.getValeurFieldByMethod(liste[i], colInt[j]);
                    if (valeurInterv[j] == null) continue;
                    if (valeurInterv[j] instanceof Number) {
                        if (!(((Number)valeurInterv[j]).doubleValue() >= Utilitaire.stringToDouble(valInt[2 * j])) || !(((Number)valeurInterv[j]).doubleValue() <= Utilitaire.stringToDouble(valInt[2 * j + 1]))) break;
                        ++nbMitovyInterv;
                    }
                    if (!(valeurInterv[j] instanceof Date)) continue;
                    if (Utilitaire.compareDaty((Date)valeurInterv[j], Utilitaire.stringDate(valInt[2 * j])) < 0 || Utilitaire.compareDaty((Date)valeurInterv[j], Utilitaire.stringDate(valInt[2 * j + 1])) > 0) break;
                    ++nbMitovyInterv;
                }
            }
            if (attributET != null && nbMitovy != attributET.length || colInt != null && nbMitovyInterv != colInt.length) continue;
            temp.add(liste[i]);
        }
        Object[] retour = (ClassMAPTable[])Array.newInstance(((ClassMAPTable)liste[0].getClass().newInstance()).getClass(), temp.size());
        temp.copyInto(retour);
        return retour;
    }

    public static ResultatGroupe[] findGroupe(ClassMAPTable[] e, int numColGroupe, int numColSomme) {
        Object[] rg = null;
        Vector<ResultatGroupe> rTemp = new Vector<ResultatGroupe>();
        if (e.length > 0) {
            Object o = e[0].getValeur(numColSomme);
            Double montD = (Double)o;
            double mont = montD;
            rTemp.add(new ResultatGroupe((String)e[0].getValeur(numColGroupe), mont, 1.0));
        }
        for (int i = 1; i < e.length; ++i) {
            double mont;
            Double montD;
            Object o;
            ResultatGroupe et = (ResultatGroupe)Utilitaire.extraire(rTemp, 0, (String)e[i].getValeur(numColGroupe));
            if (et == null) {
                o = e[i].getValeur(numColSomme);
                montD = (Double)o;
                mont = montD;
                rTemp.add(new ResultatGroupe((String)e[i].getValeur(numColGroupe), mont, 1.0));
                continue;
            }
            o = e[i].getValeur(numColSomme);
            montD = (Double)o;
            mont = montD;
            et.setSomme(et.getSomme() + mont);
            et.setNombre(et.getNombre() + 1.0);
        }
        rg = new ResultatGroupe[rTemp.size()];
        rTemp.copyInto(rg);
        return rg;
    }

    public static ClassEtat[] findObjet(ClassEtat[] liste, String[] attributET, String[] valET) throws Exception {
        Object[] retour = null;
        if (liste.length == 0) {
            return null;
        }
        Vector<ClassEtat> temp = new Vector<ClassEtat>();
        for (int i = 0; i < liste.length; ++i) {
            Object[] valeurListe = new Object[attributET.length];
            int nbMitovy = 0;
            for (int iAtr = 0; iAtr < attributET.length; ++iAtr) {
                valeurListe[iAtr] = CGenUtil.getValeurFieldByMethod((ClassMAPTable)liste[i], attributET[iAtr]);
                if (valeurListe[iAtr] == null || valeurListe[iAtr].toString().compareToIgnoreCase(valET[iAtr]) != 0) continue;
                ++nbMitovy;
            }
            if (nbMitovy != attributET.length) continue;
            temp.add(liste[i]);
        }
        retour = (ClassEtat[])Array.newInstance(((ClassEtat)liste[0].getClass().newInstance()).getClass(), temp.size());
        temp.copyInto(retour);
        return retour;
    }

    public static boolean testDedansIgnoreCase(ClassMAPTable[] e, int[] numColGroupe, int[] numColSomme) {
        return true;
    }

    public static boolean estDedans(ClassMAPTable[] e, String[] nomChamps, String[] val) throws Exception {
        if (nomChamps.length != val.length) {
            throw new Exception("Longeur de champs et valeurs doivent etre egal");
        }
        int equal = 0;
        for (int i = 0; i < e.length; ++i) {
            ClassMAPTable cm = e[i];
            for (int j = 0; j < nomChamps.length; ++j) {
                String nomChamp = nomChamps[j];
                String valChamp = val[j];
                String valy = (String)CGenUtil.getValeurFieldByMethod(cm, nomChamp);
                System.out.println("invoked :" + valy);
                System.out.println("vallll " + valChamp);
                if (valy.compareToIgnoreCase(valChamp) != 0) continue;
                ++equal;
            }
        }
        return equal == nomChamps.length;
    }

    public static RgMultiple[] findGroupeMultiple(ClassMAPTable[] e, int[] numColGroupe, int[] numColSomme) {
        Object[] rg = null;
        Vector<RgMultiple> rTemp = new Vector<RgMultiple>();
        try {
            if (e.length > 0) {
                double[] montD = new double[numColSomme.length];
                for (int i = 0; i < numColSomme.length; ++i) {
                    Object o = e[0].getValeur(numColSomme[i]);
                    Double temp = (Double)o;
                    montD[i] = temp;
                }
                String[] colListe = new String[numColGroupe.length];
                for (int i = 0; i < numColGroupe.length; ++i) {
                    colListe[i] = (String)e[0].getValeur(numColGroupe[i]);
                }
                rTemp.add(new RgMultiple(colListe, montD, 1));
            }
            for (int i = 1; i < e.length; ++i) {
                Double temp;
                double[] montD;
                String[] colVal = new String[numColGroupe.length];
                for (int k = 0; k < numColGroupe.length; ++k) {
                    colVal[k] = (String)e[i].getValeur(numColGroupe[k]);
                }
                RgMultiple et = (RgMultiple)Utilitaire.extraireMultiple(rTemp, 0, numColGroupe, colVal);
                if (et == null) {
                    montD = new double[numColSomme.length];
                    for (int ik = 0; ik < numColSomme.length; ++ik) {
                        Object o = e[i].getValeur(numColSomme[ik]);
                        temp = (Double)o;
                        montD[ik] = temp;
                    }
                    String[] colListe = new String[numColGroupe.length];
                    for (int ij = 0; ij < numColGroupe.length; ++ij) {
                        colListe[ij] = (String)e[i].getValeur(numColGroupe[ij]);
                    }
                    rTemp.add(new RgMultiple(colListe, montD, 1));
                    continue;
                }
                montD = new double[numColSomme.length];
                for (int ik = 0; ik < numColSomme.length; ++ik) {
                    Object o = e[i].getValeur(numColSomme[ik]);
                    temp = (Double)o;
                    montD[ik] = temp + et.getSommeGroupe()[ik];
                }
                et.setSommeGroupe(montD);
                et.setNombre(et.getNombre() + 1);
            }
            rg = new RgMultiple[rTemp.size()];
            rTemp.copyInto(rg);
            return rg;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static double calculSommeDouble(ClassMAPTable[] c, int indice) {
        double somme = 0.0;
        try {
            for (int i = 0; i < c.length; ++i) {
                Object o = c[i].getValeur(indice);
                somme += ((Number)o).doubleValue();
            }
            double f = somme;
            return f;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            double f1 = 0.0;
            return f1;
        }
    }

    public static double calculSommeDouble(ClassMAPTable[] c, String nomCol) {
        double somme = 0.0;
        try {
            for (int i = 0; i < c.length; ++i) {
                Object o = CGenUtil.getValeurFieldByMethod(c[i], nomCol);
                if (o instanceof Integer) {
                    somme += ((Integer)o).doubleValue();
                    continue;
                }
                somme += Double.parseDouble(String.valueOf(o));
            }
            return (double)Math.round(somme * 100.0) / 100.0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0.0;
        }
    }

    public static double calculSommeDouble(ClassMAPTable[] c, String nomCol, int indInf, int indFinal) {
        double somme = 0.0;
        try {
            if (indFinal >= c.length) {
                indFinal = c.length - 1;
            }
            for (int i = indInf; i <= indFinal; ++i) {
                Object o = CGenUtil.getValeurFieldByMethod(c[i], nomCol);
                somme += ((Number)o).doubleValue();
            }
            double f = somme;
            return f;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            double f1 = 0.0;
            return f1;
        }
    }

    public static double calculSommeDoubleSi(ClassMAPTable[] c, String nomCol, String nomColSi, Object valeurSi) throws Exception {
        double somme = 0.0;
        try {
            for (int i = 0; i < c.length; ++i) {
                Object objSi = CGenUtil.getValeurFieldByMethod(c[i], nomColSi);
                if (objSi == null || !objSi.equals(valeurSi)) continue;
                Object o = CGenUtil.getValeurFieldByMethod(c[i], nomCol);
                somme += ((Number)o).doubleValue();
            }
            double f = somme;
            return f;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            double f1 = 0.0;
            return f1;
        }
    }

    public static double[] calculSommeDouble(ClassMAPTable[] c, String[] indice) {
        double[] somme = new double[indice.length];
        try {
            for (int i = 0; i < c.length; ++i) {
                int j = 0;
                while (j < indice.length) {
                    Object o = CGenUtil.getValeurFieldByMethod(c[i], indice[j]);
                    int n = j++;
                    somme[n] = somme[n] + ((Number)o).doubleValue();
                }
            }
            return somme;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ClassMAPTable[] trier(ClassMAPTable[] liste, String[] nomCol, String[] colOrdre) throws Exception {
        if (liste.length == 0) {
            return null;
        }
        if (nomCol.length != colOrdre.length) {
            throw new Exception("Taille de colonne de tri et de valeur differente");
        }
        for (int k = 0; k < liste.length - 1; ++k) {
            for (int i = 0; i < liste.length - 1; ++i) {
                for (int iCol = 0; iCol < nomCol.length; ++iCol) {
                    ClassMAPTable temp;
                    int reponse = liste[i].compareTo(liste[i + 1], nomCol[iCol]);
                    if (reponse == 0) continue;
                    if (reponse > 0 && (colOrdre[iCol] == null || colOrdre[iCol].compareToIgnoreCase("") == 0 || colOrdre[iCol].compareToIgnoreCase("ASC") == 0)) {
                        temp = liste[i];
                        liste[i] = liste[i + 1];
                        liste[i + 1] = temp;
                    }
                    if (reponse >= 0 || colOrdre[iCol].compareToIgnoreCase("DESC") != 0) continue;
                    temp = liste[i];
                    liste[i] = liste[i + 1];
                    liste[i + 1] = temp;
                }
            }
        }
        return liste;
    }

    public static String calculSommeDoubleFormatte(ClassMAPTable[] c, int indice) {
        return Utilitaire.formaterAr(AdminGen.calculSommeDouble(c, indice));
    }

    public Map<String, Double> getValeurParColonne(ClassMAPTable[] liste, String nomCol, String nomColval) throws Exception {
        LinkedHashMap<String, Double> retour = new LinkedHashMap<String, Double>();
        for (ClassMAPTable c : liste) {
            String cle = (String)CGenUtil.getValeurFieldByMethod(c, nomCol);
            Double val = (Double)CGenUtil.getValeurFieldByMethod(c, nomColval);
            retour.put(cle, val);
        }
        return retour;
    }

    public static float calculSomme(ClassMAPTable[] c, int indice) {
        float somme = 0.0f;
        try {
            for (int i = 0; i < c.length; ++i) {
                Double d = new Double((String)c[i].getValeur(indice));
                somme = (float)((double)somme + d);
            }
            float f = somme;
            return f;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0.0f;
        }
    }

    public static Object[] findCast(List liste, String[] attributET, String[] valET) throws Exception {
        ClassMAPTable[] listeTab = new ClassMAPTable[liste.size()];
        liste.toArray(listeTab);
        return AdminGen.findCast(listeTab, attributET, valET);
    }

    public static Object[] findCast(ClassMAPTable[] liste, String[] attributET, String[] valET) throws Exception {
        Vector<ClassMAPTable> temp = new Vector<ClassMAPTable>();
        for (int i = 0; i < liste.length; ++i) {
            Object[] valeurListe = new Object[attributET.length];
            int nbMitovy = 0;
            for (int iAtr = 0; iAtr < attributET.length; ++iAtr) {
                valeurListe[iAtr] = CGenUtil.getValeurFieldByMethod(liste[i], attributET[iAtr]);
                if (valeurListe[iAtr].toString().compareTo(valET[iAtr]) != 0) break;
                ++nbMitovy;
            }
            if (nbMitovy != attributET.length) continue;
            temp.add(liste[i]);
        }
        Object[] retour = (Object[])Array.newInstance(((ClassMAPTable)liste[0].getClass().newInstance()).getClass(), temp.size());
        temp.copyInto(retour);
        return retour;
    }

    public static ClassMere fusionner(ClassMere cible, ClassMere[] hoFusion) throws Exception {
        try {
            ArrayList<ClassFille> filles = new ArrayList<ClassFille>();
            filles.addAll(Arrays.asList(cible.getFille()));
            for (int i = 0; i < hoFusion.length; ++i) {
                for (int j = 0; j < hoFusion[i].getFille().length; ++j) {
                    filles.add(hoFusion[i].getFille()[j]);
                }
            }
            cible.setFille(filles.toArray(new ClassFille[0]));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return cible;
    }

    public static ClassMere[] fusionnerMultiple(ClassMere[] object, String attr) throws Exception {
        try {
            object = (ClassMere[])AdminGen.trier(object, new String[]{attr}, new String[]{"ASC"});
            ArrayList<ClassMere> meres = new ArrayList<ClassMere>();
            meres.add(object[0]);
            int indice = 0;
            for (int i = 1; i < object.length; ++i) {
                String last = object[i - 1].getClass().getDeclaredMethod("get" + Utilitaire.convertDebutMajuscule(attr), new Class[0]).invoke(object[i - 1], new Object[0]).toString();
                String temp = object[i].getClass().getDeclaredMethod("get" + Utilitaire.convertDebutMajuscule(attr), new Class[0]).invoke(object[i], new Object[0]).toString();
                if (temp.equalsIgnoreCase(last)) {
                    ClassMere[] t = new ClassMere[]{object[i]};
                    ClassMere classMere = AdminGen.fusionner((ClassMere)meres.get(indice), t);
                    continue;
                }
                meres.add(object[i]);
                ++indice;
            }
            return meres.toArray(new ClassMere[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors du fusion multiple : " + e.getMessage());
        }
    }

    public static ClassMere[] scinder(ClassMere[] object) throws Exception {
        try {
            ArrayList<ClassMere> meres = new ArrayList<ClassMere>();
            for (int i = 0; i < object.length; ++i) {
                ClassFille[] tempfille = object[i].getFille();
                for (int j = 0; j < tempfille.length; ++j) {
                    ClassMere me = (ClassMere)Class.forName(object[i].getClass().getName()).newInstance();
                    ClassFille[] tf = new ClassFille[]{tempfille[j]};
                    me.setFille(tf);
                    meres.add(me);
                }
            }
            return meres.toArray(new ClassMere[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors du scindage : " + e.getMessage());
        }
    }

    public static boolean testExiste(ClassMAPTable[] liste, String[] attributET, String[] valET) throws Exception {
        for (int i = 0; i < liste.length; ++i) {
            Object[] valeurListe = new Object[attributET.length];
            int nbMitovy = 0;
            for (int iAtr = 0; iAtr < attributET.length; ++iAtr) {
                valeurListe[iAtr] = CGenUtil.getValeurFieldByMethod(liste[i], attributET[iAtr]);
                if (valeurListe[iAtr] == null) continue;
                if (valeurListe[iAtr].toString().compareTo(valET[iAtr]) != 0) break;
                ++nbMitovy;
            }
            if (nbMitovy != attributET.length) continue;
            return true;
        }
        return false;
    }

    public static ClassMAPTable getById(ClassMAPTable[] liste, String id) throws Exception {
        String[] val;
        if (liste == null || liste.length == 0) {
            return null;
        }
        String[] col = new String[]{liste[0].getTuppleID()};
        ClassMAPTable[] retour = AdminGen.find(liste, col, val = new String[]{id});
        if (retour != null) {
            return retour[0];
        }
        return null;
    }

    public static String[] grouperDistinctString(ClassMAPTable[] liste, String nomAttr) throws Exception {
        ArrayList listeValiny = new ArrayList();
        for (ClassMAPTable iter : liste) {
            listeValiny.add((String)CGenUtil.getValeurFieldByMethod(iter, nomAttr));
        }
        listeValiny = (ArrayList)listeValiny.stream().distinct().collect(Collectors.toList());
        return listeValiny.toArray(new String[listeValiny.size()]);
    }

    public static int updateBatch(ClassMAPTable[] liste, Connection c) throws Exception {
        try (Statement cmd = null;){
            cmd = c.createStatement();
            for (ClassMAPTable obj : liste) {
                String req = obj.getComandeUpdate(c);
                cmd.addBatch(req);
                String recSync = obj.genererHistoSync("UPDATE", req);
                if (recSync == null || recSync.compareToIgnoreCase("") == 0) continue;
                cmd.addBatch(recSync);
            }
            int n = cmd.executeBatch().length;
            return n;
        }
    }

    public static int insertBatch(ClassMAPTable[] liste, Connection c) throws Exception {
        try (Statement cmd = null;){
            cmd = c.createStatement();
            for (ClassMAPTable obj : liste) {
                obj.construirePK(c);
                String req = obj.getComandeInsert(c);
                cmd.addBatch(req);
                String recSync = obj.genererHistoSync("INSERT", req);
                if (recSync == null || recSync.compareToIgnoreCase("") == 0) continue;
                cmd.addBatch(recSync);
            }
            int n = cmd.executeBatch().length;
            return n;
        }
    }
}

