/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 */
package affichage;

import affichage.IKanban;
import bean.CGenUtil;
import bean.ClassMAPTable;
import com.google.gson.Gson;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import utilitaire.UtilDB;

public class Kanban {
    public static final int KANBAN_DARK = 1;
    public static final int KANBAN_LIGHT = 0;
    public static final String KANBAN_DEFAULT_BG = "#e7f3fe";
    public static final String BADGE_DEFAULT_BG = "#007bff";
    private String titre;
    private ClassMAPTable entite;
    private ClassMAPTable entete;
    private String colonneEntete;
    private String tablePrincipal;
    private String classe;
    private String aWhere;
    private String colonneGrouper;
    private String colonneGrouperLib;
    private boolean showCount;
    private String colonneTitre;
    private String colonneDescription;
    private String colonneDescriptionLib;
    private String hauteurSection;
    private String largeurSection;
    private String linkClick;
    private String contentHtml;
    private String contentHtmlGraph;
    private Connection connection;
    private int theme;
    private boolean showKanbanCardContent = true;
    private boolean avtiveDifferentColor = true;
    private boolean activeBtnDuplicate;
    private boolean activeBtnRemove;
    private String duplicateClasse;
    private String colonnePk;
    private String bgKanbanCard;
    private String bgKanbanBadge;
    private Object[] data;
    private boolean showDateFilter;
    private String debut;
    private String fin;
    private static String[] KANBAN_COLORS = new String[]{"#27C475", "#3498DB", "#9B59B6", "#F39C12", "#1ABC9C", "#E67E22", "#E84393", "#2ECC71", "#34495E", "#16A085"};

    public Kanban(ClassMAPTable entite, String titre, String tablePrincipal, String aWhere, String colonneGrouper, String colonneGrouperLib, String colonneTitre, String colonneDescription, String colonneDescriptionLib, String hauteurSection, boolean showCount) {
        this.setEntite(entite);
        this.setTitre(titre);
        this.setTablePrincipal(tablePrincipal);
        this.setaWhere(aWhere);
        this.setColonneGrouper(colonneGrouper);
        this.setColonneGrouperLib(colonneGrouperLib);
        this.setColonneTitre(colonneTitre);
        this.setColonneDescription(colonneDescription);
        this.setColonneDescriptionLib(colonneDescriptionLib);
        this.setHauteurSection(hauteurSection);
        this.setShowCount(showCount);
        this.setColonnePk(entite.getAttributIDName());
        this.classe = entite.getClass().getName();
    }

    private static Field getFieldRecursive(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            }
            catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    private static Map<String, Integer> extractGroupedItemCounts(List<?> items, String groupField) throws Exception {
        LinkedHashMap<String, Integer> groupCounts = new LinkedHashMap<String, Integer>();
        for (Object item : items) {
            Field groupF = Kanban.getFieldRecursive(item.getClass(), groupField);
            if (groupF == null) continue;
            groupF.setAccessible(true);
            String groupValue = String.valueOf(groupF.get(item));
            groupCounts.put(groupValue, groupCounts.getOrDefault(groupValue, 0) + 1);
        }
        return groupCounts;
    }

    private static Map<String, List<Object>> extractGroupedItems(List<?> items, Map<String, List<Object>> groupedItems, String groupField, String groupFieldLib) throws Exception {
        for (Object item : items) {
            Field groupF = Kanban.getFieldRecursive(item.getClass(), groupField);
            Field groupFL = Kanban.getFieldRecursive(item.getClass(), groupFieldLib);
            if (groupF == null) continue;
            groupF.setAccessible(true);
            groupFL.setAccessible(true);
            String groupValue = String.valueOf(groupF.get(item));
            String groupValueLib = String.valueOf(groupFL.get(item));
            String realKey = groupValue + "__" + groupValueLib;
            groupedItems.computeIfAbsent(realKey, k -> new ArrayList()).add(item);
        }
        return groupedItems;
    }

