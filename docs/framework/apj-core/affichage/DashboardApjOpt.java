/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  user.UserEJB
 */
package affichage;

import affichage.DashboardGraph;
import affichage.DashboardGraphType;
import affichage.Formulaire;
import affichage.Page;
import constante.ConstanteDashboard;
import java.sql.Connection;
import user.UserEJB;
import utilitaire.UtilDB;

public class DashboardApjOpt
extends Page {
    private DashboardGraph[] graphs;
    private String apres;
    private Page[] dataPages;

    public DashboardApjOpt(DashboardGraph[] graphs, UserEJB u) {
        this.setUtilisateur(u);
        this.setGraphs(graphs);
    }

    public void creerObjetPage() throws Exception {
        this.creerObjetPage(true);
    }

    public void creerObjetPage(boolean autoGraph) throws Exception {
        try (Connection con = null;){
            con = new UtilDB().GetConn();
            for (DashboardGraph graph : this.getGraphs()) {
                if (graph == null) continue;
                if (graph.getPageRecherche() != null) {
                    graph.getPageRecherche().setIsGraphe(true);
                }
                if (graph.getPageRechercheGroupe() != null) {
                    graph.getPageRechercheGroupe().setIsGraphe(true);
                }
                if (autoGraph && graph.getEtat() < ConstanteDashboard.ETAT_PAGE_CREE) {
                    graph.creerObjetPage(con);
                }
                graph.setLien(this.getLien());
                graph.creerObjetGraph();
                graph.setLienPage(graph.getLienPage() + graph.getFormu().getListeCritereString());
                graph.makeHtml();
            }
            DashboardGraphType[] ordre = new DashboardGraphType[]{DashboardGraphType.CARD, DashboardGraphType.PIE, DashboardGraphType.DOUGHNUT, DashboardGraphType.BAR, DashboardGraphType.LINE, DashboardGraphType.UNDEFINED};
            StringBuilder html = new StringBuilder();
            for (DashboardGraphType type : ordre) {
                StringBuilder rowContent = new StringBuilder();
                for (DashboardGraph graph : this.getGraphs()) {
                    if (graph == null || graph.getType() != type) continue;
                    rowContent.append(graph.getHtml());
                }
                if (rowContent.length() <= 0) continue;
                html.append("<div class=\"row dashboard-row dashboard-row-").append(type.getVal()).append("\">").append((CharSequence)rowContent).append("</div>");
            }
            this.setHtml(html.toString());
        }
    }

    public String getHtmlFormu() throws Exception {
        String html = this.getFormu().getHtmlEnsemble();
        return this.cacherMotsCles(html);
    }

    private String cacherMotsCles(String html) {
        return html.replaceFirst("(<div[^>]*class=\"mots-cless\"[^>]*)", "$1 style=\"display:none\"");
    }

    public DashboardGraph[] getGraphs() {
        return this.graphs;
    }

    public void setGraphs(DashboardGraph[] graphs) {
        Formulaire formu = null;
        for (DashboardGraph graph : graphs) {
            if (graph == null) continue;
            if (formu == null && graph.isAvecDate()) {
                formu = graph.getFormu();
            } else if (formu != null && graph.isAvecDate()) {
                graph.setFormu(formu);
            }
            graph.setUtilisateur(this.getUtilisateur());
        }
        this.setFormu(formu);
        this.graphs = graphs;
    }

    public String getApres() {
        return this.apres;
    }

    public void setApres(String apres) {
        this.apres = apres;
    }

    public void setDataPages(Page[] dataPages) {
        this.dataPages = dataPages;
    }

    public Page[] getDataPages() {
        return this.dataPages;
    }
}

