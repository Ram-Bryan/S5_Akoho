package cinema;

import bean.ClassFille;
import java.sql.Connection;

public class CinemaDetailReservation extends ClassFille {
    private String id;
    private String idreservation;
    private String idplace;
    private int etat;
    private String iduser;

    public CinemaDetailReservation() throws Exception {
        this.setNomTable("cinema_detail_reservation");
        this.setNomClasseMere("cinema.CinemaReservation");
        this.setLiaisonMere("id_reservation");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CDR", "getseq_cinema_detail_reservation");
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
        return new String[]{"id_reservation", "id_place"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"id_reservation", "id_place"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdreservation() {
        return idreservation;
    }

    public void setIdreservation(String idreservation) {
        this.idreservation = idreservation;
    }

    public String getIdplace() {
        return idplace;
    }

    public void setIdplace(String idplace) {
        this.idplace = idplace;
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
