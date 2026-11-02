/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.AdminGen
 *  bean.ClassMAPTable
 *  utilitaire.Utilitaire
 */
package reporting.utilitaire;

import bean.AdminGen;
import bean.ClassMAPTable;
import java.io.File;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import utilitaire.Utilitaire;

public class UtilitaireCR {
    private static final String asyncLogoUrl = "https://i.ibb.co/xq63bfvD/2-Logo-Async-Footer.png";
    private static final String biciLogoUrl = "https://i.ibb.co/ZRwF8Xqs/logo-bici.png";

    public static String pageEntete() {
        String entete = "<!DOCTYPE html>\n<html>\n<head>\n  <meta charset=\"UTF-8\">\n  <style>\n    body {\n        font-family: Arial, sans-serif;\n        margin: 20px;\n        color: #333;\n        background-color: #f9f9f9;\n    }\n\n    h2 {\n        text-align: center;\n        text-transform: uppercase;\n        letter-spacing: 2px;\n        color: #004080;\n        border-bottom: 2px solid #004080;\n        padding-bottom: 8px;\n        margin-bottom: 16px;\n    }\n\n    h3 {\n        color: #004080;\n        font-size: 13px;\n        text-transform: uppercase;\n        letter-spacing: 1px;\n        border-left: 4px solid #2c7fb8;\n        padding-left: 8px;\n        margin-top: 24px;\n        margin-bottom: 8px;\n    }\n\n    p {\n        font-size: 11px;\n        color: #555;\n        margin-bottom: 10px;\n    }\n\n    table {\n        border-collapse: collapse;\n        width: 100%;\n        font-family: Arial, sans-serif;\n        font-size: 11px;\n        margin-bottom: 20px;\n        background-color: #ffffff;\n        box-shadow: 0 1px 3px rgba(0,0,0,0.08);\n    }\n\n    th {\n        background-color: #004080;\n        color: white;\n        padding: 8px 6px;\n        border: 1px solid #003060;\n        font-weight: normal;\n        letter-spacing: 0.5px;\n        vertical-align: top;\n        min-width: 80px;\n    }\n\n    td {\n        padding: 6px;\n        border: 1px solid #dde4ed;\n        text-align: center;\n        line-height: 1.4;\n        color: #333;\n    }\n\n    tr:nth-child(even) {\n        background-color: #f4f8fd;\n    }\n\n    tr:hover {\n        background-color: #e8f0fa;\n    }\n\n    .col-libelle {\n        text-align: left;\n        padding-left: 10px;\n        color: #333;\n        font-weight: bold;\n        width: 150px;\n    }\n\n    .row-total {\n        font-weight: bold;\n        background-color: #e8f0fa;\n    }\n\n    .row-total td {\n        color: #004080;\n        font-weight: bold;\n    }\n\n    table.total-table {\n        width: 50%;\n        margin-top: 10px;\n        border: 1px solid #dde4ed;\n        box-shadow: 0 1px 3px rgba(0,0,0,0.06);\n    }\n\n    table.total-table td {\n        padding: 6px 10px;\n        border: 1px solid #dde4ed;\n        font-size: 11px;\n        color: #333;\n    }\n\n    table.total-table td:last-child {\n        font-weight: bold;\n        color: #004080;\n        text-align: right;\n    }\n  </style>\n</head>\n<body>";
        return entete;
    }

    public static String basPage() {
        return "</body></html>";
    }

    public static String tableHeader(String[] headers) {
        String tableHeader = "<table class=\"crcontentfille\"><thead>";
        if (headers != null) {
            tableHeader = tableHeader + "<tr>";
            for (int i = 0; i < headers.length; ++i) {
                tableHeader = tableHeader + "<th>" + headers[i] + "</th>";
            }
            tableHeader = tableHeader + "</tr>";
        }
        tableHeader = tableHeader + "</thead><tbody>";
        return tableHeader;
    }

