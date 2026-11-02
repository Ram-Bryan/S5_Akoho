/*
 * Decompiled with CFR 0.152.
 */
package chatbot.templates;

import bean.ClassMAPTable;

public class Client
extends ClassMAPTable {
    String id;
    String nom;
    int age;

    public Client() {
        super.setNomTable("client");
    }

    @Override
    public String getTuppleID() {
        return this.id;
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }
}

