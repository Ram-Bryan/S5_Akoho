/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import java.sql.Date;

public class AlerteMessage {
    private String id;
    private Date daty;
    private String message;
    private String objet;
    private String lieu;
    private String remarque;
    private int etat;

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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObjet() {
        return this.objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getLieu() {
        return this.lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getRemarque() {
        return this.remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public int getEtat() {
        return this.etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
}

