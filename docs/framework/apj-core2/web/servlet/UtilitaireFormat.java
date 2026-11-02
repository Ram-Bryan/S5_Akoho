/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  utilitaire.ChiffreLettre
 *  utilitaire.Utilitaire
 */
package web.servlet;

import java.io.File;
import utilitaire.ChiffreLettre;
import utilitaire.Utilitaire;

public class UtilitaireFormat {
    public static String generateEntete(String titre, String logo, String width, String height) {
        String htmlStringEntete = "<html><head><title></title></head><body>";
        htmlStringEntete = htmlStringEntete + "<table width='100%' border='0' cellpadding='0' cellspacing='0' style='margin-bottom:15px;'>";
        htmlStringEntete = htmlStringEntete + "<tr>";
        htmlStringEntete = htmlStringEntete + "<td width='25%' align='left' valign='middle'>";
        if (logo != null && !logo.trim().isEmpty() && new File(logo).exists()) {
            htmlStringEntete = htmlStringEntete + "<img width='" + width + "' height='" + height + "' src='" + logo + "'/>";
        }
        htmlStringEntete = htmlStringEntete + "</td>";
        htmlStringEntete = htmlStringEntete + "<td width='75%' align='center' valign='middle'>";
        if (titre != null && !titre.trim().isEmpty() && !"null".equalsIgnoreCase(titre.trim())) {
            htmlStringEntete = htmlStringEntete + "<h3><b>" + titre.trim() + "</b></h3>";
        }
        htmlStringEntete = htmlStringEntete + "</td>";
        htmlStringEntete = htmlStringEntete + "</tr>";
        htmlStringEntete = htmlStringEntete + "</table>";
        htmlStringEntete = htmlStringEntete + "<br/>";
        return htmlStringEntete;
    }

    public static String generateEntete(String titre, String logo) {
        return UtilitaireFormat.generateEntete(titre, logo, "124px", "42px");
    }

    public static String generateBasPage(int nombre, double montant) {
        String htmlStringBasPage = "<div align='left'><h5>Arr&ecirc;t&eacute; le nombre &agrave; " + Utilitaire.formaterSansVirgule((double)nombre) + "</h5>";
        if (montant > 0.0) {
            htmlStringBasPage = htmlStringBasPage + "<h5>Arr&ecirc;t&eacute; le pr&eacute;sent &eacute;tat &agrave; la somme de " + ChiffreLettre.convertRealToString((double)montant) + " Ariary</h5>";
        }
        htmlStringBasPage = htmlStringBasPage + "</div>";
        return htmlStringBasPage;
    }
}

