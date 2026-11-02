/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

public class Constante {
    public static double RATIO = 25.0;
    private static String idTypeLCRecette = "TLC00001";
    private static String idTypeLCDepense = "TLC00002";
    public static String idRoleDirecteur = "directeur";
    public static String idRoleUtilisateur = "utilisateur";
    public static String idRoleAdmin = "admin";
    public static String idRoleAdminFacture = "adminFacture";
    public static String idRoleAdminCompta = "compta";
    public static String idRoleDg = "dg";
    public static String idRoleSaisie = "saisie";
    public static String tableOpFfLc = "OpFfLc";
    private static String[] mois = new String[]{"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"};
    private static String[] moisRang = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private static String objetDepense = "depense";
    private static String objetRecette = "recette";
    private static String objetFacture = "facture";
    private static String objetFactureF = "factureF";
    private static String tableRecetteLC = "recettesLC";
    private static String tableFactureLC = "factureLigneCredit";
    private static String tableFactureFLC = "FactureFournisseurLC";
    private static String tableDepenseLC = "sortieFF";
    private static String idFournAuto = "clt1";
    private static String objFactureProf = "FactureProfFournisseur";
    private static String objetBc = "BC";
    private static String objetCDetailFactProf = "detFactProf";
    private static String objetDetailBC = "detBC";
    private static String objetDetailFactFourn = "detFactF";
    private static String dedFactProfFourn = "DedFACTUREProfFOURNISSEUR";
    private static String DetailLcCree = "0";
    private static String DetailLcValide = "1";
    private static String objetDedBc = "DedBc";
    private static String objetLcDetMvt = "LcDetailMvtCaisse";
    private static String objetLcDetDed = "LcDetailDed";
    private static String objetLcDetOp = "LcDetailop";
    private static String objetOpFf = "OPFACTUREFOURNISSEUR";
    private static String objetDed = "ded";
    private static String objetOp = "ORDONNERPAYEMENT";
    private static String objetLcDetail = "LcDetails";
    private static String tableDetailBC = "detailBC";
    private static String objetMvtCaisse = "mvtCaisse";
    private static String objectDetailFPF = "detailFPF";
    public static String objetFactureFournisseur = "FactureFournisseur";
    private static String opFactureFournisseur = "OPFACTUREFOURNISSEUR";
    private static String objetLigneCredit = "LigneCredit";
    private static String objetLigneCreditRecette = "LigneCreditRecette";
    private static String visaOp = "VISAORDREPAYEMENT";
    private static String visaOr = "VISAOR";
    public static String typeOpFacture = "facture";
    public static String typeOpNormale = "normale";
    public static String tableVisaOp = "VISAORDREPAYEMENT";
    public static String objetFactureClient = "FACTURECLIENT";
    public static String tableOrFcLc = "ORFCLC";
    public static String tableFactureCLC = "FactureClientLC";
    public static String factureClient = "FACTURECLIENT";
    public static final String constanteTiersStock = "TIERS1";
    public static final String constantePeriode = "T";
    public static final String idDirectionTamatave = "DIR3";
    public static final String idDirectionMarovintsy = "DIR00001";
    public static final String idDirectionAntsirakambo = "DIR00002";
    public static final String roleadminarticle = "adminarticle";
    public static final String typeInventaire = "tpi1";
    public static final String constanteEntreeStock = "MVT000002";
    public static final String constanteSortieStock = "MVT000003";
    public static final String constanteReintegrStock = "MVT000001";
    public static final String conge = "CONGE";
    public static final String mouvement = "MOUVEMENT";
    public static final String depart = "DEPART";
    public static final String deplacement = "DEPLACEMENT";
    public static final String deces = "DECES";
    public static final String etatCreer = "1";
    public static final String DEFAULT_TYPE_MVT = "tpmvt2";
    public static final String DEFAULT_TYPE_MVT_ENTREE = "tpmvt1";
    public static final Double IndiceNonAgri = 0.8681;
    public static final Double HeureNonAgri = 173.33;
    public static int COMPTA_COMPTE_GEN_MAX_CHAR = 13;
    public static int COMPTA_COMPTE_ANAL_MAX_CHAR = 17;
    public static String COMPTA_TYPE_COMPTE_GENERAL = "1";
    public static String COMPTA_TYPE_COMPTE_AUTRE = "2";
    public static String COMPTA_TYPE_COMPTE_ANALYTIQUE = "3";
    public static String iddevise = "Ar";
    public static String modepaiement = "pay1";
    public static final String modePaiementEspece = "pay1";
    public static String adresseUniv = "Lot II B 220 Andoharanofotsy";
    public static String numeroUniv = "+261 33 03 300 32";
    public static String lienUniv = "www.ituniversity-mg.com<";
    public static String typeMvt = "1";
    public static final String ID_ECOLE_AUTRE = "ECO000101";
    public static final String ID_PAYS_DEFAULT = "PAYS1";
    public static final String ID_OPTIONNE_DEFAULT = "all";
    public static final String ID_OPTIONS_DEV = "OPT3";
    public static final String ID_OPTIONS_DEV_LIB = "dev";
    public static final String ID_PROM_16 = "PROMO000027";
    public static final String DEFAULT_AGENCE = "ag006";
    public static String regul = "TP1";
    public static String typeDepenseRegul = "TD14";
    public static int userValideRegul = 18;
    public static final String idEntite = "BE052";
    public static final String DEFAULTTYPEECOLE = "TPE1";
    public static String OPTION_GENERAL_ITC = "OPTGEN03";
    public static String OPTIONNE_ITC = "itc";
    private static String idEntiteITU = "BE052";
    private static String idEntiteITC = "BE000423";
    private static final String magasinDefaut = "STM000027";
    public static final String magasinDistribution = "STM00001";
    public static final String magasinDistributionPort = "STM000037";
    private static final int seuilValue = 4;
    public static final String journalVente = "8";
    public static final String idEntiteAutres = "ent4";
    private static final String[] branches = new String[]{"MEDBRA0025", "MEDBRA005", "MEDBRA002", "MEDBRA006"};
    public static String familleEnfant = "enfant";
    public static int ageMineur = 21;
    public static final String actionOrdonnance = "ACTEMP002";
    public static final String actionPrescription = "ACTEMP001";
    public static final String avoirNonPrise = "TYPEAV0002";
    public static String idRoleGestionnaire = "gestionnaire";
    public static final String idAvoir = "AV01";
    public static final String typeAvoir = "TYPEAV0001";
    public static final int prioriteUrgent = 3;
    public static String idoperateurInfirmier = "PRESD0002";
    public static String idoperateurDoceteur = "PRESD0001";
    public static final String financeTypeAnnulation = "3";
    public static final String journalCaisseRecette = "5";
    public static final String journalCaisseDepense = "4";
    public static final String idMvtSortie = "tpmvt2";
    public static final String idMvtEntree = "tpmvt1";
    public static final String compteReversement = "7777777";
    public static final String caisseReverser = "CAIS7777777";
    public static String idRoleInfirmiere = "infirmiere";
    public static final int prioriteNormal = 1;
    public static final String typeBesoinDefaut = "tpb2";
    public static String typeIntervalJour = "Journalier";
    public static String typeIntervalHebdo = "Hebdomadaire";
    public static String typeIntervalbimensuel = "Bi-mensuel";
    public static String typeIntervalMensuel = "Mensuel";

