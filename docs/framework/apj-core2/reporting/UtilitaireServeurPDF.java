/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.CGenUtil
 *  bean.ClassMAPTable
 *  historique.MapUtilisateur
 *  user.UserEJB
 *  utilitaire.UtilDB
 *  utilitaire.Utilitaire
 */
package reporting;

import bean.CGenUtil;
import bean.ClassMAPTable;
import historique.MapUtilisateur;
import java.io.FileNotFoundException;
import java.sql.Connection;
import reporting.ReportingCdn;
import reporting.ReportingUtils;
import reporting.ServeurPDF;
import user.UserEJB;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class UtilitaireServeurPDF {
    public static boolean testIfExist(String nom_objet, String idobjet, int etat) throws Exception {
        ServeurPDF spdf = new ServeurPDF();
        ServeurPDF[] spdfs = (ServeurPDF[])CGenUtil.rechercher((ClassMAPTable)spdf, null, null, null, (String)(" AND NOM_OBJET ='" + nom_objet + "' AND IDOBJET = '" + idobjet + "' and etat=" + etat + ""));
        return spdfs.length > 0;
    }

    public static boolean testIfExist(String nom_objet, String idobjet) throws Exception {
        ServeurPDF spdf = new ServeurPDF();
        ServeurPDF[] spdfs = (ServeurPDF[])CGenUtil.rechercher((ClassMAPTable)spdf, null, null, null, (String)(" AND NOM_OBJET ='" + nom_objet + "' AND IDOBJET = '" + idobjet + "'"));
        return spdfs.length > 0;
    }

    public static void updateWhenNotAnneeEnCours(String nom_objet, int etat) throws Exception {
        try (Connection c = new UtilDB().GetConn();){
            ServeurPDF spdf = new ServeurPDF();
            ServeurPDF[] spdfs = (ServeurPDF[])CGenUtil.rechercher((ClassMAPTable)spdf, null, null, null, (String)(" AND NOM_OBJET ='" + nom_objet + "'"));
            if (spdfs.length > 0) {
                return;
            }
            spdfs[0].setEtat(etat);
            spdfs[0].setNom_objet("");
            spdfs[0].setRemarque("0");
            spdfs[0].upDateToTable();
            c.commit();
        }
    }

    public static boolean checkEtatServeur(String fileName, ReportingCdn.Fonctionnalite fonctionnalite, ReportingUtils.ReportType typeFile, String id, int etat) throws FileNotFoundException, Exception {
        return ReportingCdn.checkExistingFile(fonctionnalite, fileName + "-" + id + ReportingCdn.getExtension(typeFile)) && etat == UtilitaireServeurPDF.getEtatServeur(fileName, id);
    }

    public static int getEtatServeur(String nom_objet, String idobjet) throws Exception {
        ServeurPDF spdf = new ServeurPDF();
        ServeurPDF[] spdfs = (ServeurPDF[])CGenUtil.rechercher((ClassMAPTable)spdf, null, null, null, (String)(" AND NOM_OBJET ='" + nom_objet + "' AND IDOBJET = '" + idobjet + "'"));
        if (spdfs.length > 0) {
            spdf = spdfs[0];
        }
        return spdf.getEtat();
    }

    public static void deleteFromServeur(String idobjet) throws Exception {
        ServeurPDF spdf = new ServeurPDF();
        spdf.setIdobjet(idobjet);
        spdf.deleteToTable();
    }

    private static void updateEtatServeur(String nom_objet, String idobjet, int etat) throws Exception {
        ServeurPDF spdf = new ServeurPDF();
        ServeurPDF[] spdfs = (ServeurPDF[])CGenUtil.rechercher((ClassMAPTable)spdf, null, null, null, (String)(" AND NOM_OBJET ='" + nom_objet + "' AND IDOBJET = '" + idobjet + "'"));
        if (spdfs.length > 0) {
            spdf = spdfs[0];
        }
        spdf.setEtat(etat);
        spdf.upDateToTable();
    }

    private static void updateEtatServeurWithRemarque(String nom_objet, String idobjet, int etat, String remarque) throws Exception {
        ServeurPDF spdf = new ServeurPDF();
        ServeurPDF[] spdfs = (ServeurPDF[])CGenUtil.rechercher((ClassMAPTable)spdf, null, null, null, (String)(" AND NOM_OBJET ='" + nom_objet + "' AND IDOBJET = '" + idobjet + "'"));
        if (spdfs.length > 0) {
            spdf = spdfs[0];
        }
        spdf.setEtat(etat);
        spdf.setRemarque(remarque);
        spdf.upDateToTable();
    }

    public static int getNumerosequentiel(String nom_objet, String idObjet) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            int toReturn = UtilitaireServeurPDF.getNumerosequentiel(c, nom_objet, idObjet);
            c.commit();
            int n = toReturn;
            return n;
        }
    }

    public static int getNumerosequentiel(Connection c, String nom_objet, String idObjet) throws Exception {
        ServeurPDF spdf = new ServeurPDF();
        ServeurPDF[] spdfs = (ServeurPDF[])CGenUtil.rechercher((ClassMAPTable)spdf, null, null, null, (String)(" AND NOM_OBJET ='" + nom_objet + "' ORDER BY id DESC"));
        if (spdfs.length > 0 && spdfs[0].getIdobjet().equals(idObjet)) {
            spdfs[0].updateToTable(c);
            return Integer.valueOf(spdfs[0].getRemarque());
        }
        spdf.construirePK(c);
        spdf.setNom_objet(nom_objet);
        spdf.setIdobjet(idObjet);
        if (spdfs.length > 0) {
            spdf.setRemarque(String.valueOf(Integer.valueOf(spdfs[0].getRemarque()) + 1));
            spdf.insertToTable(c);
            return Integer.valueOf(spdfs[0].getRemarque()) + 1;
        }
        spdf.setRemarque(String.valueOf(1));
        spdf.insertToTable(c);
        return 1;
    }

    public static void updateServeurPDF(String nom_objet, String idObjet) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            ServeurPDF spdf = new ServeurPDF();
            ServeurPDF[] spdfs = (ServeurPDF[])CGenUtil.rechercher((ClassMAPTable)spdf, null, null, (Connection)c, (String)(" AND nom_objet = '" + nom_objet + "' and idobjet='" + idObjet + "'"));
            if (spdfs.length == 0) {
                throw new Exception("Exception lors de la generation du numero sequentiel");
            }
            spdf = spdfs[0];
            spdf.setRemarque(String.valueOf(Integer.valueOf(spdf.getRemarque()) + 1));
            spdf.updateToTable(c);
            c.commit();
        }
    }

    public static void insertHistoPDF(String nom_objet, String idObjet, int etat) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            if (UtilitaireServeurPDF.testIfExist(nom_objet, idObjet, etat)) {
                UtilitaireServeurPDF.updateEtatServeur(nom_objet, idObjet, etat);
                c.commit();
                return;
            }
            ServeurPDF spdf = new ServeurPDF();
            spdf.construirePK(c);
            spdf.setNom_objet(nom_objet);
            spdf.setIdobjet(idObjet);
            spdf.setEtat(etat);
            spdf.insertToTable(c);
            c.commit();
        }
    }

    public static void insertHistoPDFWithRemarque(String nom_objet, String idObjet, int etat, String remarque) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            if (UtilitaireServeurPDF.testIfExist(nom_objet, idObjet, etat)) {
                UtilitaireServeurPDF.updateEtatServeurWithRemarque(nom_objet, idObjet, etat, remarque);
                c.commit();
                return;
            }
            ServeurPDF spdf = new ServeurPDF();
            spdf.construirePK(c);
            spdf.setNom_objet(nom_objet);
            spdf.setIdobjet(idObjet);
            spdf.setEtat(etat);
            spdf.setRemarque(remarque);
            spdf.insertToTable(c);
            c.commit();
        }
    }

    public static void updateNumeroNotificationAt(String nom_objet, String idObjet) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            ServeurPDF spdf = new ServeurPDF();
            ServeurPDF[] spdfs = (ServeurPDF[])CGenUtil.rechercher((ClassMAPTable)spdf, null, null, null, (String)(" AND NOM_OBJET ='" + nom_objet + "'"));
            if (spdfs.length > 0) {
                spdf = spdfs[0];
            }
            spdf.setIdobjet(idObjet);
            spdf.setRemarque(String.valueOf(Integer.valueOf(spdf.getRemarque()) + 1));
            spdf.upDateToTable();
            c.commit();
        }
    }

    public static boolean testNullEtVide(String value) {
        return value != null && !value.equalsIgnoreCase("") && !value.equalsIgnoreCase("null");
    }

    public static String makeCritereStringWithTestNull(String attribut, String value, String critere) {
        if (!UtilitaireServeurPDF.testNullEtVide(value)) {
            return "";
        }
        return " AND " + attribut.toUpperCase() + critere + "'" + value + "'";
    }

    public static String champNullZero(String valeur) {
        if (!UtilitaireServeurPDF.testNullEtVide(valeur)) {
            return "0";
        }
        return valeur;
    }

    public static String remplirParZero(int nombre, int isany) {
        String nombreString = String.valueOf(nombre);
        for (int i = nombreString.length(); i < isany; ++i) {
            nombreString = "0" + nombreString;
        }
        return nombreString;
    }

    public static String getNumeroIdentificationPDF(UserEJB u, String nomObjet, String identification, Connection c) throws Exception {
        boolean test = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                test = true;
            }
            String retour = "";
            MapUtilisateur pers = u.getUser();
            String initiale = pers.getTuppleID();
            if (initiale == null || initiale.isEmpty()) {
                initiale = UtilitaireServeurPDF.creerInitiale(pers.getNomuser(), "");
            }
            String string = retour = retour + UtilitaireServeurPDF.getNumeroIdentificationPDF(nomObjet, c) + identification + initiale + "/" + Utilitaire.getAnneeEnCours();
            return string;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (test && c != null) {
                c.close();
            }
        }
    }

    public static String getNumeroIdentificationPDF(String nomObjet, Connection c) throws Exception {
        ServeurPDF[] spdfs = (ServeurPDF[])CGenUtil.rechercher((ClassMAPTable)new ServeurPDF(), null, null, (Connection)c, (String)(" AND NOM_OBJET ='" + nomObjet + "'"));
        String numero = "";
        if (spdfs.length > 0) {
            boolean test = UtilitaireServeurPDF.testWithUpdateNumeroChrono(nomObjet, spdfs[0].getEtat());
            UtilitaireServeurPDF.updateNumeroNotificationAt(nomObjet, "");
            numero = test ? "1" : String.valueOf(Integer.valueOf(spdfs[0].getRemarque()) + 1);
        } else {
            UtilitaireServeurPDF.insertHistoPDFWithRemarque(nomObjet, nomObjet, Utilitaire.getAneeEnCours() - 2000, "1");
            numero = "1";
        }
        return numero;
    }

    public static String creerInitiale(String nom, String prenom) throws Exception {
        int i;
        String ret = "";
        String[] n = Utilitaire.champNull((String)nom).split(" ");
        String[] p = Utilitaire.champNull((String)prenom).split(" ");
        if (n.length > 0) {
            for (i = 0; i < n.length; ++i) {
                if (n[i] == null || n[i].isEmpty()) continue;
                ret = ret + n[i].charAt(0);
            }
        }
        if (p.length > 0) {
            for (i = 0; i < p.length; ++i) {
                if (p[i] == null || p[i].isEmpty()) continue;
                ret = ret + p[i].charAt(0);
            }
        }
        return ret.toUpperCase();
    }

    public static boolean testWithUpdateNumeroChrono(String nomObjet, int annee) throws Exception {
        boolean test = false;
        if (annee + 2000 == Utilitaire.getAneeEnCours()) {
            test = false;
        } else {
            UtilitaireServeurPDF.updateWhenNotAnneeEnCours(nomObjet, annee);
            test = true;
        }
        return test;
    }
}

