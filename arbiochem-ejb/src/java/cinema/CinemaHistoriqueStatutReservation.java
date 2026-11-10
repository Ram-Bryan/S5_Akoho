package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;

public class CinemaHistoriqueStatutReservation extends ClassMAPTable {
    private String id;
    private String idstatutreservation;
    private String idreservation;
    private Date datehist;

    public CinemaHistoriqueStatutReservation() throws Exception {
        this.setNomTable("cinema_historique_statut_reservation");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CHSR", "getseq_cinema_hist_statut_reservation");
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
        return new String[]{"id_statut_reservation", "id_reservation", "date_hist"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"id_statut_reservation", "id_reservation", "date_hist"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdstatutreservation() {
        return idstatutreservation;
    }

    public void setIdstatutreservation(String idstatutreservation) {
        this.idstatutreservation = idstatutreservation;
    }

    public String getIdreservation() {
        return idreservation;
    }

    public void setIdreservation(String idreservation) {
        this.idreservation = idreservation;
    }

    public Date getDatehist() {
        return datehist;
    }

    public void setDatehist(Date datehist) {
        this.datehist = datehist;
    }
}
