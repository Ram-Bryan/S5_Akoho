/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utilitaire.Utilitaire;

public class UtilitaireExcelMa {
    public static String[] caractereSpeciaux = new String[]{"\u00c2\u00b0", "^", "\u00c2\u00a8"};

    public static String[] getAllStringBetween(String s, String start, String end) {
        try {
            ArrayList<String> ret = new ArrayList<String>();
            String[] ret1 = null;
            String pattern1 = start;
            String pattern2 = end;
            String text = s.trim();
            System.out.println("avant____" + text);
            text = text.replace("\n", "").trim();
            text = text.replace("\t", "").trim();
            Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
            Matcher m = p.matcher(text);
            while (m.find()) {
                ret.add(m.group(1));
            }
            ret1 = new String[ret.size()];
            for (int i = 0; i < ret.size(); ++i) {
                System.out.println("ret.get(i) = " + (String)ret.get(i));
                ret1[i] = (String)ret.get(i);
            }
            return ret1;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public static String convertirEnTete(String html, String aChercher) {
        html = html.substring(html.indexOf("<" + aChercher) + 1);
        String ret = html.substring(html.indexOf(">") + 1);
        ret = "<" + aChercher + ">" + ret.substring(0, ret.indexOf("</" + aChercher + ">")) + "</" + aChercher + ">";
        return ret;
    }

    public static ArrayList<String> getAllStringBetweenToArray(String s, String start, String end) {
        ArrayList<String> ret = new ArrayList<String>();
        Object ret1 = null;
        String pattern1 = start;
        String pattern2 = end;
        String text = s.replace("\t", "").replace("\n", "");
        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(text);
        while (m.find()) {
            ret.add(m.group(1));
        }
        return ret;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ArrayList<ArrayList> convertHTMLTableIntoArrayList(String html) throws UnsupportedEncodingException {
        try {
            if (html == null) return null;
            if (html.compareTo("") == 0) {
                return null;
            }
            html = UtilitaireExcelMa.convertirEnTete(html, "table");
            ArrayList<String> ret = new ArrayList<String>();
            ArrayList<ArrayList> retFinal = new ArrayList<ArrayList>();
            String headerStr = "";
            if (html.contains("thead")) {
                headerStr = UtilitaireExcelMa.convertirEnTete(html, "thead");
                if (!headerStr.contains("tr")) return retFinal;
                headerStr = UtilitaireExcelMa.convertirEnTete(headerStr, "tr");
            } else {
                headerStr = "<tr>" + html.substring(html.indexOf("<tr"), html.indexOf("</tr>")) + "</tr>";
            }
            if (headerStr.length() > 0) {
                ret.add(headerStr);
            }
            if (!html.contains("tbody")) {
                if (html.indexOf("tr") == html.lastIndexOf("tr")) return UtilitaireExcelMa.convertTRToArray(ret);
            }
            if (html.contains("tbody")) {
                if (html.contains("thead")) {
                    html = html.substring(html.indexOf("</thead>") + 8);
                }
                String tbody = UtilitaireExcelMa.convertirEnTete(html, "tbody");
                while (tbody.indexOf("tr") != -1) {
                    String tmpNouveau;
                    String tmp = UtilitaireExcelMa.convertirEnTete(tbody, "tr");
                    ret.add(tmp);
                    tbody = tmpNouveau = tbody.substring(tbody.indexOf("</tr>") + 4);
                }
                return UtilitaireExcelMa.convertTRToArray(ret);
            }
            String tbody = UtilitaireExcelMa.convertirEnTete(html, "tbody");
            tbody = html.substring(html.indexOf("</tr>") + 3);
            while (tbody.indexOf("tr") != -1) {
                ret.add(UtilitaireExcelMa.convertirEnTete(tbody, "tr"));
                tbody = tbody.substring(0, tbody.indexOf("</tr>"));
            }
            return UtilitaireExcelMa.convertTRToArray(ret);
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public static ArrayList<ArrayList> convertTRToArray(ArrayList<String> tr) throws UnsupportedEncodingException {
        try {
            ArrayList<ArrayList> retFinal = new ArrayList<ArrayList>();
            for (int i = 0; i < tr.size(); ++i) {
                String tmp2;
                String[] between = UtilitaireExcelMa.getAllStringBetween(tr.get(i), "<tr>", "</tr>");
                String tmp = UtilitaireExcelMa.convertirEnTete(tr.get(i), "tr");
                ArrayList<String> ret = new ArrayList<String>();
                if (tmp.contains("<th")) {
                    while (tmp.indexOf("th") != -1) {
                        String tmpNouveau;
                        String[] temp;
                        tmp2 = UtilitaireExcelMa.convertirEnTete(tmp, "th");
                        for (String caractere : caractereSpeciaux) {
                            if (caractere.length() <= 1) continue;
                            tmp2 = Utilitaire.replaceChar(tmp2, String.valueOf(caractere.charAt(1)), " ");
                        }
                        Object valeur = UtilitaireExcelMa.getAllStringBetween(tmp2, "<th>", "</th>")[0];
                        if (((String)valeur).length() > 0 && (temp = UtilitaireExcelMa.getAllStringBetween((String)valeur, ">", "</")) != null && temp.length > 0) {
                            valeur = temp[0];
                        }
                        ret.add(((String)valeur).replace("&nbsp;", " "));
                        tmp = tmpNouveau = tmp.substring(tmp.indexOf("</th>") + 5);
                    }
                } else {
                    while (tmp.indexOf("<td") != -1) {
                        String tmpNouveau;
                        String[] temp;
                        tmp2 = UtilitaireExcelMa.convertirEnTete(tmp, "td");
                        for (String caractere : caractereSpeciaux) {
                            if (caractere.length() <= 1) continue;
                            tmp2 = Utilitaire.replaceChar(tmp2, String.valueOf(caractere.charAt(1)), " ");
                        }
                        byte[] utf8 = tmp2.getBytes("UTF-8");
                        if (!(tmp2 = new String(utf8).replace("\n", "").replace("\t", "")).endsWith("</td>")) {
                            tmp2 = tmp2 + "</td>";
                        }
                        System.out.println("tmp2 " + tmp2);
                        String valeur = UtilitaireExcelMa.getAllStringBetween(tmp2, "<td>", "</td>")[0];
                        if (valeur.length() > 0 && (temp = UtilitaireExcelMa.getAllStringBetween(valeur, ">", "</")) != null && temp.length > 0) {
                            valeur = temp[0];
                        }
                        ret.add(valeur.replace("&nbsp;", " "));
                        tmp = tmpNouveau = tmp.substring(tmp.indexOf("</td>") + 5);
                    }
                }
                retFinal.add(ret);
            }
            return retFinal;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }
}

