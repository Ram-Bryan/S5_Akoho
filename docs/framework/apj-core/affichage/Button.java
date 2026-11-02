/*
 * Decompiled with CFR 0.152.
 */
package affichage;

public class Button {
    private String lien;
    private String rajoutLien;
    private String nom;
    private String but;
    private String butOnePage;
    private String classe;
    private boolean isOnePage = false;
    private String html;

    public Button(String lien, String rajoutLien, String nom, String classe, String but, String butOnePage) {
        this.lien = lien;
        this.rajoutLien = rajoutLien;
        this.nom = nom;
        this.classe = classe;
        this.but = but;
        this.butOnePage = butOnePage;
    }

    public Button(String html) {
        this.html = html;
    }

    public void setRajoutLien(String rajoutLien) {
        this.rajoutLien = rajoutLien;
    }

    public String getRajoutLien() {
        return this.rajoutLien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getLien() {
        return this.lien;
    }

    public void setOnePage(boolean isOnePage) {
        this.isOnePage = isOnePage;
    }

    public boolean isOnePage() {
        return this.isOnePage;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    String getTarifTarget() {
        return "['butjsp']";
    }

    String getBut() {
        if (this.isOnePage && this.butOnePage != null && !this.butOnePage.isEmpty()) {
            return "&bute=" + this.butOnePage;
        }
        if (this.but != null && !this.but.isEmpty()) {
            return "&bute=" + this.but;
        }
        return "";
    }

    public void makeHtml() {
        this.html = this.isOnePage ? "<a href=\"#\"><button class=\"" + this.classe + "\" style=\"margin-right: 10px\" onclick=\"loadTarifGet(event, '" + this.rajoutLien + this.getBut() + "', " + this.getTarifTarget() + ", '&isOnePage=" + (this.isOnePage ? 1 : 0) + "')\">" + this.nom + "</button></a>" : "<a href=\"" + this.lien + this.rajoutLien + this.getBut() + "\" style=\"margin-right: 10px\"><button class=\"" + this.classe + "\">" + this.nom + "</button></a>";
    }

    public String getHtml() {
        if (this.html == null || this.html.isEmpty()) {
            this.makeHtml();
        }
        return this.html;
    }
}

