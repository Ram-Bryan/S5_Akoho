/*
 * Decompiled with CFR 0.152.
 */
package constante;

import utilitaire.Utilitaire;

public class ConstanteEtat {
    public static final int etatNotificationLue = 2;
    public static final int etatNotificationNonLue = 1;
    private static final int etatLivraison = 20;
    private static final int etatPayer = 19;
    private static final int etatAnnuler = 0;
    private static final int etatCreer = 1;
    private static final int etatCloture = 9;
    private static final int etatRejeter = 10;
    private static final int etatValider = 11;
    public static final int etatProforma = 8;
    private static final int etatInterviewe = 7;
    public static final String ETAT_PAYE = "PAYE";
    public static final String ETAT_NON_PAYE = "NON PAYE";
    public static final String ETAT_LIVRE = "LIVRE";
    public static final int paiePersonnelDebauche = 12;
    public static final int paiePersonnelDeces = 13;
    public static final int paiePersonnelDemission = 14;
    public static final int paiePersonnelRenvoi = 15;
    public static final int paiePersonnelRetraite = 16;
    public static final int paiePersonnelSuspension = 17;
    public static final int paieRubriqueCreer = 1;
    public static final int paieRubriqueValider = 11;
    public static final int paieRubriqueAnnuler = 0;
    private static final int etatRejetSupOv = 10;
    public static final int etatPlanifie = 17;

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
            return "Bloqu\u00c3\u00af\u00c2\u00bf\u00c2\u00bde";
        }
        if (situation == 18) {
            return "Rappel";
        }
        return "";
    }

    public static int getEtatCreer() {
        return 1;
    }

    public static int getEtatRejeter() {
        return 10;
    }

    public static int getEtatAnnuler() {
        return 0;
    }

    public static int getEtatCloture() {
        return 9;
    }

    public static int getEtatRejetSupOv() {
        return 10;
    }

    public static int getEtatValider() {
        return 11;
    }

    public static int getEtatInterviewe() {
        return 7;
    }

    public static String etatToChaine(String valeur) {
        int val = Utilitaire.stringToInt(valeur);
        if (val == ConstanteEtat.getEtatAnnuler()) {
            return "<b style='color:orange'>ANNUL&Eacute;(E)</b>";
        }
        if (val == ConstanteEtat.getEtatCloture()) {
            return "<b style='color:blue'>CLOTUR&Eacute;(E)</b>";
        }
        if (val >= ConstanteEtat.getEtatValider()) {
            return "<b style='color:green'>VIS&Eacute;(E)</b>";
        }
        if (val == ConstanteEtat.getEtatRejeter()) {
            return "<b style='color:red'>REJET&Eacute;(E)</b>";
        }
        if (val == ConstanteEtat.getEtatCreer()) {
            return "<b style='color:lightskyblue'>CR&Eacute;&Eacute;(E)</b>";
        }
        if (val == ConstanteEtat.getEtatInterviewe()) {
            return "<b style='color:lightskyblue'>INTERVIEW&Eacute;(E)</b>";
        }
        return "<b>AUTRE</b>";
    }

    public static int getEtatProforma() {
        return 8;
    }

    public static int getEtatNotificationLue() {
        return 2;
    }

    public static int getEtatNotificationNonLue() {
        return 1;
    }

    public static int getEtatPlanifie() {
        return 17;
    }

    public static int getEtatPayer() {
        return 19;
    }
}

