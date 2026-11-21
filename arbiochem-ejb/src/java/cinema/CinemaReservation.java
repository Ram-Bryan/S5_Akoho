package cinema;

import bean.ClassMere;
import mg.cnaps.compta.ComptaSousEcriture;

import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

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
        if (this.getIdstatutreservation() == null || this.getIdstatutreservation().trim().isEmpty()) {
            this.setIdstatutreservation(ConstanteCinema.STATUTRESERVE);
        }

        ClassFille[] filles = this.getFille();
        if (filles != null) {
            for (ClassFille f : filles) {
                CinemaDetailReservation d = (CinemaDetailReservation) f;
                if (d.getIdplace() == null || d.getIdplace().trim().isEmpty())
                    continue;

                // Vérification disponibilité
                if (!CinemaPlace.estDisponible(d.getIdplace(), this.getIddetailprogramme(), this.getId(), c)) {
                    throw new Exception("La place " + d.getIdplace() + " n'est plus disponible pour ce film programmé");
                }

                // Le prix est calculé (avec réduction) et figé
                double prix = CinemaDetailReservation.getPrixUnitaire(
                        this.getIddetailprogramme(), d.getIdplace(), this.getDatereservation(), c);
                d.setPrixunitaire(prix);
            }
        }

        this.calculerRemise(c);

        ClassMAPTable result = super.createObject(refUser, c);

        CinemaHistoriqueStatutReservation hist = new CinemaHistoriqueStatutReservation();
        hist.setIdreservation(this.getId());
        hist.setIdstatutreservation(this.getIdstatutreservation());
        hist.setDatehist(Utilitaire.dateDuJourSql());
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

        /**
     * Applique les remises sur le prix figé de chaque place.
     * À appeler après que prixunitaire a été renseigné sur toutes les filles.
     */
    public void calculerRemise(Connection c) throws Exception {
        ClassFille[] filles = this.getFille();
        if (filles == null || filles.length == 0) return;

        // 1. Catégorie de chaque place, lue une seule fois
        String[] cats = new String[filles.length];
        for (int i = 0; i < filles.length; i++) {
            String idPlace = ((CinemaDetailReservation) filles[i]).getIdplace();
            cats[i] = (idPlace == null || idPlace.trim().isEmpty())
                    ? null
                    : CinemaDetailReservation.getCategorieDeLaPlace(idPlace, c);
        }

        // 2. Règles déclenchées par cette réservation
        CinemaRemise[] regles = CinemaRemise.findDeclenchees(cats, this.getDatereservation(), c);
        if (regles.length == 0) return;

        // 3. Cumul par place, selon sa catégorie
        for (int i = 0; i < filles.length; i++) {
            if (cats[i] == null) continue;
            
            double pct = 0;
            for (CinemaRemise r : regles) {
                if (r.getIdcategorietarget().equals(cats[i])) {
                    pct += r.getPourcentage();
                }
            }
            
            if (pct > 0) {
                pct = Math.min(100, pct);
                CinemaDetailReservation d = (CinemaDetailReservation) filles[i];
                double prix = d.getPrixunitaire() * (100 - pct) / 100.0;
                // Arrondi à 2 décimales pour correspondre à NUMBER(15,2) ou (30,2)
                d.setPrixunitaire(Math.round(prix * 100) / 100.0); 
            }
        }
    }

    /**
     * Montant total : on additionne simplement le prix figé en base.
     * CORRECTION DU BUG : on ne recalcule surtout pas le prix de base.
     */
    public double getMontantTotal(Connection c) throws Exception {
        if (this.getId() == null) {
            throw new Exception("Montant disponible seulement après création de la réservation");
        }
        CinemaDetailReservation crit = new CinemaDetailReservation();
        String where = " AND idreservation = '" + this.getId() + "' AND etat <> 0";
        CinemaDetailReservation[] details = (CinemaDetailReservation[]) CGenUtil.rechercher(crit, null, null, c, where);
        double total = 0;
        if (details != null) {
            for (CinemaDetailReservation d : details) {
                total += d.getPrixunitaire();
            }
        }
        return total;
    }


    // ============================================================================
    // changeState : gère l'action "payer" (valider le paiement)
    // ============================================================================
    // ============================================================================
    // changeState : gère l'action "payer"
    // ============================================================================
    @Override
    public void changeState(String action, String refUser, Connection c) throws Exception {
        if ("payer".equalsIgnoreCase(action)) {
            if (!ConstanteCinema.STATUTRESERVE.equals(this.getIdstatutreservation())) {
                throw new Exception("Seule une réservation au statut 'reserve' peut être payée.");
            }
            // 1. Statut -> paye
            this.changerStatut(ConstanteCinema.STATUTPAYE, refUser, c);
            // 2. Créer la facture
            this.creerFacture(refUser, c);
            return;
        }
        // Déléguer au parent pour les autres actions
        super.changeState(action, refUser, c);
    }

    // ============================================================================
    // creerFacture : crée la facture pour la réservation courante
    // (appelée uniquement après passage au statut "paye")
    // ============================================================================
    // ============================================================================
    // creerFacture : Crée la facture (mère + filles) et le paiement associé.
    // Appelée uniquement après le passage au statut "paye".
    // ============================================================================
    public void creerFacture(String refUser, java.sql.Connection c) throws Exception {
        // 1. Vérifier s'il y a déjà une facture pour cette réservation (sécurité)
        FactureClient crit = new FactureClient();
        String where = " AND IDRESERVATIONCINEMA = '" + this.getId() + "' ";
        FactureClient[] existing = (FactureClient[]) CGenUtil.rechercher(crit, null, null, c, where);
        if (existing != null && existing.length > 0) {
            throw new Exception("Une facture existe déjà pour cette réservation.");
        }

        // 2. Créer l'en-tête de la facture (Mère)
        FactureClient facture = new FactureClient();
        facture.setIdreservationcinema(this.getId());
        facture.setIdclient(this.getIdclient());
        facture.setDaty(utilitaire.Utilitaire.dateDuJourSql());
        facture.setDesignation("Facture Cinéma - Réservation " + this.getId());
        facture.setEtat(11); // 11 = Validé (ou 1 si tu veux brouillon)
        facture.setTva(0); // Pas de TVA pour le cinéma

        // 3. Récupérer les détails de la réservation (les places)
        CinemaDetailReservation critDet = new CinemaDetailReservation();
        String whereDet = " AND idreservation = '" + this.getId() + "' ";

        CinemaDetailReservation[] details = (CinemaDetailReservation[]) CGenUtil.rechercher(critDet, null, null, c,
                whereDet);

        if (details == null || details.length == 0) {
            throw new Exception("Impossible de créer la facture : aucune place trouvée pour cette réservation.");
        }

        // 4. Préparer les lignes de détails (Filles)
        FactureClientDetails[] factureDetails = new FactureClientDetails[details.length];
        double totalFacture = 0;

        for (int i = 0; i < details.length; i++) {
            factureDetails[i] = new FactureClientDetails();
            factureDetails[i].setIddetailreservationcinema(details[i].getId());
            factureDetails[i].setQte(1); // On facture 1 place à la fois
            factureDetails[i].setPu(details[i].getPrixunitaire()); // Prix figé au moment de la résa
            factureDetails[i].setTva(0);
            factureDetails[i].setRemises(0);
            // On peut laisser idproduit, idacte, compte à NULL (ils sont nullables)

            totalFacture += details[i].getPrixunitaire();
        }

        // 5. Lier les filles à la mère
        facture.setFille(factureDetails);

        // 6. Insérer la mère + les filles en une seule transaction
        // (grâce à ClassMere, super.createObject s'occupe d'insérer les filles
        // automatiquement)
        facture.createObject(refUser, c);

        // 7. Créer le paiement
        CinemaPaiement paiement = new CinemaPaiement();
        paiement.setIdfacture(facture.getId());
        paiement.setDaty(utilitaire.Utilitaire.dateDuJourSql());
        paiement.setMontant(totalFacture);

        // TODO: Passe le mode de paiement en paramètre si tu as un dropdown dans l'UI
        // Si tu n'as pas encore d'UI de choix, tu peux mettre une valeur par défaut
        // paiement.setIdmodepaiement(request.getParameter("idmodepaiement"));

        paiement.setIduser(refUser != null ? refUser : "0");
        paiement.createObject(refUser, c);
    }
}