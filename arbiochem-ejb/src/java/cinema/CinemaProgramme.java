package cinema;

import bean.CGenUtil;
import bean.ClassFille;
import bean.ClassMAPTable;
import bean.ClassMere;
import utilitaire.Utilitaire;
import java.sql.Connection;
import java.sql.Date;

public class CinemaProgramme extends ClassMere {
    private String id;
    private String idsalle;
    private Date datedebut;
    private String heuredebut;
    private double prix;


    public CinemaProgramme() throws Exception {
        this.setNomTable("cinema_programme");
        this.setNomClasseFille("cinema.CinemaDetailProgramme");
        this.setLiaisonFille("idprogramme");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CPRG", "getseq_cinema_programme");
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
        return new String[] { "idsalle", "datedebut", "heuredebut" };
    }

    @Override
    public String[] getValMotCles() {
        return new String[] { "idsalle", "datedebut", "heuredebut" };
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdsalle() {
        return idsalle;
    }

    public void setIdsalle(String idsalle) {
        this.idsalle = idsalle;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public String getHeuredebut() {
        return heuredebut;
    }

    public void setHeuredebut(String heuredebut) {
        this.heuredebut = heuredebut;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }


    // ============================================================================
    // createObject : insère le programme + ligne de prix initiale dans l'historique
    // ============================================================================
    @Override
    public ClassMAPTable createObject(String refUser, Connection c) throws Exception {
        ClassMAPTable result = super.createObject(refUser, c);

        if (this.getPrix() > 0) {
            CinemaHistoriquePrixProgramme hist = new CinemaHistoriquePrixProgramme();
            hist.setIdprogramme(this.getId());
            hist.setPrix(this.getPrix());
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
        // Charger l'état actuel en base avant update
        CinemaProgramme avant = new CinemaProgramme();
        avant.setId(this.getId());
        CinemaProgramme[] before = (CinemaProgramme[]) CGenUtil.rechercher(avant, null, null, c, "");
        double prixAvant = (before != null && before.length > 0) ? before[0].getPrix() : 0.0;

        // Update normal
        int result = super.updateObject(refUser, c);

        // Si le prix a changé, tracer dans l'historique
        if (Math.abs(this.getPrix() - prixAvant) > 0.001) {
            CinemaHistoriquePrixProgramme hist = new CinemaHistoriquePrixProgramme();
            hist.setIdprogramme(this.getId());
            hist.setPrix(this.getPrix());
            hist.setDatedebut(Utilitaire.dateDuJourSql());
            hist.construirePK(c);
            hist.insertToTableWithHisto(refUser, c);
        }

        return result;
    }

    // ============================================================================
    // Durée totale du programme (somme des durées des films)
    // ============================================================================
    public int getDureeTotal(Connection c) throws Exception {
        ClassFille[] filles = this.getFille();
        if (filles != null && filles.length > 0) {
            int total = 0;
            for (ClassFille f : filles) {
                CinemaDetailProgramme d = (CinemaDetailProgramme) f;
                if (d.getIdfilm() == null || d.getIdfilm().trim().isEmpty())
                    continue;
                CinemaFilm film = new CinemaFilm();
                film.setId(d.getIdfilm());
                CinemaFilm[] films = (CinemaFilm[]) CGenUtil.rechercher(film, null, null, c, "");
                if (films != null && films.length > 0)
                    total += films[0].getDuree();
            }
            return total;
        }

        if (this.getId() == null)
            return 0;
        java.sql.Statement st = null;
        java.sql.ResultSet rs = null;
        try {
            st = c.createStatement();
            String sql = "SELECT NVL(SUM(f.duree),0) FROM cinema_film f "
                    + "JOIN cinema_detail_programme d ON d.idfilm = f.id "
                    + "WHERE d.idprogramme = '" + this.getId() + "' AND d.etat <> 0";
            rs = st.executeQuery(sql);
            return rs.next() ? rs.getInt(1) : 0;
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
    // Date et heure de fin, calculées dynamiquement
    // ============================================================================
    public Date getDateFin(Connection c) throws Exception {
        if (this.getDatedebut() == null || this.getHeuredebut() == null)
            return null;
        int duree = getDureeTotal(c);
        long finMilli = toMillis(this.getDatedebut(), this.getHeuredebut()) + (long) duree * 60_000L;
        return new Date(finMilli);
    }

    public String getHeureFin(Connection c) throws Exception {
        if (this.getHeuredebut() == null)
            return null;
        int duree = getDureeTotal(c);
        String[] p = this.getHeuredebut().split(":");
        int totalMin = Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]) + duree;
        totalMin = totalMin % (24 * 60);
        return String.format("%02d:%02d", totalMin / 60, totalMin % 60);
    }

    public static long toMillis(Date date, String heure) throws Exception {
        if (date == null || heure == null)
            throw new Exception("date et heure requis pour calculer les bornes");
        String[] p = heure.split(":");
        if (p.length != 2)
            throw new Exception("Format heure invalide : " + heure);
        return date.getTime()
                + (Long.parseLong(p[0]) * 60L + Long.parseLong(p[1])) * 60_000L;
    }

    // ============================================================================
    // Contrôle : chevauchement dans la même salle
    // ============================================================================
    @Override
    public void controler(Connection c) throws Exception {
        if (this.getIdsalle() == null || this.getIdsalle().trim().isEmpty())
            throw new Exception("Salle obligatoire");
        if (this.getDatedebut() == null)
            throw new Exception("Date de début obligatoire");
        if (this.getHeuredebut() == null
                || !this.getHeuredebut().matches("^([01][0-9]|2[0-3]):[0-5][0-9]$"))
            throw new Exception("Heure de début invalide (format HH:mm attendu)");

        int duree = getDureeTotal(c);
        if (duree <= 0)
            return;

        long debutMilli = toMillis(this.getDatedebut(), this.getHeuredebut());
        long finMilli = debutMilli + (long) duree * 60_000L;

        CinemaProgramme crit = new CinemaProgramme();
        crit.setIdsalle(this.getIdsalle());
        String where = " AND etat <> 0 ";
        if (this.getId() != null)
            where += " AND id <> '" + this.getId() + "' ";

        CinemaProgramme[] autres = (CinemaProgramme[]) CGenUtil.rechercher(crit, null, null, c, where);
        if (autres == null)
            return;

        for (CinemaProgramme p : autres) {
            int dureeAutre = p.getDureeTotal(c);
            if (dureeAutre <= 0)
                continue;

            long debutAutre = toMillis(p.getDatedebut(), p.getHeuredebut());
            long finAutre = debutAutre + (long) dureeAutre * 60_000L;

            if (debutMilli < finAutre && finMilli > debutAutre) {
                throw new Exception("Chevauchement avec le programme " + p.getId()
                        + " (début " + p.getHeuredebut() + ", durée " + dureeAutre + " min)"
                        + " dans la salle " + this.getIdsalle());
            }
        }
    }
}