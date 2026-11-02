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
import java.sql.Date;
import reporting.mail.MailContent;
import reporting.mapping.CRContent;
import reporting.utilitaire.UtilitaireCR;
import utilitaire.UtilDB;

public class PageMail {
    private String Objet;

    public String getObjet() {
        return this.Objet;
    }

    public void setObjet(String objet) {
        this.Objet = objet;
    }

    public MailContent[] allMailContent(Connection c) throws Exception {
        if (c == null) {
            c = new UtilDB().GetConn();
        }
        CRContent crContent = new CRContent();
        CRContent[] crContents = (CRContent[])CGenUtil.rechercher((ClassMAPTable)crContent, null, null, (Connection)c, (String)" order by ordre asc");
        MailContent[] mailContents = new MailContent[crContents.length];
        for (int i = 0; i < crContents.length; ++i) {
            mailContents[i] = MailContent.transformerCRtoMail(crContents[i], c);
        }
        return mailContents;
    }

    public String genererPageMail(int nature, Date daty, Connection c) throws Exception {
        if (c == null) {
            c = new UtilDB().GetConn();
        }
        MailContent[] mailContents = this.allMailContent(c);
        String page = this.genererPageMail(mailContents, nature, daty, c);
        return page;
    }

    public String genererPageMail(MailContent[] mailContents, int nature, Date daty, Connection c) throws Exception {
        if (c == null) {
            c = new UtilDB().GetConn();
        }
        String page = UtilitaireCR.pageEntete();
        for (int i = 0; i < mailContents.length; ++i) {
            page = page + mailContents[i].genererMailContent(nature, daty, c);
        }
        page = page + UtilitaireCR.basPage();
        return page;
    }

    public String genererPageMailAWhere(MailContent[] mailContents, Connection c, String aWhere) throws Exception {
        if (c == null) {
            c = new UtilDB().GetConn();
        }
        String page = UtilitaireCR.pageEntete();
        for (int i = 0; i < mailContents.length; ++i) {
            page = page + mailContents[i].genererMailContent(c, aWhere);
        }
        page = page + UtilitaireCR.basPage();
        return page;
    }
}

