package cinema;

import bean.ClassMere;
import java.sql.Connection;
import java.sql.Date;

public class CinemaProgramme extends ClassMere {
    private String id;
    private String idsalle;
    private Date datedebut;
    private String heuredebut;
    private int etat;
    private String iduser;

    public CinemaProgramme() throws Exception {
        this.setNomTable("cinema_programme");
        this.setNomClasseFille("cinema.CinemaDetailProgramme");
        this.setLiaisonFille("idprogramme");
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
        return new String[] { "idsalle", "datedebut", "heuredebut" };
    }

    @Override
    public String[] getValMotCles() {
        return new String[] { "idsalle", "datedebut", "heuredebut" };
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    // ============================================================================
    // Raccourcis : prix du programme à une date / aujourd'hui
    // ============================================================================
    public double getPrixEnVigueur(Date dateRef, Connection c) throws Exception {
        return CinemaHistoriquePrixProgramme.getPrixEnVigueur(this.getId(), dateRef, c);
    }

    public double getPrixActuel(Connection c) throws Exception {
        return CinemaHistoriquePrixProgramme.getPrixActuel(this.getId(), c);
    }
}