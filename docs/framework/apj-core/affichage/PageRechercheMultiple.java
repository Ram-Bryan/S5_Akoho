/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package affichage;

import affichage.Champ;
import affichage.Formulaire;
import affichage.Page;
import affichage.TableauRecherche;
import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.ResultatEtSomme;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class PageRechercheMultiple
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
    private String ordre = "";
    private String apresLienPage = "";
    private int npp = 0;
    private String[] tableauAffiche;
    private String[] tableauAfficheDefaut;
    private int nbTableauAffiche;
    private boolean premier = false;

    public void makeTableauAffiche() throws Exception {
        String[] temp = new String[this.getNbTableauAffiche()];
        for (int i = 0; i < temp.length; ++i) {
            temp[i] = this.getReq().getParameter("colAffiche" + (i + 1));
        }
        this.setTableauAffiche(Utilitaire.formerTableauGroupe(temp));
    }

    public void preparerDataList(Connection c) throws Exception {
        String recap = this.getReq().getParameter("recap");
        this.critere.setNomTable(this.getBase().getNomTable());
        this.makeCritere();
        if (recap != null && recap.compareToIgnoreCase("checked") == 0) {
            recap = "checked";
            this.getFormu().setRecapcheck(recap);
            this.rs = this.getUtilisateur().getDataPage(this.critere, this.getColInt(), this.getValInt(), this.getNumPage(), this.getAWhere(), this.getColSomme(), c, this.npp);
        } else {
            recap = "";
            this.rs = this.getUtilisateur().getDataPageMaxSansRecap(this.critere, this.getColInt(), this.getValInt(), this.getNumPage(), this.getAWhere(), this.getColSomme(), c, this.npp);
        }
        this.setListe((ClassMAPTable[])this.rs.getResultat());
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

    public void preparerData() throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            this.preparerDataList(c);
            this.formu.getAllData(c);
        }
    }

    public void makeCritere() throws Exception {
        Vector<String> colIntv = new Vector<String>();
        Vector<String> valIntv = new Vector<String>();
        Champ[] tempChamp = this.formu.getCrtFormu();
        String temp = "";
        for (int i = 0; i < tempChamp.length; ++i) {
            boolean recherchePrecis;
            Field f = CGenUtil.getField(this.getBase(), tempChamp[i].getNom());
            Champ champConfigure = this.formu.getChamp(tempChamp[i].getNom());
            boolean bl = recherchePrecis = champConfigure != null && champConfigure.isRecherchePrecis();
            if (tempChamp[i].getEstIntervalle() == 1) {
                colIntv.add(tempChamp[i].getNom());
                valIntv.add(this.getParamSansNull(tempChamp[i].getNom() + "1"));
                valIntv.add(this.getParamSansNull(tempChamp[i].getNom() + "2"));
                continue;
            }
            if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
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
            this.aWhere = this.aWhere + " and " + f.getName() + operateur + "'" + Utilitaire.getValeurNonNull(this.getParamSansNull(tempChamp[i].getNom())) + "'";
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
        if (this.formu.getChamp("colonne").getValeur() != null && this.formu.getChamp("colonne").getValeur().compareToIgnoreCase("") != 0) {
            this.setOrdre(" order by " + this.formu.getChamp("colonne").getValeur() + " " + this.formu.getChamp("ordre").getValeur());
        }
        this.aWhere = this.aWhere + this.getOrdre();
    }

    public void creerObjetPage(String[] libEntete) throws Exception {
        this.getValeurFormulaire();
        this.preparerData();
        this.makeTableauRecap();
        String critereLienTab = "<a href=" + this.getLien() + "?but=" + this.getApres() + "&numPag=1" + this.getApresLienPage() + this.formu.getListeCritereString() + "";
        this.setTableau(new TableauRecherche(this.getListe(), libEntete, critereLienTab));
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
        String critereLienTab = "<a href=" + this.getLien() + "?but=" + this.getApres() + "&numPag=1" + this.getApresLienPage() + this.formu.getListeCritereString();
        String[] enteteAuto = this.getTableauAffiche();
        if (this.getTableauAffiche() == null) {
            this.setTableau(new TableauRecherche(this.getListe(), libEnteteDefaut, critereLienTab));
        } else {
            this.setTableau(new TableauRecherche(this.getListe(), enteteAuto, critereLienTab));
        }
    }

    public PageRechercheMultiple(ClassMAPTable o, HttpServletRequest req, String[] vrt, String[] listInterval, int nbRange, String[] tabAff, int nbAff) throws Exception {
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
    }

    public PageRechercheMultiple(ClassMAPTable o, String[] vrt, String[] listInterval, int nbRange) throws Exception {
        this.setBase(o);
        this.setCritere((ClassMAPTable)Class.forName(o.getClassName()).newInstance());
        this.formu = new Formulaire();
        this.formu.setObjet(this.getBase());
        this.formu.makeChampFormu(vrt, listInterval, nbRange);
    }

    public PageRechercheMultiple() {
    }

    public PageRechercheMultiple(ClassMAPTable o, HttpServletRequest req, String[] vrt, String[] listInterval, int nbRange) throws Exception {
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

    public void makeTableauRecap() throws Exception {
        String[][] data = null;
        String[] entete = null;
        int nbColSomme = 0;
        if (this.getColSomme() != null) {
            nbColSomme = this.getColSomme().length;
        }
        entete = new String[nbColSomme + 2];
        data = new String[1][entete.length];
        entete[0] = "";
        data[0][0] = "Total";
        entete[1] = "Nombre";
        data[0][1] = Utilitaire.formaterSansVirgule(this.rs.getSommeEtNombre()[nbColSomme]);
        for (int i = 0; i < nbColSomme; ++i) {
            data[0][i + 2] = Utilitaire.formaterAr(String.valueOf(this.rs.getSommeEtNombre()[i]));
            entete[i + 2] = "Somme de " + String.valueOf(this.getColSomme()[i]);
        }
        TableauRecherche t = new TableauRecherche(data, entete);
        t.setTitre("RECAPITULATION");
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

    public boolean getPremier() {
        return this.premier;
    }

    public String getBasPage() {
        String retour = "";
        retour = retour + "<table border=0 cellpadding=0 cellspacing=0 align=center width='100%'>";
        retour = retour + "<tr><td height=25><b>Nombre de r&eacute;sultat :</b> " + Utilitaire.formaterSansVirgule(this.rs.getSommeEtNombre()[this.getNombreColonneSomme()]) + "</td><td align='right'><strong>page</strong> " + this.getNumPage() + " <b>sur</b> " + this.getNombrePage() + "</td>";
        retour = retour + "</tr>";
        retour = retour + "<tr>";
        retour = retour + "<td width=50% valign='top' height=25>";
        if (this.getNumPage() > 1) {
            retour = retour + "<a href=" + this.getLien() + "?but=" + this.getApres() + "&recap=" + this.getFormu().getRecapcheck() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNumPage() - 1) + this.getApresLienPage() + this.formu.getListeCritereString() + ">&lt;&lt;Page pr&eacute;c&eacute;dente</a>";
        }
        retour = retour + "</td>";
        retour = retour + "<td width=50% align=right>";
        if (this.getNumPage() < this.getNombrePage()) {
            retour = retour + "<a href=" + this.getLien() + "?but=" + this.getApres() + "&recap=" + this.getFormu().getRecapcheck() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNumPage() + 1) + this.getApresLienPage() + this.formu.getListeCritereString() + ">Page suivante&gt;&gt;</a>";
        }
        retour = retour + "</td>";
        retour = retour + "</tr>";
        retour = retour + "</table>";
        retour = retour + this.getDownloadForm();
        return retour;
    }

    public String getBasPagee() {
        String retour = "";
        retour = retour + "<table border=0 cellpadding=0 cellspacing=0 align=center width='100%'>";
        retour = retour + "<tr><td height=25><b>Nombre de r&eacute;sultat :</b> " + Utilitaire.formaterAr(String.valueOf(this.rs.getSommeEtNombre()[this.getNombreColonneSomme()])) + "</td><td align='right'><strong>page</strong> " + this.getNumPage() + " <b>sur</b> " + this.getNombrePage() + "</td>";
        retour = retour + "</tr>";
        retour = retour + "<tr>";
        retour = retour + "<td width=50% valign='top' height=25>";
        if (this.getNumPage() > 1) {
            retour = retour + "<a href=" + this.getApres() + "?a=1" + this.formu.getListeCritereString() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNumPage() - 1) + this.getApresLienPage() + "&champReturn=etudiant>&lt;&lt;Page pr&eacute;c&eacute;dente</a>";
        }
        retour = retour + "</td>";
        retour = retour + "<td width=50% align=right>";
        if (this.getNumPage() < this.getNombrePage()) {
            retour = retour + "<a href=" + this.getApres() + "?a=1" + this.formu.getListeCritereString() + "&premier=" + this.getPremier() + "&numPag=" + String.valueOf(this.getNumPage() + 1) + this.getApresLienPage() + "&champReturn=etudiant>Page suivante&gt;&gt;</a>";
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

    public void setNpp(int npp) {
        this.npp = npp;
    }

    public int getNpp() {
        return this.npp;
    }

    public String[] getTableauAffiche() {
        if (this.tableauAffiche == null || this.tableauAffiche.length == 0) {
            return this.tableauAfficheDefaut;
        }
        return this.tableauAffiche;
    }

    public String[] getTableauAfficheDefaut() {
        return this.tableauAfficheDefaut;
    }

    public void setTableauAfficheDefaut(String[] tableauAfficheDefaut) {
        this.tableauAfficheDefaut = tableauAfficheDefaut;
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

    public void setPremier(boolean premier) {
        this.premier = premier;
    }

    public boolean isPremier() {
        return this.premier;
    }

    public String getDownloadForm() {
        String tab = "";
        String tab1 = "";
        try {
            tab = this.getTableau().getHtml();
            tab1 = this.getTableau().getHtmlPDF();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String temp = "";
        temp = temp + "<div class='row'>";
        temp = temp + "<div class='row col-md-12'>";
        temp = temp + "<div class='box box-primary box-solid collapsed-box'>";
        temp = temp + "<div class='box-header with-border'>";
        temp = temp + "<h3 class='box-title'>Exporter</h3>";
        temp = temp + "<div class='box-tools pull-right'><button data-original-title='Collapse' class='btn btn-box-tool' data-widget='collapse' data-toggle='tooltip' title=''><i class='fa fa-plus'></i></button> </div>";
        temp = temp + "</div>";
        temp = temp + "<div class='box-body'>";
        temp = temp + "<form action=\"../download\" method=\"post\">";
        temp = temp + "<table id='export'>";
        temp = temp + "<tr>";
        temp = temp + "<th colspan='4'><h3 class='box-title'>Format</h3></th>";
        temp = temp + "<th colspan='2'><h3 class='box-title'>Donn&eacute;es &agrave; exporter</h3></th>";
        temp = temp + "<th></th>";
        temp = temp + "</tr>";
        temp = temp + "<tr>";
        temp = temp + "<td>";
        temp = temp + "<img src='../dist/img/csv_text.png'>";
        temp = temp + "<input type='radio' name='ext' value='csv' checked='checked' />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        temp = temp + "<img src='../dist/img/file_xls.png'>";
        temp = temp + "<input type='radio' name='ext' value='xls' />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        temp = temp + "<img src='../dist/img/file_xml.png'>";
        temp = temp + "<input type='radio' name='ext' value='xml' />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        temp = temp + "<img src='../dist/img/file_pdf.png'>";
        temp = temp + "<input type='radio' name='ext' value='pdf' />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        temp = temp + "<label for='donnee'>Page En Cours </label>";
        temp = temp + "<input type='radio' name='donnee' value=\"0\" checked='checked' />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        temp = temp + "<label for='donnee'>Toutes </label>";
        temp = temp + "<input type='radio' name='donnee' value=\"1\" />";
        temp = temp + "</td>";
        temp = temp + "<td>";
        if (this.getTableau().getExpxml() != null) {
            temp = temp + "<input type=\"hidden\" name=\"xml\" value=\"" + this.getTableau().getExpxml().replace('\"', '*') + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"csv\" value=\"" + this.getTableau().getExpcsv().replace('\"', '*') + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"awhere\" value=\"" + this.getAWhere().replace('\"', '\'') + "\" />";
            temp = temp + "<input type=\"hidden\" name=\"table\" value=\"" + tab1.replace('\"', '*') + "\" />";
        }
        temp = temp + "<input type='submit' value='Exporter' class='btn btn-default' />";
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
}

