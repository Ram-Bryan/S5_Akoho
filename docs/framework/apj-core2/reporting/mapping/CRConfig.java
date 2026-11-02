/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package reporting.mapping;

import bean.ClassMAPTable;

public class CRConfig
extends ClassMAPTable {
    private String id;
    private String heureEnvoi;
    private String jourEnvoi;
    private int dateEnvoi;
    private int finMois;
    private int journaliere;
    private int hebdomadaire;
    private int mensuel;

    public CRConfig() {
        this.setNomTable("CRCONFIG");
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeureEnvoi() {
        return this.heureEnvoi;
    }

    public void setHeureEnvoi(String heureEnvoi) {
        this.heureEnvoi = heureEnvoi;
    }

    public String getJourEnvoi() {
        return this.jourEnvoi;
    }

    public void setJourEnvoi(String jourEnvoi) {
        this.jourEnvoi = jourEnvoi;
    }

    public int getDateEnvoi() {
        return this.dateEnvoi;
    }

    public void setDateEnvoi(int dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public int getJournaliere() {
        return this.journaliere;
    }

    public void setJournaliere(int journaliere) {
        this.journaliere = journaliere;
    }

    public int getHebdomadaire() {
        return this.hebdomadaire;
    }

    public void setHebdomadaire(int hebdomadaire) {
        this.hebdomadaire = hebdomadaire;
    }

    public int getMensuel() {
        return this.mensuel;
    }

    public void setMensuel(int mensuel) {
        this.mensuel = mensuel;
    }

    public int getFinMois() {
        return this.finMois;
    }

    public void setFinMois(int finMois) {
        this.finMois = finMois;
    }
}

