/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  user.UserEJB
 */
package affichage;

import affichage.DashboardGraphType;
import affichage.DashboardPeriod;
import affichage.Formulaire;
import affichage.PageRecherche;
import affichage.PageRechercheGroupe;
import affichage.TableauRecherche;
import bean.ClassMAPTable;
import bean.ValeurEtiquette;
import constante.ConstanteDashboard;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import user.UserEJB;
import utilitaire.Utilitaire;

public class DashboardGraph {
    private String icon;
    private String[] couleurs;
    private String backgroundColor;
    private PageRecherche pageRecherche;
    private PageRechercheGroupe pageRechercheGroupe;
    private DashboardGraphType type;
    private String html;
    private boolean avecDate;
    private String lienPage;
    private String canvasId;
    private int colSpan;
    private String[] colValeur;
    private String[] colAffiche;
    private String[] colCroisee;
    private boolean sansRecap;
    private String recap;
    private String dateColonne;
    private String titre;
    private String lien;
    private String[] legendes;
    private boolean afficherLegende;
    private String positionLegende;
    private String orientation;
    private String ordre;
    private int limite;
    private String[] unite;
    private String[] typesDataset;
    private boolean multiAxes;
    private Map<String, String[]> dataChart;
    private int dynamicChartHeight;
    private int dynamicChartWidth;
    private int barHeightPx;
    private int chartPaddingPx;
    private int etat;
    private Map<String, String> couleursMapping;
    ClassMAPTable[] dataDirecteLigne;
    private DashboardPeriod periodeParDefaut;

    public Map<String, String> getCouleursMapping() {
        return this.couleursMapping;
    }

    public void setCouleursMapping(Map<String, String> couleursMapping) {
        this.couleursMapping = couleursMapping;
    }

    public DashboardPeriod getPeriodeParDefaut() {
        return this.periodeParDefaut;
    }

    public void setPeriodeParDefaut(DashboardPeriod periodeParDefaut) {
        this.periodeParDefaut = periodeParDefaut;
    }

    public ClassMAPTable[] getDataDirecteLigne() {
        if (this.dataDirecteLigne == null) {
            return this.getPageRecherche().getListe();
        }
        return this.dataDirecteLigne;
    }

    public void setDataDirecteLigne(ClassMAPTable[] dataDirecteLigne) {
        this.dataDirecteLigne = dataDirecteLigne;
    }

    public DashboardGraph() {
        this.setBackgroundColor(ConstanteDashboard.DEFAULT_GRAPH_BACKGROUND_COLOR);
        this.setType(DashboardGraphType.UNDEFINED);
        this.setCouleurs(ConstanteDashboard.DEFAULT_COLORS);
        this.setColSpan(4);
        this.setSansRecap(true);
        this.setAvecDate(true);
        this.setIcon("");
        this.setLienPage("#");
        this.setDateColonne("daty");
        this.setDynamicChartWidth(0);
        this.setDynamicChartHeight(300);
        this.setOrdre("");
        this.setOrientation("vertical");
        this.setPositionLegende("bottom");
        this.setAfficherLegende(true);
        this.setLimite(0);
        this.setMultiAxes(false);
        this.setBarHeightPx(28);
        this.setChartPaddingPx(80);
        this.setColCroisee(new String[0]);
        this.setPeriodeParDefaut(DashboardPeriod.MOIS);
    }

    public DashboardGraph(PageRecherche pageRecherche, String[] colValeur, String[] colAffiche, String[] legendes, DashboardGraphType type) {
        this();
        this.setColValeur(colValeur);
        this.setColAffiche(colAffiche);
        this.setPageRecherche(pageRecherche);
        this.setType(type);
        this.setLegendes(legendes);
    }

    public DashboardGraph(PageRechercheGroupe pageRecherche, String[] legendes, DashboardGraphType type) {
        this();
        this.setPageRechercheGroupe(pageRecherche);
        this.setType(type);
        this.setColAffiche(pageRecherche.getColGroupe());
        this.setLegendes(legendes);
        this.setColValeur(pageRecherche.getColSomme());
    }

    public DashboardGraph(ClassMAPTable base, HttpServletRequest request, String[] colValeur, String[] colAffiche, String[] legendes, int npp, String[] pourcentage, String dateColonne, DashboardGraphType type) throws Exception {
        this();
        String[] critere = new String[]{};
        if (dateColonne != null && !dateColonne.isEmpty()) {
            critere = new String[]{dateColonne};
            this.setDateColonne(dateColonne);
            this.setAvecDate(true);
        }
        PageRechercheGroupe pageRechercheGroupe = new PageRechercheGroupe(base, request, critere, critere, 4, colAffiche, colValeur, pourcentage, colAffiche.length, colValeur.length);
        pageRechercheGroupe.setNpp(npp);
        this.setPageRechercheGroupe(pageRechercheGroupe);
        this.setColValeur(colValeur);
        this.setLegendes(legendes);
        this.setColAffiche(colAffiche);
        this.setType(type);
    }

    public DashboardGraph(ClassMAPTable base, HttpServletRequest req, String[] colValeur, String[] colAffiche, String[] legendes, String dateColonne, DashboardGraphType type) throws Exception {
        this();
        String[] critere = new String[]{};
        if (dateColonne != null && !dateColonne.isEmpty()) {
            critere = new String[]{dateColonne};
            this.setDateColonne(dateColonne);
            this.setAvecDate(true);
        }
        PageRecherche pageRecherche = new PageRecherche(base, req, critere, critere, 4, critere, critere.length);
        this.setColValeur(colValeur);
        this.setColAffiche(colAffiche);
        this.setPageRecherche(pageRecherche);
        this.setType(type);
        this.setLegendes(legendes);
    }

    public DashboardGraph(DashboardGraph sourceGraphe, DashboardGraphType type) {
        this();
        this.setPageRecherche(sourceGraphe.getPageRecherche());
        this.setPageRechercheGroupe(sourceGraphe.getPageRechercheGroupe());
        this.setDataChart(sourceGraphe.getDataChart());
        this.setType(type);
        this.setColValeur(sourceGraphe.getColValeur());
        this.setColSpan(sourceGraphe.getColSpan());
        this.setLegendes(sourceGraphe.getLegendes());
        this.setCouleurs(sourceGraphe.getCouleurs());
        this.setBackgroundColor(ConstanteDashboard.DEFAULT_GRAPH_BACKGROUND_COLOR);
        this.setTitre(sourceGraphe.getTitre());
        this.setUnite(sourceGraphe.getUnite() != null ? (String[])sourceGraphe.getUnite().clone() : null);
        this.setTypesDataset(sourceGraphe.getTypesDataset() != null ? (String[])sourceGraphe.getTypesDataset().clone() : null);
        this.setMultiAxes(sourceGraphe.isMultiAxes());
    }

    public DashboardGraph transformer(DashboardGraphType type) {
        return new DashboardGraph(this, type);
    }

    public void setPageRechercheGroupe(PageRechercheGroupe pageRechercheGroupe) {
        this.pageRechercheGroupe = pageRechercheGroupe;
    }

