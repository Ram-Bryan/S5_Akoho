/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.CGenUtil
 *  bean.ClassMAPTable
 *  utilitaire.UtilDB
 */
package reporting.mail;

import bean.CGenUtil;
import bean.ClassMAPTable;
import java.sql.Connection;
import java.util.Map;
import reporting.mapping.MailRappelTemplate;
import reporting.mapping.MailRappelTemplateParagraphe;
import reporting.utilitaire.UtilitaireCR;
import utilitaire.UtilDB;

public class MailRappel {
    private String lienLogo;
    private String nom;
    private String titre;
    private String template;
    private String[] paragraphe;
    private String[] contacts;
    private Map<String, Object> data;

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

    public String[] getParagraphe() {
        return this.paragraphe;
    }

    public void setParagraphe(String[] paragraphe) {
        this.paragraphe = paragraphe;
    }

    public String[] getContacts() {
        return this.contacts;
    }

    public void setContacts(String[] contacts) {
        this.contacts = contacts;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void loadTemplate() throws Exception {
        this.loadTemplate(this.getTemplate(), null);
    }

    public void loadTemplate(String template) throws Exception {
        this.loadTemplate(template, null);
    }

    public void loadTemplate(String template, Connection c) throws Exception {
        this.setTemplate(template);
        this.loadTemplate(c);
    }

    public void loadTemplate(Connection c) throws Exception {
        if (this.getTemplate() != null && !this.getTemplate().equals("")) {
            String aWhere;
            MailRappelTemplate[] mailRappelTemplates;
            if (c == null) {
                c = new UtilDB().GetConn();
            }
            if ((mailRappelTemplates = (MailRappelTemplate[])CGenUtil.rechercher((ClassMAPTable)new MailRappelTemplate(), null, null, (Connection)c, (String)(aWhere = " and id = '" + this.getTemplate() + "'"))).length == 0) {
                throw new Exception("Template introuvable");
            }
            this.setLienLogo(mailRappelTemplates[0].getLienLogo());
            this.setNom(mailRappelTemplates[0].getNom());
            this.setTitre(mailRappelTemplates[0].getTitre());
            String apresWhere = " and idMailRappelTemplate = '" + mailRappelTemplates[0].getId() + "' order by ordre";
            MailRappelTemplateParagraphe[] mailRappelTemplateParagraphes = (MailRappelTemplateParagraphe[])CGenUtil.rechercher((ClassMAPTable)new MailRappelTemplateParagraphe(), null, null, (Connection)c, (String)apresWhere);
            String[] paragraphes = new String[mailRappelTemplateParagraphes.length];
            for (int i = 0; i < mailRappelTemplateParagraphes.length; ++i) {
                paragraphes[i] = mailRappelTemplateParagraphes[i].getParagraphe();
            }
            this.setParagraphe(paragraphes);
        }
    }

    public String genererMailRappelTemplateHtml() {
        String mail = "";
        mail = mail + UtilitaireCR.pageRappelEntete();
        mail = mail + UtilitaireCR.pageRappelLogoTitre(this.getLienLogo(), this.getNom(), this.getTitre());
        mail = mail + UtilitaireCR.pageRappelCorpsMessage(this.getParagraphe(), this.getContacts());
        mail = mail + UtilitaireCR.pageRappelFooter();
        return mail;
    }

    public String genererMailRappel() {
        String mail = this.genererMailRappelTemplateHtml();
        if (this.getData() != null) {
            mail = UtilitaireCR.bindDynamicData(mail, this.getData());
        }
        return mail;
    }
}

