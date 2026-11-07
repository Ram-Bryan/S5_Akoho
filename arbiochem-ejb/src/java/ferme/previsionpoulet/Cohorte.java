package ferme.previsionpoulet;

import java.time.LocalDate;

/**
 * DTO interne à la simulation. Représente un groupe d'oiseaux de même
 * naissance et même sexe, qu'il soit réel (issu d'un triage) ou virtuel
 * (issu d'une éclosion simulée). Modifié jour après jour pendant la simulation.
 */
public class Cohorte {

    private String idLot;
    private String idBatiment;
    private String idParquet;
    private String idSexe;
    private LocalDate naissance;
    private LocalDate dateTriage;
    private double vivants;
    private double morts;
    private double perdus;

    public String getIdLot() {
        return idLot;
    }

    public void setIdLot(String idLot) {
        this.idLot = idLot;
    }

    public String getIdBatiment() {
        return idBatiment;
    }

    public void setIdBatiment(String idBatiment) {
        this.idBatiment = idBatiment;
    }

    public String getIdParquet() {
        return idParquet;
    }

    public void setIdParquet(String idParquet) {
        this.idParquet = idParquet;
    }

    public String getIdSexe() {
        return idSexe;
    }

    public void setIdSexe(String idSexe) {
        this.idSexe = idSexe;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    public LocalDate getDateTriage() {
        return dateTriage;
    }

    public void setDateTriage(LocalDate dateTriage) {
        this.dateTriage = dateTriage;
    }

    public double getVivants() {
        return vivants;
    }

    public void setVivants(double vivants) {
        this.vivants = vivants;
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

    public boolean isReelle() {
        return idBatiment != null && idParquet != null;
    }
}