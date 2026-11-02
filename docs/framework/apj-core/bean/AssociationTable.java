/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;

public class AssociationTable
extends ClassMAPTable {
    public String id;
    public String t1;
    public String t2;
    private String tUnion;
    public String vueSituation2;
    public String vueSituation1;

    public AssociationTable() {
    }

    public AssociationTable(String id, String t1, String t2, String tUnion, String v1, String v2) {
        this.setNomTable("AssociationTable");
        this.setId(id);
        this.setT1(t1);
        this.setT2(t2);
        this.setVueSituation1(v1);
        this.setVueSituation2(v2);
        this.setTUnion(tUnion);
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public String getTuppleID() {
        return this.getId();
    }

    public String getId() {
        return this.id;
    }

    public String getT1() {
        return this.t1;
    }

    public String getT2() {
        return this.t2;
    }

    public String getVueSituation1() {
        return this.vueSituation1;
    }

    public String getVueSituation2() {
        return this.vueSituation2;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public void setVueSituation1(String vueSituation1) {
        this.vueSituation1 = vueSituation1;
    }

    public void setVueSituation2(String vueSituation2) {
        this.vueSituation2 = vueSituation2;
    }

    public void setTUnion(String tUnion) {
        this.tUnion = tUnion;
    }

    public String getTUnion() {
        return this.tUnion;
    }
}

