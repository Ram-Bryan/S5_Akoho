package cinema;

import bean.ClassMere;
import java.sql.Connection;
import java.sql.Date;

public class FactureClient extends ClassMere {
    private String id;
    private String idclient;
    private Date daty;
    private String designation;
    private int etat;
    private double tva;
    private String idreservationcinema; // Notre colonne de liaison

    public FactureClient() throws Exception {
        this.setNomTable("FACTURECLIENT");
        this.setNomClasseFille("cinema.FactureClientDetails");
        this.setLiaisonFille("idfactureclient");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        // Remplacer "getseqFactureClient" par le vrai nom de séquence Akoho si différent
        this.preparePk("FC", "GET_SEQ_FACTURECLIENT"); 
        this.setId(makePK(c));
    }

    @Override public String getTuppleID() { return id; }
    @Override public String getAttributIDName() { return "id"; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdclient() { return idclient; }
    public void setIdclient(String idclient) { this.idclient = idclient; }
    public Date getDaty() { return daty; }
    public void setDaty(Date daty) { this.daty = daty; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public int getEtat() { return etat; }
    public void setEtat(int etat) { this.etat = etat; }
    public double getTva() { return tva; }
    public void setTva(double tva) { this.tva = tva; }
    public String getIdreservationcinema() { return idreservationcinema; }
    public void setIdreservationcinema(String idreservationcinema) { this.idreservationcinema = idreservationcinema; }
}