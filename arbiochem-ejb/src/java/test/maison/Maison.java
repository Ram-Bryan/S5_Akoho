package test.maison;

import bean.ClassMere;
import java.sql.Connection;

public class Maison extends ClassMere {
    private String id;
    private String nom;
    private String adresse;
    private String ville;
    private String codepostal;
    private double surface;
    private int anneconstruction;

    public Maison() throws Exception {
        this.setNomTable("MAISON");
        this.setNomClasseFille("test.maison.Piece");
        this.setLiaisonFille("idmere");
    }

    @Override public String getNomClasseFille() { return "test.maison.Piece"; }
    @Override public String getLiaisonFille()    { return "idmere"; }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("MSN", "getseq_maison");
        this.setId(makePK(c));
    }

    @Override public String getTuppleID()          { return id; }
    @Override public String getAttributIDName()    { return "id"; }
    @Override public String[] getMotCles()         { return new String[]{"nom","ville"}; }
    @Override public String[] getValMotCles()      { return new String[]{"nom","ville"}; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public String getCodepostal() { return codepostal; }
    public void setCodepostal(String codepostal) { this.codepostal = codepostal; }
    public double getSurface() { return surface; }
    public void setSurface(double surface) { this.surface = surface; }
    public int getAnneconstruction() { return anneconstruction; }
    public void setAnneconstruction(int anneconstruction) { this.anneconstruction = anneconstruction; }
}