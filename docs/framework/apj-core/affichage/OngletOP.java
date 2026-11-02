/*
 * Decompiled with CFR 0.152.
 */
package affichage;

public class OngletOP {
    private String lien;
    private String rajoutLien;
    private String nom;
    private String but;
    private String classe;
    private boolean isOnePage = false;
    private String html;

    public OngletOP(String lien, String rajoutLien, String nom, String classe, String but) {
        this.lien = lien;
        this.rajoutLien = rajoutLien;
        this.nom = nom;
        this.classe = classe;
        this.but = but;
    }

    public OngletOP(String html) {
        this.html = html;
    }

    public String getLien() {
        return this.lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getRajoutLien() {
        return this.rajoutLien;
    }

    public void setRajoutLien(String rajoutLien) {
        this.rajoutLien = rajoutLien;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setBut(String but) {
        this.but = but;
    }

    public String getClasse() {
        return this.classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public boolean isOnePage() {
        return this.isOnePage;
    }

    public void setOnePage(boolean isOnePage) {
        this.isOnePage = isOnePage;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    String getPageTarget() {
        return "['butjsp']";
    }

    String getBut() {
        return "&but=" + this.but;
    }

    public void makeHtml() {
        this.html = "<li class=\"" + this.classe + "\"><a class=\"btn btn-link\" onclick=\"loadPage('" + this.lien + this.getBut() + this.rajoutLien + "&isOnePage=" + (this.isOnePage ? 1 : 0) + "', " + this.getPageTarget() + ")\">" + this.nom + "</a></li>";
    }

    public String getHtml() {
        if (this.html == null || this.html.isEmpty()) {
            this.makeHtml();
        }
        return this.html;
    }
}

