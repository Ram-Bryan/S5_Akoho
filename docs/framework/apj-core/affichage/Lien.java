/*
 * Decompiled with CFR 0.152.
 */
package affichage;

public class Lien {
    private String page;
    private String nomArgument;
    private String valeurArg;
    private String html;
    private String apresLien;

    public Lien() {
    }

    public Lien(String p, String nomArg, String valA) {
        this.setPage(p);
        this.setNomArgument(nomArg);
        this.setValeurArg(valA);
    }

    public String getHtml(String valeurAffiche) {
        String retour = "";
        if (valeurAffiche == null || valeurAffiche.compareToIgnoreCase("") == 0) {
            valeurAffiche = this.getValeurArg();
        }
        retour = "<a href=" + this.getPage() + "&" + this.getNomArgument() + "=" + this.getValeurArg() + this.getApresLien() + ">";
        retour = retour + valeurAffiche;
        retour = retour + "</a>";
        return retour;
    }

    public String getPage() {
        return this.page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setNomArgument(String nomArgument) {
        this.nomArgument = nomArgument;
    }

    public String getNomArgument() {
        return this.nomArgument;
    }

    public void setValeurArg(String valeurArg) {
        this.valeurArg = valeurArg;
    }

    public String getValeurArg() {
        return this.valeurArg;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml() {
        return this.html;
    }

    public void setApresLien(String apresLien) {
        this.apresLien = apresLien;
    }

    public String getApresLien() {
        return this.apresLien;
    }
}

