package cinema;

import bean.ClassEtat;
import java.sql.Connection;

public class CinemaFactureClient extends ClassEtat {
    private String id;
    private String numero;
    private String idreservation;
    private java.sql.Date datefacture;
    private double montanttotal;
    private int etat;
    private String iduser;

    public CinemaFactureClient() throws Exception {
        this.setNomTable("cinema_facture_client");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CFAC", "getseq_cinema_facture_client");
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
        return new String[]{"numero", "id_reservation"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"numero", "id_reservation"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIdreservation() {
        return idreservation;
    }

    public void setIdreservation(String idreservation) {
        this.idreservation = idreservation;
    }

    public java.sql.Date getDatefacture() {
        return datefacture;
    }

    public void setDatefacture(java.sql.Date datefacture) {
        this.datefacture = datefacture;
    }

    public double getMontanttotal() {
        return montanttotal;
    }

    public void setMontanttotal(double montanttotal) {
        this.montanttotal = montanttotal;
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
