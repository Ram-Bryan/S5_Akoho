/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  utilitaire.UtilDB
 */
package reporting.mail;

import java.sql.Connection;
import java.sql.Date;
import reporting.mail.MailContentGroupe;
import reporting.utilitaire.UtilitaireCR;
import utilitaire.UtilDB;

public class PageMailGroupe {
    public String genererPageMailGroupe(MailContentGroupe[] mailContentGroupes, int nature, Date daty, Connection c) throws Exception {
        if (c == null) {
            c = new UtilDB().GetConn();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(UtilitaireCR.pageGroupeEntete());
        for (int i = 0; i < mailContentGroupes.length; ++i) {
            sb.append(mailContentGroupes[i].genererMailContentGroupe(nature, daty, c));
        }
        sb.append(UtilitaireCR.basPage());
        return sb.toString();
    }

    public String genererPageMailGroupeAWhere(MailContentGroupe[] mailContentGroupes, Connection c, String aWhere) throws Exception {
        if (c == null) {
            c = new UtilDB().GetConn();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(UtilitaireCR.pageGroupeEntete());
        for (int i = 0; i < mailContentGroupes.length; ++i) {
            sb.append(mailContentGroupes[i].genererMailContentGroupeAWhere(c, aWhere));
        }
        sb.append(UtilitaireCR.basPage());
        return sb.toString();
    }
}

