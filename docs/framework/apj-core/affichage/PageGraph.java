/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package affichage;

import affichage.ChartOption;
import affichage.Charts;
import affichage.PageRechercheGroupe;
import affichage.TableauRecherche;
import bean.ClassMAPTable;
import java.lang.reflect.Field;
import javax.servlet.http.HttpServletRequest;
import utilitaire.Utilitaire;

public class PageGraph
extends PageRechercheGroupe {
    private String jsonx = "";
    private String jsony = "";
    private int height = 230;
    private ChartOption chartOption;
    private String[] couleur;
    private int x = -1;
    private int y = -1;
    private Charts chart = new Charts();
    private String nomBoutonImprimer = "Imprimer";
    private String entete = "Graphique";
    private String chartName = "barChart";

    public PageGraph(ClassMAPTable o, HttpServletRequest req, String[] vrt, String[] listInterval, int nbRange, String[] colGr, String[] sommGr, int nbCol, int somGr) throws Exception {
        super(o, req, vrt, listInterval, nbRange, colGr, sommGr, nbCol, somGr);
        this.couleur = new String[this.getNbColGroupe() + this.getNbSommeGrp()];
    }

    public String getJsonx() {
        return this.jsonx;
    }

    public void setJsonx(String jsonx) {
        this.jsonx = jsonx;
    }

    public String getJsony() {
        return this.jsony;
    }

    public void setJsony(String jsony) {
        this.jsony = jsony;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Charts getChart() {
        return this.chart;
    }

    public void setChart(Charts chart) {
        this.chart = chart;
    }

    @Override
    public void creerObjetPage() throws Exception {
        System.out.println("order =============" + this.getOrdre());
        this.getValeurFormulaire();
        this.preparerData();
        this.makeTableauRecap();
        String[] enteteAuto = Utilitaire.ajouterTableauString(this.getColGroupe(), this.getSommeGroupe());
        String critereLienTab = "<a href=" + this.getLien() + "?but=" + this.getApres() + "&numPag=1" + this.getApresLienPage() + this.formu.getListeCritereString();
        ClassMAPTable crt = this.getCritere();
        Field[] listF = crt.getClass().getDeclaredFields();
        this.setTableau(new TableauRecherche(this.getListe(), enteteAuto, critereLienTab, listF));
        this.verifierXY();
        this.chartOption = new ChartOption(this.getTableau(), this.getChart(), this.couleur, this.x, this.y, this.getNbSommeGrp());
        this.chartOption.setParentName(this.chartName);
        this.chartOption.setData();
    }

    @Override
    public void makeHtml() {
        String text = " <!--BAR CHART--> \n          <div class=\"box box-success\">\n            <div class=\"box-header with-border\">\n              <h3 class=\"box-title\">" + this.getTitre() + "</h3>\n\n            </div>\n            <div class=\"box-body\">\n              <div class=\"chart\">\n                <canvas id=\"" + this.chartName + "\" style=\"height:" + this.getHeight() + "px\"></canvas>\n              </div>\n            </div>\n             <!--/.box-body--> \n          </div>" + this.chartOption.getScript() + "\n";
        this.setHtml(text);
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(text);
        System.out.println("-------------------------------------------------------------------------");
    }

    public ChartOption getChartOption() {
        return this.chartOption;
    }

    public void setChartOption(ChartOption chartOption) {
        this.chartOption = chartOption;
    }

    public int getIndiceColone(String colonneString) {
        int colonne = -1;
        String[] entete = Utilitaire.concatener(this.getColGroupe(), this.getColSomme());
        int taille = entete.length;
        for (int i = 0; i < taille; ++i) {
            if (!entete[i].equalsIgnoreCase(colonneString)) continue;
            colonne = i;
            break;
        }
        return colonne;
    }

    public void setCouleur(String colonne, String color) throws Exception {
        int indice = this.getIndiceColone(colonne);
        if (indice < 0) {
            throw new Exception("Colonne introuvable");
        }
        this.couleur[indice] = color;
    }

    private void verifierXY() {
        int difference = this.getTableau().getLibeEntete().length - this.getNbSommeGrp();
        if (this.getTableau() != null && this.x > difference) {
            this.x = 0;
            this.y -= difference;
        }
    }

    public int getX() {
        return this.x;
    }

    public void setX(String colonne) {
        this.x = this.getIndiceColone(colonne);
    }

    public void setY(int Y) {
        this.y = Y >= this.getNbSommeGrp() + this.getNbColGroupe() ? this.getNbColGroupe() : Y;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x > this.getNbColGroupe() ? 0 : x;
    }

    public void setY(String colonne) {
        this.y = this.getIndiceColone(colonne);
    }

    public void setCouleur(String[] couleurs) {
        this.couleur = couleurs;
    }

    @Override
    public String getBasPage() {
        String temp = "<form action=\"../ChartServlet\" methode=\"post\">\n  <input type=\"hidden\" name=\"data\" value=\"" + this.chartOption.getParametrePdf() + "\">\n  <input type=\"hidden\" name=\"chart\" value=\"" + this.getChart().getType() + "\">\n  <input type=\"hidden\" name=\"titre\" value=\"" + this.getTitre() + "\">\n  <input type=\"hidden\" name=\"entete\" value=\"" + this.getEntete() + "\">\n  <input class=\"btn btn-default\" type=\"submit\" value=\"" + this.getNomBoutonImprimer() + "\">\n</form>";
        return temp;
    }

    public String getNomBoutonImprimer() {
        return this.nomBoutonImprimer;
    }

    public void setNomBoutonImprimer(String nomBoutonImprimer) {
        this.nomBoutonImprimer = nomBoutonImprimer;
    }

    public String getEntete() {
        return this.entete;
    }

    public void setEntete(String entete) {
        this.entete = entete;
    }

    public String getChartName() {
        return this.chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    @Override
    public void makeCritere() throws Exception {
        String ordreTempo = this.getOrdre();
        super.makeCritere();
        if (ordreTempo != null && ordreTempo.compareTo("") != 0) {
            this.setOrdre(ordreTempo);
        }
        if (this.getOrdre() == null) {
            this.setOrdre(" order by " + Utilitaire.tabToString(this.getColGroupe(), "", ",") + " asc");
        }
    }
}

