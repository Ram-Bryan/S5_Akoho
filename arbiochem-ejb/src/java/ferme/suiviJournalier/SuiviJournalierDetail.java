package ferme.suiviJournalier;

import bean.ClassFille;
import ferme.utils.ConstanteFerme;

import java.sql.Connection;
import java.sql.Date;

import ferme.utils.ConstanteFerme;
import utilitaire.Utilitaire;
import bean.CGenUtil;


public class SuiviJournalierDetail extends ClassFille {
    private String id;
    private String idmere;
    private String idbatiment;
    private String idparquet;
    private double agejour;
    private double entree;
    private double tri;
    private double mortalite;
    private double culls;
    private double consignesuraliment;
    private double eau;
    private double temperaturemin;
    private double temperaturemax;

    private double nbmort;
    private double nbperdu;
    private String idsexe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdmere() {
        return idmere;
    }

    public void setIdmere(String idmere) {
        this.idmere = idmere;
    }

    public String getIdbatiment() {
        return idbatiment;
    }

    public void setIdbatiment(String idbatiment) {
        this.idbatiment = idbatiment;
    }

    public String getIdparquet() {
        return idparquet;
    }

    public void setIdparquet(String idparquet) {
        this.idparquet = idparquet;
    }

    public double getAgejour() {
        return agejour;
    }

    public void setAgejour(double agejour) {
        this.agejour = agejour;
    }

    public double getEntree() {
        return entree;
    }

    public void setEntree(double entree) {
        this.entree = entree;
    }

    public double getTri() {
        return tri;
    }

    public void setTri(double tri) {
        this.tri = tri;
    }

    public double getMortalite() {
        return mortalite;
    }

    public void setMortalite(double mortalite) {
        this.mortalite = mortalite;
    }

    public double getCulls() {
        return culls;
    }

    public void setCulls(double culls) {
        this.culls = culls;
    }

    public double getConsignesuraliment() {
        return consignesuraliment;
    }

    public void setConsignesuraliment(double consignesuraliment) {
        this.consignesuraliment = consignesuraliment;
    }

    public double getEau() {
        return eau;
    }

    public void setEau(double eau) {
        this.eau = eau;
    }

    public double getTemperaturemin() {
        return temperaturemin;
    }

    public void setTemperaturemin(double temperaturemin) {
        this.temperaturemin = temperaturemin;
    }

    public double getTemperaturemax() {
        return temperaturemax;
    }

    public void setTemperaturemax(double temperaturemax) {
        this.temperaturemax = temperaturemax;
    }
    public double getNbmort() { return nbmort; }
    public void setNbmort(double nbmort) { this.nbmort = nbmort; }
    public double getNbperdu() { return nbperdu; }
    public void setNbperdu(double nbperdu) { this.nbperdu = nbperdu; }
    public String getIdsexe() { return idsexe; }
    public void setIdsexe(String idsexe) { this.idsexe = idsexe; }

