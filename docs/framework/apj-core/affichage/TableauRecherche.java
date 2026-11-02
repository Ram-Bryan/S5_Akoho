/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  user.UserEJB
 */
package affichage;

import affichage.ApjSplitView;
import affichage.Formulaire;
import bean.CGenUtil;
import bean.Champ;
import bean.ClassEtat;
import bean.ClassMAPTable;
import bean.LibelleAffichage;
import bean.ListeColonneTable;
import bean.ValeurEtiquette;
import constante.ConstanteAffichage;
import constante.ConstanteEtat;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import user.UserEJB;
import utilitaire.Utilitaire;

public class TableauRecherche {
    private String[] libeEntete;
    private ClassMAPTable[] data;
    private String cssEntete = "head";
    private String cssTableau = "table table-striped table-hover";
    private String html;
    private String htmlRecap;
    private String titre = "LISTE";
    private int[] propEntet;
    private String tailleTableau = "100%";
    private String[] libelleAffiche;
    private String[][] dataDirecte;
    private String[] lien;
    private String[] colonneLien;
    private String modelePage;
    private String[] attLien = null;
    private String[] valeurLien;
    private String expxml = "";
    private String expcsv;
    private String exppdf;
    private double[][] pourc;
    private String[] pourcentage;
    private String critereLienString = "";
    private boolean afficheBouttondevalider = false;
    private String ids;
    private String[] apresLienTab = null;
    Field[] listeField = null;
    private String[] urlLien;
    private String[] urlLienAffiche;
    private String nameBoutton = "Valider";
    private String nameActe = "attacher";
    private String nameBoutton2 = "";
    private String nameActe2 = "";
    private Map libelleEnteteAAffiche = new HashMap();
    private boolean libelleEnteteAAfficheAnnotationFait = false;
    private ClassMAPTable[] dataM1;
    private List<String> colGroupe = null;
    private String colMoisMoins1;
    private String nomTable = "";
    private String objectClass = "";
    private String buteApres = "";
    private String pageModif = "";
    private String sessionLien = "";
    private boolean groupe = false;
    private String lienFormulaire = "";
    private String[] colonneCacher = null;
    private String[] colonneModal = null;
    String lienFille = null;
    boolean isModalOnClick = false;
    private boolean apjSplitEnabled = false;
    private UserEJB userEJB;
    String tailleImage = "50";
    boolean valdierMultiple = true;
    private String[] boutons;
    private String[] boutonsActe;
    private String[] boutonsClass;
    private Map<String, String> libelle_champ = new HashMap<String, String>();
    Map<String, String> lienClicDroite = new HashMap<String, String>();
    ValeurEtiquette[][] valeurEtiquette;
    String pagination;
    String nbObject;
    boolean isTab = false;
    boolean isRecap = false;
    private boolean dataTableApjEnabled = true;
    private Formulaire formu;

    public String[] getBoutons() {
        return this.boutons;
    }

    public void setBoutons(String[] boutons) {
        this.boutons = boutons;
    }

    public String[] getBoutonsActe() {
        return this.boutonsActe;
    }

    public void setBoutonsActe(String[] boutonsActe) {
        this.boutonsActe = boutonsActe;
    }

    public String[] getBoutonsClass() {
        return this.boutonsClass;
    }

    public void setBoutonsClass(String[] boutonsClass) {
        this.boutonsClass = boutonsClass;
    }

    public boolean isValdierMultiple() {
        return this.valdierMultiple;
    }

