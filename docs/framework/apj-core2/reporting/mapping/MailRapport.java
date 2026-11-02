/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.TypeObjet
 */
package reporting.mapping;

import bean.TypeObjet;
import java.sql.Connection;

public class MailRapport
extends TypeObjet {
    public MailRapport() {
        this.setNomTable("MAILRAPPORT");
    }

    public void construirePK(Connection c) throws Exception {
        this.preparePk("MR", "getseqMailRapport");
        this.setId(this.makePK(c));
    }
}

