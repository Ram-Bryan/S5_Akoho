/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;

public class RejetTable
extends ClassMAPTable {
    private String id;
    private Date daty;
    private String remarque;
    private String mere;
    private String motif;
    private String categorierejet;

    public RejetTable(String id, Date daty, String remarque, String mere, String motif, String categorierejet) {
        this.id = id;
        this.daty = daty;
        this.remarque = remarque;
        this.mere = mere;
        this.motif = motif;
        this.categorierejet = categorierejet;
    }

    public RejetTable() {
    }

    public RejetTable(Date daty, String remarque, String mere, String motif, String categorierejet) {
        this.daty = daty;
        this.remarque = remarque;
        this.mere = mere;
        this.motif = motif;
        this.categorierejet = categorierejet;
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("REJ", "getSeqRejet");
        this.setId(this.makePK(c));
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

    public void setId(String id) {
        this.id = id;
    }

    public Date getDaty() {
        return this.daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public String getRemarque() {
        return this.remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getMere() {
        return this.mere;
    }

    public void setMere(String mere) {
        this.mere = mere;
    }

    public String getMotif() {
        return this.motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getCategorierejet() {
        return this.categorierejet;
    }

    public void setCategorierejet(String categorierejet) {
        this.categorierejet = categorierejet;
    }
}

