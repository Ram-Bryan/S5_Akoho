package cinema;

import bean.ClassEtat;
import java.sql.Connection;
import java.sql.Date;

import bean.CGenUtil;
import utilitaire.Utilitaire;

public class CinemaFactureClient extends ClassEtat {
    private String id;
    private String numero;
    private String idreservation;
    private Date datefacture;


    public CinemaFactureClient() throws Exception {
        this.setNomTable("cinema_facture_client");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CFAC", "getseq_cinema_facture_client");
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
        return new String[] { "numero", "idreservation" };
    }

    @Override
    public String[] getValMotCles() {
        return new String[] { "numero", "idreservation" };
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIdreservation() {
        return idreservation;
    }

    public void setIdreservation(String idreservation) {
        this.idreservation = idreservation;
    }

    public Date getDatefacture() {
        return datefacture;
    }

    public void setDatefacture(Date datefacture) {
        this.datefacture = datefacture;
    }


    // ============================================================================
    // Numéro de facture : FC<YYYY><seq6> ex: FC2026000001
    // Race possible en concurrence : la contrainte UNIQUE catch + retry côté
    // appelant
    // ============================================================================
    public static String genererNumero(Connection c) throws Exception {
        int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        String prefix = "FC" + year;

        java.sql.Statement st = null;
        java.sql.ResultSet rs = null;
        try {
            st = c.createStatement();
            String sql = "SELECT NVL(MAX(numero), '" + prefix + "000000') "
                    + "FROM cinema_facture_client "
                    + "WHERE numero LIKE '" + prefix + "%'";
            rs = st.executeQuery(sql);
            String max = rs.next() ? rs.getString(1) : (prefix + "000000");
            if (max == null)
                max = prefix + "000000";

            long seq = Long.parseLong(max.substring(prefix.length())) + 1;
            return prefix + String.format("%06d", seq);
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (st != null)
                try {
                    st.close();
                } catch (Exception e) {
                }
        }
    }

    // ============================================================================
    // Contrôle : unicité numéro + unicité réservation + cohérence
    // ============================================================================
    @Override
    public void controler(Connection c) throws Exception {
        if (this.getNumero() == null || this.getNumero().trim().isEmpty())
            throw new Exception("Numéro de facture obligatoire");
        if (this.getIdreservation() == null || this.getIdreservation().trim().isEmpty())
            throw new Exception("Réservation obligatoire");
        if (this.getDatefacture() == null)
            this.setDatefacture(Utilitaire.dateDuJourSql());

        // Unicité numéro
        CinemaFactureClient critN = new CinemaFactureClient();
        String whereN = " AND numero = '" + this.getNumero() + "' ";
        if (this.getId() != null)
            whereN += " AND id <> '" + this.getId() + "' ";
        CinemaFactureClient[] hitsN = (CinemaFactureClient[]) CGenUtil.rechercher(critN, null, null, c, whereN);
        if (hitsN != null && hitsN.length > 0)
            throw new Exception("Le numéro " + this.getNumero() + " existe déjà");

        // Unicité réservation (1 facture par réservation)
        CinemaFactureClient critR = new CinemaFactureClient();
        String whereR = " AND idreservation = '" + this.getIdreservation() + "' ";
        if (this.getId() != null)
            whereR += " AND id <> '" + this.getId() + "' ";
        CinemaFactureClient[] hitsR = (CinemaFactureClient[]) CGenUtil.rechercher(critR, null, null, c, whereR);
        if (hitsR != null && hitsR.length > 0)
            throw new Exception("Une facture existe déjà pour la réservation " + this.getIdreservation());
    }

    // ============================================================================
    // Montant total : délégué à la réservation
    // ============================================================================
    public double getMontantTotal(Connection c) throws Exception {
        if (this.getIdreservation() == null)
            return 0.0;
        CinemaReservation r = new CinemaReservation();
        r.setId(this.getIdreservation());
        CinemaReservation[] rs = (CinemaReservation[]) CGenUtil.rechercher(r, null, null, c, "");
        if (rs == null || rs.length == 0)
            return 0.0;
        return rs[0].getMontantTotal(c);
    }
}
