/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 *  utilitaire.Utilitaire
 */
package mg.cnaps.notification;

import bean.ClassMAPTable;
import java.sql.Connection;
import utilitaire.Utilitaire;

public class NotificationDetail
extends ClassMAPTable {
    private String id;
    private String idnotif;
    private String refuser;
    private String nom;
    private String prenom;
    private String iduser;
    private String idobjet;

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public void construirePK(Connection c) throws Exception {
        this.preparePk("NFD", "getSeqNOTIFDETAIL");
        this.setId(this.makePK(c));
    }

    public NotificationDetail() {
        this.setNomTable("notif_detail");
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

