/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package mg.spat;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Timestamp;

public class AttacherFichier
extends ClassMAPTable {
    private String id;
    private String chemin;
    private String libelle;
    private String mere;
    private Timestamp daty;

    public String getChemin() {
        return this.chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getMere() {
        return this.mere;
    }

    public void setMere(String mere) {
        this.mere = mere;
    }

    public Timestamp getDaty() {
        return this.daty;
    }

    public void setDaty(Timestamp daty) {
        this.daty = daty;
    }

    public AttacherFichier() {
        super.setNomTable("ATTACHER_FICHIER");
    }

    public void construirePK(Connection c) throws Exception {
        this.preparePk("ATT", "GETSEQ_ATTACHER_FICHIER");
        this.setId(this.makePK(c));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }
}

