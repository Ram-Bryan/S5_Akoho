package test.maison;

import bean.ClassFille;
import java.sql.Connection;

public class Piece extends ClassFille {
    private String id;
    private String idmere;
    private String nom;
    private double superficie;
    private int etage;
    private String typepiece;

    public Piece() throws Exception {
        this.setNomTable("PIECE");
        this.setNomClasseMere("test.maison.Maison");
        this.setLiaisonMere("idmere");
    }

    @Override public String getNomClasseMere() { return "test.maison.Maison"; }
    @Override public String getLiaisonMere()    { return "idmere"; }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("PCE", "getseq_piece");
        this.setId(makePK(c));
    }

    @Override public String getTuppleID()       { return id; }
    @Override public String getAttributIDName() { return "id"; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdmere() { return idmere; }
    public void setIdmere(String idmere) { this.idmere = idmere; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public double getSuperficie() { return superficie; }
    public void setSuperficie(double superficie) { this.superficie = superficie; }
    public int getEtage() { return etage; }
    public void setEtage(int etage) { this.etage = etage; }
    public String getTypepiece() { return typepiece; }
    public void setTypepiece(String typepiece) { this.typepiece = typepiece; }
}