package ferme.previsionpoulet;

import java.sql.Date;

/**
 * DTO de résultat : une ligne du calendrier détail pour un jour donné.
 * Peut représenter une cohorte réelle (avec bâtiment et parquet) ou virtuelle
 * (descendants d'un lot, sans affectation).
 */
public class PrevisionJourDetail {

    private Date date;
    private String idLot;
    private String nomLot;
    private String idBatiment;
    private String nomBatiment;
    private String idParquet;
    private String nomParquet;
    private double poussins;
    private double adultes;
    private double morts;
    private double perdus;
    private boolean virtuel;
    private String avertissement;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIdLot() {
        return idLot;
    }

    public void setIdLot(String idLot) {
        this.idLot = idLot;
    }

    public String getNomLot() {
        return nomLot;
    }

    public void setNomLot(String nomLot) {
        this.nomLot = nomLot;
    }

    public String getIdBatiment() {
        return idBatiment;
    }

    public void setIdBatiment(String idBatiment) {
        this.idBatiment = idBatiment;
    }

    public String getNomBatiment() {
        return nomBatiment;
    }

    public void setNomBatiment(String nomBatiment) {
        this.nomBatiment = nomBatiment;
    }

    public String getIdParquet() {
        return idParquet;
    }

    public void setIdParquet(String idParquet) {
        this.idParquet = idParquet;
    }

    public String getNomParquet() {
        return nomParquet;
    }

    public void setNomParquet(String nomParquet) {
        this.nomParquet = nomParquet;
    }

    public double getPoussins() {
        return poussins;
    }

    public void setPoussins(double poussins) {
        this.poussins = poussins;
    }

    public double getAdultes() {
        return adultes;
    }

    public void setAdultes(double adultes) {
        this.adultes = adultes;
    }

    public double getMorts() {
        return morts;
    }

    public void setMorts(double morts) {
        this.morts = morts;
    }

    public double getPerdus() {
        return perdus;
    }

    public void setPerdus(double perdus) {
        this.perdus = perdus;
    }

    public boolean isVirtuel() {
        return virtuel;
    }

    public void setVirtuel(boolean virtuel) {
        this.virtuel = virtuel;
    }

    public String getAvertissement() {
        return avertissement;
    }

    public void setAvertissement(String avertissement) {
        this.avertissement = avertissement;
    }

    public double getTotal(boolean inclureMorts) {
        return poussins + adultes + (inclureMorts ? morts : 0);
    }

    public String getLibelleLot() {
        if (virtuel) {
            return "Descendants du lot " + nomLot;
        }
        return nomLot;
    }

    public String getLibelleBatiment() {
        if (virtuel || nomBatiment == null) {
            return "non affecté";
        }
        return nomBatiment;
    }

    public String getLibelleParquet() {
        if (virtuel || nomParquet == null) {
            return "non affecté";
        }
        return nomParquet;
    }
}