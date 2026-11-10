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
        this.setLiaisonMere("idreservation");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CDR", "getseq_cinema_det_reserv");
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
        return new String[]{"idreservation", "idplace"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"idreservation", "idplace"};
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