    @Override
public void controler(Connection c) throws Exception {
    super.controler(c);

    if (this.getIdsexe() == null || this.getIdsexe().trim().isEmpty()) {
        throw new Exception("Le sexe est obligatoire pour toute ligne de suivi.");
    }

    if (this.getNbmort() < 0 || this.getNbperdu() < 0) {
        throw new Exception("Les morts et perdus ne peuvent pas être négatifs.");
    }

    if (this.getIdmere() == null || this.getIdmere().trim().isEmpty()) {
        return; // pas encore rattaché à un suivi, rien à vérifier
    }

    // Charger le suivi parent pour connaître lot + date
    SuiviJournalier parent = (SuiviJournalier)
        new SuiviJournalier().getById(this.getIdmere(), null, c);
    if (parent == null) {
        throw new Exception("SuiviJournalier introuvable : " + this.getIdmere());
    }

    String idLot = parent.getIdlot();
    Date date = parent.getDaty();
    String exclu = (this.getId() == null || this.getId().trim().isEmpty())
        ? "" : " AND ID <> '" + this.getId().replace("'", "''") + "'";

    // Triage jusqu'à cette date pour (lot, parquet, sexe)
    String triageDate = "SELECT NVL(SUM(QTE),0) FROM PREV_TRIAGE_V " +
        "WHERE ETAT >= 11 AND IDLOT = '" + idLot.replace("'", "''") + "' " +
        "AND " + eq("IDPARQUET", this.getIdparquet()) + " " +
        "AND " + eq("IDSEXE", this.getIdsexe()) + " " +
        "AND DATY <= " + sqlDate(date);
    double triage = number(CGenUtil.rechercherSimple(triageDate, c));

    // Suivi déjà saisi jusqu'à cette date, hors ligne en cours
    String suiviDate = "SELECT NVL(SUM(NBMORT),0) + NVL(SUM(NBPERDU),0) FROM PREV_SUIVI_V " +
        "WHERE ETAT >= 11 AND IDLOT = '" + idLot.replace("'", "''") + "' " +
        "AND " + eq("IDPARQUET", this.getIdparquet()) + " " +
        "AND " + eq("IDSEXE", this.getIdsexe()) + " " +
        "AND DATY <= " + sqlDate(date) + exclu;
    double deja = number(CGenUtil.rechercherSimple(suiviDate, c));

    double restantDate = triage - deja;
    double saisie = this.getNbmort() + this.getNbperdu();

    if (saisie > restantDate + 0.001) {
        throw new Exception(String.format(
            "Restant à la date (parquet %s, sexe %s) : %.0f, saisie : %.0f",
            libSexe(this.getIdsexe()), restantDate, saisie));
    }

    // Contrôle sans limite de date pour éviter les saisies antidatées négatives
    String triageTotal = "SELECT NVL(SUM(QTE),0) FROM PREV_TRIAGE_V " +
        "WHERE ETAT >= 11 AND IDLOT = '" + idLot.replace("'", "''") + "' " +
        "AND " + eq("IDPARQUET", this.getIdparquet()) + " " +
        "AND " + eq("IDSEXE", this.getIdsexe());
    double triageT = number(CGenUtil.rechercherSimple(triageTotal, c));
    String suiviTotal = "SELECT NVL(SUM(NBMORT),0) + NVL(SUM(NBPERDU),0) FROM PREV_SUIVI_V " +
        "WHERE ETAT >= 11 AND IDLOT = '" + idLot.replace("'", "''") + "' " +
        "AND " + eq("IDPARQUET", this.getIdparquet()) + " " +
        "AND " + eq("IDSEXE", this.getIdsexe()) + exclu;
    double dejaT = number(CGenUtil.rechercherSimple(suiviTotal, c));
    double restantT = triageT - dejaT;
    if (saisie > restantT + 0.001) {
        throw new Exception(String.format(
            "Restant total (parquet %s, sexe %s) : %.0f, saisie : %.0f",
            libSexe(this.getIdsexe()), restantT, saisie));
    }
}

        private static String eq(String col, String val) {
            if (val == null || val.trim().isEmpty()) return col + " IS NULL";
            return col + " = '" + val.replace("'", "''") + "'";
        }

        private static String sqlDate(Date d) {
            return "TO_DATE('" + new java.text.SimpleDateFormat("yyyy-MM-dd").format(d) + "','YYYY-MM-DD')";
        }

        private static double number(Object o) {
            return o == null ? 0 : ((Number) o).doubleValue();
        }

        private static String libSexe(String s) {
            return ConstanteFerme.IDSEXEFEMELLE.equals(s) ? "Femelle"
                : ConstanteFerme.IDSEXEMALE.equals(s) ? "Mâle" : s;
        }

    @Override
    public String getNomClasseMere() {
        return "ferme.suiviJournalier.SuiviJournalier";
    }

    @Override
    public String getLiaisonMere() {
        return "idmere";
    }

    public SuiviJournalierDetail() throws Exception {
        this.setNomTable("SUIVIJOURNALIERDETAIL");
        this.setNomClasseMere("ferme.suiviJournalier.SuiviJournalier");
        this.setLiaisonMere("idmere");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("SJD","getseq_suivijournalierdetail");
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
}

