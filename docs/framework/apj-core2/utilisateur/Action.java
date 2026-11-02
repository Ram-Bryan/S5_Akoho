/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package utilisateur;

import bean.ClassMAPTable;
import java.sql.Connection;

public class Action
extends ClassMAPTable {
    private String id;
    private String val;
    private String desce;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVal() {
        return this.val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getDesce() {
        return this.desce;
    }

    public void setDesce(String desce) {
        this.desce = desce;
    }

    public void construirePK(Connection c) throws Exception {
        super.setNomTable("action");
        this.preparePk("ACT", "SEQ_ACT");
        this.setId(this.makePK(c));
    }

    public String getTuppleID() {
        return this.getId();
    }

    public String getAttributIDName() {
        return "id";
    }
}

