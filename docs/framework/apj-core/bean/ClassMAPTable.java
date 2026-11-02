/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  notification.Notification
 *  org.apache.commons.csv.CSVFormat
 *  org.apache.commons.csv.CSVPrinter
 *  user.UserEJB
 *  user.UserEJBBean
 */
package bean;

import bean.CGenUtil;
import bean.Champ;
import bean.ClassEtat;
import bean.ClassFille;
import bean.ClassMAPTableException;
import bean.ClassMere;
import bean.ClassUser;
import bean.ErreurDAO;
import bean.ListeColonneTable;
import bean.ResultatEtSomme;
import bean.UploadPj;
import historique.HistoInsert;
import historique.Historique_valeur;
import historique.MapHistorique;
import historique.MapUtilisateur;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import notification.Notification;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import user.UserEJB;
import user.UserEJBBean;
import utilitaire.ConstanteEtat;
import utilitaire.Log;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;
import utilitaire.UtilitaireFormule;

public abstract class ClassMAPTable
implements Serializable {
    public static final String histoSync = "REQUETE_A_ENVOYER";
    String nomChampDatyDuplique = "daty";
    String nomChampTotalPrev = null;
    String sensPrev = "credit";
    boolean estIndexable = false;
    String attrMere;
    String classMere;
    UserEJBBean userEJBBean;
    UserEJB userEJB;
    public static final boolean isMirroring = false;
    Date dateHistoriqueValeur;
    String idHistoriqueValeur;
    int nombreApresVirgule = 5;
    String requete;
    String actionValidation;
    String motsClesss = "";
    Map<String, String[]> paramsMultiple;
    Set<String> attributsRecherchePrecis;
    String messageNotif;
    String notifAWhere;
    String fichierUploadInsert;
    String fichierUploadInsertData;
    String fichierUploadInsertName;
    String nomTableHistoriqueValeur;
    static final int TYPE_CLASS_MAP_BOOLEAN = 0;
    static final int TYPE_CLASS_MAP_BYTE = 1;
    static final int TYPE_CLASS_MAP_SHORT = 2;
    static final int TYPE_CLASS_MAP_INT = 3;
    static final int TYPE_CLASS_MAP_LONG = 4;
    static final int TYPE_CLASS_MAP_FLOAT = 5;
    static final int TYPE_CLASS_MAP_DOUBLE = 6;
    static final int TYPE_CLASS_MAP_STRING = 10;
    static final int TYPE_CLASS_MAP_WBYTE = 11;
    static final int TYPE_CLASS_MAP_WSHORT = 12;
    static final int TYPE_CLASS_MAP_WINT = 13;
    static final int TYPE_CLASS_MAP_WLONG = 14;
    static final int TYPE_CLASS_MAP_WFLOAT = 15;
    static final int TYPE_CLASS_MAP_WDOUBLE = 16;
    static final int TYPE_CLASS_MAP_DATE = 21;
    static final int TYPE_CLASS_MAP_TIME = 22;
    static final int TYPE_CLASS_MAP_BLOB = 31;
    static final int TYPE_CLASS_MAP_CLOB = 32;
    static final int TYPE_CLASS_MAP_INTEGER = 33;
    String nomTable;
    Vector champ;
    int nombreChamp;
    Class cls;
    String INDICE_PK = "EX";
    String nomProcedureSequence = "GetSeqExecutions";
    int longuerClePrimaire = 6;
    String nomTableSelect;
    private String mode = "modif";
    private String columnlibelle = null;
    private boolean groupee = false;
    private int nombrepargroupe;
    String nomSequenceDirecte;
    boolean estHistorise = false;
    String memo = "";

    public boolean getIsMirroring() {
        return false;
    }

    public String getFichierUploadInsertData() {
        return this.fichierUploadInsertData;
    }

    public void setFichierUploadInsertData(String fichierUploadInsertData) {
        this.fichierUploadInsertData = fichierUploadInsertData;
    }

    public String getFichierUploadInsertName() {
        return this.fichierUploadInsertName;
    }

    public void setFichierUploadInsertName(String fichierUploadInsertName) {
        this.fichierUploadInsertName = fichierUploadInsertName;
    }

    public String getFichierUploadInsert() {
        return this.fichierUploadInsert;
    }

    public void setFichierUploadInsert(String fichierUploadInsert) {
        this.fichierUploadInsert = fichierUploadInsert;
    }

    public String getGroupeTable() throws Exception {
        String retour = (String)ListeColonneTable.getClasseEtPackage().get(this.getClassName());
        if ((retour == null || retour.compareToIgnoreCase("") == 0) && this.getClass().getPackage() != null) {
            return this.getClass().getPackage().getName();
        }
        return retour;
    }

    public boolean estDansleGroupeTable(String groupeTable) throws Exception {
        String gp = this.getGroupeTable();
        return gp.toLowerCase().contains(groupeTable.toLowerCase());
    }

    public UserEJB getUserEJB() {
        return this.userEJB;
    }

    public void setUserEJB(UserEJB userEjb) {
        this.userEJB = userEjb;
    }

    public String getMessageNotif() {
        return this.messageNotif;
    }

    public void setMessageNotif(String messageNotif) {
        this.messageNotif = messageNotif;
    }

    public String getNotifAWhere() {
        return this.notifAWhere;
    }

    public void setNotifAWhere(String notifAWhere) {
        this.notifAWhere = notifAWhere;
    }

    public Map<String, String[]> getParamsMultiple() {
        return this.paramsMultiple;
    }

    public void setParamsMultiple(Map<String, String[]> paramsMultiple) {
        this.paramsMultiple = paramsMultiple;
    }

    public void addAttributRecherchePrecis(String nomAttribut) {
        String nomNormalise = this.normaliserNomAttributRecherchePrecis(nomAttribut);
        if (nomNormalise == null) {
            return;
        }
        if (this.attributsRecherchePrecis == null) {
            this.attributsRecherchePrecis = new HashSet<String>();
        }
        this.attributsRecherchePrecis.add(nomNormalise);
    }

    public boolean isRecherchePrecis(String nomAttribut) {
        if (this.attributsRecherchePrecis == null) {
            return false;
        }
        String nomNormalise = this.normaliserNomAttributRecherchePrecis(nomAttribut);
        return nomNormalise != null && this.attributsRecherchePrecis.contains(nomNormalise);
    }

    public Set<String> getAttributsRecherchePrecis() {
        return this.attributsRecherchePrecis;
    }

    private String normaliserNomAttributRecherchePrecis(String nomAttribut) {
        if (nomAttribut == null) {
            return null;
        }
        String nomNormalise = nomAttribut.trim().toLowerCase(Locale.ROOT);
        return nomNormalise.isEmpty() ? null : nomNormalise;
    }

    public String getMotsClesss() {
        return this.motsClesss;
    }

    public void setMotsClesss(String motsClesss) {
        this.motsClesss = motsClesss;
    }

    public String getActionValidation() {
        return this.actionValidation;
    }

    public void setActionValidation(String action) {
        this.actionValidation = action;
    }

    public String getRequete() {
        return this.requete;
    }

    public void setRequete(String requete) {
        this.requete = requete;
    }

    public int getNombreApresVirgule() {
        return this.nombreApresVirgule;
    }

    public void setNombreApresVirgule(int nombreApresVirgule) {
        this.nombreApresVirgule = nombreApresVirgule;
    }

    public boolean isEstIndexable() {
        return this.estIndexable;
    }

    public boolean isGroupee() {
        return this.groupee;
    }

    public boolean isEstHistorise() {
        return this.estHistorise;
    }

    public String getIdHistoriqueValeur() {
        return this.idHistoriqueValeur;
    }

    public void setHistoSync(String histoSync) {
    }

    public String getHistoSync() {
        return histoSync;
    }

    public void setIdHistoriqueValeur(String idHistoriqueValeur) {
        this.idHistoriqueValeur = idHistoriqueValeur;
    }

    public UserEJBBean getUserEJBBean() {
        return this.userEJBBean;
    }

    public void setUserEJBBean(UserEJBBean userEJBBean) {
        this.userEJBBean = userEJBBean;
    }

    public String getNomChampTotalPrev() {
        return this.nomChampTotalPrev;
    }

    public boolean getEstIndexable() {
        return this.estIndexable;
    }

    public void setEstIndexable(boolean estIndexable) {
        this.estIndexable = estIndexable;
    }

    public Date getDateHistoriqueValeur() {
        return this.dateHistoriqueValeur;
    }

    public void setDateHistoriqueValeur(Date dateHistoriqueValeur) {
        this.dateHistoriqueValeur = dateHistoriqueValeur;
    }

    public void setNomChampTotalPrev(String nomChampTotalPrev) {
        this.nomChampTotalPrev = nomChampTotalPrev;
    }

    public String getSensPrev() {
        return this.sensPrev;
    }

    public void setSensPrev(String sensPrev) {
        this.sensPrev = sensPrev;
    }

    public String getNomChampDatyDuplique() {
        return this.nomChampDatyDuplique;
    }

    public void setNomChampDatyDuplique(String nomChampDatyDuplique) {
        this.nomChampDatyDuplique = nomChampDatyDuplique;
    }

    public boolean isSynchro() {
        return false;
    }

    public void appendCsv(CSVPrinter printer, String[] entete) throws Exception {
        printer.printRecord(CGenUtil.getValFromEntete(this, entete));
    }

    public void appendCsv(String filename, String[] entete) throws Exception {
        try (FileWriter fileWriter = new FileWriter(filename, true);
             CSVPrinter csvPrinter = new CSVPrinter((Appendable)fileWriter, CSVFormat.DEFAULT);){
            this.appendCsv(csvPrinter, entete);
        }
    }

    public void saveCsv(CSVPrinter printer, String[] entetesAffiche, String[] entete) throws Exception {
        printer.printRecord((Object[])entetesAffiche);
        printer.printRecord(CGenUtil.getValFromEntete(this, entete));
    }

    public void saveCsv(String filename, String[] entetesAffiche, String[] entete) throws Exception {
        try (FileWriter fileWriter = new FileWriter(filename, false);
             CSVPrinter csvPrinter = new CSVPrinter((Appendable)fileWriter, CSVFormat.DEFAULT);){
            this.saveCsv(csvPrinter, entetesAffiche, entete);
        }
    }

    public static void appendCsv(ClassMAPTable[] objects, String filename, String[] entete) throws Exception {
        try (FileWriter fileWriter = new FileWriter(filename, true);
             CSVPrinter csvPrinter = new CSVPrinter((Appendable)fileWriter, CSVFormat.DEFAULT);){
            for (int i = 0; i < objects.length; ++i) {
                objects[i].appendCsv(csvPrinter, entete);
            }
        }
    }

    public static void saveCsv(ClassMAPTable[] objects, String filename, String[] entetesAffiche, String[] entete) throws Exception {
        try (FileWriter fileWriter = new FileWriter(filename, false);
             CSVPrinter csvPrinter = new CSVPrinter((Appendable)fileWriter, CSVFormat.DEFAULT);){
            for (int i = 0; i < objects.length; ++i) {
                if (i == 0) {
                    objects[i].saveCsv(csvPrinter, entetesAffiche, entete);
                    continue;
                }
                objects[i].appendCsv(csvPrinter, entete);
            }
        }
    }

    public ClassMAPTable dupliquerSansBase() throws Exception {
        Field[] listeC;
        boolean estOuvert = false;
        ClassMAPTable retour = (ClassMAPTable)Class.forName(this.getClassName()).newInstance();
        for (Field f : listeC = this.getFieldList()) {
            CGenUtil.setValChamp(retour, f, CGenUtil.getValeurFieldByMethod(this, f));
        }
        return retour;
    }

    public ClassMAPTable[] dupliquerSansBase(Object[] valeurs, String col) throws Exception {
        ClassMAPTable[] retour = (ClassMAPTable[])Array.newInstance(this.getClass(), valeurs.length);
        for (int i = 0; i < valeurs.length; ++i) {
            Field[] listeC;
            ClassMAPTable classMAPTable = (ClassMAPTable)Class.forName(this.getClassName()).newInstance();
            for (Field f : listeC = this.getFieldList()) {
                CGenUtil.setValChamp(classMAPTable, f, CGenUtil.getValeurFieldByMethod(this, f));
            }
            retour[i] = classMAPTable;
            CGenUtil.setValChamp(retour[i], this.getFieldByName(col), valeurs[i]);
        }
        return retour;
    }

    public void createObjectDupliquerBatch(String u, Connection c, ClassMAPTable[] duplications) throws Exception {
        Statement st = c.createStatement();
        for (int i = 0; i < duplications.length; ++i) {
            duplications[i].createObjectBatch(u, st);
        }
        st.executeBatch();
    }

    public ClassMAPTable dupliquer(String user, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            Field[] listeC;
            if (c == null) {
                estOuvert = true;
                c = new UtilDB().GetConn();
            }
            ClassMAPTable retour = (ClassMAPTable)Class.forName(this.getClassName()).newInstance();
            for (Field f : listeC = this.getFieldList()) {
                CGenUtil.setValChamp(retour, f, CGenUtil.getValeurFieldByMethod(this, f));
            }
            retour.construirePK(c);
            ClassMAPTable classMAPTable = retour;
            return classMAPTable;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (c != null && estOuvert) {
                c.close();
            }
        }
    }

    public ClassMAPTable dupliquer(int freqMois, int qte, String user, Connection c) throws Exception {
        return null;
    }

    public String genererHistoSync(String acte, String req) throws Exception {
        if (!this.isSynchro()) {
            return "";
        }
        if (this.getNomTable().compareTo(histoSync) != 0 && this.getNomTable().compareToIgnoreCase("HISTORIQUE") != 0) {
            String query = "INSERT INTO REQUETE_A_ENVOYER (ID,ACTION,TABLECONCERNE,REQUETE,DATY,HEURE,SERVEUR) VALUES";
            req = req.replace("'", "''");
            query = query + "('RQ'||getSeqRequeteAEnvoyer,'" + acte + "','" + this.getNomTable() + "','" + req + "','" + Utilitaire.dateDuJour() + "','" + Utilitaire.heureCourante() + "',null)";
            return query;
        }
        return "";
    }

    public ClassMAPTable createObjectBatch(String u, Statement st) throws Exception {
        this.controlerBatch(st);
        if (this.getTuppleID() == null || this.getTuppleID().compareToIgnoreCase("") == 0 || this.getTuppleID().compareToIgnoreCase("0") == 0) {
            this.construirePK(st.getConnection());
        }
        this.insertToTableWithHistoBatch(u, st);
        return this;
    }

    public void controlerBatch(Statement st) throws Exception {
        this.controler(st.getConnection());
    }

    public void controlerUpdateBatch(Statement st) throws Exception {
        this.controler(st.getConnection());
    }

    public void controlerClotureBatch(Statement st) throws Exception {
        this.controler(st.getConnection());
    }

    public void insertToTableWithHistoBatch(String refUser, Statement st) throws Exception {
        if (refUser != null && refUser.contains("/")) {
            String[] g = Utilitaire.split(refUser, "/");
            refUser = g[0];
        }
        MapHistorique histo = new MapHistorique(this.getNomTable(), "Insertion par " + refUser, refUser, this.getTuppleID());
        histo.setIdHistorique(histo.makePK(st.getConnection()));
        this.insertToTableBatch(st);
        histo.construirePK(st.getConnection());
        histo.insertToTableBatch(st);
        if (this.getEstIndexable()) {
            HistoInsert histoInsert = new HistoInsert(this.getClassName(), "Insertion par " + refUser, refUser, this.getTuppleID(), st.getConnection());
            histoInsert.setRemarque(this.findRemarque(st.getConnection()));
            histoInsert.setRemarque(histoInsert.getRemarque() + " " + this.rajoutRemarque(st.getConnection()));
            histoInsert.insertToTableBatch(st);
        }
    }

    public void insertToTableBatch(Statement st) throws Exception {
        String recSync;
        String s = this.getComandeInsert(st.getConnection());
        st.addBatch(s);
        if (this.isSynchro() && (recSync = this.genererHistoSync("INSERT", s)) != null && recSync.compareToIgnoreCase("") != 0) {
            st.addBatch(recSync);
        }
    }

    public void updateToTableWithHistoBatch(String refUser, Statement st) throws Exception {
        if (refUser != null && refUser.contains("/")) {
            String[] g = Utilitaire.split(refUser, "/");
            refUser = g[0];
        }
        MapHistorique histo = new MapHistorique(this.getNomTable(), "update par " + refUser, refUser, this.getTuppleID());
        this.updateToTableBatch(st);
        histo.construirePK(st.getConnection());
        histo.insertToTableBatch(st);
    }

    public void deleteToTableWithHistoBatch(String refUser, Statement st) throws Exception {
        if (refUser != null && refUser.contains("/")) {
            String[] g = Utilitaire.split(refUser, "/");
            refUser = g[0];
        }
        MapHistorique histo = new MapHistorique(this.getNomTable(), "delete par " + refUser, refUser, this.getTuppleID());
        this.deleteToTableBatch(st);
        histo.insertToTableBatch(st);
    }

    public void updateToTableBatch(Statement st) throws Exception {
        String recSync;
        String s = this.getComandeUpdate(st.getConnection());
        st.addBatch(s);
        if (this.isSynchro() && (recSync = this.genererHistoSync("UPDATE", s)) != null && recSync.compareToIgnoreCase("") != 0) {
            st.addBatch(recSync);
        }
    }

    public String getCommandeDelete() throws Exception {
        String sqlQry = "DELETE FROM " + this.getNomTable() + " WHERE " + this.getAttributIDName() + " = '" + this.getTuppleID() + "'";
        return sqlQry;
    }

    public void deleteToTableBatch(Statement stmt) throws Exception {
        String s = this.getCommandeDelete();
        stmt.addBatch(s);
        if (this.isSynchro()) {
            String recSync = this.genererHistoSync("INSERT", s);
            stmt.addBatch(recSync);
        }
    }

    public ClassMAPTable createObject(String u, Connection c) throws Exception {
        this.controler(c);
        if (this.getMessageNotif() != null && !this.getMessageNotif().isEmpty()) {
            this.creerNotification(u, c);
        }
        if (this.getTuppleID() == null || this.getTuppleID().compareToIgnoreCase("") == 0 || this.getTuppleID().compareToIgnoreCase("0") == 0) {
            this.construirePK(c);
        }
        this.insertToTableWithHisto(u, c);
        this.fichierUploadInsert(u, c);
        return this;
    }

    public ClassMAPTable createObject(MapUtilisateur u, Connection c) throws Exception {
        return this.createObject(u.getTuppleID(), c);
    }

    public int updateObject(String u, Connection c) throws Exception {
        return this.updateToTableWithHisto(u, c);
    }

    public ClassMAPTable getById(String id, String nTable, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            if (nTable != null) {
                this.setNomTable(nTable);
            }
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
            }
            this.setTuppleId(id);
            ClassMAPTable[] valiny = (ClassMAPTable[])CGenUtil.rechercher(this, null, null, c, "");
            if (valiny.length > 0) {
                ClassMAPTable classMAPTable = valiny[0];
                return classMAPTable;
            }
            ClassMAPTable classMAPTable = null;
            return classMAPTable;
        }
        catch (Exception e) {
            e.printStackTrace();
            if (c != null && estOuvert) {
                c.rollback();
            }
            throw e;
        }
        finally {
            if (c != null && estOuvert) {
                c.close();
            }
        }
    }

    public ClassMAPTable() {
        this.setNombreChamp();
    }

    public String makePK(Connection c) throws Exception {
        int maxSeq = Utilitaire.getMaxSeq(this.nomProcedureSequence, c);
        String nombre = Utilitaire.completerInt(this.longuerClePrimaire, maxSeq);
        if (this.isSynchro()) {
            return String.valueOf(this.INDICE_PK) + String.valueOf(nombre) + Utilitaire.heureCouranteHMSSansSeparateur();
        }
        return String.valueOf(this.INDICE_PK) + String.valueOf(nombre);
    }

    public void construirePK(Connection c) throws Exception {
        this.setTuppleId(this.makePK(c));
    }

    public void controler(Connection c) throws Exception {
    }

    public void controlerCloture(Connection c) throws Exception {
    }

    public void controlerUpdate(Connection c) throws Exception {
    }

    public void setTuppleId(String val) throws Exception {
        this.setValChamp(this.getAttributIDName(), (Object)val);
    }

    public String makePK() {
        int maxSeq = Utilitaire.getMaxSeq(this.nomProcedureSequence);
        String nombre = Utilitaire.completerInt(this.longuerClePrimaire, maxSeq);
        if (this.isSynchro()) {
            return String.valueOf(this.INDICE_PK) + String.valueOf(nombre) + Utilitaire.heureCouranteHMSSansSeparateur();
        }
        return String.valueOf(this.INDICE_PK) + String.valueOf(nombre);
    }

    public String makePK(String indicePK, String fonct) throws Exception {
        this.preparePk(indicePK, fonct);
        return this.makePK();
    }

    public String makePKCFin(String daty) {
        int maxSeq = ClassMAPTable.getMaxColonneFactFin(daty) + 1;
        String nombre = Utilitaire.completerInt(this.longuerClePrimaire, maxSeq);
        return String.valueOf(this.INDICE_PK) + String.valueOf(nombre);
    }

    public String makePKCFinEntite(String daty, String entite) {
        int maxSeq = ClassMAPTable.getMaxColonneFactFinEntite(daty, entite) + 1;
        String nombre = Utilitaire.completerInt(this.longuerClePrimaire, maxSeq);
        return String.valueOf(this.INDICE_PK) + String.valueOf(nombre);
    }

    public void setIndicePk(String indice) {
        this.INDICE_PK = indice;
    }

    public void setNomProcedureSequence(String seq) {
        this.nomProcedureSequence = seq;
    }

    public void setLonguerClePrimaire(int longueur) {
        if (longueur > 0) {
            this.longuerClePrimaire = longueur;
        }
    }

    public String getNomTable() {
        if (this.nomTable == null) {
            if (this.nomTableSelect == null) {
                return this.getClassName();
            }
            return this.nomTableSelect.trim();
        }
        return this.nomTable.trim();
    }

    public void setNomTable(String table) {
        if (table != null && table.compareTo("") != 0) {
            this.nomTable = table;
        }
    }

    public abstract String getTuppleID();

    public abstract String getAttributIDName();

    public String getClassName() {
        return this.getClass().getName();
    }

    public String getValColLibelle() {
        return this.columnlibelle;
    }

    public void setValColLibelle(String columnlib) {
        this.columnlibelle = columnlib;
    }

    public Field[] getFieldList() throws Exception {
        return ListeColonneTable.getFieldListeHeritage(this);
    }

    public void setNombreChamp() {
        Class<?> cls = this.getClass();
        Field[] fieldlist = cls.getDeclaredFields();
        this.nombreChamp = fieldlist.length;
    }

    public void setNombreChamp(int nouveau) {
        this.nombreChamp = nouveau;
    }

    public void setFieldList() {
        try {
            this.cls = this.getClass();
            Field[] fieldlist = this.cls.getDeclaredFields();
            for (int i = 0; i < fieldlist.length; ++i) {
                this.champ.add(i, fieldlist[i]);
            }
            this.nombreChamp = fieldlist.length;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void setFieldList(Field a) {
        try {
            this.champ.add(this.nombreChamp, a);
            ++this.nombreChamp;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    protected int getTypeMAPField(Field fld) {
        String nomClasse = fld.getType().getName();
        if (nomClasse.equals("boolean")) {
            return 0;
        }
        if (nomClasse.equals("byte")) {
            return 1;
        }
        if (nomClasse.equals("short")) {
            return 2;
        }
        if (nomClasse.equals("int")) {
            return 3;
        }
        if (nomClasse.equals("long")) {
            return 4;
        }
        if (nomClasse.equals("float")) {
            return 5;
        }
        if (nomClasse.equals("Real")) {
            return 5;
        }
        if (nomClasse.equals("double")) {
            return 6;
        }
        if (nomClasse.equals("java.lang.String")) {
            return 10;
        }
        if (nomClasse.equals("java.sql.Date")) {
            return 21;
        }
        if (nomClasse.equals("java.sql.Time")) {
            return 22;
        }
        if (nomClasse.equals("java.sql.Blob")) {
            return 31;
        }
        if (nomClasse.equals("java.sql.Clob")) {
            return 32;
        }
        if (nomClasse.equals("java.lang.Number")) {
            return 34;
        }
        if (nomClasse.equals("java.sql.Timestamp")) {
            return 35;
        }
        return !nomClasse.equals("java.lang.Integer") ? -1 : 33;
    }

    public int deleteToTableWithHisto(String refUser, Connection con) throws Exception {
        try {
            int r;
            String temps = refUser;
            if (refUser != null && refUser.contains("/")) {
                String[] g = Utilitaire.split(refUser, "/");
                refUser = g[0];
            }
            this.controlerDelete(con);
            MapHistorique histo = new MapHistorique(this.getNomTable(), "Suppression par " + temps, refUser, this.getTuppleID());
            histo.setObjet(this.getClassName());
            ClassMAPTable obj = (ClassMAPTable)Class.forName(this.getClassName()).newInstance();
            obj.setNomTable(this.getNomTable());
            if (obj != null) {
                // empty if block
            }
            if ((r = this.deleteToTable(con)) >= 1) {
                histo.insertToTable(con);
                return r;
            }
        }
        catch (Exception ex) {
            if (con != null) {
                con.rollback();
            }
            throw new Exception(ex.getMessage());
        }
        return 0;
    }

    public int deleteToTableWithHisto(String refUser) throws Exception {
        try (Connection con = null;){
            con = new UtilDB().GetConn();
            con.setAutoCommit(false);
            this.deleteToTableWithHisto(refUser, con);
            con.commit();
            int n = 1;
            return n;
        }
    }

    public int deleteToTable(Connection cDb) throws Exception {
        Statement insertHistoSync = null;
        String tableName = this.getNomTable();
        Statement stmt = null;
        Object fld = null;
        String sqlQry = "DELETE FROM " + tableName;
        int retoure = 0;
        try {
            sqlQry = sqlQry + " WHERE " + this.getAttributIDName() + " = '" + this.getTuppleID() + "'";
            stmt = cDb.prepareStatement(sqlQry);
            retoure = stmt.executeUpdate();
            if (this.isSynchro() & retoure == 1 && tableName.compareTo(histoSync) != 0 && tableName.compareToIgnoreCase("HISTORIQUE") != 0) {
                String query = "INSERT INTO REQUETE_A_ENVOYER (ID,ACTION,TABLECONCERNE,REQUETE,DATY,HEURE,SERVEUR) VALUES ('RQ'||getSeqRequeteAEnvoyer,?,?,?,?,?,null)";
                insertHistoSync = cDb.prepareStatement(query);
                insertHistoSync.setString(1, "DELETE");
                insertHistoSync.setString(2, tableName);
                insertHistoSync.setString(3, sqlQry);
                insertHistoSync.setDate(4, Utilitaire.dateDuJourSql());
                insertHistoSync.setString(5, Utilitaire.heureCourante());
                if (UtilDB.properties.getProperty("apj.debug") != null && UtilDB.properties.getProperty("apj.debug").equalsIgnoreCase("true")) {
                    System.out.println("Requete Delete : " + query);
                }
                insertHistoSync.executeUpdate();
            }
            cDb.commit();
        }
        catch (SQLException sqlex) {
            throw new Exception(sqlex.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                if (cDb != null) {
                    cDb.rollback();
                }
                throw new Exception("ERREUR deleteToTable " + e.getMessage());
            }
            catch (Exception ee) {
                throw new Exception("ERREUR SQL AVEC CONN " + ee.getMessage());
            }
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (insertHistoSync != null) {
                    insertHistoSync.close();
                }
            }
            catch (Exception se) {
                Log.setLog(this.getClassName() + " - probleme de fermeture d'1 Statement ");
            }
            return retoure;
        }
    }

    public int deleteToTable(String where, Connection cDb) throws Exception {
        String tableName = this.getNomTable();
        Statement stmt = null;
        Object fld = null;
        String sqlQry = "DELETE FROM " + tableName;
        int retoure = 0;
        try {
            sqlQry = sqlQry + " WHERE " + where;
            stmt = cDb.prepareStatement(sqlQry);
            int n = stmt.executeUpdate();
            return n;
        }
        catch (SQLException sqlex) {
            throw new Exception(sqlex.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                if (cDb != null) {
                    cDb.rollback();
                }
                throw new Exception("ERREUR deleteToTable " + e.getMessage());
            }
            catch (Exception ee) {
                throw new Exception("ERREUR SQL AVEC CONN " + ee.getMessage());
            }
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (Exception se) {
                Log.setLog(this.getClassName() + " - probleme de fermeture d'1 Statement ");
            }
            return retoure;
        }
    }

    public int deleteToTableGroupe(Connection cDb) throws Exception {
        String tableName = this.getNomTable();
        Statement stmt = null;
        Object fld = null;
        String sqlQry = "DELETE FROM " + tableName;
        int retoure = 0;
        try {
            sqlQry = sqlQry + " WHERE " + CGenUtil.makeWhere(this);
            stmt = cDb.createStatement();
            int retour = stmt.executeUpdate(sqlQry);
            cDb.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                if (cDb != null) {
                    cDb.rollback();
                }
                throw new Exception("ERREUR deleteToTable " + e.getMessage());
            }
            catch (Exception ee) {
                throw new Exception("ERREUR SQL AVEC CONN " + ee.getMessage());
            }
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (Exception se) {
                Log.setLog(this.getClassName() + " - probleme de fermeture d'1 Statement ");
            }
            return retoure;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int deleteToTable() {
        UtilDB util = new UtilDB();
        Connection c = null;
        try {
            int i;
            c = util.GetConn();
            c.setAutoCommit(false);
            int n = i = this.deleteToTable(c);
            return n;
        }
        catch (Exception e) {
            int j;
            if (c != null) {
                try {
                    c.rollback();
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            int n = j = 0;
            return n;
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (Exception exception) {}
        }
    }

    public int deleteToTable(ClassMAPTable histo) throws ErreurDAO {
        UtilDB util = new UtilDB();
        Connection c = null;
        try {
            int j;
            c = util.GetConn();
            c.setAutoCommit(false);
            histo.insertToTable(c);
            int i = this.deleteToTable(c);
            c.commit();
            int n = j = 1;
            return n;
        }
        catch (Exception e) {
            try {
                if (c != null) {
                    c.rollback();
                }
                throw new ErreurDAO(String.valueOf(String.valueOf(new StringBuffer("ERREUR deleteToTable ").append(e.getMessage()).append(" ").append(e.getMessage()))));
            }
            catch (Exception ee) {
                throw new ErreurDAO(String.valueOf(String.valueOf(new StringBuffer("ERREUR SQL ").append(e.getMessage()).append(" ").append(ee.getMessage()))));
            }
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (Exception exception) {}
        }
    }

    public String getComandeUpdate(Connection cDb) throws Exception {
        String retour = "update " + this.getNomTable() + " set ";
        Field[] fieldlist = ListeColonneTable.getChampBase(this, cDb);
        for (int i = 0; i < fieldlist.length; ++i) {
            Field fld = fieldlist[i];
            String valStr = "''";
            if (this.getValField(fld) != null) {
                switch (this.getTypeMAPField(fld)) {
                    case 0: {
                        valStr = "'" + (Boolean)this.getValField(fld) + "'";
                        break;
                    }
                    case 1: {
                        valStr = "'" + (Byte)this.getValField(fld) + "'";
                        break;
                    }
                    case 2: {
                        valStr = "'" + (Byte)this.getValField(fld) + "'";
                        break;
                    }
                    case 3: {
                        Integer it = (Integer)this.getValField(fld);
                        valStr = "" + it + "";
                        break;
                    }
                    case 4: {
                        Long lo = (Long)this.getValField(fld);
                        valStr = "" + lo + "";
                        break;
                    }
                    case 5: {
                        valStr = "" + ((Float)this.getValField(fld)).floatValue() + "";
                        break;
                    }
                    case 6: {
                        valStr = "" + (Double)this.getValField(fld) + "";
                        break;
                    }
                    case 10: {
                        valStr = "'" + Utilitaire.champNull((String)this.getValField(fld)).replace("'", "`") + "'";
                        break;
                    }
                    case 21: {
                        if ((Date)this.getValField(fld) != null) {
                            valStr = "'" + Utilitaire.formatterDatySql((Date)this.getValField(fld)) + "'";
                            break;
                        }
                        valStr = "'null'";
                        break;
                    }
                    case 33: {
                        break;
                    }
                    case 35: {
                        valStr = (Timestamp)this.getValField(fld) != null ? "TO_TIMESTAMP('" + (Timestamp)this.getValField(fld) + "','YYYY-MM-DD HH24:MI:SS:FF')" : "null";
                    }
                }
            }
            retour = retour + fld.getName() + " = " + valStr + " ";
            if (i + 1 >= fieldlist.length) continue;
            retour = retour + ", ";
        }
        retour = retour + " where " + this.getAttributIDName() + "='" + this.getTuppleID() + "'";
        return retour;
    }

    public int updateToTable(Connection cDb) throws Exception {
        String tableName = this.getNomTable();
        Statement stmt = null;
        CallableStatement insertHistoSync = null;
        Field fld = null;
        String sqlQry = String.valueOf(String.valueOf(new StringBuffer("UPDATE ").append(tableName).append(" SET ")));
        try {
            int j;
            Field[] fieldlist = ListeColonneTable.getChampBase(this, cDb);
            for (int i = 0; i < fieldlist.length; ++i) {
                fld = fieldlist[i];
                sqlQry = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(sqlQry))).append(fld.getName()).append(" = ? ")));
                if (i + 1 >= fieldlist.length) continue;
                sqlQry = String.valueOf(String.valueOf(sqlQry)).concat(", ");
            }
            sqlQry = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(sqlQry))).append(" WHERE ").append(this.getAttributIDName()).append(" = ? ")));
            stmt = cDb.prepareStatement(sqlQry);
            String enTete = "UPDATE " + tableName + " SET ";
            for (int i = 0; i < fieldlist.length; ++i) {
                fld = fieldlist[i];
                String valStr = "";
                switch (this.getTypeMAPField(fld)) {
                    case 0: {
                        stmt.setBoolean(i + 1, (Boolean)this.getValField(fld));
                        valStr = "'" + (Boolean)this.getValField(fld) + "'";
                        break;
                    }
                    case 1: {
                        stmt.setByte(i + 1, (Byte)this.getValField(fld));
                        valStr = "'" + (Byte)this.getValField(fld) + "'";
                        break;
                    }
                    case 2: {
                        stmt.setShort(i + 1, (Short)this.getValField(fld));
                        valStr = "'" + (Byte)this.getValField(fld) + "'";
                        break;
                    }
                    case 3: {
                        Integer it = (Integer)this.getValField(fld);
                        stmt.setInt(i + 1, it);
                        valStr = "" + it + "";
                        break;
                    }
                    case 4: {
                        Long lo = (Long)this.getValField(fld);
                        stmt.setLong(i + 1, lo);
                        valStr = "" + lo + "";
                        break;
                    }
                    case 5: {
                        stmt.setFloat(i + 1, ((Float)this.getValField(fld)).floatValue());
                        valStr = "" + ((Float)this.getValField(fld)).floatValue() + "";
                        break;
                    }
                    case 6: {
                        stmt.setDouble(i + 1, (Double)this.getValField(fld));
                        valStr = "" + (Double)this.getValField(fld) + "";
                        break;
                    }
                    case 10: {
                        stmt.setString(i + 1, (String)this.getValField(fld));
                        valStr = "'" + Utilitaire.champNull((String)this.getValField(fld)).replace("'", "`") + "'";
                        break;
                    }
                    case 21: {
                        stmt.setDate(i + 1, (Date)this.getValField(fld));
                        if ((Date)this.getValField(fld) != null) {
                            valStr = "'" + Utilitaire.formatterDatySql((Date)this.getValField(fld)) + "'";
                            break;
                        }
                        valStr = "'null'";
                        break;
                    }
                    case 33: {
                        stmt.setInt(i + 1, (Integer)this.getValField(fld));
                        break;
                    }
                    case 35: {
                        stmt.setTimestamp(i + 1, (Timestamp)this.getValField(fld));
                        valStr = (Timestamp)this.getValField(fld) != null ? "TO_TIMESTAMP('" + (Timestamp)this.getValField(fld) + "','YYYY-MM-DD HH24:MI:SS:FF')" : "null";
                    }
                }
                valStr = valStr.compareToIgnoreCase("'null'") == 0 ? null : valStr;
                enTete = i + 1 < fieldlist.length ? enTete + fld.getName() + "=" + valStr + ", " : enTete + fld.getName() + "=" + valStr + " ";
            }
            stmt.setString(fieldlist.length + 1, this.getTuppleID());
            int retour = stmt.executeUpdate();
            if (retour == 0) {
                throw new Exception("Erreur modification dans la table");
            }
            enTete = enTete + " WHERE " + this.getAttributIDName() + " = '" + this.getTuppleID() + "'";
            if (UtilDB.properties.getProperty("apj.debug") != null && UtilDB.properties.getProperty("apj.debug").equalsIgnoreCase("true")) {
                System.out.println("Requete update : " + enTete);
            }
            if (this.isSynchro() & retour == 1 && tableName.compareTo(histoSync) != 0 && tableName.compareToIgnoreCase("HISTORIQUE") != 0) {
                String query = "INSERT INTO REQUETE_A_ENVOYER (ID,ACTION,TABLECONCERNE,REQUETE,DATY,HEURE,SERVEUR) VALUES ('RQ'||getSeqRequeteAEnvoyer,?,?,?,?,?,null)";
                insertHistoSync = cDb.prepareCall(query);
                insertHistoSync.setString(1, "INSERT");
                insertHistoSync.setString(2, tableName);
                insertHistoSync.setString(3, enTete);
                insertHistoSync.setDate(4, Utilitaire.dateDuJourSql());
                insertHistoSync.setString(5, Utilitaire.heureCourante());
                insertHistoSync.executeUpdate();
            }
            int n = j = 1;
            return n;
        }
        catch (Exception e) {
            e.printStackTrace();
            if (cDb != null) {
                cDb.rollback();
            }
            throw new Exception("Erreur update dans la table " + this.getNomTable() + " message " + e.getMessage());
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException se) {
                Log.setLog(String.valueOf(String.valueOf(this.getClassName())).concat(" - probleme de fermeture d'1 Statement "));
            }
        }
    }

    public int updateToTableMultiple(String col, String valCol, String[] id, Connection c) throws Exception {
        String apresSet = col + "='" + valCol + "'";
        String aWhere = Utilitaire.tabToString(id, "'", ",");
        aWhere = " where id in(" + aWhere + ")";
        return this.updateToTable(apresSet + aWhere, c);
    }

    public int updateToTableMultiple(String col, String valCol, String[] id, String apresWhere, Connection c) throws Exception {
        String apresSet = col + "='" + valCol + "'";
        String aWhere = Utilitaire.tabToString(id, "'", ",");
        aWhere = " where id in(" + aWhere + ")";
        if (apresWhere == null) {
            apresWhere = "";
        }
        return this.updateToTable(apresSet + aWhere + " " + apresWhere, c);
    }

    public int updateToTableMultiple(String col, Timestamp date, String[] id, Connection c) throws Exception {
        String apresSet = col + "=TO_TIMESTAMP('" + date + "','YYYY-MM-DD HH24:MI:SS:FF')";
        String aWhere = Utilitaire.tabToString(id, "'", ",");
        aWhere = " where id in(" + aWhere + ")";
        return this.updateToTable(apresSet + aWhere, c);
    }

    public int updateToTableMultiple(String col, Timestamp date, String[] id, String apresWhere, Connection c) throws Exception {
        String apresSet = col + "=TO_TIMESTAMP('" + date + "','YYYY-MM-DD HH24:MI:SS:FF')";
        String aWhere = Utilitaire.tabToString(id, "'", ",");
        aWhere = " where id in(" + aWhere + ")";
        if (apresWhere == null) {
            apresWhere = "";
        }
        return this.updateToTable(apresSet + aWhere + " " + apresWhere, c);
    }

    public int updateToTable(String apresSet, Connection cDb) throws Exception {
        String tableName = this.getNomTable();
        Statement stmt = null;
        try {
            int retour;
            String sqlQry = "UPDATE " + tableName + " SET " + apresSet;
            if (UtilDB.properties.getProperty("apj.debug") != null && UtilDB.properties.getProperty("apj.debug").equalsIgnoreCase("true")) {
                System.out.println("Requete update : " + sqlQry);
            }
            if ((retour = (stmt = cDb.createStatement()).executeUpdate(sqlQry)) == 0) {
                throw new Exception("Erreur modification dans la table");
            }
            this.saveSynchronisation(this.isSynchro(), cDb, retour, "UPDATE", sqlQry);
            int n = retour;
            return n;
        }
        catch (Exception e) {
            e.printStackTrace();
            if (cDb != null) {
                cDb.rollback();
            }
            throw new Exception("Erreur update dans la table " + this.getNomTable() + " message" + e.getMessage());
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException se) {
                Log.setLog(String.valueOf(String.valueOf(this.getClassName())).concat(" - probleme de fermeture d'1 Statement "));
            }
        }
    }

    public String getListeChampAInserer(Connection c) throws Exception {
        Champ[] listeC = ListeColonneTable.getFromListe(this, c);
        String retour = "(";
        for (int i = 0; i < listeC.length; ++i) {
            retour = i < listeC.length - 1 ? retour + listeC[i].getNomColonne() + "," : retour + listeC[i].getNomColonne() + ")";
        }
        return retour;
    }

    public String getComandeInsert(Connection c) throws Exception {
        Champ[] listeC = ListeColonneTable.getFromListe(this, c);
        String comande = "insert into " + this.getNomTable() + " " + this.getListeChampAInserer(c) + " values (";
        comande = comande + "'" + CGenUtil.getValeurFieldByMethod(this, listeC[0].getNomColonne()) + "'";
        for (int i = 1; i < listeC.length; ++i) {
            comande = comande + ",";
            if (CGenUtil.getValeurFieldByMethod(this, listeC[i].getNomColonne()) == null) {
                comande = comande + " null ";
                continue;
            }
            String valeurInsert = CGenUtil.getValeurFieldByMethod(this, listeC[i].getNomColonne()).toString();
            if (listeC[i].getTypeColonne().compareToIgnoreCase("date") == 0) {
                valeurInsert = "'" + Utilitaire.formatterDatySql((Date)CGenUtil.getValeurFieldByMethod(this, listeC[i].getNomColonne())) + "'";
            }
            if (listeC[i].getTypeColonne().contains("char")) {
                if (valeurInsert != null) {
                    valeurInsert = valeurInsert.replace("'", " ");
                }
                valeurInsert = "'" + valeurInsert + "'";
            }
            if (listeC[i].getTypeColonne().compareToIgnoreCase("timestamp") == 0) {
                valeurInsert = "TO_TIMESTAMP('" + (Timestamp)CGenUtil.getValeurFieldByMethod(this, listeC[i].getNomColonne()) + "','YYYY-MM-DD HH24:MI:SS:FF')";
            }
            comande = comande + valeurInsert;
        }
        comande = comande + ")";
        return comande;
    }

    public int executeWithRequete(String requete, Connection cDb) throws Exception {
        Statement stmt = null;
        try {
            stmt = cDb.createStatement();
            int retour = stmt.executeUpdate(requete);
            if (retour == 0) {
                throw new Exception("Erreur modification dans la table");
            }
            int n = retour;
            return n;
        }
        catch (Exception e) {
            e.printStackTrace();
            if (cDb != null) {
                cDb.rollback();
            }
            throw new Exception("Erreur update dans la table " + this.getNomTable() + " message : " + e.getMessage());
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException se) {
                Log.setLog(String.valueOf(String.valueOf(this.getClassName())).concat(" - probleme de fermeture d'1 Statement "));
            }
        }
    }

    public int updateToTable(String nomAttributValeur, String valeur, String nomAttributFiltre, String[] valeurFiltre, Connection cDb) throws Exception {
        String apresSet = nomAttributValeur + "=" + valeur + " where ";
        if (nomAttributFiltre == null || valeurFiltre == null || valeurFiltre.length == 0) {
            return this.updateToTable(apresSet + "1<2", cDb);
        }
        apresSet = apresSet + nomAttributFiltre + " in (";
        for (int i = 0; i < valeurFiltre.length; ++i) {
            apresSet = apresSet + "'" + valeurFiltre[i] + "'";
            if (i >= valeurFiltre.length - 1) continue;
            apresSet = apresSet + ",";
        }
        apresSet = apresSet + ")";
        return this.updateToTable(apresSet, cDb);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int upDateToTable() {
        UtilDB util = new UtilDB();
        Connection c = null;
        try {
            c = util.GetConn();
            c.setAutoCommit(false);
            int i = this.updateToTable(c);
            c.commit();
            int n = i;
            return n;
        }
        catch (Exception e) {
            int j;
            if (c != null) {
                try {
                    c.rollback();
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            int n = j = 0;
            return n;
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
                util.close_connection();
            }
            catch (Exception exception) {}
        }
    }

    private Historique_valeur genereHistorique(ClassMAPTable objet, MapHistorique histo, String idHistoriqueValeur, Connection c) throws Exception {
        Historique_valeur valeur = new Historique_valeur();
        Champ[] champs = ListeColonneTable.getFromListe(objet, c);
        for (int i = 0; i < champs.length; ++i) {
            int temp = i + 1;
            valeur.setValChamp("val" + temp, (Object)(champs[i].getNomColonne() + ":" + objet.getValInsert(champs[i].getNomColonne())));
        }
        valeur.setIdhisto(histo.getIdHistorique());
        valeur.setRefhisto(objet.getTuppleID());
        valeur.setNom_table(objet.getValInsert("nomTable"));
        valeur.setNom_classe(objet.getClassName());
        valeur.setVal40(this.getMemo());
        valeur.setIpuser(idHistoriqueValeur);
        valeur.construirePK(c);
        return valeur;
    }

    public String getNomTableHistoriqueValeur() {
        if (this.nomTableHistoriqueValeur == null) {
            return this.nomTable;
        }
        return this.nomTableHistoriqueValeur;
    }

    public void setNomTableHistoriqueValeur(String nomTableHistoriqueValeur) {
        this.nomTableHistoriqueValeur = nomTableHistoriqueValeur;
    }

    public int updateToTableWithHisto(String refUser, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
            }
            String temps = refUser;
            if (refUser != null && refUser.contains("/")) {
                String[] g = Utilitaire.split(refUser, "/");
                refUser = g[0];
            }
            String action = "Modification";
            if (this instanceof ClassEtat && this.getActionValidation() != null && !this.getActionValidation().equalsIgnoreCase("")) {
                action = "Validation";
            }
            MapHistorique histo = new MapHistorique(this.getNomTable(), action + " par " + temps, refUser, this.getTuppleID(), c);
            histo.setObjet(this.getClassName());
            ClassMAPTable obj = (ClassMAPTable)Class.forName(this.getClassName()).newInstance();
            if (obj != null) {
                ClassMAPTable[] tabObj = null;
                if (this.getEstHistorise()) {
                    obj.setNomTableSelect(this.getNomTableHistoriqueValeur());
                    tabObj = (ClassMAPTable[])CGenUtil.rechercher(obj, null, null, c, " and " + obj.getAttributIDName() + " = '" + this.getTuppleID() + "'");
                }
                if (tabObj != null && tabObj.length != 0) {
                    Historique_valeur valeur = this.genereHistorique((ClassMAPTable)tabObj[0], histo, null, c);
                    valeur.insertToTable(c);
                    if (this instanceof ClassMere) {
                        ClassMere mere = (ClassMere)this;
                        ClassFille classFille = (ClassFille)Class.forName(mere.getNomClasseFille()).newInstance();
                        ClassFille[] filles = mere.getFille(classFille.getNomTableHistoriqueValeur(), c, "");
                        for (int i = 0; i < filles.length; ++i) {
                            Historique_valeur historiqueFille = this.genereHistorique(filles[i], histo, valeur.getId(), c);
                            historiqueFille.insertToTable(c);
                        }
                    }
                }
            }
            int n = this.updateToTableWithHisto(histo, c);
            return n;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ERREUR UPDATE 2" + e.getMessage() + " message erruer " + e.getMessage());
        }
        finally {
            if (c != null && estOuvert) {
                c.close();
            }
        }
    }

    public int updateToTableWithHisto(String refUser) throws Exception {
        UtilDB util = new UtilDB();
        Connection c = null;
        try {
            c = util.GetConn();
            c.setAutoCommit(false);
            int i = this.updateToTableWithHisto(refUser, c);
            c.commit();
            int n = i;
            return n;
        }
        catch (Exception e) {
            if (c != null) {
                try {
                    c.rollback();
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw new Exception("ERREUR Update 1 ".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (Exception exception) {}
        }
    }

    public int insertToTableWithHisto(String refUser) throws Exception {
        String temps = refUser;
        if (refUser != null && refUser.contains("/")) {
            String[] g = Utilitaire.split(refUser, "/");
            refUser = g[0];
        }
        MapHistorique histo = new MapHistorique(this.getNomTable(), "Insertion par " + temps, refUser, this.getTuppleID());
        int i = this.insertToTable(histo);
        if (this.getEstIndexable()) {
            HistoInsert histoInsert = new HistoInsert(this.getClassName(), "Insertion par " + temps, refUser, this.getTuppleID());
            histoInsert.setRemarque(this.findRemarque());
            histoInsert.insertToTable();
        }
        return i;
    }

    public void saveSynchronisation(boolean isMirroring, Connection c, int retour, String action, String requette) throws Exception {
        try (Statement insertHistoSync = null;){
            if (this.isSynchro() && this.isSynchro() & retour == 1 && this.getNomTable().compareTo(histoSync) != 0) {
                String query = "INSERT INTO REQUETE_A_ENVOYER (ID,ACTION,TABLECONCERNE,REQUETE,DATY,HEURE) VALUES ('RAE00'||getSeqRequeteAEnvoyer,?,?,?,?,?)";
                insertHistoSync = c.prepareCall(query);
                insertHistoSync.setString(1, action);
                insertHistoSync.setString(2, this.getNomTable());
                insertHistoSync.setString(3, requette);
                insertHistoSync.setDate(4, Utilitaire.dateDuJourSql());
                insertHistoSync.setString(5, Utilitaire.heureCouranteHMS());
                insertHistoSync.executeUpdate();
            }
        }
    }

    public int updateToTableDirecte(String req, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = false;
            }
            Statement s = c.createStatement();
            int valiny = s.executeUpdate(req);
            this.saveSynchronisation(this.isSynchro(), c, valiny, "UPDATE", req);
            int n = valiny;
            return n;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (c != null && estOuvert) {
                c.close();
            }
        }
    }

    public ClassMAPTable getFK(String attributName, ClassMAPTable classeFK, String nT, Connection c) throws Exception {
        String valAttr = CGenUtil.getValeurInsert(this, attributName);
        classeFK.setTuppleId(valAttr);
        if (nT != null && nT.compareToIgnoreCase("") != 0) {
            classeFK.setNomTable(nT);
        }
        return classeFK.getById(classeFK.getTuppleID(), null, c);
    }

    public String findRemarque(Connection c) throws Exception {
        String remarques = "";
        Champ[] fields = ListeColonneTable.getFromListe(this, c);
        for (int i = 0; i < fields.length; ++i) {
            if (fields[i].getNomColonne().compareToIgnoreCase("remarque") == 0) {
                remarques = remarques + CGenUtil.getValeurInsert(this, "remarque");
                remarques = remarques + " ";
                continue;
            }
            if (fields[i].getNomColonne().compareToIgnoreCase("libelle") == 0) {
                remarques = remarques + CGenUtil.getValeurInsert(this, "libelle");
                remarques = remarques + " ";
                continue;
            }
            if (fields[i].getNomColonne().compareToIgnoreCase("designation") == 0) {
                remarques = remarques + CGenUtil.getValeurInsert(this, "designation");
                remarques = remarques + " ";
                continue;
            }
            if (fields[i].getNomColonne().compareToIgnoreCase("val") == 0) {
                remarques = remarques + CGenUtil.getValeurInsert(this, "val");
                remarques = remarques + " ";
                continue;
            }
            if (fields[i].getNomColonne().compareToIgnoreCase("explication") == 0) {
                remarques = remarques + CGenUtil.getValeurInsert(this, "explication");
                remarques = remarques + " ";
                continue;
            }
            if (fields[i].getNomColonne().compareToIgnoreCase("numero") != 0) continue;
            remarques = remarques + CGenUtil.getValeurInsert(this, "numero");
            remarques = remarques + " ";
        }
        return remarques;
    }

    public String findRemarque() throws Exception {
        String remarques = "";
        Champ[] fields = ListeColonneTable.getFromListe(this, null);
        for (int i = 0; i < fields.length; ++i) {
            if (fields[i].getNomColonne().compareToIgnoreCase("remarque") == 0) {
                remarques = remarques + CGenUtil.getValeurInsert(this, "remarque");
                remarques = remarques + " ";
                continue;
            }
            if (fields[i].getNomColonne().compareToIgnoreCase("libelle") == 0) {
                remarques = remarques + CGenUtil.getValeurInsert(this, "libelle");
                remarques = remarques + " ";
                continue;
            }
            if (fields[i].getNomColonne().compareToIgnoreCase("designation") == 0) {
                remarques = remarques + CGenUtil.getValeurInsert(this, "designation");
                remarques = remarques + " ";
                continue;
            }
            if (fields[i].getNomColonne().compareToIgnoreCase("val") == 0) {
                remarques = remarques + CGenUtil.getValeurInsert(this, "val");
                remarques = remarques + " ";
                continue;
            }
            if (fields[i].getNomColonne().compareToIgnoreCase("explication") == 0) {
                remarques = remarques + CGenUtil.getValeurInsert(this, "explication");
                remarques = remarques + " ";
                continue;
            }
            if (fields[i].getNomColonne().compareToIgnoreCase("numero") != 0) continue;
            remarques = remarques + CGenUtil.getValeurInsert(this, "numero");
            remarques = remarques + " ";
        }
        return remarques;
    }

    public ResultatEtSomme rechercherPage(String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, Connection c, int npp) throws Exception {
        return CGenUtil.rechercherPage(this, colInt, valInt, numPage, apresWhere, nomColSomme, c, npp);
    }

    public ResultatEtSomme rechercherPageGroupe(ClassMAPTable e, String[] groupe, String[] sommeGroupe, String[] colInt, String[] valInt, int numPage, String apresWhere, String[] nomColSomme, String ordre, Connection c, int npp) throws Exception {
        return CGenUtil.rechercherPageGroupe(e, groupe, sommeGroupe, colInt, valInt, numPage, apresWhere, nomColSomme, ordre, c, npp);
    }

    public String rajoutRemarque(Connection c) throws Exception {
        return "";
    }

    public int insertToTableWithHisto(String refUser, Connection c) throws Exception {
        try {
            String temps = refUser;
            if (refUser != null && refUser.contains("/")) {
                String[] g = Utilitaire.split(refUser, "/");
                refUser = g[0];
            }
            MapHistorique histo = new MapHistorique(this.getNomTable(), "Insertion par " + temps, refUser, this.getTuppleID(), c);
            histo.setObjet(this.getClassName());
            int i = this.insertToTable(histo, c);
            if (this.getEstIndexable()) {
                HistoInsert histoInsert = new HistoInsert(this.getClassName(), "Insertion par " + temps, refUser, this.getTuppleID(), c);
                histoInsert.setRemarque(this.findRemarque());
                histoInsert.setRemarque(histoInsert.getRemarque() + " " + this.rajoutRemarque(c));
                histoInsert.insertToTable(c);
            }
            return i;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int updateToTableWithHisto(ClassMAPTable histo, Connection c) throws Exception {
        UtilDB util = new UtilDB();
        try {
            histo.insertToTable(c);
            int i = this.updateToTable(c);
            if (this.getEstIndexable()) {
                String refUser = histo.getClass().getDeclaredMethod("getIdUtilisateur", new Class[0]).invoke(histo, new Object[0]).toString();
                HistoInsert histoInsert = new HistoInsert();
                histoInsert.setRefObjet(this.getTuppleID());
                HistoInsert[] histotable = (HistoInsert[])CGenUtil.rechercher(histoInsert, null, null, c, "");
                if (histotable == null || histotable.length == 0) {
                    System.out.println("HISTORIQUE INSERT DE CETTE OBJECT NULL");
                } else {
                    histotable[0].setRemarque(this.findRemarque());
                    histotable[0].updateToTable(c);
                }
            }
            return i;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ERREUR Modification table 3 " + e.getMessage());
        }
    }

    public int updateToTableWithHisto(ClassMAPTable histo) throws ErreurDAO {
        UtilDB util = new UtilDB();
        Connection c = null;
        try {
            int j;
            c = util.GetConn();
            c.setAutoCommit(false);
            histo.insertToTable(c);
            int i = this.updateToTable(c);
            if (this.getEstIndexable()) {
                String refUser = histo.getClass().getDeclaredMethod("getIdUtilisateur", new Class[0]).invoke(histo, new Object[0]).toString();
                HistoInsert histoInsert = new HistoInsert();
                histoInsert.setRefObjet(this.getTuppleID());
                HistoInsert[] histotable = (HistoInsert[])CGenUtil.rechercher(histoInsert, null, null, c, "");
                if (histotable == null || histotable.length == 0) {
                    System.out.println("HISTORIQUE INSERT DE CETTE OBJECT NULL");
                } else {
                    histotable[0].setRemarque(this.findRemarque());
                    histotable[0].updateToTable(c);
                }
            }
            c.commit();
            int n = j = 1;
            return n;
        }
        catch (Exception e) {
            if (c != null) {
                try {
                    c.rollback();
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw new ErreurDAO(String.valueOf(String.valueOf(new StringBuffer("ERREUR UPDATE_TABLE ").append(this.getNomTable()).append(" ").append(e.getMessage()))));
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
                util.close_connection();
            }
            catch (Exception exception) {}
        }
    }

    public int insertToTable(ClassMAPTable histo, Connection c) throws Exception {
        try {
            int i = this.insertToTable(c);
            return histo.insertToTable(c);
        }
        catch (Exception e) {
            if (c != null) {
                c.rollback();
            }
            throw e;
        }
    }

    public int insertToTable(ClassMAPTable histo) throws Exception {
        UtilDB util = new UtilDB();
        Connection c = null;
        try {
            c = util.GetConn();
            c.setAutoCommit(false);
            int i = this.insertToTable(histo, c);
            c.commit();
            int n = i;
            return n;
        }
        catch (Exception e) {
            if (c != null) {
                try {
                    c.rollback();
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                    throw new Exception(e.getMessage());
                }
            }
            throw new Exception("ERREUR INSERT_TABLE ".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
                util.close_connection();
            }
            catch (Exception exception) {}
        }
    }

    public int insertToTable() throws Exception {
        UtilDB util = new UtilDB();
        Connection c = null;
        int retour = 0;
        try {
            int i;
            c = util.GetConn();
            c.setAutoCommit(false);
            retour = this.insertToTable(c);
            c.commit();
            int n = i = retour;
            return n;
        }
        catch (Exception e) {
            if (c != null) {
                try {
                    c.rollback();
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw e;
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
                util.close_connection();
            }
            catch (Exception exception) {}
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int insertToTableWithId(Connection cDb) throws Exception {
        ClassUser temp;
        String tableName = this.getNomTable();
        Statement stmt = null;
        Field fld = null;
        String sqlQry = "INSERT INTO " + tableName + " ( ";
        String sqlTmp = " ) VALUES ( ";
        if (this instanceof ClassEtat && ((temp = (ClassEtat)this).getIduser() == null || temp.getIduser().compareTo("") == 0)) {
            temp.setIduser("0");
        }
        if (this instanceof ClassUser && ((temp = (ClassUser)this).getIduser() == null || temp.getIduser().compareTo("") == 0)) {
            temp.setIduser("0");
        }
        try {
            int j;
            Field[] fieldlist = ListeColonneTable.getChampBase(this, cDb);
            for (int i = 0; i < fieldlist.length; ++i) {
                fld = fieldlist[i];
                sqlQry = sqlQry + fld.getName() + " ";
                sqlTmp = fld.getName().compareToIgnoreCase(this.getAttributIDName()) == 0 ? sqlTmp + "'" + this.getValInsert(this.getAttributIDName()) + "'" : sqlTmp + "? ";
                if (i + 1 >= fieldlist.length) continue;
                sqlQry = sqlQry + ", ";
                sqlTmp = sqlTmp + ", ";
            }
            sqlQry = sqlQry + sqlTmp + " ) ";
            stmt = cDb.prepareStatement(sqlQry);
            int indicePrepared = 0;
            int i = 1;
            while (i < fieldlist.length) {
                fld = fieldlist[i];
                switch (this.getTypeMAPField(fld)) {
                    case 0: {
                        stmt.setBoolean(indicePrepared + 1, (Boolean)this.getValField(fld));
                        break;
                    }
                    case 1: {
                        stmt.setByte(indicePrepared + 1, (Byte)this.getValField(fld));
                        break;
                    }
                    case 2: {
                        stmt.setShort(indicePrepared + 1, (Short)this.getValField(fld));
                        break;
                    }
                    case 3: {
                        Integer it = (Integer)this.getValField(fld);
                        stmt.setInt(indicePrepared + 1, it);
                        break;
                    }
                    case 4: {
                        Long lo = (Long)this.getValField(fld);
                        stmt.setLong(indicePrepared + 1, lo);
                        break;
                    }
                    case 5: {
                        stmt.setFloat(indicePrepared + 1, ((Float)this.getValField(fld)).floatValue());
                        break;
                    }
                    case 6: {
                        stmt.setDouble(indicePrepared + 1, (Double)this.getValField(fld));
                        break;
                    }
                    case 10: {
                        if (fld.getName().compareToIgnoreCase(this.getAttributIDName()) != 0 || this.getNomSequenceDirecte() == null || this.getNomSequenceDirecte().compareToIgnoreCase("") == 0) {
                            stmt.setString(indicePrepared + 1, Utilitaire.champNull((String)this.getValField(fld)).replace("'", "`"));
                            break;
                        }
                        --indicePrepared;
                        break;
                    }
                    case 21: {
                        stmt.setDate(indicePrepared + 1, (Date)this.getValField(fld));
                        break;
                    }
                    case 33: {
                        stmt.setInt(indicePrepared + 1, (Integer)this.getValField(fld));
                        break;
                    }
                    case 34: {
                        stmt.setInt(indicePrepared + 1, (Integer)this.getValField(fld));
                        break;
                    }
                    case 35: {
                        stmt.setTimestamp(indicePrepared + 1, (Timestamp)this.getValField(fld));
                    }
                }
                ++i;
                ++indicePrepared;
            }
            int retour = stmt.executeUpdate();
            if (retour == 0) {
                throw new Exception("Erreur insertion dans la table ");
            }
            int n = j = 1;
            return n;
        }
        catch (Exception e) {
            try {
                if (cDb != null) {
                    cDb.rollback();
                }
                e.printStackTrace();
                throw new Exception("Erreur insertion dans la table " + this.getNomTable() + " " + e.getMessage() + " ref= " + this.getTuppleID());
            }
            catch (Exception ex) {
                int n = 0;
                return n;
            }
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException se) {
                Log.setLog(String.valueOf(String.valueOf(this.getClassName())).concat(" - probleme de fermeture d'1 Statement "));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int insertIntoTablePaieEdition(String refUser, Connection cDb, String idpersonnel, String idelementpaie, int mois, int annee, Date datedebut, Date datefin, double gain, double retenue) throws Exception {
        Statement stmt = null;
        try {
            String sqlQry = "INSERT INTO PAIE_EDITION(ID, IDPERSONNEL , IDELEMENTPAIE, MOIS, ANNEE, GAIN, RETENUE, DATEDEBUT, DATEFIN) VALUES (";
            String sqlTmp = "'ELP'||seq_paie_edition.nextval";
            sqlTmp = sqlTmp + " , ? , ? , ? , ? , ? , ? , ? , ? ";
            sqlQry = sqlQry + sqlTmp + ")";
            stmt = cDb.prepareStatement(sqlQry);
            stmt.setString(1, idpersonnel);
            stmt.setString(2, idelementpaie);
            stmt.setInt(3, mois);
            stmt.setInt(4, annee);
            stmt.setDouble(5, gain);
            stmt.setDouble(6, retenue);
            stmt.setDate(7, datedebut);
            stmt.setDate(8, datefin);
            int retour = stmt.executeUpdate();
            if (retour == 0) {
                throw new Exception("Erreur insertion dans la table paie_edition");
            }
            int n = this.insertPaieEditionIntoHistorique(cDb, refUser);
            return n;
        }
        catch (Exception e) {
            try {
                cDb.rollback();
                throw new Exception("Erreur insertion dans la table paie_edition ref= " + this.getTuppleID());
            }
            catch (Exception ex) {
                int n = 0;
                return n;
            }
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException se) {
                Log.setLog("paie_edition - probleme de fermeture d'1 Statement ");
            }
        }
    }

    public Object getValeur(String nomChamp) throws Exception {
        return CGenUtil.getValeurFieldByMethod(this, nomChamp);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int insertPaieEditionIntoHistorique(Connection cDb, String refUser) throws Exception {
        Statement stmt = null;
        try {
            int j;
            String temps = refUser;
            if (refUser != null && refUser.contains("/")) {
                String[] g = Utilitaire.split(refUser, "/");
                refUser = g[0];
            }
            String sqlQry = "INSERT INTO HISTORIQUE(IDHISTORIQUE , DATEHISTORIQUE, HEURE, OBJET, ACTION, IDUTILISATEUR, REFOBJET) VALUES (";
            String sqlTmp = "'ELP'||SEQ_HISTORIQUE.nextval";
            sqlTmp = sqlTmp + " , ? , ? , ? , ? , ? , ? ";
            sqlQry = sqlQry + sqlTmp + ")";
            stmt = cDb.prepareStatement(sqlQry);
            stmt.setDate(1, Utilitaire.dateDuJourSql());
            stmt.setString(2, Utilitaire.heureCourante());
            stmt.setString(3, this.getClassName());
            stmt.setString(4, "Insertion par " + temps);
            stmt.setString(5, refUser);
            stmt.setString(6, this.getTuppleID());
            int retour = stmt.executeUpdate();
            if (retour == 0) {
                throw new Exception("Erreur insertion dans la table historique");
            }
            int n = j = 1;
            return n;
        }
        catch (Exception e) {
            try {
                if (cDb != null) {
                    cDb.rollback();
                }
                throw new Exception("Erreur insertion dans la table HISTORIQUE ref= " + this.getTuppleID());
            }
            catch (Exception ex) {
                int n = 0;
                return n;
            }
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException se) {
                Log.setLog("HISTORIQUE - probleme de fermeture d'1 Statement ");
            }
        }
    }

    public int insertToTable(Connection cDb) throws Exception {
        ClassEtat temp;
        String tableName = this.getNomTable();
        Statement stmt = null;
        CallableStatement insertHistoSync = null;
        Field fld = null;
        String sqlQry = "INSERT INTO " + tableName + " ( ";
        String sqlTmp = " ) VALUES ( ";
        if (this.getTuppleID() == null || this.getTuppleID().compareToIgnoreCase("") == 0 || this.getTuppleID().equalsIgnoreCase("null")) {
            this.construirePK(cDb);
        }
        if ((this instanceof ClassEtat || this instanceof ClassUser) && ((temp = (ClassEtat)this).getIduser() == null || temp.getIduser().compareTo("") == 0)) {
            temp.setIduser("0");
        }
        try {
            int j;
            int retour;
            Field[] fieldlist = ListeColonneTable.getChampBase(this, cDb);
            for (int i = 0; i < fieldlist.length; ++i) {
                fld = fieldlist[i];
                sqlQry = sqlQry + fld.getName() + " ";
                sqlTmp = fld.getName().compareToIgnoreCase(this.getAttributIDName()) == 0 && this.getNomSequenceDirecte() != null && this.getNomSequenceDirecte().compareToIgnoreCase("") != 0 && (this.getTuppleID() == null || this.getTuppleID().compareToIgnoreCase("") == 0) ? sqlTmp + "'" + this.getINDICE_PK() + "'||" + this.getNomSequenceDirecte() + ".nextval" : sqlTmp + "? ";
                if (i + 1 >= fieldlist.length) continue;
                sqlQry = sqlQry + ", ";
                sqlTmp = sqlTmp + ", ";
            }
            String enTete = sqlQry + ") values ( ";
            sqlQry = sqlQry + sqlTmp + " ) ";
            stmt = cDb.prepareStatement(sqlQry);
            int indicePrepared = 0;
            int i = 0;
            while (i < fieldlist.length) {
                fld = fieldlist[i];
                String valStr = "";
                switch (this.getTypeMAPField(fld)) {
                    case 0: {
                        stmt.setBoolean(indicePrepared + 1, (Boolean)this.getValField(fld));
                        valStr = "'" + (Boolean)this.getValField(fld) + "'";
                        break;
                    }
                    case 1: {
                        stmt.setByte(indicePrepared + 1, (Byte)this.getValField(fld));
                        valStr = "'" + (Byte)this.getValField(fld) + "'";
                        break;
                    }
                    case 2: {
                        stmt.setShort(indicePrepared + 1, (Short)this.getValField(fld));
                        valStr = "" + (Short)this.getValField(fld) + "";
                        break;
                    }
                    case 3: {
                        Integer it = (Integer)this.getValField(fld);
                        stmt.setInt(indicePrepared + 1, it);
                        valStr = "" + it + "";
                        break;
                    }
                    case 4: {
                        Long lo = (Long)this.getValField(fld);
                        stmt.setLong(indicePrepared + 1, lo);
                        valStr = "" + lo + "";
                        break;
                    }
                    case 5: {
                        stmt.setFloat(indicePrepared + 1, ((Float)this.getValField(fld)).floatValue());
                        valStr = "" + ((Float)this.getValField(fld)).floatValue() + "";
                        break;
                    }
                    case 6: {
                        stmt.setDouble(indicePrepared + 1, (Double)this.getValField(fld));
                        valStr = "" + (Double)this.getValField(fld) + "";
                        break;
                    }
                    case 10: {
                        String val = (String)this.getValField(fld);
                        if (val != null && val.compareTo("") != 0) {
                            val = val.replace("'", "''");
                        }
                        if (fld.getName().compareToIgnoreCase(this.getAttributIDName()) != 0 || this.getNomSequenceDirecte() == null || this.getNomSequenceDirecte().compareToIgnoreCase("") == 0) {
                            stmt.setString(indicePrepared + 1, (String)this.getValField(fld));
                        } else {
                            --indicePrepared;
                        }
                        valStr = "'" + val + "'";
                        break;
                    }
                    case 21: {
                        stmt.setDate(indicePrepared + 1, (Date)this.getValField(fld));
                        if ((Date)this.getValField(fld) != null) {
                            valStr = "'" + Utilitaire.formatterDatySql((Date)this.getValField(fld)) + "'";
                            break;
                        }
                        valStr = "'null'";
                        break;
                    }
                    case 33: {
                        stmt.setInt(indicePrepared + 1, (Integer)this.getValField(fld));
                        valStr = "'" + (Integer)this.getValField(fld) + "'";
                        break;
                    }
                    case 34: {
                        stmt.setInt(indicePrepared + 1, (Integer)this.getValField(fld));
                        break;
                    }
                    case 35: {
                        stmt.setTimestamp(indicePrepared + 1, (Timestamp)this.getValField(fld));
                        valStr = (Timestamp)this.getValField(fld) != null ? "TO_TIMESTAMP('" + (Timestamp)this.getValField(fld) + "','YYYY-MM-DD HH24:MI:SS:FF')" : "null";
                    }
                }
                valStr = valStr.compareToIgnoreCase("'null'") == 0 ? null : valStr;
                enTete = i + 1 < fieldlist.length ? enTete + valStr + "," : enTete + valStr;
                ++i;
                ++indicePrepared;
            }
            if (UtilDB.properties.getProperty("apj.debug") != null && UtilDB.properties.getProperty("apj.debug").equalsIgnoreCase("true")) {
                System.out.println("Requete d'insertion : " + enTete);
            }
            if ((retour = stmt.executeUpdate()) == 0) {
                throw new Exception("Erreur insertion dans la table ");
            }
            enTete = enTete + ")";
            if (this.isSynchro() & retour == 1 && tableName.compareTo(histoSync) != 0 && tableName.compareToIgnoreCase("HISTORIQUE") != 0) {
                String query = "INSERT INTO REQUETE_A_ENVOYER (ID,ACTION,TABLECONCERNE,REQUETE,DATY,HEURE,SERVEUR) VALUES ('RQ'||getSeqRequeteAEnvoyer,?,?,?,?,?,null)";
                insertHistoSync = cDb.prepareCall(query);
                insertHistoSync.setString(1, "INSERT");
                insertHistoSync.setString(2, tableName);
                insertHistoSync.setString(3, enTete);
                insertHistoSync.setDate(4, Utilitaire.dateDuJourSql());
                insertHistoSync.setString(5, Utilitaire.heureCourante());
                insertHistoSync.executeUpdate();
            }
            int n = j = 1;
            return n;
        }
        catch (Exception e) {
            e.printStackTrace();
            if (cDb != null) {
                cDb.rollback();
            }
            throw new Exception("Erreur insertion dans la table " + this.getNomTable() + " " + e.getMessage() + " ref= " + this.getTuppleID());
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException se) {
                Log.setLog(String.valueOf(String.valueOf(this.getClassName())).concat(" - probleme de fermeture d'1 Statement "));
            }
        }
    }

    public void getInfoFromCursor(ResultSet rs) throws ClassMAPTableException {
        try {
            Field[] fieldlist = this.getFieldList();
            int typeClass = 0;
            block12: for (int i = 0; i < fieldlist.length; ++i) {
                Field fld = fieldlist[i];
                typeClass = this.getTypeMAPField(fld);
                switch (this.getTypeMAPField(fld)) {
                    case 0: {
                        fld.setBoolean(this, rs.getBoolean(fld.getName()));
                        Log.setLog(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.getClassName()))).append(" - Valeur boolean : ").append(fld.getBoolean(this)))));
                        continue block12;
                    }
                    case 1: {
                        fld.setByte(this, rs.getByte(fld.getName()));
                        Log.setLog(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.getClassName()))).append(" - Valeur byte : ").append(fld.getByte(this)))));
                        continue block12;
                    }
                    case 2: {
                        fld.setShort(this, rs.getShort(fld.getName()));
                        Log.setLog(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.getClassName()))).append(" - Valeur Short : ").append(fld.getShort(this)))));
                        continue block12;
                    }
                    case 3: {
                        fld.setInt(this, rs.getInt(fld.getName()));
                        Log.setLog(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.getClassName()))).append(" - Valeur int : ").append(fld.getInt(this)))));
                        continue block12;
                    }
                    case 4: {
                        fld.setLong(this, rs.getLong(fld.getName()));
                        Log.setLog(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.getClassName()))).append(" - Valeur long : ").append(fld.getLong(this)))));
                        continue block12;
                    }
                    case 5: {
                        fld.setFloat(this, rs.getFloat(fld.getName()));
                        Log.setLog(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.getClassName()))).append(" - Valeur Float : ").append(fld.getFloat(this)))));
                        continue block12;
                    }
                    case 6: {
                        fld.setDouble(this, rs.getDouble(fld.getName()));
                        Log.setLog(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.getClassName()))).append(" - Valeur double : ").append(fld.getDouble(this)))));
                        continue block12;
                    }
                    case 10: {
                        Log.setLog(String.valueOf(String.valueOf(this.getClassName())).concat(" - TYPE STRING "));
                        fld.set(this, rs.getString(fld.getName()));
                        Log.setLog(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.getClassName()))).append(" - Valeur String : ").append((String)fld.get(this)))));
                    }
                }
            }
        }
        catch (Exception e) {
            throw new ClassMAPTableException(e.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getMaxColonneFactFin(String daty) {
        UtilDB util = new UtilDB();
        Connection c = null;
        Statement cs = null;
        ResultSet rs = null;
        try {
            int l;
            String an = Utilitaire.getAnnee(daty);
            c = null;
            c = util.GetConn();
            cs = c.prepareStatement(String.valueOf(String.valueOf(new StringBuffer("select * from  seqFact where daty<='31/12/").append(an).append("' and daty>='01/01/").append(an).append("'"))));
            rs = cs.executeQuery();
            int i = 0;
            if (rs.next()) {
                ++i;
            }
            if (i == 0) {
                int k;
                int n = k = 0;
                return n;
            }
            int n = l = new Integer(rs.getString(1)).intValue();
            return n;
        }
        catch (SQLException an) {
            int j;
            int n = j = 0;
            return n;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (c != null) {
                    c.close();
                }
                util.close_connection();
            }
            catch (SQLException sQLException) {}
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getMaxColonneFactFinEntite(String daty, String entite) {
        UtilDB util = new UtilDB();
        Connection c = null;
        Statement cs = null;
        ResultSet rs = null;
        try {
            int l;
            String an = Utilitaire.getAnnee(daty);
            c = null;
            c = util.GetConn();
            cs = c.prepareStatement(String.valueOf(String.valueOf(new StringBuffer("select * from  seqFact where entite='" + entite + "' and daty<='31/12/").append(an).append("' and daty>='01/01/").append(an).append("'"))));
            rs = cs.executeQuery();
            int i = 0;
            if (rs.next()) {
                ++i;
            }
            if (i == 0) {
                int k;
                int n = k = 0;
                return n;
            }
            int n = l = new Integer(rs.getString(1)).intValue();
            return n;
        }
        catch (SQLException an) {
            int j;
            int n = j = 0;
            return n;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException sQLException) {}
        }
    }

    public int compareTo(ClassMAPTable c, String nomChamp) throws Exception {
        Object valeurArgument = CGenUtil.getValeurFieldByMethod(c, nomChamp);
        Object valeur = CGenUtil.getValeurFieldByMethod(this, nomChamp);
        if (valeur == null && valeurArgument == null) {
            return 0;
        }
        if (valeur != null && valeurArgument == null) {
            return 1;
        }
        if (valeur == null && valeurArgument != null) {
            return -1;
        }
        if (valeur instanceof String) {
            return ((String)valeur).compareToIgnoreCase((String)valeurArgument);
        }
        if (valeur instanceof Number) {
            Double valDouble = ((Number)valeur).doubleValue();
            Double valDoubleArg = ((Number)valeurArgument).doubleValue();
            return (int)(valDouble - valDoubleArg);
        }
        if (valeur instanceof Date) {
            return Utilitaire.compareDaty((Date)valeur, (Date)valeurArgument);
        }
        return 0;
    }

    public int getNombreChamp() {
        return this.nombreChamp;
    }

    public void preparePk(String indicePK, String fonct) throws Exception {
        this.setIndicePk(indicePK);
        this.setNomProcedureSequence(fonct);
    }

    public void genererPKInterne() throws Exception {
        String nomMethode = "set" + Utilitaire.convertDebutMajuscule(this.getAttributIDName());
        Object[] args = new String[]{this.makePK()};
        Class[] paramT = new Class[]{new String().getClass()};
        this.getClass().getMethod(nomMethode, paramT).invoke(this, args);
    }

    public void setValChamp(String nomChamp, Object valeur) throws Exception {
        Field f = this.getFieldByName(nomChamp);
        String nomMethode = "set" + Utilitaire.convertDebutMajuscule(nomChamp);
        Object[] args = new Object[]{valeur};
        Class[] paramT = new Class[]{f.getType()};
        if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
            String val;
            args[0] = valeur == null ? null : ((val = String.valueOf(valeur)).equalsIgnoreCase("null") || val.trim().isEmpty() ? null : val);
        } else if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0 && valeur != null) {
            args[0] = Date.valueOf(String.valueOf(valeur));
        } else if (f.getType().getName().compareToIgnoreCase("double") == 0 && valeur instanceof String) {
            args[0] = new Double(Utilitaire.stringToDouble(String.valueOf(valeur)));
        } else if (f.getType().getName().compareToIgnoreCase("int") == 0 && valeur instanceof String) {
            args[0] = valeur == null || String.valueOf(valeur).compareToIgnoreCase("") == 0 ? new Integer(0) : new Integer(String.valueOf(valeur));
        } else if (f.getType().getName().compareToIgnoreCase("float") == 0 && valeur instanceof String) {
            args[0] = new Float(Utilitaire.stringToFloat(String.valueOf(valeur)));
        } else if (f.getType().getName().compareToIgnoreCase("java.sql.Timestamp") == 0 && valeur instanceof String) {
            args[0] = Utilitaire.convertStringToTimestampHour(String.valueOf(valeur), ":");
        }
        this.getClass().getMethod(nomMethode, paramT).invoke(this, args);
    }

    public void setValChamp(Field f, Object valeur) throws Exception {
        String nomMethode = "set" + Utilitaire.convertDebutMajuscule(f.getName());
        Object[] args = new Object[]{valeur};
        Class[] paramT = new Class[]{f.getType()};
        if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
            // empty if block
        }
        if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0 && valeur instanceof String) {
            args[0] = valeur != null && !((String)valeur).equalsIgnoreCase("null") ? Utilitaire.stringToDate("yyyy-MM-dd", String.valueOf(valeur)) : null;
        }
        if (f.getType().getName().compareToIgnoreCase("double") == 0 && valeur == null) {
            args[0] = new Double(0.0);
        }
        if (f.getType().getName().compareToIgnoreCase("double") == 0 && valeur instanceof String) {
            args[0] = valeur == null || String.valueOf(valeur).compareToIgnoreCase("") == 0 ? new Double(0.0) : new Double(Utilitaire.stringToDouble(String.valueOf(valeur)));
        }
        if (f.getType().getName().compareToIgnoreCase("int") == 0 && valeur == null) {
            args[0] = new Integer(0);
        }
        if (f.getType().getName().compareToIgnoreCase("int") == 0 && valeur instanceof String) {
            args[0] = valeur == null || String.valueOf(valeur).compareToIgnoreCase("") == 0 ? new Integer(0) : new Integer(String.valueOf(valeur));
        }
        if (f.getType().getName().compareToIgnoreCase("float") == 0 && valeur == null) {
            args[0] = new Float(0.0f);
        }
        if (f.getType().getName().compareToIgnoreCase("float") == 0 && valeur instanceof String) {
            args[0] = new Float(Utilitaire.stringToFloat(String.valueOf(valeur)));
        }
        if (f.getType().getName().compareToIgnoreCase("java.sql.Timestamp") == 0 && valeur instanceof String) {
            args[0] = Utilitaire.convertStringToTimestampHour(String.valueOf(valeur), ":");
        }
        this.getClass().getMethod(nomMethode, paramT).invoke(this, args);
    }

    public Field getFieldByName(String name) throws Exception {
        Field[] t = this.getFieldList();
        for (int i = 0; i < t.length; ++i) {
            if (t[i].getName().compareTo(name) != 0) continue;
            return t[i];
        }
        return null;
    }

    public Object getValField(Field f) {
        try {
            String nomMethode = "get" + Utilitaire.convertDebutMajuscule(f.getName());
            Object o = this.getClass().getMethod(nomMethode, null).invoke(this, null);
            return o;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getValInsert(String nomCol) {
        String o = null;
        try {
            String nomMethode = "get" + Utilitaire.convertDebutMajuscule(nomCol);
            if (this.getClass().getMethod(nomMethode, null).invoke(this, null) != null) {
                o = this.getClass().getMethod(nomMethode, null).invoke(this, null).toString();
            }
            return o;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Object getValeur(int numColonne) {
        try {
            Object o = this.getValField(this.getFieldList()[numColonne]);
            return o;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public void setNomTableSelect(String nomTableSelect) {
        this.nomTableSelect = nomTableSelect;
    }

    public String getNomTableSelect() {
        if (this.nomTableSelect == null || this.nomTableSelect == "") {
            return this.getNomTable();
        }
        return this.nomTableSelect;
    }

    public String getNomProcedureSequence() {
        return this.nomProcedureSequence;
    }

    public int getLonguerClePrimaire() {
        return this.longuerClePrimaire;
    }

    public String getINDICE_PK() {
        return this.INDICE_PK;
    }

    public void setINDICE_PK(String INDICE_PK) {
        this.INDICE_PK = INDICE_PK;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return this.mode;
    }

    public void setTYPE_CLASS_MAP_DOUBLE(int type) {
    }

    public void setTYPE_CLASS_MAP_FLOAT(int type) {
    }

    public void setTYPE_CLASS_MAP_BOOLEAN(int type) {
    }

    public void setTYPE_CLASS_MAP_BYTE(int type) {
    }

    public void setTYPE_CLASS_MAP_SHORT(int type) {
    }

    public void setTYPE_CLASS_MAP_INT(int type) {
    }

    public void setTYPE_CLASS_MAP_LONG(int type) {
    }

    public String getColumnlibelle() {
        return this.columnlibelle;
    }

    public void setColumnlibelle(String columnlibelle) {
        this.columnlibelle = columnlibelle;
    }

    public String getIdLibelle() {
        return this.getTuppleID();
    }

    public Vector getChamp() {
        return this.champ;
    }

    public void setChamp(Vector champ) {
        this.champ = champ;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean getEstHistorise() {
        return this.estHistorise;
    }

    public void setEstHistorise(boolean estHistorise) {
        this.estHistorise = estHistorise;
    }

    public String getNomSequenceDirecte() {
        return this.nomSequenceDirecte;
    }

    public void setNomSequenceDirecte(String nomSequenceDirecte) {
        this.nomSequenceDirecte = nomSequenceDirecte;
    }

    public void setNomSequenceDirecte(String nomSequenceDirecte, String indicePK) {
        this.setNomSequenceDirecte(nomSequenceDirecte);
        this.setIndicePk(indicePK);
    }

    public int getNombrepargroupe() {
        return this.nombrepargroupe;
    }

    public void setNombrepargroupe(int nombrepargroupe) {
        this.nombrepargroupe = nombrepargroupe;
    }

    public Class getCls() {
        return this.cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    public boolean getGroupee() {
        return this.groupee;
    }

    public void setGroupee(boolean groupee) {
        this.groupee = groupee;
    }

    public void controlerDelete(Connection c) throws Exception {
    }

    public String[] getMotCles() {
        String[] t = new String[]{this.getAttributIDName()};
        return t;
    }

    public String[] getValMotCles() {
        return this.getMotCles();
    }

    public String toJson() throws Exception {
        String retour = "{";
        Champ[] listeChamp = ListeColonneTable.getFromListe(this, null);
        for (int i = 0; i < listeChamp.length; ++i) {
            retour = retour + "\"" + listeChamp[i].getNomColonne() + "\":\"" + CGenUtil.getValeurFieldByMethod(this, listeChamp[i].getNomColonne()) + "\"";
            if (i >= listeChamp.length - 1) continue;
            retour = retour + ",";
        }
        retour = retour + "}";
        return retour;
    }

    public static String toJsonStandard(ClassMAPTable[] mapTables) throws Exception {
        StringBuilder retour = new StringBuilder("[");
        for (int i = 0; i < mapTables.length; ++i) {
            retour.append(mapTables[i].toJson());
            if (i >= mapTables.length - 1) continue;
            retour.append(",");
        }
        retour.append("]");
        return retour.toString();
    }

    public static String toJson(ClassMAPTable[] mapTables) throws Exception {
        StringBuilder retour = new StringBuilder("[");
        for (int i = 0; i < mapTables.length; ++i) {
            retour.append("\"").append(mapTables[i].toJson());
            if (i >= mapTables.length - 1) continue;
            retour.append(",");
        }
        retour.append("]");
        return retour.toString();
    }

    public void controleAnnule(Connection c) throws Exception {
    }

    public String getAttrMere() {
        return this.attrMere;
    }

    public void setAttrMere(String attrMere) {
        this.attrMere = attrMere;
    }

    public String getClassMere() {
        return this.classMere;
    }

    public void setClassMere(String classMere) {
        this.classMere = classMere;
    }

    public String getSQLinsert(Connection cDb) throws Exception {
        String ret = null;
        this.setMode("modif");
        String tableName = this.getNomTable();
        Field fld = null;
        if (this.getTuppleID() == null) {
            this.construirePK(cDb);
        }
        String sqlQry = "INSERT INTO " + tableName + " ( ";
        String sqlTmp = " ) VALUES ( ";
        if (this.getTuppleID() == null || this.getTuppleID().compareToIgnoreCase("") == 0 || this.getTuppleID().compareToIgnoreCase("null") == 0) {
            this.construirePK(cDb);
        }
        try {
            Field[] fieldlist = ListeColonneTable.getChampBase(this, cDb);
            for (int i = 0; i < fieldlist.length; ++i) {
                fld = fieldlist[i];
                sqlQry = sqlQry + fld.getName() + " ";
                switch (this.getTypeMAPField(fld)) {
                    case 0: {
                        sqlTmp = sqlTmp + (Boolean)this.getValField(fld) + " ";
                        break;
                    }
                    case 1: {
                        sqlTmp = sqlTmp + (Byte)this.getValField(fld) + " ";
                        break;
                    }
                    case 2: {
                        sqlTmp = sqlTmp + (Short)this.getValField(fld) + " ";
                        break;
                    }
                    case 3: {
                        Integer it = (Integer)this.getValField(fld);
                        sqlTmp = sqlTmp + it.toString() + " ";
                        break;
                    }
                    case 4: {
                        Long lo = (Long)this.getValField(fld);
                        sqlTmp = sqlTmp + lo.toString() + " ";
                        break;
                    }
                    case 5: {
                        sqlTmp = sqlTmp + ((Float)this.getValField(fld)).toString() + " ";
                        break;
                    }
                    case 6: {
                        sqlTmp = sqlTmp + ((Double)this.getValField(fld)).toString() + " ";
                        break;
                    }
                    case 10: {
                        sqlTmp = sqlTmp + "'" + ((String)this.getValField(fld) == null ? "" : (String)this.getValField(fld)) + "' ";
                        break;
                    }
                    case 21: {
                        sqlTmp = sqlTmp + "to_date('" + ((Date)this.getValField(fld)).toString() + "','yyyy-mm-dd') ";
                        break;
                    }
                    case 22: {
                        sqlTmp = sqlTmp + "to_date('" + ((Time)this.getValField(fld)).toString() + "','hh24-mi-ss') ";
                        break;
                    }
                    case 35: {
                        sqlTmp = sqlTmp + "to_timestamp('" + ((Timestamp)this.getValField(fld)).toString() + "','yyyy-mm-dd hh24:mi:ss.ff') ";
                    }
                }
                if (i + 1 >= fieldlist.length) continue;
                sqlQry = sqlQry + ", ";
                sqlTmp = sqlTmp + ", ";
            }
            ret = sqlQry = sqlQry + sqlTmp + " ) ";
        }
        catch (SQLException ex) {
            throw ex;
        }
        catch (Exception e) {
            try {
                cDb.rollback();
                e.printStackTrace();
                throw new ErreurDAO(String.valueOf(String.valueOf(new StringBuffer("Erreur insertiooooooooooooooooooon dans la table ").append(this.getNomTable()).append(" ").append(e.getMessage()).append(" ref= ").append(this.getTuppleID()))));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ret;
    }

    public void executeBatchDirecte(String[] req, Statement st, String table, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
                c.setAutoCommit(false);
            }
            String action = "";
            if (req.length != 0) {
                action = req[0].split(" ")[0];
            }
            for (int i = 0; i < req.length; ++i) {
                req[i].replace("'", "''");
                st.addBatch(req[i]);
                st.addBatch(this.sqlAenvoyer(action, req[i], table));
            }
            st.executeBatch();
            if (c != null && estOuvert) {
                c.commit();
            }
        }
        catch (Exception e) {
            if (c != null && estOuvert) {
                c.rollback();
            }
            throw e;
        }
        finally {
            if (c != null && estOuvert) {
                c.close();
            }
        }
    }

    public String sqlAenvoyer(String action, String requete, String table) {
        String sql = null;
        if (this.isSynchro()) {
            sql = "INSERT INTO REQUETE_A_ENVOYER (ID,ACTION,TABLECONCERNE,REQUETE,DATY,HEURE) VALUES ('RAE00'||getSeqRequeteAEnvoyer,'%s','%s','%s',%s,'%s')";
            sql = String.format(sql, action, table, requete.replace("'", "`"), "sysdate", Utilitaire.heureCouranteHMS());
        }
        return sql;
    }

    public int insertUpdateWithHisto(String refuser, String nomcolonne, Connection c) throws Exception {
        int retour = 0;
        boolean verif = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                c.setAutoCommit(false);
                verif = true;
            }
            ClassMAPTable obj = (ClassMAPTable)Class.forName(this.getClassName()).newInstance();
            obj.setNomTable(this.getNomTable());
            if (nomcolonne == null || nomcolonne.compareToIgnoreCase("") == 0) {
                nomcolonne = this.getAttributIDName();
            }
            Object valeurcol = CGenUtil.getValeurFieldByMethod(this, nomcolonne);
            ClassMAPTable[] ls = (ClassMAPTable[])CGenUtil.rechercher(obj, null, null, c, " and " + nomcolonne + "='" + valeurcol + "'");
            if (ls == null || ls.length == 0) {
                if (this.getTuppleID() == null || this.getTuppleID().compareToIgnoreCase("") == 0) {
                    this.construirePK(c);
                }
                retour = this.insertToTableWithHisto(refuser, c);
            } else {
                retour = this.updateToTableWithHisto(refuser, c);
            }
            if (verif) {
                c.commit();
            }
        }
        catch (Exception e) {
            if (c != null) {
                c.rollback();
            }
            e.printStackTrace();
            throw new Exception("Erreur insertion update dans la table " + this.getNomTable() + " " + e.getMessage() + " ref= " + this.getTuppleID());
        }
        finally {
            if (c != null && verif) {
                c.close();
            }
        }
        return retour;
    }

    public void testDelete(Connection c) throws Exception {
        try {
            if (this instanceof ClassEtat) {
                String id = this.getValInsert("id");
                if (Utilitaire.stringToInt(this.getValInsert("etat")) >= ConstanteEtat.getEtatValider()) {
                    throw new Exception("IMPOSSIBLE DE SUPPRIMER. OBJET DEJA VISER");
                }
            }
            if (this.getAttrMere() != null && this.getClassMere() != null) {
                ClassMAPTable crt = (ClassMAPTable)Class.forName(this.getClassMere()).newInstance();
                crt.setTuppleId(this.getValInsert(this.getAttrMere()));
                ClassMAPTable[] listeMere = (ClassMAPTable[])CGenUtil.rechercher(crt, null, null, c, "");
                if (listeMere.length > 0) {
                    listeMere[0].testDelete(c);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void controleVisa(Connection c) throws Exception {
    }

    public MapUtilisateur[] getAll(Connection c) throws Exception {
        boolean verif = false;
        try {
            MapUtilisateur[] utilisateurs;
            if (c == null) {
                c = new UtilDB().GetConn();
                verif = true;
            }
            MapUtilisateur[] mapUtilisateurArray = utilisateurs = (MapUtilisateur[])CGenUtil.rechercher((ClassMAPTable)new MapUtilisateur(), null, null, "");
            return mapUtilisateurArray;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (c != null && verif) {
                c.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getMaxColonneFactFinEntiteProforma(String daty, String entite) {
        UtilDB util = new UtilDB();
        Connection c = null;
        Statement cs = null;
        ResultSet rs = null;
        try {
            int l;
            String an = Utilitaire.getAnnee(daty);
            c = null;
            c = util.GetConn();
            cs = c.prepareStatement(String.valueOf(String.valueOf(new StringBuffer("select * from  seqFactProforma where entite='" + entite + "' and daty<='31/12/").append(an).append("' and daty>='01/01/").append(an).append("'"))));
            rs = cs.executeQuery();
            int i = 0;
            if (rs.next()) {
                ++i;
            }
            if (i == 0) {
                int k;
                int n = k = 0;
                return n;
            }
            int n = l = new Integer(rs.getString(1)).intValue();
            return n;
        }
        catch (SQLException an) {
            int j;
            int n = j = 0;
            return n;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (c != null) {
                    c.close();
                }
                util.close_connection();
            }
            catch (SQLException sQLException) {}
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getMaxColonneFactFinEntiteProforma(String daty, String entite, Connection c) {
        Statement cs = null;
        ResultSet rs = null;
        try {
            int l;
            String an = Utilitaire.getAnnee(daty);
            cs = c.prepareStatement(String.valueOf(String.valueOf(new StringBuffer("select * from  seqFactProforma where entite='" + entite + "' and daty<='31/12/").append(an).append("' and daty>='01/01/").append(an).append("'"))));
            rs = cs.executeQuery();
            int i = 0;
            if (rs.next()) {
                ++i;
            }
            if (i == 0) {
                int k;
                int n = k = 0;
                return n;
            }
            int n = l = new Integer(rs.getString(1)).intValue();
            return n;
        }
        catch (SQLException an) {
            int j;
            int n = j = 0;
            return n;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
            }
            catch (SQLException sQLException) {}
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getMaxColonneFactFinEntite(String daty, String entite, Connection c) {
        Statement cs = null;
        ResultSet rs = null;
        try {
            int l;
            String an = Utilitaire.getAnnee(daty);
            cs = c.prepareStatement(String.valueOf(String.valueOf(new StringBuffer("select * from  seqFact where entite='" + entite + "' and daty<='31/12/").append(an).append("' and daty>='01/01/").append(an).append("'"))));
            rs = cs.executeQuery();
            int i = 0;
            if (rs.next()) {
                ++i;
            }
            if (i == 0) {
                int k;
                int n = k = 0;
                return n;
            }
            int n = l = new Integer(rs.getString(1)).intValue();
            return n;
        }
        catch (SQLException an) {
            int j;
            int n = j = 0;
            return n;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
            }
            catch (SQLException sQLException) {}
        }
    }

    public String makePKCFinEntite(String daty, String entite, Connection c) {
        int maxSeq = ClassMAPTable.getMaxColonneFactFinEntite(daty, entite, c) + 1;
        String nombre = Utilitaire.completerInt(this.longuerClePrimaire, maxSeq);
        return String.valueOf(this.INDICE_PK) + String.valueOf(nombre);
    }

    public String makePKCFinEntiteProforma(String daty, String entite) {
        int maxSeq = ClassMAPTable.getMaxColonneFactFinEntiteProforma(daty, entite) + 1;
        String nombre = Utilitaire.completerInt(this.longuerClePrimaire, maxSeq);
        return String.valueOf(this.INDICE_PK) + String.valueOf(nombre);
    }

    public Historique_valeur[] getHistoriqueValeurs(String nomTable, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            Historique_valeur[] retour;
            if (c == null) {
                estOuvert = true;
                c = new UtilDB().GetConn();
            }
            Historique_valeur historiqueValeur = new Historique_valeur();
            historiqueValeur.setRefhisto(this.getTuppleID());
            historiqueValeur.setNomTable("HISTORIQUE_VALEUR_LIB");
            if (nomTable != null && nomTable.compareToIgnoreCase("") != 0) {
                historiqueValeur.setNomTable(nomTable);
            }
            Historique_valeur[] historique_valeurArray = retour = (Historique_valeur[])CGenUtil.rechercher(historiqueValeur, null, null, c, " ORDER BY dateHistoriquevaleur ASC");
            return historique_valeurArray;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (c != null && estOuvert) {
                c.close();
            }
        }
    }

    public Object[] restituerHistorique(String nomTable, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                estOuvert = true;
                c = new UtilDB().GetConn();
            }
            Historique_valeur[] historiqueValeurs = this.getHistoriqueValeurs(nomTable, c);
            ClassMAPTable[] retour = new ClassMAPTable[historiqueValeurs.length];
            for (int i = 0; i < historiqueValeurs.length; ++i) {
                retour[i] = historiqueValeurs[i].toClassMAPtable();
            }
            Object[] temp = (Object[])Array.newInstance(this.getClass(), retour.length);
            for (int i = 0; i < retour.length; ++i) {
                temp[i] = retour[i];
            }
            Object[] objectArray = temp;
            return objectArray;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (c != null && estOuvert) {
                c.close();
            }
        }
    }

    public void remplacerChampFormule(ClassMAPTable[] listeConfig) throws Exception {
        Field[] lc;
        for (Field f : lc = this.getFieldList()) {
            String valeur;
            String valIns = this.getValInsert(f.getName());
            if (valIns == null || !valIns.contains("[") || !valIns.contains("]") || (valeur = UtilitaireFormule.remplacerRubriqueParChiffre(this, valIns, listeConfig)) == null) continue;
            CGenUtil.setValChamp(this, f, valeur);
        }
    }

    public void changerDyn(ClassMAPTable o, String colonne, String newValeur, String oldValeur, Connection c) throws Exception {
        try {
            ClassMAPTable[] data = (ClassMAPTable[])CGenUtil.rechercherPrecis(o, null, null, c, "");
            Class<?> clazz = o.getClass();
            Field field = clazz.getDeclaredField(colonne);
            data[0].setNomTable(o.getNomTable());
            CGenUtil.setValChamp(data[0], field, newValeur);
            data[0].updateToTable(c);
        }
        catch (Exception e) {
            throw new Exception("Erreur lors de l'action : " + e.getMessage());
        }
    }

    public void changerDyn(ClassMAPTable o, String colonne, String newValeur, String oldValeur) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            this.changerDyn(o, colonne, newValeur, oldValeur, c);
        }
    }

    public void creerNotification(String u, Connection c) throws Exception {
        Notification notif = new Notification();
        notif.setMessage(this.getMessageNotif());
        MapUtilisateur[] users = (MapUtilisateur[])CGenUtil.rechercher(new MapUtilisateur(), null, null, c, this.getNotifAWhere() == null ? "" : this.getNotifAWhere());
        notif.creerNotificationMultiple(u, users, c);
    }

    public void fichierUploadInsert(String u, Connection c) {
        try {
            String fichierUploadInsertData = this.getFichierUploadInsertData();
            String fichierUploadInsertName = this.getFichierUploadInsertName();
            if (fichierUploadInsertData != null && !fichierUploadInsertData.trim().isEmpty()) {
                String path;
                String dossierUpload;
                File dirFile;
                String extension = "";
                int posPoint = fichierUploadInsertName.lastIndexOf(46);
                if (posPoint > 0) {
                    extension = fichierUploadInsertName.substring(posPoint + 1);
                }
                String nomFichierFinal = fichierUploadInsertName.substring(0, posPoint > 0 ? posPoint : fichierUploadInsertName.length()) + "-" + Utilitaire.heureCourante() + Utilitaire.dateDuJour() + "." + extension;
                nomFichierFinal = nomFichierFinal.replace(":", "").replace("/", "-").replace(" ", "");
                String baseDir = System.getProperty("jboss.server.base.dir");
                if (baseDir == null) {
                    baseDir = System.getProperty("catalina.base");
                }
                if (!(dirFile = new File(dossierUpload = (path = baseDir + UtilDB.properties.getProperty("apj.upload.path")) + File.separator + "async" + File.separator + this.getNomTable())).exists()) {
                    dirFile.mkdirs();
                }
                String cheminComplet = dossierUpload + File.separator + nomFichierFinal;
                byte[] contenuFichier = Base64.getDecoder().decode(fichierUploadInsertData);
                FileOutputStream fos = new FileOutputStream(cheminComplet);
                fos.write(contenuFichier);
                fos.close();
                UploadPj fichier = new UploadPj("ATTACHER_FICHIER", "GETSEQ_ATTACHER_FICHIER", "FLE", "", nomFichierFinal, this.getTuppleID());
                fichier.construirePK(c);
                fichier.insertToTableWithHisto(u, c);
            }
        }
        catch (Exception e) {
            System.err.println("Erreur lors de l\u2019enregistrement du fichier upload\u00e9 : " + e.getMessage());
        }
    }
}

