/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  javax.servlet.http.HttpServletRequest
 *  user.UserEJB
 */
package affichage;

import affichage.Champ;
import affichage.Liste;
import affichage.ListeChamp;
import affichage.PageRecherche;
import bean.AnalyseLien;
import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import com.google.gson.Gson;
import constante.ConstanteAffichage;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import user.UserEJB;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class Formulaire {
    private Champ[] listeChamp;
    private Champ[][] listeChampTableau;
    private String id;
    private int nbRangee = 2;
    private String html;
    private String htmlNew;
    private String tailleTableau = "100%";
    private String cssTableau = "monographe";
    private String cssClass;
    private ClassMAPTable objet;
    private Champ[] crtFormu;
    private int nbIntervalle = 0;
    private String htmlTri;
    private String htmlButton;
    private String htmlInsert;
    private String htmlAutoComplete;
    private Champ[] champGroupe;
    private Champ[] champTableauAff;
    private String htmlGroupe;
    private String htmlTableauAff;
    private String htmlTableauAffNew;
    private String htmlTableauInsert;
    private int nbLigne = 0;
    String recapcheck = "";
    public String lang = "fr";
    boolean estFille = false;
    String[] colOrdre = null;
    private ClassMAPTable[] dataFille;
    private AnalyseLien[] liens;
    private String currentEtat;
    private String[] etatAff;
    private String[] etatVal;
    String classeFille;
    String pageActuelle;
    boolean checkAuto;
    int nbrCheck = 0;
    String checkBoxName = "ids";
    HttpServletRequest reqqq;
    boolean groupee = false;
    private String titre = "Saisie";
    int nbColonne = 2;
    int nbChemin = 0;
    HashMap<String, String> valeurs = new HashMap();
    private LinkedHashMap<String, String[]> rubrique;
    String anotherButton = "";
    private String htmlChampCacheSupplementaire = "";
    String[] ordre;
    private String apres;
    private String rajoutLien;

    public boolean isGroupee() {
        return this.groupee;
    }

    public void setGroupee(boolean groupee) {
        this.groupee = groupee;
    }

    public HttpServletRequest getReqqq() {
        return this.reqqq;
    }

    public void setReqqq(HttpServletRequest reqqq) {
        this.reqqq = reqqq;
    }

    public String getHtmlChampCacheSupplementaire() {
        return this.htmlChampCacheSupplementaire;
    }

    public void setHtmlChampCacheSupplementaire(String htmlChampCacheSupplementaire) {
        this.htmlChampCacheSupplementaire = htmlChampCacheSupplementaire == null ? "" : htmlChampCacheSupplementaire;
    }

    public String getAnotherButton() {
        return this.anotherButton;
    }

    public void setAnotherButton(String anotherButton) {
        this.anotherButton = anotherButton;
    }

    public HashMap<String, String> getValeurs() {
        return this.valeurs;
    }

    public void setValeurs(HashMap<String, String> valeurs) {
        this.valeurs = valeurs;
    }

    public String getNomChampUnic(String libelle) {
        if (libelle == null) {
            return null;
        }
        String trouve = null;
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            Champ c = this.getListeChamp()[i];
            if (c.getLibelleAfficheAnnotation() == null || !c.getLibelleAfficheAnnotation().equals(libelle) || c.getValeur().equals("") || c.getValeur().equals("%") || c.getNom().compareToIgnoreCase("colonne") <= 0 || c.getNom().equals("ordre")) continue;
            if (trouve != null) {
                return null;
            }
            trouve = c.getNom();
        }
        return trouve;
    }

    public LinkedHashMap<String, String[]> getRubrique() {
        return this.rubrique;
    }

    public void setRubrique(LinkedHashMap<String, String[]> rubrique) {
        this.rubrique = rubrique;
    }

    public boolean isCheckAuto() {
        return this.checkAuto;
    }

    public void setCheckAuto(boolean checkAuto) {
        this.checkAuto = checkAuto;
    }

    public void setCheckAuto(boolean checkAuto, int nbrLigne) {
        this.checkAuto = checkAuto;
        this.setNbrCheck(nbrLigne);
    }

    public int getNbrCheck() {
        return this.nbrCheck;
    }

    public void setNbrCheck(int nbrCheck) {
        this.nbrCheck = nbrCheck;
    }

    public String getClasseFille() {
        return this.classeFille;
    }

    public void setClasseFille(String classeFille) {
        this.classeFille = classeFille;
    }

    public String getPageActuelle() {
        return this.pageActuelle;
    }

    public void setPageActuelle(String pageActuelle) {
        this.pageActuelle = pageActuelle;
    }

    public String getCheckBoxName() {
        return this.checkBoxName;
    }

    public void setCheckBoxName(String checkBoxName) {
        this.checkBoxName = checkBoxName;
    }

    public int getNbChemin() {
        return this.nbChemin;
    }

    public void setNbChemin(int nbChemin) {
        this.nbChemin = nbChemin;
    }

    public int getNbColonne() {
        return this.nbColonne;
    }

    public void setNbColonne(int nbColonne) {
        this.nbColonne = nbColonne;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCurrentEtat() {
        return this.currentEtat;
    }

    public void setCurrentEtat(String currentEtat) {
        this.currentEtat = currentEtat;
    }

    public String[] getEtatAff() {
        return this.etatAff;
    }

    public void setEtatAff(String[] etatAff) {
        this.etatAff = etatAff;
    }

    public String[] getEtatVal() {
        return this.etatVal;
    }

    public void setEtatVal(String[] etatVal) {
        this.etatVal = etatVal;
    }

    public AnalyseLien[] getLiens() {
        return this.liens;
    }

    public void setLiens(AnalyseLien[] liens) {
        this.liens = liens;
    }

    public ClassMAPTable[] getDataFille() {
        return this.dataFille;
    }

    public void setDataFille(ClassMAPTable[] dataFille) {
        this.dataFille = dataFille;
    }

    public String[] getColOrdre() {
        return this.colOrdre;
    }

    public void setColOrdre(String[] colOrdre) {
        this.colOrdre = colOrdre;
    }

    public boolean isEstFille() {
        return this.estFille;
    }

    public void setEstFille(boolean estFille) {
        this.estFille = estFille;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String[] getOrdre() {
        return this.ordre;
    }

    public String getHtmlNew() throws Exception {
        if (this.htmlNew == null || this.htmlNew.compareToIgnoreCase("") == 0) {
            this.makeHtmlNew();
        }
        return this.htmlNew + this.getHtmlChampCacheSupplementaire();
    }

    public void setHtmlNew(String htmlNew) {
        this.htmlNew = htmlNew;
    }

    public String getHtmlTableauAffNew() {
        return this.htmlTableauAffNew;
    }

    public void setHtmlTableauAffNew(String htmlTableauAffNew) {
        this.htmlTableauAffNew = htmlTableauAffNew;
    }

    public ListeChamp getChampMulitple(String nom) throws Exception {
        ArrayList<Champ> lc = new ArrayList<Champ>();
        for (Champ ch : this.getListeChamp()) {
            String[] split = Utilitaire.split(ch.getNom(), "_");
            String gauche = "";
            for (int i = 0; i < split.length - 1; ++i) {
                gauche = gauche + split[i];
            }
            if (split.length < 2 || gauche.compareToIgnoreCase(nom) != 0) continue;
            lc.add(ch);
        }
        ListeChamp lca = new ListeChamp(this, lc);
        return lca;
    }

    public void setOrdre(String[] ordre) {
        this.ordre = ordre;
        Champ[] listeVaovao = new Champ[this.getListeChamp().length];
        int iVao = 0;
        block0: for (int i = 0; i < ordre.length; ++i) {
            for (int iListe = 0; iListe < this.listeChamp.length; ++iListe) {
                if (!this.listeChamp[iListe].getNom().equalsIgnoreCase(ordre[i])) continue;
                listeVaovao[iVao++] = this.listeChamp[iListe];
                continue block0;
            }
        }
        for (int iListe = 0; iListe < this.listeChamp.length; ++iListe) {
            boolean dejaAjoute = false;
            for (int j = 0; j < iVao; ++j) {
                if (listeVaovao[j] != this.listeChamp[iListe]) continue;
                dejaAjoute = true;
                break;
            }
            if (dejaAjoute) continue;
            listeVaovao[iVao++] = this.listeChamp[iListe];
        }
        this.setListeChamp(listeVaovao);
    }

    public void insererChamp(String nom, int rang) {
        ArrayList<Champ> liste = new ArrayList<Champ>();
        liste.addAll(Arrays.asList(this.listeChamp));
        int i = 1;
        for (Champ c : liste) {
            if (c.getNom().compareToIgnoreCase(nom) == 0) {
                liste.add(rang, c);
                liste.remove(i);
                break;
            }
            ++i;
        }
        liste.toArray(this.listeChamp);
    }

    public String getHtmlAutoComplete() {
        return this.htmlAutoComplete;
    }

    public void setHtmlAutoComplete(String htmlAutoComplete) {
        this.htmlAutoComplete = htmlAutoComplete;
    }

    public String getRecapcheck() {
        return this.recapcheck;
    }

    public void setRecapcheck(String recapcheck) {
        this.recapcheck = recapcheck;
    }

    public int getNbLigne() {
        return this.nbLigne;
    }

    public void setNbLigne(int nbLigne) {
        this.nbLigne = nbLigne;
    }

    public Formulaire() {
    }

    public static Champ[] transformerEnChamp(String[] list) {
        Champ[] ret = new Champ[list.length];
        for (int i = 0; i < list.length; ++i) {
            ret[i] = new Champ(list[i]);
        }
        return ret;
    }

    public void mergeCritereFormu(Champ[] list) throws Exception {
        System.out.println("Changer en champs");
        int nombre = list.length;
        for (int i = 0; i < nombre; ++i) {
            for (int j = 0; j < this.crtFormu.length; ++j) {
                boolean condition;
                boolean bl = condition = this.crtFormu[j].getNom().compareToIgnoreCase(list[i].getNom()) == 0;
                if (!condition) continue;
                this.crtFormu[j].setEstMultiple(list[i].getEstMultiple());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void changerEnChamp(Champ[] list) throws Exception {
        int nombre = list.length;
        boolean chargerAutocomplete = false;
        for (Champ champ : list) {
            if (champ == null || !champ.isAutocompleteDynamique()) continue;
            chargerAutocomplete = true;
            break;
        }
        try (Connection connexion = null;){
            if (chargerAutocomplete) {
                connexion = new UtilDB().GetConn();
            }
            block4: for (int i = 0; i < nombre; ++i) {
                for (int j = 0; j < this.listeChamp.length; ++j) {
                    Champ ancien;
                    boolean condition;
                    boolean bl = condition = this.listeChamp[j].getNom().compareToIgnoreCase(list[i].getNom()) == 0;
                    if (this.isEstFille()) {
                        condition = this.listeChamp[j].getNom().startsWith(list[i].getNom() + "_");
                    }
                    String valeurChamp = this.listeChamp[j].getValeur();
                    if (!condition) continue;
                    list[i].setValeur(this.listeChamp[j].getValeur());
                    if (list[i].getNom().compareToIgnoreCase("mois2") == 0) {
                        ((Liste)list[i]).setDefaultSelected("12");
                    }
                    if (!this.isEstFille()) {
                        ancien = this.listeChamp[j];
                        this.listeChamp[j] = list[i];
                        if (connexion != null) {
                            this.listeChamp[j].findAutocompleteDisplayValue(connexion);
                        }
                        if (this.listeChamp[j].getAttribut() == null) {
                            this.listeChamp[j].setAttribut(ancien.getAttribut());
                        }
                        if (this.getCrtFormu() == null) continue block4;
                        for (int k = 0; k < this.getCrtFormu().length; ++k) {
                            if (this.getCrtFormu()[k].getNom().compareToIgnoreCase(list[i].getNom()) != 0) continue;
                            this.getCrtFormu()[k] = list[i];
                        }
                        continue block4;
                    }
                    if (list[i] instanceof Liste) {
                        ancien = this.listeChamp[j];
                        Liste lis = (Liste)list[i];
                        if (lis.getValeurBrute() != null && lis.getValeurBrute().length > 0) {
                            this.listeChamp[j] = new Liste(this.listeChamp[j].getNom(), lis.getValeurBrute(), lis.getColValeurBrute());
                        } else {
                            this.listeChamp[j] = new Liste(this.listeChamp[j].getNom(), lis.getSingleton(), lis.getColAffiche(), lis.getColValeur(), lis.getApresW());
                            this.listeChamp[j].setValeur(valeurChamp);
                        }
                        if (lis.isAutocompleteDynamique()) {
                            this.listeChamp[j].setAutocompleteDynamique(lis.getAc_classeMapping(), lis.getAc_affiche(), lis.getAc_valeur(), lis.getAc_nomTable());
                            this.listeChamp[j].setUseMotCle(lis.isUseMotCle());
                            this.listeChamp[j].setAc_aWhere(lis.getAc_aWhere());
                            this.listeChamp[j].setChampRetour(lis.getChampRetour());
                            this.listeChamp[j].setChampRetourMapping(lis.getChampRetourMapping());
                            this.listeChamp[j].setPropre(lis.isPropre());
                        }
                        if (connexion != null) {
                            this.listeChamp[j].findAutocompleteDisplayValue(connexion);
                        }
                        this.listeChamp[j].setAutre(lis.getAutre());
                        this.listeChamp[j].setEstMultiple(list[i].getEstMultiple());
                        this.listeChamp[j].setEstVide(lis.isEstVide());
                        this.listeChamp[j].setAttribut(ancien.getAttribut());
                        continue;
                    }
                    ancien = this.listeChamp[j];
                    String ancienNom = this.listeChamp[j].getNom();
                    this.listeChamp[j] = list[i];
                    if (this.listeChamp[j].getAttribut() == null) {
                        this.listeChamp[j].setAttribut(ancien.getAttribut());
                    }
                    if (!this.estFille) continue;
                    this.listeChamp[j].setNom(ancienNom);
                }
            }
        }
    }

    public Formulaire(Champ[] listeC, int range) {
        this.setListeChamp(listeC);
        this.setNbRangee(range);
    }

    public Formulaire(String[] listeC, int range) {
        this.setListeChamp(Formulaire.transformerEnChamp(listeC));
        this.setNbRangee(range);
    }

    public String makeHtmlForm() throws Exception {
        String retour = "";
        return retour;
    }

    public void makeHtmlTri() throws Exception {
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(this.lang));
        String temp = "";
        temp = temp + "<div class='triecolonne'>";
        temp = temp + "<h3 class='box-title h520pxSemibold m-0' color='#103a8e'><span color='#103a8e' multi-lang>" + RB.getString("tri") + "</span></h3>";
        temp = temp + "<div class='input-container'>";
        temp = temp + "<div class='form-input'>";
        temp = temp + "<label for='Colonne' multi-lang>Colonne</label>";
        temp = temp + this.getListeChamp()[this.getListeChamp().length - 2].getHtml();
        temp = temp + "</div>";
        temp = temp + "<div class='form-input'>";
        temp = temp + "<label for='Ordre' multi-lang>Ordre</label>";
        temp = temp + this.getListeChamp()[this.getListeChamp().length - 1].getHtml();
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        this.setHtmlTri(temp);
    }

    public String makeHtmlButton() {
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(this.lang));
        String temp = "";
        temp = temp + "<div class='col-xs-4' align='center'>";
        temp = temp + "<button type='reset' class='btn btn-default'>" + RB.getString("reinitialiser") + "</button>";
        temp = temp + "</div>";
        temp = temp + "<div class='col-xs-4' align='center'>";
        temp = temp + "<input name='afficher' value='" + RB.getString("afficher") + "' type='submit' class='btn' style='background:" + ConstanteAffichage.couleurPrimaire + "; color:white' id='btnListe'/>";
        temp = temp + "</div>";
        return temp;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setDefaut(ClassMAPTable def) throws Exception {
        Champ[] listeChamp = this.getListeChamp();
        try (Connection connexion = null;){
            for (Champ c : listeChamp) {
                if (!c.isAutocompleteDynamique()) continue;
                connexion = new UtilDB().GetConn();
                break;
            }
            for (Champ c : listeChamp) {
                try {
                    c.setDefaut(CGenUtil.getValeurInsert(def, c.getNom()));
                    if (connexion == null) continue;
                    c.findAutocompleteDisplayValue(connexion);
                }
                catch (Exception e) {
                    // empty catch block
                }
            }
        }
    }

    public Champ[] getListeIntervalle() {
        Vector<Champ> ret = new Vector<Champ>();
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            if (this.getListeChamp()[i].getEstIntervalle() != 1) continue;
            ret.add(this.getListeChamp()[i]);
        }
        Object[] retour = new Champ[ret.size()];
        ret.copyInto(retour);
        return retour;
    }

    public String getHtmlEnsemble() throws Exception {
        return this.getHtmlEnsembleTemplate();
    }

    public String getHtmlTemplate() throws Exception {
        if (this.html == null || this.html.compareToIgnoreCase("") == 0) {
            this.makeHtmlTemplate();
        }
        return this.html + this.getHtmlChampCacheSupplementaire();
    }

    public void ajouterChampAttacherFichier() {
        this.ajouterChamp(new String[]{"fichierUploadInsert", "fichierUploadInsertName", "fichierUploadInsertData"});
        this.getChamp("fichierUploadInsert").setLibelle("Attacher Fichier");
        this.getChamp("fichierUploadInsert").setType("file");
        this.getChamp("fichierUploadInsertName").setVisible(false);
        this.getChamp("fichierUploadInsertData").setVisible(false);
    }

    public void ajouterChamp(String[] champs) {
        Champ[] champSup = new Champ[champs.length];
        for (int i = 0; i < champs.length; ++i) {
            champSup[i] = new Champ(champs[i]);
        }
        Champ[] ancienListeChamp = this.getListeChamp();
        int taille = ancienListeChamp.length;
        int nbAvantDerniers = taille - 2;
        Champ[] listeChampNouveau = new Champ[taille + champSup.length];
        System.arraycopy(ancienListeChamp, 0, listeChampNouveau, 0, nbAvantDerniers);
        System.arraycopy(champSup, 0, listeChampNouveau, nbAvantDerniers, champSup.length);
        System.arraycopy(ancienListeChamp, nbAvantDerniers, listeChampNouveau, nbAvantDerniers + champSup.length, 2);
        this.setListeChamp(listeChampNouveau);
    }

    public void makeHtmlTemplate() throws Exception {
        this.updateEstMoitier();
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(this.lang));
        String temp = "";
        temp = temp + "<div class=\"modal fade\" id=\"filtre-modal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"filtre-modal\" aria-hidden=\"true\">\n  <div class=\"modal-dialog clavier\" role=\"document\">\n    <div class=\"modal-content\">\n       <div class=\"modal-body\">";
        temp = temp + "<div class='box cardsearch' id='pagerecherche'>";
        temp = temp + "<div class='box-body'>";
        temp = temp + "<h3 class=\"h520pxSemibold m-0\" multi-lang=\"\">Filtre</h3>";
        temp = temp + "<div class='input-container'>";
        for (int j = 0; j < this.getListeChamp().length; ++j) {
            Champ c = this.listeChamp[j];
            String nomLower = c.getNom().toLowerCase();
            if (c.getVisible()) {
                if (!(c.getValeur().equals("") || c.getValeur().equals("%") || c.getNom().compareToIgnoreCase("colonne") <= 0 || c.getNom().equals("ordre"))) {
                    if (this.getValeurs() == null) {
                        this.setValeurs(new HashMap<String, String>());
                    }
                    String valeurAffiche = c.getValeur();
                    if (c.getEstMultiple() && valeurAffiche != null) {
                        valeurAffiche = valeurAffiche.replace("','", ", ");
                    }
                    this.getValeurs().put(c.getLibelleAfficheAnnotation(), valeurAffiche);
                }
                String classCssInputContainer = c.getEstMoitier() ? "form-input w-25" : "form-input";
                temp = temp + "<div class='" + classCssInputContainer + "'>";
                c.setAutre(c.getAutre() + " onkeydown='return searchKeyPress(event)'");
                String troisiemeTd = "";
                if (c.getPageAppel() != null && c.getPageAppel().compareToIgnoreCase("") != 0) {
                    String autre = "";
                    if (c.getColFiltre() != null && c.getValFiltre() != null) {
                        for (int i = 0; i < c.getColFiltre().length; ++i) {
                            autre = autre + "&" + c.getColFiltre()[i] + "=" + c.getValFiltre()[i];
                        }
                    }
                    temp = temp + "<div class='d-flex gp-2 col-md-12 nopadding'>";
                    temp = temp + "<span class='input-span'>" + this.getListeChamp()[j].getInputHidden() + "</span>";
                    troisiemeTd = "<input name='choix' type='button' class='submit btnheight' onclick=pagePopUp('" + c.getPageAppel() + "?champReturn=" + c.getChampReturn() + "&apresLienPageAppel=" + c.getApresLienPageappel() + autre + "') value=... />";
                    temp = temp + "<span style='text-align:right;' class=''>" + troisiemeTd + "</span>";
                    temp = temp + "</div>";
                }
                temp = temp + "<label class=' input-label' for=" + c.getLibelleAfficheAnnotation() + " multi-lang>" + c.getLibelleAfficheAnnotation() + "</label>";
                temp = temp + c.getHtml();
                temp = temp + troisiemeTd;
                temp = temp + "</div>";
                continue;
            }
            temp = temp + c.getHtml();
        }
        temp = temp + "</div>";
        temp = temp + "</div><div class='box-footer borderless'>";
        temp = temp + "<input name='afficher' multi-lang value='Filtrer' style='min-width:92px' type='submit' class='btn btn-primary pull-right' id='btnListe'/>";
        temp = temp + "<button class='btn btn-secondary pull-right' style='margin-right:8px' type=\"reset\">R&eacute;initialiser</button>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<button class='btn-exit' data-dismiss=\"modal\" type='button'><i class='fa fa-close'></i></button>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<button class=\"btn-secondary btn-bg-white btn btn-small btn-filtre\" data-toggle=\"modal\" data-target=\"#filtre-modal\" type=\"button\" ><i class='material-symbols-rounded'>filter_alt</i> Filtre <span class='filtre-nb' >" + this.getValeurs().size() + "</span></button>";
        temp = temp + this.getHtmlAutocomplete();
        this.setHtml(temp);
    }

    public void makeHtmlTableauAffTemplate() throws Exception {
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(this.lang));
        if (this.getChampTableauAff() == null) {
            this.setHtmlTableauAff("");
            return;
        }
        String temp = "";
        temp = temp + "<div class=\"modal fade\" id=\"filtreColonnne\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"filtreColonnneTitle\" aria-hidden=\"true\">\r\n  <div style='width:480px;' class=\"modal-dialog modal-dialog-centered\" role=\"document\">\r\n    <div style=\"border-radius: 16px;padding:15px;\" class=\"modal-content\">\r\n      <div style=\"padding: 0 30px 0 30px;\" class=\"modal-body\">\r\n      <h3 class='filtretitle h520pxSemibold '>Choix des colonnes &agrave; afficher</h3>\r\n";
        temp = temp + "<div class='box-body row' id='pagerecherche'>";
        int reste = this.getChampTableauAff().length % this.getNbRangee();
        int nombreLigne = 0;
        this.setNbRangee(1);
        nombreLigne = reste != 0 ? this.getChampTableauAff().length / this.getNbRangee() + 1 : this.getChampTableauAff().length / this.getNbRangee();
        for (int i = 0; i < nombreLigne; ++i) {
            temp = temp + "<div class='col-md-6'>";
            temp = temp + "<div class='form-group'>";
            for (int j = 0; j < this.getNbRangee() && i * this.getNbRangee() + j != this.getChampTableauAff().length; ++j) {
                Champ c = this.getChampTableauAff()[i * this.getNbRangee() + j];
                temp = temp + "<label class=' labelinput' for=" + c.getNom() + ">" + c.getLibelleAfficheAnnotation() + "</label>";
                temp = temp + c.getHtml();
            }
            temp = temp + "</div>";
            temp = temp + "</div>";
        }
        temp = temp + "</div>";
        temp = temp + "<div class=\"row\" style=\"padding: 15px;\">\r\n";
        temp = temp + "<input name='afficher' value='" + RB.getString("afficher") + "' type='submit' class='btn btnradius' style='background:" + ConstanteAffichage.couleurPrimaireTemplate + "; color:white;width:100%;height: 30px;padding: 0;' id='btnListe'/>";
        temp = temp + "          </div>";
        temp = temp + "</div>\r\n    </div>\r\n  </div>\r\n</div>";
        this.setHtmlTableauAff(temp);
    }

    public void makeHtmlGroupeTemplate() throws Exception {
        if (this.getChampGroupe() == null) {
            this.setHtmlGroupe("");
            return;
        }
        String temp = "";
        temp = temp + "<div class=\"modal fade\" id=\"exampleModalCenter\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalCenterTitle\" aria-hidden=\"true\">\r\n  <div class=\"modal-dialog modal-dialog-centered\" role=\"document\">\r\n    <div class=\"modal-content\">\r\n      <div class=\"modal-body row\">\r\n      <h3 class='filtretitle h520pxSemibold ' multi-lang>Choix des colonnes \u00c3\u00a0 afficher</h3>\r\n";
        temp = temp + "<div class='box-body' id='pagerecherche'>";
        int reste = this.getChampGroupe().length % this.getNbRangee();
        int nombreLigne = 0;
        nombreLigne = reste != 0 ? this.getChampGroupe().length / this.getNbRangee() + 1 : this.getChampGroupe().length / this.getNbRangee();
        for (int i = 0; i < nombreLigne; ++i) {
            temp = temp + "<div class='col-md-4'>";
            temp = temp + "<div class='form-group'>";
            for (int j = 0; j < this.getNbRangee() && i * this.getNbRangee() + j != this.getChampGroupe().length; ++j) {
                Champ c = this.getChampGroupe()[i * this.getNbRangee() + j];
                temp = temp + "<label for=" + c.getNom() + ">" + c.getLibelleAfficheAnnotation() + "</label>";
                temp = temp + c.getHtml();
            }
            temp = temp + "</div>";
            temp = temp + "</div>";
        }
        temp = temp + "</div>";
        temp = temp + "</div>\r\n    </div>\r\n  </div>\r\n</div>";
        this.setHtmlGroupe(temp);
    }

    public String getHtmlFiltre() throws Exception {
        String temp = "";
        temp = temp + "<div class='row m-0'>";
        temp = temp + "<div class='col-md-12 nopadding'>";
        temp = temp + this.getHtmlTemplate();
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        return temp;
    }

    public String getHtmlEnsembleTemplate() throws Exception {
        String temp = "";
        temp = temp + "<div class='d-flex gap-2 w-100' style='justify-content:space-between;flex-wrap:wrap'>";
        temp = temp + "<div class='d-flex'>";
        temp = temp + this.getHtmlTemplate();
        this.makeHtmlGroupe();
        temp = temp + this.getHtmlGroupe();
        this.makeHtmlTableauAff();
        temp = temp + this.getHtmlTableauAff();
        temp = temp + "</div>";
        temp = temp + "<div class='d-flex gap-2' style='flex-wrap:wrap' >";
        temp = this.isGroupee() ? temp + "<button class=\"btn-secondary btn-bg-white btn btn-small filtre-btn \" data-toggle=\"modal\" data-target=\"#exporter\" type=\"button\" style='display:none;'><i class='material-symbols-rounded'>file_export</i> Exporter</button>" : temp + "<button class=\"btn-secondary btn-bg-white btn btn-small filtre-btn \" data-toggle=\"modal\" data-target=\"#exporter\" type=\"button\" ><i class='material-symbols-rounded'>file_export</i> Exporter</button>";
        temp = temp + this.getAnotherButton();
        temp = temp + "</div>";
        temp = temp + "</div>";
        if (this.getValeurs() != null && this.getValeurs().size() > 0) {
            temp = temp + "<div class='value-container d-flex'>";
            for (Map.Entry<String, String> valeur : this.getValeurs().entrySet()) {
                String libelle = valeur.getKey();
                String nomChamp = this.getNomChampUnic(libelle);
                temp = temp + "<div style='align-items:center' class='form-value'>";
                temp = temp + "<strong>" + libelle + "</strong>: " + valeur.getValue();
                if (nomChamp != null) {
                    temp = temp + "<i class='material-symbols-rounded form-value-cancel'  onclick=\"removeCriteres('" + nomChamp + "')\" title=\"Retirer ce crit\u00e8re\" style='font-size:20px;cursor:pointer;align-tems:center' >cancel</i>";
                }
                temp = temp + "</div>";
            }
            temp = temp + "</div>";
            temp = temp + "<script>\r\nfunction removeCriteres(nom){\r\n    if(!nom){ return; }\r\n    var champs = document.querySelectorAll('[name=\"'+nom+'\"]');\r\n    for(var i=0;i<champs.length;i++){\r\n        if(champs[i].type === 'checkbox' || champs[i].type === 'radio'){ champs[i].checked = false; }\r\n        else { champs[i].value = ''; }\r\n    }\r\n    var libelleEls = document.querySelectorAll('[name=\"'+nom+'libelle\"]');\r\n    for(var j=0;j<libelleEls.length;j++){ libelleEls[j].value = ''; }\r\n    var input = document.getElementById(nom);\r\n    var form = input ? input.closest('form') : document.querySelector('form');\r\n    if(form){ form.submit(); }\r\n}\r\n</script>";
        }
        temp = temp + this.getRechercheMotsClesss();
        return temp;
    }

    public String getHtmlEnsembleNew() throws Exception {
        String temp = "";
        temp = temp + "<div class='row m-0'>";
        temp = temp + "<div class=\"filter p-3 mt-3 mb-3\">";
        temp = temp + this.getHtmlNew();
        this.makeHtmlGroupe();
        temp = temp + this.getHtmlGroupe();
        this.makeHtmlTableauAffNew();
        temp = temp + this.getHtmlTableauAffNew();
        temp = temp + "</div>";
        temp = temp + "</div>";
        return temp;
    }

    public String getHtmlSimple() throws Exception {
        String temp = "";
        temp = temp + "<div class='row'>";
        temp = temp + "<div class='row col-md-12'>";
        temp = temp + this.getHtml();
        temp = temp + "<div class='box-footer'>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<div class='row col-md-12'>";
        temp = temp + this.makeHtmlButton();
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        return temp;
    }

    public Champ getChamp(String nomChamp) {
        if (this.getListeChamp() == null) {
            return null;
        }
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            if (this.getListeChamp()[i] == null || this.getListeChamp()[i].getNom().compareToIgnoreCase(nomChamp) != 0) continue;
            return this.getListeChamp()[i];
        }
        return null;
    }

    public void updateEstMoitier() {
        if (this.getCrtFormu() == null || this.getListeChamp() == null) {
            return;
        }
        for (Champ c : this.getCrtFormu()) {
            if (c == null || c.getEstIntervalle() != 1) continue;
            String t1 = c.getNom() + "1";
            String t2 = c.getNom() + "2";
            Champ champ1 = this.getChamp(t1);
            Champ champ2 = this.getChamp(t2);
            if (champ1 == null || champ2 == null) continue;
            if (champ1.getVisible() && champ2.getVisible()) {
                champ1.setEstMoitier(true);
                champ2.setEstMoitier(true);
                continue;
            }
            champ1.setEstMoitier(false);
            champ2.setEstMoitier(false);
        }
    }

    public void changerLibelle(String[] libelle) throws Exception {
        if (libelle.length != this.getListeChamp().length) {
            throw new Exception("nombre de champ different de linitial");
        }
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            this.getListeChamp()[i].setLibelle(libelle[i]);
        }
    }

    public void makeChampFormu(String[] crt, String[] listeInterval, int nbRange) throws Exception {
        this.setCrtFormu(Formulaire.transformerEnChamp(crt));
        Vector<Champ> liste = new Vector<Champ>();
        for (int i = 0; i < crt.length; ++i) {
            String type = ListeColonneTable.getChamp(this.getObjet(), crt[i]).getTypeColonne();
            if (Utilitaire.estIlDedans(crt[i], listeInterval) != -1) {
                this.getCrtFormu()[i].setEstIntervalle(1);
                this.setNbIntervalle(this.getNbIntervalle() + 1);
                String t1 = crt[i] + "1";
                String t2 = crt[i] + "2";
                Champ champ1 = new Champ(t1);
                Champ champ2 = new Champ(t2);
                if (type.compareToIgnoreCase("Date") == 0) {
                    this.getCrtFormu()[i].setType(ConstanteAffichage.typeDaty);
                    champ1.setType(ConstanteAffichage.typeDaty);
                    champ1.setTypeData(ConstanteAffichage.typeDaty);
                    champ2.setType(ConstanteAffichage.typeDaty);
                    champ2.setTypeData(ConstanteAffichage.typeDaty);
                    champ1.setType("text");
                    champ2.setType("text");
                }
                liste.add(champ1);
                liste.add(champ2);
                continue;
            }
            liste.add(new Champ(crt[i]));
        }
        Object[] nomCol = ListeColonneTable.getFromListe(this.getObjet(), null);
        Liste col = new Liste("colonne", nomCol);
        Object[] val = new String[]{"-", "DESC", "ASC"};
        Liste ord = new Liste("ordre", val);
        liste.add(col);
        liste.add(ord);
        Object[] listeForm = new Champ[liste.size()];
        liste.copyInto(listeForm);
        this.setListeChamp((Champ[])listeForm);
        this.setNbRangee(nbRange);
    }

    public void makeChampFormuInsert() throws Exception {
    }

    public String makeHtmlAutoComplete(UserEJB u) throws Exception {
        String html = "";
        html = html + "$(document).ready(function () {";
        Champ[] champListe = this.getListeChamp();
        int count = 1;
        for (int i = 0; i < champListe.length; ++i) {
            String liste;
            String[] valColonne = champListe[i].getNom().split("_");
            String nameInput = "";
            nameInput = valColonne.length > 1 && Utilitaire.checkNumber(valColonne[1]) ? valColonne[0] + "libelle_" + valColonne[1] : champListe[i].getNom() + "libelle";
            if (champListe[i].isAutoComplete() && champListe[i].getPageAppel() != null && champListe[i].getPageAppel().compareToIgnoreCase("") != 0) {
                liste = (String)u.getMapAutoComplete().get(champListe[i].getNom());
                html = html + "var liste" + count + " = " + liste + "; ";
                html = html + "$('#" + nameInput + "').autocomplete({";
                html = html + "source: liste" + count + ",";
                html = html + "minLength: 2,";
                html = html + "select : function(event,ui){";
                html = html + "$('#" + nameInput + "').val(ui.item.id);";
                html = html + "}";
                html = html + "});";
            } else if (champListe[i].isAutoComplete()) {
                liste = (String)u.getMapAutoComplete().get(champListe[i].getNom());
                html = html + "var liste" + count + " = " + liste + "; ";
                html = html + "$('#" + champListe[i].getNom() + "libelle').autocomplete({";
                html = html + "source: liste" + count + ",";
                html = html + "minLength: 2,";
                html = html + "select : function(event,ui){";
                html = html + "$('#" + champListe[i].getNom() + "').val(ui.item.id);";
                html = html + "}";
                html = html + "});";
            }
            ++count;
        }
        html = html + "});";
        this.setHtmlAutoComplete(html);
        return html;
    }

    public void makeHtml() throws Exception {
        this.updateEstMoitier();
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(this.lang));
        String temp = "";
        temp = temp + "<div style='border-top-right-radius: 10px;border-top-left-radius: 16px;border-bottom-right-radius: 16px;border-bottom-left-radius: 16px;' class='box collapsed collapsed-box'>";
        temp = temp + "<div style='padding: 16px;' class='box-header'>";
        temp = temp + "<h3 class='box-title' color='#103a8e'><span color='#103a8e' class=' filtretitle h520pxSemibold'>" + RB.getString("Recherche_avance") + "</span></h3>";
        temp = temp + "<div class='box-tools pull-right'><button data-original-title='Collapse' class='btn btn-box-tool' data-widget='collapse' data-toggle='tooltip' title=''><i class='fa fa-plus'></i></button> </div>";
        temp = temp + "</div>";
        temp = temp + "<div class='box-body' id='pagerecherche'>";
        int reste = this.listeChamp.length % this.getNbRangee();
        int nombreLigne = 0;
        nombreLigne = reste != 0 ? this.listeChamp.length / this.getNbRangee() : this.listeChamp.length / this.getNbRangee() + 1;
        for (int i = 0; i < nombreLigne; ++i) {
            temp = temp + "<div class='form-group'>";
            for (int j = 0; j < this.getNbRangee(); ++j) {
                temp = temp + "<div class='col-md-4'>";
                if (i * this.getNbRangee() + j >= this.listeChamp.length - 2) {
                    temp = temp + "</div>";
                    break;
                }
                Champ c = this.listeChamp[i * this.getNbRangee() + j];
                c.setAutre(c.getAutre() + " onkeydown='return searchKeyPress(event)'");
                String troisiemeTd = "";
                if (c.getPageAppel() != null && c.getPageAppel().compareToIgnoreCase("") != 0) {
                    troisiemeTd = "<input name='choix' type='button' class='submit' onclick=pagePopUp('" + c.getPageAppel() + "?champReturn=" + c.getNom() + "&apresLienPageAppel=" + c.getApresLienPageappel() + "') value=... />";
                }
                temp = temp + "<label for=" + c.getLibelleAfficheAnnotation() + ">" + c.getLibelleAfficheAnnotation() + "</label>";
                temp = temp + c.getHtml();
                temp = temp + troisiemeTd;
                temp = temp + "</div>";
            }
            temp = temp + "</div>";
        }
        temp = temp + "</div>";
        this.setHtml(temp);
    }

    private String getFiltreEtat() {
        String temp = "";
        if (this.etatVal != null) {
            temp = "<div class=\"filter-header d-flex flex-row\">\n";
            for (int i = 0; i < this.etatVal.length; ++i) {
                String selected = "";
                if (this.currentEtat == null && i == 0 || this.etatVal[i].equals(this.currentEtat)) {
                    selected = "checked";
                }
                temp = temp + "          <div class=\"type text-center etat " + selected + "\" onclick=\"changeEtat('" + this.etatVal[i] + "')\" value=\"" + this.etatVal[i] + "\">" + this.etatAff[i] + "</div>\n";
            }
            temp = temp + "</div>";
            temp = temp + "<input type=\"hidden\" id=\"etat_table\" name=\"etat_table\" />";
        }
        return temp;
    }

    private String getHtmlRechercheButton() {
        String temp = "";
        temp = temp + "<div class=\"form-group p-3 col-md-3 col-sm-6 d-flex align-items-center justify-content-start filter-option \">\n                    <img src='../assets/img/Show-filter.svg' alt=\" \" srcset=\" \" class=\"show-btn\">\n";
        temp = temp + "<input name='afficher' value='Rechercher' type='submit' class='btn btn-search' style='background:" + ConstanteAffichage.couleurPrimaire + "; color:white' id='btnListe'/>";
        temp = temp + "<button type='reset' class='btn btn-reset'>reinitialiser</button></div>";
        return temp;
    }

    public void makeHtmlNew() throws Exception {
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(this.lang));
        String temp = "";
        temp = temp + "<div class='row'>";
        temp = temp + "<div class=\"filter p-3 mt-3 mb-3\">";
        temp = temp + this.getFiltreEtat();
        temp = temp + "<div class=\"filter-body d-flex flex-wrap align-items-end p-1 \">";
        int nombreLigne = this.listeChamp.length - 2;
        for (int i = 0; i < nombreLigne; ++i) {
            for (int j = 0; j < 1; ++j) {
                String hideChamp = i > 2 ? "hidden-input" : "";
                temp = temp + "<div class='form-group p-3 col-md-3 col-sm-6 " + hideChamp + "'>";
                Champ c = this.listeChamp[i];
                c.setAutre(c.getAutre() + " onkeydown='return searchKeyPress(event)'");
                String troisiemeTd = "";
                if (c.getPageAppel() != null && c.getPageAppel().compareToIgnoreCase("") != 0) {
                    troisiemeTd = "<input name='choix' type='button' class='submit' onclick=pagePopUp('" + c.getPageAppel() + "?champReturn=" + c.getNom() + "&apresLienPageAppel=" + c.getApresLienPageappel() + "') value=... />";
                }
                temp = temp + "<label for=" + c.getLibelleAfficheAnnotation() + ">" + c.getLibelleAfficheAnnotation() + "</label>";
                temp = temp + c.getHtml();
                temp = temp + troisiemeTd;
                temp = temp + "</div>";
            }
            if (i != 2) continue;
            temp = temp + this.getHtmlRechercheButton();
        }
        if (nombreLigne <= 2) {
            temp = temp + this.getHtmlRechercheButton();
        }
        temp = temp + "</div>";
        temp = temp + "</div>";
        this.setHtmlNew(temp);
    }

    public void makeChampTableauAffiche(int nbTabAffiche, String[] defaut) throws Exception {
        Champ[] listeAff = new Champ[nbTabAffiche];
        bean.Champ[] nomCol = ListeColonneTable.getFromListe(this.getObjet(), null);
        Object[] valeur = new Object[nomCol.length + 1];
        valeur[0] = "-";
        for (int k = 0; k < nomCol.length; ++k) {
            valeur[k + 1] = nomCol[k];
        }
        for (int i = 0; i < nbTabAffiche; ++i) {
            listeAff[i] = new Liste("colAffiche" + (i + 1), "Colonne " + (i + 1), valeur);
            ((Liste)listeAff[i]).setDefaultSelected(defaut[i]);
        }
        this.setChampTableauAff(listeAff);
    }

    public void makeChampGroupe(int nombreChamp, int nbsomme) throws Exception {
        this.makeChampGroupe(nombreChamp, nombreChamp, nbsomme);
    }

    public void makeChampGroupe(int nombreLigne, int nombreColonne, int nbsomme) throws Exception {
        Champ[] listeGroupe = new Champ[nombreLigne + nombreColonne + nbsomme];
        bean.Champ[] nomCol = ListeColonneTable.getFromListe(this.getObjet(), null);
        Object[] valeur = new Object[nomCol.length + 1];
        valeur[0] = "-";
        for (int k = 0; k < nomCol.length; ++k) {
            valeur[k + 1] = nomCol[k];
        }
        for (int i = 0; i < nombreLigne + nombreColonne + nbsomme; ++i) {
            if (i < nombreLigne) {
                int numeroLigne = i + 1;
                listeGroupe[i] = new Liste("colGroupe" + numeroLigne, "Ligne Group&eacute;e " + numeroLigne, valeur);
                continue;
            }
            if (i < nombreLigne + nombreColonne) {
                int numeroColonne = i - nombreLigne + 1;
                listeGroupe[i] = new Liste("ligneGroupe" + numeroColonne, "Colonne Group&eacute;e " + numeroColonne, valeur);
                continue;
            }
            int numeroSomme = i - nombreLigne - nombreColonne + 1;
            listeGroupe[i] = new Liste("colSomme" + numeroSomme, "ColSomme " + numeroSomme, valeur);
        }
        this.setChampGroupe(listeGroupe);
    }

    public void makeHtmlTableauAff() throws Exception {
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(this.lang));
        if (this.getChampTableauAff() == null) {
            this.setHtmlTableauAff("");
            return;
        }
        String temp = "";
        temp = temp + "<span class=\"separator\"></span>";
        temp = temp + "<button class=\"btn-secondary btn-bg-white btn btn-small\" data-toggle=\"modal\" data-target=\"#colonne-modal\" type=\"button\" ><i class='material-symbols-rounded'>view_column</i> R&eacute;organiser</button>";
        temp = temp + "<div class=\"modal fade\" id=\"colonne-modal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"colonne-modal\" aria-hidden=\"true\">\n  <div class=\"modal-dialog clavier\" role=\"document\">\n    <div class=\"modal-content\">\n       <div class=\"modal-body\">";
        temp = temp + "<div class='box' id=\"pagerecherche\">";
        temp = temp + "<div class='box-body'>";
        temp = temp + "<div class='tableauaffiche'>";
        temp = temp + "<h3 class='h520pxSemibold m-0' multi-lang>" + RB.getString("Choix_col") + "</h3>";
        int reste = this.getChampTableauAff().length % this.getNbRangee();
        int nombreLigne = 0;
        nombreLigne = reste != 0 ? this.getChampTableauAff().length / this.getNbRangee() + 1 : this.getChampTableauAff().length / this.getNbRangee();
        for (int i = 0; i < nombreLigne; ++i) {
            temp = temp + "<div class='input-container'>";
            for (int j = 0; j < this.getNbRangee() && i * this.getNbRangee() + j != this.getChampTableauAff().length; ++j) {
                Champ c = this.getChampTableauAff()[i * this.getNbRangee() + j];
                String classCssInputContainer = c.getEstMoitier() ? "form-input w-25" : "form-input";
                temp = temp + "<div class='" + classCssInputContainer + "'>";
                temp = temp + "<label class='input-label' for=" + c.getNom() + " multi-lang>" + c.getLibelleAfficheAnnotation() + "</label>";
                temp = temp + c.getHtml();
                temp = temp + "</div>";
            }
            temp = temp + "</div>";
        }
        temp = temp + "</div>";
        temp = temp + this.getHtmlTri();
        temp = temp + "<div class='box-footer borderless'>";
        temp = temp + "<input name='afficher' multi-lang value='" + RB.getString("afficher") + "' type='submit' class='btn btn-primary pull-right' id='btnListe'/>";
        temp = temp + "<button class='btn btn-secondary pull-right' style='margin-right:8px' type=\"reset\">R&eacute;initialiser</button>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<button class='btn-exit' data-dismiss=\"modal\" type='button'><i class='fa fa-close'></i></button>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        this.setHtmlTableauAff(temp);
    }

    public void makeHtmlTableauAffNew() throws Exception {
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(this.lang));
        if (this.getChampTableauAff() == null) {
            this.setHtmlTableauAff("");
            return;
        }
        String temp = "";
        temp = temp + "<span class=\"separator\"></span>";
        int reste = this.getChampTableauAff().length % this.getNbRangee();
        int nombreLigne = 0;
        nombreLigne = reste != 0 ? this.getChampTableauAff().length / this.getNbRangee() + 1 : this.getChampTableauAff().length / this.getNbRangee();
        for (int i = 0; i < nombreLigne; ++i) {
            for (int j = 0; j < this.getNbRangee() && i * this.getNbRangee() + j != this.getChampTableauAff().length; ++j) {
                temp = temp + "<div class='form-group p-3 col-md-4 col-sm-6 hidden-input'>";
                Champ c = this.getChampTableauAff()[i * this.getNbRangee() + j];
                temp = temp + "<label for=" + c.getNom() + ">" + c.getLibelleAfficheAnnotation() + "</label>";
                temp = temp + c.getHtml();
                temp = temp + "</div>";
            }
        }
        this.setHtmlTableauAffNew(temp);
    }

    public void makeHtmlGroupe() throws Exception {
        if (this.getChampGroupe() == null) {
            this.setHtmlGroupe("");
            return;
        }
        String temp = "";
        temp = temp + "<span class=\"separator\"></span>";
        temp = temp + "<button class=\"btn-secondary btn-bg-white btn btn-small\" data-toggle=\"modal\" data-target=\"#colonne-groupe-modal\" type=\"button\" ><i class='material-symbols-rounded'>view_column</i> Colonne</button>";
        temp = temp + "<div class=\"modal fade\" id=\"colonne-groupe-modal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"colonne-modal\" aria-hidden=\"true\">\n  <div class=\"modal-dialog clavier\" role=\"document\">\n    <div class=\"modal-content\">\n       <div class=\"modal-body\">";
        temp = temp + "<div class='box' id=\"pagerecherche\">";
        temp = temp + "<div class='box-body'>";
        temp = temp + "<div class='tableauaffiche'>";
        temp = temp + "<h3 class='h520pxSemibold m-0' multi-lang>Choix des colonnes group&eacute;es &agrave; afficher</h3>";
        this.setNbRangee(2);
        int reste = this.getChampGroupe().length % this.getNbRangee();
        int nombreLigne = 0;
        nombreLigne = reste != 0 ? this.getChampGroupe().length / this.getNbRangee() + 1 : this.getChampGroupe().length / this.getNbRangee();
        for (int i = 0; i < nombreLigne; ++i) {
            temp = temp + "<div class='input-container'>";
            for (int j = 0; j < this.getNbRangee() && i * this.getNbRangee() + j != this.getChampGroupe().length; ++j) {
                Champ c = this.getChampGroupe()[i * this.getNbRangee() + j];
                String classCssInputContainer = c.getEstMoitier() ? "form-input w-25" : "form-input";
                temp = temp + "<div class='" + classCssInputContainer + "'>";
                temp = temp + "<label class='input-label' for=" + c.getNom() + " multi-lang>" + c.getLibelleAfficheAnnotation() + "</label>";
                temp = temp + c.getHtml();
                temp = temp + "</div>";
            }
            temp = temp + "</div>";
        }
        temp = temp + "</div>";
        temp = temp + this.getHtmlTri();
        temp = temp + "<div class='box-footer borderless'>";
        temp = temp + "<input name='afficher' multi-lang value='Afficher' type='submit' class='btn btn-primary pull-right' id='btnListe'/>";
        temp = temp + "<button class='btn btn-secondary pull-right' style='margin-right:8px' type=\"reset\">R&eacute;initialiser</button>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<button class='btn-exit' data-dismiss=\"modal\" type='button'><i class='fa fa-close'></i></button>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        this.setHtmlGroupe(temp);
    }

    public void gsetLibelleAffiche(String[] liste) {
        for (int i = 0; i < liste.length; ++i) {
            this.listeChamp[i].setLibelleAffiche(liste[i]);
        }
    }

    public void setLibelleAffiche(String libelle, int numeroLib) {
        this.listeChamp[numeroLib].setLibelleAffiche(libelle);
    }

    public void makeHtmlInsertTemplate() throws Exception {
        String temp = "";
        temp = temp + this.getUploadForm();
        temp = temp + "<div class='col-md-12 cardradius input-container' >";
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            Champ c = this.getListeChamp()[i];
            String troisiemeTd = "";
            if (c.getNom().compareToIgnoreCase("iduser") == 0) {
                c.setVisible(false);
            }
            if (!c.getVisible()) continue;
            String labelAutre = c.getAutre();
            c.setAutre("tabindex='" + (i + 1) + "'" + c.getAutre());
            String classCssInputContainer = c.getEstMoitier() ? "form-input w-25" : "form-input";
            temp = temp + "<div class='" + classCssInputContainer + "'>";
            temp = temp + "<label multi-lang class='input-label' for='" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "' " + labelAutre + ">" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</label>";
            if (c.getPageAppel() != null && c.getPageAppel().compareToIgnoreCase("") != 0) {
                String autre = "";
                if (c.getColFiltre() != null && c.getValFiltre() != null) {
                    for (int j = 0; j < c.getColFiltre().length; ++j) {
                        autre = autre + "&" + c.getColFiltre()[j] + "=" + c.getValFiltre()[j];
                    }
                }
                temp = temp + "<div class='d-flex gp-2 col-md-12 nopadding'>";
                temp = temp + "<span class='input-span'>" + this.getListeChamp()[i].getInputHidden() + "</span>";
                troisiemeTd = "<input name='choix' type='button' class='submit btnheight' onclick=pagePopUp('" + c.getPageAppel() + "?champReturn=" + c.getChampReturn() + "&apresLienPageAppel=" + c.getApresLienPageappel() + autre + "') value=... />";
                temp = temp + "<span style='text-align:right;' class=''>" + troisiemeTd + "</span>";
                temp = temp + "</div>";
            } else {
                temp = temp + "<span class='col-md-12 nopadding'>" + this.getListeChamp()[i].getHtmlInsert() + "</span>";
            }
            if (c.getTypeData() != null && c.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeDaty) == 0) {
                troisiemeTd = "<a href='javascript:cal.popup()'><img src='calendar/img/cal.gif' alt='Cliquez ici pour choisir une date' width=16 height=16 border=0 align='absmiddle' /></a>";
                temp = temp + "<span>" + troisiemeTd + "</span>";
            }
            temp = temp + "</div>";
        }
        temp = temp + "</div>";
        temp = temp + this.getHtmlAutocomplete();
        this.setHtmlInsert(temp);
    }

    public void makeHtmlInsert() throws Exception {
        this.makeHtmlInsertTemplate();
    }

    public void makeHtmlAddTabIndex() throws Exception {
        String temp = "";
        temp = temp + "<div class='row'>";
        temp = temp + "<div class='col-md-3'></div>";
        temp = temp + "<div class='col-md-6'>";
        temp = temp + "<div class='box-insert-amadia'>";
        temp = temp + "<div class='box box-primary'>";
        temp = temp + "<div class='box-body'>";
        temp = temp + "<table class='table '>";
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            Champ c = this.getListeChamp()[i];
            String troisiemeTd = "";
            if (c.getNom().compareToIgnoreCase("iduser") == 0) {
                c.setVisible(false);
            }
            if (!c.getVisible()) continue;
            c.setAutre("tabindex='" + (i + 1) + "'" + c.getAutre());
            temp = temp + "<tr>";
            temp = temp + "<th><label for='" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "' " + c.getAutre() + ">" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</label></th>";
            if (c.getPageAppel() != null && c.getPageAppel().compareToIgnoreCase("") != 0) {
                temp = temp + "<td>" + this.getListeChamp()[i].getInputHidden() + "</td>";
                troisiemeTd = "<input name='choix' type='button' class='submit' onclick=pagePopUp('" + c.getPageAppel() + "?champReturn=" + c.getChampReturn() + "&apresLienPageAppel=" + c.getApresLienPageappel() + "') value=... />";
                temp = temp + "<td>" + troisiemeTd + "</td>";
            } else {
                temp = temp + "<td>" + this.getListeChamp()[i].getHtmlInsert() + "</td>";
            }
            if (c.getTypeData() != null && c.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeDaty) == 0) {
                troisiemeTd = "<a href='javascript:cal.popup()'><img src='calendar/img/cal.gif' alt='Cliquez ici pour choisir une date' width=16 height=16 border=0 align='absmiddle' /></a>";
                temp = temp + "<td>" + troisiemeTd + "</td>";
            }
            temp = temp + "</tr>";
        }
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "<div class='box-footer'>";
        temp = temp + "<div class='box-footer borderless nopadding'>";
        temp = temp + "<button type='submit' name='Submit2' class='btn btn-primary pull-right' style='margin-right: 25px;' tabIndex='" + this.getListeChamp().length + 1 + "'>Ajouter</button> ";
        temp = temp + "<button type='reset' name='Submit2' class='btn btn-secondary pull-right' style='margin-right: 15px;' tabIndex='" + this.getListeChamp().length + 2 + "'>R&eacute;initialiser</button>";
        temp = temp + "</div></div></div></div></div>";
        temp = temp + "<div class='col-md-3'></div>";
        temp = temp + "</div>";
        this.setHtmlInsert(temp);
    }

    public void makeHtmlInsertTabIndex() throws Exception {
        this.makeHtmlInsertTabIndexTemplate();
    }

    public void makeHtmlInsertTabIndexTemplate() throws Exception {
        String temp = "";
        if (this.getRubrique() == null) {
            if (this.getListeChamp().length < 10 && this.getNbColonne() < 2) {
                int i;
                ArrayList<Champ> listeChampInvisible = new ArrayList<Champ>();
                temp = temp + "<div class='row'>";
                temp = temp + "<div class='col-md-4'></div>";
                temp = temp + "<div class='col-md-4 cardradius'>";
                temp = temp + "<h1 class=\"box-title cardtitle \" align=\"left\">" + this.getTitre() + "</h1>";
                temp = temp + "<div class='row' >";
                for (i = 0; i < this.getListeChamp().length; ++i) {
                    Champ c = this.getListeChamp()[i];
                    String troisiemeTd = "";
                    if (c.getNom().compareToIgnoreCase("iduser") == 0) {
                        c.setVisible(false);
                    }
                    if (c.getNom().compareToIgnoreCase("motsClesss") == 0) {
                        c.setVisible(false);
                    }
                    if (c.getVisible()) {
                        String labelAutre = c.getAutre();
                        c.setAutre("tabindex='" + (i + 1) + "'" + c.getAutre());
                        String classCssInputContainer = c.getEstMoitier() ? "form-input w-25" : "form-input";
                        temp = temp + "<div class='" + classCssInputContainer + "'>";
                        temp = temp + "<label class='input-label' for='" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "' " + labelAutre + ">" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</label>";
                        if (c.getPageAppel() != null && c.getPageAppel().compareToIgnoreCase("") != 0) {
                            String autre = "";
                            if (c.getColFiltre() != null && c.getValFiltre() != null) {
                                for (int j = 0; j < c.getColFiltre().length; ++j) {
                                    autre = autre + "&" + c.getColFiltre()[j] + "=" + c.getValFiltre()[j];
                                }
                            }
                            temp = temp + "<div class='d-flex gp-2 col-md-12 nopadding'>";
                            temp = temp + "<span class='input-span'>" + this.getListeChamp()[i].getInputHidden() + "</span>";
                            troisiemeTd = "<input name='choix' type='button' class='submit btnheight' onclick=pagePopUp('" + c.getPageAppel() + "?champReturn=" + c.getChampReturn() + "&apresLienPageAppel=" + c.getApresLienPageappel() + autre + "') value=... />";
                            temp = temp + "<span style='text-align:right;' class=''>" + troisiemeTd + "</span>";
                            temp = temp + "</div>";
                        } else {
                            temp = temp + "<span class='col-md-12 nopadding'>" + this.getListeChamp()[i].getHtmlInsert() + "</span>";
                        }
                        if (c.getTypeData() != null && c.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeDaty) == 0) {
                            troisiemeTd = "<a href='javascript:cal.popup()'><img src='calendar/img/cal.gif' alt='Cliquez ici pour choisir une date' width=16 height=16 border=0 align='absmiddle' /></a>";
                            temp = temp + "<span>" + troisiemeTd + "</span>";
                        }
                        temp = temp + "</div>";
                    }
                    if (c.getVisible()) continue;
                    listeChampInvisible.add(c);
                }
                temp = temp + "<div class='box-footer borderless nopadding'>";
                temp = temp + "<button type='submit' name='Submit2' class='btn btn-primary pull-right' style='margin-right: 0;margin-top: 1rem;' tabIndex='" + this.getListeChamp().length + 1 + "' multi-lang>Enregistrer</button> ";
                temp = temp + "<button type='reset' name='Submit2' class='btn btn-secondary pull-right' style='margin-right: 15px;margin-top: 1rem;' tabIndex='" + this.getListeChamp().length + 2 + "' multi-lang>R&eacute;initialiser</button>";
                temp = temp + "</div>";
                temp = temp + "</div>";
                temp = temp + "</div>";
                temp = temp + "<div class='col-md-4'></div>";
                temp = temp + "</div>";
                for (i = 0; i < listeChampInvisible.size(); ++i) {
                    temp = temp + "<input name='" + ((Champ)listeChampInvisible.get(i)).getNom() + "' type='hidden' class=" + ((Champ)listeChampInvisible.get(i)).getCss() + "  id=" + ((Champ)listeChampInvisible.get(i)).getNom() + " value='" + ((Champ)listeChampInvisible.get(i)).getValeur() + "' " + ((Champ)listeChampInvisible.get(i)).getAutre() + ">";
                }
            } else {
                int i;
                temp = temp + "<div class='col-md-12 cardradius' >";
                temp = temp + "<div class='input-container' >";
                ArrayList<Champ> listeChampInvisible = new ArrayList<Champ>();
                for (i = 0; i < this.getListeChamp().length; ++i) {
                    Champ c = this.getListeChamp()[i];
                    String troisiemeTd = "";
                    if (c.getNom().compareToIgnoreCase("iduser") == 0) {
                        c.setVisible(false);
                    }
                    if (c.getNom().compareToIgnoreCase("motsClesss") == 0) {
                        c.setVisible(false);
                    }
                    if (c.getVisible()) {
                        String labelAutre = c.getAutre();
                        c.setAutre("tabindex='" + (i + 1) + "'" + c.getAutre());
                        String classCssInputContainer = c.getEstMoitier() ? "form-input w-25" : "form-input";
                        temp = temp + "<div class='" + classCssInputContainer + "'>";
                        temp = temp + "<label multi-lang class='input-label' for='" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "' " + labelAutre + ">" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</label>";
                        if (c.getPageAppel() != null && c.getPageAppel().compareToIgnoreCase("") != 0) {
                            String autre = "";
                            if (c.getColFiltre() != null && c.getValFiltre() != null) {
                                for (int j = 0; j < c.getColFiltre().length; ++j) {
                                    autre = autre + "&" + c.getColFiltre()[j] + "=" + c.getValFiltre()[j];
                                }
                            }
                            temp = temp + "<div class='d-flex gp-2 col-md-12 nopadding'>";
                            temp = temp + "<span class='input-span'>" + this.getListeChamp()[i].getInputHidden() + "</span>";
                            troisiemeTd = "<input name='choix' type='button' class='submit btnheight' onclick=pagePopUp('" + c.getPageAppel() + "?champReturn=" + c.getChampReturn() + "&apresLienPageAppel=" + c.getApresLienPageappel() + autre + "') value=... />";
                            temp = temp + "<span style='text-align:right;' class=''>" + troisiemeTd + "</span>";
                            temp = temp + "</div>";
                        } else {
                            temp = temp + "<span class='d-flex gap-2'>" + this.getListeChamp()[i].getHtmlInsert() + "</span>";
                        }
                        if (c.getTypeData() != null && c.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeDaty) == 0) {
                            troisiemeTd = "<a href='javascript:cal.popup()'><img src='calendar/img/cal.gif' alt='Cliquez ici pour choisir une date' width=16 height=16 border=0 align='absmiddle' /></a>";
                            temp = temp + "<span>" + troisiemeTd + "</span>";
                        }
                        temp = temp + "</div>";
                    }
                    if (c.getVisible()) continue;
                    listeChampInvisible.add(c);
                }
                temp = temp + "</div>";
                temp = temp + "<div class='box-footer borderless nopadding' style='margin-top: 1rem;'>";
                temp = temp + "<button type='submit' name='Submit2' class='btn btn-primary pull-right' style='margin-right: 0;' tabIndex='" + this.getListeChamp().length + 1 + "' multi-lang>Enregistrer</button> ";
                temp = temp + "<button type='reset' name='Submit2' class='btn btn-secondary pull-right' style='margin-right: 15px;' tabIndex='" + this.getListeChamp().length + 2 + "' multi-lang>R&eacute;initialiser</button>";
                temp = temp + "</div>";
                temp = temp + "</div>";
                for (i = 0; i < listeChampInvisible.size(); ++i) {
                    temp = temp + "<input name='" + ((Champ)listeChampInvisible.get(i)).getNom() + "' type='hidden' class=" + ((Champ)listeChampInvisible.get(i)).getCss() + "  id=" + ((Champ)listeChampInvisible.get(i)).getNom() + " value='" + ((Champ)listeChampInvisible.get(i)).getValeur() + "' " + ((Champ)listeChampInvisible.get(i)).getAutre() + ">";
                }
            }
        } else {
            HashSet<String> champsAssignes = new HashSet<String>();
            for (Map.Entry<String, String[]> entry : this.getRubrique().entrySet()) {
                for (String nomChamp : entry.getValue()) {
                    champsAssignes.add(nomChamp.toLowerCase());
                }
            }
            boolean hasAutres = false;
            for (int i = 0; i < this.getListeChamp().length; ++i) {
                Champ champ = this.getListeChamp()[i];
                if (champsAssignes.contains(champ.getNom().toLowerCase()) || !champ.getVisible()) continue;
                hasAutres = true;
                break;
            }
            int nombreRubriques = this.getRubrique().size();
            if (hasAutres) {
                ++nombreRubriques;
            }
            int indexRubrique = 0;
            for (Map.Entry<String, String[]> entry : this.getRubrique().entrySet()) {
                String titreRubrique = entry.getKey();
                String[] nomsChamps = entry.getValue();
                boolean estDerniereRubrique = ++indexRubrique == nombreRubriques;
                temp = temp + "<div class='rubrique-section' style='margin-bottom: 25px;'>";
                temp = temp + "<div class='rubrique-header' style='padding-top: 8px;'>";
                temp = temp + "<h5 class='Body14pxSemiBold' >" + titreRubrique + "</h5>";
                temp = temp + "</div>";
                if (this.getListeChamp().length < 10 && this.getNbColonne() < 2) {
                    temp = temp + "<div class='row'>";
                    temp = temp + "<div class='col-md-4'></div>";
                    temp = temp + "<div class='col-md-4 cardradius'>";
                    temp = temp + "<h1 class=\"box-title cardtitle \" align=\"left\">" + this.getTitre() + "</h1>";
                    temp = temp + "<div class='row' >";
                } else {
                    temp = temp + "<div class='col-md-12 cardradius' style='margin-bottom: 2em;'>";
                    temp = temp + "<div class='input-container' >";
                }
                for (int i = 0; i < this.getListeChamp().length; ++i) {
                    Champ c = this.getListeChamp()[i];
                    boolean appartientRubrique = false;
                    for (String nomChamp : nomsChamps) {
                        if (!c.getNom().equalsIgnoreCase(nomChamp)) continue;
                        appartientRubrique = true;
                        break;
                    }
                    if (!appartientRubrique) continue;
                    String troisiemeTd = "";
                    if (c.getNom().compareToIgnoreCase("iduser") == 0) {
                        c.setVisible(false);
                    }
                    if (!c.getVisible()) continue;
                    String labelAutre = c.getAutre();
                    c.setAutre("tabindex='" + (i + 1) + "'" + c.getAutre());
                    String classCssInputContainer = c.getEstMoitier() ? "form-input w-25" : "form-input";
                    temp = temp + "<div class='" + classCssInputContainer + "'>";
                    temp = temp + "<label class='input-label' for='" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "' " + labelAutre + ">" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</label>";
                    if (c.getPageAppel() != null && c.getPageAppel().compareToIgnoreCase("") != 0) {
                        String autre = "";
                        if (c.getColFiltre() != null && c.getValFiltre() != null) {
                            for (int j = 0; j < c.getColFiltre().length; ++j) {
                                autre = autre + "&" + c.getColFiltre()[j] + "=" + c.getValFiltre()[j];
                            }
                        }
                        temp = temp + "<div class='d-flex gp-2 col-md-12 nopadding'>";
                        temp = temp + "<span class='input-span'>" + this.getListeChamp()[i].getInputHidden() + "</span>";
                        troisiemeTd = "<input name='choix' type='button' class='submit btnheight' onclick=pagePopUp('" + c.getPageAppel() + "?champReturn=" + c.getChampReturn() + "&apresLienPageAppel=" + c.getApresLienPageappel() + autre + "') value=... />";
                        temp = temp + "<span style='text-align:right;' class=''>" + troisiemeTd + "</span>";
                        temp = temp + "</div>";
                    } else {
                        temp = this.getListeChamp().length < 10 && this.getNbColonne() < 2 ? temp + "<span class='col-md-12 nopadding'>" + this.getListeChamp()[i].getHtmlInsert() + "</span>" : temp + "<span class='d-flex gap-2'>" + this.getListeChamp()[i].getHtmlInsert() + "</span>";
                    }
                    if (c.getTypeData() != null && c.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeDaty) == 0) {
                        troisiemeTd = "<a href='javascript:cal.popup()'><img src='calendar/img/cal.gif' alt='Cliquez ici pour choisir une date' width=16 height=16 border=0 align='absmiddle' /></a>";
                        temp = temp + "<span>" + troisiemeTd + "</span>";
                    }
                    temp = temp + "</div>";
                }
                if (estDerniereRubrique) {
                    if (this.getListeChamp().length < 10 && this.getNbColonne() < 2) {
                        temp = temp + "<div class='box-footer borderless nopadding'>";
                        temp = temp + "<button type='submit' name='Submit2' class='btn btn-primary pull-right' style='margin-right: 0;margin-top: 1rem;' tabIndex='" + this.getListeChamp().length + 1 + "' multi-lang>Enregistrer</button> ";
                        temp = temp + "<button type='reset' name='Submit2' class='btn btn-secondary pull-right' style='margin-right: 15px;margin-top: 1rem;' tabIndex='" + this.getListeChamp().length + 2 + "' multi-lang>R&eacute;initialiser</button>";
                        temp = temp + "</div>";
                        temp = temp + "</div>";
                        temp = temp + "</div>";
                        temp = temp + "<div class='col-md-4'></div>";
                        temp = temp + "</div>";
                    } else {
                        temp = temp + "</div>";
                        temp = temp + "<div class='box-footer borderless nopadding' style='margin-top: 1rem;'>";
                        temp = temp + "<button type='submit' name='Submit2' class='btn btn-primary pull-right' style='margin-right: 0;' tabIndex='" + this.getListeChamp().length + 1 + "' multi-lang>Enregistrer</button> ";
                        temp = temp + "<button type='reset' name='Submit2' class='btn btn-secondary pull-right' style='margin-right: 15px;' tabIndex='" + this.getListeChamp().length + 2 + "' multi-lang>R&eacute;initialiser</button>";
                        temp = temp + "</div>";
                        temp = temp + "</div>";
                    }
                } else if (this.getListeChamp().length < 10 && this.getNbColonne() < 2) {
                    temp = temp + "</div>";
                    temp = temp + "</div>";
                    temp = temp + "<div class='col-md-4'></div>";
                    temp = temp + "</div>";
                } else {
                    temp = temp + "</div>";
                    temp = temp + "</div>";
                }
                temp = temp + "</div>";
            }
            if (hasAutres) {
                temp = temp + "<div class='rubrique-section' style='margin-bottom: 25px;'>";
                temp = temp + "<div class='rubrique-header' style='padding-top: 8px;'>";
                temp = temp + "<h5 class='Body14pxSemiBold' >Autres</h5>";
                temp = temp + "</div>";
                if (this.getListeChamp().length < 10 && this.getNbColonne() < 2) {
                    temp = temp + "<div class='row'>";
                    temp = temp + "<div class='col-md-4'></div>";
                    temp = temp + "<div class='col-md-4 cardradius'>";
                    temp = temp + "<h1 class=\"box-title cardtitle \" align=\"left\">" + this.getTitre() + "</h1>";
                    temp = temp + "<div class='row' >";
                } else {
                    temp = temp + "<div class='col-md-12 cardradius' >";
                    temp = temp + "<div class='input-container' >";
                }
                for (int i = 0; i < this.getListeChamp().length; ++i) {
                    Champ c = this.getListeChamp()[i];
                    if (champsAssignes.contains(c.getNom().toLowerCase()) || !c.getVisible()) continue;
                    String troisiemeTd = "";
                    if (c.getNom().compareToIgnoreCase("iduser") == 0) {
                        c.setVisible(false);
                    }
                    if (!c.getVisible()) continue;
                    String labelAutre = c.getAutre();
                    c.setAutre("tabindex='" + (i + 1) + "'" + c.getAutre());
                    String classCssInputContainer = c.getEstMoitier() ? "form-input w-25" : "form-input";
                    temp = temp + "<div class='" + classCssInputContainer + "'>";
                    temp = temp + "<label class='input-label' for='" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "' " + labelAutre + ">" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</label>";
                    if (c.getPageAppel() != null && c.getPageAppel().compareToIgnoreCase("") != 0) {
                        String autre = "";
                        if (c.getColFiltre() != null && c.getValFiltre() != null) {
                            for (int j = 0; j < c.getColFiltre().length; ++j) {
                                autre = autre + "&" + c.getColFiltre()[j] + "=" + c.getValFiltre()[j];
                            }
                        }
                        temp = temp + "<div class='d-flex gp-2 col-md-12 nopadding'>";
                        temp = temp + "<span class='input-span'>" + this.getListeChamp()[i].getInputHidden() + "</span>";
                        troisiemeTd = "<input name='choix' type='button' class='submit btnheight' onclick=pagePopUp('" + c.getPageAppel() + "?champReturn=" + c.getChampReturn() + "&apresLienPageAppel=" + c.getApresLienPageappel() + autre + "') value=... />";
                        temp = temp + "<span style='text-align:right;' class=''>" + troisiemeTd + "</span>";
                        temp = temp + "</div>";
                    } else {
                        temp = this.getListeChamp().length < 10 && this.getNbColonne() < 2 ? temp + "<span class='col-md-12 nopadding'>" + this.getListeChamp()[i].getHtmlInsert() + "</span>" : temp + "<span class='d-flex gap-2'>" + this.getListeChamp()[i].getHtmlInsert() + "</span>";
                    }
                    if (c.getTypeData() != null && c.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeDaty) == 0) {
                        troisiemeTd = "<a href='javascript:cal.popup()'><img src='calendar/img/cal.gif' alt='Cliquez ici pour choisir une date' width=16 height=16 border=0 align='absmiddle' /></a>";
                        temp = temp + "<span>" + troisiemeTd + "</span>";
                    }
                    temp = temp + "</div>";
                }
                if (this.getListeChamp().length < 10 && this.getNbColonne() < 2) {
                    temp = temp + "<div class='box-footer borderless nopadding'>";
                    temp = temp + "<button type='submit' name='Submit2' class='btn btn-primary pull-right' style='margin-right: 0;margin-top: 1rem;' tabIndex='" + this.getListeChamp().length + 1 + "' multi-lang>Enregistrer</button> ";
                    temp = temp + "<button type='reset' name='Submit2' class='btn btn-secondary pull-right' style='margin-right: 15px;margin-top: 1rem;' tabIndex='" + this.getListeChamp().length + 2 + "' multi-lang>R&eacute;initialiser</button>";
                    temp = temp + "</div>";
                    temp = temp + "</div>";
                    temp = temp + "</div>";
                    temp = temp + "<div class='col-md-4'></div>";
                    temp = temp + "</div>";
                } else {
                    temp = temp + "</div>";
                    temp = temp + "<div class='box-footer borderless nopadding' style='margin-top: 1rem;'>";
                    temp = temp + "<button type='submit' name='Submit2' class='btn btn-primary pull-right' style='margin-right: 0;' tabIndex='" + this.getListeChamp().length + 1 + "' multi-lang>Enregistrer</button> ";
                    temp = temp + "<button type='reset' name='Submit2' class='btn btn-secondary pull-right' style='margin-right: 15px;' tabIndex='" + this.getListeChamp().length + 2 + "' multi-lang>R&eacute;initialiser</button>";
                    temp = temp + "</div>";
                    temp = temp + "</div>";
                }
                temp = temp + "</div>";
            }
        }
        temp = temp + this.getHtmlAutocomplete();
        temp = temp + this.getHtmlChampInvisible();
        this.setHtmlInsert(temp);
    }

    public void makeHtmlInsertSimple() throws Exception {
        String temp = "";
        int nb2 = this.getListeChamp().length / 2;
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            Champ c = this.getListeChamp()[i];
            String troisiemeTd = "";
            if (c.getNom().compareToIgnoreCase("iduser") == 0) {
                c.setVisible(false);
            }
            if (!c.getVisible()) continue;
            c.setAutre("tabindex='" + (i + 1) + "'" + c.getAutre());
            temp = temp + "<div class=\"form-group col-lg-4 col-md-6 col-sm-6 btn-group-container p-1\">";
            temp = temp + "<label class='apj-label' for='" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "' " + c.getAutre() + ">" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</label>";
            if (c.getPageAppel() != null && c.getPageAppel().compareToIgnoreCase("") != 0) {
                String autre = "";
                if (c.getColFiltre() != null && c.getValFiltre() != null) {
                    for (int j = 0; j < c.getColFiltre().length; ++j) {
                        autre = autre + "&" + c.getColFiltre()[j] + "=" + c.getValFiltre()[j];
                    }
                }
                troisiemeTd = "<div class=\"apj-pop-up-container\">";
                troisiemeTd = troisiemeTd + this.getListeChamp()[i].getInputHidden();
                troisiemeTd = troisiemeTd + "<input name='choix' type='button' class='apj-pop-up btn btn-apj-primary' onclick=pagePopUp('" + c.getPageAppel() + "?champReturn=" + c.getChampReturn() + "&apresLienPageAppel=" + c.getApresLienPageappel() + autre + "') value=\"...\" />";
                troisiemeTd = troisiemeTd + "</div>";
                temp = temp + troisiemeTd;
            } else {
                temp = temp + this.getListeChamp()[i].getHtmlInsert();
            }
            if (c.getTypeData() != null && c.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeDaty) == 0) {
                troisiemeTd = "<a href='javascript:cal.popup()'><img src='calendar/img/cal.gif' alt='Cliquez ici pour choisir une date' width=16 height=16 border=0 align='absmiddle' /></a>";
                temp = temp + troisiemeTd;
            }
            temp = temp + "</div>";
        }
        temp = temp + "<div class=\"form-group p-1 col-lg-12 col-md-12 col-sm-12 d-flex justify-content-end align-items-center filter-option \">";
        temp = temp + "<button type='reset' name='Submit2' class='btn btn-apj-primary' tabIndex='" + this.getListeChamp().length + 2 + "' multi-lang>R&eacute;initialiser</button>";
        temp = temp + "<button type='submit' name='Submit2' class='btn btn-apj-secondary' tabIndex='" + this.getListeChamp().length + 1 + "' multi-lang>Enregistrer</button> ";
        temp = temp + "</div>";
        temp = temp + this.getHtmlAutocomplete();
        temp = temp + this.getHtmlChampInvisible();
        this.setHtmlInsert(temp);
    }

    public ArrayList<Champ> getChampInvisible() {
        ArrayList<Champ> lc = new ArrayList<Champ>();
        for (Champ c : this.getListeChamp()) {
            if (c.getVisible()) continue;
            lc.add(c);
        }
        return lc;
    }

    public String getHtmlChampInvisible() throws Exception {
        String retour = "";
        ArrayList<Champ> lc = this.getChampInvisible();
        for (Champ c : lc) {
            retour = retour + c.getHtml();
        }
        return retour;
    }

    public String getHtmlAutocomplete() throws Exception {
        String retour = "<script>";
        if (this.getListeChamp().length > 0) {
            HashMap<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < this.getListeChamp().length; ++i) {
                Champ cTemp = this.getListeChamp()[i];
                if (cTemp.getValeur_ac() == null) continue;
                map.put("data_" + cTemp.getNom(), cTemp.getValeur_ac());
            }
            for (Map.Entry me : map.entrySet()) {
                retour = retour + "var " + me.getKey() + " = " + me.getValue() + "; \n";
            }
        }
        retour = retour + " jQuery(document).ready(function () {";
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            Champ c = this.getListeChamp()[i];
            if (c.getValeur_ac() != null && c.getPageAppel() != null && c.getPageAppel().compareToIgnoreCase("") != 0) {
                String nameInput = "";
                String[] valColonne = c.getNom().split("_");
                nameInput = valColonne.length > 1 && Utilitaire.checkNumber(valColonne[1]) ? valColonne[0] + "libelle_" + valColonne[1] : c.getNom() + "libelle";
                retour = retour + "$(document).on('keydown', '#" + nameInput + "', function (e) {\n            var keyCode = e.keyCode || e.which;\n            if ((keyCode == 9 || keyCode == 13)) {\n                var prest = $('#" + nameInput + "').val();\n                if (prest != null && prest.trim() != \"\") {\n                    var temp = prest.split(\"::\");\n                    $('#" + nameInput + "').val(temp[0].trim());\n                }\n\n            } else {\n                $('#" + nameInput + "').autocomplete({\n                    source: function (request, response) {\n                         var matches = $.map(data_" + c.getNom() + ", function (acItem) {\n                            if (acItem.toUpperCase().indexOf(request.term.toUpperCase()) === 0) {\n                                return acItem;\n                            }\n                        });\n                        response(matches);\n                    },\n                    minLength: 0,\n                    select: function (e, ui) {\n                        var selectedObj = ui.item;\n                        var prest = selectedObj.value;\n                        var temp = prest.split(\"::\");\n                        var compte = temp[1].trim();\n$('#" + nameInput + "').focusout(function() {                         $('#" + nameInput + "').val(compte);\n                        console.log(compte);\n                        console.log(selectedObj);\n                        console.log(prest);\n                        console.log(temp);\n                        document.querySelector('input[name=\"" + nameInput + "auto\"]').value = compte;\n});                    }\n                });\n            }\n        });";
            } else if (c.getValeur_ac() != null) {
                retour = retour + "$(document).on('keydown', '#" + c.getNom() + "', function (e) {\n            var keyCode = e.keyCode || e.which;\n            if ((keyCode == 9 || keyCode == 13)) {\n                var prest = $('#" + c.getNom() + "').val();\n                if (prest != null && prest.trim() != \"\") {\n                    var temp = prest.split(\"::\");\n                    $('#" + c.getNom() + "').val(temp[0].trim());\n                }\n\n            } else {\n                $('#" + c.getNom() + "').autocomplete({\n                    source: function (request, response) {\n                        var matches = $.map(data_" + c.getNom() + ", function (acItem) {\n                            if (acItem.toUpperCase().indexOf(request.term.toUpperCase()) === 0) {\n                                return acItem;\n                            }\n                        });\n                        response(matches);\n                    },\n                    minLength: 0,\n                    select: function (e, ui) {\n                        var selectedObj = ui.item;\n                        var prest = selectedObj.value;\n                        var temp = prest.split(\"::\");\n                        var compte = temp[1].trim();\n$('#" + c.getNom() + "').focusout(function() {                         $('#" + c.getNom() + "').val(compte);\n                        console.log(compte);\n                        console.log(selectedObj);\n                        console.log(prest);\n                        console.log(temp);\n                        document.querySelector('input[name=\"" + c.getNom() + "auto\"]').value = compte;\n});                    }\n                });\n            }\n        });";
            }
            retour = retour + c.getAutocompleteDynamiqueJs();
        }
        retour = retour + "}); </script>";
        return retour;
    }

    public void makeHtmlUpdateTableauIndex() throws Exception {
        String temp = "";
        temp = temp + "<div class='row'>";
        temp = temp + "<div class='row col-md-12'>";
        temp = temp + "<div class='box'>";
        temp = temp + "<div class='box box-primary'>";
        temp = temp + "<div class='box-body'>";
        temp = temp + "<table class=\"table \">";
        temp = temp + "<tr>";
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            Champ c = this.getListeChamp()[i];
            if (!c.getVisible()) continue;
            c.setAutre("tabindex='" + (i + 1) + "'" + c.getAutre());
            temp = temp + "<th style='background-color:#103a8e'><label for='" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "' " + c.getAutre() + ">" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</label></th>";
        }
        temp = temp + "</tr>";
        for (int iLigne = 0; iLigne < this.getNbLigne(); ++iLigne) {
            String troisiemeTd = "";
            temp = temp + "<tr>";
            for (int iCol = 0; iCol < this.getListeChamp().length; ++iCol) {
                Champ ch = this.getListeChamp()[iCol];
                temp = temp + "<td>" + this.getListeChampTableau()[iLigne][iCol].getHtmlTableauInsert(iLigne) + "</td>";
                if (ch.getPageAppel() != null && ch.getPageAppel().compareToIgnoreCase("") != 0) {
                    troisiemeTd = "<input name='choix' type='button' class='submit btnheight' onclick=pagePopUp('" + ch.getPageAppel() + "?champReturn=" + ch.getNom() + "_" + iLigne + "') value=... />";
                    temp = temp + "<td>" + troisiemeTd + "</td>";
                }
                if (ch.getTypeData() == null || ch.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeDaty) != 0) continue;
                troisiemeTd = "<a href='javascript:cal.popup()'><img src='calendar/img/cal.gif' alt='Cliquez ici pour choisir une date' width=16 height=16 border=0 align='absmiddle' /></a>";
                temp = temp + "<td>" + troisiemeTd + "</td>";
            }
            temp = temp + "</tr>";
        }
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "<div class='box-footer'>";
        temp = temp + "<div class='box-footer borderless nopadding'>";
        temp = temp + "<button type='submit' name='Submit2' class='btn btn-success pull-right' style='margin-right: 25px;' tabIndex='" + this.getListeChamp().length + 1 + "'>Modifier</button> ";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        this.setHtmlTableauInsert(temp);
    }

    public String getBoutonsValiderAnnulerTabIndex() {
        String temp = "";
        temp = temp + "<input type='submit' name='Submit2' value='valider' class='submit' tabIndex='" + this.getListeChamp().length + 1 + "'>";
        temp = temp + "<input type='reset' name='Submit2' value='Annuler' class='submit' tabIndex='" + this.getListeChamp().length + 2 + "'>";
        return temp;
    }

    public String getListeCritereString() {
        int j;
        String valeur;
        Champ temp;
        String retour = "";
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            temp = this.getListeChamp()[i];
            valeur = temp.getValeur();
            if (temp.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeTexte) == 0) {
                valeur = Utilitaire.remplacePourcentage(valeur);
            }
            retour = retour + "&" + temp.getNom() + "=" + valeur;
        }
        if (this.getChampGroupe() != null) {
            for (j = 0; j < this.getChampGroupe().length; ++j) {
                temp = this.getChampGroupe()[j];
                valeur = temp.getValeur();
                if (temp.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeTexte) == 0) {
                    valeur = Utilitaire.remplacePourcentage(valeur);
                }
                retour = retour + "&" + temp.getNom() + "=" + valeur;
            }
        }
        if (this.getChampTableauAff() != null) {
            for (j = 0; j < this.getChampTableauAff().length; ++j) {
                temp = this.getChampTableauAff()[j];
                valeur = temp.getValeur();
                if (temp.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeTexte) == 0) {
                    valeur = Utilitaire.remplacePourcentage(valeur);
                }
                retour = retour + "&" + temp.getNom() + "=" + valeur;
            }
        }
        return retour;
    }

    public String getListeCritereStringCheckbox(PageRecherche pr) {
        int j;
        String valeur;
        Champ temp;
        String retour = "";
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            temp = this.getListeChamp()[i];
            if (temp.getEstMultiple()) {
                String[] valeurs = pr.getReq().getParameterValues(temp.getNom());
                if (valeurs == null || valeurs.length <= 0 || valeurs[0].isEmpty()) continue;
                for (int j2 = 0; j2 < valeurs.length; ++j2) {
                    retour = retour + "&" + temp.getNom() + "=" + valeurs[j2];
                }
                continue;
            }
            valeur = temp.getValeur();
            if (temp.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeTexte) == 0) {
                valeur = Utilitaire.remplacePourcentage(valeur);
            }
            retour = retour + "&" + temp.getNom() + "=" + valeur;
        }
        if (this.getChampGroupe() != null) {
            for (j = 0; j < this.getChampGroupe().length; ++j) {
                temp = this.getChampGroupe()[j];
                valeur = temp.getValeur();
                if (temp.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeTexte) == 0) {
                    valeur = Utilitaire.remplacePourcentage(valeur);
                }
                retour = retour + "&" + temp.getNom() + "=" + valeur;
            }
        }
        if (this.getChampTableauAff() != null) {
            for (j = 0; j < this.getChampTableauAff().length; ++j) {
                temp = this.getChampTableauAff()[j];
                valeur = temp.getValeur();
                if (temp.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeTexte) == 0) {
                    valeur = Utilitaire.remplacePourcentage(valeur);
                }
                retour = retour + "&" + temp.getNom() + "=" + valeur;
            }
        }
        return retour;
    }

    public void getAllDataFille(Connection c) throws Exception {
        bean.Champ[] champfle = ListeColonneTable.getFromListe(this.getObjet(), c);
        int nbChampFille = champfle.length;
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            if (i < nbChampFille) {
                this.getListeChamp()[i].findData(c);
                this.getListeChamp()[i].findDataAutoComplete(c);
                this.getListeChamp()[i].findAutocompleteDisplayValue(c);
                continue;
            }
            this.getListeChamp()[i].setBase(this.getListeChamp()[i % nbChampFille].getBase());
            this.getListeChamp()[i].setTab_ac(this.getListeChamp()[i % nbChampFille].getTab_ac());
            this.getListeChamp()[i].findAutocompleteDisplayValue(c);
        }
    }

    public void getAllData(Connection c) throws Exception {
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            if (this.isEstFille()) continue;
            this.getListeChamp()[i].findData(c);
            this.getListeChamp()[i].findDataAutoComplete(c);
            this.getListeChamp()[i].findAutocompleteDisplayValue(c);
        }
    }

    public Champ[] getListeChamp() {
        return this.listeChamp;
    }

    public void setListeChamp(Champ[] listeChamp) {
        this.listeChamp = listeChamp;
        for (Champ c : listeChamp) {
            c.setFormulaire(this);
        }
    }

    public void ajouterLignes(int nombreLigne) throws Exception {
        int premiereLigne;
        if (this.listeChamp == null || this.listeChamp.length == 0 || this.getNbLigne() <= 0 || nombreLigne <= 0) {
            return;
        }
        int nombreChampsParLigne = this.getNombreChampsParLigne();
        Champ[] champs = new Champ[this.listeChamp.length + nombreLigne * nombreChampsParLigne];
        System.arraycopy(this.listeChamp, 0, champs, 0, this.listeChamp.length);
        for (int ligne = premiereLigne = this.getNbLigne(); ligne < premiereLigne + nombreLigne; ++ligne) {
            int ligneSource = ligne % premiereLigne;
            for (int colonne = 0; colonne < nombreChampsParLigne; ++colonne) {
                Champ source = this.listeChamp[ligneSource * nombreChampsParLigne + colonne];
                String nom = source.getNom().substring(0, source.getNom().lastIndexOf(95)) + "_" + ligne;
                champs[this.listeChamp.length + (ligne - premiereLigne) * nombreChampsParLigne + colonne] = source.copierConfiguration(nom);
            }
        }
        this.setListeChamp(champs);
        this.setNbLigne(premiereLigne + nombreLigne);
    }

    private int getNombreChampsParLigne() {
        return this.listeChamp.length / this.getNbLigne();
    }

    public Champ[][] getListeChampTableau() {
        return this.listeChampTableau;
    }

    public void setListeChampTableau(Champ[][] listeChamp) {
        this.listeChampTableau = listeChamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setNbRangee(int nbRangee) {
        this.nbRangee = nbRangee;
    }

    public int getNbRangee() {
        return this.nbRangee;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml() throws Exception {
        if (this.html == null || this.html.compareToIgnoreCase("") == 0) {
            this.makeHtml();
        }
        return this.html + this.getHtmlChampCacheSupplementaire();
    }

    public void setTailleTableau(String tailleTableau) {
        this.tailleTableau = tailleTableau;
    }

    public String getTailleTableau() {
        return this.tailleTableau;
    }

    public void setCssTableau(String cssTableau) {
        this.cssTableau = cssTableau;
    }

    public String getCssTableau() {
        return this.cssTableau;
    }

    public void setObjet(ClassMAPTable objet) {
        this.objet = objet;
    }

    public ClassMAPTable getObjet() {
        return this.objet;
    }

    public void setCrtFormu(Champ[] crtFormu) {
        this.crtFormu = crtFormu;
        if (crtFormu != null) {
            for (Champ c : crtFormu) {
                c.setFormulaire(this);
            }
        }
    }

    public Champ[] getCrtFormu() {
        return this.crtFormu;
    }

    public void setNbIntervalle(int nbIntervalle) {
        this.nbIntervalle = nbIntervalle;
    }

    public int getNbIntervalle() {
        return this.nbIntervalle;
    }

    public void setHtmlTri(String htmlTri) {
        this.htmlTri = htmlTri;
    }

    public String getHtmlTri() throws Exception {
        if (this.htmlTri == null || this.htmlTri.compareToIgnoreCase("") == 0) {
            this.makeHtmlTri();
        }
        return this.htmlTri;
    }

    public void setHtmlButton(String htmlButton) {
        this.htmlButton = htmlButton;
    }

    public String getHtmlButton() {
        return this.htmlButton;
    }

    public void setHtmlInsert(String htmlInsert) {
        this.htmlInsert = htmlInsert;
    }

    public String getHtmlInsertTemplate() throws Exception {
        if (this.htmlInsert == null || this.htmlInsert.compareToIgnoreCase("") == 0) {
            this.makeHtmlInsertTemplate();
        }
        return this.htmlInsert;
    }

    public String getHtmlInsert() throws Exception {
        return this.getHtmlInsertTemplate();
    }

    public String getHtmlAdd() throws Exception {
        if (this.htmlInsert == null || this.htmlInsert.compareToIgnoreCase("") == 0) {
            this.makeHtmlInsert();
        }
        return this.htmlInsert;
    }

    public String getHtmlTableauInsert() throws Exception {
        return this.getHtmlTableauInsertTemplate();
    }

    public String getHtmlTableauInsertTemplate() throws Exception {
        if (this.htmlTableauInsert == null || this.htmlTableauInsert.compareToIgnoreCase("") == 0) {
            this.makeHtmlInsertTemplate();
        }
        return this.htmlTableauInsert;
    }

    public void setHtmlTableauInsert(String htmlTableauInsert) {
        this.htmlTableauInsert = htmlTableauInsert;
    }

    public void setChampGroupe(Champ[] champGroupe) {
        this.champGroupe = champGroupe;
    }

    public Champ[] getChampGroupe() {
        return this.champGroupe;
    }

    public void setHtmlGroupe(String htmlGroupe) {
        this.htmlGroupe = htmlGroupe;
    }

    public String getHtmlGroupe() {
        return this.htmlGroupe;
    }

    public Champ[] getChampTableauAff() {
        return this.champTableauAff;
    }

    public void setChampTableauAff(Champ[] champTableauAff) {
        this.champTableauAff = champTableauAff;
    }

    public String getHtmlTableauAff() {
        return this.htmlTableauAff;
    }

    public void setHtmlTableauAff(String htmlTableauAff) {
        this.htmlTableauAff = htmlTableauAff;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return this.cssClass;
    }

    public void makeHtmlInsertCommande() throws Exception {
        String temp = "";
        temp = temp + "<div class='row'>";
        temp = temp + "<div class='insertFormulaire'>";
        temp = temp + "<div>";
        temp = temp + "<div class='box box-primary'>";
        temp = temp + "<div class='box-body'>";
        temp = temp + "<table class=\"table \">";
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            Champ c = this.getListeChamp()[i];
            String troisiemeTd = "";
            if (c.getNom().compareToIgnoreCase("iduser") == 0) {
                c.setVisible(false);
            }
            if (!c.getVisible()) continue;
            c.setAutre("tabindex='" + (i + 1) + "'" + c.getAutre());
            temp = temp + "<tr>";
            temp = temp + "<th><label for='" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "' " + c.getAutre() + ">" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</label></th>";
            if (c.getPageAppel() != null && c.getPageAppel().compareToIgnoreCase("") != 0) {
                temp = temp + "<td>" + this.getListeChamp()[i].getInputHidden() + "</td>";
                troisiemeTd = "<input name='choix' type='button' class='submit' onclick=pagePopUp('" + c.getPageAppel() + "?champReturn=" + c.getChampReturn() + "&apresLienPageAppel=" + c.getApresLienPageappel() + "') value=... />";
                temp = temp + "<td>" + troisiemeTd + "</td>";
            } else {
                temp = temp + "<td>" + this.getListeChamp()[i].getHtmlInsert() + "</td>";
            }
            if (c.getTypeData() != null && c.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeDaty) == 0) {
                troisiemeTd = "<a href='javascript:cal.popup()'><img src='calendar/img/cal.gif' alt='Cliquez ici pour choisir une date' width=16 height=16 border=0 align='absmiddle' /></a>";
                temp = temp + "<td>" + troisiemeTd + "</td>";
            }
            temp = temp + "</tr>";
        }
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "<div class='box-footer'>";
        temp = temp + "<div class='box-footer borderless nopadding'>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        this.setHtmlInsert(temp);
    }

    public void makeHtmlInsertTableauIndex() throws Exception {
        this.makeHtmlInsertTableauIndexTemplate();
    }

    public void makeHtmlInsertTableauIndexTemplate() throws Exception {
        String temp = "";
        String acstring = "";
        temp = temp + "<div id='butfillejsp'>";
        temp = temp + "<div class='col-md-12 cardradius' style='background: white;padding: 15px;margin-top: 20px;'>";
        temp = temp + this.getUploadForm();
        temp = temp + "<h1 class=\"box-title cardtitle \" align=\"left\">" + this.getTitre() + "</h1>";
        temp = temp + "<input id='nbrLigne' name='nbrLigne' type='hidden' value='" + this.getNbLigne() + "'/>";
        temp = temp + "<input name='indexMultiple' id='indexMultiple' type='hidden' value='" + this.getNbLigne() + "'/>";
        temp = temp + "<div class='table-container nopadding borderless'>";
        temp = temp + "<table class='table ' style=\"margin-top: 15px;\">";
        temp = temp + "<thead>";
        temp = temp + "<tr>";
        String[] colonne = null;
        if (this.getColOrdre() != null) {
            colonne = this.getColOrdre();
        } else {
            bean.Champ[] champfle = ListeColonneTable.getFromListe(this.getObjet(), null);
            colonne = new String[champfle.length];
            for (int i = 0; i < champfle.length; ++i) {
                colonne[i] = champfle[i].getNomColonne();
            }
        }
        for (int i = 0; i < colonne.length; ++i) {
            Champ c = this.getChamp(colonne[i] + "_" + 0);
            if (i == 0) {
                temp = temp + "<th class='contenuetable ' style=' text-align: center' colspan='1'><input onclick=\"CocheToutCheckbox(this, '" + this.getCheckBoxName() + "')\" type=\"checkbox\"></th>";
            }
            if (c.getVisible()) {
                String labelAutre = c.getAutre();
                c.setAutre("tabindex='" + (i + 1) + "'" + c.getAutre());
                if (c.getLibelleAfficheAnnotation().equalsIgnoreCase("image")) {
                    c.setTaille(10);
                }
                if (c.getTaille() > 0) {
                    temp = temp + "<th class='contenuetable ' style=' width: " + c.getTaille() + "%;' colspan='1'><label for='" + c.getNom() + "' " + labelAutre + " multi-lang >" + c.getLibelleAfficheAnnotation() + "</label></th>";
                }
                if (!c.getNom().contains("motsClesss")) {
                    temp = temp + "<th class='contenuetable ' style='color:white;' colspan='1'><label for='" + c.getNom() + "' " + labelAutre + " multi-lang >" + c.getLibelleAfficheAnnotation() + "</label></th>";
                }
            }
            if (i != colonne.length - 1) continue;
            temp = temp + "<th style='color:white;' colspan='1'></th>";
        }
        temp = temp + "</tr>";
        temp = temp + "</thead>";
        temp = temp + "<tbody id='ajout_multiple_ligne'>";
        for (int iLigne = 0; iLigne < this.getNbLigne(); ++iLigne) {
            String idFille = "";
            String liendelete = "#";
            acstring = acstring + this.getHtmlAutocomplete(iLigne);
            String troisiemeTd = "";
            temp = temp + "<tr id='ligne-multiple-" + iLigne + "'>";
            temp = temp + "<td style=\"text-align: center;vertical-align: middle;\" align=center>";
            temp = this.isCheckAuto() && iLigne <= this.getNbrCheck() - 1 ? temp + "<input type='checkbox' value='" + iLigne + "' name='" + this.getCheckBoxName() + "' id='checkbox" + iLigne + "' checked/>" : temp + "<input type='checkbox' value='" + iLigne + "' name='" + this.getCheckBoxName() + "' id='checkbox" + iLigne + "'/>";
            temp = temp + "</td>";
            if (this.isEstFille() && this.getDataFille() != null && iLigne < this.getDataFille().length) {
                ClassMAPTable t = this.getDataFille()[iLigne];
                if (t != null) {
                    idFille = t.getTuppleID();
                }
                liendelete = "module.jsp?but=apresTarif.jsp&amp;id=" + idFille + "&amp;acte=delete&amp;bute=" + this.getApres() + "&rajoutLienFormu=" + this.getRajoutLien() + "&amp;classe=" + this.getObjet().getClassName();
            }
            for (int iCol = 0; iCol < colonne.length; ++iCol) {
                Champ ch = this.getChamp(colonne[iCol] + "_" + iLigne);
                if (ch.getPageAppel() != null && ch.getPageAppel().compareToIgnoreCase("") != 0) {
                    String autre = "";
                    if (ch.getColFiltre() != null && ch.getValFiltre() != null) {
                        for (int i = 0; i < ch.getColFiltre().length; ++i) {
                            autre = autre + "&" + ch.getColFiltre()[i] + "=" + ch.getValFiltre()[i];
                        }
                    }
                    temp = temp + "<td style=\"display: inline-flex;width: 100%;\">" + ch.getInputHidden();
                    troisiemeTd = "<input name='choix' type='button' class='submit' onclick=pagePopUp('" + ch.getPageAppel() + "?champReturn=" + ch.getChampReturn(iLigne) + "&apresLienPageAppel=" + ch.getApresLienPageappel() + autre + "') value=... />";
                    temp = temp + troisiemeTd + "</td>";
                    continue;
                }
                temp = ch.getVisible() ? temp + "<td>" + ch.getHtmlInsert() + "</td>" : temp + ch.getHtmlTableauInsert(iLigne);
            }
            temp = temp + "<td style=\"text-align: center;vertical-align: middle;\"><a href='javascript:void(0)' onclick='delete_line(" + iLigne + ")'><span class='glyphicon glyphicon-remove'></span></a></td>";
            temp = temp + "<td style=\"text-align: center;vertical-align: middle;\"><a href='javascript:void(0)' onclick='duplicateSingleRow(" + iLigne + ")'><span class='glyphicon glyphicon-duplicate'></span></a></td>";
            temp = temp + "</tr>";
        }
        temp = temp + "</tbody>";
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "<div class='box-footer borderless nopadding'>";
        Gson gson = new Gson();
        String json = gson.toJson((Object)this.getListeChamp());
        temp = temp + "<button type='submit' name='Submit2' class='btn btn-primary btnradius pull-right ' style='margin-right: 25px;' tabIndex='" + this.getListeChamp().length + 1 + "' multi-lang>Enregistrer</button> ";
        temp = temp + "<button type='reset' name='Submit2' class='btn btn-secondary btnradius pull-right ' style='margin-right: 15px;' tabIndex='" + this.getListeChamp().length + 2 + "' multi-lang>R&eacute;initialiser</button>";
        temp = temp + "<button type='submit' name='acte' value='deleteFille' class='btn btn-danger btnradius pull-left' style='margin-right: 15px;' tabIndex='" + this.getListeChamp().length + 1 + "' multi-lang>Supprimer</button>";
        temp = temp + "<button type='button' class='btn btn-tertiary pull-right ' onclick='add_line_tab(JSON.stringify(" + json + "))' style='margin-right: 25px;min-width: fit-content;' id='addLines' multi-lang>Ajouter une ligne</button>";
        temp = temp + "<button type='button' class='btn btn-tertiary pull-right ' onclick='add_line_tabs(JSON.stringify(" + json + "))' style='margin-right: 15px;min-width: fit-content;' id='addLine' multi-lang>Ajouter dix lignes</button>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + acstring;
        temp = temp + this.getHtmlAutocomplete();
        temp = temp + this.getHtmlChampInvisible();
        temp = temp + "<script>\n(function(){\n  var tbody = document.getElementById('ajout_multiple_ligne');\n  if(!tbody) return;\n  function buildGrid(){\n    var rows = Array.from(tbody.querySelectorAll('tr'));\n    var grid = [];\n    rows.forEach(function(r){\n      var focusables = Array.from(r.querySelectorAll('input:not([type=hidden]):not([disabled]), select:not([disabled]), textarea:not([disabled]), button:not([disabled])')).filter(function(el){ return el.offsetParent !== null; });\n      if(focusables.length>0) grid.push(focusables);\n    });\n    return grid;\n  }\n  document.addEventListener('keydown', function(e){\n    if(e.key !== 'Tab') return;\n    var active = document.activeElement;\n    if(!tbody.contains(active)) return;\n    e.preventDefault();\n    var grid = buildGrid();\n    var found = null;\n    for(var r=0;r<grid.length;r++){\n      for(var c=0;c<grid[r].length;c++){\n        if(grid[r][c]===active){ found={r:r,c:c}; break; }\n      }\n      if(found) break;\n    }\n    if(!found) return;\n    var r = found.r; var c = found.c;\n    if(e.shiftKey){\n      // previous: move left in row, else to end of previous row\n      if(c>0) c = c-1; else { r = (r-1+grid.length)%grid.length; c = grid[r].length-1; }\n    } else {\n      // next: move right in row, else to first of next row\n      if(c < grid[r].length-1) c = c+1; else { r = (r+1)%grid.length; c = 0; }\n    }\n    try{\n      var target = grid[r] && grid[r][c];\n      if(target) target.focus();\n    }catch(err){}\n  }, true);\n})();\n</script>";
        this.setHtmlTableauInsert(temp);
    }

    public void makeHtmlInsertMultiple() throws Exception {
        String temp = "";
        String acstring = "";
        temp = temp + "<div class='row' >";
        temp = temp + "<div class='col-md-12 cardradius' style='background: white;padding: 15px;margin-top: 20px;'>";
        temp = temp + "<h1 class=\"box-title cardtitle \" align=\"left\">" + this.getTitre() + "</h1>";
        temp = temp + "<input id='nbrLigne' name='nbrLigne' type='hidden' value='" + this.getNbLigne() + "'/>";
        temp = temp + "<input name='indexMultiple' id='indexMultiple' type='hidden' value='" + this.getNbLigne() + "'/>";
        temp = temp + "<table class='table ' style=\"margin-top: 15px;\">";
        temp = temp + "<tr>";
        String[] colonne = null;
        Champ[] champfle = this.getListeChamp();
        colonne = this.getColOrdre() != null ? this.getColOrdre() : new String[champfle.length];
        for (int i = 0; i < champfle.length; ++i) {
            Champ c = champfle[i];
            if (i == 0) {
                temp = temp + "<th class='contenuetable ' style=' text-align: center' colspan='1'><input onclick=\"CocheToutCheckbox(this, 'ids')\" type=\"checkbox\"></th>";
            }
            if (c.getVisible()) {
                c.setAutre("tabindex='" + (i + 1) + "'" + c.getAutre());
                temp = temp + "<th class='contenuetable ' style='color:white;' colspan='1'><label for='" + c.getNom() + "' " + c.getAutre() + ">" + c.getLibelleAfficheAnnotation() + "</label></th>";
            }
            if (i != colonne.length - 1) continue;
            temp = temp + "<th style='color:white;' colspan='1'></th>";
        }
        temp = temp + "</tr>";
        temp = temp + "<tbody id='ajout_multiple_ligne'>";
        Champ[] nouvelleListeChamp = new Champ[this.nbLigne * this.getListeChamp().length];
        int iNvLC = 0;
        for (int iLigne = 0; iLigne < this.getNbLigne(); ++iLigne) {
            String idFille = "";
            String liendelete = "#";
            acstring = acstring + this.getHtmlAutocomplete(iLigne);
            String troisiemeTd = "";
            temp = temp + "<tr id='ligne-multiple-" + iLigne + "'>";
            temp = temp + "<td style=\"text-align: center;vertical-align: middle;\" align=center><input type='checkbox' value='" + iLigne + "' name='ids' id='checkbox" + iLigne + "'/></td>";
            for (int iCol = 0; iCol < champfle.length; ++iCol) {
                Champ ch = champfle[iCol];
                ch.setNom(ch.getNom().split("_")[0]);
                ch.setNom(ch.getNom() + "_" + iLigne);
                if (ch.getPageAppel() != null && ch.getPageAppel().compareToIgnoreCase("") != 0) {
                    String autre = "";
                    if (ch.getColFiltre() != null && ch.getValFiltre() != null) {
                        for (int i = 0; i < ch.getColFiltre().length; ++i) {
                            autre = autre + "&" + ch.getColFiltre()[i] + "=" + ch.getValFiltre()[i];
                        }
                    }
                    ch.makeHtml();
                    temp = temp + "<td style=\"display: inline-flex;width: 100%;\">" + ch.getInputHidden();
                    troisiemeTd = "<input name='choix' type='button' class='submit' onclick=pagePopUp('" + ch.getPageAppel() + "?champReturn=" + ch.getChampReturn(iLigne) + "&apresLienPageAppel=" + ch.getApresLienPageappel() + autre + "') value=... />";
                    temp = temp + troisiemeTd + "</td>";
                } else {
                    ch.makeHtml();
                    temp = ch.getVisible() ? temp + "<td>" + ch.getHtml() + "</td>" : temp + ch.getHtml();
                }
                Champ nch = new Champ(ch.getNom());
                nch.setType(ch.getType());
                nch.setHtml(ch.getHtml());
                nouvelleListeChamp[iNvLC] = ch;
                ++iNvLC;
            }
            temp = temp + "<td style=\"text-align: center;vertical-align: middle;\"><a href='javascript:void(0)' onclick='delete_line(" + iLigne + ")'><span class='glyphicon glyphicon-remove'></span></a></td>";
            temp = temp + "</tr>";
        }
        this.setListeChamp(nouvelleListeChamp);
        temp = temp + "</tbody>";
        temp = temp + "</table>";
        temp = temp + "<div class='box-footer borderless nopadding'>";
        Gson gson = new Gson();
        String json = gson.toJson((Object)this.getListeChamp());
        temp = temp + "<button type='submit' name='Submit2' class='btn btn-primary btnradius pull-right ' style='margin-right: 25px;' tabIndex='" + this.getListeChamp().length + 1 + "' multi-lang>Enregistrer</button> ";
        temp = temp + "<button type='reset' name='Submit2' class='btn btn-secondary btnradius pull-right ' style='margin-right: 15px;' tabIndex='" + this.getListeChamp().length + 2 + "' multi-lang>R&eacute;initialiser</button>";
        temp = temp + "<button type='button' class='btn btn-tertiary pull-right ' onclick='add_line_tab(JSON.stringify(" + json + "))' style='margin-right: 25px;min-width: fit-content;'>Ajouter une ligne</button>";
        temp = temp + "<button type='button' class='btn btn-tertiary pull-right ' onclick='add_line_tabs(JSON.stringify(" + json + "))' style='margin-right: 15px;min-width: fit-content;'>Ajouter dix lignes</button>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + acstring;
        temp = temp + this.getHtmlAutocomplete();
        temp = temp + this.getHtmlChampInvisible();
        this.setHtmlTableauInsert(temp);
    }

    public void makeHtmlInsertTableauSimple() throws Exception {
        String temp = "";
        String acstring = "";
        temp = temp + "<input id='nbrLigne' name='nbrLigne' type='hidden' value='" + this.getNbLigne() + "'/>";
        temp = temp + "<input name='indexMultiple' id='indexMultiple' type='hidden' value='" + this.getNbLigne() + "'/>";
        temp = temp + "<table class='table table-insert'>";
        temp = temp + "<tr class=\"table-header\">";
        String[] colonne = null;
        if (this.getColOrdre() != null) {
            colonne = this.getColOrdre();
        } else {
            bean.Champ[] champfle = ListeColonneTable.getFromListe(this.getObjet(), null);
            colonne = new String[champfle.length];
            for (int i = 0; i < champfle.length; ++i) {
                colonne[i] = champfle[i].getNomColonne();
            }
        }
        for (int i = 0; i < colonne.length; ++i) {
            Champ c = this.getChamp(colonne[i] + "_" + 0);
            if (i == 0) {
                temp = temp + "<th style='background-color:#bed1dd; text-align: center' colspan='1'><input onclick=\"CocheToutCheckbox(this, 'ids')\" type=\"checkbox\" class=\"form-check-input search-check\"></th>";
            }
            if (!c.getVisible()) continue;
            temp = temp + "<th class='apj-label' " + c.getAutre() + ">" + c.getLibelleAfficheAnnotation() + "</th>";
        }
        temp = temp + "<th></th>";
        temp = temp + "</tr>";
        for (int iLigne = 0; iLigne < this.getNbLigne(); ++iLigne) {
            String idFille = "";
            String liendelete = "#";
            acstring = acstring + this.getHtmlAutocomplete(iLigne);
            String troisiemeTd = "";
            temp = temp + "<tr id='ligne-multiple-" + iLigne + "'>";
            temp = temp + "<td align=center><input type='checkbox' value='" + iLigne + "' name='id' id='checkbox" + iLigne + "' class=\"form-check-input search-check\"/></td>";
            if (this.isEstFille() && this.getDataFille() != null && iLigne < this.getDataFille().length) {
                ClassMAPTable t = this.getDataFille()[iLigne];
                if (t != null) {
                    idFille = t.getTuppleID();
                }
                liendelete = "module.jsp?but=apresTarif.jsp&amp;id=" + idFille + "&amp;acte=delete&amp;bute=" + this.getApres() + "&rajoutLienFormu=" + this.getRajoutLien() + "&amp;classe=" + this.getObjet().getClassName();
            }
            for (int iCol = 0; iCol < colonne.length; ++iCol) {
                Champ ch = this.getChamp(colonne[iCol] + "_" + iLigne);
                if (ch.getPageAppel() != null && ch.getPageAppel().compareToIgnoreCase("") != 0) {
                    String autre = "";
                    if (ch.getColFiltre() != null && ch.getValFiltre() != null) {
                        for (int i = 0; i < ch.getColFiltre().length; ++i) {
                            autre = autre + "&" + ch.getColFiltre()[i] + "=" + ch.getValFiltre()[i];
                        }
                    }
                    temp = temp + "<td style=\"display: inline-flex;\">" + ch.getInputHidden();
                    troisiemeTd = "<input name='choix' type='button' class='submit' onclick=pagePopUp('" + ch.getPageAppel() + "?champReturn=" + ch.getChampReturn(iLigne) + "&apresLienPageAppel=" + ch.getApresLienPageappel() + autre + "') value=... />";
                    temp = temp + troisiemeTd + "</td>";
                } else {
                    temp = ch.getVisible() ? temp + "<td>" + ch.getHtmlTableauInsert(iLigne) + "</td>" : temp + ch.getHtmlTableauInsert(iLigne);
                }
                if (ch.getTypeData() == null || ch.getTypeData().compareToIgnoreCase(ConstanteAffichage.typeDaty) != 0) continue;
                troisiemeTd = "<a href='javascript:cal.popup()'><img src='calendar/img/cal.gif' alt='Cliquez ici pour choisir une date' width=16 height=16 border=0 align='absmiddle' /></a>";
                temp = temp + "<td>" + troisiemeTd + "</td>";
                temp = temp + "<td></td>";
            }
            temp = temp + "<td class='cancel'><a href='" + liendelete + "'><span class=\"\">&times;</span></a></td>";
            temp = temp + "</tr>";
        }
        temp = temp + "<tr>";
        temp = temp + "<td colspan='100%'>";
        Gson gson = new Gson();
        String json = gson.toJson((Object)this.getListeChamp());
        temp = temp + "<button type='submit' name='Submit2' class='btn btn-apj-secondary pull-right' style='margin-right: 25px;' tabIndex='" + this.getListeChamp().length + 1 + "' multi-lang>Enregistrer</button> ";
        temp = temp + "<button type='reset' name='Submit2' class='btn btn-apj-primary pull-right' style='margin-right: 15px;' tabIndex='" + this.getListeChamp().length + 2 + "' multi-lang>R&eacute;initialiser</button>";
        temp = temp + "<button type='button' class='btn btn-tertiary pull-right' onclick='add_line_tab(JSON.stringify(" + json + "))' style='margin-right: 15px;min-width: fit-content;' multi-lang>Ajouter une ligne</button>";
        temp = temp + "<button type='button' class='btn btn-tertiary pull-right' onclick='add_line_tabs(JSON.stringify(" + json + "))' style='margin-right: 15px;min-width: fit-content;' multi-lang>Ajouter dix lignes</button>";
        temp = temp + "</td>";
        temp = temp + "</table>";
        temp = temp + acstring;
        temp = temp + this.getHtmlAutocomplete();
        temp = temp + this.getHtmlChampInvisible();
        this.setHtmlTableauInsert(temp);
    }

    public String getHtmlAutocomplete(int k) throws Exception {
        String retour = "";
        return retour;
    }

    public Champ[] getChampFille(String colonne) throws Exception {
        Champ[] tChamp = new Champ[this.getNbLigne()];
        int iV = 0;
        bean.Champ[] champfle = ListeColonneTable.getFromListe(this.getObjet(), null);
        for (int ligne = 0; ligne < this.getNbLigne(); ++ligne) {
            for (int i = 0; i < champfle.length; ++i) {
                if (colonne.compareToIgnoreCase(champfle[i].getNomColonne()) != 0) continue;
                tChamp[iV] = this.getChamp(colonne + "_" + ligne);
                ++iV;
            }
        }
        return tChamp;
    }

    public void makeChampFormuLigne(String[] aAff) throws Exception {
        Vector<Champ> liste = new Vector<Champ>();
        for (int i = 0; i < aAff.length; ++i) {
            String type = ListeColonneTable.getChamp(this.getObjet(), aAff[i]).getTypeColonne();
            liste.add(new Champ(aAff[i]));
        }
        bean.Champ[] nomCol = ListeColonneTable.getFromListe(this.getObjet(), null);
        Object[] listeForm = new Champ[liste.size()];
        liste.copyInto(listeForm);
        this.setListeChamp((Champ[])listeForm);
    }

    public String makeHtmlUpdateTableau(String libEntete, String valeur, int iLigne) throws Exception {
        String temp = "";
        String troisiemeTd = "";
        Champ ch = this.getChamp(libEntete);
        if (ch != null && ch.getVisible()) {
            temp = temp + "<td><div class='d-flex gap-2'>" + ch.getHtmlTableauInsert(iLigne, valeur);
            if (ch.getPageAppel() != null && ch.getPageAppel().compareToIgnoreCase("") != 0) {
                troisiemeTd = "<input name='choix' type='button' class='submit' onclick=pagePopUp('" + ch.getPageAppel() + "?champReturn=" + ch.getNom() + "_" + iLigne + "') value=... />";
                temp = temp + troisiemeTd;
            }
            temp = temp + "</div></td>";
        }
        return temp;
    }

    public String getApres() {
        return this.apres;
    }

    public void setApres(String apres) {
        this.apres = apres;
    }

    public String getRajoutLien() {
        return this.rajoutLien;
    }

    public void setRajoutLien(String rajoutLien) {
        this.rajoutLien = rajoutLien;
    }

    public Champ[] getChampsAvecPrefix(String prefix) {
        Vector<Champ> v = new Vector<Champ>();
        Champ[] initial = this.getListeChamp();
        for (int i = 0; i < initial.length; ++i) {
            if (!initial[i].getNom().startsWith(prefix)) continue;
            v.add(initial[i]);
            System.out.println("Champ :" + initial[i].getNom());
        }
        Object[] retour = new Champ[v.size()];
        v.copyInto(retour);
        return retour;
    }

    public String getUploadForm() {
        String ret = "";
        System.out.println(this.getPageActuelle() + " | " + this.getClasseFille());
        ret = ret + "<div id=\"uploadBox\"><div style=\"margin-bottom: 1rem;padding: 0;\" class=\"col-md-12 mb-5 import-input\">\n<input name=\"classeUpload\" type=\"hidden\" id=\"classeUpload\" value=\"" + this.getClasseFille() + "\"><input name=\"previousUrl\" type=\"hidden\" id=\"previousUrl\" value=\"" + this.getPageActuelle() + "\">  <div class=\"form-input\" style='margin-right:8px' >\n    <label class=\"input-label\" for=\"upload\" multi-lang>Entrez le chemin de votre fichier Excel</label>\n    <span class=\"col-md-12 nopadding\">\n      <input name=\"file\" type=\"file\" class=\"form-control\" id=\"fileInput\" value=\"\" accept=\".xls\">\n    </span>\n  </div>\n  <button type=\"button\" class=\"btn btn-secondary\" style=\"min-height: 34px;height:34px;\" onclick=\"uploadFormWS(event, 'uploadBox')\" multi-lang>Importer</button></div></div>";
        return ret;
    }

    public String getRechercheMotsClesss() {
        String val = "";
        if (this.getReqqq() != null && this.getReqqq().getParameter("motsclesss") != null) {
            val = Utilitaire.escapeHtml(this.getReqqq().getParameter("motsclesss"));
        }
        String motsClesss = "            <br>\n            <div class=\"mots-cless\">\n                <div class=\"label-motsclesss\">\n                    <label class=\"input-label\" for=\"motsclesss\">Recherche par mots-cl&eacute;s</label>\n                </div>\n                <div class=\"input-motsclesss\" style=\"display: -webkit-inline-box\">\n                    <input style=\"height: 100%; margin-right: 8px;\" class=\"form-control\" id=\"motsclesss\" type=\"text\" name=\"motsclesss\" value='" + val + "' onkeydown=\"if(event.key==='Enter'||event.keyCode===13){event.preventDefault();var f=this.form||this.closest('form');if(f){if(typeof f.onsubmit==='function'){var r=f.onsubmit();if(r===false)return;}f.submit();}}\">\n                    <button class=\"btn btn-secondary btn-small btn-bg-white\" type=\"submit\"><i class='material-symbols-rounded'>search</i></button>\n                </div>\n            </div>";
        return motsClesss;
    }
}

