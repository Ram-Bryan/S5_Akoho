package ferme.receptionlieux;

import bean.CGenUtil;
import bean.ClassEtat;
import bean.ClassMAPTable;
import ferme.receptionaeroport.ReceptionPoussinAeroport;
import java.sql.Connection;
import java.sql.Date;
import utilitaire.UtilDB;

public class ReceptionLieux extends ClassEtat {

    private String id;
    private String idLot;
    private int ordre;
    private String idLieu;
    private Date daty;
    private double qteRecus;
    private double nbMort;
    private double nbPerdu;
    private String heureDepart;
    private String heureArrive;
    private String remarque;

    public ReceptionLieux() throws Exception {
        this.setNomTable("RECEPTIONLIEUX");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("RLX", "GETSEQ_RECEPTIONLIEUX");
        this.setId(makePK(c));
    }

    @Override public String getTuppleID() { return id; }
    @Override public String getAttributIDName() { return "id"; }
    @Override public String[] getMotCles() { return new String[]{"idLot"}; }
    @Override public String[] getValMotCles() { return new String[]{"idLot"}; }

    // ============ getters/setters ============
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdLot() { return idLot; }
    public void setIdLot(String idLot) { this.idLot = idLot; }

    public int getOrdre() { return ordre; }
    public void setOrdre(int ordre) { this.ordre = ordre; }

    public String getIdLieu() { return idLieu; }
    public void setIdLieu(String idLieu) { this.idLieu = idLieu; }

    public Date getDaty() { return daty; }
    public void setDaty(Date daty) { this.daty = daty; }

    public double getQteRecus() { return qteRecus; }
    public void setQteRecus(double qteRecus) { this.qteRecus = qteRecus; }

    public double getNbMort() { return nbMort; }
    public void setNbMort(double nbMort) { this.nbMort = nbMort; }

    public double getNbPerdu() { return nbPerdu; }
    public void setNbPerdu(double nbPerdu) { this.nbPerdu = nbPerdu; }

    public String getHeureDepart() { return heureDepart; }
    public void setHeureDepart(String heureDepart) { this.heureDepart = heureDepart; }

    public String getHeureArrive() { return heureArrive; }
    public void setHeureArrive(String heureArrive) { this.heureArrive = heureArrive; }

    public String getRemarque() { return remarque; }
    public void setRemarque(String remarque) { this.remarque = remarque; }

    // ============ computed ============
    public double getVivants() {
        return this.qteRecus - this.nbMort - this.nbPerdu;
    }

    // ============ static helpers ============
    public static int getNextOrdre(String idLot, Connection c) throws Exception {
        int max = 0;
        ReceptionLieux[] all = (ReceptionLieux[]) CGenUtil.rechercher(
            new ReceptionLieux(), null, null, c,
            " AND IDLOT = '" + idLot.replace("'", "''") + "'");
        if (all != null) {
            for (ReceptionLieux r : all) if (r.getOrdre() > max) max = r.getOrdre();
        }
        return max + 1;
    }

    /** Reads the previous step's net vivants. Throws if absent or not validated. */
    public static double getVivantsPrecedents(String idLot, int ordre, Connection c) throws Exception {
        if (ordre <= 1) {
            // Previous = latest validated airport reception
            ReceptionPoussinAeroport[] airports = (ReceptionPoussinAeroport[]) CGenUtil.rechercher(
                new ReceptionPoussinAeroport(), null, null, c,
                " AND IDLOT = '" + idLot.replace("'", "''") + "'"
                + " AND ETAT >= 11"
                + " ORDER BY DATY DESC, ID DESC");
            if (airports == null || airports.length == 0) {
                throw new Exception("Aucune réception aéroport validée pour ce lot. Validez d'abord la réception à l'aéroport.");
            }
            ReceptionPoussinAeroport a = airports[0];
            return a.getQteRecus() - a.getNbMort() - a.getNbPerdu();
        }
        // Previous = previous step with ordre = ordre - 1
        ReceptionLieux[] prev = (ReceptionLieux[]) CGenUtil.rechercher(
            new ReceptionLieux(), null, null, c,
            " AND IDLOT = '" + idLot.replace("'", "''") + "'"
            + " AND ORDRE = " + (ordre - 1));
        if (prev == null || prev.length == 0) {
            throw new Exception("Étape précédente (ordre " + (ordre - 1) + ") introuvable.");
        }
        if (prev[0].getEtat() < 11) {
            throw new Exception("Étape précédente (ordre " + (ordre - 1) + ") n'est pas validée. Validez-la d'abord.");
        }
        return prev[0].getVivants();
    }

    // ============ framework hooks ============
    @Override
    public ClassMAPTable createObject(String u, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) { c = new UtilDB().GetConn(); estOuvert = true; }

            // 1. Compute ordre server-side (ignore any client value)
            this.setOrdre(getNextOrdre(this.getIdLot(), c));

            // 2. Verify previous step exists and is validated
            getVivantsPrecedents(this.getIdLot(), this.getOrdre(), c);

            // 3. Validate quantities
            if (this.getQteRecus() < 0 || this.getNbMort() < 0 || this.getNbPerdu() < 0) {
                throw new Exception("Les quantités ne peuvent pas être négatives.");
            }
            if (this.getNbMort() + this.getNbPerdu() > this.getQteRecus()) {
                throw new Exception("Morts (" + this.getNbMort() + ") + perdus (" + this.getNbPerdu()
                        + ") ne peut pas dépasser la quantité reçue (" + this.getQteRecus() + ").");
            }

            // 4. Insert; translate ORA-00001 (race condition on (idLot, ordre))
            try {
                return super.createObject(u, c);
            } catch (Exception ex) {
                String msg = ex.getMessage() == null ? "" : ex.getMessage();
                if (msg.contains("ORA-00001") || msg.toUpperCase().contains("UNIQUE")) {
                    throw new Exception("Une étape a été créée entre-temps par un autre utilisateur. Rechargez la page.");
                }
                throw ex;
            }
        } finally {
            // if (estOuvert && c != null) c.close();
        }
    }

    /** Non-blocking warning — shown on the fiche only, never thrown. */
    public String getAvertissement(Connection c) throws Exception {
        if (this.getOrdre() <= 1) return null;
        try {
            double prevVivants = getVivantsPrecedents(this.getIdLot(), this.getOrdre(), c);
            if (Math.abs(this.getQteRecus() - prevVivants) > 0.01) {
                return "Vous avez reçu " + this.getQteRecus()
                    + " alors que l'étape précédente comptait " + prevVivants + " vivants.";
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public void controlerDelete(Connection c) throws Exception {
        if (this.getEtat() >= 11) {
            throw new Exception("Impossible de supprimer une étape validée.");
        }
        int max = 0;
        ReceptionLieux[] all = (ReceptionLieux[]) CGenUtil.rechercher(
            new ReceptionLieux(), null, null, c,
            " AND IDLOT = '" + this.getIdLot().replace("'", "''") + "'");
        if (all != null) {
            for (ReceptionLieux r : all) if (r.getOrdre() > max) max = r.getOrdre();
        }
        if (this.getOrdre() != max) {
            throw new Exception("Impossible de supprimer une étape intermédiaire. Seule la dernière étape peut être supprimée.");
        }
    }
}