/*
 * Decompiled with CFR 0.152.
 */
package reporting.utilitaire;

public class ConstanteCR {
    public static final String[] mailRapport = new String[]{"noreplyasync@gmail.com", "lhcp apld qdgt tjqp"};
    public static final String PROJECT_NAME = "TeamTask.war";
    public static final String JAR_NAME = "teamTask-ejb.jar";
    public static String BASE_DIR = System.getProperty("jboss.server.base.dir") + "/deployments/dossier.war/";
    public static String RAPPELECHEANCEFACTURETEMPLATE = "MRT00001";
    public static String RELANCECONCERNANTDEVISTEMPLATE = "MRT00002";
    public static String CONFIRMATIONBDCTEMPLATE = "MRT00003";
    public static String RELANCEFACTUREECHUETEMPLATE = "MRT00004";
    public static int CRJOURNALIER = 1;
    public static int CRHEBDOMADAIRE = 2;
    public static int CRMENSUEL = 3;

    public static String[] getMailRapport() {
        return mailRapport;
    }
}

