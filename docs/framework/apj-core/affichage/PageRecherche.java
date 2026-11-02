/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.lang3.StringEscapeUtils
 */
package affichage;

import affichage.ApjSplitView;
import affichage.Carte;
import affichage.Champ;
import affichage.Formulaire;
import affichage.Page;
import affichage.TableauRecherche;
import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import bean.ResultatEtSomme;
import com.google.gson.Gson;
import constante.ConstanteAffichage;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringEscapeUtils;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class PageRecherche
extends Page {
    public static final String NPP_PARAMETRE = "npp";
    private static final int[] NPP_CHOIX = new int[]{25, 50, 100, 200};
    private TableauRecherche listeTabDet;
    private String libelleMarker;
    private String[] libelleMarkerListe;
    private ClassMAPTable[] liste;
    private ClassMAPTable critere;
    private String[] colInt;
    private String[] valInt;
    private ResultatEtSomme rs;
    private int numPage = 1;
    private String[] colSomme;
    private String apres;
    private String aWhere = "";
    private String ordre = "";
    private String apresLienPage = "";
    private int npp = 0;
    private int nppParDefautJsp = 0;
    private String[] tableauAffiche;
    private int nbTableauAffiche;
    private boolean premier = false;
    private String[] tableauAfficheDefaut;
    private String critereHttp = "";
    private String multipleAwhere = "";
    String nbObject;
    private boolean tableauRecapDirecte = false;
    private Carte carte;
    public static final int pageSuivante = 2;
    int pageEnRequest = 0;
    private static final String PLACEHOLDER_DEBUT_MOBILE = "{";
    private static final String PLACEHOLDER_FIN_MOBILE = "}";
    private String formatTitre;
    private String cssBoxExtraMobile;
    private String htmlMobile;
    private String imageDefaut;
    private boolean visibleColMobile = true;
    private boolean apjSplitEnabled = true;

    public boolean isApjSplitEnabled() {
        return this.apjSplitEnabled;
    }

    public void setApjSplitEnabled(boolean apjSplitEnabled) {
        this.apjSplitEnabled = apjSplitEnabled;
        if (this.getTableau() != null) {
            this.getTableau().setApjSplitEnabled(apjSplitEnabled);
        }
    }

    public String getApjSplitAttributes() {
        return this.apjSplitEnabled ? " data-apj-split-enabled=\"true\"" : "";
    }

    public String getApjSplitHtml() {
        return this.apjSplitEnabled ? ApjSplitView.render() : "";
    }

    public boolean isVisibleColMobile() {
        return this.visibleColMobile;
    }

    public void setVisibleColMobile(boolean visibleColMobile) {
        this.visibleColMobile = visibleColMobile;
    }

    public String getImageDefaut() {
        return this.imageDefaut;
    }

    public void setImageDefaut(String imageDefaut) {
        this.imageDefaut = imageDefaut;
    }

    public Carte getCarte() {
        return this.carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public String getLibelleMarker() {
        return this.libelleMarker;
    }

    public void setLibelleMarker(String libelleMarker) {
        this.libelleMarker = libelleMarker;
    }

    public String[] getLibelleMarkerListe() {
        return this.libelleMarkerListe;
    }

    public void setLibelleMarkerListe(String[] libelleMarkerListe) {
        this.libelleMarkerListe = libelleMarkerListe;
    }

    public String getNbObject() {
        return this.nbObject == null ? "" : this.nbObject;
    }

    public void setNbObject(String nbObject) {
        this.nbObject = nbObject;
    }

    public void rajoutLienBasPage(String var, String valeur) {
        this.setApresLienPage(this.getApresLienPage() + "&" + var + "=" + valeur);
    }

    public int getPageEnRequest() {
        return this.pageEnRequest;
    }

    public void setPageEnRequest(int pageEnRequest) {
        this.pageEnRequest = pageEnRequest;
    }

    public void setPageEnRequest(String pageEnRequest) {
        this.pageEnRequest = Utilitaire.stringToInt(pageEnRequest);
    }

    public void makePagination() throws Exception {
        if (this.getTableau() != null) {
            this.getTableau().setApjSplitEnabled(this.apjSplitEnabled);
        }
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        int pageCutLow = this.getNumPage() - 1;
        int pageCutHigh = this.getNumPage() + 1;
        String retour = "";
        retour = retour + "<nav aria-label=\"Page navigation\" >\r\n<ul class=\"pagination\">\r\n";
        retour = retour + "<li><span class='page-link btn btn-secondary btn-small' ><span class='apj-page-state-label'>Page: </span>" + this.getNumPage() + "<span class='apj-page-state-separator'> sur </span>" + this.getNombrePage() + " </span></li>\r\n";
        retour = retour + "<li class=\"page-item" + (this.getNumPage() <= 1 ? " disabled" : "") + "\"><a class=\"page-link btn btn-secondary btn-small\" href=" + (this.getNumPage() > 1 ? this.getLien() + "?but=" + this.getApres() + "&recap=" + this.getFormu().getRecapcheck() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNumPage() - 1) + this.getNppLien() + this.getApresLienPage() + this.formu.getListeCritereStringCheckbox(this) : "#") + "><i  class=\"material-symbols-rounded\">chevron_left</i></a></li>\r\n";
        retour = retour + "<li class=\"page-item" + (this.getNumPage() >= this.getNombrePage() ? " disabled" : "") + "\"><a class=\"page-link btn btn-secondary btn-small\" href=" + (this.getNumPage() < this.getNombrePage() ? this.getLien() + "?but=" + this.getApres() + "&recap=" + this.getFormu().getRecapcheck() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNumPage() + 1) + this.getNppLien() + this.getApresLienPage() + this.formu.getListeCritereStringCheckbox(this) : "#") + "><i  class=\"material-symbols-rounded\">chevron_right</i></a></li>\r\n";
        retour = retour + "<li class=\"apj-expand-page-item\"><button type='button' class=\"page-link btn btn-secondary btn-small\" href=\"#\" onclick='expandTable()' ><i class=\" expand-icon material-symbols-rounded\">unfold_more</i></button></li>\r\n";
        retour = retour + "</ul>\r\n</nav>";
        retour = retour + "<script>";
        retour = retour + "function expandTable() {\n    // Toggle la classe .expand sur l'\u00c3\u00a9l\u00c3\u00a9ment .box.table-box\n    const tableBox = document.querySelector('.box.table-box');\n    if (tableBox) {\n        tableBox.classList.toggle('expand');\n    }\n\n    // Toggle entre unfold_more et unfold_less sur l'ic\u00c3\u00b4ne Material Symbols\n    const expandIcon = document.querySelector('.expand-icon');\n    if (expandIcon) {\n        if (expandIcon.textContent.trim() === 'unfold_more') {\n            expandIcon.textContent = 'unfold_less';\n        } else {\n            expandIcon.textContent = 'unfold_more';\n        }\n    }\n}\n";
        retour = retour + "</script>";
        this.getTableau().setPagination(retour);
        this.setNbObject(this.getListe().length + " r&eacute;sultats sur " + Utilitaire.formaterSansVirgule(this.rs.getSommeEtNombre()[this.getNombreColonneSomme()]));
    }

    public String genererScrollInfinie() throws Exception {
        String retour = "";
        if (this.getPageEnRequest() >= 2) {
            this.getTableau().setHtml(this.getTableau().makeHtmlTemplateSansEntete());
            retour = retour + this.getTableau().getHtml();
        }
        return retour;
    }

    public String genererScrollInfinieWithCheckBox() throws Exception {
        String retour = "";
        if (this.getPageEnRequest() >= 2) {
            this.getTableau().setHtml(this.getTableau().makeHtmlWithCheckboxSansEntete());
            retour = retour + this.getTableau().getHtml();
        }
        return retour;
    }

    public String getScrollinfinieJS() {
        return "<script>\n    let offset = 2;\n    const limit = 50;\n    const nombrePage=" + this.getNombrePage() + ";\n    let isThrottled = false;\n    let hasMoreData = true;\n\n    function loadData() {\n        if (isThrottled || !hasMoreData) return;\n\n        isThrottled = true;\n\n        var lienAleha=\"" + this.getApres() + "\";\n        var filtre=\"" + this.getFormu().getListeCritereStringCheckbox(this) + "\";\n        fetch(\"" + this.getReq().getContextPath() + "/pages/\" + lienAleha + '?numPag=' + offset + '&limit=' + limit + '&npp=" + this.getValeurNppParametre() + "' + filtre)\n            .then(response => response.text())\n            .then(html => {\n                if (html.trim() === \"\"||nombrePage==1||nombrePage==(offset - 1)) {\n                    hasMoreData = false;\n                } else {\n                    let tableContainer = document.querySelector('#table-apj tbody');\n                    tableContainer.insertAdjacentHTML('beforeend', html);\n                    offset = offset+1;\n                    isThrottled = false;\n                }\n            })\n            .catch(error => console.error('Erreur:', error));\n    }\n\n    document.addEventListener('DOMContentLoaded', function() {\nexpandTable();        const monDiv = document.querySelector('.scroll-table-container#selectnonee');\n        monDiv.addEventListener('scroll', function() {\n            loadData();\n        });\n    });\n\n    window.onscroll = function() {\n        if ((window.innerHeight + window.pageYOffset) >= document.body.offsetHeight - 100) {\n            loadData();\n        }\n    };\n\n</script>";
    }

    public void makeTableauAffiche() throws Exception {
        String[] temp = new String[this.getNbTableauAffiche()];
        for (int i = 0; i < temp.length; ++i) {
            temp[i] = this.getReq().getParameter("colAffiche" + (i + 1));
        }
        this.setTableauAffiche(Utilitaire.formerTableauGroupe(temp));
    }

    public void preparerDataList(Connection c) throws Exception {
        this.critere.setNomTable(this.getBase().getNomTable());
        this.critere.setParamsMultiple(this.getBase().getParamsMultiple());
        this.makeCritere();
        if (this.premier) {
            this.rs = new ResultatEtSomme();
            this.rs.initialise(this.getColSomme());
        } else {
            String recap = this.getReq().getParameter("recap");
            recap = "checked";
            this.getFormu().setRecapcheck(recap);
            String req = this.getMultipleAwhere() + this.getAWhere();
            String motsClesss = this.getReq().getParameter("motsclesss");
            req = ListeColonneTable.getChamp(this.getBase(), "motsClesss") != null && motsClesss != null && motsClesss.compareToIgnoreCase("") != 0 && motsClesss.compareToIgnoreCase("null") != 0 ? " and upper(motsclesss) like upper('%" + motsClesss + "%')" + req : PageRecherche.construireClauseMotsCles(this.getBase(), this.getTableauAfficheDefaut(), motsClesss) + req;
            if (this.getColSomme() == null && this.getNpp() == 0) {
                Object[] donnes = this.getUtilisateur().getData(this.critere, this.getColInt(), this.getValInt(), c, req);
                this.rs = new ResultatEtSomme();
                this.rs.setResultat(donnes);
                double[] nbDonnes = new double[]{donnes.length};
                this.rs.setSommeEtNombre(nbDonnes);
            } else {
                this.rs = this.getUtilisateur().getDataPage(this.critere, this.getColInt(), this.getValInt(), this.getNumPage(), req, this.getColSomme(), c, this.npp);
            }
        }
        this.setListe((ClassMAPTable[])this.rs.getResultat());
        for (int i = 0; i < this.getListe().length; ++i) {
            this.getListe()[i].setNomTable(this.critere.getNomTable());
        }
    }

    public static String construireClauseMotsCles(ClassMAPTable base, String[] libentete, String motsClesss) throws Exception {
        if (motsClesss == null || motsClesss.trim().isEmpty() || motsClesss.equalsIgnoreCase("null")) {
            return "";
        }
        String[] colonnesCibles = libentete;
        if ((colonnesCibles == null || colonnesCibles.length == 0) && base != null) {
            colonnesCibles = base.getMotCles();
        }
        ArrayList<String> colsValides = new ArrayList<String>();
        if (colonnesCibles != null && base != null) {
            for (String col : colonnesCibles) {
                bean.Champ ch;
                if (col == null || col.trim().isEmpty() || (ch = ListeColonneTable.getChamp(base, col.trim())) == null) continue;
                colsValides.add(col.trim());
            }
        }
        if (colsValides.isEmpty()) {
            return "";
        }
        StringBuilder sql = new StringBuilder(" and (");
        boolean premierTerme = true;
        for (String colsValide : colsValides) {
            if (!premierTerme) {
                sql.append(" or ");
            }
            sql.append("upper(").append(colsValide).append(") like upper('%").append(motsClesss).append("%')");
            premierTerme = false;
        }
        sql.append(")");
        return sql.toString();
    }

    public String getOrdre() {
        return this.ordre;
    }

    public void setOrdre(String ordre) {
        this.ordre = ordre;
    }

    public void preparerDataFormu() throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            this.formu.getAllData(c);
        }
    }

    public void setPremier(boolean premier) {
        this.premier = premier;
    }

    public boolean getPremier() {
        return this.premier;
    }

    public boolean isPremier() {
        return this.premier;
    }

    public void preparerData() throws Exception {
        this.preparerData(null);
    }

    public void preparerData(Connection con) throws Exception {
        Connection c = null;
        boolean estOuvert = false;
        if (con != null) {
            c = con;
            estOuvert = true;
        }
        try {
            if (!estOuvert) {
                c = new UtilDB().GetConn();
            }
            this.appliquerNppDepuisRequete();
            this.setPageEnRequest(this.getReq().getParameter("numPag"));
            this.preparerDataList(c);
            if (!this.getIsGraphe()) {
                this.formu.getAllData(c);
            }
            this.getReq().getSession().setAttribute("critere", (Object)this.getCritere());
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            if (c != null && !estOuvert) {
                c.close();
            }
        }
    }

    public void makeCritere() throws Exception {
        Vector<String> colIntv = new Vector<String>();
        Vector<String> valIntv = new Vector<String>();
        Champ[] tempChamp = this.formu.getCrtFormu();
        this.getCritere().setMode("select");
        String temp = "";
        for (int i = 0; i < tempChamp.length; ++i) {
            Field f = CGenUtil.getField(this.getBase(), tempChamp[i].getNom());
            Champ champConfigure = this.formu.getChamp(tempChamp[i].getNom());
            boolean recherchePrecis = champConfigure != null && champConfigure.isRecherchePrecis();
            boolean isMultiple = tempChamp[i].getEstMultiple();
            if (isMultiple) {
                String[] valeurs = this.getReq().getParameterValues(tempChamp[i].getNom());
                if (valeurs != null && valeurs.length > 0 && !valeurs[0].isEmpty()) {
                    boolean containsAllWildcard = false;
                    for (String val : valeurs) {
                        if (val == null || !val.trim().equals("%")) continue;
                        containsAllWildcard = true;
                        break;
                    }
                    this.multipleAwhere = containsAllWildcard ? this.multipleAwhere + " AND " + tempChamp[i].getNom() + " LIKE '%'" : (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0 ? this.multipleAwhere + " AND " + tempChamp[i].getNom() + " IN (" + Utilitaire.tabToString(valeurs, "'", ",") + ")" : this.multipleAwhere + " AND " + tempChamp[i].getNom() + " IN (" + Utilitaire.tabToString(valeurs, "", ",") + ")");
                }
            } else if (tempChamp[i].getEstIntervalle() == 1) {
                colIntv.add(tempChamp[i].getNom());
                if ((this.getParamSansNull(tempChamp[i].getNom() + "1") == null || this.getParamSansNull(tempChamp[i].getNom() + "1").compareToIgnoreCase("") == 0) && this.getFormu().getChamp(tempChamp[i].getNom() + "1") != null && this.getFormu().getChamp(tempChamp[i].getNom() + "1").getDefaut() != null && this.getFormu().getChamp(tempChamp[i].getNom() + "1").getDefaut().compareToIgnoreCase("") != 0) {
                    valIntv.add(this.getFormu().getChamp(tempChamp[i].getNom() + "1").getDefaut());
                } else {
                    valIntv.add(this.getParamSansNull(tempChamp[i].getNom() + "1"));
                }
                if ((this.getParamSansNull(tempChamp[i].getNom() + "2") == null || this.getParamSansNull(tempChamp[i].getNom() + "2").compareToIgnoreCase("") == 0) && this.getFormu().getChamp(tempChamp[i].getNom() + "2") != null && this.getFormu().getChamp(tempChamp[i].getNom() + "2").getDefaut() != null && this.getFormu().getChamp(tempChamp[i].getNom() + "2").getDefaut().compareToIgnoreCase("") != 0) {
                    valIntv.add(this.getFormu().getChamp(tempChamp[i].getNom() + "2").getDefaut());
                } else {
                    valIntv.add(this.getParamSansNull(tempChamp[i].getNom() + "2"));
                }
            } else if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
                if ((this.getParamSansNull(tempChamp[i].getNom()) == null || this.getParamSansNull(tempChamp[i].getNom()).compareToIgnoreCase("") == 0) && this.getFormu().getChamp(tempChamp[i].getNom()) != null && this.getFormu().getChamp(tempChamp[i].getNom()).getDefaut() != null && this.getFormu().getChamp(tempChamp[i].getNom()).getDefaut().compareToIgnoreCase("") != 0) {
                    CGenUtil.setValChamp(this.getCritere(), f, this.getFormu().getChamp(tempChamp[i].getNom()).getValeur());
                    if (recherchePrecis) {
                        this.getCritere().addAttributRecherchePrecis(f.getName());
                    }
                } else {
                    String valeurChamp = this.getReq().getParameter(Utilitaire.remplacerUnderscore(tempChamp[i].getNom()));
                    if (valeurChamp != null && valeurChamp.compareTo("") != 0) {
                        CGenUtil.setValChamp(this.getCritere(), f, this.getParamSansNull(tempChamp[i].getNom()));
                        if (recherchePrecis) {
                            this.getCritere().addAttributRecherchePrecis(f.getName());
                        }
                    }
                }
            } else if (!f.getType().getName().contains("Date") || this.getParamSansNull(tempChamp[i].getNom()) != null && this.getParamSansNull(tempChamp[i].getNom()).compareToIgnoreCase("") != 0) {
                String operateur = " like ";
                if (recherchePrecis) {
                    operateur = " = ";
                }
                this.aWhere = (this.getParamSansNull(tempChamp[i].getNom()) == null || this.getParamSansNull(tempChamp[i].getNom()).compareToIgnoreCase("") == 0) && this.getFormu().getChamp(tempChamp[i].getNom()) != null && this.getFormu().getChamp(tempChamp[i].getNom()).getDefaut() != null && this.getFormu().getChamp(tempChamp[i].getNom()).getDefaut().compareToIgnoreCase("") != 0 ? this.aWhere + " and " + f.getName() + operateur + "'" + Utilitaire.getValeurNonNull(this.getFormu().getChamp(tempChamp[i].getNom()).getValeur()) + "'" : this.aWhere + " and " + f.getName() + operateur + "'" + Utilitaire.getValeurNonNull(this.getParamSansNull(tempChamp[i].getNom())) + "'";
            }
            if (this.getParamSansNull(tempChamp[i].getNom()) == null || this.getParamSansNull(tempChamp[i].getNom()).compareToIgnoreCase("") == 0) continue;
            this.critereHttp = this.critereHttp + "&" + f.getName() + "=" + this.getParamSansNull(tempChamp[i].getNom());
        }
        this.colInt = new String[colIntv.size()];
        colIntv.copyInto(this.colInt);
        this.valInt = new String[valIntv.size()];
        valIntv.copyInto(this.valInt);
        if (this.getReq().getParameter("triCol") != null) {
            this.formu.getChamp("colonne").setValeur(this.getReq().getParameter("newcol"));
            if (this.getReq().getParameter("ordre").compareToIgnoreCase("") == 0) {
                this.formu.getChamp("ordre").setValeur("asc");
                this.setOrdre(" order by " + this.getReq().getParameter("newcol") + " asc ");
            } else if (this.getReq().getParameter("ordre").compareToIgnoreCase("desc") == 0) {
                this.formu.getChamp("ordre").setValeur("asc");
                this.setOrdre(" order by " + this.getReq().getParameter("newcol") + " asc ");
            } else {
                this.formu.getChamp("ordre").setValeur("desc");
                this.setOrdre(" order by " + this.getReq().getParameter("newcol") + " desc ");
            }
        }
        if (this.formu.getChamp("colonne").getValeur() != null && this.formu.getChamp("ordre").getValeur().compareToIgnoreCase("-") != 0 && this.formu.getChamp("colonne").getValeur().compareToIgnoreCase("") != 0) {
            this.setOrdre(" order by " + this.formu.getChamp("colonne").getValeur() + " " + this.formu.getChamp("ordre").getValeur());
        }
        this.aWhere = this.aWhere + this.getOrdre();
        if (this.getReq().getParameter("etat_table") != null) {
            this.formu.setCurrentEtat(this.getReq().getParameter("etat_table"));
        }
    }

    public void creerObjetPage(String[] libEntete) throws Exception {
        this.getValeurFormulaire();
        this.preparerData();
        this.makeTableauRecap();
        String critereLienTab = "<a href=" + this.getLien() + "?but=" + this.getApres() + "&numPag=1" + this.getNppLien() + this.getApresLienPage() + this.formu.getListeCritereString() + "";
        ClassMAPTable crt = this.getCritere();
        Field[] listF = crt.getClass().getDeclaredFields();
        this.setTableau(new TableauRecherche(this.getListe(), libEntete, critereLienTab, listF));
        this.makePagination();
    }

    public void creerObjetPage(ClassMAPTable[] liste, String[] libEntete) throws Exception {
        this.setListe(liste);
        this.creerObjetPage(libEntete);
    }

    public void creerObjetPage(String[] libEnteteDefaut, String[] colSom) throws Exception {
        this.setColSomme(colSom);
        this.getValeurFormulaire();
        this.preparerData();
        this.makeTableauRecap();
        String critereLienTab = "<a href=" + this.getLien() + "?but=" + this.getApres() + "&numPag=1" + this.getNppLien() + this.getApresLienPage() + this.formu.getListeCritereString();
        ClassMAPTable crt = this.getCritere();
        Field[] listF = crt.getClass().getDeclaredFields();
        String[] enteteAuto = this.getTableauAffiche();
        if (this.getTableauAffiche() == null) {
            this.setTableau(new TableauRecherche(this.getListe(), libEnteteDefaut, critereLienTab, listF));
        } else {
            this.setTableau(new TableauRecherche(this.getListe(), enteteAuto, critereLienTab, listF));
        }
        this.getTableau().setUserEJB(this.getUtilisateur());
        this.makePagination();
        Carte cr = Carte.newInstance(this.getListe(), this.getLibelleMarker(), this.getLibelleMarkerListe());
        this.setCarte(cr);
    }

    public void creerObjetPage(String[] libEnteteDefaut, String[] colSom, Connection c) throws Exception {
        this.setColSomme(colSom);
        this.getValeurFormulaire();
        this.preparerData(c);
        this.makeTableauRecap();
        String critereLienTab = "<a href=" + this.getLien() + "?but=" + this.getApres() + "&numPag=1" + this.getNppLien() + this.getApresLienPage() + this.formu.getListeCritereString();
        ClassMAPTable crt = this.getCritere();
        Field[] listF = crt.getClass().getDeclaredFields();
        String[] enteteAuto = this.getTableauAffiche();
        if (this.getTableauAffiche() == null) {
            this.setTableau(new TableauRecherche(this.getListe(), libEnteteDefaut, critereLienTab, listF));
        } else {
            this.setTableau(new TableauRecherche(this.getListe(), libEnteteDefaut, critereLienTab, listF));
        }
        this.getTableau().setUserEJB(this.getUtilisateur());
        this.makePagination();
        Carte cr = Carte.newInstance(this.getListe(), this.getLibelleMarker(), this.getLibelleMarkerListe());
        this.setCarte(cr);
    }

    public PageRecherche(ClassMAPTable o, HttpServletRequest req, String[] vrt, String[] listInterval, int nbRange, String[] tabAff, int nbAff, Boolean premier) throws Exception {
        this.premier = premier;
        this.setBase(o);
        this.setCritere((ClassMAPTable)Class.forName(o.getClassName()).newInstance());
        this.setReq(req);
        if (this.getReq().getParameter("premier") != null && this.getReq().getParameter("premier").compareToIgnoreCase("") != 0) {
            this.setPremier(Boolean.valueOf(this.getReq().getParameter("premier")));
        }
        this.formu = new Formulaire();
        this.formu.setObjet(this.getBase());
        this.formu.makeChampFormu(vrt, listInterval, nbRange);
        this.setNbTableauAffiche(nbAff);
        this.setTableauAfficheDefaut(tabAff);
        this.makeTableauAffiche();
        this.formu.makeChampTableauAffiche(this.getNbTableauAffiche(), tabAff);
        if (req != null && req.getSession().getAttribute("lang") != null) {
            this.formu.lang = String.valueOf(req.getSession().getAttribute("lang"));
        }
    }

    public PageRecherche(ClassMAPTable o, HttpServletRequest req, String[] vrt, String[] listInterval, int nbRange, String[] tabAff, int nbAff) throws Exception {
        this.setBase(o);
        this.setCritere((ClassMAPTable)Class.forName(o.getClassName()).newInstance());
        this.setReq(req);
        if (this.getReq().getParameter("premier") != null && this.getReq().getParameter("premier").compareToIgnoreCase("") != 0) {
            this.setPremier(Boolean.valueOf(this.getReq().getParameter("premier")));
        }
        this.formu = new Formulaire();
        if (req != null && req.getParameter("motsclesss") != null && req.getParameter("motsclesss").compareToIgnoreCase("") != 0) {
            this.formu.setReqqq(req);
        }
        this.formu.setObjet(this.getBase());
        this.formu.makeChampFormu(vrt, listInterval, 4);
        this.setNbTableauAffiche(nbAff);
        this.setTableauAfficheDefaut(tabAff);
        this.makeTableauAffiche();
        this.formu.makeChampTableauAffiche(this.getNbTableauAffiche(), tabAff);
        if (req != null && req.getSession().getAttribute("lang") != null) {
            this.formu.lang = String.valueOf(req.getSession().getAttribute("lang"));
        }
    }

    public PageRecherche() {
    }

    public PageRecherche(ClassMAPTable o, HttpServletRequest req, String[] vrt, String[] listInterval, int nbRange) throws Exception {
        this.setBase(o);
        this.setCritere((ClassMAPTable)Class.forName(o.getClassName()).newInstance());
        this.setReq(req);
        if (this.getReq().getParameter("premier") != null && this.getReq().getParameter("premier").compareToIgnoreCase("") != 0) {
            this.setPremier(Boolean.valueOf(this.getReq().getParameter("premier")));
        }
        this.formu = new Formulaire();
        this.formu.setObjet(this.getBase());
        this.formu.makeChampFormu(vrt, listInterval, nbRange);
    }

    public ClassMAPTable[] getListe() {
        return this.liste;
    }

    public void setListe(ClassMAPTable[] liste) {
        this.liste = liste;
    }

    public void setListeTabDet(TableauRecherche listeTabDet) {
        this.listeTabDet = listeTabDet;
    }

    public TableauRecherche getListeTabDet() {
        return this.listeTabDet;
    }

    public void setCritere(ClassMAPTable critere) {
        this.critere = critere;
    }

    public ClassMAPTable getCritere() {
        return this.critere;
    }

    public void setColInt(String[] colInt) {
        this.colInt = colInt;
    }

    public String[] getColInt() {
        return this.colInt;
    }

    public void setValInt(String[] valInt) {
        this.valInt = valInt;
    }

    public String[] getValInt() {
        return this.valInt;
    }

    @Override
    public void makeHtml() {
    }

    public void setRs(ResultatEtSomme rs) {
        this.rs = rs;
    }

    public ResultatEtSomme getRs() {
        return this.rs;
    }

    public void setNumPage(int numPage) {
        this.numPage = numPage;
    }

    public int getNumPage() {
        int temp = 1;
        String tempS = this.getParamSansNull("numPag");
        if (tempS == null || tempS.compareToIgnoreCase("") == 0) {
            return this.numPage;
        }
        temp = Utilitaire.stringToInt(tempS);
        if (temp > 0) {
            return temp;
        }
        return this.numPage;
    }

    public void setColSomme(String[] colSomme) {
        this.colSomme = colSomme;
    }

    public String[] getColSomme() {
        return this.colSomme;
    }

    public void setDataDirecte(String[] entete, double[] data) throws Exception {
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        String[][] d = new String[1][data.length];
        for (int i = 0; i < data.length; ++i) {
            d[0][i] = Utilitaire.formaterAr(data[i]);
        }
        TableauRecherche t = new TableauRecherche(d, entete);
        t.setTitre(RB.getString("recapitulation"));
        t.setIsRecap(true);
        this.setTableauRecap(t);
        this.tableauRecapDirecte = true;
    }

    public void makeTableauRecap() throws Exception {
        if (this.tableauRecapDirecte && this.getTableauRecap() != null) {
            return;
        }
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        String[][] data = null;
        String[] entete = null;
        int nbColSomme = 0;
        if (this.getColSomme() != null) {
            nbColSomme = this.getColSomme().length;
        }
        entete = new String[nbColSomme + 2];
        data = new String[1][entete.length];
        entete[0] = "";
        data[0][0] = RB.getString("total");
        entete[1] = RB.getString("nombre");
        data[0][1] = Utilitaire.formaterSansVirgule(this.rs.getSommeEtNombre()[nbColSomme]);
        for (int i = 0; i < nbColSomme; ++i) {
            data[0][i + 2] = Utilitaire.formaterAr(String.valueOf(this.rs.getSommeEtNombre()[i]));
            entete[i + 2] = RB.getString("sommede") + " " + String.valueOf(this.getColSomme()[i]);
        }
        TableauRecherche t = new TableauRecherche(data, entete);
        t.setTitre(RB.getString("recapitulation"));
        t.setIsRecap(true);
        this.setTableauRecap(t);
    }

    public int getNombrePage() {
        return Utilitaire.calculNbPage(this.rs.getSommeEtNombre()[this.getNombreColonneSomme()], this.getNpp());
    }

    public int getNombreColonneSomme() {
        int nbColSomme = 0;
        if (this.getColSomme() != null) {
            nbColSomme = this.getColSomme().length;
        }
        return nbColSomme;
    }

    public String getBasPage() throws Exception {
        return this.getBasPageTemplate();
    }

    public String getDownloadFormTemplate() throws Exception {
        String[] cols;
        String rawRequete;
        int sommeId;
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        String contextPath = this.getReq().getContextPath();
        String tab = "";
        String tab1 = "";
        try {
            tab = this.getTableau().getHtml();
            tab1 = this.getTableau().getHtmlPDF();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String awhere = " AND " + CGenUtil.makeWhere(this.getBase()) + CGenUtil.makeWhereIntervalle(this.colInt, this.valInt) + this.getAWhere().replace('\"', '\'');
        String temp = "";
        temp = temp + "<div class='row'>";
        temp = temp + "<div class='col-md-12 d-flex gap-2 toggle-button-export-container' style='margin-bottom: 25px;'>";
        temp = temp + "<label class=\"toggle-button-export export-card col-md-4\">\n         <input type=\"radio\" name=\"ext\" value=\"xls\">\n            <div class=\"checkbox\">\n                <span class=\"checkmark\"><i class=\"material-symbols-rounded\">check</i></span>\n             </div>\n             <div class=\"d-flex\">\n                 <img  src='" + contextPath + "/assets/img/excel file.png' alt='CSV'>                 <h5 class='Body14pxSemiBold'>Format .xls</h5>                 <p class='Body12pxRegular'>Compatible avec Microsoft Excel et autres tableurs.</p>             </div>\n      </label>";
        temp = temp + "<label class=\"toggle-button-export export-card col-md-4\">\n         <input type=\"radio\" name=\"ext\" value=\"csv\">\n            <div class=\"checkbox\">\n                <span class=\"checkmark\"><i class=\"material-symbols-rounded\">check</i></span></span>\n             </div>\n             <div class=\"d-flex\">\n                 <img  src='" + contextPath + "/assets/img/csv file.png' alt='CSV'>                 <h5 class='Body14pxSemiBold'>Format .csv</h5>                 <p class='Body12pxRegular'>Parfait pour l&#x27importation dans des bases de donn&eacute;es.</p>             </div>\n      </label>";
        temp = temp + "<label class=\"toggle-button-export export-card col-md-4\">\n         <input type=\"radio\" name=\"ext\" value=\"xml\">\n            <div class=\"checkbox\">\n                <span class=\"checkmark\"><i class=\"material-symbols-rounded\">check</i></span></span>\n             </div>\n             <div class=\"d-flex\">\n                 <img  src='" + contextPath + "/assets/img/xml file.png' alt='CSV'>                 <h5 class='Body14pxSemiBold'>Format .xml</h5>                 <p class='Body12pxRegular'>Recommand&eacute; pour les &eacute;changes de donn&eacute;es entre syst&egrave;mes.</p>             </div>\n      </label>";
        temp = temp + "<label class=\"toggle-button-export export-card col-md-4\">\n         <input type=\"radio\" name=\"ext\" value=\"pdf\">\n            <div class=\"checkbox\">\n                <span class=\"checkmark\"><i class=\"material-symbols-rounded\">check</i></span></span>\n             </div>\n             <div class=\"d-flex\">\n                 <img  src='" + contextPath + "/assets/img/pdf file.png' alt='CSV'>                 <h5 class='Body14pxSemiBold'>Format .pdf</h5>                 <p class='Body12pxRegular'>Id&eacute;al pour l&#x27impression ou le partage de rapports. Contenu non modifiable.</p>             </div>\n      </label>";
        temp = temp + "</div>";
        temp = temp + "<div class='row m-0' >";
        temp = temp + "<div class='col-md-8'>";
        temp = temp + "<label class='Body14pxRegular' for='donnee'>S&eacute;lectionnez les donn&eacute;es &agrave; exporter : </label>";
        temp = temp + "<div class='d-flex gap-2' >";
        temp = temp + "<label class=\"toggle-button-export pages\">\n   <input type=\"radio\" name=\"donnee\" value=\"0\">\n   <div class=\"checkbox\">\n     <span class=\"checkmark\"><i class=\"material-symbols-rounded\">check</i></span></span>\n   </div>\n   <div class=\"d-flex\" style='flex-direction:column' >\n     <h6 class='Body14pxSemiBold'>Page actuelle</h6>   </div>\n</label>";
        temp = temp + "<label class=\"toggle-button-export pages\">\n   <input type=\"radio\" name=\"donnee\" value=\"1\">\n   <div class=\"checkbox\">\n     <span class=\"checkmark\"><i class=\"material-symbols-rounded\">check</i></span></span>\n   </div>\n   <div class=\"d-flex\" style='flex-direction:column' >\n     <h6 class='Body14pxSemiBold'>Tous les donn&eacute;es</h6>   </div>\n</label>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<div class='col-md-4 orientation-section' id=\"orientation-section\">";
        temp = temp + "<label class='Body14pxRegular' for='donnee'>Orientation (PDF uniquement) :</label>";
        temp = temp + "<div class='d-flex gap-2' >";
        temp = temp + "<label class=\"toggle-button-export orientation\">\n  <input type=\"radio\" name=\"landscape\" value=\"0\">\n     <div class=\"checkbox d-none\">\n       <span class=\"checkmark\"><i class=\"material-symbols-rounded\">check</i></span></span>\n     </div>\n     <div class=\"d-flex\">\n        <i class='material-symbols-rounded'>crop_portrait</i>\n        <p class='Body14pxSemiBold'>Portrait</p>\n     </div>\n</label>";
        temp = temp + "<label class=\"toggle-button-export orientation\">\n  <input type=\"radio\" name=\"landscape\" value=\"1\">\n     <div class=\"checkbox d-none\">\n       <span class=\"checkmark\"><i class=\"material-symbols-rounded\">check</i></span></span>\n     </div>\n     <div class=\"d-flex\">\n        <i class='material-symbols-rounded'>crop_landscape</i>\n        <p class='Body14pxSemiBold'>Paysage</p>\n     </div>\n</label>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        if (this.getTableau().getExpxml() != null) {
            String valXml = this.getTableau().getExpxml();
            if (valXml.startsWith("null")) {
                valXml = valXml.substring(4).trim();
            }
            temp = temp + "<input type=\"hidden\" name=\"xml\" value=\"" + valXml.replace('\"', '*') + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"csv\" value=\"" + this.getTableau().getExpcsv().replace('\"', '*') + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"awhere\" value=\"" + awhere + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"table\" value=\"" + tab1.replace('\"', '*').replace("'", " ").replace("\"", " ") + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"entete\" value=\"" + Utilitaire.tabToString(this.getTableau().getLibeEntete(), "", ";") + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"entetelibelle\" value=\"" + Utilitaire.tabToString(this.getTableau().getLibelleAffiche(), "", ";") + "\" />";
        }
        if ((sommeId = Utilitaire.estIlDedans("montant", this.getColSomme())) > -1) {
            temp = temp + "<input type=\"hidden\" name=\"nombreEncours\" value=\"" + Utilitaire.formaterSansVirgule(this.rs.getSommeEtNombre()[this.getColSomme().length]) + "\" />";
            double test = this.rs.getSommeEtNombre()[sommeId];
            String testEnString = Utilitaire.enleverExponentielleDouble(test);
            temp = temp + "<input type=\"hidden\" name=\"recap\" value=\"" + testEnString + "\" />";
        }
        Gson gson = new Gson();
        String string = rawRequete = this.getCritere() != null ? this.getCritere().getRequete() : null;
        if (rawRequete == null || rawRequete.trim().isEmpty() || rawRequete.equalsIgnoreCase("null")) {
            rawRequete = "SELECT * FROM " + this.getBase().getNomTable() + " WHERE 1=1 " + (this.getAWhere() != null ? this.getAWhere() : "");
        }
        String requete = StringEscapeUtils.escapeHtml4((String)rawRequete);
        temp = temp + "<input type=\"hidden\" name=\"requete\" value=\"" + requete + "\" />";
        temp = temp + "<input type=\"hidden\" name=\"nomTable\" value=\"" + this.getBase().getNomTable() + "\" />";
        String titreExport = this.getTitre() != null && !this.getTitre().trim().isEmpty() && !this.getTitre().equalsIgnoreCase("null") ? this.getTitre().trim() : (this.getBase() != null && this.getBase().getNomTable() != null ? this.getBase().getNomTable() : "Export");
        temp = temp + "<input type=\"hidden\" name=\"titre\" value=\"" + StringEscapeUtils.escapeHtml4((String)titreExport) + "\" />";
        temp = temp + "<input type=\"hidden\" name=\"className\" value=\"" + this.getBase().getClassName() + "\" />";
        String[] stringArray = cols = this.getTableauAfficheDefaut() != null ? this.getTableauAfficheDefaut() : this.getTableauAffiche();
        if (cols == null || cols.length == 0) {
            cols = this.getTableau() != null ? this.getTableau().getLibeEntete() : null;
        }
        temp = temp + "<input type='hidden' name='colonneExcel' value='" + gson.toJson((Object)cols) + "' />";
        temp = temp + "<input type='hidden' name='libColonneExcel' value='" + gson.toJson((Object)this.getTableau().getLibelleAffiche()) + "' />";
        temp = temp + "</div>";
        return temp;
    }

    public String getBasPageTemplate() throws Exception {
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        String contextPath = this.getReq().getContextPath();
        String retour = "";
        if (this.getListe().length == 0) {
            return retour;
        }
        retour = retour + "<div class=\" pagination-bas-page \">";
        retour = retour + this.getHtmlSelectNpp();
        retour = retour + "<h3 class=\"Body12pxRegular pagination-title m-0 \" multi-lang>" + this.getNbObject() + "</h3>";
        retour = retour + this.getTableau().getPagination();
        retour = retour + "</div>";
        retour = retour + "<div class=\"modal fade\" id=\"exporter\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"filtreColonnneTitle\" aria-hidden=\"true\">\r\n  <div class=\"modal-dialog modal-dialog-centered\" role=\"document\">\r\n    <div style=\"border-radius: 16px;padding:15px;\" class=\"modal-content\">\r\n      <div class=\"modal-body\">\r\n";
        retour = retour + "<form action=\"" + contextPath + "/download\" method=\"post\">";
        retour = retour + "<div class='box cardsearch' id='pagerecherche'>   <div class=\"box-body\">\r\n";
        retour = retour + "     <h3 class='h520pxSemibold m-0' multi-lang>Exportation des donn&eacute;es</h3>\r\n";
        retour = retour + "     <h4 class='Body14pxRegular' multi-lang>S&eacute;lectionnez le type de fichier dans lequel vous souhaitez exporter vos donn&eacute;es.</h4>\r\n";
        retour = retour + this.getDownloadFormTemplate();
        retour = retour + "</div>\r\n<div class='box-footer borderless' ><input type='submit' value='T&eacute;l&eacute;charger le fichier' type='submit' class='btn btn-primary pull-right' style='margin-left:8px' /><input type='reset' value='Annuler' type='submit' class='btn btn-secondary pull-right' data-dismiss=\"modal\" /></div>    </div>\r\n <button class='btn-exit' data-dismiss=\"modal\" type='button'><i class='fa fa-close'></i></button>     </form>\r\n    </div>\r\n    </div>\r\n  </div>\r\n</div>";
        retour = retour + "<script>";
        retour = retour + "document.addEventListener('DOMContentLoaded', function() {\n    // S\u00c3\u00a9lection des diff\u00c3\u00a9rents groupes de toggle buttons\n    const formatButtons = Array.from(document.querySelectorAll('.export-card'));\n    const dataButtons = Array.from(document.querySelectorAll('.pages'));\n    const orientationButtons = Array.from(document.querySelectorAll('.orientation'));\n    const orientationSection = document.querySelector('#orientation-section'); // La section orientation\n\n    // Fonction pour activer un bouton dans un groupe\n    function activateButton(buttons, value) {\n        buttons.forEach(btn => {\n            const input = btn.querySelector('input');\n            if (input && input.value === value) {\n                btn.classList.add('active');\n                input.checked = true;\n            } else {\n                btn.classList.remove('active');\n                if (input) input.checked = false;\n            }\n        });\n    }\n\n    // Fonction pour g\u00c3\u00a9rer l'affichage de la section orientation\n    function updateOrientationVisibility() {\n        const activeFormat = formatButtons.find(b => b.classList.contains('active'));\n        if (activeFormat) {\n            const input = activeFormat.querySelector('input[name=\"ext\"]');\n            const isPDF = input && input.value === 'pdf';\n            \n            if (orientationSection) {\n                orientationSection.style.display = isPDF ? 'block' : 'none';\n            }\n        }\n    }\n\n    // Initialisation : activer les valeurs par d\u00c3\u00a9faut selon le design\n    activateButton(formatButtons, 'xml');  // Format XML par d\u00c3\u00a9faut\n    activateButton(dataButtons, '0');      // Page actuelle par d\u00c3\u00a9faut\n    activateButton(orientationButtons, '0'); // Portrait par d\u00c3\u00a9faut\n\n    // Gestion des clics sur les formats\n    formatButtons.forEach(btn => {\n        btn.addEventListener('click', function() {\n            const input = this.querySelector('input[name=\"ext\"]');\n            if (input) {\n                formatButtons.forEach(b => {\n                    b.classList.remove('active');\n                    const inp = b.querySelector('input[name=\"ext\"]');\n                    if (inp) inp.checked = false;\n                });\n                \n                this.classList.add('active');\n                input.checked = true;\n                updateOrientationVisibility();\n            }\n        });\n    });\n\n    // Gestion des clics sur les donn\u00c3\u00a9es\n    dataButtons.forEach(btn => {\n        btn.addEventListener('click', function() {\n            const input = this.querySelector('input[name=\"donnee\"]');\n            if (input) {\n                dataButtons.forEach(b => {\n                    b.classList.remove('active');\n                    const inp = b.querySelector('input[name=\"donnee\"]');\n                    if (inp) inp.checked = false;\n                });\n                \n                this.classList.add('active');\n                input.checked = true;\n            }\n        });\n    });\n\n    // Gestion des clics sur l'orientation\n    orientationButtons.forEach(btn => {\n        btn.addEventListener('click', function() {\n            const input = this.querySelector('input[name=\"landscape\"]');\n            if (input) {\n                orientationButtons.forEach(b => {\n                    b.classList.remove('active');\n                    const inp = b.querySelector('input[name=\"landscape\"]');\n                    if (inp) inp.checked = false;\n                });\n                \n                this.classList.add('active');\n                input.checked = true;\n            }\n        });\n    });\n\n    // Appel initial pour g\u00c3\u00a9rer la visibilit\u00c3\u00a9 de l'orientation\n    updateOrientationVisibility();\n});";
        retour = retour + "</script>";
        return retour;
    }

    public String getBasPageOnglet() throws Exception {
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        String retour = "";
        retour = retour + "<div style=\"padding: 5px 0px 10px 0px;\">";
        retour = retour + "<div class=\"col-md-12 page-page-container onglet \">";
        retour = retour + "<div class=\" pagination-bas-page col-offset-md-6 \">";
        retour = retour + "<h3 class=\"box-title cardtitle fontinter\" align=\"left\"> <span style=\"color: #686868;font-size: 18px;font-weight: 400;font-family: 'Inter', sans-serif;display:flex;align-items:center;\">" + this.getNbObject() + "</span></h3>";
        retour = retour + this.getTableau().getPagination();
        retour = retour + "</div>";
        retour = retour + "</div>";
        retour = retour + "</div>";
        return retour;
    }

    public String getBasPagee() {
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        String retour = "";
        retour = retour + "<table border=0 cellpadding=0 cellspacing=0 align=center width='100%'>";
        retour = retour + "<tr><td height=25><b>" + RB.getString("nbresultat") + " :</b> " + Utilitaire.formaterAr(String.valueOf(this.rs.getSommeEtNombre()[this.getNombreColonneSomme()])) + "</td><td align='right'><strong>page</strong> " + this.getNumPage() + " <b>sur</b> " + this.getNombrePage() + "</td>";
        retour = retour + "</tr>";
        retour = retour + "<tr>";
        retour = retour + "<td width=50% valign='top' height=25>";
        if (this.getNumPage() > 1) {
            retour = retour + "<a href=" + this.getApres() + "?a=1" + this.formu.getListeCritereString() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNumPage() - 1) + this.getNppLien() + this.getApresLienPage() + "&champReturn=" + this.getReq().getParameter("champReturn") + ">&lt;&lt;" + RB.getString("pageprecedente") + "</a>";
        }
        retour = retour + "</td>";
        retour = retour + "<td width=50% align=right>";
        if (this.getNumPage() < this.getNombrePage()) {
            retour = retour + "<a href=" + this.getApres() + "?a=1" + this.formu.getListeCritereString() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNumPage() + 1) + this.getNppLien() + this.getApresLienPage() + "&champReturn=" + this.getReq().getParameter("champReturn") + ">" + RB.getString("pagesuivante") + "&gt;&gt;</a>";
        }
        retour = retour + "</td>";
        retour = retour + "</tr>";
        retour = retour + "</table>";
        return retour;
    }

    public void setApres(String apres) {
        this.apres = apres;
    }

    public String getApres() {
        if (this.apres == null || this.apres.isEmpty()) {
            this.apres = this.getReq().getQueryString().split("&")[0].split("=")[1];
        }
        return this.apres;
    }

    public String getAWhere() {
        return this.aWhere;
    }

    public void setAWhere(String aWhere) {
        this.aWhere = aWhere;
    }

    public String getMultipleAwhere() {
        return this.multipleAwhere;
    }

    public void setMultipleAwhere(String multipleAwhere) {
        this.multipleAwhere = multipleAwhere;
    }

    public void setApresLienPage(String apresLienPage) {
        this.apresLienPage = apresLienPage;
    }

    public String getApresLienPage() {
        return this.apresLienPage;
    }

    public void setNpp(int npp) {
        this.nppParDefautJsp = Math.max(npp, 0);
        this.synchroniserNppDansFormulaire();
    }

    public int getNpp() {
        return this.npp;
    }

    void appliquerNppDepuisRequete() {
        String valeurDemandee;
        this.npp = this.getNppParDefaut();
        if (this.getReq() != null && (valeurDemandee = this.getReq().getParameter(NPP_PARAMETRE)) != null && !valeurDemandee.trim().isEmpty()) {
            try {
                int valeur = Integer.parseInt(valeurDemandee.trim());
                if (PageRecherche.estChoixNppStandard(valeur) || valeur == this.getNppParDefaut()) {
                    this.npp = valeur;
                }
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                throw new RuntimeException("Erreur appliquerNppDepuisRequete: " + e.getMessage());
            }
        }
        this.synchroniserNppDansFormulaire();
    }

    private void synchroniserNppDansFormulaire() {
        if (this.formu != null) {
            this.formu.setHtmlChampCacheSupplementaire("<input type=\"hidden\" name=\"npp\" value=\"" + this.getValeurNppParametre() + "\" data-apj-npp-hidden>");
        }
    }

    private static boolean estChoixNppStandard(int valeur) {
        for (int choix : NPP_CHOIX) {
            if (choix != valeur) continue;
            return true;
        }
        return false;
    }

    private int getNppParDefaut() {
        return this.nppParDefautJsp;
    }

    int getNppEffectif() {
        return this.npp > 0 ? this.npp : this.getNppParDefaut();
    }

    private String getValeurNppParametre() {
        return String.valueOf(this.getNppEffectif());
    }

    private String getNppLien() {
        return "&npp=" + this.getValeurNppParametre();
    }

    private String getLienChangementNpp() {
        return this.getLien() + "?but=" + this.getApres() + "&recap=" + this.getFormu().getRecapcheck() + "&premier=" + this.getPremier() + "&numPag=1" + this.getApresLienPage() + this.formu.getListeCritereStringCheckbox(this);
    }

    String getHtmlSelectNpp() {
        LinkedHashSet<Integer> choix = new LinkedHashSet<Integer>();
        int nppParDefaut = this.getNppParDefaut();
        if (!PageRecherche.estChoixNppStandard(nppParDefaut)) {
            choix.add(nppParDefaut);
        }
        for (int valeur : NPP_CHOIX) {
            choix.add(valeur);
        }
        String valeurSelectionnee = this.getValeurNppParametre();
        String lien = StringEscapeUtils.escapeHtml4((String)this.getLienChangementNpp());
        StringBuilder html = new StringBuilder();
        html.append("<label class=\"apj-npp-control\" data-apj-npp-control>").append("<span class=\"apj-npp-label\" multi-lang>R&eacute;sultats par page</span>").append("<select class=\"apj-npp-select\" name=\"npp\" aria-label=\"Nombre de r&eacute;sultats par page\" data-apj-npp-url=\"").append(lien).append("\" onchange=\"window.location.href=this.getAttribute('data-apj-npp-url')+'&amp;npp='+encodeURIComponent(this.value)\">");
        for (Integer valeur : choix) {
            String valeurTexte = String.valueOf(valeur);
            html.append("<option value=\"").append(valeurTexte).append("\"");
            if (valeurTexte.equals(valeurSelectionnee)) {
                html.append(" selected=\"selected\"");
            }
            html.append(">").append(valeurTexte).append("</option>");
        }
        html.append("</select></label>");
        return html.toString();
    }

    public void setTableauAffiche(String[] tableauAffiche) {
        this.tableauAffiche = tableauAffiche;
    }

    public int getNbTableauAffiche() {
        return this.nbTableauAffiche;
    }

    public void setNbTableauAffiche(int nbTableauAffiche) {
        this.nbTableauAffiche = nbTableauAffiche;
    }

    public String getDownloadForm() throws Exception {
        int sommeId;
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        String contextPath = this.getReq().getContextPath();
        String tab = "";
        String tab1 = "";
        try {
            tab = this.getTableau().getHtml();
            tab1 = this.getTableau().getHtmlPDF();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String awhere = " AND " + CGenUtil.makeWhere(this.getBase()) + CGenUtil.makeWhereIntervalle(this.colInt, this.valInt) + this.getAWhere().replace('\"', '\'');
        String temp = "";
        temp = temp + "<div class='row'>";
        temp = temp + "<div class='row col-md-12'>";
        temp = temp + "<div class='box box-solid '>";
        temp = temp + "<div style='background-color:" + ConstanteAffichage.couleurPrimaire + "; color:white;'class='box-header with-border'>";
        temp = temp + "<h3 class='box-title'>" + RB.getString("exporter") + "</h3>";
        temp = temp + "<div class='box-tools pull-right'><button data-original-title='Collapse' class='btn btn-box-tool' data-widget='collapse' data-toggle='tooltip' title=''><i class='fa fa-plus'></i></button> </div>";
        temp = temp + "</div>";
        temp = temp + "<div class='box-body'>";
        temp = temp + "<form action=\"" + contextPath + "/download\" method=\"post\">";
        temp = temp + "<table id='export'>";
        temp = temp + "<tr>";
        temp = temp + "<th colspan='4'><h3 class='box-title'>" + RB.getString("format") + "</h3></th>";
        temp = temp + "<th colspan='2'><h3 class='box-title'>" + RB.getString("donneeexport") + "</h3></th>";
        temp = temp + "<th colspan='2'><h3 class='box-title'><strong>LANDSCAPE</strong>(pour pdf)</h3></th>";
        temp = temp + "<th></th>";
        temp = temp + "</tr>";
        temp = temp + "<tr>";
        temp = temp + "<td>";
        temp = temp + "<img src='" + contextPath + "/dist/img/csv_text.png'>";
        temp = temp + "<input type='radio' name='ext' value='csv' checked='checked' />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        temp = temp + "<img src='" + contextPath + "/dist/img/file_xls.png'>";
        temp = temp + "<input type='radio' name='ext' value='xls' />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        temp = temp + "<img src='" + contextPath + "/dist/img/file_xml.png'>";
        temp = temp + "<input type='radio' name='ext' value='xml' />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        temp = temp + "<img src='" + contextPath + "/dist/img/file_pdf.png'>";
        temp = temp + "<input type='radio' name='ext' value='pdf' />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        temp = temp + "<label for='donnee'>" + RB.getString("pageencours") + " </label>";
        temp = temp + "<input type='radio' name='donnee' value=\"0\" checked='checked' />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        temp = temp + "<label for='donnee'>" + RB.getString("toutes") + " </label>";
        temp = temp + "<input type='radio' name='donnee' value=\"1\" />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        temp = temp + "<label for='landscape'>Portrait</label>";
        temp = temp + "<input type='radio' name='landscape' value=\"0\" checked='checked' />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        temp = temp + "<label for='landscape'>Paysage</label>";
        temp = temp + "<input type='radio' name='landscape' value=\"1\" />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        if (this.getTableau().getExpxml() != null) {
            String valXml = this.getTableau().getExpxml();
            if (valXml.startsWith("null")) {
                valXml = valXml.substring(4).trim();
            }
            temp = temp + "<input type=\"hidden\" name=\"xml\" value=\"" + valXml.replace('\"', '*') + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"csv\" value=\"" + this.getTableau().getExpcsv().replace('\"', '*') + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"awhere\" value=\"" + awhere + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"table\" value=\"" + tab1.replace('\"', '*').replace("'", " ").replace("\"", " ") + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"entete\" value=\"" + Utilitaire.tabToString(this.getTableau().getLibeEntete(), "", ";") + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"entetelibelle\" value=\"" + Utilitaire.tabToString(this.getTableau().getLibelleAffiche(), "", ";") + "\" />";
        }
        if ((sommeId = Utilitaire.estIlDedans("montant", this.getColSomme())) > -1) {
            temp = temp + "<input type=\"hidden\" name=\"nombreEncours\" value=\"" + Utilitaire.formaterSansVirgule(this.rs.getSommeEtNombre()[this.getColSomme().length]) + "\" />";
            double test = this.rs.getSommeEtNombre()[sommeId];
            String testEnString = Utilitaire.enleverExponentielleDouble(test);
            temp = temp + "<input type=\"hidden\" name=\"recap\" value=\"" + testEnString + "\" />";
        }
        Gson gson = new Gson();
        String requete = StringEscapeUtils.escapeHtml4((String)this.getCritere().getRequete());
        temp = temp + "<input type=\"hidden\" name=\"requete\" value=\"" + requete + "\" />";
        temp = temp + "<input type=\"hidden\" name=\"nomTable\" value=\"" + this.getBase().getNomTable() + "\" />";
        temp = temp + "<input type=\"hidden\" name=\"titre\" value=\"" + this.getTitre() + "\" />";
        temp = temp + "<input type=\"hidden\" name=\"className\" value=\"" + this.getBase().getClassName() + "\" />";
        temp = temp + "<input type='hidden' name='colonneExcel' value='" + gson.toJson((Object)this.getTableauAfficheDefaut()) + "' />";
        temp = temp + "<input type='hidden' name='libColonneExcel' value='" + gson.toJson((Object)this.getTableau().getLibelleAffiche()) + "' />";
        temp = temp + "<input type='submit' value='" + RB.getString("exporter") + "' class='btn btn-default' />";
        temp = temp + "<input type='hidden' name='titre' value='" + this.getTitre() + "' />";
        temp = temp + "</td>";
        temp = temp + "</tr>";
        temp = temp + "</table>";
        temp = temp + "</form>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        return temp;
    }

    public String[] getTableauAffiche() {
        return this.tableauAfficheDefaut;
    }

    public String[] getTableauAfficheDefaut() {
        return this.tableauAfficheDefaut;
    }

    public void setTableauAfficheDefaut(String[] tableauAfficheDefaut) {
        this.tableauAfficheDefaut = tableauAfficheDefaut;
    }

    public String getCritereHttp() {
        return this.critereHttp;
    }

    public void setCritereHttp(String critereHttp) {
        this.critereHttp = critereHttp;
    }

    public PageRecherche(ClassMAPTable o, HttpServletRequest req, String[] vrt, String[] listInterval, int nbRange, String[] tabAff) throws Exception {
        this.setBase(o);
        this.setCritere((ClassMAPTable)Class.forName(o.getClassName()).newInstance());
        this.setReq(req);
        if (this.getReq().getParameter("premier") != null && this.getReq().getParameter("premier").compareToIgnoreCase("") != 0) {
            this.setPremier(Boolean.valueOf(this.getReq().getParameter("premier")));
        }
        this.formu = new Formulaire();
        this.formu.setObjet(this.getBase());
        this.formu.makeChampFormu(vrt, listInterval, nbRange);
        this.setNbTableauAffiche(tabAff.length);
        this.setTableauAfficheDefaut(tabAff);
        this.makeTableauAffiche();
        this.formu.makeChampTableauAffiche(this.getNbTableauAffiche(), tabAff);
    }

    public String getPagination() throws Exception {
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        String formLien = this.getApres() + this.getCritereHttp();
        String retour = "";
        int maxPageAffiche = 2;
        int startPage = Math.max(1, this.getNumPage() - maxPageAffiche);
        int endPage = Math.min(this.getNombrePage(), this.getNumPage() + maxPageAffiche);
        retour = retour + "<div class=\"pagination d-flex justify-content-between align-items-center \">\n                <div class=\"number \" multi-lang>\n                    " + this.getListe().length + " r&eacute;sultats sur " + Utilitaire.formaterSansVirgule(this.rs.getSommeEtNombre()[this.getNombreColonneSomme()]) + "\n                </div>\n                <div class=\"page d-flex justify-content-around \">\n";
        if (this.getNumPage() > 1) {
            retour = retour + "<div class=\"btn pagination-apj nav-btn \"><a href=" + this.getLien() + "?but=" + formLien + "&recap=" + this.getFormu().getRecapcheck() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNumPage() - 1) + this.getNppLien() + this.getApresLienPage() + this.formu.getListeCritereString() + "><i class=\"fas fa-chevron-left\"></i></a></div>\n";
        }
        if (startPage > 1) {
            retour = retour + "<div class=\"btn pagination-apj\"><a href=" + this.getLien() + "?but=" + formLien + "&recap=" + this.getFormu().getRecapcheck() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf("1") + this.getNppLien() + this.getApresLienPage() + this.formu.getListeCritereString() + ">1</a></div>\n";
            retour = retour + "<div class=\"btn pagination-apj \">...</div>\n";
        }
        for (int i = startPage; i <= endPage; ++i) {
            String current = this.getNumPage() == i ? "current-page" : "";
            retour = retour + "<div class=\"btn pagination-apj " + current + "\"><a href=" + this.getLien() + "?but=" + formLien + "&recap=" + this.getFormu().getRecapcheck() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(i) + this.getNppLien() + this.getApresLienPage() + this.formu.getListeCritereString() + ">" + i + "</a></div>\n";
        }
        if (endPage < this.getNombrePage()) {
            retour = retour + "<div class=\"btn pagination-apj \">...</div>\n";
            retour = retour + "<div class=\"btn pagination-apj \"><a href=" + this.getLien() + "?but=" + formLien + "&recap=" + this.getFormu().getRecapcheck() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNombrePage()) + this.getNppLien() + this.getApresLienPage() + this.formu.getListeCritereString() + ">" + this.getNombrePage() + "</a></div>\n";
        }
        if (this.getNumPage() < this.getNombrePage()) {
            retour = retour + "<div class=\"btn pagination-apj nav-btn \"><a href=" + this.getLien() + "?but=" + formLien + "&recap=" + this.getFormu().getRecapcheck() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNumPage() + 1) + this.getNppLien() + this.getApresLienPage() + this.formu.getListeCritereString() + "><i class=\"fas fa-chevron-right\"></i></a></div>\n";
        }
        retour = retour + "</div></div>";
        return retour;
    }

    public String getBouttonExport() throws Exception {
        int sommeId;
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        String contextPath = this.getReq().getContextPath();
        String tab = "";
        String tab1 = "";
        try {
            tab1 = this.getTableau().getHtmlPDF();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String awhere = " AND " + CGenUtil.makeWhere(this.getBase()) + CGenUtil.makeWhereIntervalle(this.colInt, this.valInt) + this.getAWhere().replace('\"', '\'');
        String temp = "";
        temp = temp + "<div id='sourceDiv' class=\"export-block inactive\">\n                    <h4 multi-lang>Exporter les donn&eacute;es</h4>\n                    <hr>\n                       <form action=\"" + contextPath + "/download\" method=\"post\">                    <div class=\"form-group\">\n                        <label for=\"\" multi-lang>Format</label>\n                        <select name='ext' id='ext' class=\"form-control\">\n                           <option value='csv' style=\"background-image: url('" + contextPath + "/dist/img/csv_text.png')\" selected><img src='" + contextPath + "/dist/img/csv_text.png' alt=\"logo\">CSV</option>\n                           <option value='xls' style=\"background-image: url('" + contextPath + "/dist/img/file_xls.png')\"><img src='" + contextPath + "/dist/img/file_xls.png' alt=\"logo\">XLS</option>\n                           <option value='xml' style=\"background-image: url('" + contextPath + "/dist/img/file_xml.png')\"><img src='" + contextPath + "/dist/img/file_xml.png' alt=\"logo\">XML</option>\n                           <option value='pdf' style=\"background-image: url('" + contextPath + "/dist/img/file_pdf.png')\"><img src='" + contextPath + "/dist/img/file_pdf.png' alt=\"logo\">PDF</option>\n                        </select>\n                    </div>\n                    <div class=\"form-group\">\n                        <label for=\"\" multi-lang>Donn&eacute;es &agrave; exporter</label>\n                        <select name='donnee' id='donnee' class=\"form-control\">\n                           <option value='0' selected multi-lang>" + RB.getString("pageencours") + "</option>\n                           <option value='1' multi-lang>" + RB.getString("toutes") + "</option>\n                       </select>\n                    </div>\n                    <div class=\"form-group\">\n                        <label for=\"\" multi-lang>Orientation</label>\n                    </div>\n                    <select name='landscape' id='landscape' class=\"form-control\">\n                        <option value='0' multi-lang>Portrait</option>\n                        <option value='1' multi-lang>Paysage</option>\n                    </select>\n";
        if (this.getTableau().getExpxml() != null) {
            String valXml = this.getTableau().getExpxml();
            if (valXml.startsWith("null")) {
                valXml = valXml.substring(4).trim();
            }
            temp = temp + "<input type=\"hidden\" name=\"xml\" value=\"" + valXml.replace('\"', '*') + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"csv\" value=\"" + this.getTableau().getExpcsv().replace('\"', '*') + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"awhere\" value=\"" + awhere + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"table\" value=\"" + tab1.replace('\"', '*').replace("'", " ").replace("\"", " ") + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"entete\" value=\"" + Utilitaire.tabToString(this.getTableau().getLibeEntete(), "", ";") + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"entetelibelle\" value=\"" + Utilitaire.tabToString(this.getTableau().getLibelleAffiche(), "", ";") + "\" />";
        }
        if ((sommeId = Utilitaire.estIlDedans("montant", this.getColSomme())) > -1) {
            temp = temp + "<input type=\"hidden\" name=\"nombreEncours\" value=\"" + Utilitaire.formaterSansVirgule(this.rs.getSommeEtNombre()[this.getColSomme().length]) + "\" />";
            double test = this.rs.getSommeEtNombre()[sommeId];
            String testEnString = Utilitaire.enleverExponentielleDouble(test);
            temp = temp + "<input type=\"hidden\" name=\"recap\" value=\"" + testEnString + "\" />";
        }
        Gson gson = new Gson();
        String requete = StringEscapeUtils.escapeHtml4((String)this.getCritere().getRequete());
        temp = temp + "<input type=\"hidden\" name=\"requete\" value=\"" + requete + "\" />";
        temp = temp + "<input type=\"hidden\" name=\"nomTable\" value=\"" + this.getBase().getNomTable() + "\" />";
        temp = temp + "<input type=\"hidden\" name=\"titre\" value=\"" + this.getTitre() + "\" />";
        temp = temp + "<input type=\"hidden\" name=\"className\" value=\"" + this.getBase().getClassName() + "\" />";
        temp = temp + "<input type='hidden' name='colonneExcel' value='" + gson.toJson((Object)this.getTableauAfficheDefaut()) + "' />";
        temp = temp + "<input type='hidden' name='libColonneExcel' value='" + gson.toJson((Object)this.getTableau().getLibelleAffiche()) + "' />";
        temp = temp + "<div class=\"form-group d-flex mt-2 justify-content-center justify-items-center\">";
        temp = temp + "<input type='submit' value='" + RB.getString("exporter") + "' class='btn btn-search' />";
        temp = temp + "<input type='hidden' name='titre' value='" + this.getTitre() + "' />";
        temp = temp + "</div></form></div>";
        return temp;
    }

    public String getHtmlEnsemble() throws Exception {
        String temp = "";
        temp = temp + "<div class=\"result mt-5 \">";
        temp = temp + "<div class=\"export-container mb-1\"><div class=\"btn btn-export \" onclick=\"exprt()\">\n                    <i class=\"fas fa-file-export\"></i> <span multi-lang>Exporter</span>\n                </div>";
        temp = temp + "<div id='destinationDiv'></div></div>";
        temp = temp + this.getTableau().getHtmlAvecOrdre();
        temp = temp + this.getPagination();
        temp = temp + this.getBouttonExport();
        temp = temp + "</div>";
        return temp;
    }

    public static void creerObjetPage(PageRecherche[] pr) throws Exception {
        try (Connection con = new UtilDB().GetConn();){
            for (PageRecherche p : pr) {
                p.creerObjetPage(new String[0], null, con);
            }
        }
    }

    public void setFormatTitre(String formatTitre) {
        this.formatTitre = formatTitre;
    }

    public String getFormatTitre() {
        return this.formatTitre;
    }

    public void setCssBoxExtraMobile(String cssBoxExtraMobile) {
        this.cssBoxExtraMobile = cssBoxExtraMobile;
    }

    public String getCssBoxExtraMobile() {
        return this.cssBoxExtraMobile;
    }

    public String getHtmlSearchMobile() {
        return "    <div class=\"mobile__search-container mb-5\">\n        <div class=\"input-with-addons with-btn-left\">\n            <div class=\"btn btn-left\">\n                <i class=\"material-symbols-rounded\">search</i>\n            </div>\n            <input type=\"text\" class=\"form-control mobile__search-box\" />\n        </div>\n    </div>\n";
    }

    public String getHtmlInterfaceMobile() throws Exception {
        if (Utilitaire.champNull(this.htmlMobile).isEmpty()) {
            this.makeHtmlInterfaceMobile();
        }
        return Utilitaire.champNull(this.htmlMobile).isEmpty() ? "<h2 class='no-data-msg' style='margin-left:0'>Aucune donn\u00e9e disponible</h2>" : this.htmlMobile;
    }

    private void makeHtmlInterfaceMobile() throws Exception {
        ClassMAPTable[] liste = this.getListe();
        if (liste == null || liste.length == 0) {
            this.htmlMobile = "";
            return;
        }
        StringBuilder temp = new StringBuilder();
        temp.append("<div class=\"search-result-container\">");
        for (ClassMAPTable objet : liste) {
            int j;
            String classeBox = "search-result__box" + (Utilitaire.champNull(this.getCssBoxExtraMobile()).isEmpty() ? "client-box" : " " + this.getCssBoxExtraMobile());
            String titreMobile = this.construireTitreMobile(objet);
            StringBuilder recherche = new StringBuilder(Utilitaire.champNull(titreMobile));
            String[] valeursAffichees = null;
            if (this.getTableauAfficheDefaut() != null) {
                valeursAffichees = new String[this.getTableauAfficheDefaut().length];
                for (j = 0; j < this.getTableauAfficheDefaut().length; ++j) {
                    valeursAffichees[j] = this.formaterValeurChampMobile(objet, this.getTableauAfficheDefaut()[j]);
                    recherche.append(" ").append(Utilitaire.champNull(valeursAffichees[j]));
                }
            }
            temp.append("<div class=\"").append(classeBox).append("\" data-search-value=\"").append(Utilitaire.champNull(recherche.toString()).replace("\"", "&quot;")).append("\">");
            if (this.getImageDefaut() != null) {
                String valeurImage = this.formaterValeurChampMobile(objet, "image");
                valeurImage = !Utilitaire.champNull(valeurImage).isEmpty() ? "/dossier/async/" + valeurImage : this.getImageDefaut();
                temp.append("<img src=\"").append(valeurImage).append("\" alt=\"item\" class=\"search-result__image\" />");
            }
            temp.append("<div class=\"search-result__details\">");
            temp.append("<h3 class=\"h617pxSemibold search-result__title m-0\">").append(this.construireTitreMobile(objet)).append("</h3>");
            if (this.getTableauAfficheDefaut() != null) {
                for (j = 0; j < this.getTableauAfficheDefaut().length; ++j) {
                    String valeur = this.formaterValeurChampMobile(objet, this.getTableauAfficheDefaut()[j]);
                    temp.append("<p class=\"Body14pxRegular m-0\">");
                    if (this.isVisibleColMobile()) {
                        temp.append(this.getTableau().getLibelleAffiche()[j]);
                        temp.append(": ");
                    }
                    temp.append("<span>").append(Utilitaire.champNull(valeur)).append("</span></p>");
                }
            }
            temp.append("</div>");
            String lien = this.construireLienCarteMobile(objet);
            if (lien != null) {
                temp.append("<a href=\"").append(lien).append("\" class=\"search-result__link\">");
                temp.append("<i class=\"material-symbols-rounded\">arrow_forward_ios</i>");
                temp.append("</a>");
            }
            temp.append("</div>");
        }
        temp.append("</div>");
        this.htmlMobile = temp.toString();
    }

    private String construireTitreMobile(ClassMAPTable objet) throws Exception {
        if (this.getFormatTitre() == null) {
            return "";
        }
        StringBuilder titre = new StringBuilder();
        String format = this.getFormatTitre();
        int i = 0;
        while (i < format.length()) {
            int debut = format.indexOf(PLACEHOLDER_DEBUT_MOBILE, i);
            if (debut == -1) {
                titre.append(format.substring(i));
                break;
            }
            titre.append(format.substring(i, debut));
            int fin = format.indexOf(PLACEHOLDER_FIN_MOBILE, debut);
            if (fin == -1) {
                titre.append(format.substring(debut));
                break;
            }
            String nomChamp = format.substring(debut + 1, fin);
            Object valeur = CGenUtil.getValeurFieldByMethod(objet, nomChamp);
            titre.append(valeur == null ? "" : String.valueOf(valeur));
            i = fin + 1;
        }
        return titre.toString();
    }

    private String formaterValeurChampMobile(ClassMAPTable objet, String nomChamp) throws Exception {
        bean.Champ c = ListeColonneTable.getChamp(objet, nomChamp);
        Object valeurC = CGenUtil.getValeurFieldByMethod(objet, nomChamp);
        if (valeurC == null && c == null) {
            return "";
        }
        if (valeurC != null && c == null) {
            if (valeurC instanceof Number) {
                return Utilitaire.formaterAr(String.valueOf(valeurC));
            }
            return String.valueOf(valeurC);
        }
        if (c.getTypeJava().compareToIgnoreCase("double") == 0) {
            return Utilitaire.formaterAr(String.valueOf(valeurC));
        }
        if (c.getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
            return Utilitaire.formatterDaty((Date)valeurC);
        }
        return String.valueOf(valeurC);
    }

    private String construireLienCarteMobile(ClassMAPTable objet) throws Exception {
        String[] lien = this.getTableau().getLien();
        String[] colonneLien = this.getTableau().getColonneLien();
        String[] attLien = this.getTableau().getAttLien();
        if (lien == null || lien.length == 0) {
            return null;
        }
        if (colonneLien == null || colonneLien.length == 0) {
            return lien[0];
        }
        Object valeur = CGenUtil.getValeurFieldByMethod(objet, colonneLien[0]);
        String att = colonneLien[0];
        if (attLien != null && attLien.length > 0) {
            att = attLien[0];
        }
        return lien[0] + "&" + att + "=" + (valeur == null ? "" : String.valueOf(valeur));
    }
}

