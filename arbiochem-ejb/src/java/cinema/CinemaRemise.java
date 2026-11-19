package cinema;

import bean.ClassMAPTable;
import bean.CGenUtil;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CinemaRemise extends ClassMAPTable {
    private String id;
    private String idcategorie;
    private int seuilplace;
    private double pourcentage;
    private String idcategorietarget;
    private Date datydebut;
    private Date datyfin;

    public CinemaRemise() throws Exception {
        this.setNomTable("cinema_remise");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CREM", "getseq_cinema_remise");
        this.setId(makePK(c));
    }

    @Override public String getTuppleID() { return id; }
    @Override public String getAttributIDName() { return "id"; }

    @Override
    public String[] getMotCles() {
        return new String[]{"idcategorie", "idcategorietarget", "seuilplace", "datydebut"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"idcategorie", "idcategorietarget", "seuilplace", "datydebut"};
    }

    /**
     * Retourne toutes les règles applicables à une date donnée pour les quantités achetées.
     * Ne fusionne PAS : chaque règle déclenchée est retournée séparément.
     */
    public static CinemaRemise[] findApplicables(
            Map<String, Integer> qteParCategorie,
            Date dateRef,
            Connection c) throws Exception {

        if (qteParCategorie == null || qteParCategorie.isEmpty()) {
            return new CinemaRemise[0];
        }

        CinemaRemise crit = new CinemaRemise();
        String dateStr = dateRef.toString();
        String where = " AND TRUNC(datydebut) <= TO_DATE('" + dateStr + "', 'YYYY-MM-DD') "
                     + " AND (datyfin IS NULL OR TRUNC(datyfin) >= TO_DATE('" + dateStr + "', 'YYYY-MM-DD')) ";
        CinemaRemise[] toutes = (CinemaRemise[]) CGenUtil.rechercher(crit, null, null, c, where);

        if (toutes == null || toutes.length == 0) return new CinemaRemise[0];

        List<CinemaRemise> retenues = new ArrayList<>();
        for (CinemaRemise r : toutes) {
            Integer qte = qteParCategorie.get(r.getIdcategorie());
            if (qte != null && qte >= r.getSeuilplace()) {
                
                retenues.add(r);
            }
        }
        return retenues.toArray(new CinemaRemise[0]);
    }

    // Getters / setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdcategorie() { return idcategorie; }
    public void setIdcategorie(String idcategorie) { this.idcategorie = idcategorie; }
    public int getSeuilplace() { return seuilplace; }
    public void setSeuilplace(int seuilplace) { this.seuilplace = seuilplace; }
    public double getPourcentage() { return pourcentage; }
    public void setPourcentage(double pourcentage) { this.pourcentage = pourcentage; }
    public String getIdcategorietarget() { return idcategorietarget; }
    public void setIdcategorietarget(String idcategorietarget) { this.idcategorietarget = idcategorietarget; }
    public Date getDatydebut() { return datydebut; }
    public void setDatydebut(Date datydebut) { this.datydebut = datydebut; }
    public Date getDatyfin() { return datyfin; }
    public void setDatyfin(Date datyfin) { this.datyfin = datyfin; }
}