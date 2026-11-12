package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;

import bean.CGenUtil;
import bean.ClassMAPTable;
import utilitaire.Utilitaire;
public class CinemaCategorieRange extends ClassMAPTable {
    private String id;
    private String libelle;
    private double prix;

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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    // ============================================================================
// createObject : insère la catégorie + ligne de prix initial dans l'historique
// ============================================================================
@Override
public ClassMAPTable createObject(String refUser, Connection c) throws Exception {
    ClassMAPTable result = super.createObject(refUser, c);

    if (this.getPrix() >= 0) {   // 0 est valide (Standard = 0)
        CinemaHistoriquePrixCategorieRange hist = new CinemaHistoriquePrixCategorieRange();
        hist.setIdcategorierange(this.getId());
        hist.setPrixsupplement(this.getPrix());
        hist.setDatedebut(Utilitaire.dateDuJourSql());
        hist.construirePK(c);
        hist.insertToTableWithHisto(refUser, c);
    }

    return result;
}

// ============================================================================
// updateObject : si le prix change, ajoute une ligne d'historique
// ============================================================================
@Override
public int updateObject(String refUser, Connection c) throws Exception {
    CinemaCategorieRange avant = new CinemaCategorieRange();
    avant.setId(this.getId());
    CinemaCategorieRange[] before = (CinemaCategorieRange[]) CGenUtil.rechercher(avant, null, null, c, "");
    double prixAvant = (before != null && before.length > 0) ? before[0].getPrix() : 0.0;

    int result = super.updateObject(refUser, c);

    if (Math.abs(this.getPrix() - prixAvant) > 0.001) {
        CinemaHistoriquePrixCategorieRange hist = new CinemaHistoriquePrixCategorieRange();
        hist.setIdcategorierange(this.getId());
        hist.setPrixsupplement(this.getPrix());
        hist.setDatedebut(Utilitaire.dateDuJourSql());
        hist.construirePK(c);
        hist.insertToTableWithHisto(refUser, c);
    }

    return result;
}
}
