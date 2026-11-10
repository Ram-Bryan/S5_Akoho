package cinema;

import bean.ClassMere;
import java.sql.Connection;

public class CinemaReservation extends ClassMere {
    private String id;
    private String idclient;
    private String idprogramme;
    private java.sql.Date datereservation;
    private String idstatutreservation;
    private int etat;
    private String iduser;

    public CinemaReservation() throws Exception {
        this.setNomTable("cinema_reservation");
        this.setNomClasseFille("cinema.CinemaDetailReservation");
        this.setLiaisonFille("id_reservation");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CRES", "getseq_cinema_reservation");
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
        return new String[]{"id_client", "id_programme", "date_reservation"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"id_client", "id_programme", "date_reservation"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdclient() {
        return idclient;
    }

    public void setIdclient(String idclient) {
        this.idclient = idclient;
    }

    public String getIdprogramme() {
        return idprogramme;
    }

    public void setIdprogramme(String idprogramme) {
        this.idprogramme = idprogramme;
    }

    public java.sql.Date getDatereservation() {
        return datereservation;
    }

    public void setDatereservation(java.sql.Date datereservation) {
        this.datereservation = datereservation;
    }

    public String getIdstatutreservation() {
        return idstatutreservation;
    }

    public void setIdstatutreservation(String idstatutreservation) {
        this.idstatutreservation = idstatutreservation;
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
