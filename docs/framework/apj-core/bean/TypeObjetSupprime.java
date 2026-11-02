/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;
import java.sql.Date;
import utilitaire.Utilitaire;

public class TypeObjetSupprime
extends ClassMAPTable {
    private static final long serialVersionUID = -253144357449988256L;
    public String ID;
    public String HEURE;
    public String RMQ;
    public String IDOBJET;
    public Date DATY;
    public String IDUTILISATEUR;

    public TypeObjetSupprime(String nomtable, String nomProcedure, String suff, String idobjet, String iduser, Date daty, String heure, String rmq) {
        this.setNomTable(nomtable);
        this.setNomProcedureSequence(nomProcedure);
        this.setIndicePk(suff);
        this.ID = this.makePK();
        this.setIDOBJET(idobjet);
        this.setIDUTILISATEUR(iduser);
        this.setDATY(daty);
        this.setHEURE(heure);
        this.setRMQ(rmq);
    }

    public TypeObjetSupprime(String nomtable, String id, String idobjet, String iduser, Date daty, String heure, String rmq) {
        this.setID(id);
        this.setNomTable(nomtable);
        this.setIDOBJET(idobjet);
        this.setIDUTILISATEUR(iduser);
        this.setDATY(daty);
        this.setHEURE(heure);
        this.setRMQ(rmq);
    }

    public TypeObjetSupprime(String id, String idobjet, String iduser, Date daty, String rmq, String utilBDD) {
        this.setID(id);
        this.setIDOBJET(idobjet);
        this.setIDUTILISATEUR(iduser);
        this.setDATY(daty);
        this.setRMQ(rmq);
    }

    @Override
    public String getAttributIDName() {
        return "ID";
    }

    @Override
    public String getTuppleID() {
        return this.ID;
    }

    public void setDATY(Date DATY) {
        this.DATY = String.valueOf(DATY).compareTo("") == 0 || DATY == null ? Utilitaire.dateDuJourSql() : DATY;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setIDOBJET(String IDOBJET) {
        this.IDOBJET = IDOBJET;
    }

    public void setIDUTILISATEUR(String IDUTILISATEUR) {
        this.IDUTILISATEUR = IDUTILISATEUR;
    }

    public void setRMQ(String RMQ) {
        this.RMQ = RMQ == null || String.valueOf(RMQ).compareTo("") == 0 ? "-" : RMQ;
    }

    public String getRMQ() {
        return this.RMQ;
    }

    public String getIDUTILISATEUR() {
        return this.IDUTILISATEUR;
    }

    public String getIDOBJET() {
        return this.IDOBJET;
    }

    public String getID() {
        return this.ID;
    }

    public String getHEURE() {
        return this.HEURE;
    }

    public Date getDATY() {
        return this.DATY;
    }

    public void setHEURE(String HEURE) {
        this.HEURE = String.valueOf(HEURE).compareTo("") == 0 || this.DATY == null ? Utilitaire.heureCourante() : HEURE;
    }
}