    public static String getCaisseReverser() {
        return caisseReverser;
    }

    public static String[] getBranches() {
        return branches;
    }

    public static int getSeuilValue() {
        return 4;
    }

    public static int getUserValideRegul() {
        return userValideRegul;
    }

    public static String getTypeDepenseRegul() {
        return typeDepenseRegul;
    }

    public static Double getHeureNonAgri() {
        return HeureNonAgri;
    }

    public static Double getIndiceNonAgri() {
        return IndiceNonAgri;
    }

    public static String getIdTypeLCDepense() {
        return idTypeLCDepense;
    }

    public static String getDetailLcCree() {
        return DetailLcCree;
    }

    public static String getDetailLcValide() {
        return DetailLcValide;
    }

    public static String getIdTypeLCRecette() {
        return idTypeLCRecette;
    }

    public static String getIdRoleDirecteur() {
        return idRoleDirecteur;
    }

    public static String[] getMois() {
        return mois;
    }

    public static String[] getMoisRang() {
        return moisRang;
    }

    public static String getObjetDepense() {
        return objetDepense;
    }

    public static String getObjetRecette() {
        return objetRecette;
    }

    public static String getObjetFacture() {
        return objetFacture;
    }

    public static String getObjetFactureF() {
        return objetFactureF;
    }

