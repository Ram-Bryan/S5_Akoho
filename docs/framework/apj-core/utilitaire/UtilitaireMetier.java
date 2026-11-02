/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import bean.CGenUtil;
import bean.UnionIntraTable;
import java.sql.Connection;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class UtilitaireMetier {
    public static String mapperMereToFille(String nomtableMappage, String nomFonctMap, String suffixeMap, String idMere, String idFille, String rem, String montant, String refU, Connection c) throws Exception {
        return UtilitaireMetier.mapperMereToFille(nomtableMappage, nomFonctMap, suffixeMap, idMere, idFille, rem, montant, refU, "0", c);
    }

    public static String mapperMereToFille(String nomtableMappage, String nomFonctMap, String suffixeMap, String idMere, String idFille, String rem, String montant, String refU, String etat, Connection c) throws Exception {
        try {
            int[] tab = new int[]{2, 3};
            String[] val = new String[]{idMere, idFille};
            UnionIntraTable uit = new UnionIntraTable();
            uit.setNomTable(nomtableMappage);
            uit.setId1(idMere);
            uit.setId2(idFille);
            UnionIntraTable[] uu = (UnionIntraTable[])CGenUtil.rechercher(uit, null, null, c, "");
            if (uu.length > 0) {
                throw new Exception("Objet d\u00ef\u00bf\u00bdj\u00ef\u00bf\u00bd attribu\u00ef\u00bf\u00bd");
            }
            UnionIntraTable u = null;
            u = new UnionIntraTable(nomtableMappage, nomFonctMap, suffixeMap, idMere, idFille, rem, Utilitaire.stringToDouble(montant), Utilitaire.stringToInt(etat));
            u.insertToTableWithHisto(refU, c);
            return u.getId();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            c.rollback();
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String mapperMereToFille(String nomtableMappage, String nomFonctMap, String suffixeMap, String idMere, String idFille, String rem, String montant, String refU, String etat) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            String g = UtilitaireMetier.mapperMereToFille(nomtableMappage, nomFonctMap, suffixeMap, idMere, idFille, rem, montant, refU, etat, c);
            c.commit();
            String string = g;
            return string;
        }
    }

    public static String mapperMereToFille(String nomtableMappage, String nomFonctMap, String suffixeMap, String idMere, String idFille, String rem, String montant, String refU) throws Exception {
        return UtilitaireMetier.mapperMereToFille(nomtableMappage, nomFonctMap, suffixeMap, idMere, idFille, rem, montant, refU, "0");
    }

    public static void deleteMereToFille(String nomtable, String idMere, String idFille, String refU, Connection c) throws Exception {
        try {
            UnionIntraTable uti = new UnionIntraTable();
            uti.setNomTable(nomtable);
            uti.setId1(idMere);
            uti.setId2(idFille);
            UnionIntraTable[] ui = (UnionIntraTable[])CGenUtil.rechercher(uti, null, null, c, "");
            if (ui != null && ui.length > 0) {
                ui[0].setNomTable(nomtable);
                ui[0].deleteToTableWithHisto(refU, c);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public static String mapperMereToFilleMetier(String nomtableMappage, String nomFonctMap, String suffixeMap, String idMere, String idFille, String rem, String montant, String refU) throws Exception {
        return UtilitaireMetier.mapperMereToFilleMetier(nomtableMappage, nomFonctMap, suffixeMap, idMere, idFille, rem, montant, refU, "0");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String mapperMereToFilleMetier(String nomtableMappage, String nomFonctMap, String suffixeMap, String idMere, String idFille, String rem, String montant, String refU, String etat) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            String g = UtilitaireMetier.mapperMereToFilleMetier(nomtableMappage, nomFonctMap, suffixeMap, idMere, idFille, rem, montant, refU, etat, c);
            c.commit();
            String string = g;
            return string;
        }
    }

    public static String mapperMereToFilleMetier(String nomtableMappage, String nomFonctMap, String suffixeMap, String idMere, String idFille, String rem, String montant, String refU, Connection c) throws Exception {
        return UtilitaireMetier.mapperMereToFilleMetier(nomtableMappage, nomFonctMap, suffixeMap, idMere, idFille, rem, montant, refU, "0", c);
    }

    public static String mapperMereToFilleMetier(String nomtableMappage, String nomFonctMap, String suffixeMap, String idMere, String idFille, String rem, String montant, String refU, String etat, Connection c) throws Exception {
        try {
            int[] tab = new int[]{2, 3};
            String[] val = new String[]{idMere, idFille};
            UnionIntraTable uit = new UnionIntraTable();
            uit.setNomTable(nomtableMappage);
            uit.setId1(idMere);
            uit.setId2(idFille);
            UnionIntraTable[] uu = (UnionIntraTable[])CGenUtil.rechercher(uit, null, null, c, "");
            UnionIntraTable u = null;
            u = new UnionIntraTable(nomtableMappage, nomFonctMap, suffixeMap, idMere, idFille, rem, Utilitaire.stringToDouble(montant), Utilitaire.stringToInt(etat));
            if (uu.length > 0) {
                throw new Exception("Objet d\u00ef\u00bf\u00bdj\u00ef\u00bf\u00bd attribu\u00ef\u00bf\u00bd");
            }
            u.insertToTableWithHisto(refU, c);
            return u.getId();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            c.rollback();
            throw ex;
        }
    }
}

