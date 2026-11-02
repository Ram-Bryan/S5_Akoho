/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import utilitaire.Utilitaire;

public class ConstanteEtat {
    public static final String journalConstatation = "46";
    public static final int etatNotificationLue = 2;
    public static final int etatNotificationNonLue = 1;
    public static final int etatTVAPaye = 8;
    private static final int etatPrivilegerRetourRelance = 2;
    private static final int etatRadie = 2;
    private static final int etatDossierAnnuler = -1;
    private static final int etatAnnuler = 0;
    private static final int etatEnAttente = 7;
    private static final int etatCreer = 1;
    private static final int etatMedicamentNonLivre = 1;
    private static final int etatMedicamentLivre = 2;
    private static final int etatDossierRecue = 2;
    public static final int constanteEtatFinaliser = 4;
    private static final int etatSituationdroitSuspendue = 8;
    private static final int etatSituationdroitBloquer = 9;
    private static final int etatCloture = 9;
    private static final int etatOvCloture = 9;
    private static final int etatTefCloture = 9;
    private static final int etatTefRejeter = 10;
    private static final int etatRejeter = 10;
    private static final int etatObjetRejeter = 10;
    private static final int etatRejetSupOp = 10;
    private static final int etatObjetValider = 11;
    private static final int etatImmoCondamne = 20;
    private static final int etatImmoCede = 30;
    private static final int etatValider = 11;
    private static final int etatValiderSupOp = 11;
    private static final int etatValiderSupOv = 11;
    private static final int etatValiderSupTef = 11;
    private static final int etatEcritureValider = 11;
    private static final int etatSituationdroitOK = 11;
    private static final int etatOpInclusOvCree = 12;
    private static final int etatEcritureGenerer = 15;
    private static final int etatEcritureCessionGenerer = 16;
    private static final int etatDemandePriseEnChargeDejaPrise = 17;
    private static final int etatDemandeConsultationDejaConsulte = 17;
    private static final int etatSituationdroitRappel = 18;
    private static final int etatOpInclusOvCloture = 19;
    private static final int etatTefEnCasOvCloturer = 19;
    private static final int etatOpInclusOvRejeter = 20;
    private static final int etatTefEnCasOpRejeter = 20;
    private static final int etatDnok = 21;
    private static final int etatOpInclusOvValide = 21;
    public static final int etatExpedition = 21;
    private static final int etatTefEnCasOpValider = 21;
    private static final int etatFinPlacement = 22;
    private static final int etatSituationdroitSuspensionAutoPMD = 28;
    private static final int etatTefEnCasOvRejeter = 30;
    private static final int etatTefEnCasOvValider = 31;
    private static final int etatSituationdroitSuspensionAutoBAF = 38;
    private static final int etatTalonDNOk = 41;
    private static final int etatTalonVentillationValider = 21;
    private static final int etatSituationdroitSuspensionAutoCIE = 48;
    private static final int etatPaye = 51;
    private static final int etatTefFictif = 61;
    private static final int etatEcritureContre = 100;
    private static final int etatPensSusp = 8;
    private static final int etatDemandeEnvoye = 3;
    private static final int etatDemandeRecue = 5;
    private static final int etatDemandeVise = 7;
    private static final int etatDemandeLivre = 9;
    private static final int etatDemandeAccepte = 11;
    private static final int etatDemandeLivraisonPrepare = 3;
    private static final int etatDemandeLivraisonEnvoye = 5;
    private static final int etatDemandeLivraisonRecue = 11;
    public static final int etatProforma = 8;
    public static final int etatBCTefCreer = 17;
    private static final int etatOvInclusTitreValider = 21;
    private static final int etatOvInclusTitreRejeter = 20;
    private static final int etatOvInclusTitreCree = 12;
    private static final int etatOpInclusTitreRejeter = 30;
    private static final int etatOpInclusTitreValider = 31;
    private static final int etatOpRecuParLeBeneficiaire = 31;
    private static final int etatOpMdpEspeceVerifier = 13;
    private static final int etatOpMdpEspeceValiderService = 23;
    private static final int etatEditionRecalculee = 15;
    private static final int etatEditionCloturee = 19;
    private static final String changement_indice = "TYD000005";
    private static final int etatSTCCreer = 24;
    private static final int etatSTCPayer = 34;
    private static final int etatRappelGenerer = 44;
    private static final int etatDebauche = -2;
    private static final String compteTva = "4456100";
    public static final int etatValideNonApprouve = 5;
    public static final String JournalReportNouveau = "COMP000015";
    private static final int etatconsulte = 21;
    public static final int etatterminerdocteur = 12;
    public static final int niveauinfirmiere = 2;
    public static final int niveaudocteur = 3;
    private static final int etatReverser = 15;
    private static final int etatOpMdpEspeceValiderSup = 33;
    private static int etatPretretour = 100;
    private static final int etatOpInclusTitreCreer = 32;
    private static final int etatOpRetournee = 41;
    public static final int etatOvValideSmsEnvoye = 61;
    private static final int etatTefInclusTitreValider = 41;
    private static final int etatTefInclusTitreRejeter = 40;
    private static final int etatVehiculeDisponible = 0;
    private static final int etatVehiculeMaintenance = 1;
    private static final int etatVehiculeEnDeplacement = 2;
    private static final int etatPlanningEntretienNonRealise = 0;
    private static final int etatPlanningEntretienRealise = 1;
    private static final int etatImmoRemiseMagasin = 16;
    public static final int paiePersonnelDebauche = 12;
    public static final int paiePersonnelDeces = 13;
    public static final int paiePersonnelDemission = 14;
    public static final int paiePersonnelRenvoi = 15;
    public static final int paiePersonnelRetraite = 16;
    public static final int paiePersonnelSuspension = 17;
    public static final int paieRubriqueCreer = 1;
    public static final int paieRubriqueValider = 11;
    public static final int paieRubriqueAnnuler = 0;
    private static final int etatretourner = 16;
    private static final int etatReleveConstater = 20;
    private static final int etatRDeclarationConstater = 20;
    private static final int etatORPaye = 20;
    private static final int etatCmViseParDg = 9;
    private static final int etatCmViseParDc = 11;
    private static final int etatCmEnregistre = 15;
    private static final int etatJournalFermer = 15;
    private static final int etatGrandLivreDejaRepporte = 15;
    private static final int etatDossierRetourne = 8;
    private static final int etatTVACloturee = 9;
    private static final int etatArchiveDetruiser = 0;
    private static final int Etatdispatcher = 12;
    private static final int etatNonRecue = -1;
    private static final int etatEvalue = 9;
    private static final int etatCourrierRetourner = 30;
    private static final int etatCourrierRecu = 31;
    private static final int etatCourrierEntrant = 21;
    private static final int etatCourrierSortant = 20;
    public static final int etatOpRepayer = -1;
    public static final int etatFormationRealise = 12;
    public static final int etatDossierValider = 9;
    public static final int etatRenteRadier = 3;
    private static final int etatLivreDisponible = 11;
    private static final int etatLivreIndisponible = 12;
    private static final int etatSupprimer = -11;
    private static final int etatCongeDemandeApprouve = 9;
    private static final int etatMissionTermine = 21;
    private static final int etatMissionApprouve = 9;
    public static final int etatChequeVerse = 6;
    public static final int etatVerse = 31;
    private static final int etatOrdoFait = 12;
    private static final int etatFait = 10;
    private static final int etatLivraison = 20;
    private static final int etatEncoursPayement = 30;
    private static final int cloture = 9;
    private static final int fait = 10;
    private static final int valider = 11;
    private static final int payer = 40;
    private static final int livrer = 20;
    private static final int effectuer = 12;
    private static final int etatRembourser = 5;
    private static final int etatAnnulerPayer = 6;
    private static final int etatACloturer = 8;
    private static final int etatALivrer = 12;
    public static final String etatreport = "5";
    private static final int etatVoarayCuisinier = 10;
    private static final int ingdisponible = 11;
    private static final int ingindisponible = 1;
    private static final int updateEtat = -1;
    private static final int enCours = 3;
    private static final int payerAvance = 30;
    public static final int etatDemandeRechargeCarteValiderSup = 110;
    private static final int etatImmoAttribuer = 17;
    private static final int etatRejetSupOv = 10;
    public static final int etatEnvoye = 21;
    public static final int etatRecu = 31;

