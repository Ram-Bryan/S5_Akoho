/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassEtat
 */
package notification;

import bean.ClassEtat;
import java.sql.Date;

public class NotificationLibelle
extends ClassEtat {
    private String id;
    private String objet;
    private String message;
    private String iduser;
    private String direction;
    private String service;
    private String destinataire;
    private String idobjet;
    private String lien;
    private String iduser_recevant;
    private String prestation;
    private String heure;
    private Date daty;
    private int priorite;
    private int classee;

    public NotificationLibelle() {
        this.setNomTable("NOTIFICATION");
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

    public String getObjet() {
        return this.objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrestation() {
        return this.prestation;
    }

    public void setPrestation(String prestation) {
        this.prestation = prestation;
    }

    public String getIdobjet() {
        return this.idobjet;
    }

    public void setIdobjet(String idobjet) {
        this.idobjet = idobjet;
    }

    public String getIduser() {
        return this.iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getPriorite() {
        return this.priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    public int getClassee() {
        return this.classee;
    }

    public void setClassee(int classee) {
        this.classee = classee;
    }

    public String getLien() {
        return this.lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public Date getDaty() {
        return this.daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public String getIduser_recevant() {
        return this.iduser_recevant;
    }

    public void setIduser_recevant(String iduser_recevant) {
        this.iduser_recevant = iduser_recevant;
    }

    public String getDestinataire() {
        return this.destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getHeure() {
        return this.heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
}

