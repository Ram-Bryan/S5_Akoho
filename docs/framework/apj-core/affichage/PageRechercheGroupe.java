/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.lang3.StringEscapeUtils
 */
package affichage;

import affichage.Champ;
import affichage.Formulaire;
import affichage.Liste;
import affichage.Page;
import affichage.PageInsert;
import affichage.PageRecherche;
import affichage.TableauRecherche;
import affichage.TableauRechercheGroupe;
import bean.AnalyseLien;
import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import bean.ResultatEtSomme;
import bean.ValeurEtiquette;
import com.google.gson.Gson;
import constante.ConstanteAffichage;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringEscapeUtils;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class PageRechercheGroupe
extends Page {
    private TableauRecherche listeTabDet;
    private ClassMAPTable[] liste;
    private ClassMAPTable critere;
    private String[] colInt;
    private String[] valInt;
    private ResultatEtSomme rs;
    private int numPage = 1;
    private String[] colSomme;
    private String apres;
    private String aWhere = "";
    private String apresLienPage = "";
    private String[] colGroupe;
    private String[] sommeGroupe;
    private String ordre = "";
    private int nbColGroupe = 2;
    private int nbLigneGroupe = 2;
    private int nbSommeGrp = 2;
    private String[] colGroupeDefaut;
    private String[] sommeGroupeDefaut;
    private int npp = 0;
    private String[] listeColonneMoyenne;
    private String[] pourcentage;
    private double[][] pourc;
    private boolean premier = false;
    private Liste count;
    private PageInsert insertLienAnalyse;
    private String[] ligneGroupe;
    private ClassMAPTable[] dataM1;
    String nbObject;
    private boolean tableauRecapDirecte = false;
    private LinkedHashMap<String, String[]> rubriquesAnalyse;
    private String[] colonnesSommeAutorisees;
    private String[] colonnesDnDVisibles;
    private String identifiantFormulaire = "analyse";
    private boolean afficherDnDAutomatique = true;

    public LinkedHashMap<String, String[]> getRubriquesAnalyse() {
        return this.rubriquesAnalyse;
    }

    public void setRubriquesAnalyse(LinkedHashMap<String, String[]> rubriquesAnalyse) {
        this.rubriquesAnalyse = rubriquesAnalyse;
    }

    public String[] getColonnesSommeAutorisees() {
        return this.colonnesSommeAutorisees;
    }

    public void setColonnesSommeAutorisees(String[] colonnesSommeAutorisees) {
        this.colonnesSommeAutorisees = colonnesSommeAutorisees;
    }

    public String[] getColonnesDnDVisibles() {
        return this.colonnesDnDVisibles;
    }

    public void setColonnesDnDVisibles(String[] colonnesDnDVisibles) {
        this.colonnesDnDVisibles = colonnesDnDVisibles;
    }

    public String getIdentifiantFormulaire() {
        return this.identifiantFormulaire;
    }

    public void setIdentifiantFormulaire(String identifiantFormulaire) {
        this.identifiantFormulaire = identifiantFormulaire;
    }

    public boolean isAfficherDnDAutomatique() {
        return this.afficherDnDAutomatique;
    }

    public void setAfficherDnDAutomatique(boolean afficherDnDAutomatique) {
        this.afficherDnDAutomatique = afficherDnDAutomatique;
    }

    public PageRechercheGroupe() {
    }

    public ClassMAPTable[] getDataM1() {
        return this.dataM1;
    }

    public void setDataM1(ClassMAPTable[] dataM1) {
        this.dataM1 = dataM1;
    }

    public PageInsert getInsertLienAnalyse() {
        return this.insertLienAnalyse;
    }

    public void setInsertLienAnalyse(PageInsert insertLienAnalyse) {
        this.insertLienAnalyse = insertLienAnalyse;
    }

    public String getNbObject() {
        return this.nbObject == null ? "" : this.nbObject;
    }

    public void setNbObject(String nbObject) {
        this.nbObject = nbObject;
    }

    public void preparerDataList(Connection c) throws Exception {
        this.critere.setNomTable(this.getBase().getNomTable());
        if (this.getSommeGroupe() != null) {
            bean.Champ[] champs = ListeColonneTable.getFromListe(this.getBase(), null);
            HashMap<String, Object> champMap = new HashMap<String, Object>();
            if (champs != null) {
                for (bean.Champ ch : champs) {
                    if (ch == null || ch.getNomColonne() == null) continue;
                    champMap.put(ch.getNomColonne().toLowerCase(Locale.ROOT), ch);
                }
            }
            for (String sCol : this.getSommeGroupe()) {
                bean.Champ ch;
                if (sCol == null || sCol.trim().isEmpty() || sCol.equals("-") || (ch = (bean.Champ)champMap.get(sCol.trim().toLowerCase(Locale.ROOT))) == null || Utilitaire.isChampNumerique(ch)) continue;
                String libelle = ch.getLibelleAfficheAnnotation();
                throw new Exception("Erreur durant la recherche group\u00c3\u00a9e : Impossible de sommer le champ non num\u00c3\u00a9rique '" + libelle + "'.");
            }
        }
        this.makeCritere();
        if (this.premier) {
            this.rs = new ResultatEtSomme();
            this.rs.initialise(this.getColSomme());
        } else {
            String req = this.getAWhere();
            String motsClesss = this.getReq().getParameter("motsclesss");
            if (ListeColonneTable.getChamp(this.getBase(), "motsClesss") != null && motsClesss != null && motsClesss.compareToIgnoreCase("") != 0 && motsClesss.compareToIgnoreCase("null") != 0) {
                req = " and upper(motsclesss) like upper('%" + motsClesss + "%')" + req;
                this.critere.setMotsClesss(motsClesss);
            } else {
                req = PageRecherche.construireClauseMotsCles(this.getBase(), null, motsClesss) + req;
                if (motsClesss != null && !motsClesss.trim().isEmpty()) {
                    this.critere.setMotsClesss(motsClesss);
                }
            }
            this.rs = this.getUtilisateur().getDataPageGroupe(this.critere, this.getColGroupe(), this.getSommeGroupe(), this.getColInt(), this.getValInt(), this.getNumPage(), req, this.getColSomme(), this.getOrdre(), c, this.npp);
        }
        ClassMAPTable[] listeVal = (ClassMAPTable[])this.rs.getResultat();
        this.setRs(this.rs);
        this.setListe(listeVal);
    }

    public void calculPourcentage(ResultatEtSomme rs, ClassMAPTable[] listeVal, Connection c) throws Exception {
        ClassMAPTable[] liste = (ClassMAPTable[])CGenUtil.rechercher(this.getBase(), null, null, c, "");
        this.pourc = new double[this.getPourcentage().length][listeVal.length];
        for (int iCol = 0; iCol < this.getPourcentage().length; ++iCol) {
            for (int iLigne = 0; iLigne < listeVal.length; ++iLigne) {
                double valeur = Double.parseDouble(CGenUtil.getValeurFieldByMethod(listeVal[iLigne], this.getPourcentage()[iCol]).toString());
                if (this.getSommeGroupe().length != 0 && this.getPourcentage()[iCol].compareToIgnoreCase("nombrepargroupe") != 0) {
                    int k = 0;
                    for (k = 0; k < this.getSommeGroupe().length && this.getSommeGroupe()[k].compareTo(this.getPourcentage()[iCol]) != 0; ++k) {
                    }
                    this.pourc[iCol][iLigne] = valeur * 100.0 / rs.getSommeEtNombre()[k];
                    continue;
                }
                this.pourc[iCol][iLigne] = valeur * 100.0 / (double)liste.length;
            }
        }
    }

    public void preparerDataFormu() throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            this.formu.getAllData(c);
        }
    }

    public void preparerData() throws Exception {
        this.preparerData(null);
    }

    public void preparerData(Connection con) throws Exception {
        boolean estOuvert = false;
        Connection c = null;
        if (con != null) {
            c = con;
            estOuvert = true;
        }
        try {
            if (!estOuvert) {
                c = new UtilDB().GetConn();
            }
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
        for (int i = 0; i < tempChamp.length; ++i) {
            boolean recherchePrecis;
            Field f = CGenUtil.getField(this.getBase(), tempChamp[i].getNom());
            Champ champConfigure = this.formu.getChamp(tempChamp[i].getNom());
            boolean bl = recherchePrecis = champConfigure != null && champConfigure.isRecherchePrecis();
            if (tempChamp[i].getEstIntervalle() == 1) {
                colIntv.add(tempChamp[i].getNom());
                if ((this.getParamSansNull(tempChamp[i].getNom() + "1") == null || this.getParamSansNull(tempChamp[i].getNom() + "1").compareToIgnoreCase("") == 0) && this.getFormu().getChamp(tempChamp[i].getNom() + "1") != null && this.getFormu().getChamp(tempChamp[i].getNom() + "1").getDefaut() != null && this.getFormu().getChamp(tempChamp[i].getNom() + "1").getDefaut().compareToIgnoreCase("") != 0) {
                    valIntv.add(this.getFormu().getChamp(tempChamp[i].getNom() + "1").getDefaut());
                } else {
                    valIntv.add(this.getParamSansNull(tempChamp[i].getNom() + "1"));
                }
                if ((this.getParamSansNull(tempChamp[i].getNom() + "2") == null || this.getParamSansNull(tempChamp[i].getNom() + "2").compareToIgnoreCase("") == 0) && this.getFormu().getChamp(tempChamp[i].getNom() + "2") != null && this.getFormu().getChamp(tempChamp[i].getNom() + "2").getDefaut() != null && this.getFormu().getChamp(tempChamp[i].getNom() + "2").getDefaut().compareToIgnoreCase("") != 0) {
                    valIntv.add(this.getFormu().getChamp(tempChamp[i].getNom() + "2").getDefaut());
                    continue;
                }
                valIntv.add(this.getParamSansNull(tempChamp[i].getNom() + "2"));
                continue;
            }
            if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
                if ((this.getParamSansNull(tempChamp[i].getNom()) == null || this.getParamSansNull(tempChamp[i].getNom()).compareToIgnoreCase("") == 0) && this.getFormu().getChamp(tempChamp[i].getNom()) != null && this.getFormu().getChamp(tempChamp[i].getNom()).getDefaut() != null && this.getFormu().getChamp(tempChamp[i].getNom()).getDefaut().compareToIgnoreCase("") != 0) {
                    CGenUtil.setValChamp(this.getCritere(), f, this.getFormu().getChamp(tempChamp[i].getNom()).getValeur());
                    if (!recherchePrecis) continue;
                    this.getCritere().addAttributRecherchePrecis(f.getName());
                    continue;
                }
                String valeurChamp = this.getReq().getParameter(Utilitaire.remplacerUnderscore(tempChamp[i].getNom()));
                if (valeurChamp == null || valeurChamp.compareTo("") == 0) continue;
                CGenUtil.setValChamp(this.getCritere(), f, this.getParamSansNull(tempChamp[i].getNom()));
                if (!recherchePrecis) continue;
                this.getCritere().addAttributRecherchePrecis(f.getName());
                continue;
            }
            String operateur = " like ";
            if (recherchePrecis) {
                operateur = " = ";
            }
            this.aWhere = (this.getParamSansNull(tempChamp[i].getNom()) == null || this.getParamSansNull(tempChamp[i].getNom()).compareToIgnoreCase("") == 0) && this.getFormu().getChamp(tempChamp[i].getNom()) != null && this.getFormu().getChamp(tempChamp[i].getNom()).getDefaut() != null && this.getFormu().getChamp(tempChamp[i].getNom()).getDefaut().compareToIgnoreCase("") != 0 ? this.aWhere + " and " + f.getName() + operateur + "'" + Utilitaire.getValeurNonNull(this.getFormu().getChamp(tempChamp[i].getNom()).getValeur()) + "'" : this.aWhere + " and " + f.getName() + operateur + "'" + Utilitaire.getValeurNonNull(this.getParamSansNull(tempChamp[i].getNom())) + "'";
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
        } else if (this.formu.getChamp("colonne").getValeur() != null && this.formu.getChamp("ordre").getValeur().compareToIgnoreCase("-") != 0 && this.formu.getChamp("colonne").getValeur().compareToIgnoreCase("") != 0 && Utilitaire.estIlDedans(this.formu.getChamp("colonne").getValeur(), Utilitaire.concatener(this.getColGroupe(), this.getColSomme())) != -1) {
            this.setOrdre(" order by " + this.formu.getChamp("colonne").getValeur() + " " + this.formu.getChamp("ordre").getValeur());
        }
    }

    public void makeCritereMultiple() throws Exception {
        Vector<String> colIntv = new Vector<String>();
        Vector<String> valIntv = new Vector<String>();
        Champ[] tempChamp = this.formu.getCrtFormu();
        for (int i = 0; i < tempChamp.length; ++i) {
            boolean recherchePrecis;
            Field f = CGenUtil.getField(this.getBase(), tempChamp[i].getNom());
            Champ champConfigure = this.formu.getChamp(tempChamp[i].getNom());
            boolean bl = recherchePrecis = champConfigure != null && champConfigure.isRecherchePrecis();
            if (tempChamp[i].getEstIntervalle() == 1) {
                colIntv.add(tempChamp[i].getNom());
                if ((this.getParamSansNull(tempChamp[i].getNom() + "1") == null || this.getParamSansNull(tempChamp[i].getNom() + "1").compareToIgnoreCase("") == 0) && this.getFormu().getChamp(tempChamp[i].getNom() + "1") != null && this.getFormu().getChamp(tempChamp[i].getNom() + "1").getDefaut() != null && this.getFormu().getChamp(tempChamp[i].getNom() + "1").getDefaut().compareToIgnoreCase("") != 0) {
                    valIntv.add(this.getFormu().getChamp(tempChamp[i].getNom() + "1").getDefaut());
                } else {
                    valIntv.add(this.getParamSansNull(tempChamp[i].getNom() + "1"));
                }
                if ((this.getParamSansNull(tempChamp[i].getNom() + "2") == null || this.getParamSansNull(tempChamp[i].getNom() + "2").compareToIgnoreCase("") == 0) && this.getFormu().getChamp(tempChamp[i].getNom() + "2") != null && this.getFormu().getChamp(tempChamp[i].getNom() + "2").getDefaut() != null && this.getFormu().getChamp(tempChamp[i].getNom() + "2").getDefaut().compareToIgnoreCase("") != 0) {
                    valIntv.add(this.getFormu().getChamp(tempChamp[i].getNom() + "2").getDefaut());
                    continue;
                }
                valIntv.add(this.getParamSansNull(tempChamp[i].getNom() + "2"));
                continue;
            }
            if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
                String temp = this.getReq().getParameter(Utilitaire.remplacerUnderscore(tempChamp[i].getNom()));
                if ((temp == null || temp.compareToIgnoreCase("") == 0) && this.getFormu().getChamp(tempChamp[i].getNom()) != null && this.getFormu().getChamp(tempChamp[i].getNom()).getDefaut() != null && this.getFormu().getChamp(tempChamp[i].getNom()).getDefaut().compareToIgnoreCase("") != 0) {
                    CGenUtil.setValChamp(this.getCritere(), f, this.getFormu().getChamp(tempChamp[i].getNom()).getValeur());
                    if (!recherchePrecis) continue;
                    this.getCritere().addAttributRecherchePrecis(f.getName());
                    continue;
                }
                if (temp == null || temp.compareToIgnoreCase("") == 0) continue;
                CGenUtil.setValChamp(this.getCritere(), f, this.getParamSansNullMultiple(tempChamp[i].getNom()));
                if (!recherchePrecis) continue;
                this.getCritere().addAttributRecherchePrecis(f.getName());
                continue;
            }
            String operateur = " like ";
            if (recherchePrecis) {
                operateur = " = ";
            }
            this.aWhere = (this.getParamSansNullMultiple(tempChamp[i].getNom()) == null || this.getParamSansNullMultiple(tempChamp[i].getNom()).compareToIgnoreCase("") == 0) && this.getFormu().getChamp(tempChamp[i].getNom()) != null && this.getFormu().getChamp(tempChamp[i].getNom()).getDefaut() != null && this.getFormu().getChamp(tempChamp[i].getNom()).getDefaut().compareToIgnoreCase("") != 0 ? this.aWhere + " and " + f.getName() + operateur + "'" + Utilitaire.getValeurNonNull(this.getFormu().getChamp(tempChamp[i].getNom()).getValeur()) + "'" : this.aWhere + " and " + f.getName() + operateur + "'" + Utilitaire.getValeurNonNull(this.getParamSansNullMultiple(tempChamp[i].getNom())) + "'";
        }
        this.colInt = new String[colIntv.size()];
        colIntv.copyInto(this.colInt);
        this.valInt = new String[valIntv.size()];
        valIntv.copyInto(this.valInt);
        if (this.formu.getChamp("colonne").getValeur() != null && this.formu.getChamp("colonne").getValeur().compareToIgnoreCase("") != 0) {
            this.ordre = this.ordre + " order by " + this.formu.getChamp("colonne").getValeur() + " " + this.formu.getChamp("ordre").getValeur();
        }
    }

    private void setAnalyseLien() throws Exception {
        AnalyseLien crit = new AnalyseLien();
        crit.setNomTable("analyselien");
        int idUser = this.getUtilisateur().getUser().getRefuser();
        crit.setIdUtilisateur(idUser);
        crit.setPage(this.getPathPage());
        AnalyseLien[] liens = (AnalyseLien[])CGenUtil.rechercher((ClassMAPTable)crit, null, null, " and idutilisateur=" + idUser);
        this.getFormu().setLiens(liens);
    }

    public void creerObjetPage() throws Exception {
        this.getValeurFormulaire();
        this.preparerData();
        this.makeTableauRecap();
        this.makeInsertLienModal();
        String[] enteteAuto = Utilitaire.ajouterTableauString(this.getColGroupe(), this.getSommeGroupe());
        String critereLienTab = "<a href=" + this.getLien() + "?but=" + this.getApres() + "&numPag=1" + this.getApresLienPage() + this.formu.getListeCritereString();
        ClassMAPTable crt = this.getCritere();
        Field[] listF = crt.getClass().getDeclaredFields();
        this.setTableau(new TableauRecherche(this.getListe(), enteteAuto, critereLienTab, listF));
        this.makePagination();
    }

    public void makeInsertLienModal() throws Exception {
        try {
            AnalyseLien analyselien = new AnalyseLien();
            analyselien.setNomTable("analyselien");
            String lienAnalyse = this.getLien() + "?" + this.getReq().getQueryString();
            PageInsert pageinsertModal = new PageInsert((ClassMAPTable)analyselien, this.getReq(), this.getUtilisateur());
            pageinsertModal.setLien(this.getLien());
            pageinsertModal.getFormu().getChamp("lien").setDefaut(lienAnalyse);
            pageinsertModal.getFormu().getChamp("lien").setAutre("readonly");
            pageinsertModal.getFormu().getChamp("idUtilisateur").setDefaut("" + this.getUtilisateur().getUser().getRefuser());
            pageinsertModal.getFormu().getChamp("idUtilisateur").setAutre("readonly");
            pageinsertModal.getFormu().getChamp("idUtilisateur").setLibelle("Reference");
            pageinsertModal.getFormu().getChamp("page").setDefaut(this.getPathPage());
            pageinsertModal.getFormu().getChamp("page").setAutre("readonly");
            pageinsertModal.getFormu().getChamp("page").setLibelle("lien de la page");
            this.setInsertLienAnalyse(pageinsertModal);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getInsertLienModal() throws Exception {
        PageInsert analyselien = this.getInsertLienAnalyse();
        String ret = "<div class='modal fade' id='saveLienModal' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>";
        ret = ret + "<div class='modal-dialog'>";
        ret = ret + " <div class='modal-content'>";
        ret = ret + " <div class='modal-header'>";
        ret = ret + " <button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>";
        ret = ret + " <h4 class='modal-title' id='myModalLabel'>Analyse Lien insertion</h4>";
        ret = ret + "</div>";
        ret = ret + "<div class='modal-body'>";
        ret = ret + "<form action='" + analyselien.getLien() + "?but=apresTarif.jsp' method='post'  name='diplome' id='diplome'  data-parsley-validate>";
        analyselien.getFormu().makeHtmlInsertTabIndex();
        ret = ret + analyselien.getFormu().getHtmlInsert();
        ret = ret + "<input name='acte' type='hidden' id='nature' value='insert'>";
        ret = ret + "<input name='bute' type='hidden' id='bute' value='" + this.getPathPage() + "'>";
        ret = ret + "<input name='classe' type='hidden' id='classe' value='bean.AnalyseLien'>";
        ret = ret + "</form>";
        ret = ret + "</div>";
        ret = ret + "</div>";
        ret = ret + "</div>";
        ret = ret + "</div>";
        return ret;
    }

    public void creerObjetPage(ClassMAPTable[] liste) throws Exception {
        this.setListe(liste);
        this.creerObjetPage();
    }

    public void creerObjetPage(String[] colGroupe) throws Exception {
        this.creerObjetPage();
    }

    public void creerObjetPagePourc() throws Exception {
        this.getValeurFormulaire();
        this.preparerData();
        this.makeTableauRecap();
        String critereLienTab = "<a href=" + this.getLien() + "?but=" + this.getApres() + "&numPag=1" + this.getApresLienPage() + this.formu.getListeCritereString();
        String[] enteteAuto = Utilitaire.ajouterTableauString(this.getColGroupe(), this.getSommeGroupe());
        String[] enteteNombre = new String[]{"nombrepargroupe"};
        enteteAuto = Utilitaire.ajouterTableauString(enteteAuto, enteteNombre);
        ClassMAPTable crt = this.getCritere();
        Field[] listF = crt.getClass().getDeclaredFields();
        this.setTableau(new TableauRecherche(this.getListe(), enteteAuto, this.getPourcentage(), this.getPourc(), critereLienTab, listF));
    }

    public void preparerDataListMultiple(Connection c) throws Exception {
        this.critere.setNomTable(this.getBase().getNomTable());
        this.makeCritereMultiple();
        if (this.premier) {
            this.rs = new ResultatEtSomme();
            this.rs.initialise(this.getColSomme());
        } else {
            this.rs = this.getUtilisateur().getDataPageGroupeMultiple(this.critere, this.getColGroupe(), this.getSommeGroupe(), this.getColInt(), this.getValInt(), this.getNumPage(), this.getAWhere(), this.getColSomme(), this.getOrdre(), c, this.npp);
        }
        this.setListe((ClassMAPTable[])this.rs.getResultat());
    }

    public void preparerDataListMultiple(String count, Connection c) throws Exception {
        this.critere.setNomTable(this.getBase().getNomTable());
        this.makeCritereMultiple();
        if (this.premier) {
            this.rs = new ResultatEtSomme();
            this.rs.initialise(this.getColSomme());
        } else {
            this.rs = this.getUtilisateur().getDataPageGroupeMultiple(this.critere, this.getColGroupe(), this.getSommeGroupe(), this.getColInt(), this.getValInt(), this.getNumPage(), this.getAWhere(), this.getColSomme(), this.getOrdre(), c, this.npp, count);
        }
        this.setListe((ClassMAPTable[])this.rs.getResultat());
    }

    public void preparerDataMultiple() throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            this.preparerDataListMultiple(c);
            this.formu.getAllData(c);
            this.getReq().getSession().setAttribute("critere", (Object)this.getCritere());
        }
    }

    public void preparerDataMultiple(String count) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            this.preparerDataListMultiple(count, c);
            this.formu.getAllData(c);
            this.getReq().getSession().setAttribute("critere", (Object)this.getCritere());
        }
    }

    public void creerObjetPagePourcMultiple() throws Exception {
        this.getValeurFormulaireMultiple();
        this.preparerDataMultiple();
        this.makeTableauRecap();
        String critereLienTab = "<a href=" + this.getLien() + "?but=" + this.getApres() + "&numPag=1" + this.getApresLienPage() + this.formu.getListeCritereString();
        String[] enteteAuto = Utilitaire.ajouterTableauString(this.getColGroupe(), this.getSommeGroupe());
        String[] enteteNombre = new String[]{"nombrepargroupe"};
        enteteAuto = Utilitaire.ajouterTableauString(enteteAuto, enteteNombre);
        ClassMAPTable crt = this.getCritere();
        Field[] listF = crt.getClass().getDeclaredFields();
        this.setTableau(new TableauRecherche(this.getListe(), enteteAuto, this.getPourcentage(), this.getPourc(), critereLienTab, listF));
    }

    public void creerObjetPagePourcMultiple(String count) throws Exception {
        this.getValeurFormulaireMultiple();
        this.preparerDataMultiple(count);
        this.makeTableauRecap();
        String critereLienTab = "<a href=" + this.getLien() + "?but=" + this.getApres() + "&numPag=1" + this.getApresLienPage() + this.formu.getListeCritereString();
        String[] enteteAuto = Utilitaire.ajouterTableauString(this.getColGroupe(), this.getSommeGroupe());
        String[] enteteNombre = new String[]{"nombrepargroupe"};
        enteteAuto = Utilitaire.ajouterTableauString(enteteAuto, enteteNombre);
        ClassMAPTable crt = this.getCritere();
        Field[] listF = crt.getClass().getDeclaredFields();
        this.setTableau(new TableauRecherche(this.getListe(), enteteAuto, this.getPourcentage(), this.getPourc(), critereLienTab, listF));
    }

    public PageRechercheGroupe(ClassMAPTable o, HttpServletRequest req, String[] vrt, String[] listInterval, int nbRange, String[] colGr, String[] sommGr, int nbCol, int somGr) throws Exception {
        this.setBase(o);
        o.setMode("groupe");
        o.setGroupee(true);
        this.setCritere((ClassMAPTable)Class.forName(o.getClassName()).newInstance());
        this.getCritere().setMode("groupe");
        this.setReq(req);
        if (this.getReq().getParameter("premier") != null && this.getReq().getParameter("premier").compareToIgnoreCase("") != 0) {
            this.setPremier(Boolean.valueOf(this.getReq().getParameter("premier")));
        }
        this.formu = new Formulaire();
        this.formu.setGroupee(true);
        this.formu.setObjet(this.getBase());
        this.formu.makeChampFormu(vrt, listInterval, nbRange);
        this.setColGroupeDefaut(colGr);
        this.setSommeGroupeDefaut(sommGr);
        this.setNbColGroupe(nbCol);
        this.setNbLigneGroupe(nbCol);
        this.setNbSommeGrp(somGr);
        this.makeGroupe();
        this.ajusterNombreChampsGroupe();
        this.formu.makeChampGroupe(this.getNbColGroupe(), this.getNbLigneGroupe(), this.getNbSommeGrp());
        this.synchroniserChoixParDefaut(colGr, null, sommGr);
    }

    public PageRechercheGroupe(ClassMAPTable o, HttpServletRequest req, String[] vrt, String[] listInterval, int nbRange, String[] colGr, String[] sommGr, String[] pourc, int nbCol, int somGr) throws Exception {
        this.setBase(o);
        o.setGroupee(true);
        o.setMode("groupe");
        this.setCritere((ClassMAPTable)Class.forName(o.getClassName()).newInstance());
        this.getCritere().setMode("groupe");
        this.setReq(req);
        if (this.getReq().getParameter("premier") != null && this.getReq().getParameter("premier").compareToIgnoreCase("") != 0) {
            this.setPremier(Boolean.valueOf(this.getReq().getParameter("premier")));
        }
        this.formu = new Formulaire();
        if (req != null && req.getParameter("motsclesss") != null && req.getParameter("motsclesss").compareToIgnoreCase("") != 0) {
            this.formu.setReqqq(req);
        }
        this.formu.setObjet(this.getBase());
        this.formu.setGroupee(true);
        this.setColGroupeDefaut(colGr);
        this.setSommeGroupeDefaut(sommGr);
        this.setNbColGroupe(nbCol);
        this.setNbLigneGroupe(nbCol);
        this.setNbSommeGrp(somGr);
        this.setPourcentage(pourc);
        this.formu.makeChampFormu(vrt, listInterval, nbRange);
        this.makeGroupe();
        this.ajusterNombreChampsGroupe();
        this.formu.makeChampGroupe(this.getNbColGroupe(), this.getNbLigneGroupe(), this.getNbSommeGrp());
        this.synchroniserChoixParDefaut(colGr, null, sommGr);
    }

    public PageRechercheGroupe(ClassMAPTable o, HttpServletRequest req, String[] vrt, String[] listInterval, int nbRange, String[] colGr, String[] sommGr, String[] pourc, int nbCol, int somGr, Liste count) throws Exception {
        this(o, req, vrt, listInterval, nbRange, colGr, sommGr, pourc, nbCol, somGr);
        this.setCount(count);
    }

    public void makeGroupe() throws Exception {
        TreeMap<Integer, String> colGroupeParams = new TreeMap<Integer, String>();
        TreeMap<Integer, String> ligneGroupeParams = new TreeMap<Integer, String>();
        TreeMap<Integer, String> sommeParams = new TreeMap<Integer, String>();
        Enumeration nomsParams = this.getReq().getParameterNames();
        while (nomsParams.hasMoreElements()) {
            String nom = (String)nomsParams.nextElement();
            if (nom == null) continue;
            if (nom.startsWith("colGroupe")) {
                this.ajouterParamGroupe(colGroupeParams, nom, "colGroupe");
                continue;
            }
            if (nom.startsWith("ligneGroupe")) {
                this.ajouterParamGroupe(ligneGroupeParams, nom, "ligneGroupe");
                continue;
            }
            if (!nom.startsWith("colSomme")) continue;
            this.ajouterParamGroupe(sommeParams, nom, "colSomme");
        }
        boolean aucuneSommeValide = true;
        for (String valeurSomme : sommeParams.values()) {
            if (valeurSomme == null || valeurSomme.trim().isEmpty() || valeurSomme.trim().equals("-")) continue;
            aucuneSommeValide = false;
            break;
        }
        if (sommeParams.isEmpty() || aucuneSommeValide) {
            sommeParams.clear();
            String[] sommesParDefaut = this.getSommeGroupeDefaut();
            if (sommesParDefaut != null) {
                for (int i = 0; i < sommesParDefaut.length; ++i) {
                    sommeParams.put(i + 1, sommesParDefaut[i]);
                }
            }
        }
        this.setColGroupe(this.nettoyerDoublonsTableau(Utilitaire.formerTableauGroupe(this.valeursGroupe(colGroupeParams))));
        this.setLigneGroupe(this.nettoyerDoublonsTableau(Utilitaire.formerTableauGroupe(this.valeursGroupe(ligneGroupeParams))));
        this.setSommeGroupe(this.nettoyerDoublonsTableau(Utilitaire.formerTableauGroupe(this.valeursGroupe(sommeParams))));
    }

    private String[] nettoyerDoublonsTableau(String[] tab) {
        if (tab == null || tab.length == 0) {
            return tab;
        }
        ArrayList<String> result = new ArrayList<String>();
        HashSet<String> vus = new HashSet<String>();
        for (String s : tab) {
            String key;
            if (s == null || s.trim().isEmpty() || s.trim().equals("-") || !vus.add(key = s.trim().toLowerCase(Locale.ROOT))) continue;
            result.add(s.trim());
        }
        return result.toArray(new String[0]);
    }

    private void ajusterNombreChampsGroupe() {
        if (this.getColGroupe() != null && this.getColGroupe().length > this.getNbColGroupe()) {
            this.setNbColGroupe(this.getColGroupe().length);
        }
        if (this.getLigneGroupe() != null && this.getLigneGroupe().length > this.getNbLigneGroupe()) {
            this.setNbLigneGroupe(this.getLigneGroupe().length);
        }
        if (this.getSommeGroupe() != null && this.getSommeGroupe().length > this.getNbSommeGrp()) {
            this.setNbSommeGrp(this.getSommeGroupe().length);
        }
    }

    private void ajouterParamGroupe(TreeMap<Integer, String> destination, String nomParam, String prefixe) {
        Integer indice = null;
        String suffixe = nomParam.substring(prefixe.length()).trim();
        try {
            indice = Integer.valueOf(suffixe);
        }
        catch (NumberFormatException e) {
            indice = null;
        }
        if (indice != null) {
            destination.put(indice, this.getReq().getParameter(nomParam));
        }
    }

    private String[] valeursGroupe(TreeMap<Integer, String> params) {
        if (params.isEmpty()) {
            return new String[0];
        }
        String[] valeurs = new String[params.size()];
        int i = 0;
        Iterator<String> iterator = params.values().iterator();
        while (iterator.hasNext()) {
            String valeur;
            valeurs[i] = valeur = iterator.next();
            ++i;
        }
        return valeurs;
    }

    private void synchroniserChoixParDefaut(String[] lignes, String[] colonnes, String[] sommes) {
        Champ[] champsGroupe = this.getFormu().getChampGroupe();
        if (champsGroupe == null) {
            return;
        }
        this.appliquerChoixParDefaut(champsGroupe, 0, lignes);
        this.appliquerChoixParDefaut(champsGroupe, this.getNbColGroupe(), colonnes);
        this.appliquerChoixParDefaut(champsGroupe, this.getNbColGroupe() + this.getNbLigneGroupe(), sommes);
    }

    private void appliquerChoixParDefaut(Champ[] champs, int debut, String[] valeurs) {
        if (valeurs == null) {
            return;
        }
        for (int i = 0; i < valeurs.length && debut + i < champs.length; ++i) {
            if (!(champs[debut + i] instanceof Liste)) continue;
            ((Liste)champs[debut + i]).setDefaultSelected(valeurs[i]);
        }
    }

    private void synchroniserValeursChampsGroupe() {
        Champ[] champsGroupe = this.getFormu().getChampGroupe();
        if (champsGroupe == null) {
            return;
        }
        this.appliquerValeursChamps(champsGroupe, 0, this.getColGroupe(), this.getNbColGroupe());
        this.appliquerValeursChamps(champsGroupe, this.getNbColGroupe(), this.getLigneGroupe(), this.getNbLigneGroupe());
        this.appliquerValeursChamps(champsGroupe, this.getNbColGroupe() + this.getNbLigneGroupe(), this.getSommeGroupe(), this.getNbSommeGrp());
    }

    private void appliquerValeursChamps(Champ[] champs, int debut, String[] valeurs, int limite) {
        if (valeurs == null) {
            return;
        }
        for (int i = 0; i < valeurs.length && i < limite && debut + i < champs.length; ++i) {
            champs[debut + i].setValeur(valeurs[i]);
        }
    }

    @Override
    public void getValeurFormulaire() throws Exception {
        super.getValeurFormulaire();
        this.synchroniserValeursChampsGroupe();
    }

    public Liste getCount() {
        return this.count;
    }

    public void setCount(Liste count) {
        this.count = count;
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
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        String contextPath = this.getReq().getContextPath();
        String retour = "";
        if (this.getListe().length == 0) {
            return retour;
        }
        retour = retour + "<div class=\" pagination-bas-page \">";
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

    public String getDownloadFormTemplate() throws Exception {
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
        temp = temp + "<label class=\"toggle-button-export export-card col-md-4\">\n         <input type=\"radio\" name=\"ext\" value=\"xls\">\n            <div class=\"checkbox\">\n                <span class=\"checkmark\"><i class=\"material-symbols-rounded\">check</i></span></span>\n             </div>\n             <div class=\"d-flex\">\n                 <img  src='" + contextPath + "/assets/img/excel file.png' alt='CSV'>                 <h5 class='Body14pxSemiBold'>Format .xls</h5>                 <p class='Body12pxRegular'>Compatible avec Microsoft Excel et autres tableurs.</p>             </div>\n      </label>";
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
        temp = temp + "<div class='col-md-4' id=\"orientation-section\">";
        temp = temp + "<label class='Body14pxRegular' for='donnee'>Orientation (PDF uniquement) :</label>";
        temp = temp + "<div class='d-flex gap-2' >";
        temp = temp + "<label class=\"toggle-button-export orientation\">\n  <input type=\"radio\" name=\"landscape\" value=\"0\">\n     <div class=\"checkbox d-none\">\n       <span class=\"checkmark\"><i class=\"material-symbols-rounded\">check</i></span></span>\n     </div>\n     <div class=\"d-flex\">\n        <i class='material-symbols-rounded'>crop_portrait</i>\n        <p class='Body14pxSemiBold'>Portrait</p>\n     </div>\n</label>";
        temp = temp + "<label class=\"toggle-button-export orientation\">\n  <input type=\"radio\" name=\"landscape\" value=\"1\">\n     <div class=\"checkbox d-none\">\n       <span class=\"checkmark\"><i class=\"material-symbols-rounded\">check</i></span></span>\n     </div>\n     <div class=\"d-flex\">\n        <i class='material-symbols-rounded'>crop_landscape</i>\n        <p class='Body14pxSemiBold'>Paysage</p>\n     </div>\n</label>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        if (this.getTableau().getExpxml() != null) {
            temp = temp + "<input type=\"hidden\" name=\"xml\" value=\"" + this.getTableau().getExpxml().replace('\"', '*') + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"csv\" value=\"" + this.getTableau().getExpcsv().replace('\"', '*') + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"awhere\" value=\"" + awhere + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"table\" value=\"" + tab1.replace('\"', '*').replace("'", " ").replace("\"", " ") + "\" />";
            if (this.getTableau().getLibeEntete() != null && this.getTableau().getLibeEntete().length > 0) {
                temp = temp + "<input type=\"hidden\" name=\"entete\" value=\"" + Utilitaire.tabToString(this.getTableau().getLibeEntete(), "", ";") + "\" />";
            }
            if (this.getTableau().getLibelleAffiche() != null && this.getTableau().getLibelleAffiche().length > 0) {
                temp = temp + "<input type=\"hidden\" name=\"entetelibelle\" value=\"" + Utilitaire.tabToString(this.getTableau().getLibelleAffiche(), "", ";") + "\" />";
            }
        }
        if ((sommeId = Utilitaire.estIlDedans("montant", this.getColSomme())) > -1) {
            temp = temp + "<input type=\"hidden\" name=\"nombreEncours\" value=\"" + Utilitaire.formaterSansVirgule(this.rs.getSommeEtNombre()[this.getColSomme().length]) + "\" />";
            double test = this.rs.getSommeEtNombre()[sommeId];
            String testEnString = Utilitaire.enleverExponentielleDouble(test);
            temp = temp + "<input type=\"hidden\" name=\"recap\" value=\"" + testEnString + "\" />";
        }
        Gson gson = new Gson();
        temp = temp + "<input type=\"hidden\" name=\"requete\" value=\"" + this.getCritere().getRequete() + "\" />";
        temp = temp + "<input type=\"hidden\" name=\"nomTable\" value=\"" + this.getBase().getNomTable() + "\" />";
        temp = temp + "<input type=\"hidden\" name=\"titre\" value=\"" + this.getTitre() + "\" />";
        temp = temp + "<input type=\"hidden\" name=\"className\" value=\"" + this.getBase().getClassName() + "\" />";
        temp = temp + "<input type='hidden' name='libColonneExcel' value='" + gson.toJson((Object)this.getTableau().getLibelleAffiche()) + "' />";
        temp = temp + "<input type='hidden' name='titre' value='" + this.getTitre() + "' />";
        temp = temp + "</div>";
        return temp;
    }

    public void setApres(String apres) {
        this.apres = apres;
    }

    public String getApres() {
        return this.apres;
    }

    public String getAWhere() {
        return this.aWhere;
    }

    public void setAWhere(String aWhere) {
        this.aWhere = aWhere;
    }

    public void setApresLienPage(String apresLienPage) {
        this.apresLienPage = apresLienPage;
    }

    public String getApresLienPage() {
        return this.apresLienPage;
    }

    public void setColGroupe(String[] colGroupe) {
        this.colGroupe = colGroupe;
    }

    public String[] getColGroupe() {
        if (this.colGroupe == null || this.colGroupe.length == 0) {
            return this.getColGroupeDefaut();
        }
        return this.colGroupe;
    }

    public void setSommeGroupe(String[] sommeGroupe) {
        this.sommeGroupe = sommeGroupe;
        this.setColSomme(this.getSommeGroupe());
    }

    public String[] getSommeGroupe() {
        if (this.sommeGroupe == null || this.sommeGroupe.length == 0) {
            return this.sommeGroupeDefaut;
        }
        return this.sommeGroupe;
    }

    public void setOrdre(String ordre) {
        this.ordre = ordre;
    }

    public String getOrdre() {
        return this.ordre;
    }

    public void setNbColGroupe(int nbColGroupe) {
        this.nbColGroupe = nbColGroupe;
    }

    public int getNbColGroupe() {
        return this.nbColGroupe;
    }

    public void setNbLigneGroupe(int nbLigneGroupe) {
        this.nbLigneGroupe = nbLigneGroupe;
    }

    public int getNbLigneGroupe() {
        return this.nbLigneGroupe;
    }

    public void setNbSommeGrp(int nbSommeGrp) {
        this.nbSommeGrp = nbSommeGrp;
    }

    public int getNbSommeGrp() {
        return this.nbSommeGrp;
    }

    public void setColGroupeDefaut(String[] colGroupeDefaut) {
        this.colGroupeDefaut = colGroupeDefaut;
    }

    @Override
    public void setTableau(TableauRecherche tableau) {
        super.setTableau(tableau);
        if (this.getTableau() != null) {
            this.getTableau().setGroupe(true);
            this.getTableau().setLienFormulaire(this.generateUrlFormulaire());
        }
        if (tableau instanceof TableauRechercheGroupe) {
            ((TableauRechercheGroupe)tableau).setPageProprietaire(this);
        }
    }

    public String[] getColGroupeDefaut() {
        return this.colGroupeDefaut;
    }

    public void setSommeGroupeDefaut(String[] sommeGroupeDefaut) {
        this.sommeGroupeDefaut = sommeGroupeDefaut;
        this.setColSomme(sommeGroupeDefaut);
    }

    public String[] getSommeGroupeDefaut() {
        return this.sommeGroupeDefaut;
    }

    public void setNpp(int npp) {
        this.npp = npp;
    }

    public int getNpp() {
        return this.npp;
    }

    public void setListeColonneMoyenne(String[] listeColonneMoyenne) {
        this.listeColonneMoyenne = listeColonneMoyenne;
    }

    public String[] getListeColonneMoyenne() {
        return this.listeColonneMoyenne;
    }

    public String getDownloadForm() throws Exception {
        int sommeId;
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
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
        temp = temp + "<form action=\"../download\" method=\"post\">";
        temp = temp + "<div class='row col-md-12' style='margin-bottom: 25px;'>";
        temp = temp + "<div class='col-md-3'>";
        temp = temp + "<img style='width: 98px;height: 98px;' src='../dist/img/csv_text.png'>";
        temp = temp + "<input type='radio' name='ext' value='csv' checked='checked' />";
        temp = temp + "</div>";
        temp = temp + "<div class='col-md-3'>";
        temp = temp + "<img style='width: 98px;height: 98px;' src='../dist/img/file_xls.png'>";
        temp = temp + "<input type='radio' name='ext' value='xls' />";
        temp = temp + "</div>";
        temp = temp + "<div class='col-md-3'>";
        temp = temp + "<img style='width: 98px;height: 98px;' src='../dist/img/file_xml.png'>";
        temp = temp + "<input type='radio' name='ext' value='xml' />";
        temp = temp + "</div>";
        temp = temp + "<div class='col-md-3'>";
        temp = temp + "<img style='width: 98px;height: 98px;' src='../dist/img/file_pdf.png'>";
        temp = temp + "<input type='radio' name='ext' value='pdf' />";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<div class='row col-md-12' style=\"display: flex;flex-direction: row;flex-wrap: nowrap;justify-content: space-between;margin-bottom: 18px;\">";
        temp = temp + "<div class='col-md-6 row'>";
        temp = temp + "<div class='col-md-12' style=\"margin-bottom: 10px;padding:0;\">";
        temp = temp + "<label class='fontinter' for='donnee'>" + RB.getString("donneeexport") + " </label>";
        temp = temp + "</div>";
        temp = temp + "<div class='col-md-6' style=\"display: flex;flex-direction: row-reverse;margin: 0;padding: 0;justify-content: flex-end;align-items: flex-start;\">";
        temp = temp + "<label class='fontinter' for='donnee' style=\"margin-left: 6px;margin-top: 3px;font-size:12px;\">" + RB.getString("pageencours") + " </label>";
        temp = temp + "<input type='radio' name='donnee' value=\"0\" checked='checked' />";
        temp = temp + "</div>";
        temp = temp + "<div class='col-md-6' style=\"display: flex;flex-direction: row-reverse;margin: 0;padding: 0;justify-content: flex-end;align-items: flex-start;\">";
        temp = temp + "<label class='fontinter' for='donnee' style=\"margin-left: 6px;margin-top: 3px;font-size:12px;\">" + RB.getString("toutes") + " </label>";
        temp = temp + "<input type='radio' name='donnee' value=\"1\" />";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<div class='col-md-6 row'>";
        temp = temp + "<div class='col-md-12' style=\"margin-bottom: 10px;padding:0;\">";
        temp = temp + "<label class='fontinter' for='donnee'><strong>LANDSCAPE</strong>(pour pdf)</label>";
        temp = temp + "</div>";
        temp = temp + "<div class='col-md-6' style=\"display: flex;flex-direction: row-reverse;margin: 0;padding: 0;justify-content: flex-end;align-items: flex-start;\">";
        temp = temp + "<label class='fontinter' for='landscape' style=\"margin-left: 6px;margin-top: 3px;font-size:12px;\">Portrait</label>";
        temp = temp + "<input type='radio' name='landscape' value=\"0\" checked='checked' />";
        temp = temp + "</div>";
        temp = temp + "<div class='col-md-6' style=\"display: flex;flex-direction: row-reverse;margin: 0;padding: 0;justify-content: flex-end;align-items: flex-start;\">";
        temp = temp + "<label class='fontinter' for='landscape' style=\"margin-left: 6px;margin-top: 3px;font-size:12px;\">Paysage</label>";
        temp = temp + "<input type='radio' name='landscape' value=\"1\" />";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "<div style=\"padding: 10px 30px;\">\r\n";
        if (this.getTableau().getExpxml() != null) {
            temp = temp + "<input type=\"hidden\" name=\"xml\" value=\"" + this.getTableau().getExpxml().replace('\"', '*') + "\" />";
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
        temp = temp + "<input type='submit' value='" + RB.getString("exporter") + "' type='submit' class='btn btnradius' style='background:" + ConstanteAffichage.couleurPrimaireTemplate + "; color:white;width:100%;height: 30px;padding: 0;' id='btnListe'/>";
        temp = temp + "          </div>";
        temp = temp + "<input type='hidden' name='titre' value='" + this.getTitre() + "' />";
        temp = temp + "</form>";
        temp = temp + "</div>";
        return temp;
    }

    public void setPourcentage(String[] pourcentage) {
        this.pourcentage = pourcentage;
    }

    public String[] getPourcentage() {
        return this.pourcentage;
    }

    public void setPourc(double[][] pourc) {
        this.pourc = pourc;
    }

    public double[][] getPourc() {
        return this.pourc;
    }

    public String getCamembertChart() {
        String ret = "";
        return ret;
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

    public String generateUrlFormulaire() {
        String retour = "";
        Champ[] c = this.formu.getListeChamp();
        for (int i = 0; i < c.length; ++i) {
            String valeurRecup = this.getParamSansNull(c[i].getNom());
            if (valeurRecup == null || valeurRecup.isEmpty() || valeurRecup.equals("%")) continue;
            retour = retour + c[i].getNom() + "=" + valeurRecup + "&";
        }
        return retour;
    }

    public static double[][] cumul(ResultatEtSomme rs, String[] col) throws Exception {
        ClassMAPTable[] os = (ClassMAPTable[])rs.getResultat();
        double[][] ret = new double[os.length][col.length];
        for (int j = 0; j < col.length; ++j) {
            double cumul = 0.0;
            for (int i = 0; i < os.length; ++i) {
                double[] dArray = ret[i];
                int n = j;
                dArray[n] = dArray[n] + (cumul += ((Double)CGenUtil.getValeurFieldByMethod(os[i], col[j])).doubleValue());
            }
            System.out.println("");
        }
        return ret;
    }

    public String[] getLigneGroupe() {
        return this.ligneGroupe;
    }

    public void setLigneGroupe(String[] ligneGroupe) {
        this.ligneGroupe = ligneGroupe;
    }

    public void creerObjetPageCroise(String lien) throws Exception {
        this.getValeurFormulaire();
        this.preparerData();
        this.makeTableauRecap();
        this.setTableau(new TableauRechercheGroupe(this.getListe(), this.getColGroupe(), this.getSommeGroupeDefaut(), lien));
    }

    public void creerObjetPageCroise(String[] colon, String lien) throws Exception {
        this.synchroniserChoixParDefaut(null, colon, null);
        if (this.getLigneGroupe() != null && this.getLigneGroupe().length > 0) {
            colon = this.getLigneGroupe();
        }
        this.setColGroupe(this.ajouterColonnesGroupeSansDoublon(this.getColGroupe(), colon));
        this.getValeurFormulaire();
        this.preparerData();
        this.makeTableauRecap();
        this.setTableau(new TableauRechercheGroupe(this.getListe(), this.getColGroupe(), colon, this.getSommeGroupe(), lien));
    }

    public void creerObjetPageCroise(String[] colon, String lien, Connection c) throws Exception {
        this.synchroniserChoixParDefaut(null, colon, null);
        if (this.getLigneGroupe() != null && this.getLigneGroupe().length > 0) {
            colon = this.getLigneGroupe();
        }
        this.setColGroupe(this.ajouterColonnesGroupeSansDoublon(this.getColGroupe(), colon));
        this.getValeurFormulaire();
        this.preparerData(c);
        this.makeTableauRecap();
        this.setTableau(new TableauRechercheGroupe(this.getListe(), this.getColGroupe(), colon, this.getSommeGroupe(), lien));
    }

    private String[] ajouterColonnesGroupeSansDoublon(String[] groupes, String[] colonnes) {
        if (colonnes == null || colonnes.length == 0) {
            return groupes;
        }
        String[] fusion = Utilitaire.ajouterTableauString(groupes, colonnes);
        String[] retenues = new String[fusion.length];
        int nombreRetenues = 0;
        for (String s : fusion) {
            if (s == null) continue;
            boolean doublon = false;
            for (int k = 0; k < nombreRetenues; ++k) {
                if (retenues[k].compareToIgnoreCase(s) != 0) continue;
                doublon = true;
                break;
            }
            if (doublon) continue;
            retenues[nombreRetenues] = s;
            ++nombreRetenues;
        }
        String[] retour = new String[nombreRetenues];
        for (int k = 0; k < nombreRetenues; ++k) {
            retour[k] = retenues[k];
        }
        return retour;
    }

    public String getDragAndDropColumnChangerHtml(String idForm) throws Exception {
        bean.Champ[] colonnes = ListeColonneTable.getFromListe(this.getFormu().getObjet(), null);
        String formId = idForm == null ? "" : idForm.trim();
        String suffixeId = formId.replaceAll("[^A-Za-z0-9_-]", "-");
        if (suffixeId.isEmpty()) {
            suffixeId = "formulaire";
        }
        String idComposant = "apj-analysis-dnd-" + suffixeId + "-" + Integer.toHexString(System.identityHashCode(this));
        StringBuilder html = new StringBuilder();
        html.append("<div class='col-md-12 mb-5' id='").append(idComposant).append("' data-apj-analysis-dnd='true' data-form-id='").append(Utilitaire.escapeHtml(formId)).append("' style='padding:20px;color:#1f2937;background:#fff;border:1px solid var(--Border);border-radius:12px;font-family:inherit;'>");
        html.append("<header style='display:flex;align-items:flex-start;justify-content:space-between;flex-wrap:wrap;gap:16px;margin-bottom:18px;'>").append("<div><h3 class=\"h520pxSemibold m-0\">Champs disponibles</h3>").append("<p class=\"Body14pxRegular\">Glissez un champ sur la premi&egrave;re colonne, la premi&egrave;re ligne ou une cellule du tableau.</p></div>").append("<span data-apj-analysis-loading aria-hidden='true' style='display:none;color:#2563eb;font-size:13px;font-weight:600;'>Actualisation...</span>").append("</header>");
        html.append("<div data-apj-analysis-source style='padding:14px;background:var(--Onyx-035);border-radius:10px;'>");
        LinkedHashMap<String, Object> rubriquesEffectives = this.getRubriquesAnalyse();
        if (rubriquesEffectives == null) {
            rubriquesEffectives = new LinkedHashMap();
            String[] nomsToutesColonnes = new String[colonnes.length];
            for (int i = 0; i < colonnes.length; ++i) {
                nomsToutesColonnes[i] = colonnes[i].getNomColonne();
            }
            rubriquesEffectives.put("Tout les champs", nomsToutesColonnes);
        }
        LinkedHashMap<String, bean.Champ> colonnesParNom = new LinkedHashMap<String, bean.Champ>();
        for (bean.Champ colonne : colonnes) {
            colonnesParNom.put(colonne.getNomColonne().toLowerCase(Locale.ROOT), colonne);
        }
        HashSet<String> sommesAutorisees = new HashSet<String>();
        if (this.getColonnesSommeAutorisees() == null) {
            for (bean.Champ colonne : colonnes) {
                if (!Utilitaire.isChampNumerique(colonne)) continue;
                sommesAutorisees.add(colonne.getNomColonne().toLowerCase(Locale.ROOT));
            }
        } else {
            for (String colonneSomme : this.getColonnesSommeAutorisees()) {
                if (colonneSomme == null) continue;
                sommesAutorisees.add(colonneSomme.trim().toLowerCase(Locale.ROOT));
            }
        }
        HashSet<String> colonnesVisibles = null;
        if (this.getColonnesDnDVisibles() != null) {
            colonnesVisibles = new HashSet<String>();
            for (String colonneVisible : this.getColonnesDnDVisibles()) {
                if (colonneVisible == null) continue;
                colonnesVisibles.add(colonneVisible.trim().toLowerCase(Locale.ROOT));
            }
        }
        int nombreColonnesAffichees = 0;
        HashSet<String> colonnesAjoutees = new HashSet<String>();
        StringBuilder rubriquesHtml = new StringBuilder();
        for (Map.Entry rubrique : rubriquesEffectives.entrySet()) {
            StringBuilder chipsRubrique = new StringBuilder();
            int nombreColonnesRubrique = 0;
            String[] nomsColonnes = (String[])rubrique.getValue();
            if (nomsColonnes != null) {
                for (String nomColonne : nomsColonnes) {
                    bean.Champ colonne;
                    if (nomColonne == null) continue;
                    String cle = nomColonne.trim().toLowerCase(Locale.ROOT);
                    if (colonnesVisibles != null && !colonnesVisibles.contains(cle) || (colonne = (bean.Champ)colonnesParNom.get(cle)) == null || !colonnesAjoutees.add(cle)) continue;
                    this.ajouterChipAnalyse(chipsRubrique, colonne, sommesAutorisees.contains(cle));
                    ++nombreColonnesRubrique;
                    ++nombreColonnesAffichees;
                }
            }
            if (nombreColonnesRubrique <= 0) continue;
            rubriquesHtml.append("<section data-apj-analysis-category style='flex:1 1 220px;min-width:0;margin:0;padding:12px;background:#fff;border:1px solid var(--Border);border-radius:8px;'>").append("<h4 class=\"Body14pxSemiBold\">").append(Utilitaire.escapeHtml((String)rubrique.getKey())).append("</h4>").append("<ul style='display:flex;flex-wrap:wrap;gap:8px;margin:0;padding:0;list-style:none;'>").append((CharSequence)chipsRubrique).append("</ul></section>");
        }
        if (nombreColonnesAffichees == 0) {
            html.append("<p style='margin:0;color:#dc2626;'>Aucune colonne valide n&#39;est configur&eacute;e dans les rubriques.</p>");
        } else {
            html.append("<div data-apj-analysis-categories style='display:flex;flex-wrap:wrap;gap:14px;align-items:stretch;'>").append((CharSequence)rubriquesHtml).append("</div>");
        }
        html.append("</div>");
        html.append("<div data-apj-analysis-legend style='display:flex;flex-wrap:wrap;align-items:center;gap:10px 18px;margin-top:14px;padding:10px 12px;background:var(--Onyx-035);border-radius:8px;font-size:12px;'>").append("<strong style='color:#374151;'>L&eacute;gende</strong>").append("<span style='display:inline-flex;align-items:center;gap:6px;'><span aria-hidden='true' style='width:10px;height:10px;border-radius:50%;background:var(--Primaire-blue-main-color, #2B68D9);'></span>Colonne</span>").append("<span style='display:inline-flex;align-items:center;gap:6px;'><span aria-hidden='true' style='width:10px;height:10px;border-radius:50%;background:var(--Success-success-color, #22C55E);'></span>Ligne</span>").append("<span style='display:inline-flex;align-items:center;gap:6px;'><span aria-hidden='true' style='width:10px;height:10px;border-radius:50%;background:var(--Warning-warning-color, #F59E0B);'></span>Somme</span>").append("<span style='display:inline-flex;align-items:center;gap:6px;'><span aria-hidden='true' style='width:10px;height:10px;border:1px solid #d1d5db;border-radius:50%;background:#fff;'></span>Disponible</span>").append("</div>");
        html.append("<p data-apj-analysis-status role='status' aria-live='polite' style='min-height:18px;margin:10px 0 0;color:#6b7280;font-size:12px;'></p>");
        html.append("</section>");
        html.append("<script>\n").append("(function () {\n").append("    var root = document.getElementById('").append(idComposant).append("');\n").append("    if (!root) return;\n").append("\n").append("    function init() {\n").append("        var form = null;\n").append("        var formId = root.getAttribute('data-form-id');\n").append("        var contentWrapper = document.querySelector('.content-wrapper');\n").append("        var wrapperForms = contentWrapper ? contentWrapper.querySelectorAll('form') : [];\n").append("        for (var wrapperIndex = 0; wrapperIndex < wrapperForms.length; wrapperIndex++) {\n").append("            if (formId && wrapperForms[wrapperIndex].id === formId) {\n").append("                form = wrapperForms[wrapperIndex];\n").append("                break;\n").append("            }\n").append("        }\n").append("        if (!form && wrapperForms.length) form = wrapperForms[0];\n").append("        if (!form && formId) form = document.getElementById(formId);\n").append("        var status = root.querySelector('[data-apj-analysis-status]');\n").append("        var loading = root.querySelector('[data-apj-analysis-loading]');\n").append("        var source = root.querySelector('[data-apj-analysis-source]');\n").append("        var chips = root.querySelectorAll('[data-apj-analysis-chip]');\n").append("        var tables = document.querySelectorAll('table[data-apj-analysis-table]');\n").append("        var table = null;\n").append("        var selectedColumn = '';\n").append("        var submitting = false;\n").append("        var i;\n").append("\n").append("        for (i = 0; i < tables.length; i++) {\n").append("            if ((root.compareDocumentPosition(tables[i]) & 4) !== 0) {\n").append("                table = tables[i];\n").append("                break;\n").append("            }\n").append("        }\n").append("        if (!table && tables.length) table = tables[0];\n").append("        var zones = table ? table.querySelectorAll('[data-apj-analysis-field]') : [];\n").append("\n").append("        function showStatus(message, isError) {\n").append("            status.textContent = message || '';\n").append("            status.style.color = isError ? '#dc2626' : '#6b7280';\n").append("        }\n").append("\n").append("        function getField(fieldName) {\n").append("            return form && form.elements ? form.elements.namedItem(fieldName) : null;\n").append("        }\n").append("\n").append("        function hasOption(select, value) {\n").append("            if (!select || !select.options) return false;\n").append("            for (var index = 0; index < select.options.length; index++) {\n").append("                if (select.options[index].value === value) return true;\n").append("            }\n").append("            return false;\n").append("        }\n").append("\n").append("        function getZoneAxis(zone) {\n").append("            var fieldName = zone.getAttribute('data-apj-analysis-field') || '';\n").append("            if (fieldName.indexOf('colGroupe') === 0) return 'ligne';\n").append("            if (fieldName.indexOf('ligneGroupe') === 0) return 'colonne';\n").append("            if (fieldName.indexOf('colSomme') === 0) return 'somme';\n").append("            return '';\n").append("        }\n").append("\n").append("        function isAllowedColumn(value, axis) {\n").append("            for (var chipIndex = 0; chipIndex < chips.length; chipIndex++) {\n").append("                if (chips[chipIndex].getAttribute('data-column') !== value) continue;\n").append("                return true;\n").append("            }\n").append("            return false;\n").append("        }\n").append("\n").append("        function isSumAllowed(value) {\n").append("            for (var chipIndex = 0; chipIndex < chips.length; chipIndex++) {\n").append("                if (chips[chipIndex].getAttribute('data-column') !== value) continue;\n").append("                return chips[chipIndex].getAttribute('data-sum-allowed') === 'true';\n").append("            }\n").append("            return false;\n").append("        }\n").append("\n").append("        function getChipLabel(value) {\n").append("            if (!value) return '';\n").append("            for (var chipIndex = 0; chipIndex < chips.length; chipIndex++) {\n").append("                if (chips[chipIndex].getAttribute('data-column') !== value) continue;\n").append("                return chips[chipIndex].getAttribute('data-label') || value;\n").append("            }\n").append("            return value;\n").append("        }\n").append("\n").append("        function paintChip(chip, usedInfo, selected) {\n").append("            var used = !!usedInfo;\n").append("            var famille = usedInfo ? usedInfo.famille : null;\n").append("            var num = usedInfo ? usedInfo.num : null;\n").append("            chip.disabled = false;\n").append("            chip.setAttribute('aria-disabled', used ? 'true' : 'false');\n").append("            chip.setAttribute('draggable', used ? 'false' : 'true');\n").append("            chip.title = used ? 'D\u00e9j\u00e0 utilis\u00e9 dans le tableau' : '';\n").append("            var couleur = couleurFamille(famille);\n").append("            chip.style.border = used ? '2px solid ' + couleur : '1px solid ' + (selected ? '#2563eb' : '#d1d5db');\n").append("            chip.style.background = used ? '#ffffff' : (selected ? '#2563eb' : '#fff');\n").append("            chip.style.color = used ? 'var(--Text-primary, #1f2937)' : (selected ? '#fff' : '#374151');\n").append("            chip.style.opacity = '1';\n").append("            chip.style.cursor = used ? 'not-allowed' : 'grab';\n").append("            var cancelChip = chip.querySelector('[data-apj-analysis-cancel]');\n").append("            if (cancelChip) {\n").append("                cancelChip.style.display = used ? 'inline-flex' : 'none';\n").append("                cancelChip.style.color = used ? couleur : '#dc2626';\n").append("            }\n").append("            var numSpan = chip.querySelector('[data-chip-num]');\n").append("            if (numSpan) {\n").append("                if (used && famille === 'colSomme' && num) {\n").append("                    numSpan.textContent = num;\n").append("                    numSpan.style.display = 'inline-flex';\n").append("                } else {\n").append("                    numSpan.textContent = '';\n").append("                    numSpan.style.display = 'none';\n").append("                }\n").append("            }\n").append("        }\n").append("\n").append("        function getUsedColumns() {\n").append("            var used = {};\n").append("            for (var zoneIndex = 0; zoneIndex < zones.length; zoneIndex++) {\n").append("                var fieldName = zones[zoneIndex].getAttribute('data-apj-analysis-field') || '';\n").append("                var select = getField(fieldName);\n").append("                if (select && select.value && select.value !== '-') {\n").append("                    var fam = familleZone(zones[zoneIndex]);\n").append("                    var num = null;\n").append("                    var numMatch = fieldName.match(/\\d+$/);\n").append("                    if (numMatch) num = parseInt(numMatch[0], 10);\n").append("                    used[select.value] = { famille: fam, num: num, fieldName: fieldName };\n").append("                }\n").append("            }\n").append("            return used;\n").append("        }\n").append("\n").append("        function isColumnUsed(value) {\n").append("            return !!getUsedColumns()[value];\n").append("        }\n").append("\n").append("        function getSortScore(chip, usedInfo) {\n").append("            if (!usedInfo) {\n").append("                var orig = parseInt(chip.getAttribute('data-original-index'), 10);\n").append("                return 10000 + (isNaN(orig) ? 0 : orig);\n").append("            }\n").append("            if (usedInfo.famille === 'colSomme') return 100 + (usedInfo.num || 0);\n").append("            if (usedInfo.famille === 'colGroupe') return 200 + (usedInfo.num || 0);\n").append("            if (usedInfo.famille === 'ligneGroupe') return 300 + (usedInfo.num || 0);\n").append("            return 5000;\n").append("        }\n").append("\n").append("        function refreshUsedChips() {\n").append("            var used = getUsedColumns();\n").append("            for (var chipIndex = 0; chipIndex < chips.length; chipIndex++) {\n").append("                var chip = chips[chipIndex];\n").append("                var value = chip.getAttribute('data-column');\n").append("                var usedInfo = used[value] || null;\n").append("                if (usedInfo) chip.setAttribute('aria-pressed', 'false');\n").append("                paintChip(chip, usedInfo, chip.getAttribute('aria-pressed') === 'true');\n").append("            }\n").append("            var categories = root.querySelectorAll('[data-apj-analysis-category]');\n").append("            for (var catIndex = 0; catIndex < categories.length; catIndex++) {\n").append("                var ul = categories[catIndex].querySelector('ul');\n").append("                if (!ul) continue;\n").append("                var liItems = Array.prototype.slice.call(ul.children);\n").append("                liItems.sort(function (a, b) {\n").append("                    var chipA = a.querySelector('[data-apj-analysis-chip]');\n").append("                    var chipB = b.querySelector('[data-apj-analysis-chip]');\n").append("                    if (!chipA || !chipB) return 0;\n").append("                    var valA = chipA.getAttribute('data-column');\n").append("                    var valB = chipB.getAttribute('data-column');\n").append("                    return getSortScore(chipA, used[valA] || null) - getSortScore(chipB, used[valB] || null);\n").append("                });\n").append("                for (var sortIdx = 0; sortIdx < liItems.length; sortIdx++) {\n").append("                    ul.appendChild(liItems[sortIdx]);\n").append("                }\n").append("            }\n").append("            if (selectedColumn && used[selectedColumn]) {\n").append("                selectedColumn = '';\n").append("            }\n").append("        }\n").append("\n").append("        function selectChip(chip) {\n").append("            var chipLabel = chip.getAttribute('data-label') || chip.getAttribute('data-column') || 'Cette colonne';\n").append("            if (chip.disabled || chip.getAttribute('aria-disabled') === 'true') {\n").append("                showStatus(chipLabel + ' est d\\u00e9j\\u00e0 utilis\\u00e9e dans le tableau.', true);\n").append("                return;\n").append("            }\n").append("            selectedColumn = chip.getAttribute('data-column') || '';\n").append("            for (var chipIndex = 0; chipIndex < chips.length; chipIndex++) {\n").append("                var selected = chips[chipIndex] === chip;\n").append("                chips[chipIndex].setAttribute('aria-pressed', selected ? 'true' : 'false');\n").append("            }\n").append("            refreshUsedChips();\n").append("            showStatus(chipLabel + ' s\\u00e9lectionn\\u00e9e : touchez sa cible dans le tableau.', false);\n").append("        }\n").append("\n").append("        function paintZone(zone, highlighted, isError) {\n").append("            var famille = familleZone(zone);\n").append("            var couleur = isError ? '#dc2626' : couleurFamille(famille);\n").append("            var bg = isError ? '#fef2f2' : '';\n").append("            var targets = zone.tagName === 'TR' ? zone.querySelectorAll('th,td') : [zone];\n").append("            for (var targetIndex = 0; targetIndex < targets.length; targetIndex++) {\n").append("                targets[targetIndex].style.backgroundColor = bg;\n").append("                if (highlighted) {\n").append("                    if (!targets[targetIndex].hasAttribute('data-apj-analysis-original-border')) {\n").append("                        targets[targetIndex].setAttribute('data-apj-analysis-original-border', targets[targetIndex].style.border || '');\n").append("                        targets[targetIndex].setAttribute('data-apj-analysis-original-border-color', targets[targetIndex].style.borderColor || '');\n").append("                    }\n").append("                    if (targets[targetIndex].tagName === 'SPAN') {\n").append("                        targets[targetIndex].style.border = '2px dashed ' + couleur;\n").append("                    } else {\n").append("                        targets[targetIndex].style.borderColor = couleur;\n").append("                    }\n").append("                } else if (targets[targetIndex].hasAttribute('data-apj-analysis-original-border')) {\n").append("                    targets[targetIndex].style.border = targets[targetIndex].getAttribute('data-apj-analysis-original-border');\n").append("                    targets[targetIndex].style.borderColor = targets[targetIndex].getAttribute('data-apj-analysis-original-border-color');\n").append("                    targets[targetIndex].removeAttribute('data-apj-analysis-original-border');\n").append("                    targets[targetIndex].removeAttribute('data-apj-analysis-original-border-color');\n").append("                }\n").append("            }\n").append("        }\n").append("\n").append("        function highlightField(fieldName, highlighted, isError) {\n").append("            for (var zoneIndex = 0; zoneIndex < zones.length; zoneIndex++) {\n").append("                if (zones[zoneIndex].getAttribute('data-apj-analysis-field') === fieldName) {\n").append("                    paintZone(zones[zoneIndex], highlighted, isError);\n").append("                }\n").append("            }\n").append("        }\n").append("\n").append("        function setBusy() {\n").append("            root.setAttribute('aria-busy', 'true');\n").append("            loading.style.display = 'inline';\n").append("            source.style.pointerEvents = 'none';\n").append("            source.style.opacity = '0.55';\n").append("            if (table) {\n").append("                table.style.pointerEvents = 'none';\n").append("                table.style.opacity = '0.6';\n").append("            }\n").append("        }\n").append("\n").append("        function dispatchChange(select) {\n").append("            var changeEvent;\n").append("            if (typeof Event === 'function') {\n").append("                changeEvent = new Event('change', { bubbles: true });\n").append("            } else {\n").append("                changeEvent = document.createEvent('Event');\n").append("                changeEvent.initEvent('change', true, true);\n").append("            }\n").append("            select.dispatchEvent(changeEvent);\n").append("        }\n").append("\n").append("        function isGroupField(nom) {\n").append("            return nom.indexOf('colGroupe') === 0 || nom.indexOf('ligneGroupe') === 0 || nom.indexOf('colSomme') === 0;\n").append("        }\n").append("\n").append("        function familleDeCle(cle) {\n").append("            if (cle.indexOf('colGroupe') === 0) return 'colGroupe';\n").append("            if (cle.indexOf('ligneGroupe') === 0) return 'ligneGroupe';\n").append("            if (cle.indexOf('colSomme') === 0) return 'colSomme';\n").append("            return null;\n").append("        }\n").append("\n").append("        function familleZone(zone) {\n").append("            return familleDeCle(zone.getAttribute('data-apj-analysis-field') || '');\n").append("        }\n").append("\n").append("        function couleurFamille(famille) {\n").append("            if (famille === 'colGroupe') return 'var(--Success-success-color, #22C55E)';\n").append("            if (famille === 'ligneGroupe') return 'var(--Primaire-blue-main-color, #2B68D9)';\n").append("            if (famille === 'colSomme') return 'var(--Warning-warning-color, #F59E0B)';\n").append("            return '#2563eb';\n").append("        }\n").append("\n").append("        function lireParametresUrl() {\n").append("            var params = {};\n").append("            var requete = window.location.search || '';\n").append("            requete = requete.charAt(0) === '?' ? requete.substring(1) : requete;\n").append("            var morceaux = requete.split('&');\n").append("            for (var m = 0; m < morceaux.length; m++) {\n").append("                if (!morceaux[m]) continue;\n").append("                var egal = morceaux[m].indexOf('=');\n").append("                var cle = egal === -1 ? morceaux[m] : morceaux[m].substring(0, egal);\n").append("                var valeur = egal === -1 ? '' : morceaux[m].substring(egal + 1);\n").append("                try { cle = decodeURIComponent(cle); } catch (e) {}\n").append("                try { valeur = decodeURIComponent(valeur); } catch (e) {}\n").append("                params[cle] = valeur;\n").append("            }\n").append("            return params;\n").append("        }\n").append("\n").append("        function criteresFormulaire() {\n").append("            var params = {};\n").append("            if (!form || !form.elements) return params;\n").append("            for (var c = 0; c < form.elements.length; c++) {\n").append("                var el = form.elements[c];\n").append("                if (!el || !el.name || el.disabled) continue;\n").append("                if (isGroupField(el.name)) continue;\n").append("                if ((el.type === 'checkbox' || el.type === 'radio') && !el.checked) continue;\n").append("                var valeur = el.value === undefined ? '' : el.value;\n").append("                if (valeur === null || valeur === '' || valeur === '-') continue;\n").append("                params[el.name] = valeur;\n").append("            }\n").append("            return params;\n").append("        }\n").append("\n").append("        function obtenirGroupesNettoyes(familleAjout, valeurAjout, valeurRetrait) {\n").append("            var url = lireParametresUrl();\n").append("            var parFamille = { 'colGroupe': [], 'ligneGroupe': [], 'colSomme': [] };\n").append("\n").append("            function ajouterValeur(fam, val) {\n").append("                if (!fam || !val || val === '' || val === '-') return;\n").append("                if (valeurRetrait && val.toLowerCase() === valeurRetrait.toLowerCase()) return;\n").append("                if (!parFamille[fam]) parFamille[fam] = [];\n").append("                for (var v = 0; v < parFamille[fam].length; v++) {\n").append("                    if (parFamille[fam][v].toLowerCase() === val.toLowerCase()) return;\n").append("                }\n").append("                parFamille[fam].push(val);\n").append("            }\n").append("\n").append("            var clesSorted = [];\n").append("            for (var keyUrl in url) {\n").append("                if (url.hasOwnProperty(keyUrl) && isGroupField(keyUrl)) {\n").append("                    var fUrl = familleDeCle(keyUrl);\n").append("                    var nUrl = parseInt(keyUrl.replace(/^[a-zA-Z]+/, ''), 10);\n").append("                    clesSorted.push({ key: keyUrl, fam: fUrl, num: isNaN(nUrl) ? 99 : nUrl, val: url[keyUrl] });\n").append("                }\n").append("            }\n").append("            clesSorted.sort(function (a, b) { return a.num - b.num; });\n").append("            for (var idx = 0; idx < clesSorted.length; idx++) {\n").append("                ajouterValeur(clesSorted[idx].fam, clesSorted[idx].val);\n").append("            }\n").append("\n").append("            if (form && form.elements) {\n").append("                for (var s = 0; s < form.elements.length; s++) {\n").append("                    var el = form.elements[s];\n").append("                    if (!el || !el.name || el.disabled || !isGroupField(el.name)) continue;\n").append("                    var famEl = familleDeCle(el.name);\n").append("                    if (parFamille[famEl]) {\n").append("                        var valEl = el.value === undefined ? '' : el.value;\n").append("                        ajouterValeur(famEl, valEl);\n").append("                    }\n").append("                }\n").append("            }\n").append("\n").append("            if (familleAjout && valeurAjout) {\n").append("                for (var fOther in parFamille) {\n").append("                    if (!parFamille.hasOwnProperty(fOther)) continue;\n").append("                    var idxVal = parFamille[fOther].indexOf(valeurAjout);\n").append("                    if (idxVal !== -1) parFamille[fOther].splice(idxVal, 1);\n").append("                }\n").append("                ajouterValeur(familleAjout, valeurAjout);\n").append("            }\n").append("\n").append("            var resultat = {};\n").append("            var familles = ['colGroupe', 'ligneGroupe', 'colSomme'];\n").append("            for (var fIdx = 0; fIdx < familles.length; fIdx++) {\n").append("                var famNom = familles[fIdx];\n").append("                var listeVals = parFamille[famNom];\n").append("                for (var lIdx = 0; lIdx < listeVals.length; lIdx++) {\n").append("                    resultat[famNom + (lIdx + 1)] = listeVals[lIdx];\n").append("                }\n").append("            }\n").append("            return resultat;\n").append("        }\n").append("\n").append("        function serialiser(params) {\n").append("            var ordre = { 'colGroupe': 0, 'ligneGroupe': 1, 'colSomme': 2 };\n").append("            var groupes = [];\n").append("            var autres = [];\n").append("            for (var cle in params) {\n").append("                if (!params.hasOwnProperty(cle)) continue;\n").append("                if (isGroupField(cle)) {\n").append("                    var fam = familleDeCle(cle);\n").append("                    var num = parseInt(cle.replace(/^[a-zA-Z]+/, ''), 10);\n").append("                    groupes.push({ cle: cle, valeur: params[cle], range: ordre[fam] === undefined ? 9 : ordre[fam], num: isNaN(num) ? 9999 : num });\n").append("                } else {\n").append("                    autres.push(cle);\n").append("                }\n").append("            }\n").append("            groupes.sort(function (a, b) {\n").append("                if (a.range !== b.range) return a.range - b.range;\n").append("                return a.num - b.num;\n").append("            });\n").append("            autres.sort();\n").append("            var partie = [];\n").append("            for (var g = 0; g < groupes.length; g++) {\n").append("                partie.push(encodeURIComponent(groupes[g].cle) + '=' + encodeURIComponent(groupes[g].valeur));\n").append("            }\n").append("            for (var a = 0; a < autres.length; a++) {\n").append("                var cleA = autres[a];\n").append("                partie.push(encodeURIComponent(cleA) + '=' + encodeURIComponent(params[cleA]));\n").append("            }\n").append("            return partie.join('&');\n").append("        }\n").append("\n").append("        function rechargerAnalyse(famille, valeurAjout, valeurRetrait) {\n").append("            var params = lireParametresUrl();\n").append("            var criteres = criteresFormulaire();\n").append("            var groupesNettoyes = obtenirGroupesNettoyes(famille, valeurAjout, valeurRetrait);\n").append("            var resultat = {};\n").append("            for (var c in criteres) if (criteres.hasOwnProperty(c)) resultat[c] = criteres[c];\n").append("            for (var p in params) {\n").append("                if (params.hasOwnProperty(p) && !isGroupField(p) && !resultat.hasOwnProperty(p)) {\n").append("                    resultat[p] = params[p];\n").append("                }\n").append("            }\n").append("            for (var g in groupesNettoyes) if (groupesNettoyes.hasOwnProperty(g)) resultat[g] = groupesNettoyes[g];\n").append("            submitting = true;\n").append("            setBusy();\n").append("            showStatus('Actualisation de l\\u2019analyse...', false);\n").append("            window.location.href = window.location.pathname + '?' + serialiser(resultat);\n").append("        }\n").append("\n").append("        function applyValue(zone, value) {\n").append("            if (submitting || !value) return;\n").append("            var targetAxis = getZoneAxis(zone);\n").append("            var label = getChipLabel(value);\n").append("            if (targetAxis === 'somme' && !isSumAllowed(value)) {\n").append("                var msg = 'Impossible de sommer la colonne non numerique ' + label + '.';\n").append("                showStatus(msg, true);\n").append("                alert('Erreur : Impossible de sommer la colonne non numerique \\'' + label + '\\'. Seuls les nombres peuvent etre sommes.');\n").append("                return;\n").append("            }\n").append("            if (!isAllowedColumn(value, targetAxis)) {\n").append("                showStatus('La colonne ' + label + ' n\\u2019est pas autoris\\u00e9e sur l\\u2019axe ' + targetAxis + '.', true);\n").append("                return;\n").append("            }\n").append("            if (isColumnUsed(value)) {\n").append("                showStatus(label + ' est d\\u00e9j\\u00e0 utilis\\u00e9e dans le tableau.', true);\n").append("                return;\n").append("            }\n").append("            var famille = familleZone(zone);\n").append("            rechargerAnalyse(famille, value, null);\n").append("        }\n").append("\n").append("        if (!form || !table || zones.length === 0) {\n").append("            source.style.pointerEvents = 'none';\n").append("            source.style.opacity = '0.55';\n").append("            showStatus(!form ? 'Formulaire d\\u2019analyse introuvable.' : 'Tableau d\\u2019analyse introuvable.', true);\n").append("            return;\n").append("        }\n").append("\n").append("        for (i = 0; i < chips.length; i++) {\n").append("            chips[i].setAttribute('data-original-index', i.toString());\n").append("            chips[i].addEventListener('click', function () { selectChip(this); });\n").append("            chips[i].addEventListener('dragstart', function (event) {\n").append("                if (this.disabled || this.getAttribute('aria-disabled') === 'true') {\n").append("                    event.preventDefault();\n").append("                    return;\n").append("                }\n").append("                selectChip(this);\n").append("                event.dataTransfer.effectAllowed = 'copy';\n").append("                event.dataTransfer.setData('text/plain', this.getAttribute('data-column'));\n").append("                this.style.opacity = '0.55';\n").append("            });\n").append("            chips[i].addEventListener('dragend', function () {\n").append("                this.style.opacity = '1';\n").append("                for (var zoneIndex = 0; zoneIndex < zones.length; zoneIndex++) paintZone(zones[zoneIndex], false, false);\n").append("            });\n").append("            var cancelChip = chips[i].querySelector('[data-apj-analysis-cancel]');\n").append("            if (cancelChip) cancelChip.addEventListener('click', function (event) {\n").append("                event.stopPropagation();\n").append("                event.preventDefault();\n").append("                var chip = this.closest('[data-apj-analysis-chip]');\n").append("                var value = chip ? chip.getAttribute('data-column') : '';\n").append("                if (!value) return;\n").append("                selectedColumn = '';\n").append("                rechargerAnalyse(null, null, value);\n").append("            });\n").append("        }\n").append("\n").append("        for (i = 0; i < zones.length; i++) {\n").append("            zones[i].addEventListener('dragover', function (event) {\n").append("                event.preventDefault();\n").append("                var axis = getZoneAxis(this);\n").append("                var isSumNotAllowed = axis === 'somme' && !!selectedColumn && !isSumAllowed(selectedColumn);\n").append("                var allowed = !!selectedColumn && !isColumnUsed(selectedColumn) && isAllowedColumn(selectedColumn, axis);\n").append("                event.dataTransfer.dropEffect = allowed ? 'copy' : 'none';\n").append("                if (isSumNotAllowed) {\n").append("                    highlightField(this.getAttribute('data-apj-analysis-field'), true, true);\n").append("                    showStatus('Impossible de sommer la colonne \\'' + getChipLabel(selectedColumn) + '\\' car ce n est pas un nombre.', true);\n").append("                } else {\n").append("                    highlightField(this.getAttribute('data-apj-analysis-field'), allowed, false);\n").append("                }\n").append("            });\n").append("            zones[i].addEventListener('dragleave', function (event) {\n").append("                if (!event.relatedTarget || !this.contains(event.relatedTarget)) {\n").append("                    highlightField(this.getAttribute('data-apj-analysis-field'), false, false);\n").append("                }\n").append("            });\n").append("            zones[i].addEventListener('drop', function (event) {\n").append("                event.preventDefault();\n").append("                event.stopPropagation();\n").append("                highlightField(this.getAttribute('data-apj-analysis-field'), false);\n").append("                applyValue(this, event.dataTransfer.getData('text/plain') || selectedColumn);\n").append("            });\n").append("            zones[i].addEventListener('click', function (event) {\n").append("                if (!selectedColumn) return;\n").append("                event.preventDefault();\n").append("                event.stopPropagation();\n").append("                applyValue(this, selectedColumn);\n").append("            });\n").append("            zones[i].addEventListener('keydown', function (event) {\n").append("                if ((event.key === 'Enter' || event.key === ' ') && selectedColumn) {\n").append("                    event.preventDefault();\n").append("                    applyValue(this, selectedColumn);\n").append("                }\n").append("            });\n").append("        }\n").append("\n").append("        form.addEventListener('submit', setBusy);\n").append("        for (var zoneIndex = 0; zoneIndex < zones.length; zoneIndex++) paintZone(zones[zoneIndex], false);\n").append("        refreshUsedChips();\n").append("    }\n").append("\n").append("    if (document.readyState === 'loading') document.addEventListener('DOMContentLoaded', init);\n").append("    else init();\n").append("})();\n").append("</script>");
        return html.toString();
    }

    public String getDragAndDropColumnChangerHtml(String idForm, LinkedHashMap<String, String[]> rubriques, String[] colonnesSomme) throws Exception {
        this.setRubriquesAnalyse(rubriques);
        this.setColonnesSommeAutorisees(colonnesSomme);
        return this.getDragAndDropColumnChangerHtml(idForm);
    }

    private void ajouterChipAnalyse(StringBuilder html, bean.Champ colonne, boolean sommeAutorisee) {
        String nomEchappe = Utilitaire.escapeHtml(colonne.getNomColonne());
        String libelleEchappe = Utilitaire.escapeHtml(StringEscapeUtils.unescapeHtml4((String)colonne.getLibelleAfficheAnnotation()));
        html.append("<li style='display:block;'><button type='button' data-apj-analysis-chip data-column='").append(nomEchappe).append("' data-label='").append(libelleEchappe).append("' data-sum-allowed='").append(sommeAutorisee).append("' draggable='true' aria-pressed='false' style='display:inline-flex;align-items:center;gap:4px;min-height:36px;padding:8px 10px;color:var(--Text-primary);background:var(--White,#fff);border:1px solid var(--Input-Border);border-radius:6px;cursor:grab;font:inherit;line-height:1;text-align:start'>").append("<span aria-hidden='true' style='color:#9ca3af;font-size:16px;line-height:1;'>&#8942;&#8942;</span>").append("<span data-chip-label>").append(libelleEchappe).append("</span>").append("<span data-chip-num style='display:none;align-items:center;justify-content:center;width:18px;height:18px;font-size:11px;font-weight:700;border-radius:4px;background:var(--Warning-warning-color, #F59E0B);color:#ffffff;margin-left:2px;'></span>").append("<span class='material-symbols-rounded' data-apj-analysis-cancel title='Retirer la colonne' aria-label='Retirer la colonne' style='display:none;color:#dc2626;font-size:20px;line-height:1;cursor:pointer;'>cancel</span>").append("</button></li>");
    }

    public void makePagination() throws Exception {
        String lang = String.valueOf(this.getReq().getSession().getAttribute("lang"));
        ResourceBundle RB = ResourceBundle.getBundle("text", new Locale(lang));
        int pageCutLow = this.getNumPage() - 1;
        int pageCutHigh = this.getNumPage() + 1;
        String retour = "";
        retour = retour + "<nav aria-label=\"Page navigation\" >\r\n<ul class=\"pagination\">\r\n";
        retour = retour + "<li><span class='page-link btn btn-secondary btn-small' >Page: " + this.getNumPage() + " sur " + this.getNombrePage() + " </span></li>\r\n";
        retour = retour + "<li class=\"page-item" + (this.getNumPage() <= 1 ? " disabled" : "") + "\"><a class=\"page-link btn btn-secondary btn-small\" href=" + (this.getNumPage() > 1 ? this.getLien() + "?but=" + this.getApres() + "&recap=" + this.getFormu().getRecapcheck() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNumPage() - 1) + this.getApresLienPage() : "#") + "><i  class=\"material-symbols-rounded\">chevron_left</i></a></li>\r\n";
        retour = retour + "<li class=\"page-item" + (this.getNumPage() >= this.getNombrePage() ? " disabled" : "") + "\"><a class=\"page-link btn btn-secondary btn-small\" href=" + (this.getNumPage() < this.getNombrePage() ? this.getLien() + "?but=" + this.getApres() + "&recap=" + this.getFormu().getRecapcheck() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNumPage() + 1) + this.getApresLienPage() : "#") + "><i  class=\"material-symbols-rounded\">chevron_right</i></a></li>\r\n";
        retour = retour + "<li><button type='button'  class=\"page-link btn btn-secondary btn-small\" href=\"#\" onclick='expandTable()' ><i  class=\"material-symbols-rounded\">unfold_more</i></button></li>\r\n";
        retour = retour + "</ul>\r\n</nav>";
        retour = retour + "<script>";
        retour = retour + "function expandTable() {\n    // Toggle la classe .expand sur l'\u00c3\u00a9l\u00c3\u00a9ment .box.table-box\n    const tableBox = document.querySelector('.box.table-box');\n    if (tableBox) {\n        tableBox.classList.toggle('expand');\n    }\n\n    // Toggle entre unfold_more et unfold_less sur l'ic\u00c3\u00b4ne Material Symbols\n    const expandIcon = document.querySelector('.expand-icon');\n    if (expandIcon) {\n        if (expandIcon.textContent.trim() === 'unfold_more') {\n            expandIcon.textContent = 'unfold_less';\n        } else {\n            expandIcon.textContent = 'unfold_more';\n        }\n    }\n}\n";
        retour = retour + "</script>";
        this.getTableau().setPagination(retour);
        this.setNbObject(this.getListe().length + " r&eacute;sultats sur " + Utilitaire.formaterSansVirgule(this.rs.getSommeEtNombre()[this.getNombreColonneSomme()]));
    }

    public Map<String, String[]> transformerEtiquetteToMap() throws Exception {
        HashMap<String, String[]> map = new HashMap<String, String[]>();
        try {
            ValeurEtiquette[][] val = this.getTableau().getValeurEtiquette();
            for (int i = 0; i < val.length; ++i) {
                for (int j = 0; j < val[i].length; ++j) {
                    String[] tabString = val[i][1].getValeur().split("<BR>");
                    for (int k = 0; k < tabString.length; ++k) {
                        tabString[k] = tabString[k].replace(" ", "");
                    }
                    if (val[i][0].getValeur().trim().isEmpty()) continue;
                    map.put(val[i][0].getValeur(), tabString);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la transformation");
        }
        return map;
    }
}