    public static int getEtatReverser() {
        return 15;
    }

    public static int getNiveaudocteur() {
        return 3;
    }

    public static int getNiveauinfirmiere() {
        return 2;
    }

    public static int getEtatterminerdocteur() {
        return 12;
    }

    public static int getEtatconsulte() {
        return 21;
    }

    public static String getJournalReportNouveau() {
        return JournalReportNouveau;
    }

    public static String getCompteTVA() {
        return compteTva;
    }

    public static int getEtatDebauche() {
        return -2;
    }

    public static int getEtatEditionCloturee() {
        return 19;
    }

    public static String getChangement_indice() {
        return changement_indice;
    }

    public static int getEtatSTCCreer() {
        return 24;
    }

    public static int getEtatSTCPayer() {
        return 34;
    }

    public static int getEtatOpMdpEspeceVerifier() {
        return 13;
    }

    public static int getEtatOpMdpEspeceValiderService() {
        return 23;
    }

    public static int getEtatOpMdpEspeceValiderSup() {
        return 33;
    }

    public static int getEtatOpRecuParLeBeneficiaire() {
        return 31;
    }

    public static int getEtatOpRetournee() {
        return 41;
    }

    public static int getEtatOrdoFait() {
        return 12;
    }

    public static int getEtatChequeVerse() {
        return 6;
    }

