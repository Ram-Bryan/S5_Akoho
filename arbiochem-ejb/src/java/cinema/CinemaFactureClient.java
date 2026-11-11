package cinema;

import bean.ClassEtat;
import java.sql.Connection;
import java.sql.Date;

public class CinemaFactureClient extends ClassEtat {
    private String id;
    private String numero;
    private String idreservation;
    private Date datefacture;
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
        return new String[]{"numero", "idreservation"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"numero", "idreservation"};
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

    public Date getDatefacture() {
        return datefacture;
    }

    public void setDatefacture(Date datefacture) {
        this.datefacture = datefacture;
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
