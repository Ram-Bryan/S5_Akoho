/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  configuration.CynthiaConf
 *  org.apache.commons.lang3.StringEscapeUtils
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.HttpVersion
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.entity.mime.MultipartEntity
 *  org.apache.http.entity.mime.content.ContentBody
 *  org.apache.http.entity.mime.content.InputStreamBody
 *  org.apache.http.entity.mime.content.StringBody
 *  org.apache.http.impl.client.DefaultHttpClient
 */
package utilitaire;

import bean.CGenUtil;
import bean.Champ;
import bean.ClassMAPTable;
import bean.TypeObjet;
import bean.ValeurEtiquette;
import configuration.CynthiaConf;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import utilitaire.ChiffreLettre;
import utilitaire.Constante;
import utilitaire.Parametre;
import utilitaire.PropertiesLoader;
import utilitaire.UtilDB;

public class Utilitaire
extends Parametre {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static final char[] listSeparator = new char[]{' ', '-', '_', ',', '/', ';'};
    static Random r = new Random();

    public static String genererChampReturnMultiple(String champRet, int numeroLigne) {
        String[] retTab = champRet.split(";");
        String retour = "";
        for (int i = 0; i < retTab.length; ++i) {
            retour = retour + retTab[i] + "_" + numeroLigne + ";";
        }
        return retour;
    }

    public static String heureCouranteHMSSansSeparateur() {
        Calendar a = Calendar.getInstance();
        String retour = null;
        retour = String.valueOf(String.valueOf(Utilitaire.completerInt(2, a.get(11)))).concat("");
        retour = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(retour))).append(Utilitaire.completerInt(2, a.get(12))).append("")));
        retour = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(retour))).append(Utilitaire.completerInt(2, a.get(13)))));
        return retour;
    }

    public static int doubleToInt(String string) {
        Double d = new Double(string);
        int i = d.intValue();
        return i;
    }

    public static String[] formerTableauGroupe(String[] val) throws Exception {
        Object[] retour = null;
        Vector<String> r = new Vector<String>();
        for (int i = 0; i < val.length; ++i) {
            if (val[i] == null || val[i].compareToIgnoreCase("") == 0 || val[i].compareToIgnoreCase("-") == 0) continue;
            r.add(val[i]);
        }
        if (r.size() > 0) {
            retour = new String[r.size()];
            r.copyInto(retour);
        }
        return retour;
    }

    public static String getRangMoisLettre(int rangMois) {
        String[] r = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        return r[rangMois - 1];
    }

    public static String findSeparator(String text) {
        String ret = " ";
        for (int i = 0; i < text.length(); ++i) {
            if (!Utilitaire.isSeparator(text.charAt(i))) continue;
            ret = text.charAt(i) + "";
        }
        return ret;
    }

    public static boolean isSeparator(char caractere) {
        for (int k = 0; k < listSeparator.length; ++k) {
            if (caractere != listSeparator[k]) continue;
            return true;
        }
        return false;
    }

    public static int valeurCharEnChiffre(String caract) {
        String[] r = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", " ", "-", "\""};
        for (int i = 0; i < r.length; ++i) {
            if (caract.compareToIgnoreCase(caract) != 0) continue;
            return i + 1;
        }
        return 30;
    }

    public static String getAnneeEnCours() {
        String daty = Utilitaire.dateDuJour();
        String an = Utilitaire.getAnnee(daty);
        return an;
    }

    public static String secondesToTime(int msecondes) {
        int secondes = 0;
        int minutes = 0;
        int heures = 0;
        secondes = msecondes % 60;
        minutes = (msecondes /= 60) % 60;
        heures = msecondes /= 60;
        return heures + ":" + minutes + ":" + secondes;
    }

    public static int timeToMillisecondes(String time) {
        int secondes = 0;
        int minutesMS = 0;
        int heuresMS = 0;
        int sec = 0;
        if (time != null || time.compareToIgnoreCase("") != 0) {
            String[] splt = time.split(":");
            if (splt.length > 2) {
                secondes = Utilitaire.stringToInt(splt[2]);
                minutesMS = Utilitaire.stringToInt(splt[1]) * 60;
                heuresMS = Utilitaire.stringToInt(splt[0]) * 3600;
            } else {
                minutesMS = Utilitaire.stringToInt(splt[1]) * 60;
                heuresMS = Utilitaire.stringToInt(splt[0]) * 3600;
            }
            sec = secondes + minutesMS + heuresMS;
        }
        return sec;
    }

    public static int diffDeuxheures(String heuredebut, int seconde) {
        int result = 0;
        String[] debut = Utilitaire.split(heuredebut, ":");
        int hmsd = Utilitaire.stringToInt(debut[0]) * 3600;
        int mmsd = Utilitaire.stringToInt(debut[1]) * 60;
        result = hmsd + mmsd + seconde;
        return result;
    }

    public static int diffDeuxheures(String heuredebut, String heurefin) {
        int result = 0;
        String[] debut = Utilitaire.split(heuredebut, ":");
        String[] fin = Utilitaire.split(heurefin, ":");
        int secondeDebut = 0;
        int secondeFin = 0;
        if (debut.length > 2) {
            secondeDebut = Utilitaire.stringToInt(debut[2]);
        }
        if (fin.length > 2) {
            secondeFin = Utilitaire.stringToInt(fin[2]);
        }
        int hmsd = Utilitaire.stringToInt(debut[0]) * 3600;
        int mmsd = Utilitaire.stringToInt(debut[1]) * 60;
        int hmsf = Utilitaire.stringToInt(fin[0]) * 3600;
        int mmsf = Utilitaire.stringToInt(fin[1]) * 60;
        result = hmsf + mmsf + secondeFin - (hmsd + mmsd + secondeDebut);
        return result;
    }

    public static double dechiffrer(String caractere) {
        String var_car_act = "";
        double var_total = 0.0;
        String temp = caractere.trim();
        int var_long = temp.length();
        for (int i = 0; i < var_long; ++i) {
            var_car_act = temp.substring(i, i + 1);
            int var_val_act = Utilitaire.valeurCharEnChiffre(var_car_act);
            int var_coeff = var_long - i;
            var_total += (double)(var_coeff * var_val_act);
        }
        return var_total * (double)var_long;
    }

    public static java.util.Date convertFromSQLDateToUtilDate(Date sqlDate) {
        Date javaDate = null;
        if (sqlDate != null) {
            javaDate = new Date(sqlDate.getTime());
        }
        return javaDate;
    }

    public static String getLastDayOfDate(String daty) {
        String ret = "";
        try {
            Date dt = Utilitaire.stringDate(daty);
            ret = Utilitaire.getLastDayOfDate(dt);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    public static int getTrimestre(Date d) {
        int month = d.getMonth();
        int reste = (month + 1) % 3;
        if (reste != 0) {
            return (month + 1) / 3 + 1;
        }
        if (reste == 0) {
            return (month + 1) / 3;
        }
        return 0;
    }

    public static String getDateFinAPartirTrimestre(int trimestre, int annee) {
        int mois = trimestre * 3;
        if (mois == 12) {
            return "31/" + mois + "/" + annee;
        }
        if (mois == 9) {
            return "30/" + mois + "/" + annee;
        }
        if (mois == 6) {
            return "30/" + mois + "/" + annee;
        }
        if (mois == 3) {
            return "31/" + mois + "/" + annee;
        }
        return "";
    }

    public static Date getLastDateInTrimestre(Date d) {
        if (d.after(Utilitaire.stringDate("01/01/" + Utilitaire.getAnnee(d))) && d.before(Utilitaire.stringDate("31/03/" + Utilitaire.getAnnee(d)))) {
            d = Utilitaire.stringDate("31/03/" + Utilitaire.getAnnee(d));
        }
        if (d.after(Utilitaire.stringDate("01/04/" + Utilitaire.getAnnee(d))) && d.before(Utilitaire.stringDate("30/06/" + Utilitaire.getAnnee(d)))) {
            d = Utilitaire.stringDate("30/06/" + Utilitaire.getAnnee(d));
        }
        if (d.after(Utilitaire.stringDate("01/07/" + Utilitaire.getAnnee(d))) && d.before(Utilitaire.stringDate("30/09/" + Utilitaire.getAnnee(d)))) {
            d = Utilitaire.stringDate("30/09/" + Utilitaire.getAnnee(d));
        }
        if (d.after(Utilitaire.stringDate("01/10/" + Utilitaire.getAnnee(d))) && d.before(Utilitaire.stringDate("31/12/" + Utilitaire.getAnnee(d)))) {
            d = Utilitaire.stringDate("31/12/" + Utilitaire.getAnnee(d));
        }
        return d;
    }

    public static String getTrimestreAnnee(Date d, String separateur) {
        int trim = Utilitaire.getTrimestre(d);
        int annee = Utilitaire.getAnnee(d);
        return trim + separateur + annee;
    }

    public static String getLastDateInTrimestre(String trimAnnee, String separateur) {
        String[] g = Utilitaire.split(trimAnnee, separateur);
        return Utilitaire.getDateFinAPartirTrimestre(Utilitaire.stringToInt(g[0]), Utilitaire.stringToInt(g[1]));
    }

    public static String moisEtAnneeToDate2(int month, int year, String minOuMax) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        switch (minOuMax) {
            case "min": {
                calendar.set(5, calendar.getActualMinimum(5));
                break;
            }
            case "max": {
                calendar.set(5, calendar.getActualMaximum(5));
            }
        }
        java.util.Date date = calendar.getTime();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        return DATE_FORMAT.format(date);
    }

    public static int getLastJourInMonth(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        int maxDay = calendar.getActualMaximum(5);
        return maxDay;
    }

    public static int getLastJourInMonth(Date d) {
        int year = d.getYear() + 1900;
        int month = d.getMonth();
        int day = d.getDate();
        return Utilitaire.getLastJourInMonth(year, month, day);
    }

    public static int dayOfDate(Date daty) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(daty);
        int day = cal.get(7);
        return day;
    }

    public static String getJourDate(Date daty) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(daty);
        int day = cal.get(7);
        switch (day) {
            case 1: {
                return "Dimanche";
            }
            case 2: {
                return "Lundi";
            }
            case 3: {
                return "Mardi";
            }
            case 4: {
                return "Mercredi";
            }
            case 5: {
                return "Jeudi";
            }
            case 6: {
                return "Vendredi";
            }
            case 7: {
                return "Samedi";
            }
        }
        return null;
    }

    public static String getLastDayOfDate(Date daty) {
        String ret = "";
        try {
            java.util.Date dt = Utilitaire.convertFromSQLDateToUtilDate(daty);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dt);
            calendar.add(2, 1);
            calendar.set(5, 1);
            calendar.add(5, -1);
            java.util.Date lastDayOfMonth = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(lastDayOfMonth);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return ret;
        }
    }

    public static Date getLastDayOfDateSQL(Date daty) {
        Date ret = null;
        try {
            java.util.Date dt = Utilitaire.convertFromSQLDateToUtilDate(daty);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dt);
            calendar.add(2, 1);
            calendar.set(5, 1);
            calendar.add(5, -1);
            java.util.Date lastDayOfMonth = calendar.getTime();
            ret = new Date(lastDayOfMonth.getTime());
            return ret;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return ret;
        }
    }

    public static String decimalToHexa(double number) {
        int puissance = 1;
        String petit_hexa = "";
        while (number / Math.pow(16.0, puissance) >= 16.0) {
            ++puissance;
        }
        double nombre_dec1 = number;
        do {
            double temp = nombre_dec1 / Math.pow(16.0, puissance);
            String val_prov = String.valueOf(temp);
            nombre_dec1 = number % Math.pow(16.0, puissance);
            val_prov = val_prov.compareToIgnoreCase("10") == 0 ? "A" : (val_prov.compareToIgnoreCase("11") == 0 ? "B" : (val_prov.compareToIgnoreCase("12") == 0 ? "C" : (val_prov.compareToIgnoreCase("13") == 0 ? "D" : (val_prov.compareToIgnoreCase("14") == 0 ? "E" : "F"))));
            petit_hexa = petit_hexa + val_prov;
        } while (--puissance >= 0);
        return petit_hexa;
    }

    public static boolean isStringNumeric(String str) {
        if (str == null || str.compareTo("") == 0) {
            return false;
        }
        DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
        char localeMinusSign = currentLocaleSymbols.getMinusSign();
        boolean isDecimalSeparatorFound = false;
        char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();
        for (char c : str.substring(1).toCharArray()) {
            if (Character.isDigit(c)) continue;
            if (c == localeDecimalSeparator && !isDecimalSeparatorFound && c != ' ') {
                isDecimalSeparatorFound = true;
                continue;
            }
            return false;
        }
        return true;
    }

    public static String convertDatyFormtoRealDatyFormat(String daty) {
        if (daty == null || daty.compareToIgnoreCase("") == 0) {
            return "";
        }
        String[] tableau = new String[3];
        tableau = Utilitaire.split(daty, "-");
        String result = "";
        if (tableau.length == 3) {
            result = tableau[2] + "/" + tableau[1] + "/" + tableau[0];
        }
        return result;
    }

    public static String[] ajouterTableauString(String[] s1, String[] s2) {
        String[] retour = new String[s1.length + s2.length];
        int i = 0;
        for (i = 0; i < s1.length; ++i) {
            retour[i] = s1[i];
        }
        for (int j = 0; j < s2.length; ++j) {
            retour[i + j] = s2[j];
        }
        return retour;
    }

    public static String[] concatener(String[] t1, String[] t2) {
        int taille = t1.length + t2.length;
        String[] retour = new String[taille];
        for (int i = 0; i < t1.length; ++i) {
            retour[i] = t1[i];
        }
        for (int j = t1.length; j < taille; ++j) {
            retour[j] = t2[j - t1.length];
        }
        return retour;
    }

    public static String getGenre(String sexe) {
        if (sexe.compareTo("1") == 0) {
            return "Femme";
        }
        if (sexe.compareTo("0") == 0) {
            return "Homme";
        }
        return null;
    }

    public static String champNull(String nul) {
        if (nul == null) {
            return "";
        }
        if (nul.compareToIgnoreCase("null") == 0) {
            return "";
        }
        if (nul.compareToIgnoreCase("") == 0) {
            return "";
        }
        return nul;
    }

    public static String enleverNonChiffre(String s) {
        String listevalide = "-0123456789.";
        char[] lc = listevalide.toCharArray();
        String ch = "";
        int l = s.length();
        for (int i = 0; i < l; ++i) {
            char c = s.charAt(i);
            for (int j = 0; j < lc.length; ++j) {
                if (c != lc[j]) continue;
                ch = ch + c;
            }
        }
        return ch;
    }

    public static String enleverEspace(String s) {
        StringBuilder resultat = new StringBuilder();
        if (!s.equalsIgnoreCase("-") || !s.isEmpty()) {
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (Character.isWhitespace(c) || Character.isSpaceChar(c)) continue;
                resultat.append(c);
            }
        }
        return resultat.toString();
    }

    public static String replaceChar(String s) {
        s = s.replace('*', '%');
        s = s.replace(',', '%');
        return s;
    }

    public static String replaceChar(String text, String toReplace, String substitute) {
        String ret = text.replace(toReplace.charAt(0), substitute.charAt(0));
        return ret;
    }

    public static String diffDeuxheures(String[] heured, String[] heuref) {
        int hours;
        int minutes;
        String result = new String();
        if (Utilitaire.stringToInt(heured[1]) > Utilitaire.stringToInt(heuref[1])) {
            minutes = Utilitaire.stringToInt(heured[1]) - Utilitaire.stringToInt(heuref[1]);
            hours = Utilitaire.stringToInt(heuref[0]) - Utilitaire.stringToInt(heured[0]) - 1;
        } else {
            minutes = Utilitaire.stringToInt(heuref[1]) - Utilitaire.stringToInt(heured[1]);
            hours = Utilitaire.stringToInt(heuref[0]) - Utilitaire.stringToInt(heured[0]);
        }
        String h = hours < 10 ? "0" + hours : "" + hours;
        String mn = minutes < 10 ? "0" + minutes : "" + minutes;
        result = "" + h + ":" + mn + "";
        return result;
    }

    public static String sommeHeures(String[] heure) {
        String result = new String();
        int sommeh = 0;
        int sommemn = 0;
        String hours = "";
        String mn = "";
        for (int i = 0; i < heure.length; ++i) {
            String[] g = Utilitaire.split(heure[i], ":");
            sommeh += Utilitaire.stringToInt(g[0]);
            sommemn += Utilitaire.stringToInt(g[1]);
        }
        int x = sommemn / 60;
        if (x > 0) {
            sommeh += x;
            sommemn %= 60;
        }
        hours = sommeh < 10 ? "0" + sommeh : "" + sommeh;
        mn = sommemn < 10 ? "0" + sommemn : "" + sommemn;
        result = "" + hours + ":" + mn + "";
        return result;
    }

    public static String getDebutAnnee(String annee) {
        return "01/01/" + annee;
    }

    public static String[] split(String mot, String sep) {
        StringTokenizer tokenizer = new StringTokenizer(mot, sep);
        Vector<String> v = new Vector<String>();
        while (tokenizer.hasMoreTokens()) {
            v.add(tokenizer.nextToken());
        }
        Object[] retour = new String[v.size()];
        v.copyInto(retour);
        return retour;
    }

    public static String getFinAnnee(String annee) {
        return "31/12/" + annee;
    }

    public static String[] getDebutFinAnnee() {
        Parametre.getParametre();
        String[] retour = new String[]{Utilitaire.getDebutAnnee(String.valueOf(Utilitaire.getAneeEnCours())), Utilitaire.getFinAnnee(String.valueOf(Utilitaire.getAneeEnCours()))};
        return retour;
    }

    public static ClassMAPTable extraire(ClassMAPTable[] c, int numCol, String val) {
        try {
            for (int i = 0; i < c.length; ++i) {
                String valeur = String.valueOf(c[i].getValField(c[i].getFieldList()[numCol]));
                if (valeur.compareToIgnoreCase(val) != 0) continue;
                return c[i];
            }
            return null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String[] remplacerNullParBlanc(String[] val, String remplacant) {
        for (int i = 0; i < val.length; ++i) {
            if (val[i] != null) continue;
            val[i] = remplacant;
        }
        return val;
    }

    public static ClassMAPTable extraire(Vector v, int numCol, String val) {
        try {
            for (int i = 0; i < v.size(); ++i) {
                ClassMAPTable c = (ClassMAPTable)v.elementAt(i);
                String valeur = (String)c.getValField(c.getFieldList()[numCol]);
                if (valeur.compareToIgnoreCase(val) != 0) continue;
                return c;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ClassMAPTable extraireMultiple(Vector v, int numColVect, int[] numCol, String[] val) {
        try {
            for (int i = 0; i < v.size(); ++i) {
                ClassMAPTable c = (ClassMAPTable)v.elementAt(i);
                boolean test = true;
                String[] valeurT = (String[])c.getValField(c.getFieldList()[numColVect]);
                for (int j = 0; j < numCol.length; ++j) {
                    String valeur = valeurT[j];
                    if (valeur.compareToIgnoreCase(val[j]) == 0) continue;
                    test = false;
                    break;
                }
                if (!test) continue;
                return c;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static int estIlDedans(ClassMAPTable[] c, int numCol, String val) {
        try {
            for (int i = 0; i < c.length; ++i) {
                String valeur = (String)c[i].getValField(c[i].getFieldList()[numCol]);
                if (valeur.compareToIgnoreCase(val) != 0) continue;
                return 1;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static int estIlDedans(String test, String[] c) {
        try {
            if (c == null) {
                return -1;
            }
            for (int i = 0; i < c.length; ++i) {
                if (c[i].compareToIgnoreCase(test) != 0) continue;
                return i;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public static String convertDebutMajuscule(String autre) {
        char[] c = autre.toCharArray();
        c[0] = Character.toUpperCase(c[0]);
        return new String(c);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String[] getvalCol(String nomTable, String col) {
        UtilDB util = new UtilDB();
        Connection c = null;
        Statement cs = null;
        ResultSet rs = null;
        Object[] retour = null;
        try {
            c = util.GetConn();
            cs = c.prepareStatement("select distinct(" + col + ") from " + nomTable);
            rs = cs.executeQuery();
            Vector<String> v = new Vector<String>();
            while (rs.next()) {
                v.add(rs.getString(1));
            }
            retour = new String[v.size()];
            v.copyInto(retour);
            Object[] objectArray = retour;
            return objectArray;
        }
        catch (SQLException e) {
            e.printStackTrace();
            String[] stringArray = null;
            return stringArray;
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (rs != null) {
                    rs.close();
                }
                util.close_connection();
            }
            catch (SQLException sQLException) {}
        }
    }

    public static int[] getBornePage(int page, Object[] list) {
        int[] ret;
        ret = new int[]{(page - 1) * Parametre.getNbParPage(), ret[0] + Parametre.getNbParPage() - 1 < list.length ? ret[0] + Parametre.getNbParPage() : list.length};
        return ret;
    }

    public static int[] getBornePage(String page, Object[] list) {
        return Utilitaire.getBornePage(Utilitaire.stringToInt(page), list);
    }

    public static int calculNbPage(double tailleObjet) {
        int ret = 0;
        Double d = new Double(tailleObjet);
        ret = d.intValue() / Parametre.getNbParPage();
        if (d.intValue() % Parametre.getNbParPage() > 0) {
            ++ret;
        }
        return ret;
    }

    public static int calculNbPage(double tailleObjet, int nbParPage) {
        int ret = 0;
        int nb = Parametre.getNbParPage();
        if (nbParPage > 0) {
            nb = nbParPage;
        }
        Double d = new Double(tailleObjet);
        ret = d.intValue() / nb;
        if (d.intValue() % nb > 0) {
            ++ret;
        }
        return ret;
    }

    public static int calculInitial(int i, int pageSize) {
        int initial = 0;
        initial = i == 1 ? 0 : pageSize * (i - 1);
        return initial;
    }

    public static int calculNbPage(Object[] list) {
        return Utilitaire.calculNbPage(list.length);
    }

    public static double calculSomme(String[] val) throws Exception {
        double retour = 0.0;
        for (int i = 0; i < val.length; ++i) {
            retour += Utilitaire.stringToDouble(val[i]);
        }
        return retour;
    }

    public static double calculSomme(double[] val) throws Exception {
        double retour = 0.0;
        for (int i = 0; i < val.length; ++i) {
            retour += val[i];
        }
        return retour;
    }

    public static int stringToInt(String s) {
        if (s == null) {
            return 0;
        }
        try {
            Double ger = new Double(s);
            return ger.intValue();
        }
        catch (NumberFormatException ex) {
            int j = 0;
            return j;
        }
    }

    public static String remplacerNull(String valNull) {
        if (valNull == null || valNull.compareToIgnoreCase("null") == 0) {
            return "";
        }
        return valNull;
    }

    public static String remplacerUnderscore(String mot) {
        String nouveau = new String(mot.toCharArray());
        nouveau.replace('_', '-');
        return nouveau;
    }

    public static String remplaceMot(String valeur, String mot1, String mot2) {
        StringBuffer result = new StringBuffer();
        int startIdx = 0;
        int idxOld = 0;
        while ((idxOld = valeur.indexOf(mot1, startIdx)) >= 0) {
            result.append(valeur.substring(startIdx, idxOld));
            result.append(mot2);
            startIdx = idxOld + mot1.length();
        }
        result.append(valeur.substring(startIdx));
        return result.toString();
    }

    public static int getRang(char[] liste, char c) {
        for (int i = 0; i < liste.length; ++i) {
            if (Character.toLowerCase(liste[i]) != Character.toLowerCase(c)) continue;
            return i;
        }
        return -1;
    }

    public static String coderPwd(String entree) {
        char[] listeMot = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] chiffre = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] retr = new char[entree.length() + 1];
        retr[0] = listeMot[entree.length() % 5];
        char[] entr = entree.toCharArray();
        for (int i = 0; i < entr.length; ++i) {
            int rL = Utilitaire.getRang(listeMot, entr[i]);
            int rC = Utilitaire.getRang(chiffre, entr[i]);
            retr[i + 1] = rL > -1 ? listeMot[listeMot.length - rL - i] : (rC > -1 ? chiffre[chiffre.length + rC - i] : entr[i]);
        }
        return new String(retr);
    }

    public static String decode(String entree) {
        char[] listeMot = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] chiffre = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        return null;
    }

    public static String getDebutmot(String mot) {
        String retour = "";
        char[] motChar = new char[mot.length()];
        motChar = mot.toCharArray();
        retour = retour.concat(String.valueOf(motChar[0]));
        for (int i = 0; i < mot.length(); ++i) {
            if (motChar[i] != ' ') continue;
            retour = retour.concat(String.valueOf(motChar[i + 1]));
            break;
        }
        return retour.toUpperCase();
    }

    public static String getDebutmot(String mot, int nombre) {
        if (mot == null) {
            return "";
        }
        if (nombre >= mot.length()) {
            return mot.toUpperCase();
        }
        String retour = "";
        if (nombre <= 0) {
            return retour;
        }
        char[] motChar = new char[mot.length()];
        motChar = mot.toCharArray();
        for (int n = 0; n < nombre; ++n) {
            if (motChar[n] == ' ') continue;
            retour = retour.concat(String.valueOf(motChar[n]));
        }
        return retour.toUpperCase();
    }

    public static String getDebutMots(String mot) {
        int i;
        String retour = "";
        if (mot.compareTo("-") == 0) {
            return "NON";
        }
        boolean multiple = false;
        int indice = 3;
        if (mot.length() < 3) {
            indice = 2;
        }
        char[] motChar = new char[mot.length()];
        motChar = mot.toCharArray();
        for (i = 0; i < mot.length(); ++i) {
            if (motChar[i] != ' ') continue;
            multiple = true;
            break;
        }
        if (multiple) {
            retour = Utilitaire.getDebutmot(mot);
        } else {
            for (i = 0; i < indice; ++i) {
                retour = retour.concat(String.valueOf(motChar[i]));
            }
        }
        return retour.toUpperCase();
    }

    public static double getPvente(int pu, int marge) {
        return (double)pu * (1.0 + (double)marge / 100.0);
    }

    public static float stringToFloat(String s) {
        try {
            Float ger = new Float(s);
            float f = ger.floatValue();
            return f;
        }
        catch (NumberFormatException ex) {
            float f1 = 0.0f;
            return f1;
        }
    }

    public static String[] getBorneDatyMoisAnnee(String mois, String an) {
        String[] retour = new String[2];
        GregorianCalendar eD = new GregorianCalendar();
        GregorianCalendar eD2 = new GregorianCalendar();
        String moisF = Utilitaire.completerInt(2, mois);
        retour[0] = "01/" + moisF + "/" + an;
        Date daty1 = Utilitaire.string_date("dd/MM/yyyy", retour[0]);
        eD.setTime(daty1);
        eD2.setTime(daty1);
        eD2.add(5, 26);
        do {
            eD2.add(5, 1);
        } while (eD.get(2) == eD2.get(2));
        eD2.add(5, -1);
        retour[1] = String.valueOf(String.valueOf(Utilitaire.completerInt(2, eD2.get(5)))).concat("/");
        retour[1] = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(retour[1]))).append(Utilitaire.completerInt(2, eD2.get(2) + 1)).append("/")));
        retour[1] = String.valueOf(retour[1]) + String.valueOf(Utilitaire.completerInt(4, eD2.get(1)));
        return retour;
    }

    public static int getAneeEnCours() {
        Calendar a = Calendar.getInstance();
        return a.get(1);
    }

    public static int compterCar(String lettre, char c) {
        char[] mot = lettre.toCharArray();
        int nb = 0;
        for (int i = 0; i < mot.length; ++i) {
            if (mot[i] != c) continue;
            ++nb;
        }
        return nb;
    }

    public static String[] split(String lettre, char sep) {
        Vector<String> v = new Vector<String>();
        char[] mot = lettre.toCharArray();
        char[] part = new char[mot.length];
        int indicePart = 0;
        for (int i = 0; i < mot.length; ++i) {
            if (mot[i] == sep) {
                indicePart = 0;
                v.add(String.valueOf(part).trim());
                part = new char[mot.length];
            } else {
                part[indicePart] = mot[i];
                ++indicePart;
            }
            if (i != mot.length - 1) continue;
            v.add(String.valueOf(part).trim());
        }
        Object[] retour = new String[v.size()];
        v.copyInto(retour);
        return retour;
    }

    public static boolean estIlDedans(char[] liste, char car) {
        for (int i = 0; i < liste.length; ++i) {
            if (liste[i] != car) continue;
            return true;
        }
        return false;
    }

    public static String[] splitMultiple(String lettre) {
        return Utilitaire.split(lettre, listSeparator);
    }

    public static String[] split(String lettre, char[] sep) {
        Vector<String> v = new Vector<String>();
        char[] mot = lettre.toCharArray();
        char[] part = new char[100];
        int indicePart = 0;
        for (int i = 0; i < mot.length; ++i) {
            if (Utilitaire.estIlDedans(sep, mot[i])) {
                indicePart = 0;
                v.add(String.valueOf(part).trim());
                part = new char[100];
            } else {
                part[indicePart] = mot[i];
                ++indicePart;
            }
            if (i != mot.length - 1) continue;
            v.add(String.valueOf(part).trim());
        }
        Object[] retour = new String[v.size()];
        v.copyInto(retour);
        return retour;
    }

    public static String getAnnee(String daty) {
        return Utilitaire.split(daty, "/")[2];
    }

    public static String getAnnee(String daty, String separateur) {
        return Utilitaire.split(daty, separateur)[0];
    }

    public static int getAnnee(Date daty) {
        GregorianCalendar eD = new GregorianCalendar();
        eD.setTime(daty);
        return eD.get(1);
    }

    public static int getMois(Date daty) {
        GregorianCalendar eD = new GregorianCalendar();
        eD.setTime(daty);
        return eD.get(2) + 1;
    }

    public static String getMois(String daty) {
        return Utilitaire.completerInt(2, Utilitaire.split(daty, "/")[1]);
    }

    public static String getJour(String daty) {
        return Utilitaire.completerInt(2, Utilitaire.split(daty, "/")[0]);
    }

    public static int getMoisEnCours() {
        Calendar a = Calendar.getInstance();
        return a.get(2);
    }

    public static int getMoisEnCoursReel() {
        Calendar a = Calendar.getInstance();
        return a.get(2) + 1;
    }

    public static Timestamp transformer(Date d, String time) throws Exception {
        String dateTimeString = d.toString() + " " + time;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Date dt = format.parse(dateTimeString);
        return new Timestamp(dt.getTime());
    }

    public static double differenceEnHeure(Date d1, String time1, Date d2, String time2) throws Exception {
        Timestamp ts1 = Utilitaire.transformer(d1, time1);
        Timestamp ts2 = Utilitaire.transformer(d2, time2);
        long diff = ts2.getTime() - ts1.getTime();
        double diffMinutes = diff / 3600000L;
        return diffMinutes;
    }

    public static double differenceEnMinutes(Date d1, String time1, Date d2, String time2) throws Exception {
        Timestamp ts1 = Utilitaire.transformer(d1, time1);
        Timestamp ts2 = Utilitaire.transformer(d2, time2);
        long diff = ts2.getTime() - ts1.getTime();
        double diffMinutes = diff / 60000L;
        return diffMinutes;
    }

    public static int compareDaty(Date supe, Date infe) {
        GregorianCalendar eD = new GregorianCalendar();
        GregorianCalendar eD2 = new GregorianCalendar();
        Date sup = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.formatterDaty(supe));
        Date inf = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.formatterDaty(infe));
        eD.setTime(sup);
        eD2.setTime(inf);
        if (eD.getTime().getTime() > eD2.getTime().getTime()) {
            return 1;
        }
        if (eD.getTime().getTime() < eD2.getTime().getTime()) {
            return -1;
        }
        return 0;
    }

    public static int diffJourDaty(Date dMaxe, Date dMine) {
        GregorianCalendar eD = new GregorianCalendar();
        GregorianCalendar eD2 = new GregorianCalendar();
        Date dMax = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.formatterDaty(dMaxe));
        Date dMin = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.formatterDaty(dMine));
        eD.setTime(dMax);
        eD2.setTime(dMin);
        if (dMaxe.equals(dMine)) {
            return 0;
        }
        double resultat = eD.getTime().getTime() - eD2.getTime().getTime();
        BigDecimal result = new BigDecimal(String.valueOf(eD.getTime().getTime() - eD2.getTime().getTime()));
        BigDecimal retour = result.divide(new BigDecimal(String.valueOf(86400000)), 4);
        return 1 + retour.intValue();
    }

    public static int diffJourDaty2(Date dMaxe, Date dMine) {
        GregorianCalendar eD = new GregorianCalendar();
        GregorianCalendar eD2 = new GregorianCalendar();
        Date dMax = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.formatterDaty(dMaxe));
        Date dMin = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.formatterDaty(dMine));
        eD.setTime(dMax);
        eD2.setTime(dMin);
        if (dMaxe.equals(dMine)) {
            return 1;
        }
        double resultat = eD.getTime().getTime() - eD2.getTime().getTime();
        BigDecimal result = new BigDecimal(String.valueOf(eD.getTime().getTime() - eD2.getTime().getTime()));
        BigDecimal retour = result.divide(new BigDecimal(String.valueOf(86400000)), 4);
        return 1 + retour.intValue();
    }

    public static int diffMoisDaty(Date dMaxe, Date dMine) {
        int result = 0;
        int diffAnnee = 0;
        int yMax = 0;
        int yMin = 0;
        int mMax = 0;
        int mMin = 0;
        if (dMaxe.getTime() < dMine.getTime()) {
            Date temp = dMaxe;
            dMaxe = dMine;
            dMine = temp;
        }
        GregorianCalendar calMax = new GregorianCalendar();
        GregorianCalendar calMin = new GregorianCalendar();
        calMin.setTime(dMine);
        calMax.setTime(dMaxe);
        mMin = calMin.get(2);
        mMax = calMax.get(2);
        yMin = calMin.get(1);
        yMax = calMax.get(1);
        diffAnnee = yMax - yMin;
        if (mMax < mMin) {
            --diffAnnee;
            result = 12 - (mMin - mMax);
        } else {
            result = mMax - mMin;
        }
        return result += diffAnnee * 12;
    }

    public static int diffJourDaty(String dMax, String dMin) {
        return Utilitaire.diffJourDaty(Utilitaire.string_date("dd/MM/yyyy", dMax), Utilitaire.string_date("dd/MM/yyyy", dMin));
    }

    public static int diffMoisDaty(String dMax, String dMin) {
        return Utilitaire.diffMoisDaty(Utilitaire.string_date("dd/MM/yyyy", dMax), Utilitaire.string_date("dd/MM/yyyy", dMin));
    }

    public static String replaceVirgule(String s) {
        s = s.replace(',', '.');
        return s;
    }

    public static String supprimerEspace(String s) {
        s = s.trim();
        return s;
    }

    public static String[] splitPeriode(String periode) {
        String[] ret = new String[]{periode.substring(0, 4), periode.substring(4)};
        return ret;
    }

    public static String enleverEspaceDoubleBase(String montantBase) {
        String montant = "";
        for (int i = 0; i < montantBase.length(); ++i) {
            char c = montantBase.charAt(i);
            char j = c;
            if (j == '\u00a0') continue;
            montant = montant + c;
        }
        return montant;
    }

    public static double stringToDouble(String s) {
        try {
            String ns = Utilitaire.replaceVirgule(s);
            String nso = Utilitaire.enleverNonChiffre(ns);
            Double ger = new Double(nso);
            double d = ger;
            return d;
        }
        catch (NumberFormatException ex) {
            double d1 = 0.0;
            return d1;
        }
    }

    public static long stringToLong(String s) {
        try {
            Long ger = new Long(s);
            long l = ger;
            return l;
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
            long l1 = 0L;
            return l1;
        }
    }

    public static int[] findUniteDizaine(int nb) {
        try {
            int[] ret;
            ret = new int[]{nb % 10, nb - ret[0]};
            return ret;
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String formaterAr(String montant) {
        if (montant.contains("E")) {
            return Utilitaire.formaterAr(Double.valueOf(montant));
        }
        return Utilitaire.formaterAr(Utilitaire.stringToDouble(montant));
    }

    public static String formaterAr(double montant, int minimumFractionDigit) {
        try {
            String s;
            if (montant == 0.0) {
                return "0";
            }
            NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
            nf.setMinimumFractionDigits(minimumFractionDigit);
            String ret = s = nf.format(montant);
            return ret;
        }
        catch (Exception e) {
            e.printStackTrace();
            String s1 = null;
            return s1;
        }
    }

    public static String doubleWithoutExponential(double val) {
        String vals = String.format("%.2f", val);
        String[] temp = vals.split(",");
        if (temp.length > 1 && temp[1].compareToIgnoreCase("00") == 0) {
            vals = temp[0];
        } else if (temp.length > 1 && temp[1].endsWith("0")) {
            vals = temp[0] + "," + temp[1].substring(0, 1);
        }
        return Utilitaire.replaceChar(vals, ",", ".");
    }

    public static String doubleWithoutExponential(double val, int decimals) {
        StringBuilder pattern = new StringBuilder("0");
        if (decimals > 0) {
            pattern.append(".");
            for (int i = 0; i < decimals; ++i) {
                pattern.append("#");
            }
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat(pattern.toString(), symbols);
        return df.format(val);
    }

    public static String enleverExponentielleDouble(double montant) {
        String ret = "0";
        ret = new DecimalFormat("#").format(montant);
        return ret;
    }

    public static String formaterAr(double montant) {
        try {
            if (montant == 0.0) {
                return "0";
            }
            NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
            nf.setMaximumFractionDigits(10);
            String s = nf.format(montant);
            return s;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formaterSansVirgule(double montant) {
        try {
            if (montant == 0.0) {
                return "0";
            }
            NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
            nf.setMinimumFractionDigits(0);
            String s = nf.format(montant);
            return s;
        }
        catch (Exception e) {
            e.printStackTrace();
            String s1 = null;
            return s1;
        }
    }

    public static String formaterAr(long montant) {
        return Utilitaire.formaterAr(String.valueOf(montant));
    }

    public static String formatterDaty(String daty) {
        String dateSupll;
        if (daty == null || daty.equalsIgnoreCase("null") || daty.isEmpty()) {
            return "";
        }
        String[] parts = (daty = daty.replace("T", " ")).split(" ");
        String dateOrigin = parts[0];
        if (dateOrigin.length() < 10) {
            return "";
        }
        String valiny = dateOrigin.substring(8, 10) + "/" + dateOrigin.substring(5, 7) + "/" + dateOrigin.substring(0, 4);
        if (parts.length > 1 && (dateSupll = parts[1]) != null && !dateSupll.isEmpty() && dateSupll.length() >= 8) {
            valiny = valiny + " " + dateSupll.substring(0, 2) + ":" + dateSupll.substring(3, 5) + ":" + dateSupll.substring(6, 8);
        }
        return valiny;
    }

    public static String ajouterDate(String date, int second) {
        if (date == null || date.equalsIgnoreCase("null") || date.isEmpty()) {
            return "";
        }
        try {
            SimpleDateFormat sdfTime;
            String timePart;
            boolean hasTime = date.trim().length() > 10;
            String pattern = hasTime ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);
            java.util.Date parsed = sdf.parse(date.trim());
            Date sqlDate = new Date(parsed.getTime());
            Date result = Utilitaire.ajouterDate(sqlDate, second);
            if (!hasTime && !(timePart = (sdfTime = new SimpleDateFormat("HH:mm:ss")).format(result)).equals("00:00:00")) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(result);
            }
            return sdf.format(result);
        }
        catch (ParseException e) {
            return "";
        }
    }

    public static Date ajouterDate(Date date, int second) {
        if (date == null) {
            return null;
        }
        long newTime = date.getTime() + (long)second * 1000L;
        return new Date(newTime);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Date getDateRentreSemestre() throws Exception {
        Connection c = null;
        UtilDB util = new UtilDB();
        Statement sta = null;
        ResultSet rs = null;
        Date max = null;
        try {
            c = util.GetConn();
            String param = "Select MAX(RENTRE) FROM RENTRESEMESTRE";
            sta = c.createStatement();
            rs = sta.executeQuery(param);
            rs.next();
            max = rs.getDate(1);
        }
        catch (SQLException e) {
            System.out.println("getNbTuple : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
        finally {
            if (sta != null) {
                sta.close();
            }
            if (rs != null) {
                rs.close();
            }
            util.close_connection();
        }
        return max;
    }

    public static String getTomorowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, 1);
        return Utilitaire.format(calendar.getTime());
    }

    public static String format(java.util.Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MMM/yyyy");
        String dateFormatted = fmt.format(date);
        return dateFormatted;
    }

    public static double arrondir(double a, int apr) {
        try {
            NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
            nf.setMaximumFractionDigits(apr);
            Number retour = nf.parse(nf.format(a));
            double d1 = retour.doubleValue();
            return d1;
        }
        catch (Exception e) {
            double d = 1.0;
            return d;
        }
    }

    public static String arrondirValeur(String valeur) {
        if (valeur == null || valeur.isEmpty() || valeur.equalsIgnoreCase("-")) {
            return valeur;
        }
        if (valeur.contains("<BR>")) {
            String[] parties = valeur.split("<BR>");
            String resultat = "";
            for (int i = 0; i < parties.length; ++i) {
                if (i > 0) {
                    resultat = resultat + "<BR>";
                }
                resultat = resultat + Utilitaire.arrondirValeurSimple(parties[i]);
            }
            return resultat;
        }
        return Utilitaire.arrondirValeurSimple(valeur);
    }

    private static String arrondirValeurSimple(String valeur) {
        if (valeur == null || valeur.isEmpty() || valeur.equalsIgnoreCase("-")) {
            return valeur;
        }
        double valeurDouble = Utilitaire.stringToDouble(valeur);
        String valeurNettoyee = Utilitaire.replaceVirgule(Utilitaire.enleverEspaceDoubleBase(valeur.trim()));
        String valeurNettoyee2 = valeurNettoyee.replaceAll("\\s+", "");
        if (valeurDouble == 0.0) {
            try {
                double test = Double.parseDouble(valeurNettoyee2);
                if (test == 0.0) {
                    return Utilitaire.formaterAr(0.0);
                }
            }
            catch (NumberFormatException e) {
                return valeur;
            }
        }
        long valeurArrondie = Math.round(valeurDouble);
        return Utilitaire.formaterAr((double)valeurArrondie);
    }

    public static String formatterDaty(Date daty) {
        Object retour = null;
        return Utilitaire.formatterDaty(String.valueOf(daty));
    }

    public static String formatterDatySql(Date daty) {
        Object retour = null;
        return Utilitaire.formatterDaty(String.valueOf(daty));
    }

    public static Date ajoutJourDateOuvrable(Date aDate, int nbDay) {
        try {
            Date date = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.ajoutJourDateStringOuvrable(aDate, nbDay));
            return date;
        }
        catch (Exception e) {
            System.out.println("Error string_date :".concat(String.valueOf(String.valueOf(e.getMessage()))));
            e.printStackTrace();
            Date date1 = null;
            return date1;
        }
    }

    public static String ajoutJourDateStringOuvrable(Date aDatee, int nbDay) {
        try {
            GregorianCalendar eD = new GregorianCalendar();
            Date aDate = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.formatterDaty(aDatee));
            eD.setTime(aDate);
            int offset = 1;
            int offsetSunday = 1;
            int offsetSaturday = 2;
            if (nbDay < 0) {
                offset = -1;
                offsetSunday = -2;
                offsetSaturday = -1;
            }
            for (int i = 1; i <= Math.abs(nbDay); ++i) {
                eD.add(5, offset);
                if (eD.get(7) == 7) {
                    eD.add(5, offsetSaturday);
                    continue;
                }
                if (eD.get(7) != 1) continue;
                eD.add(5, offsetSunday);
            }
            String retour = null;
            retour = String.valueOf(String.valueOf(Utilitaire.completerInt(2, eD.get(5)))).concat("/");
            retour = String.valueOf(retour) + String.valueOf(Utilitaire.completerInt(2, String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(eD.get(2) + 1))).append("/")))));
            String s1 = retour = String.valueOf(retour) + String.valueOf(Utilitaire.completerInt(4, eD.get(1)));
            return s1;
        }
        catch (Exception e) {
            System.out.println("Error string_date :".concat(String.valueOf(String.valueOf(e.getMessage()))));
            String s = null;
            return s;
        }
    }

    public static String ajoutMoisDateString(Date aDatee, int nbMois) {
        try {
            GregorianCalendar eD = new GregorianCalendar();
            GregorianCalendar eD2 = new GregorianCalendar();
            Date aDate = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.formatterDaty(aDatee));
            eD.setTime(aDate);
            int offset = 1;
            int offsetSunday = 1;
            int offsetSaturday = 2;
            if (nbMois < 0) {
                offset = -1;
                offsetSunday = -2;
                offsetSaturday = -1;
            }
            for (int i = 1; i <= Math.abs(nbMois); ++i) {
                eD.add(2, offset);
            }
            eD2.setTime(eD.getTime());
            if (eD.get(2) == eD2.get(2) && Utilitaire.testFinDuMois(aDate)) {
                do {
                    eD2.add(5, 1);
                } while (eD.get(2) == eD2.get(2));
                eD2.add(5, -1);
            }
            String retour = null;
            retour = String.valueOf(String.valueOf(Utilitaire.completerInt(2, eD2.get(5)))).concat("/");
            retour = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(retour))).append(Utilitaire.completerInt(2, eD2.get(2) + 1)).append("/")));
            String s1 = retour = String.valueOf(retour) + String.valueOf(Utilitaire.completerInt(4, eD2.get(1)));
            return s1;
        }
        catch (Exception e) {
            System.out.println("Error string_date :".concat(String.valueOf(String.valueOf(e.getMessage()))));
            String s = null;
            return s;
        }
    }

    public static boolean testFinDuMois(Date aDatee) {
        GregorianCalendar eD = new GregorianCalendar();
        Date aDate = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.formatterDaty(aDatee));
        eD.setTime(aDate);
        GregorianCalendar eD2 = new GregorianCalendar();
        eD2.setTime(eD.getTime());
        eD2.add(5, 1);
        return eD.get(2) != eD2.get(2);
    }

    public static double getMaxListeDouble(double[] liste) {
        double max = liste[0];
        for (int i = 1; i < liste.length; ++i) {
            if (!(liste[i] >= max)) continue;
            max = liste[i];
        }
        return max;
    }

    public static String ajoutJourDateString(Date aDatee, int nbDay) {
        try {
            GregorianCalendar eD = new GregorianCalendar();
            Date aDate = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.formatterDaty(aDatee));
            eD.setTime(aDate);
            int offset = 1;
            int offsetSunday = 1;
            int offsetSaturday = 2;
            if (nbDay < 0) {
                offset = -1;
                offsetSunday = -2;
                offsetSaturday = -1;
            }
            for (int i = 1; i <= Math.abs(nbDay); ++i) {
                eD.add(5, offset);
            }
            String retour = null;
            retour = String.valueOf(String.valueOf(Utilitaire.completerInt(2, eD.get(5)))).concat("/");
            retour = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(retour))).append(Utilitaire.completerInt(2, eD.get(2) + 1)).append("/")));
            String s1 = retour = String.valueOf(retour) + String.valueOf(Utilitaire.completerInt(4, eD.get(1)));
            return s1;
        }
        catch (Exception e) {
            System.out.println("Error ajoutJourDateString :".concat(String.valueOf(String.valueOf(e.getMessage()))));
            String s = null;
            return s;
        }
    }

    public static String soustraireJourDate(int nbDay) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(5, -nbDay);
            return Utilitaire.datetostring(cal.getTime());
        }
        catch (Exception e) {
            System.out.println("Error ajoutJourDate :".concat(String.valueOf(String.valueOf(e.getMessage()))));
            String date1 = "";
            return date1;
        }
    }

    public static Date ajoutJourDate(Date aDate, int nbDay) {
        try {
            Date date = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.ajoutJourDateString(aDate, nbDay));
            return date;
        }
        catch (Exception e) {
            System.out.println("Error ajoutJourDate :".concat(String.valueOf(String.valueOf(e.getMessage()))));
            Date date1 = null;
            return date1;
        }
    }

    public static Date ajoutMoisDate(Date aDate, int nbMois) {
        try {
            Date date = Utilitaire.string_date("dd/MM/yyyy", Utilitaire.ajoutMoisDateString(aDate, nbMois));
            return date;
        }
        catch (Exception e) {
            System.out.println("Error ajoutMoisDate :".concat(String.valueOf(String.valueOf(e.getMessage()))));
            Date date1 = null;
            return date1;
        }
    }

    public static Date ajoutJourDate(String daty, int jour) {
        try {
            Date date = Utilitaire.ajoutJourDate(Utilitaire.string_date("dd/MM/yyyy", daty), jour);
            return date;
        }
        catch (Exception e) {
            System.out.println("Error ajoutJourDate :".concat(String.valueOf(String.valueOf(e.getMessage()))));
            Date date1 = null;
            return date1;
        }
    }

    public static Date string_date(String patterne, String daty) {
        try {
            Date hiredate;
            if (daty == null || daty.compareTo("") == 0) {
                return null;
            }
            SimpleDateFormat formatter = new SimpleDateFormat(patterne);
            formatter.applyPattern(patterne);
            formatter.setTimeZone(TimeZone.getTimeZone("EUROPE"));
            String annee = Utilitaire.getAnnee(daty);
            int anneecours = Utilitaire.getAneeEnCours();
            int siecl = anneecours / 100;
            if (annee.toCharArray().length == 2) {
                annee = String.valueOf(siecl) + annee;
            }
            daty = Utilitaire.getJour(daty) + "/" + Utilitaire.getMois(daty) + "/" + annee;
            Date date1 = hiredate = new Date(formatter.parse(daty).getTime());
            return date1;
        }
        catch (Exception e) {
            System.out.println("Error string_date wawawawa :" + e.getMessage());
            Date date = Utilitaire.dateDuJourSql();
            return date;
        }
    }

    public static java.util.Date stringToDate(String pattern, String daty) {
        try {
            java.util.Date hiredate;
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            java.util.Date date1 = hiredate = formatter.parse(daty);
            return date1;
        }
        catch (Exception e) {
            System.out.println("Error stringTodate :".concat(String.valueOf(String.valueOf(e.getMessage()))));
            java.util.Date date = null;
            return date;
        }
    }

    public int randomizer(int max) {
        int retour = 0;
        while (retour <= 0) {
            retour = r.nextInt(max);
        }
        return retour;
    }

    public String randomizer_daty(int annee) {
        int mois = r.nextInt(13);
        int jour = r.nextInt(31);
        String retour = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(jour))).append("/").append(mois).append("/").append(annee)));
        return retour;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getNbTuple(String nomTable) {
        Connection c = null;
        UtilDB util = new UtilDB();
        try {
            int i;
            c = util.GetConn();
            String param = "select count(*) from ".concat(String.valueOf(String.valueOf(nomTable)));
            Statement sta = c.createStatement();
            ResultSet rs = sta.executeQuery(param);
            rs.next();
            int n = i = rs.getInt(1);
            return n;
        }
        catch (SQLException e) {
            int j;
            System.out.println("getNbTuple : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            int n = j = 0;
            return n;
        }
        finally {
            util.close_connection();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getNbEliminatoire(String nomTable, String critere, String apwhere) {
        Connection c = null;
        UtilDB util = new UtilDB();
        try {
            int i;
            c = util.GetConn();
            String param = String.valueOf(String.valueOf(new StringBuffer("select count(").append(critere).append(") from ").append(nomTable).append(" where ").append(apwhere)));
            Statement sta = c.createStatement();
            ResultSet rs = sta.executeQuery(param);
            rs.next();
            int n = i = rs.getInt(1);
            return n;
        }
        catch (SQLException e) {
            int j;
            System.out.println("getNbTuple : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            int n = j = 0;
            return n;
        }
        finally {
            util.close_connection();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getMaxColonneFactFin(String daty) {
        UtilDB util = new UtilDB();
        Connection c = null;
        Statement cs = null;
        ResultSet rs = null;
        try {
            int l;
            String an = Utilitaire.getAnnee(daty);
            c = null;
            c = util.GetConn();
            cs = c.prepareStatement(String.valueOf(String.valueOf(new StringBuffer("select * from  seqFact where daty<='31/12/").append(an).append("' and daty>='01/01/").append(an).append("'"))));
            rs = cs.executeQuery();
            int i = 0;
            if (rs.next()) {
                ++i;
            }
            if (i == 0) {
                int k;
                int n = k = 0;
                return n;
            }
            int n = l = new Integer(rs.getString(1)).intValue();
            return n;
        }
        catch (SQLException e) {
            int j;
            System.out.println("getMaxSeq : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            int n = j = 0;
            return n;
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (rs != null) {
                    rs.close();
                }
                util.close_connection();
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheType ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            }
        }
    }

    public static int getMaxSeqDirecte(String ns, Connection c) throws Exception {
        Statement sta = null;
        ResultSet rs = null;
        try {
            sta = c.createStatement();
            sta.executeQuery("select " + ns + ".nextval from dual");
            rs = sta.getResultSet();
            rs.next();
            int n = rs.getInt(1);
            return n;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            if (sta != null) {
                sta.close();
            }
        }
    }

    public static int getMaxSeq(String nomProcedure, Connection c) throws Exception {
        Statement cs = null;
        ResultSet rs = null;
        try {
            int i;
            PropertiesLoader loader = PropertiesLoader.getInstance();
            Properties properties = loader.getProperties();
            if (properties.getProperty("apj.connection.driver") == null || properties.getProperty("apj.connection.driver").contains("oracle")) {
                cs = c.prepareCall("select " + nomProcedure + " from dual");
            } else if (properties.getProperty("apj.connection.driver").contains("postgresql")) {
                cs = c.prepareCall(String.valueOf(String.valueOf(new StringBuffer("select ").append(nomProcedure).append("()"))));
            }
            rs = cs.executeQuery();
            rs.next();
            int n = i = rs.getInt(1);
            return n;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getMaxSeq(String nomProcedure) {
        UtilDB util = new UtilDB();
        Connection c = null;
        try {
            c = util.GetConn();
            int n = Utilitaire.getMaxSeq(nomProcedure, c);
            return n;
        }
        catch (Exception eu) {
            eu.printStackTrace();
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getMaxNum(String nomTable, String nomColonne, String where) throws Exception {
        Connection c = null;
        UtilDB util = new UtilDB();
        Statement sta = null;
        ResultSet rs = null;
        try {
            c = util.GetConn();
            String param = "select max(" + nomColonne + ") from " + nomTable + " where " + where;
            sta = c.createStatement();
            rs = sta.executeQuery(param);
            int i = 0;
            if (rs.next()) {
                i = rs.getInt(1);
            }
            int n = i;
            return n;
        }
        catch (SQLException e) {
            int j;
            System.out.println("getNbTuple : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            int n = j = 0;
            return n;
        }
        finally {
            if (sta != null) {
                sta.close();
            }
            if (rs != null) {
                rs.close();
            }
            util.close_connection();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getMaxNum(String nomTable, String nomColonne) throws Exception {
        Connection c = null;
        UtilDB util = new UtilDB();
        Statement sta = null;
        ResultSet rs = null;
        try {
            int i;
            c = util.GetConn();
            String param = String.valueOf(String.valueOf(new StringBuffer("select max(").append(nomColonne).append(") from ").append(nomTable)));
            sta = c.createStatement();
            rs = sta.executeQuery(param);
            rs.next();
            int n = i = 1 + rs.getInt(1);
            return n;
        }
        catch (SQLException e) {
            int j;
            System.out.println("getNbTuple : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            int n = j = 0;
            return n;
        }
        finally {
            if (sta != null) {
                sta.close();
            }
            if (rs != null) {
                rs.close();
            }
            util.close_connection();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getMaxColonne(String nomTable, String nomColonne, String nomCritere, String attributCritere) throws Exception {
        Connection c = null;
        UtilDB util = new UtilDB();
        Statement sta = null;
        ResultSet rs = null;
        String max = "";
        try {
            c = util.GetConn();
            String param = String.valueOf(String.valueOf(new StringBuffer("select max(").append(nomColonne).append(") from ").append(nomTable).append(" where ").append(nomCritere).append("='").append(attributCritere).append("'")));
            sta = c.createStatement();
            rs = sta.executeQuery(param);
            rs.next();
            max = rs.getString(1);
        }
        catch (SQLException e) {
            System.out.println("getNbTuple : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
        finally {
            if (sta != null) {
                sta.close();
            }
            if (rs != null) {
                rs.close();
            }
            util.close_connection();
        }
        return max;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getMaxColonneMultiCritere(String nomTable, String nomColonne, String whereCritereContact) throws Exception {
        Connection c = null;
        UtilDB util = new UtilDB();
        Statement sta = null;
        ResultSet rs = null;
        String max = "";
        try {
            c = util.GetConn();
            String param = String.valueOf(String.valueOf(new StringBuffer("select max(").append(nomColonne).append(") from ").append(nomTable).append(whereCritereContact)));
            sta = c.createStatement();
            rs = sta.executeQuery(param);
            rs.next();
            max = rs.getString(1);
        }
        catch (SQLException e) {
            System.out.println("getNbTuple : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
        finally {
            if (sta != null) {
                sta.close();
            }
            if (rs != null) {
                rs.close();
            }
            util.close_connection();
        }
        return max;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String updateColonne(String nomTable, String nomColonne, String critere, String val, String valCritere) throws Exception {
        Connection c = null;
        UtilDB util = new UtilDB();
        Statement sta = null;
        int rs = 0;
        String max = "";
        try {
            c = util.GetConn();
            c.setAutoCommit(false);
            String param = String.valueOf(String.valueOf(new StringBuffer("update ").append(nomTable).append(" set ").append(nomColonne).append("='").append(val).append("' where ").append(critere).append("='").append(valCritere).append("'")));
            sta = c.createStatement();
            rs = sta.executeUpdate(param);
            c.commit();
        }
        catch (SQLException e) {
            System.out.println("getNbTuple : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
        finally {
            if (sta != null) {
                sta.close();
            }
            if (c != null) {
                c.close();
            }
        }
        return max;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getSommeColonneMultiCritere(String nomTable, String nomColonne, String whereCritereContact) throws Exception {
        Connection c = null;
        UtilDB util = new UtilDB();
        Statement sta = null;
        ResultSet rs = null;
        int sum = 0;
        try {
            c = util.GetConn();
            String param = String.valueOf(String.valueOf(new StringBuffer("select sum(").append(nomColonne).append(") from ").append(nomTable).append(" where ").append(whereCritereContact)));
            sta = c.createStatement();
            rs = sta.executeQuery(param);
            rs.next();
            sum = rs.getInt(1);
        }
        catch (SQLException e) {
            System.out.println("getNbTuple : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
        finally {
            if (sta != null) {
                sta.close();
            }
            if (rs != null) {
                rs.close();
            }
            util.close_connection();
        }
        return sum;
    }

    public static int getNombreJourMois(String mois, String an) {
        try {
            String datyInf = Utilitaire.getBorneDatyMoisAnnee(mois, an)[0];
            String datySup = Utilitaire.getBorneDatyMoisAnnee(mois, an)[1];
            int j = Utilitaire.diffJourDaty(datySup, datyInf);
            return j;
        }
        catch (Exception e) {
            System.out.println("getNombreJourMois : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            int i = 0;
            return i;
        }
    }

    public static int getNombreJourMois(String daty) {
        try {
            String mois = Utilitaire.getMois(daty);
            String an = Utilitaire.getAnnee(daty);
            int j = Utilitaire.getNombreJourMois(mois, an);
            return j;
        }
        catch (Exception e) {
            System.out.println("getNombreJourMois : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            int i = 0;
            return i;
        }
    }

    public static Date stringDate(String daty) {
        if (daty == null || daty.compareTo("") == 0) {
            return null;
        }
        Date sqlDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = sdf.parse(daty);
            sqlDate = new Date(date.getTime());
        }
        catch (Exception e) {
            System.out.println("Error stringDate :" + e.getMessage());
        }
        return sqlDate;
    }

    public static String completerInt(int longuerChaine, int nombre) {
        String zero = null;
        zero = "";
        for (int i = 0; i < longuerChaine - String.valueOf(nombre).length(); ++i) {
            zero = String.valueOf(String.valueOf(zero)).concat("0");
        }
        return String.valueOf(zero) + String.valueOf(String.valueOf(nombre));
    }

    public static String completerInt(int longuerChaine, String nombre2) {
        int nombre = Utilitaire.stringToInt(nombre2);
        return Utilitaire.completerInt(longuerChaine, nombre);
    }

    public static String heureCourante() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static String heureCouranteHMS() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static String heureCouranteHM() {
        Calendar a = Calendar.getInstance();
        String retour = null;
        retour = String.valueOf(String.valueOf(Utilitaire.completerInt(2, a.get(11)))).concat(":");
        retour = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(retour))).append(Utilitaire.completerInt(2, a.get(12)))));
        return retour;
    }

    public static String dateDuJour() {
        Calendar a = Calendar.getInstance();
        String retour = null;
        retour = String.valueOf(String.valueOf(Utilitaire.completerInt(2, a.get(5)))).concat("/");
        retour = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(retour))).append(Utilitaire.completerInt(2, a.get(2) + 1)).append("/")));
        retour = String.valueOf(retour) + String.valueOf(Utilitaire.completerInt(4, a.get(1)));
        return retour;
    }

    public static Date dateDuJourSql() {
        return Utilitaire.string_date("dd/MM/yyyy", Utilitaire.dateDuJour());
    }

    public static String annulerZero(int nombre) {
        if (nombre == 0) {
            return " ";
        }
        return String.valueOf(nombre);
    }

    public static Vector intersecter(ClassMAPTable[] objet1, ClassMAPTable[] objet2) {
        Vector<ClassMAPTable> retour = new Vector<ClassMAPTable>();
        int dim1 = objet1.length;
        int dim2 = objet2.length;
        int nbEgaux = 0;
        for (int i = 0; i < dim1; ++i) {
            String cle1 = objet1[i].getTuppleID();
            for (int j = 0; j < dim2; ++j) {
                String cle2 = objet2[j].getTuppleID();
                if (cle1.compareTo(cle2) != 0) continue;
                retour.add(nbEgaux, objet2[j]);
                ++nbEgaux;
            }
        }
        return retour;
    }

    public static String[] intersectionIgnoreCasse(String[] tableau1, String[] tableau2) {
        if (tableau1 == null || tableau2 == null || tableau1.length == 0 || tableau2.length == 0) {
            return new String[0];
        }
        Set set2 = Arrays.stream(tableau2).filter(Objects::nonNull).map(String::toLowerCase).collect(Collectors.toSet());
        return (String[])Arrays.stream(tableau1).filter(Objects::nonNull).map(String::trim).distinct().filter(element -> set2.contains(element.toLowerCase())).toArray(String[]::new);
    }

    public static Vector intersecter(Vector objet1, Vector objet2) {
        Vector<ClassMAPTable> retour = new Vector<ClassMAPTable>();
        int dim1 = objet1.size();
        int dim2 = objet2.size();
        int nbEgaux = 0;
        for (int i = 0; i < dim1; ++i) {
            ClassMAPTable temp = (ClassMAPTable)objet1.elementAt(i);
            String cle1 = temp.getTuppleID();
            for (int j = 0; j < dim2; ++j) {
                ClassMAPTable temp2 = (ClassMAPTable)objet2.elementAt(j);
                String cle2 = temp2.getTuppleID();
                if (cle1.compareTo(cle2) != 0) continue;
                retour.add(nbEgaux, temp2);
                ++nbEgaux;
            }
        }
        return retour;
    }

    public static boolean intersecterIgnoreCase(String ref, String valeur, ClassMAPTable[] objet1) {
        int dim1 = objet1.length;
        boolean nbEgaux = false;
        for (int i = 0; i < dim1; ++i) {
            String cle1 = objet1[i].getTuppleID();
            if (ref.compareTo(cle1) != 0) continue;
            return true;
        }
        return false;
    }

    public static boolean intersecter(String ref, ClassMAPTable[] objet1) {
        int dim1 = objet1.length;
        boolean nbEgaux = false;
        for (int i = 0; i < dim1; ++i) {
            String cle1 = objet1[i].getTuppleID();
            if (ref.compareTo(cle1) != 0) continue;
            return true;
        }
        return false;
    }

    public static boolean intersecter(String ref, Vector objet1) {
        int dim1 = objet1.size();
        boolean nbEgaux = false;
        if (objet1 != null) {
            for (int i = 0; i < dim1; ++i) {
                ClassMAPTable temp = (ClassMAPTable)objet1.elementAt(i);
                String cle1 = temp.getTuppleID();
                if (ref.compareTo(cle1) != 0) continue;
                return true;
            }
        }
        return false;
    }

    public static Object[] toArray(Vector v) {
        Object[] retour = new Object[v.size()];
        for (int i = 0; i < v.size(); ++i) {
            retour[i] = v.elementAt(i);
        }
        return retour;
    }

    public static String getRequest(String temp) {
        if (temp == null || temp.compareTo("") == 0) {
            return "";
        }
        return temp;
    }

    public static String getValeurNonNull(String temp) {
        if (temp == null || temp.compareTo("") == 0) {
            return "%";
        }
        return temp;
    }

    public static String makePK(int longPK, String indPk, String nomProcedureSequence) throws Exception {
        int maxSeq = Utilitaire.getMaxSeq(nomProcedureSequence);
        String nombre = Utilitaire.completerInt(longPK, maxSeq);
        return String.valueOf(indPk) + String.valueOf(nombre);
    }

    public static String[] getNomColonne(Object a) {
        String[] retour = null;
        Field[] f = a.getClass().getDeclaredFields();
        retour = new String[f.length];
        for (int i = 0; i < f.length; ++i) {
            retour[i] = f[i].getName();
        }
        return retour;
    }

    public static String[] getNomColonne(Object a, String typ) {
        Object[] retour = null;
        Field[] f = a.getClass().getFields();
        Vector<String> v = new Vector<String>();
        for (int i = 0; i < f.length; ++i) {
            if (typ.compareToIgnoreCase("nombre") == 0 && (f[i].getType().getName().compareToIgnoreCase("int") == 0 || f[i].getType().getName().compareToIgnoreCase("double") == 0 || f[i].getType().getName().compareToIgnoreCase("float") == 0 || f[i].getType().getName().compareToIgnoreCase("short") == 0)) {
                v.add(f[i].getName());
            }
            if (typ.compareToIgnoreCase("chaine") != 0 || f[i].getType().getName().compareToIgnoreCase("String") != 0) continue;
            v.add(f[i].getName());
        }
        retour = new String[v.size()];
        v.copyInto(retour);
        return retour;
    }

    public static String cryptWord(String mot) {
        int niveau = (int)Math.round(Math.random() * 10.0);
        int sens = (int)Math.round(Math.random());
        if (niveau == 0) {
            niveau = -5;
        }
        return Utilitaire.cryptWord(mot, niveau, sens);
    }

    public static String cryptWord(String mot, int niveauCrypt, int croissante) {
        if (croissante == 0) {
            return Utilitaire.cryptWord(mot, niveauCrypt, true);
        }
        return Utilitaire.cryptWord(mot, niveauCrypt, false);
    }

    public static String cryptWord(String mot, int niveauCrypt, boolean croissante) {
        char[] ar = mot.toCharArray();
        char[] retour = new char[ar.length];
        if (croissante) {
            for (int i = 0; i < ar.length; ++i) {
                int k = Character.getNumericValue(ar[i]);
                retour[i] = k < 36 - niveauCrypt ? Character.forDigit(k + niveauCrypt, 36) : ar[i];
            }
        } else {
            for (int i = 0; i < ar.length; ++i) {
                int k = Character.getNumericValue(ar[i]);
                retour[i] = k > niveauCrypt - 1 ? Character.forDigit(k - niveauCrypt, 36) : ar[i];
            }
        }
        return new String(retour);
    }

    public static String unCryptWord(String mot, int niveauCrypt, boolean croissante) {
        char[] ar = mot.toCharArray();
        char[] retour = new char[ar.length];
        if (croissante) {
            for (int i = 0; i < ar.length; ++i) {
                int k = Character.getNumericValue(ar[i]);
                retour[i] = k < 36 - niveauCrypt ? Character.forDigit(k - niveauCrypt, 36) : ar[i];
            }
        } else {
            for (int i = 0; i < ar.length; ++i) {
                int k = Character.getNumericValue(ar[i]);
                retour[i] = k > niveauCrypt - 1 ? Character.forDigit(k + niveauCrypt, 36) : ar[i];
            }
        }
        return new String(retour);
    }

    public static int[] transformerMoisAnnee(int mois, int annee) {
        int[] retour = new int[2];
        retour[1] = annee + mois / 12;
        retour[0] = mois % 12;
        if (retour[0] == 0) {
            retour[0] = 12;
            retour[1] = retour[1] - 1;
        }
        return retour;
    }

    public static String nbToMois(int nombre) {
        String mois = null;
        switch (nombre) {
            case 1: {
                mois = "janvier";
                break;
            }
            case 2: {
                mois = "fevrier";
                break;
            }
            case 3: {
                mois = "mars";
                break;
            }
            case 4: {
                mois = "avril";
                break;
            }
            case 5: {
                mois = "mai";
                break;
            }
            case 6: {
                mois = "juin";
                break;
            }
            case 7: {
                mois = "juillet";
                break;
            }
            case 8: {
                mois = "ao&ucirc;t";
                break;
            }
            case 9: {
                mois = "septembre";
                break;
            }
            case 10: {
                mois = "octobre";
                break;
            }
            case 11: {
                mois = "novembre";
                break;
            }
            case 12: {
                mois = "decembre";
                break;
            }
            default: {
                mois = null;
            }
        }
        return mois;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Date getDatePayementEcolage() throws Exception {
        Connection c = null;
        UtilDB util = new UtilDB();
        Statement sta = null;
        ResultSet rs = null;
        Date max = null;
        try {
            c = util.GetConn();
            String param = "Select MAX(DATEFINPAYMENTECOLAGE2TRANCHE) FROM RENTRESEMESTRE";
            sta = c.createStatement();
            rs = sta.executeQuery(param);
            rs.next();
            max = rs.getDate(1);
        }
        catch (SQLException e) {
            System.out.println("getNbTuple : ".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
        finally {
            if (sta != null) {
                sta.close();
            }
            if (rs != null) {
                rs.close();
            }
            util.close_connection();
        }
        return max;
    }

    public static String datetostring(Date d) {
        String daty = null;
        SimpleDateFormat dateJava = new SimpleDateFormat("dd/MM/yyyy");
        daty = dateJava.format(d);
        return daty;
    }

    public static String datetostring(java.util.Date d) {
        String daty = null;
        SimpleDateFormat dateJava = new SimpleDateFormat("dd/MM/yyyy");
        daty = dateJava.format(d);
        return daty;
    }

    public static String datedujourlettre(String dat) {
        String jour = Utilitaire.getJour(dat);
        String mois = Utilitaire.convertDebutMajuscule(Utilitaire.nbToMois(Utilitaire.stringToInt(Utilitaire.getMois(dat))));
        String annee = Utilitaire.getAnnee(dat);
        String daty = jour + " " + mois + " " + annee;
        return daty;
    }

    public static String getIdByCb(String cb) {
        char[] dd = cb.toCharArray();
        char[] id = new char[9];
        int j = 8;
        for (int i = cb.length() - 1; i > cb.length() - 10; --i) {
            id[j] = dd[i];
            --j;
        }
        String idString = new String(id);
        return idString;
    }

    public static String getIdByCbEns(String cb) {
        return cb.substring(5, cb.length());
    }

    public static String getAnneeParam(String param, String daty) {
        String annee = daty.split(param)[0];
        return annee;
    }

    public static String getJourParam(String param, String daty) {
        return Utilitaire.completerInt(2, Utilitaire.split(daty, param)[2]);
    }

    public static String getMoisParam(String param, String daty) {
        return Utilitaire.completerInt(2, Utilitaire.split(daty, param)[1]);
    }

    public static Date string_dateParam(String param, String patterne, String daty) {
        try {
            Date hiredate;
            SimpleDateFormat formatter = new SimpleDateFormat(patterne);
            formatter.applyPattern(patterne);
            formatter.setTimeZone(TimeZone.getTimeZone("EUROPE"));
            String annee = Utilitaire.getAnneeParam(param, daty);
            int anneecours = Utilitaire.getAneeEnCours();
            int siecl = anneecours / 100;
            if (annee.toCharArray().length == 2) {
                annee = String.valueOf(siecl) + annee;
            }
            daty = Utilitaire.getJourParam(param, daty) + "/" + Utilitaire.getMoisParam(param, daty) + "/" + annee;
            Date date1 = hiredate = new Date(formatter.parse(daty).getTime());
            return date1;
        }
        catch (Exception e) {
            System.out.println("Error string_date :".concat(String.valueOf(String.valueOf(e.getMessage()))));
            Date date = Utilitaire.dateDuJourSql();
            return date;
        }
    }

    public static String verifNumerique(String s) {
        String res = s;
        res = res.replace(',', '.');
        String[] temp = Utilitaire.split(res, ' ');
        res = "";
        for (int i = 0; i < temp.length; ++i) {
            res = res + temp[i];
        }
        try {
            Float.valueOf(res);
            return res;
        }
        catch (Exception e) {
            return s;
        }
    }

    public static String cleanCsv(String s) {
        if (s == null) {
            return "";
        }
        s = s.replace('\r', ' ').replace('\n', ' ').replace('\t', ' ');
        if ((s = s.trim()).isEmpty() || s.equalsIgnoreCase("null")) {
            return "";
        }
        String res = Utilitaire.verifNumerique(s);
        if (res == null || res.trim().isEmpty() || res.equalsIgnoreCase("null")) {
            return "";
        }
        res = res.replace('\r', ' ').replace('\n', ' ').replace('\t', ' ');
        res = res.trim().replaceAll(" +", " ");
        return res;
    }

    public static String getXmlTag(String[] libeEntete, String[] libelleAffiche, int j) {
        if (libeEntete != null && j < libeEntete.length && libeEntete[j] != null && !libeEntete[j].trim().isEmpty()) {
            return libeEntete[j].trim().replaceAll("\\s+", "_");
        }
        if (libelleAffiche != null && j < libelleAffiche.length && libelleAffiche[j] != null && !libelleAffiche[j].trim().isEmpty()) {
            return libelleAffiche[j].trim().replaceAll("\\s+", "_");
        }
        return "col_" + j;
    }

    public static String getXmlTag(String tag, int fallbackIndex) {
        if (tag != null && !tag.trim().isEmpty()) {
            return tag.trim().replaceAll("\\s+", "_");
        }
        return "col_" + fallbackIndex;
    }

    public static String escapeXml(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        block7: for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            switch (c) {
                case '&': {
                    sb.append("&amp;");
                    continue block7;
                }
                case '<': {
                    sb.append("&lt;");
                    continue block7;
                }
                case '>': {
                    sb.append("&gt;");
                    continue block7;
                }
                case '\"': {
                    sb.append("&quot;");
                    continue block7;
                }
                case '\'': {
                    sb.append("&apos;");
                    continue block7;
                }
                default: {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public static String cleanXmlValue(String s) {
        String clean = Utilitaire.champNull(s);
        if (clean.isEmpty()) {
            return "";
        }
        String num = Utilitaire.verifNumerique(clean);
        return Utilitaire.escapeXml(num);
    }

    public static void uploadFileToCdn(InputStream f, String filename) throws Exception {
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter("http.protocol.version", (Object)HttpVersion.HTTP_1_1);
            Properties prop = CynthiaConf.load();
            HttpPost httppost = new HttpPost(prop.getProperty("cdnUri"));
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("nom", (ContentBody)new StringBody(filename));
            entity.addPart("fichiers", (ContentBody)new InputStreamBody(f, filename));
            httppost.setEntity((HttpEntity)entity);
            System.out.println("EXECUTING REQUEST : " + httppost.getRequestLine());
            HttpResponse response = httpclient.execute((HttpUriRequest)httppost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                resEntity.consumeContent();
            }
            httpclient.getConnectionManager().shutdown();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteFileFromCdn(String filename) {
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter("http.protocol.version", (Object)HttpVersion.HTTP_1_1);
            Properties prop = CynthiaConf.load();
            HttpPost httppost = new HttpPost(prop.getProperty("cdnDeleteUri"));
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("filename", (ContentBody)new StringBody(filename));
            httppost.setEntity((HttpEntity)entity);
            System.out.println("EXECUTING REQUEST : " + httppost.getRequestLine());
            HttpResponse response = httpclient.execute((HttpUriRequest)httppost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                resEntity.consumeContent();
            }
            httpclient.getConnectionManager().shutdown();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getExtensionFichier(String nomfichier) {
        String text = nomfichier;
        String[] val = text.split("\\.");
        return val[val.length - 1];
    }

    public static String testHeureValide(String heure) throws Exception {
        if (heure.contains(":")) {
            return Utilitaire.transformeHeure(heure);
        }
        if (heure.contains("H")) {
            String replace = heure.replace("H", ":");
            if (replace.contains("M")) {
                replace = replace.replace("M", "");
            }
            return Utilitaire.transformeHeure(replace);
        }
        if (heure.contains("h")) {
            String replace = heure.replace("h", ":");
            if (replace.contains("m")) {
                replace = replace.replace("m", "");
            }
            return Utilitaire.transformeHeure(replace.toString());
        }
        throw new Exception("Heure non valide.");
    }

    public static String transformeHeure(String heure) throws Exception {
        String[] str = heure.split(":");
        if (str == null || str.length < 2) {
            throw new Exception("Heure non valide.");
        }
        int hr = -1;
        int min = -1;
        try {
            hr = Integer.parseInt(str[0]);
            min = Integer.parseInt(str[1]);
        }
        catch (Exception e) {
            throw new Exception("Heure non valide.");
        }
        if (hr >= 24 || hr < 0 || min < 0 || min >= 60) {
            throw new Exception("Heure non valide.");
        }
        for (int i = 0; i < str.length; ++i) {
            if (str[i].length() >= 2) continue;
            str[i] = "0" + str[i];
        }
        heure = str[0] + ":" + str[1];
        return heure;
    }

    public static String tabToString(String[] s, String quote, String virgule) {
        String res = "";
        try {
            if (s == null || s.length == 0) {
                return res;
            }
            res = quote + s[0] + quote;
            for (int i = 1; i < s.length; ++i) {
                res = res + virgule + quote + s[i] + quote;
            }
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public static String tabToString(ClassMAPTable[] s, String attr, String quote, String virg) {
        String res = quote + quote;
        try {
            Object obj;
            if (s == null || s.length == 0) {
                return null;
            }
            if (attr == null || attr.compareToIgnoreCase("") == 0) {
                attr = s[0].getAttributIDName();
            }
            if ((obj = CGenUtil.getValeurFieldByMethod(s[0], attr)) != null) {
                res = quote + obj.toString() + quote;
            }
            for (int i = 1; i < s.length; ++i) {
                Object objet = CGenUtil.getValeurFieldByMethod(s[i], attr);
                if (objet == null) continue;
                res = res + virg + quote + objet.toString() + quote;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public static String[] stringToTab(String text, String separateur) {
        String temps = text.trim();
        String[] ret = Utilitaire.split(temps, separateur.charAt(0));
        return ret;
    }

    public static boolean isPeriodString(String value, String toReplace) {
        boolean ret = false;
        try {
            String tempp = Utilitaire.replaceChar(value, toReplace, "1");
            int val = Utilitaire.stringToInt(tempp.trim());
            if (val > 0) {
                ret = true;
            }
        }
        catch (Exception ex) {
            ret = false;
            ex.printStackTrace();
        }
        return ret;
    }

    public static Date dateMax(Date d1, Date d2) {
        if (d1 == null && d2 == null) {
            return null;
        }
        if (d1 == null) {
            return d2;
        }
        if (d2 == null) {
            return d1;
        }
        return d1.after(d2) ? d1 : d2;
    }

    public static Date dateMin(Date d1, Date d2) {
        if (d1 == null && d2 == null) {
            return null;
        }
        if (d1 == null) {
            return d2;
        }
        if (d2 == null) {
            return d1;
        }
        return d1.before(d2) ? d1 : d2;
    }

    public static String dateMax(String d1, String d2) {
        Date retour = Utilitaire.dateMax(Utilitaire.stringDate(d1), Utilitaire.stringDate(d2));
        return Utilitaire.datetostring(retour);
    }

    public static String dateMin(String d1, String d2) {
        Date retour = Utilitaire.dateMin(Utilitaire.stringDate(d1), Utilitaire.stringDate(d2));
        return Utilitaire.datetostring(retour);
    }

    public static String splitAndMergeWithQuote(String str, String quote, String separatorInitial, String separatorFinal) {
        if (str == null || str.trim().isEmpty()) {
            return "";
        }
        return Arrays.asList(str.split(separatorInitial)).stream().map(v -> quote + v + quote).collect(Collectors.joining(separatorFinal));
    }

    public static String[] getMailcc() throws Exception {
        String[] retour = null;
        try {
            TypeObjet mail = new TypeObjet();
            mail.setNomTable("mailcc");
            TypeObjet[] tabMail = (TypeObjet[])CGenUtil.rechercher((ClassMAPTable)mail, null, null, "");
            if (tabMail.length > 0) {
                retour = new String[tabMail.length];
                for (int i = 0; i < tabMail.length; ++i) {
                    retour[i] = tabMail[i].getVal();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return retour;
    }

    public static String getHeureFromTimestamp(Timestamp heure) {
        String ora = Utilitaire.completerInt(2, heure.getHours());
        String min = Utilitaire.completerInt(2, heure.getMinutes());
        String sec = Utilitaire.completerInt(2, heure.getSeconds());
        return ora + ":" + min + ":" + sec;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = Utilitaire.getCalendar(first);
        Calendar b = Utilitaire.getCalendar(last);
        int diff = b.get(1) - a.get(1);
        if (a.get(2) > b.get(2) || a.get(2) == b.get(2) && a.get(5) > b.get(5)) {
            --diff;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static String getAnneePeriode(String periode) throws Exception {
        if (periode.length() != 6) {
            throw new Exception("Format periode invalide");
        }
        return periode.substring(0, 4);
    }

    public static String getMoisPeriode(String periode) throws Exception {
        if (periode.length() != 6) {
            throw new Exception("Format periode invalide");
        }
        return periode.substring(4, 6);
    }

    public static String getPeriode(Date daty) {
        int mois = Utilitaire.getMois(daty);
        String periode = "";
        if (mois >= 1 && mois <= 3) {
            periode = periode + Utilitaire.getAnnee(daty) + "01";
        }
        if (mois >= 4 && mois <= 6) {
            periode = periode + Utilitaire.getAnnee(daty) + "02";
        }
        if (mois >= 7 && mois <= 9) {
            periode = periode + Utilitaire.getAnnee(daty) + "03";
        }
        if (mois >= 10 && mois <= 12) {
            periode = periode + Utilitaire.getAnnee(daty) + "04";
        }
        return periode;
    }

    public static String[] getMoisPeriode2(String periode) {
        String trimestre;
        String[] ret = new String[3];
        switch (trimestre = periode.substring(4)) {
            case "01": {
                ret[0] = "01";
                ret[1] = "02";
                ret[2] = "03";
                break;
            }
            case "02": {
                ret[0] = "04";
                ret[1] = "05";
                ret[2] = "06";
                break;
            }
            case "03": {
                ret[0] = "07";
                ret[1] = "08";
                ret[2] = "09";
                break;
            }
            case "04": {
                ret[0] = "10";
                ret[1] = "11";
                ret[2] = "12";
            }
        }
        return ret;
    }

    public static int comparerHeure(String heureDebut, String heureFin) throws Exception {
        Utilitaire.testHeureValide(heureDebut);
        Utilitaire.testHeureValide(heureFin);
        String[] HMDebut = heureDebut.split(":");
        String[] HMFin = heureFin.split(":");
        int h1 = Integer.valueOf(HMDebut[0]) * 3600 + 60 * Integer.valueOf(HMDebut[1]);
        int h2 = Integer.valueOf(HMFin[0]) * 3600 + 60 * Integer.valueOf(HMFin[1]);
        if (HMDebut.length > 2) {
            h1 += Integer.valueOf(HMDebut[2]).intValue();
        }
        if (HMFin.length > 2) {
            h2 += Integer.valueOf(HMFin[2]).intValue();
        }
        if (h1 < h2) {
            return 1;
        }
        if (h2 < h1) {
            return -1;
        }
        return 0;
    }

    public static int ajoutMoisPeriode(int periode, int diff) throws Exception {
        String daty = "01/" + Utilitaire.getMoisPeriode(String.valueOf(periode)) + "/" + Utilitaire.getAnneePeriode(String.valueOf(periode));
        Date datySql = Utilitaire.stringDate(daty);
        Date dt = Utilitaire.ajoutMoisDate(datySql, diff);
        String annee = Utilitaire.getAnnee(Utilitaire.datetostring(dt));
        String mois = Utilitaire.getMois(Utilitaire.datetostring(dt));
        int retour = Utilitaire.stringToInt(annee + mois);
        return retour;
    }

    public static String ajoutMoisPeriode(String periode, int diff) throws Exception {
        int retour = Utilitaire.ajoutMoisPeriode(Utilitaire.stringToInt(periode), diff);
        return String.valueOf(retour);
    }

    public static String incrementLettre(char[] lettreInit) {
        char[] lettre = lettreInit;
        for (int i = lettre.length - 1; i >= 0; --i) {
            for (char j = 'a'; j <= 'z'; j = (char)(j + '\u0001')) {
                if (lettre[i] == j && j == 'z') {
                    lettre[i] = 97;
                    continue;
                }
                if (lettre[i] != j || j >= 'z') continue;
                char tmp = j;
                lettre[i] = tmp = (char)(tmp + '\u0001');
                return new String(lettre);
            }
        }
        return null;
    }

    public static int calculeAge(Date naissance) {
        int age = Utilitaire.getAneeEnCours() - Utilitaire.getAnnee(naissance);
        Date temp = new Date(naissance.getYear(), naissance.getMonth(), naissance.getDay());
        temp.setYear(Utilitaire.getAneeEnCours() - 1900);
        if (Utilitaire.dateDuJourSql().compareTo(temp) < 0) {
            --age;
        }
        return age;
    }

    public static int calculeAge(String date) {
        return Utilitaire.calculeAge(Utilitaire.stringDate(date));
    }

    public static boolean possedeDoublon(String[] input) {
        for (int i = 0; i < input.length; ++i) {
            for (int j = 0; j < input.length; ++j) {
                if (!input[i].equals(input[j]) || i == j) continue;
                return true;
            }
        }
        return false;
    }

    public static int calculeAge(Date naissance, Date parrapport) {
        int rapport = Utilitaire.getAnnee(parrapport);
        int age = rapport - Utilitaire.getAnnee(naissance);
        Date temp = new Date(naissance.getTime());
        temp.setYear(rapport - 1900);
        if (parrapport.compareTo(temp) < 0) {
            --age;
        }
        return age;
    }

    public static int getTrimestreByMois(int mois) {
        if (mois <= 3) {
            return 1;
        }
        if (mois > 3 && mois <= 6) {
            return 2;
        }
        if (mois > 6 && mois <= 9) {
            return 3;
        }
        if (mois > 9 && mois <= 12) {
            return 4;
        }
        return 0;
    }

    public static String[] enleverNulouVide(String[] array) {
        ArrayList<String> list = new ArrayList<String>();
        for (String s : array) {
            if (s == null || s.length() <= 0) continue;
            list.add(s);
        }
        array = list.toArray(new String[list.size()]);
        return array;
    }

    public static int calculNbreJourOuvrable(String mois, String annee) {
        java.util.Date startDate = new java.util.Date(Integer.valueOf(annee) - 1900, Integer.valueOf(mois) - 1, 1);
        String dates = Utilitaire.getLastDayOfDate("01/" + mois + "/" + annee);
        java.util.Date endDate = Utilitaire.stringToDate("yyyy-MM-dd", dates);
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int workDays = 0;
        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }
        do {
            startCal.add(5, 1);
            if (startCal.get(7) == 7 || startCal.get(7) == 1) continue;
            ++workDays;
        } while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());
        return workDays;
    }

    public static Date getDateAvant(Date d, int ajout) {
        GregorianCalendar c = new GregorianCalendar(d.getYear() + 1900, d.getMonth(), d.getDate());
        c.set(5, c.get(5) + ajout);
        java.util.Date dt = c.getTime();
        return new Date(dt.getTime());
    }

    public static Date[] convertIntervaleToListDate(Date dmin, Date dmax) {
        Date d1;
        Vector<Date> v = new Vector<Date>();
        int i = 0;
        while (Utilitaire.compareDaty(d1 = Utilitaire.getDateAvant(dmin, i), dmax) != 0) {
            v.add(d1);
            ++i;
        }
        v.add(dmax);
        Object[] res = new Date[v.size()];
        v.copyInto(res);
        return res;
    }

    public static int getEcheance(int mois) {
        int ret = 0;
        if (mois == 1 || mois == 4 || mois == 7 || mois == 10) {
            ret = 1;
        } else if (mois == 2 || mois == 5 || mois == 8 || mois == 11) {
            ret = 2;
        } else if (mois == 3 || mois == 6 || mois == 9 || mois == 12) {
            ret = 3;
        }
        return ret;
    }

    public static String dateEnFrancais(Date date) {
        DateFormat format = DateFormat.getInstance();
        DateFormat format_fr = DateFormat.getDateInstance(0, Locale.FRENCH);
        return format_fr.format(date);
    }

    public static boolean comparerObjet(Object[] liste) {
        boolean ret = true;
        for (int i = 0; i < liste.length; ++i) {
            if (liste[0].equals(liste[i])) continue;
            ret = false;
            break;
        }
        return ret;
    }

    public static boolean validerHeureMinute(String timeString) {
        if (timeString.length() != 5) {
            return false;
        }
        if (!timeString.substring(2, 3).equals(":")) {
            return false;
        }
        int hour = Utilitaire.validateNumber(timeString.substring(0, 2));
        int minute = Utilitaire.validateNumber(timeString.substring(3, 5));
        if (hour < 0 || hour >= 24) {
            return false;
        }
        return minute >= 0 && minute < 60;
    }

    private static int validateNumber(String numberString) {
        try {
            int number = Integer.valueOf(numberString);
            return number;
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    public static int calculeAgeDate(Date naissance, Date dateRepere) {
        int age = Utilitaire.getAneeEnCours() - Utilitaire.getAnnee(naissance);
        Date temp = new Date(naissance.getYear(), naissance.getMonth(), naissance.getDay());
        temp.setYear(Utilitaire.getAneeEnCours() - 1900);
        if (dateRepere.compareTo(temp) < 0) {
            --age;
        }
        return age;
    }

    public static double truncateDouble(double number, int numDigits) {
        double result = number;
        String arg = "" + number;
        int idx = arg.indexOf(46);
        if (idx != -1 && arg.length() > idx + numDigits) {
            arg = arg.substring(0, idx + numDigits + 1);
            result = Double.parseDouble(arg);
        }
        return result;
    }

    public static double arrondirDecimalWithMode(double a, String pattern, RoundingMode mode) {
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(mode);
        String format = df.format(a).replace(",", ".");
        return Double.valueOf(format);
    }

    public static String convertJour8hEnJourHeureMinute(double jour) {
        String result = "";
        if (jour > 0.0) {
            int j_part_ent = (int)jour;
            result = j_part_ent + "j";
            double j_part_dec = jour - (double)j_part_ent;
            double heure = j_part_dec * 8.0;
            int h_part_ent = (int)heure;
            result = result + " " + h_part_ent + "h";
            double h_part_dec = heure - (double)h_part_ent;
            double minute = h_part_dec * 60.0;
            int m_part_ent = (int)minute;
            result = result + " " + m_part_ent + "min";
        }
        return result;
    }

    public static Timestamp convertStringToTimestampHour(String val, String separator) throws Exception {
        String[] tab = val.split(separator);
        if (tab.length < 3) {
            throw new Exception("Format heure invalide");
        }
        int hour = Integer.valueOf(tab[0]);
        int min = Integer.valueOf(tab[1]);
        int sec = Integer.valueOf(tab[2]);
        return new Timestamp(0, 0, 0, hour, min, sec, 0);
    }

    public static String getCurrentHeure() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String ret = sdf.format(cal.getTime()).toString();
        return ret;
    }

    public static boolean testMemeMois(Date d1, Date d2) {
        return Utilitaire.getMois(d1) == Utilitaire.getMois(d2) && Utilitaire.getAnnee(d1) == Utilitaire.getAnnee(d2);
    }

    public static String getDateSansSeparateur(String daty) {
        String jour = Utilitaire.getJour(daty);
        String mois = Utilitaire.getMois(daty);
        String annee = Utilitaire.getAnnee(daty);
        return jour + mois + annee;
    }

    public static String getDateEnLettre(String daty) {
        String jour = Utilitaire.getJour(daty);
        jour = ChiffreLettre.convertIntToString(Integer.valueOf(jour).intValue()).toUpperCase();
        String mois = Utilitaire.nbToMois(Integer.valueOf(Utilitaire.getMois(daty))).toUpperCase();
        String annee = Utilitaire.getAnnee(daty);
        return jour + " " + mois + " " + annee;
    }

    public static boolean isValidDate(String input, String format) {
        boolean valid = false;
        try {
            if (!input.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                java.util.Date output = dateFormat.parse(input);
                valid = dateFormat.format(output).equals(input);
            }
        }
        catch (Exception ex) {
            valid = false;
        }
        return valid;
    }

    public static String getHeureLettre(String heure) {
        String[] heuresplit = heure.split(":");
        heuresplit[0] = heuresplit[0].compareToIgnoreCase("01") == 0 || heuresplit[0].compareToIgnoreCase("00") == 0 ? ChiffreLettre.convertIntToString(Integer.valueOf(heuresplit[0]).intValue()).toUpperCase() + "E HEURE " : (heuresplit[0].compareToIgnoreCase("21") == 0 ? ChiffreLettre.convertIntToString(Integer.valueOf(heuresplit[0]).intValue()).toUpperCase() + "E HEURES " : ChiffreLettre.convertIntToString(Integer.valueOf(heuresplit[0]).intValue()).toUpperCase() + " HEURES ");
        heuresplit[1] = heuresplit[1].compareToIgnoreCase("00") == 0 ? "" : (heuresplit[1].compareToIgnoreCase("01") == 0 ? "ET " + ChiffreLettre.convertIntToString(Integer.valueOf(heuresplit[1]).intValue()).toUpperCase() + "E MINUTE" : "ET " + ChiffreLettre.convertIntToString(Integer.valueOf(heuresplit[1]).intValue()).toUpperCase() + " MINUTES");
        return heuresplit[0] + heuresplit[1];
    }

    public static double arrondiInf(double k) {
        return Utilitaire.arrondirDecimalWithMode(k, "#", RoundingMode.DOWN);
    }

    public static int arrondiSupEntier(double k) {
        return (int)Math.ceil(k);
    }

    public int getHeure(String heure) {
        if (heure == null || heure.isEmpty()) {
            return 0;
        }
        String[] heuresplit = heure.split(":");
        if (heuresplit.length > 1) {
            return Utilitaire.stringToInt(heuresplit[0]);
        }
        return 0;
    }

    public static java.util.Date castString2Date(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = formatter.parse(dateString);
        return date;
    }

    public static long getDateDiff(java.util.Date date1, java.util.Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static java.util.Date modifyDate(String inputDate) throws Exception {
        java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);
        return date;
    }

    public static String splitDate(String date) {
        String[] split = date.split("/");
        return split[2] + "-" + split[1] + "-" + split[0];
    }

    public static ArrayList<String> getListDateBetween2Date(String date1, String date2) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        java.util.Date fromDate = Utilitaire.modifyDate(Utilitaire.splitDate(date1));
        java.util.Date toDate = Utilitaire.modifyDate(Utilitaire.splitDate(date2));
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        result.add(date1);
        while (cal.getTime().before(toDate)) {
            cal.add(5, 1);
            if (cal.get(7) == 7 || cal.get(7) == 1) continue;
            result.add(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
        }
        return result;
    }

    public static String[] listJours() {
        return new String[]{"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
    }

    public static String champNullToTiret(String nul) {
        if (nul == null) {
            return "-";
        }
        if (nul.compareToIgnoreCase("null") == 0) {
            return "-";
        }
        if (nul.compareToIgnoreCase("") == 0) {
            return "-";
        }
        return nul;
    }

    public static String anneeSuivantDateDuJour() {
        String daty = Utilitaire.dateDuJour();
        String[] split = daty.split("/");
        int anneeSuiv = Integer.parseInt(split[2]) + 1;
        String val = split[0] + "/" + split[1] + "/" + anneeSuiv;
        return val;
    }

    public static double convertirM3enLitre(String q) {
        String[] val;
        if (q.contains("mc") && (val = Utilitaire.split(q, "mc")).length >= 1) {
            return Utilitaire.stringToDouble(val[0]) * 1000.0;
        }
        return Utilitaire.stringToDouble(q);
    }

    public static String escapeSQLString(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder escapedText = new StringBuilder();
        block3: for (int i = 0; i < s.length(); ++i) {
            char currentChar = s.charAt(i);
            switch (currentChar) {
                case '\'': {
                    escapedText.append("''");
                    continue block3;
                }
                default: {
                    escapedText.append(currentChar);
                }
            }
        }
        return escapedText.toString();
    }

    public static String separateurMillier(String valeur) {
        return Utilitaire.separateurMillier(' ', valeur);
    }

    public static String separateurMillier(char separateur, String valeur) {
        double val = 0.0;
        if (!valeur.matches("\\p{Digit}+")) {
            return valeur;
        }
        val = Double.valueOf(valeur);
        DecimalFormat format = new DecimalFormat("000,000");
        DecimalFormatSymbols s = format.getDecimalFormatSymbols();
        s.setGroupingSeparator(separateur);
        format.setDecimalFormatSymbols(s);
        return format.format(val);
    }

    public static int getDiffMoisPrecis(Date dateMax, Date dateMin) throws Exception {
        int moisdifference = Utilitaire.diffMoisDaty(dateMax, dateMin);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateMax);
        int dayMax = cal.get(5);
        cal.setTime(dateMin);
        int dayMin = cal.get(5);
        int jour = dayMax - dayMin;
        if (jour <= 0) {
            // empty if block
        }
        return moisdifference;
    }

    public static String splitClassName(String arg) throws Exception {
        String[] res = arg.split("\\.");
        return res[res.length - 1];
    }

    public static ArrayList<String> genererDatesEntre(String date1, String date2) throws Exception {
        String[] jourFerie = new String[]{};
        Date startdate = new Date(Utilitaire.stringToDate("dd/MM/yyyy", date1).getTime());
        Date enddate = new Date(Utilitaire.stringToDate("dd/MM/yyyy", date2).getTime());
        Calendar enddateone = Calendar.getInstance();
        enddateone.setTime(enddate);
        enddateone.add(5, 1);
        ArrayList<Date> jourFerieDate = new ArrayList<Date>();
        for (int i = 0; i < jourFerie.length; ++i) {
            jourFerieDate.add(new Date(Utilitaire.stringToDate("dd/MM/yyyy", jourFerie[i]).getTime()));
        }
        ArrayList<Date> dates = new ArrayList<Date>();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);
        while (calendar.getTime().before(enddateone.getTime())) {
            java.util.Date result = calendar.getTime();
            Date dateToAdd = new Date(result.getTime());
            if (Utilitaire.dayOfDate(dateToAdd) != 1 && Utilitaire.dayOfDate(dateToAdd) != 7 && !jourFerieDate.contains(dateToAdd)) {
                dates.add(dateToAdd);
            }
            ((Calendar)calendar).add(5, 1);
        }
        ArrayList<String> listResult = new ArrayList<String>();
        for (int i = 0; i < dates.size(); ++i) {
            listResult.add(Utilitaire.formatterDaty((Date)dates.get(i)));
        }
        return listResult;
    }

    public static String getAWhereIn(String[] val, String column) throws Exception {
        String query = "";
        if (val.length == 0) {
            throw new Exception("La valeur de la selection multiple est vide.");
        }
        query = query + " AND " + column + " IN (";
        query = query + "'" + val[0] + "'";
        for (int i = 1; i < val.length; ++i) {
            query = query + ",'" + val[i] + "'";
        }
        query = query + ") ";
        return query;
    }

    public static String getAWhereIn(ClassMAPTable[] val, String column, String attributDansVal) throws Exception {
        String query = "";
        if (val.length == 0) {
            throw new Exception("La valeur de la selection multiple est vide.");
        }
        query = query + " AND " + column + " IN (";
        query = query + "'" + CGenUtil.getValeurFieldByMethod(val[0], attributDansVal).toString() + "'";
        for (int i = 1; i < val.length; ++i) {
            query = query + ",'" + CGenUtil.getValeurFieldByMethod(val[i], attributDansVal).toString() + "'";
        }
        query = query + ") ";
        return query;
    }

    public static boolean estJourOuvrable(Date daty) throws Exception {
        boolean result = false;
        if (Utilitaire.dayOfDate(daty) != 1 && Utilitaire.dayOfDate(daty) != 7) {
            result = true;
        }
        return result;
    }

    public static Date getDebutSemaine(Date d) {
        LocalDate aujourdHui = d.toLocalDate();
        LocalDate debutSemaine = aujourdHui.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return Date.valueOf(debutSemaine);
    }

    public static String getDebutSemaineString() {
        return Utilitaire.formatterDaty(Utilitaire.getDebutSemaine(Utilitaire.dateDuJourSql()));
    }

    public static String getFinSemaineString() {
        return Utilitaire.formatterDaty(Utilitaire.ajoutJourDate(Utilitaire.getDebutSemaine(Utilitaire.dateDuJourSql()), 6));
    }

    public String getJourDeLaSemaineDate(String daty) throws Exception {
        try {
            Date d = Utilitaire.stringDate(daty);
            int jourdelasemaine = d.getDay();
            String result = "-";
            switch (jourdelasemaine) {
                case 1: {
                    result = "Lundi";
                    break;
                }
                case 2: {
                    result = "Lundi";
                    break;
                }
                case 3: {
                    result = "Lundi";
                    break;
                }
                case 4: {
                    result = "Lundi";
                    break;
                }
                case 5: {
                    result = "Lundi";
                }
            }
            return result;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            throw exc;
        }
    }

    public static Date getDateAnneeProchaine(Date arg) throws Exception {
        Date result = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(arg);
        int day = cal.get(5);
        int year = cal.get(1);
        int mois = Utilitaire.getMoisEnCoursReel();
        String date_s = day - 1 + "/" + mois + "/" + (year + 1);
        result = Utilitaire.string_date("dd/MM/yyyy", date_s);
        return result;
    }

    public static String formatDateRecherche(String date) {
        String result = "";
        if (date.contains("/") & Utilitaire.stringDate(date) != null) {
            String[] split = date.split("/");
            result = split[2] + "-" + split[1] + "-" + split[0];
            return result;
        }
        return date;
    }

    public static boolean estNumerique(String lettreChiffre) {
        char[] lc;
        for (char c : lc = lettreChiffre.toCharArray()) {
            if (Character.isDigit(c)) continue;
            return false;
        }
        return true;
    }

    public static Date lastDayOfMonth(Date daty) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(daty);
        c.set(5, c.getActualMaximum(5));
        return new Date(c.getTimeInMillis());
    }

    public static String separerEnMillier(String nombre) {
        String s = "";
        char[] tab = nombre.toCharArray();
        int k = 1;
        for (int i = tab.length - 1; i >= 0; --i) {
            char t = tab[i];
            s = String.valueOf(t) + s;
            if (k == 3) {
                s = " " + s;
                k = 1;
                continue;
            }
            ++k;
        }
        return s;
    }

    public static int getNumeroPage(int indice, int n) {
        for (int k = 1; k > 0; ++k) {
            if (indice >= k * n) continue;
            return k + 1;
        }
        return 2;
    }

    public static int genererNouveauNumero(int indice, int n) {
        for (int k = 1; k > 0; ++k) {
            if (indice >= k * n) continue;
            return k * n + 2;
        }
        return 2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getParametreAnnee() {
        Connection conn = null;
        try {
            conn = new UtilDB().GetConn();
            TypeObjet o = new TypeObjet();
            o.setNomTable("configuration");
            TypeObjet[] list = (TypeObjet[])CGenUtil.rechercher(o, null, null, conn, " AND ID = 'PRM00001'");
            if (list != null && list.length != 0) {
                String string = list[0].getVal();
                return string;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Utilitaire.getAnneeEnCours();
    }

    public static int getMillenismeAnnee(String annee) {
        return Utilitaire.stringToInt(annee.substring(2));
    }

    public static String[] formerTableauGroupe(String val1, String val2) throws Exception {
        String[] retour = null;
        if ((val1 == null || val1.compareToIgnoreCase("") == 0 || val1.compareToIgnoreCase("-") == 0) && val2 != null && val2.compareToIgnoreCase("") != 0) {
            retour = new String[]{val2};
            return retour;
        }
        if ((val2 == null || val2.compareToIgnoreCase("") == 0) && val1 != null && val1.compareToIgnoreCase("") != 0) {
            retour = new String[]{val1};
            return retour;
        }
        if (!(val2 != null && val2.compareToIgnoreCase("") != 0 || val1 != null && val1.compareToIgnoreCase("") != 0)) {
            return null;
        }
        retour = new String[]{val1, val2};
        return retour;
    }

    public static String TraitementMots(String mots) {
        String motsApres = "";
        int longueurMots = mots.length();
        int resteDivision = longueurMots % 4;
        int nbDivision = longueurMots / 4 + 1;
        for (int i = 0; i < nbDivision; ++i) {
            if (resteDivision == 0 && i < nbDivision - 1) {
                String string = motsApres = i == nbDivision - 2 ? motsApres + "<div>" + mots.substring(i * 4, longueurMots) + "</div>" : motsApres + "<div>" + mots.substring(i * 4, i * 4 + 4) + "-</div>";
            }
            if (resteDivision <= 0) continue;
            motsApres = i == nbDivision - 1 ? motsApres + "<div>" + mots.substring(i * 4, i * 4 + resteDivision) + "</div>" : motsApres + "<div>" + mots.substring(i * 4, i * 4 + 4) + "-</div>";
        }
        return motsApres;
    }

    public static String TraitementMotsVerticale(String mots) {
        String motsApres = "";
        int longueurMots = mots.length();
        int nbDivision = longueurMots / 4 + 1;
        for (int i = 0; i < nbDivision; ++i) {
            motsApres = motsApres + motsApres + "<div>" + mots.substring(i, i + 1) + "</div>";
        }
        return motsApres;
    }

    public static double[] calculValeur(double na, double nc, double ma, double mc) {
        double[] coef = new double[2];
        while (na > ma & nc < mc) {
            nc += 0.01;
            na -= 0.01;
        }
        coef[0] = na;
        coef[1] = nc;
        return coef;
    }

    public static String getValPourcentage(String valeur) {
        return null;
    }

    public static String remplacePourcentage(String valeur) {
        String retour = "";
        if (valeur == null) {
            return "";
        }
        char[] val = new char[valeur.length()];
        val = valeur.toCharArray();
        int taille = val.length;
        if (valeur.compareToIgnoreCase("") == 0) {
            return "";
        }
        if (valeur.compareToIgnoreCase("%") == 0 || valeur == null) {
            return "%25";
        }
        retour = Utilitaire.remplaceMot(valeur, "%", "%25");
        retour = Utilitaire.remplaceMot(retour, " ", "%20");
        return retour;
    }

    public static String[] getBorneAnneeEnCours() {
        return null;
    }

    public static boolean checkNumber(String requested) throws Exception {
        boolean numeric = true;
        try {
            Double d = Double.parseDouble(requested);
        }
        catch (NumberFormatException e) {
            numeric = false;
        }
        return numeric;
    }

    public static String getStringAC(String ac) {
        String finals = "";
        if (ac.indexOf("_") > 0) {
            String[] tab = ac.split("_");
            for (int i = 0; i < tab.length - 1; ++i) {
                finals = finals + tab[i] + "_";
            }
        }
        return finals;
    }

    public static String completerIntFin(int longuerChaine, int nombre) {
        String zero = null;
        zero = "";
        for (int i = 0; i < longuerChaine - String.valueOf(nombre).length(); ++i) {
            zero = String.valueOf(String.valueOf(zero)).concat("0");
        }
        return String.valueOf(String.valueOf(nombre)) + String.valueOf(zero);
    }

    public static String completerIntFin(int longuerChaine, String nombre2) {
        String zero = null;
        zero = "";
        for (int i = 0; i < longuerChaine - nombre2.length(); ++i) {
            zero = String.valueOf(String.valueOf(zero)).concat("0");
        }
        return nombre2 + String.valueOf(zero);
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        }
        catch (NullPointerException | NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return StringEscapeUtils.escapeHtml4((String)value).replace("'", "&#39;");
    }

    public static boolean isChampNumerique(Champ c) {
        String t;
        if (c == null) {
            return false;
        }
        String typeJava = c.getTypeJava();
        String typeSql = c.getTypeColonne();
        if (typeJava != null && ((t = typeJava.toLowerCase(Locale.ROOT)).equals("int") || t.equals("short") || t.equals("byte") || t.contains("integer") || t.contains("double") || t.contains("float") || t.contains("long") || t.contains("bigdecimal") || t.contains("number"))) {
            return true;
        }
        return typeSql != null && typeSql.equalsIgnoreCase("Number");
    }

    public static boolean isChampTexte(Champ c) {
        String s;
        String t;
        if (c == null) {
            return false;
        }
        String typeJava = c.getTypeJava();
        String typeSql = c.getTypeColonne();
        if (typeJava != null && ((t = typeJava.toLowerCase(Locale.ROOT)).contains("date") || t.contains("time") || t.contains("calendar"))) {
            return false;
        }
        if (typeSql != null && ((s = typeSql.toLowerCase(Locale.ROOT)).contains("date") || s.contains("time") || s.contains("timestamp"))) {
            return false;
        }
        return !Utilitaire.isChampNumerique(c);
    }

    public static String[] transformerLangue(String[] mots, String lang) {
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        String[] ret = new String[mots.length];
        for (int i = 0; i < mots.length; ++i) {
            ret[i] = RB.getString(mots[i]);
        }
        return ret;
    }

    public static String completerIntFin(int longuerChaine, String nombre2, int nbr) {
        String rpad = "";
        for (int i = 0; i < longuerChaine - nombre2.length(); ++i) {
            rpad = rpad.concat(String.valueOf(nbr));
        }
        return nombre2 + String.valueOf(rpad);
    }

    public static Date ajoutHeureDate(Date date, int nbheures) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(11, nbheures);
        return new Date(calendar.getTime().getTime());
    }

    public static double string_to_double(String requested) throws Exception {
        double res = 0.0;
        if (requested == null || requested.equals("")) {
            throw new Exception("Au moin un champ est vide");
        }
        res = Double.valueOf(requested);
        return res;
    }

    public static int getJourDateNombre(Date daty) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(daty);
        int day = cal.get(7);
        return day;
    }

    public static int getWeekOfMonthFromDate(Date date) {
        Calendar ca1 = Calendar.getInstance();
        ca1.set(Utilitaire.getAnnee(date), Utilitaire.getMois(date) - 1, date.getDate());
        ca1.setMinimalDaysInFirstWeek(1);
        int wk = ca1.get(4);
        return wk;
    }

    public static TreeMap<Integer, List<Date>> getListWeekWithDate(int mois, int annee) {
        TreeMap<Integer, List<Date>> liste = new TreeMap<Integer, List<Date>>();
        Date dateDebut = Utilitaire.string_date("dd/MM/yyyy", "01/" + mois + "/" + annee);
        int lastDay = Utilitaire.getLastDayOfDateSQL(dateDebut).getDate();
        Date tmp = new Date(dateDebut.getTime());
        for (int i = 1; i <= lastDay; ++i) {
            int wk = Utilitaire.getWeekOfMonthFromDate(tmp);
            if (!liste.containsKey(wk)) {
                liste.put(wk, new ArrayList());
                liste.get(wk).add(tmp);
            } else {
                liste.get(wk).add(tmp);
            }
            tmp = Utilitaire.ajoutJourDate(tmp, 1);
        }
        return liste;
    }

    private static double roundConge(double value) {
        double result = value < 0.5 ? 0.0 : (value >= 0.5 && value < 1.0 ? 0.5 : (value >= 1.0 && value < 1.5 ? 1.0 : (value >= 1.5 && value < 2.0 ? 1.5 : (value >= 2.0 && value < 2.5 ? 2.0 : 2.5))));
        return result;
    }

    public static double calculerProrataCongeUp(Date daty) {
        double prorata = 0.0;
        if (daty != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(daty);
            int jour = cal.get(5);
            String lastDayString = Utilitaire.getLastDayOfDate(daty);
            Date lastDay = Utilitaire.stringDate(Utilitaire.formatterDaty(lastDayString));
            cal.setTime(lastDay);
            int lastjour = cal.get(5);
            prorata = (double)(lastjour - jour + 1) * 2.5 / (double)lastjour;
        }
        return Utilitaire.roundConge(prorata);
    }

    public static double calculerProrataCongeUp(Date daty1, Date daty2) {
        double prorata = 0.0;
        if (daty1 != null && daty2 != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(daty1);
            int jour = cal.get(5);
            cal.setTime(daty2);
            int lastjour = cal.get(5);
            prorata = (double)(lastjour - jour + 1) * 2.5 / (double)lastjour;
        }
        return Utilitaire.roundConge(prorata);
    }

    public static double calculerProrataCongeDown(Date daty) {
        double prorata = 0.0;
        if (daty != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(daty);
            double jour = cal.get(5);
            String lastDayString = Utilitaire.getLastDayOfDate(daty);
            Date lastDay = Utilitaire.stringDate(Utilitaire.formatterDaty(lastDayString));
            cal.setTime(lastDay);
            double lastjour = cal.get(5);
            prorata = jour * 2.5 / lastjour;
        }
        return Utilitaire.roundConge(prorata);
    }

    public static Boolean isDouble(String arg) throws Exception {
        try {
            Double.parseDouble(arg);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isNumber(String requested) throws Exception {
        boolean numeric = true;
        try {
            Double d = Double.parseDouble(requested);
        }
        catch (NumberFormatException e) {
            throw new Exception("Champ " + requested + " doit etre un nombre");
        }
        return numeric;
    }

    public static Date getDebutDuMoisByMoisAnnee(int mois, int annee) throws Exception {
        return new Date(annee - 1900, mois - 1, 1);
    }

    public static Date getFinDuMoisByMoisAnnee(int mois, int annee) throws Exception {
        return Utilitaire.lastDayOfMonth(Utilitaire.getDebutDuMoisByMoisAnnee(mois, annee));
    }

    public static Date getFinDuMoisByMoisAnnee(String mois, String annee) throws Exception {
        return Utilitaire.lastDayOfMonth(Utilitaire.getDebutDuMoisByMoisAnnee(Utilitaire.stringToInt(mois), Utilitaire.stringToInt(annee)));
    }

    public static boolean contientIgnoreCase(String valeurCible, String filtre) throws Exception {
        String valCibleupper = valeurCible.toUpperCase();
        if (filtre == null || filtre.compareToIgnoreCase("%") == 0) {
            return true;
        }
        String filtreUpper = filtre.toUpperCase();
        if (filtreUpper.startsWith("%") && !filtreUpper.endsWith("%")) {
            filtreUpper.substring(1);
            if (valCibleupper.endsWith(filtre)) {
                return true;
            }
        } else if (!filtreUpper.startsWith("%") && filtreUpper.endsWith("%")) {
            filtreUpper.substring(0, filtreUpper.length());
            if (valCibleupper.startsWith(filtreUpper)) {
                return true;
            }
        } else if (filtreUpper.startsWith("%") && filtreUpper.endsWith("%")) {
            filtreUpper.substring(0, filtreUpper.length());
            filtreUpper.substring(1);
            if (valCibleupper.contains(filtreUpper)) {
                return true;
            }
        } else if (valCibleupper.compareToIgnoreCase(filtreUpper) == 0) {
            return true;
        }
        return false;
    }

    public static String stringToTab(String[] s, String quote, String virgule) {
        String res = "";
        try {
            res = quote + s[0] + quote;
            for (int i = 1; i < s.length; ++i) {
                res = res + virgule + quote + s[i] + quote;
            }
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getMaxId(String table) {
        String retour = "---";
        Connection c = null;
        try {
            c = new UtilDB().GetConn();
            Statement sta = c.createStatement();
            ResultSet res = sta.executeQuery("select max(id) from " + table);
            res.next();
            retour = res.getString(1);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                c.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return retour;
    }

    public static Date stringDate(String daty, String format) {
        Date sqlDate = Utilitaire.dateDuJourSql();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            java.util.Date date = sdf.parse(daty);
            sqlDate = new Date(date.getTime());
        }
        catch (Exception e) {
            System.out.println("Error stringDate :".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
        return sqlDate;
    }

    public static List<Date> getDateInInterval(Date debut, Date fin, String type, int[] jours) throws Exception {
        ArrayList<Date> result = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        int i = 0;
        while (true) {
            Date temp = Utilitaire.addType(debut, type, i);
            int j = 0;
            int k = 0;
            while (k < jours.length) {
                Date temp1 = Utilitaire.addDay(temp, j);
                calendar.setTime(temp1);
                if (fin.after(temp1) && calendar.get(7) == jours[k]) {
                    ++k;
                    result.add(Utilitaire.copyValue(temp1));
                }
                if (fin.before(temp1)) break;
                ++j;
            }
            if (fin.before(temp)) break;
            ++i;
        }
        return result;
    }

    public static Date copyValue(java.util.Date date) throws Exception {
        return new Date(date.getTime());
    }

    public static Date addType(Date date, String type, int amount) throws Exception {
        if (Constante.typeIntervalJour.compareToIgnoreCase(type) == 0) {
            return Utilitaire.addDay(date, amount);
        }
        if (Constante.typeIntervalHebdo.compareToIgnoreCase(type) == 0) {
            return Utilitaire.addWeek(date, amount);
        }
        if (Constante.typeIntervalbimensuel.compareToIgnoreCase(type) == 0) {
            return Utilitaire.addWeek(date, 2 * amount);
        }
        if (Constante.typeIntervalMensuel.compareToIgnoreCase(type) == 0) {
            return Utilitaire.addMonth(date, amount);
        }
        throw new Exception("Interval non valide !");
    }

    public static Date addDay(Date date, int amount) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, amount);
        return new Date(calendar.getTime().getTime());
    }

    public static Date addMonth(Date date, int amount) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, amount);
        return new Date(calendar.getTime().getTime());
    }

    public static Date addWeek(Date date, int amount) throws Exception {
        return Utilitaire.addDay(date, 7 * amount);
    }

    public static int[] stringToInt(String[] value) {
        int[] ret = new int[value.length];
        for (int i = 0; i < value.length; ++i) {
            ret[i] = Utilitaire.stringToInt(value[i]);
        }
        return ret;
    }

    public static boolean isWeekEndSunday(Date date) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(7);
        return 1 == dayOfWeek;
    }

    public static List<Date> getIntervalleDateNonFerieParType(Date debut, Date fin, String type, String[] jours) throws Exception {
        int[] joursint = Utilitaire.stringToInt(jours);
        List<Date> dateinterval = Utilitaire.getDateInInterval(debut, fin, type, joursint);
        ArrayList<Date> result = new ArrayList<Date>();
        int limit = dateinterval.size();
        System.out.println("limit: " + limit);
        for (int i = 0; i < limit; ++i) {
            Date temp = dateinterval.get(i);
            if (Utilitaire.isWeekEndSunday(temp)) continue;
            result.add(temp);
        }
        return result;
    }

    public static Date stringToSqlDate(String pattern, String daty) {
        java.util.Date dateUtil = Utilitaire.stringToDate(pattern, daty);
        Date ret = new Date(dateUtil.getTime());
        return ret;
    }

    public static Date getDateDebutDuplication(Date datyinf, int jour) {
        while (datyinf.getDay() != jour) {
            datyinf = Utilitaire.ajoutJourDate(datyinf, 1);
        }
        return datyinf;
    }

    public static Date[] getListDateDebutDuplication(Date datyinf, int[] jours) {
        Date[] list = new Date[jours.length];
        for (int i = 0; i < jours.length; ++i) {
            list[i] = Utilitaire.getDateDebutDuplication(datyinf, jours[i]);
        }
        return list;
    }

    public static String incrementLastNumber(String id) {
        int underscoreIndex = id.lastIndexOf("_");
        if (underscoreIndex == -1) {
            return id;
        }
        String prefix = id.substring(0, underscoreIndex + 1);
        String numberPart = id.substring(underscoreIndex + 1);
        try {
            int number = Integer.parseInt(numberPart);
            return prefix + (number + 1);
        }
        catch (NumberFormatException e) {
            return id;
        }
    }

    public static String formatterDatyPerso(Date date, String pattern, Locale locale) {
        if (date == null) {
            return "";
        }
        if (locale == null) {
            locale = Locale.FRENCH;
        }
        if (pattern == null || pattern.isEmpty()) {
            pattern = "dd/MMM/yyyy";
        }
        try {
            SimpleDateFormat fmt = new SimpleDateFormat(pattern, locale);
            String result = fmt.format(date);
            if (locale.equals(Locale.ENGLISH)) {
                result = Utilitaire.addEnglishDaySuffix(result, date, pattern);
            }
            return result;
        }
        catch (Exception e) {
            return new SimpleDateFormat("dd/MMM/yyyy", locale).format(date);
        }
    }

    private static String addEnglishDaySuffix(String formattedDate, Date date, String pattern) {
        if (!pattern.contains("d")) {
            return formattedDate;
        }
        int day = Integer.parseInt(new SimpleDateFormat("d").format(date));
        String suffix = Utilitaire.getEnglishDaySuffix(day);
        return formattedDate.replaceFirst("\\b" + day + "\\b", day + suffix);
    }

    private static String getEnglishDaySuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1: {
                return "st";
            }
            case 2: {
                return "nd";
            }
            case 3: {
                return "rd";
            }
        }
        return "th";
    }

    public static int getPreviousIndex(String[] roles, String current) {
        int index = -1;
        for (int i = 0; i < roles.length; ++i) {
            if (!roles[i].equalsIgnoreCase(current)) continue;
            index = i;
            break;
        }
        if (index > 0) {
            return index - 1;
        }
        return -1;
    }

    public static String getPreviousRole(String[] roles, String current) {
        int index = -1;
        for (int i = 0; i < roles.length; ++i) {
            if (!roles[i].equalsIgnoreCase(current)) continue;
            index = i;
            break;
        }
        if (index > 0) {
            return roles[index - 1];
        }
        return null;
    }

    private static void estValide(String format, String valeur) throws Exception {
        Pattern p = Pattern.compile(format);
        Matcher m = p.matcher(valeur);
        if (!m.matches()) {
            throw new Exception("Heure non valide");
        }
    }

    public static String checkHeureFormat(String heure) throws Exception {
        String heureValide = heure;
        if (heure != null && !heure.trim().isEmpty()) {
            String[] timeSplit = heure.split(":");
            String formatHeure = "([01]?[0-9]|2[0-3])";
            String formatMinSec = "[0-5][0-9]";
            Utilitaire.estValide(formatHeure, timeSplit[0]);
            Utilitaire.estValide(formatMinSec, timeSplit[1]);
            if (timeSplit.length > 2) {
                Utilitaire.estValide(formatMinSec, timeSplit[2]);
            } else {
                if (!heure.endsWith(":")) {
                    heureValide = heureValide + ":";
                }
                heureValide = heureValide + "00";
            }
        }
        return heureValide;
    }

    public static double parseNombreFrancais(String valeur) {
        if (valeur == null || valeur.trim().isEmpty()) {
            return 0.0;
        }
        valeur = valeur.replace("\u00a0", "").replace(" ", "");
        valeur = valeur.replace(",", ".");
        return Double.parseDouble(valeur);
    }

    public static Map<String, String[]> transformerEtiquetteToMapTotal(ValeurEtiquette[][] data) throws Exception {
        LinkedHashMap<String, String[]> map = new LinkedHashMap<String, String[]>();
        try {
            ValeurEtiquette[][] val = data;
            for (int i = 0; i < val.length; ++i) {
                String[] tabString = val[i][val[i].length - 1].getValeur().split("<BR>");
                for (int k = 0; k < tabString.length; ++k) {
                    tabString[k] = tabString[k].replace(" ", "");
                }
                if (val[i][0].getValeur().trim().isEmpty()) continue;
                map.put(val[i][0].getValeur(), tabString);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la transformation");
        }
        return map;
    }

    public static Map<String, String[]> transformerEtiquetteToMapTotalMerge(ValeurEtiquette[][] data) throws Exception {
        LinkedHashMap<String, String[]> map = new LinkedHashMap<String, String[]>();
        try {
            ValeurEtiquette[][] val = data;
            for (int i = 0; i < val.length; ++i) {
                String[] tabString = val[i][val[i].length - 1].getValeur().split("<BR>");
                for (int k = 0; k < tabString.length; ++k) {
                    tabString[k] = tabString[k].replace(" ", "");
                }
                if (val[i][0].getValeur().trim().isEmpty()) continue;
                String key = val[i][0].getValeur();
                String[] nouveau = tabString;
                if (map.containsKey(key)) {
                    System.out.println("cle = " + key);
                    String[] ancien = (String[])map.get(key);
                    int taille = Math.max(ancien.length, nouveau.length);
                    System.out.println("taille = " + taille);
                    String[] fusion = new String[taille];
                    for (int l = 0; l < taille; ++l) {
                        double vAncien = 0.0;
                        double vNouveau = 0.0;
                        if (l < ancien.length) {
                            try {
                                vAncien = Utilitaire.parseNombreFrancais(ancien[l]);
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                        }
                        if (l < nouveau.length) {
                            try {
                                vNouveau = Utilitaire.parseNombreFrancais(nouveau[l]);
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                        }
                        double resultat = vAncien + vNouveau;
                        System.out.println("ancien = " + vAncien);
                        System.out.println("nouveau = " + vNouveau);
                        fusion[l] = String.valueOf(resultat);
                    }
                    map.put(key, fusion);
                    continue;
                }
                map.put(key, nouveau);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la transformation");
        }
        return map;
    }

    public static Date getDebutMois() {
        LocalDate debut = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        return Date.valueOf(debut);
    }

    public static String getDebutMoisString() {
        LocalDate debut = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        return debut.format(FORMATTER);
    }

    public static Date getFinMois() {
        LocalDate fin = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return Date.valueOf(fin);
    }

    public static String getFinMoisString() {
        LocalDate fin = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return fin.format(FORMATTER);
    }

    public static Map<String, String[]> transformerEtiquetteToMapLigne(ValeurEtiquette[][] data, boolean total) throws Exception {
        LinkedHashMap<String, String[]> map = new LinkedHashMap<String, String[]>();
        try {
            ValeurEtiquette[][] val = data;
            for (int i = 0; i < val.length; ++i) {
                int ln = val[i].length + (total ? 0 : -1);
                String[] tabString = new String[val[i].length + (total ? -1 : -2)];
                for (int j = 1; j < ln; ++j) {
                    tabString[j - 1] = val[i][j].getValeur();
                    if (val[i][0].getValeur().trim().isEmpty()) continue;
                    map.put(val[i][0].getValeur(), tabString);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return map;
    }

    public static Map<String, String[]> transformerEtiquetteToMapLigne(ValeurEtiquette[][] data) throws Exception {
        return Utilitaire.transformerEtiquetteToMapLigne(data, false);
    }

    public static String[] transformerEtiquetteToTab(ValeurEtiquette[][] data) throws Exception {
        return Utilitaire.transformerEtiquetteToTab(data, false);
    }

    public static String[] transformerEtiquetteToTab(ValeurEtiquette[][] data, boolean total) throws Exception {
        int ln = data[0].length + (total ? -1 : -2);
        String[] tab = new String[ln];
        try {
            ValeurEtiquette[][] val = data;
            for (int i = 1; i < val[0].length - 1; ++i) {
                String valeur;
                tab[i - 1] = valeur = val[0][i].getValeur();
            }
            if (total) {
                tab[ln - 1] = "Total";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return tab;
    }

    public static String ajoutHeure(String heure, int aAjouterHeure, int aAjouterMin, int aAjouterSec) {
        boolean result = false;
        String[] hms = Utilitaire.split(heure, ":");
        int heureEnSec = Utilitaire.stringToInt(hms[0]);
        int minEnSec = Utilitaire.stringToInt(hms[1]);
        int seconde = 0;
        if (hms.length > 2) {
            seconde = Utilitaire.stringToInt(hms[2]);
        }
        int secondeFin = seconde + aAjouterSec;
        int minFin = minEnSec + aAjouterMin;
        int heureFin = aAjouterHeure + heureEnSec;
        minFin += secondeFin / 60;
        heureFin += minFin / 60;
        minFin %= 60;
        if ((secondeFin %= 60) < 0) {
            int minNeg = secondeFin / 60 + 1;
            minFin -= minNeg;
            secondeFin += 60 * minNeg;
        }
        if (minFin < 0) {
            int heureNeg = minFin / 60 + 1;
            heureFin -= heureNeg;
            minFin += 60 * heureNeg;
        }
        return Utilitaire.completerInt(2, heureFin) + ":" + Utilitaire.completerInt(2, minFin) + ":" + Utilitaire.completerInt(2, secondeFin);
    }

    public static boolean estTelephoneValide(String phone) {
        boolean ret = true;
        String[] telephones = phone.split(";");
        if (telephones.length == 0) {
            if (!phone.startsWith("0") || !Utilitaire.estNumerique(phone.trim()) || phone.length() < 9) {
                ret = false;
            }
        } else {
            for (String s : telephones) {
                if (s.startsWith("0") && Utilitaire.estNumerique(s.trim()) && s.length() >= 9) continue;
                return false;
            }
        }
        return ret;
    }

    public static double modifPack(double quantiteparpack, double quantite) {
        double qte = Utilitaire.arrondir(quantite / quantiteparpack, 0);
        return qte *= quantiteparpack;
    }

    public static String strCheck(String str) {
        str = str == null ? "" : str.trim();
        return str;
    }

    public static boolean testHeureValideBool(String heure) {
        try {
            String result = Utilitaire.testHeureValide(heure);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static double extraireChiffres(String str) {
        double resultats = 0.0;
        Pattern pattern = Pattern.compile("[0-9]+(?:[,.][0-9]+)?");
        Matcher matcher = pattern.matcher(str);
        StringBuilder chiffres = new StringBuilder();
        while (matcher.find()) {
            chiffres.append(matcher.group());
        }
        try {
            resultats = Double.parseDouble(chiffres.toString().replace(',', '.'));
        }
        catch (NumberFormatException e) {
            resultats = 0.0;
            e.printStackTrace();
        }
        return resultats;
    }

    public static String testHeureValide2(String heure) throws Exception {
        String replace = null;
        if (heure.contains(":")) {
            return Utilitaire.transformeHeure(heure);
        }
        if (heure.contains("H")) {
            replace = heure.replace("H", ":");
            if (replace.contains("MIN")) {
                replace = replace.replace("MIN", "");
                return Utilitaire.transformeHeure(replace);
            }
            if (replace.contains("MN")) {
                replace = replace.replace("MN", "");
                return Utilitaire.transformeHeure(replace);
            }
            if (replace.contains("M")) {
                replace = replace.replace("M", "");
                return Utilitaire.transformeHeure(replace);
            }
            if (replace.split(":").length == 1) {
                replace = replace + "00";
            }
            return Utilitaire.transformeHeure(replace.toString());
        }
        if (heure.contains("h")) {
            replace = heure.replace("h", ":");
            if (replace.contains("min")) {
                replace = replace.replace("min", "");
                return Utilitaire.transformeHeure(replace);
            }
            if (replace.contains("mn")) {
                replace = replace.replace("mn", "");
                return Utilitaire.transformeHeure(replace);
            }
            if (replace.contains("m")) {
                replace = replace.replace("m", "");
                return Utilitaire.transformeHeure(replace);
            }
            if (replace.split(":").length == 1) {
                replace = replace + "00";
            }
            return Utilitaire.transformeHeure(replace.toString());
        }
        throw new Exception("Heure non valide.");
    }

    public static String replaceDateApresWhere(ClassMAPTable arg) throws Exception {
        Field[] fields;
        String result = "";
        for (Field column : fields = arg.getFieldList()) {
            column.setAccessible(true);
            if (column.getType().equals(Date.class)) {
                if (column.get(arg) == null) continue;
                result = result + " and " + column.getName() + " = '" + Utilitaire.formatterDaty((Date)column.get(arg)) + "'";
                column.set(arg, null);
                continue;
            }
            if (!column.getType().equals(Time.class) || column.get(arg) == null) continue;
            result = result + " and " + column.getName() + " = '" + column.get(arg) + "'";
            column.set(arg, null);
        }
        return result;
    }

    public static String[] getMailcc(Connection c) throws Exception {
        String[] retour = null;
        try {
            ArrayList<String> temp = new ArrayList<String>();
            TypeObjet mail = new TypeObjet();
            mail.setNomTable("mailcc");
            TypeObjet[] tabMail = (TypeObjet[])CGenUtil.rechercher(mail, null, null, c, "");
            if (tabMail.length > 0) {
                int i;
                for (i = 0; i < tabMail.length; ++i) {
                    temp.add(tabMail[i].getVal());
                }
                retour = new String[temp.size()];
                for (i = 0; i < temp.size(); ++i) {
                    retour[i] = (String)temp.get(i);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return retour;
    }

    public static String dateCheck(String date1) {
        if (date1 == null || date1.trim().isEmpty()) {
            date1 = Utilitaire.dateDuJour();
        }
        return date1;
    }

    public static String getMoisEnCoursReelString() {
        return Utilitaire.completerInt(2, Utilitaire.getMoisEnCoursReel());
    }
}

