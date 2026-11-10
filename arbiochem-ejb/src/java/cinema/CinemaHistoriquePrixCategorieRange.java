package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;

public class CinemaHistoriquePrixCategorieRange extends ClassMAPTable {
    private String id;
    private String idcategorierange;
    private double prixsupplement;
    private Date datedebut;

    public CinemaHistoriquePrixCategorieRange() throws Exception {
        this.setNomTable("cinema_historique_prix_categorie_range");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CHPR", "getseq_cinema_hist_prix_categorie_range");
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
        return new String[]{"id_categorie_range", "date_debut"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"id_categorie_range", "date_debut"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdcategorierange() {
        return idcategorierange;
    }

    public void setIdcategorierange(String idcategorierange) {
        this.idcategorierange = idcategorierange;
    }

    public double getPrixsupplement() {
        return prixsupplement;
    }

    public void setPrixsupplement(double prixsupplement) {
        this.prixsupplement = prixsupplement;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }
}
