package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;

public class CinemaHistoriquePrixProgramme extends ClassMAPTable {
    private String id;
    private String idprogramme;
    private double prix;
    private Date datedebut;

    public CinemaHistoriquePrixProgramme() throws Exception {
        this.setNomTable("cinema_historique_prix_programme");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CHPP", "getseq_cinema_hist_prix_programme");
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
        return new String[]{"idprogramme", "datedebut"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"idprogramme", "datedebut"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdprogramme() {
        return idprogramme;
    }

    public void setIdprogramme(String idprogramme) {
        this.idprogramme = idprogramme;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }
}