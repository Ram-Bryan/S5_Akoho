/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassEtat
 *  utilitaire.Utilitaire
 */
package mg.cnaps.notification;

import bean.ClassEtat;
import java.sql.Date;
import utilitaire.Utilitaire;

public class NotificationLibelle
extends ClassEtat {
    private String id;
    private String objet;
    private String message;
    private String expediteur;
    private String idobjet;
    private String idpers;
    private String idservice;
    private String iddirection;
    private int priorite;
    private int classee;
    private String lien;
    private Date daty;
    private String iduser_recevant;
    private String destinataire;

    public NotificationLibelle() {
        super.setNomTable("NOTIFICATION_LIBELLE2");
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

    public String getIdobjet() {
        return this.idobjet;
    }

    public void setIdobjet(String idobjet) {
        this.idobjet = idobjet;
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
        if (this.getMode().compareTo("modif") == 0 && iduser_recevant != null && iduser_recevant.contains("/")) {
            String[] g = Utilitaire.split((String)iduser_recevant, (String)"/");
            String t = g[0];
            this.iduser_recevant = g[0];
            return;
        }
        this.iduser_recevant = iduser_recevant;
    }

    public String getExpediteur() {
        return this.expediteur;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public String getDestinataire() {
        return this.destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getIdpers() {
        return this.idpers;
    }

    public void setIdpers(String idpers) {
        this.idpers = idpers;
    }

    public String getIdservice() {
        return this.idservice;
    }

    public void setIdservice(String idservice) {
        this.idservice = idservice;
    }

    public String getIddirection() {
        return this.iddirection;
    }

    public void setIddirection(String iddirection) {
        this.iddirection = iddirection;
    }
}