    public PageRechercheGroupe getPageRechercheGroupe() {
        return this.pageRechercheGroupe;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String[] getCouleurs() {
        return this.couleurs;
    }

    public void setCouleurs(String[] couleurs) {
        this.couleurs = couleurs;
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public PageRecherche getPageRecherche() {
        return this.pageRecherche;
    }

    public void setPageRecherche(PageRecherche pageRecherche) {
        this.pageRecherche = pageRecherche;
    }

    public DashboardGraphType getType() {
        return this.type;
    }

    public void setType(DashboardGraphType type) {
        this.type = type;
    }

    public void makeHtml() throws Exception {
        String html = "";
        switch (this.type) {
            case CARD: {
                html = this.makeCardHtml();
                break;
            }
            case PIE: {
                html = this.makePieHtml();
                html = html + this.getPieJavaScript();
                break;
            }
            case DOUGHNUT: {
                html = this.makePieHtml();
                html = html + this.getDoughnutJavaScript();
                break;
            }
            case BAR: {
                html = this.makeBarHtml();
                html = html + this.getBarJavaScript(false);
                break;
            }
            case STACKED_BAR: {
                html = this.makeStackedBarHtml();
                html = html + this.getBarJavaScript(true);
                break;
            }
            case LINE: {
                html = this.makeLineHtml();
                html = html + this.getLineJavaScript();
                break;
            }
            default: {
                if (this.pageRecherche != null) {
                    html = this.pageRecherche.getTableau().getHtml();
                }
                if (this.pageRechercheGroupe == null) break;
                html = this.pageRechercheGroupe.getTableau().getHtml();
            }
        }
        this.setHtml(html);
    }

    public void creerObjetGraph() throws Exception {
        if (this.getPageRechercheGroupe() != null) {
            this.preparerDataGroupe();
        }
        if (!this.isSansRecap() && this.getPageRecherche() != null) {
            this.setRecap(this.getPageRecherche().getTableauRecap().getDataDirecte()[0][1]);
        }
        if (this.getTitre() == null || this.getTitre().isEmpty()) {
            if (this.getPageRecherche() != null) {
                this.setTitre(this.getPageRecherche().getTitre());
            } else if (this.getPageRechercheGroupe() != null) {
                this.setTitre(this.getPageRechercheGroupe().getTitre());
            }
        }
        this.setLienPage(this.getLien() + "?but=" + this.getLienPage());
        this.setEtat(ConstanteDashboard.ETAT_GRAPH_CREE);
    }

    public boolean isAvecDate() {
        return this.avecDate;
    }

    public void setAvecDate(boolean avecDate) {
        this.avecDate = avecDate;
    }

    public String getLienPage() {
        return this.lienPage;
    }

    public void setLienPage(String lienPage) {
        this.lienPage = lienPage;
    }

    public void setCanvasId(String canvasId) {
        this.canvasId = canvasId;
    }

    public String getCanvasId() {
        if (this.canvasId == null) {
            return "canvas_" + this.getType().getVal() + "_" + System.currentTimeMillis();
        }
        return this.canvasId;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    public int getColSpan() {
        return this.colSpan;
    }

    public void setColValeur(String[] colValeur) {
        this.colValeur = colValeur;
    }

    public String[] getColValeur() {
        return this.colValeur;
    }

    public void setSansRecap(boolean sansRecap) {
        this.sansRecap = sansRecap;
    }

    public boolean isSansRecap() {
        return this.sansRecap;
    }

    public String getRecap() {
        return this.recap;
    }

    public void setRecap(String recap) {
        this.recap = recap;
    }

    public Formulaire getFormu() {
        if (this.pageRecherche != null) {
            return this.getPageRecherche().getFormu();
        }
        if (this.pageRechercheGroupe != null) {
            return this.getPageRechercheGroupe().getFormu();
        }
        return null;
    }

    public void setFormu(Formulaire formu) {
        if (this.pageRecherche != null) {
            this.pageRecherche.setFormu(formu);
        }
        if (this.pageRechercheGroupe != null) {
            this.pageRechercheGroupe.setFormu(formu);
        }
    }

    public String getDateColonne() {
        return this.dateColonne;
    }

    public void setDateColonne(String dateColonne) {
        this.dateColonne = dateColonne;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml() {
        return this.html;
    }

    public String getLien() {
        return this.lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public void setUtilisateur(UserEJB utilisateur) {
        if (this.pageRecherche != null) {
            this.pageRecherche.setUtilisateur(utilisateur);
        }
        if (this.pageRechercheGroupe != null) {
            this.pageRechercheGroupe.setUtilisateur(utilisateur);
        }
    }

    public int getDynamicChartHeight() {
        return this.dynamicChartHeight;
    }

    public void setDynamicChartHeight(int height) {
        this.dynamicChartHeight = height;
    }

    public int getDynamicChartWidth() {
        return this.dynamicChartWidth;
    }

    public void setDynamicChartWidth(int width) {
        this.dynamicChartWidth = width;
    }

    public int getBarHeightPx() {
        return this.barHeightPx;
    }

    public void setBarHeightPx(int barHeightPx) {
        this.barHeightPx = barHeightPx;
    }

    public int getChartPaddingPx() {
        return this.chartPaddingPx;
    }

    public void setChartPaddingPx(int chartPaddingPx) {
        this.chartPaddingPx = chartPaddingPx;
    }

    public void creerObjetPage(String[] libEnteteDefaut, String[] colSom) throws Exception {
        if (this.pageRecherche != null) {
            this.pageRecherche.creerObjetPage(libEnteteDefaut, colSom);
            this.setEtat(ConstanteDashboard.ETAT_PAGE_CREE);
        }
    }

    public void creerObjetPage() throws Exception {
        this.creerObjetPageCroise(this.getColCroisee(), this.getLienPage());
        this.creerObjetPage(this.getColValeur(), this.getColValeur());
    }

    public void creerObjetPage(String[] libEnteteDefaut, String[] colSom, Connection con) throws Exception {
        if (this.pageRecherche != null) {
            this.pageRecherche.creerObjetPage(libEnteteDefaut, colSom, con);
            this.setEtat(ConstanteDashboard.ETAT_PAGE_CREE);
        }
    }

    public void creerObjetPage(Connection con) throws Exception {
        this.creerObjetPageCroise(this.getColCroisee(), this.getLienPage(), con);
        this.creerObjetPage(this.getColValeur(), this.getColValeur(), con);
    }

    public void creerObjetPageCroise(String[] colon, String lien) throws Exception {
        if (this.pageRechercheGroupe != null) {
            this.pageRechercheGroupe.creerObjetPageCroise(colon, lien);
            this.setEtat(ConstanteDashboard.ETAT_PAGE_CREE);
        }
    }

    public void creerObjetPageCroise(String[] colon, String lien, Connection c) throws Exception {
        if (this.pageRechercheGroupe != null) {
            this.pageRechercheGroupe.creerObjetPageCroise(colon, lien, c);
            this.setEtat(ConstanteDashboard.ETAT_PAGE_CREE);
        }
    }

    public String makeUnitCardFooter(String libelle, String somme, int index) {
        return "<div class=\"dashboard-card-row\"><span class=\"Label12pxRegular dashboard-card-label\">" + libelle + "</span><span class=\"Body12pxRegular dashboard-card-value\">" + somme + this.getUniteAt(index) + "</span></div>";
    }

    public String makeCardFooter(String key, String[] values) {
        StringBuilder html = new StringBuilder("<div class=\"dashboard-card-footer\">");
        if (key == null && values == null && this.pageRecherche != null && this.colValeur.length > 1) {
            TableauRecherche recap = this.pageRecherche.getTableauRecap();
            for (int i = 0; i < this.pageRecherche.getColSomme().length; ++i) {
                html.append(this.makeUnitCardFooter(this.getLegendes()[i], recap.getDataDirecte()[0][i + 2], i));
            }
        } else if (key != null && values != null) {
            for (int i = 0; i < values.length; ++i) {
                html.append(this.makeUnitCardFooter(this.legendes[i], values[i], i));
            }
        } else {
            return "";
        }
        html.append("</div>");
        return html.toString();
    }

    public String makeCardTitle(String libelle, String somme) {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"dashboard-card-info\">");
        if (!this.isSansRecap()) {
            html.append("<div class=\"h520pxSemibold dashboard-card-title\">").append(this.getRecap()).append("</div>");
        }
        html.append("<div class=\"Body14pxSemiBold dashboard-card-subtitle-second\">").append(libelle).append("</div>");
        if (somme != null && !somme.isEmpty()) {
            html.append("<div class=\"dashboard-card-row Body14pxSemiBold\"><span class=\"dashboard-card-value\">").append(somme).append(this.getUniteAt(0)).append("</span></div>");
        }
        html.append("</div>");
        return html.toString();
    }

    public String makeCardHtml() {
        if (this.pageRecherche != null) {
            return this.makeCardHtml(null, null);
        }
        StringBuilder html = new StringBuilder();
        for (Map.Entry<String, String[]> entry : this.dataChart.entrySet()) {
            html.append(this.makeCardHtml(entry.getKey(), entry.getValue()));
        }
        return html.toString();
    }

    public String makeCardHtml(String key, String[] values) {
        String html = "<div id='" + this.getCanvasId() + "' class=\"col-md-" + this.getColSpan() + " col-sm-6\"><div class=\"dashboard-card-wrapper\"><a href=\"" + this.getLienPage() + "\" class=\"dashboard-card-link\"><div class=\"box dashboard-card clickable\" style=\"background-color: " + this.getBackgroundColor() + ";\"><div class=\"box-body\"><div class=\" dashboard-card-header\"><div class=\"dashboard-card-content\">";
        if (this.getIcon() != null && !this.getIcon().trim().isEmpty()) {
            html = html + "<div class=\"dashboard-card-avatar\"><span class=\"material-symbols-rounded\" style=\"font-size: 24px; color: var(--Text-primary);\">" + this.getIcon() + "</span></div>";
        }
        String somme = null;
        String libelle = this.getTitre();
        if (key == null && values == null && this.pageRecherche != null && this.colValeur.length == 1) {
            TableauRecherche recap = this.pageRecherche.getTableauRecap();
            somme = recap.getDataDirecte()[0][2];
        } else {
            libelle = key;
            if (values != null && values.length == 1) {
                somme = values[0];
            }
        }
        if (libelle == null) {
            libelle = this.getTitre();
        }
        html = html + this.makeCardTitle(libelle, somme) + "</div></div></div>";
        html = html + this.makeCardFooter(key, values) + "</div></a></div></div>";
        return html;
    }

    public String makePieHtml() {
        StringBuilder h = new StringBuilder();
        h.append("<div class=\"col-md-").append(this.colSpan).append(" col-sm-12\">");
        h.append("<div class=\"dashboard-card-wrapper\">");
        h.append("<div class=\"box dashboard-card dashboard-chart-box\" style=\"background-color: ").append(this.getBackgroundColor()).append(";\">");
        h.append("<div class=\"box-body\">");
        h.append("<div class=\"dashboard-card-header\">");
        h.append("<div class=\"dashboard-card-content\">");
        h.append("<div class=\"dashboard-card-avatar\">");
        h.append("<span class=\"material-symbols-rounded\" style=\"font-size: 24px; color: var(--Text-primary);\">").append(this.getIcon()).append("</span>");
        h.append("</div>");
        h.append("<div class=\"dashboard-card-info\">");
        h.append("<div class=\"Body14pxSemiBold dashboard-card-subtitle-second\">").append(this.getTitre()).append("</div>");
        h.append("</div>");
        h.append("</div>");
        h.append("</div>");
        String chartContainerId = "chartContainer_" + this.getCanvasId();
        h.append("<div id=\"").append(chartContainerId).append("\" class=\"chart-container\" style=\"position: relative; width: ").append(this.dynamicChartWidth > 0 ? this.dynamicChartWidth + "px" : "100%").append("; height: ").append(this.dynamicChartHeight > 0 ? this.dynamicChartHeight + "px" : "auto").append(";\">");
        h.append("<canvas id=\"").append(this.getCanvasId()).append("\"></canvas>");
        h.append("</div>");
        h.append("</div>");
        h.append("</div>");
        h.append("</div>");
        h.append("</div>");
        return h.toString();
    }

    public String getPieJavaScript() throws Exception {
        if (this.getColValeur().length < 1) {
            throw new Exception("Pour un pie chart, il faut 1 colonne");
        }
        if (this.pageRechercheGroupe != null && this.dataChart != null && !this.dataChart.isEmpty()) {
            return this.getPieJavaScriptGroupe();
        }
        if (this.pageRecherche != null) {
            return this.getPieJavaScriptSimple();
        }
        return "<!-- Aucune source de donn\u00c3\u00a9es disponible pour le pie chart -->";
    }

    private String getPieJavaScriptGroupe() throws Exception {
        return this.getCircularChartJavaScriptGroupe("pie");
    }

    private String getPieJavaScriptSimple() throws Exception {
        return this.getCircularChartJavaScriptSimple("pie");
    }

    public String getDoughnutJavaScript() throws Exception {
        if (this.getColValeur().length < 1) {
            throw new Exception("Pour un doughnut chart, il faut 1 colonne");
        }
        if (this.pageRechercheGroupe != null && this.dataChart != null && !this.dataChart.isEmpty()) {
            return this.getCircularChartJavaScriptGroupe("doughnut");
        }
        if (this.pageRecherche != null) {
            return this.getCircularChartJavaScriptSimple("doughnut");
        }
        return "<!-- Aucune source de donn\u00c3\u00a9es disponible pour le doughnut chart -->";
    }

    private String getCircularChartJavaScriptGroupe(String chartType) throws Exception {
        String id = this.getCanvasId();
        StringBuilder labels = new StringBuilder("[");
        StringBuilder values = new StringBuilder("[");
        StringBuilder colors = new StringBuilder("[");
        int idx = 0;
        for (Map.Entry<String, String[]> entry : this.dataChart.entrySet()) {
            String key = entry.getKey();
            if (key == null || key.trim().isEmpty()) continue;
            String[] vals = entry.getValue();
            if (idx > 0) {
                labels.append(", ");
                values.append(", ");
                colors.append(", ");
            }
            labels.append("\"").append(this.escapeJson(key)).append("\"");
            double num = 0.0;
            try {
                if (vals != null && vals.length > 0) {
                    boolean isCrossed = false;
                    if (this.getColCroisee() != null && this.getColCroisee().length > 0) {
                        isCrossed = true;
                    }
                    if (isCrossed) {
                        for (String valStr : vals) {
                            if (valStr == null || valStr.trim().isEmpty()) continue;
                            num += Utilitaire.parseNombreFrancais(valStr);
                        }
                    } else if (vals[0] != null && !vals[0].trim().isEmpty()) {
                        num = Utilitaire.parseNombreFrancais(vals[0]);
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            values.append(num);
            if (this.couleursMapping != null && this.couleursMapping.containsKey(key)) {
                colors.append("\"").append(this.couleursMapping.get(key)).append("\"");
            } else {
                colors.append("\"").append(this.couleurs[idx % this.couleurs.length]).append("\"");
            }
            ++idx;
        }
        labels.append("]");
        values.append("]");
        colors.append("]");
        StringBuilder js = new StringBuilder();
        js.append("<script>\n");
        js.append("(function() {\n");
        js.append("    var ctx").append(id).append(" = document.getElementById(\"").append(id).append("\").getContext('2d');\n");
        js.append("    // Calcul dynamique de la hauteur pour graphiques circulaires\n");
        js.append("    var chartContainerId = \"chartContainer_").append(id).append("\";\n");
        js.append("    var chartContainer = document.getElementById(chartContainerId);\n");
        js.append("    if (chartContainer) {\n");
        js.append("        var numLabels = ").append(this.dataChart.size()).append(";\n");
        js.append("        if (numLabels > 10) {\n");
        js.append("            var dynamicHeight = Math.min(600, 200 + numLabels * 20);\n");
        js.append("            chartContainer.style.height = dynamicHeight + 'px';\n");
        js.append("        }\n");
        js.append("    }\n");
        js.append("\n");
        js.append("    new Chart(ctx").append(id).append(", {\n");
        js.append("        type: \"").append(chartType).append("\",\n");
        js.append("        data: {\n");
        js.append("            labels: ").append((CharSequence)labels).append(",\n");
        js.append("            datasets: [{\n");
        js.append("                data: ").append((CharSequence)values).append(",\n");
        js.append("                backgroundColor: ").append((CharSequence)colors).append(",\n");
        js.append("                borderWidth: 1\n");
        js.append("            }]\n");
        js.append("        },\n");
        this.appendPieOptions(js);
        js.append("    });\n");
        js.append("})();\n");
        js.append("</script>\n");
        return js.toString();
    }

    private String getCircularChartJavaScriptSimple(String chartType) throws Exception {
        ClassMAPTable[] data = this.pageRecherche.getTableau().getData();
        if (data == null || data.length == 0) {
            return "<!-- Aucune donn\u00c3\u00a9e disponible pour le pie chart -->";
        }
        StringBuilder labels = new StringBuilder("[");
        StringBuilder values = new StringBuilder("[");
        StringBuilder colors = new StringBuilder("[");
        for (int i = 0; i < data.length; ++i) {
            String keySimple;
            if (i > 0) {
                labels.append(", ");
                values.append(", ");
                colors.append(", ");
            }
            Object lbl = data[i].getValeur(this.getColAffiche()[0]);
            Object val = data[i].getValeur(this.getColValeur()[0]);
            labels.append("\"").append(this.escapeJson(lbl != null ? lbl.toString() : "")).append("\"");
            values.append(val != null ? val : Integer.valueOf(0));
            String string = keySimple = lbl != null ? lbl.toString() : "";
            if (this.couleursMapping != null && this.couleursMapping.containsKey(keySimple)) {
                colors.append("\"").append(this.couleursMapping.get(keySimple)).append("\"");
                continue;
            }
            colors.append("\"").append(this.couleurs[i % this.couleurs.length]).append("\"");
        }
        labels.append("]");
        values.append("]");
        colors.append("]");
        String id = this.getCanvasId();
        StringBuilder js = new StringBuilder();
        js.append("<script>\n");
        js.append("(function() {\n");
        js.append("    var ctx").append(id).append(" = document.getElementById(\"").append(id).append("\").getContext('2d');\n");
        js.append("    // Calcul dynamique de la hauteur pour graphiques circulaires\n");
        js.append("    var chartContainerId = \"chartContainer_").append(id).append("\";\n");
        js.append("    var chartContainer = document.getElementById(chartContainerId);\n");
        js.append("    if (chartContainer) {\n");
        js.append("        var numLabels = ").append(data.length).append(";\n");
        js.append("        if (numLabels > 10) {\n");
        js.append("            var dynamicHeight = Math.min(600, 200 + numLabels * 20);\n");
        js.append("            chartContainer.style.height = dynamicHeight + 'px';\n");
        js.append("        }\n");
        js.append("    }\n");
        js.append("\n");
        js.append("    new Chart(ctx").append(id).append(", {\n");
        js.append("        type: \"").append(chartType).append("\",\n");
        js.append("        data: {\n");
        js.append("            labels: ").append((CharSequence)labels).append(",\n");
        js.append("            datasets: [{\n");
        js.append("                data: ").append((CharSequence)values).append(",\n");
        js.append("                backgroundColor: ").append((CharSequence)colors).append(",\n");
        js.append("                borderWidth: 1\n");
        js.append("            }]\n");
        js.append("        },\n");
        this.appendPieOptions(js);
        js.append("    });\n");
        js.append("})();\n");
        js.append("</script>\n");
        return js.toString();
    }

    private void appendPieOptions(StringBuilder js) {
        js.append("        options: {\n");
        js.append("            responsive: true,\n");
        js.append("            maintainAspectRatio: false,\n");
        js.append("            legend: {\n");
        js.append("                display: ").append(this.afficherLegende).append(",\n");
        js.append("                position: \"").append(this.positionLegende).append("\",\n");
        js.append("                labels: { boxWidth: 12, padding: 10 }\n");
        js.append("            },\n");
        js.append("            tooltips: {\n");
        js.append("                callbacks: {\n");
        js.append("                    label: function(tooltipItem, d) {\n");
        js.append("                        var label = d.labels[tooltipItem.index] || '';\n");
        js.append("                        var value = d.datasets[0].data[tooltipItem.index];\n");
        js.append("                        return label + ': ' + value.toLocaleString('fr-FR') + \"").append(this.escapeJson(this.getUniteAt(0))).append("\";\n");
        js.append("                    }\n");
        js.append("                }\n");
        js.append("            }\n");
        js.append("        }\n");
    }

    protected String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    public void preparerDataGroupe() throws Exception {
        if (this.pageRechercheGroupe == null) {
            return;
        }
        ValeurEtiquette[][] tabEtiquette = this.pageRechercheGroupe.getTableau().getValeurEtiquette();
        if (this.dataChart == null && this.getColCroisee() != null && this.getColCroisee().length > 0) {
            this.setDataChart(Utilitaire.transformerEtiquetteToMapLigne(tabEtiquette));
            this.setLegendes(Utilitaire.transformerEtiquetteToTab(tabEtiquette));
        } else if (this.dataChart == null) {
            this.dataChart = Utilitaire.transformerEtiquetteToMapTotal(tabEtiquette);
        }
        this.applyOrderAndLimit();
    }

    private void applyOrderAndLimit() {
        if (this.dataChart == null || this.dataChart.isEmpty()) {
            return;
        }
        List<Map.Entry<String, Object>> entries = new ArrayList<Map.Entry<String, String[]>>(this.dataChart.entrySet());
        if ("asc".equals(this.ordre)) {
            entries.sort((a, b) -> {
                double valA = this.parseFirstValue((String[])a.getValue());
                double valB = this.parseFirstValue((String[])b.getValue());
                return Double.compare(valA, valB);
            });
        } else if ("desc".equals(this.ordre)) {
            entries.sort((a, b) -> {
                double valA = this.parseFirstValue((String[])a.getValue());
                double valB = this.parseFirstValue((String[])b.getValue());
                return Double.compare(valB, valA);
            });
        } else if ("none".equals(this.ordre)) {
            entries.sort((a, b) -> {
                String keyA = a.getKey() != null ? (String)a.getKey() : "";
                String keyB = b.getKey() != null ? (String)b.getKey() : "";
                return this.compareDateLabels(keyA, keyB);
            });
        }
        if (this.limite > 0 && entries.size() > this.limite) {
            entries = entries.subList(0, this.limite);
        }
        LinkedHashMap<String, String[]> sortedData = new LinkedHashMap<String, String[]>();
        for (Map.Entry entry : entries) {
            sortedData.put((String)entry.getKey(), (String[])entry.getValue());
        }
        this.dataChart = sortedData;
    }

    private double parseFirstValue(String[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        try {
            if (values[0] != null && !values[0].trim().isEmpty()) {
                return Utilitaire.parseNombreFrancais(values[0]);
            }
        }
        catch (Exception e) {
            return 0.0;
        }
        return 0.0;
    }

    private int compareDateLabels(String a, String b) {
        long keyA = this.parseDateLabelToSortKey(a);
        long keyB = this.parseDateLabelToSortKey(b);
        if (keyA != Long.MAX_VALUE && keyB != Long.MAX_VALUE) {
            return Long.compare(keyA, keyB);
        }
        return a.compareTo(b);
    }

    private long parseDateLabelToSortKey(String label) {
        if (label == null || label.trim().isEmpty()) {
            return Long.MAX_VALUE;
        }
        label = label.trim();
        try {
            String[] parts = label.split("/");
            if (parts.length == 3) {
                int day = Integer.parseInt(parts[0].trim());
                int month = Integer.parseInt(parts[1].trim());
                int year = Integer.parseInt(parts[2].trim());
                return (long)year * 10000L + (long)month * 100L + (long)day;
            }
            if (parts.length == 2) {
                int month = Integer.parseInt(parts[0].trim());
                int year = Integer.parseInt(parts[1].trim());
                return (long)year * 10000L + (long)month * 100L;
            }
            if (label.contains("-S")) {
                String[] weekParts = label.split("-S");
                int year = Integer.parseInt(weekParts[0].trim());
                int week = Integer.parseInt(weekParts[1].trim());
                return (long)year * 10000L + (long)week;
            }
            if (label.matches("\\d{4}")) {
                return Long.parseLong(label) * 10000L;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return Long.MAX_VALUE;
    }

    public String makeBarHtml() {
        return this.makeChartHtml("bar");
    }

    public String makeStackedBarHtml() {
        return this.makeChartHtml("bar");
    }

    public String getBarJavaScript(boolean stacked) throws Exception {
        if (this.pageRechercheGroupe != null && this.dataChart != null && !this.dataChart.isEmpty()) {
            return this.getChartJavaScriptGroupe("bar", stacked);
        }
        if (this.pageRecherche != null) {
            return this.getChartJavaScriptSimple("bar", stacked);
        }
        return "<!-- Aucune source de donn\u00c3\u00a9es disponible pour le stacked bar chart -->";
    }

    public String getStackedBarJavaScript() throws Exception {
        return this.getBarJavaScript(true);
    }

    private void appendChartOptions(StringBuilder js, String chartType) {
        this.appendChartOptions(js, chartType, false);
    }

    private void appendChartOptions(StringBuilder js, String chartType, boolean isStacked) {
        js.append("        options: {\n");
        js.append("            responsive: true,\n");
        js.append("            maintainAspectRatio: false,\n");
        if (isStacked && "bar".equals(chartType)) {
            js.append("            scales: {\n");
            js.append("                xAxes: [{ stacked: true }],\n");
            js.append("                yAxes: [{ stacked: true }]\n");
            js.append("            },\n");
        } else {
            js.append("            scales: {\n");
            if ("horizontal".equalsIgnoreCase(this.orientation)) {
                js.append("                xAxes: [{\n");
                js.append("                    ticks: {\n");
                js.append("                        beginAtZero: true,\n");
                js.append("                        callback: function(value) {\n");
                js.append("                            return value.toLocaleString('fr-FR');\n");
                js.append("                        }\n");
                js.append("                    }\n");
                js.append("                }]\n");
            } else if (this.multiAxes && this.colValeur != null && this.colValeur.length > 1) {
                this.appendMultiAxesScales(js);
            } else {
                js.append("                yAxes: [{\n");
                js.append("                    ticks: {\n");
                js.append("                        beginAtZero: true,\n");
                js.append("                        callback: function(value) {\n");
                js.append("                            return value.toLocaleString('fr-FR');\n");
                js.append("                        }\n");
                js.append("                    }\n");
                js.append("                }]\n");
            }
            js.append("            },\n");
        }
        js.append("            legend: {\n");
        js.append("                display: ").append(this.afficherLegende).append(",\n");
        js.append("                position: \"").append(this.positionLegende).append("\"\n");
        js.append("            },\n");
        js.append("            tooltips: {\n");
        js.append("                mode: 'index',\n");
        js.append("                intersect: false,\n");
        js.append("                callbacks: {\n");
        js.append("                    label: function(tooltipItem, data) {\n");
        js.append("                        var dsLabel = data.datasets[tooltipItem.datasetIndex].label || '';\n");
        js.append("                        var unites = [");
        if (this.unite != null) {
            for (int i = 0; i < this.unite.length; ++i) {
                if (i > 0) {
                    js.append(", ");
                }
                js.append("\"").append(this.escapeJson(this.unite[i] != null ? this.unite[i] : "")).append("\"");
            }
        }
        js.append("];\n");
        js.append("                        var u = tooltipItem.datasetIndex < unites.length ? unites[tooltipItem.datasetIndex] : '';\n");
        js.append("if(unites.length === 1) { u = unites[0]; }\n");
        js.append("                        var value = (tooltipItem.xLabel !== undefined && tooltipItem.xLabel !== null && !isNaN(Number(tooltipItem.xLabel))) ? tooltipItem.xLabel : tooltipItem.yLabel;\n");
        js.append("                        return dsLabel + ': ' + Number(value).toLocaleString('fr-FR') + u;\n");
        js.append("                    }\n");
        js.append("                }\n");
        js.append("            }\n");
        js.append("        }\n");
    }

    private void appendMultiAxesScales(StringBuilder js) {
        int nbAxes = this.colValeur.length;
        String[] legendesLabels = this.getLegendes();
        js.append("                xAxes: [{\n");
        js.append("                    ticks: {\n");
        js.append("                        autoSkip: false,\n");
        js.append("                        maxRotation: 60,\n");
        js.append("                        minRotation: 45,\n");
        js.append("                        fontSize: 10\n");
        js.append("                    },\n");
        js.append("                    gridLines: { color: 'rgba(255,255,255,0.04)' }\n");
        js.append("                }],\n");
        js.append("                yAxes: [\n");
        for (int i = 0; i < nbAxes; ++i) {
            String axisId = "y-axis-" + i;
            String position = i == 0 ? "left" : "right";
            String labelString = legendesLabels != null && i < legendesLabels.length ? legendesLabels[i] : "S\u00c3\u00a9rie " + (i + 1);
            String color = this.couleurs[i % this.couleurs.length];
            String uniteStr = this.getUniteAt(i);
            if (i > 0) {
                js.append(",\n");
            }
            js.append("                    {\n");
            js.append("                        id: \"").append(axisId).append("\",\n");
            js.append("                        position: \"").append(position).append("\",\n");
            js.append("                        scaleLabel: {\n");
            js.append("                            display: true,\n");
            js.append("                            labelString: \"").append(this.escapeJson(labelString)).append("\",\n");
            js.append("                            fontColor: \"").append(color).append("\",\n");
            js.append("                            fontSize: 11\n");
            js.append("                        },\n");
            js.append("                        ticks: {\n");
            js.append("                            beginAtZero: true,\n");
            js.append("                            fontColor: \"").append(color).append("\",\n");
            js.append("                            callback: function(value) {\n");
            js.append("                                return value.toLocaleString('fr-FR')").append(uniteStr.isEmpty() ? "" : " + \"" + this.escapeJson(uniteStr) + "\"").append(";\n");
            js.append("                            }\n");
            js.append("                        },\n");
            if (i == 0) {
                js.append("                        gridLines: { color: 'rgba(").append(this.hexToRgb(color)).append(",0.08)' }\n");
            } else {
                js.append("                        gridLines: { drawOnChartArea: false }\n");
            }
            js.append("                    }");
        }
        js.append("\n                ]\n");
    }

    private String hexToRgb(String hex) {
        if (hex == null || hex.isEmpty()) {
            return "0,0,0";
        }
        hex = hex.replace("#", "");
        try {
            int r = Integer.parseInt(hex.substring(0, 2), 16);
            int g = Integer.parseInt(hex.substring(2, 4), 16);
            int b = Integer.parseInt(hex.substring(4, 6), 16);
            return r + "," + g + "," + b;
        }
        catch (Exception e) {
            return "0,0,0";
        }
    }

    public String makeLineHtml() {
        return this.makeChartHtml("line");
    }

    private String makeChartHtml(String chartType) {
        StringBuilder h = new StringBuilder();
        h.append("<div class=\"col-md-").append(this.colSpan).append(" col-sm-12\">");
        h.append("<div class=\"dashboard-card-wrapper\">");
        h.append("<div class=\"box dashboard-card dashboard-chart-box\" style=\"background-color: ").append(this.getBackgroundColor()).append(";\">");
        h.append("<div class=\"box-body\">");
        h.append("<div class=\"dashboard-card-header\">");
        h.append("<div class=\"dashboard-card-content\">");
        h.append("<div class=\"dashboard-card-avatar\">");
        h.append("<span class=\"material-symbols-rounded\" style=\"font-size: 24px; color: var(--Text-primary);\">").append(this.getIcon()).append("</span>");
        h.append("</div>");
        h.append("<div class=\"dashboard-card-info\">");
        h.append("<div class=\"Body14pxSemiBold dashboard-card-subtitle-second\">").append(this.getTitre()).append("</div>");
        h.append("</div>");
        h.append("</div>");
        for (String col : this.getColAffiche()) {
            if (!col.equalsIgnoreCase(this.getDateColonne()) || this.type != DashboardGraphType.BAR && this.type != DashboardGraphType.LINE) continue;
            h.append(this.makePeriodSelector());
            break;
        }
        h.append("</div>");
        String chartContainerId = "chartContainer_" + this.getCanvasId();
        h.append("<div id=\"").append(chartContainerId).append("\" class=\"chart-container\" style=\"position: relative; width: ").append(this.dynamicChartWidth > 0 ? this.dynamicChartWidth + "px" : "100%").append(";\" height: ").append(";\">");
        h.append("<canvas id=\"").append(this.getCanvasId()).append("\"></canvas>");
        h.append("</div>");
        h.append("</div>");
        h.append("</div>");
        h.append("</div>");
        h.append("</div>");
        return h.toString();
    }

    private String makePeriodSelector() {
        String selectId = "period_" + this.getCanvasId();
        String def = this.periodeParDefaut != null ? this.periodeParDefaut.getValue() : "mois";
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"dashboard-period-selector\" style=\"margin-left: auto;\">");
        html.append("<select id=\"").append(selectId).append("\" class=\"form-control form-control-sm\" style=\"width: auto; display: inline-block;\">");
        html.append("<option value=\"jour\"").append("jour".equals(def) ? " selected" : "").append(">Par jour</option>");
        html.append("<option value=\"semaine\"").append("semaine".equals(def) ? " selected" : "").append(">Par semaine</option>");
        html.append("<option value=\"mois\"").append("mois".equals(def) ? " selected" : "").append(">Par mois</option>");
        html.append("<option value=\"annee\"").append("annee".equals(def) ? " selected" : "").append(">Par ann&eacute;e</option>");
        html.append("</select>");
        html.append("</div>");
        return html.toString();
    }

    public String getLineJavaScript() throws Exception {
        if (this.pageRechercheGroupe != null && this.dataChart != null && !this.dataChart.isEmpty()) {
            return this.getChartJavaScriptGroupe("line");
        }
        if (this.pageRecherche != null) {
            return this.getChartJavaScriptSimple("line");
        }
        return "<!-- Aucune source de donn\u00c3\u00a9es disponible pour le line chart -->";
    }

    private String getChartJavaScriptGroupe(String chartType) throws Exception {
        return this.getChartJavaScriptGroupe(chartType, false);
    }

    private String getChartJavaScriptGroupe(String chartType, boolean isStacked) throws Exception {
        String id = this.getCanvasId();
        String selectId = "period_" + id;
        StringBuilder js = new StringBuilder();
        js.append("<script>\n");
        js.append("(function() {\n");
        js.append("    var rawData_").append(id).append(" = {\n");
        js.append("        labels: [],\n");
        js.append("        datasets: []\n");
        js.append("    };\n\n");
        int nbDatasets = 0;
        for (Map.Entry<String, String[]> entry : this.dataChart.entrySet()) {
            if (entry.getValue() == null || entry.getValue().length <= 0) continue;
            nbDatasets = entry.getValue().length;
            break;
        }
        for (int i = 0; i < nbDatasets; ++i) {
            js.append("    rawData_").append(id).append(".datasets[").append(i).append("] = [];\n");
        }
        js.append("\n");
        for (Map.Entry<String, String[]> entry : this.dataChart.entrySet()) {
            String key = entry.getKey();
            if (key == null || key.trim().isEmpty()) continue;
            String[] values = entry.getValue();
            js.append("    rawData_").append(id).append(".labels.push(\"").append(this.escapeJson(key)).append("\");\n");
            if (values == null) continue;
            for (int i = 0; i < values.length && i < nbDatasets; ++i) {
                double value = 0.0;
                try {
                    if (values[i] != null && !values[i].trim().isEmpty()) {
                        value = Utilitaire.parseNombreFrancais(values[i]);
                    }
                }
                catch (Exception e) {
                    value = 0.0;
                }
                js.append("    rawData_").append(id).append(".datasets[").append(i).append("].push(").append(value).append(");\n");
            }
        }
        js.append("\n    // Fonction pour regrouper les donn\u00c3\u00a9es par p\u00c3\u00a9riode\n");
        js.append("    function groupByPeriod_").append(id).append("(period) {\n");
        js.append("        var grouped = {};\n");
        js.append("        var labels = rawData_").append(id).append(".labels;\n");
        js.append("        \n");
        js.append("        for (var i = 0; i < labels.length; i++) {\n");
        js.append("            var date = labels[i];\n");
        js.append("            var key = formatDateByPeriod(date, period);\n");
        js.append("            \n");
        js.append("            if (!grouped[key]) {\n");
        js.append("                grouped[key] = [];\n");
        js.append("                for (var d = 0; d < rawData_").append(id).append(".datasets.length; d++) {\n");
        js.append("                    grouped[key].push(0);\n");
        js.append("                }\n");
        js.append("            }\n");
        js.append("            \n");
        js.append("            for (var d = 0; d < rawData_").append(id).append(".datasets.length; d++) {\n");
        js.append("                grouped[key][d] += rawData_").append(id).append(".datasets[d][i];\n");
        js.append("            }\n");
        js.append("        }\n");
        js.append("        \n");
        js.append("        // Construire un tableau de paires [label, valeurs] pour pouvoir trier\n");
        js.append("        var keys = Object.keys(grouped);\n");
        js.append("        var pairs = [];\n");
        js.append("        for (var i = 0; i < keys.length; i++) {\n");
        js.append("            pairs.push({ label: keys[i], values: grouped[keys[i]] });\n");
        js.append("        }\n");
        js.append("        \n");
        js.append("        // Tri des donn\u00c3\u00a9es selon l'ordre sp\u00c3\u00a9cifi\u00c3\u00a9\n");
        js.append("        var sortOrder = \"").append(this.ordre).append("\";\n");
        js.append("        if (sortOrder === 'asc') {\n");
        js.append("            pairs.sort(function(a, b) { return a.values[0] - b.values[0]; });\n");
        js.append("        } else if (sortOrder === 'desc') {\n");
        js.append("            pairs.sort(function(a, b) { return b.values[0] - a.values[0]; });\n");
        js.append("        } else {\n");
        js.append("            // Par d\u00c3\u00a9faut, trier par le label (date/cl\u00c3\u00a9) en ordre chronologique\n");
        js.append("            pairs.sort(function(a, b) { return compareDateLabels(a.label, b.label); });\n");
        js.append("        }\n");
        js.append("        \n");
        js.append("        // Limiter les donn\u00c3\u00a9es si n\u00c3\u00a9cessaire (top N)\n");
        js.append("        var limit = ").append(this.limite).append(";\n");
        js.append("        if (limit > 0 && pairs.length > limit) {\n");
        js.append("            pairs = pairs.slice(0, limit);\n");
        js.append("        }\n");
        js.append("        \n");
        js.append("        // Construire le r\u00c3\u00a9sultat final\n");
        js.append("        var result = { labels: [], datasets: [] };\n");
        js.append("        for (var d = 0; d < rawData_").append(id).append(".datasets.length; d++) {\n");
        js.append("            result.datasets.push([]);\n");
        js.append("        }\n");
        js.append("        \n");
        js.append("        for (var i = 0; i < pairs.length; i++) {\n");
        js.append("            result.labels.push(pairs[i].label);\n");
        js.append("            for (var d = 0; d < pairs[i].values.length; d++) {\n");
        js.append("                result.datasets[d].push(pairs[i].values[d]);\n");
        js.append("            }\n");
        js.append("        }\n");
        js.append("        \n");
        js.append("        return result;\n");
        js.append("    }\n\n");
        js.append("    function formatDateByPeriod(dateStr, period) {\n");
        js.append("        try {\n");
        js.append("            var parts = dateStr.split('/');\n");
        js.append("            if (parts.length !== 3) return dateStr;\n");
        js.append("            \n");
        js.append("            var day = parseInt(parts[0]);\n");
        js.append("            var month = parseInt(parts[1]);\n");
        js.append("            var year = parseInt(parts[2]);\n");
        js.append("            var date = new Date(year, month - 1, day);\n");
        js.append("            \n");
        js.append("            if (period === 'jour') {\n");
        js.append("                return dateStr;\n");
        js.append("            } else if (period === 'semaine') {\n");
        js.append("                var week = getWeekNumber(date);\n");
        js.append("                return year + '-S' + (week < 10 ? '0' + week : week);\n");
        js.append("            } else if (period === 'mois') {\n");
        js.append("                return (month < 10 ? '0' + month : month) + '/' + year;\n");
        js.append("            } else if (period === 'annee') {\n");
        js.append("                return year.toString();\n");
        js.append("            } else {\n");
        js.append("                return dateStr;\n");
        js.append("            }\n");
        js.append("        } catch(e) {\n");
        js.append("            return dateStr;\n");
        js.append("        }\n");
        js.append("    }\n\n");
        js.append("    function getWeekNumber(date) {\n");
        js.append("        var d = new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()));\n");
        js.append("        var dayNum = d.getUTCDay() || 7;\n");
        js.append("        d.setUTCDate(d.getUTCDate() + 4 - dayNum);\n");
        js.append("        var yearStart = new Date(Date.UTC(d.getUTCFullYear(),0,1));\n");
        js.append("        return Math.ceil((((d - yearStart) / 86400000) + 1)/7);\n");
        js.append("    }\n\n");
        js.append("    function parseDateLabelToSortKey(label) {\n");
        js.append("        if (!label || !label.trim()) return Number.MAX_SAFE_INTEGER;\n");
        js.append("        label = label.trim();\n");
        js.append("        try {\n");
        js.append("            var parts = label.split('/');\n");
        js.append("            if (parts.length === 3) {\n");
        js.append("                var day = parseInt(parts[0], 10);\n");
        js.append("                var month = parseInt(parts[1], 10);\n");
        js.append("                var year = parseInt(parts[2], 10);\n");
        js.append("                return year * 10000 + month * 100 + day;\n");
        js.append("            }\n");
        js.append("            if (parts.length === 2) {\n");
        js.append("                var m = parseInt(parts[0], 10);\n");
        js.append("                var y = parseInt(parts[1], 10);\n");
        js.append("                return y * 10000 + m * 100;\n");
        js.append("            }\n");
        js.append("            if (label.indexOf('-S') !== -1) {\n");
        js.append("                var wp = label.split('-S');\n");
        js.append("                return parseInt(wp[0], 10) * 10000 + parseInt(wp[1], 10);\n");
        js.append("            }\n");
        js.append("            if (/^\\d{4}$/.test(label)) {\n");
        js.append("                return parseInt(label, 10) * 10000;\n");
        js.append("            }\n");
        js.append("        } catch(e) {}\n");
        js.append("        return Number.MAX_SAFE_INTEGER;\n");
        js.append("    }\n\n");
        js.append("    function compareDateLabels(a, b) {\n");
        js.append("        var keyA = parseDateLabelToSortKey(a);\n");
        js.append("        var keyB = parseDateLabelToSortKey(b);\n");
        js.append("        if (keyA !== Number.MAX_SAFE_INTEGER && keyB !== Number.MAX_SAFE_INTEGER) return keyA - keyB;\n");
        js.append("        return a < b ? -1 : a > b ? 1 : 0;\n");
        js.append("    }\n\n");
        js.append("    var ctx").append(id).append(" = document.getElementById(\"").append(id).append("\").getContext('2d');\n");
        String def = this.periodeParDefaut != null ? this.periodeParDefaut.getValue() : "mois";
        js.append("    var chartData = groupByPeriod_").append(id).append("('").append(def).append("');\n");
        js.append("    var datasets = [];\n");
        js.append("\n    // Calcul dynamique de la hauteur pour les graphiques horizontaux\n");
        js.append("    var chartContainerId = \"chartContainer_").append(id).append("\";\n");
        js.append("    var chartContainer = document.getElementById(chartContainerId);\n");
        js.append("    if (chartContainer && chartData.labels.length > 0) {\n");
        js.append("        var barHeight = ").append(this.barHeightPx).append("; // hauteur par barre en pixels\n");
        js.append("        var padding = ").append(this.chartPaddingPx).append("; // padding pour axes et l\u00c3\u00a9gende\n");
        js.append("        var minHeight = 300; // hauteur minimale en pixels\n");
        js.append("        var numLabels = chartData.labels.length;\n");
        js.append("        var orientation = \"").append(this.orientation).append("\";\n");
        js.append("        // Appliquer la hauteur dynamique uniquement pour les graphiques horizontaux\n");
        js.append("        if (orientation === 'horizontal') {\n");
        js.append("            var dynamicHeight = Math.max(minHeight, (numLabels * barHeight) + padding);\n");
        js.append("            chartContainer.style.height = dynamicHeight + 'px';\n");
        js.append("        } else {\n");
        js.append("            // Pour graphiques verticaux, pas de hauteur sp\u00c3\u00a9cifique (utilise le responsive)\n");
        js.append("            chartContainer.style.height = '").append(this.dynamicChartHeight > 0 ? this.dynamicChartHeight + "px" : "auto").append("';\n");
        js.append("        }\n");
        js.append("    }\n");
        String[] legendesLabels = this.getLegendes();
        for (int i = 0; i < nbDatasets; ++i) {
            String label = legendesLabels != null && i < legendesLabels.length ? legendesLabels[i] : "S\u00c3\u00a9rie " + (i + 1);
            String color = this.couleurs[i % this.couleurs.length];
            String datasetType = this.getTypeDatasetAt(i);
            boolean isLine = "line".equals(datasetType);
            js.append("    datasets.push({\n");
            js.append("        label: \"").append(this.escapeJson(label)).append("\",\n");
            js.append("        data: chartData.datasets[").append(i).append("],\n");
            if (this.multiAxes || this.typesDataset != null && this.typesDataset.length > 0) {
                js.append("        type: \"").append(datasetType).append("\",\n");
            }
            if (this.multiAxes && this.colValeur != null && this.colValeur.length > 1) {
                js.append("        yAxisID: 'y-axis-").append(i).append("\",\n");
            }
            if (isLine) {
                js.append("        backgroundColor: 'rgba(0,0,0,0)',\n");
                js.append("        borderColor: \"").append(color).append("\",\n");
                js.append("        borderWidth: 2,\n");
                js.append("        pointRadius: 3,\n");
                js.append("        pointBackgroundColor: \"").append(color).append("\",\n");
                js.append("        fill: false,\n");
                js.append("        tension: 0.3\n");
            } else {
                js.append("        backgroundColor: \"").append(color).append("\",\n");
                js.append("        borderColor: \"").append(color).append("\",\n");
                js.append("        borderWidth: 1\n");
            }
            js.append("    });\n");
        }
        String effectiveChartType = chartType;
        if ("bar".equals(chartType) && "horizontal".equalsIgnoreCase(this.orientation)) {
            effectiveChartType = "horizontalBar";
        }
        js.append("\n    var chart_").append(id).append(" = new Chart(ctx").append(id).append(", {\n");
        js.append("        type: \"").append(effectiveChartType).append("\",\n");
        js.append("        data: {\n");
        js.append("            labels: chartData.labels,\n");
        js.append("            datasets: datasets\n");
        js.append("        },\n");
        this.appendChartOptions(js, chartType, isStacked);
        js.append("    });\n\n");
        js.append("    var selector = document.getElementById(\"").append(selectId).append("\");\n");
        js.append("    if (selector) {\n");
        js.append("        selector.addEventListener('change', function() {\n");
        js.append("            var newData = groupByPeriod_").append(id).append("(this.value);\n");
        js.append("            chart_").append(id).append(".data.labels = newData.labels;\n");
        js.append("            for (var i = 0; i < newData.datasets.length; i++) {\n");
        js.append("                chart_").append(id).append(".data.datasets[i].data = newData.datasets[i];\n");
        js.append("            }\n");
        js.append("            chart_").append(id).append(".update();\n");
        js.append("        });\n");
        js.append("    }\n");
        js.append("})();\n");
        js.append("</script>\n");
        return js.toString();
    }

    private String getChartJavaScriptSimple(String chartType) throws Exception {
        return this.getChartJavaScriptSimple(chartType, false);
    }

    private String getChartJavaScriptSimple(String chartType, boolean isStacked) throws Exception {
        int c;
        int c2;
        ClassMAPTable[] data = this.pageRecherche.getTableau().getData();
        String[] sommeAffiche = this.getColValeur();
        if (data == null || data.length == 0 || sommeAffiche == null || sommeAffiche.length == 0) {
            return "<!-- Aucune donn\u00c3\u00a9e disponible pour le " + chartType + " chart -->";
        }
        String id = this.getCanvasId();
        StringBuilder js = new StringBuilder();
        js.append("<script>\n");
        js.append("(function() {\n");
        js.append("    var pairs = [];\n");
        for (int r = 0; r < data.length; ++r) {
            Object lbl = data[r].getValeur(this.getColAffiche()[0]);
            js.append("    pairs.push({ label: \"").append(this.escapeJson(lbl != null ? lbl.toString() : "")).append("\", values: [");
            for (c2 = 0; c2 < sommeAffiche.length; ++c2) {
                if (c2 > 0) {
                    js.append(", ");
                }
                Object val = data[r].getValeur(sommeAffiche[c2]);
                double num = 0.0;
                try {
                    if (val != null && !val.toString().trim().isEmpty()) {
                        num = Double.parseDouble(val.toString().trim().replace(",", ".").replaceAll("[^\\d.]", ""));
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                js.append(num);
            }
            js.append("] });\n");
        }
        js.append("\n    // Tri des donn\u00c3\u00a9es\n");
        js.append("    function parseDateLabelToSortKey(label) {\n");
        js.append("        if (!label || !label.trim()) return Number.MAX_SAFE_INTEGER;\n");
        js.append("        label = label.trim();\n");
        js.append("        try {\n");
        js.append("            var parts = label.split('/');\n");
        js.append("            if (parts.length === 3) {\n");
        js.append("                return parseInt(parts[2],10)*10000 + parseInt(parts[1],10)*100 + parseInt(parts[0],10);\n");
        js.append("            }\n");
        js.append("            if (parts.length === 2) {\n");
        js.append("                return parseInt(parts[1],10)*10000 + parseInt(parts[0],10)*100;\n");
        js.append("            }\n");
        js.append("            if (label.indexOf('-S') !== -1) {\n");
        js.append("                var wp = label.split('-S');\n");
        js.append("                return parseInt(wp[0],10)*10000 + parseInt(wp[1],10);\n");
        js.append("            }\n");
        js.append("            if (/^\\d{4}$/.test(label)) return parseInt(label,10)*10000;\n");
        js.append("        } catch(e) {}\n");
        js.append("        return Number.MAX_SAFE_INTEGER;\n");
        js.append("    }\n");
        js.append("    function compareDateLabels(a, b) {\n");
        js.append("        var kA = parseDateLabelToSortKey(a), kB = parseDateLabelToSortKey(b);\n");
        js.append("        if (kA !== Number.MAX_SAFE_INTEGER && kB !== Number.MAX_SAFE_INTEGER) return kA - kB;\n");
        js.append("        return a < b ? -1 : a > b ? 1 : 0;\n");
        js.append("    }\n");
        if ("asc".equals(this.ordre)) {
            js.append("    pairs.sort(function(a, b) { return a.values[0] - b.values[0]; });\n");
        } else if ("desc".equals(this.ordre)) {
            js.append("    pairs.sort(function(a, b) { return b.values[0] - a.values[0]; });\n");
        } else {
            js.append("    pairs.sort(function(a, b) { return compareDateLabels(a.label, b.label); });\n");
        }
        if (this.limite > 0) {
            js.append("    // Limiter au top ").append(this.limite).append("\n");
            js.append("    if (pairs.length > ").append(this.limite).append(") {\n");
            js.append("        pairs = pairs.slice(0, ").append(this.limite).append(");\n");
            js.append("    }\n");
        }
        js.append("\n    var labels = [];\n");
        for (c = 0; c < sommeAffiche.length; ++c) {
            js.append("    var data").append(c).append(" = [];\n");
        }
        js.append("    for (var i = 0; i < pairs.length; i++) {\n");
        js.append("        labels.push(pairs[i].label);\n");
        for (c = 0; c < sommeAffiche.length; ++c) {
            js.append("        data").append(c).append(".push(pairs[i].values[").append(c).append("]);\n");
        }
        js.append("    }\n");
        js.append("\n");
        js.append("    var ctx").append(id).append(" = document.getElementById(\"").append(id).append("\").getContext('2d');\n");
        js.append("    // Calcul dynamique de la hauteur pour les graphiques horizontaux\n");
        js.append("    var chartContainerId = \"chartContainer_").append(id).append("\";\n");
        js.append("    var chartContainer = document.getElementById(chartContainerId);\n");
        js.append("    if (chartContainer && labels.length > 0) {\n");
        js.append("        var barHeight = ").append(this.barHeightPx).append("; // hauteur par barre en pixels\n");
        js.append("        var padding = ").append(this.chartPaddingPx).append("; // padding pour axes et l\u00c3\u00a9gende\n");
        js.append("        var minHeight = 300; // hauteur minimale en pixels\n");
        js.append("        var numLabels = labels.length;\n");
        js.append("        var orientation = \"").append(this.orientation).append("\";\n");
        js.append("        // Appliquer la hauteur dynamique uniquement pour les graphiques horizontaux\n");
        js.append("        if (orientation === 'horizontal') {\n");
        js.append("            var dynamicHeight = Math.max(minHeight, (numLabels * barHeight) + padding);\n");
        js.append("            chartContainer.style.height = dynamicHeight + 'px';\n");
        js.append("        } else {\n");
        js.append("            // Pour graphiques verticaux, pas de hauteur sp\u00c3\u00a9cifique (utilise le responsive)\n");
        js.append("            chartContainer.style.height = 'auto';\n");
        js.append("        }\n");
        js.append("    }\n");
        js.append("\n");
        js.append("    new Chart(ctx").append(id).append(", {\n");
        String effectiveChartType = chartType;
        if ("bar".equals(chartType) && "horizontal".equalsIgnoreCase(this.orientation)) {
            effectiveChartType = "horizontalBar";
        }
        js.append("        type: \"").append(effectiveChartType).append("\",\n");
        js.append("        data: {\n");
        js.append("            labels: labels,\n");
        js.append("            datasets: [\n");
        String[] legendesLabels = this.getLegendes();
        for (c2 = 0; c2 < sommeAffiche.length; ++c2) {
            String dsLabel = legendesLabels != null && c2 < legendesLabels.length ? legendesLabels[c2] : sommeAffiche[c2];
            String color = this.couleurs[c2 % this.couleurs.length];
            String datasetType = this.getTypeDatasetAt(c2);
            boolean isLine = "line".equals(datasetType);
            js.append("                {\n");
            js.append("                    label: \"").append(this.escapeJson(dsLabel)).append("\",\n");
            js.append("                    data: data").append(c2).append(",\n");
            if (this.multiAxes || this.typesDataset != null && this.typesDataset.length > 0) {
                js.append("                    type: \"").append(datasetType).append("\",\n");
            }
            if (this.multiAxes && sommeAffiche.length > 1) {
                js.append("                    yAxisID: 'y-axis-").append(c2).append("\",\n");
            }
            if (isLine) {
                js.append("                    backgroundColor: 'rgba(0,0,0,0)',\n");
                js.append("                    borderColor: \"").append(color).append("\",\n");
                js.append("                    borderWidth: 2,\n");
                js.append("                    pointRadius: 3,\n");
                js.append("                    pointBackgroundColor: \"").append(color).append("\",\n");
                js.append("                    fill: false,\n");
                js.append("                    tension: 0.3\n");
            } else {
                js.append("                    backgroundColor: \"").append(color).append("\",\n");
                js.append("                    borderColor: \"").append(color).append("\",\n");
                js.append("                    borderWidth: 1\n");
            }
            js.append("                }");
            if (c2 < sommeAffiche.length - 1) {
                js.append(",");
            }
            js.append("\n");
        }
        js.append("            ]\n");
        js.append("        },\n");
        this.appendChartOptions(js, chartType, isStacked);
        js.append("    });\n");
        js.append("})();\n");
        js.append("</script>\n");
        return js.toString();
    }

    public String getOrientation() {
        return this.orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public Map<String, String[]> getDataChart() {
        return this.dataChart;
    }

    public void setDataChart(Map<String, String[]> dataChart) {
        this.dataChart = dataChart;
    }

    public String[] getLegendes() {
        return this.legendes;
    }

    public void setLegendes(String[] legendes) {
        this.legendes = legendes;
    }

    public boolean isAfficherLegende() {
        return this.afficherLegende;
    }

    public void setAfficherLegende(boolean afficherLegende) {
        this.afficherLegende = afficherLegende;
    }

    public String getPositionLegende() {
        return this.positionLegende;
    }

    public void setPositionLegende(String positionLegende) {
        this.positionLegende = positionLegende;
    }

    public String[] getColAffiche() {
        return this.colAffiche;
    }

    public void setColAffiche(String[] colAffiche) {
        this.colAffiche = colAffiche;
    }

    public String[] getColCroisee() {
        return this.colCroisee;
    }

    public void setColCroisee(String[] colCroisee) {
        this.colCroisee = colCroisee;
    }

    public String getOrdre() {
        return this.ordre;
    }

    public void setOrdre(String ordre) {
        this.ordre = ordre;
    }

    public int getLimite() {
        return this.limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public String[] getUnite() {
        return this.unite;
    }

    public String getUniteAt(int index) {
        if (this.unite == null || index < 0 || this.unite.length == 0) {
            return "";
        }
        if (index >= this.unite.length) {
            index %= this.unite.length;
        }
        if (this.unite[index] == null) {
            return "";
        }
        return this.unite[index];
    }

    public void setUnite(String[] unite) {
        this.unite = unite;
    }

    public void setUnite(String unite) {
        this.unite = unite != null ? new String[]{unite} : null;
    }

    public void setAWhere(String where) {
        if (this.getPageRecherche() != null) {
            this.getPageRecherche().setAWhere(where);
        }
        if (this.getPageRechercheGroupe() != null) {
            this.getPageRechercheGroupe().setAWhere(where);
        }
    }

    public String[] getTypesDataset() {
        return this.typesDataset;
    }

    public void setTypesDataset(String[] typesDataset) {
        this.typesDataset = typesDataset;
    }

    public String getTypeDatasetAt(int index) {
        if (this.typesDataset == null || index < 0 || index >= this.typesDataset.length || this.typesDataset[index] == null) {
            return this.getType().getVal();
        }
        return this.typesDataset[index];
    }

    public boolean isMultiAxes() {
        return this.multiAxes;
    }

    public void setMultiAxes(boolean multiAxes) {
        this.multiAxes = multiAxes;
    }

    public int getEtat() {
        return this.etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
}

