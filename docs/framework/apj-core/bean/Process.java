/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.CGenUtil;
import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class Process
extends ClassMAPTable {
    String acteur;
    private String idHistorique;
    private Date dateHistorique;
    private String heure;
    private String objet;
    private String action;
    double dureeTravux = 0.0;
    double dureeBlocage = 0.0;
    double ecart;
    private String idUtilisateur;
    private String refObjet;
    String remarque;

    public double getEcart() {
        return this.ecart;
    }

    public void setEcart(double ecart) {
        this.ecart = ecart;
    }

    public double getDureeTravux() {
        return this.dureeTravux;
    }

    public void setDureeTravux(double dureeTravux) {
        this.dureeTravux = dureeTravux;
    }

    public double getDureeBlocage() {
        return this.dureeBlocage;
    }

    public void setDureeBlocage(double dureeBlocage) {
        this.dureeBlocage = dureeBlocage;
    }

    public String getIdHistorique() {
        return this.idHistorique;
    }

    public void setIdHistorique(String idHistorique) {
        this.idHistorique = idHistorique;
    }

    public Date getDateHistorique() {
        return this.dateHistorique;
    }

    public void setDateHistorique(Date dateHistorique) {
        this.dateHistorique = dateHistorique;
    }

    public String getHeure() {
        return this.heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getObjet() {
        return this.objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
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

    public String getRemarque() {
        return this.remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getActeur() {
        return this.acteur;
    }

    public void setActeur(String acteur) {
        this.acteur = acteur;
    }

    public Process() {
        this.setNomTable("Process");
        this.setNomProcedureSequence("getSeqProcess");
        this.setINDICE_PK("PRO");
    }

    @Override
    public String getTuppleID() {
        return this.idHistorique;
    }

    @Override
    public String getAttributIDName() {
        return "idHistorique";
    }

    @Override
    public ClassMAPTable createObject(String u, Connection c) throws Exception {
        boolean isOuvert = false;
        ClassMAPTable cmt = null;
        try {
            ClassMAPTable crt;
            ClassMAPTable objet;
            if (c == null) {
                c = new UtilDB().GetConn();
                isOuvert = true;
            }
            if ((objet = (crt = (ClassMAPTable)Class.forName(this.getObjet()).newInstance()).getById(this.getRefObjet(), null, c)) != null) {
                CGenUtil.getMethodDynamiqueAvecConn(objet, u, this.getAction() + "Object", c);
            }
            if (this.getAction().equalsIgnoreCase("bloquer") || this.getAction().equalsIgnoreCase("terminer")) {
                Process p = new Process();
                p.setRefObjet(this.getRefObjet());
                Process[] proc = (Process[])CGenUtil.rechercher(p, null, null, c, " order by DATEHISTORIQUE desc, heure desc, IDHISTORIQUE desc");
                if (proc != null && proc.length > 0) {
                    double ecart = Utilitaire.differenceEnMinutes(proc[0].getDateHistorique(), proc[0].getHeure(), this.getDateHistorique(), this.getHeure()) * 60.0;
                    this.setEcart(ecart);
                }
            }
            cmt = super.createObject(u, c);
        }
        catch (Exception e) {
            e.printStackTrace();
            c.rollback();
            throw new Exception("Erreur lors de la creation du process");
        }
        finally {
            if (isOuvert) {
                c.close();
            }
        }
        return cmt;
    }

    public double getHeureTravaux() throws Exception {
        double heure = 0.0;
        Process p = new Process();
        p.setRefObjet(this.getRefObjet());
        Process[] proc = (Process[])CGenUtil.rechercher((ClassMAPTable)p, null, null, "");
        double ecart = 0.0;
        for (int i = 0; i < proc.length; ++i) {
            ecart += proc[i].getEcart();
        }
        return ecart /= 3600.0;
    }
}

