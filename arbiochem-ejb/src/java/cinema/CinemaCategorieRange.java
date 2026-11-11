package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;

public class CinemaCategorieRange extends ClassMAPTable {
    private String id;
    private String libelle;

    public CinemaCategorieRange() throws Exception {
        this.setNomTable("cinema_categorie_range");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CCR", "getseq_cinema_categorie_range");
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
        return new String[] { "libelle" };
    }

    @Override
    public String[] getValMotCles() {
        return new String[] { "libelle" };
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

    // ============================================================================
    // Raccourcis : supplément de la catégorie à une date / aujourd'hui
    // ============================================================================
    public double getPrixEnVigueur(Date dateRef, Connection c) throws Exception {
        return CinemaHistoriquePrixCategorieRange.getPrixEnVigueur(this.getId(), dateRef, c);
    }

    public double getPrixActuel(Connection c) throws Exception {
        return CinemaHistoriquePrixCategorieRange.getPrixActuel(this.getId(), c);
    }
}
