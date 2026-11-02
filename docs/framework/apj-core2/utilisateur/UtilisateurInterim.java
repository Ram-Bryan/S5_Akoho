/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassEtat
 *  utilitaire.Utilitaire
 */
package utilisateur;

import bean.ClassEtat;
import java.sql.Connection;
import java.sql.Date;
import utilitaire.Utilitaire;

public class UtilisateurInterim
extends ClassEtat {
    private String id;
    private String iduser_interim;
    private String iduser_a_remplacer;
    private Date daty;
    private Date date_debut;
    private Date date_fin;

    public void construirePK(Connection c) throws Exception {
        this.preparePk("UI", "getSeqUtilisateurInterim");
        this.setId(this.makePK(c));
    }

    public UtilisateurInterim() {
        super.setNomTable("UTILISATEUR_INTERIM");
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIduser_interim() {
        return this.iduser_interim;
    }

    public void setIduser_interim(String iduser_interim) throws Exception {
        if (this.getMode().compareTo("modif") != 0) {
            this.iduser_interim = iduser_interim;
            return;
        }
        if (iduser_interim == null || iduser_interim.compareToIgnoreCase("") == 0) {
            throw new Exception("Le Champs interim est obligatoire");
        }
        if (this.getMode().compareTo("modif") == 0 && iduser_interim != null && iduser_interim.contains("/")) {
            String[] g = Utilitaire.split((String)iduser_interim, (String)"/");
            String t = g[0];
            this.iduser_interim = g[0];
            return;
        }
        this.iduser_interim = iduser_interim;
    }

    public String getIduser_a_remplacer() {
        return this.iduser_a_remplacer;
    }

    public void setIduser_a_remplacer(String iduser_a_remplacer) throws Exception {
        if (this.getMode().compareTo("modif") != 0) {
            this.iduser_a_remplacer = iduser_a_remplacer;
            return;
        }
        if (iduser_a_remplacer == null || iduser_a_remplacer.compareToIgnoreCase("") == 0) {
            throw new Exception("Le Champ a remplacer est obligatoire");
        }
        if (this.getMode().compareTo("modif") == 0 && iduser_a_remplacer != null && iduser_a_remplacer.contains("/")) {
            String[] g = Utilitaire.split((String)iduser_a_remplacer, (String)"/");
            String t = g[0];
            this.iduser_a_remplacer = g[0];
            return;
        }
        this.iduser_a_remplacer = iduser_a_remplacer;
    }

    public Date getDaty() {
        return this.daty;
    }

    public void setDaty(Date daty) throws Exception {
        if (this.getMode().compareTo("modif") != 0) {
            this.daty = daty;
            return;
        }
        if (daty == null) {
            throw new Exception("Le Champs date est obligatoire");
        }
        this.daty = daty;
    }

    public Date getDate_debut() {
        return this.date_debut;
    }

    public void setDate_debut(Date date_debut) throws Exception {
        if (this.getMode().compareTo("modif") != 0) {
            this.date_debut = date_debut;
            return;
        }
        if (date_debut == null) {
            throw new Exception("Le Champs date debut est obligatoire");
        }
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return this.date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }
}