    public static int getEtatCourrierRetourner() {
        return 30;
    }

    public static int getEtatTVACloturee() {
        return 9;
    }

    public static int getEtatCongeDemandeApprouve() {
        return 9;
    }

    public static int getEtatMissionApprouve() {
        return 9;
    }

    public static int getEtatDossierValider() {
        return 9;
    }

    public static int getEtatCourrierRecu() {
        return 31;
    }

    public static int getEtatOpRepayer() {
        return -1;
    }

    public static int getEtatPrivilegerRetourRelance() {
        return 2;
    }

    public static int getEtatEvalue() {
        return 9;
    }

    public static int getEtatGrandLivreDejaRepporte() {
        return 15;
    }

    public static int getEtatJournalFermer() {
        return 15;
    }

    public static int getEtatCmViseParDg() {
        return 9;
    }

    public static int getEtatCmViseParDc() {
        return 11;
    }

    public static int getEtatCmEnregistre() {
        return 15;
    }

    public static int getEtatReleveConstater() {
        return 20;
    }

    public static int getEtatImmoRemiseMagasin() {
        return 16;
    }

    public static int getEtatDemandeLivre() {
        return 9;
    }

    public static int getEtatDemandeAccepte() {
        return 11;
    }

    public static int getEtatDemandeLivraisonPrepare() {
        return 3;
    }

    public static int getEtatDemandeLivraisonEnvoye() {
        return 5;
    }

    public static int getEtatDemandeLivraisonRecue() {
        return 11;
    }

    public static int getEtatDemandeEnvoye() {
        return 3;
    }

    public static int getEtatDemandeRecue() {
        return 5;
    }

    public static int getEtatDemandeVise() {
        return 7;
    }

    public static int getEtatTefInclusTitreValider() {
        return 41;
    }

    public static int getEtatTefInclusTitreRejeter() {
        return 40;
    }

    public static int getEtatOpInclusTitreRejeter() {
        return 30;
    }

    public static int getEtatOpInclusTitreValider() {
        return 31;
    }

    public static int getEtatOpInclusTitreCreer() {
        return 32;
    }

    public static int getEtatOvInclusTitreValider() {
        return 21;
    }

    public static int getEtatOvInclusTitreRejeter() {
        return 20;
    }

    public static int getEtatOvInclusTitreCree() {
        return 12;
    }

    public static int getEtatPensSusp() {
        return 8;
    }

    public static int getEtatImmoCondamne() {
        return 20;
    }

