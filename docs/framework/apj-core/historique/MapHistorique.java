/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.ClassMAPTable;
import historique.MapUtilisateur;
import historique.UtilisateurUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import utilitaire.Utilitaire;

public class MapHistorique
extends ClassMAPTable
implements Serializable {
    private String idHistorique;
    private Date dateHistorique;
    private String heure;
    private String objet;
    private String action;
    private String idUtilisateur;
    private String refObjet;

    @Override
    public boolean isSynchro() {
        return false;
    }

    @Override
    public boolean getEstIndexable() {
        return false;
    }

    public MapHistorique() {
        super.setNomTable("historique");
    }

    public MapHistorique(String refe, Date date, String lera, String refOrd, String action, String user, String refObje) {
        super.setNomTable("historique");
        this.setIdHistorique(refe);
        this.setDateHistorique(date);
        this.setHeure(lera);
        this.setObjet(refOrd);
        this.setAction(action);
        this.setIdUtilisateur(user);
        this.setRefObjet(refObje);
    }

    public MapHistorique(String obje, String action, String user, String refObje) {
        this.setNomTable("historique");
        this.setNomProcedureSequence("getSeqHistorique");
        this.setLonguerClePrimaire(10);
        this.setDateHistorique(Utilitaire.dateDuJourSql());
        this.setHeure(Utilitaire.heureCourante());
        this.setAction(action);
        this.setIdUtilisateur(user);
        this.setRefObjet(refObje);
        this.setObjet(this.objet);
    }

    public MapHistorique(String obje, String action, String user, String refObje, Connection c) throws Exception {
        this.setNomTable("historique");
        this.setNomProcedureSequence("getSeqHistorique");
        this.setLonguerClePrimaire(10);
        this.setIdHistorique(this.makePK(c));
        this.setDateHistorique(Utilitaire.dateDuJourSql());
        this.setHeure(Utilitaire.heureCourante());
        this.setAction(action);
        this.setIdUtilisateur(user);
        this.setRefObjet(refObje);
        this.setObjet(this.objet);
    }

    @Override
    public String getTuppleID() {
        return String.valueOf(this.getIdHistorique());
    }

    @Override
    public String getAttributIDName() {
        return "idHistorique";
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        super.setNomTable("HISTORIQUE");
        this.preparePk("EX", "getSeqHistorique");
        this.setLonguerClePrimaire(8);
        this.setIdHistorique(this.makePK(c));
    }

    public MapUtilisateur getUtilisateurs() {
        return (MapUtilisateur)new UtilisateurUtil().rechercher(1, this.getIdUtilisateur())[0];
    }

    public void setDateHistorique(Date dateHistorique) {
        this.dateHistorique = String.valueOf(dateHistorique).compareTo("") == 0 || dateHistorique == null ? Utilitaire.dateDuJourSql() : dateHistorique;
    }

    public String getObjet() {
        return this.objet;
    }

    public MapUtilisateur getUtilisateur() {
        return (MapUtilisateur)new UtilisateurUtil().rechercher(1, this.getIdUtilisateur())[0];
    }

    public String getIdHistorique() {
        return this.idHistorique;
    }

    public void setIdHistorique(String idHistorique) {
        this.idHistorique = idHistorique;
    }

    public String getHeure() {
        return this.heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getRefObjet() {
        return this.refObjet;
    }

    public void setRefObjet(String refObjet) {
        this.refObjet = refObjet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public Date getDateHistorique() {
        return this.dateHistorique;
    }
}

