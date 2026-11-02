/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import affichage.Charts;
import affichage.Couleur;
import affichage.ObjectChart;
import affichage.TableauRecherche;
import bean.CGenUtil;
import bean.ClassMAPTable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import utilitaire.Utilitaire;

public class ChartOption {
    private TableauRecherche tableau;
    private int x;
    private int y;
    private Charts chart;
    private String[] couleur;
    private String parametrePdf;
    private int tailleSomme;
    private String donneScript;
    private List<ObjectChart> liste = new ArrayList<ObjectChart>();
    private String colOnClick;
    private String parentName;

    public ChartOption(TableauRecherche tableau, Charts chart, String[] couleur, int x, int y, int colsome) throws Exception {
        this.setTableau(tableau);
        this.setTailleSomme(colsome);
        this.setChart(chart);
        this.setCouleur(couleur);
        this.setX(x);
        this.setY(y);
    }

    public void setData() throws Exception {
        if (Charts.isCercle(this.getChart().getType())) {
            this.setDataCercle();
        } else {
            this.setDataAutre();
        }
        this.setparametrePdf();
    }

    private void setDataCercle() throws ParseException {
        String donnerTemp = "";
        String dataTemp = "";
        String labelTemp = "";
        String colorTemp = "";
        int taille = this.getTableau().getData().length;
        int indice = 0;
        String[][] donne = this.getTableau().getDataDirecte();
        int i = 0;
        while (i < taille) {
            this.liste = ObjectChart.getListe(donne, this.getX(), this.getY());
            if (indice == this.getCouleur().length) {
                indice = 0;
            }
            if (i != 0) {
                donnerTemp = donnerTemp + ",";
                dataTemp = dataTemp + ",";
                labelTemp = labelTemp + ",";
                colorTemp = colorTemp + ",";
            }
            NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
            dataTemp = dataTemp + nf.parse(donne[i][this.y]).doubleValue();
            labelTemp = labelTemp + "'" + donne[indice][this.x] + "'";
            colorTemp = colorTemp + "'" + this.couleur[indice] + "'";
            ++i;
            ++indice;
        }
        this.donneScript = "{\ndatasets:[{data: [" + dataTemp + "],backgroundColor:[" + colorTemp + "]}],\nlabels:[" + labelTemp + "]}\n;";
    }

    private void setparametrePdf() {
        this.parametrePdf = "";
        for (int i = 0; i < this.liste.size(); ++i) {
            this.parametrePdf = this.parametrePdf + this.liste.get(i).getDataToString();
        }
    }

    private void setDataAutre() throws Exception {
        int taille = this.getTableau().getData().length;
        System.out.println("------TAILLE " + taille);
        String[][] donne = this.getTableau().getDataDirecte();
        String[] entete = this.getTableau().getLibeEntete();
        String valeurChamp = null;
        String libelle = "";
        int tailleEntete = entete.length;
        String[] ordonne = new String[this.getTailleSomme()];
        String donneX = "";
        String couleurTemp = "#00a65a";
        for (int i = 0; i < taille; ++i) {
            valeurChamp = donne[i][this.x];
            if (i != 0) {
                libelle = libelle + ",";
            }
            libelle = libelle + "'" + valeurChamp + "'";
            int j = tailleEntete - this.getTailleSomme();
            int index = 0;
            while (j < tailleEntete) {
                this.liste.add(new ObjectChart(this.getTableau().getLibeEntete()[j], this.getTableau().getDataDirecte()[i][j], this.getTableau().getDataDirecte()[i][this.getX()]));
                valeurChamp = donne[i][j];
                if (ordonne[index] == null) {
                    ordonne[index] = "";
                }
                if (i != 0) {
                    int n = index;
                    ordonne[n] = ordonne[n] + ",";
                }
                NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
                int n = index;
                ordonne[n] = ordonne[n] + nf.parse(valeurChamp).doubleValue();
                if (i == taille - 1) {
                    if (index != 0) {
                        donneX = donneX + ",";
                    }
                    if (this.couleur[index] != null) {
                        couleurTemp = this.couleur[index];
                    }
                    donneX = donneX + "        {\n          label: \"" + this.tableau.getLibelleAffiche()[j] + "\",\n          backgroundColor:  \"" + couleurTemp + "\",\n          borderColor: \"" + couleurTemp + "\",\n          pointColor: \"" + couleurTemp + "\",\n          pointStrokeColor: \"" + couleurTemp + "\",\n          pointHighlightFill: \"#fff\",\n          pointHighlightStroke: \"" + couleurTemp + "\",\n          data: [" + ordonne[index] + "]\n        }";
                }
                ++j;
                ++index;
            }
        }
        this.donneScript = "{\n      labels: [" + libelle + "],\n      datasets: [\n" + donneX + "        ]    }; \n";
    }

