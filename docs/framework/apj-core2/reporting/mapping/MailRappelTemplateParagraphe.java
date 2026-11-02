/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassFille
 */
package reporting.mapping;

import bean.ClassFille;

public class MailRappelTemplateParagraphe
extends ClassFille {
    private String id;
    private String idMailRappelTemplate;
    private String paragraphe;
    private int ordre;

    public MailRappelTemplateParagraphe() {
        this.setNomTable("mailRappelTemplateParagraphe");
    }

    public String getId() {
        return this.id;
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMailRappelTemplate() {
        return this.idMailRappelTemplate;
    }

    public void setIdMailRappelTemplate(String idMailRappelTemplate) {
        this.idMailRappelTemplate = idMailRappelTemplate;
    }

    public String getParagraphe() {
        return this.paragraphe;
    }

    public void setParagraphe(String paragraphe) {
        this.paragraphe = paragraphe;
    }

    public int getOrdre() {
        return this.ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }
}