    public static int getEtatImmoCede() {
        return 30;
    }

    public static int getEtatMedicamentLivre() {
        return 2;
    }

    public static int getEtatMedicamentNonLivre() {
        return 1;
    }

    public static int getEtatDossierAnnuler() {
        return -1;
    }

    public static int getEtatEcritureContre() {
        return 100;
    }

    public static int getEtatDossierRecue() {
        return 2;
    }

    public static int getEtatPaye() {
        return 51;
    }

    public static int getEtatTalonDNOk() {
        return 41;
    }

    public static int getEtatEcritureCessionGenerer() {
        return 16;
    }

    public static int getEtatDemandePriseEnChargeDejaPrise() {
        return 17;
    }

    public static int getEtatDemandeConsultationDejaConsulte() {
        return 17;
    }

    public static int getEtatImmoAttribuer() {
        return 17;
    }

    public static int getEtatDnok() {
        return 21;
    }

    public static String getSignificationSituationDroit(int situation) {
        if (situation == 28 || situation == 38 || situation == 48) {
            return "Suspension automatique";
        }
        if (situation == 11) {
            return "Situation ok";
        }
        if (situation == 8) {
            return "Suspendue";
        }
        if (situation == 9) {
            return "Bloqu\u00ef\u00bf\u00bde";
        }
        if (situation == 18) {
            return "Rappel";
        }
        return "";
    }

    public static int getEtatEcritureGenerer() {
        return 15;
    }

    public static int getEtatCreer() {
        return 1;
    }

    public static int getEtatRejeter() {
        return 10;
    }

    public static int getEtatObjetRejeter() {
        return 10;
    }

    public static int getEtatEcritureValider() {
        return 11;
    }

    public static int getEtatSituationdroitOK() {
        return 11;
    }

    public static int getEtatSituationdroitSuspendue() {
        return 8;
    }

    public static int getEtatSituationdroitBloquer() {
        return 9;
    }

    public static int getEtatSituationdroitRappel() {
        return 18;
    }

    public static int getEtatTefEnCasOvCloturer() {
        return 19;
    }

    public static int getEtatTefEnCasOvValider() {
        return 31;
    }

    public static int getEtatTefRejeter() {
        return 10;
    }

    public static int getEtatObjetValider() {
        return 11;
    }

    public static int getEtatTefEnCasOvRejeter() {
        return 30;
    }

    public static int getEtatAnnuler() {
        return 0;
    }

    public static int getEtatTefEnCasOpValider() {
        return 21;
    }

    public static int getEtatValiderSupOp() {
        return 11;
    }

    public static int getEtatCloture() {
        return 9;
    }

    public static int getEtatTefCloture() {
        return 9;
    }

    public static int getEtatTefEnCasOpRejeter() {
        return 20;
    }

    public static int getEtatOpInclusOvCree() {
        return 12;
    }

    public static int getEtatOpInclusOvCloture() {
        return 19;
    }

    public static int getEtatOvCloture() {
        return 9;
    }

    public static int getEtatOpInclusOvRejeter() {
        return 20;
    }

    public static int getEtatRejetSupOv() {
        return 10;
    }

    public static int getEtatOpInclusOvValide() {
        return 21;
    }

    public static int getEtatValiderSupOv() {
        return 11;
    }

    public static int getEtatValiderSupTef() {
        return 11;
    }

    public static int getEtatRejetSupOp() {
        return 10;
    }

    public static int getEtatValider() {
        return 11;
    }

    public static int getEtatSituationdroitSuspensionAutoPMD() {
        return 28;
    }

    public static int getEtatFinPlacement() {
        return 22;
    }

    public static int getEtatTefFictif() {
        return 61;
    }