    public static String tableFooter() {
        return "</tbody></table><br></br>";
    }

    public static String sommeTable(ClassMAPTable[] data, String[] colSomme) throws Exception {
        String somme = "<table class=\"total-table\" align=\"center\">\n";
        for (int i = 0; i < colSomme.length; ++i) {
            somme = somme + "<tr>\n      <td><b>Total " + colSomme[i] + " :</b></td>\n      <td><b>" + AdminGen.calculSommeDouble((ClassMAPTable[])data, (String)colSomme[i]) + "</b></td>\n    </tr>\n";
        }
        somme = somme + "  </table><br></br>";
        return somme;
    }

    public static String mappingContent(ClassMAPTable data, String[] libEntete) throws Exception {
        String content = "";
        for (int i = 0; i < libEntete.length; ++i) {
            String maj = Utilitaire.convertDebutMajuscule((String)libEntete[i]);
            Object obj = UtilitaireCR.mappingAttributContent(data, maj);
            if (obj instanceof Number) {
                obj = UtilitaireCR.formatNombre(obj);
            }
            content = content + "<td>" + obj + "</td>";
        }
        return content;
    }

    public static List<Object> mappingContentList(ClassMAPTable[] data, String libEntete) throws Exception {
        ArrayList<Object> content = new ArrayList<Object>();
        String maj = Utilitaire.convertDebutMajuscule((String)libEntete);
        for (int i = 0; i < data.length; ++i) {
            Object obj = UtilitaireCR.mappingAttributContent(data[i], maj);
            content.add(obj);
        }
        return content;
    }

    public static Object mappingAttributContent(ClassMAPTable data, String libEntete) throws Exception {
        String maj = Utilitaire.convertDebutMajuscule((String)libEntete);
        Method method = data.getClass().getMethod("get" + maj, null);
        Object obj = method.invoke(data, null);
        return obj;
    }

    public static List<String> castListString(List<Object> list) {
        List<String> strings = list.stream().map(o -> (String)o).collect(Collectors.toList());
        return strings;
    }

    public static List<Double> castListDouble(List<Object> list) {
        List<Double> doubles = list.stream().map(o -> ((Number)o).doubleValue()).collect(Collectors.toList());
        return doubles;
    }

    public static String tableContent(ClassMAPTable[] mappings, String[] libEntete) throws Exception {
        String content = "";
        if (mappings.length == 0) {
            content = content + UtilitaireCR.tableFooter();
            content = content + "<p>Aucune donnees disponible</p>";
            return content;
        }
        for (int i = 0; i < mappings.length; ++i) {
            content = content + "<tr>";
            content = content + UtilitaireCR.mappingContent(mappings[i], libEntete);
            content = content + "</tr>";
        }
        content = content + UtilitaireCR.tableFooter();
        return content;
    }

    public static Date dateSemaineDerniere(Date daty) throws Exception {
        LocalDate localDate = daty.toLocalDate();
        LocalDate semaineDerniere = localDate.minusWeeks(1L);
        return Date.valueOf(semaineDerniere);
    }

    public static Date dateMoisDernier(Date daty) throws Exception {
        LocalDate localDate = daty.toLocalDate();
        LocalDate moisDernier = localDate.minusMonths(1L);
        return Date.valueOf(moisDernier);
    }

    public static String dateRequeteOracle(Date daty) {
        return "TO_DATE('" + daty + "', 'yyyy-MM-dd')";
    }

    public static String dateRequete(Date daty) {
        return "'" + daty + "'";
    }

    public static boolean isNumericType(String typeName) {
        return typeName.equals("int") || typeName.equals("float") || typeName.equals("double");
    }

    public static void createDirectory(String url) throws Exception {
        File file = new File(url);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String formatNombre(Object valeur) {
        if (valeur instanceof Number) {
            double num = ((Number)valeur).doubleValue();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.FRANCE);
            symbols.setGroupingSeparator(' ');
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
            return df.format(num);
        }
        return valeur.toString();
    }

