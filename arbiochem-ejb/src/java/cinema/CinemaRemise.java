package cinema;

import bean.ClassMAPTable;
import bean.CGenUtil;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
     * Règles valides à la date (filtre de dates uniquement).
     */
    public static CinemaRemise[] findActives(Date dateRef, Connection c) throws Exception {
        CinemaRemise crit = new CinemaRemise();
        String d = dateRef.toString();
        String where = " AND TRUNC(datydebut) <= TO_DATE('" + d + "', 'YYYY-MM-DD') "
                     + " AND (datyfin IS NULL OR TRUNC(datyfin) >= TO_DATE('" + d + "', 'YYYY-MM-DD')) ";
        CinemaRemise[] r = (CinemaRemise[]) CGenUtil.rechercher(crit, null, null, c, where);
        return r == null ? new CinemaRemise[0] : r;
    }

    /**
     * Règles réellement appliquées pour une réservation.
     * cats = catégorie de chaque place achetée (une entrée par place).
     */
    public static CinemaRemise[] findDeclenchees(String[] cats, Date dateRef, Connection c) throws Exception {
        // 1. Filtrer les règles dont le seuil est atteint
        List<CinemaRemise> declenchees = new ArrayList<>();
        for (CinemaRemise r : findActives(dateRef, c)) {
            if (compter(cats, r.getIdcategorie()) >= r.getSeuilplace()) {
                declenchees.add(r);
            }
        }
        
        // 2. Appliquer la règle de dominance (même source + même cible = garder le seuil max)
        List<CinemaRemise> retenues = new ArrayList<>();
        for (CinemaRemise r : declenchees) {
            boolean dominee = false;
            for (CinemaRemise autre : declenchees) {
                if (autre.getIdcategorie().equals(r.getIdcategorie())
                        && autre.getIdcategorietarget().equals(r.getIdcategorietarget())
                        && autre.getSeuilplace() > r.getSeuilplace()) {
                    dominee = true;
                    break;
                }
            }
            if (!dominee) retenues.add(r);
        }
        return retenues.toArray(new CinemaRemise[0]);
    }

    private static int compter(String[] cats, String idCat) {
        int n = 0;
        for (String cat : cats) {
            if (idCat.equals(cat)) n++;
        }
        return n;
    }

    @Override
    public void controler(Connection c) throws Exception {
        // Empêche les doublons exacts (même source, même cible, même seuil, même date de début)
        CinemaRemise crit = new CinemaRemise();
        String where = " AND idcategorie = '" + this.getIdcategorie() + "' "
                     + " AND idcategorietarget = '" + this.getIdcategorietarget() + "' "
                     + " AND seuilplace = " + this.getSeuilplace() + " "
                     + " AND TRUNC(datydebut) = TO_DATE('" + this.getDatydebut() + "', 'YYYY-MM-DD') ";
        if (this.getId() != null && !this.getId().isEmpty()) {
            where += " AND id <> '" + this.getId() + "' ";
        }
        
        CinemaRemise[] existantes = (CinemaRemise[]) CGenUtil.rechercher(crit, null, null, c, where);
        if (existantes != null && existantes.length > 0) {
            throw new Exception("Une règle identique existe déjà pour cette catégorie, cette cible et ce seuil à cette date.");
        }
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