    public static String etatToChaine(String valeur) {
        int val = Utilitaire.stringToInt(valeur);
        if (val == ConstanteEtat.getEtatAnnuler()) {
            return "<b style='color:orange'>ANNUL&Eacute;(E)</b>";
        }
        if (val == ConstanteEtat.getEtatCloture()) {
            return "<b style='color:blue'>CLOTUR&Eacute;(E)</b>";
        }
        if (val == ConstanteEtat.getEtatDebauche()) {
            return "<b style='color:grey'>DEBAUCH&Eacute;(E)</b>";
        }
        if (val == ConstanteEtat.getEtatEditionCloturee()) {
            return "<b style='color:blue'>CL&Ocirc;TUR&Eacute;E</b>";
        }
        if (val == ConstanteEtat.getEtatEditionRecalculee()) {
            return "<b style='color:grey'>RECALCUL&Eacute;E</b>";
        }
        if (val >= ConstanteEtat.getEtatValider()) {
            return "<b style='color:green'>VIS&Eacute;(E)</b>";
        }
        if (val >= ConstanteEtat.getEtatEnAttente()) {
            return "<b style='color:red'>VIS&Eacute;(E)</b>";
        }
        if (val >= ConstanteEtat.getEtatPaye()) {
            return "<b style='color:red'>PAY&Eacute;(E)</b>";
        }
        if (val == ConstanteEtat.getEtatRejeter()) {
            return "<b style='color:red'>REJET&Eacute;(E)</b>";
        }
        if (val == ConstanteEtat.getEtatCreer()) {
            return "<b style='color:lightskyblue'>CR&Eacute;&Eacute;(E)</b>";
        }
        if (val == ConstanteEtat.getEtatEcritureGenerer()) {
            return "<b>ECRITURE ACQUISITION GENER&Eacute;(E)</b>";
        }
        if (val == ConstanteEtat.getEtatEcritureCessionGenerer()) {
            return "<b>ECRITURE CESSION GENER&Eacute;(E)</b>";
        }
        if (val == ConstanteEtat.getEtatDemandeConsultationDejaConsulte()) {
            return "<b>CONSULTATION GENER&Eacute;E POUR CETTE DEMANDE</b>";
        }
        if (val == ConstanteEtat.getEtatMedicamentLivre()) {
            return "<b>Livr&eacute;</b>";
        }
        if (val == ConstanteEtat.getEtatMedicamentNonLivre()) {
            return "<b>Non Livr&eacute;</b>";
        }
        if (val == ConstanteEtat.getEtatFinPlacement()) {
            return "<b>Cl&ocirc;tur&eacute;</b>";
        }
        if (val == ConstanteEtat.getEtatRappelGenerer()) {
            return "<b style='color:blue'>RAPPEL GENER&Eacute;</b>";
        }
        if (val == ConstanteEtat.getEtatretourner()) {
            return "<b style='color:blue'>Retourn\u00ef\u00bf\u00bd/Rendu</b>";
        }
        if (val == ConstanteEtat.getEtatTalonDNOk()) {
            return "<b style='color:blue'>TALON DN OK</b>";
        }
        return "<b>AUTRE</b>";
    }

    public static int getEtatSituationdroitSuspensionAutoBAF() {
        return 38;
    }

    public static int getEtatSituationdroitSuspensionAutoCIE() {
        return 48;
    }

    public static int getEtatEnAttente() {
        return 7;
    }

    public static int getEtatProforma() {
        return 8;
    }

    public static int getEtatVehiculeDisponible() {
        return 0;
    }

    public static int getEtatVehiculeMaintenance() {
        return 1;
    }

    public static int getEtatVehiculeEnDeplacement() {
        return 2;
    }

    public static int getEtatTalonVentillationValider() {
        return 21;
    }

    public static int getConstanteEtatFinaliser() {
        return 4;
    }

    public static int getEtatBCTefCreer() {
        return 17;
    }

    public static int getEtatPlanningEntretienNonRealise() {
        return 0;
    }

    public static int getEtatPlanningEntretienRealise() {
        return 1;
    }

    public static int getEtatretourner() {
        return 16;
    }

    public static int getEtatRadie() {
        return 2;
    }

    public static int getEtatDossierRetourne() {
        return 8;
    }

    public static int getEtatArchiveDetruiser() {
        return 0;
    }

    public static int getEtatdispatcher() {
        return 12;
    }

    public static int getEtatNonRecue() {
        return -1;
    }

    public static int getEtatLivreDisponible() {
        return 11;
    }