    public static String pageRappelEntete() {
        return "<!DOCTYPE html>\n<html lang=\"fr\">\n<head>\n  <meta charset=\"UTF-8\">\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n  <title>Rappel \u00c3\u00a9ch\u00c3\u00a9ance de facture</title>\n</head>\n<body style=\"margin: 0; padding: 0; background-color: #dde8f0; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Helvetica, Arial, sans-serif;\">\n\n  <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color: #dde8f0; padding: 40px 20px;\">\n    <tr>\n      <td align=\"center\">\n\n        <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color: #ffffff; border-radius: 12px; overflow: hidden; max-width: 600px; width: 100%;\">\n          <tr>\n            <td style=\"padding: 40px 48px 36px 48px;\">";
    }

    public static String pageRappelLogoTitre(String lienLogo, String nom, String titre) {
        String lienLogoHtml = "                          <span style=\"display: inline-block; width: 18px; height: 18px; background-color: #111111; border-radius: 2px; margin-right: 4px; vertical-align: middle;\"></span>\n                          <span style=\"display: inline-block; width: 18px; height: 18px; background-color: #111111; border-radius: 50%; margin-right: 4px; vertical-align: middle;\"></span>\n                          <span style=\"display: inline-block; width: 18px; height: 18px; background-color: #111111; border-radius: 50%; vertical-align: middle;\"></span>\n";
        if (lienLogo != null && !lienLogo.equals("")) {
            lienLogoHtml = "<img src=\"" + lienLogo + "\" style=\"height: 64px; width: auto\" alt=\"logo\" border=\"0\">";
        }
        String content = "   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"margin-bottom: 36px;\">\n                <tr>\n                  <td>\n                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                      <tr>\n                        <td style=\"padding-bottom: 4px;\">\n" + lienLogoHtml + "                        </td>\n                      </tr>\n                      <tr>\n                        <td>\n                          <span style=\"font-size: 15px; font-weight: 700; color: #111111; letter-spacing: -0.3px;\">" + nom + "</span>\n                        </td>\n                      </tr>\n                    </table>\n                  </td>\n                </tr>\n              </table>\n              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"margin-bottom: 28px;\">\n                <tr>\n                  <td>\n                    <h1 style=\"margin: 0; font-size: 20px; font-weight: 700; color: #111111; line-height: 1.3;\">" + titre + "</h1>\n                  </td>\n                </tr>\n              </table>";
        return content;
    }

    public static String pageRappelCorpsMessage(String[] paragraphes, String[] contacts) {
        String paragraphesHtml = "";
        if (paragraphes != null) {
            for (int i = 0; i < paragraphes.length; ++i) {
                paragraphesHtml = paragraphesHtml + "<p style=\"margin: 0 0 18px 0; font-size: 15px; color: #222222; line-height: 1.6;\">\n" + paragraphes[i] + "\n</p>\n";
            }
        }
        String contactsHtml = "";
        if (contacts != null) {
            for (int i = 0; i < contacts.length; ++i) {
                contactsHtml = contactsHtml + "<p style=\"margin: 0; font-size: 14px; color: #aaaaaa; line-height: 1.7;\">" + contacts[i] + "</p>\n";
            }
        }
        String content = "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"margin-bottom: 28px;\">\n                <tr>\n                  <td>\n                    <p style=\"margin: 0 0 18px 0; font-size: 15px; color: #222222; line-height: 1.6;\">Bonjour,</p>\n" + paragraphesHtml + "                    <p style=\"margin: 0 0 8px 0; font-size: 15px; color: #222222; line-height: 1.6;\">Cordialement,</p>\n                  </td>\n                </tr>\n              </table>\n              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                <tr>\n                  <td>\n" + contactsHtml + "                  </td>\n                </tr>\n              </table>\n            </td>\n          </tr>";
        return content;
    }