    public void setValdierMultiple(boolean valdierMultiple) {
        this.valdierMultiple = valdierMultiple;
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

    public boolean isModalOnClick() {
        return this.isModalOnClick;
    }

    public String getLienFille() {
        return this.lienFille;
    }

    public void setLienFille(String lienFille) {
        this.lienFille = lienFille;
    }

    public void setModalOnClick(boolean modalOnClick) {
        this.isModalOnClick = modalOnClick;
    }

    public void setModalOnClick(boolean modalOnClick, String[] colonneModal) {
        this.isModalOnClick = modalOnClick;
        this.colonneModal = colonneModal;
    }

    public boolean isApjSplitEnabled() {
        return this.apjSplitEnabled;
    }

    public void setApjSplitEnabled(boolean apjSplitEnabled) {
        this.apjSplitEnabled = apjSplitEnabled;
    }

    private String appendApjSplitActivation(String tableHtml) {
        if (tableHtml == null || !this.apjSplitEnabled) {
            return tableHtml;
        }
        return tableHtml + ApjSplitView.getActivationScript();
    }

    public String[] getColonneModal() {
        return this.colonneModal;
    }

    public void setColonneModal(String[] colonneModal) {
        this.colonneModal = colonneModal;
    }

    public Map<String, String> getLienClicDroite() {
        return this.lienClicDroite;
    }

    public void setLienClicDroite(Map<String, String> lienClicDroite) {
        this.lienClicDroite = lienClicDroite;
    }

    private boolean isActionAllowed(ClassMAPTable obj, String key, String value) {
        if (obj == null) {
            return true;
        }
        int etat = -1;
        if (obj instanceof ClassEtat) {
            etat = ((ClassEtat)obj).getEtat();
        } else {
            try {
                Method m = obj.getClass().getMethod("getEtat", new Class[0]);
                Object res = m.invoke(obj, new Object[0]);
                if (res instanceof Number) {
                    etat = ((Number)res).intValue();
                }
            }
            catch (NoSuchMethodException m) {
            }
            catch (Exception ex) {
                System.err.println("[TableauRecherche] Erreur lors de la r\u00c3\u00a9cup\u00c3\u00a9ration de l'\u00c3\u00a9tat via r\u00c3\u00a9flexion sur " + obj.getClass().getName() + " : " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        if (etat >= ConstanteEtat.getEtatValider()) {
            String v;
            String k = key != null ? key.toLowerCase() : "";
            String string = v = value != null ? value.toLowerCase() : "";
            if (k.contains("modifier") || k.contains("modif") || v.contains("-modif.jsp")) {
                return false;
            }
        }
        return true;
    }

    public String getColonneLienId(String cle) throws Exception {
        String[] valSplit = cle.split("-");
        if (valSplit.length == 1) {
            return "id";
        }
        return valSplit[1];
    }

    public String getCleLien(String cle) throws Exception {
        String[] valSplit = cle.split("-");
        return valSplit[0];
    }

    public boolean isDataTableApjEnabled() {
        return this.dataTableApjEnabled;
    }

    public void setDataTableApjEnabled(boolean dataTableApjEnabled) {
        this.dataTableApjEnabled = dataTableApjEnabled;
    }

    public boolean getIsRecap() {
        return this.isRecap;
    }

    public void setIsRecap(boolean isRecap) {
        this.isRecap = isRecap;
    }

    public boolean getIsTab() {
        return this.isTab;
    }

    public void setIsTab(boolean isTab) {
        this.isTab = isTab;
    }

    public String getPagination() {
        return this.pagination == null ? "" : this.pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public String getNbObject() {
        return this.nbObject == null ? "" : this.nbObject;
    }

    public void setNbObject(String nbObject) {
        this.nbObject = nbObject;
    }

    public String getSessionLien() {
        return this.sessionLien;
    }

    public void setSessionLien(String sessionLien) {
        this.sessionLien = sessionLien;
    }

    public String getNomTable() {
        return this.nomTable;
    }

    public void setNomTable(String nomTable) {
        this.nomTable = nomTable;
    }

    public String getObjectClass() {
        return this.objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public String getButeApres() {
        return this.buteApres;
    }

    public void setButeApres(String buteApres) {
        this.buteApres = buteApres;
    }

    public String getPageModif() {
        return this.pageModif;
    }

    public void setPageModif(String pageModif) {
        this.pageModif = pageModif;
    }

    public Formulaire getFormu() {
        return this.formu;
    }

    public void setFormu(Formulaire formu) {
        this.formu = formu;
    }

    public void construire_libelle(String[] champ, String[] libelle) {
        for (int i = 0; i < champ.length; ++i) {
            this.libelle_champ.put(champ[i], libelle[i]);
        }
    }

    public Map<String, String> getLibelle_champ() {
        return this.libelle_champ;
    }

    public void setLibelle_champ(Map<String, String> libelle_champ) {
        this.libelle_champ = libelle_champ;
    }

    public String[] getColonneCacher() {
        return this.colonneCacher;
    }

    public void setColonneCacher(String[] colonneCacher) {
        this.colonneCacher = colonneCacher;
    }

    public boolean isColumnToHide(String colName) {
        if (this.getColonneCacher() != null && this.getColonneCacher().length > 0) {
            for (int i = 0; i < this.getColonneCacher().length; ++i) {
                String col = "Somme de " + this.getColonneCacher()[i];
                if (this.getColonneCacher()[i].compareToIgnoreCase(colName) != 0 && col.compareToIgnoreCase(colName) != 0) continue;
                return true;
            }
        }
        return false;
    }

    public String getLienFormulaire() {
        return this.lienFormulaire;
    }

    public void setLienFormulaire(String lienFormulaire) {
        this.lienFormulaire = lienFormulaire;
    }

    public boolean isGroupe() {
        return this.groupe;
    }

    public void setGroupe(boolean groupe) {
        this.groupe = groupe;
    }

    public List<String> getColGroupe() {
        return this.colGroupe;
    }

    public void setColGroupe(List<String> colGroupe) {
        this.colGroupe = colGroupe;
    }

    public String getColMoisMoins1() {
        return this.colMoisMoins1;
    }

    public void setColMoisMoins1(String colMoisMoins1) {
        this.colMoisMoins1 = colMoisMoins1;
    }

    public ClassMAPTable[] getDataM1() {
        return this.dataM1;
    }

    public void setDataM1(ClassMAPTable[] dataM1) {
        this.dataM1 = dataM1;
    }

    public String getNameBoutton2() {
        return this.nameBoutton2;
    }

    public void setNameBoutton2(String nameBoutton2) {
        this.nameBoutton2 = nameBoutton2;
    }

    public String getNameActe2() {
        return this.nameActe2;
    }

    public void setNameActe2(String nameActe2) {
        this.nameActe2 = nameActe2;
    }

    public void setNameBoutton(String nameBoutton) {
        this.nameBoutton = nameBoutton;
    }

    public void setNameActe(String nameActe) {
        this.nameActe = nameActe;
    }

    public String[] getUrlLienAffiche() {
        return this.urlLienAffiche;
    }

    public void setUrlLienAffiche(String[] urlLienAffiche) {
        this.urlLienAffiche = urlLienAffiche;
    }

    public Field[] getListeField() {
        return this.listeField;
    }

    public void setListeField(Field[] listeField) {
        this.listeField = listeField;
    }

    public String[] getApresLienTab() {
        return this.apresLienTab;
    }

    public void setApresLienTab(String[] apresLienTab) {
        this.apresLienTab = apresLienTab;
    }

    public boolean isAfficheBouttondevalider() {
        return this.afficheBouttondevalider;
    }

    public void setAfficheBouttondevalider(boolean afficheBouttondevalider) {
        this.afficheBouttondevalider = afficheBouttondevalider;
    }

    public String getHtmlRecap() throws Exception {
        if (this.htmlRecap == null || this.htmlRecap.compareToIgnoreCase("") == 0) {
            this.makeHtmlRecap();
        }
        return this.htmlRecap;
    }

    public void setHtmlRecap(String htmlRecap) {
        this.htmlRecap = htmlRecap;
    }

    public String getCritereLienString() {
        return this.critereLienString;
    }

    public void setCritereLienString(String critereLienString) {
        this.critereLienString = critereLienString;
    }

    public TableauRecherche(ClassMAPTable[] donne, String[] lib, int[] prop) throws Exception {
        this.setData(donne);
        this.setLibeEntete(lib);
        this.setPropEntet(prop);
        this.transformerDataString();
    }

    public TableauRecherche(ClassMAPTable[] donne, String[] libEnte) throws Exception {
        this.setData(donne);
        this.setLibeEntete(libEnte);
        this.transformerDataString();
    }

    public TableauRecherche(String[][] donne, String[] libEnte) throws Exception {
        this.setLibeEntete(libEnte);
        this.setDataDirecte(donne);
    }

    public TableauRecherche(ClassMAPTable[] donne, String[] libEnte, String critereLienTab, Field[] listFields) throws Exception {
        this.setData(donne);
        this.setListeField(listFields);
        this.setCritereLienString(critereLienTab);
        this.setLibeEntete(libEnte);
        this.transformerDataString();
    }

    public TableauRecherche(ClassMAPTable[] donne, String[] libEnte, String critereLienTab) throws Exception {
        this.setData(donne);
        this.setCritereLienString(critereLienTab);
        this.setLibeEntete(libEnte);
        this.transformerDataString();
    }

    public TableauRecherche(ClassMAPTable[] donne, String[] libEnte, String critereLienTab, String ids) throws Exception {
        this.setData(donne);
        this.setCritereLienString(critereLienTab);
        this.setLibeEntete(libEnte);
        this.setIds(ids);
        this.transformerDataString();
    }

    public TableauRecherche(ClassMAPTable[] donne, String[] libEnte, String[] pourcentage, double[][] pourc, String critereLienTab) throws Exception {
        this.setData(donne);
        this.setPourc(pourc);
        this.setLibeEntete(libEnte);
        this.setCritereLienString(critereLienTab);
        this.transformerDataString();
    }

    public TableauRecherche(ClassMAPTable[] donne, String[] libEnte, String[] pourcentage, double[][] pourc, String critereLienTab, Field[] listFields) throws Exception {
        this.setData(donne);
        this.setPourc(pourc);
        this.setListeField(listFields);
        this.setLibeEntete(libEnte);
        this.setCritereLienString(critereLienTab);
        this.transformerDataString();
    }

    public String getHtmlTemplate() throws Exception {
        if (this.html == null || this.html.compareToIgnoreCase("") == 0) {
            this.makeHtmlTemplate();
        }
        return this.appendApjSplitActivation(this.html);
    }

    public String getHtmlDataTableApj() {
        return "<p class=\"apj-table-column-filter-empty\" data-apj-filter-empty hidden>Aucune ligne ne correspond \u00c3\u00a0 tous les filtres actifs.</p>";
    }

    public String getJsDataTableApj() {
        StringBuilder js = new StringBuilder();
        js.append("<script>\n");
        js.append("(function (window, document) {\n");
        js.append("      \"use strict\";\n");
        js.append("\n");
        js.append("      // Le module et ses styles ne sont install\u00c3\u00a9s qu'une fois, m\u00c3\u00aame avec plusieurs tableaux.\n");
        js.append("      if (window.ApjDataTableFilter) {\n");
        js.append("        window.ApjDataTableFilter.initAll(document);\n");
        js.append("        return;\n");
        js.append("      }\n");
        js.append("\n");
        js.append("      const STYLE_ID = \"apj-data-table-filter-styles\";\n");
        js.append("      if (!document.getElementById(STYLE_ID)) {\n");
        js.append("        const style = document.createElement(\"style\");\n");
        js.append("        style.id = STYLE_ID;\n");
        js.append("        style.textContent =\n");
        js.append("          \"[data-apj-filter-root] { isolation: isolate; }\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter,\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter * { box-sizing: border-box; }\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter { display: block; width: 100%; min-width: 0; }\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-summary {\\n\" +\n");
        js.append("          \"  display: flex; min-width: 0; min-height: 30px; align-items: center; gap: 6px;\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-label {\\n\" +\n");
        js.append("          \"  display: block; min-width: 0; flex: 1; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;align-items:center\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-label > a {\\n\" +\n");
        js.append("          \"  min-width: 0; overflow: hidden; text-overflow: ellipsis; color: var(--Text-primary);display:flex;\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-toggle {\\n\" +\n");
        js.append("          \"  position: relative; display: inline-grid; width: 28px; height: 28px; flex: 0 0 auto;\\n\" +\n");
        js.append("          \"  place-items: center; padding: 0; border: 0; border-radius: 7px; background: transparent;\\n\" +\n");
        js.append("          \"  color: var(--Text-secondary, #94a3b8); cursor: pointer;\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-toggle:hover,\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-toggle:focus-visible {\\n\" +\n");
        js.append("          \"  background: var(--Background-tertiaire, #eef2f7); color: var(--Text-primary, #1e293b); outline: 0;\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter.is-active .apj-table-column-filter-toggle {\\n\" +\n");
        js.append("          \"  background: var(--Primaire-blue-main-color, white); color: var(--White, black);\\n\" +\n");
        js.append("          \"  box-shadow: inset 0 0 0 1px rgba(79, 70, 229, .2);\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter.is-active .apj-table-column-filter-toggle::after {\\n\" +\n");
        js.append("          \"  position: absolute; top: 4px; right: 4px; width: 5px; height: 5px;\\n\" +\n");
        js.append("          \"  border: 1px solid #fff; border-radius: 50%; background: var(--White, blue); content: \\\"\\\";\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-toggle .icon {\\n\" +\n");
        js.append("          \"  width: 15px; height: 15px; flex: 0 0 auto; fill: none; stroke: currentColor;\\n\" +\n");
        js.append("          \"  stroke-linecap: round; stroke-linejoin: round; stroke-width: 1.8;\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-editor {\\n\" +\n");
        js.append("          \"  position: relative; display: none; width: 100%; align-items: center;\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter.is-editing .apj-table-column-filter-summary { display: none; }\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter.is-editing .apj-table-column-filter-editor { display: flex; }\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-input {\\n\" +\n");
        js.append("          \"  width: 100%; min-width: 76px; height: 30px; padding: 0 30px 0 9px;\\n\" +\n");
        js.append("          \"  border: 1px solid var(--Border, #cbd5e1); border-radius: 7px;\\n\" +\n");
        js.append("          \"  background: var(--Background-primaire, #fff); color: var(--Text-primary, #1e293b);\\n\" +\n");
        js.append("          \"  font: inherit; font-size: 12.5px; font-weight: 500; outline: 0;\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-input::placeholder {\\n\" +\n");
        js.append("          \"  color: var(--Text-secondary, #94a3b8);\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-input:focus {\\n\" +\n");
        js.append("          \"  border-color: var(--Primaire-blue-main-color, #818cf8); box-shadow: 0 0 0 3px rgba(129, 140, 248, .2);\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-cancel {\\n\" +\n");
        js.append("          \"  position: absolute; right: 3px; display: inline-grid; width: 25px; height: 25px;\\n\" +\n");
        js.append("          \"  place-items: center; padding: 0; border: 0; border-radius: 50%; background: transparent;\\n\" +\n");
        js.append("          \"  color: var(--Text-secondary, #64748b); cursor: pointer; font-size: 17px; line-height: 1;\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-cancel:hover,\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-cancel:focus-visible {\\n\" +\n");
        js.append("          \"  background: var(--Background-tertiaire, #eef2f7); color: var(--Text-primary, #1e293b); outline: 0;\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] tbody tr.apj-table-filter-row-hidden { display: none !important; }\\n\" +\n");
        js.append("          \"[data-apj-filter-root] .apj-table-column-filter-empty {\\n\" +\n");
        js.append("          \"  margin: 10px 12px; color: var(--Text-secondary, #64748b); font-size: 13px;\\n\" +\n");
        js.append("          \"}\\n\" +\n");
        js.append("          \"[data-apj-filter-root] [data-apj-filter-empty][hidden] { display: none !important; }\";\n");
        js.append("        document.head.appendChild(style);\n");
        js.append("      }\n");
        js.append("\n");
        js.append("      const SEARCH_ICON = '<svg viewBox=\"0 0 24 24\" class=\"icon\" aria-hidden=\"true\"><circle cx=\"11\" cy=\"11\" r=\"6.5\"/><line x1=\"16\" y1=\"16\" x2=\"21\" y2=\"21\"/></svg>';\n");
        js.append("      let instanceSequence = 0;\n");
        js.append("\n");
        js.append("      class CumulativeTableFilter {\n");
        js.append("        constructor(root, table) {\n");
        js.append("          this.root = root;\n");
        js.append("          this.table = table;\n");
        js.append("          this.instanceId = ++instanceSequence;\n");
        js.append("          this.filters = new Map();\n");
        js.append("          this.openColumnKey = null;\n");
        js.append("          this.cellValueCache = new WeakMap();\n");
        js.append("          this.refreshScheduled = false;\n");
        js.append("          this.emptyState = root.querySelector(\"[data-apj-filter-empty]\");\n");
        js.append("          this.checkAll = table.querySelector(\"[data-check-all]\");\n");
        js.append("\n");
        js.append("          // Seuls les en-t\u00c3\u00aates m\u00c3\u00a9tier marqu\u00c3\u00a9s par APJ deviennent filtrables.\n");
        js.append("          this.columns = Array.from(table.tHead.rows[0].cells)\n");
        js.append("            .map((header, cellIndex) => {\n");
        js.append("              if (!header.hasAttribute(\"data-apj-filter-column\")) return null;\n");
        js.append("              return {\n");
        js.append("                key: \"column-\" + cellIndex,\n");
        js.append("                label: header.textContent.replace(/\\s+/g, \" \").trim(),\n");
        js.append("                cellIndex: cellIndex,\n");
        js.append("                header: header\n");
        js.append("              };\n");
        js.append("            })\n");
        js.append("            .filter(Boolean);\n");
        js.append("\n");
        js.append("          this.installHeaderFilters();\n");
        js.append("          this.bindEvents();\n");
        js.append("          this.observeTable();\n");
        js.append("          this.applyFilters();\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        installHeaderFilters() {\n");
        js.append("          this.columns.forEach((column, index) => {\n");
        js.append("            const header = column.header;\n");
        js.append("            const originalContent = Array.from(header.childNodes);\n");
        js.append("            const control = document.createElement(\"div\");\n");
        js.append("            const summary = document.createElement(\"div\");\n");
        js.append("            const label = document.createElement(\"span\");\n");
        js.append("            const toggle = document.createElement(\"button\");\n");
        js.append("            const editor = document.createElement(\"div\");\n");
        js.append("            const input = document.createElement(\"input\");\n");
        js.append("            const cancel = document.createElement(\"button\");\n");
        js.append("            const inputId = \"apj-table-column-filter-\" + this.instanceId + \"-\" + index;\n");
        js.append("\n");
        js.append("            control.className = \"apj-table-column-filter\";\n");
        js.append("            control.dataset.apjColumnFilter = column.key;\n");
        js.append("\n");
        js.append("            summary.className = \"apj-table-column-filter-summary\";\n");
        js.append("            label.className = \"apj-table-column-filter-label\";\n");
        js.append("            originalContent.forEach((node) => label.appendChild(node));\n");
        js.append("\n");
        js.append("            toggle.type = \"button\";\n");
        js.append("            toggle.className = \"apj-table-column-filter-toggle\";\n");
        js.append("            toggle.dataset.apjColumnFilterToggle = column.key;\n");
        js.append("            toggle.setAttribute(\"aria-label\", \"Filtrer \" + column.label);\n");
        js.append("            toggle.setAttribute(\"aria-controls\", inputId);\n");
        js.append("            toggle.setAttribute(\"aria-expanded\", \"false\");\n");
        js.append("            toggle.setAttribute(\"aria-pressed\", \"false\");\n");
        js.append("            toggle.insertAdjacentHTML(\"beforeend\", SEARCH_ICON);\n");
        js.append("            summary.append(label, toggle);\n");
        js.append("\n");
        js.append("            editor.className = \"apj-table-column-filter-editor\";\n");
        js.append("            editor.hidden = true;\n");
        js.append("\n");
        js.append("            input.id = inputId;\n");
        js.append("            input.type = \"text\";\n");
        js.append("            input.className = \"apj-table-column-filter-input\";\n");
        js.append("            input.dataset.apjColumnFilterInput = column.key;\n");
        js.append("            input.placeholder = column.label;\n");
        js.append("            input.autocomplete = \"off\";\n");
        js.append("            input.spellcheck = false;\n");
        js.append("            input.setAttribute(\"aria-label\", \"Filtrer \" + column.label);\n");
        js.append("\n");
        js.append("            cancel.type = \"button\";\n");
        js.append("            cancel.className = \"apj-table-column-filter-cancel\";\n");
        js.append("            cancel.dataset.apjColumnFilterCancel = column.key;\n");
        js.append("            cancel.setAttribute(\"aria-label\", \"Fermer le filtre \" + column.label);\n");
        js.append("            cancel.textContent = \"\\u00d7\";\n");
        js.append("\n");
        js.append("            editor.append(input, cancel);\n");
        js.append("            control.append(summary, editor);\n");
        js.append("            header.replaceChildren(control);\n");
        js.append("\n");
        js.append("            column.control = control;\n");
        js.append("            column.toggle = toggle;\n");
        js.append("            column.editor = editor;\n");
        js.append("            column.input = input;\n");
        js.append("            column.cancel = cancel;\n");
        js.append("          });\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        bindEvents() {\n");
        js.append("          this.columns.forEach((column) => {\n");
        js.append("            column.toggle.addEventListener(\"click\", (event) => {\n");
        js.append("              event.preventDefault();\n");
        js.append("              event.stopPropagation();\n");
        js.append("              if (this.openColumnKey === column.key) {\n");
        js.append("                this.closeEditor(column.key, false);\n");
        js.append("              } else {\n");
        js.append("                this.openEditor(column.key);\n");
        js.append("              }\n");
        js.append("            });\n");
        js.append("\n");
        js.append("            // L'\u00c3\u00a9v\u00c3\u00a9nement input applique le filtre \u00c3\u00a0 chaque caract\u00c3\u00a8re saisi.\n");
        js.append("            column.input.addEventListener(\"input\", () => {\n");
        js.append("              this.updateFilter(column);\n");
        js.append("            });\n");
        js.append("\n");
        js.append("            column.input.addEventListener(\"keydown\", (event) => {\n");
        js.append("              if (event.key !== \"Escape\") return;\n");
        js.append("              event.preventDefault();\n");
        js.append("              this.closeEditor(column.key, true);\n");
        js.append("            });\n");
        js.append("\n");
        js.append("            column.cancel.addEventListener(\"click\", (event) => {\n");
        js.append("              event.preventDefault();\n");
        js.append("              event.stopPropagation();\n");
        js.append("              if (this.normalize(column.input.value) === \"\") {\n");
        js.append("                this.closeEditor(column.key, true);\n");
        js.append("                return;\n");
        js.append("              }\n");
        js.append("              column.input.value = \"\";\n");
        js.append("              this.updateFilter(column);\n");
        js.append("              column.input.focus();\n");
        js.append("            });\n");
        js.append("          });\n");
        js.append("\n");
        js.append("          this.handleDocumentClick = (event) => {\n");
        js.append("            const column = this.getColumn(this.openColumnKey);\n");
        js.append("            if (column && !column.control.contains(event.target)) {\n");
        js.append("              this.closeEditor(column.key, false);\n");
        js.append("            }\n");
        js.append("          };\n");
        js.append("\n");
        js.append("          this.handleDocumentKeydown = (event) => {\n");
        js.append("            if (event.key === \"Escape\" && this.openColumnKey) {\n");
        js.append("              this.closeEditor(this.openColumnKey, true);\n");
        js.append("            }\n");
        js.append("          };\n");
        js.append("\n");
        js.append("          document.addEventListener(\"click\", this.handleDocumentClick);\n");
        js.append("          document.addEventListener(\"keydown\", this.handleDocumentKeydown);\n");
        js.append("\n");
        js.append("          if (this.checkAll) {\n");
        js.append("            this.checkAll.addEventListener(\"change\", () => {\n");
        js.append("              this.getVisibleRows().forEach((row) => {\n");
        js.append("                const checkbox = row.querySelector('input[type=\"checkbox\"]');\n");
        js.append("                if (checkbox) checkbox.checked = this.checkAll.checked;\n");
        js.append("              });\n");
        js.append("              this.syncCheckAll();\n");
        js.append("            });\n");
        js.append("\n");
        js.append("            this.table.addEventListener(\"change\", (event) => {\n");
        js.append("              if (event.target.matches('input[type=\"checkbox\"]')) this.syncCheckAll();\n");
        js.append("            });\n");
        js.append("          }\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        openEditor(columnKey) {\n");
        js.append("          const column = this.getColumn(columnKey);\n");
        js.append("          if (!column) return;\n");
        js.append("\n");
        js.append("          if (this.openColumnKey && this.openColumnKey !== columnKey) {\n");
        js.append("            this.closeEditor(this.openColumnKey, false);\n");
        js.append("          }\n");
        js.append("\n");
        js.append("          this.openColumnKey = columnKey;\n");
        js.append("          column.editor.hidden = false;\n");
        js.append("          column.control.classList.add(\"is-editing\");\n");
        js.append("          column.toggle.setAttribute(\"aria-expanded\", \"true\");\n");
        js.append("          column.input.focus();\n");
        js.append("          column.input.select();\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        closeEditor(columnKey, restoreFocus) {\n");
        js.append("          const column = this.getColumn(columnKey);\n");
        js.append("          if (!column) return;\n");
        js.append("\n");
        js.append("          column.editor.hidden = true;\n");
        js.append("          column.control.classList.remove(\"is-editing\");\n");
        js.append("          column.toggle.setAttribute(\"aria-expanded\", \"false\");\n");
        js.append("          if (this.openColumnKey === columnKey) this.openColumnKey = null;\n");
        js.append("          if (restoreFocus) column.toggle.focus();\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        updateFilter(column) {\n");
        js.append("          const value = column.input.value;\n");
        js.append("          const active = this.normalize(value) !== \"\";\n");
        js.append("\n");
        js.append("          if (active) this.filters.set(column.key, { value: value });\n");
        js.append("          else this.filters.delete(column.key);\n");
        js.append("\n");
        js.append("          column.control.classList.toggle(\"is-active\", active);\n");
        js.append("          column.header.classList.toggle(\"is-filter-active\", active);\n");
        js.append("          column.toggle.setAttribute(\"aria-pressed\", String(active));\n");
        js.append("          column.cancel.setAttribute(\"aria-label\", (active ? \"Effacer\" : \"Fermer\") + \" le filtre \" + column.label);\n");
        js.append("          this.applyFilters();\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        observeTable() {\n");
        js.append("          // Les lignes ajout\u00c3\u00a9es apr\u00c3\u00a8s une r\u00c3\u00a9ponse AJAX h\u00c3\u00a9ritent des filtres actifs.\n");
        js.append("          this.observer = new MutationObserver(() => {\n");
        js.append("            this.cellValueCache = new WeakMap();\n");
        js.append("            if (this.refreshScheduled) return;\n");
        js.append("            this.refreshScheduled = true;\n");
        js.append("            requestAnimationFrame(() => {\n");
        js.append("              this.refreshScheduled = false;\n");
        js.append("              this.applyFilters();\n");
        js.append("            });\n");
        js.append("          });\n");
        js.append("\n");
        js.append("          this.observer.observe(this.table, {\n");
        js.append("            attributes: true,\n");
        js.append("            attributeFilter: [\"data-filter-value\", \"data-filter-ignore\"],\n");
        js.append("            childList: true,\n");
        js.append("            characterData: true,\n");
        js.append("            subtree: true\n");
        js.append("          });\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        applyFilters() {\n");
        js.append("          // Chaque ligne doit satisfaire tous les filtres actifs (AND).\n");
        js.append("          const activeFilters = Array.from(this.filters.entries())\n");
        js.append("            .map(([columnKey, filter]) => ({\n");
        js.append("              column: this.getColumn(columnKey),\n");
        js.append("              normalizedValue: this.normalize(filter.value)\n");
        js.append("            }))\n");
        js.append("            .filter((filter) => filter.column && filter.normalizedValue);\n");
        js.append("\n");
        js.append("          let visibleCount = 0;\n");
        js.append("          this.getDataRows().forEach((row) => {\n");
        js.append("            const matches = activeFilters.every((filter) => {\n");
        js.append("              return this.normalize(this.getCellValue(row, filter.column)).includes(filter.normalizedValue);\n");
        js.append("            });\n");
        js.append("            this.setRowVisibility(row, matches);\n");
        js.append("            if (matches) visibleCount += 1;\n");
        js.append("          });\n");
        js.append("\n");
        js.append("          if (this.emptyState) this.emptyState.hidden = visibleCount !== 0;\n");
        js.append("          this.syncCheckAll();\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        setRowVisibility(row, visible) {\n");
        js.append("          row.classList.toggle(\"apj-table-filter-row-hidden\", !visible);\n");
        js.append("          // Une fille suit sa m\u00c3\u00a8re et conserve exactement la m\u00c3\u00aame visibilit\u00c3\u00a9 de filtre.\n");
        js.append("          let detailRow = row.nextElementSibling;\n");
        js.append("          while (this.isDetailRow(detailRow)) {\n");
        js.append("            detailRow.classList.toggle(\"apj-table-filter-row-hidden\", !visible);\n");
        js.append("            detailRow = detailRow.nextElementSibling;\n");
        js.append("          }\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        isDetailRow(row) {\n");
        js.append("          if (!row) return false;\n");
        js.append("          if (row.hasAttribute(\"data-apj-filter-detail-row\") || row.classList.contains(\"collapse\")) return true;\n");
        js.append("          const firstCell = row.cells[0];\n");
        js.append("          return row.cells.length === 1 && firstCell && firstCell.classList.contains(\"collapse-container\");\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        getDataRows() {\n");
        js.append("          const rows = [];\n");
        js.append("          Array.from(this.table.tBodies).forEach((tbody) => {\n");
        js.append("            Array.from(tbody.rows).forEach((row) => {\n");
        js.append("              if (!this.isDetailRow(row)) rows.push(row);\n");
        js.append("            });\n");
        js.append("          });\n");
        js.append("          return rows;\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        getCellValue(row, column) {\n");
        js.append("          let rowCache = this.cellValueCache.get(row);\n");
        js.append("          if (!rowCache) {\n");
        js.append("            rowCache = new Map();\n");
        js.append("            this.cellValueCache.set(row, rowCache);\n");
        js.append("          }\n");
        js.append("          if (rowCache.has(column.key)) return rowCache.get(column.key);\n");
        js.append("\n");
        js.append("          const cell = row.cells[column.cellIndex];\n");
        js.append("          const value = cell ? this.extractBusinessText(cell) : \"\";\n");
        js.append("          rowCache.set(column.key, value);\n");
        js.append("          return value;\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        extractBusinessText(cell) {\n");
        js.append("          // Une valeur explicite prime; sinon on retient le dernier texte m\u00c3\u00a9tier non vide.\n");
        js.append("          const explicitElement = cell.hasAttribute(\"data-filter-value\")\n");
        js.append("            ? cell\n");
        js.append("            : cell.querySelector(\"[data-filter-value]\");\n");
        js.append("\n");
        js.append("          if (explicitElement) {\n");
        js.append("            const explicitValue = explicitElement.getAttribute(\"data-filter-value\");\n");
        js.append("            if (explicitValue && explicitValue.trim()) return explicitValue.trim();\n");
        js.append("            return explicitElement.textContent.replace(/\\s+/g, \" \").trim();\n");
        js.append("          }\n");
        js.append("\n");
        js.append("          const clone = cell.cloneNode(true);\n");
        js.append("          clone\n");
        js.append("            .querySelectorAll(\"[data-filter-ignore], script, style, svg, button, input, select, textarea\")\n");
        js.append("            .forEach((element) => element.remove());\n");
        js.append("\n");
        js.append("          const walker = document.createTreeWalker(clone, NodeFilter.SHOW_TEXT);\n");
        js.append("          let deepestText = \"\";\n");
        js.append("          let currentNode = walker.nextNode();\n");
        js.append("          while (currentNode) {\n");
        js.append("            const candidate = currentNode.nodeValue.replace(/\\s+/g, \" \").trim();\n");
        js.append("            if (candidate) deepestText = candidate;\n");
        js.append("            currentNode = walker.nextNode();\n");
        js.append("          }\n");
        js.append("          return deepestText;\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        normalize(value) {\n");
        js.append("          return String(value || \"\")\n");
        js.append("            .normalize(\"NFD\")\n");
        js.append("            .replace(/[\\u0300-\\u036f]/g, \"\")\n");
        js.append("            .toLocaleLowerCase(\"fr\")\n");
        js.append("            .replace(/\\s+/g, \" \")\n");
        js.append("            .trim();\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        getColumn(columnKey) {\n");
        js.append("          return this.columns.find((column) => column.key === columnKey);\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        getVisibleRows() {\n");
        js.append("          return this.getDataRows()\n");
        js.append("            .filter((row) => !row.classList.contains(\"apj-table-filter-row-hidden\"));\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        syncCheckAll() {\n");
        js.append("          if (!this.checkAll) return;\n");
        js.append("          const visibleCheckboxes = this.getVisibleRows()\n");
        js.append("            .map((row) => row.querySelector('input[type=\"checkbox\"]'))\n");
        js.append("            .filter(Boolean);\n");
        js.append("          const checkedCount = visibleCheckboxes.filter((checkbox) => checkbox.checked).length;\n");
        js.append("          this.checkAll.checked = visibleCheckboxes.length > 0 && checkedCount === visibleCheckboxes.length;\n");
        js.append("          this.checkAll.indeterminate = checkedCount > 0 && checkedCount < visibleCheckboxes.length;\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        getFilters() {\n");
        js.append("          return Array.from(this.filters.entries()).map(([columnKey, filter]) => ({\n");
        js.append("            column: columnKey,\n");
        js.append("            value: filter.value\n");
        js.append("          }));\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        refresh() {\n");
        js.append("          this.cellValueCache = new WeakMap();\n");
        js.append("          this.applyFilters();\n");
        js.append("        }\n");
        js.append("\n");
        js.append("        destroy() {\n");
        js.append("          this.observer.disconnect();\n");
        js.append("          document.removeEventListener(\"click\", this.handleDocumentClick);\n");
        js.append("          document.removeEventListener(\"keydown\", this.handleDocumentKeydown);\n");
        js.append("          if (this.openColumnKey) this.closeEditor(this.openColumnKey, false);\n");
        js.append("          this.getDataRows().forEach((row) => {\n");
        js.append("            row.classList.remove(\"apj-table-filter-row-hidden\");\n");
        js.append("          });\n");
        js.append("        }\n");
        js.append("      }\n");
        js.append("\n");
        js.append("      const instances = new WeakMap();\n");
        js.append("      const api = {\n");
        js.append("        initAll: function (scope) {\n");
        js.append("          const roots = [];\n");
        js.append("          if (scope.matches && scope.matches(\"[data-apj-filter-root]\")) roots.push(scope);\n");
        js.append("          if (scope.querySelectorAll) roots.push(...scope.querySelectorAll(\"[data-apj-filter-root]\"));\n");
        js.append("          roots.forEach((root) => {\n");
        js.append("            if (instances.has(root)) return;\n");
        js.append("            const table = root.querySelector(\"table[data-apj-filter-table]\");\n");
        js.append("            if (!table || !table.tHead || !table.tHead.rows.length || !table.tBodies.length) return;\n");
        js.append("            instances.set(root, new CumulativeTableFilter(root, table));\n");
        js.append("          });\n");
        js.append("        },\n");
        js.append("        getInstance: function (root) {\n");
        js.append("          return instances.get(root) || null;\n");
        js.append("        }\n");
        js.append("      };\n");
        js.append("\n");
        js.append("      window.ApjDataTableFilter = api;\n");
        js.append("      api.initAll(document);\n");
        js.append("\n");
        js.append("      const documentObserver = new MutationObserver((mutations) => {\n");
        js.append("        if (mutations.some((mutation) => mutation.addedNodes.length > 0)) api.initAll(document);\n");
        js.append("      });\n");
        js.append("      documentObserver.observe(document.documentElement, { childList: true, subtree: true });\n");
        js.append("    })(window, document);\n");
        js.append("</script>\n");
        return js.toString();
    }

    public void makeHtmlTemplate() throws Exception {
        String recapClass = "";
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            this.setExpxml("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau></tableau>\r\n");
            return;
        }
        boolean dataTableApjActive = this.isDataTableApjEnabled() && !this.getIsRecap();
        String temp = "";
        String tempcsv = "";
        String tempxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>";
        temp = temp + "<div class=\"col-md-12 nopadding \"" + (dataTableApjActive ? " data-apj-filter-root" : "") + ">";
        String boxClass = "box table-box";
        if (this.isRecap) {
            boxClass = "box-solid table-container recap-table";
        }
        temp = temp + "<div class=\" " + boxClass + " \">";
        if (this.getIsRecap()) {
            temp = temp + "<div>";
            temp = temp + "<h3 class=\"box-title cardtitle h520pxSemibold\" align=\"left\"><span multi-lang>" + this.getTitre() + "</span><span style=\"color: #686868;font-size: 18px;font-weight: 400;font-family: 'Inter', sans-serif;\">" + this.getNbObject() + "</span></h3>";
            temp = temp + "</div>";
        }
        temp = temp + "<div class=\"box-body table-responsive no-padding\">";
        temp = temp + "<div id=\"selectnonee\" " + (!this.getIsRecap() ? " class='scroll-table-container' " : "") + " >";
        temp = temp + "<table style='margin-bottom:0;' width=\"" + this.getTailleTableau() + "\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" class='table table-hover table-striped' " + (!this.getIsRecap() ? "id='table-apj'" : "") + (dataTableApjActive ? " data-apj-filter-table" : "") + " >";
        temp = temp + "<thead>";
        temp = temp + "<tr class=\"" + this.getCssEntete() + "\">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        Field[] listeFields = this.getListeField();
        nombreColonne = this.getDataDirecte()[0].length;
        String filterColumnAttribute = dataTableApjActive ? " data-apj-filter-column" : "";
        for (int i = 0; i < nombreColonne; ++i) {
            String alignement = "center";
            String libelle = this.getLibelleAffiche()[i];
            if (this.getCritereLienString().compareToIgnoreCase("") != 0) {
                if (this.getColGroupe() != null && this.getColGroupe().contains(this.getLibeEntete()[i])) {
                    temp = temp + "<th" + filterColumnAttribute + " width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\" class='contenuetable' style=\"" + (this.getLibeEntete()[i].compareToIgnoreCase("etatlib") == 0 ? ";text-align:center" : "") + "\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " style=''><span multi-lang>" + libelle + "</span> m-1<i class='material-symbols-rounded' style='font-size: 16px;color: var(--Text-primary);font-weight: 400;margin-left:var(--Bases-4-space-1,4px)' >expand_all</i></a></th>";
                }
                temp = temp + "<th" + filterColumnAttribute + " width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\" class='contenuetable' style=\"" + (this.getLibeEntete()[i].compareToIgnoreCase("etatlib") == 0 ? ";text-align:center" : "") + "\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " style=''><span multi-lang>" + libelle + "</span><i class='material-symbols-rounded' style='font-size: 16px;color: var(--Text-primary);font-weight: 400;margin-left:var(--Bases-4-space-1,4px)' >expand_all</i></a></th>";
            } else {
                if (this.getColGroupe() != null && this.getColGroupe().contains(this.getLibeEntete()[i])) {
                    temp = temp + "<th" + filterColumnAttribute + " width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\" class='contenuetable' style=\"background-color:#ffffff\" multi-lang>" + libelle + " m-1</th>";
                }
                temp = temp + "<th" + filterColumnAttribute + " width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\" class='contenuetable' style=\"background-color:#ffffff\" multi-lang>" + libelle + "</th>";
            }
            tempcsv = tempcsv + Utilitaire.cleanCsv(this.getLibelleAffiche()[i]);
            if (i == nombreColonne - 1) continue;
            tempcsv = tempcsv + ";";
        }
        tempcsv = tempcsv + "\r\n";
        if (!this.getPageModif().equalsIgnoreCase("") || !this.getButeApres().equalsIgnoreCase("") && !this.getObjectClass().equalsIgnoreCase("") && !this.getNomTable().equalsIgnoreCase("")) {
            temp = temp + "<th align=\"center\" valign=\"top\" style=\"background-color:#ffffff\"></th>";
        }
        if (this.getLienClicDroite() != null && this.getLienClicDroite().size() > 0 && !this.isRecap) {
            temp = temp + "<th class='contenuetable'></th>";
        }
        temp = temp + "</tr>";
        temp = temp + "</thead>";
        temp = temp + this.makeHtmlTemplateSansEntete();
        tempxml = tempxml + "</tableau>\r\n";
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        if (dataTableApjActive) {
            temp = temp + this.getHtmlDataTableApj();
        }
        temp = temp + "</div>";
        if (dataTableApjActive) {
            temp = temp + this.getJsDataTableApj();
        }
        this.setHtml(temp);
        this.setExpcsv(tempcsv + Utilitaire.champNull(this.getExpcsv()));
        String contenuXml = Utilitaire.champNull(this.getExpxml());
        if (contenuXml.contains("<row>")) {
            if (!contenuXml.contains("<?xml")) {
                this.setExpxml("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>\r\n" + contenuXml + "</tableau>\r\n");
            } else {
                if (!contenuXml.contains("</tableau>")) {
                    contenuXml = contenuXml + "</tableau>\r\n";
                }
                this.setExpxml(contenuXml);
            }
        } else {
            this.setExpxml(tempxml);
        }
    }

    public String makeHtmlTemplateSansEntete() throws Exception {
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return "";
        }
        String temp = "";
        String tempcsv = "";
        String tempxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        Field[] listeFields = this.getListeField();
        nombreColonne = this.getDataDirecte()[0].length;
        temp = temp + "<tbody>";
        String objectId = "";
        for (int i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr style=\"height:45px;\" onmouseover=\"this.style.backgroundColor='#EAEAEA'\" onmouseout=\"this.style.backgroundColor=''\">";
            tempxml = tempxml + "<row>";
            int nombreLien = 0;
            int j = 0;
            int l = 0;
            String actionLinksHtml = "";
            if (this.getLienClicDroite() != null && this.getLienClicDroite().size() > 0) {
                for (Map.Entry<String, String> lienClic : this.getLienClicDroite().entrySet()) {
                    if (!this.isActionAllowed(this.data[i], lienClic.getKey(), lienClic.getValue())) continue;
                    actionLinksHtml = actionLinksHtml + "        <p><a href=" + lienClic.getValue() + "&" + this.getColonneLienId(lienClic.getKey()) + "=" + this.data[i].getTuppleID() + "><i class=\"fa fa-pencil-square-o\"></i> " + this.getCleLien(lienClic.getKey()) + " </a></p>\n";
                }
            }
            for (j = 0; j < nombreColonne; ++j) {
                String alignement = "left";
                if (listeFields != null) {
                    for (int k = 0; k < listeFields.length; ++k) {
                        if (listeFields[k].getName().compareToIgnoreCase(this.getLibeEntete()[j]) != 0) continue;
                        if (listeFields[k].getType().getSimpleName().compareToIgnoreCase("String") == 0) {
                            alignement = "left";
                            break;
                        }
                        if (listeFields[k].getType().getSimpleName().compareToIgnoreCase("Double") == 0) {
                            alignement = "right";
                            break;
                        }
                        alignement = "center";
                        break;
                    }
                }
                String lien = "";
                if (this.getLienFille() != null && j == 0) {
                    lien = "<button type='button' class='toggle-button line-661' onclick='toggleRow(this)' id='" + this.getLienFille() + "' >+</button>";
                }
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    String rajoutLien = "";
                    if (this.getUrlLien() != null) {
                        rajoutLien = this.getRajoutLienMultiple(this.getData()[i], this.getUrlLien()[l], this.getUrlLienAffiche()[l]);
                    }
                    ++l;
                    String lienAutre = "";
                    if (this.isGroupe()) {
                        for (int g = 0; g < this.getLibeEntete().length - 1; ++g) {
                            lienAutre = lienAutre + this.getLibeEntete()[g] + "=" + this.getDataDirecte()[i][g] + "&";
                        }
                        lienAutre = lienAutre + this.getLibeEntete()[this.getLibeEntete().length - 1] + "=" + this.getDataDirecte()[i][this.getLibeEntete().length - 1] + "&" + this.getLienFormulaire();
                    } else {
                        lienAutre = colLien + "=" + valL;
                        objectId = valL;
                    }
                    if (!this.isModalOnClick) {
                        lien = lien + "<a href='" + this.getLien()[numeroColonne] + "&" + lienAutre + rajoutLien + "'>";
                    } else {
                        String lienLeger = this.getLien()[numeroColonne].replace("module.jsp", "moduleLeger.jsp");
                        String lienModal = lienLeger + "&" + lienAutre + rajoutLien;
                        lien = lien + "<a href='#'  onclick=ouvrirModal(event,'" + lienModal + "&ismodal=true','modalContent')>";
                    }
                    if (this.isModalOnClick && this.getColonneModal() != null && this.getColonneModal().length > 0) {
                        boolean estModal = false;
                        for (int k = 0; k < this.getColonneModal().length; ++k) {
                            if (!this.getColonneModal()[k].equalsIgnoreCase(this.getLibeEntete()[j])) continue;
                            estModal = true;
                            break;
                        }
                        if (estModal) {
                            String lienLeger = this.getLien()[numeroColonne].replace("module.jsp", "moduleLeger.jsp");
                            String lienModal = lienLeger + "&" + lienAutre + rajoutLien;
                            lien = lien + "<a href='#' onclick=\"ouvrirModal(event,'" + lienModal + "&ismodal=true','modalContent')\">";
                        } else {
                            lien = lien + "<a href='" + this.getLien()[numeroColonne] + "&" + lienAutre + rajoutLien + "'>";
                        }
                    }
                    apresLien = "</a>";
                }
                boolean test = false;
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if (this.getColGroupe() == null || this.getColGroupe().contains(this.getLibeEntete()[j])) {
                    // empty if block
                }
                if (this.getPourcentage() != null) {
                    for (int element = 0; element < this.getPourcentage().length; ++element) {
                        if (!this.libeEntete[j].equals(this.getPourcentage()[element])) continue;
                        test = true;
                        temp = temp + "<td style=\"vertical-align: middle;\" width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\">" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + " (" + Utilitaire.formaterAr(this.getPourc()[element][i]) + " %)" + apresLien + " </td>";
                        break;
                    }
                }
                if (!test) {
                    temp = this.getLibeEntete()[j].compareToIgnoreCase("etatLib") == 0 ? (this.isColumnToHide(this.getLibeEntete()[j]) ? temp + "<td class='contenuetable' style=\"text-align: center;\" width=\"" + this.getPropEntet()[j] + "%\" align='center' >" + lien + "- " + apresLien + " </td>" : temp + "<td class='contenuetable' style=\"text-align: center;\" width=\"" + this.getPropEntet()[j] + "%\" align=\"center\" >" + this.getDataDirecte()[i][j] + "</td>") : (this.getLibeEntete()[j].compareToIgnoreCase("image") == 0 ? (this.isColumnToHide(this.getLibeEntete()[j]) ? temp + "<td class='contenuetable' width=\"" + this.getPropEntet()[j] + "%\" align='center' >" + lien + "- " + apresLien + " </td>" : temp + "<td class='contenuetable' width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\" ><img src=\"/dossier/async/" + Utilitaire.champNull(this.getDataDirecte()[i][j]) + "\" style=\"width:" + this.getTailleImage() + "%;\"> </td>") : (this.isColumnToHide(this.getLibeEntete()[j]) ? temp + "<td class='contenuetable' width=\"" + this.getPropEntet()[j] + "%\" align='center' >" + lien + "- " + apresLien + " </td>" : temp + "<td class='contenuetable' width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\" " + (this.getLienFille() != null && j == 0 ? "style=\"display:flex;align-items:baseline;gap:4px;\"" : "") + ">" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + " " + apresLien + " </td>"));
                }
                tempcsv = tempcsv + Utilitaire.cleanCsv(this.getDataDirecte()[i][j]);
                tempxml = tempxml + "<" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">" + Utilitaire.cleanXmlValue(this.getDataDirecte()[i][j]) + "</" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">\r\n";
                if (j == nombreColonne - 1) continue;
                tempcsv = tempcsv + ";";
            }
            if (this.getLienClicDroite() != null && this.getLienClicDroite().size() > 0) {
                temp = !actionLinksHtml.isEmpty() ? temp + "<td style=\"padding: 4px 15px;position: relative;\">\n    <button type=\"button\" class=\"more_info\" id=\"ellipsis1\" style=\"border: none; background: none;\">\n       <i class=\"material-symbols-rounded\" >more_vert</i>\n    </button>    <div id=\"more_action1\" class=\"mode_action\" style=\"display: none;margin-left:unset\">\n" + actionLinksHtml + "    </div>\n</td>" : temp + "<td style=\"padding: 4px 15px;position: relative;\"></td>";
            }
            temp = temp + "</tr>";
            if (this.getLienFille() != null) {
                temp = temp + "<tr class='collapse' data-apj-filter-detail-row><td class='collapse-container' colspan=12><div id=" + this.getDataDirecte()[i][0] + "></td>\n            </tr>";
            }
            tempcsv = tempcsv + "\r\n";
            tempxml = tempxml + "</row>\r\n";
        }
        temp = temp + "</tbody>";
        tempxml = tempxml + "</tableau>\r\n";
        this.setExpcsv(Utilitaire.champNull(this.getExpcsv()) + tempcsv);
        this.setExpxml(tempxml);
        return temp;
    }

    public void makeHtml() throws Exception {
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            this.setExpxml("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau></tableau>\r\n");
            return;
        }
        String temp = "";
        String tempcsv = "";
        String tempxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>";
        temp = temp + "<div class=\"row\">";
        temp = temp + "<div class=\"row col-md-12\">";
        temp = temp + "<div class=\"box box-solid\">";
        temp = temp + "<div class=\"box-body table-responsive no-padding\">";
        temp = temp + "<div id=\"selectnonee\">";
        temp = temp + "<table width=\"" + this.getTailleTableau() + "\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" class=\"table table-hover table-bordered\">";
        temp = temp + "<thead>";
        temp = temp + "<tr class=\"" + this.getCssEntete() + "\">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        Field[] listeFields = this.getListeField();
        nombreColonne = this.getDataDirecte()[0].length;
        for (int i = 0; i < nombreColonne; ++i) {
            String alignement = "center";
            String libelle = this.getLibelleAffiche()[i];
            if (this.getCritereLienString().compareToIgnoreCase("") != 0) {
                if (this.getColGroupe() != null && this.getColGroupe().contains(this.getLibeEntete()[i])) {
                    temp = temp + "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\" style=\"background-color:#103a8e\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " style=''>" + libelle + " m-1</a></th>";
                }
                temp = temp + "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\" style=\"background-color:#103a8e\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " style=''>" + libelle + "</a></th>";
            } else {
                if (this.getColGroupe() != null && this.getColGroupe().contains(this.getLibeEntete()[i])) {
                    temp = temp + "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\" style=\"background-color:#ffffff\">" + libelle + " m-1</th>";
                }
                temp = temp + "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\" style=\"background-color:#ffffff\">" + libelle + "</th>";
            }
            tempcsv = tempcsv + Utilitaire.cleanCsv(this.getLibelleAffiche()[i]);
            if (i == nombreColonne - 1) continue;
            tempcsv = tempcsv + ";";
        }
        tempcsv = tempcsv + "\r\n";
        if (!this.getPageModif().equalsIgnoreCase("") || !this.getButeApres().equalsIgnoreCase("") && !this.getObjectClass().equalsIgnoreCase("") && !this.getNomTable().equalsIgnoreCase("")) {
            temp = temp + "<th align=\"center\" valign=\"top\" style=\"background-color:#ffffff\"></th>";
        }
        temp = temp + "</tr>";
        temp = temp + "</thead>";
        temp = temp + "<tbody>";
        String objectId = "";
        for (int i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=\"this.style.backgroundColor='#EAEAEA'\" onmouseout=\"this.style.backgroundColor=''\">";
            tempxml = tempxml + "<row>";
            int nombreLien = 0;
            int j = 0;
            int l = 0;
            for (j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                String alignement = "left";
                if (listeFields != null) {
                    for (int k = 0; k < listeFields.length; ++k) {
                        if (listeFields[k].getName().compareToIgnoreCase(this.getLibeEntete()[j]) != 0) continue;
                        if (listeFields[k].getType().getSimpleName().compareToIgnoreCase("String") == 0) {
                            alignement = "left";
                            break;
                        }
                        if (listeFields[k].getType().getSimpleName().compareToIgnoreCase("Double") == 0) {
                            alignement = "right";
                            break;
                        }
                        alignement = "center";
                        break;
                    }
                }
                String lien = "";
                if (this.getLienFille() != null && j == 0) {
                    lien = "<button class='toggle-button' type='button' onclick='toggleRow(this)' id='" + this.getLienFille() + "' >+</button>";
                }
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    String rajoutLien = "";
                    if (this.getUrlLien() != null) {
                        rajoutLien = this.getRajoutLienMultiple(this.getData()[i], this.getUrlLien()[l], this.getUrlLienAffiche()[l]);
                    }
                    ++l;
                    String lienAutre = "";
                    if (this.isGroupe()) {
                        for (int g = 0; g < this.getLibeEntete().length - 1; ++g) {
                            lienAutre = lienAutre + this.getLibeEntete()[g] + "=" + this.getDataDirecte()[i][g] + "&";
                        }
                        lienAutre = lienAutre + this.getLibeEntete()[this.getLibeEntete().length - 1] + "=" + this.getDataDirecte()[i][this.getLibeEntete().length - 1] + "&" + this.getLienFormulaire();
                    } else {
                        lienAutre = colLien + "=" + valL;
                        objectId = valL;
                    }
                    if (!this.isModalOnClick) {
                        lien = lien + "<a href='" + this.getLien()[numeroColonne] + "&" + lienAutre + rajoutLien + "'>";
                    } else {
                        String lienLeger = this.getLien()[numeroColonne].replace("module.jsp", "moduleLeger.jsp");
                        String lienModal = lienLeger + "&" + lienAutre + rajoutLien;
                        lien = lien + "<a href='#'  onclick=ouvrirModal(event,'" + lienModal + "&ismodal=true','modalContent')>";
                    }
                    if (this.isModalOnClick && this.getColonneModal() != null && this.getColonneModal().length > 0) {
                        for (int k = 0; k < this.getColonneModal().length; ++k) {
                            if (!this.getColonneModal()[k].equalsIgnoreCase(this.getLibeEntete()[j])) {
                                lien = lien + "<a href='" + this.getLien()[numeroColonne] + "&" + lienAutre + rajoutLien + "'>";
                                continue;
                            }
                            String rep = "";
                            if (!this.isGroupe()) {
                                rep = lienAutre.split("=")[0];
                            }
                            String lienLeger = this.getLien()[numeroColonne].replace("module.jsp", "moduleLeger.jsp");
                            String lienModal = lienLeger + "&" + lienAutre + rajoutLien;
                            lien = lien + "<a href='#'  onclick=ouvrirModal(event,'" + lienModal + "&ismodal=true','modalContent')>";
                        }
                    }
                    apresLien = "</a>";
                }
                boolean test = false;
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                if (this.getColGroupe() == null || this.getColGroupe().contains(this.getLibeEntete()[j])) {
                    // empty if block
                }
                if (this.getPourcentage() != null) {
                    for (int element = 0; element < this.getPourcentage().length; ++element) {
                        if (!this.libeEntete[j].equals(this.getPourcentage()[element])) continue;
                        test = true;
                        temp = temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\">" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + " (" + Utilitaire.formaterAr(this.getPourc()[element][i]) + " %)" + apresLien + " </td>";
                        break;
                    }
                }
                if (!test) {
                    temp = this.isColumnToHide(this.getLibeEntete()[j]) ? temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align='center' >" + lien + "- " + apresLien + " </td>" : temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\" >" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + " " + apresLien + " </td>";
                }
                tempcsv = tempcsv + Utilitaire.cleanCsv(this.getDataDirecte()[i][j]);
                tempxml = tempxml + "<" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">" + Utilitaire.cleanXmlValue(this.getDataDirecte()[i][j]) + "</" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">\r\n";
                if (j == nombreColonne - 1) continue;
                tempcsv = tempcsv + ";";
            }
            if (this.getLienClicDroite() != null && this.getLienClicDroite().size() > 0) {
                String actionLinksHtml = "";
                for (Map.Entry<String, String> lienClic : this.getLienClicDroite().entrySet()) {
                    if (!this.isActionAllowed(this.data[i], lienClic.getKey(), lienClic.getValue())) continue;
                    actionLinksHtml = actionLinksHtml + "        <p><a href=" + lienClic.getValue() + "&" + this.getColonneLienId(lienClic.getKey()) + "=" + this.data[i].getTuppleID() + "><i class=\"fa fa-pencil-square-o\"></i> " + this.getCleLien(lienClic.getKey()) + " </a></p>\n";
                }
                temp = !actionLinksHtml.isEmpty() ? temp + "<td style=\"padding: 4px 15px;position: relative;\">\n    <button type=\"button\" class=\"more_info\" style=\"border: none; background: none;\">\n       <i class=\"material-symbols-rounded\">more_vert</i>\n    </button>    <div class=\"mode_action\" style=\"display: none;\">\n" + actionLinksHtml + "    </div>\n</td>" : temp + "<td style=\"padding: 4px 15px;position: relative;\"></td>";
            }
            temp = temp + "</tr>";
            if (this.getLienFille() != null) {
                temp = temp + "<tr class='collapse' data-apj-filter-detail-row><td class='collapse-container' colspan=12><div id=" + this.getDataDirecte()[i][0] + "></td>\n            </tr>";
            }
            tempcsv = tempcsv + "\r\n";
            tempxml = tempxml + "</row>\r\n";
        }
        temp = temp + "</tbody>";
        tempxml = tempxml + "</tableau>\r\n";
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        this.setHtml(temp);
        this.setExpcsv(tempcsv);
        this.setExpxml(tempxml);
    }

    public void makeHtmlAvecOrdre() throws Exception {
        String alignement;
        int i;
        String temp = "";
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        String tempcsv = "";
        String tempxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>";
        temp = temp + "<div class=\"table-result\">";
        temp = temp + "<table width=\"" + this.getTailleTableau() + "\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" class=\"table table-sortable \">";
        temp = temp + "<thead>";
        temp = temp + "<tr class=\"table-header\">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        Field[] listeFields = this.getListeField();
        nombreColonne = this.getDataDirecte()[0].length;
        for (i = 0; i < nombreColonne; ++i) {
            String ordre = "";
            String colonneOrdre = "";
            String[] params = this.getCritereLienString().split("&");
            if (params.length > 0) {
                for (int j = 0; j < params.length; ++j) {
                    if (params[j].contains("ordre")) {
                        String string = ordre = params[j].split("=").length > 1 ? params[j].split("=")[1] : "";
                    }
                    if (!params[j].contains("colonne")) continue;
                    colonneOrdre = params[j].split("=").length > 1 ? params[j].split("=")[1] : "";
                }
            }
            alignement = "center";
            String libelle = this.getLibelleAffiche()[i];
            if (this.getCritereLienString().compareToIgnoreCase("") != 0) {
                String entete;
                if (this.getColGroupe() != null && this.getColGroupe().contains(this.getLibeEntete()[i])) {
                    entete = "";
                    entete = ordre.compareToIgnoreCase("asc") == 0 && colonneOrdre.compareToIgnoreCase(this.getLibeEntete()[i]) == 0 ? "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "&darr;<span class=\"fleche-asc-desc\">&uarr;</span> m-1</a></th>" : (ordre.compareToIgnoreCase("desc") == 0 && colonneOrdre.compareToIgnoreCase(this.getLibeEntete()[i]) == 0 ? "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "<span class=\"fleche-asc-desc\">&darr;</span>&uarr; m-1</a></th>" : "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "&darr;&uarr; m-1</a></th>");
                    temp = temp + entete;
                }
                entete = "";
                entete = ordre.compareToIgnoreCase("asc") == 0 && colonneOrdre.compareToIgnoreCase(this.getLibeEntete()[i]) == 0 ? "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "&darr;<span class=\"fleche-asc-desc\">&uarr;</span></a></th>" : (ordre.compareToIgnoreCase("desc") == 0 && colonneOrdre.compareToIgnoreCase(this.getLibeEntete()[i]) == 0 ? "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "<span class=\"fleche-asc-desc\">&darr;</span>&uarr;</a></th>" : "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "&darr;&uarr;</a></th>");
                temp = temp + entete;
            } else {
                if (this.getColGroupe() != null && this.getColGroupe().contains(this.getLibeEntete()[i])) {
                    temp = temp + "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\">" + libelle + " m-1</th>";
                }
                temp = temp + "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\">" + libelle + "</th>";
            }
            tempcsv = tempcsv + Utilitaire.cleanCsv(this.getLibelleAffiche()[i]);
            if (i == nombreColonne - 1) continue;
            tempcsv = tempcsv + ";";
        }
        tempcsv = tempcsv + "\r\n";
        temp = temp + "</tr>";
        temp = temp + "</thead>";
        temp = temp + "<tbody>\n";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=\"this.style.backgroundColor='#EAEAEA'\" onmouseout=\"this.style.backgroundColor=''\">";
            tempxml = tempxml + "<row>";
            int nombreLien = 0;
            int j = 0;
            int l = 0;
            for (j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                alignement = "left";
                if (listeFields != null) {
                    for (int k = 0; k < listeFields.length; ++k) {
                        if (listeFields[k].getName().compareToIgnoreCase(this.getLibeEntete()[j]) != 0) continue;
                        if (listeFields[k].getType().getSimpleName().compareToIgnoreCase("String") == 0) {
                            alignement = "left";
                            break;
                        }
                        if (listeFields[k].getType().getSimpleName().compareToIgnoreCase("Double") == 0) {
                            alignement = "right";
                            break;
                        }
                        alignement = "center";
                        break;
                    }
                }
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    String rajoutLien = "";
                    if (this.getUrlLien() != null) {
                        rajoutLien = this.getRajoutLienMultiple(this.getData()[i], this.getUrlLien()[l], this.getUrlLienAffiche()[l]);
                    }
                    ++l;
                    String lienAutre = "";
                    if (this.isGroupe()) {
                        for (int g = 0; g < this.getLibeEntete().length - 1; ++g) {
                            lienAutre = lienAutre + this.getLibeEntete()[g] + "=" + this.getDataDirecte()[i][g] + "&";
                        }
                        lienAutre = lienAutre + this.getLibeEntete()[this.getLibeEntete().length - 1] + "=" + this.getDataDirecte()[i][this.getLibeEntete().length - 1] + "&" + this.getLienFormulaire();
                    } else {
                        lienAutre = colLien + "=" + valL;
                    }
                    lien = "<a href='" + this.getLien()[numeroColonne] + "&" + lienAutre + rajoutLien + "'>";
                    apresLien = "</a>";
                }
                boolean test = false;
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                if (this.getPourcentage() != null) {
                    for (int element = 0; element < this.getPourcentage().length; ++element) {
                        if (!this.libeEntete[j].equals(this.getPourcentage()[element])) continue;
                        test = true;
                        temp = temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\">" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + " (" + Utilitaire.formaterAr(this.getPourc()[element][i]) + " %)" + apresLien + " </td>";
                        break;
                    }
                }
                if (!test) {
                    temp = this.isColumnToHide(this.getLibeEntete()[j]) ? temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align='center' >" + lien + "- " + apresLien + " </td>" : temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\" >" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + " " + apresLien + " </td>";
                }
                tempcsv = tempcsv + Utilitaire.cleanCsv(this.getDataDirecte()[i][j]);
                tempxml = tempxml + "<" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">" + Utilitaire.cleanXmlValue(this.getDataDirecte()[i][j]) + "</" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">\r\n";
                if (j == nombreColonne - 1) continue;
                tempcsv = tempcsv + ";";
            }
            temp = temp + "</tr>";
            tempcsv = tempcsv + "\r\n";
            tempxml = tempxml + "</row>\r\n";
        }
        temp = temp + "</tbody>\n</table>\n</div>";
        this.setHtml(temp);
        this.setExpcsv(tempcsv);
        this.setExpxml(tempxml);
    }

    public void makeHtmlRecap() throws Exception {
        String temp = "";
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        temp = temp + "<div class=\"recap-container d-flex\">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        nombreColonne = this.getDataDirecte()[0].length;
        for (int p = 0; p < nombreLigne; ++p) {
            for (int j = 0; j < nombreColonne; ++j) {
                int nbrColMd = 12 / (nombreColonne - 1);
                if (nbrColMd <= 1) {
                    nbrColMd = 1;
                }
                if (nbrColMd >= 12) {
                    nbrColMd = 10;
                }
                String classe = "'col-md-" + nbrColMd + " recap p-3'";
                if (this.getLibeEntete()[j].compareToIgnoreCase("") == 0 || this.getDataDirecte()[p][j].compareToIgnoreCase("Total") == 0) continue;
                temp = temp + "<div class=" + classe + ">";
                temp = temp + "<div class=\"recap-header\">" + this.getLibelleAffiche()[j] + "</div>";
                temp = temp + "<div class='recap-data'>" + Utilitaire.champNull(this.getDataDirecte()[p][j]) + "</div>";
                temp = temp + "</div>";
            }
        }
        temp = temp + "</div>";
        this.setHtmlRecap(temp);
    }

    public void makeHtml(boolean recap, String recap1, String recap2) throws Exception {
        int i;
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        String temp = "";
        String tempcsv = "";
        String tempxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>";
        temp = temp + "<div class=\"row\">";
        temp = temp + "<div class=\"row col-md-12\">";
        temp = temp + "<div class=\"box\">";
        temp = temp + "<div class=\"box-header\">";
        temp = temp + "<h3 class=\"box-title\" align=\"center\">" + this.getTitre() + "</h3>";
        temp = temp + "</div>";
        temp = temp + "<div class=\"box-body table-responsive no-padding\">";
        temp = temp + "<div id=\"selectnonee\">";
        temp = temp + "<table width=\"" + this.getTailleTableau() + "\" border=\"1\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" class=\"table table-hover table-bordered\">";
        temp = temp + "<thead>";
        if (recap) {
            temp = temp + "<tr class=\"" + this.getCssEntete() + "\">";
            temp = temp + "<th></th>";
            temp = temp + "<th></th>";
            temp = temp + "<th></th>";
            temp = temp + "<th></th>";
            temp = temp + "<th></th>";
            temp = temp + "<th>Somme montant</th>";
            temp = temp + "<th>" + recap2 + "</th>";
            temp = temp + "<th>Somme credit</th>";
            temp = temp + "<th>" + recap2 + "</th>";
            temp = temp + "</tr>";
        }
        temp = temp + "<tr class=\"" + this.getCssEntete() + "\">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        Field[] listeFields = this.getListeField();
        nombreColonne = this.getDataDirecte()[0].length;
        for (i = 0; i < nombreColonne; ++i) {
            String alignement = "center";
            String libelle = this.getLibelleAffiche()[i];
            temp = this.getCritereLienString().compareToIgnoreCase("") != 0 ? temp + "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\" style=\"background-color:#103a8e\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " style=''>" + libelle + "</a></th>" : temp + "<th width=\"" + this.getPropEntet()[i] + "%\" align=\"" + alignement + "\" valign=\"top\" style=\"background-color:#103a8e\">" + libelle + "</th>";
            tempcsv = tempcsv + Utilitaire.cleanCsv(this.getLibelleAffiche()[i]);
            if (i == nombreColonne - 1) continue;
            tempcsv = tempcsv + ";";
        }
        tempcsv = tempcsv + "\r\n";
        temp = temp + "</tr>";
        temp = temp + "</thead>";
        temp = temp + "<tbody>";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=\"this.style.backgroundColor='#EAEAEA'\" onmouseout=\"this.style.backgroundColor=''\">";
            tempxml = tempxml + "<row>";
            int nombreLien = 0;
            int j = 0;
            int l = 0;
            for (j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                String alignement = "left";
                if (listeFields != null) {
                    for (int k = 0; k < listeFields.length; ++k) {
                        if (listeFields[k].getName().compareToIgnoreCase(this.getLibeEntete()[j]) != 0) continue;
                        if (listeFields[k].getType().getSimpleName().compareToIgnoreCase("String") == 0) {
                            alignement = "left";
                            break;
                        }
                        if (listeFields[k].getType().getSimpleName().compareToIgnoreCase("Double") == 0) {
                            alignement = "right";
                            break;
                        }
                        alignement = "center";
                        break;
                    }
                }
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    String rajoutLien = "";
                    if (this.getUrlLien() != null) {
                        rajoutLien = this.getRajoutLienMultiple(this.getData()[i], this.getUrlLien()[l], this.getUrlLienAffiche()[l]);
                    }
                    ++l;
                    lien = "<a href='" + this.getLien()[numeroColonne] + "&" + colLien + "=" + valL + rajoutLien + "'>";
                    apresLien = "</a>";
                }
                boolean test = false;
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                if (this.getPourcentage() != null) {
                    for (int element = 0; element < this.getPourcentage().length; ++element) {
                        if (!this.libeEntete[j].equals(this.getPourcentage()[element])) continue;
                        test = true;
                        temp = temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\">" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + " (" + Utilitaire.formaterAr(this.getPourc()[element][i]) + " %)" + apresLien + " </td>";
                        break;
                    }
                }
                if (!test) {
                    temp = this.isColumnToHide(this.getLibeEntete()[j]) ? temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align='center' >" + lien + "-" + apresLien + " </td>" : temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\" >" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + apresLien + " </td>";
                }
                tempcsv = tempcsv + Utilitaire.cleanCsv(this.getDataDirecte()[i][j]);
                tempxml = tempxml + "<" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">" + Utilitaire.cleanXmlValue(this.getDataDirecte()[i][j]) + "</" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">\r\n";
                if (j == nombreColonne - 1) continue;
                tempcsv = tempcsv + ";";
            }
            temp = temp + "</tr>";
            tempcsv = tempcsv + "\r\n";
            tempxml = tempxml + "</row>\r\n";
        }
        temp = temp + "</tbody>";
        tempxml = tempxml + "</tableau>\r\n";
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        this.setHtml(temp);
        this.setExpcsv(tempcsv);
        this.setExpxml(tempxml);
    }

    public void makeHtmlPDF() throws Exception {
        int i;
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        String temp = "";
        temp = temp + "<table align='center' border='1'>";
        temp = temp + "<thead>";
        temp = temp + "<tr>";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        nombreColonne = this.getDataDirecte()[0].length;
        for (i = 0; i < nombreColonne; ++i) {
            temp = temp + "<th style=\"font-size:9px\">" + this.getLibelleAffiche()[i] + "</th>";
        }
        temp = temp + "</tr>";
        temp = temp + "</thead>";
        temp = temp + "<tbody>";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr>";
            int nombreLien = 0;
            int j = 0;
            for (j = 0; j < nombreColonne; ++j) {
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    lien = "";
                    apresLien = "";
                }
                boolean test = false;
                if (this.getPourcentage() != null) {
                    for (int element = 0; element < this.getPourcentage().length; ++element) {
                        if (!this.libeEntete[j].equals(this.getPourcentage()[element])) continue;
                        test = true;
                        temp = temp + "<td align='center' style=\"font-size:9px\">" + lien + this.getDataDirecte()[i][j] + " (" + Utilitaire.formaterAr(this.getPourc()[element][i]) + " %)" + apresLien + " </td>";
                        break;
                    }
                }
                if (test) continue;
                temp = temp + "<td align='center' style=\"font-size:9px\">" + lien + this.getDataDirecte()[i][j] + apresLien + " </td>";
            }
            temp = temp + "</tr>";
        }
        temp = temp + "</tbody>";
        temp = temp + "</table>";
        this.setHtml(temp);
    }

    public void makeHtmlPDF(boolean recap, String v1, String v2) throws Exception {
        int i;
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        String temp = "";
        temp = temp + "<table align='center' border='1'>";
        temp = temp + "<thead>";
        if (recap) {
            System.out.println("******** MISY RECAP");
            temp = temp + "<tr>";
            temp = temp + "<th></th>";
            temp = temp + "<th></th>";
            temp = temp + "<th></th>";
            temp = temp + "<th></th>";
            temp = temp + "<th></th>";
            temp = temp + "<th>Somme montant</th>";
            temp = temp + "<th>" + v1 + "</th>";
            temp = temp + "<th>Somme credit</th>";
            temp = temp + "<th>" + v2 + "</th>";
            temp = temp + "</tr>";
        }
        temp = temp + "<tr>";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        nombreColonne = this.getDataDirecte()[0].length;
        for (i = 0; i < nombreColonne; ++i) {
            temp = temp + "<th style=\"font-size:9px\">" + this.getLibelleAffiche()[i] + "</th>";
        }
        temp = temp + "</tr>";
        temp = temp + "</thead>";
        temp = temp + "<tbody>";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr>";
            int nombreLien = 0;
            int j = 0;
            for (j = 0; j < nombreColonne; ++j) {
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    lien = "";
                    apresLien = "";
                }
                boolean test = false;
                if (this.getPourcentage() != null) {
                    for (int element = 0; element < this.getPourcentage().length; ++element) {
                        if (!this.libeEntete[j].equals(this.getPourcentage()[element])) continue;
                        test = true;
                        temp = temp + "<td align='center' style=\"font-size:9px\">" + lien + this.getDataDirecte()[i][j] + " (" + Utilitaire.formaterAr(this.getPourc()[element][i]) + " %)" + apresLien + " </td>";
                        break;
                    }
                }
                if (test) continue;
                temp = temp + "<td align='center' style=\"font-size:9px\">" + lien + this.getDataDirecte()[i][j] + apresLien + " </td>";
            }
            temp = temp + "</tr>";
        }
        temp = temp + "</tbody>";
        temp = temp + "</table>";
        this.setHtml(temp);
    }

    public String getRajoutLienMultiple(ClassMAPTable e, String lien, String lienAffiche) {
        String retour = "";
        if (lien != null || lien.compareTo("") != 0) {
            String[] g = Utilitaire.split(lien, "-");
            String[] affiche = Utilitaire.split(lienAffiche, "-");
            Field[] lf = this.getListeField();
            for (int i = 0; i < g.length; ++i) {
                String valeur = e.getValInsert(g[i]);
                for (int k = 0; k < lf.length; ++k) {
                    if (lf[k].getName().compareTo(g[i]) != 0) continue;
                    if (lf[k].getType().getSimpleName().compareToIgnoreCase("Date") != 0) break;
                    valeur = Utilitaire.convertDatyFormtoRealDatyFormat(valeur);
                    break;
                }
                retour = lienAffiche != null ? retour + "&" + affiche[i] + "=" + valeur : retour + "&" + g[i] + "=" + valeur;
            }
        }
        return retour;
    }

    public void transformerDataString() throws Exception {
        Object valeurC = null;
        this.dataDirecte = new String[this.getData().length][this.getLibeEntete().length];
        for (int i = 0; i < this.getData().length; ++i) {
            int j = 0;
            for (j = 0; j < this.getLibeEntete().length; ++j) {
                Champ c = ListeColonneTable.getChamp(this.data[i], this.getLibeEntete()[j]);
                valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getLibeEntete()[j]);
                if (valeurC == null && c == null) {
                    this.dataDirecte[i][j] = "";
                    continue;
                }
                if (valeurC != null && c == null) {
                    this.dataDirecte[i][j] = String.valueOf(valeurC);
                    if (!(valeurC instanceof Number)) continue;
                    this.dataDirecte[i][j] = Utilitaire.formaterAr(String.valueOf(valeurC));
                    continue;
                }
                this.dataDirecte[i][j] = c.getTypeJava().compareToIgnoreCase("double") == 0 ? Utilitaire.formaterAr(String.valueOf(valeurC)) : (c.getTypeJava().compareToIgnoreCase("java.sql.Date") == 0 ? Utilitaire.formatterDaty((Date)valeurC) : String.valueOf(valeurC));
            }
        }
    }

    public String[] getLibeEntete() {
        return this.libeEntete;
    }

    public String getXmlTag(int j) {
        return Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j);
    }

    public static String getXmlTag(String[] libeEntete, String[] libelleAffiche, int j) {
        return Utilitaire.getXmlTag(libeEntete, libelleAffiche, j);
    }

    public static String cleanXmlValue(String s) {
        return Utilitaire.cleanXmlValue(s);
    }

    public void setLibeEntete(String[] libeEntete) {
        this.libeEntete = libeEntete;
    }

    public void setData(ClassMAPTable[] data) {
        this.data = data;
    }

    public ClassMAPTable[] getData() {
        return this.data;
    }

    public void setCssEntete(String cssEntete) {
        this.cssEntete = cssEntete;
    }

    public String getCssEntete() {
        return this.cssEntete;
    }

    public void setCssTableau(String cssTableau) {
        this.cssTableau = cssTableau;
    }

    public String getCssTableau() {
        return this.cssTableau;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml() throws Exception {
        return this.getHtmlTemplate() == null ? "<h2 class='no-data-msg' style='margin-left:0' >Aucune donn&eacute;e disponible</h2>" : this.getHtmlTemplate();
    }

    public String getHtmlAvecOrdre() throws Exception {
        if (this.html == null || this.html.compareToIgnoreCase("") == 0) {
            this.makeHtmlAvecOrdre();
        }
        return this.html;
    }

    public String getHtmlPDF() throws Exception {
        this.makeHtmlPDF();
        return this.html;
    }

    public String getHtmlPDF(boolean recap, String v1, String v2) throws Exception {
        this.makeHtml(recap, v1, v2);
        return this.html;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setPropEntet(int[] propEntet) {
        this.propEntet = propEntet;
    }

    public int[] getPropEntet() {
        if (this.propEntet == null) {
            int[] ret = new int[this.getLibeEntete().length];
            for (int i = 0; i < ret.length; ++i) {
                ret[i] = 100 / ret.length;
            }
            return ret;
        }
        return this.propEntet;
    }

    public void setTailleTableau(String tailleTableau) {
        this.tailleTableau = tailleTableau;
    }

    public String getTailleTableau() {
        return this.tailleTableau;
    }

    public void setLibelleAffiche(String[] libelleAffiche) {
        this.libelleAffiche = libelleAffiche;
    }

    public String[] getLibelleAffiche() {
        if (this.getLibeEntete() == null) {
            return this.libelleAffiche == null ? new String[]{} : this.libelleAffiche;
        }
        int len = this.getLibeEntete().length;
        String[] result = new String[len];
        Map enteteAnnotation = this.getLibelleEnteteAAffiche();
        for (int i = 0; i < len; ++i) {
            String col = this.getLibeEntete()[i];
            String lib = null;
            lib = this.libelleAffiche != null && i < this.libelleAffiche.length && this.libelleAffiche[i] != null && !this.libelleAffiche[i].trim().isEmpty() ? this.libelleAffiche[i] : (this.getLibelle_champ() != null && col != null && this.getValFromMapIgnoreCase(this.getLibelle_champ(), col) != null ? this.getValFromMapIgnoreCase(this.getLibelle_champ(), col) : (enteteAnnotation != null && col != null && this.getValFromMapIgnoreCase(enteteAnnotation, col) != null ? this.getValFromMapIgnoreCase(enteteAnnotation, col) : col));
            result[i] = lib != null && this.getUserEJB() != null ? this.getUserEJB().getTraduction(lib) : lib;
        }
        return result;
    }

    private String getValFromMapIgnoreCase(Map map, String key) {
        String s;
        if (map == null || key == null) {
            return null;
        }
        Object val = map.get(key);
        if (val != null && !(s = String.valueOf(val)).trim().isEmpty()) {
            return s;
        }
        for (Object k : map.keySet()) {
            String s2;
            Object v;
            if (k == null || !key.equalsIgnoreCase(String.valueOf(k)) || (v = map.get(k)) == null || (s2 = String.valueOf(v)).trim().isEmpty()) continue;
            return s2;
        }
        return null;
    }

    public void setDataDirecte(String[][] dataDirecte) {
        this.dataDirecte = dataDirecte;
    }

    public String[][] getDataDirecte() {
        return this.dataDirecte;
    }

    public void setLien(String[] lien) {
        this.lien = lien;
    }

    public String[] getLien() {
        return this.lien;
    }

    public String[] getUrlLien() {
        return this.urlLien;
    }

    public void setUrlLien(String[] listeLien) {
        this.urlLien = listeLien;
    }

    public void setColonneLien(String[] colonneLien) {
        this.colonneLien = colonneLien;
    }

    public String[] getColonneLien() {
        return this.colonneLien;
    }

    public void setModelePage(String modelePage) {
        this.modelePage = modelePage;
    }

    public String getModelePage() {
        return this.modelePage;
    }

    public void setAttLien(String[] attLien) {
        this.attLien = attLien;
    }

    public String[] getAttLien() {
        return this.attLien;
    }

    public void setValeurLien(String[] valeurLien) {
        this.valeurLien = valeurLien;
    }

    public String[] getValeurLien() {
        return this.valeurLien;
    }

    public String getExpxml() {
        if (this.expxml == null) {
            return "";
        }
        if (this.expxml.startsWith("null")) {
            return this.expxml.substring(4).trim();
        }
        return this.expxml;
    }

    public void setExpxml(String s) {
        this.expxml = s;
    }

    public String getExpcsv() {
        return this.expcsv;
    }

    public void setExpcsv(String s) {
        this.expcsv = s;
    }

    public static String cleanCsv(String s) {
        return Utilitaire.cleanCsv(s);
    }

    public String getExppdf() {
        return this.exppdf;
    }

    public void setExppdf(String s) {
        this.exppdf = s;
    }

    public void makeHtmlWithRadioButton() throws Exception {
        int i;
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        String temp = "";
        temp = temp + "<div class=\"col-md-12 nopadding \"><div class=\" box table-box mb-5 \" style=\"border-bottom-left-radius: 8px !important;border-bottom-right-radius: 8px !important;\"><div class=\"box-body table-responsive no-padding\">";
        temp = temp + "<div id=\"divchck\">";
        temp = temp + "<table width=" + this.getTailleTableau() + " border=0 align=center cellpadding=3 cellspacing=3 class=" + this.getCssTableau() + ">";
        temp = temp + "<thead>";
        temp = temp + "<tr class=" + this.getCssEntete() + ">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        nombreColonne = this.getDataDirecte()[0].length;
        temp = temp + "<th class='contenuetable' align=center valign=top></th>";
        for (i = 0; i < nombreColonne; ++i) {
            temp = temp + "<th class='contenuetable' width=" + this.getPropEntet()[i] + "% align=center valign=top>" + this.getLibelleAffiche()[i] + "</th>";
        }
        temp = temp + "</tr>";
        temp = temp + "</thead>";
        temp = temp + "<tbody>";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=this.style.backgroundColor='#EAEAEA' onmouseout=\"this.style.backgroundColor=''\">";
            temp = temp + "<td align=center>";
            if (this.data[i].getValColLibelle() != null && this.data[i].getValColLibelle().compareToIgnoreCase("") != 0) {
                String temps = this.data[i].getValColLibelle();
                if (temps != null && temps.contains("'")) {
                    temps = temps.replaceAll("'", "&apos;");
                }
                if (temps != null && temps.contains("\"")) {
                    temps = temps.replaceAll("\"", " ");
                }
                temp = temp + "<input type='radio' value='" + this.getDataDirecte()[i][0] + ";" + temps + "' name='choix' onMouseDown='getChoix()' id='choix' class='radio'/>";
            } else {
                temp = temp + "<input type='radio' value='" + this.getDataDirecte()[i][0] + ";" + this.getDataDirecte()[i][0] + "' name='choix' onMouseDown='getChoix()' id='choix' class='radio'/>";
            }
            temp = temp + "</td>";
            int nombreLien = 0;
            for (int j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    lien = "<a href='" + this.getLien()[numeroColonne] + "&" + colLien + "=" + valL + "'>";
                    apresLien = "</a>";
                }
                String alignement = "left";
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                temp = j == 1 ? temp + "<td width=" + this.getPropEntet()[j] + "% align=" + alignement + ">" + this.getDataDirecte()[i][j] + "</td>" : temp + "<td width=" + this.getPropEntet()[j] + "% align=" + alignement + ">" + lien + this.getDataDirecte()[i][j] + apresLien + "</td>";
            }
            temp = temp + "</tr>";
        }
        temp = temp + "</tbody>";
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        this.setHtml(temp);
    }

    public void makeHtmlWithRadioButtonTemplate() throws Exception {
        int i;
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        String temp = "";
        temp = temp + "<div class=\"\">";
        temp = temp + "<div class=\"row col-md-12\">";
        temp = temp + "<div class=\"box box-solid\" style=\";\">";
        temp = temp + "<div>";
        temp = temp + "<div class=\"row\">";
        temp = temp + "<div class=\"col-md-6\">";
        temp = temp + "<h3 class=\"box-title cardtitle fontinter\" align=\"left\">" + this.getTitre() + " <span style=\"color: #686868;font-size: 18px;font-weight: 400;font-family: 'Inter', sans-serif;\"></span></h3>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<div id=\"divchck\">";
        temp = temp + "<table width=" + this.getTailleTableau() + " border=0 align=center cellpadding=3 cellspacing=3 class=\"table table-hover table-striped\">";
        temp = temp + "<tr class=\"" + this.getCssEntete() + "\">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        nombreColonne = this.getDataDirecte()[0].length;
        temp = temp + "<td valign=top class='contenuetable' style=\"background-color:" + ConstanteAffichage.couleurPrimaireTemplate + "\"></td>";
        for (i = 0; i < nombreColonne; ++i) {
            temp = temp + "<td width=" + this.getPropEntet()[i] + "% valign=top class='contenuetable' style=\"background-color:" + ConstanteAffichage.couleurPrimaireTemplate + (this.getLibeEntete()[i].compareToIgnoreCase("etatlib") == 0 ? ";text-align:center" : "") + "\">" + this.getLibelleAffiche()[i] + "</td>";
        }
        temp = temp + "</tr>";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr style=\"height:45px;background-color:#ffffff\" onmouseover=this.style.backgroundColor='#EAEAEA' onmouseout=\"this.style.backgroundColor=''\">";
            temp = temp + "<td style=\"vertical-align: middle;\" align=center>";
            if (this.data[i].getValColLibelle() != null && this.data[i].getValColLibelle().compareToIgnoreCase("") != 0) {
                String temps = this.data[i].getValColLibelle();
                if (temps != null && temps.contains("'")) {
                    temps = temps.replaceAll("'", "&apos;");
                }
                if (temps != null && temps.contains("\"")) {
                    temps = temps.replaceAll("\"", " ");
                }
                temp = temp + "<input type='radio' value='" + this.getDataDirecte()[i][0] + ";" + temps + "' name='choix' onMouseDown='getChoix()' id='choix' class='radio'/>";
            } else {
                temp = temp + "<input type='radio' value='" + this.getDataDirecte()[i][0] + ";" + this.getDataDirecte()[i][0] + "' name='choix' onMouseDown='getChoix()' id='choix' class='radio'/>";
            }
            temp = temp + "</td>";
            int nombreLien = 0;
            for (int j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    lien = "<a href='" + this.getLien()[numeroColonne] + "&" + colLien + "=" + valL + "'>";
                    apresLien = "</a>";
                }
                String alignement = "left";
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                temp = j == 1 ? temp + "<td class='contenuetable' style=\"vertical-align: middle;\" width=" + this.getPropEntet()[j] + "% align=" + alignement + ">" + this.getDataDirecte()[i][j] + "</td>" : temp + "<td class='contenuetable' style=\"vertical-align: middle;\" width=" + this.getPropEntet()[j] + "% align=" + alignement + ">" + lien + this.getDataDirecte()[i][j] + apresLien + "</td>";
            }
            temp = temp + "</tr>";
        }
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        this.setHtml(temp);
    }

    public void makeHtmlWithRadioButtonNew() throws Exception {
        String alignement;
        int i;
        String temp = "";
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        temp = temp + "<div class=\"result p-3 mt-3 \">";
        String tempcsv = "";
        String tempxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>";
        temp = temp + "<div class=\"export-container mb-1\"><div class=\"btn btn-export \" onclick=\"exprt()\">\n                    <i class=\"fas fa-file-export\"></i> Exporter\n                </div>";
        temp = temp + "<div id='destinationDiv'></div></div>";
        temp = temp + "<div class=\"table-result\">";
        temp = temp + "<table width=\"" + this.getTailleTableau() + "\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" class=\"table table-sortable \">";
        temp = temp + "<thead>";
        temp = temp + "<tr class=\"table-header\">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        temp = temp + "<th align=center></td>";
        Field[] listeFields = this.getListeField();
        nombreColonne = this.getDataDirecte()[0].length;
        for (i = 0; i < nombreColonne; ++i) {
            String ordre = "";
            String colonneOrdre = "";
            String[] params = this.getCritereLienString().split("&");
            if (params.length > 0) {
                for (int j = 0; j < params.length; ++j) {
                    if (params[j].contains("ordre")) {
                        String string = ordre = params[j].split("=").length > 1 ? params[j].split("=")[1] : "";
                    }
                    if (!params[j].contains("colonne")) continue;
                    colonneOrdre = params[j].split("=").length > 1 ? params[j].split("=")[1] : "";
                }
            }
            alignement = "";
            String libelle = this.getLibelleAffiche()[i];
            if (this.getCritereLienString().compareToIgnoreCase("") != 0) {
                String entete;
                if (this.getColGroupe() != null && this.getColGroupe().contains(this.getLibeEntete()[i])) {
                    entete = "";
                    entete = ordre.compareToIgnoreCase("asc") == 0 && colonneOrdre.compareToIgnoreCase(this.getLibeEntete()[i]) == 0 ? "<th  align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "&darr;<span class=\"fleche-asc-desc\">&uarr;</span> m-1</a></th>" : (ordre.compareToIgnoreCase("desc") == 0 && colonneOrdre.compareToIgnoreCase(this.getLibeEntete()[i]) == 0 ? "<th  align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "<span class=\"fleche-asc-desc\">&darr;</span>&uarr; m-1</a></th>" : "<th  align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "&darr;&uarr; m-1</a></th>");
                    temp = temp + entete;
                }
                entete = "";
                entete = ordre.compareToIgnoreCase("asc") == 0 && colonneOrdre.compareToIgnoreCase(this.getLibeEntete()[i]) == 0 ? "<th  align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "&darr;<span class=\"fleche-asc-desc\">&uarr;</span></a></th>" : (ordre.compareToIgnoreCase("desc") == 0 && colonneOrdre.compareToIgnoreCase(this.getLibeEntete()[i]) == 0 ? "<th  align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "<span class=\"fleche-asc-desc\">&darr;</span>&uarr;</a></th>" : "<th  align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "&darr;&uarr;</a></th>");
                temp = temp + entete;
            } else {
                if (this.getColGroupe() != null && this.getColGroupe().contains(this.getLibeEntete()[i])) {
                    temp = temp + "<th align=\"" + alignement + "\" valign=\"top\">" + libelle + " m-1</th>";
                }
                temp = temp + "<th  align=\"" + alignement + "\" valign=\"top\">" + libelle + "</th>";
            }
            tempcsv = tempcsv + Utilitaire.cleanCsv(this.getLibelleAffiche()[i]);
            if (i == nombreColonne - 1) continue;
            tempcsv = tempcsv + ";";
        }
        tempcsv = tempcsv + "\r\n";
        temp = temp + "</tr>";
        temp = temp + "</thead>";
        temp = temp + "<tbody>\n";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=\"this.style.backgroundColor='#EAEAEA'\" onmouseout=\"this.style.backgroundColor=''\">";
            temp = temp + "<td align=center>";
            if (this.data[i].getValColLibelle() != null && this.data[i].getValColLibelle().compareToIgnoreCase("") != 0) {
                String temps = this.data[i].getValColLibelle();
                if (temps != null && temps.contains("'")) {
                    temps = temps.replaceAll("'", "&apos;");
                }
                if (temps != null && temps.contains("\"")) {
                    temps = temps.replaceAll("\"", " ");
                }
                temp = temp + "<input type='radio' value='" + this.getDataDirecte()[i][0] + ";" + temps + "' name='choix' onMouseDown='getChoix()' id='choix' class='radio'/>";
            } else {
                temp = temp + "<input type='radio' value='" + this.getDataDirecte()[i][0] + ";" + this.getDataDirecte()[i][0] + "' name='choix' onMouseDown='getChoix()' id='choix' class='radio'/>";
            }
            temp = temp + "</td>";
            tempxml = tempxml + "<row>";
            int nombreLien = 0;
            int j = 0;
            int l = 0;
            for (j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                alignement = "left";
                if (listeFields != null) {
                    for (int k = 0; k < listeFields.length; ++k) {
                        if (listeFields[k].getName().compareToIgnoreCase(this.getLibeEntete()[j]) != 0) continue;
                        if (listeFields[k].getType().getSimpleName().compareToIgnoreCase("String") == 0) {
                            alignement = "left";
                            break;
                        }
                        if (listeFields[k].getType().getSimpleName().compareToIgnoreCase("Double") == 0) {
                            alignement = "right";
                            break;
                        }
                        alignement = "center";
                        break;
                    }
                }
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    String rajoutLien = "";
                    if (this.getUrlLien() != null) {
                        rajoutLien = this.getRajoutLienMultiple(this.getData()[i], this.getUrlLien()[l], this.getUrlLienAffiche()[l]);
                    }
                    ++l;
                    String lienAutre = "";
                    if (this.isGroupe()) {
                        for (int g = 0; g < this.getLibeEntete().length - 1; ++g) {
                            lienAutre = lienAutre + this.getLibeEntete()[g] + "=" + this.getDataDirecte()[i][g] + "&";
                        }
                        lienAutre = lienAutre + this.getLibeEntete()[this.getLibeEntete().length - 1] + "=" + this.getDataDirecte()[i][this.getLibeEntete().length - 1] + "&" + this.getLienFormulaire();
                    } else {
                        lienAutre = colLien + "=" + valL;
                    }
                    lien = "<a href='" + this.getLien()[numeroColonne] + "&" + lienAutre + rajoutLien + "'>";
                    apresLien = "</a>";
                }
                boolean test = false;
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                if (this.getPourcentage() != null) {
                    for (int element = 0; element < this.getPourcentage().length; ++element) {
                        if (!this.libeEntete[j].equals(this.getPourcentage()[element])) continue;
                        test = true;
                        temp = temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\">" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + " (" + Utilitaire.formaterAr(this.getPourc()[element][i]) + " %)" + apresLien + " </td>";
                        break;
                    }
                }
                if (!test) {
                    temp = this.isColumnToHide(this.getLibeEntete()[j]) ? temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align='center' >" + lien + "- " + apresLien + " </td>" : temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\" >" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + " " + apresLien + " </td>";
                }
                tempcsv = tempcsv + Utilitaire.cleanCsv(this.getDataDirecte()[i][j]);
                tempxml = tempxml + "<" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">" + Utilitaire.cleanXmlValue(this.getDataDirecte()[i][j]) + "</" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">\r\n";
                if (j == nombreColonne - 1) continue;
                tempcsv = tempcsv + ";";
            }
            temp = temp + "</tr>";
            tempcsv = tempcsv + "\r\n";
            tempxml = tempxml + "</row>\r\n";
        }
        temp = temp + "</tbody>\n</table>\n</div></div>";
        this.setHtml(temp);
        this.setExpcsv(tempcsv);
        this.setExpxml(tempxml);
    }

    public void makeHtmlWithMultipleCheckbox() throws Exception {
        int i;
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        String temp = "";
        temp = temp + "<p align=center><strong><u>" + this.getTitre() + "</u></strong></p>";
        temp = temp + "<div id=\"divchck\" class='table-responsive'>";
        temp = temp + "<table width=" + this.getTailleTableau() + " border=0 align=center cellpadding=3 cellspacing=3 class=" + this.getCssTableau() + ">";
        temp = temp + "<tr class=" + this.getCssEntete() + ">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        nombreColonne = this.getDataDirecte()[0].length;
        temp = temp + "<td align=center valign=top></td>";
        for (i = 0; i < nombreColonne; ++i) {
            temp = temp + "<td align=center valign=top>" + this.getLibelleAffiche()[i] + "</td>";
        }
        temp = temp + "</tr>";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=this.style.backgroundColor='#EAEAEA' onmouseout=\"this.style.backgroundColor=''\">";
            temp = temp + "<td align=center>";
            temp = this.data[i].getValColLibelle() != null && this.data[i].getValColLibelle().compareToIgnoreCase("") != 0 ? temp + "<input type='checkbox' value='" + this.getDataDirecte()[i][0] + ";" + this.data[i].getValColLibelle() + "' name='choix' id='choix' class='checkbox'/>" : temp + "<input type='checkbox' value='" + this.getDataDirecte()[i][0] + ";" + this.getDataDirecte()[i][0] + "' name='choix' id='choix' class='checkbox'/>";
            temp = temp + "</td>";
            int nombreLien = 0;
            for (int j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    lien = "<a href='" + this.getLien()[numeroColonne] + "&" + colLien + "=" + valL + "'>";
                    apresLien = "</a>";
                }
                String alignement = "left";
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                temp = j == 1 ? temp + "<td align=" + alignement + ">" + this.getDataDirecte()[i][j] + "</td>" : temp + "<td align=" + alignement + ">" + lien + this.getDataDirecte()[i][j] + apresLien + "</td>";
            }
            temp = temp + "</tr>";
        }
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "<input class=\"btn btn-success\" type=\"submit\" value=\"Valider\" />";
        this.setHtml(temp);
    }

    public void makeHtmlWithMultipleCheckboxTous() throws Exception {
        int i;
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        String temp = "";
        temp = temp + "<p align=center><strong><u>" + this.getTitre() + "</u></strong></p>";
        temp = temp + "<div id=\"divchck\">";
        temp = temp + "<table width=" + this.getTailleTableau() + " border=0 align=center cellpadding=3 cellspacing=3 class=" + this.getCssTableau() + ">";
        temp = temp + "<tr class=" + this.getCssEntete() + ">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        nombreColonne = this.getDataDirecte()[0].length;
        temp = temp + "<td align=center valign=top></td>";
        for (i = 0; i < nombreColonne; ++i) {
            temp = temp + "<td width=" + this.getPropEntet()[i] + "% align=center valign=top>" + this.getLibelleAffiche()[i] + "</td>";
        }
        temp = temp + "</tr>";
        temp = temp + "<th align=center valign=top class='contenuetable'><input onclick=\"CocheToutCheckbox(this, 'choix')\" type=\"checkbox\"></th>";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=this.style.backgroundColor='#EAEAEA' onmouseout=\"this.style.backgroundColor=''\">";
            temp = temp + "<td align=center>";
            temp = this.data[i].getValColLibelle() != null && this.data[i].getValColLibelle().compareToIgnoreCase("") != 0 ? temp + "<input type='checkbox' value='" + this.getDataDirecte()[i][0] + ";" + this.data[i].getValColLibelle() + "' name='choix' id='choix' class='checkbox'/>" : temp + "<input type='checkbox' value='" + this.getDataDirecte()[i][0] + ";" + this.getDataDirecte()[i][0] + "' name='choix' id='choix' class='checkbox'/>";
            temp = temp + "</td>";
            int nombreLien = 0;
            for (int j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    lien = "<a href='" + this.getLien()[numeroColonne] + "&" + colLien + "=" + valL + "'>";
                    apresLien = "</a>";
                }
                String alignement = "left";
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                temp = j == 1 ? temp + "<td width=" + this.getPropEntet()[j] + "% align=" + alignement + "><input type='text' readonly=true style='border:none;text-align:center;' name='idEmp" + i + "' value='" + this.getDataDirecte()[i][j] + "'></td>" : temp + "<td width=" + this.getPropEntet()[j] + "% align=" + alignement + ">" + lien + this.getDataDirecte()[i][j] + apresLien + "</td>";
            }
            temp = temp + "</tr>";
        }
        temp = temp + "</table>";
        temp = temp + "<input class=\"btn btn-success\" type=\"submit\" value=\"Valider\" />";
        temp = temp + "</div>";
        this.setHtml(temp);
    }

    public void makeHtmlWithMultipleCheckboxRetientChoix(String[] choix) throws Exception {
        int i;
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        String temp = "";
        temp = temp + "<p align=center><strong><u>" + this.getTitre() + "</u></strong></p>";
        temp = temp + "<div id=\"divchck\">";
        temp = temp + "<table width=" + this.getTailleTableau() + " border=0 align=center cellpadding=3 cellspacing=3 class=" + this.getCssTableau() + ">";
        temp = temp + "<tr class=" + this.getCssEntete() + ">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        nombreColonne = this.getDataDirecte()[0].length;
        temp = temp + "<td align=center valign=top></td>";
        for (i = 0; i < nombreColonne; ++i) {
            temp = temp + "<td width=" + this.getPropEntet()[i] + "% align=center valign=top>" + this.getLibelleAffiche()[i] + "</td>";
        }
        temp = temp + "</tr>";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=this.style.backgroundColor='#EAEAEA' onmouseout=\"this.style.backgroundColor=''\">";
            temp = temp + "<td align=center>";
            int dansChoix = Utilitaire.estIlDedans(this.getDataDirecte()[i][0], choix);
            temp = this.data[i].getValColLibelle() != null && this.data[i].getValColLibelle().compareToIgnoreCase("") != 0 ? (dansChoix != -1 ? temp + "<input type='checkbox' value='" + this.getDataDirecte()[i][0] + ";" + this.data[i].getValColLibelle() + "' name='choix' id='choix' class='checkbox' checked/>" : temp + "<input type='checkbox' value='" + this.getDataDirecte()[i][0] + ";" + this.data[i].getValColLibelle() + "' name='choix' id='choix' class='checkbox'/>") : (dansChoix != -1 ? temp + "<input type='checkbox' value='" + this.getDataDirecte()[i][0] + ";" + this.getDataDirecte()[i][0] + "' name='choix' id='choix' class='checkbox' checked/>" : temp + "<input type='checkbox' value='" + this.getDataDirecte()[i][0] + ";" + this.getDataDirecte()[i][0] + "' name='choix' id='choix' class='checkbox'/>");
            temp = temp + "</td>";
            int nombreLien = 0;
            for (int j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    lien = "<a href='" + this.getLien()[numeroColonne] + "&" + colLien + "=" + valL + "'>";
                    apresLien = "</a>";
                }
                String alignement = "left";
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                temp = j == 1 ? temp + "<td width=" + this.getPropEntet()[j] + "% align=" + alignement + "><input type='text' readonly=true style='border:none;text-align:center;' name='idEmp" + i + "' value='" + this.getDataDirecte()[i][j] + "'></td>" : temp + "<td width=" + this.getPropEntet()[j] + "% align=" + alignement + ">" + lien + this.getDataDirecte()[i][j] + apresLien + "</td>";
            }
            temp = temp + "</tr>";
        }
        temp = temp + "</table>";
        temp = temp + "<input class=\"btn btn-success\" type=\"submit\" value=\"Valider\" />";
        temp = temp + "</div>";
        this.setHtml(temp);
    }

    public void makeHtmlWithMultipleCheckboxRetientChoixTous(String[] choix) throws Exception {
        int i;
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        String temp = "";
        temp = temp + "<p align=center><strong><u>" + this.getTitre() + "</u></strong></p>";
        temp = temp + "<div id=\"divchck\">";
        temp = temp + "<table width=" + this.getTailleTableau() + " border=0 align=center cellpadding=3 cellspacing=3 class=" + this.getCssTableau() + ">";
        temp = temp + "<tr class=" + this.getCssEntete() + ">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        nombreColonne = this.getDataDirecte()[0].length;
        temp = temp + "<td align=center valign=top></td>";
        for (i = 0; i < nombreColonne; ++i) {
            temp = temp + "<td width=" + this.getPropEntet()[i] + "% align=center valign=top>" + this.getLibelleAffiche()[i] + "</td>";
        }
        temp = temp + "</tr>";
        temp = temp + "<th align=center valign=top class='contenuetable'><input onclick=\"CocheToutCheckbox(this, 'id')\" type=\"checkbox\"></th>";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=this.style.backgroundColor='#EAEAEA' onmouseout=\"this.style.backgroundColor=''\">";
            temp = temp + "<td align=center>";
            int dansChoix = Utilitaire.estIlDedans(this.getDataDirecte()[i][0], choix);
            temp = this.data[i].getValColLibelle() != null && this.data[i].getValColLibelle().compareToIgnoreCase("") != 0 ? (dansChoix != -1 ? temp + "<input type='checkbox' value='" + this.getDataDirecte()[i][0] + ";" + this.data[i].getValColLibelle() + "' name='choix' id='choix' class='checkbox' checked/>" : temp + "<input type='checkbox' value='" + this.getDataDirecte()[i][0] + ";" + this.data[i].getValColLibelle() + "' name='choix' id='choix' class='checkbox'/>") : (dansChoix != -1 ? temp + "<input type='checkbox' value='" + this.getDataDirecte()[i][0] + ";" + this.getDataDirecte()[i][0] + "' name='choix' id='choix' class='checkbox' checked/>" : temp + "<input type='checkbox' value='" + this.getDataDirecte()[i][0] + ";" + this.getDataDirecte()[i][0] + "' name='choix' id='choix' class='checkbox'/>");
            temp = temp + "</td>";
            int nombreLien = 0;
            for (int j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    lien = "<a href='" + this.getLien()[numeroColonne] + "&" + colLien + "=" + valL + "'>";
                    apresLien = "</a>";
                }
                String alignement = "left";
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                temp = j == 1 ? temp + "<td width=" + this.getPropEntet()[j] + "% align=" + alignement + "><input type='text' readonly=true style='border:none;text-align:center;' name='idEmp" + i + "' value='" + this.getDataDirecte()[i][j] + "'></td>" : temp + "<td width=" + this.getPropEntet()[j] + "% align=" + alignement + ">" + lien + this.getDataDirecte()[i][j] + apresLien + "</td>";
            }
            temp = temp + "</tr>";
        }
        temp = temp + "</table>";
        temp = temp + "<input class=\"btn btn-success\" type=\"submit\" value=\"Valider\" />";
        temp = temp + "</div>";
        this.setHtml(temp);
    }

    public String getHtmlWithRadioButton() throws Exception {
        this.makeHtmlWithRadioButton();
        return this.html;
    }

    public String getHtmlWithRadioButtonTemplate() throws Exception {
        this.makeHtmlWithRadioButtonTemplate();
        return this.html;
    }

    public String getHtmlWithRadioButtonNew() throws Exception {
        this.makeHtmlWithRadioButtonNew();
        return this.html;
    }

    public String getHtmlWithMultipleCheckbox() throws Exception {
        this.makeHtmlWithMultipleCheckbox();
        return this.html;
    }

    public String getHtmlWithMultipleCheckboxRetientChoix(String choix) throws Exception {
        if (choix != null && choix.compareTo("") != 0) {
            String[] listeChoix = choix.split(";");
            this.makeHtmlWithMultipleCheckboxRetientChoix(listeChoix);
        } else {
            this.makeHtmlWithMultipleCheckbox();
        }
        return this.html;
    }

    public String getHtmlWithMultipleCheckboxRetientChoixTous(String choix) throws Exception {
        if (choix != null && choix.compareTo("") != 0) {
            String[] listeChoix = choix.split(";");
            this.makeHtmlWithMultipleCheckboxRetientChoixTous(listeChoix);
        } else {
            this.makeHtmlWithMultipleCheckboxTous();
        }
        return this.html;
    }

    public void setPourc(double[][] pourc) {
        this.pourc = pourc;
    }

    public double[][] getPourc() {
        return this.pourc;
    }

    public void setPourcentage(String[] pourcentage) {
        this.pourcentage = pourcentage;
    }

    public String[] getPourcentage() {
        return this.pourcentage;
    }

    public void makeHtmlWithCheckbox() throws Exception {
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            this.setExpxml("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau></tableau>\r\n");
            return;
        }
        String temp = "";
        String tempcsv = "";
        String tempxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>";
        boolean dataTableApjActive = this.isDataTableApjEnabled() && !this.getIsRecap();
        temp = temp + "<div class='col-md-12 nopadding'" + (dataTableApjActive ? " data-apj-filter-root" : "") + ">";
        temp = temp + "<div class='box table-box' >";
        temp = temp + "<div class='box-body table-responsive no-padding'>";
        temp = temp + "<div class='scroll-table-container' id=\"selectnonee\">";
        temp = temp + "<table width=" + this.getTailleTableau() + " border=0 align=center cellpadding=3 cellspacing=3 class='table table-hover table-striped' style='font-size: 14px;'" + (!this.getIsRecap() ? "id='table-apj'" : "") + (dataTableApjActive ? " data-apj-filter-table" : "") + ">";
        temp = temp + "<thead>";
        temp = temp + "<tr class=" + this.getCssEntete() + ">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        nombreColonne = this.getDataDirecte()[0].length;
        temp = temp + "<th align=center valign=top class='contenuetable'><input onclick=\"CocheToutCheckbox(this, 'ids')\" type=\"checkbox\"></th>";
        String filterColumnAttribute = dataTableApjActive ? " data-apj-filter-column" : "";
        for (int i = 0; i < nombreColonne; ++i) {
            temp = this.getCritereLienString().compareToIgnoreCase("") != 0 ? temp + "<th" + filterColumnAttribute + " width=" + this.getPropEntet()[i] + "% align=center valign=top class='contenuetable'>" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + ">" + this.getLibelleAffiche()[i] + "</a></th>" : temp + "<th" + filterColumnAttribute + " width=" + this.getPropEntet()[i] + "% align=center valign=top class='contenuetable'>" + this.getLibelleAffiche()[i] + "</th>";
            tempcsv = tempcsv + Utilitaire.cleanCsv(this.getLibelleAffiche()[i]);
            if (i == nombreColonne - 1) continue;
            tempcsv = tempcsv + ";";
        }
        temp = temp + "</tr>";
        tempcsv = tempcsv + "\r\n";
        tempxml = tempxml + "\r\n";
        temp = temp + "</thead>";
        temp = temp + this.makeHtmlWithCheckboxSansEntete();
        tempxml = tempxml + "</tableau>\r\n";
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<div class='box-footer'>";
        if (this.isAfficheBouttondevalider()) {
            temp = temp + "<input id='acte' type='hidden' name='acte' value='update'/> ";
            temp = temp + "<button type='button' name='Submit2' class='btn btn-apj-secondary pull-left' style='margin-left: 25px;' onClick=\"document.getElementById('acte').value='detacher'; document.getElementById('formmultiple').submit();\">D&eacute;tacher</button> ";
            temp = temp + "<button type='button' name='Submit2' class='btn btn-apj-secondary pull-left' style='margin-left: 25px;' onClick=\"document.getElementById('acte').value='valider'; document.getElementById('formmultiple').submit();\">Viser</button> ";
        } else {
            if (this.getNameBoutton2().compareTo("") != 0) {
                temp = temp + "<button type='submit' name='Submit2' class='btn btn-primary pull-right' style='margin-right: 8px;' onClick=\"acte.value='" + this.getNameActe2() + "'\">" + this.getNameBoutton2() + "</button> ";
            }
            if (this.isValdierMultiple() && this.getNameActe().equalsIgnoreCase("attacher")) {
                temp = temp + "<button type='submit' name='Submit2' class='btn btn-secondary pull-right' style='margin-right: 8px;' onClick=\"acte.value='validerMultiple'\">" + this.getNameBoutton() + "</button> ";
            }
            if (this.isValdierMultiple() && !this.getNameActe().equalsIgnoreCase("attacher")) {
                temp = temp + "<button type='submit' name='Submit2' class='btn btn-secondary pull-right' style='margin-right: 8px;' onClick=\"acte.value='" + this.getNameActe() + "'\">" + this.getNameBoutton() + "</button> ";
            }
        }
        if (this.getBoutons() != null) {
            String[] boutons = this.getBoutons();
            String[] boutonsActe = this.getBoutonsActe();
            String[] boutonsClass = this.getBoutonsClass();
            for (int i = 0; i < boutons.length; ++i) {
                String name = boutons[i] != null ? boutons[i] : "";
                String acte = "";
                String classBtn = "btn btn-secondary pull-right";
                if (boutonsActe != null && i < boutonsActe.length && boutonsActe[i] != null) {
                    acte = boutonsActe[i];
                }
                if (boutonsClass != null && i < boutonsClass.length && boutonsClass[i] != null) {
                    classBtn = boutonsClass[i];
                }
                temp = temp + "<button type='submit' name='Submit2' class='" + classBtn + "' style='margin-right: 8px;' onClick=\"acte.value='" + acte + "'\">" + name + "</button> ";
            }
        }
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        if (dataTableApjActive) {
            temp = temp + this.getHtmlDataTableApj();
            temp = temp + this.getJsDataTableApj();
        }
        temp = temp + "<div class='col-xs-12'>";
        temp = temp + "</div>";
        this.setHtml(temp);
        this.setExpcsv(tempcsv);
        String contenuXml = Utilitaire.champNull(this.getExpxml());
        if (contenuXml.contains("<row>")) {
            if (!contenuXml.contains("<?xml")) {
                this.setExpxml("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>\r\n" + contenuXml + "</tableau>\r\n");
            } else {
                if (!contenuXml.contains("</tableau>")) {
                    contenuXml = contenuXml + "</tableau>\r\n";
                }
                this.setExpxml(contenuXml);
            }
        } else {
            this.setExpxml(tempxml);
        }
    }

    public String makeHtmlWithCheckboxSansEntete() throws Exception {
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return "";
        }
        String temp = "";
        String tempcsv = "";
        String tempxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        Field[] listeFields = this.getListeField();
        nombreColonne = this.getDataDirecte()[0].length;
        temp = temp + "<tbody>";
        String objectId = "";
        for (int i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=this.style.backgroundColor='#EAEAEA' onmouseout=\"this.style.backgroundColor=''\">";
            tempxml = tempxml + "<row>\r\n";
            int nombreLien = 0;
            int j = 0;
            int l = 0;
            temp = temp + "<td align=center><input type='checkbox' value='" + this.getDataDirecte()[i][0] + "' name='ids' id='checkbox" + i + "'/></td>";
            for (j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                String lien = "";
                if (this.getLienFille() != null && j == 0) {
                    lien = "<button type='button' class='toggle-button line-661' onclick='toggleRow(this)' id='" + this.getLienFille() + "' >+</button>";
                }
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    String rajoutLien = "";
                    if (this.getUrlLien() != null) {
                        rajoutLien = this.getRajoutLienMultiple(this.getData()[i], this.getUrlLien()[l], this.getUrlLienAffiche()[l]);
                    }
                    ++l;
                    String lienAutre = "";
                    if (this.isGroupe()) {
                        for (int g = 0; g < this.getLibeEntete().length - 1; ++g) {
                            lienAutre = lienAutre + this.getLibeEntete()[g] + "=" + this.getDataDirecte()[i][g] + "&";
                        }
                        lienAutre = lienAutre + this.getLibeEntete()[this.getLibeEntete().length - 1] + "=" + this.getDataDirecte()[i][this.getLibeEntete().length - 1] + "&" + this.getLienFormulaire();
                    } else {
                        lienAutre = colLien + "=" + valL;
                        objectId = valL;
                    }
                    if (!this.isModalOnClick) {
                        lien = lien + "<a href='" + this.getLien()[numeroColonne] + "&" + lienAutre + rajoutLien + "'>";
                    } else {
                        String lienLeger = this.getLien()[numeroColonne].replace("module.jsp", "moduleLeger.jsp");
                        String lienModal = lienLeger + "&" + lienAutre + rajoutLien;
                        lien = lien + "<a href='#'  onclick=ouvrirModal(event,'" + lienModal + "&ismodal=true','modalContent')>";
                    }
                    if (this.isModalOnClick && this.getColonneModal() != null && this.getColonneModal().length > 0) {
                        for (int k = 0; k < this.getColonneModal().length; ++k) {
                            if (!this.getColonneModal()[k].equalsIgnoreCase(this.getLibeEntete()[j])) {
                                lien = lien + "<a href='" + this.getLien()[numeroColonne] + "&" + lienAutre + rajoutLien + "'>";
                                continue;
                            }
                            String rep = "";
                            if (!this.isGroupe()) {
                                rep = lienAutre.split("=")[0];
                            }
                            String lienLeger = this.getLien()[numeroColonne].replace("module.jsp", "moduleLeger.jsp");
                            String lienModal = lienLeger + "&" + lienAutre + rajoutLien;
                            lien = lien + "<a href='#'  onclick=ouvrirModal(event,'" + lienModal + "&ismodal=true','modalContent')>";
                        }
                    }
                    apresLien = "</a>";
                }
                String alignement = "left";
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                temp = this.getLibeEntete()[j].compareToIgnoreCase("image") == 0 ? temp + "<td class='contenuetable' width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\" ><img src=\"/dossier/async/" + Utilitaire.champNull(this.getDataDirecte()[i][j]) + "\" style=\"width:" + this.getTailleImage() + "%;\"> </td>" : temp + "<td width=" + this.getPropEntet()[j] + "% align=" + alignement + " " + (this.getLienFille() != null && j == 0 ? "style=\"display:flex;align-items:baseline;gap:4px;\"" : "") + ">" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + apresLien + " </td>";
                tempcsv = tempcsv + Utilitaire.cleanCsv(this.getDataDirecte()[i][j]);
                tempxml = tempxml + "<" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">" + Utilitaire.cleanXmlValue(this.getDataDirecte()[i][j]) + "</" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">\r\n";
                if (j == nombreColonne - 1) continue;
                tempcsv = tempcsv + ";";
            }
            if (this.getLienClicDroite() != null && this.getLienClicDroite().size() > 0) {
                String actionLinksHtml = "";
                ClassMAPTable objCurrent = this.getData() != null && i < this.getData().length ? this.getData()[i] : null;
                for (Map.Entry<String, String> lienClic : this.getLienClicDroite().entrySet()) {
                    if (!this.isActionAllowed(objCurrent, lienClic.getKey(), lienClic.getValue())) continue;
                    actionLinksHtml = actionLinksHtml + "        <p><a href=" + lienClic.getValue() + "&" + this.getColonneLienId(lienClic.getKey()) + "=" + objectId + "><i class=\"fa fa-pencil-square-o\"></i> " + this.getCleLien(lienClic.getKey()) + " </a></p>\n";
                }
                temp = !actionLinksHtml.isEmpty() ? temp + "<td style=\"padding: 4px 15px;position: relative;\">\n    <button type=\"button\" class=\"more_info\" id=\"ellipsis1\" style=\"border: none; background: none;\">\n       <i class=\"material-symbols-rounded\" >more_vert</i>\n    </button>    <div id=\"more_action1\" class=\"mode_action\" style=\"display: none;\">\n" + actionLinksHtml + "    </div>\n</td>" : temp + "<td style=\"padding: 4px 15px;position: relative;\"></td>";
            }
            temp = temp + "</tr>";
            if (this.getLienFille() != null) {
                temp = temp + "<tr class='collapse' data-apj-filter-detail-row><td class='collapse-container' colspan=12><div id=" + this.getDataDirecte()[i][0] + "></td>\n            </tr>";
            }
            tempcsv = tempcsv + "\r\n";
            tempxml = tempxml + "</row>\r\n";
        }
        temp = temp + "<input type=\"hidden\" name=\"id\" value=\"" + this.getIds() + "\">";
        temp = temp + "</tbody>";
        tempxml = tempxml + "</tableau>\r\n";
        this.setExpcsv(this.getExpcsv() + tempcsv);
        this.setExpxml(tempxml);
        return temp;
    }

    public void makeHtmlWithCheckboxNew() throws Exception {
        String alignement;
        int i;
        String temp = "";
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        temp = temp + "<div class=\"result p-3 mt-3 \">";
        String tempcsv = "";
        String tempxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>";
        temp = temp + "<div class=\"export-container mb-1\"><div class=\"btn btn-export \" onclick=\"exprt()\">\n                    <i class=\"fas fa-file-export\"></i> Exporter\n                </div>";
        temp = temp + "<div id='destinationDiv'></div></div>";
        temp = temp + "<div class=\"table-result\">";
        temp = temp + "<table width=\"" + this.getTailleTableau() + "\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" class=\"table table-sortable \">";
        temp = temp + "<thead>";
        temp = temp + "<tr class=\"table-header\">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        temp = temp + "<th align=center valign=top><input onclick=\"CocheToutCheckbox(this, 'id')\" type=\"checkbox\"></th>";
        Field[] listeFields = this.getListeField();
        nombreColonne = this.getDataDirecte()[0].length;
        for (i = 0; i < nombreColonne; ++i) {
            String ordre = "";
            String colonneOrdre = "";
            String[] params = this.getCritereLienString().split("&");
            if (params.length > 0) {
                for (int j = 0; j < params.length; ++j) {
                    if (params[j].contains("ordre")) {
                        String string = ordre = params[j].split("=").length > 1 ? params[j].split("=")[1] : "";
                    }
                    if (!params[j].contains("colonne")) continue;
                    colonneOrdre = params[j].split("=").length > 1 ? params[j].split("=")[1] : "";
                }
            }
            alignement = "";
            String libelle = this.getLibelleAffiche()[i];
            if (this.getCritereLienString().compareToIgnoreCase("") != 0) {
                String entete;
                if (this.getColGroupe() != null && this.getColGroupe().contains(this.getLibeEntete()[i])) {
                    entete = "";
                    entete = ordre.compareToIgnoreCase("asc") == 0 && colonneOrdre.compareToIgnoreCase(this.getLibeEntete()[i]) == 0 ? "<th  align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "&darr;<span class=\"fleche-asc-desc\">&uarr;</span> m-1</a></th>" : (ordre.compareToIgnoreCase("desc") == 0 && colonneOrdre.compareToIgnoreCase(this.getLibeEntete()[i]) == 0 ? "<th  align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "<span class=\"fleche-asc-desc\">&darr;</span>&uarr; m-1</a></th>" : "<th  align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "&darr;&uarr; m-1</a></th>");
                    temp = temp + entete;
                }
                entete = "";
                entete = ordre.compareToIgnoreCase("asc") == 0 && colonneOrdre.compareToIgnoreCase(this.getLibeEntete()[i]) == 0 ? "<th  align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "&darr;<span class=\"fleche-asc-desc\">&uarr;</span></a></th>" : (ordre.compareToIgnoreCase("desc") == 0 && colonneOrdre.compareToIgnoreCase(this.getLibeEntete()[i]) == 0 ? "<th  align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "<span class=\"fleche-asc-desc\">&darr;</span>&uarr;</a></th>" : "<th  align=\"" + alignement + "\" valign=\"top\">" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + " >" + libelle + "&darr;&uarr;</a></th>");
                temp = temp + entete;
            } else {
                if (this.getColGroupe() != null && this.getColGroupe().contains(this.getLibeEntete()[i])) {
                    temp = temp + "<th align=\"" + alignement + "\" valign=\"top\">" + libelle + " m-1</th>";
                }
                temp = temp + "<th  align=\"" + alignement + "\" valign=\"top\">" + libelle + "</th>";
            }
            tempcsv = tempcsv + Utilitaire.cleanCsv(this.getLibelleAffiche()[i]);
            if (i == nombreColonne - 1) continue;
            tempcsv = tempcsv + ";";
        }
        tempcsv = tempcsv + "\r\n";
        temp = temp + "</tr>";
        temp = temp + "</thead>";
        temp = temp + "<tbody>\n";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=\"this.style.backgroundColor='#EAEAEA'\" onmouseout=\"this.style.backgroundColor=''\">";
            temp = temp + "<td align=center><input type='checkbox' value='" + this.getDataDirecte()[i][0] + "' name='id' id='checkbox" + i + "'/></td>";
            tempxml = tempxml + "<row>";
            int nombreLien = 0;
            int j = 0;
            int l = 0;
            for (j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                alignement = "left";
                if (listeFields != null) {
                    for (int k = 0; k < listeFields.length; ++k) {
                        if (listeFields[k].getName().compareToIgnoreCase(this.getLibeEntete()[j]) != 0) continue;
                        if (listeFields[k].getType().getSimpleName().compareToIgnoreCase("String") == 0) {
                            alignement = "left";
                            break;
                        }
                        if (listeFields[k].getType().getSimpleName().compareToIgnoreCase("Double") == 0) {
                            alignement = "right";
                            break;
                        }
                        alignement = "center";
                        break;
                    }
                }
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    String rajoutLien = "";
                    if (this.getUrlLien() != null) {
                        rajoutLien = this.getRajoutLienMultiple(this.getData()[i], this.getUrlLien()[l], this.getUrlLienAffiche()[l]);
                    }
                    ++l;
                    String lienAutre = "";
                    if (this.isGroupe()) {
                        for (int g = 0; g < this.getLibeEntete().length - 1; ++g) {
                            lienAutre = lienAutre + this.getLibeEntete()[g] + "=" + this.getDataDirecte()[i][g] + "&";
                        }
                        lienAutre = lienAutre + this.getLibeEntete()[this.getLibeEntete().length - 1] + "=" + this.getDataDirecte()[i][this.getLibeEntete().length - 1] + "&" + this.getLienFormulaire();
                    } else {
                        lienAutre = colLien + "=" + valL;
                    }
                    lien = "<a href='" + this.getLien()[numeroColonne] + "&" + lienAutre + rajoutLien + "'>";
                    apresLien = "</a>";
                }
                boolean test = false;
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                if (this.getPourcentage() != null) {
                    for (int element = 0; element < this.getPourcentage().length; ++element) {
                        if (!this.libeEntete[j].equals(this.getPourcentage()[element])) continue;
                        test = true;
                        temp = temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\">" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + " (" + Utilitaire.formaterAr(this.getPourc()[element][i]) + " %)" + apresLien + " </td>";
                        break;
                    }
                }
                if (!test) {
                    temp = this.isColumnToHide(this.getLibeEntete()[j]) ? temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align='center' >" + lien + "- " + apresLien + " </td>" : temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align=\"" + alignement + "\" >" + lien + Utilitaire.champNull(this.getDataDirecte()[i][j]) + " " + apresLien + " </td>";
                }
                tempcsv = tempcsv + Utilitaire.cleanCsv(this.getDataDirecte()[i][j]);
                tempxml = tempxml + "<" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">" + Utilitaire.cleanXmlValue(this.getDataDirecte()[i][j]) + "</" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">\r\n";
                if (j == nombreColonne - 1) continue;
                tempcsv = tempcsv + ";";
            }
            temp = temp + "</tr>";
            tempcsv = tempcsv + "\r\n";
            tempxml = tempxml + "</row>\r\n";
        }
        temp = temp + "</tbody>\n</table>\n";
        if (this.isAfficheBouttondevalider()) {
            temp = temp + "<input id='acte' type='hidden' name='acte' value='update'/> ";
            temp = temp + "<button type='button' name='Submit2' class='btn btn-success pull-left' style='margin-left: 25px;' onClick=\"document.getElementById('acte').value='detacher'; document.getElementById('formmultiple').submit();\">D&eacute;tacher</button> ";
            temp = temp + "<button type='button' name='Submit2' class='btn btn-success pull-left' style='margin-left: 25px;' onClick=\"document.getElementById('acte').value='valider'; document.getElementById('formmultiple').submit();\">Viser</button> ";
        } else {
            if (this.getNameBoutton2().compareTo("") != 0) {
                temp = temp + "<button type='submit' name='Submit2' class='btn btn-danger pull-right' style='margin-right: 25px;' onClick=\"acte.value='" + this.getNameActe2() + "'\">" + this.getNameBoutton2() + "</button> ";
            }
            temp = temp + "<button type='submit' name='Submit2' class='btn btn-success pull-right' style='margin-right: 25px;' onClick=\"acte.value='" + this.getNameActe() + "'\">" + this.getNameBoutton() + "</button> ";
        }
        temp = temp + "</div></div>";
        this.setHtml(temp);
        this.setExpcsv(tempcsv);
        this.setExpxml(tempxml);
    }

    public void makeHtmlCorrection() throws Exception {
        int i;
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        String temp = "";
        String tempcsv = "";
        String tempxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>";
        temp = temp + "<div class='row'>";
        temp = temp + "<div class='row col-md-12'>";
        temp = temp + "<div class='box box-warning'>";
        temp = temp + "<div class='box-header'>";
        temp = temp + "<h3 class='box-title' align=center>" + this.getTitre() + "</h3>";
        temp = temp + "</div>";
        temp = temp + "<div class='box-body table-responsive no-padding'>";
        temp = temp + "<div id=\"selectnonee\">";
        temp = temp + "<table width=" + this.getTailleTableau() + " border=0 align=center cellpadding=3 cellspacing=3 class='table table-hover table-striped'>";
        temp = temp + "<thead>";
        temp = temp + "<tr class=" + this.getCssEntete() + ">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        nombreColonne = this.getDataDirecte()[0].length;
        temp = temp + "<th align=center valign=top class='contenuetable'></th>";
        for (i = 0; i < nombreColonne; ++i) {
            temp = this.getCritereLienString().compareToIgnoreCase("") != 0 ? temp + "<th width=" + this.getPropEntet()[i] + "% align=center valign=top class='contenuetable'>" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + ">" + this.getLibelleAffiche()[i] + "</a></th>" : temp + "<th width=" + this.getPropEntet()[i] + "% align=center valign=top class='contenuetable'>" + this.getLibelleAffiche()[i] + "</th>";
            tempcsv = tempcsv + Utilitaire.cleanCsv(this.getLibelleAffiche()[i]);
            if (i == nombreColonne - 1) continue;
            tempcsv = tempcsv + ";";
        }
        temp = temp + "</tr>";
        tempcsv = tempcsv + "\r\n";
        tempxml = tempxml + "\r\n";
        temp = temp + "</thead>";
        temp = temp + "<tbody>";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=this.style.backgroundColor='#EAEAEA' onmouseout=\"this.style.backgroundColor=''\">";
            tempxml = tempxml + "<row>\r\n";
            int nombreLien = 0;
            int j = 0;
            temp = temp + "<td align=center><input type='checkbox' value='" + this.getDataDirecte()[i][0] + "' name='id' id='checkbox" + i + "'/></td>";
            for (j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    lien = "<a href='" + this.getLien()[numeroColonne] + "&" + colLien + "=" + valL + "'>";
                    apresLien = "</a>";
                }
                String alignement = "left";
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                temp = temp + "<td width=" + this.getPropEntet()[j] + "% align=" + alignement + " >" + lien + this.getDataDirecte()[i][j] + apresLien + " </td>";
                tempcsv = tempcsv + Utilitaire.cleanCsv(this.getDataDirecte()[i][j]);
                tempxml = tempxml + "<" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">" + Utilitaire.cleanXmlValue(this.getDataDirecte()[i][j]) + "</" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">\r\n";
                if (j == nombreColonne - 1) continue;
                tempcsv = tempcsv + ";";
            }
            temp = temp + "</tr>";
            tempcsv = tempcsv + "\r\n";
            tempxml = tempxml + "</row>\r\n";
        }
        temp = temp + "<input type=\"hidden\" name=\"id\" value=\"" + this.getIds() + "\">";
        temp = temp + "</tbody>";
        tempxml = tempxml + "</tableau>\r\n";
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<div class='box-footer'>";
        temp = temp + "<input type='hidden' name='acte' value='update'/> ";
        temp = temp + "<button type='submit' name='Submit2' class='btn btn-success pull-right' style='margin-left: 25px;' onClick=\"acte.value='corriger'\">Corriger</button> ";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<div class='col-xs-12'>";
        temp = temp + "</div>";
        this.setHtml(temp);
        this.setExpcsv(tempcsv);
        this.setExpxml(tempxml);
    }

    public String getHtmlWithCheckbox() throws Exception {
        if (this.html == null || this.html.compareToIgnoreCase("") == 0) {
            this.makeHtmlWithCheckbox();
        }
        return this.appendApjSplitActivation(this.html);
    }

    public String getHtmlWithCheckboxNew() throws Exception {
        if (this.html == null || this.html.compareToIgnoreCase("") == 0) {
            this.makeHtmlWithCheckboxNew();
        }
        return this.html;
    }

    public String getHtmlCorrection() throws Exception {
        if (this.html == null || this.html.compareToIgnoreCase("") == 0) {
            this.makeHtmlCorrection();
        }
        return this.html;
    }

    public String getHtmlWithCheckboxSelected() throws Exception {
        this.setAfficheBouttondevalider(true);
        if (this.html == null || this.html.compareToIgnoreCase("") == 0) {
            this.makeHtmlWithCheckbox();
        }
        return this.html;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getIds() {
        return this.ids;
    }

    private String getNameBoutton() {
        return this.nameBoutton;
    }

    private String getNameActe() {
        return this.nameActe;
    }

    public Map getLibelleEnteteAAffiche() {
        this.construireLibelleEnteteAnnotation();
        return this.libelleEnteteAAffiche;
    }

    public void setLibelleEnteteAAffiche(Map libelleEnteteAAffiche) {
        this.libelleEnteteAAffiche = libelleEnteteAAffiche;
        this.libelleEnteteAAfficheAnnotationFait = false;
    }

    private void construireLibelleEnteteAnnotation() {
        if (this.libelleEnteteAAfficheAnnotationFait) {
            return;
        }
        this.libelleEnteteAAfficheAnnotationFait = true;
        try {
            if (this.getLibeEntete() == null) {
                return;
            }
            if (this.libelleEnteteAAffiche == null) {
                this.libelleEnteteAAffiche = new HashMap();
            }
            ClassMAPTable modele = this.getData() != null && this.getData().length > 0 ? this.getData()[0] : null;
            Field[] listF = this.getListeField();
            if (modele == null && listF != null && listF.length > 0) {
                try {
                    modele = (ClassMAPTable)listF[0].getDeclaringClass().newInstance();
                }
                catch (Exception e) {
                    modele = null;
                }
            }
            if (modele == null && this.getData() != null && this.getData().getClass().getComponentType() != null && ClassMAPTable.class.isAssignableFrom(this.getData().getClass().getComponentType())) {
                try {
                    Class<?> compClass = this.getData().getClass().getComponentType();
                    if (!Modifier.isAbstract(compClass.getModifiers())) {
                        modele = (ClassMAPTable)compClass.newInstance();
                    }
                }
                catch (Exception e) {
                    modele = null;
                }
            }
            if (!(modele != null || listF != null && listF.length != 0 || this.getData() != null && this.getData().getClass().getComponentType() != null)) {
                return;
            }
            for (int i = 0; i < this.getLibeEntete().length; ++i) {
                LibelleAffichage la;
                String col = this.getLibeEntete()[i];
                if (col == null || this.getValFromMapIgnoreCase(this.libelleEnteteAAffiche, col) != null) continue;
                Field f = null;
                if (modele != null) {
                    try {
                        f = CGenUtil.getField(modele, col);
                    }
                    catch (Exception e) {
                        f = null;
                    }
                }
                if (f == null && listF != null) {
                    for (int j = 0; j < listF.length; ++j) {
                        if (listF[j] == null || !listF[j].getName().equalsIgnoreCase(col)) continue;
                        f = listF[j];
                        break;
                    }
                }
                if (f == null && this.getData() != null && this.getData().getClass().getComponentType() != null) {
                    try {
                        for (Class<?> c = this.getData().getClass().getComponentType(); c != null && c != Object.class; c = c.getSuperclass()) {
                            for (Field field : c.getDeclaredFields()) {
                                if (!field.getName().equalsIgnoreCase(col)) continue;
                                f = field;
                                break;
                            }
                            if (f == null) {
                                continue;
                            }
                            break;
                        }
                    }
                    catch (Exception c) {
                        // empty catch block
                    }
                }
                if (f == null || (la = f.getAnnotation(LibelleAffichage.class)) == null || la.value() == null || la.value().trim().isEmpty()) continue;
                this.libelleEnteteAAffiche.put(col, la.value());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public String getHtmlWithCheckboxUpdateMultiple() throws Exception {
        if (this.html == null || this.html.compareToIgnoreCase("") == 0) {
            this.makeHtmlWithCheckboxUpdateMultiple();
        }
        return this.html;
    }

    public void makeHtmlWithCheckboxUpdateMultiple() throws Exception {
        int i;
        if (this.getDataDirecte() == null || this.getDataDirecte().length == 0) {
            return;
        }
        String temp = "";
        String tempcsv = "";
        String tempxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>";
        temp = temp + "<div class='row m-0'>";
        temp = temp + "<div class='col-md-12 nopadding '>";
        temp = temp + "<div class='box'>";
        temp = temp + "<div class='box-body table-responsive no-padding'>";
        temp = temp + "<div id=\"selectnonee\">";
        temp = temp + "<table width=" + this.getTailleTableau() + " border=0 align=center cellpadding=3 cellspacing=3 class='table table-hover table-striped'>";
        temp = temp + "<thead>";
        temp = temp + "<tr class=" + this.getCssEntete() + ">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getDataDirecte().length;
        nombreColonne = this.getDataDirecte()[0].length;
        temp = temp + "<th align=center valign=top class='contenuetable'><input onclick=\"CocheToutCheckbox(this, 'id')\" type=\"checkbox\"></th>";
        for (i = 0; i < nombreColonne; ++i) {
            temp = this.getCritereLienString().compareToIgnoreCase("") != 0 ? temp + "<th width=" + this.getPropEntet()[i] + "% align=center valign=top class='contenuetable'>" + this.getCritereLienString() + "&triCol=yes&newcol=" + this.getLibeEntete()[i] + ">" + this.getLibelleAffiche()[i] + "</a></th>" : temp + "<th width=" + this.getPropEntet()[i] + "% align=center valign=top class='contenuetable'>" + this.getLibelleAffiche()[i] + "</th>";
            tempcsv = tempcsv + Utilitaire.cleanCsv(this.getLibelleAffiche()[i]);
            if (i == nombreColonne - 1) continue;
            tempcsv = tempcsv + ";";
        }
        temp = temp + "</tr>";
        tempcsv = tempcsv + "\r\n";
        tempxml = tempxml + "\r\n";
        temp = temp + "</thead>";
        temp = temp + "<tbody>";
        for (i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr onmouseover=this.style.backgroundColor='#EAEAEA' onmouseout=\"this.style.backgroundColor=''\">";
            tempxml = tempxml + "<row>\r\n";
            int nombreLien = 0;
            int j = 0;
            int l = 0;
            temp = temp + "<td align=center><input type='checkbox' value='" + i + "' name='ids' id='checkbox" + i + "'/></td>";
            for (j = 0; j < nombreColonne; ++j) {
                String[] testsplits;
                String testsplit;
                String lien = "";
                String apresLien = "";
                int numeroColonne = Utilitaire.estIlDedans(this.getLibeEntete()[j], this.getColonneLien());
                if (numeroColonne != -1) {
                    String colLien = this.getLibeEntete()[j];
                    String valL = this.getDataDirecte()[i][j];
                    if (this.getAttLien() != null) {
                        colLien = this.getAttLien()[nombreLien];
                    }
                    if (this.getValeurLien() != null) {
                        Object valeurC = CGenUtil.getValeurFieldByMethod(this.data[i], this.getValeurLien()[nombreLien]);
                        valL = String.valueOf(valeurC);
                    }
                    ++nombreLien;
                    String rajoutLien = "";
                    if (this.getUrlLien() != null) {
                        rajoutLien = this.getRajoutLienMultiple(this.getData()[i], this.getUrlLien()[l], this.getUrlLienAffiche()[l]);
                    }
                    ++l;
                    lien = "<a href='" + this.getLien()[numeroColonne] + "&" + colLien + "=" + valL + rajoutLien + "'>";
                    apresLien = "</a>";
                }
                String alignement = "left";
                if (this.getDataDirecte()[i][j].getClass().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    alignement = "left";
                }
                if ((testsplit = Utilitaire.enleverEspaceDoubleBase(this.getDataDirecte()[i][j])).contains(",") && Utilitaire.isStringNumeric((testsplits = testsplit.split(","))[0]) && Utilitaire.isStringNumeric(testsplits[1])) {
                    alignement = "right";
                }
                String val = lien + this.getDataDirecte()[i][j] + apresLien;
                temp = !this.getIds().equals(this.getLibeEntete()[j]) ? temp + this.getFormu().makeHtmlUpdateTableau(this.getLibeEntete()[j], val, i) : temp + "<td width=" + this.getPropEntet()[j] + "% align=" + alignement + " ><input type='hidden' id='" + this.getLibeEntete()[j] + "_" + i + "' name='" + this.getLibeEntete()[j] + "_" + i + "' value='" + lien + this.getDataDirecte()[i][j] + apresLien + "'>" + lien + this.getDataDirecte()[i][j] + apresLien + "</td>";
                tempcsv = tempcsv + Utilitaire.cleanCsv(this.getDataDirecte()[i][j]);
                tempxml = tempxml + "<" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">" + Utilitaire.cleanXmlValue(this.getDataDirecte()[i][j]) + "</" + Utilitaire.getXmlTag(this.getLibeEntete(), this.getLibelleAffiche(), j) + ">\r\n";
                if (j == nombreColonne - 1) continue;
                tempcsv = tempcsv + ";";
            }
            temp = temp + "</tr>";
            tempcsv = tempcsv + "\r\n";
            tempxml = tempxml + "</row>\r\n";
        }
        temp = temp + "<input type=\"hidden\" name=\"id\" value=\"" + this.getIds() + "\">";
        temp = temp + "</tbody>";
        tempxml = tempxml + "</tableau>\r\n";
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<div class='box-footer' style='padding:12px'>";
        temp = temp + "<button type='submit' name='Submit2' class='btn btn-primary pull-right' style='margin-right: 25px;'>Enregistrer</button> ";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        this.setHtml(temp);
        this.setExpcsv(tempcsv);
        this.setExpxml(tempxml);
    }

    public ValeurEtiquette[][] getValeurEtiquette() {
        return this.valeurEtiquette;
    }

    public void setValeurEtiquette(ValeurEtiquette[][] valeurEtiquette) {
        this.valeurEtiquette = valeurEtiquette;
    }

    public TableauRecherche() {
    }
}