    private static String getMessageFiltre(String debut, String fin) {
        StringBuilder html = new StringBuilder("R\u00c3\u00a9sultats filtr\u00c3\u00a9s ");
        if (debut != null && !debut.isEmpty() && fin != null && !fin.isEmpty()) {
            html.append("entre le <strong>").append(debut).append("</strong> et le <strong>").append(fin).append("</strong>.");
        } else if (debut != null && !debut.isEmpty()) {
            html.append("\u00c3\u00a0 partir du <strong>").append(debut).append("</strong>.");
        } else if (fin != null && !fin.isEmpty()) {
            html.append("jusqu'au <strong>").append(fin).append("</strong>.");
        }
        return html.toString();
    }

    public static String generateKanbanHTML(List<?> items, Map<String, List<Object>> groupedItemsGen, String tablePrincipal, String classePrincipal, String classe, String titre, String groupField, String groupFieldLib, String titleField, String descriptionField, String descriptionFieldLib, String linkClick, String colonnePk, String bgColor, String bgColorBadge, String hauteur, String largeur, boolean showCount, boolean showKanbanCardContent, boolean showDateFilter, boolean activeDifferentColor, boolean activeBtnDuplicate, boolean activeBtnRemove, String debut, String fin, Connection c) throws Exception {
        if (items == null || items.isEmpty()) {
            return "<div class='kanban-container'></div>";
        }
        Map<String, List<Object>> groupedItems = Kanban.extractGroupedItems(items, groupedItemsGen, groupField, groupFieldLib);
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"kanban-wrapper\" onmouseover=\"showScrollButtons(this)\" onmouseout=\"hideScrollButtons(this)\">\n");
        titre = titre != null && !titre.trim().isEmpty() ? titre : "Tableau Kanban";
        html.append("  <div class=\"kanban-big-title-card\">\n");
        html.append("    <div class=\"kanban-title-card\">\n");
        html.append("      <h1 class=\"kanban-big-title\">").append(titre).append("</h1>\n");
        if (showDateFilter) {
            html.append("      <form method=\"get\" id=\"date-filter-form\" class=\"date-filter-form d-flex\" style=\"gap: 5px;\">\n");
            html.append("        <input type=\"date\" id=\"dateDebut\" name=\"dateDebut\" class=\"form-control form-control-sm\" value=\"").append(debut != null ? debut : "").append("\" />\n");
            html.append("        <input type=\"date\" id=\"dateFin\" name=\"dateFin\" class=\"form-control form-control-sm\" value=\"").append(fin != null ? fin : "").append("\" />\n");
            html.append("        <button type=\"submit\" class=\"btn btn-sm btn-primary\">Filtrer</button>\n");
            html.append("      </form>\n");
        }
        html.append("    </div>\n");
        if (debut != null && !debut.isEmpty() || fin != null && !fin.isEmpty()) {
            html.append("    <div style=\"margin-top:10px;\">\n");
            html.append("      <div class=\"alert alert-secondary mb-0\" style=\"background-color: #c7c8c9; font-size: 0.9rem;\">\n");
            html.append("        <p style=\"font-size:large;\">\n");
            html.append(Kanban.getMessageFiltre(debut, fin));
            html.append("        </p>\n");
            html.append("      </div>\n");
            html.append("    </div>\n");
        }
        html.append("  </div>\n");
        html.append("<button class=\"scroll-btn left\" onclick=\"scrollKanban(this, -1)\">&#9664;</button>\n");
        html.append("<button class=\"scroll-btn right\" onclick=\"scrollKanban(this, 1)\">&#9654;</button>\n");
        html.append("<div id=\"kanban-container\" class=\"kanban-container\" data-mapping=\"").append(classePrincipal).append("\" data-colonne=\"").append(groupField).append("\" data-classe=\"").append(classe).append("\" data-table=\"").append(tablePrincipal).append("\">\n");
        int colorIndex = 0;
        for (Map.Entry<String, List<Object>> group : groupedItems.entrySet()) {
            String bgColorTitle;
            String realKey = group.getKey();
            String idGroupe = realKey.split("__")[0];
            String groupeLib = realKey.split("__")[1];
            String sectionId = "kanban_" + group.getKey().replaceAll("[^a-zA-Z0-9]", "_");
            String data = idGroupe;
            String largeurCss = "";
            if (largeur != null && !largeur.trim().isEmpty()) {
                largeurCss = "style=\"width:" + largeur + " !important;\"";
            }
            html.append("<div class=\"kanban-section\" " + largeurCss + " data-id=\"").append(data).append("\" id=\"").append(sectionId).append("\">\n");
            html.append("<div style=\"display: flex; align-items: center; justify-content: space-between; margin-bottom: 10px;\">\n");
            if (activeDifferentColor) {
                bgColorTitle = KANBAN_COLORS[colorIndex % KANBAN_COLORS.length];
                ++colorIndex;
            } else {
                bgColorTitle = "#27C475";
            }
            html.append("  <div class=\"cardTitle\" style=\"background:linear-gradient(0deg, rgba(255,255,255,0.30), rgba(255,255,255,0.30)), ").append(bgColorTitle).append("; display: flex; align-items: center; justify-content: space-between; padding: 10px; border-radius: 10px;\">\n");
            html.append("    <h2 class=\"title\">").append(groupeLib).append("</h2>\n");
            html.append("  </div>\n");
            html.append("  <button class=\"toggle-section-btn\" onclick=\"toggleSection('").append(sectionId).append("')\">&#9660;</button>\n");
            html.append("  <div style=\"display: flex; align-items: center; gap: 8px; margin-left: 12px;\">\n");
            if (showCount) {
                String badgeStyle = "background-color:" + bgColorBadge + ";";
                html.append("    <span id=\"count_").append(idGroupe).append("\" style=\"").append(badgeStyle).append(" padding: 6px 12px; border-radius: 999px; font-size: 13px; font-weight: bold; color: white;\">").append(group.getValue().size()).append("</span>\n");
            }
            html.append("    <div class=\"menu-wrapper\" style=\"position: relative;\">\n");
            html.append("      <button onclick=\"toggleMenu(this)\" style=\"background: none; border: none; font-size: 18px; cursor: pointer;\">&#8942;</button>\n");
            html.append("      <div class=\"export-menu\" style=\"position: absolute; top: 24px; right: 0; background: white; border: 1px solid #ddd; border-radius: 4px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); display: none; z-index: 100; min-width: 120px;\">\n");
            html.append("        <div onclick=\"exportSectionToPDF('" + sectionId + "')\" style=\"padding: 8px 12px; cursor: pointer; font-size: 14px; color: #333;\">Exporter</div>\n");
            html.append("      </div>\n");
            html.append("    </div>\n");
            html.append("  </div>\n");
            html.append("</div>\n");
            html.append("<hr style=\"border-top: 4px solid #eee !important;\">\n");
            html.append("<div class=\"kanban-data-container\"");
            if (hauteur != null && !hauteur.trim().isEmpty()) {
                html.append(" style=\"height:").append(hauteur).append("; overflow-y:auto;\"");
            }
            html.append(">\n");
            for (Object task : group.getValue()) {
                String descriptionValue;
                Field titleF = Kanban.getFieldRecursive(task.getClass(), titleField);
                Field descF = Kanban.getFieldRecursive(task.getClass(), descriptionField);
                titleF.setAccessible(true);
                descF.setAccessible(true);
                String title = String.valueOf(titleF.get(task));
                String desc = String.valueOf(descF.get(task));
                Field pkF = Kanban.getFieldRecursive(task.getClass(), colonnePk);
                pkF.setAccessible(true);
                Object pkValue = pkF.get(task);
                String string = descriptionValue = descriptionFieldLib != null && !descriptionFieldLib.trim().isEmpty() ? descriptionFieldLib : descriptionField;
                if (linkClick != null && !linkClick.trim().isEmpty() && pkValue != null) {
                    String fullLink = linkClick.contains("?") ? linkClick + "&" + colonnePk + "=" + pkValue : linkClick + "?" + colonnePk + "=" + pkValue;
                    title = "<a href=\"#\" onclick=\"window.open('" + fullLink + "', '_blank').focus(); return false;\">" + title + "</a>";
                }
                String kanbanCardStyle = "style=\"background-color:" + bgColor + " !important;\"";
                html.append("<div class=\"kanban-card\" ").append(kanbanCardStyle).append(" data-group=\"").append(group.getKey()).append("\" ").append(" data-id=\"").append(pkValue).append("\">\n");
                html.append("  <div style=\"display:flex; align-items:center; position:relative;\">\n");
                html.append("    <h3 class=\"kanban-card-title\" style=\"margin:0; flex-grow:1;\">").append(title).append("</h3>\n");
                html.append("    <div style=\"display:flex; gap:5px;\">\n");
                if (activeBtnDuplicate) {
                    html.append("      <button class=\"duplicate-btn\" onclick=\"dupliquerCard('").append(pkValue).append("')\">+</button>\n");
                }
                if (activeBtnRemove) {
                    html.append("      <button class=\"remove-btn\" onclick=\"removeCard('").append(pkValue).append("')\">-</button>\n");
                }
                html.append("    </div>\n");
                html.append("  </div>\n");
                if (showKanbanCardContent) {
                    html.append("<div class=\"kanban-card-content\">\n");
                    html.append("<div class=\"kanban-card-data\">\n");
                    if (task instanceof IKanban) {
                        String customContent = ((IKanban)task).getKanbanCardDataContent(c);
                        if (customContent != null && !customContent.trim().isEmpty()) {
                            html.append(customContent).append("\n");
                        } else {
                            html.append("<h4>").append(descriptionValue).append("</h4>\n");
                            html.append("<p>").append(desc).append("</p>\n");
                        }
                    } else {
                        html.append("<h4>").append(descriptionValue).append("</h4>\n");
                        html.append("<p>").append(desc).append("</p>\n");
                    }
                    html.append("</div></div>\n");
                }
                html.append("</div>\n");
            }
            html.append("</div>\n");
            html.append("</div>\n");
        }
        html.append("</div>\n");
        html.append("</div>\n");
        return html.toString();
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getContentHtml() throws Exception {
        if (this.contentHtml == null) {
            this.makeContentHtml();
        }
        return this.contentHtml;
    }

