/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.TypeObjet
 */
package mg.cnaps.commun;

import bean.TypeObjet;

public class Constante {
    private static final String dr_siege = "DR42";
    private static final String dr_abrev = "42";
    private static final String id_classe = "CLS0016";
    public static final String idtype_carte_prepaye = "1";
    public static final String idtype_carte_postpaye = "2";
    public static final String idtype_carte_fille = "3";
    public static final String idtype_carte_mere = "4";
    public static final String ovModePaiementCheque = "MP12";
    public static final String ovModePaiementEspece = "MP1";
    public static String idJovenna = "1";
    public static final String idLiquidite = "1";
    public static final String idBesoinFormation = "FTD2";
    public static final String idDemandeIndividuelle = "FTD2";
    public static final String listeIdArticleVehicule = "'TP000056','TP000024','TP000034','TP000044'";
    public static final String idEntretienVehiculeInterne = "0";
    public static final String libelleEntretienVehiculeInterne = "Interne";
    public static final String idEntretienVehiculeExterne = "1";
    public static final String libelleEntretienVehiculeExterne = "Externe";
    public static final String CODE_SERVICE_FORMATION = "6030";
    public static final String CODE_SERVICE_FORMATION2 = "6050";
    public static final String CODE_SERVICE_PERSONNEL = "6000";
    public static final String CODE_SERVICE_PERSONNEL2 = "6010";
    public static final String ID_TYPE_MOUVEMENT_VEHICULE_CNAPS = "TYM001";
    public static final String ID_ETAT_VEHICULE_DISPONIBLE = "0";
    public static final String ID_ETAT_VEHICULE_EN_MAINTENANCE = "1";
    public static final String ID_ETAT_VEHICULE_EN_DEPLACEMENT = "2";
    public static final String ID_ETAT_VEHICULE_VENDU = "3";
    public static final String ID_ETAT_VEHICULE_NON_DISPONIBLE = "4";
    public static final String ID_IMMO_CATEGORIE_MATERIEL_ROULANT = "CAT000010";
    public static final int diffJourRelance = 9;
    public static final double fraisLivraison = 3000.0;

    public static String getId_classe() {
        return id_classe;
    }

    public static String getDr_abrev() {
        return dr_abrev;
    }

    public static String getDr_siege() {
        return dr_siege;
    }

    public static String getIdJovenna() {
        return idJovenna;
    }

    public static void setIdJovenna(String aIdJovenna) {
        idJovenna = aIdJovenna;
    }

    public static TypeObjet[] getMoisTous() {
        TypeObjet[] mois = new TypeObjet[]{new TypeObjet("1", "Janvier", ""), new TypeObjet("2", "Fevrier", ""), new TypeObjet("3", "Mars", ""), new TypeObjet("4", "Avril", ""), new TypeObjet("5", "Mai", ""), new TypeObjet("6", "Juin", ""), new TypeObjet("7", "Juillet", ""), new TypeObjet("8", "Aout", ""), new TypeObjet("9", "Septembre", ""), new TypeObjet("10", "Octobre", ""), new TypeObjet("11", "Novembre", ""), new TypeObjet("12", "Decembre", "")};
        return mois;
    }

    public static double getFraisLivraison() {
        return 3000.0;
    }
}

