package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;
import bean.CGenUtil;

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
        return new String[] { "idprogramme", "datedebut" };
    }

    @Override
    public String[] getValMotCles() {
        return new String[] { "idprogramme", "datedebut" };
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

    // ============================================================================
    // Historique append-only : tout UPDATE est transformé en INSERT
    // ============================================================================
    @Override
    public void updateToTableWithHisto(String refUser, Connection c) throws Exception {
        this.setId(null);
        this.construirePK(c);
        this.insertToTableWithHisto(refUser, c);
    }

    // ============================================================================
    // Utilitaires : prix en vigueur à une date, prix actuel
    // ============================================================================
    public static CinemaHistoriquePrixProgramme getLigneEnVigueur(
            String idprogramme, Date dateRef, Connection c) throws Exception {
        if (idprogramme == null)
            throw new Exception("idprogramme requis");
        if (dateRef == null)
            throw new Exception("dateRef requis");

        String dateStr = dateRef.toString();
        CinemaHistoriquePrixProgramme crit = new CinemaHistoriquePrixProgramme();
        String where = " AND idprogramme = '" + idprogramme + "' "
                + " AND TRUNC(datedebut) <= TO_DATE('" + dateStr + "', 'YYYY-MM-DD') "
                + " ORDER BY datedebut DESC";

        CinemaHistoriquePrixProgramme[] res = (CinemaHistoriquePrixProgramme[]) CGenUtil.rechercher(crit, null, null, c,
                where);

        if (res == null || res.length == 0)
            return null;
        return res[0];
    }

    /**
     * Prix applicable à un programme à une date donnée.
     * Throw si aucun prix n'existe (contrairement au supplément de catégorie qui
     * retourne 0).
     */
    public static double getPrixEnVigueur(
            String idprogramme, Date dateRef, Connection c) throws Exception {
        CinemaHistoriquePrixProgramme ligne = getLigneEnVigueur(idprogramme, dateRef, c);
        if (ligne == null) {
            throw new Exception("Aucun prix en vigueur pour le programme " + idprogramme
                    + " à la date " + dateRef);
        }
        return ligne.getPrix();
    }

    /**
     * Prix applicable aujourd'hui.
     */
    public static double getPrixActuel(String idprogramme, Connection c) throws Exception {
        if (idprogramme == null)
            throw new Exception("idprogramme requis");

        CinemaHistoriquePrixProgramme crit = new CinemaHistoriquePrixProgramme();
        String where = " AND idprogramme = '" + idprogramme + "' "
                + " AND TRUNC(datedebut) <= TRUNC(SYSDATE) "
                + " ORDER BY datedebut DESC";

        CinemaHistoriquePrixProgramme[] res = (CinemaHistoriquePrixProgramme[]) CGenUtil.rechercher(crit, null, null, c,
                where);

        if (res == null || res.length == 0) {
            throw new Exception("Aucun prix en vigueur pour le programme " + idprogramme);
        }
        return res[0].getPrix();
    }
}