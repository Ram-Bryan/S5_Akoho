package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;

import bean.CGenUtil;

public class CinemaHistoriquePrixCategorieRange extends ClassMAPTable {
    private String id;
    private String idcategorierange;
    private double prixsupplement;
    private Date datedebut;

    public CinemaHistoriquePrixCategorieRange() throws Exception {
        this.setNomTable("cinema_hist_prix_cat_range");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CHPR", "getseq_cin_hist_prix_cat_rng");
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
        return new String[] { "idcategorierange", "datedebut" };
    }

    @Override
    public String[] getValMotCles() {
        return new String[] { "idcategorierange", "datedebut" };
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

    // ============================================================================
    // Historique append-only : tout UPDATE est transformé en INSERT
    // (préserve l'intégrité historique — voir règle métier)
    // ============================================================================
    @Override
    public int updateToTableWithHisto(String refUser, Connection c) throws Exception {
        // On ne modifie JAMAIS une ligne historique.
        // On insère une nouvelle ligne avec les valeurs modifiées.
        // L'ancienne ligne reste intacte → traçabilité préservée.
        this.setId(null);
        this.construirePK(c);
        this.insertToTableWithHisto(refUser, c);
        return 1;
    }

    // ============================================================================
    // Utilitaires : supplément en vigueur à une date, supplément actuel
    // ============================================================================
    public static CinemaHistoriquePrixCategorieRange getLigneEnVigueur(
            String idcategorierange, Date dateRef, Connection c) throws Exception {
        if (idcategorierange == null)
            throw new Exception("idcategorierange requis");
        if (dateRef == null)
            throw new Exception("dateRef requis");

        String dateStr = dateRef.toString(); // "yyyy-MM-dd" (java.sql.Date.toString())
        CinemaHistoriquePrixCategorieRange crit = new CinemaHistoriquePrixCategorieRange();
        String where = " AND idcategorierange = '" + idcategorierange + "' "
                + " AND TRUNC(datedebut) <= TO_DATE('" + dateStr + "', 'YYYY-MM-DD') "
                + " ORDER BY datedebut DESC";

        CinemaHistoriquePrixCategorieRange[] res = (CinemaHistoriquePrixCategorieRange[]) CGenUtil.rechercher(crit,
                null, null, c, where);

        if (res == null || res.length == 0)
            return null;
        return res[0];
    }

    /**
     * Supplément applicable à une catégorie de range à une date donnée.
     * Retourne 0 si aucun historique n'existe (une catégorie sans supplément = 0).
     */
    public static double getPrixEnVigueur(
            String idcategorierange, Date dateRef, Connection c) throws Exception {
        CinemaHistoriquePrixCategorieRange ligne = getLigneEnVigueur(idcategorierange, dateRef, c);
        return ligne == null ? 0.0 : ligne.getPrixsupplement();
    }

    /**
     * Supplément applicable aujourd'hui.
     */
    public static double getPrixActuel(String idcategorierange, Connection c) throws Exception {
        if (idcategorierange == null)
            throw new Exception("idcategorierange requis");

        CinemaHistoriquePrixCategorieRange crit = new CinemaHistoriquePrixCategorieRange();
        String where = " AND idcategorierange = '" + idcategorierange + "' "
                + " AND TRUNC(datedebut) <= TRUNC(SYSDATE) "
                + " ORDER BY datedebut DESC";

        CinemaHistoriquePrixCategorieRange[] res = (CinemaHistoriquePrixCategorieRange[]) CGenUtil.rechercher(crit,
                null, null, c, where);

        if (res == null || res.length == 0)
            return 0.0;
        return res[0].getPrixsupplement();
    }
}
