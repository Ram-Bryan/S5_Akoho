/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package evenement;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;

public class Evenement
extends ClassMAPTable {
    private String id;
    private String titre;
    private Date daty;
    private String heure;
    private double startHour;
    private String id_Theme;
    private String description;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDaty() {
        return this.daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public String getHeure() {
        return this.heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public double getStartHour() {
        return this.startHour;
    }

    public void setStartHour(double start_Hour) {
        this.startHour = this.startHour;
    }

    public String getId_Theme() {
        return this.id_Theme;
    }

    public void setId_Theme(String id_Theme) {
        this.id_Theme = id_Theme;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Evenement() {
        this.setNomTable("EVENEMENT_CALENDRIER");
    }

    public void construirePK(Connection c) throws Exception {
        this.preparePk("EVT", "getseqEVENEMENT_CALENDRIER");
        this.setId(this.makePK(c));
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }
}

