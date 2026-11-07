package ferme.previsionpoulet;

import bean.CGenUtil;
import ferme.configuration.Souche;
import ferme.lot.Lot;
import ferme.programme.ProgrammeZootechniqueDetails;
import ferme.utils.ConstanteFerme;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Simulateur multi-génération. 100 % Java, aucune logique en SQL.
 * Lecture uniquement via les vues PREV_TRIAGE_V, PREV_SUIVI_V, PREV_LOT_V.
 */
public class PrevisionUtil {

    public static final int MAX_HORIZON_JOURS = 730;
    private static final double EPS = 0.001;

    /**
     * Point d'entrée. Simule entre dateMin (affichage) et dateMax (affichage +
     * horizon).
     * Les avertissements sont ajoutés à la liste passée en argument.
     */
    public static List<PrevisionJourDetail> simuler(String idFerme,
            LocalDate dateMinUI,
            LocalDate dateMaxUI,
            List<String> avertissements,
            Connection c) throws Exception {

        LocalDate today = LocalDate.now();
        LocalDate horizonMax = today.plusDays(MAX_HORIZON_JOURS);

        // ---------- 1. Charger les lots ----------
        Map<String, Lot> lots = chargerLots(idFerme, c, avertissements);
        if (lots.isEmpty()) {
            avertissements.add("Aucun lot avec DATEECLOSION pour cette ferme.");
            return new ArrayList<PrevisionJourDetail>();
        }

        // ---------- 2. Charger souches + courbes ----------
        Map<String, Souche> souches = new HashMap<String, Souche>();
        Map<String, ProgrammeZootechniqueDetails[]> courbes = new HashMap<String, ProgrammeZootechniqueDetails[]>();
        Map<String, Map<String, double[]>> survies = new HashMap<String, Map<String, double[]>>();

        for (Lot lot : lots.values()) {
            String idProg = lot.getIdprogramme();
            String idSouche = lot.getIdSouche();

            if (idSouche == null || idSouche.trim().isEmpty()) {
                avertissements.add("Lot " + lot.getNomlot() + " : aucune souche, ignoré.");
                continue;
            }
            Souche s = souches.get(idSouche);
            if (s == null) {
                s = chargerSouche(idSouche, c);
                if (s == null) {
                    avertissements.add("Souche " + idSouche + " introuvable (lot " + lot.getNomlot() + ")");
                    continue;
                }
                souches.put(idSouche, s);
            }
            // CORRIGÉ : int primitif, on teste <= 0 au lieu de == null
            if (s.getDebutpontejours() <= 0 || s.getDureeviejours() <= 0) {
                avertissements.add("Lot " + lot.getNomlot() + " : paramètres de race incomplets.");
            }

            ProgrammeZootechniqueDetails[] courbe = courbes.get(idProg);
            if (courbe == null && idProg != null && !idProg.trim().isEmpty()) {
                courbe = chargerCourbe(idProg, c);
                courbes.put(idProg, courbe);
            }
            if (courbe == null) {
                courbe = new ProgrammeZootechniqueDetails[0];
                courbes.put(idProg, courbe);
            }

            // Précalcul de la survie cumulée par sexe pour ce lot
            if (!survies.containsKey(idSouche)) {
                Map<String, double[]> sParSexe = new HashMap<String, double[]>();
                sParSexe.put(ConstanteFerme.IDSEXEFEMELLE, precalculerSurvie(courbe, ConstanteFerme.IDSEXEFEMELLE));
                sParSexe.put(ConstanteFerme.IDSEXEMALE, precalculerSurvie(courbe, ConstanteFerme.IDSEXEMALE));
                survies.put(idSouche, sParSexe);
            }
        }

        // ---------- 3. Charger les cohortes réelles ----------
        Map<String, List<Cohorte>> cohortesParLot = new HashMap<String, List<Cohorte>>();
        LocalDate plusAncienTriage = null;
        for (Lot lot : lots.values()) {
            List<Cohorte> cs = chargerCohortesReelles(lot, c);
            cohortesParLot.put(lot.getId(), cs);
            for (int i = 0; i < cs.size(); i++) {
                Cohorte co = cs.get(i);
                LocalDate jt = co.getDateTriage();
                if (jt != null && (plusAncienTriage == null || jt.isBefore(plusAncienTriage))) {
                    plusAncienTriage = jt;
                }
            }
        }
        if (plusAncienTriage == null) {
            avertissements.add("Aucun triage validé (ETAT >= 11) pour cette ferme.");
            return new ArrayList<PrevisionJourDetail>();
        }

        // ---------- 4. Fenêtre ----------
        LocalDate dateFin = horizonMax;
        if (dateMaxUI != null && dateMaxUI.isBefore(horizonMax)) {
            dateFin = dateMaxUI;
        }
        LocalDate dateDebut = plusAncienTriage;
        if (dateDebut.isAfter(dateFin)) {
            dateDebut = dateFin;
        }

        // ---------- 5. Suivi réel ----------
        Map<String, Map<LocalDate, double[]>> suivi = chargerSuivi(idFerme, c);

        // ---------- 6. Noms bâtiment / parquet ----------
        Map<String, String> nomsBat = chargerNoms("BATIMENT", "NOMBATIMENT", c);
        Map<String, String> nomsParquet = chargerNoms("PARQUET", "VAL", c);

        // ---------- 7. Schedule des éclosions futures ----------
        // date -> idLot -> sexe -> qty
        Map<LocalDate, Map<String, Map<String, Double>>> schedule = new HashMap<LocalDate, Map<String, Map<String, Double>>>();

        // ---------- 8. Descendants par lot ----------
        // idLot -> sexe -> birthDate -> count
        Map<String, Map<String, Map<LocalDate, Double>>> descendants = new HashMap<String, Map<String, Map<LocalDate, Double>>>();

        List<PrevisionJourDetail> resultat = new ArrayList<PrevisionJourDetail>();

        // ---------- 9. Boucle jour par jour ----------
        for (LocalDate d = dateDebut; !d.isAfter(dateFin); d = d.plusDays(1)) {

            // 9.1 Éclosions prévues ce jour
            Map<String, Map<String, Double>> hatches = schedule.remove(d);
            if (hatches != null) {
                for (Map.Entry<String, Map<String, Double>> e : hatches.entrySet()) {
                    String idLot = e.getKey();
                    Map<String, Double> parSexe = e.getValue();
                    for (Map.Entry<String, Double> sq : parSexe.entrySet()) {
                        String sexe = sq.getKey();
                        Double qty = sq.getValue();
                        Map<String, Map<LocalDate, Double>> sexeMap = descendants.get(idLot);
                        if (sexeMap == null) {
                            sexeMap = new HashMap<String, Map<LocalDate, Double>>();
                            descendants.put(idLot, sexeMap);
                        }
                        Map<LocalDate, Double> birthMap = sexeMap.get(sexe);
                        if (birthMap == null) {
                            birthMap = new HashMap<LocalDate, Double>();
                            sexeMap.put(sexe, birthMap);
                        }
                        Double existant = birthMap.get(d);
                        if (existant == null) {
                            birthMap.put(d, qty);
                        } else {
                            birthMap.put(d, existant + qty);
                        }
                    }
                }
            }

            // 9.2 Mortalité + ponte sur cohortes réelles
            for (Map.Entry<String, List<Cohorte>> entry : cohortesParLot.entrySet()) {
                String idLot = entry.getKey();
                Lot lot = lots.get(idLot);
                if (lot == null)
                    continue;
                Souche souche = souches.get(lot.getIdSouche());
                ProgrammeZootechniqueDetails[] courbe = courbes.get(lot.getIdprogramme());
                if (souche == null || courbe == null)
                    continue;
                Map<String, double[]> survieParSexe = survies.get(lot.getIdSouche());

                List<Cohorte> liste = entry.getValue();
                for (int i = 0; i < liste.size(); i++) {
                    Cohorte co = liste.get(i);
                    if (co.getDateTriage() != null && d.isBefore(co.getDateTriage())) {
                        continue;
                    }
                    int age = (int) ChronoUnit.DAYS.between(lot.getDateeclosion().toLocalDate(), d);
                    if (age < 0)
                        continue;

                    if (!d.isAfter(today)) {
                        // réel : applique le suivi du jour
                        Map<LocalDate, double[]> suiviLot = suivi.get(idLot);
                        if (suiviLot != null) {
                            double[] ev = suiviLot.get(d);
                            if (ev != null) {
                                double m = Math.min(co.getVivants(), ev[0]);
                                double p = Math.min(co.getVivants() - m, ev[1]);
                                co.setVivants(co.getVivants() - m - p);
                                co.setMorts(co.getMorts() + m);
                                co.setPerdus(co.getPerdus() + p);
                            }
                        }
                    } else {
                        // projeté : taux hebdo / 7
                        double rate = mortaliteJour(courbe, co.getIdSexe(), age);
                        double newMort = co.getVivants() * rate;
                        co.setVivants(co.getVivants() - newMort);
                        co.setMorts(co.getMorts() + newMort);
                    }

                    // fin de vie — CORRIGÉ : int primitif
                    if (souche.getDureeviejours() > 0 && age >= souche.getDureeviejours()) {
                        co.setVivants(0);
                    }

                    // ponte des femelles réelles
                    if (ConstanteFerme.IDSEXEFEMELLE.equals(co.getIdSexe())
                            && souche.getDebutpontejours() > 0
                            && age >= souche.getDebutpontejours()
                            && co.getVivants() > EPS) {
                        double eggs = co.getVivants()
                                * ponteJour(courbe, co.getIdSexe(), age)
                                * oacPct(courbe, co.getIdSexe(), age)
                                * fertilitePct(courbe, co.getIdSexe(), age)
                                * eclosionPct(courbe, co.getIdSexe(), age);
                        planifier(schedule, d, idLot, eggs, souche.getIncubationjours(),
                                souche.getPourcentagefemelle());
                    }
                }
            }

            // 9.3 Ponte des descendants (cohortes virtuelles)
            for (Map.Entry<String, Map<String, Map<LocalDate, Double>>> entry : descendants.entrySet()) {
                String idLot = entry.getKey();
                Lot lot = lots.get(idLot);
                if (lot == null)
                    continue;
                Souche souche = souches.get(lot.getIdSouche());
                ProgrammeZootechniqueDetails[] courbe = courbes.get(lot.getIdprogramme());
                Map<String, double[]> survieParSexe = survies.get(lot.getIdSouche());
                if (souche == null || courbe == null || survieParSexe == null)
                    continue;
                // CORRIGÉ : int primitif
                if (souche.getDebutpontejours() <= 0)
                    continue;

                Map<LocalDate, Double> femelles = entry.getValue().get(ConstanteFerme.IDSEXEFEMELLE);
                if (femelles == null)
                    continue;

                double eggs = 0;
                for (Map.Entry<LocalDate, Double> fe : femelles.entrySet()) {
                    LocalDate birth = fe.getKey();
                    double count = fe.getValue();
                    int age = (int) ChronoUnit.DAYS.between(birth, d);
                    if (age < 0)
                        continue;
                    // CORRIGÉ : int primitif
                    if (souche.getDureeviejours() > 0 && age >= souche.getDureeviejours()) {
                        continue;
                    }
                    double surv = survieAt(survieParSexe.get(ConstanteFerme.IDSEXEFEMELLE), age);
                    double vivantes = count * surv;
                    if (vivantes <= EPS)
                        continue;
                    if (age < souche.getDebutpontejours())
                        continue;

                    eggs += vivantes
                            * ponteJour(courbe, ConstanteFerme.IDSEXEFEMELLE, age)
                            * oacPct(courbe, ConstanteFerme.IDSEXEFEMELLE, age)
                            * fertilitePct(courbe, ConstanteFerme.IDSEXEFEMELLE, age)
                            * eclosionPct(courbe, ConstanteFerme.IDSEXEFEMELLE, age);
                }
                if (eggs > EPS) {
                    planifier(schedule, d, idLot, eggs, souche.getIncubationjours(), souche.getPourcentagefemelle());
                }
            }

            // 9.4 Snapshot
            if (dateMinUI == null || !d.isBefore(dateMinUI)) {
                snapshot(d, lots, cohortesParLot, descendants, souches, courbes, survies,
                        nomsBat, nomsParquet, resultat);
            }
        }
        return resultat;
    }

