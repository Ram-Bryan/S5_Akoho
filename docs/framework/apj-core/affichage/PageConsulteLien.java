/*
 * Decompiled with CFR 0.152.
 */
package affichage;

public class PageConsulteLien {
    private String lien;
    private String queryString;

    public PageConsulteLien() {
    }

    public PageConsulteLien(String lien, String queryString) {
        this.setLien(lien);
        this.setQueryString(queryString);
    }

    public String getLien() {
        return this.lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}