    public static int getEtatLivreIndisponible() {
        return 12;
    }

    public static int getEtatSupprimer() {
        return -11;
    }

    public static int getEtatPretRetour() {
        return etatPretretour;
    }

    public static int getEtatRDeclarationConstater() {
        return 20;
    }

    public static int getEtatMissionTermine() {
        return 21;
    }

    public static int getEtatCourrierEntrant() {
        return 21;
    }

    public static int getEtatCourrierSortant() {
        return 20;
    }

    public static int getEtatORPaye() {
        return 20;
    }

    public static int getEtatNotificationLue() {
        return 2;
    }

    public static int getEtatNotificationNonLue() {
        return 1;
    }

    public static int getEtatRappelGenerer() {
        return 44;
    }

    public static int getEtatEditionRecalculee() {
        return 15;
    }

    public static int getEtatValideNonApprouve() {
        return 5;
    }

    public static int getPayerAvance() {
        return 30;
    }

    public static int getEnCours() {
        return 3;
    }

    public static int getIngdisponible() {
        return 11;
    }

    public static int getIngindisponible() {
        return 1;
    }

    public static int getUpdateEtat() {
        return -1;
    }

    public static int getEtatVoarayCuisinier() {
        return 10;
    }

    public static int getEtatRembourser() {
        return 5;
    }

    public static int getEtatAnnulerPayer() {
        return 6;
    }

    public static int getEtatACloturer() {
        return 8;
    }

    public static int getEtatALivrer() {
        return 12;
    }

    public static String getEtatreport() {
        return etatreport;
    }

    public static int getEffectuer() {
        return 12;
    }

    public static int getCloture() {
        return 9;
    }

    public static int getFait() {
        return 10;
    }

    public static int getValider() {
        return 11;
    }

    public static int getPayer() {
        return 40;
    }

    public static int getLivrer() {
        return 20;
    }

    public static int getEtatFait() {
        return 10;
    }

    public static int getEtatLivraison() {
        return 20;
    }

    public static int getEtatEncoursPayement() {
        return 30;
    }

    public static String etatToChaineLivraison(int valeur) {
        return ConstanteEtat.etatToChaineLivraison(String.valueOf(valeur));
    }

    public static String etatToChaineLivraison(String valeur) {
        int val = Utilitaire.stringToInt(valeur);
        if (val == ConstanteEtat.getEtatCreer()) {
            return "Cr&eacute;e";
        }
        if (val == ConstanteEtat.getEtatValider()) {
            return "Valid&eacute;";
        }
        if (val == ConstanteEtat.getEtatAnnuler()) {
            return "Annul&eacute;";
        }
        if (val == ConstanteEtat.getEtatCloture()) {
            return "Cl&ocirc;tur&eacute;";
        }
        if (val == ConstanteEtat.getEtatLivraison() || val == ConstanteEtat.getEtatPaye()) {
            return "Livr&eacute;e";
        }
        if (val == ConstanteEtat.getEtatFait()) {
            return "Fait";
        }
        if (val == ConstanteEtat.getEtatRembourser()) {
            return "Rembours&eacute;";
        }
        if (val == ConstanteEtat.getEtatACloturer()) {
            return "A Cloturer";
        }
        if (val == ConstanteEtat.getEtatALivrer()) {
            return "A Livrer";
        }
        if (val == ConstanteEtat.getEtatEncoursPayement()) {
            return "<b style='color:yellow'>EN COURS DE PAIEMENT</b>";
        }
        if (val == ConstanteEtat.getPayerAvance()) {
            return "Avance Pay&eacute;e";
        }
        return "AUTRE";
    }

    public static int chaineToEtat(String chaine) {
        if (chaine.compareToIgnoreCase("cree") == 0) {
            return ConstanteEtat.getEtatCreer();
        }
        if (chaine.compareToIgnoreCase("valide") == 0 || chaine.compareToIgnoreCase("cloture") == 0) {
            return ConstanteEtat.getEtatValider();
        }
        int val = Utilitaire.stringToInt(chaine);
        if (val > 0) {
            return val;
        }
        return 0;
    }
}