    private void makeContentHtml() throws Exception {
        String condition;
        Connection c = this.connection;
        boolean open = false;
        if (c == null) {
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            open = true;
        }
        LinkedHashMap<String, List<Object>> groupedItems = new LinkedHashMap<String, List<Object>>();
        if (this.entete != null) {
            Object[] dataEntete;
            for (Object data : dataEntete = CGenUtil.rechercher(this.entete, null, null, c, "")) {
                String key = CGenUtil.getValeurFieldByMethod((ClassMAPTable)data, this.entete.getAttributIDName()) + "__" + CGenUtil.getValeurFieldByMethod((ClassMAPTable)data, this.getColonneEntete());
                groupedItems.put(key, new ArrayList());
            }
        }
        String string = condition = this.aWhere != null ? this.aWhere : "";
        if (condition.isEmpty() && (this.getDebut() != null || this.getFin() != null) && this.entite instanceof IKanban) {
            condition = ((IKanban)((Object)this.entite)).getConditionDateFilter(this.getDebut(), this.getFin());
        }
        try {
            if (this.data == null) {
                this.data = CGenUtil.rechercher(this.entite, null, null, c, condition);
            }
            String html = Kanban.generateKanbanHTML(Arrays.asList(this.data), groupedItems, this.getTablePrincipal(), this.getDuplicateClasse(), this.getClasse(), this.getTitre(), this.getColonneGrouper(), this.getColonneGrouperLib(), this.getColonneTitre(), this.getColonneDescription(), this.getColonneDescriptionLib(), this.getLinkClick(), this.getColonnePk(), this.getBgKanbanCard(), this.getBgKanbanBadge(), this.getHauteurSection(), this.getLargeurSection(), this.isShowCount(), this.isShowKanbanCardContent(), this.isShowDateFilter(), this.isAvtiveDifferentColor(), this.isActiveBtnDuplicate(), this.isActiveBtnRemove(), this.getDebut(), this.getFin(), this.getConnection());
            this.setContentHtml(html);
        }
        catch (Exception ex) {
            if (open) {
                try {
                    c.rollback();
                }
                catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw ex;
        }
        finally {
            if (open) {
                try {
                    c.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getDuplicateClasse() {
        return this.duplicateClasse;
    }

    public void setDuplicateClasse(String duplicateClasse) {
        this.duplicateClasse = duplicateClasse;
    }

    public ClassMAPTable getEntite() {
        return this.entite;
    }

    public void setEntite(ClassMAPTable entite) {
        this.entite = entite;
    }

    public String getaWhere() {
        return this.aWhere;
    }

    public void setaWhere(String aWhere) {
        this.aWhere = aWhere;
    }

    public String getColonneGrouper() {
        return this.colonneGrouper;
    }

    public void setColonneGrouper(String colonneGrouper) {
        this.colonneGrouper = colonneGrouper;
    }

    public String getClasse() {
        return this.classe;
    }

    public String getColonneGrouperLib() {
        return this.colonneGrouperLib;
    }

    public void setColonneGrouperLib(String colonneGrouperLib) {
        this.colonneGrouperLib = colonneGrouperLib;
    }

    public String getTablePrincipal() {
        return this.tablePrincipal;
    }

    public void setTablePrincipal(String tablePrincipal) {
        this.tablePrincipal = tablePrincipal;
    }

    public boolean isAvtiveDifferentColor() {
        return this.avtiveDifferentColor;
    }

    public void setAvtiveDifferentColor(boolean avtiveDifferentColor) {
        this.avtiveDifferentColor = avtiveDifferentColor;
    }

    public boolean isShowCount() {
        return this.showCount;
    }

    public void setShowCount(boolean showCount) {
        this.showCount = showCount;
    }

    public String getColonneTitre() {
        return this.colonneTitre;
    }

    public void setColonneTitre(String colonneTitre) {
        this.colonneTitre = colonneTitre;
    }

    public String getColonneDescription() {
        return this.colonneDescription;
    }

    public void setColonneDescription(String colonneDescription) {
        this.colonneDescription = colonneDescription;
    }

    public String getColonneDescriptionLib() {
        return this.colonneDescriptionLib;
    }

    public void setColonneDescriptionLib(String colonneDescriptionLib) {
        this.colonneDescriptionLib = colonneDescriptionLib;
    }

    public String getHauteurSection() {
        return this.hauteurSection;
    }

    public void setHauteurSection(String hauteurSection) {
        this.hauteurSection = hauteurSection;
    }

    private void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int getTheme() {
        return this.theme;
    }

    public void setTheme(int theme) throws Exception {
        if (theme != 0 && theme != 1) {
            throw new Exception("Valeur theme non valide (choisi 1 si dark ou 0 si light)");
        }
        this.theme = theme;
    }

    public boolean isShowKanbanCardContent() {
        return this.showKanbanCardContent;
    }

    public void setShowKanbanCardContent(boolean showKanbanCardContent) {
        this.showKanbanCardContent = showKanbanCardContent;
    }

    public String getLinkClick() {
        return this.linkClick;
    }

    public void setLinkClick(String linkClick) {
        this.linkClick = linkClick;
    }

    public String getColonnePk() {
        return this.colonnePk;
    }

    public void setColonnePk(String colonnePk) {
        this.colonnePk = colonnePk;
    }

    public String getBgKanbanCard() {
        if (this.bgKanbanCard == null || this.bgKanbanCard.trim().isEmpty()) {
            this.setBgKanbanCard(KANBAN_DEFAULT_BG);
        }
        return this.bgKanbanCard;
    }

    public void setBgKanbanCard(String bgKanbanCard) {
        this.bgKanbanCard = bgKanbanCard;
    }

    public String getBgKanbanBadge() {
        if (this.bgKanbanBadge == null || this.bgKanbanBadge.trim().isEmpty()) {
            this.setBgKanbanBadge(BADGE_DEFAULT_BG);
        }
        return this.bgKanbanBadge;
    }

    public void setBgKanbanBadge(String bgKanbanBadge) {
        this.bgKanbanBadge = bgKanbanBadge;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContentHtmlGraph() throws Exception {
        if (this.contentHtmlGraph == null) {
            this.makeContentHtmlGraph();
        }
        return this.contentHtmlGraph;
    }

    private void setContentHtmlGraph(String contentHtmlGraph) {
        this.contentHtmlGraph = contentHtmlGraph;
    }

    private void makeContentHtmlGraph() throws Exception {
        Connection c = this.connection;
        boolean open = false;
        if (c == null) {
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            open = true;
        }
        String condition = this.aWhere != null ? this.aWhere : "";
        try {
            if (this.data == null) {
                this.data = CGenUtil.rechercher(this.entite, null, null, c, condition);
            }
            String html = this.generateGraphHTML(Arrays.asList(this.data), this.getTitre(), this.getColonneGrouper());
            this.setContentHtmlGraph(html);
        }
        catch (Exception ex) {
            if (open) {
                try {
                    c.rollback();
                }
                catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw ex;
        }
        finally {
            if (open) {
                try {
                    c.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String generateGraphHTML(List<?> items, String titre, String colonneGrouper) throws Exception {
        Map<String, Integer> groupedItems = Kanban.extractGroupedItemCounts(items, colonneGrouper);
        Gson gson = new Gson();
        String json = gson.toJson(groupedItems);
        return "<div id=\"chart\"></div>\n<script>\n    const kanbanData = " + json + ";\n\n    const categories = Object.keys(kanbanData);\n    const values = Object.values(kanbanData);\n\n    const options = {\n        chart: {\n            type: 'bar',\n            height: 350,\n            width: '100%',\n        },\n        series: [{\n            name: 'Nombre',\n            data: values\n        }],\n        xaxis: {\n            categories: categories\n        },\n        title: {\n            text: '" + titre + "',\n            align: 'center'\n        },\n        responsive: [{\n            breakpoint: 700,\n            options: {\n                chart: {\n                    height: 300\n                }\n            }\n        }]\n    };\n\n    const chart = new ApexCharts(document.querySelector(\"#chart\"), options);\n    chart.render();\n</script>\n";
    }

    public String getDebut() {
        return this.debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return this.fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public boolean isShowDateFilter() {
        return this.showDateFilter;
    }

    public void setShowDateFilter(boolean showDateFilter) {
        this.showDateFilter = showDateFilter;
    }

    public ClassMAPTable getEntete() {
        return this.entete;
    }

    public void setEntete(ClassMAPTable entete) {
        this.entete = entete;
    }

    public String getColonneEntete() {
        return this.colonneEntete;
    }

    public void setColonneEntete(String colonneEntete) {
        this.colonneEntete = colonneEntete;
    }

    public boolean isActiveBtnDuplicate() {
        return this.activeBtnDuplicate;
    }

    public void setActiveBtnDuplicate(boolean activeBtnDuplicate) {
        this.activeBtnDuplicate = activeBtnDuplicate;
    }

    public String getLargeurSection() {
        return this.largeurSection;
    }

    public void setLargeurSection(String largeurSection) {
        this.largeurSection = largeurSection;
    }

    public boolean isActiveBtnRemove() {
        return this.activeBtnRemove;
    }

    public void setActiveBtnRemove(boolean activeBtnRemove) {
        this.activeBtnRemove = activeBtnRemove;
    }
}

