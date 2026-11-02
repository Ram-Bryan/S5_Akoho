/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.ClassMAPTable;
import java.sql.Date;
import utilitaire.Utilitaire;

public class HistoriqueEtat
extends ClassMAPTable {
    public String id;
    public String idMere;
    public String idUser;
    public String idEtat;
    public Date dateModification;

    public HistoriqueEtat(String i, String mere, String user, String idEta, Date dateModif) {
        super.setNomTable("historiqueetat");
        this.setId(i);
        this.setIdMere(mere);
        this.setIdUser(user);
        this.setIdEtat(idEta);
        this.setDateModification(dateModif);
    }

    public HistoriqueEtat(String mere, String user, String idEta, Date dateModif) {
        super.setNomTable("historiqueetat");
        this.setIndicePk("HE");
        this.setNomProcedureSequence("getSeqHistoriqueEtat");
        this.setId(this.makePK());
        this.setIdMere(mere);
        this.setIdUser(user);
        this.setIdEtat(idEta);
        this.setDateModification(dateModif);
    }

    public HistoriqueEtat(String mere, String user, String idEta, String dateModif) {
        super.setNomTable("historiqueetat");
        this.setIndicePk("HE");
        this.setNomProcedureSequence("getSeqHistoriqueEtat");
        this.setId(this.makePK());
        if (mere.compareTo("") == 0 || mere == null) {
            this.setIdMere("-");
        } else {
            this.setIdMere(mere);
        }
        if (user.compareTo("") == 0 || user == null) {
            this.setIdUser("-");
        } else {
            this.setIdUser(user);
        }
        if (idEta.compareTo("") == 0 || idEta == null) {
            this.setIdEtat("-");
        } else {
            this.setIdEtat(idEta);
        }
        if (String.valueOf(dateModif).compareTo("") == 0 || dateModif == null) {
            this.setDateModification(Utilitaire.dateDuJourSql());
        } else {
            this.setDateModification(Utilitaire.string_date("dd/MM/yyyy", dateModif));
        }
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public String getTuppleID() {
        return String.valueOf(this.id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setIdMere(String idMere) {
        this.idMere = idMere;
    }

    public String getIdMere() {
        return this.idMere;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdUser() {
        return this.idUser;
    }

    public void setIdEtat(String idEtat) {
        this.idEtat = idEtat;
    }

    public String getIdEtat() {
        return this.idEtat;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public Date getDateModification() {
        return this.dateModification;
    }
}