    public static String pageRappelFooter() {
        return "<tr>\n            <td style=\"background-color: #f7f9fb; padding: 16px 48px; border-top: 1px solid #eeeeee;\">\n              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                <tr>\n                  <td style=\"font-size: 12px; color: #888888; vertical-align: middle;\">\n                    Powered by\n                    <img src=\"https://i.ibb.co/xq63bfvD/2-Logo-Async-Footer.png\" style=\"height: 25px; width: auto; vertical-align: middle;\" alt=\"Logo Async Footer\" border=\"0\">\n                  </td>\n                  <td align=\"right\" style=\"font-size: 12px; color: #888888; vertical-align: middle;\">\n                    Created by\n                    <img src=\"https://i.ibb.co/ZRwF8Xqs/logo-bici.png\" style=\"height: 25px; width: auto; vertical-align: middle;\" alt=\"Logo BICI\" border=\"0\">\n                  </td>\n                </tr>\n              </table>\n            </td>\n          </tr>\n\n        </table>\n\n      </td>\n    </tr>\n  </table>\n\n</body>\n</html>";
    }

    public static String bindDynamicData(String message, Map<String, Object> data) {
        if (data == null) {
            return message;
        }
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String placeholder = "[" + entry.getKey() + "]";
            message = message.replace(placeholder, entry.getValue().toString());
        }
        return message;
    }

    public static String pageGroupeEntete() {
        String entete = "<!DOCTYPE html>\n<html>\n<head>\n    <meta charset=\"UTF-8\">\n    <style>\n        body {\n            font-family: Arial, sans-serif;\n            margin: 20px;\n            color: #333;\n        }\n\n        h2 {\n            text-align: center;\n            text-transform: uppercase;\n            padding-bottom: 10px;\n            letter-spacing: 2px;\n        }\n\n        h3 {\n            color: #004080;\n            font-size: 13px;\n            text-transform: uppercase;\n            letter-spacing: 1px;\n            border-left: 4px solid #2c7fb8;\n            padding-left: 8px;\n            margin-top: 24px;\n            margin-bottom: 8px;\n        }\n\n        p, ul {\n            font-size: 11px;\n            color: #555;\n            margin-bottom: 10px;\n        }\n\n        table {\n            width: 100%;\n            border-collapse: collapse;\n            font-size: 11px;\n        }\n\n        th {\n            border: 1px solid #ccc;\n            padding: 8px 4px;\n            font-weight: normal;\n            vertical-align: top;\n            min-width: 80px;\n        }\n\n        td {\n            border: 1px solid #ccc;\n            padding: 6px;\n            text-align: center;\n            line-height: 1.4;\n            color: #2c7fb8;\n        }\n\n        .col-libelle {\n            text-align: left;\n            padding-left: 10px;\n            color: #333 !important;\n            width: 150px;\n        }\n\n        .row-total {\n            font-weight: bold;\n            background-color: #ffffff;\n        }\n\n        .row-total td {\n            color: #2c7fb8;\n        }\n        table.total-table {\n            width: 50%;\n            margin-top: 10px;\n            border: 1px solid #f2f8ff;\n        }\n    </style>\n</head>\n<body>";
        return entete;
    }

    public static Double[] emptyDoubleTable(int length) {
        Double[] valeurs = new Double[length];
        for (int i = 0; i < length; ++i) {
            valeurs[i] = 0.0;
        }
        return valeurs;
    }

    public static String totalContent(Double[] tables) {
        String colonneTotalVal = "";
        for (int i = 0; i < tables.length; ++i) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.FRANCE);
            symbols.setGroupingSeparator(' ');
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
            colonneTotalVal = colonneTotalVal + df.format(tables[i]);
            colonneTotalVal = colonneTotalVal + "<br>";
        }
        return colonneTotalVal;
    }
}

