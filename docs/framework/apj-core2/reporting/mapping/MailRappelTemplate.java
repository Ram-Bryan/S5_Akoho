/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMere
 */
package reporting.mapping;

import bean.ClassMere;

public class MailRappelTemplate
extends ClassMere {
    private String id;
    private String lienLogo;
    private String nom;
    private String titre;

    public MailRappelTemplate() {
        this.setNomTable("mailRappelTemplate");
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLienLogo() {
        return this.lienLogo;
    }

    public void setLienLogo(String lienLogo) {
        this.lienLogo = lienLogo;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}

