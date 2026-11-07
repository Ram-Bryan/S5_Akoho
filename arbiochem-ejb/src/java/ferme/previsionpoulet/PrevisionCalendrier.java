package ferme.previsionpoulet;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Clone de CaCalendrier pour les effectifs. Même API publique :
 * getListeMois(), getJours(), getTotal(), + un get par jour/mois.
 */
public class PrevisionCalendrier {

    private int[] jours;
    private String[] listeMois;
    private HashMap<String, Map<String, List<PrevisionJourDetail>>> parMois;
    private HashMap<String, Double[]> total;  // total[mois] = {total, moyenne/jour rempli}
    private List<String> avertissements;
    private boolean inclureMorts;
    private String idFerme;

    public PrevisionCalendrier(String idFerme, String dtMin, String dtMax, boolean inclureMorts) throws Exception {
        this.idFerme = idFerme;
        this.inclureMorts = inclureMorts;
        this.jours = new int[31];
        for (int i = 0; i < 31; i++) this.jours[i] = i + 1;

        LocalDate dMin = parseDate(dtMin);
        LocalDate dMax = parseDate(dtMax);
        if (dMax == null) dMax = LocalDate.now().plusDays(730);
        if (dMin == null) dMin = dMax.minusMonths(6);
        if (dMin.isAfter(dMax)) {
            LocalDate t = dMin; dMin = dMax; dMax = t;
        }

        this.avertissements = new ArrayList<String>();

        java.sql.Connection c = null;
        try {
            c = new utilitaire.UtilDB().GetConn();
            List<PrevisionJourDetail> effectifs = PrevisionUtil.simuler(idFerme, dMin, dMax, avertissements, c);

            this.parMois = new LinkedHashMap<String, Map<String, List<PrevisionJourDetail>>>();
            Set<String> moisVus = new LinkedHashSet<String>();

            for (int i = 0; i < effectifs.size(); i++) {
                PrevisionJourDetail e = effectifs.get(i);
                LocalDate d = e.getDate().toLocalDate();
                if (d.isBefore(dMin) || d.isAfter(dMax)) continue;
                String mk = moisKey(d);
                String jk = String.valueOf(d.getDayOfMonth());
                moisVus.add(mk);
                Map<String, List<PrevisionJourDetail>> parJour = parMois.get(mk);
                if (parJour == null) {
                    parJour = new LinkedHashMap<String, List<PrevisionJourDetail>>();
                    parMois.put(mk, parJour);
                }
                List<PrevisionJourDetail> liste = parJour.get(jk);
                if (liste == null) {
                    liste = new ArrayList<PrevisionJourDetail>();
                    parJour.put(jk, liste);
                }
                liste.add(e);
            }

            this.listeMois = moisVus.toArray(new String[0]);

            this.total = new HashMap<String, Double[]>();
            for (int i = 0; i < listeMois.length; i++) {
                String mk = listeMois[i];
                Map<String, List<PrevisionJourDetail>> joursDuMois = parMois.get(mk);
                double somme = 0;
                int nb = 0;
                if (joursDuMois != null) {
                    for (List<PrevisionJourDetail> lst : joursDuMois.values()) {
                        for (int k = 0; k < lst.size(); k++) {
                            somme += lst.get(k).getTotal(inclureMorts);
                            nb++;
                        }
                    }
                }
                double moyenne = (nb == 0) ? 0 : somme / nb;
                total.put(mk, new Double[]{somme, moyenne});
            }
        } finally {
            if (c != null) try { c.close(); } catch (Exception ex) {}
        }
    }

    public int[] getJours() { return jours; }
    public String[] getListeMois() { return listeMois; }
    public HashMap<String, Double[]> getTotal() { return total; }
    public List<String> getAvertissements() { return avertissements; }
    public boolean isInclureMorts() { return inclureMorts; }

    public PrevisionJourDetail[] getDetailByDay(int jour, String mois) {
        Map<String, List<PrevisionJourDetail>> joursDuMois = parMois.get(mois);
        if (joursDuMois == null) {
            return new PrevisionJourDetail[0];
        }
        List<PrevisionJourDetail> res = joursDuMois.get(String.valueOf(jour));
        if (res == null) {
            return new PrevisionJourDetail[0];
        }
        return res.toArray(new PrevisionJourDetail[0]);
    }

    public double getTotalCellule(int jour, String mois) {
        PrevisionJourDetail[] tab = getDetailByDay(jour, mois);
        double t = 0;
        for (int i = 0; i < tab.length; i++) {
            t += tab[i].getTotal(inclureMorts);
        }
        return t;
    }

    private static LocalDate parseDate(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        try {
            String[] p = s.trim().split("[/\\-]");
            return LocalDate.of(Integer.parseInt(p[2]), Integer.parseInt(p[1]), Integer.parseInt(p[0]));
        } catch (Exception e) {
            return null;
        }
    }

    private static String moisKey(LocalDate d) {
        return d.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE) + " " + d.getYear();
    }
}