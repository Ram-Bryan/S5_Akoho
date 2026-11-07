package ferme.configuration;

import bean.TypeObjet;
import java.sql.Connection;

public class Souche extends TypeObjet {
    private int incubationjours;
    private double pourcentagefemelle;
    private int debutpontejours;
    private int ageadultejours;
    private int dureeviejours;

    public Souche() {
        super.setNomTable("souche");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        super.setNomTable("souche");
        this.preparePk("SOU", "GETSEQSOUCHE");
        this.setId(this.makePK(c));
    }

    public int getIncubationjours() { return incubationjours; }
    public void setIncubationjours(int incubationjours) { this.incubationjours = incubationjours; }
    public double getPourcentagefemelle() { return pourcentagefemelle; }
    public void setPourcentagefemelle(double pourcentagefemelle) { this.pourcentagefemelle = pourcentagefemelle; }
    public int getDebutpontejours() { return debutpontejours; }
    public void setDebutpontejours(int debutpontejours) { this.debutpontejours = debutpontejours; }
    public int getAgeadultejours() { return ageadultejours; }
    public void setAgeadultejours(int ageadultejours) { this.ageadultejours = ageadultejours; }
    public int getDureeviejours() { return dureeviejours; }
    public void setDureeviejours(int dureeviejours) { this.dureeviejours = dureeviejours; }
}