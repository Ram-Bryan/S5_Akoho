package cinema;

import bean.ClassMere;
import java.sql.Connection;
import java.sql.Date;
import bean.CGenUtil;

import bean.CGenUtil;

public class CinemaReservation extends ClassMere {
    private String id;
    private String idclient;
    private String iddetailprogramme;
    private Date datereservation;
    private String idstatutreservation;
    private int etat;
    private String iduser;

    public CinemaReservation() throws Exception {
        this.setNomTable("cinema_reservation");
        this.setNomClasseFille("cinema.CinemaDetailReservation");
        this.setLiaisonFille("idreservation");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CRES", "getseq_cinema_reservation");
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
        return new String[] { "idclient", "iddetailprogramme", "datereservation" };
    }

    @Override
    public String[] getValMotCles() {
        return new String[] { "idclient", "iddetailprogramme", "datereservation" };
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdclient() {
        return idclient;
    }

    public void setIdclient(String idclient) {
        this.idclient = idclient;
    }

    // Getter/setter canoniques dérivés du nom de colonne
    public String getIddetailprogramme() {
        return iddetailprogramme;
    }

    public void setIddetailprogramme(String iddetailprogramme) {
        this.iddetailprogramme = iddetailprogramme;
    }

    // Alias camelCase lisibles (pour le code JSP, non utilisés par le framework)
    public String getIdDetailProgramme() {
        return iddetailprogramme;
    }

    public void setIdDetailProgramme(String iddetailprogramme) {
        this.iddetailprogramme = iddetailprogramme;
    }

    public Date getDatereservation() {
        return datereservation;
    }

    public void setDatereservation(Date datereservation) {
        this.datereservation = datereservation;
    }

    public String getIdstatutreservation() {
        return idstatutreservation;
    }

    public void setIdstatutreservation(String idstatutreservation) {
        this.idstatutreservation = idstatutreservation;
    }

    @Override
    public int getEtat() {
        return etat;
    }

    @Override
    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    // ============================================================================
    // createObject : initialise le statut à "reserve" + trace dans l'historique
    // ============================================================================
    @Override
    public ClassMAPTable createObject(String refUser, Connection c) throws Exception {
        // 1. Statut initial par défaut : reserve
        if (this.getIdstatutreservation() == null
                || this.getIdstatutreservation().trim().isEmpty()) {
            this.setIdstatutreservation(ConstanteCinema.STATUTRESERVE);
        }

        // 2. Insertion standard (réservation + filles + audit framework)
        ClassMAPTable result = super.createObject(refUser, c);

        // 3. Trace initiale dans le journal
        CinemaHistoriqueStatutReservation hist = new CinemaHistoriqueStatutReservation();
        hist.setIdreservation(this.getId());
        hist.setIdstatutreservation(this.getIdstatutreservation());
        hist.setDatehist(utilitaire.Utilitaire.dateDuJourSql());
        hist.construirePK(c);
        hist.insertToTableWithHisto(refUser, c);

        return result;
    }

    // ============================================================================
    // changerStatut : transition contrôlée + historique
    // ============================================================================
    public void changerStatut(String nouvelIdStatut, String refUser, Connection c) throws Exception {
        if (nouvelIdStatut == null || nouvelIdStatut.trim().isEmpty()) {
            throw new Exception("Nouveau statut requis");
        }

        String ancienIdStatut = this.getIdstatutreservation();

        // Si pas de changement, on ne fait rien
        if (nouvelIdStatut.equals(ancienIdStatut))
            return;

        // Règles de transition
        // statut terminal (annule / expire) → tout autre ❌
        if (ConstanteCinema.STATUTANNULE.equals(ancienIdStatut)
                || ConstanteCinema.STATUTEXPIRE.equals(ancienIdStatut)) {
            throw new Exception(
                    "Impossible de changer le statut d'une réservation déjà " + ancienIdStatut);
        }
        if (!ConstanteCinema.STATUTRESERVE.equals(ancienIdStatut)) {
            throw new Exception(
                    "Transition non autorisée depuis " + ancienIdStatut + " vers " + nouvelIdStatut);
        }

        // 1. Update la réservation (UPDATE normal)
        this.setIdstatutreservation(nouvelIdStatut);
        this.updateToTableWithHisto(refUser, c);

        // 2. Insert dans le journal
        CinemaHistoriqueStatutReservation hist = new CinemaHistoriqueStatutReservation();
        hist.setIdreservation(this.getId());
        hist.setIdstatutreservation(nouvelIdStatut);
        hist.setDatehist(utilitaire.Utilitaire.dateDuJourSql());
        hist.construirePK(c);
        hist.insertToTableWithHisto(refUser, c);
    }

    // ============================================================================
    // controlerDelete : ne pas supprimer une réservation non-reserve
    // ============================================================================
    @Override
    public void controlerDelete(Connection c) throws Exception {
        if (this.getIdstatutreservation() != null
                && !ConstanteCinema.STATUTRESERVE.equals(this.getIdstatutreservation())) {
            throw new Exception(
                    "Impossible de supprimer une réservation dont le statut n'est plus 'reserve'. "
                            + "Annulez-la à la place.");
        }
    }
}