    public static String getTableRecetteLC() {
        return tableRecetteLC;
    }

    public static String getTableFactureLC() {
        return tableFactureLC;
    }

    public static String getTableFactureFLC() {
        return tableFactureFLC;
    }

    public static String getTableDepenseLC() {
        return tableDepenseLC;
    }

    public static String getIdFournAuto() {
        return idFournAuto;
    }

    public static String getObjFactureProf() {
        return objFactureProf;
    }

    public static String getObjetBc() {
        return objetBc;
    }

    public static String getObjetCDetailFactProf() {
        return objetCDetailFactProf;
    }

    public static String getObjetDetailBC() {
        return objetDetailBC;
    }

    public static String getObjetDetailFactFourn() {
        return objetDetailFactFourn;
    }

    public static String getDedFactProfFourn() {
        return dedFactProfFourn;
    }

    public static void setObjetDedBc(String objetDedBce) {
        objetDedBc = objetDedBce;
    }

    public static String getObjetDedBc() {
        return objetDedBc;
    }

    public static void setObjetLcDetMvt(String objetLcDetMvte) {
        objetLcDetMvt = objetLcDetMvte;
    }

    public static String getObjetLcDetMvt() {
        return objetLcDetMvt;
    }

    public static String getObjetLcDetDed() {
        return objetLcDetDed;
    }

    public static String getObjetLcDetOp() {
        return objetLcDetOp;
    }

    public static String getObjetOpFf() {
        return objetOpFf;
    }

    public static String getObjetDed() {
        return objetDed;
    }

    public static String getObjetOp() {
        return objetOp;
    }

    public static String getObjetLcDetail() {
        return objetLcDetail;
    }

    public static String getTableDetailBC() {
        return tableDetailBC;
    }

    public static String getObjetMvtCaisse() {
        return objetMvtCaisse;
    }

    public static String getObjectDetailFPF() {
        return objectDetailFPF;
    }

    public static String getObjetFactureFournisseur() {
        return objetFactureFournisseur;
    }

    public static String getOpFactureFournisseur() {
        return opFactureFournisseur;
    }

    public static String getObjetLigneCredit() {
        return objetLigneCredit;
    }

    public static String getObjetLigneCreditRecette() {
        return objetLigneCreditRecette;
    }

    public static String getVisaOp() {
        return visaOp;
    }

    public static String getVisaOr() {
        return visaOr;
    }

    public static String getIdEntiteITU() {
        return idEntiteITU;
    }

    public static void setIdEntiteITU(String idEntiteITU) {
        Constante.idEntiteITU = idEntiteITU;
    }

    public static String getIdEntiteITC() {
        return idEntiteITC;
    }

    public static void setIdEntiteITC(String idEntiteITC) {
        Constante.idEntiteITC = idEntiteITC;
    }

    public static String getMagasinDefaut() {
        return magasinDefaut;
    }

    public static String getIdEntiteAutres() {
        return idEntiteAutres;
    }
}

