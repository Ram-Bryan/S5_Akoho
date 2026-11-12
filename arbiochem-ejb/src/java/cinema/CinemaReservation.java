package cinema;

import bean.ClassMere;
import java.sql.Connection;
import java.sql.Date;
import bean.CGenUtil;

import bean.CGenUtil;
import bean.ClassFille;
import bean.ClassMAPTable;
import utilitaire.Utilitaire;

public class CinemaReservation extends ClassMere {
    private String id;
    private String idclient;
    private String iddetailprogramme;
    private Date datereservation;
    private String idstatutreservation;


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

    // ============================================================================
    // createObject : orchestration transactionnelle
    // 1. Statut initial = reserve
    // 2. Vérifier disponibilité des places
    // 3. Insérer mère + filles + audit
    // 4. Journal statut
    // 5. Facture brouillon
    // L'appelant DOIT avoir un c.setAutoCommit(false) pour l'atomicité.
    // ============================================================================
    @Override
    public ClassMAPTable createObject(String refUser, Connection c) throws Exception {
        // 1. Statut par défaut
        if (this.getIdstatutreservation() == null || this.getIdstatutreservation().trim().isEmpty()) {
            this.setIdstatutreservation(ConstanteCinema.STATUTRESERVE);
        }

        // 2. Disponibilité des places
        ClassFille[] filles = this.getFille();
        if (filles != null) {
            for (ClassFille f : filles) {
                CinemaDetailReservation d = (CinemaDetailReservation) f;
                if (d.getIdplace() == null || d.getIdplace().trim().isEmpty())
                    continue;
                if (!CinemaPlace.estDisponible(d.getIdplace(), this.getIddetailprogramme(), this.getId(), c)) {
                    throw new Exception("La place " + d.getIdplace() + " n'est plus disponible pour ce film programmé");
                }
            }
        }

        if (filles != null) {
            for (ClassFille f : filles) {
                CinemaDetailReservation d = (CinemaDetailReservation) f;
                if (d.getIdplace() == null || d.getIdplace().trim().isEmpty()) continue;
                
                double prix = CinemaDetailReservation.getPrixUnitaire(
                    this.getIddetailprogramme(), d.getIdplace(), this.getDatereservation(), c);
                d.setPrixunitaire(prix);
            }
        }

        // 3. Insert mère + filles
        ClassMAPTable result = super.createObject(refUser, c);

        // 4. Journal statut
        CinemaHistoriqueStatutReservation hist = new CinemaHistoriqueStatutReservation();
        hist.setIdreservation(this.getId());
        hist.setIdstatutreservation(this.getIdstatutreservation());
        hist.setDatehist(Utilitaire.dateDuJourSql());
        hist.construirePK(c);
        hist.insertToTableWithHisto(refUser, c);

        // 5. Facture brouillon
        CinemaFactureClient facture = new CinemaFactureClient();
        facture.setIdreservation(this.getId());
        facture.setNumero(CinemaFactureClient.genererNumero(c));
        facture.setDatefacture(Utilitaire.dateDuJourSql());
        facture.setIduser(refUser != null ? refUser : "0");
        facture.setEtat(1);
        facture.createObject(refUser, c);

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

    // ============================================================================
    // Contrôle : champs obligatoires
    // ============================================================================
    @Override
    public void controler(Connection c) throws Exception {
        if (this.getIdclient() == null || this.getIdclient().trim().isEmpty())
            throw new Exception("Client obligatoire");
        if (this.getIddetailprogramme() == null || this.getIddetailprogramme().trim().isEmpty())
            throw new Exception("Film programmé obligatoire");
        if (this.getDatereservation() == null)
            throw new Exception("Date de réservation obligatoire");
    }

    // ============================================================================
    // Montant total calculé dynamiquement
    // - Si nouvelle réservation (id null) : depuis filles en mémoire
    // - Si réservation existante : depuis filles en base
    // Prix figé à la date de réservation (pas à aujourd'hui)
    // ============================================================================
    public double getMontantTotal(Connection c) throws Exception {
        if (this.getDatereservation() == null)
            throw new Exception("Date de réservation requise pour calculer le montant");

        if (this.getId() == null) {
            ClassFille[] filles = this.getFille();
            if (filles == null || filles.length == 0)
                return 0.0;
            double total = 0;
            for (ClassFille f : filles) {
                CinemaDetailReservation d = (CinemaDetailReservation) f;
                if (d.getIdplace() == null || d.getIdplace().trim().isEmpty())
                    continue;
                total += CinemaDetailReservation.getPrixUnitaire(
                        this.getIddetailprogramme(), d.getIdplace(), this.getDatereservation(), c);
            }
            return total;
        }

        CinemaDetailReservation crit = new CinemaDetailReservation();
        String where = " AND idreservation = '" + this.getId() + "' AND etat <> 0";
        CinemaDetailReservation[] details = (CinemaDetailReservation[]) CGenUtil.rechercher(crit, null, null, c, where);

        if (details == null)
            return 0.0;
        double total = 0;
        for (CinemaDetailReservation d : details) {
            total += CinemaDetailReservation.getPrixUnitaire(
                    this.getIddetailprogramme(), d.getIdplace(), this.getDatereservation(), c);
        }
        return total;
    }
}