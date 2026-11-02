/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  user.UserEJB
 */
package affichage;

import affichage.Ac_object;
import affichage.Formulaire;
import affichage.PageConsulteLien;
import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.LibelleAffichage;
import constante.ConstanteAffichage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import user.UserEJB;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class Champ {
    private String nom;
    private String defaut = "";
    private String css = "form-control";
    private String type = ConstanteAffichage.typeTextBox;
    private String libelle;
    private String pageAppel;
    private String cssLibelle = "left";
    private String html;
    private int taille;
    private String valeur;
    String valeurDirect;
    private String typeData = ConstanteAffichage.typeTexte;
    private transient Field attribut;
    private int estIntervalle = 0;
    private String autre;
    private String libelleAffiche;
    private String htmlInsert;
    private boolean visible = true;
    private boolean photo = false;
    private String htmlTableauInsert;
    private String htmlTableau;
    private String champReturn;
    private String apresLienPageappel = "";
    private String urlFiche = "";
    private boolean autoComplete = false;
    private String valeur_ac;
    private String[] colFiltre;
    private String[] valFiltre;
    private String pageAddnew;
    private String ac_affiche;
    private String ac_valeur;
    private String ac_nomTable;
    private String ac_aWhere;
    private boolean autocomplete = false;
    private Ac_object[] tab_ac;
    private ClassMAPTable[] base;
    private ClassMAPTable mine;
    private transient ClassMAPTable objet;
    private boolean estMultiple = false;
    private boolean estRadio = false;
    private String ac_classeMapping;
    private boolean autocompleteDynamique;
    private boolean useMotCle;
    private String champUrl;
    private String pageAppelInsert;
    private String champRetour = "";
    private String champRetourMapping = "";
    private UserEJB userEJB;
    String tailleImage = "100";
    String autreHidden = "";
    private boolean autocomplete2 = false;
    private ClassMAPTable classMAPTableToFind;
    private String nomColonneAutoComplete;
    private String nomColonneAutoCompleteToReturn;
    private String champDependant = "";
    private String jsonData;
    private boolean isPropre;
    private boolean isDateHeure = false;
    private boolean estMoitier = false;
    private boolean estVide;
    private boolean recherchePrecis = false;
    transient Formulaire formulaire;
    private String valeurLibelle;
    PageConsulteLien lien;

    public boolean isEstVide() {
        return this.estVide;
    }

    public void setEstVide(boolean estVide) {
        this.estVide = estVide;
    }

    public void ajouterVide() {
        this.setEstVide(true);
    }

    public boolean getEstMoitier() {
        return this.estMoitier;
    }

    public void setEstMoitier(boolean estMoitier) {
        this.estMoitier = estMoitier;
    }

    public boolean getEstRadio() {
        return this.estRadio;
    }

    public void setEstRadio(boolean estRadio) {
        this.estRadio = estRadio;
    }

    public boolean getIsDateHeure() {
        return this.isDateHeure;
    }

    public void setIsDateHeure(boolean dateHeure) {
        this.isDateHeure = dateHeure;
    }

    public boolean isPropre() {
        return this.isPropre;
    }

    public void setPropre(boolean propre) {
        this.isPropre = propre;
    }

    public String getJsonData() {
        return this.jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getChampDependant() {
        return this.champDependant;
    }

    public void setChampDependant(String champDependant) {
        this.champDependant = champDependant;
    }

    public String getNomColonneAutoCompleteToReturn() {
        return this.nomColonneAutoCompleteToReturn;
    }

    public void setNomColonneAutoCompleteToReturn(String nomColonneAutoCompleteToReturn) {
        this.nomColonneAutoCompleteToReturn = nomColonneAutoCompleteToReturn;
    }

    public String getNomColonneAutoComplete() {
        return this.nomColonneAutoComplete;
    }

    public void setNomColonneAutoComplete(String nomColonneAutoComplete) {
        this.nomColonneAutoComplete = nomColonneAutoComplete;
    }

    public ClassMAPTable getClassMAPTableToFind() {
        return this.classMAPTableToFind;
    }

    public void setClassMAPTableToFind(ClassMAPTable classMAPTableToFind) {
        this.classMAPTableToFind = classMAPTableToFind;
    }

    public boolean isAutocomplete2() {
        return this.autocomplete2;
    }

    public void setAutocomplete2(boolean autocomplete2) {
        this.autocomplete2 = autocomplete2;
    }

    public String getPageAddnew() {
        return this.pageAddnew;
    }

    public void setPageAddnew(String pageAddnew) {
        this.pageAddnew = pageAddnew;
    }

    public String getAutreHidden() {
        return this.autreHidden;
    }

    public void setAutreHidden(String autreHidden) {
        this.autreHidden = autreHidden;
    }

    public String getTailleImage() {
        return this.tailleImage;
    }

    public void setTailleImage(String tailleImage) {
        this.tailleImage = tailleImage;
    }

    public UserEJB getUserEJB() {
        return this.userEJB;
    }

    public void setUserEJB(UserEJB userEJB) {
        this.userEJB = userEJB;
    }

    public String getValeurDirect() {
        return this.valeurDirect;
    }

    public void setValeurDirect(String valeurDirect) {
        this.valeurDirect = valeurDirect;
    }

    public String getChampRetour() {
        return this.champRetour;
    }

    public void setPageAppel(String pageAppel, String champReturnn, String pageInsert) {
        this.pageAppel = pageAppel;
        this.champReturn = champReturnn;
        this.pageAddnew = pageInsert;
    }

    public void setChampRetour(String champRetour) {
        this.champRetour = champRetour;
    }

    public String getChampRetourMapping() {
        return this.champRetourMapping;
    }

    public void setChampRetourMapping(String champRetourMapping) {
        this.champRetourMapping = champRetourMapping;
    }

    public String getChampUrl() {
        return this.champUrl;
    }

    public void setChampUrl(String champUrl) {
        this.champUrl = champUrl;
    }

    public String getPageAppelInsert() {
        return this.pageAppelInsert;
    }

    public static void setPageAppelInsert(Champ[] tChamps, String pageAppelInsert) {
        for (Champ champ : tChamps) {
            champ.setPageAppelInsert(pageAppelInsert);
        }
    }

    public static void setPageAppelInsert(Champ[] tChamps, String pageAppelInsert, String champUrl) {
        for (Champ champ : tChamps) {
            champ.setPageAppelInsert(pageAppelInsert, champ.getNom() + ";" + champ.getNom() + "libelle", champUrl);
        }
    }

    public void setPageAppelInsert(String pageAppelInsert) {
        this.pageAppelInsert = pageAppelInsert;
        this.champReturn = this.getNom() + ";" + this.getNom() + "libelle";
        this.champUrl = "id;id";
    }

    public void setPageAppelInsert(String pageAppelInsert, String champUrl) {
        this.pageAppelInsert = pageAppelInsert;
        this.champReturn = this.getNom() + ";" + this.getNom() + "libelle";
        this.champUrl = champUrl;
    }

    public static void setPageAppelInsert(Champ[] tChamps, String pageAppelInsert, String champReturn, String champUrl) {
        for (Champ champ : tChamps) {
            champ.setPageAppelInsert(pageAppelInsert, champReturn, champUrl);
        }
    }

    public void setPageAppelInsert(String pageAppelInsert, String champReturn, String champUrl) {
        this.pageAppelInsert = pageAppelInsert;
        this.champReturn = champReturn;
        this.champUrl = champUrl;
    }

    public String getHtmlPageAppelInsert() {
        String rep = "";
        if (this.pageAppelInsert != null) {
            rep = rep + "<button type='button' class='btn btn-secondary btn-small' onclick=pagePopUp('modulePopup.jsp?but=" + this.pageAppelInsert + "&champReturn=" + this.getChampReturn() + "&champUrl=" + this.champUrl + this.getApresLienPageappel() + "')><i class=\"material-symbols-rounded\">add</i></button>";
        }
        return rep;
    }

    public Formulaire getFormulaire() {
        return this.formulaire;
    }

    public void setFormulaire(Formulaire formulaire) {
        this.formulaire = formulaire;
    }

    public ClassMAPTable getMine() {
        return this.mine;
    }

    public void setMine(ClassMAPTable mine) {
        this.mine = mine;
    }

    public boolean getEstMultiple() {
        return this.estMultiple;
    }

    public String getAc_classeMapping() {
        return this.ac_classeMapping;
    }

    public void setAc_classeMapping(String ac_classeMapping) {
        this.ac_classeMapping = ac_classeMapping;
    }

    public boolean isAutocompleteDynamique() {
        return this.autocompleteDynamique;
    }

    public void setAutocompleteDynamique(boolean autocompleteDynamique) {
        this.autocompleteDynamique = autocompleteDynamique;
    }

    public boolean isUseMotCle() {
        return this.useMotCle;
    }

    public void setUseMotCle(boolean useMotCle) {
        this.useMotCle = useMotCle;
    }

    public static void setPageAppelComplete(Champ[] tChamps, String ac_classeMapping, String ac_valeur, String ac_nomTable) {
        for (Champ champ : tChamps) {
            champ.setPageAppelComplete(ac_classeMapping, ac_valeur, ac_nomTable);
        }
    }

    public static void setPageAppelComplete(Champ[] tChamps, String ac_classeMapping, String ac_valeur, String ac_nomTable, String champRetour, String champRetourMapping) {
        for (int i = 0; i < tChamps.length; ++i) {
            tChamps[i].setPageAppelComplete(ac_classeMapping, ac_valeur, ac_nomTable);
            tChamps[i].setChampRetour(champRetour + ";" + champRetour);
            int nb_mappings = champRetourMapping.split(";").length;
            String[] tmappings = new String[nb_mappings + nb_mappings];
            String[] mappings = champRetourMapping.split(";");
            for (int a = 0; a < mappings.length; ++a) {
                int num = a + nb_mappings;
                tmappings[a] = mappings[a] + "_" + i;
                tmappings[num] = mappings[a] + "_" + i + "libelle";
                if (!mappings[a].contains("libelle")) continue;
                tmappings[a] = mappings[a].replace("libelle_" + i, "_" + i + "libelle");
            }
            tChamps[i].setChampRetourMapping(Arrays.stream(tmappings).collect(Collectors.joining(";")));
        }
    }

    public static void setPageAppelCompleteAWhere(Champ[] tChamps, String ac_classeMapping, String ac_valeur, String ac_nomTable, String champRetour, String champRetourMapping, String AWhere) {
        for (int i = 0; i < tChamps.length; ++i) {
            tChamps[i].setPageAppelCompleteAWhere(ac_classeMapping, ac_valeur, ac_nomTable, champRetour, champRetourMapping, AWhere);
            tChamps[i].setChampRetour(champRetour + ";" + champRetour);
            int nb_mappings = champRetourMapping.split(";").length;
            String[] tmappings = new String[nb_mappings + nb_mappings];
            String[] mappings = champRetourMapping.split(";");
            for (int a = 0; a < mappings.length; ++a) {
                int num = a + nb_mappings;
                tmappings[a] = mappings[a] + "_" + i;
                tmappings[num] = mappings[a] + "_" + i + "libelle";
                if (!mappings[a].contains("libelle")) continue;
                tmappings[a] = mappings[a].replace("libelle_" + i, "_" + i + "libelle");
            }
            tChamps[i].setChampRetourMapping(Arrays.stream(tmappings).collect(Collectors.joining(";")));
        }
    }

    public static void setPageAppelCompletePropre(Champ[] tChamps, String ac_classeMapping, String ac_valeur, String ac_nomTable, String champRetour, String champRetourMapping, String AWhere) {
        for (int i = 0; i < tChamps.length; ++i) {
            tChamps[i].setPageAppelCompleteAWhere(ac_classeMapping, ac_valeur, ac_nomTable, champRetour, champRetourMapping, AWhere);
            tChamps[i].setChampRetour(champRetour + ";" + champRetour);
            int nb_mappings = champRetourMapping.split(";").length;
            String[] tmappings = new String[nb_mappings + nb_mappings];
            String[] mappings = champRetourMapping.split(";");
            for (int a = 0; a < mappings.length; ++a) {
                int num = a + nb_mappings;
                tmappings[a] = mappings[a] + "_" + i;
                tmappings[num] = mappings[a] + "_" + i + "libelle";
                if (!mappings[a].contains("libelle")) continue;
                tmappings[a] = mappings[a].replace("libelle_" + i, "_" + i + "libelle");
            }
            tChamps[i].setChampRetourMapping(Arrays.stream(tmappings).collect(Collectors.joining(";")));
            tChamps[i].setPropre(true);
        }
    }

    public void setAutocompleteDynamique(String ac_classeMapping, String ac_affiche, String ac_valeur, String ac_nomTable) {
        this.setAc_classeMapping(ac_classeMapping);
        this.setAc_affiche(ac_affiche);
        this.setAc_valeur(ac_valeur);
        this.setAc_nomTable(ac_nomTable);
        this.setAutocompleteDynamique(true);
        this.setUseMotCle(false);
    }

    public void setPageAppelComplete(String ac_classeMapping, String ac_valeur, String ac_nomTable, String champRetour, String champRetourMapping) {
        this.setPageAppelComplete(ac_classeMapping, ac_valeur, ac_nomTable);
        this.setChampRetour(champRetour);
        this.setChampRetourMapping(champRetourMapping);
    }

    public void setPageAppelCompleteAWhere(String ac_classeMapping, String ac_valeur, String ac_nomTable, String champRetour, String champRetourMapping, String aWhere) {
        this.setPageAppelComplete(ac_classeMapping, ac_valeur, ac_nomTable);
        this.setChampRetour(champRetour);
        this.setChampRetourMapping(champRetourMapping);
        this.setAc_aWhere(aWhere);
    }

    public void setPageAppelComplete(String ac_classeMapping, String ac_valeur, String ac_nomTable) {
        this.setAc_classeMapping(ac_classeMapping);
        this.setAc_valeur(ac_valeur);
        this.setAc_nomTable(ac_nomTable);
        this.setUseMotCle(true);
        this.setAutocompleteDynamique(true);
    }

    public void setPageAppelCompletePropre(String ac_classeMapping, String ac_valeur, String ac_nomTable, String champRetour, String champRetourMapping, String aWhere) {
        this.setPageAppelComplete(ac_classeMapping, ac_valeur, ac_nomTable);
        this.setChampRetour(champRetour);
        this.setChampRetourMapping(champRetourMapping);
        this.setAc_aWhere(aWhere);
        this.setPropre(true);
    }

    public void setPageAppelCompleteInsert(String ac_classeMapping, String ac_valeur, String ac_nomTable, String pageAppelInsert, String champReturn, String champUrl) {
        this.setPageAppelComplete(ac_classeMapping, ac_valeur, ac_nomTable);
        this.setPageAppelInsert(pageAppelInsert, champReturn, champUrl);
    }

    public void setPageAppelCompleteInsert(String ac_classeMapping, String ac_valeur, String ac_nomTable, String champRetour, String champRetourMapping, String pageAppelInsert, String champReturn, String champUrl) {
        this.setPageAppelComplete(ac_classeMapping, ac_valeur, ac_nomTable);
        this.setChampRetour(champRetour);
        this.setChampRetourMapping(champRetourMapping);
        this.setPageAppelInsert(pageAppelInsert, champReturn, champUrl);
    }

    public void setPageAppelCompleteInsert(String ac_classeMapping, String ac_valeur, String ac_nomTable, String pageAppelInsert, String champUrl) {
        this.setPageAppelComplete(ac_classeMapping, ac_valeur, ac_nomTable);
        this.setPageAppelInsert(pageAppelInsert, champUrl);
    }

    public void setPageAppelCompleteInsert(String ac_classeMapping, String ac_valeur, String ac_nomTable, String champRetour, String champRetourMapping, String pageAppelInsert, String champUrl) {
        this.setPageAppelComplete(ac_classeMapping, ac_valeur, ac_nomTable);
        this.setChampRetour(champRetour);
        this.setChampRetourMapping(champRetourMapping);
        this.setPageAppelInsert(pageAppelInsert, champUrl);
    }

    public void setEstMultiple(boolean estMultiple) {
        this.estMultiple = estMultiple;
    }

    public ClassMAPTable[] getBase() {
        return this.base;
    }

    public void setBase(ClassMAPTable[] base) {
        this.base = base;
    }

    public boolean isAutocomplete() {
        return this.autocomplete;
    }

    public void setAutocomplete(boolean autocomplete) {
        this.autocomplete = autocomplete;
    }

    public String getAc_affiche() {
        return this.ac_affiche;
    }

    public void setAc_affiche(String ac_affiche) {
        this.ac_affiche = ac_affiche;
    }

    public String getAc_valeur() {
        return this.ac_valeur;
    }

    public void setAc_valeur(String ac_valeur) {
        this.ac_valeur = ac_valeur;
    }

    public String getAc_nomTable() {
        return this.ac_nomTable;
    }

    public void setAc_nomTable(String ac_nomTable) {
        this.ac_nomTable = ac_nomTable;
    }

    public Ac_object[] getTab_ac() {
        return this.tab_ac;
    }

    public void setTab_ac(Ac_object[] tab_ac) {
        this.tab_ac = tab_ac;
        this.transformToStringAc();
    }

    public void setOptions(String[] valeurs, String[] libelles) {
        if (valeurs == null || libelles == null || valeurs.length != libelles.length) {
            return;
        }
        Ac_object[] opts = new Ac_object[valeurs.length];
        for (int i = 0; i < valeurs.length; ++i) {
            opts[i] = new Ac_object(valeurs[i], libelles[i]);
        }
        this.setTab_ac(opts);
    }

    public String getAc_aWhere() {
        return this.ac_aWhere;
    }

    public void setAc_aWhere(String ac_aWhere) {
        this.ac_aWhere = ac_aWhere;
    }

    public String[] getColFiltre() {
        return this.colFiltre;
    }

    public void setColFiltre(String[] colFiltre) {
        this.colFiltre = colFiltre;
    }

    public String[] getValFiltre() {
        return this.valFiltre;
    }

    public void setValFiltre(String[] valFiltre) {
        this.valFiltre = valFiltre;
    }

    public void setPageAppelAvecFiltre(String pageAppel, String champReturnn, String[] col, String[] val) {
        this.pageAppel = pageAppel;
        this.champReturn = champReturnn;
        this.setColFiltre(col);
        this.setValFiltre(val);
    }

    public String getValeur_ac() {
        return this.valeur_ac;
    }

    public void setValeur_ac(String valeur_ac) {
        this.valeur_ac = valeur_ac;
    }

    public String getUrlFiche() {
        return this.urlFiche;
    }

    public void setUrlFiche(String urlFiche) {
        this.urlFiche = urlFiche;
    }

    public String getApresLienPageappel() {
        return this.apresLienPageappel;
    }

    public void setApresLienPageappel(String apresLienPageappel) {
        this.apresLienPageappel = apresLienPageappel;
    }

    public PageConsulteLien getLien() {
        return this.lien;
    }

    public void setLien(PageConsulteLien lien) {
        this.lien = lien;
    }

    public void setLien(String lien, String queryString) {
        this.setLien(new PageConsulteLien(lien, queryString));
    }

    public boolean isPhoto() {
        return this.photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
    }

    public Champ() {
    }

    public Champ(String nom) {
        this.setNom(nom);
    }

    public Champ copierConfiguration(String nouveauNom) throws Exception {
        Champ copie = (Champ)this.getClass().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        for (Class<?> classe = this.getClass(); classe != null && Champ.class.isAssignableFrom(classe); classe = classe.getSuperclass()) {
            for (Field champ : classe.getDeclaredFields()) {
                String nomChamp = champ.getName();
                if (nomChamp.equals("nom") || nomChamp.equals("defaut") || nomChamp.equals("valeur") || nomChamp.equals("valeurDirect") || nomChamp.equals("formulaire") || nomChamp.startsWith("html")) continue;
                champ.setAccessible(true);
                champ.set(copie, champ.get(this));
            }
        }
        copie.setNom(nouveauNom);
        copie.setDefaut("");
        copie.setValeur("");
        return copie;
    }

    public String toString() {
        return this.getNom();
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDefaut(String defaut) {
        this.defaut = defaut;
    }

    public static void setDefaut(Champ[] tChamp, String defaut) {
        for (Champ c : tChamp) {
            c.setDefaut(defaut);
        }
    }

    public String getDefaut() {
        return this.defaut;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public static void setCss(Champ[] tChamp, String css) {
        for (Champ c : tChamp) {
            c.setCss(css);
        }
    }

    public String getCss() {
        return this.css;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        if (this.getUserEJB() != null) {
            if (this.libelle == null || this.libelle.compareToIgnoreCase("") == 0) {
                return this.getUserEJB().getTraduction(this.nom);
            }
            return this.getUserEJB().getTraduction(this.libelle);
        }
        if (this.libelle == null || this.libelle.compareToIgnoreCase("") == 0) {
            return this.nom;
        }
        return this.libelle;
    }

    public void setPageAppel(String pageAppel) {
        this.pageAppel = pageAppel;
        this.champReturn = this.getNom() + ";" + this.getNom() + "libelle";
    }

    public static void setPageAppel(Champ[] tChamp, String pageAppel, String champReturn) throws Exception {
        for (Champ c : tChamp) {
            c.pageAppel = pageAppel;
            c.champReturn = champReturn;
        }
    }

    public static void setAutocomplete(Champ[] tChamp, String affiche, String valeur, String nomTable, String aWhere) throws Exception {
        for (Champ c : tChamp) {
            c.ac_affiche = affiche;
            c.ac_valeur = valeur;
            c.ac_nomTable = nomTable;
            c.autocomplete = true;
            c.ac_aWhere = aWhere;
        }
    }

    public String getChampReturn() {
        return this.champReturn;
    }

    public void setChampReturn(String champReturn) {
        this.champReturn = champReturn;
    }

    public void setPageAppel(String pageAppel, String champReturnn) {
        this.pageAppel = pageAppel;
        this.champReturn = champReturnn;
    }

    public String getPageAppel() {
        return this.pageAppel;
    }

    public void setCssLibelle(String cssLibelle) {
        this.cssLibelle = cssLibelle;
    }

    public String getCssLibelle() {
        return this.cssLibelle;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void setHtmlTableau(String htmlTableau) {
        this.htmlTableau = htmlTableau;
    }

    public String getHtml() throws Exception {
        if (this.html == null || this.html.compareToIgnoreCase("") == 0) {
            this.makeHtml();
        }
        return this.html;
    }

    public String getHtmlTableau(int i) throws Exception {
        this.makeHtmlTableau(i);
        return this.htmlTableau;
    }

    public void makeHtml() throws Exception {
        String temp = "";
        if (this.getNom().contains("motsClesss")) {
            this.setVisible(false);
        }
        if (!this.getVisible()) {
            temp = "<input data-type='String' name='" + this.getNom() + "' type='hidden' class=" + this.getCss() + "  id=" + this.getNom() + " value='" + this.getValeur() + "' " + this.getAutre() + ">";
        } else if (this.getNom().contains("image")) {
            String name = this.getNom() + "_img";
            temp = "<img name=" + name + " id=" + name + " src=\"/dossier/async/" + this.getValeur() + "\" style=\"width:" + this.getTailleImage() + "%;\">";
            temp = temp + "<input name='" + this.getNom() + "' type='hidden' id=" + this.getNom() + " value='" + this.getValeur() + "'>";
        } else if (this.type.compareTo(ConstanteAffichage.editor) == 0) {
            String nb = "";
            Pattern pattern = Pattern.compile(".*_(\\d+)$");
            Matcher matcher = pattern.matcher(this.getNom());
            if (matcher.matches()) {
                String number;
                nb = number = matcher.group(1);
            }
            temp = temp + "<div id=\"initial_" + this.getNom() + "\" style=\"display:none;\">" + this.getValeur() + "</div>";
            temp = temp + "<input data-type=\"String\" type=\"hidden\" id=\"" + this.getNom() + "\" name=\"" + this.getNom() + "\" value=\"" + Champ.escapeHtml(this.getValeur()) + "\" class=\"" + this.getCss() + "\">";
            temp = temp + Champ.toEditorFull(new String[]{this.getNom()});
        } else if (this.type.compareTo(ConstanteAffichage.typeMdp) == 0) {
            temp = temp + "<div class='d-flex gap-2 w-100'>";
            temp = temp + "<input name=" + this.getNom() + " type=" + this.getType() + " class=" + this.getCss() + " id=" + this.getNom() + " value=\"" + this.getValeur() + "\" " + this.getAutre() + " data-text=\"S\u00c3\u00a9lectionnez votre fichier\" oninput=\"if(this.value !== '') { synchro(this,checkbox.id) }\">";
            temp = temp + "<button class=\"btn btn-secondary btn-small\" type=\"button\" onclick=\"togglePassword_" + this.getNom() + "()\"><i id=\"toggleIcon_" + this.getNom() + "\" class=\"material-symbols-rounded\">visibility</i></button>";
            temp = temp + "<script>function togglePassword_" + this.getNom() + "() {  const input = document.getElementById('" + this.getNom() + "');  const icon = document.getElementById('toggleIcon_" + this.getNom() + "');  if (input.type === 'password') {    input.type = 'text';    icon.textContent = 'visibility_off';  } else {    input.type = 'password';    icon.textContent = 'visibility';  }}</script>";
            temp = temp + "</div>";
        } else if (this.getEstMultiple() && this.getTab_ac() != null && this.getTab_ac().length > 0) {
            List<String> selected = this.parseSelectedValues(this.getValeur());
            String idBloc = this.getNom() + "_ms";
            temp = temp + "<div class='dropdown multiselect-checkbox' id='" + idBloc + "' style='width:100%;'>";
            temp = temp + "  <button type='button' class='dropdown-toggle'>";
            temp = temp + "    <span class='dropdown-label'>Choisir</span><i class='material-symbols-rounded'>expand_more</i>";
            temp = temp + "  </button>";
            temp = temp + "  <div class='dropdown-menu'>";
            for (Ac_object opt : this.getTab_ac()) {
                String valOpt = opt.getValeur() != null ? opt.getValeur().toString() : "";
                String libOpt = opt.getAffiche() != null ? opt.getAffiche().toString() : "";
                boolean checked = selected.contains(valOpt);
                temp = temp + "    <label class='dropdown-item'>";
                temp = temp + "      <input type='checkbox' name='" + this.getNom() + "' value='" + Champ.escapeHtml(valOpt) + "' " + (checked ? "checked" : "") + ">";
                temp = temp + "      <span>" + Champ.escapeHtml(libOpt) + "</span>";
                temp = temp + "    </label>";
            }
            temp = temp + "    <div class='dropdown-item text-right'><button type='button' class='clear-multi'>R&eacute;initialiser</button></div>";
            temp = temp + "  </div>";
            temp = temp + "</div>";
            temp = temp + "<script>\n(function () {\n    const root = document.getElementById('" + idBloc + "');\n    if (!root) return;\n\n    const btn = root.querySelector('.dropdown-toggle');\n    const menu = root.querySelector('.dropdown-menu');\n    const label = root.querySelector('.dropdown-label');\n    const boxes = [...root.querySelectorAll(\"input[type='checkbox']\")];\n    const clearBtn = root.querySelector('.clear-multi');\n\n    function updateLabel() {\n        const selected = boxes.filter(cb => cb.checked);\n\n        if (selected.length === 0) {\n            label.textContent = \"Choisir\";\n        } else if (selected.length === 1) {\n            label.textContent = selected[0].parentNode.textContent.trim();\n        } else {\n            label.textContent = selected.length + \" s&eacute;lectionn&eacute;s\";\n        }\n    }\n\n    btn.addEventListener('click', (e) => {\n        e.stopPropagation();\n        menu.classList.toggle('show');\n    });\n\n    boxes.forEach(cb => {\n        cb.addEventListener('change', updateLabel);\n    });\n\n    if (clearBtn) {\n        clearBtn.addEventListener('click', (e) => {\n            e.stopPropagation();\n            boxes.forEach(cb => cb.checked = false);\n            updateLabel();\n        });\n    }\n\n    document.addEventListener('click', (e) => {\n        if (!root.contains(e.target)) {\n            menu.classList.remove('show');\n        }\n    });\n\n    updateLabel();\n})();\n</script>";
        } else if (this.type.compareTo(ConstanteAffichage.textarea) == 0) {
            temp = "<textarea data-type='String' name='" + this.getNom() + "' class='" + this.getCss() + "' id='" + this.getNom() + "' style='resize: none;'>" + this.getValeur() + "</textarea>";
        } else if (this.typeData.compareTo(ConstanteAffichage.typeDaty) == 0) {
            temp = this.getIsDateHeure() ? "<input data-type='date' name=" + this.getNom() + " type='datetime-local' class=" + this.getCss() + "  id=" + this.getNom() + " value='" + this.getValeur() + "' " + this.getAutre() + ">" : "<input data-type='date' name=" + this.getNom() + " type=" + this.getType() + " class=" + this.getCss() + "  id=" + this.getNom() + " value='" + this.getValeur() + "' onmouseover=\"datepicker('" + this.getNom() + "')\" " + this.getAutre() + ">";
        } else if (this.isAutocompleteDynamique()) {
            temp = temp + this.getInputHiddenAutoComplete();
        } else if (!this.isPhoto()) {
            String nb = "";
            Pattern pattern = Pattern.compile(".*_(\\d+)$");
            Matcher matcher = pattern.matcher(this.getNom());
            if (matcher.matches()) {
                String number;
                nb = number = matcher.group(1);
            }
            temp = "<input data-type='String' name=" + this.getNom() + " type=" + this.getType() + " class=" + this.getCss() + "  id=" + this.getNom() + " value=\"" + this.getValeur() + "\" " + this.getAutre() + " data-text=\"S\u00c3\u00a9lectionnez votre fichier\" oninput=\"if(this.value !== '') { synchro(this,checkbox" + nb + ".id) }\">";
            if (!this.isAutoComplete()) {
                temp = temp + "<input data-type='String' type=hidden value='' name=" + this.getNom() + "auto>";
            }
        } else {
            temp = "<input data-type='String' name=" + this.getNom() + " type=\"file\" class=" + this.getCss() + " id=" + this.getNom() + " " + this.getAutre() + " data-text=\"S\u00c3\u00a9lectionnez votre photo\">";
            if (!this.getValeur().isEmpty()) {
                temp = temp + "<input data-type='String' type=\"hidden\" value=\"" + this.getValeur() + "\" name=\"" + this.getNom() + "2\">";
            }
        }
        this.setHtml(temp);
    }

    private static String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }

    private List<String> parseSelectedValues(String raw) {
        String[] parts;
        ArrayList<String> res = new ArrayList<String>();
        if (raw == null || raw.trim().isEmpty()) {
            return res;
        }
        String cleaned = raw.replace("'", "");
        for (String p : parts = cleaned.split("[,;]")) {
            if (p == null || p.trim().isEmpty()) continue;
            res.add(p.trim());
        }
        return res;
    }

    public void makeHtmlTableau(int i) throws Exception {
        String temp = "";
        if (!this.getVisible()) {
            temp = "<input name='" + this.getNom() + "' type='hidden' class=" + this.getCss() + "  id=" + this.getNom() + " value=\"" + this.getValeur() + "\" " + this.getAutre() + ">";
        } else if (this.type.compareTo(ConstanteAffichage.textarea) == 0) {
            temp = "<textarea name='" + this.getNom() + "' class='" + this.getCss() + "' id='" + this.getNom() + "' style='resize: none;'>" + this.getValeur() + "</textarea>";
        } else if (this.isAutocompleteDynamique()) {
            temp = temp + this.getInputHiddenAutoCompleteAutoCheck(i);
            temp = temp + this.getHtmlPageAppelInsert();
        } else if (this.getTypeData().compareTo(ConstanteAffichage.typeDaty) == 0) {
            temp = this.getIsDateHeure() ? "<input name=" + this.getNom() + " type='datetime-local' class=" + this.getCss() + "  id=" + this.getNom() + " value='" + this.getValeur() + "' " + this.getAutre() + ">" : "<input name=" + this.getNom() + " type=" + this.getType() + " class=" + this.getCss() + "  id=" + this.getNom() + " value='" + this.getValeur() + "' onmouseover=\"datepicker('" + this.getNom() + "')\" " + this.getAutre() + ">";
        } else if (!this.isPhoto()) {
            temp = "<input name='" + this.getNom() + "' type=" + this.getType() + " class=" + this.getCss() + "  id=" + this.getNom() + " value=\"" + this.getValeur() + "\" " + this.getAutre() + " data-text=\"S\u00c3\u00a9lectionnez votre fichier\" oninput=\"if(this.value !== '') { synchro(this,checkbox" + i + ".id) }\">";
        } else {
            temp = "<input name='" + this.getNom() + "' type=\"file\" class=" + this.getCss() + " id=" + this.getNom() + " " + this.getAutre() + "  data-text=\"S\u00c3\u00a9lectionnez votre fichier\">";
            if (!this.getValeur().isEmpty()) {
                temp = temp + "<input type=\"hidden\" value=\"" + this.getValeur() + "\" name=\"" + this.getNom() + "2_" + i + "\">";
            }
        }
        this.setHtmlTableau(temp);
    }

    public void makeHtmlInsert() throws Exception {
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public static void setTaille(Champ[] tChamp, int taille) {
        for (Champ c : tChamp) {
            c.setTaille(taille);
        }
    }

    public int getTaille() {
        return this.taille;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public static void setValeur(Champ[] tChamp, String valeur) {
        for (Champ c : tChamp) {
            c.setValeur(valeur);
        }
    }

    public String getValeur() {
        if (this.valeur == null || this.valeur.compareToIgnoreCase("null") == 0 || this.valeur.compareToIgnoreCase("") == 0) {
            if (this.getDefaut() == null || this.getDefaut().compareToIgnoreCase("null") == 0) {
                return "";
            }
            return this.getDefaut();
        }
        if (this.getValeurDirect() != null) {
            return this.getValeurDirect();
        }
        return this.valeur;
    }

    public String getValeurLibelle() {
        if (this.valeurLibelle == null || this.valeurLibelle.compareToIgnoreCase("null") == 0 || this.valeurLibelle.compareToIgnoreCase("") == 0) {
            if (this.getDefaut() == null || this.getDefaut().compareToIgnoreCase("null") == 0) {
                return this.getValeur();
            }
            return this.getValeur();
        }
        String valeur = this.valeurLibelle;
        if (valeur != null && valeur.contains("'")) {
            valeur = valeur.replaceAll("'", "&apos;");
        }
        return valeur;
    }

    public void setValeurLibelle(String valeurLibelle) {
        this.valeurLibelle = valeurLibelle;
    }

    public void setTypeData(String typeData) {
        this.typeData = typeData;
    }

    public static void setTypeData(Champ[] tChamp, String typeData) {
        for (Champ c : tChamp) {
            c.setTypeData(typeData);
        }
    }

    public String getTypeData() {
        return this.typeData;
    }

    public void setAttribut(Field attribut) {
        this.attribut = attribut;
    }

    public void setObjet(ClassMAPTable objet) {
        this.objet = objet;
    }

    public ClassMAPTable getObjet() {
        return this.objet;
    }

    public Field getAttribut() {
        return this.attribut;
    }

    public void setEstIntervalle(int estIntervalle) {
        this.estIntervalle = estIntervalle;
    }

    public int getEstIntervalle() {
        return this.estIntervalle;
    }

    public void setRecherchePrecis(boolean recherchePrecis) {
        this.recherchePrecis = recherchePrecis;
    }

    public boolean isRecherchePrecis() {
        return this.recherchePrecis;
    }

    public static void setRecherchePrecis(Champ[] tChamp, boolean recherchePrecis) {
        if (tChamp == null) {
            return;
        }
        for (Champ c : tChamp) {
            if (c == null) continue;
            c.setRecherchePrecis(recherchePrecis);
        }
    }

    public void findDataAutoComplete(Connection c) throws Exception {
        if (this.isAutocomplete()) {
            Ac_object[] ac_objt = null;
            ResultSet dr = null;
            Statement st = null;
            try {
                String req = "select " + this.getAc_affiche() + ", " + this.getAc_valeur() + " from " + this.getAc_nomTable() + " where 1<2 " + this.getAc_aWhere();
                st = c.createStatement(1004, 1007);
                dr = st.executeQuery(req);
                ArrayList<Ac_object> liste = new ArrayList<Ac_object>();
                while (dr.next()) {
                    Ac_object aco = new Ac_object(dr.getObject(this.getAc_valeur()), dr.getObject(this.getAc_affiche()));
                    liste.add(aco);
                }
                if (liste.size() > 0) {
                    ac_objt = new Ac_object[liste.size()];
                    for (int k = 0; k < liste.size(); ++k) {
                        ac_objt[k] = (Ac_object)liste.get(k);
                    }
                    this.setTab_ac(ac_objt);
                }
            }
            catch (SQLException e) {
                throw e;
            }
            finally {
                if (st != null) {
                    st.close();
                }
                if (dr != null) {
                    dr.close();
                }
            }
        }
    }

    public void findAutocompleteDisplayValue(Connection c) throws Exception {
        String displayValue;
        if (!this.isAutocompleteDynamique() || c == null || this.getValeur() == null || this.getValeur().isEmpty() || this.getValeur().contains("---") || this.valeurLibelle != null && !this.valeurLibelle.isEmpty() || this.getAc_classeMapping() == null || this.getAc_classeMapping().isEmpty() || this.getAc_nomTable() == null || this.getAc_nomTable().isEmpty()) {
            return;
        }
        ClassMAPTable mapping = (ClassMAPTable)Class.forName(this.getAc_classeMapping()).newInstance();
        mapping.setValChamp(mapping.getAttributIDName(), (Object)this.getValeur());
        ClassMAPTable result = this.findAutocompleteObject(c, mapping);
        if (result == null) {
            return;
        }
        if (this.isUseMotCle()) {
            displayValue = CGenUtil.getValeurFieldByMotCle(result);
        } else {
            String displayColumn = this.getAutocompleteDisplayColumn();
            if (displayColumn == null || displayColumn.isEmpty()) {
                return;
            }
            Object value = CGenUtil.getValeurFieldByMethod(result, displayColumn);
            String string = displayValue = value == null ? "" : String.valueOf(value);
        }
        if (!displayValue.isEmpty()) {
            this.setValeurLibelle(displayValue);
        }
    }

    private ClassMAPTable findAutocompleteObject(Connection c, ClassMAPTable mapping) throws Exception {
        try {
            ClassMAPTable[] objects = (ClassMAPTable[])CGenUtil.rechercher(mapping, null, null, c, "");
            if (objects.length > 0) {
                return objects[0];
            }
        }
        catch (Exception e) {
            return null;
        }
        return null;
    }

    private String getAutocompleteDisplayColumn() {
        if (this.getAc_affiche() != null && !this.getAc_affiche().trim().isEmpty()) {
            return this.getAc_affiche();
        }
        if (this.getAc_classeMapping() == null || this.getAc_classeMapping().trim().isEmpty()) {
            return null;
        }
        try {
            ClassMAPTable mapping = (ClassMAPTable)Class.forName(this.getAc_classeMapping()).newInstance();
            return mapping.getValColLibelle();
        }
        catch (Exception e) {
            return null;
        }
    }

    public void findData(Connection c) throws Exception {
    }

    public void setAutre(String autre) {
        this.autre = autre;
    }

    public static void setAutre(Champ[] tChamp, String autre) {
        for (Champ c : tChamp) {
            c.setAutre(autre);
        }
    }

    public static void setAutreHidden(Champ[] tChamp, String autre) {
        for (Champ c : tChamp) {
            c.setAutreHidden(autre);
        }
    }

    public String getAutre() {
        if (this.autre == null) {
            return "";
        }
        return this.autre;
    }

    public void setLibelleAffiche(String libelleAffiche) {
        this.libelleAffiche = libelleAffiche;
    }

    public String getLibelleAffiche() {
        if (this.getUserEJB() != null) {
            if (this.libelleAffiche == null || this.libelleAffiche.compareToIgnoreCase("") == 0) {
                return this.getLibelle();
            }
            return this.getUserEJB().getTraduction(this.libelleAffiche);
        }
        if (this.libelleAffiche == null || this.libelleAffiche.compareToIgnoreCase("") == 0) {
            return this.getLibelle();
        }
        return this.libelleAffiche;
    }

    public String getLibelleAfficheAnnotation() {
        if (this.libelleAffiche != null && !this.libelleAffiche.trim().isEmpty()) {
            return this.getLibelleAffiche();
        }
        if (this.libelle != null && !this.libelle.trim().isEmpty()) {
            return this.getLibelleAffiche();
        }
        try {
            LibelleAffichage la;
            Field f = null;
            if (this.getAttribut() != null) {
                f = this.getAttribut();
            } else if (this.getObjet() != null) {
                f = CGenUtil.getField(this.getObjet(), this.getNom());
                if (f == null) {
                    f = CGenUtil.getField(this.getObjet(), this.getNomSansIndiceLigne());
                }
            } else if (this.getFormulaire() != null && this.getFormulaire().getObjet() != null && (f = CGenUtil.getField(this.getFormulaire().getObjet(), this.getNom())) == null) {
                f = CGenUtil.getField(this.getFormulaire().getObjet(), this.getNomSansIndiceLigne());
            }
            if (f != null && (la = f.getAnnotation(LibelleAffichage.class)) != null && la.value() != null && !la.value().trim().isEmpty()) {
                if (this.getUserEJB() != null) {
                    return this.getUserEJB().getTraduction(la.value());
                }
                return la.value();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this.getLibelleAffiche();
    }

    private String getNomSansIndiceLigne() {
        String nom = this.getNom();
        if (nom != null) {
            nom = nom.replaceAll("_[0-9]+$", "");
        }
        return nom;
    }

    public void setHtmlInsert(String htmlInsert) {
        this.htmlInsert = htmlInsert;
    }

    public String getHtmlInsert() throws Exception {
        if (this.htmlInsert == null || this.htmlInsert.compareToIgnoreCase("") == 0) {
            this.makeHtmlInsert();
        }
        if (this.htmlInsert == null || this.htmlInsert.compareToIgnoreCase("") == 0) {
            this.htmlInsert = this.getHtml();
        }
        return this.htmlInsert;
    }

    public String getInputHidden() throws Exception {
        this.makeInputPopup();
        return this.htmlInsert;
    }

    public String getInputHiddenAutoComplete() throws Exception {
        String temp = "";
        String nb = "";
        Pattern pattern = Pattern.compile(".*_(\\d+)$");
        Matcher matcher = pattern.matcher(this.getNom());
        if (matcher.matches()) {
            String number;
            nb = number = matcher.group(1);
        }
        if (this.pageAppelInsert != null) {
            temp = temp + "<div class='d-flex gap-2 col-md-12 nopadding'>";
        }
        temp = temp + "<div class=\"d-flex input-with-addons\" style='display: inline-flex; align-items: baseline;'>";
        if (this.getValeur().split("---").length > 1) {
            temp = temp + "<input data-type='String' name='" + this.getNom() + "libelle' type='" + this.getType() + "' class='" + this.getCss() + "' id='" + this.getNom() + "libelle' value='" + this.getValeur().split("---")[1] + "' " + this.getAutre() + " oninput=\"if(this.value !== '') { synchro(this,checkbox" + nb + ".id) }\">";
            temp = temp + "<input data-type='String' type='hidden' value='" + this.getValeur().split("---")[0] + "' name='" + this.getNom() + "' id='" + this.getNom() + "' " + this.getAutreHidden() + ">";
        } else {
            temp = temp + "<input data-type='String' name='" + this.getNom() + "libelle' type='" + this.getType() + "' class='" + this.getCss() + "' id='" + this.getNom() + "libelle' value='" + this.getValeurLibelle() + "' " + this.getAutre() + " oninput=\"if(this.value !== '') { synchro(this,checkbox" + nb + ".id) }\">";
            temp = temp + "<input data-type='String' type='hidden' value='" + this.getValeur() + "' name='" + this.getNom() + "' id='" + this.getNom() + "' " + this.getAutreHidden() + ">";
        }
        temp = temp + "<button type='button' class='btn btn-secondary btn-small' id='" + this.getNom() + "searchBtn'><i class=\"material-symbols-rounded\">search</i></button>";
        temp = temp + "</div>";
        if (this.pageAppelInsert != null) {
            temp = temp + this.getHtmlPageAppelInsert();
            temp = temp + "</div>";
        }
        return temp;
    }

    public String getInputHiddenAutoCompleteAutoCheck(int i) throws Exception {
        String temp = "";
        temp = temp + "<div class=\"d-flex input-with-addons\" style='display: inline-flex; align-items: center;'>";
        temp = temp + "<input data-type='String' name='" + this.getNom() + "libelle' type='" + this.getType() + "' class='" + this.getCss() + "' id='" + this.getNom() + "libelle' value='" + this.getValeurLibelle() + "' ' " + this.getAutre() + "' onchange='synchro(this,checkbox" + i + ".id)'>";
        temp = temp + "<input data-type='String' type='hidden' value='" + this.getValeur() + "' name='" + this.getNom() + "' id='" + this.getNom() + "'>";
        temp = temp + "<button type='button' class='btn btn-secondary btn-small' id='" + this.getNom() + "searchBtn'><i class=\"material-symbols-rounded\">search</i></button>";
        temp = temp + "</div>";
        return temp;
    }

    public void makeInputPopup() throws Exception {
        String temp = "";
        String[] val = this.getNom().split("_");
        if (val.length > 1 && Utilitaire.checkNumber(val[1])) {
            temp = "<input name=" + val[0] + "libelle_" + val[1] + " type=" + this.getType() + " class=" + this.getCss() + "  id=" + val[0] + "libelle_" + val[1] + " value='" + this.getValeurLibelle() + "' " + this.getAutre() + ">";
            temp = temp + "<input type='hidden' value='" + this.getValeur() + "' name='" + this.getNom() + "' id='" + this.getNom() + "'>";
        } else {
            temp = "<input name=" + this.getNom() + "libelle type=" + this.getType() + " class=" + this.getCss() + "  id=" + this.getNom() + "libelle value='" + this.getValeurLibelle() + "' " + this.getAutre() + ">";
            temp = temp + "<input type='hidden' value='" + this.getValeur() + "' name='" + this.getNom() + "' id='" + this.getNom() + "'>";
        }
        this.setHtml(temp);
        this.setHtmlInsert(temp);
    }

    public void setHtmlTableauInsert(String htmlTableauInsert) {
        this.htmlTableauInsert = htmlTableauInsert;
    }

    public String getHtmlTableauInsert(int i) throws Exception {
        this.htmlTableauInsert = null;
        this.makeHtmlInsertTableau(i);
        if (this.htmlTableauInsert == null || this.htmlTableauInsert.compareToIgnoreCase("") == 0) {
            this.htmlTableauInsert = this.getHtmlTableau(i);
        }
        return this.htmlTableauInsert;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public static void setVisible(Champ[] tChamp, boolean visible) {
        for (Champ c : tChamp) {
            c.setVisible(visible);
        }
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setAutocomplete(String affiche, String valeur, ClassMAPTable classMapTable, UserEJB u, Connection c) throws Exception {
        boolean indice = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                indice = true;
            }
            this.autoComplete = true;
            Object[] liste = CGenUtil.rechercher(classMapTable, null, null, c, "");
            Class<?> clazz = Class.forName(classMapTable.getClassName());
            StringBuilder sbA = new StringBuilder();
            sbA = new StringBuilder();
            sbA.append("get").append(affiche.substring(0, 1).toUpperCase()).append(affiche.substring(1));
            String getterAffiche = sbA.toString();
            StringBuilder sbV = new StringBuilder();
            sbV = new StringBuilder();
            sbV.append("get").append(valeur.substring(0, 1).toUpperCase()).append(valeur.substring(1));
            String getterValeur = sbV.toString();
            String jsons = "[{";
            for (int i = 0; i < liste.length; ++i) {
                Method getter = clazz.getMethod(getterAffiche, new Class[0]);
                Method getter2 = clazz.getMethod(getterValeur, new Class[0]);
                jsons = jsons + "value : '" + getter.invoke(liste[i], new Object[0]) + "',";
                jsons = jsons + "label:'" + getter.invoke(liste[i], new Object[0]) + "',";
                jsons = jsons + "id:'" + getter2.invoke(liste[i], new Object[0]) + "'";
                if (i >= liste.length - 1) continue;
                jsons = jsons + "},{ ";
            }
            jsons = jsons + "}]";
            u.getMapAutoComplete().put(this.getNom(), jsons);
            System.out.println(jsons);
            System.out.println(this.isAutoComplete());
            if (indice) {
                c.commit();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        finally {
            if (indice && c != null) {
                c.close();
            }
        }
    }

    public boolean isAutoComplete() {
        return this.autoComplete;
    }

    public void setAutoComplete(boolean autoComplete) {
        this.autoComplete = autoComplete;
    }

    public String getChampReturn(int indexe) throws Exception {
        String champ = "";
        String[] tchampReturn = Utilitaire.split(this.champReturn, ";");
        for (int i = 0; i < tchampReturn.length; ++i) {
            String[] champRet = tchampReturn[i].split("_");
            champ = champRet.length > 1 && Utilitaire.checkNumber(champRet[1]) ? champ + tchampReturn[i] : champ + tchampReturn[i] + "_" + indexe;
            if (i == tchampReturn.length - 1) continue;
            champ = champ + ";";
        }
        return champ;
    }

    public void makeHtmlInsertTableau(int indice) throws Exception {
    }

    public String getHtmlTableauInsert(int i, String valeur) throws Exception {
        this.htmlTableauInsert = null;
        this.makeHtmlInsertTableau(i, valeur);
        if (this.htmlTableauInsert == null || this.htmlTableauInsert.compareToIgnoreCase("") == 0) {
            this.htmlTableauInsert = this.getHtmlTableau(i, valeur);
        }
        return this.htmlTableauInsert;
    }

    public void makeHtmlInsertTableau(int indice, String valeur) throws Exception {
    }

    public String getHtmlTableau(int i, String valeur) throws Exception {
        this.makeHtmlTableau(i, valeur);
        return this.htmlTableau;
    }

    public void makeHtmlTableau(int i, String valeur) throws Exception {
        String temp = "";
        this.setValeur(valeur);
        if (this.type.compareTo(ConstanteAffichage.textarea) == 0) {
            temp = "<textarea name='" + this.getNom() + "_" + i + "' class='" + this.getCss() + "' id='" + this.getNom() + "_" + i + "' style='resize: none;'>" + this.getValeur() + "</textarea>";
        } else if (this.typeData.compareTo(ConstanteAffichage.typeDaty) == 0) {
            temp = this.getIsDateHeure() ? "<input name=" + this.getNom() + "_" + i + " type='datetime-local' class='" + this.getCss() + "'  id=" + this.getNom() + "_" + i + " value='" + this.getValeur() + "' " + this.getAutre() + ">" : "<input name=" + this.getNom() + "_" + i + " type=" + this.getType() + " class='" + this.getCss() + " datepicker'  id=" + this.getNom() + "_" + i + " value='" + this.getValeur() + "' " + this.getAutre() + ">";
        } else if (!this.isPhoto()) {
            temp = "<input name=" + this.getNom() + "_" + i + " type=" + this.getType() + " class=" + this.getCss() + "  id=" + this.getNom() + "_" + i + " value='" + this.getValeur() + "' " + this.getAutre() + ">";
        } else {
            temp = "<input name=" + this.getNom() + "_" + i + " type=\"file\" class=" + this.getCss() + " id=" + this.getNom() + "_" + i + " " + this.getAutre() + " data-text=\"S\u00c3\u00a9lectionnez votre fichier\">";
            if (!this.getValeur().isEmpty()) {
                temp = temp + "<input type=\"hidden\" value=\"" + this.getValeur() + "\" name=\"" + this.getNom() + "_" + i + "2\">";
            }
        }
        this.setHtmlTableau(temp);
    }

    public void setPageAppelIndice(String pageAppel, int i) {
        this.pageAppel = pageAppel;
        String[] tGetNom = this.getNom().split("_");
        if (tGetNom.length > 0) {
            this.champReturn = this.getNom() + ";" + tGetNom[0] + "libelle_" + tGetNom[1];
        }
    }

    public void setAutocomplete(String affiche, String valeur, String nomTable, String aWhere) throws Exception {
        this.ac_affiche = affiche;
        this.ac_valeur = valeur;
        this.ac_nomTable = nomTable;
        this.autocomplete = true;
        this.ac_aWhere = aWhere;
    }

    public void transformToStringAc() {
        String jsons = "";
        if (this.getTab_ac() != null && this.getTab_ac().length > 0) {
            jsons = jsons + "[";
            for (int i = 0; i < this.getTab_ac().length; ++i) {
                if (this.getTab_ac()[i].getAffiche() == null) continue;
                jsons = jsons + "\"" + this.getTab_ac()[i].getAffiche().toString().replace("'", " ").replace("\"", " ") + " :: " + this.getTab_ac()[i].getValeur().toString().replace("'", " ").replace("\"", " ") + "\"";
                if (i >= this.getTab_ac().length - 1) continue;
                jsons = jsons + ", ";
            }
            jsons = jsons + "]";
            this.setValeur_ac(jsons);
        }
    }

    public static void setPageAppel(Champ[] tChamp, String pageAppel, String champReturn, boolean isautocomplete) throws Exception {
        for (Champ c : tChamp) {
            c.pageAppel = pageAppel;
            c.champReturn = champReturn;
        }
    }

    public String getAutocompleteDynamiqueJs() {
        if (this.isAutocompleteDynamique()) {
            String nomlib = this.getNom() + "libelle";
            String nomlibNext = Utilitaire.incrementLastNumber(this.getNom()) + "libelle";
            String nom = this.getNom();
            String acAffiche = this.getAutocompleteDisplayColumn();
            String acValeur = this.getAc_valeur();
            String apresW = this.getAc_aWhere();
            String[] maps = this.getChampRetourMapping().split(";");
            String rep = "if (!document.getElementById('moteur-autocomplete-style')) {\n    const style = document.createElement('style');\n    style.id = 'moteur-autocomplete-style';\n    style.textContent = `\n      .ui-autocomplete {\n        max-height: 25em !important;\n        overflow-y: auto !important;\n        overflow-x: hidden !important;\n        z-index: 999999 !important;\n      }\n    `;\n    document.head.appendChild(style);\n  }\n$(function() {\n    var autocompleteTriggered = false;\n    $(\"#" + nomlib + "\").autocomplete({\n        source: function(request, response) {\n            if (autocompleteTriggered) {\n";
            if (!this.isPropre()) {
                rep = rep + " fetchAutocomplete(request, response, \"" + acAffiche + "\", \"" + acValeur + "\", \"" + acAffiche + "\", \"" + this.getAc_nomTable() + "\", \"" + this.getAc_classeMapping() + "\", \"" + this.isUseMotCle() + "\", \"" + this.getChampRetour() + "\", \"" + apresW + "\");\n            }\n";
            }
            if (this.isPropre()) {
                rep = rep + " fetchAutocomplete(request, response, \"" + acAffiche + "\", \"" + acValeur + "\", \"" + acAffiche + "\", \"" + this.getAc_nomTable() + "\", \"" + this.getAc_classeMapping() + "\", \"" + this.isUseMotCle() + "\", \"" + this.getChampRetour() + "\", \"" + apresW + "\", \"" + this.isPropre() + "\");\n            }\n";
            }
            rep = rep + "        },\n        open: function() {\n             var wrapper = $(this).closest('.d-flex.input-with-addons');\n             var width = wrapper.length ? wrapper.outerWidth() : $(this).outerWidth();\n             $(this).autocomplete('widget').width(width);\n        },\n        select: function(event, ui) {\n            $(\"#" + nomlib + "\").val(ui.item.label);\n            $(\"#" + nom + "\").val(ui.item.value);\n            var champsDependant = [" + Arrays.asList(this.champRetourMapping.split(";")).stream().map(v -> "'" + v + "'").collect(Collectors.joining(",")) + "            ];\n            if (ui.item.retour) {\n                var valeurs = ui.item.retour.split(';');\n                for (let i = 0; i < champsDependant.length; i++) {\n                    var champId = champsDependant[i];\n                    if ($('#' + champId).length) {\n                        $('#' + champId).val(valeurs[i]);\n                    }\n                }\n            }\n            setTimeout(function(){\n                $(\"#" + nom + "\").trigger('change');\n            }, 100);\n            autocompleteTriggered = false;\n            $(\"#" + nomlibNext + "\").focus();\n            return false;\n        }\n    }).autocomplete('disable');\n    $(\"#" + nomlib + "\").keydown(function(event) {\n        if (event.key === 'Tab') {\n            event.preventDefault();\n            var cursorPos = this.selectionStart;\n            autocompleteTriggered = true;\n            $(this).autocomplete('enable').autocomplete('search', $(this).val());\n            var el = this;\n            el.focus();\n            el.setSelectionRange(cursorPos, cursorPos);\n        }\n    });\n    $(\"#" + nomlib + "\").on('input', function() {\n        $(\"#" + nom + "\").val('');\n        autocompleteTriggered = false;\n        $(this).autocomplete('disable');\n    });\n    $(\"#" + this.getNom() + "searchBtn\").click(function() {\n        autocompleteTriggered = true;\n        $(\"#" + nomlib + "\").autocomplete('enable').autocomplete('search', $(\"#" + nomlib + "\").val());\n    });\n});\n";
            return rep;
        }
        return "";
    }

    public static String toEditor(String[] ids) {
        StringBuilder script = new StringBuilder();
        script.append("<script>\n");
        for (String id : ids) {
            script.append("makeRichEditor(\"").append(id).append("\");\n");
        }
        script.append("</script>\n");
        return script.toString();
    }

    public static String toEditorFull(String[] ids) {
        StringBuilder script = new StringBuilder();
        script.append("<script>\n");
        script.append("function makeRichEditor(id) {\n").append("    const original = document.getElementById(id);\n").append("    if (!original) return;\n").append("    if (original.contentEditable === 'true') return;\n").append("    const initialValue = original.value || '';\n").append("    if (!document.getElementById('rich-editor-style')) {\n").append("        const link = document.createElement('link');\n").append("        link.rel = 'stylesheet';\n").append("        link.href = 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css';\n").append("        document.head.appendChild(link);\n").append("        const style = document.createElement('style');\n").append("        style.id = 'rich-editor-style';\n").append("        style.textContent = `\n").append("            .toolbar { background:#f8f8f8; padding:5px; border:1px solid #ccc; display:flex; flex-wrap:wrap; gap:5px; align-items:center; margin-bottom:5px;border-radius:4px; }\n").append("            .toolbar button, .toolbar select { padding:5px 8px; border:1px solid #aaa; border-radius:5px; background:white; cursor:pointer; }\n").append("            .toolbar button:hover { background:#eee; }\n").append("            .color-palette { display:flex; gap:3px; margin-left:5px; }\n").append("            .color { width:18px; height:18px; border:1px solid #ccc; border-radius:3px; cursor:pointer; }\n").append("            .color:hover { border:2px solid #000; }\n").append("            .rich-editor { min-height:72px; border:1px solid #ccc; padding:8px; margin-top:5px; outline:none; overflow:auto; resize:both;border-radius:4px; }\n").append("            .rich-editor:empty:before { content:attr(data-placeholder); color:#888; }\n").append("            .rich-editor-wrapper { display:block; min-width:291px;width:100%;}\n").append("            table { border-collapse:collapse; margin:5px 0; }\n").append("            td, th { border:1px solid #999; padding:5px; }\n").append("        `;\n").append("        document.head.appendChild(style);\n").append("    }\n").append("    const wrapper = document.createElement('div');\n").append("    wrapper.className = 'rich-editor-wrapper';\n").append("    const toolbar = document.createElement('div');\n").append("    toolbar.className = 'toolbar';\n").append("    toolbar.innerHTML = `\n").append("        <button type='button' onclick=\"exec('bold')\" title='Gras'><i class='fas fa-bold'></i></button>\n").append("        <button type='button' onclick=\"exec('italic')\" title='Italique'><i class='fas fa-italic'></i></button>\n").append("        <button type='button' onclick=\"exec('underline')\" title='Soulign\u00c3\u00a9'><i class='fas fa-underline'></i></button>\n").append("        <button type='button' onclick=\"exec('insertUnorderedList')\" title='Liste \u00c3\u00a0 puces'><i class='fas fa-list-ul'></i></button>\n").append("        <button type='button' onclick=\"exec('insertOrderedList')\" title='Liste num\u00c3\u00a9rot\u00c3\u00a9e'><i class='fas fa-list-ol'></i></button>\n").append("        <button type='button' onclick=\"indent()\" title='Indenter'><i class='fas fa-indent'></i></button>\n").append("        <button type='button' onclick=\"outdent()\" title='D\u00c3\u00a9sindenter'><i class='fas fa-outdent'></i></button>\n").append("        <button type='button' onclick=\"insertTable()\" title='Ins\u00c3\u00a9rer tableau'><i class='fas fa-table'></i></button>\n").append("        <div class='color-palette'>\n").append("            <i class='fas fa-palette' style='margin-right:5px;color:#666;'></i>\n").append("            <div class='color' style='background:#000000' onclick=\"applyColor('#000000')\" title='Noir'></div>\n").append("            <div class='color' style='background:#ff0000' onclick=\"applyColor('#ff0000')\" title='Rouge'></div>\n").append("            <div class='color' style='background:#00aa00' onclick=\"applyColor('#00aa00')\" title='Vert'></div>\n").append("            <div class='color' style='background:#0000ff' onclick=\"applyColor('#0000ff')\" title='Bleu'></div>\n").append("            <div class='color' style='background:#ffa500' onclick=\"applyColor('#ffa500')\" title='Orange'></div>\n").append("            <div class='color' style='background:#800080' onclick=\"applyColor('#800080')\" title='Violet'></div>\n").append("        </div>\n").append("        <select onchange=\"exec('fontSize', this.value)\">\n").append("            <option value='2'>Texte</option>\n").append("            <option value='3' selected>Moyen</option>\n").append("            <option value='5'>Grand</option>\n").append("        </select>\n").append("    `;\n").append("    const editor = document.createElement('div');\n").append("    editor.className = 'rich-editor';\n").append("    editor.contentEditable = true;\n").append("    editor.id = id + '_editor';\n").append("    editor.dataset.placeholder = '';\n").append("    editor.innerHTML = initialValue;\n").append("    const hidden = original;\n").append("    hidden.style.display = 'none';\n").append("    hidden.parentNode.insertBefore(wrapper, hidden);\n").append("    wrapper.appendChild(toolbar);\n").append("    wrapper.appendChild(editor);\n").append("    function syncHidden() {\n").append("        hidden.value = editor.innerHTML;\n").append("        if (hidden.oninput && typeof hidden.oninput === 'function') {\n").append("            hidden.oninput();\n").append("        }\n").append("        const event = new Event('input', { bubbles: true });\n").append("        hidden.dispatchEvent(event);\n").append("    }\n").append("    editor.addEventListener('input', syncHidden);\n").append("    editor.addEventListener('DOMSubtreeModified', syncHidden);\n").append("    const form = editor.closest('form');\n").append("    if (form) form.addEventListener('submit', syncHidden);\n").append("    editor.addEventListener('keydown', function(e) {\n").append("        if (e.key === 'Enter') {\n").append("            const sel = window.getSelection();\n").append("            if (!sel.rangeCount) return;\n").append("            const range = sel.getRangeAt(0);\n").append("            let listItem = range.startContainer;\n").append("            while (listItem && listItem !== editor) {\n").append("                if (listItem.nodeName === 'LI') break;\n").append("                listItem = listItem.parentNode;\n").append("            }\n").append("            if (listItem && listItem.nodeName === 'LI') {\n").append("                e.preventDefault();\n").append("                const isEmpty = listItem.textContent.trim() === '';\n").append("                if (isEmpty) {\n").append("                    const parentList = listItem.parentNode;\n").append("                    const grandParentLi = parentList.parentNode;\n").append("                    if (grandParentLi && grandParentLi.nodeName === 'LI') {\n").append("                        const newLi = document.createElement('li');\n").append("                        newLi.innerHTML = '<br>';\n").append("                        grandParentLi.parentNode.insertBefore(newLi, grandParentLi.nextSibling);\n").append("                        listItem.remove();\n").append("                        range.setStart(newLi, 0);\n").append("                        range.collapse(true);\n").append("                        sel.removeAllRanges();\n").append("                        sel.addRange(range);\n").append("                    } else {\n").append("                        document.execCommand('outdent');\n").append("                        const br = document.createElement('br');\n").append("                        range.insertNode(br);\n").append("                        range.setStartAfter(br);\n").append("                        range.collapse(true);\n").append("                        sel.removeAllRanges();\n").append("                        sel.addRange(range);\n").append("                    }\n").append("                } else {\n").append("                    const newLi = document.createElement('li');\n").append("                    newLi.innerHTML = '<br>';\n").append("                    const nestedList = listItem.querySelector('ul, ol');\n").append("                    if (nestedList) {\n").append("                        nestedList.insertBefore(newLi, nestedList.firstChild);\n").append("                    } else {\n").append("                        listItem.parentNode.insertBefore(newLi, listItem.nextSibling);\n").append("                    }\n").append("                    range.setStart(newLi, 0);\n").append("                    range.collapse(true);\n").append("                    sel.removeAllRanges();\n").append("                    sel.addRange(range);\n").append("                }\n").append("            } else {\n").append("                e.preventDefault();\n").append("                const br = document.createElement('br');\n").append("                range.insertNode(br);\n").append("                range.setStartAfter(br);\n").append("                range.setEndAfter(br);\n").append("                sel.removeAllRanges();\n").append("                sel.addRange(range);\n").append("            }\n").append("        }\n").append("        if (e.key === 'Tab') {\n").append("            e.preventDefault();\n").append("            if(e.shiftKey) outdent(); else indent();\n").append("        }\n").append("    });\n").append("    window.applyColor = function(color) {\n").append("        try { document.execCommand('styleWithCSS', false, true); } catch(e){}\n").append("        const sel = window.getSelection();\n").append("        if (sel.rangeCount && sel.getRangeAt) {\n").append("            sel.removeAllRanges();\n").append("            sel.addRange(sel.getRangeAt(0));\n").append("        }\n").append("        editor.focus();\n").append("        document.execCommand('foreColor', false, color);\n").append("    };\n").append("}\n\n").append("function exec(cmd, value=null){ document.execCommand(cmd,false,value); }\n").append("function indent(){ document.execCommand('indent'); }\n").append("function outdent(){ document.execCommand('outdent'); }\n").append("function insertTable(rows=2,cols=2){\n").append("    let table='<table>'; for(let r=0;r<rows;r++){ table+='<tr>'; for(let c=0;c<cols;c++) table+='<td> </td>'; table+='</tr>'; } table+='</table>';\n").append("    document.execCommand('insertHTML', false, table);\n").append("}\n");
        for (String id : ids) {
            script.append("makeRichEditor('").append(id).append("');\n");
        }
        script.append("</script>\n");
        return script.toString();
    }

    public static String richEditorScript(String[] ids) {
        if (ids == null || ids.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<script>\n");
        sb.append("function exec(cmd, value=null){ document.execCommand(cmd,false,value); }\n");
        sb.append("function indent(){ document.execCommand('indent'); }\n");
        sb.append("function outdent(){ document.execCommand('outdent'); }\n");
        sb.append("function insertTable(rows=2,cols=2){\n").append("  let table='<table style=\"border-collapse:collapse;width:100%\">';\n").append("  for(let r=0;r<rows;r++){ table+='<tr>'; for(let c=0;c<cols;c++) table+='<td style=\"border:1px solid #999;padding:5px;\"> </td>'; table+='</tr>'; }\n").append("  table+='</table>'; document.execCommand('insertHTML', false, table);\n").append("}\n");
        sb.append("window.savedSelection=null;\n");
        sb.append("window.saveSelection=function(){\n").append("  const sel=window.getSelection();\n").append("  if(sel.rangeCount>0){\n").append("    window.savedSelection=sel.getRangeAt(0);\n").append("  }\n").append("};\n");
        sb.append("window.restoreSelection=function(){\n").append("  const sel=window.getSelection();\n").append("  if(window.savedSelection){\n").append("    sel.removeAllRanges();\n").append("    sel.addRange(window.savedSelection);\n").append("  }\n").append("};\n");
        sb.append("window.applyColor=function(color){\n").append("  window.restoreSelection();\n").append("  try{ document.execCommand('styleWithCSS', false, true); }catch(e){}\n").append("  document.execCommand('foreColor', false, color);\n").append("};\n");
        sb.append("function findNextFocusableElement(currentElement){\n").append("  const focusableSelector='input:not([disabled]):not([type=\"hidden\"]),select:not([disabled]),textarea:not([disabled]),button:not([disabled]),[tabindex]:not([tabindex=\"-1\"]):not([disabled]),[contenteditable=true]';\n").append("  const focusableElements=Array.from(document.querySelectorAll(focusableSelector));\n").append("  const currentIndex=focusableElements.indexOf(currentElement);\n").append("  return focusableElements[currentIndex+1]||null;\n").append("}\n");
        sb.append("function handleListEnter(editor){\n").append("  const sel=window.getSelection();\n").append("  if(!sel.rangeCount) return false;\n").append("  const range=sel.getRangeAt(0);\n").append("  let currentElement=range.startContainer;\n").append("  if(currentElement.nodeType===Node.TEXT_NODE) currentElement=currentElement.parentNode;\n").append("  let listItem=currentElement;\n").append("  while(listItem && listItem!==editor){\n").append("    if(listItem.nodeName==='LI') break;\n").append("    listItem=listItem.parentNode;\n").append("  }\n").append("  if(!listItem || listItem.nodeName!=='LI') return false;\n").append("  const parentList=listItem.parentNode;\n").append("  const isOrderedList=parentList.nodeName==='OL';\n").append("  const isEmpty=listItem.textContent.trim()==='';\n").append("  if(isEmpty){\n").append("    document.execCommand('outdent');\n").append("    const br=document.createElement('br');\n").append("    range.insertNode(br);\n").append("    range.setStartAfter(br);\n").append("    range.collapse(true);\n").append("    sel.removeAllRanges();\n").append("    sel.addRange(range);\n").append("  } else {\n").append("    const newLi=document.createElement('li');\n").append("    newLi.innerHTML='<br>';\n").append("    listItem.parentNode.insertBefore(newLi,listItem.nextSibling);\n").append("    range.setStart(newLi,0);\n").append("    range.collapse(true);\n").append("    sel.removeAllRanges();\n").append("    sel.addRange(range);\n").append("  }\n").append("  return true;\n").append("}\n");
        sb.append("function makeRichEditor(hiddenId){\n").append(" const hidden=document.getElementById(hiddenId);\n").append(" if(!hidden) return;\n").append(" const editorId=hiddenId+'_editor'; if(document.getElementById(editorId)) return;\n").append(" if(!document.getElementById('rich-editor-style')){\n").append("  const link=document.createElement('link'); link.rel='stylesheet'; link.href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css'; document.head.appendChild(link);\n").append("  const style=document.createElement('style'); style.id='rich-editor-style'; style.textContent=`\n").append("  .toolbar{background:#f8f8f8;padding:5px;border:1px solid #ccc;display:flex;flex-wrap:wrap;gap:5px;align-items:center;margin-bottom:5px;}\n").append("  .toolbar button,.toolbar select{padding:5px 8px;border:1px solid #aaa;border-radius:5px;background:white;cursor:pointer;}\n").append("  .toolbar button:hover{background:#eee;}\n").append("  .color-palette{display:flex;gap:3px;margin-left:5px;align-items:center;}\n").append("  .color{width:18px;height:18px;border:1px solid #ccc;border-radius:3px;cursor:pointer;}\n").append("  .color:hover{border:2px solid #000;}\n").append("  .rich-editor{min-height:200px;border:1px solid #ccc;padding:8px;margin-top:5px;outline:none;overflow:auto;resize:vertical;background:white;}\n").append("  .rich-editor:empty:before{content:attr(data-placeholder);color:#888;}\n").append("  table{border-collapse:collapse;margin:5px 0;} td,th{border:1px solid #999;padding:5px;}`;\n").append("  document.head.appendChild(style);\n").append(" }\n").append(" const toolbar=document.createElement('div'); toolbar.className='toolbar';\n").append(" toolbar.innerHTML=`\n").append("  <button type='button' onclick=\"exec('bold')\" title='Gras'><i class='fas fa-bold'></i></button>\n").append("  <button type='button' onclick=\"exec('italic')\" title='Italique'><i class='fas fa-italic'></i></button>\n").append("  <button type='button' onclick=\"exec('underline')\" title='Soulign\u00c3\u00a9'><i class='fas fa-underline'></i></button>\n").append("  <button type='button' onclick=\"exec('insertUnorderedList')\" title='Liste \u00c3\u00a0 puces'><i class='fas fa-list-ul'></i></button>\n").append("  <button type='button' onclick=\"exec('insertOrderedList')\" title='Liste num\u00c3\u00a9rot\u00c3\u00a9e'><i class='fas fa-list-ol'></i></button>\n").append("  <button type='button' onclick=\"indent()\" title='Indenter'><i class='fas fa-indent'></i></button>\n").append("  <button type='button' onclick=\"outdent()\" title='D\u00c3\u00a9sindenter'><i class='fas fa-outdent'></i></button>\n").append("  <button type='button' onclick=\"insertTable()\" title='Ins\u00c3\u00a9rer tableau'><i class='fas fa-table'></i></button>\n").append("  <button type='button' onclick=\"exec('insertLineBreak')\" title='Aller \u00c3\u00a0 la ligne'><i class='fas fa-level-down-alt'></i></button>\n").append("  <div class='color-palette'>\n").append("    <i class='fas fa-palette' style='margin-right:5px;color:#666;'></i>\n").append("    <div class='color' style='background:#000000' onclick=\"applyColor('#000000')\" title='Noir'></div>\n").append("    <div class='color' style='background:#ff0000' onclick=\"applyColor('#ff0000')\" title='Rouge'></div>\n").append("    <div class='color' style='background:#00aa00' onclick=\"applyColor('#00aa00')\" title='Vert'></div>\n").append("    <div class='color' style='background:#0000ff' onclick=\"applyColor('#0000ff')\" title='Bleu'></div>\n").append("    <div class='color' style='background:#ffa500' onclick=\"applyColor('#ffa500')\" title='Orange'></div>\n").append("    <div class='color' style='background:#800080' onclick=\"applyColor('#800080')\" title='Violet'></div>\n").append("  </div>\n").append("  <select onchange=\"exec('fontSize', this.value)\">\n").append("    <option value='2'>Texte</option>\n").append("    <option value='3' selected>Moyen</option>\n").append("    <option value='5'>Grand</option>\n").append("  </select>`;\n").append(" const editor=document.createElement('div'); editor.className='rich-editor'; editor.contentEditable=true; editor.id=editorId; editor.dataset.placeholder=hidden.getAttribute('data-placeholder')||'Ecris ici...';\n").append(" editor.innerHTML=hidden.value||'';\n").append(" hidden.style.display='none'; hidden.parentNode.insertBefore(toolbar,hidden); hidden.parentNode.insertBefore(editor,hidden);\n").append(" function syncHidden(){ hidden.value=editor.innerHTML; hidden.dispatchEvent(new Event('input',{bubbles:true})); }\n").append(" editor.addEventListener('input',syncHidden);\n").append(" editor.addEventListener('DOMSubtreeModified',syncHidden);\n").append(" editor.addEventListener('mouseup',saveSelection);\n").append(" editor.addEventListener('keyup',saveSelection);\n").append(" editor.addEventListener('focus',saveSelection);\n").append(" editor.addEventListener('keydown',function(e){\n").append("   if(e.key==='Enter'){\n").append("     e.preventDefault();\n").append("     if(!handleListEnter(editor)){\n").append("       document.execCommand('insertHTML',false,'<div><br></div>');\n").append("     }\n").append("   }\n").append("   else if(e.key==='Tab'){\n").append("     e.preventDefault();\n").append("     if(e.shiftKey){\n").append("       document.execCommand('outdent');\n").append("     } else {\n").append("       const nextElement=findNextFocusableElement(editor);\n").append("       if(nextElement){\n").append("         nextElement.focus();\n").append("       } else {\n").append("         document.execCommand('indent');\n").append("       }\n").append("     }\n").append("   }\n").append(" });\n").append(" editor.addEventListener('paste',function(e){ e.preventDefault(); const text=(e.clipboardData||window.clipboardData).getData('text/plain'); document.execCommand('insertText',false,text); });\n").append(" const form=editor.closest('form'); if(form) form.addEventListener('submit',syncHidden);\n").append("}\n");
        sb.append("(function(){\n");
        for (String id : ids) {
            sb.append("  var initial=document.getElementById('initial_").append(id).append("');\n");
            sb.append("  var hidden=document.getElementById('").append(id).append("');\n");
            sb.append("  if(initial && hidden){ hidden.value=initial.innerHTML; }\n");
            sb.append("  makeRichEditor('").append(id).append("');\n");
        }
        sb.append("})();\n");
        sb.append("</script>\n");
        return sb.toString();
    }

    public void setAutoCompleteDependanteValeur(String champDependante, String mapping, String nomTable, String ac_valeur, String filtre, String champRetour, String champRetourMapping) throws Exception {
        this.setAutre("onBlur=\"dynamicAutocompleteDependantOne(this, '" + filtre + "', 'LIKE', '" + champDependante + "', 'null', '" + ac_valeur + "', 'null', '" + nomTable + "', '" + mapping + "', 'true', '" + champRetour + "', '" + champRetourMapping + "')\"");
    }

    public void setAutoCompleteDependante(String champDependante, String mapping, String nomTable, String ac_valeur, String filtre, String champRetour, String champRetourMapping, String aWhere) throws Exception {
        this.setAutre("onBlur=\"dynamicAutocompleteDependantOne(this, '" + filtre + "', 'LIKE', '" + champDependante + "', 'null', '" + ac_valeur + "', 'null', '" + nomTable + "', '" + mapping + "', 'true', '" + champRetour + "', '" + champRetourMapping + "', '" + aWhere + "')\"");
    }

    public static void setAutoCompleteDependanteValeur(Champ[] tChamp, Champ[] champDependante, String mapping, String ac_valeur, String nomTable, String filtre, String champRetour, String champRetourMapping) throws Exception {
        int i = 0;
        for (Champ c : tChamp) {
            c.setAutoCompleteDependanteValeur(champDependante[i].getNom(), mapping, nomTable, ac_valeur, filtre, champRetour, champRetourMapping);
            ++i;
        }
    }

    public static void setAutoCompleteDependante(Champ[] tChamp, Champ[] champDependante, String mapping, String ac_valeur, String nomTable, String filtre, String champRetour, String champRetourMapping, String aWhere) throws Exception {
        int i = 0;
        for (Champ c : tChamp) {
            c.setAutoCompleteDependante(champDependante[i].getNom(), mapping, ac_valeur, nomTable, filtre, champRetour, champRetourMapping, aWhere);
            ++i;
        }
    }

    public void setAutoCompleteDependante(String champDependante, String mapping, String nomTable, String filtre, String champRetour, String champRetourMapping) throws Exception {
        this.setAutre("onBlur=\"dynamicAutocompleteDependantOne(this, '" + filtre + "', 'LIKE', '" + champDependante + "', 'null', 'id', 'null', '" + nomTable + "', '" + mapping + "', 'true', '" + champRetour + "', '" + champRetourMapping + "')\"");
    }

    public void setAutoCompleteDependante(String champDependante, String mapping, String nomTable, String filtre, String champRetour, String champRetourMapping, String aWhere) throws Exception {
        this.setAutre("onBlur=\"dynamicAutocompleteDependantOne(this, '" + filtre + "', 'LIKE', '" + champDependante + "', 'null', 'id', 'null', '" + nomTable + "', '" + mapping + "', 'true', '" + champRetour + "', '" + champRetourMapping + "', '" + aWhere + "')\"");
    }

    public static void setAutoCompleteDependante(Champ[] tChamp, Champ[] champDependante, String mapping, String nomTable, String filtre, String champRetour, String champRetourMapping) throws Exception {
        int i = 0;
        for (Champ c : tChamp) {
            c.setAutoCompleteDependante(champDependante[i].getNom(), mapping, nomTable, filtre, champRetour, champRetourMapping);
            ++i;
        }
    }

    public static void setAutoCompleteDependante(Champ[] tChamp, Champ[] champDependante, String mapping, String nomTable, String filtre, String champRetour, String champRetourMapping, String aWhere) throws Exception {
        int i = 0;
        for (Champ c : tChamp) {
            c.setAutoCompleteDependante(champDependante[i].getNom(), mapping, nomTable, filtre, champRetour, champRetourMapping, aWhere);
            ++i;
        }
    }
}

