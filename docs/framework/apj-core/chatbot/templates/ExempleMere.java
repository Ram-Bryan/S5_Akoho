/*
 * Decompiled with CFR 0.152.
 */
package chatbot.templates;

import bean.ClassMere;
import java.sql.Connection;

public class ExempleMere
extends ClassMere {
    String id;
    String var1;
    String var2;

    public ExempleMere() {
        super.setNomTable("exemple_mere");
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
    public String getNomClasseFille() {
        return "chatbot.templates.ExempleFille";
    }

    @Override
    public String getLiaisonFille() {
        return "idMere";
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("EM", "getSeqExempleMere");
        this.setId(this.makePK(c));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVar1() {
        return this.var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar2() {
        return this.var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }
}

