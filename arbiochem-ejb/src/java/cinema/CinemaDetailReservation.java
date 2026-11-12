package cinema;

import bean.ClassFille;
import java.sql.Connection;
import bean.CGenUtil;
import java.sql.Date;

public class CinemaDetailReservation extends ClassFille {
    private String id;
    private String idreservation;
    private String idplace;
    private double prixunitaire;

    public CinemaDetailReservation() throws Exception {
        this.setNomTable("cinema_detail_reservation");
        this.setNomClasseMere("cinema.CinemaReservation");
        this.setLiaisonMere("idreservation");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CDR", "getseq_cinema_det_reserv");
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
        return new String[] { "idreservation", "idplace" };
    }

    @Override
    public String[] getValMotCles() {
        return new String[] { "idreservation", "idplace" };
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdreservation() {
        return idreservation;
    }

    public void setIdreservation(String idreservation) {
        this.idreservation = idreservation;
    }

    public String getIdplace() {
        return idplace;
    }

    public void setIdplace(String idplace) {
        this.idplace = idplace;
    }

    public double getPrixunitaire() { return prixunitaire; }
public void setPrixunitaire(double prixunitaire) { this.prixunitaire = prixunitaire; }


    // ============================================================================
    // Prix unitaire d'une place pour un film programmé à une date donnée
    // Prix = prix programme + supplément catégorie range (à la date)
    // ============================================================================
    public static double getPrixUnitaire(String iddetailprogramme, String idplace, Date dateRef, Connection c)
            throws Exception {
        if (iddetailprogramme == null || iddetailprogramme.trim().isEmpty())
            throw new Exception("iddetailprogramme requis");
        if (idplace == null || idplace.trim().isEmpty())
            throw new Exception("idplace requis");
        if (dateRef == null)
            throw new Exception("dateRef requis");

        // 1. idprogramme depuis iddetailprogramme
        CinemaDetailProgramme dp = new CinemaDetailProgramme();
        dp.setId(iddetailprogramme);
        CinemaDetailProgramme[] dps = (CinemaDetailProgramme[]) CGenUtil.rechercher(dp, null, null, c, "");
        if (dps == null || dps.length == 0)
            throw new Exception("Détail programme introuvable : " + iddetailprogramme);
        String idprogramme = dps[0].getIdprogramme();

        // 2. idcategorierange depuis idplace → idrange → range
        CinemaPlace pl = new CinemaPlace();
        pl.setId(idplace);
        CinemaPlace[] pls = (CinemaPlace[]) CGenUtil.rechercher(pl, null, null, c, "");
        if (pls == null || pls.length == 0)
            throw new Exception("Place introuvable : " + idplace);

        CinemaRange rng = new CinemaRange();
        rng.setId(pls[0].getIdrange());
        CinemaRange[] rngs = (CinemaRange[]) CGenUtil.rechercher(rng, null, null, c, "");
        if (rngs == null || rngs.length == 0)
            throw new Exception("Range introuvable : " + pls[0].getIdrange());

        // 3. Prix programme + supplément catégorie (à la date de référence)
        double prixProgramme = CinemaHistoriquePrixProgramme.getPrixEnVigueur(idprogramme, dateRef, c);
        double supplement = CinemaHistoriquePrixCategorieRange.getPrixEnVigueur(rngs[0].getIdcategorierange(), dateRef,
                c);

        return prixProgramme + supplement;
    }
}
