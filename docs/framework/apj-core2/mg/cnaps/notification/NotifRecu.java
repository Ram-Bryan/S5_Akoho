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

public class NotifRecu
extends ClassEtat {
    private Date daty;
    private String objet;
    private String message;
    private String direction_initiale;
    private String service_initiale;
    private String destinataire;
    private String lien;
    private String id;
    private String idnotif;
    private String refuser;
    private String nom;
    private String prenom;
    private String iduser;
    private String idobjet;

    public NotifRecu(Date daty, String objet, String message, String direction_initiale, String service_initiale, String destinataire, String lien, String id, String idnotif, String refuser, String nom, String prenom, String iduser, String idobjet) {
        this.setDaty(daty);
        this.setObjet(objet);
        this.setMessage(message);
        this.setDirection_initiale(direction_initiale);
        this.setService_initiale(service_initiale);
        this.setDestinataire(destinataire);
        this.setLien(lien);
        this.setId(id);
        this.setIdnotif(idnotif);
        this.setRefuser(refuser);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setIduser(iduser);
        this.setIdobjet(idobjet);
    }

    public String getDirection_initiale() {
        return this.direction_initiale;
    }

    public void setDirection_initiale(String direction_initiale) {
        this.direction_initiale = direction_initiale;
    }

    public String getService_initiale() {
        return this.service_initiale;
    }

    public void setService_initiale(String service_initiale) {
        this.service_initiale = service_initiale;
    }

    public Date getDaty() {
        return this.daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
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

    public String getDestinataire() {
        return this.destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getLien() {
        return this.lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public NotifRecu() {
        this.setNomTable("NOTIF_COMPLET");
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdnotif() {
        return this.idnotif;
    }

    public void setIdnotif(String idnotif) {
        this.idnotif = idnotif;
    }

    public String getRefuser() {
        return this.refuser;
    }

    public void setRefuser(String refuser) {
        this.refuser = refuser;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getIduser() {
        return this.iduser;
    }

    public void setIduser(String iduser) {
        if (this.getMode().compareTo("modif") == 0 && iduser != null && iduser.contains("/")) {
            String[] g = Utilitaire.split((String)iduser, (String)"/");
            String t = g[0];
            this.iduser = g[0];
            return;
        }
        this.iduser = iduser;
    }

    public String getIdobjet() {
        return this.idobjet;
    }

    public void setIdobjet(String idobjet) {
        this.idobjet = idobjet;
    }
}