    public String getScript() {
        String script = "<script>\n  $(function () {\nvar rectangleSet = false;" + this.getFunctionOnClick() + "    let data = " + this.donneScript + "    let chartCanvas = $(\"#" + this.parentName + "\").get(0).getContext(\"2d\");\n" + this.chart.getChartOption() + "  });\n</script>";
        return script;
    }

    public ChartOption() {
    }

    public TableauRecherche getTableau() {
        return this.tableau;
    }

    public String getParametrePdf() {
        return this.parametrePdf;
    }

    private void setParametrePdf(String parametrePdf) {
        this.parametrePdf = parametrePdf;
    }

    public int getTailleSomme() {
        return this.tailleSomme;
    }

    public void setTailleSomme(int tailleSomme) {
        this.tailleSomme = tailleSomme;
    }

    public void setTableau(TableauRecherche tableau) {
        this.tableau = tableau;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        if (x < 0 || this.tableau != null && x > this.getTableau().getLibeEntete().length) {
            x = 0;
        } else {
            this.x = x;
        }
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y < 0 || y < this.tableau.getLibeEntete().length - this.tailleSomme ? this.tableau.getLibeEntete().length - this.tailleSomme : y;
    }

    public Charts getChart() {
        return this.chart;
    }

    public void setChart(Charts chart) {
        this.chart = chart;
    }

    public String[] getCouleur() {
        return this.couleur;
    }

    public void setCouleur(String[] couleurs) throws Exception {
        block7: {
            int taille = 0;
            this.couleur = couleurs;
            if (this.getTableau() == null) break block7;
            taille = Charts.isCercle(this.chart.getType()) ? this.tableau.getData().length : this.tableau.getLibeEntete().length;
            if (couleurs == null || couleurs.length == 0) {
                Couleur color = new Couleur(0, 255, 0);
                Couleur[] colors = color.genererCouleur(100, taille);
                this.couleur = new String[taille];
                for (int i = 0; i < taille; ++i) {
                    this.couleur[i] = colors[i].getCouleur();
                }
            } else {
                this.couleur = new String[taille];
                Couleur color = new Couleur(50, 60, 50);
                Couleur[] colors = color.genererCouleur(50, taille);
                int j = 0;
                int i = 0;
                while (i < couleurs.length && i < taille) {
                    if (couleurs[i] != null) {
                        this.couleur[i] = couleurs[i];
                    } else {
                        this.couleur[i] = colors[j].getCouleur();
                        ++j;
                    }
                    ++i;
                }
                while (i < taille) {
                    this.couleur[i] = colors[j].getCouleur();
                    ++j;
                    ++i;
                }
            }
        }
    }

    public String getColOnClick() {
        return this.colOnClick;
    }

    public void setColOnClick(String colOnClick) {
        this.colOnClick = colOnClick;
    }

    public String getFunctionOnClick() {
        try {
            if (this.colOnClick != null) {
                String nomVariable = this.colOnClick;
                String toReturn = "let " + nomVariable + " = [";
                ClassMAPTable[] data = this.getTableau().getData();
                String[] values = new String[data.length];
                for (int i = 0; i < data.length; ++i) {
                    values[i] = CGenUtil.getValeurInsert(data[i], this.colOnClick);
                }
                toReturn = toReturn + Utilitaire.tabToString(values, "'", ",");
                toReturn = toReturn + "];";
                this.chart.setNomVariable(nomVariable);
                return toReturn;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}

