/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package affichage;

import affichage.PageRecherche;
import affichage.TableauRecherche;
import bean.ClassMAPTable;
import javax.servlet.http.HttpServletRequest;
import utilitaire.Utilitaire;

public class DashCard
extends PageRecherche {
    private String icon;
    private String progress;
    private String recap;
    private String[] colSommeAffiche;
    private String aWhere;
    private ClassMAPTable[] data;
    private String lienPage;
    private boolean avecDate;
    private int colSpan;
    private boolean sansRecap;

    public boolean isSansRecap() {
        return this.sansRecap;
    }

    public void setSansRecap(boolean sansRecap) {
        this.sansRecap = sansRecap;
    }

    public int getColSpan() {
        return this.colSpan;
    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
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

    public ClassMAPTable[] getData() {
        return this.data;
    }

    public void setData(ClassMAPTable[] data) {
        this.data = data;
    }

    public String getaWhere() {
        return this.aWhere;
    }

    public void setaWhere(String aWhere) {
        this.aWhere = aWhere;
    }

    public String[] getColSommeAffiche() {
        return this.colSommeAffiche;
    }

    public void setColSommeAffiche(String[] colSommeAffiche) {
        this.colSommeAffiche = colSommeAffiche;
    }

    public String getRecap() {
        return this.recap;
    }

    public void setRecap(String recap) {
        this.recap = recap;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getProgress() {
        return this.progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    @Override
    public String getHtml() throws Exception {
        this.makeDashCard();
        return super.getHtml();
    }

    public String makeUnitCardFooter(String libelle, String somme) {
        return "<div class=\"dashboard-card-row\"><span class=\"Label12pxRegular dashboard-card-label\">" + libelle + "</span><span class=\"Body12pxRegular dashboard-card-value\">" + somme + "</span></div>";
    }

    public String makeDashCardFooter() {
        StringBuilder html = new StringBuilder("<div class=\"dashboard-card-footer\">");
        TableauRecherche recap = this.getTableauRecap();
        for (int i = 0; i < this.getColSomme().length; ++i) {
            html.append(this.makeUnitCardFooter(this.getColSommeAffiche()[i], recap.getDataDirecte()[0][i + 2]));
        }
        html.append("</div>");
        return html.toString();
    }

    public String makeDashCardTitle() {
        if (!this.isSansRecap()) {
            return "<div class=\"dashboard-card-info\"><div class=\"h520pxSemibold dashboard-card-title\">" + this.getRecap() + "</div><div class=\"Body14pxSemiBold dashboard-card-subtitle\">" + this.getTitre() + "</div></div>";
        }
        return "<div class=\"dashboard-card-info\"><div class=\"Body14pxSemiBold dashboard-card-subtitle-second\">" + this.getTitre() + "</div></div>";
    }

    public void makeDashCard() {
        String html = "<div class=\"col-md-" + this.getColSpan() + " col-sm-6\"><div class=\"dashboard-card-wrapper\"><a href=\"" + this.getLienPage() + "\" class=\"dashboard-card-link\"><div class=\"box dashboard-card clickable\"><div class=\"box-body\"><div class=\"dashboard-card-header\"><div class=\"dashboard-card-content\"><div class=\"dashboard-card-avatar\"><span class=\"material-symbols-rounded\" style=\"font-size: 24px; color: var(--Text-primary);\">" + this.getIcon() + "</span></div>" + this.makeDashCardTitle() + "</div></div>" + this.makeDashCardFooter() + "</div></div></a></div></div>";
        this.setHtml(html);
    }

    public void creerObjetDashCard() throws Exception {
        this.creerObjetPage(this.getColSomme(), this.getColSomme());
        this.setLienPage(this.getLien() + "?but=" + this.getLienPage());
        this.setRecap(this.getTableauRecap().getDataDirecte()[0][1]);
        this.setData(this.getListe());
    }

    public void configFormulaire() {
        this.getFormu().getChamp("daty1").setLibelle("Date Min");
        this.getFormu().getChamp("daty1").setDefaut(Utilitaire.dateDuJour());
        this.getFormu().getChamp("daty2").setLibelle("Date Max");
        this.getFormu().getChamp("daty2").setDefaut(Utilitaire.dateDuJour());
    }

    public DashCard() {
        this.icon = "broken_image";
        this.colSpan = 4;
    }

    public DashCard(ClassMAPTable base, HttpServletRequest req, String[] colSomme, String[] colSommeAffiche, boolean avecDate) throws Exception {
        String[] stringArray;
        if (avecDate) {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = "daty";
        } else {
            stringArray = new String[]{};
        }
        this(base, req, stringArray, colSomme, colSommeAffiche);
        this.setAvecDate(avecDate);
    }

    public DashCard(ClassMAPTable base, HttpServletRequest req, String[] critere, String[] colSomme, String[] colSommeAffiche) throws Exception {
        super(base, req, critere, critere, 4, critere, critere.length);
        this.icon = "broken_image";
        this.colSpan = 4;
        if (critere.length != 0) {
            this.configFormulaire();
        }
        this.setLienPage("#");
        this.setColSomme(colSomme);
        this.colSommeAffiche = colSommeAffiche;
    }
}

