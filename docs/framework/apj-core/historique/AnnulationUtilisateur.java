/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.ClassMAPTable;
import java.sql.Date;
import utilitaire.Utilitaire;

public class AnnulationUtilisateur
extends ClassMAPTable {
    public String idAnnulationUser;
    public int refUser;
    public Date daty;

    public AnnulationUtilisateur(String id, String ref, Date dat) {
        this.setIdAnnulationUser(id);
        this.setRefUser(ref);
        this.setDaty(dat);
        this.setNomTable("AnnulationUtilisateur");
    }

    public AnnulationUtilisateur(String ref, Date dat) {
        this.setIndicePk("ANU");
        this.setNomProcedureSequence("GET_SEQ_ANNULATIONUTILISATEUR");
        this.setIdAnnulationUser(this.makePK());
        this.setRefUser(ref);
        this.setDaty(dat);
        this.setNomTable("AnnulationUtilisateur");
    }

    public AnnulationUtilisateur(String ref, String dat) {
        this.setIndicePk("ANU");
        this.setNomProcedureSequence("GET_SEQ_ANNULATIONUTILISATEUR");
        this.setIdAnnulationUser(this.makePK());
        this.setRefUser(ref);
        if (ref.compareTo("") == 0 || ref == null) {
            // empty if block
        }
        if (String.valueOf(dat).compareTo("") == 0 || dat == null) {
            this.setDaty(Utilitaire.dateDuJourSql());
        } else {
            this.setDaty(Utilitaire.string_date("dd/MM/yyyy", dat));
        }
        this.setNomTable("AnnulationUtilisateur");
    }

    public AnnulationUtilisateur(String ref) {
        this.setIndicePk("ANU");
        this.setNomProcedureSequence("GET_SEQ_ANNULATIONUTILISATEUR");
        this.setIdAnnulationUser(this.makePK());
        this.setRefUser(ref);
        this.setDaty(Utilitaire.dateDuJourSql());
        this.setNomTable("AnnulationUtilisateur");
    }

    @Override
    public String getAttributIDName() {
        return "idAnnulationUser";
    }

    @Override
    public String getTuppleID() {
        return this.getIdAnnulationUser();
    }

    public void setIdAnnulationUser(String idAnnulationUser) {
        this.idAnnulationUser = idAnnulationUser;
    }

    public String getIdAnnulationUser() {
        return this.idAnnulationUser;
    }

    public void setRefUser(String refUser) {
        this.setRefUser(Integer.parseInt(refUser));
    }

    public int getRefUser() {
        return this.refUser;
    }

    public void setRefUser(int refUser) {
        this.refUser = refUser;
    }

    public void setDaty(Date daty) {
        this.daty = String.valueOf(daty).compareTo("") == 0 || daty == null ? Utilitaire.dateDuJourSql() : daty;
    }

    public Date getDaty() {
        return this.daty;
    }
}

