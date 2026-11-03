package test.livre;

import bean.ClassEtat;
import java.sql.Connection;

public class Livre extends ClassEtat {
    private String id;
    private String titre;
    private String auteur;
    private String isbn;
    private int anneepublication;
    private String editeur;
    private double prix;

    public Livre() throws Exception { this.setNomTable("LIVRE"); }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("LIV", "getseq_livre");
        this.setId(makePK(c));
    }

    @Override public String getTuppleID()          { return id; }
    @Override public String getAttributIDName()    { return "id"; }
    @Override public String[] getMotCles()         { return new String[]{"titre","auteur","isbn"}; }
    @Override public String[] getValMotCles()      { return new String[]{"titre","auteur","isbn"}; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public int getAnneepublication() { return anneepublication; }
    public void setAnneepublication(int anneepublication) { this.anneepublication = anneepublication; }
    public String getEditeur() { return editeur; }
    public void setEditeur(String editeur) { this.editeur = editeur; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
}