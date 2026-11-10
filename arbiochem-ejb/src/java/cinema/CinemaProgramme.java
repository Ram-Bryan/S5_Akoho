package cinema;

import bean.ClassEtat;
import java.sql.Connection;
import java.sql.Date;

public class CinemaProgramme extends ClassEtat {
    private String id;
    private String idfilm;
    private String idsalle;
    private Date datedebut;
    private String heuredebut;
    private Date datefin;
    private String heurefin;
    private double prixbase;
    private int etat;
    private String iduser;

    public CinemaProgramme() throws Exception {
        this.setNomTable("cinema_programme");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CPRG", "getseq_cinema_programme");
        this.setId(makePK(c));
    }

    @Override
    public String getTuppleID() {
        return id;
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public String[] getMotCles() {
        return new String[]{"idfilm", "idsalle", "datedebut", "heuredebut"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"idfilm", "idsalle", "datedebut", "heuredebut"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(String idfilm) {
        this.idfilm = idfilm;
    }

    public String getIdsalle() {
        return idsalle;
    }

    public void setIdsalle(String idsalle) {
        this.idsalle = idsalle;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public String getHeuredebut() {
        return heuredebut;
    }

    public void setHeuredebut(String heuredebut) {
        this.heuredebut = heuredebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public String getHeurefin() {
        return heurefin;
    }

    public void setHeurefin(String heurefin) {
        this.heurefin = heurefin;
    }

    public double getPrixbase() {
        return prixbase;
    }

    public void setPrixbase(double prixbase) {
        this.prixbase = prixbase;
    }

    @Override
    public int getEtat() {
        return etat;
    }

    @Override
    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }
}
