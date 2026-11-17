package cinema;

import bean.ClassFille;
import java.sql.Connection;

public class FactureClientDetails extends ClassFille {
    private String id;
    private String idfactureclient;
    private double qte;
    private double pu;
    private double remises;
    private double tva;
    private String iddetailreservationcinema; // Notre colonne de liaison

    public FactureClientDetails() throws Exception {
        this.setNomTable("FACTURECLIENTDETAILS");
        this.setNomClasseMere("cinema.FactureClient");
        this.setLiaisonMere("idfactureclient");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        // Remplacer "getseqFactureClientDetails" par le vrai nom de séquence Akoho
        this.preparePk("FCD", "SEQFACTURECLIENTDETAILS"); 
        this.setId(makePK(c));
    }

    @Override public String getTuppleID() { return id; }
    @Override public String getAttributIDName() { return "id"; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdfactureclient() { return idfactureclient; }
    public void setIdfactureclient(String idfactureclient) { this.idfactureclient = idfactureclient; }
    public double getQte() { return qte; }
    public void setQte(double qte) { this.qte = qte; }
    public double getPu() { return pu; }
    public void setPu(double pu) { this.pu = pu; }
    public double getRemises() { return remises; }
    public void setRemises(double remises) { this.remises = remises; }
    public double getTva() { return tva; }
    public void setTva(double tva) { this.tva = tva; }
    public String getIddetailreservationcinema() { return iddetailreservationcinema; }
    public void setIddetailreservationcinema(String iddetailreservationcinema) { this.iddetailreservationcinema = iddetailreservationcinema; }
}