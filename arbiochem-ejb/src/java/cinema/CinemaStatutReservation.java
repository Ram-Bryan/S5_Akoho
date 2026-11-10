package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;

public class CinemaStatutReservation extends ClassMAPTable {
    private String id;
    private String libelle;

    public CinemaStatutReservation() throws Exception {
        this.setNomTable("cinema_statut_reservation");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CSR", "getseq_cinema_statut_reservation");
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
        return new String[]{"libelle"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"libelle"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
