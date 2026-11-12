package cinema;

import bean.ClassFille;
import java.sql.Connection;

import bean.CGenUtil;

public class CinemaPlace extends ClassFille {
    private String id;
    private String libelle;
    private String idrange;

    public CinemaPlace() throws Exception {
        this.setNomTable("cinema_place");
        this.setNomClasseMere("cinema.CinemaRange");
        this.setLiaisonMere("idrange");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CPLA", "getseq_cinema_place");
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
        return new String[] { "libelle", "idrange" };
    }

    @Override
    public String[] getValMotCles() {
        return new String[] { "libelle", "idrange" };
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

    public String getIdrange() {
        return idrange;
    }

    public void setIdrange(String idrange) {
        this.idrange = idrange;
    }


    // ============================================================================
    // Disponibilité d'une place pour un film programmé donné
    // ============================================================================
    public static boolean estDisponible(String idplace, String iddetailprogramme, Connection c) throws Exception {
        return estDisponible(idplace, iddetailprogramme, null, c);
    }

    /**
     * Variante avec exclusion (utile pour un update : ignorer la réservation
     * courante).
     */
    public static boolean estDisponible(String idplace, String iddetailprogramme, String idreservationAExclure,
            Connection c) throws Exception {
        if (idplace == null || idplace.trim().isEmpty())
            throw new Exception("idplace requis");
        if (iddetailprogramme == null || iddetailprogramme.trim().isEmpty())
            throw new Exception("iddetailprogramme requis");

        CinemaDetailReservation crit = new CinemaDetailReservation();
        String where = " AND idplace = '" + idplace + "' "
                + " AND idreservation IN ( "
                + "     SELECT id FROM cinema_reservation "
                + "     WHERE iddetailprogramme = '" + iddetailprogramme + "' "
                + "       AND idstatutreservation NOT IN ('" + ConstanteCinema.STATUTANNULE + "','"
                + ConstanteCinema.STATUTEXPIRE + "') ";
        if (idreservationAExclure != null && !idreservationAExclure.trim().isEmpty()) {
            where += " AND id <> '" + idreservationAExclure + "' ";
        }
        where += ")";

        CinemaDetailReservation[] hits = (CinemaDetailReservation[]) CGenUtil.rechercher(crit, null, null, c, where);
        return hits == null || hits.length == 0;
    }
}