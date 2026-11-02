/*
 * Decompiled with CFR 0.152.
 */
package chatbot.templates;

import bean.ClassFille;
import java.sql.Connection;

public class ExempleFille
extends ClassFille {
    String id;
    String idMere;
    String var2;

    public ExempleFille() {
        super.setNomTable("exemple_fille");
    }

    @Override
    public String getTuppleID() {
        return this.id;
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public String getNomClasseMere() {
        return "chatbot.templates.ExempleMere";
    }

    @Override
    public String getLiaisonMere() {
        return "idMere";
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("EF", "getSeqExempleFille");
        this.setId(this.makePK(c));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMere() {
        return this.idMere;
    }

    public void setIdMere(String idMere) {
        this.idMere = idMere;
    }

    public String getVar2() {
        return this.var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }
}

