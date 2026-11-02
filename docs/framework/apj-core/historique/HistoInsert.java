/*
 * Decompiled with CFR 0.152.
 */
package historique;

import historique.MapHistorique;
import java.sql.Connection;
import java.sql.Statement;
import utilitaire.Utilitaire;

public class HistoInsert
extends MapHistorique {
    String remarque;
    String fiche;

    public HistoInsert() {
        this.setNomTable("HISTOINSERT");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        super.setNomTable("HISTORIQUE");
        this.preparePk("EX", "getSeqHistorique");
        this.setIdHistorique(this.makePK(c));
    }

    public static int ajoutRemarque(String idObjet, String ajoutRemarque, Connection c) throws Exception {
        String req = "update histoinsert set remarque=remarque||' " + ajoutRemarque + "' where refobjet='" + idObjet + "'";
        Statement st = c.createStatement();
        return st.executeUpdate(req);
    }

    public static void ajoutRemarqueBatch(String idObjet, String ajoutRemarque, Statement st) throws Exception {
        String req = "update histoinsert set remarque=remarque||' " + ajoutRemarque + "' where refobjet='" + idObjet + "'";
        st.addBatch(req);
    }

    @Override
    public String getTuppleID() {
        return String.valueOf(this.getIdHistorique());
    }

    @Override
    public String getAttributIDName() {
        return "idHistorique";
    }

    public HistoInsert(String objet, String action, String refUser, String refObje) throws Exception {
        this.setNomTable("HISTOINSERT");
        this.setNomProcedureSequence("GETSEQ_HistoInsert");
        this.setLonguerClePrimaire(10);
        this.setIdHistorique(this.makePK());
        this.setDateHistorique(Utilitaire.dateDuJourSql());
        this.setHeure(Utilitaire.heureCourante());
        this.setAction(action);
        this.setIdUtilisateur(refUser);
        this.setRefObjet(refObje);
        this.setObjet(objet);
    }

    public HistoInsert(String objet, String action, String refUser, String refObje, Connection c) throws Exception {
        this.setNomTable("HISTOINSERT");
        this.setNomProcedureSequence("GETSEQ_HistoInsert");
        this.setLonguerClePrimaire(10);
        this.setIdHistorique(this.makePK(c));
        this.setDateHistorique(Utilitaire.dateDuJourSql());
        this.setHeure(Utilitaire.heureCourante());
        this.setAction(action);
        this.setIdUtilisateur(refUser);
        this.setRefObjet(refObje);
        this.setObjet(objet);
    }

    public String getRemarque() {
        if (this.remarque != null) {
            this.remarque = this.remarque.replace("null", "");
        }
        return this.remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    @Override
    public boolean isSynchro() {
        return true;
    }

    public String getFiche() {
        return this.fiche;
    }

    public void setFiche(String fiche) {
        this.fiche = fiche;
    }
}