    // =================================================================
    // Chargement
    // =================================================================

    private static Map<String, Lot> chargerLots(String idFerme, Connection c, List<String> avert) throws Exception {
        Map<String, Lot> lots = new LinkedHashMap<String, Lot>();
        String sql = "SELECT IDLOT, NOMLOT, DATEECLOSION, IDPROGRAMME, IDSOUCHE " +
                "FROM PREV_LOT_V WHERE IDFERME = '" + esc(idFerme) + "' AND DATEECLOSION IS NOT NULL";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = c.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Lot l = new Lot();
                l.setId(rs.getString(1));
                l.setNomlot(rs.getString(2));
                l.setDateeclosion(rs.getDate(3));
                l.setIdprogramme(rs.getString(4));
                l.setIdSouche(rs.getString(5));
                lots.put(l.getId(), l);
            }
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
        return lots;
    }

    private static Souche chargerSouche(String idSouche, Connection c) throws Exception {
        String sql = "SELECT ID, VAL, INCUBATIONJOURS, POURCENTAGEFEMELLE, DEBUTPONTEJOURS, " +
                "AGEADULTEJOURS, DUREEVIEJOURS FROM SOUCHE WHERE ID = '" + esc(idSouche) + "'";
        java.sql.Statement st = null;
        java.sql.ResultSet rs = null;
        try {
            st = c.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                Souche s = new Souche();
                s.setId(rs.getString(1));
                s.setVal(rs.getString(2));
                s.setIncubationjours(rs.getInt(3));
                s.setPourcentagefemelle(rs.getDouble(4));
                s.setDebutpontejours(rs.getInt(5)); // 0 si null
                s.setAgeadultejours(rs.getInt(6)); // 0 si null
                s.setDureeviejours(rs.getInt(7)); // 0 si null
                return s;
            }
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
        return null;
    }

    private static ProgrammeZootechniqueDetails[] chargerCourbe(String idProg, Connection c) throws Exception {
        ProgrammeZootechniqueDetails crit = new ProgrammeZootechniqueDetails();
        ProgrammeZootechniqueDetails[] tab = (ProgrammeZootechniqueDetails[]) CGenUtil.rechercher(crit, null, null, c,
                " AND IDMERE = '" + esc(idProg) + "' ORDER BY AGE, IDSEXE");
        return tab == null ? new ProgrammeZootechniqueDetails[0] : tab;
    }

    private static List<Cohorte> chargerCohortesReelles(Lot lot, Connection c) throws Exception {
        List<Cohorte> out = new ArrayList<Cohorte>();
        String sql = "SELECT IDBATIMENT, IDPARQUET, IDSEXE, QTE, DATY FROM PREV_TRIAGE_V " +
                "WHERE IDLOT = '" + esc(lot.getId()) + "' AND ETAT >= 11";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = c.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Cohorte co = new Cohorte();
                co.setIdLot(lot.getId());
                co.setIdBatiment(rs.getString(1));
                co.setIdParquet(rs.getString(2));
                co.setIdSexe(rs.getString(3));
                co.setVivants(rs.getDouble(4));
                co.setMorts(0);
                co.setPerdus(0);
                co.setNaissance(lot.getDateeclosion().toLocalDate());
                co.setDateTriage(rs.getDate(5).toLocalDate());
                out.add(co);
            }
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
        return out;
    }

    private static Map<String, Map<LocalDate, double[]>> chargerSuivi(String idFerme, Connection c) throws Exception {
        Map<String, Map<LocalDate, double[]>> map = new HashMap<String, Map<LocalDate, double[]>>();
        String sql = "SELECT s.IDLOT, s.DATY, NVL(SUM(d.NBMORT),0), NVL(SUM(d.NBPERDU),0) " +
                "FROM SUIVIJOURNALIER s JOIN SUIVIJOURNALIERDETAIL d ON d.IDMERE = s.ID " +
                "WHERE s.ETAT >= 11 AND s.IDFERME = '" + esc(idFerme) + "' " +
                "AND d.IDSEXE IS NOT NULL " +
                "GROUP BY s.IDLOT, s.DATY";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = c.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String idLot = rs.getString(1);
                LocalDate dt = rs.getDate(2).toLocalDate();
                Map<LocalDate, double[]> m = map.get(idLot);
                if (m == null) {
                    m = new HashMap<LocalDate, double[]>();
                    map.put(idLot, m);
                }
                m.put(dt, new double[] { rs.getDouble(3), rs.getDouble(4) });
            }
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
        return map;
    }

    private static Map<String, String> chargerNoms(String table, String col, Connection c) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = c.createStatement();
            rs = st.executeQuery("SELECT ID, " + col + " FROM " + table);
            while (rs.next()) {
                map.put(rs.getString(1), rs.getString(2));
            }
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
        return map;
    }

    // =================================================================
    // Courbe : lookup par (semaine, sexe) avec fallback semaine précédente
    // =================================================================

    private static ProgrammeZootechniqueDetails detailAt(ProgrammeZootechniqueDetails[] courbe,
            String sexe, int ageJours) {
        if (courbe == null || courbe.length == 0)
            return null;
        int sem = Math.max(0, ageJours) / 7 + 1;
        // On cherche la semaine exacte ; si absente, on remonte jusqu'à 1
        for (int s = sem; s >= 1; s--) {
            for (int i = 0; i < courbe.length; i++) {
                ProgrammeZootechniqueDetails d = courbe[i];
                if (d.getAge() == null)
                    continue;
                int dsem;
                try {
                    dsem = Integer.parseInt(d.getAge().trim());
                } catch (Exception e) {
                    continue;
                }
                if (dsem == s && sexe.equals(d.getIdsexe())) {
                    return d;
                }
            }
        }
        return null;
    }

    private static double ponteJour(ProgrammeZootechniqueDetails[] courbe, String sexe, int age) {
    ProgrammeZootechniqueDetails d = detailAt(courbe, sexe, age);
    if (d == null) return 0;
    return d.getPontehebdomadaire() / 100.0;   // % → fraction
    }

    private static double mortaliteJour(ProgrammeZootechniqueDetails[] courbe, String sexe, int age) {
        ProgrammeZootechniqueDetails d = detailAt(courbe, sexe, age);
        if (d == null)
            return 0;
        return d.getMortalite() / 100.0 / 7.0;
    }

    private static double oacPct(ProgrammeZootechniqueDetails[] courbe, String sexe, int age) {
        ProgrammeZootechniqueDetails d = detailAt(courbe, sexe, age);
        if (d == null)
            return 1.0;
        double v = d.getOacpourcentage();
        return v <= 0 ? 1.0 : v / 100.0;
    }

    private static double fertilitePct(ProgrammeZootechniqueDetails[] courbe, String sexe, int age) {
        ProgrammeZootechniqueDetails d = detailAt(courbe, sexe, age);
        if (d == null)
            return 1.0;
        double v = d.getFertilite();
        return v <= 0 ? 1.0 : v / 100.0;
    }

    private static double eclosionPct(ProgrammeZootechniqueDetails[] courbe, String sexe, int age) {
        ProgrammeZootechniqueDetails d = detailAt(courbe, sexe, age);
        if (d == null)
            return 1.0;
        double v = d.getTauxeclosion();
        return v <= 0 ? 1.0 : v / 100.0;
    }

    private static double[] precalculerSurvie(ProgrammeZootechniqueDetails[] courbe, String sexe) {
        double[] s = new double[MAX_HORIZON_JOURS + 1];
        s[0] = 1.0;
        for (int a = 1; a <= MAX_HORIZON_JOURS; a++) {
            s[a] = s[a - 1] * (1.0 - mortaliteJour(courbe, sexe, a - 1));
        }
        return s;
    }

    private static double survieAt(double[] survie, int age) {
        if (survie == null)
            return 1.0;
        int a = age;
        if (a < 0)
            a = 0;
        if (a >= survie.length)
            a = survie.length - 1;
        return survie[a];
    }

    private static void planifier(Map<LocalDate, Map<String, Map<String, Double>>> schedule,
            LocalDate d, String idLot, double eggs,
            int incubationJours, double pctFemelle) {
        if (eggs <= EPS || incubationJours <= 0)
            return;
        LocalDate hatch = d.plusDays(incubationJours);
        double f = eggs * pctFemelle / 100.0;
        double m = eggs - f;

        Map<String, Map<String, Double>> parLot = schedule.get(hatch);
        if (parLot == null) {
            parLot = new HashMap<String, Map<String, Double>>();
            schedule.put(hatch, parLot);
        }
        Map<String, Double> parSexe = parLot.get(idLot);
        if (parSexe == null) {
            parSexe = new HashMap<String, Double>();
            parLot.put(idLot, parSexe);
        }
        Double fEx = parSexe.get(ConstanteFerme.IDSEXEFEMELLE);
        parSexe.put(ConstanteFerme.IDSEXEFEMELLE, (fEx == null ? 0 : fEx) + f);
        Double mEx = parSexe.get(ConstanteFerme.IDSEXEMALE);
        parSexe.put(ConstanteFerme.IDSEXEMALE, (mEx == null ? 0 : mEx) + m);
    }

    // =================================================================
    // Snapshot du jour
    // =================================================================

    private static void snapshot(LocalDate d,
            Map<String, Lot> lots,
            Map<String, List<Cohorte>> cohortesParLot,
            Map<String, Map<String, Map<LocalDate, Double>>> descendants,
            Map<String, Souche> souches,
            Map<String, ProgrammeZootechniqueDetails[]> courbes,
            Map<String, Map<String, double[]>> survies,
            Map<String, String> nomsBat,
            Map<String, String> nomsParquet,
            List<PrevisionJourDetail> sortie) {

        java.sql.Date sqlD = java.sql.Date.valueOf(d);

        // 9.5.a — Réel : une ligne par (lot, bat, parquet)
        Map<String, PrevisionJourDetail> buckets = new LinkedHashMap<String, PrevisionJourDetail>();

        for (Map.Entry<String, List<Cohorte>> entry : cohortesParLot.entrySet()) {
            String idLot = entry.getKey();
            Lot lot = lots.get(idLot);
            if (lot == null)
                continue;
            Souche souche = souches.get(lot.getIdSouche());

            List<Cohorte> liste = entry.getValue();
            for (int i = 0; i < liste.size(); i++) {
                Cohorte co = liste.get(i);
                if (co.getDateTriage() != null && d.isBefore(co.getDateTriage()))
                    continue;
                if (co.getVivants() <= EPS && co.getMorts() <= EPS && co.getPerdus() <= EPS)
                    continue;

                int age = (int) ChronoUnit.DAYS.between(lot.getDateeclosion().toLocalDate(), d);
                boolean adulte = estAdulte(souche, age);

                String key = idLot + "|" + co.getIdBatiment() + "|" + co.getIdParquet();
                PrevisionJourDetail row = buckets.get(key);
                if (row == null) {
                    row = new PrevisionJourDetail();
                    row.setDate(sqlD);
                    row.setIdLot(idLot);
                    row.setNomLot(lot.getNomlot());
                    row.setIdBatiment(co.getIdBatiment());
                    row.setNomBatiment(nomsBat.get(co.getIdBatiment()));
                    row.setIdParquet(co.getIdParquet());
                    row.setNomParquet(nomsParquet.get(co.getIdParquet()));
                    row.setVirtuel(false);
                    // CORRIGÉ : int primitif
                    if (souche != null && (souche.getDebutpontejours() <= 0 || souche.getDureeviejours() <= 0)) {
                        row.setAvertissement("Paramètres de race incomplets");
                    }
                    buckets.put(key, row);
                }
                if (adulte) {
                    row.setAdultes(row.getAdultes() + co.getVivants());
                } else {
                    row.setPoussins(row.getPoussins() + co.getVivants());
                }
                row.setMorts(row.getMorts() + co.getMorts());
                row.setPerdus(row.getPerdus() + co.getPerdus());
            }
        }
        // Tri stable : par lot puis bâtiment puis parquet
        List<PrevisionJourDetail> reels = new ArrayList<PrevisionJourDetail>(buckets.values());
        Collections.sort(reels, new java.util.Comparator<PrevisionJourDetail>() {
            public int compare(PrevisionJourDetail a, PrevisionJourDetail b) {
                int c = safe(a.getNomLot()).compareToIgnoreCase(safe(b.getNomLot()));
                if (c != 0)
                    return c;
                c = safe(a.getNomBatiment()).compareToIgnoreCase(safe(b.getNomBatiment()));
                if (c != 0)
                    return c;
                return safe(a.getNomParquet()).compareToIgnoreCase(safe(b.getNomParquet()));
            }
        });
        sortie.addAll(reels);

        // 9.5.b — Virtuel : une ligne par lot
        for (Map.Entry<String, Map<String, Map<LocalDate, Double>>> entry : descendants.entrySet()) {
            String idLot = entry.getKey();
            Lot lot = lots.get(idLot);
            if (lot == null)
                continue;
            Souche souche = souches.get(lot.getIdSouche());
            Map<String, double[]> survieParSexe = survies.get(lot.getIdSouche());
            if (souche == null || survieParSexe == null)
                continue;

            double p = 0, a = 0, m = 0;
            Map<String, Map<LocalDate, Double>> sexeMap = entry.getValue();
            for (Map.Entry<String, Map<LocalDate, Double>> se : sexeMap.entrySet()) {
                String sexe = se.getKey();
                double[] surv = survieParSexe.get(sexe);
                for (Map.Entry<LocalDate, Double> be : se.getValue().entrySet()) {
                    LocalDate birth = be.getKey();
                    double births = be.getValue();
                    int age = (int) ChronoUnit.DAYS.between(birth, d);
                    if (age < 0)
                        continue;

                    // CORRIGÉ : int primitif
                    boolean finVie = souche.getDureeviejours() > 0 && age >= souche.getDureeviejours();
                    double alive = finVie ? 0 : births * survieAt(surv, age);
                    double dead = births - alive;

                    if (estAdulte(souche, age)) {
                        a += alive;
                    } else {
                        p += alive;
                    }
                    m += dead;
                }
            }
            if (p + a + m <= EPS)
                continue;

            PrevisionJourDetail row = new PrevisionJourDetail();
            row.setDate(sqlD);
            row.setIdLot(idLot);
            row.setNomLot(lot.getNomlot());
            row.setVirtuel(true);
            row.setPoussins(p);
            row.setAdultes(a);
            row.setMorts(m);
            // CORRIGÉ : int primitif
            if (souche.getDebutpontejours() <= 0 || souche.getDureeviejours() <= 0) {
                row.setAvertissement("Paramètres de race incomplets");
            }
            sortie.add(row);
        }
    }

    private static boolean estAdulte(Souche souche, int age) {
        if (souche == null)
            return true;
        int seuil = souche.getAgeadultejours() > 0
                ? souche.getAgeadultejours()
                : souche.getDebutpontejours();
        if (seuil <= 0)
            return true;
        return age >= seuil;
    }

    // =================================================================
    // Utilitaires
    // =================================================================

    private static String esc(String s) {
        if (s == null)
            return "";
        return s.replace("'", "''");
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }
}